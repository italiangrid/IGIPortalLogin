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
	
	function viewTooltip(url){
		
		$("#linkImg a").tooltip({
			
			bodyHandler: function() {
				return $(url).html();
			},
			showURL: false
			
		});
	}
	
	$(function() {


		$("#tooltipImg a").tooltip({
			bodyHandler: function() {
				
				return $($(this).attr("href")).html();
			},
			showURL: false
			
		});

	});

	
</script>

<div id="containerLogin">

<portlet:renderURL var="downloadCertificateUrl">
	<portlet:param name="myaction" value="downloadCertificate" />
</portlet:renderURL>		

<portlet:actionURL var="getProxyUrl">
	<portlet:param name="myaction" value="getProxy" />
</portlet:actionURL>				 		 

<!-- <liferay-portlet:renderURL plid="11979" portletName="Registration_WAR_Registration4_INSTANCE_W1Nq" var="vaiqui"/> -->
<liferay-portlet:renderURL plid="12312" portletName="Registration_WAR_Registration4_INSTANCE_7NTSc9EWudEm" var="vaiqui"/>


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
		<div id="presentationLogin">
			<aui:column columnWidth="70">
				<div style="height: 28px; display:table-cell; vertical-align:bottom;">
				Hi <strong><c:out
						value="<%=((User) request.getAttribute(WebKeys.USER)).getFirstName() %>"></c:out>
				</strong>
				</div>
			</aui:column>
			<aui:column columnWidth="30">
				<div id="linkImg">
					<aui:a href="${vaiqui}" onmouseover="viewTooltip('#settings');"><img src="<%=request.getContextPath()%>/images/advancedsettings.png" alt="User settings" width="24" height="24" style="float: right; padding-right:10px;" /></aui:a>
				</div>
			</aui:column>
		</div>
	</aui:fieldset>
	
			
	<br/> <br/>
	
	<liferay-ui:success key="proxy-download-success"
	message="proxy-download-success" />
<liferay-ui:success key="proxy-destroy-success"
	message="proxy-destroy-success" />
<liferay-ui:success key="proxy-expired-deleted"
	message="proxy-expired-deleted" />

<liferay-ui:error key="proxy-download-problem"
	message="proxy-download-problem" />
	
	<c:if test="${!proxyDownloaded}">
		<c:choose>
		
			<c:when test="${voNumber == 0 }">
			
				<div class="portlet-msg-error"> Your registration isn't COMPLETE.</div>
				
				<br/>
				Terminate your registration <aui:a href="${vaiqui}">HERE</aui:a>.
				
				<br/><br/>
			</c:when>
			<c:when test="${voNumber == 1 }">
				
				<br/> 
				
				<div class="portlet-msg-error"> No VO selected. Insert your proxy password and get proxy.</div>
				
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
						      
						        
						        		<c:choose>
								        <c:when test="${status.count == '1'}">
								        	<aui:option value="${currentName}" selected="true"> <liferay-ui:message key="${currentName}"/></aui:option>
										</c:when>
										<c:otherwise>
										<aui:option value="${currentName}" > <liferay-ui:message key="${currentName}"/></aui:option>
										</c:otherwise>
										</c:choose>
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
				
				<br/> 
				
				<div class="portlet-msg-error"> No VO selected. Choose a VO to use clicking the button below.</div>
				
				<br/>
				 
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
<div id="renewButton" style="display:none;">Renew proxy.</div>
<div id="settings" style="display:none;">User Settings.</div>
<div id="allOK" style="display:none;">All is OK.</div>
<div id="warning" style="display:none;">Your proxy will be expire,<br/> renew proxy.</div>

</div>