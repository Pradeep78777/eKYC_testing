package com.codespine.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.codespine.util.DBUtil;

public class BackOfficeDAO {
	public static BackOfficeDAO BackOfficeDAO = null;

	public static BackOfficeDAO getInstance() {
		if (BackOfficeDAO == null) {
			BackOfficeDAO = new BackOfficeDAO();
		}
		return BackOfficeDAO;
	}

	/**
	 * 
	 * @param masterKey
	 * @param subKey
	 * @return
	 */
	public long getSerialNo(int masterKey, int subKey) {
		long serialNo = 0;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		try {
			int paromPos = 1;
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement("SELECT Value FROM tbl_keyvaluepair where MasterKey = ? and Subkey = ?");
			pStmt.setInt(paromPos++, masterKey);
			pStmt.setInt(paromPos++, subKey);
			rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					serialNo = rSet.getLong("value");
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
		return serialNo;
	}

	/**
	 * 
	 * @param serialNo
	 * @param masterKey
	 * @param subKey
	 * @return
	 */
	public boolean updateSerialNo(long serialNo, int masterKey, int subKey) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		boolean issuccessfull = false;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(" UPDATE tbl_keyvaluepair SET Value = ? WHERE MasterKey = ? and Subkey = ? ");
			int parompos = 1;
			pStmt.setLong(parompos++, serialNo);
			pStmt.setInt(parompos++, masterKey);
			pStmt.setInt(parompos++, subKey);
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
	 * Method to insert the back office request response into the data base
	 * 
	 * @author GOWRI SANKAR R
	 * @param applicationId
	 * @param url
	 * @param input
	 * @param output
	 * @return
	 */
	public boolean insertBackOfficeResponse(int applicationId, String url, String input, String output,
			String verifiedBy, String verifiedByDesigination, String branchName) {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 100, 1, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
		executor.execute(new Runnable() {
			@Override
			public void run() {
				Connection conn = null;
				boolean isSuccessful = false;
				PreparedStatement pStmt = null;
				ResultSet rSet = null;
				java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
				try {
					conn = DBUtil.getConnection();
					pStmt = conn.prepareStatement(
							"INSERT INTO tbl_backoffice_request_parameter(application_id, back_office_url, back_office_input, back_office_response, "
									+ "branch_name, verified_by, verified_by_desigination, created_by, created_on) values(?,?,?,?,?,?,?,?,?)");
					int paramPos = 1;
					pStmt.setInt(paramPos++, applicationId);
					pStmt.setString(paramPos++, url);
					pStmt.setString(paramPos++, input);
					pStmt.setString(paramPos++, output);
					pStmt.setString(paramPos++, branchName);
					pStmt.setString(paramPos++, verifiedBy);
					pStmt.setString(paramPos++, verifiedByDesigination);
					pStmt.setInt(paramPos++, applicationId);
					pStmt.setTimestamp(paramPos++, timestamp);
					isSuccessful = pStmt.execute();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (rSet != null) {
							rSet.close();
						}
						if (pStmt != null) {
							pStmt.close();
						}
						if (conn != null) {
							conn.close();
						}
						executor.shutdown();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		});
		return false;
	}

	/**
	 * Method to update the application as Completed
	 * 
	 * @author GOWRI SANKAR R
	 * @param applicationID
	 * @return
	 */
	public boolean updateApplicationCompleted(int applicationID) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		boolean issuccessfull = false;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn
					.prepareStatement(" UPDATE tbl_application_master SET is_completed = ? WHERE application_id = ? ");
			int parompos = 1;
			pStmt.setInt(parompos++, 1);
			pStmt.setInt(parompos++, applicationID);
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
}
