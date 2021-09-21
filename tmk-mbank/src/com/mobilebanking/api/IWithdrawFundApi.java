package com.mobilebanking.api;

import com.mobilebanking.entity.User;
import com.mobilebanking.util.ClientException;

public interface IWithdrawFundApi {

	void withdrawFund(User fromUser, String financialInstitution, double amount) throws ClientException;

}
