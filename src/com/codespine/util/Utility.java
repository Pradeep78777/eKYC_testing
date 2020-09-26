package com.codespine.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utility {

	/**
	 * Method to generate OTP
	 * 
	 * @since 26-9-2019
	 * @returnString
	 *
	 */
	public static String generateOTP() {
		int randomPin = (int) (Math.random() * 9000) + 1000;
		String otp = String.valueOf(randomPin);
		return otp;
	}

	/**
	 * Method to send otp via message
	 * 
	 * @return
	 */
	public static String sendSms(String otp, String pnNumber) {
		try {
			// Construct data
			String apiKey = "apikey=" + "3UWxXX8vmGg-JJS8QZqaSuM3JW4lEJj3dZZWbaHEVm";
			String message = "&message=" + "OTP to complete your AMOGA registration is " + otp
					+ ". Please do not share your otp with anyone. Thank you for choosing AMOGA.";
			String sender = "&sender=" + "AMALGO";
			String numbers = "&numbers=" + pnNumber;

			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
			String data = apiKey + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();

			return stringBuffer.toString();
		} catch (Exception e) {
			System.out.println("Error SMS " + e);
			return "Error " + e;
		}
	}

}