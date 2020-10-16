package com.codespine.util;

import java.io.File;

public class Test {

	public static void main(String[] args) {
		String givenPath = "C://apache-tomcat-8.5.35//Test//";
		int applicationId = 2;
		File dir = new File(givenPath + applicationId);
		if (!dir.exists()) {
			dir.mkdirs();
		} else {
			dir.mkdirs();
		}
	}

}
