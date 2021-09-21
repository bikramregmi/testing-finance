package com.mobilebanking.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobilebanking.api.IUserApi;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.CustomerDTO;
import com.mobilebanking.model.UserDTO;
import com.mobilebanking.repositories.CustomerRepository;

@Component
public class MPinVaidation {

	@Autowired
	private IUserApi userApi;
	
	@Autowired 
	private CustomerRepository customerRepository;
	
	public Boolean mpinValidation(UserDTO userDto){
		if (userDto.getOldPassword() == null || userDto.getOldPassword().trim().equals("")) {
			return false;
		}
		boolean check = userApi.checkPassword(userDto);
		return check;
		
	}

	public Boolean mpinValidation(CustomerDTO customerDto,String mpin) {
		
		Customer customer =  customerRepository.findOne(customerDto.getId());
		
		User user = customer.getUser();
		 
		UserDTO userDTO = new UserDTO();
		userDTO.setUserName(user.getUserName());
		userDTO.setOldPassword(mpin);
boolean check = userApi.checkPassword(userDTO);
		
		return check;
	}

	public boolean mpinValidation(Long id, String mobilePin) {
		UserDTO userDto = userApi.getUserWithId(id);
		userDto.setOldPassword(mobilePin);
		return userApi.checkPassword(userDto);
	}
	
}
