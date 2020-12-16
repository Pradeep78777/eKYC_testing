package com.codespine.dto;

import java.util.Map;

public class ExchDetailsDTO {
	private int application_id;
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
	private Map<String, String> forPDFKeyValue;
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

	public Map<String, String> getForPDFKeyValue() {
		return forPDFKeyValue;
	}

	public void setForPDFKeyValue(Map<String, String> forPDFKeyValue) {
		this.forPDFKeyValue = forPDFKeyValue;
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
