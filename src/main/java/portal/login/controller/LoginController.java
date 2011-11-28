package portal.login.controller;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import javax.portlet.RenderResponse;
import javax.portlet.RenderRequest;

import portal.login.domain.Idp;
import portal.login.domain.UserInfo;
import portal.login.domain.UserToVo;
import portal.login.domain.Vo;
import portal.login.services.IdpService;
import portal.login.services.UserInfoService;
import portal.login.services.UserToVoService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;

import org.globus.gsi.GlobusCredential;
import org.globus.gsi.GlobusCredentialException;

/**
 * This class is the controller that authenticate the user and display some
 * information about the downloaded proxyes.
 * 
 * @author dmichelotto
 * 
 */

@Controller("userInfoController")
@RequestMapping(value = "VIEW")
public class LoginController {

	/**
	 * Logger of the class DownloadCertificateController. TODO to substitute it
	 * with slf4j.
	 */
	private static final Logger log = Logger.getLogger(LoginController.class);

	/**
	 * Attribute for access to the PortalUser database.
	 */
	@Autowired
	private IdpService idpService;

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
	 * Method for render home.jsp page.
	 * 
	 * @return the page file name.
	 */
	@RenderMapping
	public String showLogin(RenderResponse response) {
		return "home";
	}

	/**
	 * Present to the portlet a list of the IDP supported by the portal.
	 * 
	 * @return List of Idp that contain all the Idp stored in the database
	 */
	@ModelAttribute("idps")
	public List<Idp> getIdps() {
		return idpService.getAllIdp();
	}

	/**
	 * Present to the portlet if the grid proxy of the user was downloaded or
	 * not, and destroy the proxy if expired.
	 * 
	 * @return the value of the attribute downloaded
	 */
	@ModelAttribute("proxyDownloaded")
	public boolean getProxyDownloaded(RenderRequest request) {

		User user = (User) request.getAttribute(WebKeys.USER);
		if (user != null) {
			String dir = System.getProperty("java.io.tmpdir");
			log.info("Directory = " + dir);

			File proxyFile = new File(dir + "/users/" + user.getUserId()
					+ "/x509up");

			if (proxyFile.exists()) {
				try {
					GlobusCredential cred = new GlobusCredential(
							proxyFile.toString());
					if (cred.getTimeLeft() > 0) {
						return true;
					} else {
						UserInfo userInfo = userInfoService.findByUsername(user
								.getScreenName());

						File credFile = new File(dir + "/users/"
								+ user.getUserId() + "/.cred");
						File proxyVoFile = null;
						credFile.delete();
						proxyFile.delete();

						List<Vo> vos = userToVoService.findVoByUserId(userInfo
								.getUserId());
						for (Iterator<Vo> iterator = vos.iterator(); iterator
								.hasNext();) {
							Vo vo = (Vo) iterator.next();
							proxyVoFile = new File(dir + "/users/"
									+ user.getUserId() + "/x509up."
									+ vo.getVo());
							proxyVoFile.delete();
						}

						SessionMessages.add(request, "proxy-expired-deleted");
					}
				} catch (GlobusCredentialException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.info("e mò sono cazzi amari");
				}
			}

		}
		return false;
	}
	
	@ModelAttribute("voNumber")
	public int getVoNumber(RenderRequest request) {

		User user = (User) request.getAttribute(WebKeys.USER);
		
		
		if (user != null) {
			String username = user.getScreenName();

			UserInfo userInfo = userInfoService.findByUsername(username);
			return userToVoService.findVoByUserId(userInfo.getUserId()).size();
		}
		return 0;
	}

}
