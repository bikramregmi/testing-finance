package com.cas.api;

import com.cas.entity.User;
import com.cas.model.UserDTO;


public interface IUserApi {

	User searchByUserIP(UserDTO dto);

	User searchByUserName(String name);


}
