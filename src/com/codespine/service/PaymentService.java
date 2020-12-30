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
							responseDTO.setMessage(eKYCConstant.PAYMENT_CREATION_FAILED);
						}
					}else {
						responseDTO.setStatus(eKYCConstant.FAILED_STATUS);
						responseDTO.setMessage(eKYCConstant.ORDERID_INVALID);
					}
				}else {
					responseDTO.setStatus(eKYCConstant.FAILED_STATUS);
					responseDTO.setMessage(eKYCConstant.AMOUNT_ZERO);
				}
			}else {
				if(StringUtil.isEqual(eKYCConstant.CONST_COMPLETED, oldPaymentDTO.getStatus())) {
					responseDTO.setStatus(eKYCConstant.FAILED_STATUS);
					responseDTO.setMessage(eKYCConstant.PAYMENT_ALREADY_COMPLETED);
				}else if(StringUtil.isEqual(eKYCConstant.CONST_CREATED, oldPaymentDTO.getStatus())) {
					responseDTO.setStatus(eKYCConstant.SUCCESS_STATUS);
					responseDTO.setMessage(eKYCConstant.PAYMENT_ALREADY_CREATED);
					responseDTO.setResult(oldPaymentDTO);
				}else {
					responseDTO.setStatus(eKYCConstant.FAILED_STATUS);
					responseDTO.setMessage(eKYCConstant.PAYMENT_CREATION_FAILED);
				}
			}
		}else {
			responseDTO.setStatus(eKYCConstant.FAILED_STATUS);
			responseDTO.setMessage(eKYCConstant.PAYMENT_FAILED_ID_NULL);
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
		if(StringUtil.isNotNullOrEmpty(dto.getRazorpay_order_id())
				&& StringUtil.isNotNullOrEmpty(dto.getRazorpay_payment_id()) 
				&& StringUtil.isNotNullOrEmpty(dto.getRazorpay_signature())) {
			boolean isEqual = PaymentHelper.getInstance().verifyPayment(dto);
			if(isEqual) {
				PaymentDto newPaymentDTO = PaymentHelper.populateRequiredForUpdate(dto);
				int status = PaymentDAO.getInstance().UpdatePaymentDetails(newPaymentDTO);
				if(status > 0 ) {
					responseDTO.setStatus(eKYCConstant.SUCCESS_STATUS);
					responseDTO.setMessage(eKYCConstant.TABLE_UPDATED);
				}else {
					responseDTO.setStatus(eKYCConstant.FAILED_STATUS);
					responseDTO.setMessage(eKYCConstant.TABLE_NOT_UPDATED);
				}
			}else {
				responseDTO.setStatus(eKYCConstant.FAILED_STATUS);
				responseDTO.setMessage(eKYCConstant.VERIFY_NOT_SUCCEED);
			}
		}else {
			responseDTO.setStatus(eKYCConstant.FAILED_STATUS);
			responseDTO.setMessage(eKYCConstant.RAZORPAY_VALUES_ARE_NULL);
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
				if(StringUtil.isEqual(eKYCConstant.CONST_COMPLETED, paymentDTO.getStatus())) {
					responseDTO.setStatus(eKYCConstant.SUCCESS_STATUS);
					responseDTO.setResult(paymentDTO);
					responseDTO.setMessage(eKYCConstant.PAYMENT_ALREADY_COMPLETED);
				}
			}else {
				responseDTO.setStatus(eKYCConstant.FAILED_STATUS);
				responseDTO.setMessage(eKYCConstant.PAYMENT_NOT_CREATED);
			}
		}else {
			responseDTO.setStatus(eKYCConstant.FAILED_STATUS);
			responseDTO.setMessage(eKYCConstant.APPLICATION_ID_NULL);
		}
		return responseDTO;
	}

}
