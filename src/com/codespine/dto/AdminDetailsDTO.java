package com.codespine.dto;

public class AdminDetailsDTO {
	private String name;
	private String email;
	private String password;
	private String designation;
	private String branchCode;
	private String remishreeCode;
	private String role;
	private long mobile_number;
	private int userDelete;

	public long getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(long mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getRemishreeCode() {
		return remishreeCode;
	}

	public void setRemishreeCode(String remishreeCode) {
		this.remishreeCode = remishreeCode;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getUserDelete() {
		return userDelete;
	}

	public void setUserDelete(int userDelete) {
		this.userDelete = userDelete;
	}

}
