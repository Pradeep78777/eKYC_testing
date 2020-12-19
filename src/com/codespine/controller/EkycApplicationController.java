package com.codespine.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.codespine.dto.PersonalDetailsDTO;
import com.codespine.dto.ResponseDTO;
import com.codespine.service.EkycApplicationService;

@Path("/eKYCapplication")
public class EkycApplicationController {

	/**
	 * Method to resume the application
	 * 
	 * @author GOWRI SANKAR R
	 * @param requestContext
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/resumeApplication")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO resumeApplication(@Context ContainerRequestContext requestContext, PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		response = EkycApplicationService.getInstance().resumeApplication(pDto);
		return response;
	}

	/**
	 * Method to verify the otp for the resume application
	 * 
	 * @author GOWRI SANKAR R
	 * @param requestContext
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/verifyOtpToResumeApplication")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO verifyOtpToResumeApplication(@Context ContainerRequestContext requestContext,
			PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		response = EkycApplicationService.getInstance().verifyOtpToResumeApplication(pDto);
		return response;
	}

	/**
	 * Get the application details for the given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param requestContext
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/getApplicationForUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO getApplicationForUser(@Context ContainerRequestContext requestContext, PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		response = EkycApplicationService.getInstance().getApplicationForUser(pDto);
		return response;
	}

}
