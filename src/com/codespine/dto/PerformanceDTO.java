package com.codespine.dto;

public class PerformanceDTO {

	private String Date;
	private int Total_Record;
	private int signed;
	private int approved;

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public int getTotalrecord() {
		return Total_Record;
	}

	public void setTotalrecord(int totalrecord) {
		Total_Record = totalrecord;
	}

	public int getSigned() {
		return signed;
	}

	public void setSigned(int signed) {
		this.signed = signed;
	}

	public int getApproved() {
		return approved;
	}

	public void setApproved(int approved) {
		this.approved = approved;
	}


}
