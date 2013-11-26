<%@ include file="/WEB-INF/jsp/init.jsp"%>

<div id="containerLogin2"> 

<portlet:actionURL var="getProxyUrl">
	<portlet:param name="myaction" value="getProxy" />
</portlet:actionURL>

<portlet:renderURL var="homeUrl">
	<portlet:param name="myaction" value="idps" />
</portlet:renderURL>


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
	This page will be reloaded in <strong><span id="timer">5</span></strong> secs.
</div>