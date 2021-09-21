package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.CardlessBank;

@Repository
public interface CardlessBankRepository extends JpaRepository<CardlessBank, Long>{

	@Query("select c from CardlessBank c where c not in (select ca.cardlessBank from CardlessBankAccount ca where ca.bank.swiftCode=?1)")
	List<CardlessBank> findCardlessBankNotInBank(String bankCode);

}
