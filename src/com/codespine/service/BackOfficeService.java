package com.codespine.service;

import com.codespine.dto.eKYCDTO;
import com.codespine.restservice.BackOfficeRestService;
import com.codespine.util.BackOfficeContants;

public class BackOfficeService {

	public static BackOfficeService BackOfficeService = null;

	public static BackOfficeService getInstance() {
		if (BackOfficeService == null) {
			BackOfficeService = new BackOfficeService();
		}
		return BackOfficeService;
	}

	String symbol = "&";
	String equalSymbol = "=";

	String category = "I";

	public void sendBackOffice(int applicationId) {
		eKYCDTO userApplicationDetails = eKYCService.getInstance().finalPDFGenerator(applicationId);
		if (userApplicationDetails != null) {
			String parameter = buildURL(userApplicationDetails);
			BackOfficeRestService.getInstance().postDataToBackEnd(parameter);
			System.out.println(parameter);
		}
	}

	private String buildURL(eKYCDTO userApplicationDetails) {

		String ckycflag = "N";
		String fatca_country = "+";
		String krarecup = "N";
		String krarec = "N";
		String kra_sta = "+";
		String payment_type = "+";
		String trustbasepayin = "NO";
		String nachflag = "N";
		String foreign_country = "+";
		String client_divpaymode = "+";
		String tax_status = "+";
		String holding_nature = "+";
		String stetement_communication_mode = "+";
		String introduced_by = "+";
		String office_country = "+";
		String off_state = "None";
		String risk_catg = "Low";
		String csc_flg = "N";
		String grossannualincomedate = "18/12/2020";
		String annual_income = "Less+Than+One+Lakhs";
		String person_verify_designation = "PERSON_VERIFY_DESIGNATION";
		String person_verify_name = "PERSON_VERIFY_NAME";
		String inperson_date = "18/12/2020";
		String person_verify = "Y";
		String mtfclauto = "N";
		String mtfcl = "N";
		String reward_points_scheme_enable = "Y";
		String ibt_sharing_type = "+";
		String payout_from_ben = "+";
		String ben_hold_charge = "N";
		String pool_hold_charge = "N";
		String inter_settlement_charge = "N";
		String beninwardchgs = "N";
		String bentoclient_charge = "Y";
		String interest_calc = "";
		String isinternettrading = "+";
		String onlinetrdfilegentype = "N";
		String cl_op_chgs_debited = "+";
		String min_op_charges = "+";
		String nri_type = "+";
		String nationality = "I";
		String occupation = "STUDENT";
		String qualification = "+";
		String political_affilication = userApplicationDetails.getPersonalDetailsDTO().getPolitically_exposed();
		String client_webxid = "";
		String webac = "WEBAC";
		String r_country = "INDIA";
		String r_state = userApplicationDetails.getAddressDTO().getState();
		String r_city = userApplicationDetails.getAddressDTO().getCity();
		String r_pin_code = String.valueOf(userApplicationDetails.getAddressDTO().getPin());
		String reg_addr1 = userApplicationDetails.getAddressDTO().getFlat_no() + " , "
				+ userApplicationDetails.getAddressDTO().getStreet();
		String correspondance_address_proof = "25";
		String nripis = "1";
		String oblcust = "N";
		String ccs_cl = "N";
		String sms_send = "Y";
		String retantion_autho_letter = "N";
		String clientdeliverytoben = "+";
		String delivery_type = "1";
		String cusi_client = "N";
		String contract_option = "+";
		String fo_inst_csv = "+";
		String inst_csv = "+";
		String payment_request = "PayOut";
		String smartreportright = "Y";
		String mrgdbt = "+";
		String html_contract = "Y";
		String master_pan_purpose = "4";
		String relationship = "+";
		String typeoffacility = "3";
		String updationflag = "Y";
		String resi_tel_no = "0";
		String country = "INDIA";
		String state = userApplicationDetails.getAddressDTO().getState();
		String city = userApplicationDetails.getAddressDTO().getCity();
		String pin_code = String.valueOf(userApplicationDetails.getAddressDTO().getPin());
		String resi_address1 = userApplicationDetails.getAddressDTO().getFlat_no() + ", "
				+ userApplicationDetails.getAddressDTO().getStreet();
		String address_proof1 = "25";
		String not_boid = "12345678";
		String not_dpid = "12345678";
		String not_poa = "Y";
		String not_eft = "Y";
		String not_bank_name = userApplicationDetails.getBankDetailsDTO().getBankName();
		String not_micrno = userApplicationDetails.getBankDetailsDTO().getMicrCode();
		String not_bankccountnno = userApplicationDetails.getBankDetailsDTO().getBank_account_no();
		String not_bankactype = userApplicationDetails.getBankDetailsDTO().getAccount_type();
		String mother_name = userApplicationDetails.getPersonalDetailsDTO().getMothersName();
		String sex = "M";
		String serial_no = "12345678";
		String residential_status = "RI";
		String marital_status = "S";
		// String birth_date =
		// userApplicationDetails.getPersonalDetailsDTO().getDob();
		String birth_date = "09/06/1997";
		String father_husband_name = userApplicationDetails.getPersonalDetailsDTO().getFathersName();
		String pan_name = userApplicationDetails.getPanCardDetailsDTO().getApplicant_name();
		String client_name = userApplicationDetails.getPanCardDetailsDTO().getApplicant_name();
		String last_name = userApplicationDetails.getPanCardDetailsDTO().getLast_name();
		String middle_name = userApplicationDetails.getPanCardDetailsDTO().getMiddle_name();
		String first_name = userApplicationDetails.getPanCardDetailsDTO().getFirst_name();
		String title = "MR";
		String agreement_date = "18/12/2020";
		String exchangelist = "NSE_CASH";
		String client_nature = "C";
		String pan_proof = "01";
		String pan_no = userApplicationDetails.getPanCardDetailsDTO().getPan_card();
		String client_id = "CLIENT_ID";
		String branch_code = "TN3";
		String g = "Submit";

		String temp = BackOfficeContants.G + equalSymbol + g + symbol + BackOfficeContants.BRANCH_CODE + equalSymbol
				+ branch_code + symbol + BackOfficeContants.CLIENT_ID + equalSymbol + client_id + symbol
				+ BackOfficeContants.PAN_NO + equalSymbol + pan_no + symbol + BackOfficeContants.PAN_PROOF + equalSymbol
				+ pan_proof + symbol + BackOfficeContants.CLIENT_NATURE + equalSymbol + client_nature + symbol
				+ BackOfficeContants.CATEGORY + equalSymbol + category + symbol + BackOfficeContants.EXCHANGELIST
				+ equalSymbol + exchangelist + symbol + BackOfficeContants.AGREEMENT_DATE + equalSymbol + agreement_date
				+ symbol + BackOfficeContants.TITLE + equalSymbol + "" + symbol + BackOfficeContants.FIRST_NAME
				+ equalSymbol + first_name + symbol + BackOfficeContants.MIDDLE_NAME + equalSymbol + middle_name
				+ symbol + BackOfficeContants.LAST_NAME + equalSymbol + last_name + symbol
				+ BackOfficeContants.CLIENT_NAME + equalSymbol + client_name + symbol + BackOfficeContants.PAN_NAME
				+ equalSymbol + pan_name + symbol + BackOfficeContants.FATHER_HUSBAND_NAME + equalSymbol
				+ father_husband_name + symbol + BackOfficeContants.BIRTH_DATE + equalSymbol + birth_date + symbol
				+ BackOfficeContants.MARITAL_STATUS + equalSymbol + marital_status + symbol
				+ BackOfficeContants.RESIDENTIAL_STATUS + equalSymbol + residential_status + symbol
				+ BackOfficeContants.SERIAL_NO + equalSymbol + serial_no + symbol + BackOfficeContants.SEX + equalSymbol
				+ sex + symbol + BackOfficeContants.MOTHER_NAME + equalSymbol + mother_name + symbol
				+ BackOfficeContants.NOT_BANKACTYPE + equalSymbol + not_bankactype + symbol
				+ BackOfficeContants.NOT_BANKCCOUNTNNO + equalSymbol + not_bankccountnno + symbol
				+ BackOfficeContants.NOT_CIFNO + equalSymbol + "" + symbol + BackOfficeContants.NOT_MICRNO + equalSymbol
				+ not_micrno + symbol + BackOfficeContants.NOT_BANK_NAME + equalSymbol + not_bank_name + symbol
				+ BackOfficeContants.NOT_IFSC + equalSymbol + "" + symbol + BackOfficeContants.NOT_EFT + equalSymbol
				+ not_eft + symbol + BackOfficeContants.NRI_PSI_NO + equalSymbol + "" + symbol
				+ BackOfficeContants.NOT_POA + equalSymbol + not_poa + symbol + BackOfficeContants.NOT_DPID
				+ equalSymbol + not_dpid + symbol + BackOfficeContants.NOT_BOID + equalSymbol + not_boid + symbol
				+ BackOfficeContants.ADDRESS_PROOF1 + equalSymbol + address_proof1 + symbol
				+ BackOfficeContants.RESI_ADDRESS1 + equalSymbol + resi_address1 + symbol
				+ BackOfficeContants.RESI_ADDRESS2 + equalSymbol + "" + symbol + BackOfficeContants.RESI_ADDRESS3
				+ equalSymbol + "" + symbol + BackOfficeContants.PIN_CODE + equalSymbol + pin_code + symbol
				+ BackOfficeContants.CITY + equalSymbol + city + symbol + BackOfficeContants.STATE + equalSymbol + state
				+ symbol + BackOfficeContants.COUNTRY + equalSymbol + country + symbol + BackOfficeContants.ISD_CODE
				+ equalSymbol + "" + symbol + BackOfficeContants.STD_CODE + equalSymbol + "" + symbol
				+ BackOfficeContants.RESI_TEL_NO + equalSymbol + resi_tel_no + symbol + BackOfficeContants.RESI_FAX_NO
				+ equalSymbol + "" + symbol + BackOfficeContants.MOBILE_NO + equalSymbol + "" + symbol
				+ BackOfficeContants.EMAIL_ID + equalSymbol + "" + symbol + BackOfficeContants.EMAIL_ID_BCC
				+ equalSymbol + "" + symbol + BackOfficeContants.UPDATIONFLAG + equalSymbol + updationflag + symbol
				+ BackOfficeContants.TYPEOFFACILITY + equalSymbol + typeoffacility + symbol
				+ BackOfficeContants.MASTERPAN + equalSymbol + "" + symbol + BackOfficeContants.RELATIONSHIP
				+ equalSymbol + relationship + symbol + BackOfficeContants.MASTER_PAN_PURPOSE + equalSymbol
				+ master_pan_purpose + symbol + BackOfficeContants.POA_FUNDS_PAYINDATE + equalSymbol + "" + symbol
				+ BackOfficeContants.POA_SECURITY_PAYINDATE + equalSymbol + "" + symbol
				+ BackOfficeContants.HTML_CONTRACT + equalSymbol + html_contract + symbol + BackOfficeContants.MRGDBT
				+ equalSymbol + mrgdbt + symbol + BackOfficeContants.SMARTREPORTRIGHT + equalSymbol + smartreportright
				+ symbol + BackOfficeContants.PAYMENT_REQUEST + equalSymbol + payment_request + symbol
				+ BackOfficeContants.INST_CSV + equalSymbol + inst_csv + symbol + BackOfficeContants.FO_INST_CSV
				+ equalSymbol + fo_inst_csv + symbol + BackOfficeContants.CONTRACT_OPTION + equalSymbol
				+ contract_option + symbol + BackOfficeContants.CUSI_CLIENT + equalSymbol + cusi_client + symbol
				+ BackOfficeContants.NOTE1 + equalSymbol + "" + symbol + BackOfficeContants.NOTE2 + equalSymbol + ""
				+ symbol + BackOfficeContants.NOTE3 + equalSymbol + "" + symbol + BackOfficeContants.DELIVERY_TYPE
				+ equalSymbol + delivery_type + symbol + BackOfficeContants.CLIENTDELIVERYTOBEN + equalSymbol
				+ clientdeliverytoben + symbol + BackOfficeContants.GSTIN_C + equalSymbol + "" + symbol
				+ BackOfficeContants.RETANTION_AUTHO_LETTER + equalSymbol + retantion_autho_letter + symbol
				+ BackOfficeContants.SMS_SEND + equalSymbol + sms_send + symbol + BackOfficeContants.CCS_CL
				+ equalSymbol + ccs_cl + symbol + BackOfficeContants.OBLCUST + equalSymbol + oblcust + symbol
				+ BackOfficeContants.NRIPIS + equalSymbol + nripis + symbol
				+ BackOfficeContants.CORRESPONDANCE_ADDRESS_PROOF + equalSymbol + correspondance_address_proof + symbol
				+ BackOfficeContants.REG_ADDR1 + equalSymbol + reg_addr1 + symbol + BackOfficeContants.REG_ADDR2
				+ equalSymbol + "" + symbol + BackOfficeContants.REG_ADDR3 + equalSymbol + "" + symbol
				+ BackOfficeContants.R_PIN_CODE + equalSymbol + r_pin_code + symbol + BackOfficeContants.R_CITY
				+ equalSymbol + r_city + symbol + BackOfficeContants.R_STATE + equalSymbol + r_state + symbol
				+ BackOfficeContants.R_COUNTRY + equalSymbol + r_country + symbol + BackOfficeContants.FAMILY_GROUP
				+ equalSymbol + "" + symbol + BackOfficeContants.RELATIONMANAGER_CODE + equalSymbol + "" + symbol
				+ BackOfficeContants.RELATIONSHIPOFFICER_CODE + equalSymbol + "" + symbol
				+ BackOfficeContants.TERMINALID + equalSymbol + "" + symbol + BackOfficeContants.DEALER_CODE
				+ equalSymbol + "" + symbol + BackOfficeContants.INTRODUCER_CODE + equalSymbol + "" + symbol
				+ BackOfficeContants.GROUP_CODE + equalSymbol + "" + symbol + BackOfficeContants.WEBAC + equalSymbol
				+ webac + symbol + BackOfficeContants.CLIENT_WEBXID + equalSymbol + client_webxid + symbol
				+ BackOfficeContants.APPLICATION_NO + equalSymbol + "" + symbol
				+ BackOfficeContants.POLITICAL_AFFILICATION + equalSymbol + political_affilication + symbol
				+ BackOfficeContants.QUALIFICATION + equalSymbol + qualification + symbol
				+ BackOfficeContants.QUALIFICATION_OTHERS + equalSymbol + "" + symbol + BackOfficeContants.OCCUPATION
				+ equalSymbol + occupation + symbol + BackOfficeContants.OCCUPATION_OTHERS + equalSymbol + "" + symbol
				+ BackOfficeContants.NATIONALITY + equalSymbol + nationality + symbol
				+ BackOfficeContants.NATIONALITY_OTHERS + equalSymbol + "" + symbol + BackOfficeContants.NRI_TYPE
				+ equalSymbol + nri_type + symbol + BackOfficeContants.MIN_OP_CHARGES + equalSymbol + min_op_charges
				+ symbol + BackOfficeContants.CL_OP_CHGS_CHQ + equalSymbol + "" + symbol
				+ BackOfficeContants.CL_OP_CHGS_DEBITED + equalSymbol + cl_op_chgs_debited + symbol
				+ BackOfficeContants.ONLINETRDFILEGENTYPE + equalSymbol + onlinetrdfilegentype + symbol
				+ BackOfficeContants.ISINTERNETTRADING + equalSymbol + isinternettrading + symbol
				+ BackOfficeContants.ODIN_DIETSEGMENT + equalSymbol + "" + symbol
				+ BackOfficeContants.NOW_CATEGORY_GLOBAL + equalSymbol + "" + symbol + BackOfficeContants.NOW_DEALERID
				+ equalSymbol + "" + symbol + BackOfficeContants.NOW_SEGMENT + equalSymbol + "" + symbol
				+ BackOfficeContants.NOW_CATEGORY + equalSymbol + "" + symbol + BackOfficeContants.PROFILEFORCERC
				+ equalSymbol + "" + symbol + BackOfficeContants.CERC_CATEGORY + equalSymbol + "" + symbol
				+ BackOfficeContants.INTERNET_TRADING_ID + equalSymbol + "" + symbol + BackOfficeContants.CLEAN_LIMIT
				+ equalSymbol + "" + symbol + BackOfficeContants.EMPLOYEE_ID + equalSymbol + "" + symbol
				+ BackOfficeContants.INTEREST_CALC + equalSymbol + interest_calc + symbol + BackOfficeContants.CRDAYS
				+ equalSymbol + "" + symbol + BackOfficeContants.DRDAYS + equalSymbol + "" + symbol
				+ BackOfficeContants.FOCRDAYS + equalSymbol + "" + symbol + BackOfficeContants.FODRDAYS + equalSymbol
				+ "" + symbol + BackOfficeContants.DR_INTEREST + equalSymbol + "" + symbol
				+ BackOfficeContants.CR_INTEREST + equalSymbol + "" + symbol + BackOfficeContants.ADD_INTEREST
				+ equalSymbol + "" + symbol + BackOfficeContants.FIX_INT_AMT + equalSymbol + "" + symbol
				+ BackOfficeContants.INT_AMOUNT_RANGE + equalSymbol + "" + symbol
				+ BackOfficeContants.REMESHIRE_INTEREST + equalSymbol + "" + symbol
				+ BackOfficeContants.BENTOMARKET_CHARGE + equalSymbol + "" + symbol + BackOfficeContants.BTM_PER
				+ equalSymbol + "" + symbol + BackOfficeContants.BTM_MAX + equalSymbol + "" + symbol
				+ BackOfficeContants.BTM_MIN + equalSymbol + "" + symbol + BackOfficeContants.BTM_CHARGE + equalSymbol
				+ "" + symbol + BackOfficeContants.BENTOCLIENT_CHARGE + equalSymbol + bentoclient_charge + symbol
				+ BackOfficeContants.BTC_PER + equalSymbol + "" + symbol + BackOfficeContants.BTC_MAX + equalSymbol + ""
				+ symbol + BackOfficeContants.BTC_MIN + equalSymbol + "" + symbol + BackOfficeContants.BTC_CHARGE
				+ equalSymbol + "" + symbol + BackOfficeContants.BENINWARDCHGS + equalSymbol + beninwardchgs + symbol
				+ BackOfficeContants.BTB_PER + equalSymbol + "" + symbol + BackOfficeContants.BTB_MAX + equalSymbol + ""
				+ symbol + BackOfficeContants.BTB_MIN + equalSymbol + "" + symbol + BackOfficeContants.BTB_CHARGE
				+ equalSymbol + "" + symbol + BackOfficeContants.INTER_SETTLEMENT_CHARGE + equalSymbol
				+ inter_settlement_charge + symbol + BackOfficeContants.POOL_HOLD_CHARGE + equalSymbol
				+ pool_hold_charge + symbol + BackOfficeContants.BEN_HOLD_CHARGE + equalSymbol + ben_hold_charge
				+ symbol + BackOfficeContants.PAYOUT_FROM_BEN + equalSymbol + payout_from_ben + symbol
				+ BackOfficeContants.IBT_SHARING_CLIENT_CODE + equalSymbol + "" + symbol
				+ BackOfficeContants.IBT_SHARING + equalSymbol + "" + symbol + BackOfficeContants.IBT_SHARING_TYPE
				+ equalSymbol + ibt_sharing_type + symbol + BackOfficeContants.IBT_SHARING_CLIENT_CODE1 + equalSymbol
				+ "" + symbol + BackOfficeContants.IBT_SHARING_CL1 + equalSymbol + "" + symbol
				+ BackOfficeContants.IBT_SHARING_TILLDATE + equalSymbol + "" + symbol
				+ BackOfficeContants.REWARD_POINTS_SCHEME_ENABLE + equalSymbol + reward_points_scheme_enable + symbol
				+ BackOfficeContants.MFT_INTEREST + equalSymbol + "" + symbol + BackOfficeContants.MFT_MAX_AMOUNT
				+ equalSymbol + "" + symbol + BackOfficeContants.MFT_SCRIP_MAX_AMOUNT + equalSymbol + "" + symbol
				+ BackOfficeContants.MTFCL + equalSymbol + mtfcl + symbol + BackOfficeContants.MTFCLAUTO + equalSymbol
				+ mtfclauto + symbol + BackOfficeContants.BROKERAGECOMPANYWISE + equalSymbol + "" + symbol
				+ BackOfficeContants.BROKERAGEGLOBALLY + equalSymbol + "" + symbol + BackOfficeContants.CL_DELI_B_TO
				+ equalSymbol + "" + symbol + BackOfficeContants.CL_DELI_S_TO + equalSymbol + "" + symbol
				+ BackOfficeContants.CL_GROSS_TO + equalSymbol + "" + symbol + BackOfficeContants.CL_TRD_TO
				+ equalSymbol + "" + symbol + BackOfficeContants.PERSON_VERIFY + equalSymbol + person_verify + symbol
				+ BackOfficeContants.INPERSON_DATE + equalSymbol + inperson_date + symbol
				+ BackOfficeContants.PERSON_VERIFY_NAME + equalSymbol + person_verify_name + symbol
				+ BackOfficeContants.PERSON_VERIFY_DESIGNATION + equalSymbol + person_verify_designation + symbol
				+ BackOfficeContants.ANNUAL_INCOME + equalSymbol + annual_income + symbol
				+ BackOfficeContants.GROSSANNUALINCOMEDATE + equalSymbol + grossannualincomedate + symbol
				+ BackOfficeContants.PORTFOLIO_MKT_VALUE + equalSymbol + "" + symbol + BackOfficeContants.NET_WORTH_DATE
				+ equalSymbol + "" + symbol + BackOfficeContants.PASSPORT_NO + equalSymbol + "" + symbol
				+ BackOfficeContants.PASSPORT_ISSUED_PLACE + equalSymbol + "" + symbol
				+ BackOfficeContants.PASS_ISSUE_DATE + equalSymbol + "" + symbol
				+ BackOfficeContants.PASSPORT_EXPIRY_DATE + equalSymbol + "" + symbol + BackOfficeContants.AADHARCARD
				+ equalSymbol + "" + symbol + BackOfficeContants.AADHAR_ISSUE_DATE + equalSymbol + "" + symbol
				+ BackOfficeContants.DRIVING_LICENSE + equalSymbol + "" + symbol
				+ BackOfficeContants.DRIVING_LICENSE_ISSUED_PLACE + equalSymbol + "" + symbol
				+ BackOfficeContants.DRIVING_LICENSE_ISSUED_DATE + equalSymbol + "" + symbol
				+ BackOfficeContants.DR_LIC_EXP_DATE + equalSymbol + "" + symbol + BackOfficeContants.RATIONCARD
				+ equalSymbol + "" + symbol + BackOfficeContants.RATIONCARD_ISSUED_PLACE + equalSymbol + "" + symbol
				+ BackOfficeContants.VOTERS_ID_CARD + equalSymbol + "" + symbol
				+ BackOfficeContants.VOTERS_ID_CARD_ISSUED_DATE + equalSymbol + "" + symbol
				+ BackOfficeContants.PLACE_ISSUE_VOUTER_ID + equalSymbol + "" + symbol
				+ BackOfficeContants.OTHER_DETAIL_ID + equalSymbol + "" + symbol + BackOfficeContants.OTHER_DETAIL_NAME
				+ equalSymbol + "" + symbol + BackOfficeContants.OTHER_PLACE + equalSymbol + "" + symbol
				+ BackOfficeContants.OTHER_EXPIRY_DATE + equalSymbol + "" + symbol + BackOfficeContants.OTHER_ISSUE_DATE
				+ equalSymbol + "" + symbol + BackOfficeContants.CSC_FLG + equalSymbol + csc_flg + symbol
				+ BackOfficeContants.CSC_NARR + equalSymbol + "" + symbol + BackOfficeContants.RISK_CATG + equalSymbol
				+ risk_catg + symbol + BackOfficeContants.RISK_NARR + equalSymbol + "" + symbol
				+ BackOfficeContants.OTHER_BROKER + equalSymbol + "" + symbol + BackOfficeContants.OTHER_CLIENT_CODE
				+ equalSymbol + "" + symbol + BackOfficeContants.OTHER_EXCHANGE + equalSymbol + "" + symbol
				+ BackOfficeContants.NOMINATION_NAME + equalSymbol + "" + symbol + BackOfficeContants.NOM_ADDRESS
				+ equalSymbol + "" + symbol + BackOfficeContants.NOM_EMAIL + equalSymbol + "" + symbol
				+ BackOfficeContants.NOM_PHONE + equalSymbol + "" + symbol + BackOfficeContants.NOM_RELATION
				+ equalSymbol + "" + symbol + BackOfficeContants.NOM_PAN + equalSymbol + "" + symbol
				+ BackOfficeContants.NOM_DOB + equalSymbol + "" + symbol + BackOfficeContants.GUARDIAN_NAME
				+ equalSymbol + "" + symbol + BackOfficeContants.GUARDIAN_PAN + equalSymbol + "" + symbol
				+ BackOfficeContants.OFFICE_NAME + equalSymbol + "" + symbol + BackOfficeContants.OFF_ADDRESS
				+ equalSymbol + "" + symbol + BackOfficeContants.OFF_CITY + equalSymbol + "" + symbol
				+ BackOfficeContants.OFF_PIN + equalSymbol + "" + symbol + BackOfficeContants.OFF_STATE + equalSymbol
				+ off_state + symbol + BackOfficeContants.OFFICE_COUNTRY + equalSymbol + office_country + symbol
				+ BackOfficeContants.OFF_TEL_NO + equalSymbol + "" + symbol + BackOfficeContants.OFF_FAX_NO
				+ equalSymbol + "" + symbol + BackOfficeContants.INTRODUCED_BY + equalSymbol + introduced_by + symbol
				+ BackOfficeContants.INTRODUCER_ACC_CODE + equalSymbol + "" + symbol
				+ BackOfficeContants.INTR_FIRST_NAME + equalSymbol + "" + symbol + BackOfficeContants.INTR_MIDDLE_NAME
				+ equalSymbol + "" + symbol + BackOfficeContants.INTR_LAST_NAME + equalSymbol + "" + symbol
				+ BackOfficeContants.INTRODUCER_NAME + equalSymbol + "" + symbol + BackOfficeContants.INTRODUCER_ADDRESS
				+ equalSymbol + "" + symbol + BackOfficeContants.INTRODUCER_PAN_NO + equalSymbol + "" + symbol
				+ BackOfficeContants.INTRODUCER_TEL_NO + equalSymbol + "" + symbol
				+ BackOfficeContants.INTRODUCER_FAX_NO + equalSymbol + "" + symbol
				+ BackOfficeContants.REL_WITH_INTRODUCER + equalSymbol + "" + symbol
				+ BackOfficeContants.STETEMENT_COMMUNICATION_MODE + equalSymbol + stetement_communication_mode + symbol
				+ BackOfficeContants.SPAN_NO + equalSymbol + "" + symbol + BackOfficeContants.SCLIENT_NAME + equalSymbol
				+ "" + symbol + BackOfficeContants.TPAN_NO + equalSymbol + "" + symbol + BackOfficeContants.TCLIENT_NAME
				+ equalSymbol + "" + symbol + BackOfficeContants.HOLDING_NATURE + equalSymbol + holding_nature + symbol
				+ BackOfficeContants.TAX_STATUS + equalSymbol + tax_status + symbol
				+ BackOfficeContants.CLIENT_DIVPAYMODE + equalSymbol + client_divpaymode + symbol
				+ BackOfficeContants.CM_FORRESIPHONE + equalSymbol + "" + symbol + BackOfficeContants.FOREIGN_COUNTRY
				+ equalSymbol + foreign_country + symbol + BackOfficeContants.NACHFLAG + equalSymbol + nachflag + symbol
				+ BackOfficeContants.NACHRECEAMT + equalSymbol + "" + symbol + BackOfficeContants.TRUSTBASEPAYIN
				+ equalSymbol + trustbasepayin + symbol + BackOfficeContants.PAYMENT_TYPE + equalSymbol + payment_type
				+ symbol + BackOfficeContants.AGEPROOF + equalSymbol + "" + symbol + BackOfficeContants.KRA_STA
				+ equalSymbol + kra_sta + symbol + BackOfficeContants.KRAREC + equalSymbol + krarec + symbol
				+ BackOfficeContants.KRARECUP + equalSymbol + krarecup + symbol + BackOfficeContants.KRA_DATE
				+ equalSymbol + "" + symbol + BackOfficeContants.ANNIVERSARY_DATE + equalSymbol + "" + symbol
				+ BackOfficeContants.MOBILE_AGREE_DATE + equalSymbol + "" + symbol + BackOfficeContants.NEW_KYC_REC_DATE
				+ equalSymbol + "" + symbol + BackOfficeContants.SIGMACODES + equalSymbol + "" + symbol
				+ BackOfficeContants.TRIPARTIATEDT + equalSymbol + "" + symbol + BackOfficeContants.FATCA_DATE
				+ equalSymbol + "" + symbol + BackOfficeContants.FATCA_COUNTRY + equalSymbol + fatca_country + symbol
				+ BackOfficeContants.FATCA_TIN + equalSymbol + "" + symbol + BackOfficeContants.CKYCFLAG + equalSymbol
				+ ckycflag;
		return temp;
	}
}
