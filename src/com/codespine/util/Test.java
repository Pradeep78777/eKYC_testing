package com.codespine.util;

import com.codespine.restservice.NsdlPanVerificationRestService;

public class Test {
	public static void main(String[] args) {
		NsdlPanVerificationRestService.pfx2JksFile(2);
		NsdlPanVerificationRestService.pkcs7Generate(2, "BZAPG5040A");
		NsdlPanVerificationRestService.apiCallForPanVerififcation(2, "BZAPG5040A");
		// String mobileNumber = "9865858790";
		// Utility.sendMessage(Long.parseLong(mobileNumber), 1234);
	}
}
