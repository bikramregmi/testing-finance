package com.mobilebanking.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.ICardlessBankFeeApi;
import com.mobilebanking.converter.CardlessBankFeeConverter;
import com.mobilebanking.entity.CardlessBankFee;
import com.mobilebanking.model.CardlessBankFeeDTO;
import com.mobilebanking.model.CardlessBankFeeDTOList;
import com.mobilebanking.model.Status;
import com.mobilebanking.repositories.CardlessBankFeeRepository;
import com.mobilebanking.repositories.CardlessBankRepository;

@Service
public class CardlessBankFeeApi implements ICardlessBankFeeApi {

	@Autowired
	private CardlessBankRepository cardlessBankRepository;

	@Autowired
	private CardlessBankFeeRepository cardlessBankFeeRepository;

	@Autowired
	private CardlessBankFeeConverter cardlessBankFeeConverter;

	@Override
	public void updateFee(CardlessBankFeeDTOList cardlessBankFeeDTOList) {
		inactivePreviousFee(cardlessBankFeeRepository
				.findFeeByCardlessBankIdAndStatus(cardlessBankFeeDTOList.getCardlessBankId(), Status.Active));
		saveFee(cardlessBankFeeDTOList);
	}

	private void inactivePreviousFee(List<CardlessBankFee> feeList) {
		for (CardlessBankFee fee : feeList) {
			fee.setStatus(Status.Inactive);
			cardlessBankFeeRepository.save(fee);
		}
	}

	private void saveFee(CardlessBankFeeDTOList cardlessBankFeeDTOList) {
		if (cardlessBankFeeDTOList.isSameforall()) {
			CardlessBankFee fee = new CardlessBankFee();
			fee.setCardlessBank(cardlessBankRepository.findOne(cardlessBankFeeDTOList.getCardlessBankId()));
			fee.setSameForAll(true);
			fee.setFee(cardlessBankFeeDTOList.getFee());
			fee.setStatus(Status.Active);
			cardlessBankFeeRepository.save(fee);
		} else {
			for (CardlessBankFeeDTO feeDTO : cardlessBankFeeDTOList.getFeeList()) {
				if (feeDTO.getFee() != null) {
					CardlessBankFee fee = new CardlessBankFee();
					fee.setCardlessBank(cardlessBankRepository.findOne(cardlessBankFeeDTOList.getCardlessBankId()));
					fee.setFromAmount(feeDTO.getFromAmount());
					fee.setToAmount(feeDTO.getToAmount());
					fee.setFee(feeDTO.getFee());
					fee.setSameForAll(false);
					fee.setStatus(Status.Active);
					cardlessBankFeeRepository.save(fee);
				}
			}
		}
	}

	@Override
	public List<CardlessBankFeeDTO> getFeeByCardlessBankId(Long id) {
		List<CardlessBankFee> feeList = cardlessBankFeeRepository.findFeeByCardlessBankIdAndStatus(id, Status.Active);
		if (feeList != null) {
			return cardlessBankFeeConverter.convertToDtoList(feeList);
		}
		return null;
	}

}
