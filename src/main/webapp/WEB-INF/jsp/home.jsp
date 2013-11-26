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

<style type="text/css">

.modal-overlay {
	position: fixed;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	height: 100%;
	width: 100%;
	margin: 0;
	padding: 0;
	background: url(/Login-1.1/images/overlay2.png) repeat;
	opacity: .85;
	filter: alpha(opacity=85);
	z-index: 101;
}
.modal-window {
	position: fixed;
	top: 50%;
	left: 50%;
	margin: 0;
	padding: 0;
	z-index: 102;
	background: #fff;
	border: solid 8px #000;
	-moz-border-radius: 8px;
	-webkit-border-radius: 8px;
}
.close-window {
	position: absolute;
	width: 47px;
	height: 47px;
	right: -23px;
	top: -23px;
	background: transparent url(/Login-1.1/images/close-button2.png) no-repeat scroll right top;
	text-indent: -99999px;
	overflow: hidden;
	cursor: pointer;
}

#proxy{
	float: left;
	border-radius: 5px;
	padding: 5px;
	margin: 10px 5px 5px 5px;
	cursor: pointer;
}

.green{
	border: 1px solid green;
	background-color: #66CC66;
}
.yellow{
	border: 1px solid #FFCC00;
	background-color: #FFFFCC;
}
.orange{
	border: 1px solid orange;
	background-color: #FFCC66;
}
.red{
	border: 1px solid red;
	background-color: red;
}

.green:hover{
	background-color: #99FF66;
}
.yellow:hover{
	background-color: #FFFF66;
}
.orange:hover{
	background-color: #FF9933;
}
.red:hover{
	background-color: red;
}

#noproxy{
	float: left;
	margin-left: 5px;
}

#titleProxy{
	margin: 15px 5px 5px 5px;
	float: left;
	font-size:120%;
	font-weight: bold;
}

#reset{
	clear: both;
}

#shortDetails {
	border-bottom: 1px solid grey;
    margin-bottom: 10px;
}

#linkImgShort{
	margin: 10px 20px 0 0;
	float: right;
}

</style>
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
		
		<c:if test="${!proxyDownloaded}">
		
			<div id="containerLogin" style="margin-bottom: 10px;">
				
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
			
					
			<br/> <br/>
			
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
						<br/>
						<div class="portlet-msg-error"> None of your VOs is enabled in the portal yet.</div>
						<p>Check your settings <a href="${vaiqui }">HERE</a>.</p>
						
						
						
					</c:when>
					<c:when test="${voNumber == 1 }">
						
						<br/> 
						
						<div class="portlet-msg-error"> None VOs in use. Click the button below to retrieve your credentials.</div>
						
						<br/>
						
						<aui:form name="addUserInfoForm" commandName="userInfo" action="${getProxyUrl}">
			
							
			
							<aui:button-row>
								<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="downloadProxy">
								<portlet:param name="myaction" value="downloadCertificate" />
								</liferay-portlet:renderURL>
								<aui:button type="button" value="Get Credentials" onclick="$(this).modal({width:400, height:300, message:true, src: '${downloadProxy }'}).open(); return false;"/>	
						
							</aui:button-row>
						</aui:form>
					</c:when>
					<c:otherwise>
						
						<br/> 
						
						<div class="portlet-msg-error"> None VOs in use. Click the button below to retrieve your credentials.</div>
						
						<br/>
						 
						<aui:form name="catalogForm"
								action="${downloadCertificateUrl}">
								
									<aui:button-row>
											<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="downloadProxy">
											<portlet:param name="myaction" value="downloadCertificate" />
											</liferay-portlet:renderURL>
											<aui:button type="button" value="Get Credentials" onclick="$(this).modal({width:400, height:300, message:true, src: '${downloadProxy }'}).open(); return false;"/>	
						
									</aui:button-row>
								
						</aui:form>
					</c:otherwise>
				</c:choose>
			</div>
		</c:if>
	
		<c:if test="${proxyDownloaded}">
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
						<div id="proxy" class="green" onclick="$(this).modal({width:400, height:300, message:true, src: '${RenewProxy }'}).open();"><strong>${fn:split(s,'|')[0] }</strong></div>
					</c:if>
					<c:if test="${fn:split(s,'|')[1] == 'yellow' }">
						<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="RenewProxy">
							<portlet:param name="myaction" value="showRenewProxy" />
							<portlet:param name="idVo" value="${fn:split(s,'|')[2] }" />
						</liferay-portlet:renderURL>
						<div id="proxy" class="yellow" onclick="$(this).modal({width:400, height:300, message:true, src: '${RenewProxy }'}).open();"><strong>${fn:split(s,'|')[0] }</strong></div>
					</c:if>
					<c:if test="${fn:split(s,'|')[1] == 'orange' }">
					<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="RenewProxy">
							<portlet:param name="myaction" value="showRenewProxy" />
							<portlet:param name="idVo" value="${fn:split(s,'|')[2] }" />
						</liferay-portlet:renderURL>
						<div id="proxy" class="orange" onclick="$(this).modal({width:400, height:300, message:true, src: '${RenewProxy }'}).open();"><strong>${fn:split(s,'|')[0] }</strong></div>
					</c:if>
					<c:if test="${fn:split(s,'|')[1] == 'black' }">
						<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="autoRenewProxy">
							<portlet:param name="myaction" value="showRenewProxyExp" />
							<portlet:param name="idVo" value="${fn:split(s,'|')[2] }" />
						</liferay-portlet:renderURL>
						<div id="proxy" class="red" onclick="$('#details').show(); $('#shortDetails').hide();"><strong>${fn:split(s,'|')[0] }</strong>
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
		
			<div id="details" style="display: none;">
				<div id="containerLogin" style="margin-bottom: 10px;">
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
		
				
		<br/> <br/>
		
		<liferay-ui:success key="proxy-download-success"
		message="proxy-download-success" />
	<liferay-ui:success key="proxy-destroy-success"
		message="proxy-destroy-success" />
	<liferay-ui:success key="proxy-expired-deleted"
		message="proxy-expired-deleted" />
	
	<liferay-ui:error key="proxy-download-problem"
		message="proxy-download-problem" />
			
				<c:set var="count" value="0" />
				
				<table id="proxyTable">
				
		
				<c:forTokens items="${proxys}"
			                 delims="*"
			                 var="currentName"
			                 varStatus="status">
			      
			        <tr><td colspan="3"></td></tr>
			        <c:out escapeXml="false" value="${currentName}"/>
			        <c:set var="count" value="${status.count}" />
					
			    </c:forTokens>
			    </table>
				<br/>
				<c:if test="${(count) < voNumber}">
					<aui:form name="catalogForm"
							action="${downloadCertificateUrl}">
							
						<aui:button-row>
								<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="downloadProxy">
								<portlet:param name="myaction" value="downloadCertificate" />
								</liferay-portlet:renderURL>
								<aui:button type="button" value="Get Credentials" onclick="$(this).modal({width:400, height:300, message:true, src: '${downloadProxy }'}).open(); return false;"/>	
						</aui:button-row>
							
					</aui:form>
				</c:if>
			
			</div>
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
	This Pop-up will be closed in <strong><span id="timer">5</span></strong> secs.
	
	</div>
</c:if>