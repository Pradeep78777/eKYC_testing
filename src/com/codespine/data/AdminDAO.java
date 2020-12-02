package com.codespine.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.codespine.dto.AdminDTO;
import com.codespine.dto.ApplicationLogDTO;
import com.codespine.dto.FileUploadDTO;
import com.codespine.dto.PersonalDetailsDTO;
import com.codespine.util.DBUtil;
import com.codespine.util.eKYCConstant;

public class AdminDAO {
	public static AdminDAO AdminDAO = null;

	public static AdminDAO getInstance() {
		if (AdminDAO == null) {
			AdminDAO = new AdminDAO();
		}
		return AdminDAO;
	}

	/**
	 * Get all user Records from the data base
	 * 
	 * @author GOWRI SANKAR R
	 * @return
	 */
	public List<PersonalDetailsDTO> getAllUserRecords() {
		List<PersonalDetailsDTO> response = new ArrayList<PersonalDetailsDTO>();
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated,"
							+ "a.application_status , b.applicant_name , b.mothersName, b.fathersName, b.gender, b.marital_status, b.annual_income,"
							+ "b.trading_experience, b.occupation, b.politically_exposed , c.pan_card ,c.dob FROM tbl_application_master A "
							+ "inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
							+ "inner join tbl_pancard_details c on a.application_id = c.application_id order by a.created_date desc");
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new PersonalDetailsDTO();
					result.setApplication_id(rSet.getInt("a.application_id"));
					result.setMobile_number(rSet.getLong("a.mobile_number"));
					result.setMobile_number_verified(rSet.getInt("a.mobile_no_verified"));
					result.setEmail(rSet.getString("a.email_id"));
					result.setEmail_id_verified(rSet.getInt("a.email_activated"));
					result.setApplicationStatus(rSet.getInt("a.application_status"));
					result.setApplicant_name(rSet.getString("b.applicant_name"));
					result.setMothersName(rSet.getString("b.mothersName"));
					result.setFathersName(rSet.getString("b.fathersName"));
					result.setGender(rSet.getString("b.gender"));
					result.setMarital_status(rSet.getString("b.marital_status"));
					result.setAnnual_income(rSet.getString("b.annual_income"));
					result.setTrading_experience(rSet.getString("b.trading_experience"));
					result.setOccupation(rSet.getString("b.occupation"));
					result.setPolitically_exposed(rSet.getString("b.politically_exposed"));
					result.setPancard(rSet.getString("c.pan_card"));
					result.setDob(rSet.getString("c.dob"));
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
	 * method to save the admin descision for the user given Pan card details
	 * 
	 * @author GOWRI SANKAR R
	 * @param isApprove
	 * @param comments
	 * @param appliactionId
	 * @return
	 */
	public boolean uploadAdminComments(boolean isApprove, String comments, int appliactionId, String tableName) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		boolean issuccessfull = false;
		int count = 0;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(" UPDATE " + tableName
					+ " SET is_approved = ? , is_rejected = ? , comments = ? where application_id = ? ");
			int parompos = 1;
			if (isApprove) {
				pStmt.setInt(parompos++, 1);
				pStmt.setInt(parompos++, 0);
				pStmt.setString(parompos++, comments);
				pStmt.setInt(parompos++, appliactionId);
			} else {
				pStmt.setInt(parompos++, 0);
				pStmt.setInt(parompos++, 1);
				pStmt.setString(parompos++, comments);
				pStmt.setInt(parompos++, appliactionId);
			}
			count = pStmt.executeUpdate();
			if (count > 0) {
				issuccessfull = true;
			}
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
		return issuccessfull;
	}

	/**
	 * Method to update the application Attachements for the given application
	 * id and proof type
	 * 
	 * @author GOWRI SANKAR R
	 * @param isApprove
	 * @param comments
	 * @param appliactionId
	 * @param proofType
	 * @return
	 */
	public boolean updateAdminCommentForAttachments(boolean isApprove, String comments, int appliactionId,
			String proofType) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		boolean issuccessfull = false;
		int count = 0;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" UPDATE tbl_application_attachements SET is_approved = ? , is_rejected = ? , comments = ? where application_id = ? and attachement_type = ? ");
			int parompos = 1;
			if (isApprove) {
				pStmt.setInt(parompos++, 1);
				pStmt.setInt(parompos++, 0);
				pStmt.setString(parompos++, comments);
				pStmt.setInt(parompos++, appliactionId);
				pStmt.setString(parompos++, proofType);
			} else {
				pStmt.setInt(parompos++, 0);
				pStmt.setInt(parompos++, 1);
				pStmt.setString(parompos++, comments);
				pStmt.setInt(parompos++, appliactionId);
				pStmt.setString(parompos++, proofType);
			}
			count = pStmt.executeUpdate();
			if (count > 0) {
				issuccessfull = true;
			}
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
		return issuccessfull;
	}

	/**
	 * Method to get the attachements type and
	 * 
	 * @author GOWRI SANKAR R
	 * @param application_id
	 * @return
	 */
	public static List<FileUploadDTO> getUploadedFile(int application_id) {
		List<FileUploadDTO> response = new ArrayList<FileUploadDTO>();
		FileUploadDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" SELECT attachement_type , attachement_url FROM tbl_application_attachements where application_id = ? ");
			pStmt.setInt(paromPos++, application_id);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new FileUploadDTO();
					result.setProofType(rSet.getString("attachement_type"));
					result.setProof(rSet.getString("attachement_url"));
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
	 * To start the application for the application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public boolean startApplication(AdminDTO pDto) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		boolean issuccessfull = false;
		int count = 0;
		java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" INSERT INTO tbl_application_status_log (application_id, start_time , status) values (?,?,?) ");
			int parompos = 1;
			pStmt.setInt(parompos++, pDto.getApplicationId());
			pStmt.setTimestamp(parompos++, timestamp);
			pStmt.setInt(parompos++, eKYCConstant.APPLICATION_STARTED_BY_ADMIN);
			count = pStmt.executeUpdate();
			if (count > 0) {
				issuccessfull = true;
			}
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
		return issuccessfull;
	}

	/**
	 * To End the application status for the Given Application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public boolean endApplication(AdminDTO pDto) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		boolean issuccessfull = false;
		java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" INSERT INTO tbl_application_status_log (application_id, end_time , status) values (?,?,?) ");
			int parompos = 1;
			pStmt.setInt(parompos++, pDto.getApplicationId());
			pStmt.setTimestamp(parompos++, timestamp);
			pStmt.setInt(parompos++, eKYCConstant.APPLICATION_STARTED_BY_ADMIN);
			issuccessfull = pStmt.execute();
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
		return issuccessfull;
	}

	/**
	 * Method to Insert the admin status
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public boolean insertAdminStatus(int applicationId, String verificationModule, boolean isApproved, String notes) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		boolean issuccessfull = false;
		int status = 2;
		if (isApproved) {
			status = 1;
		}
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" INSERT INTO tbl_application_status_log (application_id, verification_module, status, notes) values (?,?,?,?) ");
			int parompos = 1;
			pStmt.setInt(parompos++, applicationId);
			pStmt.setString(parompos++, verificationModule);
			pStmt.setInt(parompos++, status);
			pStmt.setString(parompos++, notes);
			issuccessfull = pStmt.execute();
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
		return issuccessfull;
	}

	/**
	 * Method to update the admin status
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public boolean updateAdminStatus(int applicationId, String verificationModule, boolean isApproved, String notes) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		boolean issuccessfull = false;
		int status = 2;
		if (isApproved) {
			status = 1;
		}
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" UPDATE tbl_application_status_log SET status = ? , notes = ? WHERE application_id = ? and verification_module = ? ");
			int parompos = 1;
			pStmt.setInt(parompos++, status);
			pStmt.setString(parompos++, notes);
			pStmt.setInt(parompos++, applicationId);
			pStmt.setString(parompos++, verificationModule);
			issuccessfull = pStmt.execute();
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
		return issuccessfull;
	}

	/**
	 * Method to take the admin action for the Application Id
	 * 
	 * @author GOWRI SANKAR R
	 * @param applicationId
	 * @return
	 */
	public List<ApplicationLogDTO> checkApplicationStatus(int applicationId) {
		List<ApplicationLogDTO> response = null;
		ApplicationLogDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" SELECT verification_module, start_time, end_time, status, notes FROM tbl_application_status_log where application_id = ? ");
			pStmt.setInt(paromPos++, applicationId);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				response = new ArrayList<ApplicationLogDTO>();
				while (rSet.next()) {
					result = new ApplicationLogDTO();
					result.setVerification_module(rSet.getString("verification_module"));
					result.setStart_time(rSet.getString("start_time"));
					result.setEnd_time(rSet.getString("end_time"));
					result.setStatus(rSet.getInt("status"));
					result.setNotes(rSet.getString("notes"));
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
	 * 
	 * @param isApprove
	 * @param comments
	 * @param appliactionId
	 * @param proofType
	 * @return
	 */
	public boolean updateInApplicationMaster(AdminDTO pDto) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		boolean issuccessfull = false;
		int count = 0;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" UPDATE tbl_application_master SET is_approved = ? , is_rejected = ? , comments = ? where application_id = ? ");
			int parompos = 1;
			pStmt.setInt(parompos++, pDto.getIsApprove());
			pStmt.setInt(parompos++, pDto.getIsRejected());
			pStmt.setString(parompos++, pDto.getComments());
			pStmt.setInt(parompos++, pDto.getApplicationId());
			count = pStmt.executeUpdate();
			if (count > 0) {
				issuccessfull = true;
			}
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
		return issuccessfull;
	}

	/**
	 * Method to get the user details by given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param applicationId
	 * @return
	 */
	public PersonalDetailsDTO getUserDetails(int applicationId) {
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement("SELECT a.mobile_number, a.email_id ,b.applicant_name "
					+ "FROM tbl_application_master A inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
					+ "where a.application_id = ?");
			pStmt.setLong(paromPos++, applicationId);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new PersonalDetailsDTO();
					result.setMobile_number(rSet.getLong("mobile_number"));
					result.setEmail(rSet.getString("email_id"));
					result.setApplicant_name(rSet.getString("b.applicant_name"));
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
	 * Method to get the rejected Documents for the given
	 * 
	 * @author GOWRI SANKAR R
	 * @param applicationId
	 * @return
	 */
	public List<ApplicationLogDTO> rejectedDocuments(int applicationId) {
		List<ApplicationLogDTO> response = null;
		ApplicationLogDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" SELECT verification_module, start_time, end_time, status, notes FROM tbl_application_status_log where application_id = ? and status = ? ");
			pStmt.setInt(paromPos++, applicationId);
			pStmt.setInt(paromPos++, 0);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				response = new ArrayList<ApplicationLogDTO>();
				while (rSet.next()) {
					result = new ApplicationLogDTO();
					result.setVerification_module(rSet.getString("verification_module"));
					result.setStart_time(rSet.getString("start_time"));
					result.setEnd_time(rSet.getString("end_time"));
					result.setStatus(rSet.getInt("status"));
					result.setNotes(rSet.getString("notes"));
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

	public int getAdminVerificationModules(int applicationId, String verificationModule) {
		int tempId = 0;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" SELECT id FROM tbl_application_status_log where application_id = ? and verification_module = ?");
			pStmt.setInt(paromPos++, applicationId);
			pStmt.setString(paromPos++, verificationModule);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					tempId = rSet.getInt("id");
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
		return tempId;
	}

	/**
	 * Method to get the admin password from the data base
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public AdminDTO adminLogin(AdminDTO pDto) {
		AdminDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"SELECT admin_email, admin_password FROM tbl_admin_details where admin_email = ? ");
			pStmt.setString(paromPos++, pDto.getEmail());
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new AdminDTO();
					result.setEmail(rSet.getString("admin_email"));
					result.setPassword(rSet.getString("admin_password"));
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
