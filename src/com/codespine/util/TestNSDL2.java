package com.codespine.util;

import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

public class TestNSDL2 {
	public static void main(String[] args) {
		try {
			File input = new File("D://Zebull E-Kyc//EKYC//e_sign//file//uploads//9//Pan Card//Protected-protected.pdf");
			PDDocument document = PDDocument.load(input, "123456");
			if (document.isEncrypted()) {
				System.out.println("Password is valid");
				document.setAllSecurityToBeRemoved(true);
				document.save(input);
				document.close();
			}
		} catch (InvalidPasswordException ipe) {
			System.out.println("Encrypted");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
