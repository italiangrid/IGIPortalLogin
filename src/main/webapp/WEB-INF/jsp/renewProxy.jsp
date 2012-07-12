<%@ include file="/WEB-INF/jsp/init.jsp"%>

<div id="containerLogin">

<portlet:actionURL var="getProxyUrl">
	<portlet:param name="myaction" value="getProxy" />
</portlet:actionURL>

<portlet:renderURL var="homeUrl">
	<portlet:param name="myaction" value="idps" />
</portlet:renderURL>

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
<br/><br/><br/>

<aui:form name="addUserInfoForm" commandName="userInfo"
	action="${getProxyUrl}">

	<aui:layout>
	
		<aui:fieldset>

			<aui:column>
				<strong>VO <c:out value="${Vo.vo }"></c:out> </strong>
				<br/>
				Insert your password.
				<br/>	
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
							label="Proxy Password" style="background: #ACDFA7;"/>

			</aui:column>

			<aui:button-row>
				<aui:button type="submit" value="Renew"/>
				<aui:button type="cancel" value="Back"
						onClick="location.href='${homeUrl}';" />
			</aui:button-row>

		</aui:fieldset>
	</aui:layout>
</aui:form>


</div>