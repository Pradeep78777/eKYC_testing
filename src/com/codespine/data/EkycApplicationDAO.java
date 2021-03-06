package com.codespine.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.simple.JSONObject;

import com.codespine.dto.ApplicationStatusDTO;
import com.codespine.dto.PersonalDetailsDTO;
import com.codespine.util.DBUtil;

public class EkycApplicationDAO {

	public static EkycApplicationDAO EkycApplicationDAO = null;

	public static EkycApplicationDAO getInstance() {
		if (EkycApplicationDAO == null) {
			EkycApplicationDAO = new EkycApplicationDAO();
		}
		return EkycApplicationDAO;
	}

	/**
	 * Method to get the user profile for the given information
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @param b
	 * @return
	 */
	public List<PersonalDetailsDTO> getUserProfile(PersonalDetailsDTO pDto, boolean isEmail) {
		List<PersonalDetailsDTO> response = null;
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			String query = "";
			if (isEmail) {
				query = "SELECT application_id , mobile_number ,mobile_otp, mobile_no_verified, email_id, email_activated, "
						+ "application_status , document_signed , document_downloaded , email_owner , mob_owner , is_approved , is_rejected , rectify_count "
						+ "  FROM  tbl_application_master where email_id = ? and delete_flag = ?";
				pStmt = conn.prepareStatement(query);
				pStmt.setString(paromPos++, pDto.getEmail());
				pStmt.setInt(paromPos++, 0);
			} else {
				query = "SELECT application_id , mobile_number ,mobile_otp, mobile_no_verified, email_id, email_activated, "
						+ "application_status , document_signed , document_downloaded , email_owner , mob_owner , is_approved , is_rejected , rectify_count "
						+ "  FROM  tbl_application_master where mobile_number = ? and delete_flag = ?";
				pStmt = conn.prepareStatement(query);
				pStmt.setLong(paromPos++, pDto.getMobile_number());
				pStmt.setInt(paromPos++, 0);
			}
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				response = new ArrayList<PersonalDetailsDTO>();
				while (rSet.next()) {
					result = new PersonalDetailsDTO();
					result.setApplication_id(rSet.getInt("application_id"));
					result.setMobile_number(rSet.getLong("mobile_number"));
					result.setOtp(rSet.getInt("mobile_otp"));
					result.setMobile_number_verified(rSet.getInt("mobile_no_verified"));
					result.setEmail(rSet.getString("email_id"));
					result.setEmail_id_verified(rSet.getInt("email_activated"));
					result.setApplicationStatus(rSet.getInt("application_status"));
					result.setDocumentSigned(rSet.getInt("document_signed"));
					result.setDocumentDownloaded(rSet.getInt("document_downloaded"));
					result.setMobile_owner(rSet.getString("mob_owner"));
					result.setEmail_owner(rSet.getString("email_owner"));
					result.setIsAproved(rSet.getInt("is_approved"));
					result.setIsRejected(rSet.getInt("is_rejected"));
					result.setRectifyCount(rSet.getInt("rectify_count"));
					response.add(result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtil.closeResultSet(rSet);
				DBUtil.closeStatement(pStmt);
				DBUtil.closeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	/**
	 * Method to get the user details by the given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param application_id
	 * @return
	 */
	public static PersonalDetailsDTO getApplicationForUser(int application_id) {
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			String query = "";
			query = " SELECT application_id , mobile_number ,mobile_otp, mobile_no_verified, email_id, email_activated, "
					+ " application_status , document_signed , document_downloaded , email_owner , mob_owner , is_approved , is_rejected , rectify_count "
					+ "  FROM  tbl_application_master where application_id = ? and delete_flag = ?";
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(paromPos++, application_id);
			pStmt.setInt(paromPos++, 0);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new PersonalDetailsDTO();
					result.setApplication_id(rSet.getInt("application_id"));
					result.setMobile_number(rSet.getLong("mobile_number"));
					result.setOtp(rSet.getInt("mobile_otp"));
					result.setMobile_number_verified(rSet.getInt("mobile_no_verified"));
					result.setEmail(rSet.getString("email_id"));
					result.setEmail_id_verified(rSet.getInt("email_activated"));
					result.setApplicationStatus(rSet.getInt("application_status"));
					result.setDocumentSigned(rSet.getInt("document_signed"));
					result.setDocumentDownloaded(rSet.getInt("document_downloaded"));
					result.setMobile_owner(rSet.getString("mob_owner"));
					result.setEmail_owner(rSet.getString("email_owner"));
					result.setIsAproved(rSet.getInt("is_approved"));
					result.setIsRejected(rSet.getInt("is_rejected"));
					result.setRectifyCount(rSet.getInt("rectify_count"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtil.closeResultSet(rSet);
				DBUtil.closeStatement(pStmt);
				DBUtil.closeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * To update the otp for the given application Id
	 * 
	 * @param otp
	 * @param applicationId
	 */
	public void updateOTPforApplication(PersonalDetailsDTO pDto, int otp, boolean isEmail) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		try {
			conn = DBUtil.getConnection();
			int parompos = 1;
			if (isEmail) {
				pStmt = conn.prepareStatement(
						" UPDATE tbl_application_master SET mobile_otp = ? , last_updated = ? where email_id = ? ");
				pStmt.setInt(parompos++, otp);
				pStmt.setTimestamp(parompos++, timestamp);
				pStmt.setString(parompos++, pDto.getEmail());
			} else {
				pStmt = conn.prepareStatement(
						" UPDATE tbl_application_master SET mobile_otp = ? , last_updated = ? where mobile_number = ? ");
				pStmt.setInt(parompos++, otp);
				pStmt.setTimestamp(parompos++, timestamp);
				pStmt.setLong(parompos++, pDto.getMobile_number());
			}
			pStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtil.closeStatement(pStmt);
				DBUtil.closeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Method to get
	 * 
	 * @author GOWRI SANKAR R
	 * @param randomKey
	 * @param applicationId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getIVRMasterDetails(int applicationId) {
		JSONObject result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			String query = "";
			query = "SELECT application_id, random_key, expiry_date, created_on, created_by, updated_on, updated_by, active_status "
					+ " FROM  tbl_ivr_link_master where application_id = ? ";
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(paromPos++, applicationId);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new JSONObject();
					result.put("application_id", rSet.getInt("application_id"));
					result.put("random_key", rSet.getString("random_key"));
					result.put("expiry_date", rSet.getString("expiry_date"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtil.closeResultSet(rSet);
				DBUtil.closeStatement(pStmt);
				DBUtil.closeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * Method to get the application status for the given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param applicationId
	 * @return
	 */
	public ApplicationStatusDTO getApplicationStatus(int applicationId) {
		ApplicationStatusDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			String query = "";
			query = "SELECT a.otp_verified_on as otpVerifiedOn , b.created_on as personalDetails , c.created_on as panCard, "
					+ " d.created_on as esignedOn FROM  tbl_application_master A "
					+ " inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
					+ " inner join tbl_pancard_details C on a.application_id = c.application_id "
					+ " join tbl_application_attachements D on a.application_id = d.application_id  where a.application_id = ? "
					+ " and d.attachement_type = 'SIGNED_EKYC_DOCUMENT' ";
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(paromPos++, applicationId);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new ApplicationStatusDTO();
					result.setApplicationId(applicationId);
					result.setApplicationStartedOn(rSet.getString("otpVerifiedOn"));
					result.setEsignedOn(rSet.getString("esignedOn"));
					result.setPancardUpdatedOn(rSet.getString("panCard"));
					result.setPersonalDetailsUpdatedOn(rSet.getString("personalDetails"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtil.closeResultSet(rSet);
				DBUtil.closeStatement(pStmt);
				DBUtil.closeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
