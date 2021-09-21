/**
 * 
 */
package com.mobilebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.BankAccountSettings;
import com.mobilebanking.model.Status;

/**
 * @author bibek
 *
 */
@Repository
public interface BankAccountSettingsRepository extends JpaRepository<BankAccountSettings, Long> {
	
	@Query("select ba from BankAccountSettings ba where ba.bank.swiftCode=:code and status=:status")
	BankAccountSettings getBankAccountSettingsByBankCodeAndStatus(@Param("code") String bankCode, @Param("status") Status status);
	
	@Query("select ba from BankAccountSettings ba where ba.bank.swiftCode=:code")
	BankAccountSettings getBankAccountSettingsByBankCode(@Param("code") String bankCode);
}
