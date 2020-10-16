package com.codespine.service;

import org.apache.log4j.Logger;

import com.codespine.data.AccessLogDAO;
import com.codespine.data.ErrorLogDAO;
import com.codespine.dto.AccesslogDTO;	

/**
 * AccessLog - Service with CRUD operations
 * 
 * @Author Pradeep K
 * @Date 15/11/17
 */

public class AccessLogService {
	Logger errorlog = Logger.getLogger(AccessLogService.class);
	ErrorLogDAO errordao = new ErrorLogDAO();

	public boolean insertCommunicationAccessLogRecords(AccesslogDTO pAccesslogDto) {
		try {
			// System.out.println("new
			// query------"+Calendar.getInstance().getTime());
			AccessLogDAO pAccessLogDAO = new AccessLogDAO();
			boolean isSuccessful = pAccessLogDAO.insertCommunicationAccessLogRecords(pAccesslogDto);
			return isSuccessful;
		} catch (Exception e) {
			errorlog.error(e.getMessage());
			errorlog.error(e.fillInStackTrace());
			errordao.insertErrorLogRecords("AccessLogService", "insertCommunicationAccessLogRecords",
					pAccesslogDto.toString() + "", null, e.getMessage());
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * Method to inset access log input records
	 * 
	 * @param pAccesslogDto
	 * @return
	 * @author Dinesh
	 */
	public boolean insertAccessLogInputRecords(AccesslogDTO pAccesslogDto) {
		try {
			AccessLogDAO pAccessLogDAO = new AccessLogDAO();
			boolean isSuccessful = pAccessLogDAO.insertAccessLogInputRecords(pAccesslogDto);
			return isSuccessful;
		} catch (Exception e) {
			errorlog.error(e.getMessage());
			errorlog.error(e.fillInStackTrace());
			errordao.insertErrorLogRecords("AccessLogService", "insertAccessLogInputRecords",
					pAccesslogDto.toString() + "", null, e.getMessage());
			e.printStackTrace();
		}
		return false;

	}

	public boolean insertResponseData(AccesslogDTO pAccesslogDto) {
		try {
			AccessLogDAO pAccessLogDAO = new AccessLogDAO();
			boolean isSuccessful = pAccessLogDAO.insertResponseData(pAccesslogDto);
			return isSuccessful;
		} catch (Exception e) {
			errorlog.error(e.getMessage());
			errorlog.error(e.fillInStackTrace());
			errordao.insertErrorLogRecords("AccessLogService", "insertCommunicationAccessLogRecords",
					pAccesslogDto.toString() + "", null, e.getMessage());
			e.printStackTrace();
		}
		return false;

	}

}
