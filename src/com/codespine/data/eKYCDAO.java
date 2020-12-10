package com.codespine.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;

import com.codespine.dto.AccountHolderDetailsDTO;
import com.codespine.dto.AddressDTO;
import com.codespine.dto.ApplicationMasterDTO;
import com.codespine.dto.BankDetailsDTO;
import com.codespine.dto.ExchDetailsDTO;
import com.codespine.dto.FileUploadDTO;
import com.codespine.dto.IfscCodeDTO;
import com.codespine.dto.PanCardDetailsDTO;
import com.codespine.dto.PdfCoordinationsDTO;
import com.codespine.dto.PersonalDetailsDTO;
import com.codespine.dto.PostalCodesDTO;
import com.codespine.dto.esignDTO;
import com.codespine.util.CSEnvVariables;
import com.codespine.util.DBUtil;
import com.codespine.util.DateUtil;
import com.codespine.util.Utility;
import com.codespine.util.eKYCConstant;

@SuppressWarnings("unchecked")
public class eKYCDAO {
	public static eKYCDAO eKYCDAO = null;

	public static eKYCDAO getInstance() {
		if (eKYCDAO == null) {
			eKYCDAO = new eKYCDAO();
		}
		return eKYCDAO;
	}

	/**
	 * Check the profile for given mobile number and take the profile details
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public PersonalDetailsDTO checkExistingUser(PersonalDetailsDTO pDto) {
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" SELECT application_id , mobile_number ,mobile_otp, mobile_no_verified, email_id, email_activated, application_status"
							+ " FROM tbl_application_master where mobile_number = ? and delete_flag = ? ");
			pStmt.setLong(paromPos++, pDto.getMobile_number());
			pStmt.setLong(paromPos++, 0);
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
	 * Method to get the profile details by the Application Id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public PersonalDetailsDTO getProfileDetails(PersonalDetailsDTO pDto) {
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" SELECT application_id , mobile_number ,mobile_otp, mobile_no_verified, email_id, email_activated, application_status"
							+ " FROM tbl_application_master where application_id = ? and delete_flag = ? ");
			pStmt.setLong(paromPos++, pDto.getApplication_id());
			pStmt.setLong(paromPos++, 0);
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
	 * To create the new application with given mobile number and email
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public int createNewApplication(PersonalDetailsDTO pDto) {
		int applicationId = 0;
		PreparedStatement pStmt = null;
		Connection conn = null;
		ResultSet rSet = null;
		try {
			java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"INSERT INTO tbl_application_master(mobile_number, mob_owner, mobile_otp, mobile_no_verified, email_id, email_owner, "
							+ "email_activation_code, email_activated, application_status, last_updated, created_date) "
							+ "VALUES (?,?,?,?,?,?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			int paramPos = 1;
			pStmt.setLong(paramPos++, pDto.getMobile_number());
			pStmt.setString(paramPos++, pDto.getMobile_owner());
			pStmt.setInt(paramPos++, pDto.getOtp());
			pStmt.setInt(paramPos++, 0);
			pStmt.setString(paramPos++, pDto.getEmail());
			pStmt.setString(paramPos++, pDto.getEmail_owner());
			String randomKey = Utility.randomAlphaNumeric();
			String url = CSEnvVariables.getProperty(eKYCConstant.ACTIVATIONLINK);
			String activatingLink = url + "&email=" + pDto.getEmail() + "&key=" + randomKey;
			pStmt.setString(paramPos++, randomKey);
			pStmt.setInt(paramPos++, 0);
			pStmt.setInt(paramPos++, 1);
			pStmt.setTimestamp(paramPos++, timestamp);
			pStmt.setTimestamp(paramPos++, timestamp);
			pStmt.executeUpdate();
			rSet = pStmt.getGeneratedKeys();
			if (rSet != null) {
				while (rSet.next()) {
					applicationId = rSet.getInt(1);
				}
			}
			if (applicationId > 0) {
				Utility.sentEmailVerificationLink(pDto.getEmail(), activatingLink);
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
		return applicationId;
	}

	/**
	 * To verify the email verified
	 * 
	 * @author GOWRI SANKAR R
	 * @param email
	 * @param link
	 * @return
	 */
	public String verifyActivationLink(String email, String link) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		String issuccessfull = eKYCConstant.FAILED_MSG;
		java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" UPDATE tbl_application_master SET email_activated = ? , email_activated_on = ? , last_updated = ? where email_id = ? and email_activation_code = ? ");
			int parompos = 1;
			pStmt.setInt(parompos++, 1);
			pStmt.setTimestamp(parompos++, timestamp);
			pStmt.setTimestamp(parompos++, timestamp);
			pStmt.setString(parompos++, email);
			pStmt.setString(parompos++, link);
			pStmt.executeUpdate();
			issuccessfull = eKYCConstant.SUCCESS_MSG;
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
	 * To update the otp is verified for the given Mobile number
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 */
	public void updateOtpVerified(PersonalDetailsDTO pDto) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" UPDATE tbl_application_master SET mobile_no_verified = ? , otp_verified_on = ? , last_updated = ? where mobile_number = ? ");
			int parompos = 1;
			pStmt.setInt(parompos++, 1);
			pStmt.setTimestamp(parompos++, timestamp);
			pStmt.setTimestamp(parompos++, timestamp);
			pStmt.setLong(parompos++, pDto.getMobile_number());
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
	 * To update the otp for the given application Id
	 * 
	 * @param otp
	 * @param applicationId
	 */
	public void updateOtpForApplicationId(int otp, int applicationId) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" UPDATE tbl_application_master SET mobile_otp = ? , last_updated = ? where application_id = ? ");
			int parompos = 1;
			pStmt.setInt(parompos++, otp);
			pStmt.setTimestamp(parompos++, timestamp);
			pStmt.setInt(parompos++, applicationId);
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
	 * Method to update the random key by using the application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param randomKey
	 * @param applicationId
	 */
	public void updateRandomKeyForApplicationId(String randomKey, int applicationId) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" UPDATE tbl_application_master SET email_activation_code = ? , last_updated = ? where application_id = ? ");
			int parompos = 1;
			pStmt.setString(parompos++, randomKey);
			pStmt.setTimestamp(parompos++, timestamp);
			pStmt.setInt(parompos++, applicationId);
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
	 * To update the otp for the given application Id
	 * 
	 * @param otp
	 * @param applicationId
	 */
	public void updateOtpForMobilenumber(int otp, long mobileNumber) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" UPDATE tbl_application_master SET mobile_otp = ? , last_updated = ? where mobile_number = ? ");
			int parompos = 1;
			pStmt.setInt(parompos++, otp);
			pStmt.setTimestamp(parompos++, timestamp);
			pStmt.setLong(parompos++, mobileNumber);
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
	 * Method to update the random key by using the mobile number
	 * 
	 * @author GOWRI SANKAR R
	 * @param randomKey
	 * @param mobileNumber
	 */
	public void updateRandomForMobilenumber(String randomKey, long mobileNumber) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" UPDATE tbl_application_master SET email_activation_code = ? , last_updated = ? where mobile_number = ? ");
			int parompos = 1;
			pStmt.setString(parompos++, randomKey);
			pStmt.setTimestamp(parompos++, timestamp);
			pStmt.setLong(parompos++, mobileNumber);
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
	 * To save the basic information for the user
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public int insertBasicInformation(PersonalDetailsDTO pDto) {
		int count = 0;
		PreparedStatement pStmt = null;
		Connection conn = null;
		try {
			java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"INSERT INTO tbl_account_holder_personal_details(application_id, applicant_name,mothersName, fathersName, "
							+ "gender, marital_status,annual_income, trading_experience, occupation, politically_exposed, created_on) "
							+ "VALUES (?,?,?,?,?,?,?,?,?,?,?)");
			int paramPos = 1;
			pStmt.setLong(paramPos++, pDto.getApplication_id());
			pStmt.setString(paramPos++, pDto.getApplicant_name());
			pStmt.setString(paramPos++, pDto.getMothersName());
			pStmt.setString(paramPos++, pDto.getFathersName());
			pStmt.setString(paramPos++, pDto.getGender());
			pStmt.setString(paramPos++, pDto.getMarital_status());
			pStmt.setString(paramPos++, pDto.getAnnual_income());
			pStmt.setString(paramPos++, pDto.getTrading_experience());
			pStmt.setString(paramPos++, pDto.getOccupation());
			pStmt.setString(paramPos++, pDto.getPolitically_exposed());
			pStmt.setTimestamp(paramPos++, timestamp);
			count = pStmt.executeUpdate();
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
		return count;
	}

	/**
	 * Method to update the basic information for the user
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public boolean updateBasicInformation(PersonalDetailsDTO pDto) {
		boolean isUpdated = false;
		int count = 0;
		PreparedStatement pStmt = null;
		Connection conn = null;
		try {
			java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement("UPDATE tbl_account_holder_personal_details SET  applicant_name = ? , "
					+ "mothersName = ? , fathersName = ? , gender = ? ,marital_status = ?, annual_income = ? , "
					+ "trading_experience = ? , occupation= ? , politically_exposed = ? , last_updated = ? where application_id = ? ");
			int paramPos = 1;
			pStmt.setString(paramPos++, pDto.getApplicant_name());
			pStmt.setString(paramPos++, pDto.getMothersName());
			pStmt.setString(paramPos++, pDto.getFathersName());
			pStmt.setString(paramPos++, pDto.getGender());
			pStmt.setString(paramPos++, pDto.getMarital_status());
			pStmt.setString(paramPos++, pDto.getAnnual_income());
			pStmt.setString(paramPos++, pDto.getTrading_experience());
			pStmt.setString(paramPos++, pDto.getOccupation());
			pStmt.setString(paramPos++, pDto.getPolitically_exposed());
			pStmt.setTimestamp(paramPos++, timestamp);
			pStmt.setLong(paramPos++, pDto.getApplication_id());
			count = pStmt.executeUpdate();
			if (count > 0) {
				isUpdated = true;
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
		return isUpdated;
	}

	/**
	 * To update the Communication address for the given Application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public static int insertCommunicationAddress(AddressDTO pDto) {
		int count = 0;
		PreparedStatement pStmt = null;
		Connection conn = null;
		try {
			java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"INSERT INTO tbl_communication_address(application_id, flat_no, street, pin, city, district, state, created_on) VALUES (?,?,?,?,?,?,?,?)");
			int paramPos = 1;
			pStmt.setLong(paramPos++, pDto.getApplication_id());
			pStmt.setString(paramPos++, pDto.getFlat_no());
			pStmt.setString(paramPos++, pDto.getStreet());
			pStmt.setInt(paramPos++, pDto.getPin());
			pStmt.setString(paramPos++, pDto.getCity());
			pStmt.setString(paramPos++, pDto.getDistrict());
			pStmt.setString(paramPos++, pDto.getState());
			pStmt.setTimestamp(paramPos++, timestamp);
			count = pStmt.executeUpdate();
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
		return count;
	}

	/**
	 * To update the Communication address for the given Application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public boolean udpateCommunicationAddress(AddressDTO pDto) {
		int count = 0;
		boolean isUpdated = false;
		PreparedStatement pStmt = null;
		Connection conn = null;
		try {
			java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"UPDATE tbl_communication_address SET  flat_no = ? , street = ? , pin = ? , city = ? , district = ? , state = ? , last_updated = ? where application_id = ? ");
			int paramPos = 1;
			pStmt.setString(paramPos++, pDto.getFlat_no());
			pStmt.setString(paramPos++, pDto.getStreet());
			pStmt.setInt(paramPos++, pDto.getPin());
			pStmt.setString(paramPos++, pDto.getCity());
			pStmt.setString(paramPos++, pDto.getDistrict());
			pStmt.setString(paramPos++, pDto.getState());
			pStmt.setTimestamp(paramPos++, timestamp);
			pStmt.setLong(paramPos++, pDto.getApplication_id());
			count = pStmt.executeUpdate();
			if (count > 0) {
				isUpdated = true;
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
		return isUpdated;
	}

	/**
	 * To update the permanent address for the given Application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public static int insertPermanentAddress(AddressDTO pDto) {
		int count = 0;
		PreparedStatement pStmt = null;
		Connection conn = null;
		try {
			java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"INSERT INTO tbl_permanent_address(application_id, flat_no, street, pin, city, district, state, created_on) VALUES (?,?,?,?,?,?,?,?) ");
			int paramPos = 1;
			pStmt.setLong(paramPos++, pDto.getApplication_id());
			pStmt.setString(paramPos++, pDto.getFlat_no());
			pStmt.setString(paramPos++, pDto.getStreet());
			pStmt.setInt(paramPos++, pDto.getPin());
			pStmt.setString(paramPos++, pDto.getCity());
			pStmt.setString(paramPos++, pDto.getDistrict());
			pStmt.setString(paramPos++, pDto.getState());
			pStmt.setTimestamp(paramPos++, timestamp);
			count = pStmt.executeUpdate();
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
		return count;
	}

	/**
	 * Method to update the permananent address for the given application Id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public boolean updatePermanentAddress(AddressDTO pDto) {
		int count = 0;
		boolean isUpdated = false;
		PreparedStatement pStmt = null;
		Connection conn = null;
		try {
			java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"UPDATE tbl_permanent_address SET  flat_no = ? , street = ? , pin = ? , city = ? , district = ? , state = ? , last_updated = ? where application_id = ? ");
			int paramPos = 1;
			pStmt.setString(paramPos++, pDto.getFlat_no());
			pStmt.setString(paramPos++, pDto.getStreet());
			pStmt.setInt(paramPos++, pDto.getPin());
			pStmt.setString(paramPos++, pDto.getCity());
			pStmt.setString(paramPos++, pDto.getDistrict());
			pStmt.setString(paramPos++, pDto.getState());
			pStmt.setTimestamp(paramPos++, timestamp);
			pStmt.setLong(paramPos++, pDto.getApplication_id());
			count = pStmt.executeUpdate();
			if (count > 0) {
				isUpdated = true;
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
		return isUpdated;
	}

	/**
	 * To check the basic information for the application is already inserted or
	 * not
	 * 
	 * @author GOWRI SANKAR R
	 * @param applicationId
	 * @return
	 */
	public PersonalDetailsDTO CheckBasicInformation(int applicationId) {
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		HashMap<String, String> json = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" SELECT id,application_id,applicant_name, mothersName, fathersName, gender, marital_status, annual_income, "
							+ "trading_experience, occupation, politically_exposed "
							+ " FROM tbl_account_holder_personal_details where application_id = ? ");
			pStmt.setLong(paromPos++, applicationId);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new PersonalDetailsDTO();
					json = new HashMap<String, String>();
					result.setId(rSet.getInt("id"));
					result.setApplication_id(rSet.getInt("application_id"));
					result.setApplicant_name(rSet.getString("applicant_name"));
					json.put("applicant_name", rSet.getString("applicant_name").toUpperCase());
					result.setMothersName(rSet.getString("mothersName"));
					json.put("mothersName", rSet.getString("mothersName").toUpperCase());
					result.setFathersName(rSet.getString("fathersName"));
					json.put("fathersName", rSet.getString("fathersName").toUpperCase());
					result.setGender(rSet.getString("gender"));
					json.put("gender", rSet.getString("gender"));
					result.setMarital_status(rSet.getString("marital_status"));
					json.put("marital_status", rSet.getString("marital_status"));
					result.setAnnual_income(rSet.getString("annual_income"));
					json.put("annual_income", rSet.getString("annual_income"));
					result.setTrading_experience(rSet.getString("trading_experience"));
					json.put("trading_experience", rSet.getString("trading_experience"));
					result.setOccupation(rSet.getString("occupation"));
					json.put("occupation", rSet.getString("occupation"));
					result.setPolitically_exposed(rSet.getString("politically_exposed"));
					json.put("politically_exposed", rSet.getString("politically_exposed"));
					result.setForPDFKeyValue(json);
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
	 * Check the communication address is aldready updated
	 * 
	 * @author GOWRI SANKAR R
	 * @param application_id
	 * @return
	 */
	public AddressDTO checkCommunicationAddress(int application_id) {
		AddressDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		HashMap<String, String> json = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" SELECT application_id,flat_no,street,pin,city,district,state  FROM tbl_communication_address where application_id = ? ");
			pStmt.setLong(paromPos++, application_id);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new AddressDTO();
					json = new HashMap<String, String>();
					result.setApplication_id(rSet.getInt("application_id"));
					result.setFlat_no(rSet.getString("flat_no"));
					json.put("flat_no", rSet.getString("flat_no").toUpperCase());
					result.setStreet(rSet.getString("street"));
					json.put("street", rSet.getString("street").toUpperCase());
					json.put("flatNo_street_city", json.put("flat_no", rSet.getString("flat_no").toUpperCase()) + ", "
							+ rSet.getString("street").toUpperCase() + ", " + rSet.getString("city").toUpperCase());
					result.setPin(rSet.getInt("pin"));
					json.put("pin", Integer.toString(rSet.getInt("pin")));
					result.setCity(rSet.getString("city"));
					json.put("city", rSet.getString("city").toUpperCase());
					json.put("reduced_city", rSet.getString("city").toUpperCase());
					json.put("country", "INDIA");
					result.setDistrict(rSet.getString("district"));
					json.put("district", rSet.getString("district").toUpperCase());
					result.setState(rSet.getString("state"));
					json.put("state", rSet.getString("state").toUpperCase());
					json.put("district_state_country",
							rSet.getString("district").toUpperCase() + ", " + rSet.getString("state").toUpperCase()
									+ ", " + json.put("country", "INDIA") + ", "
									+ json.put("pin", Integer.toString(rSet.getInt("pin"))));
					result.setForPDFKeyValue(json);
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
	 * Check premanaent address
	 * 
	 * @author GOWRI SANKAR R
	 * @param application_id
	 * @return
	 */
	public AddressDTO checkPermanentAddress(int application_id) {
		AddressDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		HashMap<String, String> json = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" SELECT application_id,flat_no,street,pin,city,district,state  FROM tbl_permanent_address where application_id = ? ");
			pStmt.setLong(paromPos++, application_id);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new AddressDTO();
					json = new HashMap<String, String>();
					result.setApplication_id(rSet.getInt("application_id"));
					json.put("application_id", Integer.toString(rSet.getInt("application_id")));
					result.setFlat_no(rSet.getString("flat_no"));
					json.put("permanent_flat_no", rSet.getString("flat_no").toUpperCase());
					result.setStreet(rSet.getString("street"));
					json.put("permanent_street", rSet.getString("street").toUpperCase());
					result.setPin(rSet.getInt("pin"));
					json.put("permanent_pin", Integer.toString(rSet.getInt("pin")));
					result.setCity(rSet.getString("city"));
					json.put("permanent_city", rSet.getString("city").toUpperCase());
					result.setDistrict(rSet.getString("district"));
					json.put("permanent_district", rSet.getString("district").toUpperCase());
					result.setState(rSet.getString("state"));
					json.put("permanent_state", rSet.getString("state").toUpperCase());
					result.setForPDFKeyValue(json);
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
	 * Update the Communication address for the application ID
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public void updateApplicationStatus(int applicationId, int applicationStatus) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" UPDATE tbl_application_master SET application_status = ? , last_updated = ? where application_id = ? ");
			int paramPos = 1;
			pStmt.setInt(paramPos++, applicationStatus);
			pStmt.setTimestamp(paramPos++, timestamp);
			pStmt.setInt(paramPos++, applicationId);
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
	 * To insert the bank Details for user by using application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public int insertBankAccountDetails(BankDetailsDTO pDto) {
		int count = 0;
		PreparedStatement pStmt = null;
		Connection conn = null;
		try {
			java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"INSERT INTO tbl_bank_account_details(application_id, bank_name , micr_code ,account_type , bank_address , account_holder_name, ifsc_code, bank_account_no, "
							+ " created_on) VALUES (?,?,?,?,?,?,?,?,?) ");
			int paramPos = 1;
			pStmt.setLong(paramPos++, pDto.getApplication_id());
			pStmt.setString(paramPos++, pDto.getBankName());
			pStmt.setString(paramPos++, pDto.getMicrCode());
			pStmt.setString(paramPos++, pDto.getAccount_type());
			pStmt.setString(paramPos++, pDto.getBankAddress());
			pStmt.setString(paramPos++, pDto.getAccount_holder_name());
			pStmt.setString(paramPos++, pDto.getIfsc_code());
			pStmt.setString(paramPos++, pDto.getBank_account_no());
			pStmt.setTimestamp(paramPos++, timestamp);
			count = pStmt.executeUpdate();
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
		return count;
	}

	/**
	 * Method to update the bank Account details
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public boolean updateBankAccountDetails(BankDetailsDTO pDto) {
		boolean isUpdated = false;
		int count = 0;
		PreparedStatement pStmt = null;
		Connection conn = null;
		try {
			java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"UPDATE tbl_bank_account_details SET bank_name = ? , micr_code = ?, account_type = ? , bank_address = ? , account_holder_name = ? , ifsc_code = ? , "
							+ "bank_account_no = ?, last_updated = ? where application_id = ?");
			int paramPos = 1;
			pStmt.setString(paramPos++, pDto.getBankName());
			pStmt.setString(paramPos++, pDto.getMicrCode());
			pStmt.setString(paramPos++, pDto.getAccount_type());
			pStmt.setString(paramPos++, pDto.getBankAddress());
			pStmt.setString(paramPos++, pDto.getAccount_holder_name());
			pStmt.setString(paramPos++, pDto.getIfsc_code());
			pStmt.setString(paramPos++, pDto.getBank_account_no());
			pStmt.setTimestamp(paramPos++, timestamp);
			pStmt.setLong(paramPos++, pDto.getApplication_id());
			count = pStmt.executeUpdate();
			if (count > 0) {
				isUpdated = true;
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
		return isUpdated;
	}

	/**
	 * Check the bank details using Application Id
	 * 
	 * @author GOWRI SANKAR R
	 * @param application_id
	 * @return
	 */
	public BankDetailsDTO checkBankDetailsUpdated(int application_id) {
		BankDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		HashMap<String, String> json = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" SELECT id,application_id,bank_name,micr_code, bank_address, account_type,account_holder_name,ifsc_code,"
							+ "bank_account_no,account_type FROM tbl_bank_account_details where application_id = ? ");
			pStmt.setLong(paromPos++, application_id);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new BankDetailsDTO();
					json = new HashMap<String, String>();
					result.setId(rSet.getInt("id"));
					result.setApplication_id(rSet.getInt("application_id"));
					result.setBankName(rSet.getString("bank_name"));
					json.put("bank_name", rSet.getString("bank_name"));
					result.setMicrCode(rSet.getString("micr_code"));
					result.setBankAddress(rSet.getString("bank_address"));
					json.put("bank_address", rSet.getString("bank_address"));
					json.put("micr_code", rSet.getString("micr_code"));
					result.setAccount_holder_name(rSet.getString("account_holder_name"));
					json.put("account_holder_name", rSet.getString("account_holder_name"));
					result.setIfsc_code(rSet.getString("ifsc_code").toUpperCase());
					json.put("ifsc_code", rSet.getString("ifsc_code"));
					result.setBank_account_no((rSet.getString("bank_account_no")));
					json.put("bank_account_no", rSet.getString("bank_account_no"));
					json.put("half_date",
							DateUtil.formatDate(DateUtil.getNewDateWithCurrentTime(), DateUtil.DDMMYY_SLASH));
					json.put("date",
							DateUtil.formatDate(DateUtil.getNewDateWithCurrentTime(), DateUtil.DDMMYYYY_SLASH));
					json.put("reverse_full_date",
							DateUtil.formatDate(DateUtil.getNewDateWithCurrentTime(), DateUtil.YYYYMMDD_SLASH));
					json.put("last_financial_date", DateUtil.getLastFinancialYearTo(DateUtil.getTodayDate()));
					result.setAccount_type(rSet.getString("account_type"));
					json.put("account_type", rSet.getString("account_type"));
					// result.setVerified_on(rSet.getString("verified_on"));
					// result.setVerified(rSet.getInt("verified"));
					// result.setVerification_count(rSet.getInt("verification_count"));
					result.setForPDFKeyValue(json);
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
	 * To insert the pan card details
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public int insertPanCardDetails(PanCardDetailsDTO pDto) {
		int count = 0;
		PreparedStatement pStmt = null;
		Connection conn = null;
		try {
			java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"INSERT INTO tbl_pancard_details(application_id, pan_card, aadhar_no , applicant_name , first_name , middle_name , last_name , dob , "
							+ " created_on) VALUES (?,?,?,?,?,?,?,?,?) ");
			int paramPos = 1;
			pStmt.setInt(paramPos++, pDto.getApplication_id());
			pStmt.setString(paramPos++, pDto.getPan_card().toUpperCase());
			pStmt.setLong(paramPos++, pDto.getAadharNo());
			pStmt.setString(paramPos++, pDto.getApplicant_name().toUpperCase());
			pStmt.setString(paramPos++, pDto.getFirst_name().toUpperCase());
			pStmt.setString(paramPos++, pDto.getMiddle_name().toUpperCase());
			pStmt.setString(paramPos++, pDto.getLast_name().toUpperCase());
			pStmt.setString(paramPos++, pDto.getDob());
			pStmt.setTimestamp(paramPos++, timestamp);
			count = pStmt.executeUpdate();
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
		return count;
	}

	/**
	 * Method to update the pan card Details into data base
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public boolean updatePanCardDetails(PanCardDetailsDTO pDto) {
		boolean isUpdated = false;
		int count = 0;
		PreparedStatement pStmt = null;
		Connection conn = null;
		try {
			java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"UPDATE tbl_pancard_details SET  pan_card = ? , aadhar_no = ? ,  applicant_name = ? , first_name = ? , middle_name = ? , last_name = ? , "
							+ "dob = ? , last_updated = ? where application_id = ?");
			int paramPos = 1;
			pStmt.setString(paramPos++, pDto.getPan_card());
			pStmt.setLong(paramPos++, pDto.getAadharNo());
			pStmt.setString(paramPos++, pDto.getApplicant_name());
			pStmt.setString(paramPos++, pDto.getFirst_name().toUpperCase());
			pStmt.setString(paramPos++, pDto.getMiddle_name().toUpperCase());
			pStmt.setString(paramPos++, pDto.getLast_name().toUpperCase());
			pStmt.setString(paramPos++, pDto.getDob());
			pStmt.setTimestamp(paramPos++, timestamp);
			pStmt.setInt(paramPos++, pDto.getApplication_id());
			count = pStmt.executeUpdate();
			if (count > 0) {
				isUpdated = true;
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
		return isUpdated;
	}

	/**
	 * Method to check the pan card details updated or not
	 * 
	 * @author GOWRI SANKAR R
	 * @param application_id
	 * @return
	 */
	public PanCardDetailsDTO checkPanCardUpdated(int application_id) {
		PanCardDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		HashMap<String, String> json = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" SELECT application_id,pan_card,aadhar_no,dob,mothersName,fathersName,pan_card_verified,nsdl_name,nsdl_dob  FROM tbl_pancard_details where application_id = ? ");
			pStmt.setLong(paromPos++, application_id);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new PanCardDetailsDTO();
					json = new HashMap<String, String>();
					result.setApplication_id(rSet.getInt("application_id"));
					result.setPan_card(rSet.getString("pan_card"));
					result.setAadharNo(rSet.getLong("aadhar_no"));
					json.put("pan_card", rSet.getString("pan_card").toUpperCase());
					json.put("aadhar_no", String.valueOf(rSet.getLong("aadhar_no")));
					result.setDob(rSet.getString("dob"));
					json.put("dob", DateUtil.parseDateStringForDOB(rSet.getString("dob")));
					result.setMothersName(rSet.getString("mothersName"));
					result.setFathersName(rSet.getString("fathersName"));
					result.setPan_card_verified(rSet.getInt("pan_card_verified"));
					result.setNsdl_name(rSet.getString("nsdl_name"));
					json.put("nsdl_name", rSet.getString("nsdl_name"));
					result.setNsdl_dob(rSet.getString("nsdl_dob"));
					json.put("nsdl_dob", rSet.getString("nsdl_dob"));
					result.setForPDFKeyValue(json);
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
	 * To update the email id for the given mobilemnumber
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public boolean updateEmail(PersonalDetailsDTO pDto) {
		int count = 0;
		boolean isSuccessFull = false;
		Connection conn = null;
		PreparedStatement pStmt = null;
		java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" UPDATE tbl_application_master SET email_id = ? , last_updated = ? where mobile_number = ? ");
			int paramPos = 1;
			pStmt.setString(paramPos++, pDto.getEmail());
			pStmt.setTimestamp(paramPos++, timestamp);
			pStmt.setLong(paramPos++, pDto.getMobile_number());
			count = pStmt.executeUpdate();
			if (count > 0) {
				isSuccessFull = true;
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
		return isSuccessFull;
	}

	/**
	 * Marrk the apllication id as the deleted
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public boolean deleteOldOne(PersonalDetailsDTO pDto) {
		int count = 0;
		boolean isSuccessFull = false;
		Connection conn = null;
		PreparedStatement pStmt = null;
		java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" UPDATE tbl_application_master SET delete_flag = ? , last_updated = ? where mobile_number = ? ");
			int paramPos = 1;
			pStmt.setInt(paramPos++, 1);
			pStmt.setTimestamp(paramPos++, timestamp);
			pStmt.setLong(paramPos++, pDto.getMobile_number());
			count = pStmt.executeUpdate();
			if (count > 0) {
				isSuccessFull = true;
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
		return isSuccessFull;
	}

	/**
	 * To get the application name form the data base
	 * 
	 * @author GOWRI SANKAR R
	 * @param application_id
	 * @return
	 */
	public PanCardDetailsDTO getApplicantName(int application_id) {
		PanCardDetailsDTO result = new PanCardDetailsDTO();
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" SELECT applicant_name , fathersName  FROM tbl_pancard_details where application_id = ? ");
			pStmt.setLong(paromPos++, application_id);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result.setApplicant_name(rSet.getString("applicant_name"));
					result.setFathersName(rSet.getString("fathersName"));
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
	 * Method to check the exch segments for application id is updated or not
	 * 
	 * @author GOWRI SANKAR R
	 * @param application_id
	 * @return
	 */
	public ExchDetailsDTO checkExchUpdatedStatus(int application_id) {
		ExchDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		HashMap<String, String> json = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" SELECT id, application_id, nse_eq, bse_eq, mf, nse_fo, bse_fo, cds, bcd, mcx, icex, nse_com, bse_com  "
							+ "FROM tbl_exch_segments where application_id = ? ");
			pStmt.setLong(paromPos++, application_id);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new ExchDetailsDTO();
					json = new HashMap<String, String>();
					result.setApplication_id(rSet.getInt("application_id"));
					result.setNse_eq(rSet.getInt("nse_eq"));
					if (rSet.getInt("nse_eq") != 0 && rSet.getInt("nse_eq") > 0) {
						json.put("nse_eq", "nse_eq");
					}
					result.setBse_eq(rSet.getInt("bse_eq"));
					if (rSet.getInt("bse_eq") != 0 && rSet.getInt("bse_eq") > 0) {
						json.put("bse_eq", "bse_eq");
					}
					result.setMf(rSet.getInt("mf"));
					if(rSet.getInt("mf") != 0 && rSet.getInt("mf") > 0) {
						json.put("nse_mf", "nse_mf");
						json.put("bse_mf", "bse_mf");
					}
					result.setNse_fo(rSet.getInt("nse_fo"));
					if (rSet.getInt("nse_fo") != 0 && rSet.getInt("nse_fo") > 0) {
						json.put("nse_fo", "nse_fo");
					}
					result.setBse_fo(rSet.getInt("bse_fo"));
					if (rSet.getInt("bse_fo") != 0 && rSet.getInt("bse_fo") > 0) {
						json.put("bse_fo", "bse_fo");
					}
					result.setCds(rSet.getInt("cds"));
					if (rSet.getInt("cds") != 0 && rSet.getInt("cds") > 0) {
						json.put("cds", "cds");
					}
					result.setBcd(rSet.getInt("bcd"));
					if (rSet.getInt("bcd") != 0 && rSet.getInt("bcd") > 0) {
						json.put("bcd", "bcd");
					}
					result.setMcx(rSet.getInt("mcx"));
					if (rSet.getInt("mcx") != 0 && rSet.getInt("mcx") > 0) {
						json.put("mcx", "mcx");
					}
					result.setIcex(rSet.getInt("icex"));
					if (rSet.getInt("icex") != 0 && rSet.getInt("icex") > 0) {
						json.put("icex", "icex");
					}
					result.setNse_com(rSet.getInt("nse_com"));
					if (rSet.getInt("nse_com") != 0 && rSet.getInt("nse_com") > 0) {
						json.put("nse_com", "nse_com");
					}
					result.setBse_com(rSet.getInt("bse_com"));
					if (rSet.getInt("bse_com") != 0 && rSet.getInt("bse_com") > 0) {
						json.put("bse_com", "bse_com");
					}
					result.setForPDFKeyValue(json);
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
	 * To insert the exch details for the given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public int insertExchDetails(ExchDetailsDTO pDto) {
		int count = 0;
		PreparedStatement pStmt = null;
		Connection conn = null;
		try {
			java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"INSERT INTO tbl_exch_segments(application_id, nse_eq, bse_eq, mf, nse_fo, bse_fo, cds, "
							+ "bcd, mcx, icex, nse_com, bse_com, last_updated, created_on) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			int paramPos = 1;
			pStmt.setInt(paramPos++, pDto.getApplication_id());
			pStmt.setInt(paramPos++, pDto.getNse_eq());
			pStmt.setInt(paramPos++, pDto.getBse_eq());
			pStmt.setInt(paramPos++, pDto.getMf());
			pStmt.setInt(paramPos++, pDto.getNse_fo());
			pStmt.setInt(paramPos++, pDto.getBse_fo());
			pStmt.setInt(paramPos++, pDto.getCds());
			pStmt.setInt(paramPos++, pDto.getBcd());
			pStmt.setInt(paramPos++, pDto.getMcx());
			pStmt.setInt(paramPos++, pDto.getIcex());
			pStmt.setInt(paramPos++, pDto.getNse_com());
			pStmt.setInt(paramPos++, pDto.getBse_com());
			pStmt.setTimestamp(paramPos++, timestamp);
			pStmt.setTimestamp(paramPos++, timestamp);
			count = pStmt.executeUpdate();
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
		return count;
	}

	/**
	 * To update the exch details for the given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public boolean updateExchDetails(ExchDetailsDTO pDto) {
		int count = 0;
		boolean isSuccessFull = false;
		Connection conn = null;
		PreparedStatement pStmt = null;
		java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" UPDATE tbl_exch_segments SET nse_eq = ? , bse_eq = ? , mf = ? , nse_fo = ? , bse_fo = ? , cds = ? , bcd = ? , mcx = ? , "
							+ "icex = ? , nse_com = ? , bse_com = ? , last_updated = ? where application_id = ? ");
			int paramPos = 1;
			pStmt.setInt(paramPos++, pDto.getNse_eq());
			pStmt.setInt(paramPos++, pDto.getBse_eq());
			pStmt.setInt(paramPos++, pDto.getMf());
			pStmt.setInt(paramPos++, pDto.getNse_fo());
			pStmt.setInt(paramPos++, pDto.getBse_fo());
			pStmt.setInt(paramPos++, pDto.getCds());
			pStmt.setInt(paramPos++, pDto.getBcd());
			pStmt.setInt(paramPos++, pDto.getMcx());
			pStmt.setInt(paramPos++, pDto.getIcex());
			pStmt.setInt(paramPos++, pDto.getNse_com());
			pStmt.setInt(paramPos++, pDto.getBse_com());
			pStmt.setTimestamp(paramPos++, timestamp);
			pStmt.setLong(paramPos++, pDto.getApplication_id());
			count = pStmt.executeUpdate();
			if (count > 0) {
				isSuccessFull = true;
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
		return isSuccessFull;
	}

	/**
	 * To delete the attachements for given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param applicationId
	 * @return
	 */
	public void deleteProof(int applicationId) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement("delete from tbl_application_attachements where application_id = ?");
			int paramPos = 1;
			pStmt.setInt(paramPos++, applicationId);
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
	 * To insert the attachement details for the given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param proofUrl
	 * @param proofType
	 * @param applicationId
	 * @return
	 */
	public int insertAttachementDetails(String proofUrl, String proofType, int applicationId, String typeOfProof) {
		int count = 0;
		PreparedStatement pStmt = null;
		Connection conn = null;
		try {
			java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"INSERT INTO tbl_application_attachements(application_id, attachement_type, attachement_url, type_of_proof ,last_update, created_on) "
							+ "VALUES (?,?,?,?,?,?)");
			int paramPos = 1;
			pStmt.setInt(paramPos++, applicationId);
			pStmt.setString(paramPos++, proofType);
			pStmt.setString(paramPos++, proofUrl);
			pStmt.setString(paramPos++, typeOfProof);
			pStmt.setTimestamp(paramPos++, timestamp);
			pStmt.setTimestamp(paramPos++, timestamp);
			count = pStmt.executeUpdate();
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
		return count;
	}

	/**
	 * Method to get Application Detail For PDF Print
	 * 
	 * @author Pradeep R
	 * @param ApplicationMasterDTO
	 * @return
	 */
	public ApplicationMasterDTO getApplicationMasterDetails(ApplicationMasterDTO pDto) {
		ApplicationMasterDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		HashMap<String, String> json = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn
					.prepareStatement(" SELECT application_id,mobile_number,mobile_no_verified,mob_owner,mobile_otp,"
							+ "email_id,email_owner,email_activation_code,email_activated,otp_verified_on,"
							+ "email_activated_on,application_status,last_updated,created_date"
							+ " FROM tbl_application_master where application_id = ?  ");
			pStmt.setInt(paromPos++, pDto.getApplication_id());
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					json = new HashMap<String, String>();
					result = new ApplicationMasterDTO();
					result.setApplication_id(rSet.getInt("application_id"));
					json.put("application_id", Integer.toString(rSet.getInt("application_id")));
					result.setMobile_number(rSet.getString("mobile_number"));
					json.put("mobile_number", rSet.getString("mobile_number"));
					result.setMobile_no_verified(rSet.getInt("mobile_no_verified"));
					result.setMob_owner(rSet.getString("mob_owner"));
					json.put("mob_owner", rSet.getString("mob_owner").toUpperCase());
					result.setMobile_otp(rSet.getString("mobile_otp"));
					result.setEmail_id(rSet.getString("email_id"));
					json.put("email_id", rSet.getString("email_id").toUpperCase());
					result.setEmail_owner(rSet.getString("email_owner"));
					json.put("email_owner", rSet.getString("email_owner").toUpperCase());
					result.setEmail_activation_code(rSet.getString("email_activation_code"));
					result.setEmail_activated(rSet.getInt("email_activated"));
					result.setOtp_verified_on(rSet.getDate("otp_verified_on"));
					result.setEmail_activated_on(rSet.getDate("email_activated_on"));
					result.setApplication_status(rSet.getInt("application_status"));
					result.setLast_updated(rSet.getDate("last_updated"));
					result.setCreated_date(rSet.getDate("created_date"));
					result.setForPDFKeyValue(json);

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

	public AccountHolderDetailsDTO getAccountHolderDetail(int applicationId) {
		AccountHolderDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" SELECT  id,name,mobile_number,email,otp,email_verified,verified,verification_key,verified_on,created_on,application_id"
							+ " FROM tbl_account_holder_details where application_id = ? ");
			pStmt.setInt(paromPos++, applicationId);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new AccountHolderDetailsDTO();
					result.setId(rSet.getInt("id"));
					result.setMobile_number(rSet.getInt("mobile_number"));
					result.setEmail(rSet.getString("email"));
					result.setOtp(rSet.getInt("otp"));
					result.setEmail_verified(rSet.getInt("email_verified"));
					result.setVerified(rSet.getInt("verified"));
					result.setVerification_key(rSet.getString("verification_key"));
					result.setVerified_on(rSet.getDate("verified_on"));
					result.setCreated_on(rSet.getDate("created_on"));
					result.setApplication_id(rSet.getInt("application_id"));
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

	public List<PdfCoordinationsDTO> getPdfCoordinations() {
		List<PdfCoordinationsDTO> pdfCoordinationsDTOs = null;
		PdfCoordinationsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" SELECT column_name,coordinates,page_number,is_default,is_value_reduced FROM tbl_pdf_orgins order by page_number asc ");
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new PdfCoordinationsDTO();
					result.setColumn_name(rSet.getString("column_name"));
					result.setCoordinates(rSet.getString("coordinates"));
					result.setPage_number(rSet.getInt("page_number"));
					result.setIs_default(rSet.getInt("is_default"));
					result.setIs_value_reduced(rSet.getInt("is_value_reduced"));
					if (pdfCoordinationsDTOs == null) {
						pdfCoordinationsDTOs = new ArrayList<PdfCoordinationsDTO>();
					}
					pdfCoordinationsDTOs.add(result);
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
		return pdfCoordinationsDTOs;
	}

	public List<String> getPdfTotalColumns() {
		List<String> pdfColumns = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(" SELECT column_name FROM tbl_pdf_coordinations ");
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					pdfColumns.add(rSet.getString("column_name"));
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
		return pdfColumns;
	}

	public String getFileLocation(String key) {
		String fileLocation = "";
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(" SELECT Value FROM tbl_keyvaluepair where MasterkeyDesc = ? ");
			pStmt.setString(paromPos++, key);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					fileLocation = rSet.getString("Value");
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
		return fileLocation;
	}

	public String getDocumentLink(int application_id, String attachementType) {
		String fileLocation = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" SELECT attachement_url FROM tbl_application_attachements where application_id = ? and attachement_type = ? order by last_update desc ");
			pStmt.setInt(paromPos++, application_id);
			pStmt.setString(paromPos++, attachementType);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					fileLocation = rSet.getString("attachement_url");
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
		return fileLocation;
	}

	public List<FileUploadDTO> getUploadedFile(int application_id) {
		List<FileUploadDTO> response = new ArrayList<FileUploadDTO>();
		FileUploadDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" SELECT attachement_type , attachement_url , type_of_proof FROM tbl_application_attachements where application_id = ? ");
			pStmt.setInt(paromPos++, application_id);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new FileUploadDTO();
					result.setProofType(rSet.getString("attachement_type"));
					result.setProof(rSet.getString("attachement_url"));
					result.setTypeOfProof(rSet.getString("type_of_proof"));
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

	public int checkFileUploaded(int applicationId, String proofType) {
		int dummyId = 0;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" SELECT id FROM tbl_application_attachements where application_id = ? and attachement_type = ? ");
			pStmt.setInt(paromPos++, applicationId);
			pStmt.setString(paromPos++, proofType);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					dummyId = rSet.getInt("id");
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
		return dummyId;
	}

	public boolean updateAttachementDetails(String proofUrl, String proofType, int applicationId, String typeOfProof) {
		int count = 0;
		boolean isSuccessFull = false;
		Connection conn = null;
		PreparedStatement pStmt = null;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" UPDATE tbl_application_attachements SET attachement_url = ? , type_of_proof = ?   where application_id = ? and attachement_type = ? ");
			int paramPos = 1;
			pStmt.setString(paramPos++, proofUrl);
			pStmt.setString(paramPos++, typeOfProof);
			pStmt.setInt(paramPos++, applicationId);
			pStmt.setString(paramPos++, proofType);
			count = pStmt.executeUpdate();
			if (count > 0) {
				isSuccessFull = true;
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
		return isSuccessFull;
	}

	public int insertIvrDetails(int applicationId, String proofUrl, String ivrLat, String ivrLong, String deviceIP,
			String userAgent) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pStmt = null;
		java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"INSERT INTO tbl_ivr_capture(application_id , ivr_image , ivr_lat ,ivr_long , user_agent , device_ip , created_on ) VALUES (?,?,? ,?, ?, ? , ?) "
							+ "ON DUPLICATE KEY UPDATE  application_id = ?, ivr_image = ? , ivr_lat = ? , ivr_long = ? , user_agent = ? , device_ip = ? , last_updated = ?");
			int paramPos = 1;
			pStmt.setInt(paramPos++, applicationId);
			pStmt.setString(paramPos++, proofUrl);
			pStmt.setString(paramPos++, ivrLat);
			pStmt.setString(paramPos++, ivrLong);
			pStmt.setString(paramPos++, userAgent);
			pStmt.setString(paramPos++, deviceIP);
			pStmt.setTimestamp(paramPos++, timestamp);
			pStmt.setInt(paramPos++, applicationId);
			pStmt.setString(paramPos++, proofUrl);
			pStmt.setString(paramPos++, ivrLat);
			pStmt.setString(paramPos++, ivrLong);
			pStmt.setString(paramPos++, userAgent);
			pStmt.setString(paramPos++, deviceIP);
			pStmt.setTimestamp(paramPos++, timestamp);
			count = pStmt.executeUpdate();
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
		return count;
	}

	/**
	 * Method to update the IVR details into data base
	 * 
	 * @author GOWRI SANKAR R
	 * @param applicationId
	 * @param proofUrl
	 * @param ivrLat
	 * @param ivrLong
	 * @param deviceIP
	 * @param userAgent
	 * @return
	 */
	public boolean updateIvrDetails(int applicationId, String proofUrl, String ivrLat, String ivrLong, String deviceIP,
			String userAgent) {
		boolean isUpdated = false;
		int count = 0;
		Connection conn = null;
		PreparedStatement pStmt = null;
		java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"UPDATE tbl_ivr_capture SET  ivr_image = ? , ivr_lat = ? , ivr_long = ? , user_agent = ? , device_ip = ? , last_updated = ? where application_id = ?");
			int paramPos = 1;
			pStmt.setString(paramPos++, proofUrl);
			pStmt.setString(paramPos++, ivrLat);
			pStmt.setString(paramPos++, ivrLong);
			pStmt.setString(paramPos++, userAgent);
			pStmt.setString(paramPos++, deviceIP);
			pStmt.setTimestamp(paramPos++, timestamp);
			pStmt.setInt(paramPos++, applicationId);
			count = pStmt.executeUpdate();
			if (count > 0) {
				isUpdated = true;
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
		return isUpdated;
	}

	/**
	 * Method to insert the txn Details into the data base
	 * 
	 * @author GOWRI SANKAR R
	 * @param applicationID
	 * @param txn
	 * @return
	 */
	public static int insertTxnDetails(int applicationID, String txn, String folderName) {
		int count = 0;
		PreparedStatement pStmt = null;
		Connection conn = null;
		try {
			java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"INSERT INTO tbl_txn_details(application_id, txn, folder_location ,created_on, created_by) "
							+ "VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE  application_id = ?, txn = ? , folder_location = ?, updated_on = ? , updated_by = ? ");
			int paramPos = 1;
			pStmt.setLong(paramPos++, applicationID);
			pStmt.setString(paramPos++, txn);
			pStmt.setString(paramPos++, folderName);
			pStmt.setTimestamp(paramPos++, timestamp);
			pStmt.setLong(paramPos++, applicationID);
			/**
			 * on duplicate Key
			 */
			pStmt.setLong(paramPos++, applicationID);
			pStmt.setString(paramPos++, txn);
			pStmt.setString(paramPos++, folderName);
			pStmt.setTimestamp(paramPos++, timestamp);
			pStmt.setLong(paramPos++, applicationID);
			count = pStmt.executeUpdate();
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
		return count;
	}

	/**
	 * Method to update TXN details
	 * 
	 * @author GOWRI SANKAR R
	 * @param applicationID
	 * @param txn
	 * @param folderName
	 * @return
	 */
	public boolean updateTxnDetails(int applicationID, String txn, String folderName) {
		boolean isUpdated = false;
		int count = 0;
		PreparedStatement pStmt = null;
		Connection conn = null;
		try {
			java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"UPDATE  tbl_txn_details SET txn = ? , folder_location = ?, updated_on = ? , updated_by = ? where  application_id = ? ");
			int paramPos = 1;
			pStmt.setString(paramPos++, txn);
			pStmt.setString(paramPos++, folderName);
			pStmt.setTimestamp(paramPos++, timestamp);
			pStmt.setLong(paramPos++, applicationID);
			pStmt.setLong(paramPos++, applicationID);
			count = pStmt.executeUpdate();
			if (count > 0) {
				isUpdated = true;
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
		return isUpdated;
	}

	/**
	 * Get the txn details for given txn
	 * 
	 * @author GOWRI SANKAR R
	 * @param txnName
	 * @return
	 */
	public esignDTO getTxnDetails(String txnName) {
		esignDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"SELECT b.application_id , b.applicant_name , c.city  , a.txn , a.folder_location  "
							+ "FROM tbl_txn_details A inner join tbl_account_holder_personal_details B on a.application_id = b.application_id "
							+ "inner join tbl_communication_address C  on a.application_id = c.application_id "
							+ "where a.txn = ? ");
			pStmt.setString(paromPos++, txnName);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				result = new esignDTO();
				while (rSet.next()) {
					result.setApplication_id(rSet.getInt("b.application_id"));
					result.setApplicant_name(rSet.getString("b.applicant_name"));
					result.setCity(rSet.getString("c.city"));
					result.setTxn(rSet.getString("a.txn"));
					result.setFolderLocation(rSet.getString("a.folder_location"));
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

	public String getEsignedDocument(int application_id, String signedEkycDocument) {
		String fileLocation = "";
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" SELECT attachement_url FROM tbl_application_attachements where application_id = ? and attachement_type = ? order by last_update desc");
			pStmt.setInt(paromPos++, application_id);
			pStmt.setString(paromPos++, signedEkycDocument);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					fileLocation = rSet.getString("attachement_url");
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
		return fileLocation;
	}

	/**
	 * Update the txn status for the given txn
	 * 
	 * @author GOWRI SANKAR R
	 * @param txnStatus
	 * @param txn
	 * @return
	 */
	public boolean updateTxnStatus(int txnStatus, String txn) {
		int count = 0;
		boolean isSuccessFull = false;
		Connection conn = null;
		PreparedStatement pStmt = null;
		try {
			java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
			conn = DBUtil.getConnection();
			pStmt = conn
					.prepareStatement(" UPDATE tbl_txn_details SET txn_status = ? , updated_on = ? where  txn = ? ");
			int paramPos = 1;
			pStmt.setInt(paramPos++, txnStatus);
			pStmt.setString(paramPos++, txn);
			pStmt.setTimestamp(paramPos++, timestamp);
			count = pStmt.executeUpdate();
			if (count > 0) {
				isSuccessFull = true;
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
		return isSuccessFull;
	}

	/**
	 * Method to get search results for the given postal Codes
	 * 
	 * @author GOWRI SANKAR R
	 * @param dto
	 * @return
	 */
	public List<PostalCodesDTO> getPostalCode(PostalCodesDTO dto) {
		List<PostalCodesDTO> response = null;
		PostalCodesDTO result = null;
		PreparedStatement pStmt = null;
		Connection conn = null;
		ResultSet rSet = null;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"SELECT pin_code, district_code, city_name ,district_name, state_name, status FROM tbl_pincode where pin_code like '"
							+ dto.getPin_code() + "%' limit 100");
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				response = new ArrayList<PostalCodesDTO>();
				while (rSet.next()) {
					result = new PostalCodesDTO();
					result.setPin_code(rSet.getString("pin_code"));
					result.setDistrict_code(rSet.getInt("district_code"));
					result.setCity_name(rSet.getString("city_name"));
					result.setDistrict_name(rSet.getString("district_name"));
					result.setState_name(rSet.getString("state_name"));
					result.setStatus(rSet.getString("status"));
					response.add(result);
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
		return response;
	}

	/**
	 * Method to get the bank details by giving the ifsc code
	 * 
	 * @author GOWRI SANKAR R
	 * @param dto
	 * @return
	 */
	public List<IfscCodeDTO> getIfscCode(IfscCodeDTO dto) {
		List<IfscCodeDTO> response = null;
		IfscCodeDTO result = null;
		PreparedStatement pStmt = null;
		Connection conn = null;
		ResultSet rSet = null;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"SELECT bank_id, micr_code, ifc_code, bank_name, bank_address_1, bank_address_2, bank_address_3, "
							+ "bank_city, bank_state, bank_country, bank_zip_code, bank_phone_1, bank_phone_2, unique_id, "
							+ "bank_email, bank_contact_name, bank_contact_designation FROM tbl_ifsccode_details where ifc_code = ? limit 100");
			int paramPos = 1;
			pStmt.setString(paramPos++, dto.getIfc_code());
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				response = new ArrayList<IfscCodeDTO>();
				while (rSet.next()) {
					result = new IfscCodeDTO();
					result.setBank_id(rSet.getInt("bank_id"));
					result.setMicr_code(rSet.getString("micr_code"));
					result.setIfc_code(rSet.getString("ifc_code"));
					result.setBank_name(rSet.getString("bank_name"));
					result.setBank_address_1(rSet.getString("bank_address_1"));
					result.setBank_address_2(rSet.getString("bank_address_2"));
					result.setBank_address_3(rSet.getString("bank_address_3"));
					result.setBank_city(rSet.getString("bank_city"));
					result.setBank_state(rSet.getString("bank_state"));
					result.setBank_country(rSet.getString("bank_country"));
					result.setBank_zip_code(rSet.getString("bank_zip_code"));
					result.setBank_phone_1(rSet.getString("bank_phone_1"));
					result.setBank_phone_2(rSet.getString("bank_phone_2"));
					result.setUnique_id(rSet.getString("unique_id"));
					result.setBank_email(rSet.getString("bank_email"));
					result.setBank_contact_name(rSet.getString("bank_contact_name"));
					result.setBank_contact_designation(rSet.getString("bank_contact_designation"));
					response.add(result);
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
		return response;
	}

	/**
	 * Metthod to get the unique bank name from data base
	 * 
	 * @author GOWRI SANKAR R
	 * @param dto
	 * @return
	 */
	public List<String> getUniqueBankName(IfscCodeDTO dto) {
		List<String> response = null;
		PreparedStatement pStmt = null;
		Connection conn = null;
		ResultSet rSet = null;
		String tempBankName = "";
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement("SELECT  distinct(bank_name) FROM tbl_ifsccode_details where bank_name like '"
					+ dto.getBank_name() + "%' and active_status = 1 limit 100");
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				response = new ArrayList<String>();
				while (rSet.next()) {
					tempBankName = rSet.getString("bank_name");
					response.add(tempBankName);
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
		return response;
	}

	/**
	 * Method to get the unique place by giving the bank name
	 * 
	 * @author GOWRI SANKAR R
	 * @param dto
	 * @return
	 */
	public List<String> getUniquePlaceName(IfscCodeDTO dto) {
		List<String> response = null;
		PreparedStatement pStmt = null;
		Connection conn = null;
		ResultSet rSet = null;
		String tempBankName = "";
		try {
			int paramPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement("SELECT distinct(bank_city) as place FROM tbl_ifsccode_details "
					+ "where active_status = 1 and bank_name = ? and bank_city like '" + dto.getBank_city()
					+ "%' limit 100");
			pStmt.setString(paramPos++, dto.getBank_name());
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				response = new ArrayList<String>();
				while (rSet.next()) {
					tempBankName = rSet.getString("place");
					response.add(tempBankName);
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
		return response;
	}

	/**
	 * Method to get the bank details from the dtata base
	 * 
	 * @author GOWRI SANKAR R
	 * @param dto
	 * @return
	 */
	public List<IfscCodeDTO> getBankDetailsByPlace(IfscCodeDTO dto) {
		List<IfscCodeDTO> response = null;
		IfscCodeDTO result = null;
		PreparedStatement pStmt = null;
		Connection conn = null;
		ResultSet rSet = null;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"SELECT bank_id, micr_code, ifc_code, bank_name, bank_address_1, bank_address_2, bank_address_3, "
							+ "bank_city, bank_state, bank_country, bank_zip_code, bank_phone_1, bank_phone_2, unique_id, "
							+ "bank_email, bank_contact_name, bank_contact_designation FROM tbl_ifsccode_details "
							+ "where bank_name = ? and bank_city = ? and active_status = ?");
			int paramPos = 1;
			pStmt.setString(paramPos++, dto.getBank_name());
			pStmt.setString(paramPos++, dto.getBank_city());
			pStmt.setInt(paramPos++, 1);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				response = new ArrayList<IfscCodeDTO>();
				while (rSet.next()) {
					result = new IfscCodeDTO();
					result.setBank_id(rSet.getInt("bank_id"));
					result.setMicr_code(rSet.getString("micr_code"));
					result.setIfc_code(rSet.getString("ifc_code"));
					result.setBank_name(rSet.getString("bank_name"));
					result.setBank_address_1(rSet.getString("bank_address_1"));
					result.setBank_address_2(rSet.getString("bank_address_2"));
					result.setBank_address_3(rSet.getString("bank_address_3"));
					result.setBank_city(rSet.getString("bank_city"));
					result.setBank_state(rSet.getString("bank_state"));
					result.setBank_country(rSet.getString("bank_country"));
					result.setBank_zip_code(rSet.getString("bank_zip_code"));
					result.setBank_phone_1(rSet.getString("bank_phone_1"));
					result.setBank_phone_2(rSet.getString("bank_phone_2"));
					result.setUnique_id(rSet.getString("unique_id"));
					result.setBank_email(rSet.getString("bank_email"));
					result.setBank_contact_name(rSet.getString("bank_contact_name"));
					result.setBank_contact_designation(rSet.getString("bank_contact_designation"));
					response.add(result);
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
		return response;
	}

	/**
	 * Method to update the document signed or download into the data base *
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 */
	public void updateDocumentSignedOrDownloaded(boolean signed, int applicationId) {
		PreparedStatement pStmt = null;
		Connection conn = null;
		try {
			java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
			conn = DBUtil.getConnection();
			String query = "";
			if (signed) {
				query = "UPDATE tbl_application_master SET  document_signed = ? , last_updated = ?  where application_id = ?";
			} else {
				query = "UPDATE tbl_application_master SET  document_downloaded = ? , last_updated = ? where application_id = ?";
			}
			pStmt = conn.prepareStatement(query);
			int paramPos = 1;
			pStmt.setInt(paramPos++, 1);
			pStmt.setTimestamp(paramPos++, timestamp);
			pStmt.setInt(paramPos++, applicationId);
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
	 * Method to get the Ivr details for the given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param application_id
	 * @return
	 */
	public JSONObject getIvrDetails(int application_id) {
		JSONObject response = null;
		PreparedStatement pStmt = null;
		Connection conn = null;
		ResultSet rSet = null;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"SELECT  ivr_image, ivr_lat, ivr_long FROM  tbl_ivr_capture where application_id = ? ");
			int paramPos = 1;
			pStmt.setInt(paramPos++, application_id);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					response = new JSONObject();
					response.put("ivr_image", rSet.getString("ivr_image"));
					response.put("ivr_lat", rSet.getString("ivr_lat"));
					response.put("ivr_long", rSet.getString("ivr_long"));
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
		return response;
	}

}
