package com.codespine.restservice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.codespine.util.CSEnvVariables;
import com.codespine.util.eKYCConstant;

public class BackOfficeRestService {

	public static BackOfficeRestService BackOfficeRestService = null;

	public static BackOfficeRestService getInstance() {
		if (BackOfficeRestService == null) {
			BackOfficeRestService = new BackOfficeRestService();
		}
		return BackOfficeRestService;
	}

	/**
	 * Method to check the existing customer from the back office URL
	 * 
	 * @author GOWRI SANKAR R
	 * @param parameter
	 * @param parameterValue
	 * @return
	 */
	public Object checkExistingCustomer(String parameter, String parameterValue) {
		Object object = null;
		try {
			URL url = new URL(CSEnvVariables.getMethodNames(eKYCConstant.BACK_OFFICE_URL) + parameter + "&value="
					+ parameterValue);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			BufferedReader br1 = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			while ((output = br1.readLine()) != null) {
				object = output;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
}
