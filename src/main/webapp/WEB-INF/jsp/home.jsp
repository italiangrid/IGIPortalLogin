<%@ include file="/WEB-INF/jsp/init.jsp"%>


<script type="text/javascript">
<!--
	//-->
	
$(document).ready(function() {
	
	$("#submitThis").submit();
	
	document.closeFrame = function(){  
		window.location.href=window.location.href.split('?')[0];
	};
});	

	
</script>

<c:set var="aws" value="<%= renderRequest.getWindowState()%>"/>
<c:set var="puws" value="<%= LiferayWindowState.POP_UP.toString()%>"/>
<c:if test="${aws!=puws }">
	
	
	<portlet:renderURL var="downloadCertificateUrl">
		<portlet:param name="myaction" value="downloadCertificate" />
	</portlet:renderURL>		
	
	<portlet:actionURL var="getProxyUrl">
		<portlet:param name="myaction" value="getProxy" />
	</portlet:actionURL>				 		 
	
	<c:if test="<%= !themeDisplay.isSignedIn() %>">
		<div id="containerLogin" style="margin-bottom: 10px;">
		<span style="color:red"><strong>Effettua il Login per visualizzare le tue informazioni.</strong></span>
		</div>
	</c:if>
	
	<c:if test="<%= themeDisplay.isSignedIn() %>">
		
		<c:if test="${fn:length(shortProxies) == 0}">
			
			
			<div id="shortDetails">
				<div id="titleProxy">
					<strong>Hi <c:out
							value="<%=((User) request.getAttribute(WebKeys.USER)).getFirstName() %>"></c:out>, your active VO:</strong>
				</div>
				
				<liferay-ui:success key="proxy-download-success"
					message="proxy-download-success" />
				<liferay-ui:success key="proxy-destroy-success"
					message="proxy-destroy-success" />
				<liferay-ui:success key="proxy-expired-deleted"
					message="proxy-expired-deleted" />
				
				<liferay-ui:error key="proxy-download-problem"
					message="proxy-download-problem" />
				<c:choose>
				
					<c:when test="${voNumber == 0 }">
						<div id="notproxy" class="red"> None of your VOs is enabled in the portal yet. Check your settings <a href="${vaiqui }">HERE</a>.</div>
						
						
						
						
					</c:when>
					<c:when test="${voNumber == 1 }">
						
						<div id="notproxy" class="red"> None VOs in use. Click the button beside to retrieve your credentials.</div>
						
						<div id="noproxy">
						
						<aui:form name="addUserInfoForm" commandName="userInfo" action="${getProxyUrl}">
			
							
			
							<aui:button-row>
								<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="downloadProxy">
								<portlet:param name="myaction" value="downloadCertificate" />
								</liferay-portlet:renderURL>
								<aui:button type="button" value="Get Credentials" onclick="$(this).modal({width:400, height:300, message:true, src: '${downloadProxy }'}).open(); return false;"/>	
						
							</aui:button-row>
						</aui:form>
						</div>
					</c:when>
					<c:otherwise>
						
					
						
						<div id="notproxy" class="red" > None VOs in use. Click the button beside to retrieve your credentials.</div>
						
						
						 <div id="noproxy">
						<aui:form name="catalogForm"
								action="${downloadCertificateUrl}">
								
									<aui:button-row>
											<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="downloadProxy">
											<portlet:param name="myaction" value="downloadCertificate" />
											</liferay-portlet:renderURL>
											<aui:button type="button" value="Get Credentials" onclick="$(this).modal({width:400, height:300, message:true, src: '${downloadProxy }'}).open(); return false;"/>	
						
									</aui:button-row>
								
						</aui:form>
						</div>
					</c:otherwise>
				</c:choose>
				<div id="linkImgShort">
						<aui:a href="${vaiqui}" onmouseover="viewTooltip('#settings');"><img src="<%=request.getContextPath()%>/images/advancedsettings.png" alt="User settings" width="24" height="24" style="float: right; padding-right:10px;" /></aui:a>
					</div>
				<div id="reset"></div>
			</div>
			
		</c:if>
	
		<c:if test="${fn:length(shortProxies) > 0}">
			<div id="shortDetails">
				<div id="titleProxy">
					<strong>Hi <c:out
							value="<%=((User) request.getAttribute(WebKeys.USER)).getFirstName() %>"></c:out>, your active VO:</strong>
				</div>
				<c:forEach var="s" items="${shortProxies }">
					<!-- <div id="proxy" class="green" onclick="$('#details').show(); $('#shortDetails').hide();"><strong>${s }</strong></div> -->
					<c:if test="${fn:split(s,'|')[1] == 'green' }">
						<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="RenewProxy">
							<portlet:param name="myaction" value="showRenewProxy" />
							<portlet:param name="idVo" value="${fn:split(s,'|')[2] }" />
						</liferay-portlet:renderURL>
						<div id="proxy" class="green" onclick="$(this).modal({width:400, height:300, message:true, src: '${RenewProxy }'}).open();"><strong>${fn:split(s,'|')[0] }<c:if test="${fn:split(s,'|')[3] != 'no role' }">:${fn:replace(fn:replace(fn:split(s,'|')[3],fn:split(s,'|')[0],''),'//','') }</c:if></strong></div>
					</c:if>
					<c:if test="${fn:split(s,'|')[1] == 'yellow' }">
						<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="RenewProxy">
							<portlet:param name="myaction" value="showRenewProxy" />
							<portlet:param name="idVo" value="${fn:split(s,'|')[2] }" />
						</liferay-portlet:renderURL>
						<div id="proxy" class="yellow" onclick="$(this).modal({width:400, height:300, message:true, src: '${RenewProxy }'}).open();"><strong>${fn:split(s,'|')[0] }<c:if test="${fn:split(s,'|')[3] != 'no role' }">:${fn:replace(fn:replace(fn:split(s,'|')[3],fn:split(s,'|')[0],''),'//','') }</c:if></strong></div>
					</c:if>
					<c:if test="${fn:split(s,'|')[1] == 'orange' }">
					<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="RenewProxy">
							<portlet:param name="myaction" value="showRenewProxy" />
							<portlet:param name="idVo" value="${fn:split(s,'|')[2] }" />
						</liferay-portlet:renderURL>
						<div id="proxy" class="orange" onclick="$(this).modal({width:400, height:300, message:true, src: '${RenewProxy }'}).open();"><strong>${fn:split(s,'|')[0] }<c:if test="${fn:split(s,'|')[3] != 'no role' }">:${fn:replace(fn:replace(fn:split(s,'|')[3],fn:split(s,'|')[0],''),'//','') }</c:if></strong></div>
					</c:if>
					<c:if test="${fn:split(s,'|')[1] == 'black' }">
						<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="autoRenewProxy">
							<portlet:param name="myaction" value="showRenewProxyExp" />
							<portlet:param name="idVo" value="${fn:split(s,'|')[2] }" />
						</liferay-portlet:renderURL>
						<div id="notproxy" class="red"><strong>${fn:split(s,'|')[0] }<c:if test="${fn:split(s,'|')[3] != 'no role' }">:${fn:replace(fn:replace(fn:split(s,'|')[3],fn:split(s,'|')[0],''),'//','') }</c:if></strong>
							<div style="display:none">
								<form id="submitThis" action="javascript:$(this).modal({width:400, height:300, message:true, src: '${autoRenewProxy }'}).open();"></form>
							</div>
						</div>
						
					</c:if>
					
				</c:forEach>
				<c:if test="${fn:length(shortProxies) < voNumber}">
					<div id="noproxy">
						<aui:form name="catalogForm"
								action="${downloadCertificateUrl}">
								
							<aui:button-row>
									<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="downloadProxy">
									<portlet:param name="myaction" value="downloadCertificate" />
									</liferay-portlet:renderURL>
									<aui:button type="button" value="Get Credentials" onclick="$(this).modal({width:400, height:300, message:true, src: '${downloadProxy }'}).open(); return false;"/>	
							</aui:button-row>
								
						</aui:form>
					</div>
				</c:if>
				<div id="linkImgShort">
						<aui:a href="${vaiqui}" onmouseover="viewTooltip('#settings');"><img src="<%=request.getContextPath()%>/images/advancedsettings.png" alt="User settings" width="24" height="24" style="float: right; padding-right:10px;" /></aui:a>
					</div>
				<div id="reset"></div>
			</div>
		</c:if>
		
		
	</c:if>
	<div id="renewButton" style="display:none;">Renew proxy.</div>
	<div id="settings" style="display:none;">User Settings.</div>
	<div id="allOK" style="display:none;">All is OK.</div>
	<div id="warning" style="display:none;">Your proxy will be expire,<br/> renew proxy.</div>
	
	
</c:if>
<c:if test="${aws==puws }">
	<div id="containerLogin2"> 
	
	
	
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
	
	
	<div class="portlet-msg-success"> Proxy downloaded successfully<br/> Close this pop-up. </div>
	<script>
		var count=3;
		
		var counter=setInterval(timer, 1000); //1000 will  run it every 1 second
		
		function timer(){
			count=count-1;
			if (count <= 0){
				clearInterval(counter);
				window.parent.document.closeFrame();
				return;
			}
			$("#timer").html(count); // watch for spelling
		}
	</script>
	This Pop-up will be closed in <strong><span id="timer">3</span></strong> secs.
	
	</div>
</c:if>