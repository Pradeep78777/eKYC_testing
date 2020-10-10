package com.codespine.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.codespine.dto.AccountHolderDetailsDTO;
import com.codespine.dto.AddressDTO;
import com.codespine.dto.ApplicationMasterDTO;
import com.codespine.dto.BankDetailsDTO;
import com.codespine.dto.ExchDetailsDTO;
import com.codespine.dto.PanCardDetailsDTO;
import com.codespine.dto.PdfCoordinationsDTO;
import com.codespine.dto.PersonalDetailsDTO;
import com.codespine.util.CSEnvVariables;
import com.codespine.util.DBUtil;
import com.codespine.util.DateUtil;
import com.codespine.util.Utility;
import com.codespine.util.eKYCConstant;

public class eKYCDAO {
	public static eKYCDAO eKYCDAO = null;
	public static eKYCDAO getInstance() {
		if(eKYCDAO == null) {
			eKYCDAO = new eKYCDAO();
		}
		return eKYCDAO;
	}
	// /**
	// * Methos to insert the personal details and get the application Id back
	// *
	// * @author GOWRI SANKAR R
	// * @param pDto
	// * @return
	// */
	// public int personalDetails(PersonalDetailsDTO pDto) {
	// java.sql.Timestamp timestamp = new
	// java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
	// Connection conn = null;
	// PreparedStatement pStmt = null;
	// ResultSet rSet = null;
	// int count = 0;
	// try {
	// int paromPos = 1;
	// conn = DBUtil.getConnection();
	// pStmt = conn.prepareStatement(
	// "INSERT INTO tbl_account_holder_details(name, mobile_number, email, otp,
	// verified ,created_on)"
	// + " VALUES (?,?,?,?,?,?) ",
	// Statement.RETURN_GENERATED_KEYS);
	// pStmt.setString(paromPos++, pDto.getName());
	// pStmt.setLong(paromPos++, pDto.getMobile_number());
	// pStmt.setString(paromPos++, pDto.getEmail());
	// pStmt.setInt(paromPos++, pDto.getOtp());
	// pStmt.setInt(paromPos++, 0);
	// pStmt.setTimestamp(paromPos++, timestamp);
	// pStmt.executeUpdate();
	// rSet = pStmt.getGeneratedKeys();
	// if (rSet != null) {
	// while (rSet.next()) {
	// count = rSet.getInt(1);
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// try {
	// DBUtil.closeResultSet(rSet);
	// DBUtil.closeStatement(pStmt);
	// DBUtil.closeConnection(conn);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// return count;
	// }
	//
	// /**
	// * Check user already registred
	// *
	// * @author GOWRI SANKAR R
	// * @param pDto
	// * @return
	// */
	// public PersonalDetailsDTO checkAlreadyRegistred(PersonalDetailsDTO pDto)
	// {
	// PersonalDetailsDTO dto = new PersonalDetailsDTO();
	// Connection conn = null;
	// PreparedStatement pStmt = null;
	// ResultSet rSet = null;
	// try {
	// int paromPos = 1;
	// conn = DBUtil.getConnection();
	// pStmt = conn.prepareStatement(
	// " SELECT id , otp , verified FROM tbl_account_holder_details where
	// mobile_number = ? ");
	// pStmt.setLong(paromPos++, pDto.getMobile_number());
	// rSet = pStmt.executeQuery();
	// if (rSet != null) {
	// while (rSet.next()) {
	// dto.setId(rSet.getInt("id"));
	// dto.setOtp(rSet.getInt("otp"));
	// dto.setVerified(rSet.getInt("verified"));
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// try {
	// DBUtil.closeResultSet(rSet);
	// DBUtil.closeStatement(pStmt);
	// DBUtil.closeConnection(conn);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// return dto;
	// }
	//
	// /**
	// * Make the profile as verified
	// *
	// * @author GOWRI SANKAR R
	// * @param pDto
	// * @return
	// */
	// public boolean updateAsVerified(PersonalDetailsDTO pDto) {
	// PreparedStatement pStmt = null;
	// Connection conn = null;
	// boolean issuccess = false;
	// int count = 0;
	// java.sql.Timestamp timestamp = new
	// java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
	// try {
	// conn = DBUtil.getConnection();
	// pStmt = conn.prepareStatement(
	// "UPDATE tbl_account_holder_details SET verified = ?, verified_on = ?
	// WHERE mobile_number =? ");
	// int paramPos = 1;
	// pStmt.setInt(paramPos++, 1);
	// pStmt.setTimestamp(paramPos++, timestamp);
	// pStmt.setLong(paramPos++, pDto.getMobile_number());
	// count = pStmt.executeUpdate();
	// if (count > 0) {
	// issuccess = true;
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// try {
	// DBUtil.closeStatement(pStmt);
	// DBUtil.closeConnection(conn);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// return issuccess;
	// }
	//
	// /**
	// * Make the profile as verified
	// *
	// * @author GOWRI SANKAR R
	// * @param pDto
	// * @return
	// */
	// public boolean updateOTP(PersonalDetailsDTO pDto) {
	// PreparedStatement pStmt = null;
	// Connection conn = null;
	// boolean issuccess = false;
	// int count = 0;
	// try {
	// conn = DBUtil.getConnection();
	// pStmt = conn.prepareStatement("UPDATE tbl_account_holder_details SET otp
	// = ? WHERE mobile_number =? ");
	// int paramPos = 1;
	// pStmt.setInt(paramPos++, pDto.getOtp());
	// pStmt.setLong(paramPos++, pDto.getMobile_number());
	// count = pStmt.executeUpdate();
	// if (count > 0) {
	// issuccess = true;
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// try {
	// DBUtil.closeStatement(pStmt);
	// DBUtil.closeConnection(conn);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// return issuccess;
	// }
	//
	// /**
	// * Method inser the pan card details into data base
	// *
	// * @author GOWRI SANKAR R
	// * @param pDto
	// * @return
	// */
	// public boolean insertPanCardDetails(PanCardDetailsDTO pDto) {
	// PreparedStatement pStmt = null;
	// Connection conn = null;
	// boolean isSuccessful = false;
	// int count = 0;
	// java.sql.Timestamp timestamp = new
	// java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
	// try {
	// conn = DBUtil.getConnection();
	// pStmt = conn.prepareStatement(
	// "INSERT INTO tbl_pancard_details(application_id, pan_card, dob,
	// mothersName ,fathersName, "
	// + "pan_card_verified, verification_count, created_on) " + "VALUES
	// (?,?,?,?,?,?,?,?)");
	// int paramPos = 1;
	// pStmt.setInt(paramPos++, pDto.getApplication_id());
	// pStmt.setString(paramPos++, pDto.getPan_card());
	// pStmt.setString(paramPos++, pDto.getDob());
	// pStmt.setString(paramPos++, pDto.getMothersName());
	// pStmt.setString(paramPos++, pDto.getFathersName());
	// pStmt.setInt(paramPos++, 0);
	// pStmt.setInt(paramPos++, 0);
	// pStmt.setTimestamp(paramPos++, timestamp);
	// count = pStmt.executeUpdate();
	// if (count > 0) {
	// isSuccessful = true;
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// try {
	// DBUtil.closeStatement(pStmt);
	// DBUtil.closeConnection(conn);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// return isSuccessful;
	//
	// }
	//
	// /**
	// * Method to inser the personal info Details in to data base
	// *
	// * @author GOWRI SANKAR R
	// * @param pDto
	// * @return
	// */
	// public boolean insertPersonalInfoDetails(PersonalDetailsDTO pDto) {
	// PreparedStatement pStmt = null;
	// Connection conn = null;
	// boolean isSuccessful = false;
	// int count = 0;
	// java.sql.Timestamp timestamp = new
	// java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
	// try {
	// conn = DBUtil.getConnection();
	// pStmt = conn.prepareStatement(
	// "INSERT INTO tbl_account_holder_personal_details(application_id,gender
	// ,marital_status, annual_income, "
	// + "trading_experience, occupation, politically_exposed, created_on) "
	// + "VALUES (?,?,?,?,?,?,?,?)");
	// int paramPos = 1;
	// pStmt.setInt(paramPos++, pDto.getApplication_id());
	// pStmt.setString(paramPos++, pDto.getGender());
	// pStmt.setString(paramPos++, pDto.getMarital_status());
	// pStmt.setString(paramPos++, pDto.getAnnual_income());
	// pStmt.setInt(paramPos++, pDto.getTrading_experience());
	// pStmt.setString(paramPos++, pDto.getOccupation());
	// pStmt.setInt(paramPos++, pDto.getPolitically_exposed());
	// pStmt.setTimestamp(paramPos++, timestamp);
	// count = pStmt.executeUpdate();
	// if (count > 0) {
	// isSuccessful = true;
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// try {
	// DBUtil.closeStatement(pStmt);
	// DBUtil.closeConnection(conn);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// return isSuccessful;
	//
	// }
	//
	// /**
	// * Method to insert the bank account Details in to data base
	// *
	// * @author GOWRI SANKAR R
	// * @param pDto
	// * @return
	// */
	// public boolean bankDetails(BankDetailsDTO pDto) {
	// PreparedStatement pStmt = null;
	// Connection conn = null;
	// boolean isSuccessful = false;
	// int count = 0;
	// java.sql.Timestamp timestamp = new
	// java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
	// try {
	// conn = DBUtil.getConnection();
	// pStmt = conn.prepareStatement(
	// "INSERT INTO tbl_bank_account_details(application_id,
	// account_holder_name, ifsc_code, "
	// + "bank_account_no, account_type, verified, verification_count,
	// created_on) "
	// + "VALUES (?,?,?,?,?,?,?,?)");
	// int paramPos = 1;
	// pStmt.setInt(paramPos++, pDto.getApplication_id());
	// pStmt.setString(paramPos++, pDto.getAccount_holder_name());
	// pStmt.setString(paramPos++, pDto.getIfsc_code());
	// pStmt.setLong(paramPos++, pDto.getBank_account_no());
	// pStmt.setString(paramPos++, pDto.getAccount_type());
	// pStmt.setInt(paramPos++, 0);
	// pStmt.setInt(paramPos++, 0);
	// pStmt.setTimestamp(paramPos++, timestamp);
	// count = pStmt.executeUpdate();
	// if (count > 0) {
	// isSuccessful = true;
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// try {
	// DBUtil.closeStatement(pStmt);
	// DBUtil.closeConnection(conn);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// return isSuccessful;
	//
	// }

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
					" SELECT application_id , mobile_otp, mobile_no_verified, email_id, email_activated, application_status"
							+ " FROM tbl_application_master where mobile_number = ? and delete_flag = ? ");
			pStmt.setLong(paromPos++, pDto.getMobile_number());
			pStmt.setLong(paromPos++, 0);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new PersonalDetailsDTO();
					result.setApplication_id(rSet.getInt("application_id"));
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
	 * To save the basic information for the user
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public int basicInformation(PersonalDetailsDTO pDto) {
		int count = 0;
		PreparedStatement pStmt = null;
		Connection conn = null;
		try {
			java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"INSERT INTO tbl_account_holder_personal_details(application_id, mothersName, fathersName, gender, marital_status, "
							+ "annual_income, trading_experience, occupation, politically_exposed, created_on) "
							+ "VALUES (?,?,?,?,?,?,?,?,?,?)");
			int paramPos = 1;
			pStmt.setLong(paramPos++, pDto.getApplication_id());
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
					"INSERT INTO tbl_communication_address(application_id, flat_no, street, pin, city, district, state, created_on) "
							+ "VALUES (?,?,?,?,?,?,?,?)");
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
					"INSERT INTO tbl_permanent_address(application_id, flat_no, street, pin, city, district, state, created_on) "
							+ "VALUES (?,?,?,?,?,?,?,?)");
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
	 * To check the basic information for the application is already inserted or
	 * not
	 * 
	 * @author GOWRI SANKAR R
	 * @param applicationId
	 * @return
	 */
	public static PersonalDetailsDTO CheckBasicInformation(int applicationId) {
		PersonalDetailsDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		 HashMap<String,String> json = null;
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
					json = new  HashMap<String,String>();
					result.setId(rSet.getInt("id"));
					result.setApplication_id(rSet.getInt("application_id"));
					result.setApplicant_name(rSet.getString("applicant_name"));
					json.put("applicant_name",rSet.getString("applicant_name"));
					result.setMothersName(rSet.getString("mothersName"));
					json.put("mothersName",rSet.getString("mothersName"));
					result.setFathersName(rSet.getString("fathersName"));
					json.put("fathersName",rSet.getString("fathersName"));
					result.setGender(rSet.getString("gender"));
					json.put("gender",rSet.getString("gender"));
					result.setMarital_status(rSet.getString("marital_status"));
					json.put("marital_status",rSet.getString("marital_status"));
					result.setAnnual_income(rSet.getString("annual_income"));
					json.put("annual_income",rSet.getString("annual_income"));
					result.setTrading_experience(rSet.getString("trading_experience"));
					json.put("trading_experience",rSet.getString("trading_experience"));
					result.setOccupation(rSet.getString("occupation"));
					json.put("occupation",rSet.getString("occupation"));
					result.setPolitically_exposed(rSet.getString("politically_exposed"));
					json.put("politically_exposed",rSet.getString("politically_exposed"));
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
	 * To update the basic information for the given application uSer
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public boolean updateBasicInformation(PersonalDetailsDTO pDto) {
		int count = 0;
		boolean isSuccessFull = false;
		Connection conn = null;
		PreparedStatement pStmt = null;
		java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" UPDATE tbl_account_holder_personal_details SET mothersName = ? , fathersName = ? , gender = ? , marital_status = ? , "
							+ "annual_income = ? , trading_experience = ? , occupation = ? , politically_exposed = ? , last_updated = ? where application_id = ? ");
			int paramPos = 1;
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
	 * Check the communication address is aldready updated
	 * 
	 * @author GOWRI SANKAR R
	 * @param application_id
	 * @return
	 */
	public static AddressDTO checkCommunicationAddress(int application_id) {
		AddressDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		 HashMap<String,String> json = null;
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
					json = new  HashMap<String,String>();
					result.setApplication_id(rSet.getInt("application_id"));
					result.setFlat_no(rSet.getString("flat_no"));
					json.put("flat_no", rSet.getString("flat_no"));
					result.setStreet(rSet.getString("street"));
					json.put("street", rSet.getString("street"));
					result.setPin(rSet.getInt("pin"));
					json.put("pin", Integer.toString(rSet.getInt("pin")));
					result.setCity(rSet.getString("city"));
					json.put("city", rSet.getString("city"));
					result.setDistrict(rSet.getString("district"));
					json.put("district", rSet.getString("district"));
					result.setState(rSet.getString("state"));
					json.put("state", rSet.getString("state"));
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
	public static AddressDTO checkPermanentAddress(int application_id) {
		AddressDTO result = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		 HashMap<String,String> json = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn
					.prepareStatement(" SELECT application_id,flat_no,street,pin,city,district,state  FROM tbl_permanent_address where application_id = ? ");
			pStmt.setLong(paromPos++, application_id);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new AddressDTO();
					json = new  HashMap<String,String>();
					result.setApplication_id(rSet.getInt("application_id"));
					json.put("application_id", Integer.toString(rSet.getInt("application_id")));
					result.setFlat_no(rSet.getString("flat_no"));
					json.put("flat_no", rSet.getString("flat_no"));
					result.setStreet(rSet.getString("street"));
					json.put("street", rSet.getString("street"));
					result.setPin(rSet.getInt("pin"));
					json.put("pin", Integer.toString(rSet.getInt("pin")));
					result.setCity(rSet.getString("city"));
					json.put("city", rSet.getString("city"));
					result.setDistrict(rSet.getString("district"));
					json.put("district", rSet.getString("district"));
					result.setState(rSet.getString("state"));
					json.put("state", rSet.getString("state"));
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
	public static boolean updateCommunicationAddress(AddressDTO pDto) {
		int count = 0;
		boolean isSuccessFull = false;
		Connection conn = null;
		PreparedStatement pStmt = null;
		java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" UPDATE tbl_communication_address SET flat_no = ? , street = ? , pin = ? , city = ? , district = ? , state = ? "
							+ " , last_updated = ? where application_id = ? ");
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
	 * Update the Communication address for the application ID
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public static boolean updatePermanentAddress(AddressDTO pDto) {
		int count = 0;
		boolean isSuccessFull = false;
		Connection conn = null;
		PreparedStatement pStmt = null;
		java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" UPDATE tbl_permanent_address SET flat_no = ? , street = ? , pin = ? , city = ? , district = ? , state = ? "
							+ " , last_updated = ? where application_id = ? ");
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
					"INSERT INTO tbl_bank_account_details(application_id, account_holder_name, ifsc_code, bank_account_no, "
							+ "verified, verification_count, created_on) " + "VALUES (?,?,?,?,?,?,?)");
			int paramPos = 1;
			pStmt.setLong(paramPos++, pDto.getApplication_id());
			pStmt.setString(paramPos++, pDto.getAccount_holder_name());
			pStmt.setString(paramPos++, pDto.getIfsc_code());
			pStmt.setLong(paramPos++, pDto.getBank_account_no());
			pStmt.setInt(paramPos++, 0);
			pStmt.setInt(paramPos++, 0);
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
		 HashMap<String,String> json = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" SELECT id,application_id,account_holder_name,ifsc_code,"
					+ "bank_account_no,account_type,verified_on,verified,verification_count "
					+ "  FROM tbl_bank_account_details where application_id = ? ");
			pStmt.setLong(paromPos++, application_id);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new BankDetailsDTO();
					json = new  HashMap<String,String>();
					result.setId(rSet.getInt("id"));
					result.setApplication_id(rSet.getInt("application_id"));
					result.setAccount_holder_name(rSet.getString("account_holder_name"));
					json.put("account_holder_name", rSet.getString("account_holder_name"));
					result.setIfsc_code(rSet.getString("ifsc_code"));
					json.put("ifsc_code", rSet.getString("ifsc_code"));
					result.setBank_account_no(rSet.getInt("bank_account_no"));
					json.put("bank_account_no", Integer.toString(rSet.getInt("bank_account_no")));
					result.setAccount_type(rSet.getString("account_type"));
					json.put("account_type", rSet.getString("account_type"));
					result.setVerified_on(rSet.getString("verified_on"));
					result.setVerified(rSet.getInt("verified"));
					result.setVerification_count(rSet.getInt("verification_count"));
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
	 * Update the bank details using application ID
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public boolean updateBankDetails(BankDetailsDTO pDto) {
		int count = 0;
		boolean isSuccessFull = false;
		Connection conn = null;
		PreparedStatement pStmt = null;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" UPDATE tbl_bank_account_details SET account_holder_name = ? , ifsc_code = ? , bank_account_no = ? where application_id = ? ");
			int paramPos = 1;
			pStmt.setString(paramPos++, pDto.getAccount_holder_name());
			pStmt.setString(paramPos++, pDto.getIfsc_code());
			pStmt.setLong(paramPos++, pDto.getBank_account_no());
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
					"INSERT INTO tbl_pancard_details(application_id, pan_card,applicant_name , dob , created_on) VALUES (?,?,?,?,?)");
			int paramPos = 1;
			pStmt.setInt(paramPos++, pDto.getApplication_id());
			pStmt.setString(paramPos++, pDto.getPan_card());
			pStmt.setString(paramPos++, pDto.getApplicant_name());
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
		 HashMap<String,String> json = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(" SELECT application_id,pan_card,dob,mothersName,fathersName,pan_card_verified,nsdl_name,nsdl_dob  FROM tbl_pancard_details where application_id = ? ");
			pStmt.setLong(paromPos++, application_id);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new PanCardDetailsDTO();
					json = new  HashMap<String,String>();
					result.setApplication_id(rSet.getInt("application_id"));
					result.setPan_card(rSet.getString("pan_card"));
					json.put("pan_card", rSet.getString("pan_card"));
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
	 * Method to update the pan card details
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public boolean updatePanCardDetails(PanCardDetailsDTO pDto) {
		int count = 0;
		boolean isSuccessFull = false;
		Connection conn = null;
		PreparedStatement pStmt = null;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" UPDATE tbl_pancard_details SET pan_card = ? , applicant_name = ? , dob = ? where application_id = ? ");
			int paramPos = 1;
			pStmt.setString(paramPos++, pDto.getPan_card());
			pStmt.setString(paramPos++, pDto.getApplicant_name());
			// pStmt.setString(paramPos++, pDto.getFathersName());
			pStmt.setString(paramPos++, pDto.getDob());
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
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(" SELECT application_id  FROM tbl_exch_segments where application_id = ? ");
			pStmt.setLong(paromPos++, application_id);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new ExchDetailsDTO();
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
	public int insertAttachementDetails(String proofUrl, String proofType, int applicationId) {
		int count = 0;
		PreparedStatement pStmt = null;
		Connection conn = null;
		try {
			java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"INSERT INTO tbl_application_attachements(application_id, attachement_type, attachement_url ,last_update, created_on) "
							+ "VALUES (?,?,?,?,?)");
			int paramPos = 1;
			pStmt.setInt(paramPos++, applicationId);
			pStmt.setString(paramPos++, proofType);
			pStmt.setString(paramPos++, proofUrl);
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
		 HashMap<String,String> json =null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" SELECT application_id,mobile_number,mobile_no_verified,mob_owner,mobile_otp,"
					+ "email_id,email_owner,email_activation_code,email_activated,otp_verified_on,"
					+ "email_activated_on,application_status,last_updated,created_date"
							+ " FROM tbl_application_master where application_id = ?  ");
			pStmt.setInt(paromPos++, pDto.getApplication_id());
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					json = new  HashMap<String,String>();
					result = new ApplicationMasterDTO();
					result.setApplication_id(rSet.getInt("application_id"));
					json.put("application_id", Integer.toString(rSet.getInt("application_id")));
					result.setMobile_number(rSet.getString("mobile_number"));
					json.put("mobile_number", rSet.getString("mobile_number"));
					result.setMobile_no_verified(rSet.getInt("mobile_no_verified"));
					result.setMob_owner(rSet.getString("mob_owner"));
					json.put("mob_owner", rSet.getString("mob_owner"));
					result.setMobile_otp(rSet.getString("mobile_otp")); 
					result.setEmail_id(rSet.getString("email_id"));
					json.put("email_id", rSet.getString("email_id"));
					result.setEmail_owner(rSet.getString("email_owner"));
					json.put("email_owner", rSet.getString("email_owner"));
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
			pStmt.setInt(paromPos++,applicationId);
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
					" SELECT id,column_name,coordinates,data_type FROM tbl_pdf_coordinations ");
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					result = new PdfCoordinationsDTO();
					result.setId(rSet.getInt("id"));
					result.setColumn_name(rSet.getString("column_name"));
					result.setCoordinates(rSet.getString("coordinates"));
					result.setData_type(rSet.getString("data_type"));
					if(pdfCoordinationsDTOs == null) {
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
			pStmt = conn.prepareStatement(
					" SELECT column_name FROM tbl_pdf_coordinations ");
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
	
}
