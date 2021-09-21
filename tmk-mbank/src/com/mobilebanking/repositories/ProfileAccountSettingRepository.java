package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.ProfileAccountSetting;
import com.mobilebanking.model.Status;

@Repository
public interface ProfileAccountSettingRepository extends JpaRepository<ProfileAccountSetting, Long> {

	List<ProfileAccountSetting> findByBank(Bank bank);

	ProfileAccountSetting findByBankAndStatus(Bank bank, Status active);

	
	
}
