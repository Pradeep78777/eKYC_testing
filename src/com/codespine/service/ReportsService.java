package com.codespine.service;

import com.codespine.dto.ReportsDTO;
import com.codespine.dto.ResponseDTO;
import com.codespine.util.eKYCConstant;

public class ReportsService {
	public static ReportsService ReportsService = null;

	public static ReportsService getInstance() {
		if (ReportsService == null) {
			ReportsService = new ReportsService();
		}
		return ReportsService;
	}

	/**
	 * Method to get the reports from the data base and generate the report
	 * 
	 * @author GOWRI SANKAR R
	 * @param pObject
	 * @return
	 */
	public ResponseDTO reportGeneration(ReportsDTO pObject) {
		ResponseDTO response = new ResponseDTO();
		if (pObject != null) {
			String queryParameter = " SELECT ";
			String tableApplicationMaster = "tbl_application_master";
			String tablePanMaster = "tbl_pancard_details";
			String tableBasicInformation = "tbl_account_holder_personal_details";
			String tableCommunicationAddress = "tbl_communication_address";
			String tablePermanentAddress = "tbl_permanent_address";
			String tableBankDetails = "tbl_bank_account_details";
			if (pObject.getUserDetails() != null && pObject.getUserDetails().length > 0) {
				String[] userDetails = pObject.getUserDetails();
				for (int itr = 0; itr < userDetails.length - 1; itr++) {
					String temp = userDetails[itr];
					queryParameter = queryParameter + tableApplicationMaster + "." + temp + ", ";
				}
			}
			if (pObject.getPanDetails() != null && pObject.getPanDetails().length > 0) {
				String[] panDetails = pObject.getPanDetails();
				for (int itr = 0; itr < panDetails.length - 1; itr++) {
					String temp = panDetails[itr];
					queryParameter = queryParameter + tablePanMaster + "." + temp + ", ";
				}
			}
			if (pObject.getBasicInformation() != null && pObject.getBasicInformation().length > 0) {
				String[] basicInformation = pObject.getBasicInformation();
				for (int itr = 0; itr < basicInformation.length - 1; itr++) {
					String temp = basicInformation[itr];
					queryParameter = queryParameter + tableBasicInformation + "." + temp + ", ";
				}
			}
			if (pObject.getCommunicationAddress() != null && pObject.getCommunicationAddress().length > 0) {
				String[] communicationAddress = pObject.getCommunicationAddress();
				for (int itr = 0; itr < communicationAddress.length - 1; itr++) {
					String temp = communicationAddress[itr];
					queryParameter = queryParameter + tableCommunicationAddress + "." + temp + ", ";
				}
			}
			if (pObject.getPermananentAddress() != null && pObject.getPermananentAddress().length > 0) {
				String[] permanentAddress = pObject.getPermananentAddress();
				for (int itr = 0; itr < permanentAddress.length - 1; itr++) {
					String temp = permanentAddress[itr];
					queryParameter = queryParameter + tablePermanentAddress + "." + temp + ", ";
				}
			}
			if (pObject.getBankDetails() != null && pObject.getBankDetails().length > 0) {
				String[] bankDetails = pObject.getBankDetails();
				for (int itr = 0; itr < bankDetails.length - 1; itr++) {
					String temp = bankDetails[itr];
					queryParameter = queryParameter + tableBankDetails + "." + temp + ", ";
				}
			}
			System.out.println(queryParameter);
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.INVALID_REQUEST);
		}
		return response;
	}
}
