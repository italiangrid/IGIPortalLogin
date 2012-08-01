package portal.login.test;

import org.globus.myproxy.MyProxy;
import org.globus.myproxy.MyProxyException;
import org.ietf.jgss.GSSCredential;

import portal.login.util.LoadProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class Test {
	
//	public static void main(String[] args) throws IOException {
//		MyProxy mp = new MyProxy("fullback.cnaf.infn.it",7512); 
//		try {
//			GSSCredential proxy = mp.get("dmichelotto_191", "dmikel8", 100);
//			System.out.println("----- All ok -----"+ proxy);
//		} catch (MyProxyException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("***** errore myproxy *****");
//		}
//		
//		FileInputStream inStream =
//			    new FileInputStream("/content/MyProxy.properties");
//		
//		Properties prop = new Properties();
//		prop.load(inStream);
//		inStream.close();
//		
//		String test = "Current Status:     Waiting";
//		
//		test= test.replace("Current Status:", "").trim();
//		
//		boolean var = Boolean.parseBoolean("true");
//		
//		System.out.println(var);
//		
//		System.out.println("Host = " + prop.getProperty("Host"));
//	}
	
	public static void main(String[] args) {
		LoadProperties props = new LoadProperties("test.properties");
		
		props.putValue("uno", "1");
		props.putValue("due", "2");
		props.putValue("tre", "3");
		props.putValue("tres", "3");
		
		System.out.println("Value of \"uno\" is: "+props.getValue("uno"));
		System.out.println("Value of \"due\" is: "+props.getValue("due"));
		System.out.println("Value of \"tre\" is: "+props.getValue("tre"));
		
		for (Enumeration<Object> e = props.getProperties().keys() ; e.hasMoreElements() ;) {
			
			String key = (String) e.nextElement();
	        System.out.println(key);

	     }
	}

}
