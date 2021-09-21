/**
 * 
 */
package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.model.BankBranchDTO;
import com.mobilebanking.model.BankDTO;

/**
 * @author bibek
 *
 */
public interface IBankBranchApi {
	
	void saveBranch(BankBranchDTO branchDto);
	
	List<BankBranchDTO> listBankBranchByBank(long bankId);
	
	BankBranchDTO findBranchById(long id);
	
	List<BankBranchDTO> listAllBranch();
	
	List<BankBranchDTO> listBankBranchByNameLikeAndBank(String name, long bankId);

	void saveBulkBankBranch(BankBranchDTO bank);
	
	void sendSmsForBranchUser() throws Exception;
	
	void sendUserNameForBranchUser() throws Exception;

	void editBranch(BankBranchDTO branchDto);

	BankBranchDTO findBranchByBranchId(String branchId);

	BankBranchDTO findBranchByBankAndBranchCode(long bankId, String branchCode);

	List<BankBranchDTO> listBankByClientId(String clientId);

	BankDTO findBankOfBranch(long associatedId);
}
