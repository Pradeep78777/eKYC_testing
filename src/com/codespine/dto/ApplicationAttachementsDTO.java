package com.codespine.dto;

public class ApplicationAttachementsDTO {
	private int application_id;
	private String attachement_type;
	private String type_of_proof;
	private String attachement_url;
	private int isAproved;
	private int isRejected;
	private String comments;
	private String createdAt;
	private String lastUpdatedAt;

	public int getApplication_id() {
		return application_id;
	}

	public void setApplication_id(int application_id) {
		this.application_id = application_id;
	}

	public String getAttachement_type() {
		return attachement_type;
	}

	public void setAttachement_type(String attachement_type) {
		this.attachement_type = attachement_type;
	}

	public String getType_of_proof() {
		return type_of_proof;
	}

	public void setType_of_proof(String type_of_proof) {
		this.type_of_proof = type_of_proof;
	}

	public String getAttachement_url() {
		return attachement_url;
	}

	public void setAttachement_url(String attachement_url) {
		this.attachement_url = attachement_url;
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
