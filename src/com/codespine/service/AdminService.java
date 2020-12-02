package com.codespine.service;

import java.util.List;

import com.codespine.data.AdminDAO;
import com.codespine.dto.AdminDTO;
import com.codespine.dto.ApplicationLogDTO;
import com.codespine.dto.FileUploadDTO;
import com.codespine.dto.PersonalDetailsDTO;
import com.codespine.dto.ResponseDTO;
import com.codespine.util.Utility;
import com.codespine.util.eKYCConstant;

public class AdminService {
	public static AdminService AdminService = null;

	public static AdminService getInstance() {
		if (AdminService == null) {
			AdminService = new AdminService();
		}
		return AdminService;
	}

	/**
	 * Method to get all user records from data base
	 * 
	 * @author GOWRI SANKAR R
	 * @return
	 */
	public ResponseDTO getAllUserRecords() {
		ResponseDTO response = new ResponseDTO();
		List<PersonalDetailsDTO> result = AdminDAO.getInstance().getAllUserRecords();
		if (result != null && result.size() > 0) {
			response.setStatus(eKYCConstant.SUCCESS_STATUS);
			response.setMessage(eKYCConstant.SUCCESS_MSG);
			response.setReason(eKYCConstant.SUCCESS_MSG);
			response.setResult(result);
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.FAILED_MSG);
		}
		return response;
	}

	/**
	 * Method to respond back the user for given user pan card Details
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO respondPanCard(AdminDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		boolean result = false;
		if (pDto != null && pDto.getApplicationId() > 0) {
			/**
			 * Aprrove the Pan card for the given application id
			 */
			if (pDto.getIsApprove() == 1 && pDto.getIsRejected() == 0) {
				result = AdminDAO.getInstance().uploadAdminComments(true, "", pDto.getApplicationId(),
						"tbl_pancard_details");
				if (result) {
					updateAdminStatus(pDto.getApplicationId(), "PAN CARD DETAILS", true, "");
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setReason(eKYCConstant.PAN_APPROVED);
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
				}
			}
			/**
			 * reject the Pan card for the given application id
			 */
			else if (pDto.getIsApprove() == 0 && pDto.getIsRejected() == 1) {
				result = AdminDAO.getInstance().uploadAdminComments(false, pDto.getComments(), pDto.getApplicationId(),
						"tbl_pancard_details");
				if (result) {
					updateAdminStatus(pDto.getApplicationId(), "PAN CARD DETAILS", false, pDto.getComments());
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setReason(eKYCConstant.PAN_REJECTED);
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
				}
			}
			/**
			 * Respond back to the front end no action is required or giving
			 * wrong parameter's
			 */
			else if ((pDto.getIsApprove() == 0 && pDto.getIsRejected() == 0)
					|| (pDto.getIsApprove() == 1 && pDto.getIsRejected() == 1)) {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.REJECT_OR_APPROVE);

			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.SOME_PARAMTERS_ARE_MISSING);
		}
		return response;
	}

	/**
	 * Method to respond back the user for given user Personal Details
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO respondPersonalDetails(AdminDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		boolean result = false;
		if (pDto != null && pDto.getApplicationId() > 0) {
			/**
			 * Aprrove the Pan card for the given Personal Details
			 */
			if (pDto.getIsApprove() == 1 && pDto.getIsRejected() == 0) {
				result = AdminDAO.getInstance().uploadAdminComments(true, "", pDto.getApplicationId(),
						"tbl_account_holder_personal_details");
				if (result) {
					updateAdminStatus(pDto.getApplicationId(), "PERSONAL DETAILS", true, "");
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setReason(eKYCConstant.PERSONAL_DETAILS_APPROVED);
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
				}
			}
			/**
			 * reject the Personal Details for the given application id
			 */
			else if (pDto.getIsApprove() == 0 && pDto.getIsRejected() == 1) {
				result = AdminDAO.getInstance().uploadAdminComments(false, pDto.getComments(), pDto.getApplicationId(),
						"tbl_account_holder_personal_details");
				if (result) {
					updateAdminStatus(pDto.getApplicationId(), "PERSONAL DETAILS", false, pDto.getComments());
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setReason(eKYCConstant.PERSONAL_DETAILS_REJECTED);
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
				}
			}
			/**
			 * Respond back to the front end no action is required or giving
			 * wrong parameter's
			 */
			else if ((pDto.getIsApprove() == 0 && pDto.getIsRejected() == 0)
					|| (pDto.getIsApprove() == 1 && pDto.getIsRejected() == 1)) {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.REJECT_OR_APPROVE);

			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.SOME_PARAMTERS_ARE_MISSING);
		}
		return response;
	}

	/**
	 * Method to respond back the user for given user bank Account Details
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO respondBankAccountDetails(AdminDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		boolean result = false;
		if (pDto != null && pDto.getApplicationId() > 0) {
			/**
			 * Aprrove the Bank Account Details for the given application id
			 */
			if (pDto.getIsApprove() == 1 && pDto.getIsRejected() == 0) {
				result = AdminDAO.getInstance().uploadAdminComments(true, "", pDto.getApplicationId(),
						"tbl_bank_account_details");
				if (result) {
					updateAdminStatus(pDto.getApplicationId(), "BANK DETAILS", true, "");
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setReason(eKYCConstant.BANK_DETAILS_APPROVED);
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
				}
			}
			/**
			 * reject the Bank Account Details for the given application id
			 */
			else if (pDto.getIsApprove() == 0 && pDto.getIsRejected() == 1) {
				result = AdminDAO.getInstance().uploadAdminComments(false, pDto.getComments(), pDto.getApplicationId(),
						"tbl_bank_account_details");
				if (result) {
					updateAdminStatus(pDto.getApplicationId(), "BANK DETAILS", false, pDto.getComments());
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setReason(eKYCConstant.BANK_DETAILS_REJECTED);
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
				}
			}
			/**
			 * Respond back to the front end no action is required or giving
			 * wrong parameter's
			 */
			else if ((pDto.getIsApprove() == 0 && pDto.getIsRejected() == 0)
					|| (pDto.getIsApprove() == 1 && pDto.getIsRejected() == 1)) {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.REJECT_OR_APPROVE);

			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.SOME_PARAMTERS_ARE_MISSING);
		}
		return response;
	}

	/**
	 * Method to respond to the communication address for the given application
	 * id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO respondCommunicationAddress(AdminDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		boolean result = false;
		if (pDto != null && pDto.getApplicationId() > 0) {
			/**
			 * Aprrove the Communication address Details for the given
			 * application id
			 */
			if (pDto.getIsApprove() == 1 && pDto.getIsRejected() == 0) {
				result = AdminDAO.getInstance().uploadAdminComments(true, "", pDto.getApplicationId(),
						"tbl_communication_address");
				if (result) {
					updateAdminStatus(pDto.getApplicationId(), "COMMUNICATION ADDRESS", true, "");
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setReason(eKYCConstant.COMMUNICATION_ADDRESS_DETAILS_APPROVED);
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
				}
			}
			/**
			 * reject the Communication address Details for the given
			 * application id
			 */
			else if (pDto.getIsApprove() == 0 && pDto.getIsRejected() == 1) {
				result = AdminDAO.getInstance().uploadAdminComments(false, pDto.getComments(), pDto.getApplicationId(),
						"tbl_communication_address");
				if (result) {
					updateAdminStatus(pDto.getApplicationId(), "COMMUNICATION ADDRESS", false, pDto.getComments());
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setReason(eKYCConstant.COMMUNICATION_ADDRESS_DETAILS_REJECTED);
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
				}
			}
			/**
			 * Respond back to the front end no action is required or giving
			 * wrong parameter's
			 */
			else if ((pDto.getIsApprove() == 0 && pDto.getIsRejected() == 0)
					|| (pDto.getIsApprove() == 1 && pDto.getIsRejected() == 1)) {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.REJECT_OR_APPROVE);

			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.SOME_PARAMTERS_ARE_MISSING);
		}
		return response;
	}

	/**
	 * Method to respond the permanent address for the given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO respondPermanentAddress(AdminDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		boolean result = false;
		if (pDto != null && pDto.getApplicationId() > 0) {
			/**
			 * Aprrove the permanent address Details for the given application
			 * id
			 */
			if (pDto.getIsApprove() == 1 && pDto.getIsRejected() == 0) {
				result = AdminDAO.getInstance().uploadAdminComments(true, "", pDto.getApplicationId(),
						"tbl_permanent_address");
				if (result) {
					updateAdminStatus(pDto.getApplicationId(), "PERMANENT ADDRESS", true, "");
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setReason(eKYCConstant.PERMANENT_ADDRESS_DETAILS_APPROVED);
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
				}
			}
			/**
			 * reject the permanent address Details for the given application id
			 */
			else if (pDto.getIsApprove() == 0 && pDto.getIsRejected() == 1) {
				result = AdminDAO.getInstance().uploadAdminComments(false, pDto.getComments(), pDto.getApplicationId(),
						"tbl_permanent_address");
				if (result) {
					updateAdminStatus(pDto.getApplicationId(), "PERMANENT ADDRESS", false, pDto.getComments());
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setReason(eKYCConstant.PERMANENT_ADDRESS_DETAILS_REJECTED);
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
				}
			}
			/**
			 * Respond back to the front end no action is required or giving
			 * wrong parameter's
			 */
			else if ((pDto.getIsApprove() == 0 && pDto.getIsRejected() == 0)
					|| (pDto.getIsApprove() == 1 && pDto.getIsRejected() == 1)) {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.REJECT_OR_APPROVE);

			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.SOME_PARAMTERS_ARE_MISSING);
		}
		return response;
	}

	/**
	 * Method to repond back by admin for the given Application Id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO respondAttachementDetails(AdminDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		boolean result = false;
		if (pDto != null && pDto.getApplicationId() > 0) {
			/**
			 * Aprrove the permanent address Details for the given application
			 * id
			 */
			if (pDto.getIsApprove() == 1 && pDto.getIsRejected() == 0) {
				result = AdminDAO.getInstance().updateAdminCommentForAttachments(true, "", pDto.getApplicationId(),
						pDto.getAttachementType());
				if (result) {
					updateAdminStatus(pDto.getApplicationId(), pDto.getAttachementType() + "ATTACHEMENT", true, "");
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setReason(eKYCConstant.ATTACHEMENTS_DETAILS_APPROVED);
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
				}
			}
			/**
			 * reject the permanent address Details for the given application id
			 */
			else if (pDto.getIsApprove() == 0 && pDto.getIsRejected() == 1) {
				result = AdminDAO.getInstance().updateAdminCommentForAttachments(false, pDto.getComments(),
						pDto.getApplicationId(), pDto.getAttachementType());
				if (result) {
					updateAdminStatus(pDto.getApplicationId(), pDto.getAttachementType() + "ATTACHEMENT", false,
							pDto.getComments());
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setReason(eKYCConstant.ATTACHEMENTS_DETAILS_REJECTED);
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
				}
			}
			/**
			 * Respond back to the front end no action is required or giving
			 * wrong parameter's
			 */
			else if ((pDto.getIsApprove() == 0 && pDto.getIsRejected() == 0)
					|| (pDto.getIsApprove() == 1 && pDto.getIsRejected() == 1)) {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.REJECT_OR_APPROVE);

			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.SOME_PARAMTERS_ARE_MISSING);
		}
		return response;
	}

	/**
	 * To get the attached file for given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO getAttachedFileDetails(AdminDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		if (pDto.getApplicationId() > 0) {
			List<FileUploadDTO> result = AdminDAO.getUploadedFile(pDto.getApplicationId());
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
			response.setReason(eKYCConstant.SOME_PARAMTERS_ARE_MISSING);
		}
		return response;
	}

	/**
	 * method to start the application for given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @returnS
	 */
	public ResponseDTO startApplication(AdminDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		if (pDto != null && pDto.getApplicationId() > 0) {
			List<ApplicationLogDTO> result = AdminDAO.getInstance().checkApplicationStatus(pDto.getApplicationId());
			if (result != null && result.size() > 0) {
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setResult(result);
			} else {
				boolean isSuccessfull = AdminDAO.getInstance().startApplication(pDto);
				if (isSuccessfull) {
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setResult(null);
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
				}
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.SOME_PARAMTERS_ARE_MISSING);
		}
		return response;
	}

	/**
	 * Method to end the application for the given application Id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO endApplication(AdminDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		if (pDto != null && pDto.getApplicationId() > 0) {
			boolean isSuccessfull = AdminDAO.getInstance().endApplication(pDto);
			if (isSuccessfull) {
				AdminDAO.getInstance().updateInApplicationMaster(pDto);
				PersonalDetailsDTO userDetails = AdminDAO.getInstance().getUserDetails(pDto.getApplicationId());
				if (pDto.getIsApprove() == 1 && pDto.getIsRejected() == 0) {
					Utility.applicationApprovedMessage(userDetails.getEmail(), userDetails.getApplicant_name());
				} else if (pDto.getIsApprove() == 0 && pDto.getIsRejected() == 1) {
					List<ApplicationLogDTO> result = AdminDAO.getInstance().rejectedDocuments(pDto.getApplicationId());
					Utility.applicationRejectedMessage(userDetails.getEmail(), userDetails.getApplicant_name(), result);
				}
				/**
				 * Get applicant name and email address for the given
				 * application id
				 */
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setResult(null);
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.INTERNAL_SERVER_ERROR);
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.SOME_PARAMTERS_ARE_MISSING);
		}
		return response;
	}

	/**
	 * Method to get the application status for the given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO getApplicationStatus(AdminDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		if (pDto != null && pDto.getApplicationId() > 0) {
			List<ApplicationLogDTO> result = AdminDAO.getInstance().checkApplicationStatus(pDto.getApplicationId());
			if (result != null && result.size() > 0) {
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setResult(result);
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setResult(result);
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.SOME_PARAMTERS_ARE_MISSING);
		}
		return response;
	}

	/**
	 * Check and insert or update the admin Comments
	 * 
	 * @author GOWRI SANKAR R
	 * @param applicationId
	 * @param verificationModule
	 * @param isApproved
	 * @param notes
	 */
	public static void updateAdminStatus(int applicationId, String verificationModule, boolean isApproved,
			String notes) {
		int checkInserted = AdminDAO.getInstance().getAdminVerificationModules(applicationId, verificationModule);
		if (checkInserted > 0) {
			AdminDAO.getInstance().updateAdminStatus(applicationId, verificationModule, isApproved, notes);
		} else {
			AdminDAO.getInstance().insertAdminStatus(applicationId, verificationModule, isApproved, notes);
		}

	}

	/**
	 * Method to get the full Details about the given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param applicationId
	 */
	public static void getDetailsForApplicationID(int applicationId) {
		/**
		 * To get all records for given application id
		 */

	}

	/**
	 * Method to check the password for the admin User Id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO adminLogin(AdminDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		AdminDTO adminDetails = AdminDAO.getInstance().adminLogin(pDto);
		if (adminDetails != null) {
			String actualPassword = adminDetails.getPassword();
			String givenEncryptedPassword = Utility.PasswordEncryption(pDto.getPassword());
			if (actualPassword.equals(givenEncryptedPassword)) {
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.PASSWORD_INVALID);
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.ACCESS_DENIED);
		}
		return response;
	}
}
