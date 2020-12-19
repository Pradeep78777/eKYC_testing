package com.codespine.helper;

import java.util.HashMap;

import com.codespine.data.eKYCDAO;
import com.codespine.dto.AccountHolderDetailsDTO;
import com.codespine.dto.AddressDTO;
import com.codespine.dto.ApplicationMasterDTO;
import com.codespine.dto.BankDetailsDTO;
import com.codespine.dto.ExchDetailsDTO;
import com.codespine.dto.PanCardDetailsDTO;
import com.codespine.dto.PersonalDetailsDTO;
import com.codespine.dto.eKYCDTO;
import com.codespine.util.StringUtil;

public class eKYCHelper {
	public static eKYCHelper eKYCHelper = null;
	public static eKYCHelper getInstance() {
		if(eKYCHelper == null) {
			eKYCHelper = new eKYCHelper();
		}
		return eKYCHelper;
	}

	/**
	 * Method to get poluate full detail by Application id
	 * 
	 * @author Pradeep R
	 * @param applicationMasterDTO,eKYCDTO
	 * @return eKYCDTO
	 */
	@SuppressWarnings("static-access")
	public eKYCDTO populateRerquiredFields(ApplicationMasterDTO applicationMasterDTO, eKYCDTO eKYCDTO) {
		int applicationId =applicationMasterDTO.getApplication_id();
		if(applicationMasterDTO.isAccHolderDtlRequired()) {
			AccountHolderDetailsDTO accountHolderDetailsDTO = eKYCDAO.getInstance().getAccountHolderDetail(applicationId);
			if(accountHolderDetailsDTO != null) {
				eKYCDTO.setAccountHolderDetailsDTO(accountHolderDetailsDTO);
				eKYCDTO.getForPDFKeyValue().putAll(accountHolderDetailsDTO.getForPDFKeyValue());
			}
		}
		if(applicationMasterDTO.isAccHolderPersonalDtlRequired()) {
			PersonalDetailsDTO personalDetailsDTO = eKYCDAO.getInstance().CheckBasicInformation(applicationId);
			if(personalDetailsDTO != null) {
				eKYCDTO.setPersonalDetailsDTO(personalDetailsDTO);
				eKYCDTO.getForPDFKeyValue().putAll(personalDetailsDTO.getForPDFKeyValue());
			}
		}
		if(applicationMasterDTO.isBankAccDtlRequired()) {
			BankDetailsDTO bankDetailsDTO= eKYCDAO.getInstance().checkBankDetailsUpdated(applicationId);
			if(bankDetailsDTO != null) {
				eKYCDTO.setBankDetailsDTO(bankDetailsDTO);
				eKYCDTO.getForPDFKeyValue().putAll(bankDetailsDTO.getForPDFKeyValue());
			}
		}
		if(applicationMasterDTO.isCommunicationAddressRequired()) {
			AddressDTO addressDTO=  eKYCDAO.getInstance().checkCommunicationAddress(applicationId);
			if(addressDTO != null) {
				eKYCDTO.setAddressDTO(addressDTO);
				eKYCDTO.getForPDFKeyValue().putAll(addressDTO.getForPDFKeyValue());
			}
		}
		if(applicationMasterDTO.isPanCardDetailRequired()) {
			PanCardDetailsDTO panCardDetailsDTO = eKYCDAO.getInstance().checkPanCardUpdated(applicationId);
			if(panCardDetailsDTO != null) {
				eKYCDTO.setPanCardDetailsDTO(panCardDetailsDTO);
				eKYCDTO.getForPDFKeyValue().putAll(panCardDetailsDTO.getForPDFKeyValue());
			}
			if(StringUtil.isNotNullOrEmpty(eKYCDTO.getForPDFKeyValue().get("pan_card"))&&
					StringUtil.isNotNullOrEmpty(eKYCDTO.getForPDFKeyValue().get("applicant_name"))) {
				HashMap<String, String> json = new HashMap<String, String>();
				json.put("applicant_pan_card",eKYCDTO.getForPDFKeyValue().get("applicant_name") + "  (" +eKYCDTO.getForPDFKeyValue().get("pan_card")+")");
				eKYCDTO.getForPDFKeyValue().putAll(json);
			}
		}
		if(applicationMasterDTO.isPermanentAddressRequired()) {
			AddressDTO addressDTO=  eKYCDAO.getInstance().checkPermanentAddress(applicationId);
			if(addressDTO != null) {
				eKYCDTO.setPermanentAddressDTO(addressDTO);
				eKYCDTO.getForPDFKeyValue().putAll(addressDTO.getForPDFKeyValue());
			}
		}
		if(applicationMasterDTO.isExchDetailsRequired()) {
			ExchDetailsDTO exchDetailsDTO=  eKYCDAO.getInstance().checkExchUpdatedStatus(applicationId);
			if(exchDetailsDTO != null) {
				eKYCDTO.setExchDetailsDTO(exchDetailsDTO);
				eKYCDTO.getForPDFKeyValue().putAll(exchDetailsDTO.getForPDFKeyValue());
			}
		}
		return eKYCDTO;
	}
}
