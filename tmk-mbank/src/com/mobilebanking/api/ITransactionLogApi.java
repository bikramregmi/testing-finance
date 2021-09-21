/**
 * 
 */
package com.mobilebanking.api;

import com.mobilebanking.entity.Transaction;
import com.mobilebanking.model.UserType;

/**
 * @author bibek
 *
 */
public interface ITransactionLogApi {
	
	void createTransactionLog(Transaction transaction, String remarks, UserType transactionByUser, String transactionBy);
	

}
