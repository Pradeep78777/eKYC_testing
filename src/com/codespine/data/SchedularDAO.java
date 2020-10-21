package com.codespine.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.codespine.dto.PostalCodesDTO;
import com.codespine.util.DBUtil;
import com.codespine.util.eKYCConstant;

public class SchedularDAO {
	public static SchedularDAO SchedularDAO = null;

	public static SchedularDAO getInstance() {
		if (SchedularDAO == null) {
			SchedularDAO = new SchedularDAO();
		}
		return SchedularDAO;
	}

	public String insertPostalCodeDetails(List<PostalCodesDTO> finalPostalCodes) {
		Connection conn = null;
		String isSuccessful = eKYCConstant.FAILED_MSG;
		PreparedStatement pStmt = null;
		int count = 0;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"INSERT INTO tbl_pincode(pin_code, district_code, district_name, state_name, status)"
							+ "VALUES(?,?,?,?,?)");
			for (PostalCodesDTO itr : finalPostalCodes) {
				int paramPos = 1;
				pStmt.setInt(paramPos++, itr.getPin_code());
				pStmt.setInt(paramPos++, itr.getDistrict_code());
				pStmt.setString(paramPos++, itr.getDistrict_name());
				pStmt.setString(paramPos++, itr.getState_name());
				pStmt.setString(paramPos++, itr.getStatus());
				pStmt.addBatch();
				count++;
				if (count == 100) {
					pStmt.executeBatch();
					count = 0;
				}
			}
			if (count > 0) {
				pStmt.executeBatch();
			}
			isSuccessful = eKYCConstant.SUCCESS_MSG;
		} catch (Exception e) {
			e.printStackTrace();
			isSuccessful = eKYCConstant.FAILED_MSG;
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
