package com.codespine.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.codespine.dto.AccesslogDTO;
import com.codespine.dto.AdminDTO;
import com.codespine.dto.ReportsDTO;
import com.codespine.dto.ResponseDTO;
import com.codespine.service.ReportsService;

@Path("/reports")
public class ReportsController {
	AccesslogDTO accessLog = new AccesslogDTO();
	String contentType = "content-type";
	@Context
	HttpServletRequest request;
	java.sql.Timestamp created_on = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());

	/**
	 * Method to get the reports from the data base
	 * 
	 * @author GOWRI SANKAR R
	 * @param requestContext
	 * @param pObject
	 * @return
	 */
	@POST
	@Path("/reportGeneration")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO reportGeneration(@Context ContainerRequestContext requestContext, ReportsDTO pObject) {
		ResponseDTO response = new ResponseDTO();
		response = ReportsService.getInstance().reportGeneration(pObject);
		return response;
	}

	/**
	 * Method to get OTP Records
	 * 
	 * @author pradeep
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/getOtpFullDatas")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO getOtpFullDatas(AdminDTO pDto) {
		ResponseDTO response = ReportsService.getInstance().getOtpFullDatas(pDto);
		return response;
	}
}
