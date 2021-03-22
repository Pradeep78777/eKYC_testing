package com.codespine.restservice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.util.encoders.Base64;

import com.codespine.util.APIBased.DummyHostnameVerifier;
import com.codespine.util.APIBased.DummyTrustManager;
import com.codespine.util.CSEnvVariables;
import com.codespine.util.eKYCConstant;
import com.nsdl.esign.preverifiedNo.controller.EsignApplication;

public class NsdlPanVerificationRestService {

	public static NsdlPanVerificationRestService NsdlPanVerificationRestService = null;

	public static NsdlPanVerificationRestService getInstance() {
		if (NsdlPanVerificationRestService == null) {
			NsdlPanVerificationRestService = new NsdlPanVerificationRestService();
		}
		return NsdlPanVerificationRestService;
	}

	/**
	 * To create the pfx to jks File
	 * 
	 * @author GOWRI SANKAR R
	 * @param applicationId
	 */
	public static void pfx2JksFile(int applicationId) {
		try {
			/*
			 * set the pfx location , pfxPassword , password
			 * 
			 * 
			 * Set Location to save the jks file to given user
			 */
			String pfxFileLocation = CSEnvVariables.getProperty(eKYCConstant.PFX_FILE_LOCATION);
			String pfxPassword = CSEnvVariables.getProperty(eKYCConstant.PFX_FILE_PASSWORD);
			String userJksFileLocation = CSEnvVariables.getProperty(eKYCConstant.USER_JKS_LOCATION) + applicationId
					+ "\\" + applicationId + ".jks";
			String args1[] = { pfxFileLocation, pfxPassword, userJksFileLocation };
			File dir = new File(CSEnvVariables.getProperty(eKYCConstant.USER_JKS_LOCATION) + applicationId);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			if (args1.length < 1) {
				System.out.println("usage: java p2j certi.pfx pfxpswd oupt.jks");
				System.exit(1);
			}
			File fileIn = new File(args1[0]);
			File fileOut = null;
			if (args1.length == 3) {
				fileOut = new File(args1[2]);
			} else {
				System.out.println("usage: java p2j certi.pfx pfxpswd oupt.jks");
				System.exit(1);
			}
			if (!fileIn.canRead()) {
				System.out.println("Unable to access input keystore: " + fileIn.getPath());
				System.exit(2);
			}
			if (fileOut.exists() && !fileOut.canWrite()) {
				System.out.println("Output file is not writable: " + fileOut.getPath());
				System.exit(2);
			}
			KeyStore kspkcs12 = KeyStore.getInstance("pkcs12");
			KeyStore ksjks = KeyStore.getInstance("jks");
			char inphrase[] = args1[1].toCharArray();
			char outphrase[] = args1[1].toCharArray();
			kspkcs12.load(new FileInputStream(fileIn), inphrase);
			ksjks.load(fileOut.exists() ? ((java.io.InputStream) (new FileInputStream(fileOut))) : null, outphrase);
			Enumeration eAliases = kspkcs12.aliases();
			int n = 0;
			do {
				if (!eAliases.hasMoreElements())
					break;
				String strAlias = (String) eAliases.nextElement();
				if (kspkcs12.isKeyEntry(strAlias)) {
					java.security.Key key = kspkcs12.getKey(strAlias, inphrase);
					Certificate chain[] = kspkcs12.getCertificateChain(strAlias);
					ksjks.setKeyEntry(strAlias, key, outphrase, chain);
				}
			} while (true);
			OutputStream out = new FileOutputStream(fileOut);
			ksjks.store(out, outphrase);
			out.close();
			System.out.println("Java Key Store created successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * To generate the Pkcs genaration for the given user
	 * 
	 * @author GOWRI SANKAR R
	 * @param applicationId
	 * @param panCard
	 */
	public static void pkcs7Generate(int applicationId, String panCard) {
		try {
			/**
			 * Get the jks file for the given user and create the siganture file
			 * for the given user
			 */
			String data = CSEnvVariables.getProperty(eKYCConstant.PFX_FILE_USERNAME) + "^" + panCard;
			String jksFileLocation = CSEnvVariables.getProperty(eKYCConstant.USER_JKS_LOCATION) + applicationId + "\\"
					+ applicationId + ".jks";
			String pfxPassword = CSEnvVariables.getProperty(eKYCConstant.PFX_FILE_PASSWORD);
			String signatureFile = CSEnvVariables.getProperty(eKYCConstant.USER_JKS_LOCATION) + applicationId
					+ "\\output.sig";
			String args1[] = { jksFileLocation, pfxPassword, data, signatureFile };
			if (args1.length < 3) {
				System.out.println("java pkcs7gen D:\\output.jks pfxpswd dataToBeSigned D:\\oupt.sig");
				System.exit(1);
			}

			KeyStore keystore = KeyStore.getInstance("jks");
			InputStream input = new FileInputStream(args1[0]);
			try {
				char[] password = args1[1].toCharArray();
				keystore.load(input, password);
			} catch (IOException e) {
			} finally {

			}
			Enumeration e = keystore.aliases();
			String alias = "";

			if (e != null) {
				while (e.hasMoreElements()) {
					String n = (String) e.nextElement();
					if (keystore.isKeyEntry(n)) {
						alias = n;
					}
				}
			}
			PrivateKey privateKey = (PrivateKey) keystore.getKey(alias, args1[1].toCharArray());
			X509Certificate myPubCert = (X509Certificate) keystore.getCertificate(alias);
			byte[] dataToSign = args1[2].getBytes();
			CMSSignedDataGenerator sgen = new CMSSignedDataGenerator();
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			sgen.addSigner(privateKey, myPubCert, CMSSignedDataGenerator.DIGEST_SHA1);
			Certificate[] certChain = keystore.getCertificateChain(alias);
			ArrayList certList = new ArrayList();
			CertStore certs = null;
			for (int i = 0; i < certChain.length; i++)
				certList.add(certChain[i]);
			sgen.addCertificatesAndCRLs(
					CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC"));
			CMSSignedData csd = sgen.generate(new CMSProcessableByteArray(dataToSign), true, "BC");
			byte[] signedData = csd.getEncoded();
			byte[] signedData64 = Base64.encode(signedData);
			FileOutputStream out = new FileOutputStream(args1[3]);
			out.write(signedData64);
			out.close();
			System.out.println("Signature file written to " + args1[3]);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Api call for PAN verification (NSDL)
	 * 
	 * @author GOWRI SANKAR R
	 * @param applicationId
	 * @param panCard
	 */
	public static String apiCallForPanVerififcation(int applicationId, String panCard) {
		String result = "";
		try {
			String urlOfNsdl = CSEnvVariables.getProperty(eKYCConstant.NSDL_PAN_VERIFICATION_URL);
			String data = null;
			String signature = null;
			final String version = CSEnvVariables.getProperty(eKYCConstant.NSDL_PAN_VERIFICATION_VERSION);
			Date startTime = null;
			Calendar c1 = Calendar.getInstance();
			startTime = c1.getTime();
			Date connectionStartTime = null;
			String logMsg = "\n-";
			BufferedWriter out = null;
			BufferedWriter out1 = null;
			FileWriter fstream = null;
			FileWriter fstream1 = null;
			Calendar c = Calendar.getInstance();
			long nonce = c.getTimeInMillis();
			Properties prop = new Properties();
			try {
				// prop.load(new FileInputStream("params.properties"));
				data = CSEnvVariables.getProperty(eKYCConstant.PFX_FILE_USERNAME) + "^" + panCard;
				/**
				 * read the signature file for the user and assign for the user
				 */
				signature = new String(Files.readAllBytes(Paths.get(
						CSEnvVariables.getProperty(eKYCConstant.USER_JKS_LOCATION) + applicationId + "\\output.sig")));
				// signature = CSEnvVariables.getProperty("signature");
			} catch (Exception e) {
				logMsg += "::Exception: " + e.getMessage() + " ::Program Start Time:" + startTime + "::nonce= " + nonce;
			}
			try {
				fstream = new FileWriter("API_PAN_verification.logs", true);
				out = new BufferedWriter(fstream);
			} catch (Exception e) {
				logMsg += "::Exception: " + e.getMessage() + " ::Program Start Time:" + startTime + "::nonce= " + nonce;
				out.write(logMsg);
				out.close();
			}
			SSLContext sslcontext = null;
			try {
				sslcontext = SSLContext.getInstance("SSL");
				sslcontext.init(new KeyManager[0], new TrustManager[] { new DummyTrustManager() }, new SecureRandom());
			} catch (NoSuchAlgorithmException e) {
				logMsg += "::Exception: " + e.getMessage() + " ::Program Start Time:" + startTime + "::nonce= " + nonce;
				e.printStackTrace(System.err);
				out.write(logMsg);
				out.close();
			} catch (KeyManagementException e) {
				logMsg += "::Exception: " + e.getMessage() + " ::Program Start Time:" + startTime + "::nonce= " + nonce;
				e.printStackTrace(System.err);
				out.write(logMsg);
				out.close();
			}
			SSLSocketFactory factory = sslcontext.getSocketFactory();
			String urlParameters = "data=";
			try {
				urlParameters = urlParameters + URLEncoder.encode(data, "UTF-8") + "&signature="
						+ URLEncoder.encode(signature, "UTF-8") + "&version=" + URLEncoder.encode(version, "UTF-8");
			} catch (Exception e) {
				logMsg += "::Exception: " + e.getMessage() + " ::Program Start Time:" + startTime + "::nonce= " + nonce;
				e.printStackTrace();
				out.write(logMsg);
				out.close();
			}
			try {
				URL url;
				HttpsURLConnection connection;
				InputStream is = null;
				String ip = urlOfNsdl;
				url = new URL(ip);
				System.out.println("URL " + ip);
				connection = (HttpsURLConnection) url.openConnection();
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
				connection.setRequestProperty("Content-Language", "en-US");
				connection.setUseCaches(false);
				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.setSSLSocketFactory(factory);
				connection.setHostnameVerifier(new DummyHostnameVerifier());
				OutputStream os = connection.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				osw.write(urlParameters);
				osw.flush();
				connectionStartTime = new Date();
				logMsg += "::Request Sent At: " + connectionStartTime;
				logMsg += "::Request Data: " + data;
				logMsg += "::Version: " + version;
				osw.close();
				is = connection.getInputStream();
				BufferedReader in = new BufferedReader(new InputStreamReader(is));
				String line = null;
				line = in.readLine();
				result = line;
				System.out.println("Output: " + line);
				is.close();
				in.close();
			} catch (ConnectException e) {
				logMsg += "::Exception: " + e.getMessage() + "::Program Start Time:" + startTime + "::nonce= " + nonce;
				out.write(logMsg);
				out.close();
			} catch (Exception e) {
				logMsg += "::Exception: " + e.getMessage() + "::Program Start Time:" + startTime + "::nonce= " + nonce;
				out.write(logMsg);
				out.close();
				e.printStackTrace();
			}
			out.write(logMsg);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Method to get the xml code for entering into the NSDL Page
	 * 
	 * @author GOWRI SANKAR R
	 * @return
	 */
	public String getXmlForEsign(int applicationId, String folderName) {
		String response = "";
		try {
			String pathToPDF = folderName;
			String aspID = CSEnvVariables.getProperty(eKYCConstant.E_SIGN_ASP_ID);
			String authMode = "1";
			String responseUrl = "https://oa.zebull.in/rest/eKYCService/eKYC/getNsdlXML";
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
		return response;
	}

	/**
	 * Get the signed document from the NSDL
	 * 
	 * @author GOWRI SANKAR R
	 * @param dcoumentLocation
	 * @param documentToBeSavedLocation
	 * @param receivedXml
	 * @param applicantName
	 * @param city
	 * @return
	 */
	public String getSignFromNsdl(String dcoumentLocation, String documentToBeSavedLocation, String receivedXml,
			String applicantName, String city) {
		String responseText = null;
		try {
			String pathToPDF = dcoumentLocation;
			String tickImagePath = CSEnvVariables.getProperty(eKYCConstant.E_SIGN_TICK_IMAGE);
			int serverTime = 10;
			PDDocument pdDoc = PDDocument.load(new File(pathToPDF));
			int pageNumberToInsertSignatureStamp = pdDoc.getNumberOfPages();
			String nameToShowOnSignatureStamp = applicantName.toUpperCase();
			String locationToShowOnSignatureStamp = city.toUpperCase();
			String reasonForSign = "";
			int xCo_ordinates = 10;
			int yCo_ordinates = 190;
			int signatureWidth = 200;
			int signatureHeight = 50;
			String pdfPassword = "";
			String esignXml = receivedXml;
			String returnPath = documentToBeSavedLocation;
			try {
				EsignApplication eSignApp = new EsignApplication();
				responseText = eSignApp.getSignOnDocument(esignXml, pathToPDF, tickImagePath, serverTime,
						pageNumberToInsertSignatureStamp, nameToShowOnSignatureStamp, locationToShowOnSignatureStamp,
						reasonForSign, xCo_ordinates, yCo_ordinates, signatureWidth, signatureHeight, pdfPassword,
						returnPath);
				System.out.println("Response from NSDL" + responseText);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseText;
	}
}
