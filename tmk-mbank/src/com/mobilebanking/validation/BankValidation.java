package com.mobilebanking.validation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobilebanking.api.IBankApi;
import com.mobilebanking.api.IGeneralSettingsApi;
import com.mobilebanking.api.ISmsPackageApi;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.GeneralSettings;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.SmsPackageDTO;
import com.mobilebanking.model.error.BankError;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.util.StringConstants;

@Component
public class BankValidation {

	private Logger logger = LoggerFactory.getLogger(BankValidation.class);

	@Autowired
	private IBankApi bankApi;

	@Autowired
	private ISmsPackageApi smsPackageApi;

	@Autowired
	private IGeneralSettingsApi generalSettingApi;

	@Autowired
	private BankRepository bankRepository;

	public BankError bankValidation(BankDTO bankDTO) {
		BankError bankError = new BankError();
		boolean valid = true;

		String error = null;

		error = checkBankName(bankDTO.getName());
		if (error != null) {
			bankError.setName(error);
			valid = false;
		}

		error = checkBankAddress(bankDTO.getAddress());
		if (error != null) {
			bankError.setAddress(error);
			valid = false;
		}

		error = checkBankCode(bankDTO.getSwiftCode());
		if (error != null) {
			bankError.setSwiftCode(error);
			valid = false;
		}

		error = checkChannelPartner(bankDTO.getChannelPartner());
		if (error != null) {
			bankError.setChannelPartner(error);
			valid = false;
		}

		error = checkState(bankDTO.getState());
		if (error != null) {
			bankError.setState(error);
			valid = false;
		}

		error = checkMobileNumber(bankDTO.getMobileNumber());
		if (error != null) {
			bankError.setMobileNumber(error);
			valid = false;
		}

		error = checkEmail(bankDTO.getEmail());
		if (error != null) {
			bankError.setEmail(error);
			valid = false;
		}

		error = checkCity(bankDTO.getCity());
		if (error != null) {
			bankError.setCity(error);
			valid = false;
		}

		error = checkSMSCount(bankDTO.getSmsCount());
		if (error != null) {
			bankError.setSmsCount(error);
			valid = false;
		}

		error = checkLicenseCount(bankDTO.getLicenseCount());
		if (error != null) {
			bankError.setLicenseCount(error);
			valid = false;
		}

		error = checkExpiryDate(bankDTO.getLicenseExpiryDate());
		if (error != null) {
			bankError.setLicenseExpiryDate(error);
			valid = false;
		}
		
		/*error = checkMerchants(bankDTO.getMerchants());
		if(error!=null){
			bankError.setMerchants(error);
			valid = false;
		}
*/
		error = checkBankTransferAccount(bankDTO.getBankTranferAccount());
		if (error != null) {
			bankError.setBankTranferAccount(error);
		}

		error = checkIsoUrl(bankDTO.getIsoUrl());
		if (error != null) {
			bankError.setIsoUrl(error);
			valid = false;
		}

		error = checkPortNumber(bankDTO.getPortNumber());
		if (error != null) {
			bankError.setPortNumber(error);
			valid = false;
		}

		error = checkCardAcceptorTerminalIdentification(bankDTO.getCardAcceptorTerminalIdentification());
		if (error != null) {
			bankError.setCardAcceptorTerminalIdentification(error);
			valid = false;
		}
		
		error = checkDesc1(bankDTO.getDesc1());
		if (error != null) {
			bankError.setDesc1(error);
			valid = false;
		}

		error = checkAcquiringInstitutionIdentificationCode(bankDTO.getAcquiringInstitutionIdentificationCode());
		if (error != null) {
			bankError.setAcquiringInstitutionIdentificationCode(error);
			valid = false;
		}

		error = checkMerchantType(bankDTO.getMerchantType());
		if (error != null) {
			bankError.setMerchantType(error);
			valid = false;
		}

		error = checkRole(bankDTO.getUserTemplate());
		if (error != null) {
			bankError.setRole(error);
			valid = false;
		}

		List<SmsPackageDTO> smsPackage = smsPackageApi.getAllSmsPackage();
		GeneralSettings generalSetting = generalSettingApi.get(StringConstants.SPARROW_SMS_CREDIT);
		Integer smsValue = (int) (Double.parseDouble(generalSetting.getSettingsValue()));
		Integer totalBankSms = 0;
		for (SmsPackageDTO sms : smsPackage) {
			BankDTO bank = bankApi.getBankDtoById(sms.getBankId());
			totalBankSms += bank.getSmsCount();
		}
		if (smsValue < totalBankSms) {
			bankError.setSmsCount("SMS credit less than the allocated value");
			valid = false;
		}
		bankError.setValid(valid);
		return bankError;
	}

	

	private String checkLicenseCount(Integer licenseCount) {
		if(licenseCount == null){
			return "Invalid License Count";
		}else if(licenseCount == 0){
			return "Invalid License Count";
		}
		return null;
	}
	
	private String checkExpiryDate(String licenseExpiryDate) {
		if(licenseExpiryDate == null){
			return "Invalid Expiry Date";
		}else if(licenseExpiryDate.trim().equals("")){
			return "Invalid Expiry Date";
		}
		return null;
	}

	private String checkSMSCount(Integer smsCount) {
		if(smsCount == null){
			return "Invalid SMS Count";
		}else if(smsCount == 0){
			return "Invalid SMS Count";
		}
		return null;
	}

	private String checkBankName(String bankName) {
		if (bankName == null) {
			return "Invalid Name";
		} else if (bankName.trim().equals("")) {
			return "Invalid Name";
		} else {
			BankDTO bankDto = bankApi.getBankByName(bankName);
			if (bankDto != null) {
				return "Bank with same name already exists";
			}
		}
		return null;
	}

	private String checkBankAddress(String bankAddress) {
		if (bankAddress == null) {
			return "Invalid Address";
		} else if (bankAddress.trim().equals("")) {
			return "Invalid Address";
		}
		return null;
	}

	private String checkBankCode(String bankCode) {
		if (bankCode == null) {
			return "Invalid Bank Code";
		} else if (bankCode.trim().equals("")) {
			return "Invalid Bank Code";
		} else {
			Bank bank = bankRepository.findBankByCode(bankCode);
			if (bank != null) {
				return "Bank with same bank code already exists";
			}
		}
		return null;
	}

	private String checkChannelPartner(Long channelPartner) {
		if (channelPartner == null) {
			return "Invalid Channel Partner";
		} else if (channelPartner == 0) {
			return "Invalid Channel Partner";
		}
		return null;
	}

	private String checkState(String state) {
		if (state == null) {
			return "Invalid State";
		} else if (state.trim().equals("")) {
			return "Invalid State";
		}
		return null;
	}

	private String checkCity(String city) {
		if (city == null) {
			return "Invalid City";
		} else if (city.trim().equals("")) {
			return "Invalid City";
		} else if (city.equals("0")) {
			return "Invalid City";
		}
		return null;
	}

	private String checkMobileNumber(String mobileNumber) {
		if (mobileNumber == null) {
			return "Invalid Mobile Number";
		} else if (mobileNumber.trim().equals("")) {
			return "Invalid Mobile Number";
		} else if (!(isNumber(mobileNumber))) {
			return "Invalid Mobile Number";
		}
		return null;
	}

	private String checkEmail(String email) {
		if (email == null) {
			return "Invalid Email";
		} else if (email.trim().equals("")) {
			return "Invalid Email";
		} else if (!(email.contains(".com"))) {
			return "Invalid Email";
		} else if (!(email.contains("@"))) {
			return "Invalid Email";
		}
		return null;
	}

	private String checkBankTransferAccount(String bankTransferAccount) {
		if (bankTransferAccount == null) {
			return "Invalid Bank Transfer Account";
		} else if (bankTransferAccount.trim().equals("")) {
			return "Invalid Bank Transfer Account";
		}
		return null;
	}

	private String checkMerchantType(String merchantType) {
		if (merchantType == null) {
			return "Invalid Merchant Type";
		} else if (merchantType.trim().equals("")) {
			return "Invalid Merchant Type";
		} else if (!(isNumber(merchantType))) {
			return "Invalid Merchant Type";
		} else if (merchantType.length() != 4) {
			return "Merchant Type must be 4 digit long";
		}

		return null;
	}

	private String checkIsoUrl(String isoUrl) {
		if (isoUrl == null) {
			return "Invalid ISO URL";
		} else if (isoUrl.trim().equals("")) {
			return "Invalid ISO URL";
		}
		return null;
	}

	private String checkPortNumber(String portNumber) {
		if (portNumber == null) {
			return "Invalid Port Number";
		} else if (portNumber.trim().equals("")) {
			return "Invalid Port Number";
		}
		return null;
	}
	
	private String checkMerchants(String[] merchants){
		if(merchants == null){
			return "Please select a merchant";
		}else if(merchants.length==0){
			return "Please select a merchant";
		}
		return null;
	}

	private String checkCardAcceptorTerminalIdentification(String cardAcceptorTerminalIdentification) {
		if (cardAcceptorTerminalIdentification == null) {
			return "Invalid Card Acceptor Terminal Identification";
		} else if (cardAcceptorTerminalIdentification.trim().equals("")) {
			return "Invalid Card Acceptor Terminal Identification";
		} else if (cardAcceptorTerminalIdentification.length() != 8) {
			return "Card Acceptor Terminal Identification must be 8 character long";
		}

		return null;
	}

	private String checkDesc1(String desc1) {
		if (desc1 == null) {
			return "Invalid Desc 1";
		} else if (desc1.trim().equals("")) {
			return "Invalid Desc 1";
		} else if (desc1.length() >19) {
			return "Desc1 must be less than 19 character long";
		}

		return null;
	}
	
	private String checkAcquiringInstitutionIdentificationCode(String acquiringInstitutionIdentificationCode) {
		if (acquiringInstitutionIdentificationCode == null) {
			return "Invalid Acquiring Institution Identification Code";
		} else if (acquiringInstitutionIdentificationCode.trim().equals("")) {
			return "Invalid Acquiring Institution Identification Code";
		} else if (!(isNumber(acquiringInstitutionIdentificationCode))) {
			return "Invalid Acquiring Institution Identification Code";
		} else if (acquiringInstitutionIdentificationCode.length() > 11) {
			return "Acceptor Terminal Identification must not be longer than 11 digit";
		}

		return null;
	}

	private String checkRole(String userTemplate) {
		if (userTemplate == null) {
			return "Invalid Port Number";
		} else if (userTemplate.trim().equals("")) {
			return "Invalid Port Number";
		}
		return null;
	}

	private boolean isNumber(String string) {
		try {
			Long.parseLong(string);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public BankError editValidation(BankDTO bankDTO) {
		BankError bankError = new BankError();
		boolean valid = true;

		if (bankDTO.getAddress() == null) {
			logger.debug("Bank address null");
			bankError.setAddress("Address Required");
			valid = false;
		}
		String error = checkAcquiringInstitutionIdentificationCode(bankDTO.getAcquiringInstitutionIdentificationCode());
		if (error != null) {
			bankError.setAcquiringInstitutionIdentificationCode(error);
			valid = false;
		}
		error = checkCardAcceptorTerminalIdentification(bankDTO.getCardAcceptorTerminalIdentification());
		if (error != null) {
			bankError.setCardAcceptorTerminalIdentification(error);
			valid = false;
		}
		error = checkMerchantType(bankDTO.getMerchantType());
		if (error != null) {
			bankError.setMerchantType(error);
			valid = false;
		}
		if (bankDTO.getIsoUrl() == null) {
			bankError.setIsoUrl("Invalid ISO URL");
			valid = false;
		} else if (bankDTO.getIsoUrl().trim().equals("")) {
			bankError.setIsoUrl("Invalid ISO URL");
			valid = false;
		}

		if (bankDTO.getBankTranferAccount() == null) {
			bankError.setBankTranferAccount("Invalid Bank Transfer Account");
			valid = false;
		} else if (bankDTO.getBankTranferAccount().trim().equals("")) {
			bankError.setBankTranferAccount("Invalid Bank Transfer Account");
			valid = false;
		}
		bankError.setValid(valid);
		return bankError;
	}

}
