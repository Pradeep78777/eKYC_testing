package com.codespine.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codespine.dto.PersonalDetailsDTO;
import com.codespine.dto.ResponseDTO;
import com.codespine.service.eKYCService;

@Path("/backOffice")
public class BackOfficeController {
	@POST
	@Path("/pushDataToBackOffice")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO pushDataToBackOffice(PersonalDetailsDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		response = eKYCService.getInstance().newRegistration(pDto);
		return response;
	}

}
