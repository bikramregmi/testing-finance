/**
 * 
 */
package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.CommissionAmount;
import com.mobilebanking.model.Status;

/**
 * @author bibek
 *
 */
@Repository
public interface CommissionAmountRepository extends JpaRepository<CommissionAmount, Long> {
	
	@Query("select ca from CommissionAmount ca where ca.commission.id=:cId and ca.status=:status order by ca.id asc")
	List<CommissionAmount> findCommissionAmountsOfCommission(@Param("cId") long commissionId, @Param("status") Status status);
	
	@Query("select ca from CommissionAmount ca where ca.commission.id=:cId and ca.status=:status and ca.fromAmount<=:amount and ca.toAmount>=:amount")
	CommissionAmount findCommisionAmountByAmountRange(@Param("amount") double amount, @Param("cId") long commissionId, @Param("status") Status status);
	
	@Query("select ca from CommissionAmount ca join fetch ca.commission c where c.sameForAll=:same and ca.commission.id=:cId and ca.status=:status and c.status=:status")
	CommissionAmount findCommissionAmountForAllRangeByCommission(@Param("same") boolean sameForAll, @Param("cId") long commissionId, @Param("status") Status status);
}
