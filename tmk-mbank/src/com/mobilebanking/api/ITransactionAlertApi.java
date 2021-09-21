package com.mobilebanking.api;

import java.lang.reflect.InvocationTargetException;

import com.mobilebanking.model.PagablePage;
import com.mobilebanking.model.TransactionAlertDTO;

public interface ITransactionAlertApi {

	Boolean sentTransactionAlert(TransactionAlertDTO trasactionAlertDto) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;
   //added by amrit
	PagablePage getTransactionAlertList(Integer pageNo, String fromDate, String toDate, String mobileNo,
			String swiftCode, String channelPartner);
   //end added
}
