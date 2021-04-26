package com.codespine.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.codespine.dto.AddressDTO;
import com.codespine.dto.AdminDTO;
import com.codespine.dto.AdminDetailsDTO;
import com.codespine.dto.ApplicationAttachementsDTO;
import com.codespine.dto.ApplicationLogDTO;
import com.codespine.dto.BankDetailsDTO;
import com.codespine.dto.BranchListDTO;
import com.codespine.dto.ExchDetailsDTO;
import com.codespine.dto.FileUploadDTO;
import com.codespine.dto.IfscCodeDTO;
import com.codespine.dto.PanCardDetailsDTO;
import com.codespine.dto.PerformanceDTO;
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
	public List<PersonalDetailsDTO> getAllUserRecords(AdminDTO pDto, boolean isAdmin, boolean isBranch,
			boolean isRemishree) {
		List<PersonalDetailsDTO> response = new ArrayList<PersonalDetailsDTO>();
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paramPos = 1;
			conn = DBUtil.getConnection();
			if (isAdmin) {
				pStmt = conn.prepareStatement(
						"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated,"
								+ "a.application_status , a.document_signed , a.document_downloaded , a.ref_code, a.reviewed_by ,c.applicant_name , b.mothersName, "
								+ "b.fathersName, b.gender, b.marital_status, b.annual_income, b.trading_experience, b.occupation, "
								+ "b.politically_exposed , c.pan_card ,c.dob  FROM  tbl_application_master A "
								+ "inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
								+ "inner join tbl_pancard_details c on a.application_id = c.application_id where is_completed = ? order by a.created_date desc");
				pStmt.setInt(paramPos++, 0);
			} else if (isBranch) {
				pStmt = conn.prepareStatement(
						"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated,"
								+ "a.application_status , a.document_signed , a.document_downloaded , a.ref_code, a.reviewed_by ,c.applicant_name , b.mothersName, "
								+ "b.fathersName, b.gender, b.marital_status, b.annual_income, b.trading_experience, b.occupation, "
								+ "b.politically_exposed , c.pan_card ,c.dob  FROM  tbl_application_master A "
								+ "inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
								+ "inner join tbl_pancard_details c on a.application_id = c.application_id where is_completed = ? and a.branch_code = ?  order by a.created_date desc");
				pStmt.setInt(paramPos++, 0);
				pStmt.setString(paramPos++, pDto.getBranchCode());
			} else if (isRemishree) {
				pStmt = conn.prepareStatement(
						"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated,"
								+ "a.application_status , a.document_signed , a.document_downloaded , a.ref_code, a.reviewed_by ,c.applicant_name , b.mothersName, "
								+ "b.fathersName, b.gender, b.marital_status, b.annual_income, b.trading_experience, b.occupation, "
								+ "b.politically_exposed , c.pan_card ,c.dob  FROM  tbl_application_master A "
								+ "inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
								+ "inner join tbl_pancard_details c on a.application_id = c.application_id where is_completed = ? and a.remishree_code = ?  order by a.created_date desc");
				pStmt.setInt(paramPos++, 0);
				pStmt.setString(paramPos++, pDto.getRemishreeCode());
			}
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
					result.setApplicant_name(rSet.getString("c.applicant_name"));
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
					result.setReffCode(rSet.getString("a.ref_code"));
					result.setReviewedBy(rSet.getString("a.reviewed_by"));
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
	 * Method to update the application Attachements for the given application id
	 * and proof type
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
					+ " where a.application_id = ?");
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
					"SELECT name, admin_email, admin_password, designation, branch_code, remishree_code, role FROM  tbl_admin_details where admin_email = ? ");
			pStmt.setString(paromPos++, pDto.getEmail());
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new AdminDTO();
					result.setName(rSet.getString("name"));
					result.setEmail(rSet.getString("admin_email"));
					result.setPassword(rSet.getString("admin_password"));
					result.setDesignation(rSet.getString("designation"));
					result.setRole(rSet.getInt("role"));
					result.setBranchCode(rSet.getString("branch_code"));
					result.setRemishreeCode(rSet.getString("remishree_code"));
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
					+ "c.applicant_name , b.mothersName, b.fathersName, b.gender, b.marital_status, b.annual_income, "
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
					result.setApplicant_name(rSet.getString("c.applicant_name"));
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
	public List<PersonalDetailsDTO> getCompletedRecordsWithTime(AdminDTO pDto, boolean isAdmin, boolean isBranch,
			boolean isRemishree) {
		List<PersonalDetailsDTO> response = new ArrayList<PersonalDetailsDTO>();
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			conn = DBUtil.getConnection();
			int paramPos = 1;
			if (isAdmin) {
				pStmt = conn
						.prepareStatement("SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id,"
								+ "a.email_activated,a.application_status , a.document_signed , a.document_downloaded , a.ref_code ,a.reviewed_by  "
								+ " , a.created_date , a.last_updated , c.applicant_name , b.mothersName, b.fathersName, b.gender, b.marital_status, b.annual_income, "
								+ "b.trading_experience, b.occupation, b.politically_exposed , c.pan_card ,c.dob , d.branch_name ,"
								+ "d.verified_by , d.verified_by_desigination  FROM  tbl_application_master A "
								+ "inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
								+ "inner join tbl_pancard_details c on a.application_id = c.application_id "
								+ "inner join tbl_backoffice_request_parameter D on a.application_id = d.application_id  "
								+ "where a.is_completed = ? and a.last_updated >= ? and a.last_updated <= ? order by a.created_date desc");
				pStmt.setInt(paramPos++, 1);
				pStmt.setString(paramPos++, pDto.getStartDate() + " 00:00:00");
				pStmt.setString(paramPos++, pDto.getEndDate() + " 23:59:00");
			} else if (isBranch) {
				pStmt = conn
						.prepareStatement("SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id,"
								+ "a.email_activated,a.application_status , a.document_signed , a.document_downloaded , a.ref_code ,a.reviewed_by  "
								+ " , a.created_date , a.last_updated , c.applicant_name , b.mothersName, b.fathersName, b.gender, b.marital_status, b.annual_income, "
								+ "b.trading_experience, b.occupation, b.politically_exposed , c.pan_card ,c.dob , d.branch_name ,"
								+ "d.verified_by , d.verified_by_desigination  FROM  tbl_application_master A "
								+ "inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
								+ "inner join tbl_pancard_details c on a.application_id = c.application_id "
								+ "inner join tbl_backoffice_request_parameter D on a.application_id = d.application_id  "
								+ "where a.is_completed = ? and a.last_updated >= ? and a.last_updated <= ? and a.branch_code = ? order by a.created_date desc");
				pStmt.setInt(paramPos++, 1);
				pStmt.setString(paramPos++, pDto.getStartDate() + " 00:00:00");
				pStmt.setString(paramPos++, pDto.getEndDate() + " 23:59:00");
				pStmt.setString(paramPos++, pDto.getBranchCode());
			} else if (isRemishree) {
				pStmt = conn
						.prepareStatement("SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id,"
								+ "a.email_activated,a.application_status , a.document_signed , a.document_downloaded , a.ref_code ,a.reviewed_by  "
								+ " , a.created_date , a.last_updated , c.applicant_name , b.mothersName, b.fathersName, b.gender, b.marital_status, b.annual_income, "
								+ "b.trading_experience, b.occupation, b.politically_exposed , c.pan_card ,c.dob , d.branch_name ,"
								+ "d.verified_by , d.verified_by_desigination  FROM  tbl_application_master A "
								+ "inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
								+ "inner join tbl_pancard_details c on a.application_id = c.application_id "
								+ "inner join tbl_backoffice_request_parameter D on a.application_id = d.application_id  "
								+ "where a.is_completed = ? and a.last_updated >= ? and a.last_updated <= ? and a.remishree_code = ?  order by a.created_date desc");
				pStmt.setInt(paramPos++, 1);
				pStmt.setString(paramPos++, pDto.getStartDate() + " 00:00:00");
				pStmt.setString(paramPos++, pDto.getEndDate() + " 23:59:00");
				pStmt.setString(paramPos++, pDto.getRemishreeCode());
			}

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
					result.setApplicant_name(rSet.getString("c.applicant_name"));
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
					result.setReffCode(rSet.getString("a.ref_code"));
					result.setReviewedBy(rSet.getString("a.reviewed_by"));
					result.setCreatedAt(rSet.getString("a.created_date"));
					result.setLastUpdatedAt(rSet.getString("a.last_updated"));
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
	public List<PersonalDetailsDTO> getInprogressRecordsByTime(AdminDTO pDto, boolean isAdmin, boolean isBranch,
			boolean isRemishree) {
		List<PersonalDetailsDTO> response = new ArrayList<PersonalDetailsDTO>();
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paramPos = 1;
			conn = DBUtil.getConnection();
			if (isAdmin) {
				pStmt = conn.prepareStatement(
						"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated,"
								+ "a.application_status , a.document_signed , a.document_downloaded , a.ref_code ,a.reviewed_by , a.created_date , a.last_updated, c.applicant_name , b.mothersName, "
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
			} else if (isBranch) {
				pStmt = conn.prepareStatement(
						"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated,"
								+ "a.application_status , a.document_signed , a.document_downloaded , a.ref_code ,a.reviewed_by , a.created_date , a.last_updated, c.applicant_name , b.mothersName, "
								+ "b.fathersName, b.gender, b.marital_status, b.annual_income, b.trading_experience, b.occupation, "
								+ "b.politically_exposed , c.pan_card ,c.dob  FROM  tbl_application_master A "
								+ "inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
								+ "inner join tbl_pancard_details c on a.application_id = c.application_id where a.is_completed = ? and "
								+ "a.is_approved = ? and a.is_rejected = ? and a.created_date >= ? and a.created_date <= ? and a.branch_code = ? "
								+ "order by a.created_date desc");
				pStmt.setInt(paramPos++, 0);
				pStmt.setInt(paramPos++, 0);
				pStmt.setInt(paramPos++, 0);
				pStmt.setString(paramPos++, pDto.getStartDate() + " 00:00:00");
				pStmt.setString(paramPos++, pDto.getEndDate() + " 23:59:00");
				pStmt.setString(paramPos++, pDto.getBranchCode());
			} else if (isRemishree) {
				pStmt = conn.prepareStatement(
						"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated,"
								+ "a.application_status , a.document_signed , a.document_downloaded , a.ref_code ,a.reviewed_by , a.created_date , a.last_updated, c.applicant_name , b.mothersName, "
								+ "b.fathersName, b.gender, b.marital_status, b.annual_income, b.trading_experience, b.occupation, "
								+ "b.politically_exposed , c.pan_card ,c.dob  FROM  tbl_application_master A "
								+ "inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
								+ "inner join tbl_pancard_details c on a.application_id = c.application_id where a.is_completed = ? and "
								+ "a.is_approved = ? and a.is_rejected = ? and a.created_date >= ? and a.created_date <= ? and a.remishree_code = ?  "
								+ "order by a.created_date desc");
				pStmt.setInt(paramPos++, 0);
				pStmt.setInt(paramPos++, 0);
				pStmt.setInt(paramPos++, 0);
				pStmt.setString(paramPos++, pDto.getStartDate() + " 00:00:00");
				pStmt.setString(paramPos++, pDto.getEndDate() + " 23:59:00");
				pStmt.setString(paramPos++, pDto.getRemishreeCode());
			}
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
					result.setApplicant_name(rSet.getString("c.applicant_name"));
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
					result.setReffCode(rSet.getString("a.ref_code"));
					result.setReviewedBy(rSet.getString("a.reviewed_by"));
					result.setCreatedAt(rSet.getString("a.created_date"));
					result.setLastUpdatedAt(rSet.getString("a.last_updated"));
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
	public List<PersonalDetailsDTO> getRejectedListByTime(AdminDTO pDto, boolean isAdmin, boolean isBranch,
			boolean isRemishree) {
		List<PersonalDetailsDTO> response = new ArrayList<PersonalDetailsDTO>();
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paramPos = 1;
			conn = DBUtil.getConnection();
			if (isAdmin) {
				pStmt = conn.prepareStatement(
						"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated,"
								+ "a.application_status , a.document_signed , a.document_downloaded , a.ref_code ,a.reviewed_by , a.created_date , a.last_updated , c.applicant_name , b.mothersName, "
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
			} else if (isBranch) {
				pStmt = conn.prepareStatement(
						"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated,"
								+ "a.application_status , a.document_signed , a.document_downloaded , a.ref_code ,a.reviewed_by , a.created_date , a.last_updated , c.applicant_name , b.mothersName, "
								+ "b.fathersName, b.gender, b.marital_status, b.annual_income, b.trading_experience, b.occupation, "
								+ "b.politically_exposed , c.pan_card ,c.dob  FROM  tbl_application_master A "
								+ "inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
								+ "inner join tbl_pancard_details c on a.application_id = c.application_id where a.is_completed = ? and "
								+ "a.is_approved = ? and a.is_rejected = ? and a.rectify_count = ? and a.last_updated >= ? "
								+ "and a.last_updated <= ? and a.branch_code = ? order by a.created_date desc");
				pStmt.setInt(paramPos++, 0);
				pStmt.setInt(paramPos++, 0);
				pStmt.setInt(paramPos++, 1);
				pStmt.setInt(paramPos++, 0);
				pStmt.setString(paramPos++, pDto.getStartDate() + " 00:00:00");
				pStmt.setString(paramPos++, pDto.getEndDate() + " 23:59:00");
				pStmt.setString(paramPos++, pDto.getBranchCode());
			} else if (isRemishree) {
				pStmt = conn.prepareStatement(
						"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated,"
								+ "a.application_status , a.document_signed , a.document_downloaded , a.ref_code ,a.reviewed_by , a.created_date , a.last_updated , c.applicant_name , b.mothersName, "
								+ "b.fathersName, b.gender, b.marital_status, b.annual_income, b.trading_experience, b.occupation, "
								+ "b.politically_exposed , c.pan_card ,c.dob  FROM  tbl_application_master A "
								+ "inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
								+ "inner join tbl_pancard_details c on a.application_id = c.application_id where a.is_completed = ? and "
								+ "a.is_approved = ? and a.is_rejected = ? and a.rectify_count = ? and a.last_updated >= ? "
								+ "and a.last_updated <= ? and a.remishree_code = ? order by a.created_date desc");
				pStmt.setInt(paramPos++, 0);
				pStmt.setInt(paramPos++, 0);
				pStmt.setInt(paramPos++, 1);
				pStmt.setInt(paramPos++, 0);
				pStmt.setString(paramPos++, pDto.getStartDate() + " 00:00:00");
				pStmt.setString(paramPos++, pDto.getEndDate() + " 23:59:00");
				pStmt.setString(paramPos++, pDto.getRemishreeCode());
			}
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
					result.setApplicant_name(rSet.getString("c.applicant_name"));
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
					result.setReffCode(rSet.getString("a.ref_code"));
					result.setReviewedBy(rSet.getString("a.reviewed_by"));
					result.setCreatedAt(rSet.getString("a.created_date"));
					result.setLastUpdatedAt(rSet.getString("a.last_updated"));
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
					+ "c.applicant_name , b.mothersName, b.fathersName, b.gender, b.marital_status, b.annual_income, "
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
					result.setApplicant_name(rSet.getString("c.applicant_name"));
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
							+ "a.application_status , a.document_signed , a.document_downloaded , c.applicant_name , b.mothersName, "
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
					result.setApplicant_name(rSet.getString("c.applicant_name"));
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
							+ "a.application_status , a.document_signed , a.document_downloaded , c.applicant_name , b.mothersName, "
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
					result.setApplicant_name(rSet.getString("c.applicant_name"));
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
	public List<PersonalDetailsDTO> getRetifyListByTime(AdminDTO pDto, boolean isAdmin, boolean isBranch,
			boolean isRemishree) {
		List<PersonalDetailsDTO> response = new ArrayList<PersonalDetailsDTO>();
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paramPos = 1;
			conn = DBUtil.getConnection();
			if (isAdmin) {
				pStmt = conn.prepareStatement(
						"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated,"
								+ "a.application_status , a.document_signed , a.document_downloaded , a.ref_code ,a.reviewed_by , a.created_date , a.last_updated , c.applicant_name , b.mothersName, "
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
			} else if (isBranch) {
				pStmt = conn.prepareStatement(
						"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated,"
								+ "a.application_status , a.document_signed , a.document_downloaded , a.ref_code ,a.reviewed_by , a.created_date , a.last_updated , c.applicant_name , b.mothersName, "
								+ "b.fathersName, b.gender, b.marital_status, b.annual_income, b.trading_experience, b.occupation, "
								+ "b.politically_exposed , c.pan_card ,c.dob  FROM  tbl_application_master A "
								+ "inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
								+ "inner join tbl_pancard_details c on a.application_id = c.application_id where a.is_completed = ? and "
								+ "a.is_approved = ? and a.is_rejected = ? and a.rectify_count >= ? and a.last_updated >= ? and"
								+ " a.last_updated <= ? and a.branch_code = ? order by a.created_date desc");
				pStmt.setInt(paramPos++, 0);
				pStmt.setInt(paramPos++, 0);
				pStmt.setInt(paramPos++, 1);
				pStmt.setInt(paramPos++, 1);
				pStmt.setString(paramPos++, pDto.getStartDate() + " 00:00:00");
				pStmt.setString(paramPos++, pDto.getEndDate() + " 23:59:00");
				pStmt.setString(paramPos++, pDto.getBranchCode());
			} else if (isRemishree) {
				pStmt = conn.prepareStatement(
						"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated,"
								+ "a.application_status , a.document_signed , a.document_downloaded , a.ref_code ,a.reviewed_by , a.created_date , a.last_updated , c.applicant_name , b.mothersName, "
								+ "b.fathersName, b.gender, b.marital_status, b.annual_income, b.trading_experience, b.occupation, "
								+ "b.politically_exposed , c.pan_card ,c.dob  FROM  tbl_application_master A "
								+ "inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
								+ "inner join tbl_pancard_details c on a.application_id = c.application_id where a.is_completed = ? and "
								+ "a.is_approved = ? and a.is_rejected = ? and a.rectify_count >= ? and a.last_updated >= ? "
								+ "and a.last_updated <= ?  and a.remishree_code = ? order by a.created_date desc");
				pStmt.setInt(paramPos++, 0);
				pStmt.setInt(paramPos++, 0);
				pStmt.setInt(paramPos++, 1);
				pStmt.setInt(paramPos++, 1);
				pStmt.setString(paramPos++, pDto.getStartDate() + " 00:00:00");
				pStmt.setString(paramPos++, pDto.getEndDate() + " 23:59:00");
				pStmt.setString(paramPos++, pDto.getRemishreeCode());
			}
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
					result.setApplicant_name(rSet.getString("c.applicant_name"));
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
					result.setReffCode(rSet.getString("a.ref_code"));
					result.setReviewedBy(rSet.getString("a.reviewed_by"));
					result.setCreatedAt(rSet.getString("a.created_date"));
					result.setLastUpdatedAt(rSet.getString("a.last_updated"));
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
							+ "a.application_status , a.document_signed , a.document_downloaded , c.applicant_name , b.mothersName, "
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
					result.setApplicant_name(rSet.getString("c.applicant_name"));
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
	 * 
	 * 
	 * @author
	 * @param
	 * @return
	 */

	public List<PerformanceDTO> PerformanceChart(List<String> oneMonthDates) {
		List<PerformanceDTO> response = null;
		PerformanceDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"select date_format(created_date, '%d-%m-%Y') as date, application_status as status, count(*) as count from tbl_application_master "
							+ "where created_date >= DATE_SUB(NOW(), INTERVAL 300 DAY) "
							+ "group by date_format(created_date, '%d-%m-%Y') having application_status < 11 order by created_date desc");
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				response = new ArrayList<PerformanceDTO>();
				while (rSet.next()) {
					result = new PerformanceDTO();
					String actualDate = rSet.getString("date");
					if (oneMonthDates.contains(actualDate)) {
						result.setDate(rSet.getString("date"));
						result.setTotalrecord(rSet.getInt("count"));
						oneMonthDates.remove(actualDate);
						response.add(result);
					}
				}
			}
			if (oneMonthDates != null && oneMonthDates.size() > 0) {
				for (int itr = 0; itr < oneMonthDates.size(); itr++) {
					result = new PerformanceDTO();
					result.setDate(oneMonthDates.get(itr));
					result.setTotalrecord(0);
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

	public List<PerformanceDTO> PerformanceChartsigned(List<String> oneMonthDates) {
		List<PerformanceDTO> response = null;
		PerformanceDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"select date_format(created_date, '%d-%m-%y') as date, application_status as status, count(*) as count from tbl_application_master "
							+ "where created_date >= DATE_SUB(NOW(), INTERVAL 300 DAY) "
							+ "group by date_format(created_date, '%d-%m-%y'), application_status having application_status in (11, 12)");
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				response = new ArrayList<PerformanceDTO>();
				while (rSet.next()) {
					result = new PerformanceDTO();
					String actualDate = rSet.getString("date");
					if (oneMonthDates.contains(actualDate)) {
						result.setDate(rSet.getString("date"));
						result.setTotalrecord(rSet.getInt("count"));
						oneMonthDates.remove(actualDate);
						response.add(result);
					}
				}
			}
			if (oneMonthDates != null && oneMonthDates.size() > 0) {
				for (int sign = 0; sign < oneMonthDates.size(); sign++) {
					result = new PerformanceDTO();
					result.setDate(oneMonthDates.get(sign));
					result.setTotalrecord(0);
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

	public List<PerformanceDTO> PerformanceChartapproved(List<String> oneMonthDates) {
		List<PerformanceDTO> response = null;
		PerformanceDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"select date_format(created_date, '%d-%m-%y') as date, application_status as status, count(*) as count from tbl_application_master "
							+ "where created_date >= DATE_SUB(NOW(), INTERVAL 300 DAY) "
							+ "group by date_format(created_date, '%d-%m-%y'), application_status having application_status =22");
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				response = new ArrayList<PerformanceDTO>();
				while (rSet.next()) {
					result = new PerformanceDTO();
					String actualDate = rSet.getString("date");
					if (oneMonthDates.contains(actualDate)) {
						result.setDate(rSet.getString("date"));
						result.setTotalrecord(rSet.getInt("count"));
						oneMonthDates.remove(actualDate);
						response.add(result);
					}
				}
			}
			if (oneMonthDates != null && oneMonthDates.size() > 0) {
				for (int appr = 0; appr < oneMonthDates.size(); appr++) {
					result = new PerformanceDTO();
					result.setDate(oneMonthDates.get(appr));
					result.setTotalrecord(0);
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
	 * Method to get the user report list from the data base
	 * 
	 * @author GOWRI SANKAR R
	 * @return
	 */
	public List<PersonalDetailsDTO> getUserReportList(AdminDTO pDto, boolean isAdmin, boolean isBranch,
			boolean isRemishree) {
		List<PersonalDetailsDTO> response = new ArrayList<PersonalDetailsDTO>();
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			conn = DBUtil.getConnection();
			int paramPos = 1;
			if (isAdmin) {
				pStmt = conn.prepareStatement(
						"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated, "
								+ " a.application_status , a.document_signed , a.document_downloaded , a.ref_code , a.created_date , a.last_updated ,"
								+ " c.applicant_name , b.mothersName, "
								+ " b.fathersName, b.gender, b.marital_status, b.annual_income, b.trading_experience, b.occupation, "
								+ " b.politically_exposed , c.pan_card ,c.dob  FROM  tbl_application_master A "
								+ " left join tbl_account_holder_personal_details B on a.application_id = b.application_id "
								+ " left join tbl_pancard_details c on a.application_id = c.application_id order by a.created_date desc limit 500");
			} else if (isBranch) {
				pStmt = conn.prepareStatement(
						"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated, "
								+ " a.application_status , a.document_signed , a.document_downloaded , a.ref_code , a.created_date , a.last_updated ,"
								+ " c.applicant_name , b.mothersName, "
								+ " b.fathersName, b.gender, b.marital_status, b.annual_income, b.trading_experience, b.occupation, "
								+ " b.politically_exposed , c.pan_card ,c.dob  FROM  tbl_application_master A "
								+ " left join tbl_account_holder_personal_details B on a.application_id = b.application_id "
								+ " left join tbl_pancard_details c on a.application_id = c.application_id order where a.branch_code = ? order by a.created_date desc limit 500");
				pStmt.setString(paramPos++, pDto.getBranchCode());
			} else if (isRemishree) {
				pStmt = conn.prepareStatement(
						"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated, "
								+ " a.application_status , a.document_signed , a.document_downloaded , a.ref_code , a.created_date , a.last_updated ,"
								+ " c.applicant_name , b.mothersName, "
								+ " b.fathersName, b.gender, b.marital_status, b.annual_income, b.trading_experience, b.occupation, "
								+ " b.politically_exposed , c.pan_card ,c.dob  FROM  tbl_application_master A "
								+ " left join tbl_account_holder_personal_details B on a.application_id = b.application_id "
								+ " left join tbl_pancard_details c on a.application_id = c.application_id  where a.remishree_code = ? order by a.created_date desc limit 500");
				pStmt.setString(paramPos++, pDto.getRemishreeCode());
			}

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
					result.setApplicant_name(rSet.getString("c.applicant_name"));
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
					result.setReffCode(rSet.getString("a.ref_code"));
					result.setCreatedAt(rSet.getString("a.created_date"));
					result.setLastUpdatedAt(rSet.getString("a.last_updated"));
					if (applicationStatus < eKYCConstant.OTP_VERIFIED) {
						result.setStatus("Registered");
						result.setExactStatus("In Process");
					}
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
	 * Method to get the report list for the user's in the given date
	 * 
	 * @author GOWRI SANKAR R
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<PersonalDetailsDTO> getUserReportListByTime(AdminDTO pDto, boolean isAdmin, boolean isBranch,
			boolean isRemishree) {
		List<PersonalDetailsDTO> response = new ArrayList<PersonalDetailsDTO>();
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paramPos = 1;
			conn = DBUtil.getConnection();
			if (isAdmin) {
				pStmt = conn.prepareStatement(
						"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated, "
								+ " a.application_status , a.document_signed , a.document_downloaded , a.ref_code , a.created_date , a.last_updated ,"
								+ " c.applicant_name , b.mothersName, "
								+ " b.fathersName, b.gender, b.marital_status, b.annual_income, b.trading_experience, b.occupation, "
								+ " b.politically_exposed , c.pan_card ,c.dob  FROM  tbl_application_master A "
								+ " left join tbl_account_holder_personal_details B on a.application_id = b.application_id "
								+ " left join tbl_pancard_details c on a.application_id = c.application_id where a.created_date >= ? "
								+ " and a.created_date <= ? order by a.created_date desc limit 1000");
				pStmt.setString(paramPos++, pDto.getStartDate() + " 00:00:00");
				pStmt.setString(paramPos++, pDto.getEndDate() + " 23:59:59");
			} else if (isBranch) {
				pStmt = conn.prepareStatement(
						"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated, "
								+ " a.application_status , a.document_signed , a.document_downloaded , a.ref_code , a.created_date , a.last_updated ,"
								+ " c.applicant_name , b.mothersName, "
								+ " b.fathersName, b.gender, b.marital_status, b.annual_income, b.trading_experience, b.occupation, "
								+ " b.politically_exposed , c.pan_card ,c.dob  FROM  tbl_application_master A "
								+ " left join tbl_account_holder_personal_details B on a.application_id = b.application_id "
								+ " left join tbl_pancard_details c on a.application_id = c.application_id where a.created_date >= ? "
								+ " and a.created_date <= ? and a.branch_code = ? order by a.created_date desc limit 1000");
				pStmt.setString(paramPos++, pDto.getStartDate() + " 00:00:00");
				pStmt.setString(paramPos++, pDto.getEndDate() + " 23:59:59");
				pStmt.setString(paramPos++, pDto.getBranchCode());
			} else if (isRemishree) {
				pStmt = conn.prepareStatement(
						"SELECT a.application_id, a.mobile_number, a.mobile_no_verified, a.email_id, a.email_activated, "
								+ " a.application_status , a.document_signed , a.document_downloaded , a.ref_code , a.created_date , a.last_updated ,"
								+ " c.applicant_name , b.mothersName, "
								+ " b.fathersName, b.gender, b.marital_status, b.annual_income, b.trading_experience, b.occupation, "
								+ " b.politically_exposed , c.pan_card ,c.dob  FROM  tbl_application_master A "
								+ " left join tbl_account_holder_personal_details B on a.application_id = b.application_id "
								+ " left join tbl_pancard_details c on a.application_id = c.application_id where a.created_date >= ? "
								+ " and a.created_date <= ? and a.remishree_code = ? order by a.created_date desc limit 1000");
				pStmt.setString(paramPos++, pDto.getStartDate() + " 00:00:00");
				pStmt.setString(paramPos++, pDto.getEndDate() + " 23:59:59");
				pStmt.setString(paramPos++, pDto.getRemishreeCode());
			}
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
					result.setApplicant_name(rSet.getString("c.applicant_name"));
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
					result.setReffCode(rSet.getString("a.ref_code"));
					result.setCreatedAt(rSet.getString("a.created_date"));
					result.setLastUpdatedAt(rSet.getString("a.last_updated"));
					if (applicationStatus < eKYCConstant.OTP_VERIFIED) {
						result.setStatus("Registered");
						result.setExactStatus("In Process");
					}
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
	 * Method to get the branch list from the data base
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public List<BranchListDTO> getBranchList(BranchListDTO pDto) {
		List<BranchListDTO> response = new ArrayList<BranchListDTO>();
		BranchListDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			conn = DBUtil.getConnection();
			int paramPos = 1;
			pStmt = conn.prepareStatement(
					"SELECT branch_code, branch_name, user_id, type, sub4, sub3, sub2, sub1, sub, main, master, "
							+ "region, area FROM tbl_branch_list where branch_code like '%" + pDto.getBranchCode()
							+ "%' and active_status =  ?");
			pStmt.setInt(paramPos++, 1);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new BranchListDTO();
					result.setBranchCode(rSet.getString("branch_code"));
					result.setBranchName(rSet.getString("branch_name"));
					result.setUserId(rSet.getString("user_id"));
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
	 * Method to get the remishree list from the data base
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * 
	 * @return
	 */
	public List<BranchListDTO> getRemishreeList(BranchListDTO pDto) {
		List<BranchListDTO> response = new ArrayList<BranchListDTO>();
		BranchListDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			conn = DBUtil.getConnection();
			int paramPos = 1;
			pStmt = conn.prepareStatement(
					"SELECT remeshire_code, branch_code FROM ekyc.tbl_remishree_list where branch_code = ?");
			pStmt.setString(paramPos++, pDto.getBranchCode());
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new BranchListDTO();
					result.setRemeshireCode(rSet.getString("remeshire_code"));
					result.setBranchCode(rSet.getString("branch_code"));
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
	 * Method to update the client code details in the data base *
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public boolean updateClientCodeDetails(BranchListDTO pDto) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		boolean issuccessfull = false;
		int count = 0;
		try {
			java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" UPDATE tbl_application_master SET branch_code = ? , client_code = ? , remishree_code = ? , last_updated = ?  "
							+ "where application_id = ? ");
			int parompos = 1;
			pStmt.setString(parompos++, pDto.getBranchCode());
			pStmt.setString(parompos++, pDto.getClientCode());
			pStmt.setString(parompos++, pDto.getRemeshireCode());
			pStmt.setTimestamp(parompos++, timestamp);
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

	/****
	 * @author VICKY
	 * @param dto
	 * @return
	 */

	public boolean adminaddnewbank(IfscCodeDTO dto) {
		int count = 0;
		PreparedStatement pStmt = null;
		Connection conn = null;
		boolean isSuccessful = false;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" INSERT INTO tbl_ifsccode_details (bank_id, micr_code, ifc_code , bank_name, bank_address_1, bank_address_2,"
							+ " bank_address_3, bank_city, bank_state, bank_country, bank_zip_code, bank_phone_1, bank_phone_2, unique_id, bank_email,"
							+ " bank_contact_name, bank_contact_designation) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			int paramPos = 1;
			pStmt.setInt(paramPos++, dto.getBank_id());
			pStmt.setString(paramPos++, dto.getMicr_code());
			pStmt.setString(paramPos++, dto.getIfc_code());
			pStmt.setString(paramPos++, dto.getBank_name());
			pStmt.setString(paramPos++, dto.getBank_address_1());
			pStmt.setString(paramPos++, dto.getBank_address_2());
			pStmt.setString(paramPos++, dto.getBank_address_3());
			pStmt.setString(paramPos++, dto.getBank_city());
			pStmt.setString(paramPos++, dto.getBank_state());
			pStmt.setString(paramPos++, dto.getBank_country());
			pStmt.setString(paramPos++, dto.getBank_zip_code());
			pStmt.setString(paramPos++, dto.getBank_phone_1());
			pStmt.setString(paramPos++, dto.getBank_phone_2());
			pStmt.setString(paramPos++, dto.getUnique_id());
			pStmt.setString(paramPos++, dto.getBank_email());
			pStmt.setString(paramPos++, dto.getBank_contact_name());
			pStmt.setString(paramPos++, dto.getBank_contact_designation());
			count = pStmt.executeUpdate();
			if (count > 0) {
				isSuccessful = true;
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
		return isSuccessful;
	}

	/***
	 * 
	 * @param pDto
	 * @return
	 */

	public List<IfscCodeDTO> getifsccodefind(IfscCodeDTO pDto) {
		List<IfscCodeDTO> response = new ArrayList<IfscCodeDTO>();
		IfscCodeDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement("SELECT ifc_code FROM ekyc.tbl_ifsccode_details where ifc_code = ? ");
			pStmt.setString(1, pDto.getIfc_code());
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new IfscCodeDTO();
					result.setIfc_code(rSet.getString("ifc_code"));
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

	/***
	 * 
	 * @param dto
	 * @return
	 */

	public boolean emailUpdateadmin(AdminDTO dto) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		boolean issuccessfull = false;
		int count = 0;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" UPDATE tbl_application_master SET email_activated = ? where application_id = ?");
			int parompos = 1;
			pStmt.setLong(parompos++, dto.getEmailactive());
			pStmt.setLong(parompos++, dto.getApplicationId());
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

	/***
	 * 
	 * @param dto
	 * @return
	 */

	public boolean addnewUser(AdminDetailsDTO dto) {
		int count = 0;
		PreparedStatement pStmt = null;
		Connection conn = null;
		boolean isSuccessful = false;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"INSERT INTO tbl_admin_details(name, admin_email, admin_password, designation, branch_code, remishree_code,"
							+ " role, mobile_number)  VALUES(?,?,?,?,?,?,?,?)");
			int paramPos = 1;
			pStmt.setString(paramPos++, dto.getName());
			pStmt.setString(paramPos++, dto.getEmail());
			pStmt.setString(paramPos++, dto.getPassword());
			pStmt.setString(paramPos++, dto.getDesignation());
			pStmt.setString(paramPos++, dto.getBranchCode());
			pStmt.setString(paramPos++, dto.getRemishreeCode());
			pStmt.setString(paramPos++, dto.getRole());
			pStmt.setLong(paramPos++, dto.getMobile_number());

			count = pStmt.executeUpdate();
			if (count > 0) {
				isSuccessful = true;
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
		return isSuccessful;
	}

	/***
	 * 
	 * @param pDto
	 * @return
	 */

	public List<AdminDetailsDTO> geadminEmailfind(AdminDetailsDTO pDto) {
		List<AdminDetailsDTO> response = new ArrayList<AdminDetailsDTO>();
		AdminDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement("select admin_email from ekyc.tbl_admin_details where admin_email = ? ");
			pStmt.setString(1, pDto.getEmail());
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new AdminDetailsDTO();
					result.setEmail(rSet.getString("admin_email"));
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

	/***
	 * 
	 * @param dto
	 * @return
	 */

	public List<AdminDetailsDTO> getadminlist(AdminDetailsDTO dto) {
		List<AdminDetailsDTO> response = null;
		AdminDetailsDTO result = null;
		PreparedStatement pStmt = null;
		Connection conn = null;
		ResultSet rSet = null;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"SELECT name, admin_email, designation, branch_code, remishree_code, role, mobile_number, active_status FROM tbl_admin_details ");
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				response = new ArrayList<AdminDetailsDTO>();
				while (rSet.next()) {
					result = new AdminDetailsDTO();
					result.setName(rSet.getString("name"));
					result.setEmail(rSet.getString("admin_email"));
					result.setDesignation(rSet.getString("designation"));
					result.setBranchCode(rSet.getString("branch_code"));
					result.setRemishreeCode(rSet.getString("remishree_code"));
					result.setRole(rSet.getString("role"));
					result.setMobile_number(rSet.getLong("mobile_number"));
					result.setUserDelete(rSet.getInt("active_status"));
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
	 * @param adminemail
	 * @param isDelete
	 * @return
	 */

	public boolean adminDeleteuser(String adminemail, boolean isDelete) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		boolean issuccessfull = false;
		int count = 0;
		try {
			int paramPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(" UPDATE tbl_admin_details SET active_status = ? where admin_email = ?");
			if (isDelete) {
				pStmt.setInt(paramPos++, 0);
				pStmt.setString(paramPos++, adminemail);
			} else {
				pStmt.setInt(paramPos++, 1);
				pStmt.setString(paramPos++, adminemail);
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

}
