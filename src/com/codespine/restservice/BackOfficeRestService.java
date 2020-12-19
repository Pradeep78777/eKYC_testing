package com.codespine.restservice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONValue;

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
				object = JSONValue.parse(output);
				if (object == null) {
					String tempString = (String) output;
					String[] resp = tempString.split(",");
					int statusCode = Integer.parseInt(StringUtils.substringAfter(resp[0], ":"));
					if (statusCode == 0) {
						return null;
					} else {
						return null;
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	/**
	 * Method to post the data to the back office
	 * 
	 * @author GOWRI SANKAR R
	 * @param parameter
	 * @param parameterValue
	 * @return
	 */
	public Object postDataToBackEnd(String parameter) {
		Object object = null;
		try {
			// String tempURl =
			// CSEnvVariables.getMethodNames(eKYCConstant.POST_DATA_URL) + "?" +
			// java.net.URLEncoder.encode(parameter, "UTF-8");
			String tempURl = parameter.replace(" ", "%20");
			System.out.println(tempURl);
			URL url = new URL(CSEnvVariables.getMethodNames(eKYCConstant.POST_DATA_URL) + "?" + tempURl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			BufferedReader br1 = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			while ((output = br1.readLine()) != null) {
				object = output;
				System.out.println(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

}
