package com.mobilebanking.api.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.ISmsLogApi;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankBranch;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.CustomerBankAccount;
import com.mobilebanking.entity.SmsLog;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.CustomerDTO;
import com.mobilebanking.model.PagablePage;
import com.mobilebanking.model.SmsLogDTO;
import com.mobilebanking.model.SmsStatus;
import com.mobilebanking.model.SmsType;
import com.mobilebanking.model.UserType;
import com.mobilebanking.repositories.BankBranchRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.CustomerBankAccountRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.repositories.SmsLogRepository;
import com.mobilebanking.repositories.UserRepository;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.ConvertUtil;
import com.mobilebanking.util.DateTypes;
import com.mobilebanking.util.DateUtil;
import com.mobilebanking.util.PageInfo;

@Service
public class SmsLogApi implements ISmsLogApi {
	
	@Autowired
	private SmsLogRepository smsLogRepository;
	
	@Autowired
	private BankBranchRepository bankBranchRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ConvertUtil convertUtil;

	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private CustomerBankAccountRepository customerBankAccountRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public SmsLogDTO save(SmsLogDTO smsLogDto) throws JSONException, IOException {
		
		SmsLog smsLog = new SmsLog();
		BankBranch bankBranch = bankBranchRepository.findOne(Long.parseLong(smsLogDto.getBankBranch()));
		
		smsLog.setSmsType(smsLog.getSmsType());
		smsLog.setBank(bankBranch.getBank());
		smsLog.setBankBranch(bankBranch);
		smsLog.setMessage(smsLog.getMessage());
		smsLog.setSmsFor(smsLog.getSmsFor());
		smsLog.setSmsForUser(smsLog.getSmsForUser());
		smsLog.setSmsFrom(smsLog.getSmsFrom());
		smsLog.setSmsFromUser(smsLog.getSmsFromUser());
		smsLog.setStatus(SmsStatus.INITIATED);
		smsLogRepository.save(smsLog);
		return null;
	}

	@Override
	public List<SmsLogDTO> getAllSmsLog() {
		List<SmsLog> smslogList = smsLogRepository.getDeliveredSmsLog(SmsStatus.DELIVERED);
		return convertUtil.converSmsLogList(smslogList);
	}

	@Override
	public List<SmsLogDTO> getSmsLogByBank(long bankId) {
		List<SmsLog> smslogList = smsLogRepository.getDeliveredSmsLogByBank(bankId, SmsStatus.DELIVERED);
		return convertUtil.converSmsLogList(smslogList);
	}

	@Override
	public List<SmsLogDTO> getSmsLogByBankBranch(long bankBranchId) {
		List<SmsLog> smslogList = smsLogRepository.getDeliveredSmsLogByBranch(bankBranchId, SmsStatus.DELIVERED);
		return convertUtil.converSmsLogList(smslogList);
	}

	@Override
	public List<SmsLogDTO> getSmsLogByCustomer(String uniqueId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.mobilebanking.api.ISmsLogApi#getSmsLogById(long)
	 */
	@Override
	public SmsLogDTO getSmsLogById(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@Scheduled(fixedRate=5000)
	public void sendCustomerRegistrationSms() throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
	//	DateUtil.getDateBackByGivenMinutes(-5);
		//List<Customer> customerList = 
	}


	@Override
	public List<SmsLogDTO> getIncomingSms() {
		List<SmsLog> smslogList = smsLogRepository.findByIsSmsIn(true);
		if(smslogList!=null){
			return convertUtil.converSmsLogList(smslogList);
		}
		return null;
	}

	@Override
	public List<SmsLogDTO> getOutgoingSms() {
		List<SmsLog> smslogList = smsLogRepository.findByIsSmsIn(false);
		if(smslogList!=null){
			return convertUtil.converSmsLogList(smslogList);
		}
		return null;
	}

	@Override
	public void creatShortCodeSmsLog(CustomerDTO customerDTO, SmsType smsType, String accountNumber,String message) {
		SmsLog smsLog = new SmsLog();
		Customer customer = customerRepository.findOne(customerDTO.getId());
		CustomerBankAccount customerBankAccount = customerBankAccountRepository.findByAccountNumberAndBankId(accountNumber, customer.getBank().getId());
		User user = customer.getUser();
		long bankId = 0;
		long bankBranchId = 0;
		boolean createdByBank = false;
			smsLog.setBank(customerBankAccount.getBranch().getBank());
			smsLog.setSmsForUser(UserType.Customer);
			smsLog.setSmsFor(customer.getUniqueId());
			smsLog.setStatus(SmsStatus.DELIVERED);
			smsLog.setSmsFromUser(createdByBank?UserType.Bank:UserType.BankBranch);
			smsLog.setSmsFrom(createdByBank?bankId:bankBranchId);
			smsLog.setSmsType(smsType);
			smsLog.setMessage(message);
			smsLog.setSmsIn(true);
			smsLog.setForMobileNo(customer.getMobileNumber());
			smsLogRepository.save(smsLog);
			
	}

	@Override
	public List<SmsLogDTO> getOutgoingSmsByBank(long bankId) {
		List<SmsLog> smslogList = smsLogRepository.findByIsSmsInAndBank(false, bankId);
		if(smslogList!=null){
			return convertUtil.converSmsLogList(smslogList);
		}
		return null;
	}

	@Override
	public List<SmsLogDTO> getIncomingSmsByBank(long bankId) {
		List<SmsLog> smslogList = smsLogRepository.findByIsSmsInAndBank(true, bankId);
		if(smslogList!=null){
			return convertUtil.converSmsLogList(smslogList);
		}
		return null;
	}

	@Override
	public PagablePage getSmsLog(Integer currentpage, String fromDate, String toDate, String mobileNo, String smsType) {
		PagablePage pageble = new PagablePage();

		if (currentpage == null || currentpage == 0) {
			currentpage = 1;
		}

		int starting = ((currentpage * (int) PageInfo.pageList) - (int) PageInfo.pageList);
		
		entityManager=entityManager.getEntityManagerFactory().createEntityManager();
		
	//	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(SmsLog.class);
		User currentUser = AuthenticationUtil.getCurrentUser();
		
		if(currentUser.getUserType()==UserType.Bank){
			Bank bank = bankRepository.findOne(currentUser.getAssociatedId());
			criteria.add(Restrictions.eq("bank", bank));
		}else if(currentUser.getUserType()==UserType.BankBranch){
			BankBranch branch = bankBranchRepository.findOne(currentUser.getAssociatedId());
			criteria.add(Restrictions.eq("bankBranch", branch));
		}
		
		try {
			if (fromDate != null && !(fromDate.trim().equals(""))) {
				criteria.add(Restrictions.ge("created", DateUtil.getDate(fromDate, DateTypes.FROM_DATE)));
			}

			if (toDate != null && !(toDate.trim().equals(""))) {
				criteria.add(Restrictions.lt("created", DateUtil.getDate(toDate,DateTypes.TO_DATE)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (smsType != null && !(smsType.trim().equals(""))) {
			criteria.add(Restrictions.eq("isSmsIn", Boolean.valueOf(smsType)));
		}
		
		if (mobileNo != null && !(mobileNo.trim().equals(""))) {
			criteria.add(Restrictions.like("forMobileNo", mobileNo+"%"));
		}
		
		criteria.addOrder(Order.desc("id"));
		

		int totalpage = (int) Math.ceil(criteria.list().size() / PageInfo.pageList);
		List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(currentpage, totalpage, PageInfo.numberOfPage);
		criteria.setFirstResult(starting);
		criteria.setMaxResults((int) PageInfo.pageList);
		List<SmsLog> smsLogList = criteria.list();

		pageble.setCurrentPage(currentpage);
		pageble.setObject(convertUtil.converSmsLogList(smsLogList));
		pageble.setPageList(pagesnumbers);
		pageble.setLastpage(totalpage);

		return pageble;
	}

	@Override
	public List<SmsLogDTO> getOutgoingSmsByBranch(long branchId) {
		List<SmsLog> smslogList = smsLogRepository.findByIsSmsInAndBranch(false, branchId);
		if(smslogList!=null){
			return convertUtil.converSmsLogList(smslogList);
		}
		return null;
	}

	@Override
	public List<SmsLogDTO> getIncomingSmsByBranch(long branchId) {
		List<SmsLog> smslogList = smsLogRepository.findByIsSmsInAndBranch(true, branchId);
		if(smslogList!=null){
			return convertUtil.converSmsLogList(smslogList);
		}
		return null;
	}
	
	@Override
	public Long countSmsLog() {
		User currentUser = userRepository.findOne(AuthenticationUtil.getCurrentUser().getId());
		if(currentUser.getUserType().equals(UserType.Admin)){
			return smsLogRepository.countAllSmsLog();
		}else if(currentUser.getUserType().equals(UserType.Bank)){
			return smsLogRepository.countSmsLogByBank(currentUser.getAssociatedId());
		}else if(currentUser.getUserType().equals(UserType.BankBranch)){
			return smsLogRepository.countSmsLogByBranch(currentUser.getAssociatedId());
		}
		return 0L;
	}

	@Override
	public Long countOutgoingSms() {
		User currentUser = userRepository.findOne(AuthenticationUtil.getCurrentUser().getId());
		if(currentUser.getUserType().equals(UserType.Admin)){
			return smsLogRepository.countByIsSmsIn(false);
		}else if(currentUser.getUserType().equals(UserType.Bank)){
			return smsLogRepository.countByIsSmsInAndBank(false,currentUser.getAssociatedId());
		}else if(currentUser.getUserType().equals(UserType.BankBranch)){
			return smsLogRepository.countByIsSmsInAndBranch(false,currentUser.getAssociatedId());
		}
		return 0L;
	}

	@Override
	public Long countIncomingSms() {
		User currentUser = userRepository.findOne(AuthenticationUtil.getCurrentUser().getId());
		if(currentUser.getUserType().equals(UserType.Admin)){
			return smsLogRepository.countByIsSmsIn(true);
		}else if(currentUser.getUserType().equals(UserType.Bank)){
			return smsLogRepository.countByIsSmsInAndBank(true,currentUser.getAssociatedId());
		}else if(currentUser.getUserType().equals(UserType.BankBranch)){
			return smsLogRepository.countByIsSmsInAndBranch(true,currentUser.getAssociatedId());
		}
		return 0L;
	}
	//added by amrit
	@Override
	public Long countSmsLogByDate(Date date) {
		User currentUser = userRepository.findOne(AuthenticationUtil.getCurrentUser().getId());
		if(currentUser.getUserType().equals(UserType.Admin)){
			return smsLogRepository.countAllSmsLogByDate(date);
		}else if(currentUser.getUserType().equals(UserType.Bank)){
			return smsLogRepository.countSmsLogByBankAndDate(currentUser.getAssociatedId(),date);
		}else if(currentUser.getUserType().equals(UserType.BankBranch)){
			return smsLogRepository.countSmsLogByBranchAndDate(currentUser.getAssociatedId(),date);
		}
		return 0L;
	}

	@Override
	public Long countIncomingSmsByDate(Date date) {
		User currentUser = userRepository.findOne(AuthenticationUtil.getCurrentUser().getId());
		if(currentUser.getUserType().equals(UserType.Admin)){
			return smsLogRepository.countByIsSmsInByDate(true,date);
		}else if(currentUser.getUserType().equals(UserType.Bank)){
			return smsLogRepository.countByIsSmsInAndBankAndDate(true,currentUser.getAssociatedId(),date);
		}else if(currentUser.getUserType().equals(UserType.BankBranch)){
			return smsLogRepository.countByIsSmsInAndBranchAndDate(true,currentUser.getAssociatedId(),date);
		}
		return 0L;
	}
	

	@Override
	public Long countOutgoingSmsByDate(Date date) {
		User currentUser = userRepository.findOne(AuthenticationUtil.getCurrentUser().getId());
		if(currentUser.getUserType().equals(UserType.Admin)){
			return smsLogRepository.countByIsSmsInByDate(false,date);
		}else if(currentUser.getUserType().equals(UserType.Bank)){
			return smsLogRepository.countByIsSmsInAndBankAndDate(false,currentUser.getAssociatedId(),date);
		}else if(currentUser.getUserType().equals(UserType.BankBranch)){
			return smsLogRepository.countByIsSmsInAndBranchAndDate(false,currentUser.getAssociatedId(),date);
		}
		return 0L;
	}


	@Override
	public Map<String, List<String>> getSMSLogReportData(String fromDate, String toDate) {
		// TODO Auto-generated method stub
		Map<String, List<String>> map=new HashMap<String, List<String>>();
		
		entityManager= entityManager.getEntityManagerFactory().createEntityManager();
		Session session=entityManager.unwrap(Session.class);
		StringBuilder sql=new StringBuilder("SELECT sl , SUM(CASE isSmsIn WHEN 1 THEN 1 ELSE 0 END) AS incommingSms ,"
				                             + " SUM(CASE isSmsIn WHEN 0 THEN 1 ELSE 0 END) AS outGoingSms FROM SmsLog sl where 1=1");
	    int temp=0;
	   // System.out.println(fromDate);
	    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (fromDate != null && !(fromDate.trim().equals(""))) {
			
			sql.append(" AND sl.created>=:fromDate");
			temp++;
		}

		if (toDate != null && !(toDate.trim().equals(""))) {
		
			sql.append(" AND sl.created<=:toDate ");
			temp=temp+2;
		}
	
		sql.append(" GROUP BY bank");
	   // System.out.println("the sql is :::::: "+ sql.toString());
		Query query=session.createQuery(sql.toString());
		//System.out.println("temp :"+temp);
		if(temp>0) {
		 try {

		 if(temp==1) {
		  query.setParameter("fromDate", (Date) formatter.parse(fromDate));
		  //System.out.println("from date before is :"+fromDate +" and after is : "+(Date) formatter.parse(fromDate));
		  }
		  else if(temp==2) {
			query.setParameter("toDate", (Date) formatter.parse(toDate));
		  }
		  else if(temp==3)
		  {
			query.setParameter("fromDate", (Date) formatter.parse(fromDate));
			query.setParameter("toDate", (Date) formatter.parse(toDate));
			
		   }
		 temp=0;
		 }catch(Exception e) {
			System.out.println("exception in smslog.api "+e.getMessage());
				
		 }
		 
		}
		
		List<Object[]> list = query.list();
		 for (int i=0;i<list.size();i++) {
			 
			 try {
			 SmsLog sl=(SmsLog) list.get(i)[0];
			// System.out.println("bank:"+sl.getBank().getName()+" total In comming sms: "+list.get(i)[1].toString()+" total out sms: "+list.get(i)[2].toString());
				List<String> dataList=new ArrayList<String>();
				dataList.add(String.valueOf(((Long)list.get(i)[1]+(Long)list.get(i)[2])));
				dataList.add(list.get(i)[1].toString());
				dataList.add(list.get(i)[2].toString());
			  map.put(sl.getBank().getName(),dataList );
			
		      }
			     catch(NullPointerException ne) {
					continue;
				}  
		  }
		
		return map;
	}
	
	
 
	//end added by amrit
	
//	@Scheduled(fixedRate = 3200)
/*	public void getTrasactionToAlert(){
		
	String traceId =	smsAlertTraceRepository.findByTraced();
	List<TransactionAlert> alertTransaction = 	transactionAlertReposiory.findbyTraceId(Integer.parseInt(traceId));
		
	for(TransactionAlert transactionAlert:alertTransaction){
		createTrasactionAlertSmsLog(transactionAlert);
		CustomerBankAccount customerBankAccount = customerBankAccountRepository.findByAccountNumber(transactionAlert.getAccounNo());
		SmsAlertTrace smsTrace = smsAlertTraceRepository.findByBank(customerBankAccount.getBranch().getBank());
		smsTrace.setTraceId(transactionAlert.getTraceId().toString());
		smsTrace = smsAlertTraceRepository.save(smsTrace);
	}
	
	}
*/
}


