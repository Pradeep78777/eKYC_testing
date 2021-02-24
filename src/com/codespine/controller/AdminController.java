package com.codespine.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codespine.dto.AdminDTO;
import com.codespine.dto.ResponseDTO;
import com.codespine.service.AdminService;

@Path("/admin")
public class AdminController {
	public static AdminController AdminController = null;

	public static AdminController getInstance() {
		if (AdminController == null) {
			AdminController = new AdminController();
		}
		return AdminController;
	}

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
	public ResponseDTO adminLogin(AdminDTO pDto) {
		ResponseDTO response = new ResponseDTO();
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
	public ResponseDTO getAllUserRecords() {
		ResponseDTO response = new ResponseDTO();
		response = AdminService.getInstance().getAllUserRecords();
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
	public ResponseDTO respondPanCard(AdminDTO pDto) {
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
	public ResponseDTO respondPersonalDetails(AdminDTO pDto) {
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
	public ResponseDTO respondBankAccountDetails(AdminDTO pDto) {
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
	public ResponseDTO respondCommunicationAddress(AdminDTO pDto) {
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
	public ResponseDTO respondPermanentAddress(AdminDTO pDto) {
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
	public ResponseDTO respondAttachementDetails(AdminDTO pDto) {
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
	public ResponseDTO getAttachedFileDetails(AdminDTO pDto) {
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
	public ResponseDTO startApplication(AdminDTO pDto) {
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
	public ResponseDTO endApplication(AdminDTO pDto) {
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
	public ResponseDTO getRejectedDocuments(AdminDTO pDto) {
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
	public ResponseDTO getApplicationStatus(AdminDTO pDto) {
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
	public ResponseDTO getPendingRecords(AdminDTO pDto) {
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
	public ResponseDTO getCompletedRecords() {
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
	public ResponseDTO getRecordsDetails(AdminDTO pDto) {
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
	public ResponseDTO getExcelDownloadLink(AdminDTO pDto) {
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
}
