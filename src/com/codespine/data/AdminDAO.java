package com.codespine.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.codespine.dto.AddressDTO;
import com.codespine.dto.AdminDTO;
import com.codespine.dto.ApplicationAttachementsDTO;
import com.codespine.dto.ApplicationLogDTO;
import com.codespine.dto.BankDetailsDTO;
import com.codespine.dto.ExchDetailsDTO;
import com.codespine.dto.FileUploadDTO;
import com.codespine.dto.PanCardDetailsDTO;
import com.codespine.dto.PersonalDetailsDTO;
import com.codespine.util.DBUtil;
import com.codespine.util.Utility;
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
			int paramPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated,"
							+ "a.application_status , a.document_signed , a.document_downloaded , b.applicant_name , b.mothersName, "
							+ "b.fathersName, b.gender, b.marital_status, b.annual_income, b.trading_experience, b.occupation, "
							+ "b.politically_exposed , c.pan_card ,c.dob  FROM  tbl_application_master A "
							+ "inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
							+ "inner join tbl_pancard_details c on a.application_id = c.application_id where is_completed = ? order by a.created_date desc");
			pStmt.setInt(paramPos++, 0);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new PersonalDetailsDTO();
					int applicationStatus = rSet.getInt("a.application_status");
					result.setApplication_id(rSet.getInt("a.application_id"));
					result.setMobile_number(rSet.getLong("a.mobile_number"));
					result.setMobile_number_verified(rSet.getInt("a.mobile_no_verified"));
					result.setEmail(rSet.getString("a.email_id"));
					result.setEmail_id_verified(rSet.getInt("a.email_activated"));
					result.setApplicationStatus(applicationStatus);
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
					result.setDocumentSigned(rSet.getInt("a.document_signed"));
					result.setDocumentDownloaded(rSet.getInt("a.document_downloaded"));
					if (applicationStatus == eKYCConstant.OTP_VERIFIED
							|| applicationStatus == eKYCConstant.PAN_CARD_UPDATED) {
						result.setStatus("Pancard Details");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.BASIC_DETAILS_UPDATED) {
						result.setStatus("Basic Details");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.COMMUNICATION_ADDRESS_UPDATED) {
						result.setStatus("Comminication Address");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.PERMANENT_ADDRESS_UPDATED) {
						result.setStatus("Permanent Address");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.BANK_DETAILS_UPDATED) {
						result.setStatus("Bank Details");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.EXCH_UPDATED) {
						result.setStatus("EXCH");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.ATTACHEMENT_UPLOADED) {
						result.setStatus("Document Uploaded");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.IPV_UPLOADED) {
						result.setStatus("Ipv Uploaded");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.DOCUMENT_DOWNLOADED) {
						result.setStatus("Document Downloaded");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.DOCUMENT_SIGNED) {
						result.setStatus("Document Signed");
						result.setExactStatus("In Process");
					}
					if (applicationStatus > eKYCConstant.DOCUMENT_SIGNED) {
						result.setStatus("Admin Started Application");
						result.setExactStatus("Review");
					}
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
					" SELECT attachement_type , attachement_url  FROM  tbl_application_attachements where application_id = ? ");
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
			int count = 0;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" INSERT INTO tbl_application_status_log (application_id, end_time , status) values (?,?,?) ");
			int parompos = 1;
			pStmt.setInt(parompos++, pDto.getApplicationId());
			pStmt.setTimestamp(parompos++, timestamp);
			pStmt.setInt(parompos++, eKYCConstant.APPLICATION_ENDED_BY_ADMIN);
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
		int status = 0;
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
		int status = 0;
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
					" SELECT verification_module, start_time, end_time, status, notes  FROM  tbl_application_status_log where application_id = ? ");
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
	 * Method to update the retify Count
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public boolean updateRetifyCount(int applicationId, int retifyCount) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		boolean issuccessfull = false;
		int count = 0;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn
					.prepareStatement("UPDATE tbl_application_master SET rectify_count = ? where application_id = ? ");
			int parompos = 1;
			pStmt.setInt(parompos++, retifyCount);
			pStmt.setInt(parompos++, applicationId);
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
					+ " FROM  tbl_application_master A inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
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
					" SELECT verification_module, start_time, end_time, status, notes  FROM  tbl_application_status_log where application_id = ? and status = ? ");
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
					" SELECT id  FROM  tbl_application_status_log where application_id = ? and verification_module = ?");
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
					"SELECT name, admin_email, admin_password, designation  FROM  tbl_admin_details where admin_email = ? ");
			pStmt.setString(paromPos++, pDto.getEmail());
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new AdminDTO();
					result.setName(rSet.getString("name"));
					result.setEmail(rSet.getString("admin_email"));
					result.setPassword(rSet.getString("admin_password"));
					result.setDesignation(rSet.getString("designation"));
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
	 * Method to get the user records from the data base
	 * 
	 * @author GOWRI SANKAR R
	 * @return
	 */
	public List<PersonalDetailsDTO> getUserRecords() {
		List<PersonalDetailsDTO> response = null;
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			conn = DBUtil.getConnection();
			int paramPos = 1;
			pStmt = conn.prepareStatement(
					"SELECT application_id, mobile_number, mob_owner, mobile_no_verified, email_id, email_owner, email_activated, "
							+ "otp_verified_on, email_activated_on, application_status, is_approved, is_rejected, document_signed, document_downloaded, "
							+ "comments, last_updated, created_date  FROM  tbl_application_master where delete_flag = ? ");
			pStmt.setInt(paramPos++, 0);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				response = new ArrayList<PersonalDetailsDTO>();
				while (rSet.next()) {
					result = new PersonalDetailsDTO();
					result.setApplication_id(rSet.getInt("application_id"));
					result.setMobile_number(rSet.getLong("mobile_number"));
					result.setMobile_owner(rSet.getString("mob_owner"));
					result.setMobile_number_verified(rSet.getInt("mobile_no_verified"));
					result.setEmail(rSet.getString("email_id"));
					result.setEmail_owner(rSet.getString("email_owner"));
					result.setEmail_id_verified(rSet.getInt("email_activated"));
					result.setOtpVerifiedAt(rSet.getString("otp_verified_on"));
					result.setEmailVerifiedAt(rSet.getString("email_activated_on"));
					result.setApplicationStatus(rSet.getInt("application_status"));
					result.setIsAproved(rSet.getInt("is_approved"));
					result.setIsRejected(rSet.getInt("is_rejected"));
					result.setDocumentSigned(rSet.getInt("document_signed"));
					result.setDocumentDownloaded(rSet.getInt("document_downloaded"));
					result.setComments(rSet.getString("comments"));
					String lastUpdated = rSet.getString("last_updated");
					result.setLastUpdatedAt(lastUpdated);
					String createdOn = rSet.getString("created_date");
					result.setCreatedAt(createdOn);
					String exactTime = Utility.getTimeTaken(createdOn, lastUpdated);
					result.setExactTime(exactTime);
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
	 * Method to get the pan card details for the given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param applicationID
	 * @return
	 */
	public PanCardDetailsDTO getPanCardDetails(int applicationID) {
		PanCardDetailsDTO response = new PanCardDetailsDTO();
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			conn = DBUtil.getConnection();
			int paramPos = 1;
			pStmt = conn.prepareStatement(
					"SELECT application_id, applicant_name, first_name, middle_name, last_name, pan_card, aadhar_no, dob, "
							+ "pan_card_verified, is_approved, is_rejected, comments, last_updated, created_on "
							+ " FROM  tbl_pancard_details where application_id = ? ");
			pStmt.setInt(paramPos++, applicationID);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					response = new PanCardDetailsDTO();
					response.setApplication_id(rSet.getInt("application_id"));
					response.setApplicant_name(rSet.getString("applicant_name"));
					response.setFirst_name(rSet.getString("first_name"));
					response.setMiddle_name(rSet.getString("middle_name"));
					response.setLast_name(rSet.getString("last_name"));
					response.setPan_card(rSet.getString("pan_card"));
					response.setAadharNo(rSet.getLong("aadhar_no"));
					response.setDob(rSet.getString("dob"));
					response.setPan_card_verified(rSet.getInt("pan_card_verified"));
					response.setIsAproved(rSet.getInt("is_approved"));
					response.setIsRejected(rSet.getInt("is_rejected"));
					response.setComments(rSet.getString("comments"));
					response.setLastUpdatedAt(rSet.getString("last_updated"));
					response.setCreatedAt(rSet.getString("created_on"));
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
	 * Method to personal details for given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param appliccationId
	 * @return
	 */
	public PersonalDetailsDTO getPersonalDetails(int applicationId) {
		PersonalDetailsDTO response = new PersonalDetailsDTO();
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			conn = DBUtil.getConnection();
			int paramPos = 1;
			pStmt = conn.prepareStatement(
					"SELECT application_id, applicant_name, mothersName, fathersName, gender, marital_status, "
							+ "annual_income, trading_experience, occupation, politically_exposed, "
							+ "is_approved, is_rejected, comments, last_updated, created_on "
							+ " FROM  tbl_account_holder_personal_details where application_id = ? ");
			pStmt.setInt(paramPos++, applicationId);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					response = new PersonalDetailsDTO();
					response.setApplication_id(rSet.getInt("application_id"));
					response.setApplicant_name(rSet.getString("applicant_name"));
					response.setMothersName(rSet.getString("applicant_name"));
					response.setFathersName(rSet.getString("applicant_name"));
					response.setGender(rSet.getString("applicant_name"));
					response.setMarital_status(rSet.getString("applicant_name"));
					response.setAnnual_income(rSet.getString("applicant_name"));
					response.setTrading_experience(rSet.getString("applicant_name"));
					response.setOccupation(rSet.getString("applicant_name"));
					response.setPolitically_exposed(rSet.getString("applicant_name"));
					response.setIsAproved(rSet.getInt("is_approved"));
					response.setIsRejected(rSet.getInt("is_rejected"));
					response.setComments(rSet.getString("comments"));
					response.setLastUpdatedAt(rSet.getString("last_updated"));
					response.setCreatedAt(rSet.getString("created_on"));
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
	 * Method to get communication address for the given applicatio id
	 * 
	 * @author GOWRI SANKAR R
	 * @param applicationId
	 * @return
	 */
	public AddressDTO getCommunicationAddress(int applicationId) {
		AddressDTO response = new AddressDTO();
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paramPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"SELECT application_id, flat_no, street, pin, city, district, state, is_approved, is_rejected, comments, created_on, last_updated "
							+ " FROM  tbl_communication_address where application_id = ? ");
			pStmt.setInt(paramPos++, applicationId);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					response = new AddressDTO();
					response.setApplication_id(rSet.getInt("application_id"));
					response.setFlat_no(rSet.getString("flat_no"));
					response.setStreet(rSet.getString("street"));
					response.setPin(rSet.getInt("pin"));
					response.setCity(rSet.getString("city"));
					response.setDistrict(rSet.getString("district"));
					response.setState(rSet.getString("state"));
					response.setIsAproved(rSet.getInt("is_approved"));
					response.setIsRejected(rSet.getInt("is_rejected"));
					response.setComments(rSet.getString("comments"));
					response.setLastUpdatedAt(rSet.getString("last_updated"));
					response.setCreatedAt(rSet.getString("created_on"));
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
	 * Method to get permanent address for the given applicatio id
	 * 
	 * @author GOWRI SANKAR R
	 * @param applicationId
	 * @return
	 */
	public AddressDTO getPermanentAddress(int applicationId) {
		AddressDTO response = new AddressDTO();
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paramPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"SELECT application_id, flat_no, street, pin, city, district, state, is_approved, is_rejected, comments, created_on, last_updated "
							+ " FROM  tbl_permanent_address where application_id = ? ");
			pStmt.setInt(paramPos++, applicationId);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					response = new AddressDTO();
					response.setApplication_id(rSet.getInt("application_id"));
					response.setFlat_no(rSet.getString("flat_no"));
					response.setStreet(rSet.getString("street"));
					response.setPin(rSet.getInt("pin"));
					response.setCity(rSet.getString("city"));
					response.setDistrict(rSet.getString("district"));
					response.setState(rSet.getString("state"));
					response.setIsAproved(rSet.getInt("is_approved"));
					response.setIsRejected(rSet.getInt("is_rejected"));
					response.setComments(rSet.getString("comments"));
					response.setLastUpdatedAt(rSet.getString("last_updated"));
					response.setCreatedAt(rSet.getString("created_on"));
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
	 * Method to get the bank account details
	 * 
	 * @author GOWRI SANKAR R
	 * @param applicationId
	 * @return
	 */
	public BankDetailsDTO getBankDetails(int applicationId) {
		BankDetailsDTO response = new BankDetailsDTO();
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paramPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"SELECT application_id, bank_name, micr_code, account_holder_name, ifsc_code, bank_account_no, "
							+ "bank_address, account_type, is_approved, is_rejected, comments, last_updated, created_on "
							+ " FROM  tbl_bank_account_details where application_id = ? ");
			pStmt.setInt(paramPos++, applicationId);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					response = new BankDetailsDTO();
					response.setApplication_id(rSet.getInt("application_id"));
					response.setBankName(rSet.getString("bank_name"));
					response.setMicrCode(rSet.getString("micr_code"));
					response.setAccount_holder_name(rSet.getString("account_holder_name"));
					response.setIfsc_code(rSet.getString("ifsc_code"));
					response.setBank_account_no(rSet.getString("bank_account_no"));
					response.setBankAddress(rSet.getString("bank_address"));
					response.setAccount_type(rSet.getString("account_type"));
					response.setIsAproved(rSet.getInt("is_approved"));
					response.setIsRejected(rSet.getInt("is_rejected"));
					response.setComments(rSet.getString("comments"));
					response.setLastUpdatedAt(rSet.getString("last_updated"));
					response.setCreatedAt(rSet.getString("created_on"));
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
	 * Method to get the exch details for the given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param applicationId
	 * @return
	 */
	public ExchDetailsDTO getExchDetails(int applicationId) {
		ExchDetailsDTO response = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paramPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"SELECT application_id, nse_eq, bse_eq, mf, nse_fo, bse_fo, cds, bcd, mcx, icex, nse_com, bse_com, last_updated, created_on "
							+ " FROM  tbl_exch_segments where application_id = ? ");
			pStmt.setInt(paramPos++, applicationId);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					response = new ExchDetailsDTO();
					response.setApplication_id(rSet.getInt("application_id"));
					response.setNse_eq(rSet.getInt("nse_eq"));
					response.setBse_eq(rSet.getInt("bse_eq"));
					response.setMf(rSet.getInt("mf"));
					response.setNse_fo(rSet.getInt("nse_fo"));
					response.setBse_fo(rSet.getInt("bse_fo"));
					response.setCds(rSet.getInt("cds"));
					response.setBcd(rSet.getInt("bcd"));
					response.setMcx(rSet.getInt("mcx"));
					response.setIcex(rSet.getInt("icex"));
					response.setNse_com(rSet.getInt("nse_com"));
					response.setBse_com(rSet.getInt("bse_com"));
					response.setLastUpdatedAt(rSet.getString("last_updated"));
					response.setCreatedAt(rSet.getString("created_on"));
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
	 * Method to get the attchemets details for the given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param applicationId
	 * @return
	 */
	public List<ApplicationAttachementsDTO> getApplicationAttachementsDetails(int applicationId) {
		List<ApplicationAttachementsDTO> response = null;
		ApplicationAttachementsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paramPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"SELECT application_id, attachement_type, type_of_proof, attachement_url, is_approved, is_rejected, comments, last_update, created_on, delete_flag "
							+ " FROM  tbl_application_attachements where application_id = ? ");
			pStmt.setInt(paramPos++, applicationId);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				response = new ArrayList<ApplicationAttachementsDTO>();
				while (rSet.next()) {
					result = new ApplicationAttachementsDTO();
					result.setApplication_id(rSet.getInt("application_id"));
					result.setAttachement_type(rSet.getString("attachement_type"));
					result.setType_of_proof(rSet.getString("type_of_proof"));
					result.setAttachement_url(rSet.getString("attachement_url"));
					result.setIsAproved(rSet.getInt("is_approved"));
					result.setIsRejected(rSet.getInt("is_rejected"));
					result.setComments(rSet.getString("comments"));
					result.setLastUpdatedAt(rSet.getString("last_update"));
					result.setCreatedAt(rSet.getString("created_on"));
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
	 * Method to get completed records from the data base
	 * 
	 * @author GOWRI SANKAR R
	 * @return
	 */
	public List<PersonalDetailsDTO> getCompletedRecords() {
		List<PersonalDetailsDTO> response = new ArrayList<PersonalDetailsDTO>();
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			conn = DBUtil.getConnection();
			int paramPos = 1;
			pStmt = conn.prepareStatement("SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id,"
					+ "a.email_activated,a.application_status , a.document_signed , a.document_downloaded , "
					+ "b.applicant_name , b.mothersName, b.fathersName, b.gender, b.marital_status, b.annual_income, "
					+ "b.trading_experience, b.occupation, b.politically_exposed , c.pan_card ,c.dob , d.branch_name ,"
					+ "d.verified_by , d.verified_by_desigination  FROM  tbl_application_master A "
					+ "inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
					+ "inner join tbl_pancard_details c on a.application_id = c.application_id "
					+ "inner join tbl_backoffice_request_parameter D on a.application_id = d.application_id  "
					+ "where a.is_completed = ? order by a.created_date desc");
			pStmt.setInt(paramPos++, 1);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new PersonalDetailsDTO();
					int applicationStatus = rSet.getInt("a.application_status");
					result.setApplication_id(rSet.getInt("a.application_id"));
					result.setMobile_number(rSet.getLong("a.mobile_number"));
					result.setMobile_number_verified(rSet.getInt("a.mobile_no_verified"));
					result.setEmail(rSet.getString("a.email_id"));
					result.setEmail_id_verified(rSet.getInt("a.email_activated"));
					result.setApplicationStatus(applicationStatus);
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
					result.setDocumentSigned(rSet.getInt("a.document_signed"));
					result.setDocumentDownloaded(rSet.getInt("a.document_downloaded"));
					result.setBranchName(rSet.getString("d.branch_name"));
					result.setVerifiedBy(rSet.getString("d.verified_by"));
					result.setVerifiedByDesigination(rSet.getString("d.verified_by_desigination"));
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
	 * method to get the completed list from the data base using time
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public List<PersonalDetailsDTO> getCompletedRecordsWithTime(AdminDTO pDto) {
		List<PersonalDetailsDTO> response = new ArrayList<PersonalDetailsDTO>();
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			conn = DBUtil.getConnection();
			int paramPos = 1;
			pStmt = conn.prepareStatement("SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id,"
					+ "a.email_activated,a.application_status , a.document_signed , a.document_downloaded , "
					+ "b.applicant_name , b.mothersName, b.fathersName, b.gender, b.marital_status, b.annual_income, "
					+ "b.trading_experience, b.occupation, b.politically_exposed , c.pan_card ,c.dob , d.branch_name ,"
					+ "d.verified_by , d.verified_by_desigination  FROM  tbl_application_master A "
					+ "inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
					+ "inner join tbl_pancard_details c on a.application_id = c.application_id "
					+ "inner join tbl_backoffice_request_parameter D on a.application_id = d.application_id  "
					+ "where a.is_completed = ? and a.last_updated >= ? and a.last_updated <= ? order by a.created_date desc");
			pStmt.setInt(paramPos++, 1);
			pStmt.setString(paramPos++, pDto.getStartDate() + " 00:00:00");
			pStmt.setString(paramPos++, pDto.getEndDate() + " 23:59:00");
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new PersonalDetailsDTO();
					int applicationStatus = rSet.getInt("a.application_status");
					result.setApplication_id(rSet.getInt("a.application_id"));
					result.setMobile_number(rSet.getLong("a.mobile_number"));
					result.setMobile_number_verified(rSet.getInt("a.mobile_no_verified"));
					result.setEmail(rSet.getString("a.email_id"));
					result.setEmail_id_verified(rSet.getInt("a.email_activated"));
					result.setApplicationStatus(applicationStatus);
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
					result.setDocumentSigned(rSet.getInt("a.document_signed"));
					result.setDocumentDownloaded(rSet.getInt("a.document_downloaded"));
					result.setBranchName(rSet.getString("d.branch_name"));
					result.setVerifiedBy(rSet.getString("d.verified_by"));
					result.setVerifiedByDesigination(rSet.getString("d.verified_by_desigination"));
					result.setStatus("Completed");
					result.setExactStatus("Completed");
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
	 * Get user record from the data base which are in IN-process
	 * 
	 * @author GOWRI SANKAR R
	 * @return
	 */
	public List<PersonalDetailsDTO> getInprogressRecordsByTime(AdminDTO pDto) {
		List<PersonalDetailsDTO> response = new ArrayList<PersonalDetailsDTO>();
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paramPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated,"
							+ "a.application_status , a.document_signed , a.document_downloaded , b.applicant_name , b.mothersName, "
							+ "b.fathersName, b.gender, b.marital_status, b.annual_income, b.trading_experience, b.occupation, "
							+ "b.politically_exposed , c.pan_card ,c.dob  FROM  tbl_application_master A "
							+ "inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
							+ "inner join tbl_pancard_details c on a.application_id = c.application_id where a.is_completed = ? and "
							+ "a.is_approved = ? and a.is_rejected = ? and a.created_date >= ? and a.created_date <= ? "
							+ "order by a.created_date desc");
			pStmt.setInt(paramPos++, 0);
			pStmt.setInt(paramPos++, 0);
			pStmt.setInt(paramPos++, 0);
			pStmt.setString(paramPos++, pDto.getStartDate() + " 00:00:00");
			pStmt.setString(paramPos++, pDto.getEndDate() + " 23:59:00");
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new PersonalDetailsDTO();
					int applicationStatus = rSet.getInt("a.application_status");
					result.setApplication_id(rSet.getInt("a.application_id"));
					result.setMobile_number(rSet.getLong("a.mobile_number"));
					result.setMobile_number_verified(rSet.getInt("a.mobile_no_verified"));
					result.setEmail(rSet.getString("a.email_id"));
					result.setEmail_id_verified(rSet.getInt("a.email_activated"));
					result.setApplicationStatus(applicationStatus);
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
					result.setDocumentSigned(rSet.getInt("a.document_signed"));
					result.setDocumentDownloaded(rSet.getInt("a.document_downloaded"));
					if (applicationStatus == eKYCConstant.OTP_VERIFIED
							|| applicationStatus == eKYCConstant.PAN_CARD_UPDATED) {
						result.setStatus("Pancard Details");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.BASIC_DETAILS_UPDATED) {
						result.setStatus("Basic Details");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.COMMUNICATION_ADDRESS_UPDATED) {
						result.setStatus("Comminication Address");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.PERMANENT_ADDRESS_UPDATED) {
						result.setStatus("Permanent Address");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.BANK_DETAILS_UPDATED) {
						result.setStatus("Bank Details");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.EXCH_UPDATED) {
						result.setStatus("EXCH");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.ATTACHEMENT_UPLOADED) {
						result.setStatus("Document Uploaded");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.IPV_UPLOADED) {
						result.setStatus("Ipv Uploaded");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.DOCUMENT_DOWNLOADED) {
						result.setStatus("Document Downloaded");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.DOCUMENT_SIGNED) {
						result.setStatus("Document Signed");
						result.setExactStatus("In Process");
					}
					if (applicationStatus > eKYCConstant.DOCUMENT_SIGNED) {
						result.setStatus("Approval In Process");
						result.setExactStatus("Review");
					}
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
	 * Get user record from the data base which are in IN-process
	 * 
	 * @author GOWRI SANKAR R
	 * @return
	 */
	public List<PersonalDetailsDTO> getRejectedListByTime(AdminDTO pDto) {
		List<PersonalDetailsDTO> response = new ArrayList<PersonalDetailsDTO>();
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paramPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated,"
							+ "a.application_status , a.document_signed , a.document_downloaded , b.applicant_name , b.mothersName, "
							+ "b.fathersName, b.gender, b.marital_status, b.annual_income, b.trading_experience, b.occupation, "
							+ "b.politically_exposed , c.pan_card ,c.dob  FROM  tbl_application_master A "
							+ "inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
							+ "inner join tbl_pancard_details c on a.application_id = c.application_id where a.is_completed = ? and "
							+ "a.is_approved = ? and a.is_rejected = ? and a.rectify_count = ? and a.last_updated >= ? and a.last_updated <= ? "
							+ "order by a.created_date desc");
			pStmt.setInt(paramPos++, 0);
			pStmt.setInt(paramPos++, 0);
			pStmt.setInt(paramPos++, 1);
			pStmt.setInt(paramPos++, 0);
			pStmt.setString(paramPos++, pDto.getStartDate() + " 00:00:00");
			pStmt.setString(paramPos++, pDto.getEndDate() + " 23:59:00");
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new PersonalDetailsDTO();
					int applicationStatus = rSet.getInt("a.application_status");
					result.setApplication_id(rSet.getInt("a.application_id"));
					result.setMobile_number(rSet.getLong("a.mobile_number"));
					result.setMobile_number_verified(rSet.getInt("a.mobile_no_verified"));
					result.setEmail(rSet.getString("a.email_id"));
					result.setEmail_id_verified(rSet.getInt("a.email_activated"));
					result.setApplicationStatus(applicationStatus);
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
					result.setDocumentSigned(rSet.getInt("a.document_signed"));
					result.setDocumentDownloaded(rSet.getInt("a.document_downloaded"));
					result.setStatus("Rejected");
					result.setExactStatus("Rejected");
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
	 * method to get the completed list from the data base using time
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public List<PersonalDetailsDTO> getCompletedRecords(AdminDTO pDto) {
		List<PersonalDetailsDTO> response = new ArrayList<PersonalDetailsDTO>();
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			conn = DBUtil.getConnection();
			int paramPos = 1;
			pStmt = conn.prepareStatement("SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id,"
					+ "a.email_activated,a.application_status , a.document_signed , a.document_downloaded , "
					+ "b.applicant_name , b.mothersName, b.fathersName, b.gender, b.marital_status, b.annual_income, "
					+ "b.trading_experience, b.occupation, b.politically_exposed , c.pan_card ,c.dob , d.branch_name ,"
					+ "d.verified_by , d.verified_by_desigination  FROM  tbl_application_master A "
					+ "inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
					+ "inner join tbl_pancard_details c on a.application_id = c.application_id "
					+ "inner join tbl_backoffice_request_parameter D on a.application_id = d.application_id  "
					+ "where a.is_completed = ?  order by a.created_date desc");
			pStmt.setInt(paramPos++, 1);
			// pStmt.setString(paramPos++, pDto.getStartDate() + " 00:00:00");
			// pStmt.setString(paramPos++, pDto.getEndDate() + " 23:59:00");
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new PersonalDetailsDTO();
					int applicationStatus = rSet.getInt("a.application_status");
					result.setApplication_id(rSet.getInt("a.application_id"));
					result.setMobile_number(rSet.getLong("a.mobile_number"));
					result.setMobile_number_verified(rSet.getInt("a.mobile_no_verified"));
					result.setEmail(rSet.getString("a.email_id"));
					result.setEmail_id_verified(rSet.getInt("a.email_activated"));
					result.setApplicationStatus(applicationStatus);
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
					result.setDocumentSigned(rSet.getInt("a.document_signed"));
					result.setDocumentDownloaded(rSet.getInt("a.document_downloaded"));
					result.setBranchName(rSet.getString("d.branch_name"));
					result.setVerifiedBy(rSet.getString("d.verified_by"));
					result.setVerifiedByDesigination(rSet.getString("d.verified_by_desigination"));
					result.setStatus("Completed");
					result.setExactStatus("Completed");
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
	 * Get user record from the data base which are in IN-process
	 * 
	 * @author GOWRI SANKAR R
	 * @return
	 */
	public List<PersonalDetailsDTO> getInprogressRecords(AdminDTO pDto) {
		List<PersonalDetailsDTO> response = new ArrayList<PersonalDetailsDTO>();
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paramPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated,"
							+ "a.application_status , a.document_signed , a.document_downloaded , b.applicant_name , b.mothersName, "
							+ "b.fathersName, b.gender, b.marital_status, b.annual_income, b.trading_experience, b.occupation, "
							+ "b.politically_exposed , c.pan_card ,c.dob  FROM  tbl_application_master A "
							+ "inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
							+ "inner join tbl_pancard_details c on a.application_id = c.application_id where a.is_completed = ? and "
							+ "a.is_approved = ? and a.is_rejected = ? order by a.created_date desc");
			pStmt.setInt(paramPos++, 0);
			pStmt.setInt(paramPos++, 0);
			pStmt.setInt(paramPos++, 0);
			// pStmt.setString(paramPos++, pDto.getStartDate() + " 00:00:00");
			// pStmt.setString(paramPos++, pDto.getEndDate() + " 23:59:00");
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new PersonalDetailsDTO();
					int applicationStatus = rSet.getInt("a.application_status");
					result.setApplication_id(rSet.getInt("a.application_id"));
					result.setMobile_number(rSet.getLong("a.mobile_number"));
					result.setMobile_number_verified(rSet.getInt("a.mobile_no_verified"));
					result.setEmail(rSet.getString("a.email_id"));
					result.setEmail_id_verified(rSet.getInt("a.email_activated"));
					result.setApplicationStatus(applicationStatus);
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
					result.setDocumentSigned(rSet.getInt("a.document_signed"));
					result.setDocumentDownloaded(rSet.getInt("a.document_downloaded"));
					if (applicationStatus == eKYCConstant.BASIC_DETAILS_UPDATED) {
						result.setStatus("Basic Details");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.COMMUNICATION_ADDRESS_UPDATED) {
						result.setStatus("Comminication Address");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.PERMANENT_ADDRESS_UPDATED) {
						result.setStatus("Permanent Address");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.BANK_DETAILS_UPDATED) {
						result.setStatus("Bank Details");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.EXCH_UPDATED) {
						result.setStatus("EXCH");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.ATTACHEMENT_UPLOADED) {
						result.setStatus("Document Uploaded");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.IPV_UPLOADED) {
						result.setStatus("Ipv Uploaded");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.DOCUMENT_DOWNLOADED) {
						result.setStatus("Document Downloaded");
						result.setExactStatus("In Process");
					}
					if (applicationStatus == eKYCConstant.DOCUMENT_SIGNED) {
						result.setStatus("Document Signed");
						result.setExactStatus("In Process");
					}
					if (applicationStatus > eKYCConstant.DOCUMENT_SIGNED) {
						result.setStatus("Approval In Process");
						result.setExactStatus("Review");
					}
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
	 * Get user record from the data base which are in IN-process
	 * 
	 * @author GOWRI SANKAR R
	 * @return
	 */
	public List<PersonalDetailsDTO> getRejectedList(AdminDTO pDto) {
		List<PersonalDetailsDTO> response = new ArrayList<PersonalDetailsDTO>();
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paramPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated,"
							+ "a.application_status , a.document_signed , a.document_downloaded , b.applicant_name , b.mothersName, "
							+ "b.fathersName, b.gender, b.marital_status, b.annual_income, b.trading_experience, b.occupation, "
							+ "b.politically_exposed , c.pan_card ,c.dob  FROM  tbl_application_master A "
							+ "inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
							+ "inner join tbl_pancard_details c on a.application_id = c.application_id where a.is_completed = ? and "
							+ "a.is_approved = ? and a.is_rejected = ? and a.rectify_count = ? order by a.created_date desc");
			pStmt.setInt(paramPos++, 0);
			pStmt.setInt(paramPos++, 0);
			pStmt.setInt(paramPos++, 1);
			pStmt.setInt(paramPos++, 0);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new PersonalDetailsDTO();
					int applicationStatus = rSet.getInt("a.application_status");
					result.setApplication_id(rSet.getInt("a.application_id"));
					result.setMobile_number(rSet.getLong("a.mobile_number"));
					result.setMobile_number_verified(rSet.getInt("a.mobile_no_verified"));
					result.setEmail(rSet.getString("a.email_id"));
					result.setEmail_id_verified(rSet.getInt("a.email_activated"));
					result.setApplicationStatus(applicationStatus);
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
					result.setDocumentSigned(rSet.getInt("a.document_signed"));
					result.setDocumentDownloaded(rSet.getInt("a.document_downloaded"));
					result.setStatus("Rejected");
					result.setExactStatus("Rejected");
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
	 * Method to get the retify of document from the data base
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public List<PersonalDetailsDTO> getRetifyListByTime(AdminDTO pDto) {
		List<PersonalDetailsDTO> response = new ArrayList<PersonalDetailsDTO>();
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paramPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated,"
							+ "a.application_status , a.document_signed , a.document_downloaded , b.applicant_name , b.mothersName, "
							+ "b.fathersName, b.gender, b.marital_status, b.annual_income, b.trading_experience, b.occupation, "
							+ "b.politically_exposed , c.pan_card ,c.dob  FROM  tbl_application_master A "
							+ "inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
							+ "inner join tbl_pancard_details c on a.application_id = c.application_id where a.is_completed = ? and "
							+ "a.is_approved = ? and a.is_rejected = ? and a.rectify_count >= ? and a.last_updated >= ? and a.last_updated <= ? "
							+ "order by a.created_date desc");
			pStmt.setInt(paramPos++, 0);
			pStmt.setInt(paramPos++, 0);
			pStmt.setInt(paramPos++, 1);
			pStmt.setInt(paramPos++, 1);
			pStmt.setString(paramPos++, pDto.getStartDate() + " 00:00:00");
			pStmt.setString(paramPos++, pDto.getEndDate() + " 23:59:00");
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new PersonalDetailsDTO();
					int applicationStatus = rSet.getInt("a.application_status");
					result.setApplication_id(rSet.getInt("a.application_id"));
					result.setMobile_number(rSet.getLong("a.mobile_number"));
					result.setMobile_number_verified(rSet.getInt("a.mobile_no_verified"));
					result.setEmail(rSet.getString("a.email_id"));
					result.setEmail_id_verified(rSet.getInt("a.email_activated"));
					result.setApplicationStatus(applicationStatus);
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
					result.setDocumentSigned(rSet.getInt("a.document_signed"));
					result.setDocumentDownloaded(rSet.getInt("a.document_downloaded"));
					result.setStatus("Rectified");
					result.setExactStatus("Rectified");
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
	 * Method to get the retify of document from the data base
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public List<PersonalDetailsDTO> getRetifyList(AdminDTO pDto) {
		List<PersonalDetailsDTO> response = new ArrayList<PersonalDetailsDTO>();
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paramPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated,"
							+ "a.application_status , a.document_signed , a.document_downloaded , b.applicant_name , b.mothersName, "
							+ "b.fathersName, b.gender, b.marital_status, b.annual_income, b.trading_experience, b.occupation, "
							+ "b.politically_exposed , c.pan_card ,c.dob  FROM  tbl_application_master A "
							+ "inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
							+ "inner join tbl_pancard_details c on a.application_id = c.application_id where a.is_completed = ? and "
							+ "a.is_approved = ? and a.is_rejected = ? and a.rectify_count >= ? "
							+ "order by a.created_date desc");
			pStmt.setInt(paramPos++, 0);
			pStmt.setInt(paramPos++, 0);
			pStmt.setInt(paramPos++, 1);
			pStmt.setInt(paramPos++, 1);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new PersonalDetailsDTO();
					int applicationStatus = rSet.getInt("a.application_status");
					result.setApplication_id(rSet.getInt("a.application_id"));
					result.setMobile_number(rSet.getLong("a.mobile_number"));
					result.setMobile_number_verified(rSet.getInt("a.mobile_no_verified"));
					result.setEmail(rSet.getString("a.email_id"));
					result.setEmail_id_verified(rSet.getInt("a.email_activated"));
					result.setApplicationStatus(applicationStatus);
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
					result.setDocumentSigned(rSet.getInt("a.document_signed"));
					result.setDocumentDownloaded(rSet.getInt("a.document_downloaded"));
					result.setStatus("Rectified");
					result.setExactStatus("Rectified");
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

}
