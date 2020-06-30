package com.jdc.app.util;

import java.io.FileInputStream;
import java.util.Properties;

public class SqlHelper {

	private static Properties prop;
	
	static {
		try {
			
			prop = new Properties();
			prop.load(new FileInputStream("sql.properties"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getSql(String key) {
		return prop.getProperty(key);
	}
	
}
