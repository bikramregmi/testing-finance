package com.wallet.api;

import java.net.UnknownHostException;

import com.wallet.model.DishHomeDto;

public interface IDishHomeApi {

	DishHomeDto balanceEnquiry(String serviceI) throws UnknownHostException;
	
	boolean dishHomeRecharge(String serviceI,double amount,long aid) throws Exception;
	
	boolean rechargeBySTBIDorCASIDorCHIPID(String serviceI,double amount,String casId,long aid) throws Exception;
}
