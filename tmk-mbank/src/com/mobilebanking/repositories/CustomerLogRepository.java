/**
 * 
 */
package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.CustomerLog;

/**
 * @author bibek
 *
 */
@Repository
public interface CustomerLogRepository extends JpaRepository<CustomerLog, Long> {
	
	@Query("select cl from CustomerLog cl where cl.customer.uniqueId = :uuid order by cl.created desc")
	List<CustomerLog> findLogByCustomer(@Param("uuid") String uniqueId);

}
