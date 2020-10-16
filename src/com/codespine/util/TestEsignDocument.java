package com.codespine.util;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.nsdl.esign.preverifiedNo.controller.EsignApplication;

public class TestEsignDocument {

	public static void main(String[] args) {
		String response = "";
		try {
			String pathToPDF = "C:\\Users\\GOWRI SANKAR R\\Desktop\\zebu_ekyc\\test\\Trading & Demat KYC_V6.pdf";
			String aspID = CSEnvVariables.getProperty(eKYCConstant.E_SIGN_ASP_ID);
			String authMode = "1";
			String responseUrl = "https://zebull.in";
			String p12CertificatePath = CSEnvVariables.getProperty(eKYCConstant.PFX_FILE_LOCATION);
			String p12CertiPwd = CSEnvVariables.getProperty(eKYCConstant.PFX_FILE_PASSWORD);
			String tickImagePath = CSEnvVariables.getProperty(eKYCConstant.E_SIGN_TICK_IMAGE);
			int serverTime = 10;
			String alias = CSEnvVariables.getProperty(eKYCConstant.E_SIGN_ALIAS);
			int pageNumberToInsertSignatureStamp = 1;
			String nameToShowOnSignatureStamp = "Test";
			String locationToShowOnSignatureStamp = "Test";
			String reasonForSign = "";
			int xCo_ordinates = 100;
			int yCo_ordinates = 100;
			int signatureWidth = 200;
			int signatureHeight = 50;
			String pdfPassword = "";
			String txn = "";

			String esignXml = new String(
					Files.readAllBytes(Paths.get("C:\\Users\\GOWRI SANKAR R\\Desktop\\zebu_ekyc\\esignXML.txt")));

			String returnPath = "C:\\Users\\GOWRI SANKAR R\\Desktop\\zebu_ekyc\\Ekyc_document\\";
			try {
				EsignApplication eSignApp = new EsignApplication();
				// eSignApp.getSignOnDocument(eSignResp, PdfSigneture,
				// tickImagePath, serverTime, pageNumberToInsertSignatureStamp,
				// nameToShowOnSignatureStamp, locationToShowOnSignatureStamp,
				// reasonForSign, xCo_ordinates, yCo_ordinates, signatureWidth,
				// signatureHeight, pdfPassword, outputFinalPdfPath)
				// response = eSignApp.getEsignRequestXml("", pathToPDF, aspID,
				// authMode, responseUrl, p12CertificatePath,
				// p12CertiPwd, tickImagePath, serverTime, alias,
				// pageNumberToInsertSignatureStamp,
				// nameToShowOnSignatureStamp, locationToShowOnSignatureStamp,
				// reasonForSign, xCo_ordinates,
				// yCo_ordinates, signatureWidth, signatureHeight, pdfPassword,
				// txn);
				// System.out.println(response);

				String signedDocument = eSignApp.getSignOnDocument(esignXml, pathToPDF, tickImagePath, serverTime,
						pageNumberToInsertSignatureStamp, nameToShowOnSignatureStamp, locationToShowOnSignatureStamp,
						reasonForSign, xCo_ordinates, yCo_ordinates, signatureWidth, signatureHeight, pdfPassword,
						returnPath);
				System.out.println(signedDocument);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
