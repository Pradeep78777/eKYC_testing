package com.codespine.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codespine.dto.PaymentDto;
import com.codespine.dto.ResponseDTO;
import com.codespine.service.PaymentService;

@Path("/payment")
public class PaymentController {
	
	/**
	 * method to create Payment in razerpay
	 * @author Pradeep Ravichandran
	 * @param PaymentDto
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/createPayment")
	public ResponseDTO createPayment(PaymentDto dto) {
		ResponseDTO response = PaymentService.createPayment(dto);
		return response;
	}
	
	/**
	 * method to verify Payment in razerpay
	 * @author Pradeep Ravichandran
	 * @param PaymentDto
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/verifyPayment")
	public ResponseDTO verifyPayment(PaymentDto dto) {
		ResponseDTO response = PaymentService.verifyPayment(dto);
		return response;
	}
	/**
	 * Method to check checkPaymentStatus
	 * @author Pradeep Ravichandran
	 * @param dto
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/checkPayment")
	public ResponseDTO checkPaymentStatus(int receiptId) {
		ResponseDTO response = PaymentService.checkPaymentStatus(receiptId);
		return response;
	}
}
