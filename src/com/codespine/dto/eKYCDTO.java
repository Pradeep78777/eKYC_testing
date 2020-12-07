package com.codespine.dto;

import java.util.List;
import java.util.Map;

public class eKYCDTO {
		private ApplicationMasterDTO applicationMasterDTO;
		private AddressDTO addressDTO;
		private AddressDTO PermanentAddressDTO;
		private BankDetailsDTO bankDetailsDTO;
		private PanCardDetailsDTO panCardDetailsDTO;
		private PersonalDetailsDTO personalDetailsDTO;
		private ExchDetailsDTO exchDetailsDTO;
		private ResponseDTO responseDTO;
		private List<ApplicationMasterDTO> ApplicationMasterDTOs;
		private List<AddressDTO> addressDTOs;
		private List<AddressDTO>  PermanentAddressDTOs;
		private List<BankDetailsDTO> bankDetailsDTOs;
		private List<PanCardDetailsDTO > panCardDetailsDTOs;
		private List<PersonalDetailsDTO> personalDetailsDTOs;
		private List<ResponseDTO> responseDTOs;
		private AccountHolderDetailsDTO accountHolderDetailsDTO;
		private List<AccountHolderDetailsDTO> accountHolderDetailsDTOs;
		private List<ExchDetailsDTO> exchDetailsDTOs;
		private Map<String,String>  forPDFKeyValue; 
		public ApplicationMasterDTO getApplicationMasterDTO() {
			return applicationMasterDTO;
		}
		public void setApplicationMasterDTO(ApplicationMasterDTO applicationMasterDTO) {
			this.applicationMasterDTO = applicationMasterDTO;
		}
		public AddressDTO getAddressDTO() {
			return addressDTO;
		}
		public void setAddressDTO(AddressDTO addressDTO) {
			this.addressDTO = addressDTO;
		}
		public BankDetailsDTO getBankDetailsDTO() {
			return bankDetailsDTO;
		}
		public void setBankDetailsDTO(BankDetailsDTO bankDetailsDTO) {
			this.bankDetailsDTO = bankDetailsDTO;
		}
		public PanCardDetailsDTO getPanCardDetailsDTO() {
			return panCardDetailsDTO;
		}
		public void setPanCardDetailsDTO(PanCardDetailsDTO panCardDetailsDTO) {
			this.panCardDetailsDTO = panCardDetailsDTO;
		}
		public PersonalDetailsDTO getPersonalDetailsDTO() {
			return personalDetailsDTO;
		}
		public void setPersonalDetailsDTO(PersonalDetailsDTO personalDetailsDTO) {
			this.personalDetailsDTO = personalDetailsDTO;
		}
		public ResponseDTO getResponseDTO() {
			return responseDTO;
		}
		public void setResponseDTO(ResponseDTO responseDTO) {
			this.responseDTO = responseDTO;
		}
		public List<ApplicationMasterDTO> getApplicationMasterDTOs() {
			return ApplicationMasterDTOs;
		}
		public void setApplicationMasterDTOs(List<ApplicationMasterDTO> applicationMasterDTOs) {
			ApplicationMasterDTOs = applicationMasterDTOs;
		}
		public List<AddressDTO> getAddressDTOs() {
			return addressDTOs;
		}
		public void setAddressDTOs(List<AddressDTO> addressDTOs) {
			this.addressDTOs = addressDTOs;
		}
		public List<BankDetailsDTO> getBankDetailsDTOs() {
			return bankDetailsDTOs;
		}
		public void setBankDetailsDTOs(List<BankDetailsDTO> bankDetailsDTOs) {
			this.bankDetailsDTOs = bankDetailsDTOs;
		}
		public List<PanCardDetailsDTO> getPanCardDetailsDTOs() {
			return panCardDetailsDTOs;
		}
		public void setPanCardDetailsDTOs(List<PanCardDetailsDTO> panCardDetailsDTOs) {
			this.panCardDetailsDTOs = panCardDetailsDTOs;
		}
		public List<PersonalDetailsDTO> getPersonalDetailsDTOs() {
			return personalDetailsDTOs;
		}
		public void setPersonalDetailsDTOs(List<PersonalDetailsDTO> personalDetailsDTOs) {
			this.personalDetailsDTOs = personalDetailsDTOs;
		}
		public List<ResponseDTO> getResponseDTOs() {
			return responseDTOs;
		}
		public void setResponseDTOs(List<ResponseDTO> responseDTOs) {
			this.responseDTOs = responseDTOs;
		}
		public AccountHolderDetailsDTO getAccountHolderDetailsDTO() {
			return accountHolderDetailsDTO;
		}
		public void setAccountHolderDetailsDTO(AccountHolderDetailsDTO accountHolderDetailsDTO) {
			this.accountHolderDetailsDTO = accountHolderDetailsDTO;
		}
		public List<AccountHolderDetailsDTO> getAccountHolderDetailsDTOs() {
			return accountHolderDetailsDTOs;
		}
		public void setAccountHolderDetailsDTOs(List<AccountHolderDetailsDTO> accountHolderDetailsDTOs) {
			this.accountHolderDetailsDTOs = accountHolderDetailsDTOs;
		}
		public AddressDTO getPermanentAddressDTO() {
			return PermanentAddressDTO;
		}
		public void setPermanentAddressDTO(AddressDTO permanentAddressDTO) {
			PermanentAddressDTO = permanentAddressDTO;
		}
		public List<AddressDTO> getPermanentAddressDTOs() {
			return PermanentAddressDTOs;
		}
		public void setPermanentAddressDTOs(List<AddressDTO> permanentAddressDTOs) {
			PermanentAddressDTOs = permanentAddressDTOs;
		}
		public Map<String, String> getForPDFKeyValue() {
			return forPDFKeyValue;
		}
		public void setForPDFKeyValue(Map<String, String> forPDFKeyValue) {
			this.forPDFKeyValue = forPDFKeyValue;
		}
		public ExchDetailsDTO getExchDetailsDTO() {
			return exchDetailsDTO;
		}
		public void setExchDetailsDTO(ExchDetailsDTO exchDetailsDTO) {
			this.exchDetailsDTO = exchDetailsDTO;
		}
		public List<ExchDetailsDTO> getExchDetailsDTOs() {
			return exchDetailsDTOs;
		}
		public void setExchDetailsDTOs(List<ExchDetailsDTO> exchDetailsDTOs) {
			this.exchDetailsDTOs = exchDetailsDTOs;
		}
		
}
