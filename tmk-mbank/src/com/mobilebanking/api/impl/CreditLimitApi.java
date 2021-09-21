package com.mobilebanking.api.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.ICreditLimitApi;
import com.mobilebanking.entity.CreditLimit;
import com.mobilebanking.repositories.CreditLimitRepository;
import com.mobilebanking.util.DateUtil;

@Service
public class CreditLimitApi implements ICreditLimitApi{

	@Autowired
	CreditLimitRepository creditLimitRepo;
	
	@Override
	public Map<String, Object> getCreditLimitReportData() {
		// TODO Auto-generated method stub
		Map<String, Object> creditLimitMap=new HashMap<String, Object>();
		Map<String, String> map=null;
		for(CreditLimit creditlimit:creditLimitRepo.findAll()) {
			map=new HashMap<String, String>();
			map.put("date",DateUtil.convertDateToString(creditlimit.getCreated()));
			map.put("amount",creditlimit.getAmount().toString());
			creditLimitMap.put(creditlimit.getBank().getName(), map);
			
		}
		return creditLimitMap;
	}

}
