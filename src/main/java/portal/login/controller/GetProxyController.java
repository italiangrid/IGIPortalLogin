package portal.login.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CertificateEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.apache.log4j.Logger;
import org.globus.gsi.GlobusCredential;
import org.globus.gsi.gssapi.GlobusGSSCredentialImpl;
import org.globus.myproxy.MyProxy;
import org.globus.myproxy.MyProxyException;
import org.globus.util.Util;
import org.ietf.jgss.GSSCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import portal.login.domain.Certificate;
import portal.login.domain.UserInfo;
import portal.login.domain.Vo;
import portal.login.services.CertificateService;
import portal.login.services.UserInfoService;
import portal.login.services.UserToVoService;
import portal.login.services.VoService;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;

@Controller(value = "getProxyController")
@RequestMapping(value = "VIEW")
public class GetProxyController {

	/**
	 * Logger of the class DownloadCertificateController. TODO to substitute it
	 * with slf4j.
	 */
	private static final Logger log = Logger
			.getLogger(GetProxyController.class);
	
	/**
	 * Attribute for access to the PortalUser database.
	 */
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * Attribute for access to the PortalUser database.
	 */
	@Autowired
	private VoService voService;
	
	/**
	 * Attribute for access to the PortalUser database.
	 */
	@Autowired
	private UserToVoService userToVoService;
	
	/**
	 * Attribute for access to the PortalUser database.
	 */
	@Autowired
	private CertificateService certificateService;

	/**
	 * Writes the specified globus credential to disk.
	 */
	@ActionMapping(params = "myaction=getProxy")
	public void getProxy(ActionRequest request, ActionResponse response) throws IOException,
			CertificateEncodingException {
		log.info("***** Pronto per scaricare il proxy *****");

		MyProxy mp = new MyProxy("halfback.cnaf.infn.it", 7512);

		/*
		 * use CoGProperties.getDefault().getProxyFile() for default location
		 */
		
		User user = (User) request.getAttribute(WebKeys.USER);
		
		String dir = System.getProperty("java.io.tmpdir");
		log.info("Directory = "+ dir);
		
		
		File location = new File(dir+"/users/"+user.getUserId()+"/");
		if(!location.exists()){
			location.mkdirs();
		}
		
		
		
		OutputStream out = null;

		int cert = Integer.parseInt(request.getParameter("certsId"));
		int vo = Integer.parseInt(request.getParameter("vosId"));
		String pass = (String) request.getParameter("proxyPass");

		log.info("certificato: " + cert + " password: " + pass);

		String username = user.getScreenName();
		
		UserInfo userInfo = userInfoService.findByUsername(username);
		
		if(cert == 0){
			Certificate certificate = null;
			List<Certificate> certs =certificateService.findById(userInfo.getUserId());
			for (Iterator<Certificate> iterator = certs.iterator(); iterator.hasNext();) {
				certificate = (Certificate) iterator.next();
				if(certificate.getPrimaryCert().equals("true"))
					break;
			}
			cert = certificate.getIdCert();
		}
		String voname=null;
		
		if(vo==0){
			voname = userToVoService.findDefaultVo(userInfo.getUserId());
		}else{
			voname = voService.findById(vo).getVo();
		}
		
		File proxyFile = new File(dir+"/users/"+user.getUserId()+"/x509up");
		File proxyFileVO = new File(dir+"/users/"+user.getUserId()+"/x509up." + voname);

		try {
			
			log.info("certificato: " + username + "_" + cert + " password: " + pass);
			//GSSCredential proxy = mp.get("dmichelotto", pass, 100);
			GSSCredential proxy = mp.get(username + "_" + cert, pass, 100 * 3600);
			log.info("----- All ok -----");
			log.info("Proxy:" + proxy.toString());

			GlobusCredential globusCred = null;
			globusCred = ((GlobusGSSCredentialImpl) proxy).getGlobusCredential();
			log.info("----- Passo per il istanceof GlobusGSSCredentialImpl");

			log.info("Save proxy file: " + globusCred);
			out = new FileOutputStream(proxyFile);
			Util.setFilePermissions(proxyFile.toString(), 600);
			globusCred.save(out);
			
			out = new FileOutputStream(proxyFileVO);
			Util.setFilePermissions(proxyFileVO.toString(), 600);
			globusCred.save(out);

			FileWriter fstream = new FileWriter(dir+"/users/"+user.getUserId()+"/.cred",true);
			BufferedWriter outcred = new BufferedWriter(fstream);
		    outcred.write(System.currentTimeMillis() + ";halfback.cnaf.infn.it;" + globusCred.getTimeLeft() + "; ;#" + voname +"\n");
		    //Close the output stream
		    outcred.close();

			LoginController.setDownloaded(true);
			
			SessionMessages.add(request, "proxy-download-success");

		} catch (MyProxyException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			LoginController.setDownloaded(false);
			SessionErrors.add(request, "proxy-download-problem");
			log.info("***** errore myproxy " + e.getMessage() + " MyProxyException *****");
			response.setRenderParameter("myaction",
					"idps");
			
		}catch (IllegalArgumentException e){
			LoginController.setDownloaded(false);
			SessionErrors.add(request, "proxy-download-problem");
			log.info("***** errore myproxy IllegalArgumentException *****");
			response.setRenderParameter("myaction",
					"idps");
			
		} catch (FileNotFoundException e) {
			log.error("Could not write credential to file "
					+ proxyFile.getAbsolutePath() + ": " + e.getMessage());
			throw new IOException(e.getMessage());

		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					log.error("Could not write credential to file "
							+ proxyFile.getAbsolutePath() + ": "
							+ e.getMessage());
					throw e;
				}
			}
		}
	}
	
	
	
	/**
	 * Destroy the specified globus credential to disk.
	 * @throws CredentialException 
	 */
	@ActionMapping(params = "myaction=destroyProxy")
	public void destroyProxy(ActionRequest request)/* throws CredentialException*/{

		User user = (User) request.getAttribute(WebKeys.USER);
		
		String dir = System.getProperty("java.io.tmpdir");
		log.info("Directory = "+ dir);
		
		UserInfo userInfo = userInfoService.findByUsername(user.getScreenName());
		File credFile = new File(dir+"/users/"+user.getUserId()+"/.cred");
		File proxyVoFile = null;
		File proxyFile = new File(dir+"/users/"+user.getUserId()+"/x509up");
		proxyFile.delete();
		credFile.delete();
		
		List<Vo> vos = userToVoService.findVoByUserId(userInfo.getUserId());
		for (Iterator<Vo> iterator = vos.iterator(); iterator.hasNext();) {
			Vo vo = (Vo) iterator.next();
			proxyVoFile = new File(dir+"/users/"+user.getUserId()+"/x509up." + vo.getVo());
			proxyVoFile.delete();
		}
		
		LoginController.setDownloaded(false);
		SessionMessages.add(request, "proxy-destroy-success");
	}
}
