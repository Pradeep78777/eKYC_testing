package com.codespine.util;

import java.util.List;

import com.codespine.data.AdminDAO;
import com.codespine.dto.ApplicationLogDTO;

public class Test {

	public static void main(String[] args) {
		try {
			List<ApplicationLogDTO> result = AdminDAO.getInstance().rejectedDocuments(1);
			Utility.applicationRejectedMessage("gowrisankar@stoneagesolutions.com", "Gowrisankar", result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
