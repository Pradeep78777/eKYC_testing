package com.codespine.dto;

public class PostalCodesDTO {
	private int pin_code;
	private int district_code;
	private String district_name;
	private String state_name;
	private String status;
	public int getPin_code() {
		return pin_code;
	}
	public void setPin_code(int pin_code) {
		this.pin_code = pin_code;
	}
	public int getDistrict_code() {
		return district_code;
	}
	public void setDistrict_code(int district_code) {
		this.district_code = district_code;
	}
	public String getDistrict_name() {
		return district_name;
	}
	public void setDistrict_name(String district_name) {
		this.district_name = district_name;
	}
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
