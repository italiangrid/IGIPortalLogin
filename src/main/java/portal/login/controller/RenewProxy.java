package portal.login.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.portlet.RenderRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;

import it.italiangrid.portal.dbapi.domain.UserInfo;
import it.italiangrid.portal.dbapi.domain.UserToVo;
import it.italiangrid.portal.dbapi.domain.Vo;
import it.italiangrid.portal.dbapi.services.UserInfoService;
import it.italiangrid.portal.dbapi.services.UserToVoService;
import it.italiangrid.portal.dbapi.services.VoService;

@Controller(value = "renewProxyController")
@RequestMapping(value = "VIEW")
public class RenewProxy {
	
	/**
	 * Logger of the class DownloadCertificateController. 
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
	 * Method for render downloadCertificate.jsp page.
	 * 
	 * @return the page file name.
	 */
	@RenderMapping(params = "myaction=showRenewProxy")
	public String showDownloadCertificate() {
		return "renewProxy";
	}
	
	/**
	 * Method for render downloadCertificate.jsp page.
	 * 
	 * @return the page file name.
	 */
	@RenderMapping(params = "myaction=showRenewProxyDM")
	public String showDownloadCertificateDM() {
		return "renewProxyDM";
	}
	
	/**
	 * Method for render downloadCertificate.jsp page.
	 * 
	 * @return the page file name.
	 */
	@RenderMapping(params = "myaction=showRenewProxyExp")
	public String showDownloadCertificateExp() {
		return "renewProxyExp";
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
	 * Return to the portlet the list of the user's vo membership
	 * @param request: session parameter
	 * @return the list of the user's vo membership
	 */
	@ModelAttribute("timeLeft")
	public String getExpirationTime(RenderRequest request, @RequestParam int idVo) {
		Vo vo =  voService.findById(idVo);
		
		User user = (User)request.getAttribute(WebKeys.USER);
		UserInfo userInfo = userInfoService.findByMail(user.getEmailAddress());
		if(user!=null){
			
			String dir = System.getProperty("java.io.tmpdir");
			File proxyVoFile = new File(dir + "/users/"
					+ user.getUserId() + "/x509up."
					+ vo.getVo());
			
			if(proxyVoFile.exists()){
					
				long totalSecs = getExpirationTime(proxyVoFile.getAbsolutePath());
				long hours = totalSecs / 3600;
				long minutes = (totalSecs % 3600) / 60;
				long seconds = totalSecs % 60;
				
				String color = "greenText";
				if(hours<5)
					color = "yellowText";
				if(hours<2)
					color = "orangeText";
				if(hours<1)
					color = "redText";
				String role = getRole(userInfo, vo, proxyVoFile.getAbsolutePath());
				return hours + ":" + minutes +  ":" + seconds+"|"+color + "|" + role;
			}
			
		}
		
		return null;
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
		
		log.info("Get User FQANs");
		
		UserToVo utv = userToVoService.findById(userInfo.getUserId(), idVo);
		
		return utv.getFqans();
	}
	
	@ModelAttribute("vaiqui")
	public String getMydataUrl() {

		String contextPath = LoginController.class.getClassLoader()
				.getResource("").getPath();
		
		String result = "";

		File test = new File(contextPath + "/content/MyProxy.properties");
		if (test.exists()) {
			
			try {
				FileInputStream inStream = new FileInputStream(contextPath
						+ "/content/MyProxy.properties");

				Properties prop = new Properties();

				prop.load(inStream);

				inStream.close();
				
				result = prop.getProperty("mydata.url");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
 		
		return result;
	}
	
	private long getExpirationTime(String proxyFile) {

		long result = 0;

		try {
			String cmd = "voms-proxy-info -timeleft -file " + proxyFile;

			log.info("cmd = " + cmd);
			Process p = Runtime.getRuntime().exec(cmd);
			InputStream stdout = p.getInputStream();
			InputStream stderr = p.getErrorStream();

			BufferedReader output = new BufferedReader(new InputStreamReader(
					stdout));
			String line = null;

			long totalSecs=0;
			
			while ((line = output.readLine()) != null) {
				log.info("[Stdout] " + line);
				totalSecs = Long.parseLong(line);
				result = totalSecs;
				
			}

			output.close();

			BufferedReader brCleanUp = new BufferedReader(
					new InputStreamReader(stderr));
			while ((line = brCleanUp.readLine()) != null) {

				log.error("[Stderr] " + line);
			}

			brCleanUp.close();

		} catch (IOException e) {

			e.printStackTrace();
		}

		return result;
	}
	
	private String getRole(UserInfo userInfo, Vo vo, String proxyFile){
		String role="no role";
		try {
			
			UserToVo utv = userToVoService.findById(userInfo.getUserId(), vo.getIdVo());
			
			String toParse = utv.getFqans();
			String roles[]=null;
			if(toParse!=null){
				roles=toParse.split(";");
			}
			
			String cmd = "voms-proxy-info -all -file " + proxyFile;
			
			log.info("cmd = " + cmd);
			Process p = Runtime.getRuntime().exec(cmd);
			InputStream stdout = p.getInputStream();
			InputStream stderr = p.getErrorStream();

			BufferedReader output = new BufferedReader(new InputStreamReader(
					stdout));
			String line = null;
			
			List<String> tmpRole = new ArrayList<String>();
			
			
			while ((line = output.readLine()) != null) {
				log.info("[Stdout] " + line);
				if(roles!=null){
					if(line.contains("/"+vo.getVo()+"/Role=NULL")){
						tmpRole.add(role);
						log.info("trovato: "+ role);
					}
					for(int i=0; i<roles.length; i++){
						if(line.contains(roles[i].trim())){
							tmpRole.add(roles[i].trim());
							log.info("trovato: "+ roles[i]);
						}
					}
				}
			}
			if(!tmpRole.isEmpty())
				role=tmpRole.get(0);
			log.info("Fine trovato: "+ role);
			output.close();

			BufferedReader brCleanUp = new BufferedReader(
					new InputStreamReader(stderr));
			while ((line = brCleanUp.readLine()) != null) {
				log.error("[Stderr] " + line);
			}
			brCleanUp.close();
			
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return role;
	}

}
