package com.codespine.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.codespine.data.eKYCDAO;
import com.codespine.dto.AddressDTO;
import com.codespine.dto.ApplicationMasterDTO;
import com.codespine.dto.BankDetailsDTO;
import com.codespine.dto.ExchDetailsDTO;
import com.codespine.dto.FileUploadDTO;
import com.codespine.dto.PanCardDetailsDTO;
import com.codespine.dto.PersonalDetailsDTO;
import com.codespine.dto.ResponseDTO;
import com.codespine.dto.eKYCDTO;
import com.codespine.dto.esignDTO;
import com.codespine.helper.eKYCHelper;
import com.codespine.restservice.NsdlPanVerificationRestService;
import com.codespine.util.CSEnvVariables;
import com.codespine.util.FinalPDFGenerator;
import com.codespine.util.StringUtil;
import com.codespine.util.Utility;
import com.codespine.util.eKYCConstant;

@SuppressWarnings("unchecked")
public class eKYCService {
	public static eKYCService eKYCService = null;

	public static eKYCService getInstance() {
		if (eKYCService == null) {
			eKYCService = new eKYCService();
		}
		return eKYCService;
	}

	eKYCDAO peKYCDao = new eKYCDAO();

	/**
	 * To check the given mobile and email number in the data base
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO newRegistration(PersonalDetailsDTO pDto) {
		PersonalDetailsDTO result = null;
		ResponseDTO response = new ResponseDTO();
		PersonalDetailsDTO checkUser = peKYCDao.checkExistingUser(pDto);
		if (checkUser != null && checkUser.getApplication_id() > 0) {
			/**
			 * Send Otp to the given mobile number
			 */
			String otp = Utility.generateOTP();
			peKYCDao.updateOtpForApplicationId(Integer.parseInt(otp), checkUser.getApplication_id());
			Utility.sendMessage(checkUser.getMobile_number(), Integer.parseInt(otp));

			result = new PersonalDetailsDTO();
			result.setApplication_id(checkUser.getApplication_id());
			result.setApplicationStatus(checkUser.getApplicationStatus());
			/**
			 * Check the user given the same email id or not
			 */
			if (checkUser.getEmail().equalsIgnoreCase(pDto.getEmail())) {
				/**
				 * Check the otp and email is verified
				 */
				if (checkUser.getMobile_number_verified() == 0) {
					if (checkUser.getApplicationStatus() >= 3) {
						PanCardDetailsDTO panCardName = peKYCDao.getApplicantName(checkUser.getApplication_id());
						result.setApplicant_name(panCardName.getApplicant_name());
						result.setFathersName(panCardName.getFathersName());
					}
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setReason(eKYCConstant.OTP_SENT_SUCESSFULLY);
					response.setResult(result);
				} else {
					if (checkUser.getApplicationStatus() >= 3) {
						PanCardDetailsDTO panCardName = peKYCDao.getApplicantName(checkUser.getApplication_id());
						result.setApplicant_name(panCardName.getApplicant_name());
						result.setFathersName(panCardName.getFathersName());
					}
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setResult(result);
				}
			} else {
				result.setApplicationStatus(0);
				result.setApplication_id(0);
				result.setEmail(checkUser.getEmail());
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setResult(result);
			}
		} else {
			result = new PersonalDetailsDTO();
			/**
			 * if there is no result create the new application with given
			 * mobile number and email
			 */
			String otp = Utility.generateOTP();
			pDto.setOtp(Integer.parseInt(otp));
			int newApplicationId = peKYCDao.createNewApplication(pDto);
			if (newApplicationId != 0 && newApplicationId > 0) {
				result.setApplication_id(newApplicationId);
				Utility.sendMessage(pDto.getMobile_number(), Integer.parseInt(otp));
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setReason(eKYCConstant.NEW_USER_SUCESS);
				response.setResult(result);
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
			}
		}
		return response;
	}

	/**
	 * Method to verify the OTP
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO verifyOtp(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		PersonalDetailsDTO checkUser = peKYCDao.checkExistingUser(pDto);
		if (checkUser != null) {
			if (pDto.getOtp() == checkUser.getOtp()) {
				peKYCDao.updateOtpVerified(pDto);
				peKYCDao.updateApplicationStatus(checkUser.getApplication_id(), eKYCConstant.OTP_VERIFIED);

				/**
				 * Create the Seesion id for the and keep the session valid for
				 * the another 30 mins
				 */
				String sessionId = Utility.createAndStoreSeesionInCache(pDto.getMobile_number() + "");
				JSONObject result = new JSONObject();
				result.put("authKey", sessionId);

				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setReason(eKYCConstant.OTP_VERIFIED_SUCCESS);
				response.setResult(result);
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.OTP_VERIFIED_FAILED);
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	/**
	 * To verify the user by activating email
	 * 
	 * @author GOWRI SANKAR R
	 * @param email
	 * @param link
	 * @return
	 */
	public String verifyActivationLink(String email, String link) {
		String success = peKYCDao.verifyActivationLink(email, link);
		return success;
	}

	/**
	 * To verify the pan card Details
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO verifyPan(PanCardDetailsDTO pDto) {
		PanCardDetailsDTO dummResult = new PanCardDetailsDTO();
		ResponseDTO response = new ResponseDTO();
		if (pDto.getApplication_id() > 0) {
			/**
			 * To create the create the jks file for the given application id
			 */
			NsdlPanVerificationRestService.pfx2JksFile(pDto.getApplication_id());
			/**
			 * To create the sig file from the jks file
			 */
			NsdlPanVerificationRestService.pkcs7Generate(pDto.getApplication_id(), pDto.getPan_card());
			/**
			 * TO get the result from the NSDL
			 */
			String result = NsdlPanVerificationRestService.apiCallForPanVerififcation(pDto.getApplication_id(),
					pDto.getPan_card());
			if (result != null && !result.equalsIgnoreCase("")) {
				/**
				 * Change the String response to json Response
				 */
				JSONObject tempResult = Utility.stringToJson(result);
				String panStatus = (String) tempResult.get("panCardStatus");
				if (panStatus != null && !panStatus.equalsIgnoreCase(" ") && panStatus.equalsIgnoreCase("E")) {
					String firstName = (String) tempResult.get("firstName");
					String middleName = (String) tempResult.get("middleName");
					String lastName = (String) tempResult.get("lastName");
					String panCardname = firstName + " " + middleName + " " + lastName;
					// String fatherName = (String) tempResult.get("lastName");
					dummResult.setApplicant_name(panCardname);
					// dummResult.setFathersName(fatherName);
					pDto.setApplicant_name(panCardname);
					// pDto.setFathersName(fatherName);
					ResponseDTO topResult = new ResponseDTO();
					topResult = updatePanCard(pDto);
					if (topResult.getStatus() == eKYCConstant.SUCCESS_STATUS) {
						response.setStatus(eKYCConstant.SUCCESS_STATUS);
						response.setMessage(eKYCConstant.SUCCESS_MSG);
						response.setReason(eKYCConstant.PAN_CARD_DETAILS_SAVED);
						response.setResult(dummResult);
					} else {
						response.setStatus(eKYCConstant.FAILED_STATUS);
						response.setMessage(eKYCConstant.FAILED_MSG);
						response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
					}
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.INVALID_PANCARD);
				}
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
		}
		return response;
	}

	/**
	 * Update pan card Details
	 */
	public ResponseDTO updatePanCard(PanCardDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		if (pDto.getApplication_id() > 0) {
			int insertCount = peKYCDao.insertPanCardDetails(pDto);
			if (insertCount > 0) {
				peKYCDao.updateApplicationStatus(pDto.getApplication_id(), eKYCConstant.PAN_CARD_UPDATED);
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setReason(eKYCConstant.OTP_VERIFIED_SUCCESS);
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
		}
		return response;
	}

	/**
	 * To save the basic information for the user by the given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO basicInformation(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		/**
		 * Insert the basic information for thr given user
		 */
		if (pDto.getApplication_id() > 0) {
			int insertCount = peKYCDao.insertBasicInformation(pDto);
			if (insertCount > 0) {
				peKYCDao.updateApplicationStatus(pDto.getApplication_id(), eKYCConstant.BASIC_DETAILS_UPDATED);
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setReason(eKYCConstant.BASIC_INFORMATION_SAVED_SUCESSFULLY);
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
		}
		return response;
	}

	/**
	 * To update the both communication and premanent address
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO updateAddress(AddressDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		ResponseDTO result = null;
		if (pDto.getApplication_id() > 0) {
			result = new ResponseDTO();
			result = updateCommunicationAddress(pDto);
			if (result.getStatus() == eKYCConstant.SUCCESS_STATUS) {
				result = updatePermanentAddress(pDto);
				if (result.getStatus() == eKYCConstant.SUCCESS_STATUS) {
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setReason(eKYCConstant.ADDRESS_SAVED_SUCESSFULLY);
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
				}
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
		}
		return response;
	}

	/**
	 * To update the Communication Address
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO updateCommunicationAddress(AddressDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		if (pDto.getApplication_id() > 0) {
			int insertCommunicationCount = eKYCDAO.insertCommunicationAddress(pDto);
			if (insertCommunicationCount > 0) {
				peKYCDao.updateApplicationStatus(pDto.getApplication_id(), eKYCConstant.COMMUNICATION_ADDRESS_UPDATED);
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setReason(eKYCConstant.ADDRESS_SAVED_SUCESSFULLY);
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
		}
		return response;
	}

	/**
	 * To update the permanent address
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO updatePermanentAddress(AddressDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		if (pDto.getApplication_id() > 0) {
			int premanentCount = eKYCDAO.insertPermanentAddress(pDto);
			if (premanentCount > 0) {
				peKYCDao.updateApplicationStatus(pDto.getApplication_id(), eKYCConstant.PERMANENT_ADDRESS_UPDATED);
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setReason(eKYCConstant.ADDRESS_SAVED_SUCESSFULLY);
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
		}
		return response;
	}

	/**
	 * To update the bank Account Details
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO updateBankAccountDetails(BankDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		if (pDto.getApplication_id() > 0) {
			int insertCount = peKYCDao.insertBankAccountDetails(pDto);
			if (insertCount > 0) {
				peKYCDao.updateApplicationStatus(pDto.getApplication_id(), eKYCConstant.BANK_DETAILS_UPDATED);
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setReason(eKYCConstant.BANK_DETAILS_SAVED);
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
		}
		return response;
	}

	/**
	 * To update the email for the given mobilenumber
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO updateEmail(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		boolean isSuccessfull = peKYCDao.updateEmail(pDto);
		if (isSuccessfull) {
			response.setStatus(eKYCConstant.SUCCESS_STATUS);
			response.setMessage(eKYCConstant.SUCCESS_MSG);
			response.setReason(eKYCConstant.EMAIL_ID_UPDATED_SUCESSFULLY);
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	/**
	 * Mark the application as the deleted
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO deleteOldOne(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		boolean isSuccessfull = peKYCDao.deleteOldOne(pDto);
		if (isSuccessfull) {
			response.setStatus(eKYCConstant.SUCCESS_STATUS);
			response.setMessage(eKYCConstant.SUCCESS_MSG);
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	/**
	 * TO update the exch details forthr given apllication id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO updateExchDetails(ExchDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		if (pDto.getApplication_id() > 0) {
			ExchDetailsDTO checkExchUpdated = peKYCDao.checkExchUpdatedStatus(pDto.getApplication_id());
			if (checkExchUpdated != null && checkExchUpdated.getApplication_id() > 0) {
				boolean isSucessfull = peKYCDao.updateExchDetails(pDto);
				if (isSucessfull) {
					peKYCDao.updateApplicationStatus(pDto.getApplication_id(), eKYCConstant.EXCH_UPDATED);
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setReason(eKYCConstant.EXCH_DETAILS_UPDATED_SUCESSFULLY);
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
				}
			} else {
				int insertCount = peKYCDao.insertExchDetails(pDto);
				if (insertCount > 0) {
					peKYCDao.updateApplicationStatus(pDto.getApplication_id(), eKYCConstant.EXCH_UPDATED);
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setReason(eKYCConstant.EXCH_DETAILS_UPDATED_SUCESSFULLY);
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
				}
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
		}
		return response;
	}

	/**
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 * @throws Exception
	 */
	public ResponseDTO getXmlEncode(PersonalDetailsDTO pDto) throws Exception {
		ResponseDTO response = new ResponseDTO();
		String filePath = null;
		if (pDto.getApplication_id() > 0) {
			PersonalDetailsDTO result = new PersonalDetailsDTO();
			/**
			 * Create the folder name
			 */
			long timeInmillsecods = System.currentTimeMillis();
			String folderName = String.valueOf(timeInmillsecods);
			// /**
			// * Create the folder name and mov ethe ekyc document to the folder
			// * for the Esign
			// */
			// String filePath =
			// CSEnvVariables.getProperty(eKYCConstant.FILE_PATH_NEWDOCUMENT) +
			// pDto.getApplication_id()
			// + "\\" + folderName;
			//
			// File dir = new File(filePath);
			// if (!dir.exists()) {
			// dir.mkdirs();
			// }
			eKYCDTO eKYCdto = finalPDFGenerator(pDto.getApplication_id());
			if (eKYCdto != null) {
				filePath = FinalPDFGenerator.pdfInserterRequiredValues(eKYCdto, folderName);
				if (StringUtil.isNotNullOrEmpty(filePath)) {
					int checkId = eKYCDAO.getInstance().checkFileUploaded(pDto.getApplication_id(),
							eKYCConstant.EKYC_DOCUMENT);
					if (checkId > 0) {
						eKYCDAO.getInstance().updateAttachementDetails(
								eKYCConstant.SITE_URL_FILE + eKYCConstant.UPLOADS_DIR + filePath,
								eKYCConstant.EKYC_DOCUMENT, pDto.getApplication_id());
					} else {
						eKYCDAO.getInstance().insertAttachementDetails(
								eKYCConstant.SITE_URL_FILE + eKYCConstant.UPLOADS_DIR + filePath,
								eKYCConstant.EKYC_DOCUMENT, pDto.getApplication_id());
					}
				}
			}
			/**
			 * Copy the example document to the given Application id location
			 */
			// Utility.exampleDocumentToNewUser(CSEnvVariables.getProperty(eKYCConstant.FILE_PATH_EXAMPLE_DOCUMENT),
			// filePath);
			/**
			 * Call to NSDL for getting the xml form the NSDL
			 */
			String getXml = Utility.getXmlForEsign(pDto.getApplication_id(),
					eKYCDAO.getInstance().getFileLocation(eKYCConstant.FILE_PATH) + filePath);
			Utility.createNewXmlFile(eKYCDAO.getInstance().getFileLocation(eKYCConstant.FILE_PATH)
					+ pDto.getApplication_id() + "\\" + folderName, getXml);
			/**
			 * Read the Xml file and get the txn id to save in the data base
			 */
			String txnId = Utility.toGetTxnFromXMlpath(eKYCDAO.getInstance().getFileLocation(eKYCConstant.FILE_PATH)
					+ pDto.getApplication_id() + "\\" + folderName + "\\FirstResponse.xml");

			if (txnId != null && !txnId.isEmpty()) {
				/**
				 * Save the Txn into the data base
				 */
				int insertCount = eKYCDAO.insertTxnDetails(pDto.getApplication_id(), txnId, folderName);
				if (insertCount > 0) {
					eKYCDAO.getInstance().updateTxnStatus(1, txnId);
					result.setEsign_Xml(getXml);
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setResult(result);
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
				}
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
		}
		return response;
	}

	/**
	 * 
	 * @param applicationId
	 * @return
	 */
	public eKYCDTO finalPDFGenerator(int applicationId) {
		ApplicationMasterDTO applicationMasterDTO = new ApplicationMasterDTO();
		eKYCDTO eKYCDTO = null;
		if (applicationId != 0 && applicationId > 0) {
			applicationMasterDTO.setApplication_id(applicationId);
		}
		applicationMasterDTO = eKYCDAO.getInstance().getApplicationMasterDetails(applicationMasterDTO);
		if (applicationMasterDTO != null) {
			eKYCDTO = new eKYCDTO();
			if (eKYCDTO.getForPDFKeyValue() == null) {
				eKYCDTO.setForPDFKeyValue(new HashMap<String, String>());
			}
			eKYCDTO.setApplicationMasterDTO(applicationMasterDTO);
			eKYCDTO.getForPDFKeyValue().putAll(applicationMasterDTO.getForPDFKeyValue());
			applicationMasterDTO.setAccHolderPersonalDtlRequired(true);
			applicationMasterDTO.setBankAccDtlRequired(true);
			applicationMasterDTO.setCommunicationAddressRequired(true);
			applicationMasterDTO.setPanCardDetailRequired(true);
			applicationMasterDTO.setPermanentAddressRequired(true);
			eKYCDTO = eKYCHelper.getInstance().populateRerquiredFields(applicationMasterDTO, eKYCDTO);
		}
		return eKYCDTO;
	}

	/**
	 * TO get the Link to Download the Document
	 * 
	 * @param pDto
	 * @return
	 */
	public ResponseDTO getDocumentLink(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		try {
			PersonalDetailsDTO result = null;
			if (pDto.getApplication_id() > 0) {
				result = new PersonalDetailsDTO();
				String documentLink = eKYCDAO.getInstance().getDocumentLink(pDto.getApplication_id(),
						eKYCConstant.EKYC_DOCUMENT);
				if (documentLink != null && !documentLink.isEmpty()) {
					result.setEsign_document(documentLink);
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setResult(result);
				} else {
					ResponseDTO xmlResult = getXmlEncode(pDto);
					if (xmlResult.getStatus() == eKYCConstant.SUCCESS_STATUS) {
						documentLink = eKYCDAO.getInstance().getDocumentLink(pDto.getApplication_id(),
								eKYCConstant.EKYC_DOCUMENT);
						result.setEsign_document(documentLink);
						response.setStatus(eKYCConstant.SUCCESS_STATUS);
						response.setMessage(eKYCConstant.SUCCESS_MSG);
						response.setResult(result);
					} else {
						response.setStatus(eKYCConstant.FAILED_STATUS);
						response.setMessage(eKYCConstant.FAILED_MSG);
						response.setMessage(eKYCConstant.FAILED_MSG);
					}
				}
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * To get pan Card Details
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO getPanCardDetails(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		if (pDto.getApplication_id() > 0) {
			PanCardDetailsDTO panDetails = peKYCDao.checkPanCardUpdated(pDto.getApplication_id());
			if (panDetails != null) {
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setResult(panDetails);
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
		}
		return response;
	}

	/**
	 * To get the basic Information for given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO getBasicInformation(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		if (pDto.getApplication_id() > 0) {
			PersonalDetailsDTO result = eKYCDAO.CheckBasicInformation(pDto.getApplication_id());
			if (result != null) {
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setResult(result);
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
		}
		return response;
	}

	/**
	 * To get the Communication Address for the given application address
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO getCommunicationAddress(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		if (pDto.getApplication_id() > 0) {
			AddressDTO result = eKYCDAO.checkPermanentAddress(pDto.getApplication_id());
			if (result != null) {
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setResult(result);
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
		}
		return response;
	}

	/**
	 * To get the permanent Address for the given application address
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO getPermanentAddress(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		if (pDto.getApplication_id() > 0) {
			AddressDTO result = eKYCDAO.checkPermanentAddress(pDto.getApplication_id());
			if (result != null) {
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setResult(result);
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
		}
		return response;
	}

	/**
	 * To get the Bank Details for the given application address
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO getBankDetails(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		if (pDto.getApplication_id() > 0) {
			BankDetailsDTO result = peKYCDao.checkBankDetailsUpdated(pDto.getApplication_id());
			if (result != null) {
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setResult(result);
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
		}
		return response;
	}

	/**
	 * To get the Exch Details for the given application address
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO getExchDetails(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		if (pDto.getApplication_id() > 0) {
			ExchDetailsDTO result = peKYCDao.checkExchUpdatedStatus(pDto.getApplication_id());
			if (result != null) {
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setResult(result);
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
		}
		return response;
	}

	/**
	 * To get the File uploaded for the given application address
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO getUploadedFile(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		if (pDto.getApplication_id() > 0) {
			List<FileUploadDTO> result = peKYCDao.getUploadedFile(pDto.getApplication_id());
			if (result != null && result.size() > 0) {
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setResult(result);
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
		}
		return response;
	}

	public ResponseDTO uploadProof(FormDataBodyPart proof, String proofType, int applicationId) {
		ResponseDTO response = new ResponseDTO();
		try {
			if (applicationId > 0) {
				int checkId = 0;
				FormDataBodyPart formDataBodyPart = proof;
				FormDataContentDisposition contentDisposition = formDataBodyPart.getFormDataContentDisposition();
				String fileName = "";
				if (contentDisposition.getFileName() != null) {
					checkId = eKYCDAO.getInstance().checkFileUploaded(applicationId, proofType);
					fileName = contentDisposition.getFileName().trim();
					InputStream is = formDataBodyPart.getEntityAs(InputStream.class);
					int read = 0;
					String filePath = eKYCConstant.PROJ_DIR + eKYCConstant.UPLOADS_DIR + applicationId + "//"
							+ proofType;
					File dir = new File(filePath);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					byte[] bytes = new byte[1024];
					OutputStream out = new FileOutputStream(dir + "//" + fileName);
					while ((read = is.read(bytes)) != -1) {
						out.write(bytes, 0, read);
					}
					out.flush();
					out.close();
					String proofUrl = eKYCConstant.SITE_URL_FILE + eKYCConstant.UPLOADS_DIR + applicationId + "//"
							+ proofType + "//" + fileName;
					if (checkId > 0) {
						boolean isSucessFull = peKYCDao.updateAttachementDetails(proofUrl, proofType, applicationId);
						if (isSucessFull) {
							peKYCDao.updateApplicationStatus(applicationId, eKYCConstant.ATTACHEMENT_UPLOADED);
							response.setStatus(eKYCConstant.SUCCESS_STATUS);
							response.setMessage(eKYCConstant.SUCCESS_MSG);
							response.setReason(eKYCConstant.EXCH_DETAILS_UPDATED_SUCESSFULLY);
						} else {
							response.setStatus(eKYCConstant.FAILED_STATUS);
							response.setMessage(eKYCConstant.FAILED_MSG);
							response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
						}
					} else {
						int insertCount = peKYCDao.insertAttachementDetails(proofUrl, proofType, applicationId);
						if (insertCount > 0) {
							peKYCDao.updateApplicationStatus(applicationId, eKYCConstant.ATTACHEMENT_UPLOADED);
							response.setStatus(eKYCConstant.SUCCESS_STATUS);
							response.setMessage(eKYCConstant.SUCCESS_MSG);
							response.setReason(eKYCConstant.EXCH_DETAILS_UPDATED_SUCESSFULLY);
						} else {
							response.setStatus(eKYCConstant.FAILED_STATUS);
							response.setMessage(eKYCConstant.FAILED_MSG);
							response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
						}
					}
				}
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * upload the ivr capture details for given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param ivrImage
	 * @param ivrLat
	 * @param ivrLong
	 * @param applicationId
	 * @return
	 */
	public ResponseDTO uploadIvrCapture(String ivrImageBase64, String ivrLat, String ivrLong, int applicationId,
			String deviceIP, String userAgent) {
		ResponseDTO response = new ResponseDTO();
		try {
			if (applicationId > 0) {
				if (ivrImageBase64 != null && !ivrImageBase64.isEmpty()) {
					String siteUrl = Utility.convertBase64ToImage(ivrImageBase64,
							eKYCConstant.PROJ_DIR + eKYCConstant.UPLOADS_DIR + applicationId + "//" + "IVR_IMAGE",
							applicationId);
					if (siteUrl != null && !siteUrl.isEmpty()) {
						int count = eKYCDAO.getInstance().insertIvrDetails(applicationId, siteUrl, ivrLat, ivrLong,
								deviceIP, userAgent);
						if (count > 0) {
							response.setStatus(eKYCConstant.SUCCESS_STATUS);
							response.setMessage(eKYCConstant.SUCCESS_MSG);
							response.setReason(eKYCConstant.IVR_DETAILS_UPDATED_SUCESSFULLY);
						} else {
							response.setStatus(eKYCConstant.FAILED_STATUS);
							response.setMessage(eKYCConstant.FAILED_MSG);
							response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
						}
					} else {
						response.setStatus(eKYCConstant.FAILED_STATUS);
						response.setMessage(eKYCConstant.FAILED_MSG);
						response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
					}
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.FILE_CANNOT_BE_EMPTY);
				}
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * To get the Esign application as signed
	 * 
	 * @author GOWRI SANKAR R
	 * @param msg
	 * @return
	 */
	public Response getNsdlXML(String msg) {
		try {
			if (msg != null && !msg.isEmpty()) {
				/**
				 * Write into the temp file and get the txn from the xml
				 */
				String random = Utility.generateOTP();
				String fileName = "lastXml" + random + ".xml";
				// File fXmlFile = new
				// File(CSEnvVariables.getProperty(eKYCConstant.TEMP_FILE_XML_DOCUMENTS)
				// + fileName);
				File fXmlFile = new File(eKYCDAO.getInstance().getFileLocation(eKYCConstant.FILE_PATH) + "TempXMLFiles"
						+ eKYCConstant.WINDOWS_FORMAT_SLASH + fileName);
				if (fXmlFile.createNewFile()) {
					FileWriter myWriter = new FileWriter(fXmlFile);
					myWriter.write(msg);
					myWriter.close();

					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(fXmlFile);
					doc.getDocumentElement().normalize();
					Element eElement = doc.getDocumentElement();
					String txnName = eElement.getAttribute("txn");

					/**
					 * Get Application for thr TxnName
					 */

					esignDTO applicationNumber = eKYCDAO.getInstance().getTxnDetails(txnName);
					if (applicationNumber != null && applicationNumber.getApplication_id() > 0) {
						String filePath = eKYCDAO.getInstance().getFileLocation(eKYCConstant.FILE_PATH)
								+ applicationNumber.getApplication_id() + eKYCConstant.WINDOWS_FORMAT_SLASH
								+ applicationNumber.getFolderLocation() + eKYCConstant.WINDOWS_FORMAT_SLASH;
						/**
						 * 
						 */
						// System.out.println("BBBBBBBBBB"+filePath);
						String resposne = Utility.getSignFromNsdl(
								filePath + eKYCConstant.WINDOWS_FORMAT_SLASH
										+ eKYCDAO.getInstance().getFileLocation(eKYCConstant.CONSTANT_PDF_NAME)
										+ eKYCConstant.PDF_FILE_EXTENSION,
								filePath, msg, applicationNumber.getApplicant_name(), applicationNumber.getCity());
						if (resposne != null && !resposne.isEmpty() && resposne.equalsIgnoreCase(
								CSEnvVariables.getProperty(eKYCConstant.SIGNED_FINAL_RESPONSE_TEXT))) {
							/**
							 * update the txn status
							 */
							eKYCDAO.getInstance().updateTxnStatus(1, txnName);
							/**
							 * check and update the url details in attchements
							 * details
							 */
							int checkId = eKYCDAO.getInstance().checkFileUploaded(applicationNumber.getApplication_id(),
									eKYCConstant.SIGNED_EKYC_DOCUMENT);
							if (checkId > 0) {
								eKYCDAO.getInstance().updateAttachementDetails(eKYCConstant.SITE_URL_FILE
										+ eKYCConstant.UPLOADS_DIR + applicationNumber.getApplication_id()
										+ eKYCConstant.WINDOWS_FORMAT_SLASH + applicationNumber.getFolderLocation()
										+ eKYCConstant.WINDOWS_FORMAT_SLASH + eKYCConstant.SIGNED_FINAL_DOCUMENT_NAME,
										eKYCConstant.SIGNED_EKYC_DOCUMENT, applicationNumber.getApplication_id());

								java.net.URI location = new java.net.URI(eKYCConstant.SITE_URL_FILE
										+ eKYCConstant.UPLOADS_DIR + applicationNumber.getApplication_id()
										+ eKYCConstant.WINDOWS_FORMAT_SLASH + applicationNumber.getFolderLocation()
										+ eKYCConstant.WINDOWS_FORMAT_SLASH + eKYCConstant.SIGNED_FINAL_DOCUMENT_NAME);
								return Response.temporaryRedirect(location).build();
							} else {
								eKYCDAO.getInstance().insertAttachementDetails(eKYCConstant.SITE_URL_FILE
										+ eKYCConstant.UPLOADS_DIR + applicationNumber.getApplication_id()
										+ eKYCConstant.WINDOWS_FORMAT_SLASH + applicationNumber.getFolderLocation()
										+ eKYCConstant.WINDOWS_FORMAT_SLASH + eKYCConstant.SIGNED_FINAL_DOCUMENT_NAME,
										eKYCConstant.SIGNED_EKYC_DOCUMENT, applicationNumber.getApplication_id());
								java.net.URI location = new java.net.URI(eKYCConstant.SITE_URL_FILE
										+ eKYCConstant.UPLOADS_DIR + applicationNumber.getApplication_id()
										+ eKYCConstant.WINDOWS_FORMAT_SLASH + applicationNumber.getFolderLocation()
										+ eKYCConstant.WINDOWS_FORMAT_SLASH + eKYCConstant.SIGNED_FINAL_DOCUMENT_NAME);
								return Response.temporaryRedirect(location).build();
							}
						} else {

						}
					} else {
						/**
						 * application id cannot be zero at this time
						 */
					}

				} else {
					/**
					 * message cannot be null
					 */
				}
			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ResponseDTO getEsignedDocument(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		PersonalDetailsDTO result = null;
		if (pDto.getApplication_id() > 0) {
			result = new PersonalDetailsDTO();
			String documentLink = eKYCDAO.getInstance().getEsignedDocument(pDto.getApplication_id(),
					eKYCConstant.SIGNED_EKYC_DOCUMENT);
			result.setEsign_document(documentLink);
			response.setStatus(eKYCConstant.SUCCESS_STATUS);
			response.setMessage(eKYCConstant.SUCCESS_MSG);
			response.setResult(result);
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
		}
		return response;
	}

	/**
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO resendOTP(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		PersonalDetailsDTO checkUser = peKYCDao.checkExistingUser(pDto);
		if (checkUser != null && checkUser.getApplication_id() > 0) {
			String otp = Utility.generateOTP();
			peKYCDao.updateOtpForApplicationId(Integer.parseInt(otp), checkUser.getApplication_id());
			Utility.sendMessage(checkUser.getMobile_number(), Integer.parseInt(otp));
		} else {
			String otp = Utility.generateOTP();
			peKYCDao.updateOtpForMobilenumber(Integer.parseInt(otp), pDto.getApplication_id());
			Utility.sendMessage(checkUser.getMobile_number(), Integer.parseInt(otp));
		}
		response.setStatus(eKYCConstant.SUCCESS_STATUS);
		response.setMessage(eKYCConstant.SUCCESS_MSG);
		response.setReason(eKYCConstant.OTP_SENT_SUCESSFULLY);
		return response;
	}

	// public ResponseDTO resendEmailVerification(PersonalDetailsDTO pDto) {
	// // TODO Auto-generated method stub
	// return null;
	// }

}
