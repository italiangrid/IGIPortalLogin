package portal.login.controller;

import java.util.List;

import javax.portlet.RenderRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import portal.login.domain.Certificate;
import portal.login.domain.UserInfo;
import portal.login.services.CertificateService;
import portal.login.services.UserInfoService;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;

/**
 * This class is the controller that manage the download of the grid proxy of
 * the user after login.
 * 
 * @author dmichelotto
 * 
 */

@Controller(value = "downloadCertificateController")
@RequestMapping(value = "VIEW")
public class DownloadCertificateController {

	/**
	 * Logger of the class DownloadCertificateController. TODO to substitute it
	 * with slf4j.
	 */
	private static final Logger log = Logger
			.getLogger(DownloadCertificateController.class);
	
	/**
	 * Attribute for access to the PortalUser database.
	 */
	@Autowired
	private UserInfoService userInfoService;
	
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
	@RenderMapping(params = "myaction=downloadCertificate")
	public String showDownloadCertificate() {
		log.info("***** faccio il render *****");
		return "downloadCertificate";
	}

	
	
	@ModelAttribute("certs")
	public List<Certificate> getIdps(RenderRequest request) {
		String username = ((User)request.getAttribute(WebKeys.USER)).getScreenName();
		UserInfo userInfo = userInfoService.findByUsername(username);
		return certificateService.findById(userInfo.getUserId());
	}

}
