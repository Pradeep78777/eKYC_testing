package com.codespine.service;

import com.codespine.data.eKYCDAO;
import com.codespine.dto.AddressDTO;
import com.codespine.dto.BankDetailsDTO;
import com.codespine.dto.PanCardDetailsDTO;
import com.codespine.dto.PersonalDetailsDTO;
import com.codespine.dto.ResponseDTO;
import com.codespine.util.Utility;
import com.codespine.util.eKYCConstant;

public class eKYCService {
	eKYCDAO peKYCDao = new eKYCDAO();

	// /**
	// * Method insert the personal details and send back the Application id to
	// * front end
	// *
	// * @author GOWRI SANKAR R
	// * @param pDto
	// * @return
	// */
	//
	// public ResponseDTO personalDetails(PersonalDetailsDTO pDto) {
	// ResponseDTO response = new ResponseDTO();
	// JSONObject resp = new JSONObject();
	// PersonalDetailsDTO checkUser = new PersonalDetailsDTO();
	// checkUser = peKYCDao.checkAlreadyRegistred(pDto);
	// if (checkUser.getId() > 0) {
	// response.setStatus(eKYCConstant.FAILED_STATUS);
	// response.setMessage(eKYCConstant.FAILED_MSG);
	// response.setReason(eKYCConstant.USER_REGISTERED);
	// } else {
	// String stringOtp = Utility.generateOTP();
	// int otp = Integer.parseInt(stringOtp);
	// pDto.setOtp(otp);
	// int applicationID = peKYCDao.personalDetails(pDto);
	// if (applicationID > 0) {
	// Utility.sendSms(otp + "", pDto.getMobile_number() + "");
	// resp.put("applicationID", applicationID);
	// response.setStatus(eKYCConstant.SUCCESS_STATUS);
	// response.setMessage(eKYCConstant.SUCCESS_MSG);
	// response.setReason(eKYCConstant.PERSONAL_DETAILS_INSERTED_SUCESS);
	// response.setResult(resp);
	// } else {
	// response.setStatus(eKYCConstant.FAILED_STATUS);
	// response.setMessage(eKYCConstant.FAILED_MSG);
	// response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
	// }
	// }
	// return response;
	// }
	//
	// /**
	// * Method to inseert the pan card details
	// *
	// * @author GOWRI SANKAR R
	// * @param pDto
	// * @return
	// */
	// public ResponseDTO panCardDetails(PanCardDetailsDTO pDto) {
	// ResponseDTO response = new ResponseDTO();
	// // Change the given date to sql date format
	// String strDate = pDto.getDob();
	// String finalDate = "";
	// try {
	// Date date = new SimpleDateFormat("dd/MM/yyyy").parse(strDate);
	// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	// finalDate = formatter.format(date);
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	// pDto.setDob(finalDate);
	// /**
	// * insert the pan card details
	// */
	// boolean isSuccess = peKYCDao.insertPanCardDetails(pDto);
	// if (isSuccess) {
	// response.setStatus(eKYCConstant.SUCCESS_STATUS);
	// response.setMessage(eKYCConstant.SUCCESS_MSG);
	// response.setReason(eKYCConstant.PAN_CARD_DETAILS_INSERTED_SUCCESS);
	// } else {
	// response.setStatus(eKYCConstant.FAILED_STATUS);
	// response.setMessage(eKYCConstant.FAILED_MSG);
	// response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
	// }
	// return response;
	// }
	//
	// /**
	// * Method to insert the personal Details
	// *
	// * @author GOWRI SANKAR R
	// * @param pDto
	// * @return
	// */
	// public ResponseDTO insertPersonalInfoDetails(PersonalDetailsDTO pDto) {
	// ResponseDTO response = new ResponseDTO();
	// boolean isSuccess = peKYCDao.insertPersonalInfoDetails(pDto);
	// if (isSuccess) {
	// response.setStatus(eKYCConstant.SUCCESS_STATUS);
	// response.setMessage(eKYCConstant.SUCCESS_MSG);
	// response.setReason(eKYCConstant.PERSONAL_INFO_INSERTED_SUCCESS);
	// } else {
	// response.setStatus(eKYCConstant.FAILED_STATUS);
	// response.setMessage(eKYCConstant.FAILED_MSG);
	// response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
	// }
	// return response;
	// }
	//
	// /**
	// * Mathod to insert bank Details
	// *
	// * @author GOWRI SANKAR R
	// * @param pDto
	// * @return
	// */
	// public ResponseDTO bankDetails(BankDetailsDTO pDto) {
	// ResponseDTO response = new ResponseDTO();
	// boolean isSuccess = peKYCDao.bankDetails(pDto);
	// if (isSuccess) {
	// response.setStatus(eKYCConstant.SUCCESS_STATUS);
	// response.setMessage(eKYCConstant.SUCCESS_MSG);
	// response.setReason(eKYCConstant.BANK_DETAILS_INSERTED_SUCCESS);
	// } else {
	// response.setStatus(eKYCConstant.FAILED_STATUS);
	// response.setMessage(eKYCConstant.FAILED_MSG);
	// response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
	// }
	// return response;
	// }
	//
	// /**
	// * Method to send the Otp to the user
	// *
	// * @author GOWRI SANKAR R
	// * @param pDto
	// * @return
	// */
	// public ResponseDTO sendOTP(PersonalDetailsDTO pDto) {
	// ResponseDTO response = new ResponseDTO();
	// PersonalDetailsDTO checkUser = new PersonalDetailsDTO();
	// checkUser = peKYCDao.checkAlreadyRegistred(pDto);
	// if (checkUser.getId() > 0) {
	// String stringOtp = Utility.generateOTP();
	// int otp = Integer.parseInt(stringOtp);
	// pDto.setOtp(otp);
	// boolean update = peKYCDao.updateOTP(pDto);
	// if (update) {
	// Utility.sendSms(otp + "", pDto.getMobile_number() + "");
	// response.setStatus(eKYCConstant.SUCCESS_STATUS);
	// response.setMessage(eKYCConstant.SUCCESS_MSG);
	// response.setReason(eKYCConstant.PERSONAL_DETAILS_INSERTED_SUCESS);
	// } else {
	// response.setStatus(eKYCConstant.FAILED_STATUS);
	// response.setMessage(eKYCConstant.FAILED_MSG);
	// response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
	// }
	// } else {
	// response.setStatus(eKYCConstant.FAILED_STATUS);
	// response.setMessage(eKYCConstant.FAILED_MSG);
	// response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
	// }
	// return response;
	// }

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
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setReason(eKYCConstant.OTP_SENT_SUCESSFULLY);
					response.setResult(result);
				} else {
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setResult(result);
				}
			} else {
				result.setEmail(checkUser.getEmail());
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setResult(result);
			}
		} else {
			/**
			 * if there is no result create the new application with given
			 * mobile number and email
			 */
			String otp = Utility.generateOTP();
			pDto.setOtp(Integer.parseInt(otp));
			int newApplicationId = peKYCDao.createNewApplication(pDto);
			if (newApplicationId != 0 && newApplicationId > 0) {
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setReason(eKYCConstant.NEW_USER_SUCESS);
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
		ResponseDTO response = new ResponseDTO();
		if (pDto.getApplication_id() > 0) {
			PanCardDetailsDTO checkApplicationId = peKYCDao.checkPanCardUpdated(pDto.getApplication_id());
			if (checkApplicationId != null && checkApplicationId.getApplication_id() > 0) {
				boolean isSucessFull = peKYCDao.updatePanCardDetails(pDto);
				if (isSucessFull) {
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

}
