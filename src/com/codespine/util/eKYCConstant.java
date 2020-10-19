package com.codespine.util;

public class eKYCConstant {

	public static final int FAILED_STATUS = 0;
	public static final int SUCCESS_STATUS = 1;

	public static final String FAILED_MSG = "Failed";
	public static final String SUCCESS_MSG = "Success";
	public static final String INTERNAL_SERVER_ERROR = "Something went wrong. please try again after some time";

	public static final String OTP_VERIFIED_SUCCESS = "Otp verified sucessfully";
	public static final String OTP_VERIFIED_FAILED = "Please enter the valid otp";

	public static final String ACTIVATIONLINK = "activateEmail";
	public static final String REDIRECT_PAGE = "redirect_page";
	public static final String OTP_SENT_SUCESSFULLY = "OTP sent to registered mobile number ";
	/**
	 * New User sucess and failure message
	 */
	public static final String NEW_USER_SUCESS = "Please verify OTP and email";
	public static final String APPLICATION_ID_ERROR = "Application id cannot be zero";

	/**
	 * To send email
	 * 
	 */

	public static final String HOST = "host";
	public static final String USER_NAME = "username";
	public static final String PORT = "port";
	public static final String PASSWORD = "password";
	public static final String FROM = "from";

	public static final String PFX_FILE_LOCATION = "pfxLocation";
	public static final String PFX_FILE_USERNAME = "pfxUserId";
	public static final String PFX_FILE_PASSWORD = "pfxPassword";
	public static final String E_SIGN_ASP_ID = "aspId";
	public static final String E_SIGN_ALIAS = "e_sign_alias";
	public static final String E_SIGN_TICK_IMAGE = "e_sign_tick_image";
	public static final String USER_JKS_LOCATION = "userJksLocation";

	public static final String NSDL_PAN_VERIFICATION_URL = "nsdlPanVerification";
	public static final String NSDL_PAN_VERIFICATION_VERSION = "nsdlVersion";

	public static final String BASIC_INFORMATION_SAVED_SUCESSFULLY = "Given basic information saved sucessfully";

	public static final String ADDRESS_SAVED_SUCESSFULLY = "Address saved sucessfully";
	public static final String BANK_DETAILS_SAVED = "Bank Deatails saved Sucessfully";
	public static final String PAN_CARD_DETAILS_SAVED = "Pan Card Details saved Sucessfully";
	public static final String PDF_GENERATED_SUCESSFULLY = "PDF Generated Sucessfully";
	public static final String PDF_GENERATED_FAILED = "PDF Generated Failed";

	public static final String EMAIL_ID_UPDATED_SUCESSFULLY = "Email updated sucessfully";

	public static final String INVALID_PANCARD = "Your pan Card is invalid";
	public static final String EXCH_DETAILS_UPDATED_SUCESSFULLY = "Exch deatils are updated sucessfully";

	public static final String PROOF_UPLOADED_SUCESSFULLY = "Proof uploaded Sucessfully";
	public static final String FILE_CANNOT_BE_EMPTY = "File cannot be empty";

	public static final String IVR_DETAILS_UPDATED_SUCESSFULLY = "Ivr Details updated sucessfully";

	/**
	 * Application Status
	 */
	public static final int OTP_NOT_VERIFIED = 1;
	public static final int OTP_VERIFIED = 2;
	public static final int PAN_CARD_UPDATED = 3;
	public static final int BASIC_DETAILS_UPDATED = 4;
	public static final int COMMUNICATION_ADDRESS_UPDATED = 5;
	public static final int PERMANENT_ADDRESS_UPDATED = 6;
	public static final int BANK_DETAILS_UPDATED = 7;
	public static final int EXCH_UPDATED = 8;
	public static final int ATTACHEMENT_UPLOADED = 9;

	public static final String MESSAGE_URL = "messageURl";
	public static final String MESSAGE_USERNAME = "messageUserName";
	public static final String MESSAGE_PASSWORD = "messagePassword";
	public static final String MESSAGE_SENDER = "messageFrom";
	public static final String MESSAGE_MESSAGE = " is your verification OTP for sign up, This OTP will be valid for next 30 mins";

	public static final String PROJ_DIR = "C://apache-tomcat-8.5.35//webapps//e_sign//";
	public static final String SITE_URL_FILE = "http://rest.irongates.in//e_sign/";
	public static final String UPLOADS_DIR = "file//uploads//";

	public static final String FILE_UPLOAD_SUCESSFULL = "Document uploaded Sucessfully";
	public static final String STRING = "STRING";
	public static final String IMAGE = "IMG";
	public static final String TICK = "TICK";
	public static final String COMMA_SEPERATOR = ",";
	public static final String IMAGE_FILEPATH = "C:\\Users\\user\\Downloads\\";

	public static final String FILE_PATH = "TESTING_FILE_PATH";
	public static final String CONSTANT_PDF_NAME = "CONSTANT_PDF_NAME";
	public static final String GENERATED_PDF_NAME = "GENERATED_PDF_NAME";
	public static final String JPG_FILE_EXTENSION = ".jpg";
	public static final String PDF_FILE_EXTENSION = ".pdf";
	public static final String WINDOWS_FORMAT_SLASH = "//";
	public static final String EKYC_DOCUMENT = "EKYC_DOCUMENT";

	public static final String EXPIRY_TIME = "expiryInMilleseconds";

	/**
	 * 
	 */
	public static final String SECURED_METHODS = "securedMethods";
	public static final String UNAUTHORIZED = "Unauthorized";

	public static final String FILE_PATH_NEWDOCUMENT = "filePathFirstXml";
	public static final String FILE_PATH_EXAMPLE_DOCUMENT = "exampleDocumentFilePath";
	public static final String TEMP_FILE_XML_DOCUMENTS = "tempXMLFiles";
	
	public static final String DOCUMENT_FILE_NAEM = "constantPDFName";
}