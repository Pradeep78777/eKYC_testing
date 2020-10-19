package com.codespine.util;

import com.nsdl.esign.preverifiedNo.controller.EsignApplication;

public class TestXMLCode {
	public static void main(String[] args) {
		String response = "";
		try {
			String pathToPDF = "C:\\Users\\GOWRI SANKAR R\\Desktop\\zebu_ekyc\\Document Check\\Trading & Demat KYC_V6.pdf";
			String aspID = CSEnvVariables.getProperty(eKYCConstant.E_SIGN_ASP_ID);
			String authMode = "1";
			String responseUrl = "http://rest.irongates.in/eKYCService/eKYC/getNsdlXML";
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
			int signatureWidth = 20;
			int signatureHeight = 5;
			String pdfPassword = "";
			String txn = "";
			try {
				EsignApplication eSignApp = new EsignApplication();
				// eSignApp.getSignOnDocument(eSignResp, PdfSigneture,
				// tickImagePath, serverTime, pageNumberToInsertSignatureStamp,
				// nameToShowOnSignatureStamp, locationToShowOnSignatureStamp,
				// reasonForSign, xCo_ordinates, yCo_ordinates, signatureWidth,
				// signatureHeight, pdfPassword, outputFinalPdfPath)
				response = eSignApp.getEsignRequestXml("", pathToPDF, aspID, authMode, responseUrl, p12CertificatePath,
						p12CertiPwd, tickImagePath, serverTime, alias, pageNumberToInsertSignatureStamp,
						nameToShowOnSignatureStamp, locationToShowOnSignatureStamp, reasonForSign, xCo_ordinates,
						yCo_ordinates, signatureWidth, signatureHeight, pdfPassword, txn);
				System.out.println(response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
