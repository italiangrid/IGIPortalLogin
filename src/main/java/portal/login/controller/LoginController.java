package portal.login.controller;

import java.io.File;
import java.util.List;
import javax.annotation.Resource;
import javax.portlet.RenderResponse;
import javax.portlet.RenderRequest;

import portal.login.domain.Idp;
import portal.login.dao.IdpDAO;
import portal.login.services.IdpService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;

@Controller("userInfoController")
@RequestMapping(value = "VIEW")
public class LoginController {
	
	/**
	 * Logger of the class DownloadCertificateController. TODO to substitute it
	 * with slf4j.
	 */
	private static final Logger log = Logger
			.getLogger(LoginController.class);

	/**
	 * Attribute the momorize if the proxy was downloaded. By default the
	 * attrubite is false.
	 */
	static boolean downloaded = false;

	/**
	 * Attribute for access to the PortalUser database.
	 */
	@Autowired
	private IdpService idpService;

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
	 * not
	 * 
	 * @return the value of the attribute downloaded
	 */
	@ModelAttribute("proxyDownloaded")
	public boolean getProxyDownloaded(RenderRequest request) {
		
		
		User user = (User) request.getAttribute(WebKeys.USER);
		if(user!=null){
			String dir = System.getProperty("java.io.tmpdir");
			log.info("Directory = "+ dir);
			
			
			File proxyFile = new File(dir+"/users/"+user.getUserId()+"/x509up");
			
			if(proxyFile.exists())
				return true;
		}
		return false;
	}

	/**
	 * Setter method for the attribute downloaded.
	 * 
	 * @param value
	 *            is the value to assign to the attribute downloaded.
	 */
	public static void setDownloaded(boolean value) {
		downloaded = value;
	}

	/**
	 * Getter method for the attribute downloaded.
	 * 
	 * @return the value of the attribute.
	 */
	public static boolean getDownloaded() {
		return downloaded;
	}

}
