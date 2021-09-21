package com.mobilebanking.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobilebanking.api.INeaOfficeCodeApi;
import com.mobilebanking.model.NeaOfficeCodeDTO;
import com.mobilebanking.model.error.NeaOfficeCodeError;

@Component
public class NeaOfficeCodeValidation {

	private NeaOfficeCodeError neaOfficeCodeError;

	@Autowired
	private INeaOfficeCodeApi neaOfficeCodeApi;

	public NeaOfficeCodeError officeCodeValidation(NeaOfficeCodeDTO dto) {

		neaOfficeCodeError = new NeaOfficeCodeError();

		boolean valid = true;

		valid = valid && checkOffice(dto);

		valid = valid && checkOfficeCode(dto);

		neaOfficeCodeError.setValid(valid);
		return neaOfficeCodeError;
	}

	public boolean onlyContainsNumbers(String number) {
		try {
			Long.parseLong(number);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	public boolean checkOffice(NeaOfficeCodeDTO dto) {

		if (dto.getOffice() == null) {
			neaOfficeCodeError.setOffice("Counter Name is null");
			return false;
		} else if (dto.getOffice().trim().equals("")) {
			neaOfficeCodeError.setOffice("Counter Name Required");
			return false;
		}
		return true;
	}

	public boolean checkOfficeCode(NeaOfficeCodeDTO dto) {
		if (dto.getOfficeCode() == null) {
			neaOfficeCodeError.setOfficeCodes("Counter Code Required");
			return false;
		} else if (dto.getOfficeCode().trim().equals("")) {
			neaOfficeCodeError.setOfficeCodes("Counter Code Required");
			return false;
		} else {
			if (dto.getId() == 0) {
				NeaOfficeCodeDTO counter = neaOfficeCodeApi.getByOfficeCode(dto.getOfficeCode());
				if (counter != null) {
					neaOfficeCodeError.setOfficeCodes("Counter Code Already Exists");
					return false;
				}
			} else {
				NeaOfficeCodeDTO counter = neaOfficeCodeApi.getNeaOfficeCode(dto.getId());
				if (counter != null) {
					if (!(counter.getOfficeCode().equalsIgnoreCase(dto.getOfficeCode()))) {
						counter = neaOfficeCodeApi.getByOfficeCode(dto.getOfficeCode());
						if (counter != null) {
							neaOfficeCodeError.setOfficeCodes("Counter Code Already Exists");
							return false;
						}
					}
				}
			}
		}
		return true;
	}

}
