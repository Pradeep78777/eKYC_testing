package com.codespine.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	public static final String DDMMYYYY_SLASH 				= "ABC:def";
	
	
	public static void main(String[] args) {
		SimpleDateFormat  formatter1 = null;
		Date date = null;
		try {
////			List<ApplicationLogDTO> result = AdminDAO.getInstance().rejectedDocuments(1);
////			Utility.applicationRejectedMessage("gowrisankar@stoneagesolutions.com", "Gowrisankar", result);
////			Utility.stringToJson("1^cntpk5744h^E^KRITHIHASRI^CHINNASWAMY^VENUGOPAL^Kumari^01/06/2012^KRITHIHASRI C V^Y^");
////			System.out.println(DateUtil.formatDate(DateUtil.getTodayDate(), DDMMYYYY_SLASH));
////			System.out.println(DateUtil.getLastFinancialYearTo(DateUtil.getTodayDate()));
//			System.out.println(StringUtils.substringAfter(DDMMYYYY_SLASH, ":"));
			String dateString = "1997-06-24";
			formatter1 = new SimpleDateFormat(DateUtil.YYYYMMDD);
			date = (Date) formatter1.parse((StringUtil.replace(dateString,"-","")));
			System.out.println("date"+date);
			String changedDate = formatDate(date,DateUtil.DDMMYYYY_SLASH);
			System.out.println(changedDate);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String formatDate(Date date, String format){
		DateFormat dateFormat = new SimpleDateFormat(format);
		return formatDateByformat(date, dateFormat);
	}
	public static String formatDateByformat(Date date, DateFormat dateFormat){
		String dateStr = null;
		try {
			if (date != null) {
				dateStr = dateFormat.format(date);
			}
		}catch (Exception e) {
			return null;
		}
		return dateStr;
	}

}
