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
import com.codespine.dto.AdminDetailsDTO;
import com.codespine.dto.BranchListDTO;
import com.codespine.dto.IfscCodeDTO;
import com.codespine.dto.PerformanceDTO;
import com.codespine.dto.ResponseDTO;
import com.codespine.service.AdminService;
import com.codespine.util.Utility;

@Path("/admin")
public class AdminController {
	public static AdminController AdminController = null;

	public static AdminController getInstance() {
		if (AdminController == null) {
			AdminController = new AdminController();
		}
		return AdminController;
	}

	AccesslogDTO accessLog = new AccesslogDTO();
	String contentType = "content-type";
	@Context
	HttpServletRequest request;
	java.sql.Timestamp created_on = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());

	/**
	 * Method to login for admin using email and password
	 * 
	 * @author GOWRI SANKAR R
	 * @return
	 */
	@POST
	@Path("/adminLogin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO adminLogin(@Context ContainerRequestContext requestContext, AdminDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		accessLog.setDevice_ip(request.getHeader("X-Forwarded-For"));
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		Utility.inputAccessLogDetails(accessLog, pDto, "");
		response = AdminService.getInstance().adminLogin(pDto);
		return response;
	}

	/**
	 * Method to get all user records from data base
	 * 
	 * @author GOWRI SANKAR R
	 * @return
	 */
	@POST
	@Path("/getAllUserRecords")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO getAllUserRecords(@Context ContainerRequestContext requestContext, AdminDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		accessLog.setDevice_ip(request.getHeader("X-Forwarded-For"));
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		Utility.inputAccessLogDetails(accessLog, "", "");
		response = AdminService.getInstance().getAllUserRecords(pDto);
		return response;
	}

	/**
	 * Method to get report list from the data base
	 * 
	 * @author GOWRI SANKAR R
	 * @return
	 */
	@POST
	@Path("/getUserReportList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO getUserReportList(@Context ContainerRequestContext requestContext, AdminDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		accessLog.setDevice_ip(request.getHeader("X-Forwarded-For"));
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		Utility.inputAccessLogDetails(accessLog, pDto, "");
		response = AdminService.getInstance().getUserReportList(pDto);
		return response;
	}

	/**
	 * Method to respond back the user for given user pan card Details
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/respondPanCard")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO respondPanCard(@Context ContainerRequestContext requestContext, AdminDTO pDto) {
		accessLog.setDevice_ip(request.getHeader("X-Forwarded-For"));
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		Utility.inputAccessLogDetails(accessLog, pDto, "");
		ResponseDTO response = AdminService.getInstance().respondPanCard(pDto);
		return response;
	}

	/**
	 * Method to respond back the user for given user pan card Details
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/respondPersonalDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO respondPersonalDetails(@Context ContainerRequestContext requestContext, AdminDTO pDto) {
		accessLog.setDevice_ip(request.getHeader("X-Forwarded-For"));
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		Utility.inputAccessLogDetails(accessLog, pDto, "");
		ResponseDTO response = AdminService.getInstance().respondPersonalDetails(pDto);
		return response;
	}

	/**
	 * Method to respond back the user for given user pan card Details
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/respondBankAccountDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO respondBankAccountDetails(@Context ContainerRequestContext requestContext, AdminDTO pDto) {
		accessLog.setDevice_ip(request.getHeader("X-Forwarded-For"));
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		Utility.inputAccessLogDetails(accessLog, pDto, "");
		ResponseDTO response = AdminService.getInstance().respondBankAccountDetails(pDto);
		return response;
	}

	/**
	 * Method to respond back the user for given Communication Address
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/respondCommunicationAddress")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO respondCommunicationAddress(@Context ContainerRequestContext requestContext, AdminDTO pDto) {
		accessLog.setDevice_ip(request.getHeader("X-Forwarded-For"));
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		Utility.inputAccessLogDetails(accessLog, pDto, "");
		ResponseDTO response = AdminService.getInstance().respondCommunicationAddress(pDto);
		return response;
	}

	/**
	 * Method to respond back the user for given premanent Address DEtails
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/respondPermanentAddress")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO respondPermanentAddress(@Context ContainerRequestContext requestContext, AdminDTO pDto) {
		accessLog.setDevice_ip(request.getHeader("X-Forwarded-For"));
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		Utility.inputAccessLogDetails(accessLog, pDto, "");
		ResponseDTO response = AdminService.getInstance().respondPermanentAddress(pDto);
		return response;
	}

	/**
	 * Method to respond back the attached Details for the given Application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/respondAttachementDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO respondAttachementDetails(@Context ContainerRequestContext requestContext, AdminDTO pDto) {
		accessLog.setDevice_ip(request.getHeader("X-Forwarded-For"));
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		Utility.inputAccessLogDetails(accessLog, pDto, "");
		ResponseDTO response = AdminService.getInstance().respondAttachementDetails(pDto);
		return response;
	}

	/**
	 * TO get attached file for the given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/getAttachedFileDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO getAttachedFileDetails(@Context ContainerRequestContext requestContext, AdminDTO pDto) {
		accessLog.setDevice_ip(request.getHeader("X-Forwarded-For"));
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		Utility.inputAccessLogDetails(accessLog, pDto, "");
		ResponseDTO response = AdminService.getInstance().getAttachedFileDetails(pDto);
		return response;
	}

	/**
	 * To start the application by the admin for given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/startApplication")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO startApplication(@Context ContainerRequestContext requestContext, AdminDTO pDto) {
		accessLog.setDevice_ip(request.getHeader("X-Forwarded-For"));
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		Utility.inputAccessLogDetails(accessLog, pDto, "");
		ResponseDTO response = AdminService.getInstance().startApplication(pDto);
		return response;
	}

	/**
	 * Method to end the aplication for given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/endApplication")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO endApplication(@Context ContainerRequestContext requestContext, AdminDTO pDto) {
		accessLog.setDevice_ip(request.getHeader("X-Forwarded-For"));
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		Utility.inputAccessLogDetails(accessLog, pDto, "");
		ResponseDTO response = AdminService.getInstance().endApplication(pDto);
		return response;
	}

	/**
	 * Method to get rejected documents for the given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/getRejectedDocuments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO getRejectedDocuments(@Context ContainerRequestContext requestContext, AdminDTO pDto) {
		accessLog.setDevice_ip(request.getHeader("X-Forwarded-For"));
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		Utility.inputAccessLogDetails(accessLog, pDto, "");
		ResponseDTO response = AdminService.getInstance().getRejectedDocuments(pDto);
		return response;
	}

	/**
	 * Method
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/getApplicationStatus")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO getApplicationStatus(@Context ContainerRequestContext requestContext, AdminDTO pDto) {
		accessLog.setDevice_ip(request.getHeader("X-Forwarded-For"));
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		Utility.inputAccessLogDetails(accessLog, pDto, "");
		ResponseDTO response = AdminService.getInstance().getApplicationStatus(pDto);
		return response;
	}

	/**
	 * Method to get all pending records
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/getPendingRecords")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO getPendingRecords(@Context ContainerRequestContext requestContext, AdminDTO pDto) {
		accessLog.setDevice_ip(request.getHeader("X-Forwarded-For"));
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		Utility.inputAccessLogDetails(accessLog, pDto, "");
		ResponseDTO response = AdminService.getInstance().getPendingRecords(pDto);
		return response;
	}

	/**
	 * Method to get the completed records
	 * 
	 * @author GOWRI SANKAR R
	 * @return
	 */
	@POST
	@Path("/getCompletedRecords")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO getCompletedRecords(@Context ContainerRequestContext requestContext) {
		accessLog.setDevice_ip(request.getHeader("X-Forwarded-For"));
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		Utility.inputAccessLogDetails(accessLog, "", "");
		ResponseDTO response = AdminService.getInstance().getCompletedRecords();
		return response;
	}

	/**
	 * Method to get the report list for the given status
	 * 
	 * @author GOWRI SANKAR R
	 * @param status
	 * @return
	 */
	@POST
	@Path("/getRecordsDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO getRecordsDetails(@Context ContainerRequestContext requestContext, AdminDTO pDto) {
		accessLog.setDevice_ip(request.getHeader("X-Forwarded-For"));
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		Utility.inputAccessLogDetails(accessLog, pDto, "");
		ResponseDTO response = AdminService.getInstance().getRecordsDetails(pDto);
		return response;
	}

	/**
	 * Method to get the report list for the given status without Time
	 * 
	 * @author GOWRI SANKAR R
	 * @param status
	 * @return
	 */
	@POST
	@Path("/getExcelDownloadLink")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO getExcelDownloadLink(@Context ContainerRequestContext requestContext, AdminDTO pDto) {
		accessLog.setDevice_ip(request.getHeader("X-Forwarded-For"));
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		Utility.inputAccessLogDetails(accessLog, pDto, pDto.getApplicationId() + "");
		ResponseDTO response = AdminService.getInstance().getExcelDownloadLink(pDto);
		return response;
	}

	//
	// @POST
	// @Path("/getExcelResult")
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	// public ResponseDTO getExcelResult(AdminDTO pDto) {
	// ResponseDTO response = AdminService.getInstance().getExcelResult(pDto);
	// return response;
	// }

	/**
	 * Method to get the Chart data from data base
	 * 
	 * @param pDto
	 * @return
	 */

	@POST
	@Path("/performanceChart")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO PerformanceChart(@Context ContainerRequestContext requestContext, PerformanceDTO pDto) {
		accessLog.setDevice_ip(request.getHeader("X-Forwarded-For"));
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		Utility.inputAccessLogDetails(accessLog, pDto, "");
		ResponseDTO response = AdminService.getInstance().PerformanceChart(pDto);
		return response;
	}

	/**
	 * Method to get the branch list from the data base
	 * 
	 * @author GOWRI SANKAR R
	 * @param requestContext
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/getBranchList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO getBranchList(@Context ContainerRequestContext requestContext, BranchListDTO pDto) {
		accessLog.setDevice_ip(request.getHeader("X-Forwarded-For"));
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		Utility.inputAccessLogDetails(accessLog, pDto, "");
		ResponseDTO response = AdminService.getInstance().getBranchList(pDto);
		return response;
	}

	/**
	 * method to get remishree list from data base
	 * 
	 * @author GOWRI SANKAR R
	 * @param requestContext
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/getRemishreeList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO getRemishreeList(@Context ContainerRequestContext requestContext, BranchListDTO pDto) {
		accessLog.setDevice_ip(request.getHeader("X-Forwarded-For"));
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		Utility.inputAccessLogDetails(accessLog, pDto, "");
		ResponseDTO response = AdminService.getInstance().getRemishreeList(pDto);
		return response;
	}

	/**
	 * Method to update the client code details into the data base for the given
	 * application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param requestContext
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/updateClientCodeDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO updateClientCodeDetails(@Context ContainerRequestContext requestContext, BranchListDTO pDto) {
		accessLog.setDevice_ip(request.getHeader("X-Forwarded-For"));
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		Utility.inputAccessLogDetails(accessLog, pDto, "");
		ResponseDTO response = AdminService.getInstance().updateClientCodeDetails(pDto);
		return response;
	}

	/***
	 * Method to Admin insert New Bank list
	 * 
	 * @author VICKY
	 * @param dto
	 * @return
	 */

	@POST
	@Path("/addminAddBank")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO addminAddBank(IfscCodeDTO dto) {
		ResponseDTO response = AdminService.getInstance().addminAddBank(dto);
		return response;
	}

	/***
	 * Method to Admin Update Email activation
	 * 
	 * @author VICKY
	 * @param dto
	 * @return
	 */

	@POST
	@Path("/updateEmail")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO updateEmail(AdminDTO dto) {
		ResponseDTO response = AdminService.getInstance().updateEmail(dto);
		return response;
	}

	/***
	 * Method to Admin Add New User
	 * 
	 * @author VICKY
	 * @param dto
	 * @return
	 */

	@POST
	@Path("/adminNewUserAdd")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO adminNewUserAdd(AdminDetailsDTO dto) {
		ResponseDTO response = AdminService.getInstance().adminNewUserAdd(dto);
		return response;
	}

	/***
	 * Method to get Admin List from data base
	 * 
	 * @author VICKY
	 * @param dto
	 * @return
	 */

	@POST
	@Path("/getAdminDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO getAdminDetails(AdminDetailsDTO pDto) {
		ResponseDTO response = AdminService.getInstance().getAdminDetails(pDto);
		return response;
	}

	/***
	 * 
	 * @param pDto
	 * @return
	 */

	@POST
	@Path("/deleteadminUser")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO deleteadminUser(AdminDetailsDTO pDto) {
		ResponseDTO response = AdminService.getInstance().deleteadminUser(pDto);
		return response;
	}

}
