/**
 * 
 */
package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.User;
import com.mobilebanking.entity.UserLog;

/**
 * @author bibek
 *
 */
@Repository
public interface UserLogRepository extends JpaRepository<UserLog, Long> {
	
	@Query("select ul from UserLog ul where ul.user.userName=:uName order by ul.created desc")
	List<UserLog> findLogByUser(@Param("uName") String userName);

	List<UserLog> findByUser(User user);

}
