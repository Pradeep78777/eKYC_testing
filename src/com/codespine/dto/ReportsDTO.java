package com.codespine.dto;

import java.util.List;

public class ReportsDTO {

	private List<ReportColumnDTO> userDetails;
	private List<ReportColumnDTO> panDetails;
	private List<ReportColumnDTO> basicInformation;
	private List<ReportColumnDTO> communicationAddress;
	private List<ReportColumnDTO> permananentAddress;
	private List<ReportColumnDTO> bankDetails;
	private List<ReportColumnDTO> tradingPreferences;
	private List<ReportColumnDTO> adminPanDetails;
	private List<ReportColumnDTO> adminBasicInfo;
	private List<ReportColumnDTO> adminCommunicationAddress;
	private List<ReportColumnDTO> adminPermanentAddress;
	private List<ReportColumnDTO> adminBankAccountDetail;
	private List<ReportColumnDTO> eSign;
	
	public List<ReportColumnDTO> getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(List<ReportColumnDTO> userDetails) {
		this.userDetails = userDetails;
	}

	public List<ReportColumnDTO> getPanDetails() {
		return panDetails;
	}

	public void setPanDetails(List<ReportColumnDTO> panDetails) {
		this.panDetails = panDetails;
	}

	public List<ReportColumnDTO> getBasicInformation() {
		return basicInformation;
	}

	public void setBasicInformation(List<ReportColumnDTO> basicInformation) {
		this.basicInformation = basicInformation;
	}

	public List<ReportColumnDTO> getCommunicationAddress() {
		return communicationAddress;
	}

	public void setCommunicationAddress(List<ReportColumnDTO> communicationAddress) {
		this.communicationAddress = communicationAddress;
	}

	public List<ReportColumnDTO> getPermananentAddress() {
		return permananentAddress;
	}

	public void setPermananentAddress(List<ReportColumnDTO> permananentAddress) {
		this.permananentAddress = permananentAddress;
	}

	public List<ReportColumnDTO> getBankDetails() {
		return bankDetails;
	}

	public void setBankDetails(List<ReportColumnDTO> bankDetails) {
		this.bankDetails = bankDetails;
	}

	public List<ReportColumnDTO> getTradingPreferences() {
		return tradingPreferences;
	}

	public void setTradingPreferences(List<ReportColumnDTO> tradingPreferences) {
		this.tradingPreferences = tradingPreferences;
	}

	public List<ReportColumnDTO> getAdminPanDetails() {
		return adminPanDetails;
	}

	public void setAdminPanDetails(List<ReportColumnDTO> adminPanDetails) {
		this.adminPanDetails = adminPanDetails;
	}

	public List<ReportColumnDTO> getAdminBasicInfo() {
		return adminBasicInfo;
	}

	public void setAdminBasicInfo(List<ReportColumnDTO> adminBasicInfo) {
		this.adminBasicInfo = adminBasicInfo;
	}

	public List<ReportColumnDTO> getAdminCommunicationAddress() {
		return adminCommunicationAddress;
	}

	public void setAdminCommunicationAddress(List<ReportColumnDTO> adminCommunicationAddress) {
		this.adminCommunicationAddress = adminCommunicationAddress;
	}

	public List<ReportColumnDTO> getAdminPermanentAddress() {
		return adminPermanentAddress;
	}

	public void setAdminPermanentAddress(List<ReportColumnDTO> adminPermanentAddress) {
		this.adminPermanentAddress = adminPermanentAddress;
	}

	public List<ReportColumnDTO> getAdminBankAccountDetail() {
		return adminBankAccountDetail;
	}

	public void setAdminBankAccountDetail(List<ReportColumnDTO> adminBankAccountDetail) {
		this.adminBankAccountDetail = adminBankAccountDetail;
	}

	public List<ReportColumnDTO> geteSign() {
		return eSign;
	}

	public void seteSign(List<ReportColumnDTO> eSign) {
		this.eSign = eSign;
	}
}
