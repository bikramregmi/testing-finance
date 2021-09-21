package com.mobilebanking.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

	@Query("select n from Notice n where n.created>?1")
	List<Notice> findByCreatedDate(Date created);

	List<Notice> findByBank(Bank bank);

}
