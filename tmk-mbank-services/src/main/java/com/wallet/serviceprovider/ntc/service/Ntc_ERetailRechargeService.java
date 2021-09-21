package com.wallet.serviceprovider.ntc.service;

import java.util.HashMap;

import com.wallet.serviceprovider.ntc.WebServiceException_Exception;

public interface Ntc_ERetailRechargeService {

	HashMap<String, String> mobileTopUp(HashMap<String, String> myHash) throws WebServiceException_Exception;

}
