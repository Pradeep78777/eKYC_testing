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
		;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT ");
		String tableApplicationMaster = "tbl_application_master";
		String tablePanMaster = "tbl_pancard_details";
		String tableBasicInformation = "tbl_account_holder_personal_details";
		String tableCommunicationAddress = "tbl_communication_address";
		String tablePermanentAddress = "tbl_permanent_address";
		String tableBankDetails = "tbl_bank_account_details";
		String tableEXCHSegment = "tbl_exch_segments";
		if (pObject != null) {
			if (StringUtil.isListNotNullOrEmpty(pObject.getUserDetails())) {
				String searchText = "";
				for (ReportColumnDTO reportColumnDTO : pObject.getUserDetails()) {
					if (reportColumnDTO != null) {
						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
							String[] columnName = StringUtil.split(reportColumnDTO.getColumns(), ",");
							int size = columnName.length;
							for (int i = 0; i < size; i++) {
								if (StringUtil.isNotNullOrEmpty(columnName[i])
										&& !StringUtil.isEqual(columnName[i], "undefined")) {
									columnNames.add(columnName[i]);
									searchText += tableApplicationMaster + "." + columnName[i];
									if ((i + 1) < size) {
										searchText = searchText + ",";
									}
								}
							}

						}
					}
				}
				if (StringUtil.isNotNullOrEmpty(searchText)) {
					tableNames.add(tableApplicationMaster);
					queryString.append(searchText);
				} else {
					tableNames.add(tableApplicationMaster);
					searchText = "tbl_application_master.mobile_number";
					columnNames.add("mobile_number");
					queryString.append(searchText);
				}
			} else {
				tableNames.add(tableApplicationMaster);
				String searchText = "tbl_application_master.mobile_number";
				columnNames.add("mobile_number");
				queryString.append(searchText);
			}
			if (StringUtil.isListNotNullOrEmpty(pObject.getPanDetails())) {
				String searchText = "";
				for (ReportColumnDTO reportColumnDTO : pObject.getPanDetails()) {
					if (reportColumnDTO != null) {
						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
							String[] columnName = StringUtil.split(reportColumnDTO.getColumns(), ",");
							int size = columnName.length;
							for (int i = 0; i < size; i++) {
								if (StringUtil.isNotNullOrEmpty(columnName[i])
										&& !StringUtil.isEqual(columnName[i], "undefined")) {
									columnNames.add(columnName[i]);
									searchText += tablePanMaster + "." + columnName[i];
									if ((i + 1) < size) {
										searchText = searchText + ",";
									}
								}
							}
						}
					}
				}
				if (StringUtil.isNotNullOrEmpty(searchText)) {
					tableNames.add(tablePanMaster);
					queryString.append("," + searchText);
				}
			}
			if (StringUtil.isListNotNullOrEmpty(pObject.getBasicInformation())) {
				String searchText = "";
				for (ReportColumnDTO reportColumnDTO : pObject.getBasicInformation()) {
					if (reportColumnDTO != null) {
						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
							String[] columnName = StringUtil.split(reportColumnDTO.getColumns(), ",");
							int size = columnName.length;
							for (int i = 0; i < size; i++) {
								if (StringUtil.isNotNullOrEmpty(columnName[i])
										&& !StringUtil.isEqual(columnName[i], "undefined")) {
									columnNames.add(columnName[i]);
									searchText += tableBasicInformation + "." + columnName[i];
									if ((i + 1) < size) {
										searchText = searchText + ",";
									}
								}
							}
						}
					}
				}
				if (StringUtil.isNotNullOrEmpty(searchText)) {
					tableNames.add(tableBasicInformation);
					queryString.append("," + searchText);
				}
			}
			if (StringUtil.isListNotNullOrEmpty(pObject.getCommunicationAddress())) {
				String searchText = "";
				for (ReportColumnDTO reportColumnDTO : pObject.getCommunicationAddress()) {
					if (reportColumnDTO != null) {
						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
							String[] columnName = StringUtil.split(reportColumnDTO.getColumns(), ",");
							int size = columnName.length;
							for (int i = 0; i < size; i++) {
								if (StringUtil.isNotNullOrEmpty(columnName[i])
										&& !StringUtil.isEqual(columnName[i], "undefined")) {
									columnNames.add(columnName[i]);
									searchText += tableCommunicationAddress + "." + columnName[i];
									if ((i + 1) < size) {
										searchText = searchText + ",";
									}
								}
							}
						}
					}
				}
				if (StringUtil.isNotNullOrEmpty(searchText)) {
					tableNames.add(tableCommunicationAddress);
					queryString.append("," + searchText);
				}
			}
			if (StringUtil.isListNotNullOrEmpty(pObject.getPermananentAddress())) {
				String searchText = "";
				for (ReportColumnDTO reportColumnDTO : pObject.getPermananentAddress()) {
					if (reportColumnDTO != null) {
						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
							String[] columnName = StringUtil.split(reportColumnDTO.getColumns(), ",");
							int size = columnName.length;
							for (int i = 0; i < size; i++) {
								if (StringUtil.isNotNullOrEmpty(columnName[i])
										&& !StringUtil.isEqual(columnName[i], "undefined")) {
									columnNames.add(columnName[i]);
									searchText += tablePermanentAddress + "." + columnName[i];
									if ((i + 1) < size) {
										searchText = searchText + ",";
									}
								}
							}
						}
					}
				}
				if (StringUtil.isNotNullOrEmpty(searchText)) {
					tableNames.add(tablePermanentAddress);
					queryString.append("," + searchText);
				}
			}
			if (StringUtil.isListNotNullOrEmpty(pObject.getBankDetails())) {
				String searchText = "";
				for (ReportColumnDTO reportColumnDTO : pObject.getBankDetails()) {
					if (reportColumnDTO != null) {
						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
							String[] columnName = StringUtil.split(reportColumnDTO.getColumns(), ",");
							int size = columnName.length;
							for (int i = 0; i < size; i++) {
								if (StringUtil.isNotNullOrEmpty(columnName[i])
										&& !StringUtil.isEqual(columnName[i], "undefined")) {
									columnNames.add(columnName[i]);
									searchText += tableBankDetails + "." + columnName[i];
									if ((i + 1) < size) {
										searchText = searchText + ",";
									}
								}
							}
						}
					}
				}
				if (StringUtil.isNotNullOrEmpty(searchText)) {
					tableNames.add(tableBankDetails);
					queryString.append("," + searchText);
				}
			}
			if (StringUtil.isListNotNullOrEmpty(pObject.getTradingPreferences())) {
				String searchText = "";
				for (ReportColumnDTO reportColumnDTO : pObject.getTradingPreferences()) {
					if (reportColumnDTO != null) {
						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
							String[] columnName = StringUtil.split(reportColumnDTO.getColumns(), ",");
							int size = columnName.length;
							for (int i = 0; i < size; i++) {
								if (StringUtil.isNotNullOrEmpty(columnName[i])
										&& !StringUtil.isEqual(columnName[i], "undefined")) {
									columnNames.add(columnName[i]);
									searchText += tableEXCHSegment + "." + columnName[i];
									if ((i + 1) < size) {
										searchText = searchText + ",";
									}
								}
							}
						}
					}
				}
				if (StringUtil.isNotNullOrEmpty(searchText)) {
					tableNames.add(tableEXCHSegment);
					queryString.append("," + searchText);
				}
			}
			if (StringUtil.isListNotNullOrEmpty(pObject.geteSign())) {
				String searchText = "";
				for (ReportColumnDTO reportColumnDTO : pObject.geteSign()) {
					if (reportColumnDTO != null) {
						if (StringUtil.isNotNullOrEmpty(reportColumnDTO.getColumns())) {
							String[] columnName = StringUtil.split(reportColumnDTO.getColumns(), ",");
							int size = columnName.length;
							for (int i = 0; i < size; i++) {
								if (StringUtil.isNotNullOrEmpty(columnName[i])
										&& !StringUtil.isEqual(columnName[i], "undefined")) {
									columnNames.add(columnName[i]);
									searchText += tableApplicationMaster + "." + columnName[i];
									if ((i + 1) < size) {
										searchText = searchText + ",";
									}
								}
							}
						}
					}
				}
				if (StringUtil.isNotNullOrEmpty(searchText)) {
					queryString.append("," + searchText);
				}
			}
			String innerJoinCondition = generateInnerJoinCondition(tableNames);
			queryString.append(innerJoinCondition);
			System.out.println(queryString);
			List<ReportOutputDTO> dtos = ResportsDAO.getInstance().reportGeneration(queryString, columnNames);
			if (StringUtil.isListNotNullOrEmpty(dtos)) {
				ReportListDTO listDTO = new ReportListDTO();
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
						innerjoins.append(" INNER JOIN " + tableNames.get(i) + " as " + tableNames.get(i) + " ON "
								+ firstTableName + ".application_id = " + tableNames.get(i) + ".application_id ");
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
