package com.codespine.dto;

import java.util.Date;

public class AccountHolderDetailsDTO {
	private int id;
	private String name;
	private int mobile_number;
	private String email;
	private int otp;	
	private int email_verified;
	private int verified;
	private String verification_key;
	private Date verified_on;
	private Date created_on;
	private int application_id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMobile_number() {
		return mobile_number;
	}
	public void setMobile_number(int mobile_number) {
		this.mobile_number = mobile_number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getOtp() {
		return otp;
	}
	public void setOtp(int otp) {
		this.otp = otp;
	}
	public int getEmail_verified() {
		return email_verified;
	}
	public void setEmail_verified(int email_verified) {
		this.email_verified = email_verified;
	}
	public int getVerified() {
		return verified;
	}
	public void setVerified(int verified) {
		this.verified = verified;
	}
	public String getVerification_key() {
		return verification_key;
	}
	public void setVerification_key(String verification_key) {
		this.verification_key = verification_key;
	}
	public Date getVerified_on() {
		return verified_on;
	}
	public void setVerified_on(Date verified_on) {
		this.verified_on = verified_on;
	}
	public Date getCreated_on() {
		return created_on;
	}
	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}
	public int getApplication_id() {
		return application_id;
	}
	public void setApplication_id(int application_id) {
		this.application_id = application_id;
	}
	
}
