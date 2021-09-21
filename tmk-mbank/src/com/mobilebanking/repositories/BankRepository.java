package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.Bank;
import com.mobilebanking.model.Status;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long>{
	
	@Query("select b from Bank b order by b.name")
	List<Bank> findBank();
	
	@Query("select b from Bank b where b.name=?1")
	Bank findByBank(String name);
	
	@Query("select b from Bank b where b.name like concat(?1, '%') order by b.name asc ")
	List<Bank> getBankByNameLike(String name);
	
	@Query("select b from Bank b where b.status=:status")
	List<Bank> getBankByStatus(@Param("status") Status status);
	
	@Query("select b from Bank b where b.swiftCode=:code")
	Bank findBankByCode(@Param("code") String bankCode);
	
	@Query("select b from Bank b where b.id=:assoId")
	List<Bank> findBankByAssociatedId(@Param("assoId") long associatedId);

	@Query("select b from Bank b where b.id not in(select bl.id from FcmServerSetting fcm join fcm.bankList bl where fcm.fcmServerKey=?1)")
	List<Bank> findBankNotInFcmSetting(String serverKey);

	@Query("select b from Bank b where b.id in(select bl.id from FcmServerSetting fcm join fcm.bankList bl where fcm.fcmServerKey=?1)")
	List<Bank> findBankInFcmSetting(String serverKey);

	@Query("select b from Bank b where b not in (select bl.bank from BankOAuthClient bl)")
	List<Bank> findBankWithoutOauthClient();

	@Query("select count(b) from Bank b where b.channelPartner.id=?1")
	Long countByChannelPartner(long channelPartnerId);

	@Query("select b from Bank b where b.channelPartner.id=?1")
	List<Bank> findByChannelPartner(Long channelPartnerId);

	Bank findByAccountNumber(String accountNumber);

	List<Bank> findByCbsStatus(Status status);
	
}
