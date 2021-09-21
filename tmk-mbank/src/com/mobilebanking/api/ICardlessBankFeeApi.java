package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.model.CardlessBankFeeDTO;
import com.mobilebanking.model.CardlessBankFeeDTOList;

public interface ICardlessBankFeeApi {
	
	public void updateFee(CardlessBankFeeDTOList cardlessBankFeeDTOList);

	public List<CardlessBankFeeDTO> getFeeByCardlessBankId(Long id);
	
}
