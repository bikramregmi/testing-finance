package com.wallet.api;

import java.net.UnknownHostException;

public interface IUnitedSolutionsApi {

	boolean checkBalance(String serviceI) throws UnknownHostException;
	boolean flightAvailability();
}
