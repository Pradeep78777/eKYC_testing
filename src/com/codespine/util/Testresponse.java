package com.codespine.util;

import java.util.Arrays;

public class Testresponse {

	public static void main(String[] args) {
		String response = "1^AAIPM3854E^E^MAHESHWARI^SUDHA^^Smt^31/05/2018^^^";
		String[] numbers = response.split("^");
		System.out.println(Arrays.toString(numbers));
	}

}
