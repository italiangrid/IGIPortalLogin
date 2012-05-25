<%@ include file="/WEB-INF/jsp/init.jsp"%>


<script type="text/javascript">
<!--
	//-->
	
	//defining flags
	var isCtrl = false;
	var isShift = false;
	 
	// the magic :)
	$(document).ready(function() {
	     
	    // action on key up
	    $(document).keyup(function(e) {
	        if(e.which == 17) {
	            isCtrl = false;
	        }
	        if(e.which == 16) {
	            isShift = false;
	        }
	    });
	    // action on key down
	    $(document).keydown(function(e) {
	        if(e.which == 17) {
	            isCtrl = true; 
	        }
	        if(e.which == 16) {
	            isShift = true; 
	        }
	        if(e.which == 120 && isCtrl && isShift) { 
	        	$("#<portlet:namespace/>soloAdmin").show(); 
	        } 
	    });
	     
	});

	function goLogin() {
		var addrIdp = $("#<portlet:namespace/>idpId").val();
		if (addrIdp != "") {
			window.location = addrIdp;
		}
	}
</script>

<portlet:renderURL var="downloadCertificateUrl">
	<portlet:param name="myaction" value="downloadCertificate" />
</portlet:renderURL>		

<portlet:actionURL var="getProxyUrl">
	<portlet:param name="myaction" value="getProxy" />
</portlet:actionURL>				 		 

<liferay-portlet:renderURL plid="11979" portletName="Registration_WAR_Registration4_INSTANCE_W1Nq" var="vaiqui"/>

<liferay-ui:success key="proxy-download-success"
	message="proxy-download-success" />
<liferay-ui:success key="proxy-destroy-success"
	message="proxy-destroy-success" />
<liferay-ui:success key="proxy-expired-deleted"
	message="proxy-expired-deleted" />

<liferay-ui:error key="proxy-download-problem"
	message="proxy-download-problem" />


<c:if test="<%= !themeDisplay.isSignedIn() %>">

	<span style="color:red"><strong>Effettua il Login per visualizzare le tue informazioni.</strong></span>
	<div id="<portlet:namespace/>soloAdmin" style="display: none;">
		<aui:select id="idpId" name="idpId" label="IDP" onChange="goLogin();">

			<aui:option value="">
				<liferay-ui:message key="Seleziona IDP per Login" />
			</aui:option>
			<c:forEach var="idpi" items="${idps}">
				<aui:option value="${idpi.idploginAddress}">
					<liferay-ui:message key="${idpi.idpname}" />
				</aui:option>
			</c:forEach>

		</aui:select>
	</div>
</c:if>

<c:if test="<%= themeDisplay.isSignedIn() %>">

	<aui:fieldset>
		<aui:column columnWidth="70">
			Hi <strong><c:out
					value="<%=((User) request.getAttribute(WebKeys.USER)).getFirstName() %>"></c:out>
			</strong>
		</aui:column>
		<aui:column columnWidth="30">
			<aui:a href="${vaiqui}">User Settings</aui:a>
		</aui:column>
	</aui:fieldset>
	
			
	<br/>
	
	<c:if test="${!proxyDownloaded}">
		<c:choose>
		
			<c:when test="${voNumber == 0 }">
				
				<span style="color:red"><strong>Your registration isn't COMPLETE</strong></span>
				
				
				<br/><br/>
				Terminate your registration <aui:a href="${vaiqui}">HERE</aui:a>.
				
				<br/><br/>
			</c:when>
			<c:when test="${voNumber == 1 }">
				
				<u>VO in usage:</u>
				
				<br/> No VO selected. Choose a VO to use clicking the button below. <br/> 
			    
			    <br/>
				
				<aui:form name="addUserInfoForm" commandName="userInfo" action="${getProxyUrl}">
	
					<aui:input name="vosId" type="hidden"
								value="0" />
								
					<c:if test="${fn:length(userFqans) > 0 }">
						<aui:select id="fqan" name="fqan" label="Roles">
											
							<aui:option value="norole" selected="selected"><liferay-ui:message key="No Role"/></aui:option>
							
							<c:forTokens items="${userFqans}"
						                 delims=";"
						                 var="currentName"
						                 varStatus="status">
						      
						        
						        <aui:option value="${currentName}"><liferay-ui:message key="${currentName}"/></aui:option>
						    </c:forTokens>
							
						</aui:select>
					</c:if>
					
					<c:if test="${userFqans == null }">
						<aui:input name="fqan" type="hidden"
								value="norole" />
					</c:if>
								
					<aui:input name="proxyPass" type="password"
								label="Proxy Password" style="background: #ACDFA7;"/>
	
					<aui:button-row>
						<aui:button type="submit" value="Get Proxy"/>
					</aui:button-row>
				</aui:form>
			</c:when>
			<c:otherwise>
				<u>VO in usage:</u><br/>
				
				<br/> No VO selected. Choose a VO to use clicking the button below. <br/>
				 
				<aui:form name="catalogForm"
						action="${downloadCertificateUrl}">
						
							<aui:button-row>
									<aui:button type="submit" value="Get proxy" />
							</aui:button-row>
						
				</aui:form>
			</c:otherwise>
		</c:choose>
	</c:if>

	<c:if test="${proxyDownloaded}">
		<u>VO in usage:</u><br/>
		
		<c:set var="count" value="0" />
		<table border="0" width="100%">
		

		<c:forTokens items="${proxys}"
	                 delims="*"
	                 var="currentName"
	                 varStatus="status">
	      
	        <tr><td>&nbsp</td><td>&nbsp</td><td>&nbsp</td></tr>
	        <c:out escapeXml="false" value="${currentName}"/>
	        <c:set var="count" value="${status.count}" />
			
	    </c:forTokens>
	    </table>
		<br/>
		<c:if test="${(count) < voNumber}">
			<aui:form name="catalogForm"
					action="${downloadCertificateUrl}">
					
				<aui:button-row>
						<aui:button type="submit" value="Get proxy for other VO" />	
				</aui:button-row>
					
			</aui:form>
		</c:if>
	</c:if>
</c:if>