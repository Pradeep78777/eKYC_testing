package com.codespine.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static final String DDMMYYYY 					= "ddMMyyyy";
	public static final String DDMMYYYY_SLASH 				= "dd/MM/yyyy";
	public static final String DDMMYY 						= "ddMMyy";
	public static final String DDMMYY_SLASH  				= "dd/MM/yy";
	public static final String DD_MMM_YYYY_HH_MM_SS 		= "dd-MMM-yyyyHHmmss";
	public static final String DD_MMM_YY					= "dd-MMM-yy";
	public static final String DD_MMM_YYYY 					= "dd-MM-yyyy";
	public static final String DD_MMM_YYYY_SLASH 			= "dd/MM/yyyy";
	public static final String YYYYMMDD 					= "yyyyMMdd";
	public static final String YYYYMMDD_SLASH  				= "yyyy/MM/dd";
	public static final String YYYY_MM_DD 					= "yyyy-MM-dd";
	public static final DateFormat DATEFORMAT_YYYYMMDD = new SimpleDateFormat(YYYYMMDD);
	public static final DateFormat DATEFORMAT_DDMMYY = new SimpleDateFormat(DDMMYY);
	public static final DateFormat DATEFORMAT_DDMMYYYY = new SimpleDateFormat(DDMMYYYY);
	public static final DateFormat DATEFORMAT_YYYYMMDD_SLASH = new SimpleDateFormat(YYYYMMDD_SLASH);
	public static final DateFormat DATEFORMAT_DDMMYY_SLASH = new SimpleDateFormat(DDMMYY_SLASH);
	public static final DateFormat DATEFORMAT_DDMMYYYY_SLASH = new SimpleDateFormat(DDMMYYYY_SLASH);
	public static String parseDate(Date date) {
		String date2 = null; 
		DateFormat formatter ; 
		formatter = new SimpleDateFormat("dd-MMM-yy");
		date2 = (String) formatter.format(date);  
		 
		 return date2;
	}
	public static Calendar getCalendar(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
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
				formatter1 = new SimpleDateFormat(DateUtil.YYYYMMDD);
				date = (Date) formatter1.parse((StringUtil.replace(dateString,"-","")));
				changedDate = formatDate(date,DateUtil.DDMMYYYY_SLASH);
			} catch (Exception e) {
				System.out.println("Exception :" + e);
			}
		}
		return changedDate;
	}
	public static Date getTodayDate(){
		String date = DATEFORMAT_YYYYMMDD.format(getNewDateWithCurrentTime());
		return parseString(date, DATEFORMAT_YYYYMMDD);
	}
	public static Date getNewDateWithCurrentTime(){
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}
	public static String getTodayStringDate(){
		String date = DATEFORMAT_DDMMYY.format(getNewDateWithCurrentTime());
		return date;
	}
	public static String getLastFinancialDate(){
		String date = DATEFORMAT_YYYYMMDD.format(getNewDateWithCurrentTime());
		return date;
	}
	public static String getFinancialYearFrom(Date date) {
		Calendar calendar = DateUtil.getCalendar(date);
		int CurrentYear = calendar.get(Calendar.YEAR);
	    int CurrentMonth = (calendar.get(Calendar.MONTH)+1);
	    String financiyalYearFrom="";
	    if(CurrentMonth<4) {
	        financiyalYearFrom="01/04/"+(CurrentYear-1);
	    } else {
	        financiyalYearFrom="01/04/"+(CurrentYear);
	    }
	    return financiyalYearFrom;
	}
	public static String getFinancialYearTo(Date date) {
		Calendar calendar = DateUtil.getCalendar(date);
		 
		int CurrentYear = calendar.get(Calendar.YEAR);
	    int CurrentMonth = (calendar.get(Calendar.MONTH)+1);
	    String financiyalYearTo="";
	    if(CurrentMonth<4) {
	        financiyalYearTo="31/03/"+(CurrentYear);
	    } else {
	        financiyalYearTo="31/03/"+(CurrentYear+1);
	    }
	    return financiyalYearTo;
	}
	public static String getLastFinancialYearTo(Date date) {
		Calendar calendar = DateUtil.getCalendar(date);
		 
		int CurrentYear = calendar.get(Calendar.YEAR);
	    int CurrentMonth = (calendar.get(Calendar.MONTH)+1);
	    String financiyalYearTo="";
	    if(CurrentMonth<4) {
	        financiyalYearTo="31/03/"+(CurrentYear-1);
	    } else {
	        financiyalYearTo="31/03/"+(CurrentYear);
	    }
	    return financiyalYearTo;
	}
}
