package com.codespine.data;

public class ResportsDAO {
	public static ResportsDAO ResportsDAO = null;

	public static ResportsDAO getInstance() {
		if (ResportsDAO == null) {
			ResportsDAO = new ResportsDAO();
		}
		return ResportsDAO;
	}
}
