package com.codespine.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;

import com.codespine.data.eKYCDAO;
import com.codespine.dto.BankDetailsDTO;
import com.codespine.dto.PanCardDetailsDTO;
import com.codespine.dto.PersonalDetailsDTO;
import com.codespine.dto.ResponseDTO;
import com.codespine.util.Utility;
import com.codespine.util.eKYCConstant;

@SuppressWarnings("unchecked")
public class eKYCService {
	eKYCDAO peKYCDao = new eKYCDAO();

	/**
	 * Method insert the personal details and send back the Application id to
	 * front end
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */

	public ResponseDTO personalDetails(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		JSONObject resp = new JSONObject();
		PersonalDetailsDTO checkUser = new PersonalDetailsDTO();
		checkUser = peKYCDao.checkAlreadyRegistred(pDto);
		if (checkUser.getId() > 0) {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.USER_REGISTERED);
		} else {
			String stringOtp = Utility.generateOTP();
			int otp = Integer.parseInt(stringOtp);
			pDto.setOtp(otp);
			int applicationID = peKYCDao.personalDetails(pDto);
			if (applicationID > 0) {
				Utility.sendSms(otp + "", pDto.getMobile_number() + "");
				resp.put("applicationID", applicationID);
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setReason(eKYCConstant.PERSONAL_DETAILS_INSERTED_SUCESS);
				response.setResult(resp);
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
		PersonalDetailsDTO checkUser = new PersonalDetailsDTO();
		checkUser = peKYCDao.checkAlreadyRegistred(pDto);
		int otp = checkUser.getOtp();
		JSONObject temp = new JSONObject();
		// int userEnteredOTP = pDto.getOtp();
		if (otp > 0) {
			boolean isSuccess = peKYCDao.updateAsVerified(pDto);
			if (isSuccess) {
				temp.put("application_id", checkUser.getId());
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setReason(eKYCConstant.OTP_VERIFIED_SUCCESS);
				response.setResult(temp);
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.OTP_VERIFIED_FAILED);
		}
		return response;
	}

	/**
	 * Method to inseert the pan card details
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO panCardDetails(PanCardDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		// Change the given date to sql date format
		String strDate = pDto.getDob();
		String finalDate = "";
		try {
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(strDate);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			finalDate = formatter.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		pDto.setDob(finalDate);
		/**
		 * insert the pan card details
		 */
		boolean isSuccess = peKYCDao.insertPanCardDetails(pDto);
		if (isSuccess) {
			response.setStatus(eKYCConstant.SUCCESS_STATUS);
			response.setMessage(eKYCConstant.SUCCESS_MSG);
			response.setReason(eKYCConstant.PAN_CARD_DETAILS_INSERTED_SUCCESS);
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	/**
	 * Method to insert the personal Details
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO insertPersonalInfoDetails(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		boolean isSuccess = peKYCDao.insertPersonalInfoDetails(pDto);
		if (isSuccess) {
			response.setStatus(eKYCConstant.SUCCESS_STATUS);
			response.setMessage(eKYCConstant.SUCCESS_MSG);
			response.setReason(eKYCConstant.PERSONAL_INFO_INSERTED_SUCCESS);
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	/**
	 * Mathod to insert bank Details
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO bankDetails(BankDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		boolean isSuccess = peKYCDao.bankDetails(pDto);
		if (isSuccess) {
			response.setStatus(eKYCConstant.SUCCESS_STATUS);
			response.setMessage(eKYCConstant.SUCCESS_MSG);
			response.setReason(eKYCConstant.BANK_DETAILS_INSERTED_SUCCESS);
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	/**
	 * Method to send the Otp to the user
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO sendOTP(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		PersonalDetailsDTO checkUser = new PersonalDetailsDTO();
		checkUser = peKYCDao.checkAlreadyRegistred(pDto);
		if (checkUser.getId() > 0) {
			String stringOtp = Utility.generateOTP();
			int otp = Integer.parseInt(stringOtp);
			pDto.setOtp(otp);
			boolean update = peKYCDao.updateOTP(pDto);
			if (update) {
				Utility.sendSms(otp + "", pDto.getMobile_number() + "");
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setReason(eKYCConstant.PERSONAL_DETAILS_INSERTED_SUCESS);
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
		return response;
	}

}
