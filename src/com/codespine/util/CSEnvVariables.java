package com.codespine.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Pradeep K
 *
 */
public class CSEnvVariables {
	static InputStream inStream = null;
	static Properties configuration = null;

	public static String getProperty(String propertyName) {
		if (null == configuration) {
			try {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				inStream = classLoader.getResourceAsStream("props.ini");
				configuration = new Properties();
				configuration.load(inStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return configuration.getProperty(propertyName);
	}
}
