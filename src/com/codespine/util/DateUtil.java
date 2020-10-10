package com.codespine.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static final String DDMMYYYY 					= "ddMMyyyy";
	public static final String DD_MMM_YYYY_HH_MM_SS 		= "dd-MMM-yyyyHHmmss";
	public static final String DD_MMM_YY					= "dd-MMM-yy";
	public static final String DD_MMM_YYYY 					= "dd-MM-yyyy";
	public static final String YYYYMMDD 					= "yyyyMMdd";
	
	public static String parseDate(Date date) {
		String date2 = null; 
		DateFormat formatter ; 
		formatter = new SimpleDateFormat("dd-MMM-yy");
		date2 = (String) formatter.format(date);  
		 
		 return date2;
	}
	
	public static Date parseString(String dateString, DateFormat format) {
		Date date = null;
		if(StringUtil.isNotNullOrEmpty(dateString)&& format !=null){
			try {
				date = (Date) format.parse(dateString);
			} catch (ParseException e) {
				System.out.println("ParseException :" + e);
			} catch (Exception e) {
				System.out.println("Exception :" + e);
			}
		}
		return date;
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
	public static Date parseString(String dateString) {
		Date date = null;
		SimpleDateFormat formatter = null;
		if(StringUtil.isNotNullOrEmpty(dateString)){
			try {
				formatter = new SimpleDateFormat(DD_MMM_YYYY);
				date = (Date) formatter.parse(dateString);
			} catch (ParseException e) {
				System.out.println("Exception :" + e);
			}
		}
		return date;
	}
	public static String parseDateStringForDOB(String dateString) {
		String changedDate = "";
		Date date = null;
		SimpleDateFormat  formatter1 = null;
		if(StringUtil.isNotNullOrEmpty(dateString)){
			try {
				formatter1 = new SimpleDateFormat(YYYYMMDD);
				date = (Date) formatter1.parse((StringUtil.replace(dateString,"-","")));
				changedDate = formatDate(date,DDMMYYYY);
			} catch (ParseException e) {
				System.out.println("Exception :" + e);
			}
		}
		return changedDate;
	}
	

}
