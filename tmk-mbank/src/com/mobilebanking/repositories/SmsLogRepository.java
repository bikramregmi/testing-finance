/**
 * 
 */
package com.mobilebanking.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.SmsLog;
import com.mobilebanking.model.SmsStatus;
import com.mobilebanking.model.SmsType;
import com.mobilebanking.model.UserType;

/**
 * @author bibek
 *
 */
@Repository
public interface SmsLogRepository extends JpaRepository<SmsLog, Long> {
	
	@Query("select sl from SmsLog sl where sl.status=:status and sl.smsType=:sType and sl.smsForUser=:sUser")
	List<SmsLog> findSmsLogBySmsTypeAndStatusAndUserType(
			@Param("sType") SmsType smsType, @Param("status") SmsStatus smsStatus, 
			@Param("sUser") UserType userType);
	
	@Query("select sl from SmsLog sl where sl.bank.id=?1 order by sl.id desc")
	List<SmsLog> getSmsLogByBank(long bankId);

	@Query("select sl from SmsLog sl where sl.bank.id=?1 and sl.status=?2 order by sl.id desc")
	List<SmsLog> getDeliveredSmsLogByBank(long bankId, SmsStatus status);

	@Query("select sl from SmsLog sl where sl.isSmsIn=?1 order by sl.id desc")
	List<SmsLog> findByIsSmsIn(boolean isSmsIn);

	@Query("select sl from SmsLog sl where sl.status=?1 order by sl.id desc")
	List<SmsLog> getDeliveredSmsLog(SmsStatus status);

	@Query("select sl from SmsLog sl where sl.isSmsIn=?1 and sl.bank.id=?2 order by sl.id desc")
	List<SmsLog> findByIsSmsInAndBank(boolean isSmsIn, long bankId);

	@Query("select sl from SmsLog sl where sl.isSmsIn=?1 and sl.bankBranch.id=?2 order by sl.id desc")
	List<SmsLog> findByIsSmsInAndBranch(boolean isSmsIn, long branchId);

	@Query("select sl from SmsLog sl where sl.bankBranch.id=?1 and sl.status=?2 order by sl.id desc")
	List<SmsLog> getDeliveredSmsLogByBranch(long bankBranchId, SmsStatus status);
	
	@Query("select count(sl) from SmsLog sl")
	Long countAllSmsLog();
	
	@Query("select count(sl) from SmsLog sl where sl.bank.id=?1")
	Long countSmsLogByBank(long bankId);
	
	@Query("select count(sl) from SmsLog sl where sl.bankBranch.id=?1")
	Long countSmsLogByBranch(long branchId);

	@Query("select count(sl) from SmsLog sl where sl.isSmsIn=?1")
	Long countByIsSmsIn(boolean smsIn);
	
	@Query("select count(sl) from SmsLog sl where sl.isSmsIn=?1 and sl.bank.id=?2")
	Long countByIsSmsInAndBank(boolean smsIn, long bankId);
	
	@Query("select count(sl) from SmsLog sl where sl.isSmsIn=?1 and sl.bankBranch.id=?2")
	Long countByIsSmsInAndBranch(boolean smsIn, long branchId);
	
	@Query("select count(sl) from SmsLog sl where sl.created>=?1")
	Long countAllSmsLogByDate(Date date);

	@Query("select count(sl) from SmsLog sl where sl.bank.id=?1 and sl.created>=?2")
	Long countSmsLogByBankAndDate(long associatedId, Date date);

	@Query("select count(sl) from SmsLog sl where sl.bankBranch.id=?1 and sl.created>=?2")
	Long countSmsLogByBranchAndDate(long associatedId, Date date);

	@Query("select count(sl) from SmsLog sl where sl.isSmsIn=?1 and sl.created>=?2")
	Long countByIsSmsInByDate(boolean b, Date date);

	@Query("select count(sl) from SmsLog sl where sl.isSmsIn=?1 and sl.bank.id=?2 and sl.created>=?3")
	Long countByIsSmsInAndBankAndDate(boolean b, long associatedId, Date date);

	@Query("select count(sl) from SmsLog sl where sl.isSmsIn=?1 and sl.bankBranch.id=?2 and sl.created>=?3")
	Long countByIsSmsInAndBranchAndDate(boolean b, long associatedId, Date date);

}
