package com.codespine.helper;

import com.codespine.data.eKYCDAO;
import com.codespine.dto.AccountHolderDetailsDTO;
import com.codespine.dto.AddressDTO;
import com.codespine.dto.ApplicationMasterDTO;
import com.codespine.dto.BankDetailsDTO;
import com.codespine.dto.PanCardDetailsDTO;
import com.codespine.dto.PersonalDetailsDTO;
import com.codespine.dto.eKYCDTO;

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
			}
		}
		if(applicationMasterDTO.isAccHolderPersonalDtlRequired()) {
			PersonalDetailsDTO personalDetailsDTO = eKYCDAO.getInstance().CheckBasicInformation(applicationId);
			if(personalDetailsDTO != null) {
				eKYCDTO.setPersonalDetailsDTO(personalDetailsDTO);
			}
		}
		if(applicationMasterDTO.isBankAccDtlRequired()) {
			BankDetailsDTO bankDetailsDTO= eKYCDAO.getInstance().checkBankDetailsUpdated(applicationId);
			if(bankDetailsDTO != null) {
				eKYCDTO.setBankDetailsDTO(bankDetailsDTO);
			}
		}
		if(applicationMasterDTO.isCommunicationAddressRequired()) {
			AddressDTO addressDTO=  eKYCDAO.getInstance().checkCommunicationAddress(applicationId);
			if(addressDTO != null) {
				eKYCDTO.setAddressDTO(addressDTO);
			}
		}
		if(applicationMasterDTO.isPanCardDetailRequired()) {
			PanCardDetailsDTO panCardDetailsDTO = eKYCDAO.getInstance().checkPanCardUpdated(applicationId);
			if(panCardDetailsDTO != null) {
				eKYCDTO.setPanCardDetailsDTO(panCardDetailsDTO);
			}
		}
		if(applicationMasterDTO.isPermanentAddressRequired()) {
			AddressDTO addressDTO=  eKYCDAO.getInstance().checkPermanentAddress(applicationId);
			if(addressDTO != null) {
				eKYCDTO.setPermanentAddressDTO(addressDTO);
			}
		}
		return eKYCDTO;
	}
}
