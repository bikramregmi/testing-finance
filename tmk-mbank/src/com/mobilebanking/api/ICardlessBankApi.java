package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.model.CardlessBankAccountDTO;
import com.mobilebanking.model.CardlessBankDTO;

public interface ICardlessBankApi {
	
	public CardlessBankDTO saveCardlessBank(CardlessBankDTO cardlessBankDto);
	
	public List<CardlessBankDTO> getAllCardlessBank();

	public CardlessBankDTO getById(Long id);

	public CardlessBankDTO updateCardlessBank(CardlessBankDTO cardlessBankDTO);

	public List<CardlessBankDTO> findByClientId(String clientId);

	public CardlessBankAccountDTO saveCardlessBankAccount(CardlessBankAccountDTO cardlessBankAccountDTO);

	public CardlessBankAccountDTO editCardlessBankAccount(CardlessBankAccountDTO cardlessBankAccountDTO);

	public CardlessBankAccountDTO getCardlessBankAccountById(Long id);

	public List<CardlessBankAccountDTO> getCardlessBankAccountByBank(String bankCode);

	public List<CardlessBankDTO> getCardlessBankNotInBank(String bankCode);
	

}
