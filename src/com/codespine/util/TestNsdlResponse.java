package com.codespine.util;

import org.json.simple.JSONObject;

public class TestNsdlResponse {
	public static void main(String[] args) {
		String output = "1^BZAPG5040A^E^GOWRI SANKAR^RAJENDRAN^^Shri^31/12/2016^GOWRI SANKAR^Y^";
		String[] resp = null;
		if (output.lastIndexOf("^") == output.length() - 1) {
			resp = output.split("\\^");
		} else {
			resp = output.split("\\^");
		}
		JSONObject tempJson = new JSONObject();
		tempJson.put("responseCode", resp[0]);
		tempJson.put("panCard", resp[1]);
		tempJson.put("panCardStatus", resp[2]);
		tempJson.put("lastName", resp[3]);
		tempJson.put("firstName", resp[4]);
		tempJson.put("middleName", resp[5]);
		tempJson.put("panTittle", resp[6]);
		tempJson.put("lastUpdatedDate", resp[7]);
		tempJson.put("nameOnCard", resp[8]);
		tempJson.put("aadhaar seeding status", resp[9]);
		System.out.println(tempJson);
	}
}
