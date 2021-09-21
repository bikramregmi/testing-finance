/**
 * 
 */
package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.SmsMode;
import com.mobilebanking.model.SmsType;
import com.mobilebanking.model.Status;

/**
 * @author bibek
 *
 */
@Repository
public interface SmsModeRepository extends CrudRepository<SmsMode, Long>, JpaSpecificationExecutor<SmsMode> {
	
	@Query("select sm from SmsMode sm where sm.smsType=:sType and sm.bankBranch.id=:branchId and sm.status=:status")
	SmsMode findSmsModeBySmsTypeAndBankBranch(
			@Param("sType") SmsType smsType, @Param("branchId") long bankBranchId, @Param("status") Status status);
	
	@Query("select sm from SmsMode sm where sm.status=:status and sm.smsType=:sType order by sm.bank asc")
	List<SmsMode> findSmsModeBySmsType(@Param("sType") SmsType smsType, @Param("status") Status status);
	
	@Query("select sm from SmsMode sm where sm.bank.id=:bankId order by sm.smsType asc")
	List<SmsMode> findSmsModeByBank(@Param("bankId") long bankId);
	
	@Query("select sm from SmsMode sm order by sm.bank.id")
	List<SmsMode> findAllSmsMode();
	
	@Query("select sm from SmsMode sm where sm.bank.name=:bankName and sm.status=:status order by sm.smsType asc")
	List<SmsMode> findSmsModeByBank(@Param("bankName") String bankName, @Param("status")Status status);
	
	@Query("select sm from SmsMode sm where sm.bank.name=:bank and sm.status=:status and sm.smsType=:sType")
	SmsMode findSmsModeByBankAndSmsType(@Param("bank") String bankName, @Param("status") Status status, @Param("sType") SmsType smsType);
	
	@Query("select sm.message from SmsMode sm where sm.bank.id=:bank and sm.smsType=:sType and sm.status=:status")
	String findSmsModeMessageByBankAndSmsType(
			@Param("bank") long bankId, @Param("sType") SmsType smsType, @Param("status") Status status);

	@Query("select sm.message from SmsMode sm where  sm.smsType=:sType and sm.status=:status")
	String findSmsModeMessageByBankAndSmsType(
			 @Param("sType") SmsType smsType, @Param("status") Status status);

	@Query("select sm from SmsMode sm where sm.bank.name=:bankName order by sm.smsType asc")
	List<SmsMode> findSmsModeByBank(@Param("bankName") String bankName);
	
}
