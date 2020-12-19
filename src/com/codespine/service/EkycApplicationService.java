package com.codespine.service;

import java.util.List;

import com.codespine.data.EkycApplicationDAO;
import com.codespine.data.eKYCDAO;
import com.codespine.dto.PanCardDetailsDTO;
import com.codespine.dto.PersonalDetailsDTO;
import com.codespine.dto.ResponseDTO;
import com.codespine.util.Utility;
import com.codespine.util.eKYCConstant;

public class EkycApplicationService {

	public static EkycApplicationService EkycApplicationService = null;

	public static EkycApplicationService getInstance() {
		if (EkycApplicationService == null) {
			EkycApplicationService = new EkycApplicationService();
		}
		return EkycApplicationService;
	}

	/**
	 * Method to resume the application
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO resumeApplication(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		List<PersonalDetailsDTO> userProfile = null;
		if (pDto != null && pDto.getEmail() != null && !pDto.getEmail().isEmpty() && pDto.getMobile_number() == 0) {
			userProfile = EkycApplicationDAO.getInstance().getUserProfile(pDto, true);
			if (userProfile != null && userProfile.size() > 0) {
				String otp = Utility.generateOTP();
				EkycApplicationDAO.getInstance().updateOTPforApplication(pDto, Integer.parseInt(otp), true);
				Utility.sendOTPtoEmail(otp, pDto.getEmail());
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.OTP_SENT_SUCESSFULLY_EMAIL);
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.NO_USER_FOUND);
			}
		} else if (pDto != null && pDto.getMobile_number() != 0
				&& (pDto.getEmail().isEmpty() || pDto.getEmail() != null)) {
			userProfile = EkycApplicationDAO.getInstance().getUserProfile(pDto, false);
			if (userProfile != null && userProfile.size() > 0) {
				String otp = Utility.generateOTP();
				EkycApplicationDAO.getInstance().updateOTPforApplication(pDto, Integer.parseInt(otp), false);
				Utility.sendMessage(pDto.getMobile_number(), Integer.parseInt(otp));
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.OTP_SENT_SUCESSFULLY_MOBILE);
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.NO_USER_FOUND);
			}
		} else {
			/**
			 * Send the failed response
			 */
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.WRONG_INPUT);
			response.setResult(userProfile);
		}
		return response;
	}

	/**
	 * Method to get the Application Details for the given user
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO getApplicationForUser(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		if (pDto.getApplication_id() > 0) {
			/*
			 * Method to get the user details by the given application id
			 */
			PersonalDetailsDTO userDetails = EkycApplicationDAO.getApplicationForUser(pDto.getApplication_id());
			if (userDetails != null) {
				userDetails.setOtp(0);
				if (userDetails.getApplicationStatus() >= 3) {
					PanCardDetailsDTO panCardName = eKYCDAO.getInstance().getApplicantName(pDto.getApplication_id());
					userDetails.setApplicant_name(panCardName.getApplicant_name());
					userDetails.setFathersName(panCardName.getFathersName());
				}
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setResult(userDetails);
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.NO_USER_FOUND);
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
		}
		return response;
	}

	/**
	 * Method to verify the otp for resuming the application
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO verifyOtpToResumeApplication(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		List<PersonalDetailsDTO> userProfileList = null;
		PersonalDetailsDTO result = null;
		int originalOtp = 0;
		int userGivenOTP = pDto.getOtp();
		if (pDto != null && pDto.getEmail() != null && !pDto.getEmail().isEmpty() && pDto.getMobile_number() == 0) {
			userProfileList = EkycApplicationDAO.getInstance().getUserProfile(pDto, true);
			if (userProfileList != null && userProfileList.size() > 0) {
				result = userProfileList.get(0);
				originalOtp = result.getOtp();
				if (originalOtp == userGivenOTP) {
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setReason(eKYCConstant.OTP_VERIFIED_SUCCESS);
					response.setResult(userProfileList);
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.OTP_VERIFIED_FAILED);
				}
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.NO_USER_FOUND);
			}
		} else if (pDto != null && pDto.getMobile_number() != 0
				&& (pDto.getEmail().isEmpty() || pDto.getEmail() != null)) {
			userProfileList = EkycApplicationDAO.getInstance().getUserProfile(pDto, false);
			if (userProfileList != null && userProfileList.size() > 0) {
				result = userProfileList.get(0);
				originalOtp = result.getOtp();
				if (originalOtp == userGivenOTP) {
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setReason(eKYCConstant.OTP_VERIFIED_SUCCESS);
					response.setResult(userProfileList);
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.OTP_VERIFIED_FAILED);
				}
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.NO_USER_FOUND);
			}
		} else {
			/**
			 * Send the failed response
			 */
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.WRONG_INPUT);
		}
		return response;
	}

}
