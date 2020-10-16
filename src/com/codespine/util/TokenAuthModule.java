package com.codespine.util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.NotAuthorizedException;

public class TokenAuthModule {

	public static Map<String, TokenObject> tokenKeyCache = new HashMap<>();
	public static Map<String, String> userIdKeyCache = new HashMap<>();

	public static Map<String, TokenObject> getTokenKeyCache() {
		return tokenKeyCache;
	}

	public static void setTokenKeyCache(Map<String, TokenObject> tokenKeyCache) {
		TokenAuthModule.tokenKeyCache = tokenKeyCache;
	}

	public static Map<String, String> getUserIdKeyCache() {
		return userIdKeyCache;
	}

	public static void setUserIdKeyCache(Map<String, String> userIdKeyCache) {
		TokenAuthModule.userIdKeyCache = userIdKeyCache;
	}

	/**
	 * Method validates the provided token and returns the user id for
	 * processing
	 * 
	 * @param pToken
	 */
	public static void validateToken(String pToken, String userId) {
		try {
			TokenObject tokenObj = tokenKeyCache.get(pToken);
			// System.out.println(tokenObj.getUser().equalsIgnoreCase(userId));
			if (tokenObj == null || tokenObj.getExpiry() < Calendar.getInstance().getTimeInMillis()
					|| !tokenObj.getUser().equalsIgnoreCase(userId)) {
				throw new NotAuthorizedException("Not Authorized");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to store session id for the respective user id
	 * 
	 * @param userId
	 * @param sessionId
	 */
	public static void storeToken(String userId, String sessionId) {
		final String token = userIdKeyCache.get(userId); // Get token (if exist)
															// with key of user
															// Id

		/**
		 * User token already exist but user logged in again, this will
		 * invalidate the existing token from any other device #Note: This will
		 * not suitable for multi login
		 * 
		 */
		if (token != null && tokenKeyCache.get(token).getUser().equalsIgnoreCase(userId)) {
			tokenKeyCache.remove(token);
		}

		/*
		 * Store the token with object as value and set expiry till midnight
		 * (00:00:00)
		 */

		final TokenObject obj = new TokenObject();
		obj.setExpiry(Utility.getExpirySeconds());
		obj.setUser(userId);
		tokenKeyCache.put(sessionId, obj);
		userIdKeyCache.put(userId, sessionId);
	}

}
