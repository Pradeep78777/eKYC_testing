package com.codespine.dto;

public class PaymentDto {
	private int id;
	private int application_id;
	private int amount;
	private String currency;
	private String receipt;
	private String razorpay_signature;
	private String razorpay_order_id;
	private String razorpay_payment_id;
	private int amount_paid;
	private String created_at;
	private int amount_due;
	private String payment_id;
	private String entity;
	private String offer_id;
	private String status;
	private int attempts;
	private String refer_id;
	private String notes;
	private int order_id;
	private int postedBy;
	private int postedTo;
	private String postedByName;
	private String postedToName;
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getReceipt() {
		return receipt;
	}
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getRazorpay_signature() {
		return razorpay_signature;
	}
	public void setRazorpay_signature(String razorpay_signature) {
		this.razorpay_signature = razorpay_signature;
	}
	public String getRazorpay_order_id() {
		return razorpay_order_id;
	}
	public void setRazorpay_order_id(String razorpay_order_id) {
		this.razorpay_order_id = razorpay_order_id;
	}
	public String getRazorpay_payment_id() {
		return razorpay_payment_id;
	}
	public void setRazorpay_payment_id(String razorpay_payment_id) {
		this.razorpay_payment_id = razorpay_payment_id;
	}
	public int getAmount_paid() {
		return amount_paid;
	}
	public void setAmount_paid(int amount_paid) {
		this.amount_paid = amount_paid;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public int getAmount_due() {
		return amount_due;
	}
	public void setAmount_due(int amount_due) {
		this.amount_due = amount_due;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getOffer_id() {
		return offer_id;
	}
	public void setOffer_id(String offer_id) {
		this.offer_id = offer_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getAttempts() {
		return attempts;
	}
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}
	public String getRefer_id() {
		return refer_id;
	}
	public void setRefer_id(String refer_id) {
		this.refer_id = refer_id;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public int getPostedBy() {
		return postedBy;
	}
	public void setPostedBy(int postedBy) {
		this.postedBy = postedBy;
	}
	public int getPostedTo() {
		return postedTo;
	}
	public void setPostedTo(int postedTo) {
		this.postedTo = postedTo;
	}
	public String getPostedByName() {
		return postedByName;
	}
	public void setPostedByName(String postedByName) {
		this.postedByName = postedByName;
	}
	public String getPostedToName() {
		return postedToName;
	}
	public void setPostedToName(String postedToName) {
		this.postedToName = postedToName;
	}
	public int getApplication_id() {
		return application_id;
	}
	public void setApplication_id(int application_id) {
		this.application_id = application_id;
	}

}
