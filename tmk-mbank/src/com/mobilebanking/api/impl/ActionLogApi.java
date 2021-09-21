/**
 * 
 */
package com.mobilebanking.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IActionLogApi;
import com.mobilebanking.entity.ActionLog;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.ActionLogDTO;
import com.mobilebanking.model.UserType;
import com.mobilebanking.repositories.ActionLogRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.repositories.UserRepository;

/**
 * @author bibek
 *
 */
@Service
public class ActionLogApi implements IActionLogApi {
	
	@Autowired
	private ActionLogRepository actionLogRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public ActionLogDTO saveActionLog(String remarks, long customerId, UserType userType, long userId) throws Exception {
		ActionLog actionLog = new ActionLog();
		if (userType == UserType.Customer) {
			Customer customer = customerRepository.findOne(customerId);
			actionLog.setForUser(customer.getUser());
		} else if (userType == UserType.Bank || userType == UserType.BankBranch) {
			actionLog.setForUser(userRepository.findOne(customerId));
		} 
		User user = userRepository.findOne(userId);
		actionLog.setRemarks(remarks);
		actionLog.setUpdatedBy(user);
		actionLog.setForUserType(userType);
		actionLog = actionLogRepository.save(actionLog);
		return null;
	}

}
