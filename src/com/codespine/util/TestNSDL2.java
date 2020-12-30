package com.codespine.util;

public class TestNSDL2 {
	public static void main(String[] args) {
		try {
			Utility.stringToJson("1^aaapn3925h^E^NATARAJAN^MURUGESAN^^Shri^09/07/2015^^^");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
