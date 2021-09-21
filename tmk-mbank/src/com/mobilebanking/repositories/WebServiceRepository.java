package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mobilebanking.entity.WebService;

public interface WebServiceRepository extends CrudRepository<WebService, Long>, JpaSpecificationExecutor<WebService> {

	@Query("Select w from WebService w where w.api_user=?1")
	WebService findByName(String api_user);

	@Query("Select w from WebService w")
	List<WebService> getAllUser();

	@Query("Select w from WebService w where w.api_user=?1 and w.api_password=?2")
	WebService findByUserAndkey(String clientId, String clientKey);

	@Query("Select w from WebService w where w.access_key=?1")
	WebService findByAccessKey(String accessKey);

}
