package com.mobilebanking.api.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IMerchantApi;
import com.mobilebanking.entity.Account;
import com.mobilebanking.entity.City;
import com.mobilebanking.entity.ManagedService;
import com.mobilebanking.entity.Merchant;
import com.mobilebanking.entity.MerchantManager;
import com.mobilebanking.entity.MerchantService;
import com.mobilebanking.model.AccountType;
import com.mobilebanking.model.MerchantDTO;
import com.mobilebanking.model.Status;
import com.mobilebanking.repositories.AccountRepository;
import com.mobilebanking.repositories.CityRepository;
import com.mobilebanking.repositories.MerchantManagerRepository;
import com.mobilebanking.repositories.MerchantRepository;
import com.mobilebanking.repositories.MerchantServiceRepository;
import com.mobilebanking.util.AccountUtil;
import com.mobilebanking.util.ConvertUtil;

@Service
public class MerchantApi implements IMerchantApi {

	@Autowired
	private MerchantRepository merchantRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private MerchantServiceRepository merchantServiceRepository;
	
	@Autowired
	private MerchantManagerRepository merchantManagerRepository;
	
	@Autowired
	private AccountUtil accountUtil;

	@Override
	public MerchantDTO findMerchantById(long merchantId) {
		Merchant merchant = merchantRepository.getMerchantById(merchantId);
		if (merchant == null) {
			return null;
		}
		return ConvertUtil.convertMerchant(merchant);
	}

	@Override
	public MerchantDTO saveMerchant(MerchantDTO merchantDto) throws Exception {
			
			Merchant merchant = new Merchant();
			City city = cityRepository.findCityByIdAndState(merchantDto.getState(), Long.parseLong(merchantDto.getCity()));
			merchant.setName(merchantDto.getName());
			merchant.setCity(city);
			merchant.setDescription(merchantDto.getDescription());
			merchant.setLandLine(merchantDto.getLandLine());
			merchant.setMobileNumber(merchantDto.getMobileNumber());
			merchant.setOwnerName(merchantDto.getOwnerName());
			merchant.setRegistrationNumber(merchantDto.getRegistrationNumber());
			merchant.setVatNumber(merchantDto.getVatNumber());
//			merchant.setAccountNumber(AccountUtil.generateAccountNumber());
			merchant.setAddress(merchantDto.getAddress());
			merchant.setStatus(Status.Active);
			merchant.setMerchantApiUrl(merchantDto.getApiUrl());
			merchant.setApiPassWord(merchantDto.getApiPassword());
			merchant.setApiUsername(merchantDto.getApiUsername());
			merchant.setExtraFieldOne(merchantDto.getExtraField1());
			merchant.setExtraFieldTwo(merchantDto.getExtraField2());
			merchant.setExtraFieldThree(merchantDto.getExtraField3());
			merchant = merchantRepository.save(merchant);
			
			createAccount(merchant, 0.0);
			
			return ConvertUtil.convertMerchant(merchant);
	}
	
	//create user later this one
	public void createUser(Merchant merchant) throws Exception {
		
	}
	
	public void createAccount(Merchant merchant, double balance) {
		Account account = new Account();
		account.setAccountHead(merchant.getName());
		account.setAccountNumber(accountUtil.generateAccountNumber());
		account.setAccountType(AccountType.MERCHANT);
		account.setBalance(balance);
		//account.setUser(user);
		accountRepository.save(account);
		merchant.setAccountNumber(account.getAccountNumber());
		merchantRepository.save(merchant);
	}

	@Override
	public List<MerchantDTO> getAllMerchant() {
		List<Merchant> merchantList = ConvertUtil.convertIterableToList(merchantRepository.findAll());
		if (! merchantList.isEmpty()) {
			return ConvertUtil.convertMerchantToDTO(merchantList);
		}
		return null ;
		
	}
	
	@Override
	public void deleteMerchant(long merchantid) {
		merchantRepository.delete(merchantid);
	}

	@Override
	public MerchantDTO editMerchant(MerchantDTO merchantDTO){
		City city = cityRepository.findCityByIdAndState(merchantDTO.getState(), Long.parseLong(merchantDTO.getCity()));
		Merchant merchant = merchantRepository.findOne(merchantDTO.getId());
		merchant.setName(merchantDTO.getName());
		merchant.setCity(city);
		merchant.setAddress(merchantDTO.getAddress());
		merchant.setDescription(merchantDTO.getDescription());
		merchant.setStatus(merchantDTO.getStatus());
		merchant.setMerchantApiUrl(merchantDTO.getApiUrl());
		merchant.setApiPassWord(merchantDTO.getApiPassword());
		merchant.setApiUsername(merchantDTO.getApiUsername());
		merchant.setExtraFieldOne(merchantDTO.getExtraField1());
		merchant.setExtraFieldTwo(merchantDTO.getExtraField2());
		merchant.setExtraFieldThree(merchantDTO.getExtraField3());
		merchantRepository.save(merchant);
		return ConvertUtil.convertMerchant(merchant);
	}

	@Override
	public List<MerchantDTO> getMerchantByStatus(Status status) {
		List<Merchant> merchantList = ConvertUtil.convertIterableToList(
				merchantRepository.findMerchantByStatus(status));
		if (!merchantList.isEmpty()) {
			return ConvertUtil.convertMerchantToDTO(merchantList);
		}
		return null;
	}

	@Override
	public void addMerchantServiceToMerchant(long merchantServiceId, long merchantId) {
MerchantService services  = merchantServiceRepository.findOne(merchantServiceId);
		
		Merchant serviceProvider = merchantRepository.findOne(merchantId);
		
		ManagedService merchantsAndServices = new ManagedService();
		merchantsAndServices.setMerchant(serviceProvider);
		merchantsAndServices.setMerchantService(services);
		
		MerchantManager merchantManager = new MerchantManager();
		merchantManager.setCreated(new Date());
		merchantManager.setMerchantsAndServices(merchantsAndServices);
		merchantManager.setStatus(Status.Active);
		merchantManager.setSelected(false);
		merchantManagerRepository.save(merchantManager);

	}
}
