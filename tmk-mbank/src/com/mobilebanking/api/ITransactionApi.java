/**
 * 
 */
package com.mobilebanking.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankBranch;
import com.mobilebanking.entity.CustomerBankAccount;
import com.mobilebanking.entity.MerchantManager;
import com.mobilebanking.entity.Transaction;
import com.mobilebanking.model.PagablePage;
import com.mobilebanking.model.RevenueReport;
import com.mobilebanking.model.TransactionDTO;
import com.mobilebanking.model.TransactionStatus;
import com.mobilebanking.model.TransactionType;
import com.mobilebanking.util.ETransactionChannelForm;

/**
 * @author bibek
 *
 */
public interface ITransactionApi {
	
	TransactionDTO save(TransactionDTO transactionDto) throws Exception;
	
	List<TransactionDTO> getAllTransactions();
	
	List<TransactionDTO> getTransactionByCustomer(String uniqueId);
	
	TransactionDTO getTransactionByRefNumber(String referenceNumber);

	Transaction createTransaction(TransactionDTO dto , MerchantManager merchatnManager);

	TransactionDTO getTransactionByTransactionIdentifier(String identifier);

	TransactionDTO editTransaction(TransactionDTO dto);

	Transaction createTransactionbank(TransactionDTO dto, MerchantManager merchantMerchant, Bank bank);
	
	Transaction createTransactionBankBranch(TransactionDTO dto, MerchantManager manager, BankBranch bankBranch);
	
	Transaction createTransactionCustomer(TransactionDTO dto, MerchantManager manager, BankBranch bankBranch, CustomerBankAccount customerBankAccount);

	TransactionDTO editAmbiguousTransaction(TransactionDTO dto);

	List<TransactionDTO> filterTransaction(String fromDate, String toDate, String identifier, String status, String mobileNo, String bank);
	
	/*List<TransactionDTO> getByPageNumber(int pageNumber);*/

	List<TransactionDTO> getTransactionBetweenDate(Date fromDate, Date toDate);
	
	List<TransactionDTO> getTransactionByBank(Long bankId);

	Long countTransactionByServiceAndStatus(String uniqueIdentifier,TransactionStatus status);
	
	String fundTransferTransacion(String sourceAccount,String destinationAccount,Double amount,TransactionType transactionType,long customerId) throws Exception;

	HashMap<String, String> cardlessFundTransferTransacion(long customerBankAccountId,String destinationAccount,Double amount,TransactionType transactionType) throws Exception;
	
	String checkAmbiguousTransactionStatus(String transactionIdentifier) throws Exception;
	
	PagablePage getAllTransactions(Integer currentpage, String fromDate, String toDate, String identifier,
			TransactionStatus status, String mobileNo, String bank,String serviceIdentifier, boolean forReport, String targetNo);

	List<TransactionDTO> getTransactionByStatus(TransactionStatus transactionStatus);

	void manualUpdateAmbiguousTransaction(String transactionIdentifier, boolean status) throws Exception;

	String exportFinancialTransaction(String path);

	PagablePage getCommissionTransactions(Integer page, String fromDate, String toDate, String bank, String merchant, boolean forReport);

	List<TransactionDTO> getTransactionByBranch(long associatedId);
	
	void paypointFailureReversal(String transactionIdentifier);
	
	Long countTransactions();

	String checkProblematicTransaction(String transactionIdentifier);

	RevenueReport getRevenue(String fromDate, String toDate, String bank,String merchant);
//added by amrit
	Long countTransactionsByDate(Date date);
	//end added 

	Long countTransactionByServiceAndStatusAndDate(String uniqueIdentifier, TransactionStatus complete, Date date);

	HashMap<String,Object> getReversalReport(String fromDate, String toDate, String bank);

	List<TransactionDTO> getByServiceCategoryAndDate(Long serviceCategoryId, Date date);

	Long countByTransactionTypeAndDate(TransactionType transactionType, Date date);

	Long countTransactionUptoGivenMonthByBankAndDate(String bankId, Date monthYear);

	Long countTransactionThisNepaliMonthByBankAndDate(String bankId, Date monthYear, Date previousDate);

	Long countTotalTransactionByBank(String bankId);

	Double sumTotalTransactionAmountUptoGivenMonthByBankAndDate(String bankId, Date monthYear);

	Double sumTotalTransactionAmountThisNepaliMonthByBankAndDate(String bankId, Date monthYear, Date PreviousMonthYear);

	Double sumTotalTransactionAmountByBank(String bankId);

	Map<String, String> getTransactionDetailNepaliDateWise(Bank bank, Date thisMonth,Date previousMonth, ETransactionChannelForm eTransactionChannelForm);

	Map<String, String> getPrinDetails(String transactionId);

	HashMap<String, Object> getTransactionForCustomer(Long userId,String fromDate, String toDate,String pageNo);

	//Map<String,String> getNepaliPreviousMontheTransactionDetail(Bank bank, Date thisMonth,Date previousMonth,ETransactionChannelForm eTransactionChannelForm);

}
