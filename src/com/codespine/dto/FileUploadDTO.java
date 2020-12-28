package com.codespine.dto;

import java.util.Map;

public class FileUploadDTO {
	private String proofType;
	private String proof;
	private String typeOfProof;
	private Map<String, String> forPDFKeyValue;

	public String getProofType() {
		return proofType;
	}

	public void setProofType(String proofType) {
		this.proofType = proofType;
	}

	public String getProof() {
		return proof;
	}

	public void setProof(String proof) {
		this.proof = proof;
	}

	public String getTypeOfProof() {
		return typeOfProof;
	}

	public void setTypeOfProof(String typeOfProof) {
		this.typeOfProof = typeOfProof;
	}

	public Map<String, String> getForPDFKeyValue() {
		return forPDFKeyValue;
	}

	public void setForPDFKeyValue(Map<String, String> forPDFKeyValue) {
		this.forPDFKeyValue = forPDFKeyValue;
	}

}
