package com.codespine.util;

import com.codespine.service.BackOfficeService;

public class TestNSDL2 {
	public static void main(String[] args) {
		try {
			BackOfficeService.getInstance().sendBackOffice(1);
			// FileInputStream stream = new FileInputStream(new
			// File("C://Users//GOWRI SANKAR R//Desktop//tempFiiles.txt"));
			// InputStreamReader reader = new InputStreamReader(stream);
			// BufferedReader br = new BufferedReader(reader);
			// String thisLine = "";
			// while ((thisLine = br.readLine()) != null) {
			// System.out.println( "BackOfficeContants."+thisLine + " +
			// equalSymbol + " +thisLine.toLowerCase() +" + symbol +");
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
