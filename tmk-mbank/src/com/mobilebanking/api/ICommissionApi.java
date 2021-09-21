/**
 * 
 */
package com.mobilebanking.api;

import java.util.List;

import org.json.JSONException;

import com.mobilebanking.model.BankCommissionDTO;
import com.mobilebanking.model.CommissionAmountDTO;
import com.mobilebanking.model.CommissionDTO;
import com.mobilebanking.model.Status;
import com.mobilebanking.util.ClientException;

/**
 * @author bibek
 *
 */
public interface ICommissionApi {
		
	CommissionDTO editCommission(CommissionDTO commissionDto, List<CommissionAmountDTO> commissionAmounts) throws JSONException, ClientException, Exception;
	
	CommissionDTO getCommissionById(long id);
	
	List<CommissionAmountDTO> getCommissionAmountByCommission(long commissionId);
	
	List<CommissionDTO> getCommissionByStatus(Status status);
	
	List<CommissionDTO> getAllCommission();
	
	List<CommissionDTO> getCommissionByBank(long bankId);
	
	List<CommissionDTO> getCommissionByBankAndStatus(long bankId, Status status);
	
	CommissionDTO updateCommission(long commissionId);
	
	CommissionDTO saveCommission(CommissionDTO commissionDto, List<CommissionAmountDTO> commissionAmountDto) throws Exception;

	void saveCommissionAmount(CommissionAmountDTO commissionAmount);

	void saveBankCommission(BankCommissionDTO commissionAmount,Long bankId);

	BankCommissionDTO getBankCommissionByCommissionAmountIdAndBank(long id,long bankId);

}
