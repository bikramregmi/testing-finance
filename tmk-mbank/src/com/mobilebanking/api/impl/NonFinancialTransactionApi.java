package com.mobilebanking.api.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.INonFinancialTransactionApi;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankBranch;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.NonFinancialTransaction;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.IsoStatus;
import com.mobilebanking.model.NonFinancialTransactionDTO;
import com.mobilebanking.model.NonFinancialTransactionType;
import com.mobilebanking.model.PagablePage;
import com.mobilebanking.model.UserType;
import com.mobilebanking.repositories.BankBranchRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.repositories.NonFinancialTransactionRepository;
import com.mobilebanking.repositories.UserRepository;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.ConvertUtil;
import com.mobilebanking.util.DateTypes;
import com.mobilebanking.util.DateUtil;
import com.mobilebanking.util.PageInfo;
import com.mobilebanking.util.PdfExporter;

@Service
public class NonFinancialTransactionApi implements INonFinancialTransactionApi {

	@Autowired
	private NonFinancialTransactionRepository transactionRepository;

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private BankBranchRepository branchRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private PdfExporter pdfExporter;

	@Autowired
	private UserRepository userRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<NonFinancialTransactionDTO> getTransactionByBank(Long bankId) {

		Bank bank = bankRepository.findOne(bankId);
		List<NonFinancialTransaction> transactionList = transactionRepository.findByBank(bank);
		if (transactionList != null) {
			return ConvertUtil.convertNonFinancialTransactionList(transactionList);
		}
		return null;
	}

	@Override
	public List<NonFinancialTransactionDTO> getByBranch(Long branchId) {
		BankBranch branch = branchRepository.findOne(branchId);
		List<NonFinancialTransaction> transactionList = transactionRepository.findByBankBranch(branch);
		if (transactionList != null) {
			return ConvertUtil.convertNonFinancialTransactionList(transactionList);
		}
		return null;

	}

	@Override
	public List<NonFinancialTransactionDTO> getAllTransaction() {
		List<NonFinancialTransaction> transactionList = transactionRepository.findAllTransaction();
		if (transactionList != null) {
			return ConvertUtil.convertNonFinancialTransactionList(transactionList);
		}
		return null;
	}

	@Override
	public List<NonFinancialTransactionDTO> filterTransaction(String fromDate, String toDate, String identifier,
			String status, String mobileNo, String bankId) {
		List<NonFinancialTransaction> transactionList = new ArrayList<>();

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		entityManager = entityManager.getEntityManagerFactory().createEntityManager();

		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(NonFinancialTransaction.class);

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

		if (status != null && !(status.trim().equals(""))) {
			criteria.add(Restrictions.eq("status", IsoStatus.valueOf(status)));
		}

		if (identifier != null && !(identifier.trim().equals(""))) {
			criteria.add(Restrictions.eq("transactionId", identifier));
		}

		// if (mobileNo != null && !(mobileNo.trim().equals(""))) {
		// Customer customer =
		// customerRepository.getCustomerByMobileNumber(mobileNo);
		// if (customer != null) {
		// criteria.add(Restrictions.eq("customer", customer));
		// }
		// }

		if (bankId != null && !(bankId.trim().equals("")) && (isNumber(bankId))) {
			Bank bank = bankRepository.findOne(Long.parseLong(bankId));
			criteria.add(Restrictions.eq("bank", bank));
		}

		criteria.addOrder(Order.desc("id"));

		transactionList = criteria.list();

		return ConvertUtil.convertNonFinancialTransactionList(transactionList);

	}

	@Override
	public PagablePage getAllTransactions(Integer currentpage, String fromDate, String toDate, String identifier,
			IsoStatus status, String mobileNo, String bank) {
		PagablePage pageble = new PagablePage();

		if (currentpage == null || currentpage == 0) {
			currentpage = 1;
		}

		int starting = ((currentpage * (int) PageInfo.pageList) - (int) PageInfo.pageList);
		entityManager = entityManager.getEntityManagerFactory().createEntityManager();
		Session session = entityManager.unwrap(Session.class);

		String startQuery = "select t from NonFinancialTransaction t";
		String countQueryString = "select count(t) from NonFinancialTransaction t";
		String endQuery = "";
		boolean valid = false;

		// DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Date startd = null;
		Date endd = null;
		Customer customer = null;

		try {
			if (toDate != null && !(toDate.trim().equals(""))) {
				// endd = formatter.parse(toDate);
				endd = DateUtil.getDate(toDate, DateTypes.TO_DATE);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (fromDate != null && !(fromDate.trim().equals(""))) {
				startd = DateUtil.getDate(fromDate, DateTypes.FROM_DATE);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		Bank self = null;
		if (AuthenticationUtil.getCurrentUser().getUserType() == UserType.Bank) {
			self = bankRepository.findOne(AuthenticationUtil.getCurrentUser().getAssociatedId());
			if (self != null) {
				if (valid == false) {
					endQuery += " t.bank =:self ";
				} else {
					endQuery += " and t.bank =:self ";
				}
				valid = true;
			}
		}

		BankBranch selfBranch = null;
		if (AuthenticationUtil.getCurrentUser().getUserType() == UserType.BankBranch) {
			selfBranch = branchRepository.findOne(AuthenticationUtil.getCurrentUser().getAssociatedId());
			if (selfBranch != null) {
				if (valid == false) {
					endQuery += " t.bankBranch =:selfBranch ";
				} else {
					endQuery += " and t.bankBranch =:selfBranch ";
				}
				valid = true;
			}
		}

		if (startd != null) {
			if (valid == false) {
				endQuery += " t.created >:startd ";
			} else {
				endQuery += " and t.created >:startd ";
			}
			valid = true;

		}

		if (endd != null) {
			if (valid == false) {
				endQuery += " t.created <:endd ";
			} else {
				endQuery += " and  t.created <:endd ";
			}
			valid = true;
		}
		if (status != null) {
			if (valid == false) {
				endQuery += " t.status =:status ";
			} else {
				endQuery += " and t.status =:status ";
			}
			valid = true;
		}

		if (identifier != null && !identifier.trim().equals("")) {
			if (valid == false) {
				endQuery += " t.transactionId like  CONCAT(:identifier, '%')";
			} else {
				endQuery += " and t.transactionId like CONCAT(:identifier, '%') ";
			}
			valid = true;
		}
		boolean isAdmin = false;
		if (mobileNo != null && !mobileNo.trim().equals("")) {
			/*
			 * if (valid == false) { endQuery += " t.destination =:mobileNo "; }
			 * else { endQuery += " and  t.destination =:mobileNo "; }
			 */
			if (AuthenticationUtil.getCurrentUser().getUserType() == UserType.Admin) {
				if (valid == false) {
					endQuery += " t.customer.mobileNumber =:mobilenumber ";
				} else {
					endQuery += " and t.customer.mobileNumber =:mobilenumber ";
				}
				isAdmin = true;
				valid = true;
			} else {
				if (AuthenticationUtil.getCurrentUser().getUserType() == UserType.Bank) {
					customer = customerRepository.findByMobileNumberAndBank(mobileNo, self);
				}
				if (AuthenticationUtil.getCurrentUser().getUserType() == UserType.BankBranch) {
					customer = customerRepository.findByMobileNumberAndBankBranch(mobileNo, selfBranch);
				}
				if (customer != null) {
					if (valid == false) {
						endQuery += "  t.customer =:customer ";
					} else {
						endQuery += " and t.customer =:customer ";
					}
					valid = true;

				}
			}
		}

		Bank banke = null;
		BankBranch branch = null;
		if (AuthenticationUtil.getCurrentUser().getUserType() == UserType.Admin) {
			if (bank != null && bank != "") {
				try {
					banke = bankRepository.findOne(Long.parseLong(bank));
					if (banke != null) {
						if (valid == false) {
							endQuery += " t.bank =:bank ";
						} else {
							endQuery += " and t.bank =:bank ";
						}
						valid = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (AuthenticationUtil.getCurrentUser().getUserType() == UserType.Bank) {
			if (bank != null) {
				try {
					branch = branchRepository.findOne(Long.parseLong(bank));
					if (branch != null) {
						if (valid == false) {
							endQuery += " t.bankBranch =:branch ";
						} else {
							endQuery += " and t.bankBranch =:branch ";
						}
						valid = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		if (valid) {
			startQuery += " where " + endQuery;
			countQueryString += " where " + endQuery;
		}

		startQuery += " order by t.id desc";

		Query selectQuery = session.createQuery(startQuery);
		Query countQuery = session.createQuery(countQueryString);

		if (startd != null) {
			selectQuery.setParameter("startd", startd);
			countQuery.setParameter("startd", startd);
		}

		if (endd != null) {
			selectQuery.setParameter("endd", endd);
			countQuery.setParameter("endd", endd);
		}

		if (status != null) {
			selectQuery.setParameter("status", status);
			countQuery.setParameter("status", status);
		}

		if (identifier != null && !identifier.trim().equals("")) {
			selectQuery.setParameter("identifier", identifier);
			countQuery.setParameter("identifier", identifier);
		}

		if (mobileNo != null && !mobileNo.trim().equals("")) {
			/* selectQuery.setParameter("mobileNo", mobileNo); */
			if (isAdmin) {
				selectQuery.setParameter("mobilenumber", mobileNo);
				countQuery.setParameter("mobilenumber", mobileNo);
			}
			if (customer != null) {
				selectQuery.setParameter("customer", customer);
				countQuery.setParameter("customer", customer);
			}
		}

		if (self != null) {
			selectQuery.setParameter("self", self);
			countQuery.setParameter("self", self);
		}

		if (selfBranch != null) {
			selectQuery.setParameter("selfBranch", selfBranch);
			countQuery.setParameter("selfBranch", selfBranch);
		}

		if (AuthenticationUtil.getCurrentUser().getUserType() == UserType.Admin) {
			if (banke != null) {
				selectQuery.setParameter("bank", banke);
				countQuery.setParameter("bank", banke);
			}
		} else if (AuthenticationUtil.getCurrentUser().getUserType() == UserType.Bank) {
			if (branch != null) {
				selectQuery.setParameter("branch", branch);
				countQuery.setParameter("branch", branch);
			}
		}

		int totalpage = (int) Math.ceil(((Long) countQuery.iterate().next()).longValue() / PageInfo.pageList);
		List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(currentpage, totalpage, PageInfo.numberOfPage);
		selectQuery.setFirstResult(starting);
		selectQuery.setMaxResults((int) PageInfo.pageList);
		List<NonFinancialTransaction> trnasactions = selectQuery.list();
		pageble.setCurrentPage(currentpage);
		pageble.setObject(ConvertUtil.convertNonFinancialTransactionList(trnasactions));
		pageble.setPageList(pagesnumbers);
		pageble.setLastpage(totalpage);

		return pageble;
	}

	private boolean isNumber(String s) {
		try {
			Long.parseLong(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Long countNonFinancialTransactionCountByTransactionType(
			NonFinancialTransactionType nonFinancialTransactionType) {
		User currentUser = AuthenticationUtil.getCurrentUser();
		if (currentUser.getUserType() == UserType.Admin) {
			return transactionRepository.countByTransactionType(nonFinancialTransactionType);
		} else if (currentUser.getUserType() == UserType.Bank) {
			Bank bank = bankRepository.findOne(currentUser.getAssociatedId());
			return transactionRepository.countByBankAndTransactionType(bank, nonFinancialTransactionType);
		} else if (currentUser.getUserType() == UserType.BankBranch) {
			BankBranch bankBranch = branchRepository.findOne(currentUser.getAssociatedId());
			return transactionRepository.countByBankBranchAndTransactionType(bankBranch, nonFinancialTransactionType);
		} else if (currentUser.getUserType() == UserType.Customer) {
			Customer customer = customerRepository.findOne(currentUser.getAssociatedId());
			return transactionRepository.countByCustomerAndTransactionType(customer, nonFinancialTransactionType);
		}
		return transactionRepository.countByTransactionType(nonFinancialTransactionType);
	}

	@Override
	public String exportNonFinancialTransaction(String path) {
		List<NonFinancialTransaction> transactionList = new ArrayList<>();
		if (AuthenticationUtil.getCurrentUser().getUserType() == UserType.Admin) {
			transactionList = transactionRepository.findAllTransaction();
		} else if (AuthenticationUtil.getCurrentUser().getUserType() == UserType.Bank) {
			Bank bank = bankRepository.findOne(AuthenticationUtil.getCurrentUser().getAssociatedId());
			transactionList = transactionRepository.findByBank(bank);
		} else if (AuthenticationUtil.getCurrentUser().getUserType() == UserType.BankBranch) {
			BankBranch branch = branchRepository.findOne(AuthenticationUtil.getCurrentUser().getAssociatedId());
			transactionList = transactionRepository.findByBankBranch(branch);
		}
		return pdfExporter.exportNonFinancialReport(transactionList, path);
	}

	@Override
	public Long countTransaction() {
		User currentUser = userRepository.findOne(AuthenticationUtil.getCurrentUser().getId());
		if (currentUser.getUserType().equals(UserType.Admin)) {
			return transactionRepository.countAllTransaction();
		} else if (currentUser.getUserType().equals(UserType.Bank)) {
			return transactionRepository.countAllTransactionByBank(currentUser.getAssociatedId());
		} else if (currentUser.getUserType().equals(UserType.BankBranch)) {
			return transactionRepository.countAllTransactionByBranch(currentUser.getAssociatedId());
		}
		return 0L;
	}

	// added by amrit
	@Override
	public Long countTransactionsByDate(Date date) {
		User currentUser = userRepository.findOne(AuthenticationUtil.getCurrentUser().getId());
		if (currentUser.getUserType().equals(UserType.Admin)) {
			return transactionRepository.countAllTransactionByDate(date);
		} else if (currentUser.getUserType().equals(UserType.Bank)) {
			return transactionRepository.countAllTransactionByBankAndDate(currentUser.getAssociatedId(), date);
		} else if (currentUser.getUserType().equals(UserType.BankBranch)) {
			return transactionRepository.countAllTransactionByBranchAndDate(currentUser.getAssociatedId(), date);
		}
		return 0L;
	}

	@Override
	public Long countNonFinancialTransactionCountByTransactionTypeAndDate(
			NonFinancialTransactionType nonFinancialTransactionType, Date date) {
		User currentUser = AuthenticationUtil.getCurrentUser();
		if (currentUser.getUserType() == UserType.Admin) {
			return transactionRepository.countByTransactionTypeAndDate(nonFinancialTransactionType, date);
		} else if (currentUser.getUserType() == UserType.Bank) {
			Bank bank = bankRepository.findOne(currentUser.getAssociatedId());
			return transactionRepository.countByBankAndTransactionTypeAndDate(bank, nonFinancialTransactionType, date);
		} else if (currentUser.getUserType() == UserType.BankBranch) {
			BankBranch bankBranch = branchRepository.findOne(currentUser.getAssociatedId());
			return transactionRepository.countByBankBranchAndTransactionTypeAndDate(bankBranch,
					nonFinancialTransactionType, date);
		} else if (currentUser.getUserType() == UserType.Customer) {
			Customer customer = customerRepository.findOne(currentUser.getAssociatedId());
			return transactionRepository.countByCustomerAndTransactionTypeAndDate(customer, nonFinancialTransactionType,
					date);
		}
		return transactionRepository.countByTransactionTypeAndDate(nonFinancialTransactionType, date);

	}

}
