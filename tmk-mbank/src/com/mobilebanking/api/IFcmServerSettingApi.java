package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.FcmServerSettingDTO;

public interface IFcmServerSettingApi {

	void saveServerSetting(FcmServerSettingDTO fcmServerSetting);
	
	List<FcmServerSettingDTO> listFcmServerSetting();

	FcmServerSettingDTO getByServerKey(String serverKey);

	List<BankDTO> getNotAddedBank(String serverKey);

	void addBankToFcmServer(String serverKey, String[] bankSwiftCodeList);

	void removeBankFromFcmServer(String swiftCode , String serverKey);

	Object getAddedBank(String serverKey);
	
}
