package com.codespine.util;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import com.codespine.restservice.BackOfficeRestService;

public class TestNSDL2 {
	public static void main(String[] args) {
		String[] resp = null;
		Object object = BackOfficeRestService.getInstance()
				.checkExistingCustomer(CSEnvVariables.getMethodNames(eKYCConstant.SEARCH_BY_PANCARD), "FVEPS4255");
		if (object.getClass() == JSONObject.class) {
			System.out.println(object.toString());
		} else {
			String tempString = (String) object;
			resp = tempString.split(",");
			int statusCode = Integer.parseInt(StringUtils.substringAfter(resp[0], ":"));
			System.out.println(statusCode);
			String errorMessage = StringUtils.substringAfter(resp[1], ":");
			System.out.println(errorMessage);
		}
	}

}
