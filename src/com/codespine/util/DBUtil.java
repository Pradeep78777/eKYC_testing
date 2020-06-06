package com.codespine.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

public class DBUtil {

	static final Logger ERRORLOG = Logger.getLogger("debugLogger");

	private static final String driverSrc = CSEnvVariables.getProperty("dbDriverSrc");
	private static final String urlSrc = CSEnvVariables.getProperty("dbServerSrc");
	private static final String usernameSrc = CSEnvVariables.getProperty("dbLoginSrc");
	private static final String passwordSrc = CSEnvVariables.getProperty("dbPasswordSrc");
	private static final String validationQuerySrc = CSEnvVariables.getProperty("validationQuerySrc");
	private static final int minConnsSrc = Integer.valueOf(CSEnvVariables.getProperty("minConnsSrc"));
	private static final int maxConnsSrc = Integer.valueOf(CSEnvVariables.getProperty("maxConnsSrc"));
	private static final int maxWaitSrc = Integer.valueOf(CSEnvVariables.getProperty("maxWaitTimeSrc"));

	static DataSource ds = setupSrcDataSource();

	public static DataSource setupSrcDataSource() {
		BasicDataSource bs = new BasicDataSource();
		try {
			bs.setDriverClassName(driverSrc);
			bs.setUrl(urlSrc);
			bs.setInitialSize(minConnsSrc);
			bs.setMaxActive(maxConnsSrc);
			bs.setMinIdle(minConnsSrc);
			bs.setMaxWait(maxWaitSrc);
			bs.setMaxIdle(minConnsSrc);
			bs.setUsername(usernameSrc);
			bs.setPassword(passwordSrc);
			bs.setValidationQuery(validationQuerySrc);
			bs.setTestOnBorrow(true);
			bs.setTestWhileIdle(true);
		} catch (Exception e) {
			ERRORLOG.error(e.getClass() + ":", e);
			e.printStackTrace();
		}
		return bs;
	}

	public static Connection getDerbyConnection() throws Exception {
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			String myDb = "jdbc:derby://localhost:1527/sample";
			Connection DBconn = DriverManager.getConnection(myDb, "app", "app");
			return DBconn;
		} catch (SQLException e) {
			e.printStackTrace();
			ERRORLOG.error(e.getClass() + ":", e);
			return null;
		}
	}

	public static Connection getConnection() throws Exception {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			ERRORLOG.error(e.getClass() + ":", e);
			return null;
		}
	}

	public void closeStatement(Statement stmt) {
		if (stmt == null) {
			return;
		}
		try {
			stmt.close();
		} catch (Exception e) {
		}
	}

	public static void closeStatement(PreparedStatement stmt) {
		if (stmt == null) {
			return;
		}
		try {
			stmt.close();
		} catch (Exception e) {
		}
	}

	public static void closeConnection(Connection connection) {
		if (connection == null) {
			return;
		}
		try {
			connection.close();
		} catch (Exception e) {
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs == null) {
			return;
		}
		try {
			rs.close();
		} catch (Exception e) {
		}
	}

	public void closeCallableStmt(CallableStatement callableStatement) {
		if (callableStatement == null) {
			return;
		}
		try {
			callableStatement.close();
		} catch (Exception e) {
		}
	}

	public static String getDriversrc() {
		return driverSrc;
	}
}
