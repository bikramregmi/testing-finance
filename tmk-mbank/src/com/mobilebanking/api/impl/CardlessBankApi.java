package com.mobilebanking.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.ICardlessBankApi;
import com.mobilebanking.api.IUserApi;
import com.mobilebanking.converter.CardlessBankAccountsConverter;
import com.mobilebanking.converter.CardlessBankConverter;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankOAuthClient;
import com.mobilebanking.entity.CardlessBank;
import com.mobilebanking.entity.CardlessBankAccount;
import com.mobilebanking.entity.City;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.CardlessBankAccountDTO;
import com.mobilebanking.model.CardlessBankDTO;
import com.mobilebanking.model.Status;
import com.mobilebanking.model.UserDTO;
import com.mobilebanking.model.UserType;
import com.mobilebanking.repositories.BankOAuthClientRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.CardlessBankAccountsRepository;
import com.mobilebanking.repositories.CardlessBankRepository;
import com.mobilebanking.repositories.CityRepository;

@Service
public class CardlessBankApi implements ICardlessBankApi {

	@Autowired
	private CardlessBankRepository cardlessBankRepository;

	@Autowired
	private IUserApi userApi;

	@Autowired
	private CardlessBankConverter cardlessBankConverter;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private BankOAuthClientRepository bankAuthRepository;

	@Autowired
	private CardlessBankAccountsRepository cardlessBankAccountsRepository;

	@Autowired
	private CardlessBankAccountsConverter cardlessBankAccountsConverter;

	@Autowired
	private BankRepository bankRepository;

	@Override
	public CardlessBankDTO saveCardlessBank(CardlessBankDTO cardlessBankDto) {
		CardlessBank cardlessBank = new CardlessBank();
		cardlessBank = cardlessBankConverter.convertToEntity(cardlessBankDto);
		City city = cityRepository.findCityByIdAndState(cardlessBankDto.getState(),
				Long.parseLong(cardlessBankDto.getCity()));
		cardlessBank.setCity(city);
		cardlessBank = cardlessBankRepository.save(cardlessBank);

		cardlessBankDto.setId(cardlessBank.getId());
		User user = createUser(cardlessBankDto);
		cardlessBank.setUser(user);
		cardlessBank = cardlessBankRepository.save(cardlessBank);

		return cardlessBankConverter.convertToDto(cardlessBank);
	}

	private User createUser(CardlessBankDTO cardlessBankDto) {
		UserDTO user = new UserDTO();
		user.setAssociatedId(cardlessBankDto.getId());
		/*
		 * user.setUserName(cardlessBankDto.getUsername());
		 * user.setPassword(cardlessBankDto.getPassword());
		 */
		user.setUserName(cardlessBankDto.getBank());
		user.setPassword("123456");
		user.setState(cardlessBankDto.getState());
		user.setCity(cardlessBankDto.getCity());
		user.setAddress(cardlessBankDto.getAddress());
		user.setUserType(UserType.CardLessBank);
		user.setUserTemplate("CardLessBankRole");
		return userApi.saveUser(user);
	}

	@Override
	public List<CardlessBankDTO> getAllCardlessBank() {
		List<CardlessBank> cardlessBankList = cardlessBankRepository.findAll();
		if (cardlessBankList != null) {
			return cardlessBankConverter.convertToDtoList(cardlessBankList);
		}
		return null;
	}

	@Override
	public CardlessBankDTO getById(Long id) {
		CardlessBank cardlessBank = cardlessBankRepository.findOne(id);
		if (cardlessBank != null) {
			return cardlessBankConverter.convertToDto(cardlessBank);
		}
		return null;
	}

	@Override
	public CardlessBankDTO updateCardlessBank(CardlessBankDTO cardlessBankDTO) {
		CardlessBank cardlessBank = cardlessBankRepository.findOne(cardlessBankDTO.getId());
		cardlessBank.setBank(cardlessBankDTO.getBank());
		cardlessBank.setHost(cardlessBankDTO.getHost());
		cardlessBank.setPort(cardlessBankDTO.getPort());
		cardlessBank.setUserSign(cardlessBankDTO.getUserSign());
		cardlessBank.setUserPassword(cardlessBankDTO.getUserPassword());
		cardlessBank.setCompanyCode(cardlessBankDTO.getCompanyCode());
		cardlessBank.setAddress(cardlessBankDTO.getAddress());
		cardlessBank = cardlessBankRepository.save(cardlessBank);
		return cardlessBankConverter.convertToDto(cardlessBank);
	}

	@Override
	public List<CardlessBankDTO> findByClientId(String clientId) {
		BankOAuthClient bankAouthClient = bankAuthRepository.findByOAuthClientId(clientId);
		Bank bank = bankAouthClient.getBank();
		List<CardlessBankAccount> cardlessBankAccountList = cardlessBankAccountsRepository
				.findByBankCode(bank.getSwiftCode());
		List<CardlessBankDTO> cardlessBanklist = new ArrayList<>();
		for (CardlessBankAccount cardlessBankAccount : cardlessBankAccountList) {
			cardlessBanklist.add(cardlessBankConverter.convertToDto(cardlessBankAccount.getCardlessBank()));
		}
		return cardlessBanklist;
	}

	@Override
	public CardlessBankAccountDTO saveCardlessBankAccount(CardlessBankAccountDTO cardlessBankAccountDTO) {
		CardlessBankAccount cardlessBankAccount = new CardlessBankAccount();
		cardlessBankAccount.setBank(bankRepository.findBankByCode(cardlessBankAccountDTO.getBank()));
		cardlessBankAccount.setCardlessBank(cardlessBankRepository.findOne(cardlessBankAccountDTO.getCardlessBankId()));
		cardlessBankAccount.setAccountNumber(cardlessBankAccountDTO.getAccountNumber());
		cardlessBankAccount.setStatus(Status.Active);
		cardlessBankAccount.setBankAccountNo(cardlessBankAccountDTO.getBankAccountNo());
		cardlessBankAccount.setCardlessBankAccountNo(cardlessBankAccountDTO.getCardlessBankAccountNo());
		cardlessBankAccount.setDebitTheirRef(cardlessBankAccountDTO.getDebitTheirRef());
		cardlessBankAccount.setAtmBinNo(cardlessBankAccountDTO.getAtmBinNo());
		cardlessBankAccount.setAtmTermNo(cardlessBankAccountDTO.getAtmTermNo());
		cardlessBankAccount = cardlessBankAccountsRepository.save(cardlessBankAccount);
		return cardlessBankAccountsConverter.convertToDto(cardlessBankAccount);
	}

	@Override
	public CardlessBankAccountDTO editCardlessBankAccount(CardlessBankAccountDTO cardlessBankAccountDTO) {
		CardlessBankAccount cardlessBankAccount = cardlessBankAccountsRepository
				.findOne(cardlessBankAccountDTO.getId());
		cardlessBankAccount.setAccountNumber(cardlessBankAccountDTO.getAccountNumber());
		cardlessBankAccount.setStatus(cardlessBankAccountDTO.getStatus());
		cardlessBankAccount.setBankAccountNo(cardlessBankAccountDTO.getBankAccountNo());
		cardlessBankAccount.setCardlessBankAccountNo(cardlessBankAccountDTO.getCardlessBankAccountNo());
		cardlessBankAccount.setDebitTheirRef(cardlessBankAccountDTO.getDebitTheirRef());
		cardlessBankAccount.setAtmBinNo(cardlessBankAccountDTO.getAtmBinNo());
		cardlessBankAccount.setAtmTermNo(cardlessBankAccountDTO.getAtmTermNo());
		cardlessBankAccount = cardlessBankAccountsRepository.save(cardlessBankAccount);
		return cardlessBankAccountsConverter.convertToDto(cardlessBankAccount);
	}

	@Override
	public CardlessBankAccountDTO getCardlessBankAccountById(Long id) {
		CardlessBankAccount cardlessBankAccount = cardlessBankAccountsRepository.findOne(id);
		if (cardlessBankAccount != null) {
			return cardlessBankAccountsConverter.convertToDto(cardlessBankAccount);
		}
		return null;
	}

	@Override
	public List<CardlessBankAccountDTO> getCardlessBankAccountByBank(String bankCode) {
		List<CardlessBankAccount> accounts = cardlessBankAccountsRepository.findByBankCode(bankCode);
		if (accounts != null) {
			return cardlessBankAccountsConverter.convertToDtoList(accounts);
		}
		return null;
	}

	@Override
	public List<CardlessBankDTO> getCardlessBankNotInBank(String bankCode) {
		List<CardlessBank> cardlessBankList = cardlessBankRepository.findCardlessBankNotInBank(bankCode);
		if (cardlessBankList != null) {
			return cardlessBankConverter.convertToDtoList(cardlessBankList);
		}
		return null;
	}

}
