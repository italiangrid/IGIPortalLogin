<%@ include file="/WEB-INF/jsp/init.jsp"%>

<div id="containerLogin2"> 

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
		</div>
	</aui:fieldset>
<br/><br/><br/>


<div class="portlet-msg-success"> Proxy downloaded successfully<br/> Close this pop-up. </div>


</div>