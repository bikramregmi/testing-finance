package com.mobilebanking.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.entity.City;
import com.mobilebanking.entity.Merchant;
import com.mobilebanking.model.MerchantDTO;
import com.mobilebanking.model.error.MerchantError;
import com.mobilebanking.repositories.CityRepository;
import com.mobilebanking.repositories.MerchantRepository;

@Service
public class MerchantValidation {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private MerchantRepository merchantRepository;
	

	public MerchantError merchantValidation(MerchantDTO merchantDto) {
		MerchantError error = new MerchantError();
		boolean valid = true;
		
		if (merchantDto.getName() != null) {
			if (merchantDto.getName().trim().equals("")) {
				logger.debug("Merchant Name Required==>");
				error.setName("Merchant Name Required");
				valid = false;
			}
			Merchant merchant = merchantRepository.findMerchantByName(merchantDto.getName());
			if (merchant != null) {
				logger.debug("duplicate merchant name");
				error.setName("Merchant Name Already Exists");
				valid = false;
			}
		} else {
			logger.debug("Merchant Name Required==>");
			error.setName("Merchant Name Required");
			valid = false;
		}

		if (merchantDto.getCity() != null) {
			if (merchantDto.getCity().trim().equals("")) {
				logger.debug("city Required==>");
				error.setCity("City Name Required");
				valid = false;
			} else {
				try {
					//long cityId = Long.parseLong(merchantDTO.getCity());
					City city = cityRepository.findCityByIdAndState(
							merchantDto.getState(), Long.parseLong(merchantDto.getCity()));
					if (city == null) {
						logger.debug("Invalid cidty==>");
						error.setCity("Invalid City");
						valid = false;
					}
				} catch (Exception e) {

					logger.debug("Invalid city name Required==>");
					error.setCity("Invalid city Name Required");
					valid = false;
					e.printStackTrace();
				}

			}
		} else {
			logger.debug("city Name Required==>");
			error.setCity("City Name Required");
			valid = false;
		}

		

		if (merchantDto.getDescription() != null) {
			if (merchantDto.getDescription().trim().equals("")) {
				logger.debug("description is blank==>");
				error.setDescription("Description Required");
				valid = false;
			} else if (merchantDto.getDescription().length() < 10) {
				logger.debug("Description too short==>");
				error.setDescription("Description too short");
				valid = false;
			}
		} else {
			logger.debug("Description null ==>");
			error.setDescription("City Name Required");
			valid = false;
		}

		if (merchantDto.getMobileNumber() != null) {
			if (merchantDto.getMobileNumber().trim().equals("")) {
				logger.debug("dsicription is blank==>");
				error.setMobileNo("Description Required");
				valid = false;
			} else if (merchantDto.getMobileNumber().length() < 10 && merchantDto.getMobileNumber().length() > 10) {
				logger.debug("Mobile number is less than 10 digit==>");
				error.setMobileNo("Mobile number must be 10 digit");
				valid = false;
			} else {
				try {
					logger.debug("mobile number " + merchantDto.getMobileNumber().trim());
					Long.parseLong(merchantDto.getMobileNumber().trim());

				} catch (Exception e) {
					e.printStackTrace();
					logger.debug("Mobile number is not valid number==>");
					error.setMobileNo("Only numbers");
					valid = false;
				}
			}
		} else {
			logger.debug("mobile number null ==>");
			error.setMobileNo("Moble number is null");
			valid = false;
		}

		if (merchantDto.getOwnerName() != null) {
			if (merchantDto.getOwnerName().trim().equals("")) {
				logger.debug("Owner name is blank==>");
				error.setOwnerName("Owner name Required");
				valid = false;
			} else if (merchantDto.getOwnerName().length() < 4) {
				logger.debug("Owner name too short==>");
				error.setOwnerName("Owner name too short");
				valid = false;
			}
		} else {
			logger.debug("Owner name null ==>");
			error.setOwnerName("Owner name is null");
			valid = false;
		}

		if (merchantDto.getRegistrationNumber() != null) {
			if (merchantDto.getRegistrationNumber().trim().equals("")) {
				logger.debug("Registration name is blank==>");
				error.setRegistrationNumber("Owner name Required");
				valid = false;
			} else if (merchantDto.getRegistrationNumber().length() < 4) {
				logger.debug("registartion name too short==>");
				error.setRegistrationNumber("registartion name too short");
				valid = false;
			}
		} else {
			logger.debug("registartion name null ==>");
			error.setRegistrationNumber("registartion name is null");
			valid = false;
		}

		if (merchantDto.getVatNumber() != null) {
			if (merchantDto.getVatNumber().trim().equals("")) {
				logger.debug("VAT number is blank==>");
				error.setVatNumber("Owner name Required");
				valid = false;
			} else if (merchantDto.getVatNumber().length() < 4) {
				logger.debug("VAT number too short==>");
				error.setVatNumber("Owner name too short");
				valid = false;
			}
		} else {
			logger.debug("VAT number null ==>");
			error.setVatNumber("VAT number is null");
			valid = false;
		}
		
		if(merchantDto.getApiUrl()==null){
			error.setApiUrl("Invalid Api Url");
			valid = false;
		}else if(merchantDto.getApiUrl().trim().equals("")){
			error.setApiUrl("Invalid Api Url");
			valid = false;
		}
		
		if(merchantDto.getApiUsername()==null){
			error.setApiUsername("Invalid Api Username");
			valid = false;
		}else if(merchantDto.getApiUsername().trim().equals("")){
			error.setApiUsername("Invalid Api Username");
			valid = false;
		}
		
		if(merchantDto.getApiPassword()==null){
			error.setApiPassword("Invalid Api Password");
			valid = false;
		}else if(merchantDto.getApiPassword().trim().equals("")){
			error.setApiPassword("Invalid Api Password");
			valid = false;
		}

		error.setValid(valid);
		return error;

	}
	
	
	public MerchantError merchantEditValidation(MerchantDTO merchantDto) {
		MerchantError error = new MerchantError();
		boolean valid = true;
		
		if (merchantDto.getName() != null) {
			if (merchantDto.getName().trim().equals("")) {
				logger.debug("Merchant Name Required==>");
				error.setName("Merchant Name Required");
				valid = false;
			}
			Merchant currentMerchant = merchantRepository.findOne(merchantDto.getId());
			if(!(currentMerchant.getName().equals(merchantDto.getName()))){
				Merchant merchant = merchantRepository.findMerchantByName(merchantDto.getName());
				if (merchant != null) {
					logger.debug("duplicate merchant name");
					error.setName("Merchant Name Already Exists");
					valid = false;
				}
			}
		} else {
			logger.debug("Merchant Name Required==>");
			error.setName("Merchant Name Required");
			valid = false;
		}

		if (merchantDto.getCity() != null) {
			if (merchantDto.getCity().trim().equals("")) {
				logger.debug("city Required==>");
				error.setCity("City Name Required");
				valid = false;
			} else {
				try {
					//long cityId = Long.parseLong(merchantDTO.getCity());
					City city = cityRepository.findCityByIdAndState(
							merchantDto.getState(), Long.parseLong(merchantDto.getCity()));
					if (city == null) {
						logger.debug("Invalid cidty==>");
						error.setCity("Invalid City");
						valid = false;
					}
				} catch (Exception e) {

					logger.debug("Invalid city name Required==>");
					error.setCity("Invalid city Name Required");
					valid = false;
					e.printStackTrace();
				}

			}
		} else {
			logger.debug("city Name Required==>");
			error.setCity("City Name Required");
			valid = false;
		}

		

		if (merchantDto.getDescription() != null) {
			if (merchantDto.getDescription().trim().equals("")) {
				logger.debug("description is blank==>");
				error.setDescription("Description Required");
				valid = false;
			} else if (merchantDto.getDescription().length() < 10) {
				logger.debug("Description too short==>");
				error.setDescription("Description too short");
				valid = false;
			}
		} else {
			logger.debug("Description null ==>");
			error.setDescription("City Name Required");
			valid = false;
		}
		
		if(merchantDto.getApiUrl()==null){
			error.setApiUrl("Invalid Api Url");
			valid = false;
		}else if(merchantDto.getApiUrl().trim().equals("")){
			error.setApiUrl("Invalid Api Url");
			valid = false;
		}
		
		if(merchantDto.getApiUsername()==null){
			error.setApiUsername("Invalid Api Username");
			valid = false;
		}else if(merchantDto.getApiUsername().trim().equals("")){
			error.setApiUsername("Invalid Api Username");
			valid = false;
		}
		
		if(merchantDto.getApiPassword()==null){
			error.setApiPassword("Invalid Api Password");
			valid = false;
		}else if(merchantDto.getApiPassword().trim().equals("")){
			error.setApiPassword("Invalid Api Password");
			valid = false;
		}

		error.setValid(valid);
		return error;

	}

}
