package com.codespine.service;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.simple.JSONObject;

import com.codespine.data.AdminDAO;
import com.codespine.data.eKYCDAO;
import com.codespine.dto.AddressDTO;
import com.codespine.dto.AdminDTO;
import com.codespine.dto.ApplicationAttachementsDTO;
import com.codespine.dto.ApplicationLogDTO;
import com.codespine.dto.BankDetailsDTO;
import com.codespine.dto.ExchDetailsDTO;
import com.codespine.dto.FileUploadDTO;
import com.codespine.dto.PanCardDetailsDTO;
import com.codespine.dto.PerformanceDTO;
import com.codespine.dto.PersonalDetailsDTO;
import com.codespine.dto.ResponseDTO;
import com.codespine.dto.eKYCDTO;
import com.codespine.util.Utility;
import com.codespine.util.eKYCConstant;

@SuppressWarnings("unchecked")
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
			eKYCDAO.getInstance().updateApplicationStatus(pDto.getApplicationId(), eKYCConstant.PAN_CARD_VERIFIED);
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
			eKYCDAO.getInstance().updateApplicationStatus(pDto.getApplicationId(), eKYCConstant.BASIC_DETAILS_VERIFIED);
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
			eKYCDAO.getInstance().updateApplicationStatus(pDto.getApplicationId(), eKYCConstant.BANK_DETAILS_VERIFIED);
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
			eKYCDAO.getInstance().updateApplicationStatus(pDto.getApplicationId(),
					eKYCConstant.COMMUNICATION_ADDRESS_VERIFIED);
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
			eKYCDAO.getInstance().updateApplicationStatus(pDto.getApplicationId(),
					eKYCConstant.PERMANENT_ADDRESS_VERIFIED);
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
			eKYCDAO.getInstance().updateApplicationStatus(pDto.getApplicationId(), eKYCConstant.ATTACHEMENT_VERIFIED);
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

				/*
				 * get the uploaded ivr details from the data base
				 */
				JSONObject ivrResult = eKYCDAO.getInstance().getIvrDetails(pDto.getApplicationId());
				if (ivrResult != null) {
					FileUploadDTO tempDto = new FileUploadDTO();
					tempDto.setProofType("IVP");
					tempDto.setProof((String) ivrResult.get("ivr_image"));
					result.add(tempDto);
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setResult(result);
				} else {
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setResult(result);
				}
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
				eKYCDAO.getInstance().updateApplicationStatus(pDto.getApplicationId(),
						eKYCConstant.APPLICATION_STARTED_BY_ADMIN);
				/*
				 * Set the Admin name in data base
				 */
				eKYCDAO.getInstance().updateAdminName(pDto.getApplicationId(), pDto.getAdminName());
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setResult(result);
			} else {
				boolean isSuccessfull = AdminDAO.getInstance().startApplication(pDto);
				if (isSuccessfull) {
					eKYCDAO.getInstance().updateApplicationStatus(pDto.getApplicationId(),
							eKYCConstant.APPLICATION_STARTED_BY_ADMIN);
					/*
					 * Set Admin name in data base
					 */
					eKYCDAO.getInstance().updateAdminName(pDto.getApplicationId(), pDto.getAdminName());
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
			// updateAdminStatus
			if (isSuccessfull) {
				AdminDAO.getInstance().updateInApplicationMaster(pDto);
				PersonalDetailsDTO userDetails = AdminDAO.getInstance().getUserDetails(pDto.getApplicationId());
				eKYCDAO.getInstance().updateApplicationStatus(pDto.getApplicationId(),
						eKYCConstant.APPLICATION_ENDED_BY_ADMIN);
				if (pDto.getIsApprove() == 1 && pDto.getIsRejected() == 0) {
					Utility.applicationApprovedMessage(userDetails.getEmail(), userDetails.getApplicant_name());
				} else if (pDto.getIsApprove() == 0 && pDto.getIsRejected() == 1) {
					List<ApplicationLogDTO> result = AdminDAO.getInstance().rejectedDocuments(pDto.getApplicationId());
					Utility.applicationRejectedMessage(userDetails.getEmail(), userDetails.getApplicant_name(), result);

					/**
					 * if the second time rejected set retify count as 0
					 */
					AdminDAO.getInstance().updateRetifyCount(pDto.getApplicationId(), 0);
				}
				/**
				 * Get applicant name and email address for the given
				 * application id
				 */
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setReason(eKYCConstant.SUCCESS_MSG);
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
			JSONObject result = new JSONObject();
			String actualPassword = adminDetails.getPassword();
			String givenEncryptedPassword = Utility.PasswordEncryption(pDto.getPassword());
			if (actualPassword.equals(givenEncryptedPassword)) {
				result.put("adminName", adminDetails.getName());
				result.put("adminDesignation", adminDetails.getDesignation());
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setResult(result);
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

	/**
	 * Method to get all pending records
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO getPendingRecords(AdminDTO pDto) {
		List<JSONObject> finalRespone = new ArrayList<JSONObject>();
		// List<JSONObject> finalResult = new ArrayList<JSONObject>();
		JSONObject result = new JSONObject();
		// JSONObject tempJson = null;
		ResponseDTO response = new ResponseDTO();
		List<PersonalDetailsDTO> allUserRecords = AdminDAO.getInstance().getUserRecords();
		if (allUserRecords != null && allUserRecords.size() > 0) {
			for (int itr = 0; itr < allUserRecords.size(); itr++) {
				result = new JSONObject();
				// tempJson = new JSONObject();
				// finalResult = new ArrayList<JSONObject>();
				PersonalDetailsDTO tempDto = allUserRecords.get(itr);
				int applicationId = tempDto.getApplication_id();
				int applicationStatus = tempDto.getApplicationStatus();
				result.put("applicationId", applicationId);
				result.put("applicationStatusTemp", applicationStatus);
				if (applicationStatus == 2 || applicationStatus < 2) {
					result.put("ApplicationStatus", tempDto);
				} else if (applicationStatus > 2) {
					result.put("ApplicationStatus", tempDto);
					if (applicationStatus == 3) {
						PanCardDetailsDTO pancardDetails = AdminDAO.getInstance().getPanCardDetails(applicationId);
						result.put("panCardDetails", pancardDetails);

					} else if (applicationStatus == 4) {

						PanCardDetailsDTO pancardDetails = AdminDAO.getInstance().getPanCardDetails(applicationId);
						result.put("panCardDetails", pancardDetails);

						PersonalDetailsDTO personalDetailsDTO = AdminDAO.getInstance()
								.getPersonalDetails(applicationId);
						result.put("basicInformationDetails", personalDetailsDTO);

					} else if (applicationStatus == 5) {

						PanCardDetailsDTO pancardDetails = AdminDAO.getInstance().getPanCardDetails(applicationId);
						result.put("panCardDetails", pancardDetails);

						PersonalDetailsDTO personalDetailsDTO = AdminDAO.getInstance()
								.getPersonalDetails(applicationId);
						result.put("basicInformationDetails", personalDetailsDTO);

						AddressDTO communicationAddress = AdminDAO.getInstance().getCommunicationAddress(applicationId);
						result.put("communicationAddress", communicationAddress);

					} else if (applicationStatus == 6) {

						PanCardDetailsDTO pancardDetails = AdminDAO.getInstance().getPanCardDetails(applicationId);
						result.put("panCardDetails", pancardDetails);

						PersonalDetailsDTO personalDetailsDTO = AdminDAO.getInstance()
								.getPersonalDetails(applicationId);
						result.put("basicInformationDetails", personalDetailsDTO);

						AddressDTO communicationAddress = AdminDAO.getInstance().getCommunicationAddress(applicationId);
						result.put("communicationAddress", communicationAddress);

						AddressDTO permanentAddress = AdminDAO.getInstance().getPermanentAddress(applicationId);
						result.put("permanentAddress", permanentAddress);

					} else if (applicationStatus == 7) {
						PanCardDetailsDTO pancardDetails = AdminDAO.getInstance().getPanCardDetails(applicationId);
						result.put("panCardDetails", pancardDetails);

						PersonalDetailsDTO personalDetailsDTO = AdminDAO.getInstance()
								.getPersonalDetails(applicationId);
						result.put("basicInformationDetails", personalDetailsDTO);

						AddressDTO communicationAddress = AdminDAO.getInstance().getCommunicationAddress(applicationId);
						result.put("communicationAddress", communicationAddress);

						AddressDTO permanentAddress = AdminDAO.getInstance().getPermanentAddress(applicationId);
						result.put("permanentAddress", permanentAddress);

						BankDetailsDTO bankDetails = AdminDAO.getInstance().getBankDetails(applicationId);
						result.put("bankDetails", bankDetails);

					} else if (applicationStatus == 8) {

						PanCardDetailsDTO pancardDetails = AdminDAO.getInstance().getPanCardDetails(applicationId);
						result.put("panCardDetails", pancardDetails);

						PersonalDetailsDTO personalDetailsDTO = AdminDAO.getInstance()
								.getPersonalDetails(applicationId);
						result.put("basicInformationDetails", personalDetailsDTO);

						AddressDTO communicationAddress = AdminDAO.getInstance().getCommunicationAddress(applicationId);
						result.put("communicationAddress", communicationAddress);

						AddressDTO permanentAddress = AdminDAO.getInstance().getPermanentAddress(applicationId);
						result.put("permanentAddress", permanentAddress);

						BankDetailsDTO bankDetails = AdminDAO.getInstance().getBankDetails(applicationId);
						result.put("bankDetails", bankDetails);

						ExchDetailsDTO exchDetails = AdminDAO.getInstance().getExchDetails(applicationId);
						result.put("exchDetails", exchDetails);

					} else if (applicationStatus == 9) {
						PanCardDetailsDTO pancardDetails = AdminDAO.getInstance().getPanCardDetails(applicationId);
						result.put("panCardDetails", pancardDetails);

						PersonalDetailsDTO personalDetailsDTO = AdminDAO.getInstance()
								.getPersonalDetails(applicationId);
						result.put("basicInformationDetails", personalDetailsDTO);

						AddressDTO communicationAddress = AdminDAO.getInstance().getCommunicationAddress(applicationId);
						result.put("communicationAddress", communicationAddress);

						AddressDTO permanentAddress = AdminDAO.getInstance().getPermanentAddress(applicationId);
						result.put("permanentAddress", permanentAddress);

						BankDetailsDTO bankDetails = AdminDAO.getInstance().getBankDetails(applicationId);
						result.put("bankDetails", bankDetails);

						ExchDetailsDTO exchDetails = AdminDAO.getInstance().getExchDetails(applicationId);
						result.put("exchDetails", exchDetails);

						List<ApplicationAttachementsDTO> applicationAttachements = AdminDAO.getInstance()
								.getApplicationAttachementsDetails(applicationId);
						result.put("applicationAttachements", applicationAttachements);
					} else if (applicationStatus > 9) {
						PanCardDetailsDTO pancardDetails = AdminDAO.getInstance().getPanCardDetails(applicationId);
						result.put("panCardDetails", pancardDetails);

						PersonalDetailsDTO personalDetailsDTO = AdminDAO.getInstance()
								.getPersonalDetails(applicationId);
						result.put("basicInformationDetails", personalDetailsDTO);

						AddressDTO communicationAddress = AdminDAO.getInstance().getCommunicationAddress(applicationId);
						result.put("communicationAddress", communicationAddress);

						AddressDTO permanentAddress = AdminDAO.getInstance().getPermanentAddress(applicationId);
						result.put("permanentAddress", permanentAddress);

						BankDetailsDTO bankDetails = AdminDAO.getInstance().getBankDetails(applicationId);
						result.put("bankDetails", bankDetails);

						ExchDetailsDTO exchDetails = AdminDAO.getInstance().getExchDetails(applicationId);
						result.put("exchDetails", exchDetails);

						List<ApplicationAttachementsDTO> applicationAttachements = AdminDAO.getInstance()
								.getApplicationAttachementsDetails(applicationId);
						result.put("applicationAttachements", applicationAttachements);

						JSONObject ipvResult = eKYCDAO.getInstance().getIvrDetails(applicationId);
						result.put("ipv", ipvResult);
					}
				}
				finalRespone.add(result);
			}
			response.setStatus(eKYCConstant.SUCCESS_STATUS);
			response.setMessage(eKYCConstant.SUCCESS_MSG);
			response.setResult(finalRespone);
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.NO_RECORD_FOUND);
		}
		return response;
	}

	public ResponseDTO postDataToBackOffice() {
		ResponseDTO response = new ResponseDTO();
		return response;
	}

	/**
	 * Method to get the rejected documnets and its commets
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO getRejectedDocuments(AdminDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		if (pDto != null && pDto.getApplicationId() > 0) {
			List<ApplicationLogDTO> result = AdminDAO.getInstance().rejectedDocuments(pDto.getApplicationId());
			if (result != null && result.size() > 0) {
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setResult(result);
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
		return null;
	}

	public ResponseDTO getExcelResult() {
		ResponseDTO response = new ResponseDTO();
		try {
			// if (pDto != null && pDto.getApplicationId() > 0) {

			List<Integer> applicationIdList = new ArrayList<Integer>();
			applicationIdList.add(1);
			// applicationIdList.add(2);
			// List<ek>
			String filename = "C://Users//GOWRI SANKAR R//Desktop//Muthu pc//Check.xlsx";
			HSSFWorkbook workbook = new HSSFWorkbook();
			// invoking creatSheet() method and passing the name of the
			// sheet to
			// be created
			HSSFSheet sheet = workbook.createSheet("Reports");
			// creating the 0th row using the createRow() method
			HSSFRow rowhead = sheet.createRow((short) 0);
			// creating cell by using the createCell() method and setting
			// the
			// values to the cell by using the setCellValue() method
			rowhead.createCell(0).setCellValue("Id");
			rowhead.createCell(1).setCellValue("Application id");
			rowhead.createCell(2).setCellValue("Name");
			rowhead.createCell(3).setCellValue("Mobile number");
			rowhead.createCell(4).setCellValue("Pan");
			rowhead.createCell(5).setCellValue("Status");
			rowhead.createCell(6).setCellValue("Dob");
			rowhead.createCell(7).setCellValue("Fathers name");
			rowhead.createCell(8).setCellValue("Mothers name");
			rowhead.createCell(9).setCellValue("Gender");
			rowhead.createCell(10).setCellValue("Marital status");
			rowhead.createCell(11).setCellValue("Occupation");
			rowhead.createCell(12).setCellValue("Politically exposed Person");
			rowhead.createCell(13).setCellValue("Trading Experience");

			int size = applicationIdList.size();
			for (int itr = 0; itr < size; itr++) {
				int applicationId = applicationIdList.get(itr);
				eKYCDTO userApplicationDetails = eKYCService.getInstance().finalPDFGenerator(applicationId);
				HSSFRow row = sheet.createRow((short) itr + 1);
				row.createCell(0).setCellValue(itr + 1);
				row.createCell(1).setCellValue(applicationId);
				row.createCell(2).setCellValue(userApplicationDetails.getPanCardDetailsDTO().getApplicant_name());
				row.createCell(3).setCellValue(userApplicationDetails.getApplicationMasterDTO().getMobile_number());
				row.createCell(4)
						.setCellValue(userApplicationDetails.getPanCardDetailsDTO().getPan_card().toUpperCase());
				// int applicationSatus =
				// userApplicationDetails.getApplicationMasterDTO().getApplication_status();
				// String status = "";
				// if(applicationSatus == eKYCConstant.re){
				//
				// }
				row.createCell(5).setCellValue("Status");
				row.createCell(6).setCellValue(userApplicationDetails.getPanCardDetailsDTO().getDob());
				row.createCell(7).setCellValue(userApplicationDetails.getPersonalDetailsDTO().getFathersName());
				row.createCell(8).setCellValue(userApplicationDetails.getPersonalDetailsDTO().getMothersName());
				row.createCell(9).setCellValue(userApplicationDetails.getPersonalDetailsDTO().getGender());
				row.createCell(10).setCellValue(userApplicationDetails.getPersonalDetailsDTO().getMarital_status());
				row.createCell(11).setCellValue(userApplicationDetails.getPersonalDetailsDTO().getOccupation());
				row.createCell(12)
						.setCellValue(userApplicationDetails.getPersonalDetailsDTO().getPolitically_exposed());
				row.createCell(13).setCellValue(userApplicationDetails.getPersonalDetailsDTO().getTrading_experience());

			}
			FileOutputStream fileOut = new FileOutputStream(filename);
			workbook.write(fileOut);
			// closing the Stream
			fileOut.close();
			// closing the workbook
			workbook.close();
			// } else {
			// response.setStatus(eKYCConstant.FAILED_STATUS);
			// response.setMessage(eKYCConstant.FAILED_MSG);
			// response.setReason(eKYCConstant.APPLICATION_ID_ERROR);
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * Method to get the completed records from data base
	 * 
	 * @author GOWRI SANKAR R
	 * @return
	 */
	public ResponseDTO getCompletedRecords() {
		ResponseDTO response = new ResponseDTO();
		List<PersonalDetailsDTO> result = AdminDAO.getInstance().getCompletedRecords();
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
	 * Method to get the report list for the given status
	 * 
	 * @author GOWRI SANKAR R
	 * @param status
	 * @return
	 * 
	 */
	public ResponseDTO getRecordsDetails(AdminDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		if (pDto != null) {
			String startDate = pDto.getStartDate();
			String endDate = pDto.getEndDate();
			String applicationStatus = pDto.getStatus();
			if (startDate != null && endDate != null && applicationStatus != null && !startDate.equalsIgnoreCase("")
					&& !endDate.equalsIgnoreCase("") && !applicationStatus.equalsIgnoreCase("") && !startDate.isEmpty()
					&& !endDate.isEmpty() && !applicationStatus.isEmpty()) {
				List<PersonalDetailsDTO> result = new ArrayList<PersonalDetailsDTO>();
				if (applicationStatus.equalsIgnoreCase("In Process") || applicationStatus.equalsIgnoreCase("Review")) {
					result = AdminDAO.getInstance().getInprogressRecordsByTime(pDto);
				}
				if (applicationStatus.equalsIgnoreCase("Completed")) {
					result = AdminDAO.getInstance().getCompletedRecordsWithTime(pDto);
				}
				if (applicationStatus.equalsIgnoreCase("Rejected")) {
					result = AdminDAO.getInstance().getRejectedListByTime(pDto);
				}
				if (applicationStatus.equalsIgnoreCase("Rectifi")) {
					result = AdminDAO.getInstance().getRetifyListByTime(pDto);
				}

				/*
				 * Check the list and send the response
				 */
				if (result != null && result.size() > 0) {
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setResult(result);
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.NO_RECORD_FOUND);
				}
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.INVALID_REQUEST);
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.INVALID_REQUEST);
		}
		return response;
	}

	/**
	 * Method to get the record for given status without time
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO getRecordsDetailsWiti(AdminDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		if (pDto != null) {
			String startDate = pDto.getStartDate();
			String endDate = pDto.getEndDate();
			String applicationStatus = pDto.getStatus();
			if (startDate != null && endDate != null && applicationStatus != null && !startDate.equalsIgnoreCase("")
					&& !endDate.equalsIgnoreCase("") && !applicationStatus.equalsIgnoreCase("") && !startDate.isEmpty()
					&& !endDate.isEmpty() && !applicationStatus.isEmpty()) {
				List<PersonalDetailsDTO> result = new ArrayList<PersonalDetailsDTO>();
				if (applicationStatus.equalsIgnoreCase("In Process") || applicationStatus.equalsIgnoreCase("Review")) {
					result = AdminDAO.getInstance().getInprogressRecordsByTime(pDto);
				}
				if (applicationStatus.equalsIgnoreCase("Completed")) {
					result = AdminDAO.getInstance().getCompletedRecordsWithTime(pDto);
				}
				if (applicationStatus.equalsIgnoreCase("Rejected")) {
					result = AdminDAO.getInstance().getRejectedListByTime(pDto);
				}
				/*
				 * Check the list and send the response
				 */
				if (result != null && result.size() > 0) {
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setResult(result);
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.NO_RECORD_FOUND);
				}
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.INVALID_REQUEST);
			}
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.INVALID_REQUEST);
		}
		return response;
	}

	/**
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public ResponseDTO getExcelDownloadLink(AdminDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		try {
			String startDate = pDto.getStartDate();
			String endDate = pDto.getEndDate();
			String applicationStatus = pDto.getStatus();
			List<PersonalDetailsDTO> result = null;
			if (startDate != null && endDate != null && applicationStatus != null && !startDate.equalsIgnoreCase("")
					&& !endDate.equalsIgnoreCase("") && !applicationStatus.equalsIgnoreCase("") && !startDate.isEmpty()
					&& !endDate.isEmpty() && !applicationStatus.isEmpty()) {
				result = new ArrayList<PersonalDetailsDTO>();
				if (applicationStatus.equalsIgnoreCase("In Process") || applicationStatus.equalsIgnoreCase("Review")) {
					result = AdminDAO.getInstance().getInprogressRecordsByTime(pDto);
				}
				if (applicationStatus.equalsIgnoreCase("Completed")) {
					result = AdminDAO.getInstance().getCompletedRecordsWithTime(pDto);
				}
				if (applicationStatus.equalsIgnoreCase("Rejected")) {
					result = AdminDAO.getInstance().getRejectedListByTime(pDto);
				}
				if (applicationStatus.equalsIgnoreCase("Rectifi")) {
					result = AdminDAO.getInstance().getRetifyListByTime(pDto);
				}
			}
			if (result != null && result.size() > 0) {
				String dummyFileName = System.currentTimeMillis() + "Reports.xlsx";
				String filename = eKYCConstant.PROJ_DIR + eKYCConstant.UPLOADS_DIR + "Reports//" + dummyFileName;
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet("Reports");
				HSSFRow rowhead = sheet.createRow((short) 0);
				rowhead.createCell(0).setCellValue("Id");
				rowhead.createCell(1).setCellValue("Application id");
				rowhead.createCell(2).setCellValue("Name");
				rowhead.createCell(3).setCellValue("Mobile number");
				rowhead.createCell(4).setCellValue("Pan");
				rowhead.createCell(5).setCellValue("Status");
				rowhead.createCell(6).setCellValue("Dob");
				rowhead.createCell(7).setCellValue("Fathers name");
				rowhead.createCell(8).setCellValue("Mothers name");
				rowhead.createCell(9).setCellValue("Gender");
				rowhead.createCell(10).setCellValue("Marital status");
				rowhead.createCell(11).setCellValue("Occupation");
				rowhead.createCell(12).setCellValue("Politically exposed Person");
				rowhead.createCell(13).setCellValue("Trading Experience");
				rowhead.createCell(14).setCellValue("Created At");
				rowhead.createCell(15).setCellValue("Last Updated At");
				rowhead.createCell(16).setCellValue("Refferal Code");
				rowhead.createCell(17).setCellValue("Esigned");
				rowhead.createCell(18).setCellValue("Reviewed by");
				int size = result.size();
				for (int itr = 0; itr < size; itr++) {
					PersonalDetailsDTO personal = result.get(itr);
					HSSFRow row = sheet.createRow((short) itr + 1);
					row.createCell(0).setCellValue(itr + 1);
					row.createCell(1).setCellValue(personal.getApplication_id());
					row.createCell(2).setCellValue(personal.getApplicant_name());
					row.createCell(3).setCellValue(personal.getMobile_number());
					row.createCell(4).setCellValue(personal.getPancard().toUpperCase());
					row.createCell(5).setCellValue(personal.getStatus());
					row.createCell(6).setCellValue(personal.getDob());
					row.createCell(7).setCellValue(personal.getFathersName());
					row.createCell(8).setCellValue(personal.getMothersName());
					row.createCell(9).setCellValue(personal.getGender());
					row.createCell(10).setCellValue(personal.getMarital_status());
					row.createCell(11).setCellValue(personal.getOccupation());
					row.createCell(12).setCellValue(personal.getPolitically_exposed());
					row.createCell(13).setCellValue(personal.getTrading_experience());
					row.createCell(14).setCellValue(personal.getCreatedAt());
					row.createCell(15).setCellValue(personal.getLastUpdatedAt());
					row.createCell(16).setCellValue(personal.getReffCode());
					row.createCell(17).setCellValue(personal.getEsign_document());
					row.createCell(18).setCellValue(personal.getReviewedBy());
				}
				FileOutputStream fileOut = new FileOutputStream(filename);
				workbook.write(fileOut);
				fileOut.close();
				workbook.close();
				String fileUrl = eKYCConstant.SITE_URL_FILE + eKYCConstant.UPLOADS_DIR + "Reports//" + dummyFileName;
				JSONObject originalResult = new JSONObject();
				originalResult.put("csvUrl", fileUrl);
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setResult(originalResult);
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * @author user
	 * @param pDto
	 * @return
	 */
	public ResponseDTO PerformanceChart(PerformanceDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		/*
		 * 
		 */
		List<String> oneMonthDates = new ArrayList<String>();
		Calendar start = Calendar.getInstance();
		start.add(Calendar.DATE, -30);
		Calendar end = Calendar.getInstance();
		for (Calendar date = start; date.before(end); date.add(Calendar.DATE, 1)) {
			String modifiedDate = new SimpleDateFormat("dd-MM-yyyy").format(date.getTime());
			oneMonthDates.add(modifiedDate);
		}
		List<PerformanceDTO> result = AdminDAO.getInstance().PerformanceChart(oneMonthDates);
		List<PerformanceDTO> approvedresult = AdminDAO.getInstance().PerformanceChartapproved(oneMonthDates);
		List<PerformanceDTO> signedresult = AdminDAO.getInstance().PerformanceChartsigned(oneMonthDates);
		JSONObject res = new JSONObject();
		res.put("total_record", result);
		res.put("approved", approvedresult);
		res.put("signed", signedresult);
		if (result != null && result.size() > 0) {
			response.setStatus(eKYCConstant.SUCCESS_STATUS);
			response.setMessage(eKYCConstant.SUCCESS_MSG);
			response.setReason(eKYCConstant.SUCCESS_MSG);
			response.setResult(res);
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.FAILED_MSG);
		}
		return response;
	}

	/**
	 * Method to get the report list from the data base
	 * 
	 * @author GOWRI SANKAR R
	 * @return
	 */
	public ResponseDTO getUserReportList(AdminDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		try {
			if (pDto != null) {
				List<PersonalDetailsDTO> result = null;
				if (pDto.getStartDate() != null && pDto.getEndDate() != null && !pDto.getStartDate().isEmpty()
						&& !pDto.getEndDate().isEmpty()) {
					/*
					 * Get the Report list for the given date
					 */
					result = AdminDAO.getInstance().getUserReportListByTime(pDto.getStartDate(), pDto.getEndDate());
					if (result != null && result.size() > 0) {
						response.setStatus(eKYCConstant.SUCCESS_STATUS);
						response.setMessage(eKYCConstant.SUCCESS_MSG);
						response.setResult(result);
					} else {
						response.setStatus(eKYCConstant.FAILED_STATUS);
						response.setMessage(eKYCConstant.FAILED_MSG);
						response.setReason(eKYCConstant.NO_RECORD_FOUND);
					}
				} else {
					/*
					 * Send the last updated 100 member from data base
					 */
					result = AdminDAO.getInstance().getUserReportList();
					if (result != null && result.size() > 0) {
						response.setStatus(eKYCConstant.SUCCESS_STATUS);
						response.setMessage(eKYCConstant.SUCCESS_MSG);
						response.setResult(result);
					} else {
						response.setStatus(eKYCConstant.FAILED_STATUS);
						response.setMessage(eKYCConstant.FAILED_MSG);
						response.setReason(eKYCConstant.NO_RECORD_FOUND);
					}
				}
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
				response.setReason(eKYCConstant.INVALID_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}
