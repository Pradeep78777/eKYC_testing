package com.codespine.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import com.codespine.data.EkycApplicationDAO;
import com.codespine.dto.PersonalDetailsDTO;
import com.codespine.dto.ResponseDTO;
import com.codespine.service.EkycApplicationService;
import com.codespine.util.CSEnvVariables;
import com.codespine.util.eKYCConstant;

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

	/**
	 * Method to generate the ipv link and sent it through the mobile and email
	 * 
	 * @author GOWRI SANKAR R
	 * @param dto
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getIPVlink")
	public ResponseDTO getIPVlink(PersonalDetailsDTO dto) {
		ResponseDTO response = EkycApplicationService.getInstance().getIPVlink(dto);
		return response;
	}

	/**
	 * Method to check the random key
	 * 
	 * @author GOWRI SANKAR R
	 * @param randomKey
	 * @param applicationId
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/checkIvrRandom")
	public Response checkIvrRandom(@QueryParam("randomKey") String randomKey,
			@QueryParam("applicationId") int applicationId) {
		/**
		 * Check the random key is there are not
		 */
		JSONObject tempJson = EkycApplicationDAO.getInstance().getIVRMasterDetails(randomKey, applicationId);
		try {
			if (tempJson != null) {
				/**
				 * Get the current time and link expiry time
				 */
				Calendar cal = Calendar.getInstance();
				String expiry = (String) tempJson.get("expiry_date");
				Date expiryDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expiry);
				long expiryDateMillesconds = expiryDate.getTime();
				long currentTime = cal.getTimeInMillis();
				/**
				 * check the link is expired or not
				 */
				if (expiryDateMillesconds < currentTime) {
					/**
					 * if not redirect to ipv page
					 */
					java.net.URI location = new java.net.URI(
							CSEnvVariables.getMethodNames(eKYCConstant.IPV_SUCCESS_URL));
					System.out.println("EKYC Application Controller line no 137 URL " + location);
					return Response.temporaryRedirect(location).build();
				} else {
					/**
					 * if not redirect to timeout page
					 */
					java.net.URI location = new java.net.URI(
							CSEnvVariables.getMethodNames(eKYCConstant.REQUEST_TIMEOUT));
					System.out.println("EKYC Application Controller line no 144 URL " + location);
					return Response.temporaryRedirect(location).build();
				}
			} else {
				/**
				 * if empty redirect to Login Page
				 */
				java.net.URI location = new java.net.URI(CSEnvVariables.getMethodNames(eKYCConstant.REGIDTRATION_URL));
				System.out.println("EKYC Application Controller line no 152 URL " + location);
				return Response.temporaryRedirect(location).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
