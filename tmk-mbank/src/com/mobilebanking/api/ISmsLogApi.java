/**
 * 
 */
package com.mobilebanking.api;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.mobilebanking.model.CustomerDTO;
import com.mobilebanking.model.PagablePage;
import com.mobilebanking.model.SmsLogDTO;
import com.mobilebanking.model.SmsType;

/**
 * @author bibek
 *
 */
public interface ISmsLogApi {
	
	SmsLogDTO save(SmsLogDTO smsLogDto) throws JSONException, IOException;
	
	List<SmsLogDTO> getAllSmsLog();
	
	List<SmsLogDTO> getSmsLogByBank(long bankId);
	
	List<SmsLogDTO> getSmsLogByBankBranch(long bankBranchId);
	
	List<SmsLogDTO> getSmsLogByCustomer(String uniqueId);
	
	SmsLogDTO getSmsLogById(long id);
	
	void sendCustomerRegistrationSms() throws Exception;

	List<SmsLogDTO> getIncomingSms();

	List<SmsLogDTO> getOutgoingSms();

	void creatShortCodeSmsLog(CustomerDTO customer, SmsType notification,String accountNumber, String message);

	List<SmsLogDTO> getOutgoingSmsByBank(long bankId);

	List<SmsLogDTO> getIncomingSmsByBank(long bankId);

	PagablePage getSmsLog(Integer currentPage,String fromDate, String toDate, String mobileNo, String smsType);

	List<SmsLogDTO> getOutgoingSmsByBranch(long branchId);

	List<SmsLogDTO> getIncomingSmsByBranch(long branchId);
	
	Long countSmsLog();

	Long countOutgoingSms();

	Long countIncomingSms();

	Long countSmsLogByDate(Date date);

	Long countIncomingSmsByDate(Date date);

	Long countOutgoingSmsByDate(Date date);

	Map<String, List<String>> getSMSLogReportData(String fromDate, String toDate);

} 
