package com.codespine.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.codespine.dto.AddressDTO;
import com.codespine.dto.BankDetailsDTO;
import com.codespine.dto.ExchDetailsDTO;
import com.codespine.dto.PanCardDetailsDTO;
import com.codespine.dto.PersonalDetailsDTO;
import com.codespine.dto.ResponseDTO;
import com.codespine.service.eKYCService;
import com.codespine.util.CSEnvVariables;
import com.codespine.util.eKYCConstant;

@Path("/eKYC")
public class eKYCController {
	eKYCService pService = new eKYCService();

	/**
	 * To check the given mobile and email, proceed to next step
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/newRegistration")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO newRegistration(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		response = pService.newRegistration(pDto);
		return response;
	}

	/**
	 * To verify the email using activation Link
	 * 
	 * @param username
	 * @param key
	 * @return
	 */
	@Path("/verifyActivationLink")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response verifyActivationLink(@QueryParam("email") String email, @QueryParam("key") String link) {
		String response = pService.verifyActivationLink(email, link);
		if (response.equals(eKYCConstant.SUCCESS_MSG)) {
			try {
				java.net.URI location = new java.net.URI(CSEnvVariables.getProperty(eKYCConstant.REDIRECT_PAGE));
				return Response.temporaryRedirect(location).build();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * To verify the otp for user
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
	 * To verify the pan card details
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/verifyPan")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO verifyPan(PanCardDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		response = pService.verifyPan(pDto);
		return response;
	}

	/**
	 * To save the basic information for the user by application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/basicInformation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO basicInformation(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		response = pService.basicInformation(pDto);
		return response;
	}

	/**
	 * To update the address for the user
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/updateAddress")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO updateAddress(AddressDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		response = pService.updateAddress(pDto);
		return response;
	}

	/**
	 * To update the Communication Address
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/updateCommunicationAddress")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO updateCommunicationAddress(AddressDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		response = pService.updateCommunicationAddress(pDto);
		return response;
	}

	/**
	 * To update the Permanent Address
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/updatePermanentAddress")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO updatePermanentAddress(AddressDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		response = pService.updatePermanentAddress(pDto);
		return response;
	}

	/**
	 * To update the bank Account Details
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/updateBankAccountDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO updateBankAccountDetails(BankDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		response = pService.updateBankAccountDetails(pDto);
		return response;
	}

	/**
	 * To update the email for the given Mobile number
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/updateEmail")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO updateEmail(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		response = pService.updateEmail(pDto);
		return response;
	}

	/**
	 * To mark the application as deleted
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/deleteOldOne")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO deleteOldOne(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		response = pService.deleteOldOne(pDto);
		return response;
	}

	/**
	 * To update the exchange details for the application id
	 * 
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/updateExchDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO updateExchDetails(ExchDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		response = pService.updateExchDetails(pDto);
		return response;
	}

	/**
	 * To upload the file into the data base
	 * 
	 * @author GOWRI SANKAR R
	 * @param proof
	 * @return
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/fileUpload")
	public ResponseDTO uploadProof(@FormDataParam("proof") FormDataBodyPart proof,
			@FormDataParam("proofType") String proofType, @FormDataParam("applicationId") int applicationId) {
		ResponseDTO response = new ResponseDTO();
		response = pService.uploadProof(proof, proofType, applicationId);
		return response;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getEsignXml")
	public ResponseDTO getXmlEncode(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		response = pService.getXmlEncode(pDto);
		return response;
	}
}
