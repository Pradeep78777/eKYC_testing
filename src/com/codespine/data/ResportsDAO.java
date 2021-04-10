package com.codespine.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.codespine.dto.ApplicationMasterDTO;
import com.codespine.dto.ReportOutputDTO;
import com.codespine.util.DBUtil;

public class ResportsDAO {
	public static ResportsDAO ResportsDAO = null;

	public static ResportsDAO getInstance() {
		if (ResportsDAO == null) {
			ResportsDAO = new ResportsDAO();
		}
		return ResportsDAO;
	}

//	public static boolean isNumeric(String strNum) {
//		if (strNum == null) {
//			return false;
//		}
//		try {
//			Double.parseDouble(strNum);
//		} catch (NumberFormatException nfe) {
//			return false;
//		}
//		return true;
//	}

	public List<ReportOutputDTO> reportGeneration(StringBuffer query, List<String> columnNames) {
		List<ReportOutputDTO> dtos = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(query.toString());
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				dtos = new ArrayList<ReportOutputDTO>();
				while (rSet.next()) {
					ReportOutputDTO reportDTO = new ReportOutputDTO();
					if (columnNames.contains("mobile_no_verified")) {
						reportDTO.setMobile_no_verified(rSet.getInt("tbl_application_master.mobile_no_verified"));
					}
					if (columnNames.contains("mobile_number")) {
						reportDTO.setMobile_number(rSet.getLong("tbl_application_master.mobile_number"));
					}
					if (columnNames.contains("email_id")) {
						reportDTO.setEmail_id(rSet.getString("tbl_application_master.email_id"));
					}
					if (columnNames.contains("email_activated")) {
						reportDTO.setEmail_activated(rSet.getInt("tbl_application_master.email_activated"));
					}
					if (columnNames.contains("email_activated_on")) {
						reportDTO.setEmail_activated_on(rSet.getDate("tbl_application_master.email_activated_on"));
					}
					if (columnNames.contains("otp_verified_on")) {
						reportDTO.setOtp_verified_on(rSet.getDate("tbl_application_master.otp_verified_on"));
					}
					if (columnNames.contains("documentSigned")) {
						reportDTO.setDocumentSigned(rSet.getInt("documentSigned"));
					}
					if (columnNames.contains("documentDownloaded")) {
						reportDTO.setDocumentDownloaded(rSet.getInt("documentDownloaded"));
					}
					if (columnNames.contains("pan_card")) {
						reportDTO.setPan_card(rSet.getString("tbl_pancard_details.pan_card"));
					}
					if (columnNames.contains("aadhar_no")) {
						reportDTO.setAadharNo(rSet.getLong("tbl_pancard_details.aadhar_no"));
					}
					if (columnNames.contains("dob")) {
						reportDTO.setDob(rSet.getString("tbl_pancard_details.dob"));
					}
					if (columnNames.contains("pan_card_verified")) {
						reportDTO.setPan_card_verified(rSet.getInt("tbl_pancard_details.pan_card_verified"));
					}
//					if (columnNames.contains("")) {
//					reportDTO.setPanIsAproved(rSet.getInt("is_approved"));
//					}
//					if (columnNames.contains("")) {
//					reportDTO.setPanIsRejected(rSet.getInt("is_rejected"));
//					}
//					if (columnNames.contains("")) {
//					reportDTO.setPanComments(rSet.getString("comments"));
//					}
					if (columnNames.contains("mothersName")) {
						reportDTO.setMothersName(rSet.getString("tbl_account_holder_personal_details.mothersName"));
					}
					if (columnNames.contains("fathersName")) {
						reportDTO.setFathersName(rSet.getString("tbl_account_holder_personal_details.fathersName"));
					}
					if (columnNames.contains("gender")) {
						reportDTO.setGender(rSet.getString("tbl_account_holder_personal_details.gender"));
					}
					if (columnNames.contains("marital_status")) {
						reportDTO.setMarital_status(
								rSet.getString("tbl_account_holder_personal_details.marital_status"));
					}
					if (columnNames.contains("annual_income")) {
						reportDTO.setAnnual_income(rSet.getString("tbl_account_holder_personal_details.annual_income"));
					}
					if (columnNames.contains("trading_experience")) {
						reportDTO.setTrading_experience(
								rSet.getString("tbl_account_holder_personal_details.trading_experience"));
					}
					if (columnNames.contains("occupation")) {
						reportDTO.setOccupation(rSet.getString("tbl_account_holder_personal_details.occupation"));
					}
					if (columnNames.contains("politically_exposed")) {
						reportDTO.setPolitically_exposed(
								rSet.getString("tbl_account_holder_personal_details.politically_exposed"));
					}
//					if (columnNames.contains("")) {
//					reportDTO.setPersonalIsAproved(rSet.getInt("is_approved"));
//				    }
//				    if (columnNames.contains("")) {
//					reportDTO.setPersonalIsRejected(rSet.getInt("is_rejected"));
//				    }
//				    if (columnNames.contains("")) {
//					reportDTO.setPersonalComments(rSet.getString("comments"));
//				    }
					if (columnNames.contains("flat_no")) {
						reportDTO.setFlat_no(rSet.getString("tbl_communication_address.flat_no"));
					}
					if (columnNames.contains("street")) {
						reportDTO.setStreet(rSet.getString("tbl_communication_address.street"));
					}
					if (columnNames.contains("pin")) {
						reportDTO.setPin(rSet.getInt("tbl_communication_address.pin"));
					}
					if (columnNames.contains("city")) {
						reportDTO.setCity(rSet.getString("tbl_communication_address.city"));
					}
					if (columnNames.contains("state")) {
						reportDTO.setState(rSet.getString("tbl_communication_address.state"));
					}
					if (columnNames.contains("district")) {
						reportDTO.setDistrict(rSet.getString("tbl_communication_address.district"));
					}
//					if (columnNames.contains("")) {
//					reportDTO.setAddressIsAproved(rSet.getInt("is_approved"));
//					}
//					if (columnNames.contains("")) {
//					reportDTO.setAddressIsRejected(rSet.getInt("is_rejected"));
//					}
//					if (columnNames.contains("")) {
//					reportDTO.setAddresscomments(rSet.getString("comments"));
//					}
					if (columnNames.contains("flat_no")) {
						reportDTO.setP_flat_no(rSet.getString("tbl_permanent_address.flat_no"));
					}
					if (columnNames.contains("street")) {
						reportDTO.setP_street(rSet.getString("tbl_permanent_address.street"));
					}
					if (columnNames.contains("city")) {
						reportDTO.setP_city(rSet.getString("tbl_permanent_address.city"));
					}
					if (columnNames.contains("district")) {
						reportDTO.setP_district(rSet.getString("tbl_permanent_address.district"));
					}
					if (columnNames.contains("state")) {
						reportDTO.setP_state(rSet.getString("tbl_permanent_address.state"));
					}
					if (columnNames.contains("pin")) {
						reportDTO.setP_pin(rSet.getInt("tbl_permanent_address.pin"));
					}
//					if (columnNames.contains("")) {
//					reportDTO.setP_addressIsAproved(rSet.getInt("is_approved"));
//					}
//					if (columnNames.contains("")) {
//					reportDTO.setP_addressIsRejected(rSet.getInt("is_rejected"));
//					}
//					if (columnNames.contains("")) {
//					reportDTO.setP_addresscomments(rSet.getString("comments"));
//					}
					if (columnNames.contains("bank_account_no")) {
						reportDTO.setBank_account_no(rSet.getString("tbl_bank_account_details.bank_account_no"));
					}
					if (columnNames.contains("account_holder_name")) {
						reportDTO
								.setAccount_holder_name(rSet.getString("tbl_bank_account_details.account_holder_name"));
					}
					if (columnNames.contains("ifsc_code")) {
						reportDTO.setIfsc_code(rSet.getString("tbl_bank_account_details.ifsc_code"));
					}
					if (columnNames.contains("bank_name")) {
						reportDTO.setBankName(rSet.getString("tbl_bank_account_details.bank_name"));
					}
					if (columnNames.contains("bank_address")) {
						reportDTO.setBankAddress(rSet.getString("tbl_bank_account_details.bank_address"));
					}
					if (columnNames.contains("micr_code")) {
						reportDTO.setMicrCode(rSet.getString("tbl_bank_account_details.micr_code"));
					}
//					if (columnNames.contains("")) {
//					reportDTO.setBankIsAproved(rSet.getInt("is_approved"));
//					}
//					if (columnNames.contains("")) {
//					reportDTO.setBankIsRejected(rSet.getInt("is_rejected"));
//					}
//					if (columnNames.contains("")) {
//					reportDTO.setBankComments(rSet.getString("comments"));
//					}
					if (columnNames.contains("nse_eq")) {
						reportDTO.setNse_eq(rSet.getInt("tbl_exch_segments.nse_eq"));
					}
					if (columnNames.contains("bse_eq")) {
						reportDTO.setBse_eq(rSet.getInt("tbl_exch_segments.bse_eq"));
					}
					if (columnNames.contains("mf")) {
						reportDTO.setMf(rSet.getInt("tbl_exch_segments.mf"));
					}
					if (columnNames.contains("nse_fo")) {
						reportDTO.setNse_fo(rSet.getInt("tbl_exch_segments.nse_fo"));
					}
					if (columnNames.contains("bse_fo")) {
						reportDTO.setBse_fo(rSet.getInt("tbl_exch_segments.bse_fo"));
					}
					if (columnNames.contains("cds")) {
						reportDTO.setCds(rSet.getInt("tbl_exch_segments.cds"));
					}
					if (columnNames.contains("bcd")) {
						reportDTO.setBcd(rSet.getInt("tbl_exch_segments.bcd"));
					}
					if (columnNames.contains("mcx")) {
						reportDTO.setMcx(rSet.getInt("tbl_exch_segments.mcx"));
					}
					if (columnNames.contains("icex")) {
						reportDTO.setIcex(rSet.getInt("tbl_exch_segments.icex"));
					}
					if (columnNames.contains("nse_com")) {
						reportDTO.setNse_com(rSet.getInt("tbl_exch_segments.nse_com"));
					}
					if (columnNames.contains("bse_com")) {
						reportDTO.setBse_com(rSet.getInt("tbl_exch_segments.bse_com"));
					}
					dtos.add(reportDTO);
//					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pStmt.close();
				conn.close();
				rSet.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dtos;
	}

	/**
	 * Method to get OTP Records
	 * 
	 * @author pradeep
	 * @param pDto
	 * @return
	 */
	public List<ApplicationMasterDTO> getOtpFullDatas(String startDate) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		List<ApplicationMasterDTO> applicationMasterDTOs = null;
		ResultSet rSet = null;
		ApplicationMasterDTO result = null;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"SELECT mobile_number,email_id,email_activated,mobile_otp from tbl_application_master where created_date >= ? ");
			pStmt.setString(1, startDate + " 00:00:00");
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				applicationMasterDTOs = new ArrayList<ApplicationMasterDTO>();
				while (rSet.next()) {
					result = new ApplicationMasterDTO();
					result.setMobile_number(rSet.getString("mobile_number"));
					result.setEmail_id(rSet.getString("email_id"));
					result.setEmail_activated(rSet.getInt("email_activated"));
					result.setMobile_otp(rSet.getString("mobile_otp"));
					applicationMasterDTOs.add(result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pStmt.close();
				conn.close();
				rSet.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return applicationMasterDTOs;
	}
}
