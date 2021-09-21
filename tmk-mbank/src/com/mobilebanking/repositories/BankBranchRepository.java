/**
 * 
 */
package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankBranch;
import com.mobilebanking.model.Status;

/**
 * @author bibek
 *
 */
@Repository
public interface BankBranchRepository extends JpaRepository<BankBranch, Long> {
	
	@Query("select bb from BankBranch bb where bb.bank.id=:id order by bb.name asc")
	List<BankBranch> findBankBranchByBank(@Param("id") long bankId);
	
	@Query("select bb from BankBranch bb where bb.status=:status order by bb.bank.name asc")
	List<BankBranch> findAllActiveBranches(@Param("status") Status status);
	
	@Query("select bb from BankBranch bb where bb.bank.id=:bId and bb.name like concat(:name, '%') order by bb.name asc")
	List<BankBranch> findBankBranchByNameLikeAndBank(@Param("name") String branchName, @Param("bId") long bankId);
	
	@Query("select bb from BankBranch bb where bb.id=:assoId")
	List<BankBranch> findBankBranchesByAssociateId(@Param("assoId") long associatedId);

	BankBranch findByBranchId(String branchId);

	@Query("select bb from BankBranch bb where bb.bank.id=?1 and bb.branchCode=?2")
	BankBranch findByBankAndBranchCode(long bank, String branchCode);

	List<BankBranch> findByBank(Bank bank);
}
