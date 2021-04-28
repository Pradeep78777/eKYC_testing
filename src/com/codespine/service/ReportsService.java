package com.codespine.service;

import java.util.ArrayList;
import java.util.List;

import com.codespine.data.ResportsDAO;
import com.codespine.dto.AdminDTO;
import com.codespine.dto.ApplicationMasterDTO;
import com.codespine.dto.ReportColumnDTO;
import com.codespine.dto.ReportListDTO;
import com.codespine.dto.ReportOutputDTO;
import com.codespine.dto.ReportsDTO;
import com.codespine.dto.ResponseDTO;
import com.codespine.util.StringUtil;
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
		List<String> tableNames = new ArrayList<>();
		List<String> columnNames = new ArrayList<String>();
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT ");
		String tableApplicationMaster = "tbl_application_master";
		String tablePanMaster = "tbl_pancard_details";
		String tableBasicInformation = "tbl_account_holder_personal_details";
		String tableCommunicationAddress = "tbl_communication_address";
		String tablePermanentAddress = "tbl_permanent_address";
		String tableBankDetails = "tbl_bank_account_details";
		String tableEXCHSegment = "tbl_exch_segments";
		String tableattachement = "tbl_application_attachements";

		if (pObject != null) {
			if (StringUtil.isListNotNullOrEmpty(pObject.getUserDetails())) {
				String searchText = "";
				for (ReportColumnDTO reportColumnDTO : pObject.getUserDetails()) {
					if (reportColumnDTO != null) {
						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
							List<String> columnName = StringUtil
									.splitOnlyNonEmptyAndUndefined(reportColumnDTO.getColumns(), ",");
							int size = columnName.size();
							for (int i = 0; i < size; i++) {
								if (StringUtil.isNotNullOrEmpty(columnName.get(i))
										&& !StringUtil.isEqual(columnName.get(i), "undefined")) {
									columnNames.add(columnName.get(i));
									searchText += tableApplicationMaster + "." + columnName.get(i);
									if ((i + 1) < size) {
										searchText = searchText + ",";
									}
								}
							}

						}
					}
				}
				if (StringUtil.isNotNullOrEmpty(searchText)) {
					if (!tableNames.contains(tableApplicationMaster)) {
						tableNames.add(tableApplicationMaster);
					}
					queryString.append(searchText);
				} else {
					if (!tableNames.contains(tableApplicationMaster)) {
						tableNames.add(tableApplicationMaster);
					}
					searchText = "tbl_application_master.mobile_number";
					columnNames.add("mobile_number");
					queryString.append(searchText);
				}
			} else {
				if (!tableNames.contains(tableApplicationMaster)) {
					tableNames.add(tableApplicationMaster);
				}
				String searchText = "tbl_application_master.mobile_number";
				columnNames.add("mobile_number");
				queryString.append(searchText);
			}
			if (StringUtil.isListNotNullOrEmpty(pObject.getPanDetails())) {
				String searchText = "";
				for (ReportColumnDTO reportColumnDTO : pObject.getPanDetails()) {
					if (reportColumnDTO != null) {
						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
							List<String> columnName = StringUtil
									.splitOnlyNonEmptyAndUndefined(reportColumnDTO.getColumns(), ",");
							int size = columnName.size();
							for (int i = 0; i < size; i++) {
								if (StringUtil.isNotNullOrEmpty(columnName.get(i))
										&& !StringUtil.isEqual(columnName.get(i), "undefined")) {
									columnNames.add(columnName.get(i));
									searchText += tablePanMaster + "." + columnName.get(i);
									if ((i + 1) < size) {
										searchText = searchText + ",";
									}
								}
							}
						}
					}
				}
				if (StringUtil.isNotNullOrEmpty(searchText)) {
					if (!tableNames.contains(tablePanMaster)) {
						tableNames.add(tablePanMaster);
					}
					queryString.append("," + searchText);
				}
			} // signed_date
			if (StringUtil.isListNotNullOrEmpty(pObject.getAttachemntDetail())) {
				String searchText = "";
				for (ReportColumnDTO reportColumnDTO : pObject.getAttachemntDetail()) {
					if (reportColumnDTO != null) {
						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
							List<String> columnName = StringUtil
									.splitOnlyNonEmptyAndUndefined(reportColumnDTO.getColumns(), ",");
							int size = columnName.size();
							for (int i = 0; i < size; i++) {
								if (StringUtil.isNotNullOrEmpty(columnName.get(i))
										&& !StringUtil.isEqual(columnName.get(i), "undefined")) {
									if (StringUtil.isEqual(columnName.get(i), "signed_date")) {
										columnNames.add(columnName.get(i));
										searchText += tableattachement + "1" + "." + "last_update" + " as "
												+ columnName.get(i);
										if ((i + 1) < size) {
											searchText = searchText + ",";
										}
									} else {
										columnNames.add(columnName.get(i));
										searchText += tableattachement + "." + columnName.get(i);
										if ((i + 1) < size) {
											searchText = searchText + ",";
										}
									}
								}
							}
						}
					}
				}
				if (StringUtil.isNotNullOrEmpty(searchText)) {
					if (!tableNames.contains(tableattachement)) {
						tableNames.add(tableattachement);
						tableNames.add(tableattachement + "1");
					}
					queryString.append("," + searchText);
				}
			}
			if (StringUtil.isListNotNullOrEmpty(pObject.getBasicInformation())) {
				String searchText = "";
				for (ReportColumnDTO reportColumnDTO : pObject.getBasicInformation()) {
					if (reportColumnDTO != null) {
						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
							List<String> columnName = StringUtil
									.splitOnlyNonEmptyAndUndefined(reportColumnDTO.getColumns(), ",");
							int size = columnName.size();
							for (int i = 0; i < size; i++) {
								if (StringUtil.isNotNullOrEmpty(columnName.get(i))
										&& !StringUtil.isEqual(columnName.get(i), "undefined")) {
									columnNames.add(columnName.get(i));
									searchText += tableBasicInformation + "." + columnName.get(i);
									if ((i + 1) < size) {
										searchText = searchText + ",";
									}
								}
							}
						}
					}
				}
				if (StringUtil.isNotNullOrEmpty(searchText)) {
					if (!tableNames.contains(tableBasicInformation)) {
						tableNames.add(tableBasicInformation);
					}
					queryString.append("," + searchText);
				}
			}
			if (StringUtil.isListNotNullOrEmpty(pObject.getCommunicationAddress())) {
				String searchText = "";
				for (ReportColumnDTO reportColumnDTO : pObject.getCommunicationAddress()) {
					if (reportColumnDTO != null) {
						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
							List<String> columnName = StringUtil
									.splitOnlyNonEmptyAndUndefined(reportColumnDTO.getColumns(), ",");
							int size = columnName.size();
							for (int i = 0; i < size; i++) {
								if (StringUtil.isNotNullOrEmpty(columnName.get(i))
										&& !StringUtil.isEqual(columnName.get(i), "undefined")) {
									columnNames.add(columnName.get(i));
									searchText += tableCommunicationAddress + "." + columnName.get(i);
									if ((i + 1) < size) {
										searchText = searchText + ",";
									}
								}
							}
						}
					}
				}
				if (StringUtil.isNotNullOrEmpty(searchText)) {
					if (!tableNames.contains(tableCommunicationAddress)) {
						tableNames.add(tableCommunicationAddress);
					}
					queryString.append("," + searchText);
				}
			}
			if (StringUtil.isListNotNullOrEmpty(pObject.getPermananentAddress())) {
				String searchText = "";
				for (ReportColumnDTO reportColumnDTO : pObject.getPermananentAddress()) {
					if (reportColumnDTO != null) {
						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
							List<String> columnName = StringUtil
									.splitOnlyNonEmptyAndUndefined(reportColumnDTO.getColumns(), ",");
							int size = columnName.size();
							for (int i = 0; i < size; i++) {
								if (StringUtil.isNotNullOrEmpty(columnName.get(i))
										&& !StringUtil.isEqual(columnName.get(i), "undefined")) {
									columnNames.add(columnName.get(i));
									searchText += tablePermanentAddress + "." + columnName.get(i);
									if ((i + 1) < size) {
										searchText = searchText + ",";
									}
								}
							}
						}
					}
				}
				if (StringUtil.isNotNullOrEmpty(searchText)) {
					if (!tableNames.contains(tablePermanentAddress)) {
						tableNames.add(tablePermanentAddress);
					}
					queryString.append("," + searchText);
				}
			}
			if (StringUtil.isListNotNullOrEmpty(pObject.getBankDetails())) {
				String searchText = "";
				for (ReportColumnDTO reportColumnDTO : pObject.getBankDetails()) {
					if (reportColumnDTO != null) {
						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
							List<String> columnName = StringUtil
									.splitOnlyNonEmptyAndUndefined(reportColumnDTO.getColumns(), ",");
							int size = columnName.size();
							for (int i = 0; i < size; i++) {
								if (StringUtil.isNotNullOrEmpty(columnName.get(i))
										&& !StringUtil.isEqual(columnName.get(i), "undefined")) {
									columnNames.add(columnName.get(i));
									searchText += tableBankDetails + "." + columnName.get(i);
									if ((i + 1) < size) {
										searchText = searchText + ",";
									}
								}
							}
						}
					}
				}
				if (StringUtil.isNotNullOrEmpty(searchText)) {
					if (!tableNames.contains(tableBankDetails)) {
						tableNames.add(tableBankDetails);
					}
					queryString.append("," + searchText);
				}
			}
			if (StringUtil.isListNotNullOrEmpty(pObject.getTradingPreferences())) {
				String searchText = "";
				for (ReportColumnDTO reportColumnDTO : pObject.getTradingPreferences()) {
					if (reportColumnDTO != null) {
						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
							List<String> columnName = StringUtil
									.splitOnlyNonEmptyAndUndefined(reportColumnDTO.getColumns(), ",");
							int size = columnName.size();
							for (int i = 0; i < size; i++) {
								if (StringUtil.isNotNullOrEmpty(columnName.get(i))
										&& !StringUtil.isEqual(columnName.get(i), "undefined")) {
									columnNames.add(columnName.get(i));
									searchText += tableEXCHSegment + "." + columnName.get(i);
									if ((i + 1) < size) {
										searchText = searchText + ",";
									}
								}
							}
						}
					}
				}
				if (StringUtil.isNotNullOrEmpty(searchText)) {
					if (!tableNames.contains(tableEXCHSegment)) {
						tableNames.add(tableEXCHSegment);
					}
					queryString.append("," + searchText);
				}
			}
			if (StringUtil.isListNotNullOrEmpty(pObject.geteSign())) {
				String searchText = "";
				for (ReportColumnDTO reportColumnDTO : pObject.geteSign()) {
					if (reportColumnDTO != null) {
						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
							List<String> columnName = StringUtil
									.splitOnlyNonEmptyAndUndefined(reportColumnDTO.getColumns(), ",");
							int size = columnName.size();
							for (int i = 0; i < size; i++) {
								if (StringUtil.isNotNullOrEmpty(columnName.get(i))
										&& !StringUtil.isEqual(columnName.get(i), "undefined")) {
									columnNames.add(columnName.get(i));
									searchText += tableApplicationMaster + "." + columnName.get(i);
									if ((i + 1) < size) {
										searchText = searchText + ",";
									}
								}
							}
						}
					}
				}
				if (StringUtil.isNotNullOrEmpty(searchText)) {
					if (!tableNames.contains(tableApplicationMaster)) {
						tableNames.add(tableApplicationMaster);
					}
					queryString.append("," + searchText);
				}
			}

			if (StringUtil.isListNotNullOrEmpty(pObject.getAdminBankAccountDetail())) {
				String searchText = "";
				for (ReportColumnDTO reportColumnDTO : pObject.getAdminBankAccountDetail()) {
					if (reportColumnDTO != null) {
						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
							List<String> columnName = StringUtil
									.splitOnlyNonEmptyAndUndefined(reportColumnDTO.getColumns(), ",");
							int size = columnName.size();
							for (int i = 0; i < size; i++) {
								if (StringUtil.isNotNullOrEmpty(columnName.get(i))
										&& !StringUtil.isEqual(columnName.get(i), "undefined")) {
									columnNames.add(tableBankDetails + "_" + columnName.get(i));
									searchText += tableBankDetails + "." + columnName.get(i) + " as " + tableBankDetails
											+ "_" + columnName.get(i);
									if ((i + 1) < size) {
										searchText = searchText + ",";
									}
								}
							}
						}
					}
				}
				if (StringUtil.isNotNullOrEmpty(searchText)) {
					if (!tableNames.contains(tableBankDetails)) {
						tableNames.add(tableBankDetails);
					}
					queryString.append("," + searchText);
				}
			}
			if (StringUtil.isListNotNullOrEmpty(pObject.getAdminBasicInfo())) {
				String searchText = "";
				for (ReportColumnDTO reportColumnDTO : pObject.getAdminBasicInfo()) {
					if (reportColumnDTO != null) {
						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
							List<String> columnName = StringUtil
									.splitOnlyNonEmptyAndUndefined(reportColumnDTO.getColumns(), ",");
							int size = columnName.size();
							for (int i = 0; i < size; i++) {
								if (StringUtil.isNotNullOrEmpty(columnName.get(i))
										&& !StringUtil.isEqual(columnName.get(i), "undefined")) {
									columnNames.add(tableBasicInformation + "_" + columnName.get(i));
									searchText += tableBasicInformation + "." + columnName.get(i) + " as "
											+ tableBasicInformation + "_" + columnName.get(i);
									if ((i + 1) < size) {
										searchText = searchText + ",";
									}
								}
							}
						}
					}
				}
				if (StringUtil.isNotNullOrEmpty(searchText)) {
					if (!tableNames.contains(tableBasicInformation)) {
						tableNames.add(tableBasicInformation);
					}
					queryString.append("," + searchText);
				}
			}

			if (StringUtil.isListNotNullOrEmpty(pObject.getAdminCommunicationAddress())) {
				String searchText = "";
				for (ReportColumnDTO reportColumnDTO : pObject.getAdminCommunicationAddress()) {
					if (reportColumnDTO != null) {
						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
							List<String> columnName = StringUtil
									.splitOnlyNonEmptyAndUndefined(reportColumnDTO.getColumns(), ",");
							int size = columnName.size();
							for (int i = 0; i < size; i++) {
								if (StringUtil.isNotNullOrEmpty(columnName.get(i))
										&& !StringUtil.isEqual(columnName.get(i), "undefined")) {
									columnNames.add(tableCommunicationAddress + "_" + columnName.get(i));
									searchText += tableCommunicationAddress + "." + columnName.get(i) + " as "
											+ tableCommunicationAddress + "_" + columnName.get(i);
									if ((i + 1) < size) {
										searchText = searchText + ",";
									}
								}
							}
						}
					}
				}
				if (StringUtil.isNotNullOrEmpty(searchText)) {
					if (!tableNames.contains(tableCommunicationAddress)) {
						tableNames.add(tableCommunicationAddress);
					}
					queryString.append("," + searchText);
				}

			}
			if (StringUtil.isListNotNullOrEmpty(pObject.getAdminPermanentAddress())) {
				String searchText = "";
				for (ReportColumnDTO reportColumnDTO : pObject.getAdminPermanentAddress()) {
					if (reportColumnDTO != null) {
						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
							List<String> columnName = StringUtil
									.splitOnlyNonEmptyAndUndefined(reportColumnDTO.getColumns(), ",");
							int size = columnName.size();
							for (int i = 0; i < size; i++) {
								if (StringUtil.isNotNullOrEmpty(columnName.get(i))
										&& !StringUtil.isEqual(columnName.get(i), "undefined")) {
									columnNames.add(tablePermanentAddress + "_" + columnName.get(i));
									searchText += tablePermanentAddress + "." + columnName.get(i) + " as "
											+ tablePermanentAddress + "_" + columnName.get(i);
									if ((i + 1) < size) {
										searchText = searchText + ",";
									}
								}
							}
						}
					}
				}
				if (StringUtil.isNotNullOrEmpty(searchText)) {
					if (!tableNames.contains(tablePermanentAddress)) {
						tableNames.add(tablePermanentAddress);
					}
					queryString.append("," + searchText);
				}
			}
			if (StringUtil.isListNotNullOrEmpty(pObject.getAdminPanDetails())) {
				String searchText = "";
				for (ReportColumnDTO reportColumnDTO : pObject.getAdminPanDetails()) {
					if (reportColumnDTO != null) {
						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
							List<String> columnName = StringUtil
									.splitOnlyNonEmptyAndUndefined(reportColumnDTO.getColumns(), ",");
							int size = columnName.size();
							for (int i = 0; i < size; i++) {
								if (StringUtil.isNotNullOrEmpty(columnName.get(i))
										&& !StringUtil.isEqual(columnName.get(i), "undefined")) {
									columnNames.add(tablePanMaster + "_" + columnName.get(i));
									searchText += tablePanMaster + "." + columnName.get(i) + " as " + tablePanMaster
											+ "_" + columnName.get(i);
									if ((i + 1) < size) {
										searchText = searchText + ",";
									}
								}
							}
						}
					}
				}
				if (StringUtil.isNotNullOrEmpty(searchText)) {
					if (!tableNames.contains(tablePanMaster)) {
						tableNames.add(tablePanMaster);
					}
					queryString.append("," + searchText);
				}
			}
			String innerJoinCondition = generateInnerJoinCondition(tableNames);
			queryString.append(innerJoinCondition);
			String condition = "";
			if (StringUtil.isNotNullOrEmpty(pObject.getFromDate())) {
				condition += " tbl_application_master.created_date >= '" + pObject.getFromDate() + " 00:00:00' and";
			}
			if (StringUtil.isNotNullOrEmpty(pObject.getToDate())) {
				condition += " tbl_application_master.created_date <= '" + pObject.getToDate() + " 23:59:59' ";
			}
			if (StringUtil.isNotNullOrEmpty(condition)) {
				queryString.append("where " + condition);
//				query += "where " + condition + query;
			}
			System.out.println(queryString);
			List<ReportOutputDTO> dtos = ResportsDAO.getInstance().reportGeneration(queryString, columnNames);
			if (StringUtil.isListNotNullOrEmpty(dtos)) {
				ReportListDTO listDTO = new ReportListDTO();
				refactorColumnNames(columnNames);
				listDTO.setColumnNames(columnNames);
				listDTO.setReportOutputDTOs(dtos);
				response.setStatus(eKYCConstant.SUCCESS_STATUS);
				response.setMessage(eKYCConstant.SUCCESS_MSG);
				response.setResult(listDTO);
			}
//			 -- admin details

//			if (StringUtil.isListNotNullOrEmpty(pObject.getAdminBankAccountDetail())) {
//				for (ReportColumnDTO reportColumnDTO : pObject.getAdminBankAccountDetail()) {
//					if (reportColumnDTO != null) {
//						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
//							String[] columnName = StringUtil.split(reportColumnDTO.getColumns(), ",");
//							columnNames = new ArrayList<String>();
//							String searchText = "";
//							int size = columnName.length;
//							for(int i=0; i< size; i++){
//								columnNames.add(columnName[i]);
//								searchText+=columnName[i];
//								if((i +1)< size){
//									searchText = searchText+",";
//								}
//							}
//							String a = "SELECT "+ searchText +" FROM " + tableBankDetails;
//							System.out.println(a);
//						}
//					}
//				}
//			}
//			if (StringUtil.isListNotNullOrEmpty(pObject.getAdminBasicInfo())) {
//				for (ReportColumnDTO reportColumnDTO : pObject.getAdminBasicInfo()) {
//					if (reportColumnDTO != null) {
//						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
//							String[] columnName = StringUtil.split(reportColumnDTO.getColumns(), ",");
//							columnNames = new ArrayList<String>();
//							queryParameter = new StringBuffer();
//							queryParameter.append(" SELECT ");
//							for (String column : columnName) {
//								if (StringUtil.isNotNullOrEmpty(column) && !StringUtil.isEqual(column, "undefined")) {
//									columnNames.add(column);
//									queryParameter.append(column+",");
//								}
//							}
//							queryParameter.append(" FROM ");
//						}
//					}
//				}
//			}
//			if (StringUtil.isListNotNullOrEmpty(pObject.getAdminCommunicationAddress())) {
//				for (ReportColumnDTO reportColumnDTO : pObject.getAdminCommunicationAddress()) {
//					if (reportColumnDTO != null) {
//						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
//							String[] columnName = StringUtil.split(reportColumnDTO.getColumns(), ",");
//							columnNames = new ArrayList<String>();
//							queryParameter = new StringBuffer();
//							queryParameter.append(" SELECT ");
//							for (String column : columnName) {
//								if (StringUtil.isNotNullOrEmpty(column) && !StringUtil.isEqual(column, "undefined")) {
//									columnNames.add(column);
//									queryParameter.append(column+",");
//								}
//							}
//							queryParameter.append(" FROM " + tableCommunicationAddress);
//						}
//					}
//				}
//			}
//			if (StringUtil.isListNotNullOrEmpty(pObject.getAdminPanDetails())) {
//				for (ReportColumnDTO reportColumnDTO : pObject.getAdminPanDetails()) {
//					if (reportColumnDTO != null) {
//						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
//							String[] columnName = StringUtil.split(reportColumnDTO.getColumns(), ",");
//							columnNames = new ArrayList<String>();
//							queryParameter = new StringBuffer();
//							queryParameter.append(" SELECT ");
//							for (String column : columnName) {
//								if (StringUtil.isNotNullOrEmpty(column) && !StringUtil.isEqual(column, "undefined")) {
//									columnNames.add(column);
//									queryParameter.append(column+",");
//								}
//							}
//							queryParameter.append(" FROM " + tablePanMaster);
//						}
//					}
//				}
//			}
//			if (StringUtil.isListNotNullOrEmpty(pObject.getAdminPermanentAddress())) {
//				for (ReportColumnDTO reportColumnDTO : pObject.getAdminPermanentAddress()) {
//					if (reportColumnDTO != null) {
//						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
//							String[] columnName = StringUtil.split(reportColumnDTO.getColumns(), ",");
//							columnNames = new ArrayList<String>();
//							queryParameter = new StringBuffer();
//							queryParameter.append(" SELECT ");
//							for (String column : columnName) {
//								if (StringUtil.isNotNullOrEmpty(column) && !StringUtil.isEqual(column, "undefined")) {
//									columnNames.add(column);
//									queryParameter.append(column+",");
//								}
//							}
//							queryParameter.append(" FROM " + tablePermanentAddress);
//						}
//					}
//				}
//			}

//			if (pObject.getUserDetails() != null && pObject.getUserDetails().length > 0) {
//				String[] userDetails = pObject.getUserDetails();
//				for (int itr = 0; itr < userDetails.length - 1; itr++) {
//					String temp = userDetails[itr];
//					queryParameter = queryParameter + tableApplicationMaster + "." + temp + ", ";
//				}
//			}
//			if (pObject.getPanDetails() != null && pObject.getPanDetails().length > 0) {
//				String[] panDetails = pObject.getPanDetails();
//				for (int itr = 0; itr < panDetails.length - 1; itr++) {
//					String temp = panDetails[itr];
//					queryParameter = queryParameter + tablePanMaster + "." + temp + ", ";
//				}
//			}
//			if (pObject.getBasicInformation() != null && pObject.getBasicInformation().length > 0) {
//				String[] basicInformation = pObject.getBasicInformation();
//				for (int itr = 0; itr < basicInformation.length - 1; itr++) {
//					String temp = basicInformation[itr];
//					queryParameter = queryParameter + tableBasicInformation + "." + temp + ", ";
//				}
//			}
//			if (pObject.getCommunicationAddress() != null && pObject.getCommunicationAddress().length > 0) {
//				String[] communicationAddress = pObject.getCommunicationAddress();
//				for (int itr = 0; itr < communicationAddress.length - 1; itr++) {
//					String temp = communicationAddress[itr];
//					queryParameter = queryParameter + tableCommunicationAddress + "." + temp + ", ";
//				}
//			}
//			if (pObject.getPermananentAddress() != null && pObject.getPermananentAddress().length > 0) {
//				String[] permanentAddress = pObject.getPermananentAddress();
//				for (int itr = 0; itr < permanentAddress.length - 1; itr++) {
//					String temp = permanentAddress[itr];
//					queryParameter = queryParameter + tablePermanentAddress + "." + temp + ", ";
//				}
//			}
//			if (pObject.getBankDetails() != null && pObject.getBankDetails().length > 0) {
//				String[] bankDetails = pObject.getBankDetails();
//				for (int itr = 0; itr < bankDetails.length - 1; itr++) {
//					String temp = bankDetails[itr];
//					queryParameter = queryParameter + tableBankDetails + "." + temp + ", ";
//				}
//			}
//			System.out.println(queryParameter);

		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.INVALID_REQUEST);
		}
		return response;
	}

	private void refactorColumnNames(List<String> columnNames) {
		for (int i = 0; i < columnNames.size(); i++) {
			if (StringUtil.isEqual(columnNames.get(i), "tbl_pancard_details_is_approved")) {
				columnNames.set(i, "panIsAproved");
			} else if (StringUtil.isEqual(columnNames.get(i), "tbl_pancard_details_is_rejected")) {
				columnNames.set(i, "panIsRejected");
			} else if (StringUtil.isEqual(columnNames.get(i), "tbl_pancard_details_comments")) {
				columnNames.set(i, "panComments");
			} else if (StringUtil.isEqual(columnNames.get(i), "tbl_account_holder_personal_details_is_approved")) {
				columnNames.set(i, "personalIsAproved");
			} else if (StringUtil.isEqual(columnNames.get(i), "tbl_account_holder_personal_details_is_rejected")) {
				columnNames.set(i, "personalIsRejected");
			} else if (StringUtil.isEqual(columnNames.get(i), "tbl_account_holder_personal_details_comments")) {
				columnNames.set(i, "personalComments");
			} else if (StringUtil.isEqual(columnNames.get(i), "tbl_communication_address_is_approved")) {
				columnNames.set(i, "addressIsAproved");
			} else if (StringUtil.isEqual(columnNames.get(i), "tbl_communication_address_is_rejected")) {
				columnNames.set(i, "addressIsRejected");
			} else if (StringUtil.isEqual(columnNames.get(i), "tbl_communication_address_comments")) {
				columnNames.set(i, "addresscomments");
			} else if (StringUtil.isEqual(columnNames.get(i), "tbl_permanent_address_is_approved")) {
				columnNames.set(i, "p_addressIsAproved");
			} else if (StringUtil.isEqual(columnNames.get(i), "tbl_permanent_address_is_rejected")) {
				columnNames.set(i, "p_addressIsRejected");
			} else if (StringUtil.isEqual(columnNames.get(i), "tbl_permanent_address_comments")) {
				columnNames.set(i, "p_addresscomments");
			} else if (StringUtil.isEqual(columnNames.get(i), "tbl_bank_account_details_is_approved")) {
				columnNames.set(i, "bankIsAproved");
			} else if (StringUtil.isEqual(columnNames.get(i), "tbl_bank_account_details_is_rejected")) {
				columnNames.set(i, "bankIsRejected");
			} else if (StringUtil.isEqual(columnNames.get(i), "tbl_bank_account_details_comments")) {
				columnNames.set(i, "bankComments");
			} else if (StringUtil.isEqual(columnNames.get(i), "last_update")) {
				columnNames.set(i, "documentDate");
			} else if (StringUtil.isEqual(columnNames.get(i), "signed_date")) {
				columnNames.set(i, "signedDate");
			}
		}

	}

	private String generateInnerJoinCondition(List<String> tableNames) {
		String query = "";
		if (StringUtil.isListNotNullOrEmpty(tableNames)) {
			String firstTableName = "";
			if (tableNames.size() > 1) {
				StringBuffer innerjoins = new StringBuffer();
				for (int i = 0; i < tableNames.size(); i++) {
					if (i == 0) {
						firstTableName = tableNames.get(i);
						innerjoins.append(" FROM " + tableNames.get(i) + " as " + tableNames.get(i));
					} else {
						if (StringUtil.isEqual("tbl_application_attachements", tableNames.get(i))) {
							innerjoins.append(" INNER JOIN " + tableNames.get(i) + " as " + tableNames.get(i) + " ON "
									+ firstTableName + ".application_id = " + tableNames.get(i) + ".application_id and "
									+ tableNames.get(i) + ".attachement_type = 'EKYC_DOCUMENT' ");
						} else if (StringUtil.isEqual("tbl_application_attachements1", tableNames.get(i))) {
							innerjoins.append(" INNER JOIN tbl_application_attachements as " + tableNames.get(i) + " ON "
									+ firstTableName + ".application_id = " + tableNames.get(i)  + ".application_id and "
									+ tableNames.get(i)  + ".attachement_type = 'SIGNED_EKYC_DOCUMENT' ");
						} else {
							innerjoins.append(" INNER JOIN " + tableNames.get(i) + " as " + tableNames.get(i) + " ON "
									+ firstTableName + ".application_id = " + tableNames.get(i) + ".application_id ");
						}
					}

				}
				query = innerjoins.toString();
			} else {
				query += " FROM " + tableNames.get(0);
			}
		}
		return query;
	}

	/**
	 * Method to get OTP Records
	 * 
	 * @author pradeep
	 * @param pDto
	 * @return
	 */
	public ResponseDTO getOtpFullDatas(AdminDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		try {
			if (pDto.getStartDate() != null && !pDto.getStartDate().isEmpty()) {
				List<ApplicationMasterDTO> applicationMasterDTOs = ResportsDAO.getInstance()
						.getOtpFullDatas(pDto.getStartDate());
				if (StringUtil.isListNotNullOrEmpty(applicationMasterDTOs)) {
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setResult(applicationMasterDTOs);
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
					response.setReason(eKYCConstant.NO_RECORD_FOUND);
				}
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

}
