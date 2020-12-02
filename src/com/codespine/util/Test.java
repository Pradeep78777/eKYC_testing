package com.codespine.util;

import java.util.List;

import com.codespine.data.AdminDAO;
import com.codespine.dto.ApplicationLogDTO;

public class Test {

	public static void main(String[] args) {
		try {
//			List<ApplicationLogDTO> result = AdminDAO.getInstance().rejectedDocuments(1);
//			Utility.applicationRejectedMessage("gowrisankar@stoneagesolutions.com", "Gowrisankar", result);
			Utility.stringToJson("1^cntpk5744h^E^KRITHIHASRI^CHINNASWAMY^VENUGOPAL^Kumari^01/06/2012^KRITHIHASRI C V^Y^");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
