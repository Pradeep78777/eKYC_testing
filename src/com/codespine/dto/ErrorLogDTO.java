package com.codespine.dto;

import java.util.Date;

public class ErrorLogDTO {

	private String class_name;
	private String method_name;
	private String parameter;
	private String error_msg;
	private String created_by;
	private Date created_on;
	private Date updated_on;
	private String updated_by;
	private int active_status;

	public String getClass_name() {
		return this.class_name;
	}

	public void setClass_name(String pClass_name) {
		this.class_name = pClass_name;
	}

	public String getMethod_name() {
		return this.method_name;
	}

	public void setMethod_name(String pMethod_name) {
		this.method_name = pMethod_name;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getError_msg() {
		return this.error_msg;
	}

	public void setError_msg(String pError_msg) {
		this.error_msg = pError_msg;
	}

	public String getCreated_by() {
		return this.created_by;
	}

	public void setCreated_by(String pCreated_by) {
		this.created_by = pCreated_by;
	}

	public Date getCreated_on() {
		return this.created_on;
	}

	public void setCreated_on(Date string) {
		this.created_on = string;
	}

	public Date getUpdated_on() {
		return this.updated_on;
	}

	public void setUpdated_on(Date pUpdated_on) {
		this.updated_on = pUpdated_on;
	}

	public String getUpdated_by() {
		return this.updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public int getActive_status() {
		return this.active_status;
	}

	public void setActive_status(int pActive_status) {
		this.active_status = pActive_status;
	}

}