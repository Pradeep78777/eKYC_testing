/**
 * 
 */
package com.codespine.util;

/**
 * @author Pradeep k
 *
 */
public class TokenObject {
	private String user;
	private long expiry;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public long getExpiry() {
		return expiry;
	}

	public void setExpiry(long expiry) {
		this.expiry = expiry;
	}
}