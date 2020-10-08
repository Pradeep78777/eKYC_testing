package com.codespine.util;

import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;

import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.util.encoders.Base64;

public class Etoken_Sig {
//	private static final Provider UserProvider = new sun.security.pkcs11.SunPKCS11(
//			"C:\\Users\\GOWRI SANKAR R\\Desktop\\zebu_ekyc\\Etoken1\\pkcs11.cfg");
	private static final X509Certificate myPubCert = null;
	private static KeyStore ks = null;
	private static X509Certificate UserCert = null;
	private static PrivateKey UserCertPrivKey = null;
	private static PublicKey UserCertPubKey = null;
	private static String alias = null;
	private static final char Password[] = "nsdl@1234".toCharArray();
	private static byte[] dataToSign = null;

	public static void main(String[] args) {
		dataToSign = args[0].getBytes();
		loginToken();

	}

	public static void loginToken() {

		// Adding Security Provider for PKCS 11
//		Security.addProvider(UserProvider);

		try {

			// Setting password for the e-Token

			// logging into token
//			ks = KeyStore.getInstance("PKCS11", UserProvider);

			// Loading Keystore
			ks.load(null, Password);

			// enumeration alias

			Enumeration e = ks.aliases();
			// System.out.println("11111");
			while (e.hasMoreElements()) {

				alias = (String) e.nextElement();

				System.out.println("Alias of the e-Token : " + alias);

				// Populating UserCert
				UserCert = (X509Certificate) ks.getCertificate(alias);

				// Populating PublicKey
				UserCertPubKey = (PublicKey) ks.getCertificate(alias).getPublicKey();

				// Populating PrivateKey reference
				UserCertPrivKey = (PrivateKey) ks.getKey(alias, Password);

				// System.out.println("ALIAS UNKNOWN");
				// System.exit(1);

			}

			// Method Call to generate Signature

			if (MakeSignature()) {
				System.out.println(" *** SIGNATURE OK *** ");
			} else {
				System.out.println(" *** SIGNATURE KO *** ");
			}

		}

		catch (Throwable e) {
			e.printStackTrace();
			System.out.println("ERROR: " + e);
		}

	}

	public static boolean MakeSignature() {
		try {

			/*
			 * Commented section Used for raw signature generation
			 */

			// File I/O

			/*
			 * FileInputStream txtfis = new
			 * FileInputStream("C:\\Users\\530362\\Test.txt");
			 * 
			 * Signature Obj init Signature rsa256 =
			 * Signature.getInstance("SHA256withRSA", UserProvider.getName());
			 * rsa256.initSign(UserCertPrivKey);
			 * 
			 * //Update data BufferedInputStream bufin = new
			 * BufferedInputStream(txtfis); byte[] buffer = new byte[1024]; int
			 * len; while (bufin.available() != 0) { len = bufin.read(buffer);
			 * rsa256.update(buffer, 0, len); } bufin.close();
			 * 
			 * //Make signature byte[] realSig = rsa256.sign();
			 */

			// PKCS7 Signature generation Starts
			ArrayList certList = new ArrayList();

			CertStore certs = null;

			FileOutputStream sigfos = new FileOutputStream("Test_Signature.txt");

			PrivateKey privateKey = (PrivateKey) ks.getKey(alias, Password.toString().toCharArray());

			X509Certificate myPubCert = (X509Certificate) ks.getCertificate(alias);

			CMSSignedDataGenerator sgen = new CMSSignedDataGenerator();

			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

			sgen.addSigner(privateKey, myPubCert, CMSSignedDataGenerator.DIGEST_SHA1);

			certList.add(myPubCert);

			certs = (CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC"));

			sgen.addCertificatesAndCRLs(certs);

			CMSSignedData csd = sgen.generate(new CMSProcessableByteArray(dataToSign), true,
					"SunPKCS11-Aladdin_eToken");

			byte[] signedData = csd.getEncoded();

			byte[] signedData64 = Base64.encode(signedData);

			/* PKCS7 Signature generation Ends */

			// save signature on file
			sigfos.write(signedData64);
			sigfos.close();

			return true;
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("ERROR: " + e);
			return false;
		}

	}
}
