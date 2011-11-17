package portal.login.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CertificateEncodingException;

import org.apache.log4j.Logger;
import org.globus.myproxy.MyProxy;
import org.globus.myproxy.MyProxyException;
import org.globus.gsi.X509Credential;
import org.globus.gsi.gssapi.GlobusGSSCredentialImpl;
import org.globus.util.Util;
import org.ietf.jgss.GSSCredential;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller(value = "downloadCertificateController")
@RequestMapping(value = "VIEW")
public class DownloadCertificateController {
	
	private static final Logger log = Logger
			.getLogger(DownloadCertificateController.class);

	@RenderMapping(params = "myaction=downloadCertificate")
	public String showDownloadCertificate() {
        log.info("***** faccio il render *****");
		return "downloadCertificate";
	}
	
	@ActionMapping(params = "myaction=getProxy")
	public void getProxy()  throws IOException, CertificateEncodingException{
		log.info("***** Pronto per scaricare il proxy *****");
		
		MyProxy mp = new MyProxy("halfback.cnaf.infn.it",7512); 
		try {
			GSSCredential proxy = mp.get("mikel8_186", "dmikel8", 100);
			System.out.println("----- All ok -----");
			System.out.println("Proxy:" + proxy.toString());
			
			X509Credential globusCred = null;
			if (proxy instanceof GlobusGSSCredentialImpl) {
			  globusCred = ((GlobusGSSCredentialImpl)proxy).getX509Credential();
			}
			File proxyFile= new File("/Users/dmichelotto/proxy");
			OutputStream out = null;
			log.info("Save proxy file: " + globusCred);
			try {
				out = new FileOutputStream(proxyFile);
				Util.setFilePermissions(proxyFile.toString(), 600);
				globusCred.save(out);
			} catch (FileNotFoundException e) {
				log.error("Could not write credential to file "+proxyFile.getAbsolutePath()+": "+e.getMessage());
				throw new IOException(e.getMessage());
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						log.error("Could not write credential to file "+proxyFile.getAbsolutePath()+": "+e.getMessage());
						throw e;
					}
				}
			}
		} catch (MyProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("***** errore myproxy *****");
		}
		
		
		
	}
}
