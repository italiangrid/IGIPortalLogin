package portal.login.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadProperties {

	private static final Logger log = Logger.getLogger(LoadProperties.class);

	private Properties props = new Properties();
	private boolean initiated = false;
	private String filename = null;

	public LoadProperties(String file) {
		String contextPath = LoadProperties.class.getClassLoader()
				.getResource("").getPath();

		File test = new File(contextPath + file);
		log.info("File properties notificator: " + test.getAbsolutePath());

		if (test.exists()) {
			log.info("Properties found: " + contextPath + file);
			
		}else{
			log.error("Properties not found: " + contextPath + file + ". Cretating new file.");
			try {
				test.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			FileInputStream inStream = new FileInputStream(contextPath
					+ file);

			props.load(inStream);

			inStream.close();
			initiated = true;
			filename = contextPath + file;
			log.info("Properties loaded: " + filename);
		} catch (IOException e) {
			initiated= false;
			log.info("Properties non loaded: " + e.getMessage());
		}
	}
	
	public String getValue(String key){
		if(!initiated){
			log.error("Properties not initated");
			return null;
		}
		return props.getProperty(key);
	}
	
	public void putValue(String key, String value){
		if(!initiated){
			log.error("Properties not initated");
			return;
		}
		props.put(key, value);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(filename);
	        props.store(fos,null);
	        fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteValue(String key){
		if(!initiated){
			log.error("Properties not initated");
			return;
		}
		props.remove(key);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(filename);
	        props.store(fos,null);
	        fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Properties getProperties(){
		return this.props;
	}

}
