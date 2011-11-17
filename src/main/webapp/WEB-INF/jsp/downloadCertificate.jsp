<%@ include file="/WEB-INF/jsp/init.jsp"%>
<script src="http://code.jquery.com/jquery-1.6.1.min.js"></script>


Ciao mondo!!

<portlet:actionURL var="getProxyUrl">
	<portlet:param name="myaction" value="getProxy" />
</portlet:actionURL>


<aui:form name="addUserInfoForm" commandName="userInfo" method="post"
	action="${getProxyUrl}">

	<aui:layout>

		<aui:fieldset>

			<aui:column columnWidth="20">

			<aui:input id="proxyPass" name="proxyPass" type="password"
							label="Proxy Password" />

			</aui:column>

			<aui:button-row>
				<aui:button type="submit" value="Download"/>
			</aui:button-row>

		</aui:fieldset>
	</aui:layout>
</aui:form>


