package com.mobilebanking.api;

import com.mobilebanking.model.ConfirmationCodeDTO;
import com.mobilebanking.model.ConfirmationCodeType;




public interface IConfirmationCodeApi {
	
	
	ConfirmationCodeDTO createConfirmationCode( ConfirmationCodeType casType,
			String generatedTo,  double cicoAmount);

	
	boolean consumeConfirmation(ConfirmationCodeType casType,
			String generatedTo,  double cicoAmount, long cid, String code);

}
