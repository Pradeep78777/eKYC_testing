package com.codespine.dto;

import java.util.Date;

public class ReportOutputDTO {
	// Application master tables
	private long mobile_number;
	private int mobile_no_verified;
	private String email_id;
	private int email_activated;
	private Date otp_verified_on;
	private Date email_activated_on;
	private String mob_owner;
	private String email_owner;
	private String ref_code;
	// pan card details - pan card DTO
	private String pan_card;
	private long aadhar_no;
	private String dob;
	private int pan_card_verified;
	private int panIsAproved;
	private int panIsRejected;
	private String panComments;
	// personal details - personal Details DTO
	private String mothersName;
	private String fathersName;
	private String gender;
	private String marital_status;
	private String annual_income;
	private String trading_experience;
	private String occupation;
	private String politically_exposed;
	private int personalIsAproved;
	private int personalIsRejected;
	private String personalComments;
	// communication address-Address DTO
	private String flat_no;
	private String street;
	private int pin;
	private String city;
	private String district;
	private String state;
	private int addressIsAproved;
	private int addressIsRejected;
	private String addresscomments;
	// permanent address-Address DTO
	private String p_flat_no;
	private String p_street;
	private int p_pin;
	private String p_city;
	private String p_district;
	private String p_state;
	private int p_addressIsAproved;
	private int p_addressIsRejected;
	private String p_addresscomments;
	// bank details - bank Detail DTO
	private String bank_account_no;
	private String account_holder_name;
	private String ifsc_code;
	private String bank_name;
	private String bank_address;
	private String micr_code;
	private int bankIsAproved;
	private int bankIsRejected;
	private String bankComments;
	// Exch Details
	private int nse_eq;
	private int bse_eq;
	private int mf;
	private int nse_fo;
	private int bse_fo;
	private int cds;
	private int bcd;
	private int mcx;
	private int icex;
	private int nse_com;
	private int bse_com;
	// e sign
	private int document_signed;
	private int document_downloaded;

	public long getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(long mobile_number) {
		this.mobile_number = mobile_number;
	}

	public int getMobile_no_verified() {
		return mobile_no_verified;
	}

	public void setMobile_no_verified(int mobile_no_verified) {
		this.mobile_no_verified = mobile_no_verified;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public int getEmail_activated() {
		return email_activated;
	}

	public void setEmail_activated(int email_activated) {
		this.email_activated = email_activated;
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

	public String getPan_card() {
		return pan_card;
	}

	public long getAadhar_no() {
		return aadhar_no;
	}

	public void setAadhar_no(long aadhar_no) {
		this.aadhar_no = aadhar_no;
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

	public int getPanIsAproved() {
		return panIsAproved;
	}

	public void setPanIsAproved(int panIsAproved) {
		this.panIsAproved = panIsAproved;
	}

	public int getPanIsRejected() {
		return panIsRejected;
	}

	public void setPanIsRejected(int panIsRejected) {
		this.panIsRejected = panIsRejected;
	}

	public String getPanComments() {
		return panComments;
	}

	public void setPanComments(String panComments) {
		this.panComments = panComments;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getTrading_experience() {
		return trading_experience;
	}

	public void setTrading_experience(String trading_experience) {
		this.trading_experience = trading_experience;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getPolitically_exposed() {
		return politically_exposed;
	}

	public void setPolitically_exposed(String politically_exposed) {
		this.politically_exposed = politically_exposed;
	}

	public int getPersonalIsAproved() {
		return personalIsAproved;
	}

	public void setPersonalIsAproved(int personalIsAproved) {
		this.personalIsAproved = personalIsAproved;
	}

	public int getPersonalIsRejected() {
		return personalIsRejected;
	}

	public void setPersonalIsRejected(int personalIsRejected) {
		this.personalIsRejected = personalIsRejected;
	}

	public String getPersonalComments() {
		return personalComments;
	}

	public void setPersonalComments(String personalComments) {
		this.personalComments = personalComments;
	}

	public String getFlat_no() {
		return flat_no;
	}

	public void setFlat_no(String flat_no) {
		this.flat_no = flat_no;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getAddressIsAproved() {
		return addressIsAproved;
	}

	public void setAddressIsAproved(int addressIsAproved) {
		this.addressIsAproved = addressIsAproved;
	}

	public int getAddressIsRejected() {
		return addressIsRejected;
	}

	public void setAddressIsRejected(int addressIsRejected) {
		this.addressIsRejected = addressIsRejected;
	}

	public String getAddresscomments() {
		return addresscomments;
	}

	public void setAddresscomments(String addresscomments) {
		this.addresscomments = addresscomments;
	}

	public String getP_flat_no() {
		return p_flat_no;
	}

	public void setP_flat_no(String p_flat_no) {
		this.p_flat_no = p_flat_no;
	}

	public String getP_street() {
		return p_street;
	}

	public void setP_street(String p_street) {
		this.p_street = p_street;
	}

	public int getP_pin() {
		return p_pin;
	}

	public void setP_pin(int p_pin) {
		this.p_pin = p_pin;
	}

	public String getP_city() {
		return p_city;
	}

	public void setP_city(String p_city) {
		this.p_city = p_city;
	}

	public String getP_district() {
		return p_district;
	}

	public void setP_district(String p_district) {
		this.p_district = p_district;
	}

	public String getP_state() {
		return p_state;
	}

	public void setP_state(String p_state) {
		this.p_state = p_state;
	}

	public int getP_addressIsAproved() {
		return p_addressIsAproved;
	}

	public void setP_addressIsAproved(int p_addressIsAproved) {
		this.p_addressIsAproved = p_addressIsAproved;
	}

	public int getP_addressIsRejected() {
		return p_addressIsRejected;
	}

	public void setP_addressIsRejected(int p_addressIsRejected) {
		this.p_addressIsRejected = p_addressIsRejected;
	}

	public String getP_addresscomments() {
		return p_addresscomments;
	}

	public void setP_addresscomments(String p_addresscomments) {
		this.p_addresscomments = p_addresscomments;
	}

	public String getBank_account_no() {
		return bank_account_no;
	}

	public void setBank_account_no(String bank_account_no) {
		this.bank_account_no = bank_account_no;
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

	public String getBank_address() {
		return bank_address;
	}

	public void setBank_address(String bank_address) {
		this.bank_address = bank_address;
	}

	public String getMicr_code() {
		return micr_code;
	}

	public void setMicr_code(String micr_code) {
		this.micr_code = micr_code;
	}

	public int getBankIsAproved() {
		return bankIsAproved;
	}

	public void setBankIsAproved(int bankIsAproved) {
		this.bankIsAproved = bankIsAproved;
	}

	public int getBankIsRejected() {
		return bankIsRejected;
	}

	public void setBankIsRejected(int bankIsRejected) {
		this.bankIsRejected = bankIsRejected;
	}

	public String getBankComments() {
		return bankComments;
	}

	public void setBankComments(String bankComments) {
		this.bankComments = bankComments;
	}

	public int getNse_eq() {
		return nse_eq;
	}

	public void setNse_eq(int nse_eq) {
		this.nse_eq = nse_eq;
	}

	public int getBse_eq() {
		return bse_eq;
	}

	public void setBse_eq(int bse_eq) {
		this.bse_eq = bse_eq;
	}

	public int getMf() {
		return mf;
	}

	public void setMf(int mf) {
		this.mf = mf;
	}

	public int getNse_fo() {
		return nse_fo;
	}

	public void setNse_fo(int nse_fo) {
		this.nse_fo = nse_fo;
	}

	public int getBse_fo() {
		return bse_fo;
	}

	public void setBse_fo(int bse_fo) {
		this.bse_fo = bse_fo;
	}

	public int getCds() {
		return cds;
	}

	public void setCds(int cds) {
		this.cds = cds;
	}

	public int getBcd() {
		return bcd;
	}

	public void setBcd(int bcd) {
		this.bcd = bcd;
	}

	public int getMcx() {
		return mcx;
	}

	public void setMcx(int mcx) {
		this.mcx = mcx;
	}

	public int getIcex() {
		return icex;
	}

	public void setIcex(int icex) {
		this.icex = icex;
	}

	public int getNse_com() {
		return nse_com;
	}

	public void setNse_com(int nse_com) {
		this.nse_com = nse_com;
	}

	public int getBse_com() {
		return bse_com;
	}

	public void setBse_com(int bse_com) {
		this.bse_com = bse_com;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getMob_owner() {
		return mob_owner;
	}

	public void setMob_owner(String mob_owner) {
		this.mob_owner = mob_owner;
	}

	public String getEmail_owner() {
		return email_owner;
	}

	public void setEmail_owner(String email_owner) {
		this.email_owner = email_owner;
	}



	public String getRef_code() {
		return ref_code;
	}

	public void setRef_code(String ref_code) {
		this.ref_code = ref_code;
	}

	public int getDocument_signed() {
		return document_signed;
	}

	public void setDocument_signed(int document_signed) {
		this.document_signed = document_signed;
	}

	public int getDocument_downloaded() {
		return document_downloaded;
	}

	public void setDocument_downloaded(int document_downloaded) {
		this.document_downloaded = document_downloaded;
	}

}
