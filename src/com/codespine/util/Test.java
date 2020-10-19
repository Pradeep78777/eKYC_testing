package com.codespine.util;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Test {

	public static void main(String[] args) {
		try {

			String esignXml = new String(
					Files.readAllBytes(Paths.get("C:\\Users\\GOWRI SANKAR R\\Desktop\\zebu_ekyc\\esignXML.txt")));
			FileWriter myWriter = new FileWriter(
					"C:\\Users\\GOWRI SANKAR R\\Desktop\\zebu_ekyc\\Ekyc_document\\2\\firstEsign.xml");
			myWriter.write(esignXml);
			myWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
