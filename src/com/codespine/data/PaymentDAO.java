package com.codespine.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.codespine.dto.PaymentDto;
import com.codespine.util.DBUtil;
import com.codespine.util.StringUtil;

public class PaymentDAO {
	public static PaymentDAO paymentDAO = null;
	public static PaymentDAO getInstance() {
		if (paymentDAO == null) {
			paymentDAO = new PaymentDAO();
		}
		return paymentDAO;
	}
	private Connection conn = null;
	/**
	 * Method to insert Payment Details
	 * @author Pradeep Ravichandran
	 * @param PaymentDto
	 * @return
	 */
	public int insertPaymentDetails(PaymentDto dto) {
		int updateCount = 0;
		PreparedStatement pStmt = null;
		ResultSet rSet = null;
		int paymentId = 0;
		try {
			conn = DBUtil.getConnection();
			pStmt = conn.prepareStatement(
					"INSERT INTO tbl_payment(payment_id,amount,currency,receipt,razorpay_signature,razorpay_order_id,razorpay_payment_id,amount_paid,amount_due,entity,status,attempts,refer_id) "
							+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			int paramPos = 1;
			pStmt.setString(paramPos++, dto.getPayment_id());
			pStmt.setInt(paramPos++, dto.getAmount());
			pStmt.setString(paramPos++, dto.getCurrency());
			pStmt.setString(paramPos++, dto.getReceipt());
			pStmt.setString(paramPos++,dto.getRazorpay_signature());
			pStmt.setString(paramPos++, dto.getRazorpay_order_id());
			pStmt.setString(paramPos++, dto.getRazorpay_payment_id());
			pStmt.setInt(paramPos++, dto.getAmount_paid());
//			pStmt.setString(paramPos++, dto.getCreated_at());
			pStmt.setInt(paramPos++, dto.getAmount_due());
			pStmt.setString(paramPos++, dto.getEntity());
//			pStmt.setString(paramPos++, dto.getOffer_id());
			pStmt.setString(paramPos++, dto.getStatus());
			pStmt.setInt(paramPos++, dto.getAttempts());
//			pStmt.setString(paramPos++, dto.getNotes());
			pStmt.setString(paramPos++, dto.getRefer_id());
			updateCount = pStmt.executeUpdate();
			if(updateCount > 0) {
				rSet = pStmt.getGeneratedKeys();
				if (rSet != null) {
					while (rSet.next()) {
						paymentId = rSet.getInt(1);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtil.closeResultSet(rSet);
				DBUtil.closeStatement(pStmt);
				DBUtil.closeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return paymentId;
	}
	/**
	 * method to update table for completting success
	 * @author Pradeep Ravichandran
	 * @param PaymentDto
	 * @return
	 */
	public int UpdatePaymentDetails(PaymentDto dto) {
			int updateCount = 0;
			try {
				conn = DBUtil.getConnection();
				PreparedStatement pStmt = conn.prepareStatement("UPDATE tbl_payment SET status = ?,razorpay_signature = ?,razorpay_order_id = ?,razorpay_payment_id= ?   where  id = ?");
				int paramPos = 1;
				pStmt.setString(paramPos++,dto.getStatus());
				pStmt.setString(paramPos++,dto.getRazorpay_signature());
				pStmt.setString(paramPos++, dto.getRazorpay_order_id());
				pStmt.setString(paramPos++, dto.getRazorpay_payment_id());
				pStmt.setInt(paramPos++,dto.getId());
				updateCount = pStmt.executeUpdate();
				DBUtil.closeStatement(pStmt);
				DBUtil.closeConnection(conn);
			} catch(Exception e){	 
				e.printStackTrace(); 
			} 
			return  updateCount;
	}
	
	/**
	 * Method to get Payment Records
	 * @param ratingsandreferralsDto
	 * @return
	 */
	public PaymentDto getAllPaymentRecordsByCondition(PaymentDto paymentDto) {
		PaymentDto dto = null;
		try {
			conn = DBUtil.getConnection();
			StringBuffer queryString =  new StringBuffer();
			queryString.append("SELECT id,payment_id,amount,currency,receipt,razorpay_signature,razorpay_order_id,razorpay_payment_id,amount_paid,created_at,amount_due,entity,offer_id,status,attempts,notes,refer_id FROM tbl_payment ");
			List<String> conditions = getConditon(paymentDto);
			if(!conditions.isEmpty()) {
				queryString.append( " where " + StringUtil.convertConditionsListToString(conditions));
			}
			PreparedStatement pStmt = conn.prepareStatement(queryString.toString());
			ResultSet rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					dto = new PaymentDto();
					dto.setId(rSet.getInt("id"));
					dto.setPayment_id(rSet.getString("payment_id"));
					dto.setAmount(rSet.getInt("amount"));
					dto.setCurrency(rSet.getString("currency"));
					dto.setReceipt(rSet.getString("receipt"));
					dto.setRazorpay_signature(rSet.getString("razorpay_signature"));
					dto.setRazorpay_order_id(rSet.getString("razorpay_order_id"));
					dto.setRazorpay_payment_id(rSet.getString("razorpay_payment_id"));
					dto.setAmount_paid(rSet.getInt("amount_paid"));
					dto.setCreated_at(rSet.getString("created_at"));
					dto.setAmount_due(rSet.getInt("amount_due"));
					dto.setEntity(rSet.getString("entity"));
					dto.setOffer_id(rSet.getString("offer_id"));
					dto.setStatus(rSet.getString("status"));
					dto.setAttempts(rSet.getInt("attempts"));
					dto.setNotes(rSet.getString("notes"));
					dto.setRefer_id(rSet.getString("refer_id"));
				} 
			} 
			DBUtil.closeResultSet(rSet);
			DBUtil.closeStatement(pStmt);
			DBUtil.closeConnection(conn);
		} catch(Exception e){	 
			e.printStackTrace(); 
		} 
				 
		return dto;
	}
	private List<String> getConditon(PaymentDto dto) {
		List<String> conditions = new ArrayList<String>();
		if(dto.getId() != 0 && dto.getId() > 0){
			conditions.add(" id = "+dto.getId()+" ");
		}
		if(StringUtil.isNotNullOrEmpty(dto.getRefer_id())){
			conditions.add(" refer_id = "+dto.getRefer_id()+" ");
		}
		if(StringUtil.isNotNullOrEmpty(dto.getReceipt())){
			conditions.add(" receipt = "+dto.getReceipt()+" ");
		}
		return conditions;
	}
	/**
	 * Method to check checkPaymentStatus
	 * @author Pradeep Ravichandran
	 * @param dto
	 * @return
	 */
	public PaymentDto checkPaymentStatus(int receiptId) {
		PaymentDto paymentDto = new PaymentDto();
		paymentDto.setReceipt(Integer.toString(receiptId));
		PaymentDto dto = null;
		try {
			conn = DBUtil.getConnection();
			StringBuffer queryString =  new StringBuffer();
			queryString.append("SELECT id,payment_id,amount,currency,receipt,razorpay_signature,razorpay_order_id,razorpay_payment_id,amount_paid,created_at,amount_due,entity,offer_id,status,attempts,notes,refer_id FROM tbl_payment ");
			List<String> conditions = getConditon(paymentDto);
			if(!conditions.isEmpty()) {
				queryString.append( " where " + StringUtil.convertConditionsListToString(conditions));
			}
			PreparedStatement pStmt = conn.prepareStatement(queryString.toString());
			ResultSet rSet = pStmt.executeQuery();
			if (rSet != null) {
				while (rSet.next()) {
					dto = new PaymentDto();
					dto.setId(rSet.getInt("id"));
					dto.setPayment_id(rSet.getString("payment_id"));
					dto.setAmount(rSet.getInt("amount"));
					dto.setCurrency(rSet.getString("currency"));
					dto.setReceipt(rSet.getString("receipt"));
					dto.setRazorpay_signature(rSet.getString("razorpay_signature"));
					dto.setRazorpay_order_id(rSet.getString("razorpay_order_id"));
					dto.setRazorpay_payment_id(rSet.getString("razorpay_payment_id"));
					dto.setAmount_paid(rSet.getInt("amount_paid"));
					dto.setCreated_at(rSet.getString("created_at"));
					dto.setAmount_due(rSet.getInt("amount_due"));
					dto.setEntity(rSet.getString("entity"));
					dto.setOffer_id(rSet.getString("offer_id"));
					dto.setStatus(rSet.getString("status"));
					dto.setAttempts(rSet.getInt("attempts"));
					dto.setNotes(rSet.getString("notes"));
					dto.setRefer_id(rSet.getString("refer_id"));
				} 
			} 
			DBUtil.closeResultSet(rSet);
			DBUtil.closeStatement(pStmt);
			DBUtil.closeConnection(conn);
		} catch(Exception e){	 
			e.printStackTrace(); 
		} 
				 
		return dto;
	}
}
