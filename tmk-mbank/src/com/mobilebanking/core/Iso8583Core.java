/**
 * 
 */
package com.mobilebanking.core;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.Iso8583Log;
import com.mobilebanking.model.IsoRequestType;
import com.mobilebanking.model.IsoStatus;
import com.mobilebanking.repositories.CustomerBankAccountRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.repositories.Iso8583LogRepository;
import com.mobilebanking.util.DateUtil;
import com.mobilebanking.util.STANGenerator;
import com.wallet.iso8583.core.NavaJeevanIsoCore;
import com.wallet.iso8583.network.NetworkTransportNavaJeevan;
import com.wallet.iso8583.parser.ParseFields;

/**
 * @author bibek
 *
 */
@Component
public class Iso8583Core {
	
	@Autowired
	private CustomerBankAccountRepository customerBankRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private NavaJeevanIsoCore navaJeevanIsoCore;
	@Autowired
	private NetworkTransportNavaJeevan networkTransporstNavaJeevan;
	@Autowired
	private Iso8583LogRepository isoLogRepository;
	@Autowired
	private ParseFields fieldParser;
	
	
	public Double balanceInquiryCustomer(String uniqueId, String accountNumber, String branchId) {
		Customer customer = customerRepository.findCustomerByUniqueId(uniqueId);
		if (customer != null) {
			HashMap<String, String> isoData = new HashMap<String, String>();
			isoData.put("2", "123467890123456");
			isoData.put("3", "350000");
			isoData.put("7",DateUtil.convertDateToStringForIso(new Date()));
			isoData.put("11",STANGenerator.StanForIso(""));
			isoData.put("12", DateUtil.convertDateToStringForIso(new Date()).substring(4,10));
			isoData.put("13",DateUtil.convertDateToStringForIso(new Date()).substring(0,4));
			isoData.put("14","0000");
			isoData.put("15", DateUtil.convertDateToStringForIso(new Date()).substring(0,4));
			isoData.put("18","1244");
			isoData.put("25", "00");
			isoData.put("22", "000");
			isoData.put("32","15548116");
			isoData.put("37", DateUtil.convertDateToStringForIso(new Date())+"98");
			isoData.put("41", "15548116");
			isoData.put("49", "524");
			isoData.put("102", accountNumber);
			String packedData = "";
			String response = "";
			try {
				packedData = navaJeevanIsoCore.packMessage("0200", isoData);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				response = networkTransporstNavaJeevan.transportIsoMessage("", 1265, packedData);
				if (response.equalsIgnoreCase("failure")) {
					Iso8583Log isoLog = new Iso8583Log();
					isoLog.setIsoRequest(packedData);
					isoLog.setRequestType(IsoRequestType.BALANCEINQUIRY);
					isoLog.setIsoResponse(response);
					isoLog.setStatus(IsoStatus.NORESPONSE);
					isoLogRepository.save(isoLog);
				}
			
				HashMap<String, String> unpackedData = navaJeevanIsoCore.unpackMessage(response);
				if (unpackedData.get("39").equals("00")) {
					//now parse the field 47 if account holder name is to be displayed
					Iso8583Log isoLog = new Iso8583Log();
					isoLog.setIsoRequest(packedData);
					isoLog.setIsoResponse(response);
					isoLog.setRequestType(IsoRequestType.BALANCEINQUIRY);
					isoLog.setStatus(IsoStatus.SUCCESS);
					isoLog.setResponseCode("00");
					isoLogRepository.save(isoLog);
					Double balance = fieldParser.parseAmountField54(unpackedData.get("54"));
					return balance;
				} else {
					Iso8583Log isoLog = new Iso8583Log();
					isoLog.setIsoRequest(packedData);
					isoLog.setIsoResponse(response);
					isoLog.setRequestType(IsoRequestType.BALANCEINQUIRY);
					isoLog.setStatus(IsoStatus.FAILURE);
					isoLog.setResponseCode(unpackedData.get("39"));
					isoLogRepository.save(isoLog);
					return null;
				}
			} catch (IOException  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
}

/*
 * public Double getCustomerBankBalance(String uniqueId, String accountNumber) {
		Customer customer = customerRepository.findCustomerByUniqueId(uniqueId);
		if (customer != null) {
			//get bank of the customer so that we can call dynamically to bankISO
			//CustomerBankAccount account = customerbankAccountRepository.findCustomerAccountByAccountNumber(accountNumber, customer.getId());
				//from here we make call to bank using ISO8583 with account and customer information
//			CustomerBankAccount customerBankAccount = customerbankAccountRepository.findCustomerAccountByAccountNumber(accountNumber, customer.getId());
//			if (accountNumber.length() < 23) {
//				accountNumber = customerBankAccount.getBranch().getBranchCode() + accountNumber;
//				System.out.println("here + IN THE ACCOUNT PART");
//			}
			HashMap<String, String> isoData = new HashMap<String,String>();
			String date = DateUtil.convertDateToString(new Date());
			String[] dateArray = date.split("/");
			isoData.put("2", "123467890123456");
			isoData.put("3", "350000");
			isoData.put("7",DateUtil.convertDateToStringForIso(new Date()));
			isoData.put("11",STANGenerator.StanForIso(""));
			isoData.put("12", DateUtil.convertDateToStringForIso(new Date()).substring(4,10));
			isoData.put("13",DateUtil.convertDateToStringForIso(new Date()).substring(0,4));
			isoData.put("14","0000");
			isoData.put("15", DateUtil.convertDateToStringForIso(new Date()).substring(0,4));
			isoData.put("18","1244");
			isoData.put("25", "00");
			isoData.put("22", "000");
			isoData.put("32","15548116");
			isoData.put("37", DateUtil.convertDateToStringForIso(new Date())+"98");
			isoData.put("41", "15548116");
			isoData.put("49", "524");
			isoData.put("102", accountNumber);
			String packedData = "";
			String response = "";
			try {
				packedData = navaJeevanIsoCore.packMessage("0200", isoData);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				response = networkTransporstNavaJeevan.transportIsoMessage("", 1265, packedData);
				if (response.equalsIgnoreCase("failure")) {
					Iso8583Log isoLog = new Iso8583Log();
					isoLog.setIsoRequest(packedData);
					isoLog.setRequestType(IsoRequestType.BALANCEINQUIRY);
					isoLog.setIsoResponse(response);
					isoLog.setStatus(IsoStatus.NORESPONSE);
					isoLogRepository.save(isoLog);
				}
			
				HashMap<String, String> unpackedData = navaJeevanIsoCore.unpackMessage(response);
				if (unpackedData.get("39").equals("00")) {
					//now parse the field 47 if account holder name is to be displayed
					Iso8583Log isoLog = new Iso8583Log();
					isoLog.setIsoRequest(packedData);
					isoLog.setIsoResponse(response);
					isoLog.setRequestType(IsoRequestType.BALANCEINQUIRY);
					isoLog.setStatus(IsoStatus.SUCCESS);
					isoLog.setResponseCode("00");
					isoLogRepository.save(isoLog);
					Double balance = fieldParser.parseAmountField54(unpackedData.get("54"));
					return balance;
				} else {
					Iso8583Log isoLog = new Iso8583Log();
					isoLog.setIsoRequest(packedData);
					isoLog.setIsoResponse(response);
					isoLog.setRequestType(IsoRequestType.BALANCEINQUIRY);
					isoLog.setStatus(IsoStatus.FAILURE);
					isoLog.setResponseCode(unpackedData.get("39"));
					isoLogRepository.save(isoLog);
					return null;
				}
			} catch (IOException  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		return null;
	}
 * */
