package portal.login.controller;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.portlet.RenderRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/*import portal.login.domain.UserInfo;
import portal.login.domain.UserToVo;
import portal.login.domain.Vo;
import portal.login.services.UserInfoService;
import portal.login.services.UserToVoService;*/

import it.italiangrid.portal.dbapi.domain.UserInfo;
import it.italiangrid.portal.dbapi.domain.UserToVo;
import it.italiangrid.portal.dbapi.domain.Vo;
import it.italiangrid.portal.dbapi.services.UserInfoService;
import it.italiangrid.portal.dbapi.services.UserToVoService;

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
	private UserToVoService userToVoService;

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
	
	/**
	 * Return to the portlet the list of the user's vo membership
	 * @param request: session parameter
	 * @return the list of the user's vo membership
	 */
	@ModelAttribute("userVos")
	public List<Vo> getUserVos(RenderRequest request) {
		String username = ((User)request.getAttribute(WebKeys.USER)).getScreenName();
		UserInfo userInfo = userInfoService.findByUsername(username);
		return userToVoService.findVoByUserId(userInfo.getUserId());
	}
	
	/**
	 * Return to the portlet the list of the user's vo membership
	 * @param request: session parameter
	 * @return the list of the user's vo membership
	 */
	@ModelAttribute("defaultVo")
	public String defaultVo(RenderRequest request) {
		String username = ((User)request.getAttribute(WebKeys.USER)).getScreenName();
		UserInfo userInfo = userInfoService.findByUsername(username);
		return userToVoService.findDefaultVo(userInfo.getUserId());
	}
	
	/**
	 * Return to the portlet the list of the user's fqans.
	 * @param request: session parameter.
	 * @return the list of the user's fqans.
	 */
	@ModelAttribute("userFqans")
	public Map<Object,Object> getUserFqans(RenderRequest request) {
		String username = ((User)request.getAttribute(WebKeys.USER)).getScreenName();
		UserInfo userInfo = userInfoService.findByUsername(username);
		
		List<UserToVo> utv = userToVoService.findById(userInfo.getUserId());
		
		Map<Object, Object> x = new Properties();
		
		String toParse = null;
		
		for (Iterator<UserToVo> iterator = utv.iterator(); iterator.hasNext();) {
			UserToVo userToVo = iterator.next();
			toParse = userToVo.getFqans();
			if(toParse != null){
				x.put(userToVo.getId().getIdVo(), toParse);
				log.info(userToVo.getId().getIdVo() + " --> " + toParse);
			}
		}
		
		return x;
	}
	
}
