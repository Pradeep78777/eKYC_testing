package com.codespine.util;

import java.io.File;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		Utility.pfx2JksFile(101);
		Utility.pkcs7Generate(101, "AAIPM3854E");
		Utility.apiCallForPanVerififcation(101, "AAIPM3854E");
//		try {
//			File myObj = new File("C:\\Users\\GOWRI SANKAR R\\Desktop\\zebu_ekyc\\1001\\output1.sig");
//			Scanner myReader = new Scanner(myObj);
//			while (myReader.hasNextLine()) {
//				String data = myReader.nextLine();
//				System.out.println(data);
//			}
//			myReader.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
