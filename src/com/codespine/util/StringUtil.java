package com.codespine.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StringUtil {
	
	public static boolean isNotNullOrEmptyAfterTrim(String str){
		if(StringUtil.isNotNullOrEmpty(str) && str.trim().length()>0 ){
			return true;
		}else{
			return false;
		}
	}
	public static boolean isEqual(String str1, String str2){
		boolean isEqual = false;
		if(str1!=null && str2!=null
				&& str1.equalsIgnoreCase(str2)){
			isEqual = true;
		}
		return isEqual;
	}
	public static boolean isNotNullOrEmpty(String str){
		return !isNullOrEmpty(str);
	}
	public static boolean isNullOrEmpty(String str){
		if(str != null && str.length()>0 ){
			return false;
		}else{
			return true;
		}
	}
	public static boolean isNotEqual(String str1, String str2){
		return !isEqual(str1, str2);
	}
	public static boolean isNullOrEqual(String str1, String str2){
		boolean isNullOrEqual = false;
		if(str1==null && str2==null){
			isNullOrEqual = true;
		}else {
			isNullOrEqual = isEqual(str1, str2);
		}
		return isNullOrEqual;
	}
	public static String[] split(String str, String seperator){
		String strArray[] = null;
		if(isNotNullOrEmpty(str)){
			strArray = str.split(seperator);
		}
		return strArray;
	}
	public static List<String> splitOnlyNonEmpty(String str, String seperator){
		List<String> values = new ArrayList<String>();
		String strArray[] = split(str, seperator);
		if(strArray!=null){
			for(String val : strArray){
				if(StringUtil.isNotNullOrEmpty(val)){
					values.add(val);
				}
			}
		}
		return values;
	}
	public static String reverseString(String str){
		return new StringBuffer(str).reverse().toString();
	}
	public static String replace(String str, String toBeReplaced, String toReplaceWith){
		if(isNotNullOrEmpty(str) && isNotNullOrEmpty(toBeReplaced)){
			str = str.replace(toBeReplaced, getString(toReplaceWith));
		}
		return str;
	}
	public static String getString(String str){
		return StringUtil.isNullOrEmpty(str)? "" : str;
	}
	public static String isImageReSizeExist(String imageFilepath, String image) {
		for(int i=0;i<=1000;i++) {
			String fileName = i+"-"+image;
			File tmpDir = new File(imageFilepath+fileName);
			if(!tmpDir.exists()) {
				fileName = i+"-"+image;
				return fileName;
			}
		}
		return null;
	}
}
