package com.codespine.dto;

public class BackOfficeDTO {
	private String branchCode;
	private String clientCode;
	private String verifiedBy;
	private String verifiedByDesigination;

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getVerifiedBy() {
		return verifiedBy;
	}

	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}

	public String getVerifiedByDesigination() {
		return verifiedByDesigination;
	}

	public void setVerifiedByDesigination(String verifiedByDesigination) {
		this.verifiedByDesigination = verifiedByDesigination;
	}

}
