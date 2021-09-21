/**
 * 
 */
package com.mobilebanking.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.ICustomerLogApi;
import com.mobilebanking.converter.CustomerLogConverter;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.CustomerLog;
import com.mobilebanking.model.CustomerLogDto;
import com.mobilebanking.repositories.CustomerLogRepository;
import com.mobilebanking.repositories.CustomerRepository;

/**
 * @author bibek
 *
 */
@Service
public class CustomerLogApi implements ICustomerLogApi {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerLogRepository customerLogRepository;

	@Autowired
	private CustomerLogConverter customerLogConverter;

	@Override
	public CustomerLogDto save(String uniqueId, String remarks, String changedBy) {
		try {
			CustomerLog customerLog = new CustomerLog();
			Customer customer = customerRepository.findCustomerByUniqueId(uniqueId);
			customerLog.setCustomer(customer);
			customerLog.setRemarks(remarks);
			customerLog.setChangedBy(changedBy);
			customerLogRepository.save(customerLog);
			return customerLogConverter.convertToDto(customerLog);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CustomerLogDto> getCustomerLogByCustomer(String uniqueId) {
		List<CustomerLog> customerLogList = customerLogRepository.findLogByCustomer(uniqueId);
		if (customerLogList != null) {
			return customerLogConverter.convertToDtoList(customerLogList);
		}
		return null;
	}

}
