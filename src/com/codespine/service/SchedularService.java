package com.codespine.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.codespine.data.SchedularDAO;
import com.codespine.dto.PostalCodesDTO;
import com.codespine.dto.ResponseDTO;
import com.codespine.util.CSEnvVariables;
import com.codespine.util.eKYCConstant;

public class SchedularService {
	public static SchedularService SchedularService = null;

	public static SchedularService getInstance() {
		if (SchedularService == null) {
			SchedularService = new SchedularService();
		}
		return SchedularService;
	}

	/**
	 * update the postal codes
	 * 
	 * @return
	 */
	public ResponseDTO updatePostalCodes() {
		try {
			List<PostalCodesDTO> filesToBeInserted = new ArrayList<PostalCodesDTO>();
			FileInputStream stream = new FileInputStream(new File(CSEnvVariables.getMethodNames("postCodesLocation")));
			InputStreamReader reader = new InputStreamReader(stream);
			BufferedReader br = new BufferedReader(reader);
			String thisLine = "";
			PostalCodesDTO paramObj = new PostalCodesDTO();
			thisLine = br.readLine();
			while ((thisLine = br.readLine()) != null) {
				String[] inArr = thisLine.split("\\~");
				paramObj = new PostalCodesDTO();
				if (inArr != null) {
					paramObj.setPin_code(Integer.parseInt(inArr[0]));
					paramObj.setDistrict_code(Integer.parseInt(inArr[1]));
					paramObj.setDistrict_name(inArr[2]);
					paramObj.setState_name(inArr[4]);
					paramObj.setStatus(inArr[5]);
					filesToBeInserted.add(paramObj);
				}
			}
			br.close();
			String result = SchedularDAO.getInstance().insertPostalCodeDetails(filesToBeInserted);
			if (result.equalsIgnoreCase(eKYCConstant.SUCCESS_MSG)) {
				System.out.println("Inserted Sucessfully");
			} else {
				System.out.println("Insertion Failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
