package com.codespine.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TestXMLConversion {
	public static void main(String[] args) {
		try {
			File fXmlFile = new File("C:\\Users\\GOWRI SANKAR R\\Desktop\\zebu_ekyc\\CheckEsignXML.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			Element eElement = doc.getDocumentElement();
			System.out.println(eElement.getAttribute("txn"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
