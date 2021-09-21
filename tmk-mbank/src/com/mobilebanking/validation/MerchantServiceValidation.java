/**
 * 
 */
package com.mobilebanking.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobilebanking.entity.Merchant;
import com.mobilebanking.entity.MerchantService;
import com.mobilebanking.model.MerchantServiceDTO;
import com.mobilebanking.model.error.MerchantServiceError;
import com.mobilebanking.repositories.MerchantRepository;
import com.mobilebanking.repositories.MerchantServiceRepository;

/**
 * @author bibek
 *
 */
@Component
public class MerchantServiceValidation {
	
	private Logger logger = LoggerFactory.getLogger(MerchantServiceValidation.class);
	@Autowired
	MerchantServiceRepository merchantServiceRepository;
	
	@Autowired
	MerchantRepository merchantRepository;
	
	public MerchantServiceError merchantServiceValidation(MerchantServiceDTO merchantServiceDto) {
		MerchantServiceError merchantServiceError = new MerchantServiceError();
		boolean valid = true;
		if (merchantServiceDto.getService() == null) {
			logger.debug("service name null");
			merchantServiceError.setService("Service Name Required");
			valid = false;
		}else if(merchantServiceDto.getService().trim().equals("")){
			logger.debug("service name null");
			merchantServiceError.setService("Service Name Required");
			valid = false;
		}
		MerchantService merchantService = merchantServiceRepository.findMerchantServiceByName(merchantServiceDto.getService());
		if (merchantService != null) {
			logger.debug("duplicate service name");
			merchantServiceError.setService("Duplicate Service Name");
			valid = false;
		}
		if (merchantServiceDto.getMerchant() == null) {
			logger.debug("empty merchant");
			merchantServiceError.setMerchant("Merchant Required");
			valid = false;
		}
		try {
			
			Merchant merchant = merchantRepository.getMerchantById(Long.parseLong(merchantServiceDto.getMerchant()));
			if (merchant == null) {
				logger.debug("Invalid Merchant");
				merchantServiceError.setMerchant("Invalid Merchant");
				valid = false;
			}
		} catch (Exception e) {			
			logger.debug(e.getMessage());
			merchantServiceError.setMerchant("Error While Retriving Merchant");
			valid = false;
		}
		
		if (merchantServiceDto.getUniqueIdentifier() == null) {
			logger.debug("Unique Identifier null");
			merchantServiceError.setUniqueIdentifier("Unique Identifier Required");
			valid = false;
		}else if(merchantServiceDto.getUniqueIdentifier().trim().equals("")){
			logger.debug("Unique Identifier null");
			merchantServiceError.setUniqueIdentifier("Unique Identifier Required");
			valid = false;
		}
		merchantService = merchantServiceRepository.findMerchantServiceByIdentifier(
				merchantServiceDto.getUniqueIdentifier());
		if (merchantService != null) {
			logger.debug("duplicate service identifier");
			merchantServiceError.setUniqueIdentifier("Duplicate Service Unique Identifier");
			valid = false;
		}
//		if (merchantServiceDto.getUrl() == null) {
//			logger.debug("url null");
//			merchantServiceError.setUrl("Service Url Requiredy");
//			valid = false;
//		}else if(merchantServiceDto.getUrl().trim().equals("")){
//			logger.debug("url null");
//			merchantServiceError.setUrl("Service Url Required");
//			valid = false;
//		}
//		if (merchantServiceDto.getLabelName() == null) {
//			logger.debug("label name null");
//			merchantServiceError.setLabelName("Label Name Required");
//			valid = false;
//		}else if(merchantServiceDto.getLabelName().trim().equals("")){
//			logger.debug("label name null");
//			merchantServiceError.setLabelName("Label Name Required");
//			valid = false;
//		}
	
//		if (merchantServiceDto.getLabelPrefix() == null) {
//			logger.debug("label prefix null");
//			merchantServiceError.setLabelPrefix("Label Prefix Required");
//			valid = false;
//		}else{
//			try{
//				String[] prefixList = merchantServiceDto.getLabelPrefix().split(",");
//				for(String prefix : prefixList){
//					Long.parseLong(prefix.trim());
//				}
//				
//			}catch(Exception e){
//				logger.debug("invalid label prefix");
//				merchantServiceError.setLabelPrefix("Invalid Label Prefix");
//				valid = false;
//			}
//		}
//		if (merchantServiceDto.getLabelSample() == null) {
//			logger.debug("label sample null");
//			merchantServiceError.setLabelSample("Label Sample Required");
//			valid = false;
//		}else if(merchantServiceDto.getLabelSample().trim().equals("")){
//			logger.debug("label sample null");
//			merchantServiceError.setLabelSample("Label Sample Required");
//			valid = false;
//		}
		if (merchantServiceDto.getInstructions() == null) {
			logger.debug("instruction null");
			merchantServiceError.setInstructions("Instruction Required");
			valid = false;
		}else if(merchantServiceDto.getInstructions().trim().equals("")){
			logger.debug("instruction null");
			merchantServiceError.setInstructions("Instruction Required");
			valid = false;
		}
		if(merchantServiceDto.isPriceInput()){
			if (merchantServiceDto.getPriceRange() == null) {
				logger.debug("price range null");
				merchantServiceError.setPriceRange("Invalid Price Range");
				valid = false;
			}else{
				try{
					String[] rangeList = merchantServiceDto.getPriceRange().split(",");
					for(String range : rangeList){
						Long.parseLong(range.trim());
					}
					
				}catch(Exception e){
					logger.debug("invalid price range");
					merchantServiceError.setPriceRange("Invalid Price Range");
					valid = false;
				}
			}
		}
		
		if(merchantServiceDto.isFixedlabelSize()){
			if (merchantServiceDto.getLabelSize() == null) {
				logger.debug("Label Size null");
				merchantServiceError.setLabelSize("Invalid Label Size");
				valid = false;
			}else if(merchantServiceDto.getLabelSize().trim().equals("")){
				logger.debug("Invalid Label Size");
				merchantServiceError.setLabelSize("Invalid Label Size");
				valid = false;
			}
		}else{
			if (merchantServiceDto.getLabelMinLength() == null) {
				logger.debug("label min length null");
				merchantServiceError.setLabelMinLength("Label Min Length Required");
				valid = false;
			}else if(merchantServiceDto.getLabelMinLength().trim().equals("")){
				logger.debug("label min length null");
				merchantServiceError.setLabelMinLength("Label Min Length Required");
				valid = false;
			}else{
				try{
					Long.parseLong(merchantServiceDto.getLabelMinLength());
				}catch(Exception e){
					logger.debug("invalid label min length");
					merchantServiceError.setLabelMinLength("Invalid Label Min Length");
					valid = false;
				}
			}
			if (merchantServiceDto.getLabelMaxLength() == null) {
				logger.debug("label max length null");
				merchantServiceError.setLabelMaxLength("Label Max Length Required");
				valid = false;
			}else if(merchantServiceDto.getLabelMaxLength().trim().equals("")){
				logger.debug("label max length null");
				merchantServiceError.setLabelMaxLength("Label Max Length Required");
				valid = false;
			}else{
				try{
					Long.parseLong(merchantServiceDto.getLabelMaxLength());
					if(Long.parseLong(merchantServiceDto.getLabelMaxLength()) <= Long.parseLong(merchantServiceDto.getLabelMinLength())){
						logger.debug("label max length less than min Length");
						merchantServiceError.setLabelMaxLength("Label Max Length must be greater than Label Min Length");
						valid = false;
					}
				}catch(Exception e){
					logger.debug("invalid label max length");
					merchantServiceError.setLabelMaxLength("Invalid Label Max Length");
					valid = false;
				}
			}
		}
		merchantServiceError.setValid(valid);
		return merchantServiceError;
	}
	
	public MerchantServiceError merchantServiceEditValidation(MerchantServiceDTO merchantServiceDto) {
		MerchantServiceError merchantServiceError = new MerchantServiceError();
		boolean valid = true;
		if (merchantServiceDto.getService() == null) {
			logger.debug("service name null");
			merchantServiceError.setService("Service Name Required");
			valid = false;
		}else if(merchantServiceDto.getService().trim().equals("")){
			logger.debug("service name null");
			merchantServiceError.setService("Service Name Required");
			valid = false;
		}
		MerchantService service = merchantServiceRepository.findOne(merchantServiceDto.getId());
		if(!(service.getService().equalsIgnoreCase(merchantServiceDto.getService()))){
			MerchantService merchantService = merchantServiceRepository.findMerchantServiceByName(merchantServiceDto.getService());
			if (merchantService != null) {
				logger.debug("duplicate service name");
				merchantServiceError.setService("Duplicate Service Name");
				valid = false;
			}
		}
//		if (merchantServiceDto.getLabelName() == null) {
//			logger.debug("label name null");
//			merchantServiceError.setLabelName("Label Name Required");
//			valid = false;
//		}else if(merchantServiceDto.getLabelName().trim().equals("")){
//			logger.debug("label name null");
//			merchantServiceError.setLabelName("Label Name Required");
//			valid = false;
//		}
	
//		if (merchantServiceDto.getLabelPrefix() == null) {
//			logger.debug("label prefix null");
//			merchantServiceError.setLabelPrefix("Label Prefix Required");
//			valid = false;
//		}else{
//			try{
//				String[] prefixList = merchantServiceDto.getLabelPrefix().split(",");
//				for(String prefix : prefixList){
//					Long.parseLong(prefix.trim());
//				}
//				
//			}catch(Exception e){
//				logger.debug("invalid label prefix");
//				merchantServiceError.setLabelPrefix("Invalid Label Prefix");
//				valid = false;
//			}
//		}
		if (merchantServiceDto.getLabelSample() == null) {
			logger.debug("label sample null");
			merchantServiceError.setLabelSample("Label Sample Required");
			valid = false;
		}else if(merchantServiceDto.getLabelSample().trim().equals("")){
			logger.debug("label sample null");
			merchantServiceError.setLabelSample("Label Sample Required");
			valid = false;
		}
		if (merchantServiceDto.getInstructions() == null) {
			logger.debug("instruction null");
			merchantServiceError.setInstructions("Instruction Required");
			valid = false;
		}else if(merchantServiceDto.getInstructions().trim().equals("")){
			logger.debug("instruction null");
			merchantServiceError.setInstructions("Instruction Required");
			valid = false;
		}
		if(merchantServiceDto.isPriceInput()){
			if (merchantServiceDto.getPriceRange() == null) {
				logger.debug("price range null");
				merchantServiceError.setPriceRange("Invalid Price Range");
				valid = false;
			}else{
				try{
					String[] rangeList = merchantServiceDto.getPriceRange().split(",");
					for(String range : rangeList){
						Long.parseLong(range.trim());
					}
					
				}catch(Exception e){
					logger.debug("invalid price range");
					merchantServiceError.setPriceRange("Invalid Price Range");
					valid = false;
				}
			}
		}
		
		if(merchantServiceDto.isFixedlabelSize()){
			if (merchantServiceDto.getLabelSize() == null) {
				logger.debug("Label Size null");
				merchantServiceError.setLabelSize("Invalid Label Size");
				valid = false;
			}else if(merchantServiceDto.getLabelSize().trim().equals("")){
				logger.debug("Invalid Label Size");
				merchantServiceError.setLabelSize("Invalid Label Size");
				valid = false;
			}
		}else{
			if (merchantServiceDto.getLabelMinLength() == null) {
				logger.debug("label min length null");
				merchantServiceError.setLabelMinLength("Label Min Length Required");
				valid = false;
			}else if(merchantServiceDto.getLabelMinLength().trim().equals("")){
				logger.debug("label min length null");
				merchantServiceError.setLabelMinLength("Label Min Length Required");
				valid = false;
			}else{
				try{
					Long.parseLong(merchantServiceDto.getLabelMinLength());
				}catch(Exception e){
					logger.debug("invalid label min length");
					merchantServiceError.setLabelMinLength("Invalid Label Min Length");
					valid = false;
				}
			}
			if (merchantServiceDto.getLabelMaxLength() == null) {
				logger.debug("label max length null");
				merchantServiceError.setLabelMaxLength("Label Max Length Required");
				valid = false;
			}else if(merchantServiceDto.getLabelMaxLength().trim().equals("")){
				logger.debug("label max length null");
				merchantServiceError.setLabelMaxLength("Label Max Length Required");
				valid = false;
			}else{
				try{
					Long.parseLong(merchantServiceDto.getLabelMaxLength());
					if(Long.parseLong(merchantServiceDto.getLabelMaxLength()) <= Long.parseLong(merchantServiceDto.getLabelMinLength())){
						logger.debug("label max length less than min Length");
						merchantServiceError.setLabelMaxLength("Label Max Length must be greater than Label Min Length");
						valid = false;
					}
				}catch(Exception e){
					logger.debug("invalid label max length");
					merchantServiceError.setLabelMaxLength("Invalid Label Max Length");
					valid = false;
				}
			}
		}
		merchantServiceError.setValid(valid);
		return merchantServiceError;
	}
}
