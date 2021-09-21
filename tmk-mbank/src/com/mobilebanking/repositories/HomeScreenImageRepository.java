package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.HomeScreenImage;

@Repository
public interface HomeScreenImageRepository extends JpaRepository<HomeScreenImage, Long>{

	@Query("select h from HomeScreenImage h where h.bank.id=?1 and h.status=0 order by placement")
	List<HomeScreenImage> findByBank(long bankId);

	@Query("select count(h) from HomeScreenImage h where h.bank=?1 and h.status=0")
	Long countByBank(Bank bank);


}
