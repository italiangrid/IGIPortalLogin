<%@ include file="/WEB-INF/jsp/init.jsp"%>

<script type="text/javascript">
<!--
	//-->
	
	function setValue(idVo){
		
		var valore = $("#<portlet:namespace/>fqan_"+idVo+" option:selected").val();
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
	}
	
	function start(){
		var defaultVo =  $('#<portlet:namespace/>defaultVo').val();
		alert(defaultVo);
		
		$('#<portlet:namespace/>vosId').find('option').each(function() {
            //alert($(this).val());
            //$('#<portlet:namespace/>div_fqans_'+$(this).val()).hide("slow");
            alerct($(this).text());
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

<portlet:actionURL var="getProxyUrl">
	<portlet:param name="myaction" value="getProxy" />
</portlet:actionURL>

<portlet:renderURL var="homeUrl">
	<portlet:param name="myaction" value="idps" />
</portlet:renderURL>

<jsp:useBean id="userVos" type="java.util.List<portal.login.domain.Vo>" scope="request"></jsp:useBean>
<jsp:useBean id="userFqans" type="java.util.Map" scope="request" />

<aui:form name="addUserInfoForm" commandName="userInfo" method="post"
	action="${getProxyUrl}">

	<aui:layout>

		<aui:fieldset>

			<aui:column>
			
			<c:if test="${fn:length(userVos) == 1}">
				<strong>VO <c:forEach var="userVo" items="${userVos}"><c:out value="${userVo.vo }"></c:out></c:forEach> </strong>
				<br/>
				Insert your password.
				<br/>	
				<aui:input name="vosId" type="hidden"
								value="0" />
			</c:if>
			
			<c:if test="${fn:length(userVos) > 1}">
				
				
				<strong>Select VO:</storng><br/>
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
											
							<aui:option value="norole"><liferay-ui:message key="No Role"/></aui:option>
							
							
							
							<c:forTokens items="${userFqans[userVo.idVo]}"
						                 delims=";"
						                 var="currentName"
						                 varStatus="status">
						      
						        
						        <aui:option value="${currentName}"><liferay-ui:message key="${currentName}"/></aui:option>
						    </c:forTokens>
							
						</aui:select>
						
						</c:if>
					
					</div>
				</c:forEach>
			<div id="<portlet:namespace/>result">
				<aui:input name="fqan" type="hidden" value="norole"></aui:input>
			</div>
			
			<aui:input name="proxyPass" type="password"
							label="Proxy Password" style="background: #ACDFA7;"/>

			</aui:column>

			<aui:button-row>
				<aui:button type="submit" value="Download"/>
				<aui:button type="cancel" value="Back"
						onClick="location.href='${homeUrl}';" />
			</aui:button-row>

		</aui:fieldset>
	</aui:layout>
</aui:form>


