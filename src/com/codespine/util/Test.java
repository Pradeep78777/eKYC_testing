package com.codespine.util;

import com.codespine.restservice.NsdlPanVerificationRestService;

public class Test {
	public static void main(String[] args) {
		NsdlPanVerificationRestService.pfx2JksFile(2);
		NsdlPanVerificationRestService.pkcs7Generate(2, "ANHPK5791Q");
		NsdlPanVerificationRestService.apiCallForPanVerififcation(2, "ANHPK5791Q");
	}
}
