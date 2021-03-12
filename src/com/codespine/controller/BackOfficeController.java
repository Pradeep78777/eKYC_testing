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
import com.codespine.dto.BackOfficeDTO;
import com.codespine.dto.ResponseDTO;
import com.codespine.service.BackOfficeService;
import com.codespine.util.Utility;

@Path("/backOffice")
public class BackOfficeController {
	AccesslogDTO accessLog = new AccesslogDTO();
	String contentType = "content-type";
	@Context
	HttpServletRequest request;
	java.sql.Timestamp created_on = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());

	/**
	 * Method to push the datan to the back office
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/pushDataToBackOffice")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO pushDataToBackOffice(@Context ContainerRequestContext requestContext, BackOfficeDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		accessLog.setDevice_ip(request.getHeader("X-Forwarded-For"));
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		Utility.inputAccessLogDetails(accessLog, pDto, "");
		response = BackOfficeService.getInstance().pushDataToBackOffice(pDto.getApplicationId(), pDto.getBranchCode(),
				pDto.getClientCode(), pDto.getVerifiedBy(), pDto.getVerifiedByDesigination());
		return response;
	}

}
