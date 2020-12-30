package com.codespine.util;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.codespine.cache.CacheController;
import com.codespine.dto.AccesslogDTO;
import com.codespine.dto.ApplicationLogDTO;
import com.codespine.service.AccessLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsdl.esign.preverifiedNo.controller.EsignApplication;

import sun.misc.BASE64Encoder;

@SuppressWarnings("unchecked")
public class Utility {

	/**
	 * Method to create the show url for the IPV
	 * 
	 * @author GOWRI SANKAR R
	 * @param Long
	 *            URL
	 * @return
	 */
	public static String getBitlyLink(String longLink) {
		JSONObject response = new JSONObject();
		JSONObject result = new JSONObject();
		Object object = new Object();
		String bitly_Url = "";
		try {
			String encoded_url = URLEncoder.encode(longLink, "UTF-8");
			URL url = new URL(CSEnvVariables.getMethodNames(eKYCConstant.BITLY_BASEURL) + "access_token="
					+ CSEnvVariables.getMethodNames(eKYCConstant.BITLY_ACCESS_TOKEN) + "&longUrl=" + encoded_url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			BufferedReader br1 = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;

			while ((output = br1.readLine()) != null) {
				object = JSONValue.parse(output);
				response = (JSONObject) object;
				String Stat = (String) response.get("status_txt");
				if (Stat.equalsIgnoreCase("ok")) {
					result = (JSONObject) response.get("data");
					bitly_Url = (String) result.get("url");
				} else {
					bitly_Url = null;
				}
				System.out.println(response);

			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return bitly_Url;

	}

	/**
	 * To random generated Text
	 * 
	 * @author GOWRI SANKAR R
	 * @param count
	 * @return
	 */
	public static String randomAlphaNumericNew(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

	/**
	 * Method to generate OTP
	 * 
	 * @since 26-9-2019
	 * @returnString
	 *
	 */
	public static String generateOTP() {
		int randomPin = (int) (Math.random() * 9000) + 1000;
		String otp = String.valueOf(randomPin);
		return otp;
	}

	/**
	 * To create the random Alpha numeric String for sending the email
	 * Verification Link
	 * 
	 * @author GOWRI SANKAR R
	 */
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public static String randomAlphaNumeric() {
		StringBuilder builder = new StringBuilder();
		int count1 = 16;
		while (count1-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

	public static String sentEmailVerificationLink(String email, String activationLink) {
		StringBuilder builder = new StringBuilder();
		String status = eKYCConstant.FAILED_MSG;
		try {

			// Get system properties
			Properties properties = new Properties();
			// Setup mail server
			properties.put("mail.smtp.host", CSEnvVariables.getProperty(eKYCConstant.HOST));
			properties.put("mail.smtp.user", CSEnvVariables.getProperty(eKYCConstant.USER_NAME));
			properties.put("mail.smtp.port", CSEnvVariables.getProperty(eKYCConstant.PORT));
			properties.put("mail.smtp.socketFactory.port", CSEnvVariables.getProperty(eKYCConstant.PORT));
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.debug", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.put("mail.smtp.socketFactory.fallback", "false");
			Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(CSEnvVariables.getProperty(eKYCConstant.USER_NAME),
							CSEnvVariables.getProperty(eKYCConstant.PASSWORD));
				}
			});
			try {
				String hs = "<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n"
						+ "<head><title>Zebull E-Kyc</title>\r\n"
						+ "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">\r\n"
						+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
						+ "<style type=\"text/css\">\r\n" + "\r\n"
						+ "    @media only screen and (max-width:375px) and (min-width:374px){\r\n"
						+ "      .gmail-fix{\r\n" + "        min-width:374px !important;\r\n" + "      }\r\n" + "\r\n"
						+ "    }	@media only screen and (max-width:414px) and (min-width:413px){\r\n"
						+ "      .gmail-fix{\r\n" + "        min-width:413px !important;\r\n" + "      }\r\n" + "\r\n"
						+ "    }	@media only screen and (max-width:500px){\r\n" + "      .flexible{\r\n"
						+ "        width:100% !important;\r\n" + "      }\r\n" + "\r\n"
						+ "    }	@media only screen and (max-width:500px){\r\n" + "      .table-center{\r\n"
						+ "        float:none !important;\r\n" + "        margin:0 auto !important;\r\n" + "      }\r\n"
						+ "\r\n" + "    }	@media only screen and (max-width:500px){\r\n" + "      .img-flex img{\r\n"
						+ "        width:100% !important;\r\n" + "        height:auto !important;\r\n" + "      }\r\n"
						+ "\r\n" + "    }	@media only screen and (max-width:500px){\r\n" + "      .aligncenter{\r\n"
						+ "        text-align:center !important;\r\n" + "      }\r\n" + "\r\n"
						+ "    }	@media only screen and (max-width:500px){\r\n" + "      .table-holder{\r\n"
						+ "        display:table !important;\r\n" + "        width:100% !important;\r\n" + "      }\r\n"
						+ "\r\n" + "    }	@media only screen and (max-width:500px){\r\n" + "      .thead{\r\n"
						+ "        display:table-header-group !important;\r\n" + "        width:100% !important;\r\n"
						+ "      }\r\n" + "\r\n" + "    }	@media only screen and (max-width:500px){\r\n"
						+ "      .trow{\r\n" + "        display:table-row !important;\r\n"
						+ "        width:100% !important;\r\n" + "      }\r\n" + "\r\n"
						+ "    }	@media only screen and (max-width:500px){\r\n" + "      .tfoot{\r\n"
						+ "        display:table-footer-group !important;\r\n" + "        width:100% !important;\r\n"
						+ "      }\r\n" + "\r\n" + "    }	@media only screen and (max-width:500px){\r\n"
						+ "      .flex{\r\n" + "        display:block !important;\r\n"
						+ "        width:100% !important;\r\n" + "      }\r\n" + "\r\n"
						+ "    }	@media only screen and (max-width:500px){\r\n" + "      .hide{\r\n"
						+ "        display:none !important;\r\n" + "        width:0 !important;\r\n"
						+ "        height:0 !important;\r\n" + "        padding:0 !important;\r\n"
						+ "        font-size:0 !important;\r\n" + "        line-height:0 !important;\r\n"
						+ "      }\r\n" + "\r\n" + "    }	@media only screen and (max-width:500px){\r\n"
						+ "      .plr-0{\r\n" + "        padding-left:0 !important;\r\n"
						+ "        padding-right:0 !important;\r\n" + "      }\r\n" + "\r\n"
						+ "    }	@media only screen and (max-width:500px){\r\n" + "      .plr-10{\r\n"
						+ "        padding-left:10px !important;\r\n" + "        padding-right:10px !important;\r\n"
						+ "      }\r\n" + "\r\n" + "    }	@media only screen and (max-width:500px){\r\n"
						+ "      .pl-0{\r\n" + "        padding-left:0 !important;\r\n" + "      }\r\n" + "\r\n"
						+ "    }	@media only screen and (max-width:500px){\r\n" + "      .pb-10{\r\n"
						+ "        padding-bottom:10px !important;\r\n" + "      }\r\n" + "\r\n"
						+ "    }	@media only screen and (max-width:500px){\r\n" + "      .pb-30{\r\n"
						+ "        padding-bottom:30px !important;\r\n" + "      }\r\n" + "\r\n" + "    }\r\n"
						+ "    </style>\r\n" + "	<style>a:hover{text-decoration:none !important}\r\n"
						+ ".h-u a:hover{text-decoration:underline !important}\r\n"
						+ "a[href^=tel]:hover{text-decoration:none !important}\r\n"
						+ ".active-i a:hover{opacity:.8}\r\n" + ".active-t:hover{opacity:.8}</style>\r\n"
						+ "<meta name=\"robots\" content=\"noindex, follow\"></head>\r\n"
						+ "<body bgcolor=\"white\" style=\"margin:0; padding:0; -webkit-text-size-adjust:100%; -ms-text-size-adjust:100%;\">\r\n"
						+ "  <table class=\"gmail-fix\" bgcolor=\"#ffffff\" width=\"100%\" style=\"min-width:320px;\" cellspacing=\"0\" cellpadding=\"0\">\r\n"
						+ "  <tbody>\r\n" + "  <tr>\r\n"
						+ "  <td style=\"mso-line-height-rule:exactly;display: none;font-size:\r\n"
						+ " 0;line-height: 0;mso-line-height-rule: exactly;\">      \r\n" + "  </td>\r\n"
						+ "  </tr>\r\n" + "  <tr>\r\n"
						+ "  <td bgcolor=\"white\" style=\"mso-line-height-rule:exactly;padding: 9px 0 0;mso-line-height-rule: exactly;\">\r\n"
						+ "<table class=\"flexible\" width=\"600\" align=\"center\" style=\"margin:0 auto;\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
						+ "  <tbody>\r\n"
						+ "  <tr><td class=\"aligncenter h-u\" align=\"right\" style=\"mso-line-height-rule:exactly;padding: 0 12px 31px;font: 12px/16px Avenir, Verdana,\r\n"
						+ " sans-serif;color: #d0c087;mso-line-height-rule: exactly;\">\r\n"
						+ " <span style=\"text-decoration:none\"><span style=\"color:rgb(128, 128, 128);text-decoration:none\"></span></span>\r\n"
						+ " </td>\r\n" + " </tr>\r\n" + " <tr>\r\n"
						+ " <td class=\"pb-30\" align=\"center\" style=\"mso-line-height-rule:exactly;padding: 0 0 40px;mso-line-height-rule: exactly;\">\r\n"
						+ " <a href=\"https://s3.amazonaws.com/assets.mailcharts.com/emails/50d0815b-d855-a5da-0799-518e471d57ac/#\" style=\"outline:none;color:#5ab1a0;text-decoration:underline;text-decoration: none;outline: none;color: #424242;\">\r\n"
						+ " \r\n"
						+ " <!-- <img style=\"width:250px\" src=\"data:image/png;base64,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\" align=\"left\" /> -->\r\n"
						+ " \r\n" + "  </tr></tbody>\r\n" + " </tr></tbody>\r\n" + " </table>\r\n" + "\r\n"
						+ " <table class=\"flexible\" width=\"600\" align=\"center\" style=\"border-radius: 5px; background-color: #6a61ab;padding : 2%\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr>\r\n"
						+ " <td class=\"pb-30 plr-10\" style=\"mso-line-height-rule:exactly;padding: 35px 41px 25px;mso-line-height-rule: exactly;\">\r\n"
						+ " <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\r\n" + " <tbody>\r\n"
						+ " <tr><center>\r\n" + " </center>\r\n" + "    </tr>\r\n"
						+ " <tr><td style=\"mso-line-height-rule:exactly;padding: 0;font: 15px/25px Avenir, Verdana, sans-serif;color: #ffffff;letter-spacing: 0px;mso-line-height-rule: exactly;\">\r\n"
						+ " <p style=\"margin:0 0 15px\"></p>\r\n" + " <p style=\"margin:0 0 15px \">Hi, </p>\r\n"
						+ " <p style=\"margin:0 0 15px \">Click here to activate your email:</p>\r\n";
				builder.append(hs);
				builder.append(
						"<center><a style=\"padding:2%;color:white;background-color:black;text-decoration:none;margin:2%\" href= "
								+ activationLink
								+ " class='link' width='20%'><span>Activate email </span></a></center><br><br>\r\n"
								+ " or copy and paste the below link in new tab");

				builder.append(" <p style=\"margin:0 0 15px \"><a href= " + activationLink
						+ " class='link' width='20%' style=\"outline:none;color:#0a0a09;text-decoration:underline\">"
						+ activationLink + "</a></p>\r\n"
						+ " <p style=\"margin:0 0 15px \">Thank you! <br>ZEBULL E-Kyc</p>\r\n" + " </td>\r\n"
						+ " </tr></tbody>\r\n" + " </table>\r\n" + " </td>\r\n" + " </tr></tbody>\r\n" + " </table>\r\n"
						+ " \r\n" + "</tr><tr>\r\n"
						+ "<td bgcolor=\"#ffffff\" style=\"mso-line-height-rule:exactly;mso-line-height-rule: exactly;\">\r\n"
						+ "</td>\r\n" + "</tr></tbody></table>\r\n" + "</body></html>");

				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(CSEnvVariables.getProperty(eKYCConstant.FROM)));
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(email.trim()));
				message.setSubject("Email Verification");
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(builder.toString(), "text/html");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				message.setContent(multipart);
				Transport.send(message);
				status = eKYCConstant.SUCCESS_MSG;
			} catch (MessagingException ex) {
				ex.printStackTrace();
				status = eKYCConstant.FAILED_MSG;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public static String mailCheck(String email, String msg) {
		StringBuilder builder = new StringBuilder();
		String status = eKYCConstant.FAILED_MSG;
		try {
			// Get system properties
			Properties properties = new Properties();
			// Setup mail server
			properties.put("mail.smtp.host", CSEnvVariables.getProperty(eKYCConstant.HOST));
			properties.put("mail.smtp.user", CSEnvVariables.getProperty(eKYCConstant.USER_NAME));
			properties.put("mail.smtp.port", CSEnvVariables.getProperty(eKYCConstant.PORT));
			properties.put("mail.smtp.socketFactory.port", CSEnvVariables.getProperty(eKYCConstant.PORT));
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.debug", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.put("mail.smtp.socketFactory.fallback", "false");
			Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(CSEnvVariables.getProperty(eKYCConstant.USER_NAME),
							CSEnvVariables.getProperty(eKYCConstant.PASSWORD));
				}
			});
			try {
				// String hs = msg;
				builder.append(msg);
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(CSEnvVariables.getProperty(eKYCConstant.FROM)));
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(email.trim()));
				message.setSubject("Email Verification");
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(builder.toString(), "text/html");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				message.setContent(multipart);
				Transport.send(message);
				status = eKYCConstant.SUCCESS_MSG;
			} catch (MessagingException ex) {
				ex.printStackTrace();
				status = eKYCConstant.FAILED_MSG;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * @author GOWRI SANKAR R
	 * @param mobileNumber
	 * @param otp
	 */
	public static void sendMessage(long mobileNumber, int otp) {
		try {
			String message = eKYCConstant.MESSAGE_MESSAGE;
			message = message.replace(" ", "%20");
			String tempUrl = CSEnvVariables.getProperty(eKYCConstant.MESSAGE_URL)
					+ CSEnvVariables.getProperty(eKYCConstant.MESSAGE_USERNAME) + "&password="
					+ CSEnvVariables.getProperty(eKYCConstant.MESSAGE_PASSWORD) + "&to=" + mobileNumber + "&text=" + otp
					+ "%20" + message + "&from=" + CSEnvVariables.getProperty(eKYCConstant.MESSAGE_SENDER);
			URL url = new URL(tempUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * @author GOWRI SANKAR R
	 * @param mobileNumber
	 * @param otp
	 */
	public static void sendIPVLink(long mobileNumber, String bitlyUrl, String applicantName) {
		try {
			String newline = System.getProperty("line.separator");
			String message = "Dear " + applicantName + ", Your eKYC Application IPV is pending. Kindly click here "
					+ bitlyUrl + " to take this forward and complete your IPV.";
			message = message.replace(" ", "%20");
			String tempUrl = CSEnvVariables.getProperty(eKYCConstant.MESSAGE_URL)
					+ CSEnvVariables.getProperty(eKYCConstant.MESSAGE_USERNAME) + "&password="
					+ CSEnvVariables.getProperty(eKYCConstant.MESSAGE_PASSWORD) + "&to=" + mobileNumber + "&text="
					+ message + "&from=" + CSEnvVariables.getProperty(eKYCConstant.MESSAGE_SENDER);
			URL url = new URL(tempUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * create the json object when the response code is one
	 * 
	 * @author GOWRI SANKAR R
	 * @param nsdlResponse
	 * @return
	 */
	public static JSONObject stringToJson(String nsdlResponse) {
		JSONObject response = new JSONObject();
		String[] resp = null;
		if (nsdlResponse.lastIndexOf("^") == nsdlResponse.length() - 1) {
			resp = nsdlResponse.split("\\^");
		} else {
			resp = nsdlResponse.split("\\^");
		}
		System.out.println(resp.length);
		if (resp.length > 3) {
			response.put("responseCode", resp[0]);
			if (resp.length > 1) {
				response.put("panCard", resp[1]);
			}
			if (resp.length > 2) {
				response.put("panCardStatus", resp[2]);
			}
			if (resp.length > 3) {
				response.put("lastName", resp[3]);
			}
			if (resp.length > 4) {
				response.put("firstName", resp[4]);
			}
			if (resp.length > 5) {
				response.put("middleName", resp[5]);
			}
			if (resp.length > 6) {
				response.put("panTittle", resp[6]);
			}
			if (resp.length > 7) {
				response.put("lastUpdatedDate", resp[7]);
			}
			if (resp.length > 8) {
				response.put("nameOnCard", resp[8]);
			}
			if (resp.length > 9) {
				response.put("aadhaar seeding status", resp[9]);
			}
		} else {
			response.put("responseCode", resp[0]);
			response.put("panCard", resp[1]);
			response.put("panCardStatus", resp[2]);
		}
		return response;
	}

	/**
	 * To get the XML code for getting esign
	 * 
	 * @author GOWRI SANKAR R
	 * @return
	 */
	public static String getXmlForEsign(int applicationId, String folderName) {
		String response = "";
		try {
			String pathToPDF = folderName;
			String aspID = CSEnvVariables.getProperty(eKYCConstant.E_SIGN_ASP_ID);
			String authMode = "1";
			String responseUrl = "https://oa2.zebull.in/eKYCService/eKYC/getNsdlXML";
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

	public static String convertBase64ToImage(String base64, String location, int applicationId) {
		String siteUrl = "";
		String base64String = base64;
		String[] strings = base64String.split(",");
		String extension;
		switch (strings[0]) {// check image's extension
		case "data:image/jpeg;base64":
			extension = "jpeg";
			break;
		case "data:image/png;base64":
			extension = "png";
			break;
		default:// should write cases for more images types
			extension = "jpg";
			break;
		}
		// convert base64 string to binary data
		byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
		String path = location + "//" + applicationId + "_ivrImage." + extension;
		File file = new File(path);
		File foldercheck = new File(location);
		if (!foldercheck.exists()) {
			foldercheck.mkdirs();
		}
		try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
			outputStream.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		siteUrl = eKYCConstant.SITE_URL_FILE + eKYCConstant.UPLOADS_DIR + applicationId + "//IVR_IMAGE//"
				+ applicationId + "_ivrImage." + extension;
		return siteUrl;
	}

	/**
	 * Method to insert the access log into the data base
	 * 
	 * @author GOWRI SANKAR R
	 * @param accessLogDto
	 * @param pObj
	 * @param userId
	 */
	public static void inputAccessLogDetails(AccesslogDTO accessLogDto, Object pObj, String userId) {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 100, 1, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
		executor.execute(new Runnable() {
			@Override
			public void run() {
				AccesslogDTO accessLog = new AccesslogDTO();
				ObjectMapper mapper = new ObjectMapper();
				String convert = "";
				try {
					convert = mapper.writeValueAsString(pObj);
					accessLog.setInput(convert);
					accessLog.setUri(accessLogDto.getUri());
					accessLog.setDevice_ip(accessLogDto.getDevice_ip());
					accessLog.setUser_id(userId);
					accessLog.setCreated_on(accessLogDto.getCreated_on());
					accessLog.setUser_agent(accessLogDto.getUser_agent());
					accessLog.setDomain(accessLogDto.getDomain());
					AccessLogService logService = new AccessLogService();
					logService.insertAccessLogInputRecords(accessLog);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					executor.shutdown();
				}
			}
		});
	}

	public static long getExpirySeconds() {
		long timeNow = System.currentTimeMillis();
		long expiry = timeNow + Integer.parseInt(CSEnvVariables.getProperty(eKYCConstant.EXPIRY_TIME));
		return expiry;
	}

	/**
	 * Create and store the token into the cache and set the validation for 30
	 * minutues
	 */
	public static String createAndStoreSeesionInCache(String mobileNumber) {
		String sessionId = randomAlphaNumericNew(256);
		TokenAuthModule.storeToken(mobileNumber, sessionId);
		long timeNow = System.currentTimeMillis();
		CacheController.getKeyTimeMap().put(sessionId, timeNow);
		return sessionId;
	}

	/**
	 * Method to copy one folder to another folder
	 * 
	 * @author GOWRI SANKAR R
	 * @param sourceLocation
	 * @param desinitionLocation
	 */
	public static void exampleDocumentToNewUser(String sourceLocation, String desinitionLocation) {
		File source = new File(sourceLocation);
		File dest = new File(desinitionLocation);
		try {
			FileUtils.copyDirectory(source, dest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * To create the new xml document and write the response Xml from the NSDL
	 * 
	 * @author GOWRI SANKAR R
	 * @param xmlPath
	 * @param xmlTowrite
	 */
	public static void createNewXmlFile(String xmlPath, String xmlTowrite) {
		try {
			File myObj = new File(xmlPath + "\\" + "FirstResponse.xml");
			if (myObj.createNewFile()) {
				FileWriter myWriter = new FileWriter(xmlPath + "\\" + "FirstResponse.xml");
				myWriter.write(xmlTowrite);
				myWriter.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * To get the TXN id from the XMl
	 * 
	 * @param xmlPath
	 * @param xmlTowrite
	 * @return
	 */
	public static String toGetTxnFromXMlpath(String xmlPath) {
		String txnId = "";
		try {
			File fXmlFile = new File(xmlPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			Element eElement = doc.getDocumentElement();
			txnId = eElement.getAttribute("txn");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txnId;
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
	public static String getSignFromNsdl(String dcoumentLocation, String documentToBeSavedLocation, String receivedXml,
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

	/**
	 * method to send the application approve mail to the application registered
	 * Email
	 * 
	 * @author GOWRI SANKAR R
	 * @param email
	 * @param applicantName
	 * @return
	 */
	public static String applicationApprovedMessage(String email, String applicantName) {
		StringBuilder builder = new StringBuilder();
		String status = eKYCConstant.FAILED_MSG;
		try {
			// Get system properties
			Properties properties = new Properties();
			// Setup mail server
			properties.put("mail.smtp.host", CSEnvVariables.getProperty(eKYCConstant.HOST));
			properties.put("mail.smtp.user", CSEnvVariables.getProperty(eKYCConstant.USER_NAME));
			properties.put("mail.smtp.port", CSEnvVariables.getProperty(eKYCConstant.PORT));
			properties.put("mail.smtp.socketFactory.port", CSEnvVariables.getProperty(eKYCConstant.PORT));
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.debug", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.put("mail.smtp.socketFactory.fallback", "false");
			Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(CSEnvVariables.getProperty(eKYCConstant.USER_NAME),
							CSEnvVariables.getProperty(eKYCConstant.PASSWORD));
				}
			});
			try {
				String hs = "<!DOCTYPE html><html><head><style>*{font-family:'Open Sans',"
						+ " Helvetica, Arial;color: #1e3465}table {margin-left:100px;font-family: arial, sans-serif;border-collapse:"
						+ " separate;}td, th {border: 1px solid #1e3465;text-align: left;padding: 8px;}"
						+ "th{background :#1e3465;color:white;}</style></head><body><div>"
						+ "<div  style='font-size:14px'><p><b>Hi " + applicantName
						+ ",</b></p><br><p>Your application information and documents are successfully verified and accepted. "
						+ "You will receive an email with your account details shortly with login credentials. </p>"
						+ "<br><p>Thank you for choosing Zebu Etrade and we wish you all the best </p></div>"
						+ "<div><p align='left'>" + "<b>Regards,"
						+ "<br>Zebu E-Trade Services.</b></p></div></div></body></html>";
				builder.append(hs);
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(CSEnvVariables.getProperty(eKYCConstant.FROM)));
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(email.trim()));
				message.setSubject("Zebull E-Kyc Application status");
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(builder.toString(), "text/html");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				message.setContent(multipart);
				Transport.send(message);
				status = eKYCConstant.SUCCESS_MSG;
			} catch (MessagingException ex) {
				ex.printStackTrace();
				status = eKYCConstant.FAILED_MSG;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public static String applicationRejectedMessage(String email, String applicantName,
			List<ApplicationLogDTO> rejectedDoumnets) {
		StringBuilder builder = new StringBuilder();
		StringBuilder documentsBuilder = new StringBuilder();
		String status = eKYCConstant.FAILED_MSG;
		ApplicationLogDTO tempDto = null;
		String tempString = null;
		try {
			// Get system properties
			Properties properties = new Properties();
			// Setup mail server
			properties.put("mail.smtp.host", CSEnvVariables.getProperty(eKYCConstant.HOST));
			properties.put("mail.smtp.user", CSEnvVariables.getProperty(eKYCConstant.USER_NAME));
			properties.put("mail.smtp.port", CSEnvVariables.getProperty(eKYCConstant.PORT));
			properties.put("mail.smtp.socketFactory.port", CSEnvVariables.getProperty(eKYCConstant.PORT));
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.debug", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.put("mail.smtp.socketFactory.fallback", "false");
			Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(CSEnvVariables.getProperty(eKYCConstant.USER_NAME),
							CSEnvVariables.getProperty(eKYCConstant.PASSWORD));
				}
			});
			try {
				for (int itr = 0; itr < rejectedDoumnets.size(); itr++) {
					String newline = System.getProperty("line.separator");
					tempDto = new ApplicationLogDTO();
					tempDto = rejectedDoumnets.get(itr);
					tempString = "<p>" + tempDto.getVerification_module() + " : " + tempDto.getNotes() + newline
							+ "</p>";
					documentsBuilder.append(tempString);
				}
				String hs = "<!DOCTYPE html><html><head><style>*{font-family:'Open Sans',"
						+ " Helvetica, Arial;color: #1e3465}table {margin-left:100px;font-family: arial, sans-serif;border-collapse:"
						+ " separate;}td, th {border: 1px solid #1e3465;text-align: left;padding: 8px;}"
						+ "th{background :#1e3465;color:white;}</style></head><body><div>"
						+ "<div  style='font-size:14px'><p><b>Hi " + applicantName
						+ ",</b> <p>Your application information and documents "
						+ "are verified and the team has found some discrepancies or issues. Please find below the "
						+ "notes from accounts team. </p> <b> " + documentsBuilder + "</b><br> "
						+ "<p>We request you to take the remedial action using the the below link. Please reach out to our support team on 9XXXXXXXX for any further information</p></div>"
						+ "<div><p align='left'>" + "<b>Regards,"
						+ "<br>Zebu E-Trade Services.</b></p></div></div></body></html>";
				builder.append(hs);
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(CSEnvVariables.getProperty(eKYCConstant.FROM)));
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(email.trim()));
				message.setSubject("Zebull E-Kyc Application status");
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(builder.toString(), "text/html");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				message.setContent(multipart);
				Transport.send(message);
				status = eKYCConstant.SUCCESS_MSG;
			} catch (MessagingException ex) {
				ex.printStackTrace();
				status = eKYCConstant.FAILED_MSG;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * Creating the MD5 text for the Password
	 * 
	 * @param passkey
	 * @return
	 */
	public static String PasswordEncryption(String passkey) {
		String MD5pass = "";
		final byte[] defaultBytes = passkey.getBytes();
		try {
			final MessageDigest md5MsgDigest = MessageDigest.getInstance("MD5");
			md5MsgDigest.reset();
			md5MsgDigest.update(defaultBytes);
			final byte messageDigest[] = md5MsgDigest.digest();

			final StringBuffer hexString = new StringBuffer();
			for (final byte element : messageDigest) {
				final String hex = Integer.toHexString(0xFF & element);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			passkey = hexString + "";
		} catch (final NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		}
		MD5pass = passkey;
		return MD5pass;
	}

	public static Map<String, String> annualIncomeMap() {
		Map<String, String> incomeMap = new HashMap<String, String>();
		incomeMap.put("below 1 lakh", "90,000");
		incomeMap.put("1l-5l", "4,00,000");
		incomeMap.put("5l-10l", "9,00,000");
		incomeMap.put("10l-25l", "24,00,000");
		incomeMap.put("above 25l", "30,00,000");
		return incomeMap;
	}

	/**
	 * Method to send the mail update to complete the IVR
	 * 
	 * @author GOWRI SANKAR R
	 * @param msg
	 * @param email
	 * @return
	 */
	public static String ivpMailUpdate(String msg, String email) {
		StringBuilder builder = new StringBuilder();
		String success = eKYCConstant.FAILED_MSG;
		try {
			Properties properties = new Properties();
			properties.put("mail.smtp.host", CSEnvVariables.getProperty(eKYCConstant.HOST));
			properties.put("mail.smtp.user", CSEnvVariables.getProperty(eKYCConstant.USER_NAME));
			properties.put("mail.smtp.port", CSEnvVariables.getProperty(eKYCConstant.PORT));
			properties.put("mail.smtp.socketFactory.port", CSEnvVariables.getProperty(eKYCConstant.PORT));
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.debug", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.put("mail.smtp.socketFactory.fallback", "false");
			Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(CSEnvVariables.getProperty(eKYCConstant.USER_NAME),
							CSEnvVariables.getProperty(eKYCConstant.PASSWORD));
				}
			});
			try {
				String hs = "<!DOCTYPE html><html><head><style>*{font-family:'Open Sans',"
						+ " Helvetica, Arial;color: #1e3465}table {margin-left:100px;font-family: arial, sans-serif;border-collapse:"
						+ " separate;}td, th {border: 1px solid #1e3465;text-align: left;padding: 8px;}"
						+ "th{background :#1e3465;color:white;}</style></head><body><div>"
						+ "<div  style='font-size:14px'><p>Hi User,</p><p>Please use this link to complete the IVR from any other device. Link will be valid for 30 min</p>"
						+ "<p> " + msg + " </p></div>" + "<div><p align='left'>" + "<b>Regards,"
						+ "<br>Zebu E-Trade Services.</b></p></div></div></body></html>";
				builder.append(hs);
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(CSEnvVariables.getProperty(eKYCConstant.FROM)));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				message.setSubject("Message From Zebu eTrade");
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(builder.toString(), "text/html");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				message.setContent(multipart);
				Transport.send(message);
				success = eKYCConstant.SUCCESS_MSG;
			} catch (MessagingException ex) {
				ex.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * 
	 * Method to get the time taken
	 * 
	 * @author GOWRI SANKAR R
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static String getTimeTaken(String startTime, String endTime) {
		String response = "";
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

		Date d1 = null;
		Date d2 = null;
		try {
			d1 = format.parse(startTime);
			d2 = format.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Get msec from each, and subtract.
		long diff = d2.getTime() - d1.getTime();
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000);
		long diffDays = diff / (60 * 60 * 24 * 1000);
		response = diffDays + " Days " + diffHours + " Hours " + diffMinutes + " Minutes";
		return response;
	}

	/**
	 * Method to send the mail update to complete the IVR
	 * 
	 * @author GOWRI SANKAR R
	 * @param msg
	 * @param email
	 * @return
	 */
	public static String sendOTPtoEmail(String otp, String email) {
		StringBuilder builder = new StringBuilder();
		String success = eKYCConstant.FAILED_MSG;
		try {
			Properties properties = new Properties();
			properties.put("mail.smtp.host", CSEnvVariables.getProperty(eKYCConstant.HOST));
			properties.put("mail.smtp.user", CSEnvVariables.getProperty(eKYCConstant.USER_NAME));
			properties.put("mail.smtp.port", CSEnvVariables.getProperty(eKYCConstant.PORT));
			properties.put("mail.smtp.socketFactory.port", CSEnvVariables.getProperty(eKYCConstant.PORT));
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.debug", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.put("mail.smtp.socketFactory.fallback", "false");
			Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(CSEnvVariables.getProperty(eKYCConstant.USER_NAME),
							CSEnvVariables.getProperty(eKYCConstant.PASSWORD));
				}
			});
			try {
				String hs = "<!DOCTYPE html><html><head><style>*{font-family:'Open Sans',"
						+ " Helvetica, Arial;color: #1e3465}table {margin-left:100px;font-family: arial, sans-serif;border-collapse:"
						+ " separate;}td, th {border: 1px solid #1e3465;text-align: left;padding: 8px;}"
						+ "th{background :#1e3465;color:white;}</style></head><body><div>"
						+ "<div  style='font-size:14px'><p>Hi User,</p><p>Your verification OTP for resume application is </p>"
						+ "<p> " + otp + " </p></div>" + "<div><p align='left'>" + "<b>Regards,"
						+ "<br>Zebu E-Trade Services.</b></p></div></div></body></html>";
				builder.append(hs);
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(CSEnvVariables.getProperty(eKYCConstant.FROM)));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				message.setSubject("Message From Zebu eTrade");
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(builder.toString(), "text/html");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				message.setContent(multipart);
				Transport.send(message);
				success = eKYCConstant.SUCCESS_MSG;
			} catch (MessagingException ex) {
				ex.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * Method to convert the image to base64
	 * 
	 * @author GOWRI SANKAR
	 * @param imageUrl
	 * @return
	 */
	public static String imageToBase64(String imageUrl) {
		String base64Image = "";
		try {
			String replacedURL = StringUtil.replace(imageUrl, " ", "%20");
			InputStream in = new URL(replacedURL).openStream();
			BufferedImage bimg = ImageIO.read(in);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(bimg, "png", bos);
			BASE64Encoder encoder = new BASE64Encoder();
			base64Image = encoder.encode(bos.toByteArray());
			// System.out.print(base64Image);
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return base64Image;
	}
}