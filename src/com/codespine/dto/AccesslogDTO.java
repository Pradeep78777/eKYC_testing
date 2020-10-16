package com.codespine.dto;

import java.sql.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AccesslogDTO {

	private int id;
	private String uri;
	private String user_id;
	private Date entry_time;
	private String device_ip;
	private String user_agent;
	private String content_type;
	private int authenticate_token;
	private String response_data;
	private String elapsed_time;
	private String input;
	private java.sql.Timestamp created_on;
	private String created_by;
	private Date updated_on;
	private String updated_by;
	private int active_status;
	private String domain;
	private String userSessionID;
	private int sessionExpiredTag;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Date getEntry_time() {
		return entry_time;
	}

	public void setEntry_time(Date entry_time) {
		this.entry_time = entry_time;
	}

	public String getDevice_ip() {
		return device_ip;
	}

	public void setDevice_ip(String device_ip) {
		this.device_ip = device_ip;
	}

	public String getUser_agent() {
		return user_agent;
	}

	public void setUser_agent(String user_agent) {
		this.user_agent = user_agent;
	}

	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	public int getAuthenticate_token() {
		return authenticate_token;
	}

	public void setAuthenticate_token(int authenticate_token) {
		this.authenticate_token = authenticate_token;
	}

	public String getResponse_data() {
		return response_data;
	}

	public void setResponse_data(String response_data) {
		this.response_data = response_data;
	}

	public String getElapsed_time() {
		return elapsed_time;
	}

	public void setElapsed_time(String elapsed_time) {
		this.elapsed_time = elapsed_time;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public java.sql.Timestamp getCreated_on() {
		return created_on;
	}

	public void setCreated_on(java.sql.Timestamp created_on) {
		this.created_on = created_on;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getUpdated_on() {
		return updated_on;
	}

	public void setUpdated_on(Date updated_on) {
		this.updated_on = updated_on;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public int getActive_status() {
		return active_status;
	}

	public void setActive_status(int active_status) {
		this.active_status = active_status;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUserSessionID() {
		return userSessionID;
	}

	public void setUserSessionID(String userSessionID) {
		this.userSessionID = userSessionID;
	}

	public int getSessionExpiredTag() {
		return sessionExpiredTag;
	}

	public void setSessionExpiredTag(int sessionExpiredTag) {
		this.sessionExpiredTag = sessionExpiredTag;
	}

}
