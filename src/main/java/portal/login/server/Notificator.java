package portal.login.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import org.apache.log4j.Logger;
import portal.login.util.LoadProperties;
import portal.login.util.SendMail;

public class Notificator implements Runnable {

	private static final Logger log = Logger.getLogger(Notificator.class);

	public void run() {
		try {
			log.info("############## NOTIFICATOR ##############"); 

			LoadProperties props = new LoadProperties("../../../ProxyTempDir/checkProxy.properties");

			if (props.getProperties().isEmpty())
				return;

			for (Enumeration<Object> e = props.getProperties().keys(); e
					.hasMoreElements();) {

				String key = (String) e.nextElement();
				log.info(key);

				String value = props.getValue(key);
				log.info(value);
				
				String[] values = value.split(";");

				String proxyFile = values[0];
				int limit = Integer.parseInt(values[1]);
				String mail = values[2];
				String user = values[3];
				String valid = values[4];
				String role = values[5];
				boolean notify = Boolean.parseBoolean(values[6]);
				
				log.info("Notify is: " + notify);

				String voName = key.substring(key.indexOf(".")+1, key.length());

				log.info("RESULT: " + proxyFile + " | " + limit + " | "
						+ voName + " | " + mail + " | " + user + " | " + valid
						+ " | " + role);

				File proxy = new File(proxyFile);

				if (proxy.exists()) {

					long[] expirationTime = getExpirationTime(proxyFile,valid);
					if (expirationTime != null) {
						
						if ((expirationTime[1] < (long) limit)
								&& (expirationTime[0] == 0)) {

							log.info("sono dentro");

							switch (limit) {
							case 60:
								String newValid = expirationTime[3] +":"+expirationTime[4];
								boolean renewed = false;
								if((expirationTime[3]>1)&&(tryToRenewProxy(proxyFile, voName, newValid, role))) {
									renewed=true;
									log.info("Authomatic renewed.");
								}
								
								if(!renewed && notify){
									props.putValue(key,value.replace("60", "30"));
									log.info("60  minutes limit");
									sendMail(mail, user, limit, voName);
								}
								break;
							case 30:
								if(notify){
									props.deleteValue(key);
	
									log.info("30  minutes limit");
									
									sendMail(mail, user, limit, voName);
								}
								break;
							}
						}else{
							
							
						}
					}
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return;
		}
	}

	private long[] getExpirationTime(String proxyFile, String valid) {

		long[] result = null;

		try {
			String cmd2 = "voms-proxy-info -timeleft -file " + proxyFile;
			log.info("cmd = " + cmd2);
			Process p2 = Runtime.getRuntime().exec(cmd2);
			InputStream stdout2 = p2.getInputStream();
			InputStream stderr2 = p2.getErrorStream();
			
			BufferedReader output2 = new BufferedReader(new InputStreamReader(
					stdout2));
			String line2 = null;

			long timeleftProxy=0;
			
			while ((line2 = output2.readLine()) != null) {
				log.info("[Stdout] " + line2);
				timeleftProxy = Long.parseLong(line2);
			}
			
			output2.close();

			BufferedReader brCleanUp2 = new BufferedReader(
					new InputStreamReader(stderr2));
			while ((line2 = brCleanUp2.readLine()) != null) {

				log.error("[Stderr] " + line2);
			}

			String cmd = "voms-proxy-info -actimeleft -file " + proxyFile;

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
				
				long hours = totalSecs / 3600;
				long minutes = (totalSecs % 3600) / 60;
				long seconds = totalSecs % 60;
				
				long validHours = timeleftProxy / 3600;
				long validMinutes = (timeleftProxy % 3600) /60;

				long[] newResult = { hours, minutes, seconds, validHours, validMinutes };
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

	private boolean tryToRenewProxy(String proxyFile, String voName,
			String valid, String role) {

		try {

			String proxy = proxyFile.replace("." + voName, "");

			String cmd = "voms-proxy-init -noregen -cert " + proxy + " -key "
					+ proxy + " -out " + proxyFile + " -valid " + valid
					+ " -voms " + voName;
			log.info(cmd);
			if (!role.equals("norole")) {
				cmd += ":" + role;
			}
			log.info("cmd = " + cmd);
			Process p = Runtime.getRuntime().exec(cmd);
			InputStream stdout = p.getInputStream();
			InputStream stderr = p.getErrorStream();

			BufferedReader output = new BufferedReader(new InputStreamReader(
					stdout));
			String line = null;

			while ((line = output.readLine()) != null) {
				log.info("[Stdout] " + line);
			}
			output.close();

			BufferedReader brCleanUp = new BufferedReader(
					new InputStreamReader(stderr));
			while ((line = brCleanUp.readLine()) != null) {
				
				if (!line.contains("....")) {
					log.error("[Stderr] " + line);
				}
			}

			brCleanUp.close();
			long[] expirationTime = getExpirationTime(proxyFile,valid);
			
			if(expirationTime[3]>1)
				return true;
			
			return false;

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}

	}

	private void sendMail(String mail, String user, int limit, String voName) {
		LoadProperties props = new LoadProperties("content/MyProxy.properties");
		String sender = props.getValue("notificator.mail.sender");
		boolean isHtml = true;
		String text;
		try {
			text = readFile(props.getValue("notificator.mail.text.file"));
			text = text.replaceAll("##USER##", user);
			text = text.replaceAll("##LIMIT##", String.valueOf(limit));
			text = text.replaceAll("##HOST##", props.getValue("portal.url"));
		} catch (IOException e) {
			e.printStackTrace();
			isHtml =  false;
			text = "Dear "
					+ user
					+ ",\n your proxy will expire in "
					+ limit
					+ " minutes.\n\n If necessary renew the proxy on https://portal.italiangrid.it \n\n [If you don't want to receive this mail go to Advanced option into My Data page and uncheck che option.] \n\n      - Portal Administrators";
		}
		
		String subject = props.getValue("notificator.mail.subject") + " " + voName;
		
		SendMail sm = new SendMail(sender,
				mail, subject, text, isHtml);
		sm.send();
	}
	
	private String readFile(String fileName) throws IOException {
		log.info("Path mail: " + Notificator.class.getClassLoader().getResource("").getPath() + fileName);
	    BufferedReader br = new BufferedReader(new FileReader(Notificator.class.getClassLoader().getResource("").getPath() + fileName));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}

}
