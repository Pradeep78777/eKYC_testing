package com.codespine.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Writer;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
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

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONObject;

import com.codespine.data.eKYCDAO;
import com.codespine.dto.AccesslogDTO;
import com.codespine.dto.AddressDTO;
import com.codespine.dto.BankDetailsDTO;
import com.codespine.dto.ExchDetailsDTO;
import com.codespine.dto.IfscCodeDTO;
import com.codespine.dto.PanCardDetailsDTO;
import com.codespine.dto.PersonalDetailsDTO;
import com.codespine.dto.PostalCodesDTO;
import com.codespine.dto.ResponseDTO;
import com.codespine.dto.eKYCDTO;
import com.codespine.service.eKYCService;
import com.codespine.util.CSEnvVariables;
import com.codespine.util.FinalPDFGenerator;
import com.codespine.util.StringUtil;
import com.codespine.util.eKYCConstant;

@Path("/eKYC")
public class eKYCController {
	AccesslogDTO accessLog = new AccesslogDTO();
	String contentType = "content-type";
	@Context
	HttpServletRequest request;
	java.sql.Timestamp created_on = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());

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
	public ResponseDTO newRegistration(@Context ContainerRequestContext requestContext, PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);

		response = eKYCService.getInstance().newRegistration(pDto);
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
		String response = eKYCService.getInstance().verifyActivationLink(email, link);
		if (response.equals(eKYCConstant.SUCCESS_MSG)) {
			try {
				java.net.URI location = new java.net.URI(CSEnvVariables.getProperty(eKYCConstant.REDIRECT_PAGE));
				System.out.println("EKYC Controller line no 85 URL " + location);
				return Response.temporaryRedirect(location).build();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * To verify the email using activation Link
	 * 
	 * @param username
	 * @param key
	 * @return
	 */
	@POST
	@Path("/verifyEmail")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO verifyEmail(PersonalDetailsDTO pdto) {
		ResponseDTO response = new ResponseDTO();
		try {
			if (!pdto.getEmail().isEmpty() && !pdto.getKey().isEmpty() && !pdto.getEmail().equalsIgnoreCase("")
					&& !pdto.getKey().equalsIgnoreCase("")) {
				String dummyResponse = eKYCService.getInstance().verifyActivationLink(pdto.getEmail(), pdto.getKey());
				if (dummyResponse.equalsIgnoreCase(eKYCConstant.SUCCESS_MSG)) {
					int applicationId = eKYCDAO.getInstance().getApplicationId(pdto.getEmail(), pdto.getKey());
					JSONObject result = new JSONObject();
					result.put("application_id", applicationId);
					response.setStatus(eKYCConstant.SUCCESS_STATUS);
					response.setMessage(eKYCConstant.SUCCESS_MSG);
					response.setResult(result);
					return response;
				} else {
					response.setStatus(eKYCConstant.FAILED_STATUS);
					response.setMessage(eKYCConstant.FAILED_MSG);
				}
			} else {
				response.setStatus(eKYCConstant.FAILED_STATUS);
				response.setMessage(eKYCConstant.FAILED_MSG);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
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
	public ResponseDTO verifyOtp(@Context ContainerRequestContext requestContext, PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);

		response = eKYCService.getInstance().verifyOtp(pDto);
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
	public ResponseDTO verifyPan(@Context ContainerRequestContext requestContext, PanCardDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		// Utility.inputAccessLogDetails(accessLog, pDto,
		// pDto.getApplication_id() + "");

		response = eKYCService.getInstance().verifyPan(pDto);
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
	public ResponseDTO basicInformation(@Context ContainerRequestContext requestContext, PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		// Utility.inputAccessLogDetails(accessLog, pDto,
		// pDto.getApplication_id() + "");

		response = eKYCService.getInstance().basicInformation(pDto);
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
	public ResponseDTO updateAddress(@Context ContainerRequestContext requestContext, AddressDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		// Utility.inputAccessLogDetails(accessLog, pDto,
		// pDto.getApplication_id() + "");

		response = eKYCService.getInstance().updateAddress(pDto);
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
	public ResponseDTO updateCommunicationAddress(@Context ContainerRequestContext requestContext, AddressDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		// Utility.inputAccessLogDetails(accessLog, pDto,
		// pDto.getApplication_id() + "");

		response = eKYCService.getInstance().updateCommunicationAddress(pDto);
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
	public ResponseDTO updatePermanentAddress(@Context ContainerRequestContext requestContext, AddressDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		// Utility.inputAccessLogDetails(accessLog, pDto,
		// pDto.getApplication_id() + "");

		response = eKYCService.getInstance().updatePermanentAddress(pDto);
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
	public ResponseDTO updateBankAccountDetails(@Context ContainerRequestContext requestContext, BankDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		// Utility.inputAccessLogDetails(accessLog, pDto,
		// pDto.getApplication_id() + "");

		response = eKYCService.getInstance().updateBankAccountDetails(pDto);
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
	public ResponseDTO updateEmail(@Context ContainerRequestContext requestContext, PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		// Utility.inputAccessLogDetails(accessLog, pDto,
		// pDto.getApplication_id() + "");

		response = eKYCService.getInstance().updateEmail(pDto);
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
	public ResponseDTO deleteOldOne(@Context ContainerRequestContext requestContext, PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		// Utility.inputAccessLogDetails(accessLog, pDto,
		// pDto.getApplication_id() + "");

		response = eKYCService.getInstance().deleteOldOne(pDto);
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
	public ResponseDTO updateExchDetails(@Context ContainerRequestContext requestContext, ExchDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		// Utility.inputAccessLogDetails(accessLog, pDto,
		// pDto.getApplication_id() + "");

		response = eKYCService.getInstance().updateExchDetails(pDto);
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
	public ResponseDTO uploadProof(@Context ContainerRequestContext requestContext,
			@FormDataParam("proof") FormDataBodyPart proof, @FormDataParam("proofType") String proofType,
			@FormDataParam("applicationId") int applicationId, @FormDataParam("typeOfProof") String typeOfProof,
			@FormDataParam("pdfPassword") String pdfPassword) {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		// Utility.inputAccessLogDetails(accessLog, proofType, applicationId +
		// "");

		response = eKYCService.getInstance().uploadProof(proof, proofType, applicationId, typeOfProof, pdfPassword);
		return response;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getEsignXml")
	public ResponseDTO getXmlEncode(@Context ContainerRequestContext requestContext, PersonalDetailsDTO pDto)
			throws Exception {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		// accessLog.setDevice_ip(request.getRemoteAddr());
		// accessLog.setUser_agent(request.getHeader("user-agent"));
		// accessLog.setUri(requestContext.getUriInfo().getPath());
		// accessLog.setCreated_on(created_on);
		// Utility.inputAccessLogDetails(accessLog, pDto,
		// pDto.getApplication_id() + "");

		response = eKYCService.getInstance().getXmlEncode(pDto);
		return response;
	}

	@POST
	@Path("/finalPDFGenerator")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO finalPDFGenerator(@Context ContainerRequestContext requestContext, int applicationId)
			throws Exception {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);

		eKYCDTO eKYCdto = eKYCService.getInstance().finalPDFGenerator(applicationId);
		if (eKYCdto != null) {
			String eKYCPdfFileLocation = FinalPDFGenerator.pdfInserterRequiredValues(eKYCdto, null);
			if (StringUtil.isNotNullOrEmpty(eKYCPdfFileLocation)) {
				eKYCDAO.getInstance().insertAttachementDetails(eKYCPdfFileLocation, eKYCConstant.EKYC_DOCUMENT,
						applicationId, "");
			}
			response.setStatus(eKYCConstant.SUCCESS_STATUS);
			response.setMessage(eKYCConstant.SUCCESS_MSG);
			response.setReason(eKYCConstant.PDF_GENERATED_SUCESSFULLY);
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.PDF_GENERATED_FAILED);
		}
		return response;
	}

	/**
	 * TO get the Link to Download the Document
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getDocumentLink")
	public ResponseDTO getDocumentLink(@Context ContainerRequestContext requestContext, PersonalDetailsDTO pDto) {
		ResponseDTO repsonse = eKYCService.getInstance().getDocumentLink(pDto);
		return repsonse;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getEsignedDocument")
	public ResponseDTO getEsignedDocument(@Context ContainerRequestContext requestContext, PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		// Utility.inputAccessLogDetails(accessLog, pDto,
		// pDto.getApplication_id() + "");

		response = eKYCService.getInstance().getEsignedDocument(pDto);
		return response;
	}

	/**
	 * To get the Pan Card details
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getPanCardDetails")
	public ResponseDTO getPanCardDetails(@Context ContainerRequestContext requestContext, PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		// Utility.inputAccessLogDetails(accessLog, pDto,
		// pDto.getApplication_id() + "");

		response = eKYCService.getInstance().getPanCardDetails(pDto);
		return response;
	}

	/**
	 * To get the Basic information
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getBasicInformation")
	public ResponseDTO getBasicInformation(@Context ContainerRequestContext requestContext, PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		// Utility.inputAccessLogDetails(accessLog, pDto,
		// pDto.getApplication_id() + "");

		response = eKYCService.getInstance().getBasicInformation(pDto);
		return response;
	}

	/**
	 * To get the Communication Address
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getCommunicationAddress")
	public ResponseDTO getCommunicationAddress(@Context ContainerRequestContext requestContext,
			PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		// Utility.inputAccessLogDetails(accessLog, pDto,
		// pDto.getApplication_id() + "");

		response = eKYCService.getInstance().getCommunicationAddress(pDto);
		return response;
	}

	/**
	 * To get the Permanent Address
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getPermanentAddress")
	public ResponseDTO getPermanentAddress(@Context ContainerRequestContext requestContext, PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		// Utility.inputAccessLogDetails(accessLog, pDto,
		// pDto.getApplication_id() + "");

		response = eKYCService.getInstance().getPermanentAddress(pDto);
		return response;
	}

	/**
	 * 
	 * @author GOWRI SANKAR R
	 * @param pDto
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getBankDetails")
	public ResponseDTO getBankDetails(@Context ContainerRequestContext requestContext, PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		// Utility.inputAccessLogDetails(accessLog, pDto,
		// pDto.getApplication_id() + "");

		response = eKYCService.getInstance().getBankDetails(pDto);
		return response;
	}

	/**
	 * 
	 * @param pDto
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getExchDetails")
	public ResponseDTO getExchDetails(@Context ContainerRequestContext requestContext, PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		// Utility.inputAccessLogDetails(accessLog, pDto,
		// pDto.getApplication_id() + "");

		response = eKYCService.getInstance().getExchDetails(pDto);
		return response;
	}

	/**
	 * 
	 * @param pDto
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getUploadedFile")
	public ResponseDTO getUploadedFile(@Context ContainerRequestContext requestContext, PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		// Utility.inputAccessLogDetails(accessLog, pDto,
		// pDto.getApplication_id() + "");

		response = eKYCService.getInstance().getUploadedFile(pDto);
		return response;
	}

	/**
	 * Method to get the IVR details for the given Application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param requestContext
	 * @param pDto
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getIvrDetails")
	public ResponseDTO getIvrDetails(@Context ContainerRequestContext requestContext, PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		// Utility.inputAccessLogDetails(accessLog, pDto,
		// pDto.getApplication_id() + "");

		response = eKYCService.getInstance().getIvrDetails(pDto);
		return response;
	}

	/**
	 * To upload the ivr capture for given application id
	 * 
	 * @author GOWRI SANKAR R
	 * @param ivrImage
	 * @param ivrLat
	 * @param ivrLong
	 * @param applicationId
	 * @return
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/uploadIvrCapture")
	public ResponseDTO uploadIvrCapture(@Context ContainerRequestContext requestContext,
			@FormDataParam("ivrImage") String ivrImage, @FormDataParam("ivrLat") String ivrLat,
			@FormDataParam("ivrLong") String ivrLong, @FormDataParam("applicationId") int applicationId) {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);

		response = eKYCService.getInstance().uploadIvrCapture(ivrImage, ivrLat, ivrLong, applicationId,
				request.getHeader("X-Forwarded-For"), request.getHeader("user-agent"));
		return response;
	}

	/**
	 * @author GOWRI SANKAR R
	 * @param requestContext
	 * @param ivrImage
	 * @param ivrLat
	 * @param ivrLong
	 * @param applicationId
	 * @return
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/uploadIvrMobile")
	public ResponseDTO uploadIvrMobile(@Context ContainerRequestContext requestContext,
			@FormDataParam("ivrImage") String ivrImage, @FormDataParam("ivrLat") String ivrLat,
			@FormDataParam("ivrLong") String ivrLong, @FormDataParam("applicationId") int applicationId,
			@FormDataParam("randomKey") String randomKey) {
		ResponseDTO response = new ResponseDTO();
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		if (randomKey != null && !randomKey.isEmpty() && !randomKey.equalsIgnoreCase("")) {
			response = eKYCService.getInstance().uploadIvrMobile(ivrImage, ivrLat, ivrLong, applicationId,
					request.getHeader("X-Forwarded-For"), request.getHeader("user-agent"), randomKey);
		} else {
			response.setStatus(eKYCConstant.FAILED_STATUS);
			response.setMessage(eKYCConstant.FAILED_MSG);
			response.setReason(eKYCConstant.INVALID_RANDOM_KEY);
		}
		return response;
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getEsignSignature")
	public String checkIP(@Context ContainerRequestContext requestContext, String sampleBase64) {
		String response = "";
		/*
		 * TO insert Access log into data base
		 */
		accessLog.setDevice_ip(request.getRemoteAddr());
		accessLog.setUser_agent(request.getHeader("user-agent"));
		accessLog.setUri(requestContext.getUriInfo().getPath());
		accessLog.setCreated_on(created_on);
		// Utility.inputAccessLogDetails(accessLog, sampleBase64, "");

		// String randomString = Utility.randomAlphaNumericNew(256);
		// long currentTime = System.currentTimeMillis();
		return response;
	}

	/**
	 * To resend otp to given mobile number
	 * 
	 * @author GOWRI SANKAR R
	 * @param requestContext
	 * @param pDto
	 * @return
	 */
	@POST
	@Path("/resendOtp")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO resendOTP(@Context ContainerRequestContext requestContext, PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		response = eKYCService.getInstance().resendOTP(pDto);
		return response;
	}

	/**
	 * Method to get search results for given postal codes
	 * 
	 * @author GOWRI SANKAR R
	 * @param dto
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getPostalCode")
	public ResponseDTO getPostalCode(PostalCodesDTO dto) {
		ResponseDTO response = eKYCService.getInstance().getPostalCode(dto);
		return response;
	}

	/**
	 * Method to get the IFSC Code details for the given IFSC Code
	 * 
	 * @author GOWRI SANKAR R
	 * @param dto
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getIfscCode")
	public ResponseDTO getIfscCode(IfscCodeDTO dto) {
		ResponseDTO response = eKYCService.getInstance().getIfscCode(dto);
		return response;
	}

	/**
	 * method to get the unique bank name for find the IFSC Code
	 * 
	 * @author GOWRI SANKAR R
	 * @param dto
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getUniqueBankName")
	public ResponseDTO getUniqueBankName(IfscCodeDTO dto) {
		ResponseDTO response = eKYCService.getInstance().getUniqueBankName(dto);
		return response;
	}

	/**
	 * Method to get the unique place by bank name
	 * 
	 * @author GOWRI SANKAR R
	 * @param dto
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getUniquePlaceByBankName")
	public ResponseDTO getUniquePlaceByBankName(IfscCodeDTO dto) {
		ResponseDTO response = eKYCService.getInstance().getUniquePlaceName(dto);
		return response;
	}

	/**
	 * Method to get the IFSC code
	 * 
	 * @author GOWRI SANKAR R
	 * @param dto
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getBankDetailsByPlace")
	public ResponseDTO getBankDetailsByPlace(IfscCodeDTO dto) {
		ResponseDTO response = eKYCService.getInstance().getBankDetailsByPlace(dto);
		return response;
	}

	/**
	 * Method to check the given file is password protected or not
	 * 
	 * @author GOWRI SANKAR R
	 * @param requestContext
	 * @param proof
	 * @return
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/checkPasswordProtected")
	public ResponseDTO checkPasswordProtected(@Context ContainerRequestContext requestContext,
			@FormDataParam("proof") FormDataBodyPart proof) {
		ResponseDTO response = new ResponseDTO();
		response = eKYCService.getInstance().checkPasswordProtected(proof);
		return response;
	}

	// @POST
	// @Consumes(MediaType.MULTIPART_FORM_DATA)
	// @Produces(MediaType.TEXT_HTML)
	// @Path("/getNsdlXML")
	// public InputStream getNsdlXML(@FormDataParam("msg") String msg) {
	// try {
	// String response = eKYCService.getInstance().getNsdlXML(msg);
	// if (response != null && !response.isEmpty() &&
	// !response.equalsIgnoreCase("")) {
	// String signedPdfUrl = "function openWin() { window.open('" + response +
	// "' ,'_self' );}";
	// String htmlUrl =
	// CSEnvVariables.getMethodNames(eKYCConstant.HTML_STARTING) + signedPdfUrl
	// + CSEnvVariables.getMethodNames(eKYCConstant.HTML_ENDING);
	// File testFile = new File("signed_pdf.html");
	// Writer writer = new BufferedWriter(new FileWriter(testFile));
	// writer.write(htmlUrl);
	// writer.close();
	// return new FileInputStream(testFile);
	// } else {
	// String responseUrl = "<!DOCTYPE html><html><head><title>Zebull
	// E-KYC</title><style>.myDiv { text-align: center; position: absolute;
	// left: 40%; top: 50%;}</style></head><body><div class='myDiv'> <h1> Sorry
	// something went wrong</h1></div></body></html>";
	// File testFile = new File("signed_pdf.html");
	// Writer writer = new BufferedWriter(new FileWriter(testFile));
	// writer.write(responseUrl);
	// writer.close();
	// return new FileInputStream(testFile);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_HTML)
	@Path("/getNsdlXML")
	public Response getNsdlXML(@FormDataParam("msg") String msg) {
		try {
			Response response = eKYCService.getInstance().getNsdlXML(msg);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/htmlCheck")
	public InputStream htmlCheck() {
		try {
			String tempStarting = "<!DOCTYPE html><html><head><title>Zebull E-KYC</title><style>.myDiv {text-align: center;position: absolute;left: 45%; top: 50%;}.colorClass{background: #007aff;border: 2px solid #007aff; border-radius: 3px;  color: #fff; font-size: 16px;width: 100%; font-weight: 600;height: 40px;}</style><script>";
			String mainString = "function openWin() {  window.open('https://oa1.zebull.in//e_sign/file//uploads//1//PANCARD//motivational-10.jpg' ,'_self' );}";
			String endStrign = "</script></head><body><div class='myDiv'> <input type='button' class='colorClass' value='Download E-sign Document' onclick='openWin()'></div></body></html>";
			String mainText = tempStarting + mainString + endStrign;
			File testFile = new File("signed_pdf.html");
			Writer writer = new BufferedWriter(new FileWriter(testFile));
			writer.write(mainText);
			writer.close();
			return new FileInputStream(testFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
