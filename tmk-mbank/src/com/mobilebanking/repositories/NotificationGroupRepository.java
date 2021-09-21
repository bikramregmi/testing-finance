package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.NotificationGroup;

@Repository
public interface NotificationGroupRepository extends JpaRepository<NotificationGroup, Long> {

	@Query("select ng from NotificationGroup ng where ng.name=?1 and ng.bankCode=?2)")
	NotificationGroup findByName(String notificationGroupName,String swiftCode);

	@Query("select ng from NotificationGroup ng where ng.bankCode=?1)")
	List<NotificationGroup> findByBank(String swiftCode);
	
	@Query("select ng from NotificationGroup ng join ng.customerList cl where ng.bankCode=?2 and cl.id in (select c.id from Customer c where  c.id=?1)")
	List<NotificationGroup> findByCustomerAndBankCode(Long id, String swiftCode);

}
