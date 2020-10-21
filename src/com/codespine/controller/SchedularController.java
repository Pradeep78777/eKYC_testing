package com.codespine.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codespine.dto.ResponseDTO;
import com.codespine.service.SchedularService;

@Path("/schedular")
public class SchedularController {
	public static SchedularController SchedularController = null;

	public static SchedularController getInstance() {
		if (SchedularController == null) {
			SchedularController = new SchedularController();
		}
		return SchedularController;
	}

	/**
	 * update the postal codes
	 * 
	 * @author GOWRI SANKAR R
	 * @return
	 */
	@POST
	@Path("/updatePostalCodes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO updatePostalCodes() {
		ResponseDTO response = new ResponseDTO();
		response = SchedularService.getInstance().updatePostalCodes();
		return response;
	}
}
