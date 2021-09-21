package com.mobilebanking.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IChannelPartnerApi;
import com.mobilebanking.api.IUserApi;
import com.mobilebanking.entity.Account;
import com.mobilebanking.entity.ChannelPartner;
import com.mobilebanking.entity.City;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.AccountType;
import com.mobilebanking.model.ChannelPartnerDTO;
import com.mobilebanking.model.Status;
import com.mobilebanking.model.UserDTO;
import com.mobilebanking.model.UserType;
import com.mobilebanking.repositories.AccountRepository;
import com.mobilebanking.repositories.ChannelPartnerRepository;
import com.mobilebanking.repositories.CityRepository;
import com.mobilebanking.util.AccountUtil;
import com.mobilebanking.util.ConvertUtil;

@Service
public class ChannelPartnerApi implements IChannelPartnerApi {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private AccountUtil accountUtil;
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ChannelPartnerRepository channelPartnerRepository;

	@Autowired
	private IUserApi userApi;
	
	@Override
	public void saveChannelPartner(ChannelPartnerDTO channelPartnerDto) {
		City city = cityRepository.findCityByIdAndState(channelPartnerDto.getState(),Long.parseLong(channelPartnerDto.getCity()));

		ChannelPartner channelPartner = new ChannelPartner();
		channelPartner.setName(channelPartnerDto.getName());
		channelPartner.setCity(city);
		channelPartner.setAddress(channelPartnerDto.getAddress());
		channelPartner.setOwner(channelPartnerDto.getOwner());
		channelPartner.setStatus(Status.Active);
		channelPartner.setUniqueCode(channelPartnerDto.getUniqueCode());
		channelPartner.setPassCode(channelPartnerDto.getPassCode());

		channelPartner = channelPartnerRepository.save(channelPartner);
		String accountNumber = createAccount(channelPartner);
		channelPartner.setAccountNumber(accountNumber);

		channelPartner = channelPartnerRepository.save(channelPartner);

		
	}

	private String createAccount(ChannelPartner channelPartner) {
		Account account = new Account();
		account.setAccountHead(channelPartner.getName());
		account.setAccountType(AccountType.CHANNELPARTNER);
		account.setAccountNumber(accountUtil.generateAccountNumber());
		account.setBalance(0.0);
		account = accountRepository.save(account);
		return account.getAccountNumber();
	}

	private User createUser(ChannelPartner channelPartner) {
		UserDTO user = new UserDTO();
		user.setUserName(channelPartner.getUniqueCode());
		user.setAddress(channelPartner.getAddress());
		user.setCity(channelPartner.getCity().getId().toString());
		user.setState(channelPartner.getCity().getState().getName());
		user.setPassword(channelPartner.getPassCode());
		user.setUserType(UserType.ChannelPartner);
		user.setAssociatedId(channelPartner.getId());
		user.setFirstLogin(true);
		user.setUserTemplate("ChannelPartnerRole");
		return userApi.saveUser(user);
	}

	@Override
	public ChannelPartnerDTO findChannelPartnerById(Long id) {
		ChannelPartner channelPartner = channelPartnerRepository.findOne(id);
		if (channelPartner != null) {
			return ConvertUtil.convertChannelPartner(channelPartner);
		}
		return null;
	}

	@Override
	public List<ChannelPartnerDTO> findAllChannelPartner() {
		List<ChannelPartner> channelPartnersList = ConvertUtil
				.convertIterableToList(channelPartnerRepository.getAllChannelParter());
		return ConvertUtil.convertChannelPartnerListToDTO(channelPartnersList);
	}

	@Override
	public ChannelPartnerDTO editChannelPartner(ChannelPartnerDTO channelPartnerDto) {
		City city = cityRepository.findCityByIdAndState(channelPartnerDto.getState(),
				Long.parseLong(channelPartnerDto.getCity()));
		ChannelPartner channelPartner = channelPartnerRepository.findOne(channelPartnerDto.getId());
		channelPartner.setAddress(channelPartnerDto.getAddress());
		channelPartner.setCity(city);
		channelPartner.setStatus(channelPartnerDto.getStatus());
//		User user = userRepository.findByUsername(channelPartnerDto.getUniqueCode());
		channelPartner.setUniqueCode(channelPartnerDto.getUniqueCode());
		channelPartner.setPassCode(channelPartnerDto.getPassCode());
		channelPartnerRepository.save(channelPartner);
		return ConvertUtil.convertChannelPartner(channelPartner);

	}
 //added by amrit
	@Override
	public List<ChannelPartnerDTO> getAllChannelPartners() {
		// TODO Auto-generated method stub
		List<ChannelPartner> channelPartnerList=null;
		
			channelPartnerList=ConvertUtil.convertIterableToList(channelPartnerRepository.findAll());
			if(channelPartnerList!=null) {
				return ConvertUtil.convertChannelPartnerListToDTO(channelPartnerList);
			}
		
		return null;
	}


	public List<ChannelPartnerDTO> getChannelPartnerByNameLike(String name) {
		// TODO Auto-generated method stub
		List<ChannelPartner> channelPatnersList=ConvertUtil.convertIterableToList(channelPartnerRepository.getChannelPartnerByNameLike(name));
		if(channelPatnersList!=null && !channelPatnersList.isEmpty()) {
			return ConvertUtil.convertChannelPartnerListToDTO(channelPatnersList);
		}
		return null;
	}
}
//end added by amrit
