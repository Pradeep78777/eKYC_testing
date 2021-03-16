package com.codespine.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;

import com.codespine.data.AdminDAO;
import com.codespine.data.EkycApplicationDAO;
import com.codespine.data.eKYCDAO;
import com.codespine.dto.ApplicationStatusDTO;
import com.codespine.dto.PanCardDetailsDTO;
import com.codespine.dto.PersonalDetailsDTO;
import com.codespine.dto.ResponseDTO;
import com.codespine.util.CSEnvVariables;
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
				int isRejected = userDetails.getIsRejected();
//				int isAproved = userDetails.getIsAproved();
				int retifyCount = userDetails.getRectifyCount();
				if (userDetails.getApplicationStatus() >= 3) {
					PanCardDetailsDTO panCardName = eKYCDAO.getInstance().getApplicantName(pDto.getApplication_id());
					userDetails.setApplicant_name(panCardName.getApplicant_name());
					userDetails.setFathersName(panCardName.getFathersName());
					if (isRejected == 1 && retifyCount == 0) {
						userDetails.setApplicationStatus(2);
					}
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

	/**
	 * Method to generate the ipv link and sent it through the mobile and email
	 * 
	 * @author GOWRI SANKAR R
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ResponseDTO getIPVlink(PersonalDetailsDTO dto) {
		ResponseDTO response = new ResponseDTO();
		if (dto != null && dto.getApplication_id() > 0) {
			PersonalDetailsDTO userDetails = eKYCDAO.getInstance().getProfileDetails(dto);
			if (userDetails != null) {
				JSONObject result = new JSONObject();
				/*
				 * get the email and mobile from the user details in data base
				 */
				String userEmail = userDetails.getEmail();
				long mobileNumber = userDetails.getMobile_number();
				/*
				 * create the random key and store in data base and send to both
				 * mobile and email
				 */
				String randomKey = Utility.randomAlphaNumeric();
				String url = CSEnvVariables.getMethodNames(eKYCConstant.IVP_BASE_URL) + dto.getApplication_id()
						+ "&randomKey=" + randomKey;
				String bityURl = Utility.getBitlyLink(url);
				/**
				 * url will valid for the 30 minutes
				 */
				Calendar currentTime = Calendar.getInstance();
				currentTime.add(Calendar.MINUTE, 30);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				/**
				 * update into the database
				 */
				JSONObject tempJson = EkycApplicationDAO.getInstance()
						.getIVRMasterDetails(userDetails.getApplication_id());
				if (tempJson != null && (Integer) tempJson.get("application_id") > 0) {
					eKYCDAO.getInstance().updateIvrUrlDetails(dto.getApplication_id(), randomKey,
							formatter.format(currentTime.getTime()));
				} else {
					eKYCDAO.getInstance().insertIvrUrlDetails(dto.getApplication_id(), randomKey,
							formatter.format(currentTime.getTime()));
				}
				/*
				 * Get the applicant name by using the application id
				 */
				PersonalDetailsDTO applicantName = AdminDAO.getInstance().getUserDetails(dto.getApplication_id());
				/**
				 * send the link to both mail and message
				 */
				Utility.ivpMailUpdate(bityURl, userEmail);
				Utility.sendIPVLink(mobileNumber, bityURl, applicantName.getApplicant_name());
				result.put("ivrURL", bityURl);
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setReason(eKYCConstant.SUCCESS_MSG);
				response.setResult(result);
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.USER_DETAILS_NOT_FOUND);
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
		}
		return response;
	}

	/**
	 * @author GOWRI SANKAR R
	 * @param randomKey
	 * @param applicationId
	 * @return
	 */
	public String checkIvrRandom(String randomKey, int applicationId) {
		String response = eKYCConstant.FAILED_MSG;
		try {
			JSONObject tempJson = EkycApplicationDAO.getInstance().getIVRMasterDetails(applicationId);
			if (tempJson != null) {
				Calendar cal = Calendar.getInstance();
				String expiry = (String) tempJson.get("expiry_date");
				Date expiryDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expiry);
				long expiryDateMillesconds = expiryDate.getTime();
				long currentTime = cal.getTimeInMillis();
				if (expiryDateMillesconds < currentTime) {
					response = eKYCConstant.SUCCESS_MSG;
					return response;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public ResponseDTO getExcelExport() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method to get the application status for the given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO getApplicationStatus(ApplicationStatusDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		if (pDto != null && pDto.getApplicationId() > 0) {
			ApplicationStatusDTO result = EkycApplicationDAO.getInstance()
					.getApplicationStatus(pDto.getApplicationId());
			response.setStatus(eKYCConstant.SUCCESS_STATUS);
			response.setResult(result);
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
		}
		return response;
	}

}
