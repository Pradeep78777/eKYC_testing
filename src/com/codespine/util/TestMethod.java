package com.codespine.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TestMethod {
	public static void main(String[] args) {
		try {
			File xmlFile = new File("C:\\Users\\GOWRI SANKAR R\\Desktop\\lastXml2860.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			Element eElement = doc.getDocumentElement();
			String errorMessage = eElement.getAttribute("errMsg");
			String errorCode = eElement.getAttribute("errCode");
			if (errorMessage != null && errorCode != null && !errorMessage.isEmpty() && !errorCode.isEmpty()
					&& errorMessage.equalsIgnoreCase("NA") && errorCode.equalsIgnoreCase("NA")) {
				System.out.println("SucessFully Esigned");
			} else {
				System.out.println(errorMessage);
				System.out.println(errorCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
