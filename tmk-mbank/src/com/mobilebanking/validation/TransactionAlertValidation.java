package com.mobilebanking.validation;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.ChannelPartner;
import com.mobilebanking.model.Status;
import com.mobilebanking.model.TransactionAlertDTO;
import com.mobilebanking.model.TransactionAlertResponseCode;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.ChannelPartnerRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.repositories.SmsAlertTraceRepository;

@Component
public class TransactionAlertValidation {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private SmsAlertTraceRepository alertTraceRepository;

	@Autowired
	private ChannelPartnerRepository channelPartnerRepository;

	public HashMap<String, String> validateTransaction(TransactionAlertDTO transactionAlertDto) {
		HashMap<String, String> response = new HashMap<>();
		/*
		 * if(transactionAlertDto.getAccountNumber()==null){
		 * response.put("status", "invalid");
		 * response.put("code",TransactionAlertResponseCode.INVALID_ACCOUNT_NO.
		 * getKey()); response.put("message",
		 * TransactionAlertResponseCode.INVALID_ACCOUNT_NO.getValue()); return
		 * response; }else
		 * if(transactionAlertDto.getAccountNumber().trim().equals("")){
		 * response.put("status", "invalid");
		 * response.put("code",TransactionAlertResponseCode.INVALID_ACCOUNT_NO.
		 * getKey()); response.put("message",
		 * TransactionAlertResponseCode.INVALID_ACCOUNT_NO.getValue()); return
		 * response; }
		 */

		if (transactionAlertDto.getChannelPartner() == null) {
			response.put("status", "invalid");
			response.put("code", TransactionAlertResponseCode.INVALID_CHANNEL_PARTNER.getKey());
			response.put("message", TransactionAlertResponseCode.INVALID_CHANNEL_PARTNER.getValue());
			return response;
		} else if (transactionAlertDto.getChannelPartner().trim().equals("")) {
			response.put("status", "invalid");
			response.put("code", TransactionAlertResponseCode.INVALID_CHANNEL_PARTNER.getKey());
			response.put("message", TransactionAlertResponseCode.INVALID_CHANNEL_PARTNER.getValue());
			return response;
		} else {
			ChannelPartner channelPartner = channelPartnerRepository
					.findByUniqueCode(transactionAlertDto.getChannelPartner());
			if (channelPartner == null) {
				response.put("status", "invalid");
				response.put("code", TransactionAlertResponseCode.INVALID_CHANNEL_PARTNER.getKey());
				response.put("message", TransactionAlertResponseCode.INVALID_CHANNEL_PARTNER.getValue());
				return response;
			} else if (!channelPartner.getPassCode().equals(transactionAlertDto.getPassword())) {
				response.put("status", "invalid");
				response.put("code", TransactionAlertResponseCode.INVALID_PASSWORD.getKey());
				response.put("message", TransactionAlertResponseCode.INVALID_PASSWORD.getValue());
				return response;
			} else if (!channelPartner.getStatus().equals(Status.Active)) {
				response.put("status", "invalid");
				response.put("code", TransactionAlertResponseCode.INACTIVE_CHANNEL_PARTNER.getKey());
				response.put("message", TransactionAlertResponseCode.INACTIVE_CHANNEL_PARTNER.getValue());
				return response;
			}
		}

		if (transactionAlertDto.getMessage() == null) {
			response.put("status", "invalid");
			response.put("code", TransactionAlertResponseCode.INVALID_MESSAGE.getKey());
			response.put("message", TransactionAlertResponseCode.INVALID_MESSAGE.getValue());
			// response.put("message", "Invalid Message, Please provide valid
			// Message.");
			return response;
		} else if (transactionAlertDto.getMessage().trim().equals("")) {
			response.put("status", "invalid");
			response.put("code", TransactionAlertResponseCode.INVALID_MESSAGE.getKey());
			response.put("message", TransactionAlertResponseCode.INVALID_MESSAGE.getValue());
			return response;
		}

		if (transactionAlertDto.getMobileNumber() == null) {
			response.put("status", "invalid");
			response.put("code", TransactionAlertResponseCode.INVALID_MOBILENUMBER.getKey());
			response.put("message", TransactionAlertResponseCode.INVALID_MOBILENUMBER.getValue());
			return response;
		} else if (transactionAlertDto.getMobileNumber().trim().equals("")) {
			response.put("status", "invalid");
			response.put("code", TransactionAlertResponseCode.INVALID_MOBILENUMBER.getKey());
			response.put("message", TransactionAlertResponseCode.INVALID_MOBILENUMBER.getValue());
			return response;
		}

		if (transactionAlertDto.getSwiftCode() == null) {
			response.put("status", "invalid");
			response.put("code", TransactionAlertResponseCode.INVALID_SWIFTCODE.getKey());
			response.put("message", TransactionAlertResponseCode.INVALID_SWIFTCODE.getValue());
			return response;
		} else if (transactionAlertDto.getSwiftCode().trim().equals("")) {
			response.put("status", "invalid");
			response.put("code", TransactionAlertResponseCode.INVALID_SWIFTCODE.getKey());
			response.put("message", TransactionAlertResponseCode.INVALID_SWIFTCODE.getValue());
			return response;
		}
		/*
		 * SimpleDateFormat dateFormat = new
		 * SimpleDateFormat("MM/dd/yyyy HH:mm:ss"); try { Date date =
		 * dateFormat.parse(transactionAlertDto.getDateTime()); } catch
		 * (ParseException e) { response.put("status", "invalid");
		 * response.put("code",TransactionAlertResponseCode.INVALID_DATE_TIMER.
		 * getKey()); response.put("message",
		 * TransactionAlertResponseCode.INVALID_DATE_TIMER.getValue()); }
		 */
		/*
		 * if(transactionAlertDto.getTraceId()==null){ response.put("status",
		 * "invalid");
		 * response.put("code",TransactionAlertResponseCode.INVALID_TRACEID.
		 * getKey()); response.put("message",
		 * TransactionAlertResponseCode.INVALID_TRACEID.getValue()); return
		 * response; }
		 */

		Bank bank = bankRepository.findBankByCode(transactionAlertDto.getSwiftCode());

		if (bank == null) {
			response.put("status", "invalid");
			response.put("code", TransactionAlertResponseCode.BANK_NOT_FOUND.getKey());
			response.put("message", TransactionAlertResponseCode.BANK_NOT_FOUND.getValue());

			return response;
		}

		/*
		 * else{ Customer customer =
		 * customerRepository.findByMobileNumberAndBank(transactionAlertDto.
		 * getMobileNumber(), bank); if(customer==null){ response.put("status",
		 * "invalid");
		 * response.put("code",TransactionAlertResponseCode.CUSTOMER_NOT_FOUND.
		 * getKey()); response.put("message",
		 * TransactionAlertResponseCode.CUSTOMER_NOT_FOUND.getValue()); return
		 * response; }else if(!customer.isSmsSubscribed()){
		 * response.put("status", "invalid");
		 * response.put("code",TransactionAlertResponseCode.CUSTOMER_NOT_FOUND.
		 * getKey()); response.put("message",
		 * TransactionAlertResponseCode.CUSTOMER_NOT_FOUND.getValue()); } }
		 */

		/*
		 * SmsAlertTrace smsTrace = alertTraceRepository.findByBank(bank);
		 * if(smsTrace.getTraceId()!=null){
		 * if(smsTrace.getTraceId()==transactionAlertDto.getTraceId()){
		 * response.put("status", "invalid"); response.put("message",
		 * "Provided trace id is already saved in out system."); } }
		 */
		response.put("status", "valid");
		response.put("code", TransactionAlertResponseCode.SUCCESS.getKey());
		return response;
	}

}
