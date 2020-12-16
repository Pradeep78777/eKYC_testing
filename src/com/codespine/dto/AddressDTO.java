package com.codespine.dto;

import java.util.Map;

public class AddressDTO {
	private int application_id;
	private String flat_no;
	private String street;
	private int pin;
	private String city;
	private String district;
	private String state;
	private Map<String, String> forPDFKeyValue;
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

	public int getApplication_id() {
		return application_id;
	}

	public void setApplication_id(int application_id) {
		this.application_id = application_id;
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
