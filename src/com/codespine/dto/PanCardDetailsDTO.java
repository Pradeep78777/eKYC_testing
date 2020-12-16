package com.codespine.dto;

import java.util.Map;

public class PanCardDetailsDTO {
	private int id;
	private int application_id;
	private String pan_card;
	private String dob;
	private int pan_card_verified;
	private String nsdl_name;
	private String nsdl_dob;
	private int verified_on;
	private int verification_count;
	private String mothersName;
	private String fathersName;
	private String applicant_name;
	private Map<String, String> forPDFKeyValue;
	private String first_name;
	private String middle_name;
	private String last_name;
	private long aadharNo;
	private int isAproved;
	private int isRejected;
	private String comments;
	private String createdAt;
	private String lastUpdatedAt;

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

	public String getPan_card() {
		return pan_card;
	}

	public void setPan_card(String pan_card) {
		this.pan_card = pan_card;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public int getPan_card_verified() {
		return pan_card_verified;
	}

	public void setPan_card_verified(int pan_card_verified) {
		this.pan_card_verified = pan_card_verified;
	}

	public String getNsdl_name() {
		return nsdl_name;
	}

	public void setNsdl_name(String nsdl_name) {
		this.nsdl_name = nsdl_name;
	}

	public String getNsdl_dob() {
		return nsdl_dob;
	}

	public void setNsdl_dob(String nsdl_dob) {
		this.nsdl_dob = nsdl_dob;
	}

	public int getVerified_on() {
		return verified_on;
	}

	public void setVerified_on(int verified_on) {
		this.verified_on = verified_on;
	}

	public int getVerification_count() {
		return verification_count;
	}

	public void setVerification_count(int verification_count) {
		this.verification_count = verification_count;
	}

	public String getMothersName() {
		return mothersName;
	}

	public void setMothersName(String mothersName) {
		this.mothersName = mothersName;
	}

	public String getFathersName() {
		return fathersName;
	}

	public void setFathersName(String fathersName) {
		this.fathersName = fathersName;
	}

	public String getApplicant_name() {
		return applicant_name;
	}

	public void setApplicant_name(String applicant_name) {
		this.applicant_name = applicant_name;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getMiddle_name() {
		return middle_name;
	}

	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public long getAadharNo() {
		return aadharNo;
	}

	public void setAadharNo(long aadharNo) {
		this.aadharNo = aadharNo;
	}

	public int getIsAproved() {
		return isAproved;
	}

	public void setIsAproved(int isAproved) {
		this.isAproved = isAproved;
	}

	public int getIsRejected() {
		return isRejected;
	}

	public void setIsRejected(int isRejected) {
		this.isRejected = isRejected;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getLastUpdatedAt() {
		return lastUpdatedAt;
	}

	public void setLastUpdatedAt(String lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}

}
