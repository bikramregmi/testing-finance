package com.mobilebanking.validation;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobilebanking.api.IAccountApi;
import com.mobilebanking.api.IMerchantServiceApi;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankBranch;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.MerchantServiceDTO;
import com.mobilebanking.model.UserType;
import com.mobilebanking.repositories.BankBranchRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.repositories.UserRepository;
import com.mobilebanking.util.StringConstants;

@Component
public class MerchantPaymentValidation {

	@Autowired
	private IAccountApi accountApi;

	@Autowired
	private IMerchantServiceApi merchantServiceApi;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private BankBranchRepository bankBranchRepository;

	@Autowired
	private CustomerRepository customerRepository;

	public HashMap<String, String> merchantPaymentValidation(String transactionId, String serviceId, Double amount,
			Long aid) {

		HashMap<String, String> hashResponse = new HashMap<>();

		String response;

		response = checkServiceValidation(serviceId);
		if (response == null) {
			response = checkAmountValidation(amount, aid, serviceId);
			if (response == null) {
				if(!serviceId.equals(StringConstants.SMART_CELL))
					if(!serviceId.equals(StringConstants.BROADLINK))
			if(!serviceId.equals(StringConstants.BROADTEL))
				if(!serviceId.equals(StringConstants.DISHHOME_PIN))
					if(!serviceId.equals(StringConstants.NET_TV))
				response = checkTransactionIdValidation(transactionId, serviceId);
				if (response == null) {
					hashResponse.put("message", "valid");
					return hashResponse;
				} else {
					hashResponse.put("message", response);
					return hashResponse;
				}
			} else {
				hashResponse.put("message", response);
				return hashResponse;
			}
		} else {
			hashResponse.put("message", response);
			return hashResponse;
		}

	}

	private String checkTransactionIdValidation(String transactionId, String serviceId) {
		if (transactionId == null) {
			return "Invalid Transaction Id";
		} else if (transactionId.trim().equals("")) {
			return "Invalid Transaction Id";
		} else {
			MerchantServiceDTO service = merchantServiceApi.findServiceByUniqueIdentifier(serviceId);
			if (service.isFixedlabelSize()) {
				if (transactionId.length() != Long.parseLong(service.getLabelSize())) {
					return "Transaction Id Must Be " + service.getLabelSize() + " Digit Long";
				}
			} else if (!(service.isFixedlabelSize())) {
				if (transactionId.length() < Long.parseLong(service.getLabelMinLength())) {
					return "Transaction Id Must Be At Least " + service.getLabelMinLength() + " Digit Long";
				} else if (transactionId.length() > Long.parseLong(service.getLabelMaxLength())) {
					return "Transaction Id Must Not Be Longer Than " + service.getLabelMaxLength() + " Digit";
				}
			} else {
				String[] prefixArray = service.getLabelPrefix().split(",");
				boolean valid = false;
				for (String prefix : prefixArray) {
					if (transactionId.startsWith(prefix)) {
						valid = true;
					}
				}
				if (!valid) {
					return "Invalid Prefix";
				}
			}
		}
		return null;
	}

	private String checkAmountValidation(Double amount, long aid, String serviceId) {
		MerchantServiceDTO service = merchantServiceApi.findServiceByUniqueIdentifier(serviceId);
		User user = userRepository.findOne(aid);
		Bank bank = null;
		if (service.isPriceInput()) {
			String[] priceRangeArray = service.getPriceRange().split(",");
			boolean valid = false;
			for (String priceRange : priceRangeArray) {
				if (amount == Double.parseDouble(priceRange)) {
					valid = true;
				}
			}
			if (!valid) {
				return "Invalid Amount";
			}

		} else if (!(service.isPriceInput())) {
			if (amount < service.getMinValue()) {
				return "Minimum Amount Is " + service.getMinValue();
			} else if (amount > service.getMaxValue()) {
				return "Maximum Amount Is " + service.getMaxValue();
			}
		} else {
			if (user.getUserType() == UserType.Bank) {

				bank = bankRepository.findOne(user.getAssociatedId());

			} else if (user.getUserType() == UserType.BankBranch) {
				BankBranch bankBranch = bankBranchRepository.findOne(user.getAssociatedId());
				bank = bankBranch.getBank();
			} else if (user.getUserType() == UserType.Customer) {
				Customer customer = customerRepository.findOne(user.getAssociatedId());

			}

			Double spendableAmount = accountApi.getSpendableAmount(bank);
			if (spendableAmount < amount) {
				return "Balance Too Low For Transaction, Please Load Balance";
			}
		}
		return null;
	}

	private String checkServiceValidation(String serviceId) {
		MerchantServiceDTO service = merchantServiceApi.findServiceByUniqueIdentifier(serviceId);
		if (service == null) {
			return "Invalid Service";
		}
		return null;
	}

}
