<%@ include file="/WEB-INF/jsp/init.jsp"%>
<script src="http://code.jquery.com/jquery-1.6.1.min.js"></script>

<portlet:actionURL var="getProxyUrl">
	<portlet:param name="myaction" value="getProxy" />
</portlet:actionURL>

<jsp:useBean id="certs" type="java.util.List<portal.login.domain.Certificate>" scope="request"></jsp:useBean>

<aui:form name="addUserInfoForm" commandName="userInfo" method="post"
	action="${getProxyUrl}">

	<aui:layout>

		<aui:fieldset>

			<aui:column>
			
			
			<aui:select name="certsId" label="Certificati">
								
				<aui:option value="0"><liferay-ui:message key="Default"/></aui:option>
				
				<c:forEach var="cert" items="${certs}">
					
					<aui:option value="${cert.idCert}"><liferay-ui:message key="${cert.subject}"/></aui:option> 	

				</c:forEach>
				
			</aui:select>

			<aui:input name="proxyPass" type="password"
							label="Proxy Password" />

			</aui:column>

			<aui:button-row>
				<aui:button type="submit" value="Download"/>
			</aui:button-row>

		</aui:fieldset>
	</aui:layout>
</aui:form>


