package com.codespine.cache;

import java.util.Map;
import java.util.TreeMap;

public class CacheController {

	private static Map<Integer, String> userKeyMap = new TreeMap<Integer, String>();
	private static Map<String, Long> keyTimeMap = new TreeMap<String, Long>();

	public static Map<Integer, String> getUserKeyMap() {
		return userKeyMap;
	}

	public static void setUserKeyMap(Map<Integer, String> userKeyMap) {
		CacheController.userKeyMap = userKeyMap;
	}

	public static Map<String, Long> getKeyTimeMap() {
		return keyTimeMap;
	}

	public static void setKeyTimeMap(Map<String, Long> keyTimeMap) {
		CacheController.keyTimeMap = keyTimeMap;
	}

}
