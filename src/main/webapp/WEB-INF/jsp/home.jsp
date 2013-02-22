<%@ include file="/WEB-INF/jsp/init.jsp"%>


<script type="text/javascript">
<!--
	//-->
	
	//defining flags
	var isCtrl = false;
	var isShift = false;
	 
	// the magic :)
	$(document).ready(function() {
	     
	    // action on key up
	    $(document).keyup(function(e) {
	        if(e.which == 17) {
	            isCtrl = false;
	        }
	        if(e.which == 16) {
	            isShift = false;
	        }
	    });
	    // action on key down
	    $(document).keydown(function(e) {
	        if(e.which == 17) {
	            isCtrl = true; 
	        }
	        if(e.which == 16) {
	            isShift = true; 
	        }
	        if(e.which == 120 && isCtrl && isShift) { 
	        	$("#<portlet:namespace/>soloAdmin").show(); 
	        } 
	    });
	     
	});
	
	

	function goLogin() {
		var addrIdp = $("#<portlet:namespace/>idpId").val();
		if (addrIdp != "") {
			window.location = addrIdp;
		}
	}
	
	function viewTooltip(url){
		
		$("#linkImg a").tooltip({
			
			bodyHandler: function() {
				return $(url).html();
			},
			showURL: false
			
		});
	}
	
	$(function() {


		$("#tooltipImg a").tooltip({
			bodyHandler: function() {
				
				return $($(this).attr("href")).html();
			},
			showURL: false
			
		});

	});
	
	
	(function ($) {

		/**********************************
		* CUSTOMIZE THE DEFAULT SETTINGS
		* Ex:
		* var _settings = {
		* 	id: 'modal',
		* 	src: function(sender){
		*		return jQuery(sender).attr('href');
		*	},
		* 	width: 800,
		* 	height: 600
		* }
		**********************************/
		var _settings = {
			width: 800, // Use this value if not set in CSS or HTML
			height: 600, // Use this value if not set in CSS or HTML
			overlayOpacity: .85, // Use this value if not set in CSS or HTML
			id: 'modal',
			fadeInSpeed: 0,
			fadeOutSpeed: 0
		};

		/**********************************
		* DO NOT CUSTOMIZE BELOW THIS LINE
		**********************************/
		$.modal = function (options) {
			return _modal(this, options);
		};
		$.modal.open = function () {
			_modal.open();
		};
		$.modal.close = function () {
			_modal.close();
		};
		$.fn.modal = function (options) {
			return _modal(this, options);
		};
		_modal = function (sender, params) {
			this.options = {
				parent: null,
				overlayOpacity: null,
				id: null,
				content: null,
				width: null,
				height: null,
				message: false,
				modalClassName: null,
				imageClassName: null,
				closeClassName: null,
				overlayClassName: null,
				src: null
			};
			this.options = $.extend({}, options, _defaults);
			this.options = $.extend({}, options, _settings);
			this.options = $.extend({}, options, params);
			this.close = function () {
				jQuery('.' + options.modalClassName + ', .' + options.overlayClassName).fadeOut(_settings.fadeOutSpeed, function () { jQuery(this).unbind().remove(); });
			};
			this.open = function () {
				if (typeof options.src == 'function') {
					options.src = options.src(sender);
				} else {
					options.src = options.src || _defaults.src(sender);
				}

				var fileExt = /^.+\.((jpg)|(gif)|(jpeg)|(png)|(jpg))$/i;
				var contentHTML = '';
				if (fileExt.test(options.src)) {
					contentHTML = '<div class="' + options.imageClassName + '"><img src="' + options.src + '"/></div>';

				} else {
					contentHTML = '<iframe width="' + options.width + '" height="' + options.height + '" frameborder="0" scrolling="no" allowtransparency="true" src="' + options.src + '"></iframe>';
				}
				options.content = options.content || contentHTML;

				if (jQuery('.' + options.modalClassName).length && jQuery('.' + options.overlayClassName).length) {
					jQuery('.' + options.modalClassName).html(options.content);
				} else {
					$overlay = jQuery((_isIE6()) ? '<iframe src="BLOCKED SCRIPT\'<html></html>\';" scrolling="no" frameborder="0" class="' + options.overlayClassName + '"></iframe><div class="' + options.overlayClassName + '"></div>' : '<div class="' + options.overlayClassName + '"></div>');
					$overlay.hide().appendTo(options.parent);

					$modal = jQuery('<div id="' + options.id + '" class="' + options.modalClassName + '" style="width:' + options.width + 'px; height:' + options.height + 'px; margin-top:-' + (options.height / 2) + 'px; margin-left:-' + (options.width / 2) + 'px;">' + options.content + '</div>');
					$modal.hide().appendTo(options.parent);

					$close = jQuery('<a class="' + options.closeClassName + '"></a>');
					$close.appendTo($modal);

					var overlayOpacity = _getOpacity($overlay.not('iframe')) || options.overlayOpacity;
					$overlay.fadeTo(0, 0).show().not('iframe').fadeTo(_settings.fadeInSpeed, overlayOpacity);
					$modal.fadeIn(_settings.fadeInSpeed);
					
					//alert(options.message)
					if(options.message==false){
					//$close.click(function () { jQuery.modal().close(); location.href='https://halfback.cnaf.infn.it/casshib/shib/app4/login?service=https%3A%2F%2Fgridlab04.cnaf.infn.it%2Fc%2Fportal%2Flogin%3Fp_l_id%3D10671';});
					$close.click(function () { jQuery.modal().close(); location.href='https://halfback.cnaf.infn.it/casshib/shib/app1/login?service=https%3A%2F%2Fflyback.cnaf.infn.it%2Fc%2Fportal%2Flogin%3Fp_l_id%3D10669';});
					}else{
						$close.click(function () { window.location.href=window.location.href;});
						$overlay.click(function () { window.location.href=window.location.href; });
					}
					
				}
			};
			return this;
		};
		_isIE6 = function () {
			if (document.all && document.getElementById) {
				if (document.compatMode && !window.XMLHttpRequest) {
					return true;
				}
			}
			return false;
		};
		_getOpacity = function (sender) {
			$sender = jQuery(sender);
			opacity = $sender.css('opacity');
			filter = $sender.css('filter');

			if (filter.indexOf("opacity=") >= 0) {
				return parseFloat(filter.match(/opacity=([^)]*)/)[1]) / 100;
			}
			else if (opacity != '') {
				return opacity;
			}
			return '';
		};
		_defaults = {
			parent: 'body',
			overlayOpacity: 85,
			id: 'modal',
			content: null,
			width: 800,
			height: 600,
			modalClassName: 'modal-window',
			imageClassName: 'modal-image',
			closeClassName: 'close-window',
			overlayClassName: 'modal-overlay',
			src: function (sender) {
				return jQuery(sender).attr('href');
			}
		};
	})(jQuery);

	
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

</style>
<c:set var="aws" value="<%= renderRequest.getWindowState()%>"/>
<c:set var="puws" value="<%= LiferayWindowState.POP_UP.toString()%>"/>
<c:if test="${aws!=puws }">
	<div id="containerLogin">
	
	<portlet:renderURL var="downloadCertificateUrl">
		<portlet:param name="myaction" value="downloadCertificate" />
	</portlet:renderURL>		
	
	<portlet:actionURL var="getProxyUrl">
		<portlet:param name="myaction" value="getProxy" />
	</portlet:actionURL>				 		 
	
	<c:if test="<%= !themeDisplay.isSignedIn() %>">
	
		<span style="color:red"><strong>Effettua il Login per visualizzare le tue informazioni.</strong></span>
		<div id="<portlet:namespace/>soloAdmin" style="display: none;">
			<aui:select id="idpId" name="idpId" label="IDP" onChange="goLogin();">
	
				<aui:option value="">
					<liferay-ui:message key="Seleziona IDP per Login" />
				</aui:option>
				<c:forEach var="idpi" items="${idps}">
					<aui:option value="${idpi.idploginAddress}">
						<liferay-ui:message key="${idpi.idpname}" />
					</aui:option>
				</c:forEach>
	
			</aui:select>
		</div>
	</c:if>
	
	<c:if test="<%= themeDisplay.isSignedIn() %>">
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
		<c:if test="${!proxyDownloaded}">
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
		</c:if>
	
		<c:if test="${proxyDownloaded}">
			
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
							<aui:button type="button" value="Get proxy for other VO" onclick="$(this).modal({width:400, height:300, message:true, src: '${downloadProxy }'}).open(); return false;"/>	
					</aui:button-row>
						
				</aui:form>
			</c:if>
		</c:if>
		
		
	</c:if>
	<div id="renewButton" style="display:none;">Renew proxy.</div>
	<div id="settings" style="display:none;">User Settings.</div>
	<div id="allOK" style="display:none;">All is OK.</div>
	<div id="warning" style="display:none;">Your proxy will be expire,<br/> renew proxy.</div>
	
	</div>
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
	
	
	</div>
</c:if>