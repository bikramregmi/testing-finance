package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.CardLessOTP;

@Repository
public interface CardlessOtpRepository extends JpaRepository<CardLessOTP, Long> {

	@Query("select c from CardLessOTP c where c.mobileNumber=?1 and c.amount=?2 and c.isWithDrawn=0 and c.isExpired=0")
	List<CardLessOTP> findByMobileNumberAndAmount(String mobileNumber, Double amt);

	List<CardLessOTP> findByIsWithDrawnAndIsExpired(boolean b, boolean c);

	CardLessOTP findByBit90(String string);

}
