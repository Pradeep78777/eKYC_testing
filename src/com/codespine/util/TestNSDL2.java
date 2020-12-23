package com.codespine.util;

import com.codespine.dto.ExchDetailsDTO;
import com.codespine.dto.eKYCDTO;
import com.codespine.service.eKYCService;

public class TestNSDL2 {
	public static void main(String[] args) {
		try {
			eKYCDTO userApplicationDetails = eKYCService.getInstance().finalPDFGenerator(1);
			ExchDetailsDTO tempDTO = userApplicationDetails.getExchDetailsDTO();
			String exchList = "EXCHANGELIST";
			StringBuffer response = new StringBuffer();

			if (tempDTO.getBcd() == 1) {
				response.append(exchList + "=");
				response.append("CD_BSE&");
			}
			if (tempDTO.getBse_com() == 1) {
				response.append(exchList + "=");
				response.append("BSE_COM&");
			}
			if (tempDTO.getBse_eq() == 1) {
				response.append(exchList + "=");
				response.append("BSE_CASH&");
			}
			if (tempDTO.getBse_fo() == 1) {
				response.append(exchList + "=");
				response.append("BSE_FNO&");
			}
			if (tempDTO.getCds() == 1) {
				response.append(exchList + "=");
				response.append("CD_NSE&");
			}
			if (tempDTO.getIcex() == 1) {
				response.append(exchList + "=");
				response.append("ICEX&");
			}
			if (tempDTO.getMcx() == 1) {
				response.append(exchList + "=");
				response.append("MCX&");
			}
			if (tempDTO.getMf() == 1) {
				response.append(exchList + "=");
				response.append("MF_BSE&");
				response.append(exchList + "=");
				response.append("MF_NSE&");
			}
			if (tempDTO.getNse_com() == 1) {
				response.append(exchList + "=");
				response.append("NSE_COM&");
			}
			if (tempDTO.getNse_eq() == 1) {
				response.append(exchList + "=");
				response.append("NSE_CASH&");
			}
			if (tempDTO.getNse_fo() == 1) {
				response.append(exchList + "=");
				response.append("NSE_FNO&");
			}
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
