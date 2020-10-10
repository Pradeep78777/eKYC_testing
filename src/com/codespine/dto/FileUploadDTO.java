package com.codespine.dto;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;

public class FileUploadDTO {
	private String proofType;
	private FormDataBodyPart proof;
	public String getProofType() {
		return proofType;
	}
	public void setProofType(String proofType) {
		this.proofType = proofType;
	}
	public FormDataBodyPart getProof() {
		return proof;
	}
	public void setProof(FormDataBodyPart proof) {
		this.proof = proof;
	}
	
}
