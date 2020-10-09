package com.codespine.dto;

import java.util.Date;
import java.util.Map;


public class ApplicationMasterDTO {
	private int application_id;
	private String mobile_number;
	private String mob_owner;
	private String mobile_otp;
	private int mobile_no_verified;
	private int email_activated;
	private String email_owner;
	private String email_id;
	private String email_activation_code;
	private int application_status;
	private Date otp_verified_on;
	private Date email_activated_on;
	private Date last_updated;
	private Date created_date;
	private boolean isBankAccDtlRequired;
	private boolean isAccHolderDtlRequired;
	private boolean isAccHolderPersonalDtlRequired;
	private boolean isCommunicationAddressRequired;
	private boolean isKeyValuePairRequired;
	private boolean isPanCardDetailRequired;
	private boolean isPermanentAddressRequired;
	private boolean isTPAccessLogRequired;
	private Map<String,String>  forPDFKeyValue;
	public Map<String, String> getForPDFKeyValue() {
		return forPDFKeyValue;
	}
	public void setForPDFKeyValue(Map<String, String> forPDFKeyValue) {
		this.forPDFKeyValue = forPDFKeyValue;
	}
	public int getApplication_id() {
		return application_id;
	}
	public void setApplication_id(int application_id) {
		this.application_id = application_id;
	}
	public String getMobile_number() {
		return mobile_number;
	}
	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}
	public String getMob_owner() {
		return mob_owner;
	}
	public void setMob_owner(String mob_owner) {
		this.mob_owner = mob_owner;
	}
	public String getMobile_otp() {
		return mobile_otp;
	}
	public void setMobile_otp(String mobile_otp) {
		this.mobile_otp = mobile_otp;
	}
	public int getMobile_no_verified() {
		return mobile_no_verified;
	}
	public void setMobile_no_verified(int mobile_no_verified) {
		this.mobile_no_verified = mobile_no_verified;
	}
	public int getEmail_activated() {
		return email_activated;
	}
	public void setEmail_activated(int email_activated) {
		this.email_activated = email_activated;
	}
	public String getEmail_owner() {
		return email_owner;
	}
	public void setEmail_owner(String email_owner) {
		this.email_owner = email_owner;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public String getEmail_activation_code() {
		return email_activation_code;
	}
	public void setEmail_activation_code(String email_activation_code) {
		this.email_activation_code = email_activation_code;
	}
	public int getApplication_status() {
		return application_status;
	}
	public void setApplication_status(int application_status) {
		this.application_status = application_status;
	}
	public Date getOtp_verified_on() {
		return otp_verified_on;
	}
	public void setOtp_verified_on(Date otp_verified_on) {
		this.otp_verified_on = otp_verified_on;
	}
	public Date getEmail_activated_on() {
		return email_activated_on;
	}
	public void setEmail_activated_on(Date email_activated_on) {
		this.email_activated_on = email_activated_on;
	}
	public Date getLast_updated() {
		return last_updated;
	}
	public void setLast_updated(Date last_updated) {
		this.last_updated = last_updated;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	public boolean isBankAccDtlRequired() {
		return isBankAccDtlRequired;
	}
	public void setBankAccDtlRequired(boolean isBankAccDtlRequired) {
		this.isBankAccDtlRequired = isBankAccDtlRequired;
	}
	public boolean isAccHolderDtlRequired() {
		return isAccHolderDtlRequired;
	}
	public void setAccHolderDtlRequired(boolean isAccHolderDtlRequired) {
		this.isAccHolderDtlRequired = isAccHolderDtlRequired;
	}
	public boolean isAccHolderPersonalDtlRequired() {
		return isAccHolderPersonalDtlRequired;
	}
	public void setAccHolderPersonalDtlRequired(boolean isAccHolderPersonalDtlRequired) {
		this.isAccHolderPersonalDtlRequired = isAccHolderPersonalDtlRequired;
	}
	public boolean isCommunicationAddressRequired() {
		return isCommunicationAddressRequired;
	}
	public void setCommunicationAddressRequired(boolean isCommunicationAddressRequired) {
		this.isCommunicationAddressRequired = isCommunicationAddressRequired;
	}
	public boolean isKeyValuePairRequired() {
		return isKeyValuePairRequired;
	}
	public void setKeyValuePairRequired(boolean isKeyValuePairRequired) {
		this.isKeyValuePairRequired = isKeyValuePairRequired;
	}
	public boolean isPanCardDetailRequired() {
		return isPanCardDetailRequired;
	}
	public void setPanCardDetailRequired(boolean isPanCardDetailRequired) {
		this.isPanCardDetailRequired = isPanCardDetailRequired;
	}
	public boolean isPermanentAddressRequired() {
		return isPermanentAddressRequired;
	}
	public void setPermanentAddressRequired(boolean isPermanentAddressRequired) {
		this.isPermanentAddressRequired = isPermanentAddressRequired;
	}
	public boolean isTPAccessLogRequired() {
		return isTPAccessLogRequired;
	}
	public void setTPAccessLogRequired(boolean isTPAccessLogRequired) {
		this.isTPAccessLogRequired = isTPAccessLogRequired;
	}

}
