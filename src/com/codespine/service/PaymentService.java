package com.codespine.service;

import java.util.HashMap;

import com.codespine.data.PaymentDAO;
import com.codespine.dto.ApplicationMasterDTO;
import com.codespine.dto.PaymentDto;
import com.codespine.dto.ResponseDTO;
import com.codespine.dto.eKYCDTO;
import com.codespine.helper.PaymentHelper;
import com.codespine.helper.eKYCHelper;
import com.codespine.util.StringUtil;
import com.codespine.util.eKYCConstant;
import com.razorpay.Order;

public class PaymentService {
	public static PaymentService paymentService = null;
	public static PaymentService getInstance() {
		if (paymentService == null) {
			paymentService = new PaymentService();
		}
		return paymentService;
	}
	
	/**
	 * method to create Payment in razerpay
	 * @author Pradeep Ravichandran
	 * @param PaymentDto
	 * @return
	 */
	public static ResponseDTO createPayment(PaymentDto dto) {
		ResponseDTO  responseDTO = new ResponseDTO();
		String receipt = Integer.toString(dto.getApplication_id());
		eKYCDTO eKYCDTO = null;
		if(StringUtil.isNotNullOrEmpty(receipt)) {
			PaymentDto paymentCriteria = new PaymentDto();
			paymentCriteria.setReceipt(receipt);
			PaymentDto oldPaymentDTO   = PaymentDAO.getInstance().getAllPaymentRecordsByCondition(paymentCriteria);
			if(oldPaymentDTO == null) {
				if( dto.getAmount() != 0 &&  dto.getAmount() > 0) {
					if(dto.getApplication_id() != 0 && dto.getApplication_id() > 0 ) {
						ApplicationMasterDTO applicationMasterDTO = new ApplicationMasterDTO();
						applicationMasterDTO.setApplication_id(dto.getApplication_id());
						applicationMasterDTO.setBankAccDtlRequired(true);
						eKYCDTO kycdto = new eKYCDTO();
						if (kycdto.getForPDFKeyValue() == null) {
							kycdto.setForPDFKeyValue(new HashMap<String, String>());
						}
						eKYCDTO = eKYCHelper.getInstance().populateRerquiredFields(applicationMasterDTO, kycdto);
					}
					if(eKYCDTO != null && eKYCDTO.getBankDetailsDTO() != null) {
						Order order = PaymentHelper.getInstance().createPayment(dto,eKYCDTO);
						if(order != null) {
							PaymentDto newPaymentDTO = PaymentHelper.populateRequiredFeilds(order,dto);
							responseDTO.setResult(newPaymentDTO);
							responseDTO.setStatus(eKYCConstant.SUCCESS_STATUS);
						}else {
							responseDTO.setStatus(eKYCConstant.FAILED_STATUS);
							responseDTO.setMessage("Payment Creation Failed Check Server!");
						}
					}else {
						responseDTO.setStatus(eKYCConstant.FAILED_STATUS);
						responseDTO.setMessage("order id is invalid");
					}
				}else {
					responseDTO.setStatus(eKYCConstant.FAILED_STATUS);
					responseDTO.setMessage("Amount is Zero");
				}
			}else {
				if(StringUtil.isEqual("completed", oldPaymentDTO.getStatus())) {
					responseDTO.setStatus(eKYCConstant.FAILED_STATUS);
					responseDTO.setMessage("Payment already Completed");
				}else if(StringUtil.isEqual("created", oldPaymentDTO.getStatus())) {
					responseDTO.setStatus(eKYCConstant.SUCCESS_STATUS);
					responseDTO.setMessage("Payment already Created");
					responseDTO.setResult(oldPaymentDTO);
				}else {
					responseDTO.setStatus(eKYCConstant.FAILED_STATUS);
					responseDTO.setMessage("Payment Creation Failed Check Server!");
				}
			}
		}else {
			responseDTO.setStatus(eKYCConstant.FAILED_STATUS);
			responseDTO.setMessage("Payment Creation Failed referId is null!");
		}
		return responseDTO;
	}
	/**
	 * method to verify Payment in razerpay
	 * @author Pradeep Ravichandran
	 * @param PaymentDto
	 * @return
	 */
	public static ResponseDTO verifyPayment(PaymentDto dto) {
		ResponseDTO  responseDTO = new ResponseDTO();
		boolean isEqual = PaymentHelper.getInstance().verifyPayment(dto);
		if(isEqual) {
			PaymentDto newPaymentDTO = PaymentHelper.populateRequiredForUpdate(dto);
			int status = PaymentDAO.getInstance().UpdatePaymentDetails(newPaymentDTO);
			if(status > 0 ) {
				responseDTO.setStatus(eKYCConstant.SUCCESS_STATUS);
				responseDTO.setMessage("Table Updated");
			}else {
				responseDTO.setStatus(eKYCConstant.FAILED_STATUS);
				responseDTO.setMessage("Table Not Updated");
			}
		}else {
			responseDTO.setStatus(eKYCConstant.FAILED_STATUS);
			responseDTO.setMessage("Verify Not Succeed");
		}
		return responseDTO;
	}
	
	/**
	 * Method to check checkPaymentStatus
	 * @author Pradeep Ravichandran
	 * @param dto
	 * @return
	 */
	public static ResponseDTO checkPaymentStatus(int receiptId) {
		ResponseDTO responseDTO = new ResponseDTO();
		if(receiptId != 0 && receiptId > 0) {
			PaymentDto paymentDTO = PaymentDAO.getInstance().checkPaymentStatus(receiptId);
			if(paymentDTO != null) {
				if(StringUtil.isEqual("completed", paymentDTO.getStatus())) {
					responseDTO.setStatus(eKYCConstant.SUCCESS_STATUS);
					responseDTO.setResult(paymentDTO);
					responseDTO.setMessage("Payment already Completed");
				}
			}else {
				responseDTO.setStatus(eKYCConstant.FAILED_STATUS);
				responseDTO.setMessage("Payment is not created");
			}
		}else {
			responseDTO.setStatus(eKYCConstant.FAILED_STATUS);
			responseDTO.setMessage("receiptId is null");
		}
		return responseDTO;
	}

}
