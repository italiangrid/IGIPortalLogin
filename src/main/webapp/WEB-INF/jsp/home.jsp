<%@ include file="/WEB-INF/jsp/init.jsp"%>
<script src="http://code.jquery.com/jquery-1.6.1.min.js"></script>

<script type="text/javascript">
<!--
	//-->

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

<portlet:actionURL var="destroyProxyUrl">
	<portlet:param name="myaction" value="destroyProxy" />
</portlet:actionURL>

<liferay-ui:success key="proxy-download-success"
	message="proxy-download-success" />
<liferay-ui:success key="proxy-destroy-success"
	message="proxy-destroy-success" />
<liferay-ui:success key="proxy-expired-deleted"
	message="proxy-expired-deleted" />

<liferay-ui:error key="proxy-download-problem"
	message="proxy-download-problem" />


<c:if test="<%= !themeDisplay.isSignedIn() %>">
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

</c:if>

<c:if test="<%= themeDisplay.isSignedIn() %>">

	Ciao <strong><c:out
					value="<%=((User) request.getAttribute(WebKeys.USER)).getFirstName() %>"></c:out>
			</strong>
	
	<c:if test="${!proxyDownloaded}">
		<c:choose>
		
			<c:when test="${voNumber == 0 }">
				<br/><br/>
				<span style="color:red"><strong>Carica un certificato!!!</strong></span>
				<br/><br/>
			</c:when>
			<c:when test="${voNumber == 1 }">
				<portlet:actionURL var="getProxyUrl">
					<portlet:param name="myaction" value="getProxy" />
				</portlet:actionURL>
				
				<br/><br/>
				
				<aui:form name="addUserInfoForm" commandName="userInfo" method="post" action="${getProxyUrl}">
	
					<aui:input name="vosId" type="hidden"
								value="0" />
								
					<aui:input name="proxyPass" type="password"
								label="Proxy Password" />
	
					<aui:button-row>
						<aui:button type="submit" value="Download"/>
					</aui:button-row>
				</aui:form>
			</c:when>
			<c:otherwise>
				<aui:form name="catalogForm" method="post"
						action="${downloadCertificateUrl}">
						<aui:button-row>
								<aui:button type="submit" value="Select VO" />
						</aui:button-row>
				</aui:form>
			</c:otherwise>
		</c:choose>
	</c:if>

	<c:if test="${proxyDownloaded}">
		<aui:form name="catalogForm" method="post"
				action="${downloadCertificateUrl}">
				<aui:button-row>
					<c:choose>
						<c:when test="${voNumber == 1 }">
							<aui:button type="submit" value="Reload VO" />
						</c:when>
						<c:otherwise>
							<aui:button type="submit" value="Change VO" />
						</c:otherwise>
					</c:choose>
				</aui:button-row>
			</aui:form>
	</c:if>
</c:if>