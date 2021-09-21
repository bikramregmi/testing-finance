package com.mobilebanking.util;

/*import java.util.Date;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import com.remittance.entity.User;
import com.remittance.model.Status;
import com.remittance.model.UserDTO;
import com.remittance.model.UserType;*/


public class  EntityToDtoConverter <R,P> {
	
	/*public <R,P> R testDozer(P user,Class destinationClass){
		Mapper mapper = new DozerBeanMapper();
		R destObject = (R) mapper.map(user, destinationClass);
		return destObject;
	}
	
	public static void main(String[] args){
		User user = getUser();
		
		EntityToDtoConverter<UserDTO,User> converter = new EntityToDtoConverter<UserDTO,User>();
		UserDTO userDto = converter.testDozer(user, UserDTO.class);
		System.out.println(userDto);
	}
	
	public static User getUser(){
		User user = new User();
		
		user.setAccountNo("12345");
		user.setAssociatedId(12345);
		user.setAuthority("USER");
		user.setCreated(new Date());
		user.setPassword("test");
		user.setStatus(Status.Active);
		user.setUsername("testUser");
		user.setUserType(UserType.Admin);
		user.setVersion(1);
		
		return user;
	}*/

}
