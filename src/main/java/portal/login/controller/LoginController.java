package portal.login.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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

import com.liferay.portal.kernel.servlet.SessionErrors;
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
							cred = new GlobusCredential(
									proxyVoFile.toString());
							if(cred.getTimeLeft() <= 0)
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
	
	/**
	 * Return to the portlet the list of the user's fqans.
	 * @param request: session parameter.
	 * @return the list of the user's fqans.
	 */
	@ModelAttribute("userFqans")
	public String getUserFqans(RenderRequest request) {
		User user = (User)request.getAttribute(WebKeys.USER);
		if(user!=null){
			UserInfo userInfo = userInfoService.findByUsername(user.getScreenName());
			if(userToVoService.findVoByUserId(userInfo.getUserId()).size()==1){
				List<UserToVo> utvs = userToVoService.findById(userInfo.getUserId());
				UserToVo utv = utvs.get(0);
				return utv.getFqans();
			}
		}
		return null;
	}
	
	/**
	 * Return to the portlet the list of the user's fqans.
	 * @param request: session parameter.
	 * @return the list of the user's fqans.
	 * @throws GlobusCredentialException 
	 */
	@ModelAttribute("proxys")
	public String getProxys(RenderRequest request) throws GlobusCredentialException {
		String result = "";
		User user = (User)request.getAttribute(WebKeys.USER);
		if(user!=null){
			UserInfo userInfo = userInfoService.findByUsername(user.getScreenName());
			List<Vo> vos = userToVoService.findVoByUserId(userInfo
					.getUserId());
			String dir = System.getProperty("java.io.tmpdir");
			File proxyVoFile = null;
			for (Iterator<Vo> iterator = vos.iterator(); iterator
					.hasNext();) {
				Vo vo = (Vo) iterator.next();
				proxyVoFile = new File(dir + "/users/"
						+ user.getUserId() + "/x509up."
						+ vo.getVo());
				
				if(proxyVoFile.exists()){
					
					GlobusCredential cred = new GlobusCredential(
							proxyVoFile.toString());
					if (cred.getTimeLeft() <= 0) {
						proxyVoFile.delete();
						SessionMessages.add(request, "proxy-expired-deleted");
					}else{
						String role="             no role setted";
						try {
							
							UserToVo utv = userToVoService.findById(userInfo.getUserId(), vo.getIdVo());
							
							String toParse = utv.getFqans();
							String roles[]=null;
							if(toParse!=null){
								roles=toParse.split(";");
							}
							
							log.info("Directory = " + dir);

							//String proxy = dir + "/users/" + user.getUserId() + "/x509up";
							
							String cmd = "voms-proxy-info -all -file " + proxyVoFile.toString();
							
							log.info("cmd = " + cmd);
							Process p = Runtime.getRuntime().exec(cmd);
							InputStream stdout = p.getInputStream();
							InputStream stderr = p.getErrorStream();

							BufferedReader output = new BufferedReader(new InputStreamReader(
									stdout));
							String line = null;

							while ((line = output.readLine()) != null) {
								log.info("[Stdout] " + line);
								if(roles!=null){
									for(int i=0; i<roles.length; i++){
										if(line.contains(roles[i])){
											role=roles[i];
										}
									}
								}
							}
							output.close();
							
							//boolean error = false;

							BufferedReader brCleanUp = new BufferedReader(
									new InputStreamReader(stderr));
							while ((line = brCleanUp.readLine()) != null) {
								//error= true;
								log.error("[Stderr] " + line);
							}
							/*if(error)
								SessionErrors.add(request, "voms-proxy-init-problem");*/
							brCleanUp.close();
							
							

						} catch (IOException e) {
							SessionErrors.add(request, "voms-proxy-init-exception");
							e.printStackTrace();
						}
						
						long totalSecs =cred.getTimeLeft();
						long hours = totalSecs / 3600;
						long minutes = (totalSecs % 3600) / 60;
						long seconds = totalSecs % 60;
						
						String timeLeft = hours + ":" + minutes +  ":" + seconds;
						if(hours < 1)
							timeLeft = "<span style=\"color:red\"><strong>" + timeLeft + "</strong></span>";
						else
							timeLeft = "<span style=\"color:#63AC68\"><strong>" + timeLeft + "</strong></span>";
						
						
						result += "<tr><td><strong>VO:</strong> " + vo.getVo() + "&nbsp&nbsp</td><td> <strong>Ruolo:</strong>&nbsp&nbsp</td><td> " + role + "</td></tr><tr><td>&nbsp&nbsp</td><td> <strong>TimeLeft:</strong>&nbsp&nbsp</td><td> " + timeLeft + "</td></tr>";
					}
				}
			}
		}
		return result;
	}

}
