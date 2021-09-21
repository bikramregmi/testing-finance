package com.mobilebanking.api.impl;

import java.text.DateFormat;
import java.text.ParseException;
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
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IAccountApi;
import com.mobilebanking.api.ILedgerApi;
import com.mobilebanking.converter.LedgerConverter;
import com.mobilebanking.entity.Account;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.Ledger;
import com.mobilebanking.model.LedgerDTO;
import com.mobilebanking.model.LedgerStatus;
import com.mobilebanking.model.LedgerType;
import com.mobilebanking.model.PagablePage;
import com.mobilebanking.repositories.AccountRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.LedgerRepository;
import com.mobilebanking.repositories.TransactionRepository;
import com.mobilebanking.util.ClientException;
import com.mobilebanking.util.ConvertUtil;
import com.mobilebanking.util.DateTypes;
import com.mobilebanking.util.DateUtil;
import com.mobilebanking.util.PageInfo;
import com.mobilebanking.util.StringConstants;

@Service
public class LedgerApi implements ILedgerApi {

	@Autowired
	private LedgerRepository ledgerRepository;

	@Autowired
	private IAccountApi accountService;

	@Autowired
	private LedgerConverter ledgerConverter;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TransactionRepository transactionRepisotory;

	@Autowired
	private BankRepository bankRepository;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private ConvertUtil convertUtil;

	@Override
	public Ledger searchByLedgerId(long ledgerId) {
		return ledgerRepository.findByLedgerId(ledgerId);
	}

	@Override
	public Ledger saveLedger(long txnId, double amount, Account fromAccount, Account toAccount, LedgerStatus status,
			LedgerType type) throws ClientException {
		String identifier = transactionRepisotory.findOne(txnId).getTransactionIdentifier();
		Ledger ledger = new Ledger();
		ledger.setAmount(amount);
		ledger.setCreated(new Date());
		ledger.setFromAccount(fromAccount);
		ledger.setToAccount(toAccount);
		ledger.setFromBalance(fromAccount.getBalance());
		ledger.setToBalance(toAccount.getBalance());
		ledger.setTransactionId(identifier);
		ledger.setStatus(status);
		ledger.setType(type);
		return ledgerRepository.save(ledger);

	}

	@Override
	public LedgerDTO createLedger(LedgerDTO dto) throws ClientException {
		Account fromAccount = accountService.searchByAccountId(dto.getFromAccount());
		Account toAccount = accountService.searchByAccountId(dto.getToAccount());

		/*
		 * if (dto.getTransactionType().equals(LedgerType.TRANSFER)) { if
		 * (fromAccount == null || toAccount == null) { throw new
		 * ClientException("Invalid Account Details"); } } else { throw new
		 * ClientException("Invalid Transaction Type"); }
		 */

		Ledger txn = new Ledger();
		txn.setAmount(dto.getAmount());
		txn.setFromAccount(fromAccount);
		txn.setToAccount(toAccount);
		if (fromAccount != null) {
			txn.setFromBalance(fromAccount.getBalance() - dto.getAmount());
		}
		txn.setRemarks(dto.getRemarks());
		txn.setToBalance(toAccount.getBalance() + dto.getAmount());
		txn.setStatus(dto.getTransactionStatus());
		txn.setTransactionId(dto.getTransactionId());
		txn.setType(dto.getTransactionType());
		txn.setExternalRefId(dto.getExternalRefId());
		ledgerRepository.save(txn);
		return ledgerConverter.convertToDto(txn);

	}

	@Override
	public LedgerDTO reverseTxnInLedger(long ledgerId, String remarks) throws ClientException {
		Ledger txn = ledgerRepository.findByLedgerId(ledgerId);
		LedgerDTO dto = new LedgerDTO();
		if (txn != null) {
			if (txn.getStatus().equals(LedgerStatus.COMPLETE)) {
				if (txn.getType().equals(LedgerType.TRANSFER)) {
					Account fromAccount = txn.getFromAccount();
					Account toAccount = txn.getToAccount();
					if (toAccount.getBalance() >= txn.getAmount()) {
						fromAccount.setBalance(fromAccount.getBalance() + txn.getAmount());
						accountRepository.save(fromAccount);
						toAccount.setBalance(toAccount.getBalance() - txn.getAmount());
						accountRepository.save(toAccount);
						txn.setLastModified(new Date());
						txn.setStatus(LedgerStatus.REVERSED);
						ledgerRepository.save(txn);

						Ledger reversedTransaction = new Ledger();

						reversedTransaction.setStatus(LedgerStatus.COMPLETE);
						reversedTransaction.setCreated(new Date());
						reversedTransaction.setLastModified(new Date());
						reversedTransaction.setAmount(txn.getAmount());
						reversedTransaction.setFromAccount(toAccount);
						reversedTransaction.setToAccount(fromAccount);
						reversedTransaction.setType(LedgerType.REVERSAL);
						reversedTransaction.setFromBalance(toAccount.getBalance());
						reversedTransaction.setToBalance(fromAccount.getBalance());
						reversedTransaction.setParentId(txn.getId());
						reversedTransaction.setRemarks(remarks);
						reversedTransaction.setTransactionId("" + System.currentTimeMillis()); // need
																								// timestamp
						ledgerRepository.save(reversedTransaction);

						dto = ledgerConverter.convertToDto(reversedTransaction);
					} else {
						throw new ClientException("Insufficient Amount in Receiver Account For Reversal");
					}
				}
				return dto;
			} else if (txn.getStatus().equals(LedgerStatus.PENDING)) {
				throw new ClientException("Transaction Not yet Processed");
			} else if (txn.getStatus().equals(LedgerStatus.REVERSED)) {
				throw new ClientException("Transaction Already Reversed");
			} else if (txn.getStatus().equals(LedgerStatus.CANCEL)) {
				throw new ClientException("Transaction Already Cancelled");
			} else {
				throw new ClientException("Invalid Transaction Status");
			}
		} else {
			throw new ClientException("Invalid Transaction Id");
		}

	}

	@Override
	public LedgerDTO cancelTxnInLedger(long ledgerId) throws ClientException {

		Ledger txn = ledgerRepository.findByLedgerId(ledgerId);
		if (txn != null) {
			if (txn.getStatus().equals(LedgerStatus.PENDING)) {
				txn.setStatus(LedgerStatus.CANCEL);
				txn.setLastModified(new Date());
				ledgerRepository.save(txn);
				return ledgerConverter.convertToDto(txn);
			} else if (txn.getStatus().equals(LedgerStatus.COMPLETE)) {
				throw new ClientException(
						"Transaction Already Completed.Cannot Cancel The Transaction But Can Reverse The Transaction");
			} else if (txn.getStatus().equals(LedgerStatus.CANCEL)) {
				throw new ClientException("Transaction Already Cancelled");
			} else if (txn.getStatus().equals(LedgerStatus.REVERSED)) {
				throw new ClientException("Transaction Already Reversed");
			} else {
				throw new ClientException("Invalid Transaction Status");
			}
		} else {
			throw new ClientException("Invalid Transaction Id");
		}

	}

	@Override
	public List<LedgerDTO> getStatement(long accountId) throws Exception {
		List<Ledger> txn = ledgerRepository.findByAccountId(accountId);
		return ledgerConverter.convertToDtoList(txn);
	}

	@Override
	public LedgerDTO getLedgerDetail(long txnId) {
		Ledger txn = searchByLedgerId(txnId);
		return ledgerConverter.convertToDto(txn);

	}

	@Override
	public Ledger saveCommissionSettlementInLedger(long txnId, double amount, Account fromAccount, Account toAccount,
			double fromCommissionInStlmntCur, double toCommissionInStlmntCur, LedgerStatus status, LedgerType type)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LedgerDTO editLedger(LedgerDTO dto) throws ClientException {
		Account fromAccount = accountService.searchByAccountId(dto.getFromAccount());
		Account toAccount = accountService.searchByAccountId(dto.getToAccount());
		Ledger txn = ledgerRepository.findOne(dto.getId());
		ledgerRepository.save(txn);

		if (dto.getTransactionStatus().equals(LedgerStatus.COMPLETE)) {
			if (fromAccount.getBalance() >= dto.getAmount()) {
				fromAccount.setBalance(fromAccount.getBalance() - txn.getAmount());
				accountRepository.save(fromAccount);
				toAccount.setBalance(toAccount.getBalance() + txn.getAmount());
				accountRepository.save(toAccount);
			} else {
				throw new ClientException("Insufficient Amount in Sender Account");
			}
		}

		return ledgerConverter.convertToDto(txn);

	}

	@Override
	public HashMap<String, Object> getLoadFundReport(String fromDate, String toDate) {
		HashMap<String, Object> loadFundReport = new HashMap<>();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		entityManager = entityManager.getEntityManagerFactory().createEntityManager();

		Session session = entityManager.unwrap(Session.class);
		List<Bank> bankList = bankRepository.findAll();
		loadFundReport.put("totalBank", bankList.size());
		List<HashMap<String, Object>> ledgerList = new ArrayList<>();
		if (fromDate != null && !(fromDate.trim().equals(""))) {
			loadFundReport.put("fromDate", fromDate);
		} else {
			loadFundReport.put("fromDate", "-");
		}
		if (toDate != null && !(toDate.trim().equals(""))) {
			loadFundReport.put("toDate", toDate);
		} else {
			loadFundReport.put("toDate", "-");
		}
		for (Bank bank : bankList) {
			Account account = accountRepository.findAccountByAccountNumber(bank.getAccountNumber());
			Criteria criteria = session.createCriteria(Ledger.class);
			criteria.add(Restrictions.eq("type", LedgerType.LOADFUND));
			try {
				if (fromDate != null && !(fromDate.trim().equals(""))) {
					criteria.add(Restrictions.ge("created", (Date) formatter.parse(fromDate)));
				}
				if (toDate != null && !(toDate.trim().equals(""))) {
					criteria.add(Restrictions.lt("created", (Date) formatter.parse(toDate)));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			criteria.add(Restrictions.eq("toAccount", account));
			List<Ledger> ledgers = criteria.list();
			for (Ledger ledger : ledgers) {
				HashMap<String, Object> ledgerMap = new HashMap<>();
				ledgerMap.put("bankName", bank.getName());
				ledgerMap.put("bankCode", bank.getSwiftCode());
				ledgerMap.put("amount", ledger.getAmount());
				ledgerMap.put("date", DateUtil.convertDateToString(ledger.getCreated()));
				ledgerMap.put("status", ledger.getStatus());
				ledgerList.add(ledgerMap);
			}
		}
		loadFundReport.put("ledgerList", ledgerList);
		return loadFundReport;
	}

	@Override
	public Map<String, Object> getCreditLimitReport(String fromDate, String toDate) {
		// TODO Auto-generated method stub
		Map<String, Object> creditLimitDataMap = new HashMap<String, Object>();
		entityManager = entityManager.getEntityManagerFactory().createEntityManager();
		Session session = entityManager.unwrap(Session.class);
		StringBuilder sql = new StringBuilder(
				"SELECT l.created as date, l.toAccount.accountHead as Bank, l.toAccount.accountNumber as accountNumber, l.amount as creditLimitAmount, l.remarks FROM Ledger l WHERE l.isCredit=1  and l.type=:type and remarks like 'Credit Limit%'");
		int temp = 0;

		if (fromDate != null && !(fromDate.trim().equals(""))) {

			sql.append(" AND l.created>=:fromDate");
			temp++;
		}

		if (toDate != null && !(toDate.trim().equals(""))) {

			sql.append(" AND l.created<=:toDate ");
			temp = temp + 2;
		}
		Query query = session.createQuery(sql.toString());
		// System.out.println("final query is :"+query);
		query.setParameter("type", LedgerType.UPDATE_CREDIT_LIMIT);
		if (temp > 0) {
			try {

				if (temp == 1) {
					query.setParameter("fromDate", DateUtil.getDate(fromDate, DateTypes.FROM_DATE));
				} else if (temp == 2) {
					query.setParameter("toDate", DateUtil.getDate(toDate, DateTypes.TO_DATE));
				} else if (temp == 3) {
					query.setParameter("fromDate", DateUtil.getDate(fromDate, DateTypes.FROM_DATE));
					query.setParameter("toDate", DateUtil.getDate(toDate, DateTypes.TO_DATE));

				}
				temp = 0;
			} catch (Exception e) {
				System.out.println("exception in ledgerapi.api " + e.getMessage());

			}
		}
		List<Object[]> resultList = query.list();
		if (resultList == null || resultList.isEmpty()) {
			return null;
		}
		// System.out.println("the size of the list is : "+ resultList.size());
		for (int i = 0; i < resultList.size(); i++) {
			List<String> list = new ArrayList<String>();
			Object[] objectArray = resultList.get(i);
			list.add(DateUtil.convertDateToString((Date) objectArray[0]));
			list.add(objectArray[1].toString());// bank
			list.add(objectArray[2].toString());// a/c no.
			list.add(String.valueOf(objectArray[3]));// credit limit
			list.add(objectArray[4].toString()); // remarks

			creditLimitDataMap.put(String.valueOf(i + 1), list);
		}

		return creditLimitDataMap;
	}

	@Override
	public PagablePage getLedgerHistory(Integer currentPage, String fromDate, String toDate, String bankCode,
			String type) {
		PagablePage pageble = new PagablePage();

		if (currentPage == null || currentPage == 0) {
			currentPage = 1;
		}
		int starting = ((currentPage * (int) PageInfo.pageList) - (int) PageInfo.pageList);
		entityManager = entityManager.getEntityManagerFactory().createEntityManager();
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Ledger.class);
		Criteria criteria2 = session.createCriteria(Ledger.class);

		if (fromDate != null && fromDate != "") {
			criteria.add(Restrictions.ge("created", DateUtil.getDate(fromDate, DateTypes.FROM_DATE)));
			criteria2.add(Restrictions.ge("created", DateUtil.getDate(fromDate, DateTypes.FROM_DATE)));
		}
		if (toDate != null && toDate != "") {
			criteria.add(Restrictions.le("created", DateUtil.getDate(toDate, DateTypes.TO_DATE)));
			criteria2.add(Restrictions.le("created", DateUtil.getDate(toDate, DateTypes.TO_DATE)));
		}
		if (bankCode != null && bankCode != "") {
			Bank bank = bankRepository.findOne(Long.parseLong(bankCode));
			if (bank != null && bank.getAccountNumber() != null && !bank.getAccountNumber().equalsIgnoreCase("")) {
				criteria.add(Restrictions.eq("toAccount",
						accountRepository.findAccountByAccountNumber(bank.getAccountNumber())));
				criteria2.add(Restrictions.eq("toAccount",
						accountRepository.findAccountByAccountNumber(bank.getAccountNumber())));

			}
		}

		if (type != null && type != "") {
			if (type.equalsIgnoreCase(LedgerType.UPDATE_CREDIT_LIMIT.getValue())) {
				criteria.add(Restrictions.eq("type", LedgerType.UPDATE_CREDIT_LIMIT));
				criteria2.add(Restrictions.eq("type", LedgerType.UPDATE_CREDIT_LIMIT));

			} else if (type.equalsIgnoreCase(LedgerType.LOADFUND.getValue())) {
				criteria.add(Restrictions.eq("type", LedgerType.LOADFUND));
				criteria2.add(Restrictions.eq("type", LedgerType.LOADFUND));
			}

			else if (type.equalsIgnoreCase(LedgerType.DECREASE_BALANCE.getValue())) {
				criteria.add(Restrictions.eq("type", LedgerType.DECREASE_BALANCE));
				criteria2.add(Restrictions.eq("type", LedgerType.DECREASE_BALANCE));

			} else {
				criteria.add(Restrictions.or(Restrictions.eq("type", LedgerType.UPDATE_CREDIT_LIMIT),
						Restrictions.or(Restrictions.eq("type", LedgerType.LOADFUND),
								Restrictions.eq("type", LedgerType.DECREASE_BALANCE))));
				criteria2.add(Restrictions.or(Restrictions.eq("type", LedgerType.UPDATE_CREDIT_LIMIT),
						Restrictions.or(Restrictions.eq("type", LedgerType.LOADFUND),
								Restrictions.eq("type", LedgerType.DECREASE_BALANCE))));

			}
		} else {
			criteria.add(Restrictions.or(Restrictions.eq("type", LedgerType.UPDATE_CREDIT_LIMIT),
					Restrictions.or(Restrictions.eq("type", LedgerType.LOADFUND),
							Restrictions.eq("type", LedgerType.DECREASE_BALANCE))));
			criteria2.add(Restrictions.or(Restrictions.eq("type", LedgerType.UPDATE_CREDIT_LIMIT),
					Restrictions.or(Restrictions.eq("type", LedgerType.LOADFUND),
							Restrictions.eq("type", LedgerType.DECREASE_BALANCE))));

		}
		try {
			// int totalpage = (int) Math.ceil(criteria.list().size() /
			// PageInfo.pageList);

			criteria.setProjection(Projections.rowCount());
			int totalpage = (int) ((Integer.valueOf(criteria.uniqueResult().toString())) / PageInfo.pageList);
			System.out.println("the total page is : " + totalpage);
			List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(currentPage, totalpage, PageInfo.numberOfPage);

			criteria2.setFirstResult(starting);
			criteria2.setMaxResults((int) PageInfo.pageList);
			List<Ledger> ledgerHistory = criteria2.list();
			pageble.setObject(convertUtil.convertLedgerListToDTO(ledgerHistory));
			pageble.setPageList(pagesnumbers);
			pageble.setLastpage(totalpage);
			pageble.setCurrentPage(currentPage);
		} catch (NullPointerException ne) {

			ne.printStackTrace();
			return null;
		}
		// pageble.setCurrentPage(currentPage);

		return pageble;

	}

	@Override
	public List<LedgerDTO> getBankLedger(String bankCode, String fromDate, String toDate, String debitCredit) {
		PagablePage pg = new PagablePage();

		entityManager = entityManager.getEntityManagerFactory().createEntityManager();
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Ledger.class);

		if (toDate != null && !toDate.equalsIgnoreCase("")) {
			criteria.add(Restrictions.le("created", DateUtil.getDate(toDate, DateTypes.TO_DATE)));
		}
		if (fromDate != null && !fromDate.equalsIgnoreCase("")) {
			criteria.add(Restrictions.ge("created", DateUtil.getDate(fromDate, DateTypes.FROM_DATE)));
		}

		if (bankCode != null && !bankCode.trim().equalsIgnoreCase("")) {
			Bank bank = bankRepository.findOne(Long.valueOf(bankCode));
			if (bank != null && bank.getAccountNumber() != null && !(bank.getAccountNumber().equalsIgnoreCase(""))) {
				Account bnkAccount = accountRepository.findAccountByAccountNumber(bank.getAccountNumber());
				if (debitCredit != null && !(debitCredit.trim().equalsIgnoreCase(""))) {
					if (debitCredit.equalsIgnoreCase(StringConstants.DEBIT)) {
						criteria.add(Restrictions.eq("fromAccount", bnkAccount));
					} else if (debitCredit.equalsIgnoreCase(StringConstants.CREDIT)) {
						criteria.add(Restrictions.eq("toAccount", bnkAccount));
					} else {
						criteria.add(Restrictions.or(Restrictions.eq("toAccount", bnkAccount),
								Restrictions.eq("fromAccount", bnkAccount)));
					}
				} else {
					criteria.add(Restrictions.or(Restrictions.eq("toAccount", bnkAccount),
							Restrictions.eq("fromAccount", bnkAccount)));
				}
			}
		}
		try {
			criteria.add(Restrictions.or(Restrictions.or(Restrictions.like("remarks", "Transfer To", MatchMode.START),
					Restrictions.like("remarks", "Cash Account", MatchMode.ANYWHERE)), Restrictions.like("remarks", "Decrease Balance", MatchMode.START)));
			criteria.addOrder(Order.asc("created"));
			List<Ledger> ledgerList = criteria.list();
			return convertUtil.convertLedgerListToDTO(ledgerList);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public PagablePage getLedgerHistory1(Integer currentPage, String fromDate, String toDate, String bankCode,
			String type) {

		PagablePage pageble = new PagablePage();

		if (currentPage == null || currentPage == 0) {
			currentPage = 1;
		}
		int starting = ((currentPage * (int) PageInfo.pageList) - (int) PageInfo.pageList);
		entityManager = entityManager.getEntityManagerFactory().createEntityManager();
		Session session = entityManager.unwrap(Session.class);
		StringBuilder sql = new StringBuilder("select count(l) from Ledger l where 1=1 ");
		// StringBuilder sql2=new StringBuilder("select l from Ledger l where
		// 1=1 ");
		StringBuilder suffix = new StringBuilder("");
		Criteria criteria = session.createCriteria(Ledger.class);
		if (fromDate != null && fromDate != "") {
			criteria.add(Restrictions.ge("created", DateUtil.getDate(fromDate, DateTypes.FROM_DATE)));
			suffix = suffix.append("and l.created>=:fromDate ");
		}
		if (toDate != null && toDate != "") {
			criteria.add(Restrictions.le("created", DateUtil.getDate(toDate, DateTypes.TO_DATE)));
			suffix = suffix.append("and l.created<=:fromDate ");
		}
		if (bankCode != null && bankCode != "") {
			Bank bank = bankRepository.findOne(Long.parseLong(bankCode));
			if (bank != null && bank.getAccountNumber() != null && !bank.getAccountNumber().equalsIgnoreCase("")) {
				criteria.add(Restrictions.eq("toAccount",
						accountRepository.findAccountByAccountNumber(bank.getAccountNumber())));
				suffix = suffix.append("and l.toAccount=:bankCode ");
			}
		}

		if (type != null && type != "") {
			if (type.equalsIgnoreCase(LedgerType.UPDATE_CREDIT_LIMIT.getValue())) {
				criteria.add(Restrictions.eq("type", LedgerType.UPDATE_CREDIT_LIMIT));
				suffix = suffix.append("and l.type=:creditLimit ");
			} else if (type.equalsIgnoreCase(LedgerType.LOADFUND.getValue())) {
				criteria.add(Restrictions.eq("type", LedgerType.LOADFUND));
				suffix = suffix.append("and l.type<=:loadFund ");
			}

			else if (type.equalsIgnoreCase(LedgerType.DECREASE_BALANCE.getValue())) {
				criteria.add(Restrictions.eq("type", LedgerType.DECREASE_BALANCE));
				suffix = suffix.append("and l.type<=:decreaseBalance ");
			} else {
				criteria.add(Restrictions.or(Restrictions.eq("type", LedgerType.UPDATE_CREDIT_LIMIT),
						Restrictions.or(Restrictions.eq("type", LedgerType.LOADFUND),
								Restrictions.eq("type", LedgerType.DECREASE_BALANCE))));
				// can be coded for the or operation
			}
		}
		sql.append(suffix.toString());
		Query query = session.createQuery(sql.toString());
		if (fromDate != null && fromDate != "") {
			// criteria.add(Restrictions.ge("created",DateUtil.getDate(fromDate,
			// DateTypes.FROM_DATE)));
			query.setParameter("fromDate", DateUtil.getDate(fromDate, DateTypes.FROM_DATE));
		}
		if (toDate != null && toDate != "") {
			// criteria.add(Restrictions.le("created",DateUtil.getDate(toDate,DateTypes.TO_DATE)));

			query.setParameter("toDate", DateUtil.getDate(toDate, DateTypes.TO_DATE));
		}
		if (bankCode != null && bankCode != "") {
			Bank bank = bankRepository.findOne(Long.parseLong(bankCode));
			if (bank != null && bank.getAccountNumber() != null && !bank.getAccountNumber().equalsIgnoreCase("")) {
				// criteria.add(Restrictions.eq("toAccount",
				// accountRepository.findAccountByAccountNumber(bank.getAccountNumber())));
				// suffix=suffix.append("and l.toAccount=:bankCode ");
				query.setParameter("bankCode", accountRepository.findAccountByAccountNumber(bank.getAccountNumber()));
			}
		}

		if (type != null && type != "") {
			if (type.equalsIgnoreCase(LedgerType.UPDATE_CREDIT_LIMIT.getValue())) {
				// criteria.add(Restrictions.eq("type",
				// LedgerType.UPDATE_CREDIT_LIMIT));
				// suffix=suffix.append("and l.type=:creditLimit ");
				query.setParameter("type", LedgerType.UPDATE_CREDIT_LIMIT);

			} else if (type.equalsIgnoreCase(LedgerType.LOADFUND.getValue())) {
				// criteria.add(Restrictions.eq("type", LedgerType.LOADFUND));
				// suffix=suffix.append("and l.type<=:loadFund ");
				query.setParameter("type", LedgerType.LOADFUND);
			}

			/*
			 * else
			 * if(type.equalsIgnoreCase(LedgerType.DECREASE_BALANCE.getValue()))
			 * { criteria.add(Restrictions.eq("type",
			 * LedgerType.DECREASE_BALANCE)); }
			 */

		}
		// sql2.append(suffix);
		try {
			// int totalpage = (int) Math.ceil(criteria.list().size() /
			// PageInfo.pageList);
			// criteria.setProjection(Projections.rowCount());
			int totalpage = (int) ((Integer.valueOf(query.uniqueResult().toString())) / PageInfo.pageList);
			System.out.println("the total page is : " + totalpage);
			List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(currentPage, totalpage, PageInfo.numberOfPage);
			// criteria.setProjection(null);
			criteria.setFirstResult(starting);
			criteria.setMaxResults((int) PageInfo.pageList);
			List<Ledger> ledgerHistory = criteria.list();
			pageble.setObject(convertUtil.convertLedgerListToDTO(ledgerHistory));
			pageble.setPageList(pagesnumbers);
			pageble.setLastpage(totalpage);
			pageble.setCurrentPage(currentPage);
		} catch (NullPointerException ne) {

			ne.printStackTrace();
			return null;
		}
		// pageble.setCurrentPage(currentPage);

		return pageble;

	}

	/*
	 * @Override public Map<String, Object> getCreditLimitReport(String
	 * fromDate, String toDate) { // TODO Auto-generated method stub Map<String,
	 * Object> creditLimitDataMap =new HashMap<String , Object>();
	 * entityManager=entityManager.getEntityManagerFactory().createEntityManager
	 * (); Session session= entityManager.unwrap(Session.class); Criteria
	 * c=session.createCriteria(Ledger.class); DateFormat format=new
	 * SimpleDateFormat("yyyy-mm-dd"); c.add(Restrictions.eq("isCredit", true));
	 * c.add(Restrictions.eqProperty("fromAccount", "toAccount"));
	 * 
	 * c.add(Restrictions.ilike("remarks", "Credit Limit"));
	 * 
	 * 
	 * System.out.println("here  i am"); List<Ledger>
	 * resultList=(List<Ledger>)c.list();
	 * 
	 * System.out.println("the size of the list is : "+ resultList.size());
	 * creditLimitDataMap.put("creditLimitList", resultList); return
	 * creditLimitDataMap; }
	 */

	/*
	 * @Override public List<Ledger> getCreditLimitReport(String fromDate,
	 * String toDate) { // TODO Auto-generated method stub
	 * entityManager=entityManager.getEntityManagerFactory().createEntityManager
	 * (); Session session= entityManager.unwrap(Session.class); Criteria
	 * c=session.createCriteria(Ledger.class); DateFormat format=new
	 * SimpleDateFormat("yyyy-mm-dd"); c.add(Restrictions.eq("isCredit", true));
	 * c.add(Restrictions.eqProperty("fromAccount.id", "toAccount.id"));
	 * 
	 * c.add(Restrictions.like("remarks", "Credit",MatchMode.START));
	 * StringBuilder sql=new
	 * StringBuilder("SELECT l.fromAccount.accountHead as Bank, l.fromAccount.accountNumber as accountNumber, l.toBalance as creditLimitAmount, l.remarks FROM Ledger l WHERE l.isCredit=1 and l.fromAccount=l.toAccount and remarks like 'Credit Limit%'"
	 * ); int temp=0;
	 * 
	 * DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); if (fromDate
	 * != null && !(fromDate.trim().equals(""))) {
	 * 
	 * sql.append(" AND l.created>=:fromDate"); temp++; }
	 * 
	 * if (toDate != null && !(toDate.trim().equals(""))) {
	 * 
	 * sql.append(" AND l.created<=:toDate "); temp=temp+2; } Query
	 * query=session.createQuery(sql.toString());
	 * 
	 * if(temp>0) { try {
	 * 
	 * if(temp==1) { query.setParameter("fromDate", (Date)
	 * formatter.parse(fromDate));
	 * //System.out.println("from date before is :"+fromDate
	 * +" and after is : "+(Date) formatter.parse(fromDate)); } else if(temp==2)
	 * { query.setParameter("toDate", (Date) formatter.parse(toDate)); } else
	 * if(temp==3) { query.setParameter("fromDate", (Date)
	 * formatter.parse(fromDate)); query.setParameter("toDate", (Date)
	 * formatter.parse(toDate));
	 * 
	 * } temp=0; }catch(Exception e) {
	 * System.out.println("exception in smslog.api "+e.getMessage());
	 * 
	 * } } System.out.println("here  i am"); List<Object[]> resultList=
	 * query.list();
	 * 
	 * System.out.println("the size of the list is : "+ resultList.size());
	 * return resultList; }
	 */

}
