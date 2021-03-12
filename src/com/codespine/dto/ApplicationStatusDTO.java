package com.codespine.dto;

public class ApplicationStatusDTO {
	private int applicationId;
	private String applicationStartedOn;
	private String pancardUpdatedOn;
	private String personalDetailsUpdatedOn;
	private String esignedOn;

	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationStartedOn() {
		return applicationStartedOn;
	}

	public void setApplicationStartedOn(String applicationStartedOn) {
		this.applicationStartedOn = applicationStartedOn;
	}

	public String getPancardUpdatedOn() {
		return pancardUpdatedOn;
	}

	public void setPancardUpdatedOn(String pancardUpdatedOn) {
		this.pancardUpdatedOn = pancardUpdatedOn;
	}

	public String getPersonalDetailsUpdatedOn() {
		return personalDetailsUpdatedOn;
	}

	public void setPersonalDetailsUpdatedOn(String personalDetailsUpdatedOn) {
		this.personalDetailsUpdatedOn = personalDetailsUpdatedOn;
	}

	public String getEsignedOn() {
		return esignedOn;
	}

	public void setEsignedOn(String esignedOn) {
		this.esignedOn = esignedOn;
	}

}
