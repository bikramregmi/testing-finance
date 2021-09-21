package com.mobilebanking.api;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.springframework.web.multipart.MultipartFile;

import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.CustomerBankAccount;
import com.mobilebanking.model.AccountMode;
import com.mobilebanking.model.BankServiceCharge;
import com.mobilebanking.model.BeneficiaryDTO;
import com.mobilebanking.model.CustomerBankAccountDTO;
import com.mobilebanking.model.CustomerDTO;
import com.mobilebanking.model.CustomerStatus;
import com.mobilebanking.model.MiniStatementRespose;
import com.mobilebanking.model.PagablePage;
import com.mobilebanking.model.UserType;
import com.mobilebanking.util.ClientException;

public interface ICustomerApi {

	public boolean saveCustomer(CustomerDTO customerDTO);

	public CustomerDTO getCustomerById(long customerId) throws ClientException;

	public List<CustomerDTO> findCustomerOfParticularBank(long bankId);

	public List<CustomerDTO> findCustomerOfParticularBankBranch(long bankBranchId);

	public CustomerDTO updateCustomer(long customerId, CustomerStatus status) throws IOException, JSONException;

	public CustomerDTO findCustomerByUniqueId(String uniqueId);

	public CustomerDTO editCustomer(CustomerDTO customerDto) throws IOException, JSONException;

	public void sendSmsToRegisteredCustomer() throws Exception;

	public void sendSmsToMultipleAccountsForCustomer() throws Exception;

	public boolean getCustomerByMobileNo(String from, String CLIENTiD);

	public CustomerDTO getCustomerBalanceByMobileNumber(String from, String cllientId);

	public Double getCustomerBankBalance(String uniqueId, String accountNumber) throws Exception;

	public boolean resendSmsForVerification(String userName) throws Exception;

	public BeneficiaryDTO saveBenificiary(BeneficiaryDTO customerDto, CustomerDTO customerDTO);

	public List<BeneficiaryDTO> listBenificiary(Long id, String clientId);

	public boolean deleteBenificiary(Long id);

	public String resendToken(String userName);

	public MiniStatementRespose getMiniStatementOfUser(String uniqueId, String accountNumber) throws Exception;

	public boolean verifyToken(String userName, String token);

	public HashMap<String, String> fundTransfer(String sourceAccountNumber, String destinationAccountNumber, Double amount, Bank bank);

	public boolean resetPin(String uniqueId) throws UnknownHostException, InvocationTargetException;

	public void changeAccountNumber(String uniqueId, String currentAccountNumber, String newAccountNumber,
			AccountMode accountMode, String comment);

	public boolean saveBulkCustomer(CustomerDTO customer, UserType userType);

	public void addCustomerBankAccount(String uniqueId, String accountNumber, long bankBranchId,
			AccountMode accountMode, String comment);

	public void deleteCustomerBankAccount(Long customerBankAccountId);

	public BeneficiaryDTO editBenificiary(BeneficiaryDTO customerDto, CustomerDTO customerDTO);

	public List<CustomerDTO> getCustomerWithoutTransaction(Long associatedId);

	public Long countCustomerWithInLastWeek(long bankId);

	public Long countCustomerWithInLastMonth(long bankId);

	public HashMap<String, String> fundTransfer(String customerAccountNumber, String parkingAccountNumber, double amount, Bank bank,
			String remarksOne, String remarksTwo,String desc1);

	public PagablePage getCustomer(Integer page, String name, String mobileNo, String city, String status,
			String accountNo, String branchId, String bankCode,Boolean firstApproval, String fromDate, String toDate,boolean expired);

	public String uploadBulkCustomer(MultipartFile file, String basePath);

	public void changeMobileNumber(String uniqueId, String currentMobileNumber, String newMobileNumber, String comment);

	public boolean getCustomerByMobileNo(String newMobileNumber, Long id, UserType currentUserType);

	public Long countCustomer(long currentUserId);

	public Long countCustomerWithoutTransaction(long currentUserId);

	public void createSmsLog(CustomerBankAccountDTO customerBankAccount, String smsType, String message);

	public List<CustomerDTO> findCustomerList(List<Long> customerIdList);

	public String getUsernameByCustomer(Long id);

	public void createSmsLog(CustomerDTO customer, String string, String message);

	public HashMap<String, String> accountTransfer(String sourceAccountNumber, String destinationAccountNumber,
			Double amount, Bank bank);

	public boolean changeStatus(String uniqueId, CustomerStatus status, String remarks, String changedBy);

	List<CustomerDTO> getUserByMobileNumberConCat(String data, String bankCode);

	public Boolean resetToken(String uniqueId);
	
	//added by amrit
	public Long countCustomerByDate(Date currentDate);

	public Long countCustomerWithoutTransactionByDate(Date date);

	public List<CustomerDTO> getCustomerWithoutTransactionByDate(long associatedId, Date date);
	//end of added

	public HashMap<String, Object> getCustomerRegestrationReport(Long bank,Long branch,String fromDate,String toDate,long currentUserId);

	Long countCustomerByStatus(long currentUserId, CustomerStatus status);

	public Long countCustomerThisNepaliMonthByBankAndDate(String bankId, Date thisMonthYear, Date lastMonthYear);

	public Long countUptoGivenMonthCustomerByBankAndDate(String bankId, Date previousMonthYear);

	public Long countCustomerByBank(String bankId);

	public List<CustomerDTO> getCustomer(String fromDate, String toDate, String branch, String bank);

	boolean deleteOauthToken(String username);

	HashMap<String, String> deductServiceCharge(CustomerBankAccount customerBankAccount, long profileId,
			BankServiceCharge bankServiceChargeType);

	public HashMap<String, String> renewCustomer(String uniqueId, String createdBy);

	public List<CustomerDTO> getRenewedCustomer(int days,Long currentUserId);

	public CustomerDTO getCustomerByBankAndMobileNumber(Long bankId, String mobileNumber);
	
	public List<CustomerDTO> getAllCustomer();
}