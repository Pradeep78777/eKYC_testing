package com.codespine.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Pradeep K
 *
 */
public class CSEnvVariables {
	static InputStream probsInStream = null;
	static Properties probConfig = null;

	static InputStream methodsInStream = null;
	static Properties methodConfig = null;
	
	static InputStream contractInStream = null;
	static Properties contractConfig = null;

	/**
	 * App based common properties
	 * 
	 * @param propertyName
	 * @return
	 */
	public static String getProperty(String propertyName) {
		if (null == probConfig) {
			try {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				probsInStream = classLoader.getResourceAsStream("props.ini");
				probConfig = new Properties();
				probConfig.load(probsInStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return probConfig.getProperty(propertyName);
	}

	/**
	 * Thomson Reuters method names
	 * 
	 * @param propertyName
	 * @return
	 */
	public static String getMethodNames(String propertyName) {
		if (null == methodConfig) {
			try {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				methodsInStream = classLoader.getResourceAsStream("methods.ini");
				methodConfig = new Properties();
				methodConfig.load(methodsInStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return methodConfig.getProperty(propertyName);
	}
	

	/**
	 *  App based contract properties
	 * 
	 * @param propertyName
	 * @return
	 */
	public static String getContractProperty(String propertyName) {
		if (null == contractConfig) {
			try {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				contractInStream = classLoader.getResourceAsStream("contract.ini");
				contractConfig = new Properties();
				contractConfig.load(contractInStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return contractConfig.getProperty(propertyName);
	}
}
