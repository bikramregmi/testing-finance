package com.mobilebanking.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IMobileBankingCancelRequestApi;
import com.mobilebanking.converter.MobileBankingCancelRequestConverter;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.MobileBankingCancelRequest;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.MobileBankingCancelRequestDTO;
import com.mobilebanking.model.UserType;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.repositories.MobileBankingCancelRequestRepository;
import com.mobilebanking.repositories.UserRepository;

@Service
public class MobileBankingCancelRequestApi implements IMobileBankingCancelRequestApi {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private MobileBankingCancelRequestRepository mobileBankingCancelRequestRepository;

	@Autowired
	private MobileBankingCancelRequestConverter mobileBankingCancelRequestConverter;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void save(Long customerId) {
		MobileBankingCancelRequest mBankingCancelRequest = new MobileBankingCancelRequest();
		mBankingCancelRequest.setCustomer(customerRepository.findOne(customerId));
		mobileBankingCancelRequestRepository.save(mBankingCancelRequest);

	}

	@Override
	public List<MobileBankingCancelRequestDTO> findRequest(Long userId) {
		User user = userRepository.findOne(userId);
		List<MobileBankingCancelRequest> cancelRequestList = new ArrayList<>();
		if (user.getUserType() == UserType.Admin) {
			cancelRequestList = mobileBankingCancelRequestRepository.findAllCancelRequest();
		} else if (user.getUserType() == UserType.Bank) {
			cancelRequestList = mobileBankingCancelRequestRepository.findAllCancelRequestByBank(user.getAssociatedId());
		} else if (user.getUserType() == UserType.BankBranch) {
			cancelRequestList = mobileBankingCancelRequestRepository.findAllCancelRequestByBranch(user.getAssociatedId());
		}
		if(cancelRequestList != null)
			return mobileBankingCancelRequestConverter.convertToDtoList(cancelRequestList);
		return null;
	}

	@Override
	public MobileBankingCancelRequestDTO findByCustomer(long customerId) {
		Customer customer = customerRepository.findOne(customerId);
		MobileBankingCancelRequest request = mobileBankingCancelRequestRepository.findByCustomer(customer);
		if(request != null)
			return mobileBankingCancelRequestConverter.convertToDto(request);
		return null;
	}

}
