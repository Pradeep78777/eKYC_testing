package com.codespine.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.codespine.data.SchedularDAO;
import com.codespine.dto.IfscCodeDTO;
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
					paramObj.setPin_code(inArr[0]);
					paramObj.setDistrict_code(Integer.parseInt(inArr[1]));
					paramObj.setDistrict_name(inArr[2]);
					paramObj.setState_name(inArr[4]);
					paramObj.setStatus(inArr[5]);
					filesToBeInserted.add(paramObj);
				}
			}
			br.close();
			if (filesToBeInserted != null && filesToBeInserted.size() > 0) {
				String result = SchedularDAO.getInstance().insertPostalCodeDetails(filesToBeInserted);
				if (result.equalsIgnoreCase(eKYCConstant.SUCCESS_MSG)) {
					System.out.println("Inserted Sucessfully");
				} else {
					System.out.println("Insertion Failed");
				}
			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ResponseDTO updateIfscCodeDetails() {
		try {
			List<IfscCodeDTO> filesToBeInserted = new ArrayList<IfscCodeDTO>();
			FileInputStream stream = new FileInputStream(new File(CSEnvVariables.getMethodNames("ifscCodeLocation")));
			InputStreamReader reader = new InputStreamReader(stream);
			BufferedReader br = new BufferedReader(reader);
			String thisLine = "";
			IfscCodeDTO paramObj = new IfscCodeDTO();
			thisLine = br.readLine();
			while ((thisLine = br.readLine()) != null) {
				String[] inArr = thisLine.split("\\~");
				paramObj = new IfscCodeDTO();
				if (inArr != null) {
					paramObj.setBank_id(Integer.parseInt(inArr[0]));
					paramObj.setMicr_code(inArr[1]);
					paramObj.setIfc_code(inArr[2]);
					paramObj.setBank_name(inArr[3]);
					paramObj.setBank_address_1(inArr[4]);
					paramObj.setBank_address_2(inArr[5]);
					paramObj.setBank_address_3(inArr[6]);
					paramObj.setBank_city(inArr[7]);
					paramObj.setBank_state(inArr[8]);
					paramObj.setBank_country(inArr[9]);
					paramObj.setBank_zip_code(inArr[10]);
					paramObj.setBank_phone_1(inArr[11]);
					paramObj.setBank_phone_2(inArr[12]);
					paramObj.setUnique_id(inArr[13]);
					paramObj.setBank_email(inArr[14]);
					paramObj.setBank_contact_name(inArr[15]);
					paramObj.setBank_contact_designation(inArr[16]);
					filesToBeInserted.add(paramObj);
				}
			}
			br.close();
			if (filesToBeInserted != null && filesToBeInserted.size() > 0) {
				String result = SchedularDAO.getInstance().updateIfscCodeDetails(filesToBeInserted);
				if (result.equalsIgnoreCase(eKYCConstant.SUCCESS_MSG)) {
					System.out.println("Inserted Sucessfully");
				} else {
					System.out.println("Insertion Failed");
				}
			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
