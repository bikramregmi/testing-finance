package com.wallet.serviceprovider.epay.service;

import java.net.UnknownHostException;
import java.util.HashMap;

public interface Epay_Service {

	void getBalance() throws UnknownHostException;
	void getStatus();
	void addPayment();
	void addPayment(HashMap<String, String> hashRequest);
	void onlinePayment(HashMap<String, String> hashRequest);
	void onlinePaymentTest();
}
