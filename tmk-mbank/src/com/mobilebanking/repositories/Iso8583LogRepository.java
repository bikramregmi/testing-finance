/**
 * 
 */
package com.mobilebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.Iso8583Log;

/**
 * @author bibek
 *
 */
@Repository
public interface Iso8583LogRepository extends JpaRepository<Iso8583Log, Long> {
//
//	@Query("select i from Iso8583Log i JOIN FETCH i.transaction where i.transaction.id =?1 ORDER by i.id asc")
//	List<Iso8583Log> findByTransaction(Long id);
	
}
