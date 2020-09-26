package com.codespine.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codespine.dto.BankDetailsDTO;
import com.codespine.dto.PanCardDetailsDTO;
import com.codespine.dto.PersonalDetailsDTO;
import com.codespine.dto.ResponseDTO;
import com.codespine.service.eKYCService;

@Path("/eKYC")
public class eKYCController {
	eKYCService pService = new eKYCService();

	/**
	 * Method insert the personal Details for the application
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/personalDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO personalDetails(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		response = pService.personalDetails(pDto);
		return response;
	}

	/**
	 * Method to verify the otp For the registered USer
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/verifyOtp")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO verifyOtp(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		response = pService.verifyOtp(pDto);
		return response;
	}

	/**
	 * methos to insert pan card Details
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/panCardDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO panCardDetails(PanCardDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		response = pService.panCardDetails(pDto);
		return response;
	}

	/**
	 * methos to insert pan card Details
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/personalInfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO insertPersonalDetails(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		response = pService.insertPersonalInfoDetails(pDto);
		return response;
	}

	/**
	 * methos to insert pan card Details
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/bankDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO bankDetails(BankDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		response = pService.bankDetails(pDto);
		return response;
	}

	/**
	 * Method to send otp to the Mobile to Continue the document upload
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/sendOTP")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO sendOTP(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		response = pService.sendOTP(pDto);
		return response;
	}

}
