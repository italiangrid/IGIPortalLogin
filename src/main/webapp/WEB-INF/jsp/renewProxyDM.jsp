<%@ include file="/WEB-INF/jsp/init.jsp"%>

<div id="containerLogin2">

<portlet:actionURL var="getProxyUrl">
	<portlet:param name="myaction" value="getProxy" />
</portlet:actionURL>

<portlet:renderURL var="homeUrl">
	<portlet:param name="myaction" value="idps" />
</portlet:renderURL>

<liferay-ui:error key="proxy-download-problem"
	message="proxy-download-problem" />

<aui:form name="addUserInfoForm" commandName="userInfo"
	action="${getProxyUrl}">
	
	<aui:input name="isDM" type="hidden" value="true"/>

	<aui:layout>
	
		<aui:fieldset>

			<aui:column>
				Insert the password set during the registration.<br/>
				<aui:input name="vosId" type="hidden"
								value="${Vo.idVo}" />
					
						<c:if test="${fn:length(userFqan) > 0 }">
	
						<aui:select id="fqan" name="fqan" label="Roles for ${Vo.vo}">
											
							<aui:option value="norole"><liferay-ui:message key="No Role"/></aui:option>
							
							
							
							<c:forTokens items="${userFqan}"
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
						
						<c:if test="${fn:length(userFqan) == 0 }">
							<aui:input name="fqan" type="hidden"
								value="norole" />
						</c:if>
					
			
			<aui:input name="proxyPass" type="password"
							label="Password" style="background: #ACDFA7;"/>

			</aui:column>

			<aui:button-row>
				<aui:button type="submit" value="Renew Credentials"/>
			</aui:button-row>

		</aui:fieldset>
	</aui:layout>
</aui:form>


</div>