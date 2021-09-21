package com.cas.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cas.entity.User;

public interface UserRepository extends CrudRepository<User, Long>,
		JpaSpecificationExecutor<User> {

	@Query("select u.id from User u where u.userName=?1")
	long findUserIdByUserName(String username);

	@Query("select u from User u where u.userName=?1")
	User findByUserName(String username);

	@Query("select u from User u where u.userName=?1 and u.ipAddress=?2")
	User findByUserIP(String userName, String ipAddress);

}
