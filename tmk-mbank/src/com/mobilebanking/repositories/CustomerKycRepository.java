/**
 * 
 */
package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.CustomerKyc;
import com.mobilebanking.model.Status;

/**
 * @author bibek
 *
 */
@Repository
public interface CustomerKycRepository extends JpaRepository<CustomerKyc,Long>{
	@Query("select k from CustomerKyc k where k.customer.uniqueId=:uuid AND k.status=:status order by k.documentId.documentType asc")
	List<CustomerKyc> findCustomerDocumentsByCustomer(@Param("uuid") String uniqueId, @Param("status") Status status);
}
