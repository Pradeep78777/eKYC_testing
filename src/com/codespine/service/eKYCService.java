package com.codespine.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.json.simple.JSONObject;
import java.util.HashMap;

import com.codespine.data.eKYCDAO;
import com.codespine.dto.AddressDTO;
import com.codespine.dto.ApplicationMasterDTO;
import com.codespine.dto.BankDetailsDTO;
import com.codespine.dto.ExchDetailsDTO;
import com.codespine.dto.PanCardDetailsDTO;
import com.codespine.dto.PersonalDetailsDTO;
import com.codespine.dto.ResponseDTO;
import com.codespine.restservice.NsdlPanVerificationRestService;
import com.codespine.dto.eKYCDTO;
import com.codespine.helper.eKYCHelper;
import com.codespine.util.Utility;
import com.codespine.util.eKYCConstant;

public class eKYCService {
	public static eKYCService eKYCService = null;
	public static eKYCService getInstance() {
		if(eKYCService == null) {
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
					String otp = Utility.generateOTP();
					peKYCDao.updateOtpForApplicationId(Integer.parseInt(otp), checkUser.getApplication_id());
					Utility.sendMessage(checkUser.getMobile_number(), Integer.parseInt(otp));
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
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setReason(eKYCConstant.OTP_VERIFIED_SUCCESS);
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
					String panCardname = firstName + middleName + lastName;
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
			PanCardDetailsDTO checkApplicationId = peKYCDao.checkPanCardUpdated(pDto.getApplication_id());
			if (checkApplicationId != null && checkApplicationId.getApplication_id() > 0) {
				boolean isSucessFull = peKYCDao.updatePanCardDetails(pDto);
				if (isSucessFull) {
					peKYCDao.updateApplicationStatus(pDto.getApplication_id(), eKYCConstant.PAN_CARD_UPDATED);
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setReason(eKYCConstant.PAN_CARD_DETAILS_SAVED);
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
				}
			} else {
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
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
		}
		return response;
	}

	// /**
	// *
	// * @param applicationId
	// * @param panCard
	// */
	// public static void panVerification(int applicationId, String panCard) {
	// /**
	// * To create the create the jks file for the given application id
	// */
	// NsdlPanVerificationRestService.pfx2JksFile(applicationId);
	// /**
	// * To create the sig file from the jks file
	// */
	// NsdlPanVerificationRestService.pkcs7Generate(applicationId, panCard);
	// /**
	// * TO get the result from the NSDL
	// */
	// String respone =
	// NsdlPanVerificationRestService.apiCallForPanVerififcation(applicationId,
	// panCard);
	// /**
	// * Make the String to json Object
	// */
	// }

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
			/**
			 * Check user inserted or not
			 */
			PersonalDetailsDTO checkUser = eKYCDAO.CheckBasicInformation(pDto.getApplication_id());
			if (checkUser != null && checkUser.getApplication_id() > 0) {
				/**
				 * If already there e update the basic information
				 */
				boolean updateCount = peKYCDao.updateBasicInformation(pDto);
				if (updateCount) {
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
				/**
				 * else insert the basic information
				 */
				int insertCount = peKYCDao.basicInformation(pDto);
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
			AddressDTO checkAddress = eKYCDAO.checkCommunicationAddress(pDto.getApplication_id());
			if (checkAddress != null && checkAddress.getApplication_id() > 0) {
				boolean isSuucessFull = eKYCDAO.updateCommunicationAddress(pDto);
				if (isSuucessFull) {
					peKYCDao.updateApplicationStatus(pDto.getApplication_id(),
							eKYCConstant.COMMUNICATION_ADDRESS_UPDATED);
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setReason(eKYCConstant.ADDRESS_SAVED_SUCESSFULLY);
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
				}
			} else {
				int insertCommunicationCount = eKYCDAO.insertCommunicationAddress(pDto);
				if (insertCommunicationCount > 0) {
					peKYCDao.updateApplicationStatus(pDto.getApplication_id(),
							eKYCConstant.COMMUNICATION_ADDRESS_UPDATED);
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setReason(eKYCConstant.ADDRESS_SAVED_SUCESSFULLY);
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
	 * To update the permanent address
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO updatePermanentAddress(AddressDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		if (pDto.getApplication_id() > 0) {
			AddressDTO checkAddress = eKYCDAO.checkPermanentAddress(pDto.getApplication_id());
			if (checkAddress != null && checkAddress.getApplication_id() > 0) {
				boolean isSuucessFull = eKYCDAO.updatePermanentAddress(pDto);
				if (isSuucessFull) {
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
			BankDetailsDTO checkUser = peKYCDao.checkBankDetailsUpdated(pDto.getApplication_id());
			if (checkUser != null && checkUser.getApplication_id() > 0) {
				boolean isSuccessFull = peKYCDao.updateBankDetails(pDto);
				if (isSuccessFull) {
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

	public ResponseDTO uploadProof(FormDataBodyPart proof, String proofType, int applicationId) {
		ResponseDTO response = new ResponseDTO();
		try {
			if (applicationId > 0) {
				/**
				 * Delete all the proof and proof type and insert the new one
				 */
				peKYCDao.deleteProof(applicationId);
				FormDataBodyPart formDataBodyPart = proof;
				FormDataContentDisposition contentDisposition = formDataBodyPart.getFormDataContentDisposition();
				String fileName = "";
				if (contentDisposition.getFileName() != null) {
					fileName = contentDisposition.getFileName();
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
					int insertCount = peKYCDao.insertAttachementDetails(proofUrl, proofType, applicationId);
					if (insertCount > 0) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO getXmlEncode(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		if (pDto.getApplication_id() > 0) {
			PersonalDetailsDTO result = new PersonalDetailsDTO();
			String xml = Utility.getXmlForEsign();
			result.setEsign_Xml(xml);
			response.setStatus(eKYCConstant.SUCCESS_STATUS);
			response.setResult(result);
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
		}
		return response;
	}
	public eKYCDTO finalPDFGenerator(int applicationId) {
		ApplicationMasterDTO applicationMasterDTO = new ApplicationMasterDTO();
		eKYCDTO eKYCDTO = null;
		if(applicationId != 0 && applicationId > 0 ) {
			applicationMasterDTO.setApplication_id(applicationId);
		}
		applicationMasterDTO = eKYCDAO.getInstance().getApplicationMasterDetails(applicationMasterDTO);
		if(applicationMasterDTO != null) {
			eKYCDTO = new eKYCDTO();
			if(eKYCDTO.getForPDFKeyValue() == null) {
				eKYCDTO.setForPDFKeyValue(new HashMap<String,String>());
			}
			eKYCDTO.setApplicationMasterDTO(applicationMasterDTO);
			eKYCDTO.getForPDFKeyValue().putAll(applicationMasterDTO.getForPDFKeyValue());
			applicationMasterDTO.setAccHolderPersonalDtlRequired(true);
			applicationMasterDTO.setBankAccDtlRequired(true);
			applicationMasterDTO.setCommunicationAddressRequired(true);
			applicationMasterDTO.setPanCardDetailRequired(true);
			applicationMasterDTO.setPermanentAddressRequired(true);
			eKYCDTO = eKYCHelper.getInstance().populateRerquiredFields(applicationMasterDTO,eKYCDTO);
		}
		return eKYCDTO;
	}

}
