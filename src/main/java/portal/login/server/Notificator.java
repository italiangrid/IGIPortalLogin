package portal.login.server;

import it.italiangrid.portal.dbapi.domain.UserToVo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.log4j.Logger;
import org.globus.gsi.GlobusCredential;
import org.globus.gsi.GlobusCredentialException;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;

import portal.login.util.LoadProperties;
import portal.login.util.SendMail;


public class Notificator implements Runnable{
	
	private static final Logger log = Logger
			.getLogger(Notificator.class);
	
	
	public void run(){
		try{
			log.debug("############## NOTIFICATOR ##############");
			
			/*
			 * Recupera cose da notificate
			 * 
			 * notifica se il tempo del proxy rimante è inferiore ad un'ora.
			 * 
			 */
			
			LoadProperties props = new LoadProperties("checkProxy.properties");
			
			if(props.getProperties().isEmpty())
				return;
			
			for (Enumeration<Object> e = props.getProperties().keys() ; e.hasMoreElements() ;) {
				
				String key = (String) e.nextElement();
		        log.info(key);
		        
		        
		        String value = props.getValue(key);
		        log.info(value);
		        
		        
		        String proxyFile = value.split(";")[0];
		        int limit = Integer.parseInt(value.split(";")[1]);
		        String mail = value.split(";")[2];
		        String user = value.split(";")[3];
		        String valid = value.split(";")[4];
		        String role = value.split(";")[5];
		        
		        String voName =key.split("\\.")[1];
		        
		        log.error("RESULT: " + proxyFile + " | " + limit + " | " + voName + " | " + mail + " | " + user + " | " + valid + " | " + role);
		        
		        File proxy = new File(proxyFile);
		        
		        if(proxy.exists()){
		        	
		        	long[] expirationTime = getExpirationTime(proxyFile);
					if(expirationTime!=null){	
						if((expirationTime[1]<(long) limit)&&(expirationTime[0]==0)){
							
							log.info("sono dentro");
							
							switch(limit){
							case 60: 
									 if(tryToRenewProxy(proxyFile, voName, valid, role)){
								
										 props.putValue(key, value.replace("60","30"));
										
										 log.info("60  minutes limit");
								
										 sendMail(mail, user, limit, voName);
									 }
									 break;
							case 30: props.deleteValue(key);
							
							 		 log.info("30  minutes limit");
					
									 sendMail(mail, user, limit, voName);
									 break;
							}
						}
					}
		        }
		     }

		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return;
		}
	}


	private long[] getExpirationTime(String proxyFile) {
		
		long[] result = null;
		
		try {
			
			String cmd = "voms-proxy-info -actimeleft -file " + proxyFile;
			
			log.info("cmd = " + cmd);
			Process p = Runtime.getRuntime().exec(cmd);
			InputStream stdout = p.getInputStream();
			InputStream stderr = p.getErrorStream();

			BufferedReader output = new BufferedReader(new InputStreamReader(
					stdout));
			String line = null;
			
			while ((line = output.readLine()) != null) {
				log.info("[Stdout] " + line);
				long totalSecs =Long.parseLong(line);
				long hours = totalSecs / 3600;
				long minutes = (totalSecs % 3600) / 60;
				long seconds = totalSecs % 60;
				
				long[] newResult = {hours, minutes, seconds};
				result = newResult;
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


	private boolean tryToRenewProxy(String proxyFile, String voName, String valid, String role) {
		
		try {
			
			String proxy = proxyFile.replace("."+ voName, "");
			
			String cmd = "voms-proxy-init -noregen -cert " + proxy + " -key " + proxy + " -out " + proxyFile + " -valid " + valid  + " -voms " + voName;
			log.error(cmd);
			if(!role.equals("norole")){
				cmd += ":" + role;
			}
			log.error("cmd = " + cmd);
			Process p = Runtime.getRuntime().exec(cmd);
			InputStream stdout = p.getInputStream();
			InputStream stderr = p.getErrorStream();

			BufferedReader output = new BufferedReader(new InputStreamReader(
					stdout));
			String line = null;

			while ((line = output.readLine()) != null) {
				log.error("[Stdout] " + line);
			}
			output.close();
			
			boolean error = false;

			BufferedReader brCleanUp = new BufferedReader(
					new InputStreamReader(stderr));
			while ((line = brCleanUp.readLine()) != null) {
				error= true;
				if(!line.contains("....")){
					log.error("[Stderr] " + line);
				}
			}
			
			brCleanUp.close();
			if(error==true)
				return false;
			return true;

		} catch (IOException e) {
			
			e.printStackTrace();
			return false;
		}
		
	}


	private void sendMail(String mail, String user, int limit, String voName) {
		
		String text= "Dear " + user + ",\n your proxy will expire in " + limit + " minutes.\n\n If necessary renew the proxy on https://portal.italiangrid.it \n\n [If you don't want to receive this mail go to Advanced option into My Data page and uncheck che option.] \n\n      - Portal Administrators";
		SendMail sm = new SendMail("igi-portal-admin@lists.italiangrid.it", mail, "Proxy Expiration for " + voName, text);
		sm.send();
	}

}
