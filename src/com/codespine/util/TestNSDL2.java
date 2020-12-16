package com.codespine.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestNSDL2 {
	public static void main(String[] args) {
		try {
			Calendar currentTime = Calendar.getInstance();
			currentTime.add(Calendar.MINUTE, 30);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(formatter.format(currentTime.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
