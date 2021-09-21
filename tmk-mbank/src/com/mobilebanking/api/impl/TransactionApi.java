package com.mobilebanking.api.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.ICustomerApi;
import com.mobilebanking.api.ICustomerProfileApi;
import com.mobilebanking.api.ITransactionApi;
import com.mobilebanking.api.ITransactionLogApi;
import com.mobilebanking.converter.TransactionConverter;
import com.mobilebanking.core.CommissionCore;
import com.mobilebanking.core.TransactionCore;
import com.mobilebanking.entity.Account;
import com.mobilebanking.entity.AccountPosting;
import com.mobilebanking.entity.AirlinesCommissionManagement;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankAccountSettings;
import com.mobilebanking.entity.BankBranch;
import com.mobilebanking.entity.BankCommission;
import com.mobilebanking.entity.Commission;
import com.mobilebanking.entity.CommissionAmount;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.CustomerBankAccount;
import com.mobilebanking.entity.Merchant;
import com.mobilebanking.entity.MerchantManager;
import com.mobilebanking.entity.MerchantService;
import com.mobilebanking.entity.SettlementLog;
import com.mobilebanking.entity.Transaction;
import com.mobilebanking.entity.TransactionLog;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.PagablePage;
import com.mobilebanking.model.Revenue;
import com.mobilebanking.model.RevenueReport;
import com.mobilebanking.model.SettlementStatus;
import com.mobilebanking.model.SettlementType;
import com.mobilebanking.model.Status;
import com.mobilebanking.model.TransactionDTO;
import com.mobilebanking.model.TransactionStatus;
import com.mobilebanking.model.TransactionType;
import com.mobilebanking.model.UserType;
import com.mobilebanking.repositories.AccountPostingRepository;
import com.mobilebanking.repositories.AccountRepository;
import com.mobilebanking.repositories.AirlinesCommissionManagementRepository;
import com.mobilebanking.repositories.BankAccountSettingsRepository;
import com.mobilebanking.repositories.BankBranchRepository;
import com.mobilebanking.repositories.BankCommissionRepository;
import com.mobilebanking.repositories.BankMerchantAccountsRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.CommissionAmountRepository;
import com.mobilebanking.repositories.CommissionRepository;
import com.mobilebanking.repositories.CustomerBankAccountRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.repositories.MerchantRepository;
import com.mobilebanking.repositories.MerchantServiceRepository;
import com.mobilebanking.repositories.SettlementLogRepository;
import com.mobilebanking.repositories.TransactionLogRepository;
import com.mobilebanking.repositories.TransactionRepository;
import com.mobilebanking.repositories.UserRepository;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.ConvertUtil;
import com.mobilebanking.util.DateTypes;
import com.mobilebanking.util.DateUtil;
import com.mobilebanking.util.ETransactionChannelForm;
import com.mobilebanking.util.PageInfo;
import com.mobilebanking.util.PdfExporter;
import com.mobilebanking.util.StringConstants;
import com.wallet.serviceprovider.paypoint.service.PayPoint_GetCompanyInfoService;

@Service
public class TransactionApi implements ITransactionApi {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BankCommissionRepository bankCommissionRepository;

	@Autowired
	private CommissionRepository commissionRepository;

	@Autowired
	private CommissionAmountRepository commissionAmountRepository;

	@Autowired
	private TransactionCore transactionCore;

	@Autowired
	private TransactionLogRepository transactionLogRepository;

	@Autowired
	private TransactionConverter transactionConverter;

	@Autowired
	private BankRepository bankRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private CustomerBankAccountRepository customerBankAccountRepository;

	@Autowired
	private ICustomerApi customerApi;

	@Autowired
	private AccountPostingRepository accountPostingRepository;

	@Autowired
	private PayPoint_GetCompanyInfoService payPointService;

	@Autowired
	private BankBranchRepository bankBranchRepository;

	@Autowired
	private CommissionCore commissionCore;

	@Autowired
	private BankAccountSettingsRepository bankAccountSettingsRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ITransactionLogApi transactionLogApi;

	@Autowired
	private BankMerchantAccountsRepository bankMerchantAccountRepository;

	@Autowired
	private PdfExporter pdfExporter;

	@Autowired
	private ICustomerProfileApi customerProfileApi;

	@Autowired
	private SettlementLogRepository settlementLogRepository;

	@Autowired
	private MerchantServiceRepository serviceRepository;

	@Autowired
	private AirlinesCommissionManagementRepository airlinesCommissionManagementRepository;

	@Autowired
	private MerchantRepository merchantRepository;

	@Override
	public TransactionDTO save(TransactionDTO transactionDto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TransactionDTO> getAllTransactions() {
		List<Transaction> transactionList = transactionRepository.getAllTransactions();
		if (!transactionList.isEmpty()) {
			return transactionConverter.convertToDtoList(transactionList);
		}
		return null;
	}

	@Override
	public PagablePage getAllTransactions(Integer currentpage, String fromDate, String toDate, String identifier,
			TransactionStatus status, String mobileNo, String bank, String serviceIdentifier, boolean forReport,
			String targetNo) {
		PagablePage pageble = new PagablePage();

		if (currentpage == null || currentpage == 0) {
			currentpage = 1;
		}

		int starting = ((currentpage * (int) PageInfo.pageList) - (int) PageInfo.pageList);
		entityManager = entityManager.getEntityManagerFactory().createEntityManager();
		Session session = entityManager.unwrap(Session.class);

		String startQuery = "select t from Transaction t";
		String countQueryString = "select count(t) from Transaction t";
		String endQuery = "";
		boolean valid = false;

		Date startd = null;
		Date endd = null;
		Customer customer = null;
		List<Customer> customersList = null;

		try {
			if (toDate != null && !(toDate.trim().equals(""))) {
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
			selfBranch = bankBranchRepository.findOne(AuthenticationUtil.getCurrentUser().getAssociatedId());
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
				endQuery += " t.created >=:startd ";
			} else {
				endQuery += " and t.created >=:startd ";
			}
			valid = true;

		}

		if (endd != null) {
			if (valid == false) {
				endQuery += " t.created <=:endd ";
			} else {
				endQuery += " and  t.created <=:endd ";
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
			boolean isRefStan = false;
			if (AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.Admin)) {
				if (transactionRepository.findByRefStan(identifier) != null) {
					if (valid == false) {
						endQuery += " t.refstan =:identifier ";
					} else {
						endQuery += " and t.refstan =:identifier ";
					}
					isRefStan = true;
				}
			}
			if (!isRefStan) {
				if (valid == false) {
					endQuery += " t.transactionIdentifier like  CONCAT(:identifier, '%')";
				} else {
					endQuery += " and t.transactionIdentifier like CONCAT(:identifier, '%') ";
				}
			}
			valid = true;
		}
		User ouser = null;
		List<User> ouserList = null;

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
					branch = bankBranchRepository.findOne(Long.parseLong(bank));
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

		if (serviceIdentifier != null && !(serviceIdentifier.trim().equals(""))) {
			if (serviceIdentifier.equals("fundTransfer")) {
				if (valid == false) {
					endQuery += " t.transactionType =:serviceIdentifier ";
				} else {
					endQuery += " and t.transactionType =:serviceIdentifier ";
				}
			} else {
				if (valid == false) {
					endQuery += " t.merchantManager.merchantsAndServices.merchantService.uniqueIdentifier =:serviceIdentifier ";
				} else {
					endQuery += " and t.merchantManager.merchantsAndServices.merchantService.uniqueIdentifier =:serviceIdentifier ";
				}
			}
			valid = true;
		}
		// edited by amrit
		if (targetNo != null && !(targetNo.trim().equals(""))) {

			if (valid == false) {
				endQuery += " t.destination =:mobileNo ";
			} else {
				endQuery += " and  t.destination =:mobileNo ";
			}
			valid = true;
		}
		String endQueryReference = endQuery;// this is the reference taken for
											// or operation
		if (mobileNo != null && !mobileNo.trim().equals("")) {
			if (AuthenticationUtil.getCurrentUser().getUserType() == UserType.Bank) {
				customer = customerRepository.findByMobileNumberAndBank(mobileNo, self);
			} else if (AuthenticationUtil.getCurrentUser().getUserType() == UserType.BankBranch) {
				customer = customerRepository.findByMobileNumberAndBankBranch(mobileNo, selfBranch);
			}

			else if (AuthenticationUtil.getCurrentUser().getUserType() == UserType.Admin) {
				// System.out.println("size of list from repo
				// :"+customerRepository.findByMobile(mobileNo).size());
				customersList = customerRepository.findByMobile(mobileNo);
				// System.out.println("size of the list from list
				// :"+customersList.size());
			}
			if (customer != null) {
				ouser = userRepository.findByUserTypeAndAssociatedId(UserType.Customer, customer.getId());
				if (ouser != null) {
					if (valid) {
						endQuery += " and t.originatingUser =:customer ";
					} else {

						endQuery += " t.originatingUser =:customer ";
					}
				}
				valid = true;

			}
			int count = 1;
			if (customersList != null) {
				for (int i = 0; i < customersList.size(); i++) {
					// this user exist is not needed but only used to double
					// comfirm

					User user = new User();
					user = userRepository.findByUserTypeAndAssociatedId(UserType.Customer,
							customersList.get(i).getId());
					if (user != null) {
						// System.out.println("there is user of the customers
						// !!");
						if (ouserList == null) {
							ouserList = new ArrayList<User>();
							ouserList.add(customersList.get(i).getUser());
							if (valid) {
								endQuery += " and t.originatingUser =:ouser" + count;
							} else {

								endQuery += " t.originatingUser =:ouser" + count;
							}
						} else {
							ouserList.add(customersList.get(i).getUser());
							if (endQueryReference != "" && endQueryReference != null) {
								endQuery = "(" + endQuery + ") or (" + endQueryReference
										+ " and t.originatingUser =:ouser" + count + " )";
							} else {
								endQuery = "(" + endQuery + ") or (" + endQueryReference + " t.originatingUser =:ouser"
										+ count + " )";

							}
						}
						count++;
					}
				}
				valid = true;
			}
		}
		// end edited

		if (valid) {
			startQuery += " where " + endQuery;
			countQueryString += " where " + endQuery;
		}

		startQuery += " order by t.id desc";

		// testing....

		// System.out.println("final query of the financial transaction is
		// ::::::: "+startQuery);
		// end testing

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

		if (targetNo != null && !(targetNo.trim().equals(""))) {
			selectQuery.setParameter("mobileNo", targetNo);
			countQuery.setParameter("mobileNo", targetNo);

		}
		if (mobileNo != null && !mobileNo.equals("")) {
			if (ouser != null) {
				selectQuery.setParameter("customer", ouser);
				countQuery.setParameter("customer", ouser);
			}
			if (ouserList != null) {
				for (int i = 1; i <= ouserList.size(); i++) {
					selectQuery.setParameter("ouser" + i, ouserList.get(i - 1));
					countQuery.setParameter("ouser" + i, ouserList.get(i - 1));
				}
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

		if (serviceIdentifier != null && !(serviceIdentifier.trim().equals(""))) {
			if (serviceIdentifier.equals("fundTransfer")) {
				selectQuery.setParameter("serviceIdentifier", TransactionType.Transfer);
				countQuery.setParameter("serviceIdentifier", TransactionType.Transfer);
			} else {
				selectQuery.setParameter("serviceIdentifier", serviceIdentifier);
				countQuery.setParameter("serviceIdentifier", serviceIdentifier);
			}
		}
		if (!forReport) {
			int totalpage = (int) Math.ceil(((Long) countQuery.iterate().next()).longValue() / PageInfo.pageList);
			List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(currentpage, totalpage, PageInfo.numberOfPage);
			selectQuery.setFirstResult(starting);
			selectQuery.setMaxResults((int) PageInfo.pageList);
			pageble.setPageList(pagesnumbers);
			pageble.setLastpage(totalpage);
		}
		List<Transaction> transactions = selectQuery.list();

		pageble.setCurrentPage(currentpage);
		if (!forReport) {
			pageble.setObject(transactionConverter.convertToDtoList(transactions));
		} else {
			pageble.setObject(transactionConverter.convertForReport(transactions));
		}

		return pageble;
	}

	@Override
	public List<TransactionDTO> getTransactionByCustomer(String uniqueId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionDTO getTransactionByRefNumber(String referenceNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaction createTransaction(TransactionDTO dto, MerchantManager merchantManger) {
		Transaction transaction = new Transaction();

		transaction.setTransactionIdentifier("" + System.currentTimeMillis());
		User originatingUser = userRepository.findOne(dto.getOriginatingUser());
		if (originatingUser != null) {
			transaction.setOriginatingUser(userRepository.findOne(dto.getOriginatingUser()));
		}
		User transactionOwner = userRepository.findOne(dto.getTransactionOwner());
		if (transactionOwner != null) {
			transaction.setOwner(userRepository.findOne(dto.getTransactionOwner()));
		}
		if (dto.getRequestDetail() != null) {
			transaction.setRequestDetail(dto.getRequestDetail());
		}
		if (dto.getResponseDetail() != null) {
			transaction.setResponseDetail(dto.getResponseDetail());
		}
		transaction.setDestination(dto.getDestination());
		transaction.setAmount(dto.getAmount());
		transaction.setTransactionType(dto.getTransactionType());
		transaction.setSourceAccount(dto.getSourceAccount());
		transaction.setDestinationAccount(dto.getDestinationAccount());
		transaction.setStatus(dto.getTransactionStatus());
		transaction.setMerchantManager(merchantManger);
		transactionRepository.save(transaction);

		// transactionMetaService.createTransactionMeta(transaction);

		return transaction;

	}

	@Override
	public Transaction createTransactionbank(TransactionDTO dto, MerchantManager merchantManger, Bank bank) {
		Transaction transaction = new Transaction();
		transaction.setBank(bank);
		Commission commission = commissionRepository.findByBankAndService(
				merchantManger.getMerchantsAndServices().getMerchant().getId(),
				merchantManger.getMerchantsAndServices().getMerchantService().getId(), Status.Active);
		if (commission != null) {
			if (commission.isSameForAll()) {
				CommissionAmount commissionAmount = commissionAmountRepository
						.findCommissionAmountForAllRangeByCommission(true, commission.getId(), Status.Active);
				BankCommission bankCommission = bankCommissionRepository
						.findByCommissionAmountAndStatus(commissionAmount.getId(), Status.Active, bank.getId());
				transaction.setBankCommission(bankCommission);
				transaction.setCommissionAmountRef(commissionAmount);
				transaction.setCommissionRef(commission);
				// transaction.setTotalCommission();
				Map<String, Double> commissionMap = transactionCore.getCommission(commissionAmount, bankCommission,
						dto.getAmount());
				transaction.setTotalCommission(commissionMap.get("commission"));
				transaction.setBankCommissionAmount(commissionMap.get("bankCommission"));
				transaction.setOperatorCommissionAmount(commissionMap.get("operatorCommission"));
				transaction.setChannelPartnerCommissionAmount(commissionMap.get("channelPartnerCommission"));
			} else {
				CommissionAmount commissionAmount = commissionAmountRepository
						.findCommisionAmountByAmountRange(dto.getAmount(), commission.getId(), Status.Active);
				BankCommission bankCommission = bankCommissionRepository
						.findByCommissionAmountAndStatus(commissionAmount.getId(), Status.Active, bank.getId());
				transaction.setBankCommission(bankCommission);
				transaction.setCommissionAmountRef(commissionAmount);
				transaction.setCommissionRef(commission);
				// transaction.setTotalCommission();
				Map<String, Double> commissionMap = transactionCore.getCommission(commissionAmount, bankCommission,
						dto.getAmount());
				transaction.setTotalCommission(commissionMap.get("commission"));
				transaction.setBankCommissionAmount(commissionMap.get("bankCommission"));
				transaction.setOperatorCommissionAmount(commissionMap.get("operatorCommission"));
				transaction.setChannelPartnerCommissionAmount(commissionMap.get("channelPartnerCommission"));
			}
		}

		transaction.setTransactionIdentifier("" + System.currentTimeMillis());
		User originatingUser = userRepository.findOne(dto.getOriginatingUser());
		if (originatingUser != null) {
			transaction.setOriginatingUser(userRepository.findOne(dto.getOriginatingUser()));
		}
		User transactionOwner = userRepository.findOne(dto.getTransactionOwner());
		if (transactionOwner != null) {
			transaction.setOwner(userRepository.findOne(dto.getTransactionOwner()));
		}
		if (dto.getRequestDetail() != null) {
			transaction.setRequestDetail(dto.getRequestDetail());
		}
		if (dto.getResponseDetail() != null) {
			transaction.setResponseDetail(dto.getResponseDetail());
		}
		transaction.setDestination(dto.getDestination());
		transaction.setAmount(dto.getAmount());
		transaction.setTransactionType(dto.getTransactionType());
		transaction.setSourceAccount(dto.getSourceAccount());
		transaction.setDestinationAccount(dto.getDestinationAccount());
		transaction.setStatus(dto.getTransactionStatus());
		transaction.setMerchantManager(merchantManger);
		transaction = transactionRepository.save(transaction);
		createTransactionLog(transaction, "Transaction Created", UserType.Bank, transaction.getBank().getSwiftCode());
		return transaction;
	}

	@Override
	public TransactionDTO editTransaction(TransactionDTO dto) {
		Transaction transaction = transactionRepository.findOne(dto.getId());
		dto.setTransactionIdentifier(transaction.getTransactionIdentifier());
		transaction.setStatus(dto.getTransactionStatus());
		transaction.setSettlementStatus(SettlementStatus.FAILURE);
		if (dto.getTransactionOwner() > 0) {
			User transactionOwner = userRepository.findOne(dto.getTransactionOwner());
			if (transactionOwner != null) {
				transaction.setOwner(userRepository.findOne(dto.getTransactionOwner()));
			}
		}
		if (dto.getSourceAccount() != null) {
			if (!dto.getSourceAccount().trim().equals("")) {
				transaction.setSourceAccount(dto.getSourceAccount());
			}
		}
		if (dto.getDestinationAccount() != null) {
			if (!dto.getDestinationAccount().trim().equals("dto")) {
				transaction.setDestinationAccount(dto.getDestinationAccount());
			}
		}
		if (dto.getResponseDetail() != null) {
			transaction.setResponseDetail(dto.getResponseDetail());
		}
		if (dto.getMerchantRefstan() != null) {
			transaction.setRefstan(dto.getMerchantRefstan());
		}

		transactionRepository.save(transaction);
		// transactionMetaService.createTransactionMeta(transaction);

		return dto;

	}

	@Override
	public TransactionDTO getTransactionByTransactionIdentifier(String identifier) {

		return transactionConverter
				.convertToDto(transactionRepository.getTransactionByTransactionIdentifier(identifier));

	}

	@Override
	public TransactionDTO editAmbiguousTransaction(TransactionDTO dto) {
		Transaction transaction = transactionRepository.findOne(dto.getId());
		dto.setTransactionIdentifier(transaction.getTransactionIdentifier());

		transaction.setStatus(dto.getTransactionStatus());

		if (dto.getResponseDetail() != null) {
			transaction.setResponseDetail(dto.getResponseDetail());
		}

		transactionRepository.save(transaction);

		return dto;
	}

	@Override
	public Transaction createTransactionBankBranch(TransactionDTO dto, MerchantManager manager, BankBranch bankBranch) {
		Transaction transaction = new Transaction();
		transaction.setBankBranch(bankBranch);
		transaction.setBank(bankBranch.getBank());
		Commission commission = commissionRepository.findByBankAndService(
				manager.getMerchantsAndServices().getMerchant().getId(),
				manager.getMerchantsAndServices().getMerchantService().getId(), Status.Active);
		if (commission != null) {
			if (commission.isSameForAll()) {
				CommissionAmount commissionAmount = commissionAmountRepository
						.findCommissionAmountForAllRangeByCommission(true, commission.getId(), Status.Active);
				BankCommission bankCommission = bankCommissionRepository.findByCommissionAmountAndStatus(
						commissionAmount.getId(), Status.Active, bankBranch.getBank().getId());
				transaction.setBankCommission(bankCommission);
				transaction.setCommissionAmountRef(commissionAmount);
				transaction.setCommissionRef(commission);
				// transaction.setTotalCommission();
				Map<String, Double> commissionMap = transactionCore.getCommission(commissionAmount, bankCommission,
						dto.getAmount());
				transaction.setTotalCommission(commissionMap.get("commission"));
				transaction.setBankCommissionAmount(commissionMap.get("bankCommission"));
				transaction.setOperatorCommissionAmount(commissionMap.get("operatorCommission"));
				transaction.setChannelPartnerCommissionAmount(commissionMap.get("channelPartnerCommission"));
			} else {
				CommissionAmount commissionAmount = commissionAmountRepository
						.findCommisionAmountByAmountRange(dto.getAmount(), commission.getId(), Status.Active);
				BankCommission bankCommission = bankCommissionRepository.findByCommissionAmountAndStatus(
						commissionAmount.getId(), Status.Active, bankBranch.getBank().getId());
				transaction.setBankCommission(bankCommission);
				transaction.setCommissionAmountRef(commissionAmount);
				transaction.setCommissionRef(commission);
				// transaction.setTotalCommission();
				Map<String, Double> commissionMap = transactionCore.getCommission(commissionAmount, bankCommission,
						dto.getAmount());
				transaction.setTotalCommission(commissionMap.get("commission"));
				transaction.setBankCommissionAmount(commissionMap.get("bankCommission"));
				transaction.setOperatorCommissionAmount(commissionMap.get("operatorCommission"));
				transaction.setChannelPartnerCommissionAmount(commissionMap.get("channelPartnerCommission"));
			}
		}
		transaction.setTransactionIdentifier("" + System.currentTimeMillis());

		User originatingUser = userRepository.findOne(dto.getOriginatingUser());
		User transactionOwner = userRepository.findOne(dto.getTransactionOwner());
		if (originatingUser != null) {
			transaction.setOriginatingUser(userRepository.findOne(dto.getOriginatingUser()));
		}

		if (transactionOwner != null) {
			transaction.setOwner(userRepository.findOne(dto.getTransactionOwner()));
		}
		if (dto.getRequestDetail() != null) {
			transaction.setRequestDetail(dto.getRequestDetail());
		}
		if (dto.getResponseDetail() != null) {
			transaction.setResponseDetail(dto.getResponseDetail());
		}
		transaction.setDestination(dto.getDestination());
		transaction.setAmount(dto.getAmount());
		transaction.setTransactionType(dto.getTransactionType());
		transaction.setSourceAccount(dto.getSourceAccount());
		transaction.setDestinationAccount(dto.getDestinationAccount());
		transaction.setStatus(dto.getTransactionStatus());
		transaction.setMerchantManager(manager);
		transaction = transactionRepository.save(transaction);
		createTransactionLog(transaction, "Transaction Created", UserType.BankBranch,
				"" + transaction.getBankBranch().getId());
		return transaction;
	}

	@Override
	public Transaction createTransactionCustomer(TransactionDTO dto, MerchantManager manager, BankBranch bankBranch,
			CustomerBankAccount customerBankAccount) {
		Transaction transaction = new Transaction();
		transaction.setBankBranch(bankBranch);
		transaction.setBank(bankBranch.getBank());
		Commission commission = commissionRepository.findByBankAndService(
				manager.getMerchantsAndServices().getMerchant().getId(),
				manager.getMerchantsAndServices().getMerchantService().getId(), Status.Active);
		if (commission != null) {
			if (commission.isSameForAll()) {
				CommissionAmount commissionAmount = commissionAmountRepository
						.findCommissionAmountForAllRangeByCommission(true, commission.getId(), Status.Active);
				BankCommission bankCommission = bankCommissionRepository.findByCommissionAmountAndStatus(
						commissionAmount.getId(), Status.Active, bankBranch.getBank().getId());
				transaction.setBankCommission(bankCommission);
				transaction.setCommissionAmountRef(commissionAmount);
				transaction.setCommissionRef(commission);

				Map<String, Double> commissionMap;
				if (manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
						.equals(StringConstants.AIRLINES_BOOKING)) {

					AirlinesCommissionManagement airlinesCommission = airlinesCommissionManagementRepository
							.findByCustomer(customerBankAccount.getCustomer());
					Double agencyCommmission;
					if (airlinesCommission != null) {
						agencyCommmission = airlinesCommission.getCommissionAmount();
					} else {
						agencyCommmission = 0.0;
					}
					transaction.setAgencyCommission(agencyCommmission);
					commissionMap = transactionCore.getCommission(commissionAmount, bankCommission, agencyCommmission);
				} else {
					commissionMap = transactionCore.getCommission(commissionAmount, bankCommission, dto.getAmount());
				}

				// transaction.setTotalCommission();
				if (commissionMap.get("commission") != null) {
					transaction.setTotalCommission(commissionMap.get("commission"));
				} else {
					transaction.setTotalCommission(0.0);
				}

				if (commissionMap.get("bankCommission") != null) {
					transaction.setBankCommissionAmount(commissionMap.get("bankCommission"));
				} else {
					transaction.setBankCommissionAmount(0.0);
				}

				if (commissionMap.get("operatorCommission") != null) {
					transaction.setOperatorCommissionAmount(commissionMap.get("operatorCommission"));
				} else {
					transaction.setOperatorCommissionAmount(0.0);
				}
				if (commissionMap.get("cashBack") != null) {
					transaction.setCashBack(commissionMap.get("cashBack"));
				} else {
					transaction.setCashBack(0.0);
				}

				if (commissionMap.get("channelPartnerCommission") != null) {
					transaction.setChannelPartnerCommissionAmount(commissionMap.get("channelPartnerCommission"));
				} else {
					transaction.setChannelPartnerCommissionAmount(0.0);
				}

			} else {
				CommissionAmount commissionAmount = commissionAmountRepository
						.findCommisionAmountByAmountRange(dto.getAmount(), commission.getId(), Status.Active);
				BankCommission bankCommission = bankCommissionRepository.findByCommissionAmountAndStatus(
						commissionAmount.getId(), Status.Active, bankBranch.getBank().getId());
				transaction.setBankCommission(bankCommission);
				transaction.setCommissionAmountRef(commissionAmount);
				transaction.setCommissionRef(commission);
				Map<String, Double> commissionMap = transactionCore.getCommission(commissionAmount, bankCommission,
						dto.getAmount());
				transaction.setTotalCommission(commissionMap.get("commission"));
				transaction.setBankCommissionAmount(commissionMap.get("bankCommission"));
				transaction.setOperatorCommissionAmount(commissionMap.get("operatorCommission"));
				transaction.setChannelPartnerCommissionAmount(commissionMap.get("channelPartnerCommission"));
			}
		}

		transaction.setSourceBankAccount(dto.getSourceBankAccount());

		transaction.setTransactionIdentifier("" + System.currentTimeMillis());
		Customer customer = customerBankAccount.getCustomer();
		User originatingUser = customer.getUser();
		if (originatingUser != null) {
			transaction.setOriginatingUser(originatingUser);
		}
		User transactionOwner = userRepository.findOne(dto.getTransactionOwner());
		if (transactionOwner != null) {
			transaction.setOwner(userRepository.findOne(dto.getTransactionOwner()));
		}
		if (dto.getRequestDetail() != null) {
			transaction.setRequestDetail(dto.getRequestDetail());
		}
		if (dto.getResponseDetail() != null) {
			transaction.setResponseDetail(dto.getResponseDetail());
		}
		transaction.setDestination(dto.getDestination());
		transaction.setAmount(dto.getAmount());
		transaction.setTransactionType(dto.getTransactionType());
		transaction.setSourceAccount(dto.getSourceAccount());
		transaction.setDestinationAccount(dto.getDestinationAccount());
		transaction.setStatus(dto.getTransactionStatus());
		transaction.setMerchantManager(manager);
		transaction = transactionRepository.save(transaction);
		String customerId = "";
		if (customerBankAccount != null) {
			customerId = customerBankAccount.getCustomer().getUniqueId();
		}
		createTransactionLog(transaction, "Transaction Created ", UserType.Customer, customerId);
		return transaction;
	}

	@Override
	public String fundTransferTransacion(String sourceAccount, String destinationAccount, Double amount,
			TransactionType transactionType, long customerId) throws Exception {

		CustomerBankAccount customerBankAccount = customerBankAccountRepository
				.findCustomerAccountByAccountNumber(sourceAccount, customerId);
		Transaction transaction = new Transaction();
		transaction.setBankBranch(customerBankAccount.getBranch());
		transaction.setBank(customerBankAccount.getBranch().getBank());
		transaction.setTransactionIdentifier("" + System.currentTimeMillis());
		Customer customer = customerBankAccount.getCustomer();
		User originatingUser = customer.getUser();
		if (originatingUser != null) {
			transaction.setOriginatingUser(originatingUser);
		}
		User transactionOwner = customerBankAccount.getCustomer().getUser();
		if (transactionOwner != null) {
			transaction.setOwner(transactionOwner);

		}

		transaction.setDestination(destinationAccount);
		transaction.setAmount(amount);
		transaction.setSourceBankAccount(sourceAccount);
		transaction.setTransactionType(transactionType);
		transaction.setSourceAccount(sourceAccount);
		transaction.setStatus(TransactionStatus.Pending);

		transaction = transactionRepository.save(transaction);
		String customerUniqueId = "";
		if (customerBankAccount != null) {
			customerUniqueId = customerBankAccount.getCustomer().getUniqueId();
		}
		createTransactionLog(transaction, "Transaction Created ", UserType.Customer, customerUniqueId);
		HashMap<String, String> response = new HashMap<>();
		if (StringConstants.IS_PRODUCTION) {
			response = customerApi.accountTransfer(sourceAccount, destinationAccount, amount, transaction.getBank());
		} else
			response.put("status", "failure");

		if (response.get("status").equals("success")) {
			transaction.setStatus(TransactionStatus.Complete);
			transaction = transactionRepository.save(transaction);
			customerProfileApi.updateCustomerTransactionLog(customerBankAccount, amount);
			createAccountPosting(sourceAccount, destinationAccount, amount, transaction);
			return "success";
		} else if (response.get("status").equals("failure")) {
			transaction.setStatus(TransactionStatus.CancelledWithRefund);
			transaction = transactionRepository.save(transaction);
			return "failure";
		}

		return null;
	}

	@Override
	public HashMap<String, String> cardlessFundTransferTransacion(long customerBankAccountId, String destinationAccount,
			Double amount, TransactionType transactionType) throws Exception {
		CustomerBankAccount customerBankAccount = customerBankAccountRepository.findOne(customerBankAccountId);
		Transaction transaction = new Transaction();
		transaction.setBankBranch(customerBankAccount.getBranch());
		transaction.setBank(customerBankAccount.getBranch().getBank());
		transaction.setTransactionIdentifier("" + System.currentTimeMillis());
		Customer customer = customerBankAccount.getCustomer();
		User originatingUser = customer.getUser();
		if (originatingUser != null) {
			transaction.setOriginatingUser(originatingUser);
		}
		User transactionOwner = customerBankAccount.getCustomer().getUser();
		if (transactionOwner != null) {
			transaction.setOwner(transactionOwner);

		}

		transaction.setDestination(destinationAccount);
		transaction.setAmount(amount);
		transaction.setSourceBankAccount(customerBankAccount.getAccountNumber());
		transaction.setTransactionType(transactionType);
		transaction.setSourceAccount(customerBankAccount.getAccountNumber());
		transaction.setStatus(TransactionStatus.Pending);

		transaction = transactionRepository.save(transaction);
		String customerId = "";
		if (customerBankAccount != null) {
			customerId = customerBankAccount.getCustomer().getUniqueId();
		}
		createTransactionLog(transaction, "Transaction Created ", UserType.Customer, customerId);
		HashMap<String, String> response = new HashMap<>();
		if (StringConstants.IS_PRODUCTION) {
			response = customerApi.accountTransfer(customerBankAccount.getAccountNumber(), destinationAccount, amount,
					transaction.getBank());
		} else
			response.put("status", "failure");

		if (response.get("status").equals("success")) {

			transaction.setStatus(TransactionStatus.Complete);
			transaction = transactionRepository.save(transaction);
			// customerProfileApi.updateCustomerTransactionLog(customerBankAccount,
			// amount);
			createAccountPosting(customerBankAccount.getAccountNumber(), destinationAccount, amount, transaction);
			response.put("transactionId", transaction.getTransactionIdentifier());
			return response;
		} else if (response.get("status").equals("failure")) {
			transaction.setStatus(TransactionStatus.CancelledWithRefund);
			transaction = transactionRepository.save(transaction);
			response.put("transactionId", transaction.getTransactionIdentifier());
			return response;
		}

		return null;
	}

	public void createTransactionLog(Transaction transaction, String remarks, UserType transactionByUser,
			String transactionBy) {
		TransactionLog transactionLog = new TransactionLog();
		transactionLog.setRemarks(remarks);
		transactionLog.setTransactionByUser(transactionByUser);
		transactionLog.setTransaction(transaction);
		transactionLog.setStatus(transaction.getStatus());
		transactionLog.setTransactionBy(transactionBy);

		try {
			transactionLogRepository.save(transactionLog);
		} catch (Exception e) {

		}
	}

	@Override
	public List<TransactionDTO> filterTransaction(String fromDate, String toDate, String identifier, String status,
			String mobileNo, String bankId) {
		List<Transaction> transactionList = new ArrayList<>();

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		entityManager = entityManager.getEntityManagerFactory().createEntityManager();

		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Transaction.class);

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
			criteria.add(Restrictions.eq("status", TransactionStatus.valueOf(status)));
		}

		if (identifier != null && !(identifier.trim().equals(""))) {
			criteria.add(Restrictions.eq("transactionIdentifier", identifier));
		}

		if (mobileNo != null && !(mobileNo.trim().equals(""))) {
			criteria.add(Restrictions.eq("destination", mobileNo));
		}

		/*
		 * if (mobileNo != null && !(mobileNo.trim().equals(""))) {
		 * criteria.add(Restrictions.eq("destination", mobileNo)); Customer
		 * customer = customerRepository.getCustomerByMobileNumber(mobileNo); if
		 * (customer != null) { criteria.add(Restrictions.eq("originatingUser",
		 * customer.getUser())); } }
		 */

		if (bankId != null && !(bankId.trim().equals("")) && (isNumber(bankId))) {
			Bank bank = bankRepository.findOne(Long.parseLong(bankId));
			criteria.add(Restrictions.eq("bank", bank));
		}

		criteria.addOrder(Order.desc("id"));

		transactionList = criteria.list();

		return transactionConverter.convertToDtoList(transactionList);

	}

	private boolean isNumber(String string) {
		try {
			Long.parseLong(string);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * @Override public List<TransactionDTO> getByPageNumber(int pageNumber) {
	 * entityManager =
	 * entityManager.getEntityManagerFactory().createEntityManager();
	 * 
	 * Query query = (Query) entityManager.createQuery("From Transaction"); int
	 * pageSize = 10; query.setFirstResult((pageNumber - 1) * pageSize);
	 * query.setMaxResults(pageSize); List<Transaction> transactionList =
	 * ((javax.persistence.Query) query).getResultList(); ======= Query query =
	 * entityManager.createQuery("From Transaction"); int pageSize = 10;
	 * query.setFirstResult((pageNumber - 1) * pageSize);
	 * query.setMaxResults(pageSize); List<Transaction> transactionList =
	 * query.getResultList(); >>>>>>> 43a66fd8472ac580d6033478de19349a630eb8c3
	 * return transactionConverter.convertToDtoList(transactionList); }
	 */

	@Override
	public List<TransactionDTO> getTransactionBetweenDate(Date fromDate, Date toDate) {
		List<Transaction> transactionList = transactionRepository.findTransactionBetweenDates(fromDate, toDate);
		if (transactionList != null) {
			transactionConverter.convertToDtoList(transactionList);
		}
		return null;
	}

	@Override
	public List<TransactionDTO> getTransactionByBank(Long bankId) {
		List<Transaction> transactionList = transactionRepository.getTransactionByBank(bankId);
		if (transactionList != null) {
			return transactionConverter.convertToDtoList(transactionList);
		}
		return null;
	}

	@Override
	public Long countTransactionByServiceAndStatus(String uniqueIdentifier, TransactionStatus status) {
		User currentUser = AuthenticationUtil.getCurrentUser();
		if (currentUser.getUserType() == UserType.Bank) {
			return transactionRepository.countTransactionByServiceAndBankAndStatus(uniqueIdentifier,
					currentUser.getAssociatedId(), status);
		} else if (currentUser.getUserType() == UserType.Admin) {
			return transactionRepository.countTransactionByServiceAndStatus(uniqueIdentifier, status);
		} else if (currentUser.getUserType() == UserType.BankBranch) {
			return transactionRepository.countTransactionByServiceAndBranchAndStatus(uniqueIdentifier,
					currentUser.getAssociatedId(), status);
		}
		return 0L;
	}

	public void createAccountPosting(String fromAccountNumber, String toAccountNumber, double amount,
			Transaction transaction) {
		String remarks = StringConstants.ACCOUNT_POSTING_FUND_TRANSFER;
		remarks = remarks.replace("{amount}", "" + amount);
		remarks = remarks.replace("{accountNumber1}", fromAccountNumber);
		remarks = remarks.replace("{accountNumber2}", toAccountNumber);
		AccountPosting accountPosting = new AccountPosting();
		accountPosting.setAmount(amount);
		accountPosting.setFromAccountNumber(fromAccountNumber);
		accountPosting.setToAccountNumber(toAccountNumber);
		accountPosting.setRemarks(remarks);
		accountPosting.setTransaction(transaction);
		accountPostingRepository.save(accountPosting);

	}

	@Override
	public List<TransactionDTO> getTransactionByStatus(TransactionStatus transactionStatus) {
		List<Transaction> transactionList = transactionRepository.findByStatus(transactionStatus);
		if (transactionList != null) {
			return transactionConverter.convertToDtoList(transactionList);
		}
		return null;
	}

	@Override
	public void manualUpdateAmbiguousTransaction(String transactionIdentifier, boolean status) throws Exception {
		Transaction transaction = transactionRepository.getTransactionByTransactionIdentifier(transactionIdentifier);
		Merchant merchant = transaction.getMerchantManager().getMerchantsAndServices().getMerchant();
		MerchantManager manager = transaction.getMerchantManager();
		HashMap<String, String> hashResponse = ConvertUtil.mapToHashMapString(transaction.getResponseDetail());
		HashMap<String, String> hashRequest = ConvertUtil.mapToHashMapString(transaction.getResponseDetail());
		String serviceTo = hashRequest.get("serviceTo");
		BankAccountSettings bankAccountSettings = bankAccountSettingsRepository
				.getBankAccountSettingsByBankCodeAndStatus(transaction.getBank().getSwiftCode(), Status.Active);
		String remarksTwo = serviceTo;
		String remarksOne = transaction.getTransactionIdentifier();
		String desc1 = manager.getMerchantsAndServices().getMerchantService().getService();
		if (status) {
			if (transaction.getOriginatingUser().getUserType() == UserType.Bank) {
				String destinationAccountNumber = bankAccountSettings.getOperatorAccountNumber();
				String sourceAccountNumber = bankAccountSettings.getBankParkingAccountNumber();

				customerApi.fundTransfer(sourceAccountNumber, destinationAccountNumber,
						transaction.getOperatorCommissionAmount(), transaction.getBank());
				createAccountPosting(sourceAccountNumber, destinationAccountNumber,
						transaction.getOperatorCommissionAmount(), transaction);
				destinationAccountNumber = bankAccountSettings.getChannelPartnerAccountNumber();
				customerApi.fundTransfer(sourceAccountNumber, destinationAccountNumber,
						transaction.getChannelPartnerCommissionAmount(), transaction.getBank());
				destinationAccountNumber = bankAccountSettings.getBankPoolAccountNumber();
				customerApi.fundTransfer(sourceAccountNumber, destinationAccountNumber, transaction.getTotalCommission()
						- (transaction.getOperatorCommissionAmount() + transaction.getChannelPartnerCommissionAmount()),
						transaction.getBank());
				createAccountPosting(sourceAccountNumber, destinationAccountNumber, transaction.getTotalCommission()
						- (transaction.getOperatorCommissionAmount() + transaction.getChannelPartnerCommissionAmount()),
						transaction);

				// now to merchant
				String merchantBankAccount = bankMerchantAccountRepository.findMerchantAccountByBankAndMerchantStatus(
						transaction.getBank().getId(), merchant.getId(), Status.Active);
				if (merchantBankAccount != null) {
					customerApi.fundTransfer(sourceAccountNumber, merchantBankAccount,
							transaction.getAmount() - transaction.getTotalCommission(), transaction.getBank());
					createAccountPosting(sourceAccountNumber, destinationAccountNumber,
							transaction.getChannelPartnerCommissionAmount(), transaction);
				}
				Account fromAccount = accountRepository
						.findAccountByAccountNumber(transaction.getBank().getAccountNumber());
				transactionCore.transactionParkingToMerchant(transaction.getId(), manager, hashResponse);
				transactionCore.ledgerParkingToMerchant(transaction.getAmount() - transaction.getTotalCommission(),
						manager, transactionIdentifier);
				// transactionCore.accountParkingToService(amount, service);
				Account toAccount = accountRepository.findAccountByAccountNumber(
						transaction.getMerchantManager().getMerchantsAndServices().getMerchant().getAccountNumber());
				commissionCore.transferBalanceFromPoolToMerchant(transaction, toAccount,
						transaction.getAmount() - transaction.getTotalCommission());

				commissionCore.allocateCommissions(transaction);

			} else if (transaction.getOriginatingUser().getUserType() == UserType.BankBranch) {
				String destinationAccountNumber = bankAccountSettings.getOperatorAccountNumber();
				// need a change here
				String sourceAccountNumber = bankAccountSettings.getBankParkingAccountNumber(); 
				customerApi.fundTransfer(sourceAccountNumber, destinationAccountNumber,
						transaction.getOperatorCommissionAmount(), transaction.getBank());
				createAccountPosting(sourceAccountNumber, destinationAccountNumber,
						transaction.getOperatorCommissionAmount(), transaction);
				// channel partner
				destinationAccountNumber = bankAccountSettings.getChannelPartnerAccountNumber();

				customerApi.fundTransfer(sourceAccountNumber, destinationAccountNumber,
						transaction.getChannelPartnerCommissionAmount(), transaction.getBank());

				// sourceAccountNumber =
				// bankAccountSettings.getBankParkingAccountNumber();

				// TODO NEEED TO CHECK THIS
				destinationAccountNumber = bankAccountSettings.getBankPoolAccountNumber();
				customerApi.fundTransfer(sourceAccountNumber, destinationAccountNumber, transaction.getTotalCommission()
						- (transaction.getOperatorCommissionAmount() + transaction.getChannelPartnerCommissionAmount()),
						transaction.getBank());
				createAccountPosting(sourceAccountNumber, destinationAccountNumber, transaction.getTotalCommission()
						- (transaction.getOperatorCommissionAmount() + transaction.getChannelPartnerCommissionAmount()),
						transaction);

				// now to merchant
				String merchantBankAccount = bankMerchantAccountRepository.findMerchantAccountByBankAndMerchantStatus(
						transaction.getBank().getId(), merchant.getId(), Status.Active);
				if (merchantBankAccount != null) {
					customerApi.fundTransfer(sourceAccountNumber, merchantBankAccount,
							transaction.getAmount() - transaction.getTotalCommission(), transaction.getBank());
					createAccountPosting(sourceAccountNumber, destinationAccountNumber,
							transaction.getChannelPartnerCommissionAmount(), transaction);
				}

				transactionCore.ledgerParkingToMerchant(transaction.getAmount() - transaction.getTotalCommission(),
						manager, transactionIdentifier);
				// transactionCore.accountParkingToService(amount, service);
				Account toAccount = accountRepository
						.findAccountByAccountNumber(manager.getMerchantsAndServices().getMerchant().getAccountNumber());
				commissionCore.transferBalanceFromPoolToMerchant(transaction, toAccount,
						transaction.getAmount() - transaction.getTotalCommission());

				Account fromAccount = accountRepository
						.findAccountByAccountNumber(transaction.getBank().getAccountNumber());
				commissionCore.transferBalanceFromPoolToMerchant(transaction, toAccount,
						transaction.getAmount() - transaction.getTotalCommission());
				commissionCore.allocateCommissions(transaction);

			} else if (transaction.getOriginatingUser().getUserType() == UserType.Customer) {

				String destinationAccountNumber = bankAccountSettings.getOperatorAccountNumber();
				String sourceAccountNumber = bankAccountSettings.getOperatorAccountNumber(); // need
																								// to
				String remarksCommission = "Comm " + remarksTwo;
				customerApi.fundTransfer(sourceAccountNumber, destinationAccountNumber,
						transaction.getOperatorCommissionAmount(), transaction.getBank(), remarksOne, remarksTwo,
						desc1);
				createAccountPosting(sourceAccountNumber, destinationAccountNumber,
						transaction.getOperatorCommissionAmount(), transaction);
				// channel partner
				destinationAccountNumber = bankAccountSettings.getChannelPartnerAccountNumber();
				customerApi.fundTransfer(sourceAccountNumber, destinationAccountNumber,
						transaction.getChannelPartnerCommissionAmount(), transaction.getBank(), remarksOne, remarksTwo,
						desc1);
				createAccountPosting(sourceAccountNumber, destinationAccountNumber,
						transaction.getChannelPartnerCommissionAmount(), transaction);

				// now to bank
				destinationAccountNumber = bankAccountSettings.getBankPoolAccountNumber();
				sourceAccountNumber = bankAccountSettings.getBankParkingAccountNumber();
				customerApi.fundTransfer(sourceAccountNumber, destinationAccountNumber, transaction.getTotalCommission()
						- (transaction.getOperatorCommissionAmount() + transaction.getChannelPartnerCommissionAmount()),
						transaction.getBank(), remarksOne, remarksTwo, desc1);
				createAccountPosting(sourceAccountNumber, destinationAccountNumber, transaction.getTotalCommission()
						- (transaction.getOperatorCommissionAmount() + transaction.getChannelPartnerCommissionAmount()),
						transaction);

				// now to merchant
				String merchantBankAccount = bankMerchantAccountRepository.findMerchantAccountByBankAndMerchantStatus(
						transaction.getBank().getId(), merchant.getId(), Status.Active);
				if (merchantBankAccount != null) {
					customerApi.fundTransfer(sourceAccountNumber, merchantBankAccount,
							transaction.getAmount() - transaction.getTotalCommission(), transaction.getBank());
					createAccountPosting(sourceAccountNumber, destinationAccountNumber,
							transaction.getChannelPartnerCommissionAmount(), transaction);
				}

				String txnIdentifier = transactionCore.transactionParkingToMerchant(transaction.getId(), manager,
						hashResponse);
				transactionCore.ledgerParkingToMerchant(transaction.getAmount() - transaction.getTotalCommission(),
						manager, serviceTo);
				// transactionCore.accountParkingToService(amount, service);
				// commissionCore.updateBalanceToBank(transaction);
				Account toAccount = accountRepository.findAccountByAccountNumber(
						transaction.getMerchantManager().getMerchantsAndServices().getMerchant().getAccountNumber());
				commissionCore.transferBalanceFromPoolToMerchant(transaction, toAccount,
						transaction.getAmount() - transaction.getTotalCommission());
				commissionCore.allocateCommissions(transaction);
			}

		} else {
			if (transaction.getOriginatingUser().getUserType() == UserType.Bank
					|| transaction.getOriginatingUser().getUserType() == UserType.BankBranch) {
				customerApi.fundTransfer(bankAccountSettings.getBankParkingAccountNumber(),
						bankAccountSettings.getBankPoolAccountNumber(), transaction.getAmount(), transaction.getBank());
				createTransactionLog(transaction, "Transaction Failed", UserType.Bank,
						"" + transaction.getBank().getId());
				String txnIdentifier = transactionCore.reverseTransactionParkingToBank(transaction.getId(),
						transaction.getBank(), hashResponse);
				// transactionCore.reverseAccountParkingToBank(amount, bank);
				Account toAccount = accountRepository
						.findAccountByAccountNumber(transaction.getBank().getAccountNumber());
				commissionCore.transferBalanceFromPool(transaction, toAccount);
				transactionCore.reverseLedgerParkingToBank(transaction.getAmount(), transaction.getBank(),
						transactionIdentifier);

			} else if (transaction.getOriginatingUser().getUserType() == UserType.Customer) {
				remarksTwo = remarksTwo + "Refund";
				CustomerBankAccount customerBankAccount = customerBankAccountRepository
						.findCustomerAccountByAccountNumber(transaction.getSourceBankAccount(),
								transaction.getOriginatingUser().getAssociatedId());
				HashMap<String, String> isoResponse = customerApi.fundTransfer(
						bankAccountSettings.getBankParkingAccountNumber(), transaction.getSourceBankAccount(),
						transaction.getAmount(), transaction.getBank(), remarksOne, remarksTwo, desc1);
				transactionLogApi.createTransactionLog(transaction, "Transaction Failed", UserType.Customer,
						customerBankAccount.getCustomer().getUniqueId());
				transactionCore.reverseTransactionParkingToCustomer(transaction.getId(), customerBankAccount,
						hashResponse);
				Account toAccount = accountRepository
						.findAccountByAccountNumber(transaction.getBank().getAccountNumber());
				commissionCore.transferBalanceFromPool(transaction, toAccount);
				createAccountPosting(bankAccountSettings.getBankParkingAccountNumber(),
						customerBankAccount.getAccountNumber(), transaction.getAmount(), transaction);

			}
		}

	}

	@Override
	public String checkAmbiguousTransactionStatus(String transactionIdentifier) throws Exception {
		Transaction transaction = transactionRepository.getTransactionByTransactionIdentifier(transactionIdentifier);
		Merchant merchant = transaction.getMerchantManager().getMerchantsAndServices().getMerchant();

		MerchantService service = transaction.getMerchantManager().getMerchantsAndServices().getMerchantService();
		HashMap<String, String> hashResponse = ConvertUtil.mapToHashMapString(transaction.getResponseDetail());
		HashMap<String, String> hashRequest = ConvertUtil.mapToHashMapString(transaction.getResponseDetail());
		HashMap<String, String> hash = new HashMap<>();
		HashMap<String, String> satusResponseHash = new HashMap<>();
		hash.put("username", merchant.getApiUsername());
		hash.put("password", merchant.getApiPassWord());
		hash.put("apiurl", merchant.getMerchantApiUrl());
		switch (transaction.getTransactionType()) {
		case Mpayment:
			switch (merchant.getName()) {
			case "PAYPOINT":
				try {
					hash.put("refStan", hashResponse.get("RefStan"));
					hash.put("billNumber", hashResponse.get("Bill Number"));
					satusResponseHash = payPointService.getTransaction(hash);
					if (satusResponseHash.get("status").equals("success")) {
						manualUpdateAmbiguousTransaction(transaction.getTransactionIdentifier(), true);
						return "success";
					} else if (satusResponseHash.get("status").equals("failure")) {
						manualUpdateAmbiguousTransaction(transaction.getTransactionIdentifier(), false);
						return "failure";
					} else {
						return "unknown";
					}
				} catch (JAXBException e) {
					e.printStackTrace();
					return "notAvailable";
				}

			default:
				return "notAvailable";

			}

		case Transfer:
			return "notAvailable";

		default:
			return "notAvailable";

		}
	}

	@Override
	public String checkProblematicTransaction(String transactionIdentifier) {
		Transaction transaction = transactionRepository.getTransactionByTransactionIdentifier(transactionIdentifier);
		Merchant merchant = transaction.getMerchantManager().getMerchantsAndServices().getMerchant();

		MerchantService service = transaction.getMerchantManager().getMerchantsAndServices().getMerchantService();
		HashMap<String, String> hashResponse = ConvertUtil.mapToHashMapString(transaction.getResponseDetail());
		HashMap<String, String> hashRequest = ConvertUtil.mapToHashMapString(transaction.getResponseDetail());
		HashMap<String, String> hash = new HashMap<>();
		HashMap<String, String> satusResponseHash = new HashMap<>();
		hash.put("username", merchant.getApiUsername());
		hash.put("password", merchant.getApiPassWord());
		hash.put("apiurl", merchant.getMerchantApiUrl());
		switch (transaction.getTransactionType()) {
		case Mpayment:
			switch (merchant.getName()) {
			case "PAYPOINT":
				try {
					hash.put("refStan", hashResponse.get("RefStan"));
					hash.put("billNumber", hashResponse.get("Bill Number"));
					satusResponseHash = payPointService.getTransaction(hash);
					if (satusResponseHash.get("status").equals("success")) {
						return "success";
					} else if (satusResponseHash.get("status").equals("failure")) {
						return "failure";
					} else {
						return "unknown";
					}
				} catch (JAXBException e) {
					e.printStackTrace();
					return "notAvailable";
				}

			default:
				return "notAvailable";

			}

		case Transfer:
			return "notAvailable";

		default:
			return "notAvailable";

		}
	}

	@Override
	public String exportFinancialTransaction(String path) {
		List<Transaction> transactionList = new ArrayList<>();
		if (AuthenticationUtil.getCurrentUser().getUserType() == UserType.Admin) {
			transactionList = transactionRepository.getAllTransactions();
		} else if (AuthenticationUtil.getCurrentUser().getUserType() == UserType.Bank) {
			transactionList = transactionRepository
					.getTransactionByBank(AuthenticationUtil.getCurrentUser().getAssociatedId());
		} else if (AuthenticationUtil.getCurrentUser().getUserType() == UserType.BankBranch) {
			transactionList = transactionRepository
					.getTransactionByBankBranch(AuthenticationUtil.getCurrentUser().getAssociatedId());
		}
		return pdfExporter.exportFinancialReport(transactionList, path);
	}

	@Override
	public PagablePage getCommissionTransactions(Integer currentpage, String fromDate, String toDate, String bankId,
			String merchantId, boolean forReport) {
		PagablePage pageble = new PagablePage();

		if (currentpage == null || currentpage == 0) {
			currentpage = 1;
		}

		int starting = ((currentpage * (int) PageInfo.pageList) - (int) PageInfo.pageList);

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		entityManager = entityManager.getEntityManagerFactory().createEntityManager();

		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Transaction.class);
		criteria.add(Restrictions.eq("status", TransactionStatus.Complete));

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

		if (bankId != null && !(bankId.trim().equals("")) && (isNumber(bankId))) {
			Bank bank = bankRepository.findOne(Long.parseLong(bankId));
			criteria.add(Restrictions.eq("bank", bank));
		}
		if (merchantId != null && !(merchantId.trim().equals("")) && (isNumber(merchantId))) {
			criteria.createAlias("merchantManager.merchantsAndServices.merchant", "merchant");
			criteria.add(Restrictions.eq("merchant.id", Long.parseLong(merchantId)));
		}

		criteria.addOrder(Order.desc("id"));

		int totalpage = (int) Math.ceil(criteria.list().size() / PageInfo.pageList);
		List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(currentpage, totalpage, PageInfo.numberOfPage);
		criteria.setFirstResult(starting);
		if (!forReport)
			criteria.setMaxResults((int) PageInfo.pageList);
		List<Transaction> transaction = criteria.list();

		pageble.setCurrentPage(currentpage);
		pageble.setObject(transactionConverter.convertToDtoList(transaction));
		pageble.setPageList(pagesnumbers);
		pageble.setLastpage(totalpage);

		return pageble;
	}

	@Override
	public List<TransactionDTO> getTransactionByBranch(long branchId) {
		List<Transaction> transactionList = transactionRepository.getTransactionByBankBranch(branchId);
		if (transactionList != null) {
			return transactionConverter.convertToDtoList(transactionList);
		}
		return null;
	}

	@Override
	public void paypointFailureReversal(String transactionIdentifier) {
		Transaction transaction = transactionRepository.getTransactionByTransactionIdentifier(transactionIdentifier);

		String remarksOne = transaction.getTransactionIdentifier();
		String desc1 = transaction.getMerchantManager().getMerchantsAndServices().getMerchantService().getService();
		String remarksTwo = "reversal " + transaction.getDestination();
		Account toAccount = accountRepository.findAccountByAccountNumber(
				transaction.getMerchantManager().getMerchantsAndServices().getMerchant().getAccountNumber());

		BankAccountSettings bankAccountSettings = bankAccountSettingsRepository
				.getBankAccountSettingsByBankCodeAndStatus(transaction.getBank().getSwiftCode(), Status.Active);

		commissionCore.transferBalanceFromMerchantToPool(transaction, toAccount,
				transaction.getAmount() - transaction.getTotalCommission());
		SettlementLog merchantSettlementLog = settlementLogRepository
				.findByTransactionAndSettlementType(transaction.getId(), SettlementType.MERCHANT);

		commissionCore.reverseAllocatedCommission(transaction);// this will
																// reverse all
																// the
																// commission to
																// pool account

		Transaction reversalTransaction = new Transaction();

		try {
			PropertyUtils.copyProperties(reversalTransaction, transaction);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		reversalTransaction = setReversalTransaction(reversalTransaction, transactionIdentifier);
		transaction.setStatus(TransactionStatus.Reversed);
		transaction = transactionRepository.save(transaction);

		// pdx hit to get balance back from merchant to bank parking account
		SettlementLog settlementLog = new SettlementLog();
		settlementLog.setBank(transaction.getBank());
		settlementLog.setTransaction(reversalTransaction);
		settlementLog.setSettlementType(SettlementType.MERCHANT);
		settlementLog.setFromAccountNumber(merchantSettlementLog.getToAccountNumber());
		settlementLog.setToAccountNumber(bankAccountSettings.getBankParkingAccountNumber());
		settlementLog.setAmount(merchantSettlementLog.getAmount());

		// List<Iso8583Log> isomerchantLog =
		// isoLogRepository.findByTransaction(transaction.getId());

		HashMap<String, String> parkingReversalResponse = customerApi.fundTransfer(
				merchantSettlementLog.getToAccountNumber(), bankAccountSettings.getBankParkingAccountNumber(),
				merchantSettlementLog.getTransferAmount(), transaction.getBank(), remarksOne, remarksTwo, desc1);
		if (parkingReversalResponse.get("status").equals("success")) {
			settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
		} else {
			settlementLog.setSettlementStatus(SettlementStatus.FAILURE);
			reversalTransaction.setSettlementStatus(SettlementStatus.FAILURE);
			reversalTransaction = transactionRepository.save(transaction);
			settlementLog.setTransaction(reversalTransaction);
		}
		settlementLog.setResponseCode(parkingReversalResponse.get("code"));
		settlementLog = settlementLogRepository.save(settlementLog);
		// pdx hit to get balance back from operator/bank pool account and
		// channel partner to bank parking account

		if (transaction.getChannelPartnerCommissionAmount() > 0.0) {
			SettlementLog channelPartnerSettlementLog = settlementLogRepository
					.findByTransactionAndSettlementType(transaction.getId(), SettlementType.CHANNELPARTNER);
			SettlementLog commissionsettlementLog = new SettlementLog();
			commissionsettlementLog.setBank(transaction.getBank());
			commissionsettlementLog.setTransaction(reversalTransaction);
			commissionsettlementLog.setSettlementType(SettlementType.CHANNELPARTNER);
			commissionsettlementLog.setFromAccountNumber(channelPartnerSettlementLog.getToAccountNumber());
			commissionsettlementLog.setToAccountNumber(bankAccountSettings.getBankParkingAccountNumber());
			commissionsettlementLog.setAmount(channelPartnerSettlementLog.getAmount());
			/*
			 * HashMap<String, String> channelPrtnerReversalResponse =
			 * customerApi.fundTransfer(
			 * channelPartnerSettlementLog.getToAccountNumber(),
			 * bankAccountSettings.getBankParkingAccountNumber(),
			 * channelPartnerSettlementLog.getAmount(), transaction.getBank(),
			 * remarksOne, remarksTwo, desc1);
			 * 
			 * if
			 * (channelPrtnerReversalResponse.get("status").equals("success")) {
			 * commissionsettlementLog.setSettlementStatus(SettlementStatus.
			 * SUCCESS); } else {
			 * commissionsettlementLog.setSettlementStatus(SettlementStatus.
			 * FAILURE);
			 * reversalTransaction.setSettlementStatus(SettlementStatus.FAILURE)
			 * ; reversalTransaction = transactionRepository.save(transaction);
			 * } commissionsettlementLog.setResponseCode(
			 * channelPrtnerReversalResponse.get("code"));
			 */
			commissionsettlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
			commissionsettlementLog.setResponseCode("no_settlement");
			commissionsettlementLog = settlementLogRepository.save(commissionsettlementLog);
		}
		if (transaction.getOperatorCommissionAmount() > 0.0) {
			SettlementLog operatorSettlementLog = settlementLogRepository
					.findByTransactionAndSettlementType(transaction.getId(), SettlementType.OPERATOR);

			SettlementLog commissionsettlementLog = new SettlementLog();
			commissionsettlementLog.setBank(transaction.getBank());
			commissionsettlementLog.setTransaction(reversalTransaction);
			commissionsettlementLog.setSettlementType(SettlementType.OPERATOR);
			commissionsettlementLog.setFromAccountNumber(operatorSettlementLog.getToAccountNumber());
			commissionsettlementLog.setToAccountNumber(bankAccountSettings.getBankParkingAccountNumber());
			commissionsettlementLog.setAmount(operatorSettlementLog.getAmount());

			/*
			 * HashMap<String, String> operatorReversalResponse =
			 * customerApi.fundTransfer(
			 * operatorSettlementLog.getToAccountNumber(),
			 * bankAccountSettings.getBankParkingAccountNumber(),
			 * operatorSettlementLog.getAmount(), transaction.getBank(),
			 * remarksOne, remarksTwo, desc1);
			 * 
			 * if (operatorReversalResponse.get("status").equals("success")) {
			 * commissionsettlementLog.setSettlementStatus(SettlementStatus.
			 * SUCCESS); } else {
			 * commissionsettlementLog.setSettlementStatus(SettlementStatus.
			 * FAILURE);
			 * reversalTransaction.setSettlementStatus(SettlementStatus.FAILURE)
			 * ; reversalTransaction =
			 * transactionRepository.save(reversalTransaction); }
			 * commissionsettlementLog.setResponseCode(operatorReversalResponse.
			 * get("code"));
			 */
			commissionsettlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
			commissionsettlementLog.setResponseCode("no_settlement");
			commissionsettlementLog = settlementLogRepository.save(commissionsettlementLog);
		}

		if (transaction.getBankCommissionAmount() > 0.0) {
			SettlementLog bankSettlementLog = settlementLogRepository
					.findByTransactionAndSettlementType(transaction.getId(), SettlementType.BANK);

			SettlementLog commissionsettlementLog = new SettlementLog();
			commissionsettlementLog.setBank(transaction.getBank());
			commissionsettlementLog.setTransaction(reversalTransaction);
			commissionsettlementLog.setSettlementType(SettlementType.BANK);
			commissionsettlementLog.setFromAccountNumber(bankSettlementLog.getToAccountNumber());
			commissionsettlementLog.setToAccountNumber(bankAccountSettings.getBankParkingAccountNumber());
			commissionsettlementLog.setAmount(bankSettlementLog.getAmount());

			/*
			 * HashMap<String, String> bankReversalResponse =
			 * customerApi.fundTransfer( bankSettlementLog.getToAccountNumber(),
			 * bankAccountSettings.getBankParkingAccountNumber(),
			 * bankSettlementLog.getAmount(), transaction.getBank(), remarksOne,
			 * remarksTwo, desc1);
			 * 
			 * if (bankReversalResponse.get("status").equals("success")) {
			 * commissionsettlementLog.setSettlementStatus(SettlementStatus.
			 * SUCCESS); } else {
			 * commissionsettlementLog.setSettlementStatus(SettlementStatus.
			 * FAILURE);
			 * reversalTransaction.setSettlementStatus(SettlementStatus.FAILURE)
			 * ; reversalTransaction =
			 * transactionRepository.save(reversalTransaction); }
			 * 
			 * commissionsettlementLog.setResponseCode(bankReversalResponse.get(
			 * "code"));
			 */

			commissionsettlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
			commissionsettlementLog.setResponseCode("no_settlement");
			commissionsettlementLog = settlementLogRepository.save(commissionsettlementLog);

		}
		// now total transaction amount is in parking account

		toAccount = accountRepository.findAccountByAccountNumber(transaction.getBank().getAccountNumber());

		commissionCore.transferBalanceFromPool(transaction, toAccount);
		// this will restore the bank spendable balance to it previous state

		if (transaction.getOriginatingUser().getUserType() == UserType.Bank) {

			// now pdx hit to refund balance to bank transfer account

		} else if (transaction.getOriginatingUser().getUserType() == UserType.BankBranch) {

			// now pdx hit to refund balance to bank transfer account

		} else if (transaction.getOriginatingUser().getUserType() == UserType.Customer) {

			SettlementLog commissionsettlementLog = new SettlementLog();
			commissionsettlementLog.setBank(transaction.getBank());
			commissionsettlementLog.setTransaction(reversalTransaction);
			commissionsettlementLog.setSettlementType(SettlementType.CUSTOMER);
			commissionsettlementLog.setFromAccountNumber(bankAccountSettings.getBankParkingAccountNumber());
			commissionsettlementLog.setToAccountNumber(transaction.getSourceBankAccount());
			commissionsettlementLog.setAmount(transaction.getAmount());
			HashMap<String, String> customerReversalResponse = customerApi.fundTransfer(
					bankAccountSettings.getBankParkingAccountNumber(), transaction.getSourceBankAccount(),
					transaction.getAmount(), transaction.getBank(), remarksOne, remarksTwo, desc1);

			if (customerReversalResponse.get("status").equals("success")) {
				commissionsettlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
			} else {
				commissionsettlementLog.setSettlementStatus(SettlementStatus.FAILURE);
				reversalTransaction.setSettlementStatus(SettlementStatus.FAILURE);
				reversalTransaction = transactionRepository.save(reversalTransaction);
				commissionsettlementLog.setTransaction(reversalTransaction);
			}
			commissionsettlementLog.setResponseCode(customerReversalResponse.get("code"));
			commissionsettlementLog = settlementLogRepository.save(commissionsettlementLog);
		}

	}

	private Transaction setReversalTransaction(Transaction reversalTransaction, String orginatingTranId) {

		reversalTransaction.setPreviousTransactionId(orginatingTranId);
		reversalTransaction.setStatus(TransactionStatus.ReversalWithRefund);
		reversalTransaction.setVersion(0);
		reversalTransaction.setCreated(new Date());
		reversalTransaction.setTransactionIdentifier(System.currentTimeMillis() + "");
		reversalTransaction = transactionRepository.save(reversalTransaction);
		return reversalTransaction;
	}

	@Override
	public Long countTransactions() {
		User currentUser = userRepository.findOne(AuthenticationUtil.getCurrentUser().getId());
		if (currentUser.getUserType().equals(UserType.Admin)) {
			return transactionRepository.countAllTransactions();
		} else if (currentUser.getUserType().equals(UserType.Bank)) {
			return transactionRepository.countAllTransactionsByBank(currentUser.getAssociatedId());
		} else if (currentUser.getUserType().equals(UserType.BankBranch)) {
			return transactionRepository.countAllTransactionsByBranch(currentUser.getAssociatedId());
		}
		return 0L;
	}

	@Override
	public RevenueReport getRevenue(String fromDate, String toDate, String bankId, String merchantId) {
		RevenueReport revenueReport = new RevenueReport();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		entityManager = entityManager.getEntityManagerFactory().createEntityManager();

		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Transaction.class);

		criteria.add(Restrictions.eq("status", TransactionStatus.Complete));

		try {
			if (fromDate != null && !(fromDate.trim().equals(""))) {
				criteria.add(Restrictions.ge("created", (Date) formatter.parse(fromDate)));
				revenueReport.setFromDate(fromDate);
			}

			if (toDate != null && !(toDate.trim().equals(""))) {
				criteria.add(Restrictions.lt("created", (Date) formatter.parse(toDate)));
				revenueReport.setToDate(toDate);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Bank bank = null;
		if (bankId != null && !(bankId.trim().equals("")) && (isNumber(bankId))) {
			bank = bankRepository.findOne(Long.parseLong(bankId));
			criteria.add(Restrictions.eq("bank", bank));
			revenueReport.setBankName(bank.getName());
		}
		if (merchantId != null && !(merchantId.trim().equals("")) && (isNumber(merchantId))) {
			criteria.createAlias("merchantManager.merchantsAndServices.merchant", "merchant");
			criteria.add(Restrictions.eq("merchant.id", Long.parseLong(merchantId)));
			revenueReport.setMerchant(merchantRepository.findOne(Long.parseLong(merchantId)).getName());
		}
		criteria.add(Restrictions.eq("status", TransactionStatus.Complete));
		criteria.add(Restrictions.eq("transactionType", TransactionType.Mpayment));
		List<Transaction> transactionList = criteria.list();
		double totalAmount = 0.0;
		double totalCommission = 0.0;
		for (Transaction transaction : transactionList) {
			totalAmount += transaction.getAmount();
			totalCommission += transaction.getTotalCommission();
		}
		revenueReport.setTotalTransactions(criteria.list().size());
		revenueReport.setTotalTransactionAmount(totalAmount);
		revenueReport.setTotalCommissionEarned(totalCommission);
		List<Revenue> revenueList = new ArrayList<>();
		for (MerchantService service : serviceRepository.findAll()) {
			Revenue revenue = new Revenue();
			List<Transaction> transactionsByService = transactionList.stream()
					.filter(t -> (t.getMerchantManager() != null && t.getMerchantManager().getMerchantsAndServices()
							.getMerchantService().getUniqueIdentifier().equals(service.getUniqueIdentifier())))
					.collect(Collectors.toList());
			revenue.setTransactionType(service.getService());
			revenue.setNoOfTransaction(transactionsByService.size());
			totalAmount = 0.0;
			totalCommission = 0.0;
			for (Transaction transaction : transactionsByService) {
				totalAmount += transaction.getAmount();
				totalCommission += transaction.getTotalCommission();
			}
			revenue.setValueOfTransaction(totalAmount);
			revenue.setCommissionEarned(totalCommission);
			revenueList.add(revenue);
		}
		revenueReport.setRevenueList(revenueList);
		return revenueReport;
	}

	@Override
	public Long countTransactionsByDate(Date date) {
		User currentUser = userRepository.findOne(AuthenticationUtil.getCurrentUser().getId());
		if (currentUser.getUserType().equals(UserType.Admin)) {
			return transactionRepository.countAllTransactionsByDate(date);
		} else if (currentUser.getUserType().equals(UserType.Bank)) {
			return transactionRepository.countAllTransactionsThisMonthByBankAndDate(currentUser.getAssociatedId(),
					date);
		} else if (currentUser.getUserType().equals(UserType.BankBranch)) {
			return transactionRepository.countAllTransactionsByBranchAndDate(currentUser.getAssociatedId(), date);
		}
		return 0L;
	}

	@Override
	public Long countTransactionByServiceAndStatusAndDate(String uniqueIdentifier, TransactionStatus status,
			Date date) {
		User currentUser = AuthenticationUtil.getCurrentUser();
		if (currentUser.getUserType() == UserType.Bank) {
			return transactionRepository.countTransactionByServiceAndBankAndStatusAndDate(uniqueIdentifier,
					currentUser.getAssociatedId(), status, date);
		} else if (currentUser.getUserType() == UserType.Admin) {
			return transactionRepository.countTransactionByServiceAndStatusAndDate(uniqueIdentifier, status, date);
		} else if (currentUser.getUserType() == UserType.BankBranch) {
			return transactionRepository.countTransactionByServiceAndBranchAndStatusAndDate(uniqueIdentifier,
					currentUser.getAssociatedId(), status, date);
		}
		return 0L;
	}

	@Override
	public HashMap<String, Object> getReversalReport(String fromDate, String toDate, String bankId) {
		HashMap<String, Object> reversalReport = new HashMap<>();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		entityManager = entityManager.getEntityManagerFactory().createEntityManager();

		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Transaction.class);
		criteria.add(Restrictions.eq("status", TransactionStatus.ReversalWithRefund));
		try {
			if (fromDate != null && !(fromDate.trim().equals(""))) {
				criteria.add(Restrictions.ge("created", (Date) formatter.parse(fromDate)));
				reversalReport.put("fromDate", fromDate);
			} else {
				reversalReport.put("fromDate", "-");
			}

			if (toDate != null && !(toDate.trim().equals(""))) {
				criteria.add(Restrictions.lt("created", (Date) formatter.parse(toDate)));
				reversalReport.put("toDate", toDate);
			} else {
				reversalReport.put("toDate", "-");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (bankId != null && !(bankId.trim().equals("")) && isNumber(bankId)) {
			Bank bank = bankRepository.findOne(Long.parseLong(bankId));
			criteria.createAlias("bankBranch.bank", "bank");
			criteria.add(Restrictions.eq("bank.id", bank.getId()));
			reversalReport.put("bank", bank.getName());
		} else {
			reversalReport.put("bank", "-");
		}
		List<Transaction> transactionList = criteria.list();
		List<HashMap<String, Object>> transactionReportList = new ArrayList<>();
		for (Transaction transaction : transactionList) {
			HashMap<String, Object> transactionReport = new HashMap<>();
			if (transaction.getOriginatingUser().getUserType() == UserType.Customer) {
				Customer customer = customerRepository.findOne(transaction.getOriginatingUser().getAssociatedId());
				transactionReport.put("mobileNo", customer.getMobileNumber());
			} else if (transaction.getOriginatingUser().getUserType() == UserType.Bank) {
				Bank bank = bankRepository.findOne(transaction.getOriginatingUser().getAssociatedId());
				transactionReport.put("mobileNo", bank.getMobileNumber());
			} else if (transaction.getOriginatingUser().getUserType() == UserType.BankBranch) {
				BankBranch branch = bankBranchRepository.findOne(transaction.getOriginatingUser().getAssociatedId());
				transactionReport.put("mobileNo", branch.getMobileNumber());
			}
			transactionReport.put("accountNo", transaction.getSourceBankAccount());
			transactionReport.put("bank", transaction.getBank().getName());
			transactionReport.put("amount", transaction.getAmount());
			transactionReport.put("transactionType", transaction.getTransactionType());
			transactionReport.put("refstan", transaction.getResponseDetail().get("RefStan"));
			transactionReport.put("date", DateUtil.convertDateToString(transaction.getCreated()));
			transactionReportList.add(transactionReport);
		}
		reversalReport.put("transactionList", transactionReportList);
		return reversalReport;
	}

	@Override
	public List<TransactionDTO> getByServiceCategoryAndDate(Long serviceCategoryId, Date date) {
		if (date != null) {
			return transactionConverter
					.convertToDtoList(transactionRepository.getByServiceCategoryAndDate(serviceCategoryId, date));
		} else {
			return transactionConverter.convertToDtoList(transactionRepository.getByServiceCategory(serviceCategoryId));
		}
	}

	@Override
	public Long countByTransactionTypeAndDate(TransactionType transactionType, Date date) {
		if (date == null) {
			return transactionRepository.countByTransactionType(transactionType);
		} else {
			return transactionRepository.countByTransactionTypeAndDate(transactionType, date);
		}
	}

	@Override
	public Long countTransactionUptoGivenMonthByBankAndDate(String bankId, Date date) {
		// TODO Auto-generated method stub
		return transactionRepository.countAllTransactionsUpToLastMonthByBankAndDate(Long.parseLong(bankId), date);
	}

	@Override
	public Long countTransactionThisNepaliMonthByBankAndDate(String bankId, Date monthYear, Date previousMonthDate) {
		// TODO Auto-generated method stub
		return transactionRepository.countAllTransactionsThisNepaliMonthByBankAndDate(Long.parseLong(bankId), monthYear,
				previousMonthDate);
	}

	@Override
	public Long countTotalTransactionByBank(String bankId) {
		// TODO Auto-generated method stub
		return transactionRepository.countAllTransactionsByBank(Long.parseLong(bankId));
	}

	@Override
	public Double sumTotalTransactionAmountUptoGivenMonthByBankAndDate(String bankId, Date monthYear) {
		// TODO Auto-generated method stub
		// return
		// transactionRepository.sumTotalTransactionAmountUptoLatMonthByBankAndDate(Long.parseLong(bankId),monthYear);
		entityManager = entityManager.getEntityManagerFactory().createEntityManager();
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery(
				"select sum(t.amount) as amount from Transaction t where t.bank.id=:bankId and t.created<=:date");
		query.setParameter("bankId", Long.parseLong(bankId));
		query.setParameter("date", monthYear);
		List list = query.list();
		// System.out.println("lastmonthamt:"+query.list());
		return (Double) ((list.get(0) != null) ? list.get(0) : 0.0);
	}

	@Override
	public Double sumTotalTransactionAmountThisNepaliMonthByBankAndDate(String bankId, Date monthYear,
			Date previousMonth) {
		// TODO Auto-generated method stub
		entityManager = entityManager.getEntityManagerFactory().createEntityManager();
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery(
				"select sum(t.amount) as amount from Transaction t where t.bank.id=:bankId and t.created<=:date and t.created>:previousDate");
		query.setParameter("bankId", Long.parseLong(bankId));
		query.setParameter("date", monthYear);
		query.setParameter("previousDate", previousMonth);
		List list = query.list();
		// System.out.println("thismonthamt:"+((list.get(0)!=null)?list.get(0):0.0));
		return (Double) ((list.get(0) != null) ? list.get(0) : 0.0);
	}

	@Override
	public Double sumTotalTransactionAmountByBank(String bankId) {
		// TODO Auto-generated method stub
		entityManager = entityManager.getEntityManagerFactory().createEntityManager();
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("select sum(t.amount) as amount from Transaction t where t.bank.id=:bankId");
		query.setParameter("bankId", Long.parseLong(bankId));
		List list = query.list();
		// System.out.println("totalamt:"+((list.get(0)!=null)?list.get(0):0.0));
		return (Double) ((list.get(0) != null) ? list.get(0) : 0.0);
	}

	@Override
	public Map<String, String> getTransactionDetailNepaliDateWise(Bank bank, Date thisMonth, Date previousMonth,
			ETransactionChannelForm eTransactionChannelForm) {
		// TODO Auto-generated method stub
		// System.out.println("Inside the map !!");
		Map<String, String> map = new HashMap<String, String>();
		TransactionType tt = null;
		if (eTransactionChannelForm == ETransactionChannelForm.CI_BP) {
			tt = TransactionType.Mpayment;
		} else if (eTransactionChannelForm == ETransactionChannelForm.CI_TR
				|| eTransactionChannelForm == ETransactionChannelForm.MIOECT_MBT) {
			tt = TransactionType.Transfer;
		}
		if (tt != null) {
			// System.out.println("Inside the tt !!");
			entityManager = entityManager.getEntityManagerFactory().createEntityManager();
			Session session = entityManager.unwrap(Session.class);
			Query query = session.createSQLQuery(
					"Select (select count(*) FROM transaction t1 WHERE t1.amount<5000 and t1.bank_id=:bank1 and t1.created>:previousMonth1 and t1.created<=:thisMonth1 and t1.transactionType=:tt1) as lessThan5k ,"
							+ "(select count(*) FROM transaction t2 WHERE t2.amount>5000 and t2.amount<10000 and t2.bank_id=:bank2 and t2.created>:previousMonth2 and t2.created<=:thisMonth2  and t2.transactionType=:tt2) as between5kand10k ,"
							+ "(select count(*) FROM transaction t3 WHERE t3.amount>10000 and t3.bank_id=:bank3 and t3.created>:previousMonth3 and t3.created<=:thisMonth3  and t3.transactionType=:tt3) as greaterThan10k ,"
							+ " (select count(*) From transaction t4 where t4.bank_id=:bank4 and t4.created>:previousMonth4 and t4.created<=:thisMonth4  and t4.transactionType=:tt4) as total ");
			query.setParameter("bank1", bank.getId());
			query.setParameter("thisMonth1", thisMonth);
			query.setParameter("previousMonth1", previousMonth);
			query.setParameter("tt1", tt);
			query.setParameter("bank2", bank.getId());
			query.setParameter("thisMonth2", thisMonth);
			query.setParameter("previousMonth2", previousMonth);
			query.setParameter("tt2", tt);
			query.setParameter("bank3", bank.getId());
			query.setParameter("thisMonth3", thisMonth);
			query.setParameter("previousMonth3", previousMonth);
			query.setParameter("tt3", tt);
			query.setParameter("bank4", bank.getId());
			query.setParameter("thisMonth4", thisMonth);
			query.setParameter("previousMonth4", previousMonth);
			query.setParameter("tt4", tt);
			// System.out.println("query is :"+query.toString());
			List list = query.list();
			// System.out.println("list of the eTransaction count is: "+list);
			if (list.size() != 0) {
				for (Object obj : list) {
					Object[] objArray = (Object[]) obj;
					map.put("less_than_5k", (objArray[0] != null ? String.valueOf(objArray[0]) : "Not Found"));
					map.put("between_5k_10k", (objArray[1] != null ? String.valueOf(objArray[1]) : "Not Found"));
					map.put("greater_than_10k", (objArray[2] != null ? String.valueOf(objArray[2]) : "Not Found"));
					map.put("total", (objArray[3] != null ? String.valueOf(objArray[3]) : "Not Found"));
				}

			}
		}
		return map;
	}

	@Override
	public Map<String, String> getPrinDetails(String transactionId) {
		HashMap<String, String> printDetail = new HashMap<>();
		Transaction transaction = transactionRepository.getTransactionByTransactionIdentifier(transactionId);
		printDetail.put("transactionId", transaction.getTransactionIdentifier());
		printDetail.put("created", transaction.getCreated().toString().substring(0, 19));
		printDetail.put("serviceTo", transaction.getDestination());
		if (transaction.getOriginatingUser().getUserType() == UserType.Customer) {
			printDetail.put("channel", "MOBILE");
			printDetail.put("enteredBy",
					customerRepository.findOne(transaction.getOriginatingUser().getAssociatedId()).getFullName());
			printDetail.put("enteredByMobileNo", transaction.getOriginatingUser().getSmsUserName());
		} else {
			printDetail.put("channel", "WEB");
			printDetail.put("enteredBy", transaction.getOriginatingUser().getUserName());
		}
		if (transaction.getMerchantManager() != null) {
			printDetail.put("serviceName",
					transaction.getMerchantManager().getMerchantsAndServices().getMerchantService().getService());
		}
		printDetail.put("amount", String.valueOf(transaction.getAmount()));
		printDetail.put("status", transaction.getStatus().toString());
		if (transaction.getBankBranch() != null) {
			printDetail.put("bankCode", transaction.getBankBranch().getBank().getSwiftCode());
			printDetail.put("bankName", transaction.getBankBranch().getBank().getName());
			printDetail.put("branchName", transaction.getBankBranch().getName());
		} else if (transaction.getBank() != null) {
			printDetail.put("bankCode", transaction.getBank().getSwiftCode());
			printDetail.put("bankName", transaction.getBank().getName());
		}

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		printDetail.put("currentDate", formatter.format(new Date()));
		printDetail.put("serviceImageUrl", transaction.getMerchantManager().getMerchantsAndServices()
				.getMerchantService().getDocuments().getIdentifier() + ".png");
		return printDetail;
	}

	@Override
	public HashMap<String, Object> getTransactionForCustomer(Long userId, String fromDate, String toDate,
			String pageNo) {
		HashMap<String, Object> response = new HashMap<>();
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(pageNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int starting = ((currentPage * (int) PageInfo.pageList) - (int) PageInfo.pageList);
		entityManager = entityManager.getEntityManagerFactory().createEntityManager();

		Session session = entityManager.unwrap(Session.class);

		Criteria criteria = session.createCriteria(Transaction.class);
		Criteria countCriteria = session.createCriteria(Transaction.class);

		criteria.createAlias("originatingUser", "user");
		criteria.add(Restrictions.eq("user.id", userId));

		countCriteria.createAlias("originatingUser", "user");
		countCriteria.add(Restrictions.eq("user.id", userId));

		if (fromDate != null && !fromDate.trim().equals("")) {
			criteria.add(Restrictions.gt("created", DateUtil.getDate(fromDate, DateTypes.FROM_DATE)));
			countCriteria.add(Restrictions.gt("created", DateUtil.getDate(fromDate, DateTypes.FROM_DATE)));
		}

		if (toDate != null && !toDate.trim().equals("")) {
			criteria.add(Restrictions.lt("created", DateUtil.getDate(toDate, DateTypes.TO_DATE)));
			countCriteria.add(Restrictions.lt("created", DateUtil.getDate(toDate, DateTypes.TO_DATE)));
		}
		countCriteria.setProjection(Projections.rowCount());
		int totalpage = (int) Math
				.ceil(((Integer.valueOf(countCriteria.uniqueResult().toString())) / PageInfo.pageList));
		criteria.setFirstResult(starting);
		criteria.setMaxResults((int) PageInfo.pageList);
		criteria.addOrder(Order.desc("created"));
		List<Transaction> transactionList = criteria.list();
		response.put("currentPage", currentPage);
		response.put("lastPage", totalpage);
		if (transactionList != null) {
			response.put("transactionList", transactionConverter.convertForCustomer(transactionList));
		}
		return response;
	}

}
