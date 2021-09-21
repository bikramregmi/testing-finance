package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.FcmServerSetting;

@Repository
public interface FcmServerSettingRepository extends JpaRepository<FcmServerSetting, Long> {
	
	@Query("select fcm from FcmServerSetting fcm join fcm.bankList bl where bl.id=?1")
	List<FcmServerSetting> findByBank(Long bankid);
	
	@Query("select fcm from FcmServerSetting fcm join fcm.bankList bl where fcm.fcmServerKey=?2 and bl.id=?1")
	FcmServerSetting findByBankAndFcmServerKey(Long bankId, String serverKey);

	FcmServerSetting findByFcmServerKey(String serverKey);

}
