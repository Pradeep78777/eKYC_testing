package com.codespine.util;

public class Test {

	public static void main(String[] args) {
		try {
//			List<ApplicationLogDTO> result = AdminDAO.getInstance().rejectedDocuments(1);
//			Utility.applicationRejectedMessage("gowrisankar@stoneagesolutions.com", "Gowrisankar", result);
//			Utility.stringToJson("1^cntpk5744h^E^KRITHIHASRI^CHINNASWAMY^VENUGOPAL^Kumari^01/06/2012^KRITHIHASRI C V^Y^");
			System.out.println(DateUtil.getLastFinancialDate());
			System.out.println(DateUtil.getLastFinancialYearTo(DateUtil.getTodayDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
