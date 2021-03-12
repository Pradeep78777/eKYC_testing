package com.codespine.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class TestXMLConversion {
	public static void main(String[] args) {
		try {
			// String panCard = "";
			// String userName = "";
			// String posCode = "";
			// String password = "";
			// String passKey = "";
			// String xmlCode =
			// "panNo=BZAPG5040A&userName=ITTEAM&PosCode=1401317927&password=Zebu@@456&PassKey=";
			// connectKra(xmlCode);
			Utility.sendMessage(Long.parseLong("9865858790"), 1234);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void connectKra(String xmlCode) {
		try {
			URL url = new URL("https://www.cvlkra.com/panInquiry.asmx/GetPanStatus");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setDoOutput(true);
			try (OutputStream os = conn.getOutputStream()) {
				byte[] input = xmlCode.getBytes("utf-8");
				os.write(input, 0, input.length);
			}
			if (conn.getResponseCode() != 200) {
				System.out.println(conn.getResponseMessage());
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			BufferedReader br1 = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			StringBuilder sb = new StringBuilder();
			while ((output = br1.readLine()) != null) {
				System.out.println(output);
				sb.append(output);
			}
			parseXmlResponse(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	private static void parseXmlResponse(String xmlResponse) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlResponse);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			Node nList = (Node) doc.getElementsByTagName("APP_PAN_INQ");
			Element eElement = (Element) nList;
			System.out.println(eElement.getAttribute("APP_NAME"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
