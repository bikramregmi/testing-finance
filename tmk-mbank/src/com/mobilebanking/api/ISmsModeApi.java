/**
 * 
 */
package com.mobilebanking.api;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import com.mobilebanking.model.SmsModeDto;
import com.mobilebanking.model.SmsType;
import com.mobilebanking.model.Status;
import com.mobilebanking.util.ClientException;

/**
 * @author bibek
 *
 */
public interface ISmsModeApi {
	
	SmsModeDto save(SmsModeDto smsModeDto) throws IOException, JSONException, ClientException;
	
	List<SmsModeDto> findAllSmsMode();	
	
	List<SmsModeDto> findSmsModeByBank(String bankName);
	
	SmsModeDto findSmsModeById(long id);
	
	SmsModeDto edit(SmsModeDto smsModeDto) throws Exception;

	String findSmsModeMessageBySmsType(String smsType, Status active);
	
	//Boolean changeStatus(Long id ,Status status);
	Boolean changeStatus(Long id );
	Boolean removeCustomSms(Long id );


	List<SmsType> getSmsTypesForBankToAdd(String bankName);
}
