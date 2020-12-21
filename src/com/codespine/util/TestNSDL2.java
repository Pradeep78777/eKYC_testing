package com.codespine.util;

public class TestNSDL2 {
	public static void main(String[] args) {
		try {
			String mobileNumber = "9865858790";
			Utility.sendIPVLink(Long.parseLong(mobileNumber), "oa1.zebull.in", "Gowri sankar");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
