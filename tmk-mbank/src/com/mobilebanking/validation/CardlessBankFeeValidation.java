package com.mobilebanking.validation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.model.CardlessBankFeeDTO;
import com.mobilebanking.model.CardlessBankFeeDTOList;
import com.mobilebanking.model.error.CardlessBankFeeError;

@Component
public class CardlessBankFeeValidation {

	public CardlessBankFeeError validateCardlessBankFee(CardlessBankFeeDTOList cardlessBankFeeDTOList) {
		CardlessBankFeeError error = new CardlessBankFeeError();
		boolean valid = true;
		String errorMessage;
		if (cardlessBankFeeDTOList.isSameforall()) {
			errorMessage = checkFee(cardlessBankFeeDTOList.getFee());
			if (errorMessage != null) {
				error.setFee(errorMessage);
				valid = false;
			}
		} else {
			errorMessage = checkTableData(cardlessBankFeeDTOList.getFeeList());
			if (errorMessage != null) {
				error.setTableData(errorMessage);
				valid = false;
			}
		}
		error.setValid(valid);
		return error;
	}

	private String checkTableData(List<CardlessBankFeeDTO> feeList) {
		for (CardlessBankFeeDTO fee : feeList) {
			if ((fee.getFromAmount() != null) || (fee.getToAmount() != null) || (fee.getFee() != null)) {
				if (fee.getFromAmount() == null || fee.getToAmount() == null || fee.getFee() == null) {
					return "Invalid Data";
				}
			}
		}
		return null;
	}

	private String checkFee(Double fee) {
		if (fee == null) {
			return "Invalid fee";
		}
		return null;
	}

}
