/**
 * 
 */
package com.mobilebanking.api.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.ISmsModeApi;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.SmsMode;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.SmsModeDto;
import com.mobilebanking.model.SmsType;
import com.mobilebanking.model.Status;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.SmsModeRepository;
import com.mobilebanking.repositories.UserRepository;
import com.mobilebanking.util.ClientException;
import com.mobilebanking.util.ConvertUtil;

/**
 * @author bibek
 *
 */
@Service
public class SmsModeApi implements ISmsModeApi {
	
	@Autowired
	private SmsModeRepository smsModeRepository;
	
	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public SmsModeDto save(SmsModeDto smsModeDto) throws IOException, JSONException, ClientException {
		SmsMode smsMode = new SmsMode();
		User createdBy = userRepository.findOne(Long.parseLong(smsModeDto.getCreatedBy()));
		Bank bank = bankRepository.findByBank(smsModeDto.getBank());
		smsMode.setBank(bank);
		smsMode.setSmsType(smsModeDto.getSmsType());
		smsMode.setStatus(Status.Active);
		smsMode.setMessage(smsModeDto.getMessage());
		smsMode.setCreatedBy(createdBy);
		smsMode = smsModeRepository.save(smsMode);
		return ConvertUtil.convertSmsModeToDto(smsMode);
	}

	/* (non-Javadoc)
	 * @see com.mobilebanking.api.ISmsModeApi#findAllSmsMode()
	 */
	@Override
	public List<SmsModeDto> findAllSmsMode() {
		List<SmsMode> smsModeList = ConvertUtil.convertIterableToList(smsModeRepository.findAllSmsMode());
		if (! smsModeList.isEmpty()) {
			return ConvertUtil.convertSmsModeToDtoList(smsModeList);
		}
		return null;
	}

	@Override
	public List<SmsModeDto> findSmsModeByBank(String bankName) {
		List<SmsMode> smsModeList = ConvertUtil.convertIterableToList(
				smsModeRepository.findSmsModeByBank(bankName));
		if (! smsModeList.isEmpty()) {
			return ConvertUtil.convertSmsModeToDtoList(smsModeList);
		}
		return null;
	}

	@Override
	public SmsModeDto findSmsModeById(long id) {
		SmsMode smsMode = smsModeRepository.findOne(id);
		if (smsMode != null) {
			return ConvertUtil.convertSmsModeToDto(smsMode);
		}
		return null;
	}

	@Override
	public SmsModeDto edit(SmsModeDto smsModeDto) throws Exception {
		SmsMode smsMode = smsModeRepository.findOne(smsModeDto.getId());
		smsMode.setMessage(smsModeDto.getMessage());
		smsMode.setSmsType(smsModeDto.getSmsType());
		smsMode = smsModeRepository.save(smsMode);
		return ConvertUtil.convertSmsModeToDto(smsMode);
	}


	//added by amrit
	@Override
	public String findSmsModeMessageBySmsType(String type, Status active) {
		SmsType smsType = SmsType.valueOf(type);
		String message = smsModeRepository.findSmsModeMessageByBankAndSmsType(smsType, Status.Active);
		if (message != null) {
			return message;
		}
		return null;
	}

/*	@Override
	public Boolean changeStatus(Long id, Status status) {
		// TODO Auto-generated method stub
		boolean result=false;
		try{
			SmsMode smsMode=smsModeRepository.findOne(id);
			//System.out.println("status before :"+smsMode.getStatus().getValue());
			smsMode.setStatus(status);
			//System.out.println("status after :"+smsMode.getStatus().getValue());
			smsModeRepository.save(smsMode);
			result=true;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("exception :"+e.getMessage());
			return result;
		}
		return result;
	}*/
	@Override
	public Boolean changeStatus(Long id) {
		// TODO Auto-generated method stub
		boolean result=false;
		try{
			SmsMode smsMode=smsModeRepository.findOne(id);
			//System.out.println("status before :"+smsMode.getStatus().getValue());
			if(smsMode.getStatus()==Status.Active) {
			smsMode.setStatus(Status.Inactive);
			}
			else if(smsMode.getStatus()==Status.Inactive) {
				smsMode.setStatus(Status.Active);
			}
			//System.out.println("status after :"+smsMode.getStatus().getValue());
			smsModeRepository.save(smsMode);
			result=true;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("exception :"+e.getMessage());
			return result;
		}
		return result;
	}
	@Override
	public List<SmsType> getSmsTypesForBankToAdd(String bankName) {
		SmsType [] smsTypes= SmsType.values();
		 
		List<SmsType> existingSmsTypeList=new ArrayList<SmsType>();
		List<SmsType> resultList=new ArrayList<SmsType>(Arrays.asList(smsTypes));
	  try {
		Iterator iterator = findSmsModeByBank(bankName).iterator();
		 while(iterator.hasNext()) {
				
				SmsModeDto smsModeDto = (SmsModeDto) iterator.next();
				existingSmsTypeList.add(smsModeDto.getSmsType());
			    }
		
		}catch(NullPointerException ne) {
			return resultList;
		}
	    resultList.removeAll(existingSmsTypeList);
	    return resultList;
	}
//end added

	@Override
	public Boolean removeCustomSms(Long id) {
		boolean result=false;
		try{
			smsModeRepository.delete(id);
			result=true;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("exception :"+e.getMessage());
			return result;
		}
		return result;
	}


}
