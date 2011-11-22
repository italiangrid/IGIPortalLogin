<%@ include file="/WEB-INF/jsp/init.jsp"%>
<script src="http://code.jquery.com/jquery-1.6.1.min.js"></script>

<script type="text/javascript">
<!--
	//-->
	function showMoreOption() {
		$("#<portlet:namespace/>moreOption").show("slow");
		$("#<portlet:namespace/>default").hide("slow");
		$("#<portlet:namespace/>vosId option[value = '0']").attr(
				"selected", "selected");
		$("#<portlet:namespace/>certsId option[value = '0']").attr(
				"selected", "selected");
	}

	function hideMoreOption() {
		$("#<portlet:namespace/>moreOption").hide("slow");
		$("#<portlet:namespace/>default").show("slow");
	}

</script>

<portlet:actionURL var="getProxyUrl">
	<portlet:param name="myaction" value="getProxy" />
</portlet:actionURL>

<jsp:useBean id="certs" type="java.util.List<portal.login.domain.Certificate>" scope="request"></jsp:useBean>
<jsp:useBean id="userVos" type="java.util.List<portal.login.domain.Vo>" scope="request"></jsp:useBean>

<aui:form name="addUserInfoForm" commandName="userInfo" method="post"
	action="${getProxyUrl}">

	<aui:layout>

		<aui:fieldset>

			<aui:column>
			
			<div name="default" id="<portlet:namespace/>default">
				<a href="#moreOption" onclick="showMoreOption();">More Option</a>
			</div>
			
			<div name="moreOption" id="<portlet:namespace/>moreOption" style="display: none;">
			
				<a href="#default" onclick="hideMoreOption();">Hide More Option</a>
			
				<aui:select name="certsId" label="Certificati">
									
					<aui:option value="0"><liferay-ui:message key="Default"/></aui:option>
					
					<c:forEach var="cert" items="${certs}">
						
						<aui:option value="${cert.idCert}"><liferay-ui:message key="${cert.subject}"/></aui:option> 	
	
					</c:forEach>
					
				</aui:select>
				
				<aui:select name="vosId" label="VOs">
									
					<aui:option value="0"><liferay-ui:message key="Default"/></aui:option>
					
					<c:forEach var="userVo" items="${userVos}">
						
						<aui:option value="${userVo.idVo}"><liferay-ui:message key="${userVo.vo}"/></aui:option> 	
	
					</c:forEach>
					
				</aui:select>
			
			</div>

			<aui:input name="proxyPass" type="password"
							label="Proxy Password" />

			</aui:column>

			<aui:button-row>
				<aui:button type="submit" value="Download"/>
			</aui:button-row>

		</aui:fieldset>
	</aui:layout>
</aui:form>


