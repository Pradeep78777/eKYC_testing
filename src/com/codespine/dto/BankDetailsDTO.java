package com.codespine.dto;

import java.util.Map;

public class BankDetailsDTO {

	private int id;
	private int application_id;
	private String account_holder_name;
	private String ifsc_code;
	private long bank_account_no;
	private String account_type;
	private String verified_on;
	private int verified;
	private int verification_count;
	private Map<String,String>  forPDFKeyValue;
	public Map<String, String> getForPDFKeyValue() {
		return forPDFKeyValue;
	}
	public void setForPDFKeyValue(Map<String, String> forPDFKeyValue) {
		this.forPDFKeyValue = forPDFKeyValue;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getApplication_id() {
		return application_id;
	}
	public void setApplication_id(int application_id) {
		this.application_id = application_id;
	}
	public String getAccount_holder_name() {
		return account_holder_name;
	}
	public void setAccount_holder_name(String account_holder_name) {
		this.account_holder_name = account_holder_name;
	}
	public String getIfsc_code() {
		return ifsc_code;
	}
	public void setIfsc_code(String ifsc_code) {
		this.ifsc_code = ifsc_code;
	}
	public long getBank_account_no() {
		return bank_account_no;
	}
	public void setBank_account_no(long bank_account_no) {
		this.bank_account_no = bank_account_no;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	public String getVerified_on() {
		return verified_on;
	}
	public void setVerified_on(String verified_on) {
		this.verified_on = verified_on;
	}
	public int getVerified() {
		return verified;
	}
	public void setVerified(int verified) {
		this.verified = verified;
	}
	public int getVerification_count() {
		return verification_count;
	}
	public void setVerification_count(int verification_count) {
		this.verification_count = verification_count;
	}
	
	
}
