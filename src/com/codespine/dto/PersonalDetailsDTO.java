package com.codespine.dto;

import java.util.Map;

public class PersonalDetailsDTO {

	private int id;
	private String name;
	private long mobile_number;
	private String email;
	private int application_id;
	private String marital_status;
	private String annual_income;
	private String trading_experience;
	private String occupation;
	private String politically_exposed;
	private int otp;
	private String gender;
	private int mobile_number_verified;
	private int email_id_verified;
	private int applicationStatus;
	private String mobile_owner;
	private String email_owner;
	private String mothersName;
	private String fathersName;
	private String applicant_name;
	private String esign_Xml;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(long mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getApplication_id() {
		return application_id;
	}

	public void setApplication_id(int application_id) {
		this.application_id = application_id;
	}

	public String getMarital_status() {
		return marital_status;
	}

	public void setMarital_status(String marital_status) {
		this.marital_status = marital_status;
	}

	public String getAnnual_income() {
		return annual_income;
	}

	public void setAnnual_income(String annual_income) {
		this.annual_income = annual_income;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getMobile_number_verified() {
		return mobile_number_verified;
	}

	public void setMobile_number_verified(int mobile_number_verified) {
		this.mobile_number_verified = mobile_number_verified;
	}

	public int getEmail_id_verified() {
		return email_id_verified;
	}

	public void setEmail_id_verified(int email_id_verified) {
		this.email_id_verified = email_id_verified;
	}

	public int getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(int applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public String getMobile_owner() {
		return mobile_owner;
	}

	public void setMobile_owner(String mobile_owner) {
		this.mobile_owner = mobile_owner;
	}

	public String getEmail_owner() {
		return email_owner;
	}

	public void setEmail_owner(String email_owner) {
		this.email_owner = email_owner;
	}

	public String getTrading_experience() {
		return trading_experience;
	}

	public void setTrading_experience(String trading_experience) {
		this.trading_experience = trading_experience;
	}

	public String getPolitically_exposed() {
		return politically_exposed;
	}

	public void setPolitically_exposed(String politically_exposed) {
		this.politically_exposed = politically_exposed;
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

	public String getEsign_Xml() {
		return esign_Xml;
	}

	public void setEsign_Xml(String esign_Xml) {
		this.esign_Xml = esign_Xml;
	}
}
