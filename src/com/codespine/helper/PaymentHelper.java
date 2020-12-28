package com.codespine.helper;

import org.json.JSONObject;
import com.codespine.data.PaymentDAO;
import com.codespine.dto.PaymentDto;
import com.codespine.dto.eKYCDTO;
import com.codespine.util.CSEnvVariables;
import com.codespine.util.StringUtil;
import com.codespine.util.eKYCConstant;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

public class PaymentHelper {
	public static PaymentHelper paymentHelper = null;
	public static PaymentHelper getInstance() {
		if (paymentHelper == null) {
			paymentHelper = new PaymentHelper();
		}
		return paymentHelper;
	}
	String Key = CSEnvVariables.getProperty(eKYCConstant.CONST_PAYMENT_KEY);
	String secret = CSEnvVariables.getProperty(eKYCConstant.CONST_PAYMENT_SECRET);
	public Order createPayment(PaymentDto dto, eKYCDTO eKYCDTO) {
		Order order = null;
		JSONObject bankJSON = new JSONObject();
		if(StringUtil.isNotNullOrEmpty(eKYCDTO.getBankDetailsDTO().getBank_account_no())) {
			bankJSON.put(eKYCConstant.CONST_BANK_ACCOUNT_NUMBER, eKYCDTO.getBankDetailsDTO().getBank_account_no());
		}
		if(StringUtil.isNotNullOrEmpty(eKYCDTO.getBankDetailsDTO().getAccount_holder_name())) {
			bankJSON.put(eKYCConstant.CONST_BANK_NAME, eKYCDTO.getBankDetailsDTO().getAccount_holder_name());
		}
		if(StringUtil.isNotNullOrEmpty(eKYCDTO.getBankDetailsDTO().getIfsc_code())) {
			bankJSON.put(eKYCConstant.CONST_BANK_IFSC, eKYCDTO.getBankDetailsDTO().getIfsc_code());
		}
		try {
			RazorpayClient razorpay = new RazorpayClient(Key, secret);
			JSONObject orderRequest = new JSONObject();
			orderRequest.put(eKYCConstant.AMOUNT, dto.getAmount()*100); 
			orderRequest.put(eKYCConstant.METHOD, eKYCConstant.BANKING_VIA);
			orderRequest.put(eKYCConstant.CURRENCY, eKYCConstant.RAZORPAY_CURRENCY_INR);
			orderRequest.put(eKYCConstant.RECEIPT, Integer.toString(dto.getApplication_id()));
			orderRequest.put(eKYCConstant.CONST_BANK_ACCOUNT,bankJSON);
			System.out.println(orderRequest);
			order = razorpay.Orders.create(orderRequest);
			System.out.println(order);
		} catch (RazorpayException e) {
			e.printStackTrace();
		}
		return order;
	}
	/**
	 * Method to insert PaymentDto
	 * @author Pradeep Ravichandran
	 * @param order
	 * @param dto 
	 * @return
	 */
	public static PaymentDto populateRequiredFeilds(Order order, PaymentDto paymentDto) {
//		OrdersDto orders = OrdersDAO.getInstance().getOrderDetails(Integer.parseInt(paymentDto.getReceipt()));
		PaymentDto dto = new PaymentDto();
		dto.setPayment_id(order.get("id"));
		int amount = order.get("amount");
		dto.setAmount(amount/100);
		dto.setCurrency(order.get("currency"));
		dto.setReceipt(order.get("receipt"));
		dto.setAmount_paid(order.get("amount_paid"));
//		dto.setCreated_at(order.get("created_at"));
		int amountDue = order.get("amount_due");
		dto.setAmount_due(amountDue/100);
		dto.setEntity(order.get("entity"));
//		dto.setOffer_id(order.get("offer_id"));
		dto.setStatus(order.get("status"));
		dto.setAttempts(order.get("attempts"));
		dto.setRefer_id(paymentDto.getReceipt());
//		dto.setPostedBy(Integer.parseInt(paymentDto.getReceipt()));
//		dto.setPostedTo(Integer.parseInt(paymentDto.getReceipt()));
		PaymentDAO.getInstance().insertPaymentDetails(dto);
		return dto;
	}
	/**
	 * method to verify Payment in razerpay
	 * @author Pradeep Ravichandran
	 * @param PaymentDto
	 * @return
	 */
	public boolean verifyPayment(PaymentDto dto) {
		boolean isEqual = false;
		JSONObject orderRequest = new JSONObject();
		orderRequest.put(eKYCConstant.AMOUNT, dto.getAmount()); 
		orderRequest.put(eKYCConstant.CURRENCY, eKYCConstant.RAZORPAY_CURRENCY_INR);
		orderRequest.put(eKYCConstant.RECEIPT, dto.getReceipt());
		orderRequest.put(eKYCConstant.RAZORPAY_ORDERID, dto.getRazorpay_order_id());
		orderRequest.put(eKYCConstant.RAZORPAY_PAYMENTID, dto.getRazorpay_payment_id());
		orderRequest.put(eKYCConstant.RAZORPAY_SIGNATURE,dto.getRazorpay_signature());
		try {
//			RazorpayClient razorpay = new RazorpayClient(Key, secret);
//			Order order1 = razorpay.Orders.fetch(dto.getPayment_id());
			isEqual = Utils.verifyPaymentSignature(orderRequest, secret);
		} catch (RazorpayException e) {
			e.printStackTrace();
		}
		return isEqual;
	}
	public static PaymentDto populateRequiredForUpdate(PaymentDto paymentDto) {
		PaymentDto dto =  PaymentDAO.getInstance().getAllPaymentRecordsByCondition(paymentDto);
		dto.setRazorpay_order_id(paymentDto.getRazorpay_order_id());
		dto.setRazorpay_payment_id(paymentDto.getRazorpay_payment_id());
		dto.setRazorpay_signature(paymentDto.getRazorpay_signature());
		dto.setStatus("completed");
		return dto;
	}
}
