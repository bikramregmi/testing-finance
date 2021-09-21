package com.cas.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cas.entity.Country;
import com.cas.entity.User;

public interface CountryRepository extends CrudRepository<Country, Long>, JpaSpecificationExecutor<Country> {

	@Query("select c from Country c where c.countryName=?1")
	Country findByCountryName(String string);

	@Query("select c from Country c where c.countryCode=?1")
	Country findByCountryCode(String country);


}
