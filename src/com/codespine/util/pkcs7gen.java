package com.codespine.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.util.encoders.Base64;

//dataToBeSigned is the carrot delimited data consisting of user id and upto 5 pans  - V0128601|ANHPK5791Q
public class pkcs7gen {
	public static void main(String args[]) throws Exception {
		String data= null;
	    try {
	        data=CSEnvVariables.getProperty("urldata");
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    
		String args1[] = { "D:\\output.jks", "12345678", data, "D:\\output1.sig" };

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
	}

}
