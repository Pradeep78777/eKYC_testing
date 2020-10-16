package com.codespine.util;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Test {

	public static void main(String[] args) {
		try {
			String esignXml = new String(
					Files.readAllBytes(Paths.get("C:\\Users\\GOWRI SANKAR R\\Desktop\\zebu_ekyc\\esignXML.txt")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
