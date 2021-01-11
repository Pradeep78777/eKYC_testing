package com.codespine.dto;

public class AdminDTO {

	private int applicationId;
	private int isApprove;
	private int isRejected;
	private String comments;
	private String attachementType;
	private String email;
	private String password;
	private String name;
	private String designation;
	private String adminName;
	private String status;
	private String startDate;
	private String endDate;

	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public int getIsApprove() {
		return isApprove;
	}

	public void setIsApprove(int isApprove) {
		this.isApprove = isApprove;
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

	public String getAttachementType() {
		return attachementType;
	}

	public void setAttachementType(String attachementType) {
		this.attachementType = attachementType;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
