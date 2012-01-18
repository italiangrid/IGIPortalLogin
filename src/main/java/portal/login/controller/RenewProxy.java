package portal.login.controller;

import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;

import portal.login.domain.UserInfo;
import portal.login.domain.UserToVo;
import portal.login.domain.Vo;
import portal.login.services.CertificateService;
import portal.login.services.UserInfoService;
import portal.login.services.UserToVoService;
import portal.login.services.VoService;

@Controller(value = "renewProxyController")
@RequestMapping(value = "VIEW")
public class RenewProxy {
	
	/**
	 * Logger of the class DownloadCertificateController. TODO to substitute it
	 * with slf4j.
	 */
	private static final Logger log = Logger
			.getLogger(RenewProxy.class);
	
	/**
	 * Attribute for access to the PortalUser database.
	 */
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * Attribute for access to the PortalUser database.
	 */
	@Autowired
	private UserToVoService userToVoService;
	
	/**
	 * Attribute for access to the PortalUser database.
	 */
	@Autowired
	private VoService voService;
	
	/**
	 * Attribute for access to the PortalUser database.
	 */
	@Autowired
	private CertificateService certificateService;

	/**
	 * Method for render downloadCertificate.jsp page.
	 * 
	 * @return the page file name.
	 */
	@RenderMapping(params = "myaction=showRenewProxy")
	public String showDownloadCertificate() {
		return "renewProxy";
	}
	
	/**
	 * Return to the portlet the list of the user's vo membership
	 * @param request: session parameter
	 * @return the list of the user's vo membership
	 */
	@ModelAttribute("Vo")
	public Vo getUserVos(RenderRequest request, @RequestParam int idVo) {
		return voService.findById(idVo);
	}
	
	/**
	 * Return to the portlet the list of the user's fqans.
	 * @param request: session parameter.
	 * @return the list of the user's fqans.
	 */
	@ModelAttribute("userFqan")
	public String getUserFqan(RenderRequest request, @RequestParam int idVo) {
		String username = ((User)request.getAttribute(WebKeys.USER)).getScreenName();
		UserInfo userInfo = userInfoService.findByUsername(username);
		
		
		UserToVo utv = userToVoService.findById(userInfo.getUserId(), idVo);
		
		return utv.getFqans();
	}

}
