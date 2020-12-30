package com.codespine.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codespine.dto.BackOfficeDTO;
import com.codespine.dto.ResponseDTO;
import com.codespine.service.BackOfficeService;

@Path("/backOffice")
public class BackOfficeController {
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
	public ResponseDTO pushDataToBackOffice(BackOfficeDTO pDto) {
		ResponseDTO response = new ResponseDTO();
		response = BackOfficeService.getInstance().pushDataToBackOffice(pDto.getApplicationId(), pDto.getBranchCode(),
				pDto.getClientCode(), pDto.getVerifiedBy(), pDto.getVerifiedByDesigination());
		return response;
	}

}
