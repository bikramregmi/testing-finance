package com.wallet.api;

public interface IEpayApi {

	boolean getStatus();
	boolean getBalance(String serviceI);
	boolean onlinePayment(String serviceI,double amount,long aid);
	boolean addPayment();
	boolean addPayment(String serviceI,double amount,long aid);
}
