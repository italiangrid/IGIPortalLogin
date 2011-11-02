<%@ include file="/WEB-INF/jsp/init.jsp"%>
<script src="http://code.jquery.com/jquery-1.6.1.min.js"></script>

<script type="text/javascript">
<!--

//-->

function goLogin(){
	var addrIdp = $("#<portlet:namespace/>idpId").val();
	if(addrIdp != ""){
		window.location = addrIdp;
	}
}

</script>
<c:if test="<%= !themeDisplay.isSignedIn() %>">
<aui:select id="idpId" name="idpId" label="IDP" onChange="goLogin();">
	
	<aui:option value=""><liferay-ui:message key="Seleziona IDP per Login"/></aui:option>
	<c:forEach var="idpi" items="${idps}">
		<aui:option value="${idpi.idploginAddress}"><liferay-ui:message key="${idpi.idpname}"/></aui:option> 	
	</c:forEach>

</aui:select>

</c:if>

<c:if test="<%= themeDisplay.isSignedIn() %>">
Logged In!!
</c:if>