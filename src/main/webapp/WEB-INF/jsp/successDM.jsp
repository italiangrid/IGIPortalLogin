<%@ include file="/WEB-INF/jsp/init.jsp"%>

<div id="containerLogin2"> 

<portlet:actionURL var="getProxyUrl">
	<portlet:param name="myaction" value="getProxy" />
</portlet:actionURL>

<portlet:renderURL var="homeUrl">
	<portlet:param name="myaction" value="idps" />
</portlet:renderURL>


<div class="portlet-msg-success"> Proxy downloaded successfully<br/> Close this pop-up. </div>


</div>