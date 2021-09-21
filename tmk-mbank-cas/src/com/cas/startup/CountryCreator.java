package com.cas.startup;

import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.cas.entity.Country;
import com.cas.entity.User;
import com.cas.model.UserStatus;
import com.cas.repositories.CountryRepository;
import com.cas.repositories.UserRepository;
import com.cas.util.Authorities;

public class CountryCreator {

	private final CountryRepository countryRepository;

	public CountryCreator(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;

	}

	public void create() {

		Country c = countryRepository.findByCountryName("Nepal");
		if (c == null) {
			c = new Country();
			c.setCountryCode("NPL");
			c.setCountryName("Nepal");
			c.setCurrency("NPR");
			c.setPhoneCode("+977");
			countryRepository.save(c);
		}

	}

}
