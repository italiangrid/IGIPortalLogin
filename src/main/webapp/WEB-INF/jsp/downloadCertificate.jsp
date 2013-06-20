<%@ include file="/WEB-INF/jsp/init.jsp"%>

<script type="text/javascript">
<!--
	//-->
	
	function setValue(idVo){
		
		var valore = $("#<portlet:namespace/>fqan_"+idVo+" option:selected").val();
		//alert(idVo)
		//alert(valore);
		
		var output = "";
	
		output = "<input name='<portlet:namespace/>fqan' id='<portlet:namespace/>fqan' type='hidden' value='" + valore+ "' />";
			   
		$("#<portlet:namespace/>result").html(output);
		
		
	}

	
	function showRoleList(){
		$('#<portlet:namespace/>vosId').find('option').each(function() {
            //alert($(this).val());
            $('#<portlet:namespace/>div_fqans_'+$(this).val()).hide("slow");
   		});
		var showThis = $('#<portlet:namespace/>vosId').val();
		//alert(showThis);
		$('#<portlet:namespace/>div_fqans_'+showThis).show("slow");
		
		//alert($('#<portlet:namespace/>fqan_'+showThis).val());
		
		setValue(showThis);
		
	}
	
	function start(){
		var defaultVo =  $('#<portlet:namespace/>defaultVo').val();
		alert(defaultVo);
		
		$('#<portlet:namespace/>vosId').find('option').each(function() {
            //alert($(this).val());
            //$('#<portlet:namespace/>div_fqans_'+$(this).val()).hide("slow");
            alert($(this).text());
            if($(this).text()==defaultVo){
            	alert("trovato");
            	$(this).attr("selected", "selected");
            }
   		});
	}
	
	$(document).ready(function() {
		showRoleList();
		//start();
	});

</script>

<div id="containerLogin2"> 

<portlet:actionURL var="getProxyUrl">
	<portlet:param name="myaction" value="getProxy" />
</portlet:actionURL>

<portlet:renderURL var="homeUrl">
	<portlet:param name="myaction" value="idps" />
</portlet:renderURL>

<jsp:useBean id="userVos" type="java.util.List<it.italiangrid.portal.dbapi.domain.Vo>" scope="request"></jsp:useBean>
<jsp:useBean id="userFqans" type="java.util.Map" scope="request" />

<aui:fieldset>
		<div id="presentationLogin">
			<aui:column columnWidth="70">
				<div style="height: 28px; display:table-cell; vertical-align:bottom;">
				Hi <strong><c:out
						value="<%=((User) request.getAttribute(WebKeys.USER)).getFirstName() %>"></c:out>
				</strong>
				</div>
			</aui:column>
		</div>
	</aui:fieldset>
<br/><br/><br/>
<aui:form name="addUserInfoForm" commandName="userInfo"
	action="${getProxyUrl}">

	
	
		

		<aui:fieldset>

			<aui:column>
			
			<c:if test="${fn:length(userVos) == 1}">
				Insert the password set during the registration.<br/><br/>
				Your VO is <strong><c:forEach var="userVo" items="${userVos}"><c:out value="${userVo.vo }"></c:out><aui:input name="vosId" type="hidden" value="${userVo.idVo}" /></c:forEach></strong>
					
				
			</c:if>
			
			<c:if test="${fn:length(userVos) > 1}">
				
				
				Choose a working VO and insert the password set during the registration.<br/><br/>
				<select id="<portlet:namespace/>vosId" name="<portlet:namespace/>vosId" onChange="showRoleList();">
					
					<c:forEach var="userVo" items="${userVos}">
						
						<c:if test="${fn:contains(userVo.vo, defaultVo)}">
							<c:out value="stampa questo"></c:out>
							<option value="${userVo.idVo}" selected="selected"><liferay-ui:message key="${userVo.vo}"/></option>
						</c:if>
						
						<c:if test="${userVo.vo != defaultVo}">
							<option value="${userVo.idVo}"><liferay-ui:message key="${userVo.vo}"/></option>
						</c:if>
						
	
					</c:forEach>
					
				</select>
			
			</c:if>
			
				
			
				<c:forEach var="userVo" items="${userVos}">
				
					<div id="<portlet:namespace/>div_fqans_${userVo.idVo}" style="display:none">
					
						<c:if test="${fn:length(userFqans[userVo.idVo]) > 0 }">
	
						<aui:select id="fqan_${userVo.idVo }" name="fqan_${userVo.idVo }" label="Roles for ${userVo.vo}" onChange="setValue(${userVo.idVo });">
											
							<aui:option value="norole" ><liferay-ui:message key="No Role"/></aui:option>
							
							
							
							<c:forTokens items="${userFqans[userVo.idVo]}"
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
						
						<c:if test="${fn:length(userFqans[userVo.idVo]) == 0 }">
						
							<div style="display:none;">
								<aui:select id="fqan_${userVo.idVo }" name="fqan_${userVo.idVo }" label="Roles for ${userVo.vo}" onChange="setValue(${userVo.idVo });">
												
									<aui:option value="norole"  selected="true"><liferay-ui:message key="No Role"/></aui:option>
									
									
									
									
									
								</aui:select>
							</div>
						</c:if>
						
						<c:if test="${fn:length(userFqans[userVo.idVo]) == 0 }">
						
							<div style="display:none;">
								<aui:select id="fqan_${userVo.idVo }" name="fqan_${userVo.idVo }" label="Roles for ${userVo.vo}" onChange="setValue(${userVo.idVo });">
												
									<aui:option value="norole"  selected="true"><liferay-ui:message key="No Role"/></aui:option>
									
									
									
									
									
								</aui:select>
							</div>
						</c:if>
					
					</div>
				</c:forEach>
			<div id="<portlet:namespace/>result">
				<aui:input name="fqan" type="hidden" value="norole"></aui:input>
			</div>
			
			<aui:input name="proxyPass" type="password"
							label="Password" style="background: #ACDFA7;"/>
			<br/>
			</aui:column>
			
			<aui:button-row>
				<aui:button type="submit" value="Get Credentials"/>
			</aui:button-row>

		</aui:fieldset>
	
</aui:form>


</div>
