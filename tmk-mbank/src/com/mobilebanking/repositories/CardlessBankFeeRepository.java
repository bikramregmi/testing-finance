package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.CardlessBankFee;
import com.mobilebanking.model.Status;

@Repository
public interface CardlessBankFeeRepository extends JpaRepository<CardlessBankFee, Long>{

	@Query("select c from CardlessBankFee c where c.cardlessBank.id=?1 and c.status=?2")
	List<CardlessBankFee> findFeeByCardlessBankIdAndStatus(Long cardlessBankId, Status status);
	
	@Query("select c.fee from CardlessBankFee c where c.cardlessBank.id=?2 and c.fromAmount<=?1 and c.toAmount>=?1")
	Double findByAmountRange(Double amount, Long cardlessBankId);

}
