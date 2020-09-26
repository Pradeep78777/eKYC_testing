package com.codespine.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import com.codespine.dto.BankDetailsDTO;
import com.codespine.dto.PanCardDetailsDTO;
import com.codespine.dto.PersonalDetailsDTO;
import com.codespine.util.DBUtil;

public class eKYCDAO {
	/**
	 * Methos to insert the personal details and get the application Id back
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public int personalDetails(PersonalDetailsDTO pDto) {
		java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		int count = 0;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"INSERT INTO tbl_account_holder_details(name, mobile_number, email, otp, verified ,created_on)"
							+ " VALUES (?,?,?,?,?,?) ",
					Statement.RETURN_GENERATED_KEYS);
			pStmt.setString(paromPos++, pDto.getName());
			pStmt.setLong(paromPos++, pDto.getMobile_number());
			pStmt.setString(paromPos++, pDto.getEmail());
			pStmt.setInt(paromPos++, pDto.getOtp());
			pStmt.setInt(paromPos++, 0);
			pStmt.setTimestamp(paromPos++, timestamp);
			pStmt.executeUpdate();
			rSet = pStmt.getGeneratedKeys();
			if (rSet != null) {
				while (rSet.next()) {
					count = rSet.getInt(1);
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
		return count;
	}

	/**
	 * Check user already registred
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public PersonalDetailsDTO checkAlreadyRegistred(PersonalDetailsDTO pDto) {
		PersonalDetailsDTO dto = new PersonalDetailsDTO();
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					" SELECT id , otp , verified FROM tbl_account_holder_details where mobile_number = ? ");
			pStmt.setLong(paromPos++, pDto.getMobile_number());
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					dto.setId(rSet.getInt("id"));
					dto.setOtp(rSet.getInt("otp"));
					dto.setVerified(rSet.getInt("verified"));
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
		return dto;
	}

	/**
	 * Make the profile as verified
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public boolean updateAsVerified(PersonalDetailsDTO pDto) {
		PreparedStatement pStmt = null;
		Connection conn = null;
		boolean issuccess = false;
		int count = 0;
		java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"UPDATE tbl_account_holder_details SET verified = ?, verified_on = ? WHERE mobile_number =? ");
			int paramPos = 1;
			pStmt.setInt(paramPos++, 1);
			pStmt.setTimestamp(paramPos++, timestamp);
			pStmt.setLong(paramPos++, pDto.getMobile_number());
			count = pStmt.executeUpdate();
			if (count > 0) {
				issuccess = true;
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
		return issuccess;
	}

	/**
	 * Make the profile as verified
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public boolean updateOTP(PersonalDetailsDTO pDto) {
		PreparedStatement pStmt = null;
		Connection conn = null;
		boolean issuccess = false;
		int count = 0;
		java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		try {
			conn = DBUtil.getConnection();
			pStmt = conn
					.prepareStatement("UPDATE tbl_account_holder_details SET  otp = ? WHERE mobile_number =? ");
			int paramPos = 1;
			pStmt.setInt(paramPos++, pDto.getOtp());
			pStmt.setLong(paramPos++, pDto.getMobile_number());
			count = pStmt.executeUpdate();
			if (count > 0) {
				issuccess = true;
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
		return issuccess;
	}

	/**
	 * Method inser the pan card details into data base
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public boolean insertPanCardDetails(PanCardDetailsDTO pDto) {
		PreparedStatement pStmt = null;
		Connection conn = null;
		boolean isSuccessful = false;
		int count = 0;
		java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"INSERT INTO tbl_pancard_details(application_id, pan_card, dob, mothersName ,fathersName, "
							+ "pan_card_verified, verification_count, created_on) " + "VALUES (?,?,?,?,?,?,?,?)");
			int paramPos = 1;
			pStmt.setInt(paramPos++, pDto.getApplication_id());
			pStmt.setString(paramPos++, pDto.getPan_card());
			pStmt.setString(paramPos++, pDto.getDob());
			pStmt.setString(paramPos++, pDto.getMothersName());
			pStmt.setString(paramPos++, pDto.getFathersName());
			pStmt.setInt(paramPos++, 0);
			pStmt.setInt(paramPos++, 0);
			pStmt.setTimestamp(paramPos++, timestamp);
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

	/**
	 * Method to inser the personal info Details in to data base
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public boolean insertPersonalInfoDetails(PersonalDetailsDTO pDto) {
		PreparedStatement pStmt = null;
		Connection conn = null;
		boolean isSuccessful = false;
		int count = 0;
		java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"INSERT INTO tbl_account_holder_personal_details(application_id,gender ,marital_status, annual_income, "
							+ "trading_experience, occupation, politically_exposed, created_on) "
							+ "VALUES (?,?,?,?,?,?,?,?)");
			int paramPos = 1;
			pStmt.setInt(paramPos++, pDto.getApplication_id());
			pStmt.setString(paramPos++, pDto.getGender());
			pStmt.setString(paramPos++, pDto.getMarital_status());
			pStmt.setString(paramPos++, pDto.getAnnual_income());
			pStmt.setInt(paramPos++, pDto.getTrading_experience());
			pStmt.setString(paramPos++, pDto.getOccupation());
			pStmt.setInt(paramPos++, pDto.getPolitically_exposed());
			pStmt.setTimestamp(paramPos++, timestamp);
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

	/**
	 * Method to insert the bank account Details in to data base
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	public boolean bankDetails(BankDetailsDTO pDto) {
		PreparedStatement pStmt = null;
		Connection conn = null;
		boolean isSuccessful = false;
		int count = 0;
		java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"INSERT INTO tbl_bank_account_details(application_id, account_holder_name, ifsc_code, "
							+ "bank_account_no, account_type,  verified, verification_count, created_on) "
							+ "VALUES (?,?,?,?,?,?,?,?)");
			int paramPos = 1;
			pStmt.setInt(paramPos++, pDto.getApplication_id());
			pStmt.setString(paramPos++, pDto.getAccount_holder_name());
			pStmt.setString(paramPos++, pDto.getIfsc_code());
			pStmt.setLong(paramPos++, pDto.getBank_account_no());
			pStmt.setString(paramPos++, pDto.getAccount_type());
			pStmt.setInt(paramPos++, 0);
			pStmt.setInt(paramPos++, 0);
			pStmt.setTimestamp(paramPos++, timestamp);
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

}
