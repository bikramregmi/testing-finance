package com.cas.startup;

import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.cas.entity.User;
import com.cas.model.UserStatus;
import com.cas.repositories.UserRepository;
import com.cas.util.Authorities;

public class FirstAdminCreator {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public FirstAdminCreator(UserRepository userRepository, PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = encoder;
	}

	public void create() {

		User u = userRepository.findByUserName("admin");
		if (u == null) {
			u = new User();
			u.setUserName("admin");
			u.setAuthority(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED);
			u.setPassword(passwordEncoder.encode("123456"));
			u.setCreated(new Date());
			u.setStatus(UserStatus.ACTIVE);
			userRepository.save(u);
		}

	}

}
