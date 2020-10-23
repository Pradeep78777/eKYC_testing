package com.codespine.dto;

public class ApplicationLogDTO {

	private int application_id;
	private String verification_module;
	private String start_time;
	private String end_time;
	private int status;
	private String notes;

	public int getApplication_id() {
		return application_id;
	}

	public void setApplication_id(int application_id) {
		this.application_id = application_id;
	}

	public String getVerification_module() {
		return verification_module;
	}

	public void setVerification_module(String verification_module) {
		this.verification_module = verification_module;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
