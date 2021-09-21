package com.mobilebanking.repositories;

import com.mobilebanking.entity.MiniStatementRequest;

import com.mobilebanking.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MiniStatementRepository extends JpaRepository<MiniStatementRequest, Long> {

    @Query("select m from MiniStatementRequest m where m.customerBankAccount.branch.bank.id=?1")
    List<MiniStatementRequest> findMiniStatementRequestByBank(Long bankId);

    @Query("select m from MiniStatementRequest m where m.customerBankAccount.branch.id=?1")
    List<MiniStatementRequest> findMiniStatementRequestByBranch(Long branchId);
}
