package com.codespine.util;

import org.apache.commons.lang3.StringUtils;

public class Test {
	public static final String DDMMYYYY_SLASH 				= "ABC:def";
	public static void main(String[] args) {
		try {
//			List<ApplicationLogDTO> result = AdminDAO.getInstance().rejectedDocuments(1);
//			Utility.applicationRejectedMessage("gowrisankar@stoneagesolutions.com", "Gowrisankar", result);
//			Utility.stringToJson("1^cntpk5744h^E^KRITHIHASRI^CHINNASWAMY^VENUGOPAL^Kumari^01/06/2012^KRITHIHASRI C V^Y^");
//			System.out.println(DateUtil.formatDate(DateUtil.getTodayDate(), DDMMYYYY_SLASH));
//			System.out.println(DateUtil.getLastFinancialYearTo(DateUtil.getTodayDate()));
			System.out.println(StringUtils.substringAfter(DDMMYYYY_SLASH, ":"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
