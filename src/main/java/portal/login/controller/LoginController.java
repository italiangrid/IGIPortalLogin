package portal.login.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.portlet.PortletConfig;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.RenderRequest;
//import portal.login.domain.Idp;
//import portal.login.domain.UserInfo;
//import portal.login.domain.UserToVo;
//import portal.login.domain.Vo;
//import portal.login.services.IdpService;
//import portal.login.services.UserInfoService;
//import portal.login.services.UserToVoService;

import it.italiangrid.portal.dbapi.domain.Idp;
import it.italiangrid.portal.dbapi.domain.UserInfo;
import it.italiangrid.portal.dbapi.domain.UserToVo;
import it.italiangrid.portal.dbapi.domain.Vo;
import it.italiangrid.portal.dbapi.services.IdpService;
import it.italiangrid.portal.dbapi.services.UserInfoService;
import it.italiangrid.portal.dbapi.services.UserToVoService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.JavaConstants;
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
			
			UserInfo userInfo = userInfoService.findByUsername(user.getScreenName());
			List<Vo> vos = userToVoService.findVoByUserId(userInfo
					.getUserId());
			File proxyVoFile = null;
			for (Iterator<Vo> iterator = vos.iterator(); iterator
					.hasNext();) {
				Vo vo = (Vo) iterator.next();
				proxyVoFile = new File(dir + "/users/"
						+ user.getUserId() + "/x509up."
						+ vo.getVo());

//				File proxyFile = new File(dir + "/users/" + user.getUserId()
//						+ "/x509up");
	
				if (proxyVoFile.exists()) {
					try {
						GlobusCredential cred = new GlobusCredential(
								proxyVoFile.toString());
						if (cred.getTimeLeft() > 0) {
							return true;
						} else {
							
	//						UserInfo userInfo = userInfoService.findByUsername(user
	//								.getScreenName());
	//
	//						File credFile = new File(dir + "/users/"
	//								+ user.getUserId() + "/.creds");
	//						File proxyVoFile = null;
	//						credFile.delete();
	//						proxyFile.delete();
	//
	//						List<Vo> vos = userToVoService.findVoByUserId(userInfo
	//								.getUserId());
	//						for (Iterator<Vo> iterator = vos.iterator(); iterator
	//								.hasNext();) {
	//							Vo vo = (Vo) iterator.next();
	//							proxyVoFile = new File(dir + "/users/"
	//									+ user.getUserId() + "/x509up."
	//									+ vo.getVo());
	//							if(proxyVoFile.exists()){
	//								cred = new GlobusCredential(
	//										proxyVoFile.toString());
	//								if(cred.getTimeLeft() <= 0)
										proxyVoFile.delete();
	//							}
	//						}
	
							SessionMessages.add(request, "proxy-expired-deleted");
						}
					} catch (GlobusCredentialException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.info("e mò sono cazzi amari");
					}
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
	public String getProxys(RenderRequest request, RenderResponse response) throws GlobusCredentialException {
		
		PortletConfig portletConfig = (PortletConfig)request.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
		SessionMessages.add(request, portletConfig.getPortletName() + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
		
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
						String role="             no role";
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
							
							boolean check = true;
							List<String> tmpRole = new ArrayList<String>();
							
							
							while ((line = output.readLine()) != null) {
								log.info("[Stdout] " + line);
								if(roles!=null){
									if(line.contains("/gridit/Role=NULL")){
										tmpRole.add(role);
										log.info("trovato: "+ role);
									}
									for(int i=0; i<roles.length; i++){
										//log.info("check con: "+roles[i]);
										if(line.contains(roles[i].trim())){
											tmpRole.add(roles[i].trim());
											log.info("trovato: "+ roles[i]);
										}
									}
									
									/*if(line.contains("attribute : ")){
										check = true;
									}*/
								}
							}
							if(!tmpRole.isEmpty())
								role=tmpRole.get(0);
							log.info("Fine trovato: "+ role);
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
						PortletURL url = response.createRenderURL();
						url.setParameter("myaction", "showRenewProxy");
						url.setParameter("idVo", Integer.toString(vo.getIdVo()));
//						String button = "<input type=\"submit\" value=\"Renew\" onClick=\"location.href=\'"+url+"\';\"></input>";
						String button = "<div id=\"linkImg\"><a href=\""+url+"\" onmouseover=\"viewTooltip('#renewButton');\"><img src=\"" + request.getContextPath() + "/images/update.png\" width=\"24\" height=\"24\" style=\"float: right; padding-right:10px;\"/></a></div>";
						String timeLeft = hours + ":" + minutes +  ":" + seconds;
						if(hours < 1){
							timeLeft = "<span style=\"color:red\"><strong>" + timeLeft + "</strong></span>";
							button += "<div id=\"tooltipImg\"><a href=\"#warning\"><img src=\"" + request.getContextPath() + "/images/alert.png\" width=\"24\" height=\"24\" style=\"float: right; padding-right:10px;\"/></a></div>";
						}else{
							timeLeft = "<span style=\"color:#63AC68\"><strong>" + timeLeft + "</strong></span>";
							button += "<div id=\"tooltipImg\"><a href=\"#allOK\"><img src=\"" + request.getContextPath() + "/images/check.png\" width=\"24\" height=\"24\" style=\"float: right; padding-right:10px;\"/></a></div>";
						}
						
						
						result += "<tr>" +
									"<td colspan=\"3\" style=\"color: #000080\"><strong><u>VO: " + vo.getVo() + "</u></strong></td>" +
								  "</tr>" +
								  "<tr>" +
								    "<td style='width: 60px;'> <strong>Role:</strong>&nbsp&nbsp</td>" +
								    "<td> " + role + "&nbsp&nbsp</td>" +
								    "<td style='width: 70px;'rowspan=\"2\" align=\"right\">" + button + "</td>"  +
								  "</tr>" +
								  "<tr>" +
								    "<td style='width: 60px;'> <strong>TimeLeft:</strong>&nbsp&nbsp</td>" +
								    "<td>" + timeLeft + "</td>" +
								  "</tr> *";
					}
				}
			}
		}
		return result;
	}

}
