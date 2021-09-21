package com.cas.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.cas.api.ISessionLogApi;
import com.cas.api.IUserApi;
import com.cas.entity.User;
import com.cas.model.UserDTO;
import com.cas.repositories.UserRepository;

public class UserApi implements IUserApi, MessageSourceAware {

	private final UserRepository userRepository;
	private final ISessionLogApi sessionLogApi;
	private final PasswordEncoder passwordEncoder;
	private MessageSource messageSource;
	private final Logger logger = LoggerFactory.getLogger(UserApi.class);
	private long userId;

	public UserApi(UserRepository userRepository,
			PasswordEncoder passwordEncoder, ISessionLogApi sessionLogApi) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.sessionLogApi = sessionLogApi;

	}

	@Override
	public void setMessageSource(MessageSource arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public User searchByUserIP(UserDTO dto){
		return userRepository.findByUserIP(dto.getUserName(),dto.getIpAddress());
	}
	
	@Override
	public User searchByUserName(String name){
		return userRepository.findByUserName(name);
	}

	
	
}
