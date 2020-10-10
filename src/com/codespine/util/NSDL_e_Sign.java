package com.codespine.util;

import com.nsdl.esign.preverifiedNo.controller.EsignApplication;

/**
 * @Cretead_On 24-Sep-2020 7:53:40 pm
 * @Author jerok
 * 
 */

public class NSDL_e_Sign {

	public static void main(String[] args) {
		String pathToPDF = "C:\\Users\\GOWRI SANKAR R\\Desktop\\zebu_ekyc\\Ekyc_document\\Trading & Demat KYC_V6.pdf";
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
			String xml = eSignApp.getEsignRequestXml("", pathToPDF, aspID, authMode, responseUrl, p12CertificatePath,
					p12CertiPwd, tickImagePath, serverTime, alias, pageNumberToInsertSignatureStamp,
					nameToShowOnSignatureStamp, locationToShowOnSignatureStamp, reasonForSign, xCo_ordinates,
					yCo_ordinates, signatureWidth, signatureHeight, pdfPassword, txn);
			System.out.println(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
