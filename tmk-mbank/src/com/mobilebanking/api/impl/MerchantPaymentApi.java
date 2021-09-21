package com.mobilebanking.api.impl;
import java.util.HashMap;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IMerchantPaymentApi;
import com.mobilebanking.core.CommissionCore;
import com.mobilebanking.core.TransactionCore;
import com.mobilebanking.repositories.AccountRepository;
import com.mobilebanking.repositories.BankBranchRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.repositories.MerchantManagerRepository;
import com.mobilebanking.repositories.MerchantServiceRepository;
import com.mobilebanking.repositories.TransactionRepository;
import com.mobilebanking.repositories.UserRepository;
import com.mobilebanking.util.ServiceDetector;
import com.wallet.serviceprovider.bussewa.service.Bus_SewaService;
import com.wallet.serviceprovider.epay.service.Epay_Service;
import com.wallet.serviceprovider.paypoint.paypointResponse.WorldLinkPackage;
import com.wallet.serviceprovider.paypoint.service.PayPoint_GetCompanyInfoService;

@Service
public class MerchantPaymentApi implements IMerchantPaymentApi {

	@Autowired
	private MerchantServiceRepository servicesRepository;

	@Autowired
	private UserRepository userRepository;

//	@Autowired
//	private ILedgerApi ledgerApi;

	@Autowired
	private CustomerRepository customerRepository;

//	@Autowired
//	private AgentRepository agentRepository;

	@Autowired
	private ServiceDetector serviceDetector;

//	@Autowired
//	private DishHomeRepository dishHomeRepository;

	@Autowired
	private TransactionCore trasactioncore;
	
	@Autowired
	private Epay_Service epayService;
	
	@Autowired
	private Bus_SewaService busSewaService;
	
	@Autowired 
	private PayPoint_GetCompanyInfoService payPointService;
	
	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private BankBranchRepository bankBranchRepository;
	
	@Autowired
	private MerchantManagerRepository merchantManagerRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private CommissionCore commissionCore;
	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public boolean paytime() {
		boolean valid = true;
		//serviceDetector.checkPaypoint();
		return valid;
	}

	@Override
	public void eprabhu() {
		//serviceDetector.checkEprabhu();
	}
/*
	@Override
	public HashMap<String,String> merchantPayment(String serviceI, String transactionI, double amount, long aid) throws Exception {

		boolean valid = true;
		TransactionDTO dto = new TransactionDTO();
		HashMap<String,String> hashResponse = new HashMap<String, String>();
		MerchantService service = servicesRepository.findMerchantServiceByIdentifier(serviceI);
		Merchant merchant = null;
		MerchantManager merchantManager = null;
		if (service != null) {
			merchantManager = merchantManagerRepository.getselected(service.getUniqueIdentifier(), Status.Active, true);
			merchant = merchantManager.getMerchantsAndServices().getMerchant();
		

			Customer sourceCustomer = null;
			Bank bank = null;
			BankBranch bankBranch = null;
			User user = userRepository.findOne(aid);
	
			if (user.getUserType() == UserType.Customer) {
				sourceCustomer = customerRepository.findOne(user.getAssociatedId());
				hashResponse.put("status", "success");
				hashResponse.put("transactionIdentifier", ""+System.currentTimeMillis());
				return hashResponse;
				//hashResponse = merchantPaymentCustomer(merchantManager, transactionI, service, aid, amount, sourceCustomer);
			
			} 
			else if (user.getUserType() == UserType.Bank) {
				bank = bankRepository.findOne(user.getAssociatedId());
				hashResponse = merchantPaymentBank(merchantManager, transactionI, service, aid, amount, bank);
				
				hashResponse.put("transactionDate", ""+new Date());
				hashResponse.put("ServiceTo", transactionI);
			}
			else if (user.getUserType() == UserType.BankBranch) {
				bankBranch = bankBranchRepository.findOne(user.getAssociatedId());
				hashResponse = merchantPaymentBankBrach(merchantManager, transactionI, service, aid, amount, bankBranch);
			
				hashResponse.put("transactionDate", ""+new Date());
				hashResponse.put("ServiceTo", transactionI);
			
			}
			else {
				hashResponse.put("status","Contact Operator");
			}
		}

		return hashResponse;

	}

	private HashMap<String,String> merchantPaymentBankBrach(MerchantManager merchant, String transactionI, MerchantService service, long aid,
			double amount, BankBranch bankBranch) throws ClientException, UnknownHostException, WebServiceException_Exception, JAXBException {
		boolean valid = true;
		HashMap<String, String> hashRequest = new HashMap<String, String>();
		hashRequest.put("serviceTo", transactionI);
		hashRequest.put("amount", "" + amount);
		hashRequest.put("serviceId", service.getUniqueIdentifier());
		long id = trasactioncore.transactionBankBranchToParking(merchant, aid, amount, bankBranch, hashRequest);
		trasactioncore.ledgerBankBranchToParking(amount, bankBranch,id);
		trasactioncore.accountBankBranchToParking(bankBranch, amount);
		hashRequest.put("transactionId", transactionRepository.findOne(id).getTransactionIdentifier());
		HashMap<String, String> hashResponse = serviceDetector.detectService(service.getUniqueIdentifier(),
				hashRequest);
		if (hashResponse == null) {
			valid = false;
		} else {
			if (hashResponse.get("status").equals("success")) {
				String transactionIdentifier = trasactioncore.transactionParkingToService(id, service, hashResponse);
				trasactioncore.ledgerParkingToService(amount, service,transactionIdentifier);
				trasactioncore.accountParkingToService(amount, service);
				Transaction transaction = transactionRepository.findOne(id);
				commissionCore.updateBalanceToBank(transaction);
				Account fromAccount = accountRepository.findAccountByAccountNumber(transaction.getBank().getAccountNumber());
				commissionCore.transferBalanceToPool(transaction, fromAccount);
				commissionCore.allocateCommissions(transaction);
			}else if(hashResponse.get("status").equals("unknown")) {
				trasactioncore.setAmbiguousTransaction(id,hashResponse);
			}
			else {
				valid = false;
				String transactionIdentifier = trasactioncore.reverseTransactionParkingToBankBranch(id, bankBranch, hashResponse);
				trasactioncore.reverseLedgerParkingToBankBranch(amount, bankBranch,transactionIdentifier);
				trasactioncore.reverseAccountParkingToBankBranch(amount, bankBranch);
			}
		}
		hashResponse.put("transactionIdentifier", hashRequest.get("transactionId"));
		return hashResponse;
	}

	private HashMap<String,String> merchantPaymentBank(MerchantManager merchantManager, String transactionI, MerchantService service, long aid,
			double amount, Bank bank) throws Exception {
		boolean valid = true;
		HashMap<String, String> hashRequest = new HashMap<String, String>();
		hashRequest.put("serviceTo", transactionI);
		hashRequest.put("amount", "" + amount);
		hashRequest.put("serviceId", service.getUniqueIdentifier());
		
		long id = trasactioncore.transactionBankToParking(merchantManager, aid, amount, bank, hashRequest);
	
		trasactioncore.ledgerBankToParking(amount, bank,id);
		trasactioncore.accountBankToParking(bank, amount);
		hashRequest.put("transactionId", transactionRepository.findOne(id).getTransactionIdentifier());
		HashMap<String, String> hashResponse = serviceDetector.detectService(service.getUniqueIdentifier(),
				hashRequest);
		if (hashResponse == null) {
			valid = false;
		} else {
			if (hashResponse.get("status").equals("success")) {
				
				Transaction transaction = transactionRepository.findOne(id);
				
				commissionCore.updateBalanceToBank(transaction);
				
				commissionCore.pollAndAllocateCommission(transaction);
				commissionCore.allocateCommissionToBank(transaction, transaction.getBank());
			
				String transactionIdentifier = trasactioncore.transactionParkingToService(id, service, hashResponse);
				trasactioncore.ledgerParkingToService(amount, service,transactionIdentifier);
				trasactioncore.accountParkingToService(amount, service);
			}
			else if(hashResponse.get("status").equals("unknown")) {
				trasactioncore.setAmbiguousTransaction(id,hashResponse);
			} 
			else {
				valid = false;
				String transactionIdentifier = trasactioncore.reverseTransactionParkingToBank(id, bank, hashResponse);
				trasactioncore.reverseLedgerParkingToBank(amount, bank,transactionIdentifier);
				trasactioncore.reverseAccountParkingToBank(amount, bank);
			}
		}
		hashResponse.put("transactionIdentifier", hashRequest.get("transactionId"));
		return hashResponse;
	}

	private HashMap<String,String> merchantPaymentCustomer(MerchantManager merchant, String requestData, MerchantService service, long aid,
			double amount, Customer sourceCustomer) throws Exception {

		boolean valid = true;
		HashMap<String, String> hashRequest = new HashMap<String, String>();
		hashRequest.put("serviceTo", requestData);
		hashRequest.put("amount", "" + amount);
		hashRequest.put("serviceId", service.getUniqueIdentifier());
		long id = trasactioncore.transactionCustomerToParking(merchant, aid, amount, sourceCustomer, hashRequest);
		trasactioncore.ledgerCustomerToParking(amount, sourceCustomer);
		trasactioncore.accountCustomerToParking(sourceCustomer, amount);
		hashRequest.put("transactionId", transactionRepository.findOne(id).getTransactionIdentifier());
		HashMap<String, String> hashResponse = serviceDetector.detectService(service.getUniqueIdentifier(),
				hashRequest);
		if (hashResponse == null) {
			valid = false;
		} else {
			if (hashResponse.get("status").equals("success")) {
				String transactionIdentifier = trasactioncore.transactionParkingToService(id, service, hashResponse);
				trasactioncore.ledgerParkingToService(amount, service,transactionIdentifier);
				trasactioncore.accountParkingToService(amount, service);
			}else if(hashResponse.get("status").equals("unknown")) {
				trasactioncore.setAmbiguousTransaction(id,hashResponse);
			}
			else {
				valid = false;
				String transactionIdentifier = trasactioncore.reverseTransactionParkingToCustomer(id, sourceCustomer, hashResponse);
				trasactioncore.reverseLedgerParkingToCustomer(amount, sourceCustomer,transactionIdentifier);
				trasactioncore.reverseAccountParkingToCustomer(amount, sourceCustomer);
			}
		}
		hashResponse.put("transactionIdentifier", hashRequest.get("transactionId"));
		return hashResponse;

	}

	@Override
	public void epay() {

		try {
			
			//epayService.getStatus();
			//epayService.addPayment();
			//epayService.addPayment();
			//epayService.onlinePayment();
			epayService.onlinePaymentTest();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void bussewa() {
		// TODO Auto-generated method stub
		try {
			busSewaService.refresh();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void payPoint() {
		// TODO Auto-generated method stub
		payPointService.companyInfo();
		
	}

	@Override
	public void unitedsolution() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public WorldLinkPackage worldLinkCheck(String serviceId, String transactionId, double amount, Long id) throws JAXBException {
		HashMap<String, String> hashRequest = new HashMap<String, String>();
		MerchantService  service = servicesRepository.findMerchantServiceByIdentifier(serviceId);
		hashRequest.put("serviceTo", transactionId);
		hashRequest.put("amount", "" + amount);
		hashRequest.put("serviceId", service.getUniqueIdentifier());
		hashRequest.put("company code", "597");
		hashRequest.put("serviceCode", "0");
		WorldLinkPackage worldLinkPackage = payPointService.checkPakageForWorldLink(hashRequest);
		
		return worldLinkPackage;
	}

	@Override
	public HashMap<String, String> merchantPayment(String serviceI, String transactionI, double amount, Long aid,
			HashMap<String,String> hash) throws Exception {
		boolean valid = true;
		MerchantManager merchantManager = null;
		TransactionDTO dto = new TransactionDTO();
		HashMap<String,String> hashResponse = null;
		MerchantService service = servicesRepository.findMerchantServiceByIdentifier(serviceI);
		Merchant merchant = null;
		if (service != null) {
			 merchantManager = merchantManagerRepository.getselected(service.getUniqueIdentifier(), Status.Active, true);
			merchant = merchantManager.getMerchantsAndServices().getMerchant();
		}

		Customer sourceCustomer = null;
		Bank bank = null;
		BankBranch bankBranch = null;
		User user = userRepository.findOne(aid);

		if (user.getUserType() == UserType.Customer) {
			sourceCustomer = customerRepository.findOne(user.getAssociatedId());
			
			hashResponse = merchantPaymentCustomer(merchantManager, transactionI, service, aid, amount, sourceCustomer);
		} 
		else if (user.getUserType() == UserType.Bank) {
			bank = bankRepository.findOne(user.getAssociatedId());
			hashResponse = merchantPaymentBank(merchantManager, transactionI, service, aid, amount, bank,hash);
			
			hashResponse.put("transactionDate", ""+new Date());
			hashResponse.put("ServiceTo", transactionI);
		}
		else if (user.getUserType() == UserType.BankBranch) {
			bankBranch = bankBranchRepository.findOne(user.getAssociatedId());
			hashResponse = merchantPaymentBankBrach(merchantManager, transactionI, service, aid, amount, bankBranch,hash);
		
			hashResponse.put("transactionDate", ""+new Date());
			hashResponse.put("ServiceTo", transactionI);
			
		}
		else {
			hashResponse.put("status","Contact Operator");
		}

		return hashResponse;

	}

	private HashMap<String, String> merchantPaymentBankBrach(MerchantManager merchantManager, String transactionI,
			MerchantService service, Long aid, double amount, BankBranch bankBranch, HashMap<String, String> hash) throws ClientException, UnknownHostException, WebServiceException_Exception, JAXBException {
		boolean valid = true;
		HashMap<String, String> hashRequest = new HashMap<String, String>();
		hashRequest.put("serviceTo", transactionI);
		hashRequest.put("amount", "" + amount);
		hashRequest.put("serviceId", service.getUniqueIdentifier());
	
		long id = trasactioncore.transactionBankBranchToParking(merchantManager, aid, amount, bankBranch, hashRequest);
		trasactioncore.ledgerBankBranchToParking(amount, bankBranch,id);
		trasactioncore.accountBankBranchToParking(bankBranch, amount);
		hashRequest.put("transactionId", transactionRepository.findOne(id).getTransactionIdentifier());
		
		if(merchantManager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier().equals(StringConstants.WLINK_TOPUP)){
			hashRequest.put("Utility Code" , ""+ hash.get("Utility Code"));
			hashRequest.put("Bill Number" , ""+ hash.get("Bill Number"));
			hashRequest.put("Reserve Info" , ""+ hash.get("Reserve Info"));
			hashRequest.put("RefStan" , ""+ hash.get("RefStan"));
			hashRequest.put("Commission Type" , ""+ hash.get("Commission Type"));
			hashRequest.put("Commission Value" , ""+ hash.get("Commission Value"));
			hashRequest.put("package", "true");
			hashRequest.put("packageId",hash.get("packageId"));
			}else if (merchantManager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier().equals(StringConstants.SUBISU)){
				hashRequest.put("username", hash.get("username"));
			}else if (merchantManager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier().equals(StringConstants.NEA)){
				
				hashRequest.put("officeCode", hash.get("officeCode"));
				hashRequest.put("Utility Code" , ""+ hash.get("Utility Code"));
				hashRequest.put("Bill Number" , ""+ hash.get("Bill Number"));
				hashRequest.put("RefStan" , ""+ hash.get("RefStan"));
				hashRequest.put("Commission Value" , ""+ hash.get("Commission Value"));
				hashRequest.put("payment",  hash.get("payment"));
				hashRequest.put("customerId",  hash.get("customerId"));
			}
		
		HashMap<String, String> hashResponse = serviceDetector.detectService(service.getUniqueIdentifier(),
				hashRequest);
		if (hashResponse == null) {
			valid = false;
		} else {
			if (hashResponse.get("status").equals("success")) {
				String transactionIdentifier = trasactioncore.transactionParkingToService(id, service, hashResponse);
				trasactioncore.ledgerParkingToService(amount, service,transactionIdentifier);
				trasactioncore.accountParkingToService(amount, service);
			} 
			else if(hashResponse.get("status").equals("unknown")) {
				trasactioncore.setAmbiguousTransaction(id,hashResponse);
			}
			
			else {
				valid = false;
				String transactionIdentifier = trasactioncore.reverseTransactionParkingToBankBranch(id, bankBranch, hashResponse);
				trasactioncore.reverseLedgerParkingToBankBranch(amount, bankBranch,transactionIdentifier);
				trasactioncore.reverseAccountParkingToBankBranch(amount, bankBranch);
			}
		}
		hashResponse.put("transactionIdentifier", hashRequest.get("transactionId"));
		return hashResponse;
	}


	private HashMap<String, String> merchantPaymentBank(MerchantManager merchantManager, String transactionI, MerchantService service,
			Long aid, double amount, Bank bank, HashMap<String, String> hash) throws ClientException, UnknownHostException, WebServiceException_Exception, JAXBException {
		boolean valid = true;
		HashMap<String, String> hashRequest = new HashMap<String, String>();
		hashRequest.put("serviceTo", transactionI);
		hashRequest.put("amount", "" + amount);
		hashRequest.put("serviceId", service.getUniqueIdentifier());
	
		long id = trasactioncore.transactionBankToParking(merchantManager, aid, amount, bank, hashRequest);
		trasactioncore.ledgerBankToParking(amount, bank,id);
		trasactioncore.accountBankToParking(bank, amount);
		hashRequest.put("transactionId", transactionRepository.findOne(id).getTransactionIdentifier());
		HashMap<String, String> hashResponse = new HashMap<>();
		if(merchantManager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier().equals(StringConstants.WLINK_TOPUP)){
			hashRequest.put("Utility Code" , ""+ hash.get("Utility Code"));
			hashRequest.put("Bill Number" , ""+ hash.get("Bill Number"));
			hashRequest.put("Reserve Info" , ""+ hash.get("Reserve Info"));
			hashRequest.put("RefStan" , ""+ hash.get("RefStan"));
			hashRequest.put("Commission Type" , ""+ hash.get("Commission Type"));
			hashRequest.put("Commission Value" , ""+ hash.get("Commission Value"));
			hashRequest.put("package", "true");
			hashRequest.put("packageId",hash.get("packageId"));
			}else if (merchantManager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier().equals(StringConstants.SUBISU)){
				hashRequest.put("username", hash.get("username"));
			}else if (merchantManager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier().equals(StringConstants.NEA)){
				
				hashRequest.put("officeCode", hash.get("officeCode"));
				hashRequest.put("Utility Code" , ""+ hash.get("Utility Code"));
				hashRequest.put("Bill Number" , ""+ hash.get("Bill Number"));
				hashRequest.put("RefStan" , ""+ hash.get("RefStan"));
				hashRequest.put("Commission Value" , ""+ hash.get("Commission Value"));
				hashRequest.put("payment",  hash.get("payment"));
				hashRequest.put("customerId",  hash.get("customerId"));
			}
		
		try{
			hashResponse = serviceDetector.detectService(service.getUniqueIdentifier(),
				hashRequest);
		}catch(Exception e){
			trasactioncore.setAmbiguousTransaction(id,hashResponse);
		}
		if (hashResponse == null) {
			valid = false;
		} else {
			if (hashResponse.get("status").equals("success")) {
				String transactionIdentifier = trasactioncore.transactionParkingToService(id, service, hashResponse);
				trasactioncore.ledgerParkingToService(amount, service,transactionIdentifier);
				trasactioncore.accountParkingToService(amount, service);
			} 
			else if(hashResponse.get("status").equals("unknown")) {
				trasactioncore.setAmbiguousTransaction(id,hashResponse);
			}
			
			else {
				valid = false;
				String transactionIdentifier = trasactioncore.reverseTransactionParkingToBank(id, bank, hashResponse);
				trasactioncore.reverseLedgerParkingToBank(amount, bank,transactionIdentifier);
				trasactioncore.reverseAccountParkingToBank(amount, bank);
			}
		}
		hashResponse.put("transactionIdentifier", hashRequest.get("transactionId"));
		return hashResponse;
	}

	@Override
	public HashMap<String, String> getNeaPaymentAmount(String serviceId, String transactionId, String scno,
			String officeCode) throws JAXBException {
		HashMap<String, String> hashRequest = new HashMap<String, String>();
		MerchantService  service = servicesRepository.findMerchantServiceByIdentifier(serviceId);
		hashRequest.put("serviceTo", scno);
		hashRequest.put("serviceId", service.getUniqueIdentifier());
		hashRequest.put("customerId", transactionId);
		hashRequest.put("officeCode", officeCode);
		hashRequest.put("company code", "598");
		hashRequest.put("serviceCode", "0");
		HashMap<String, String> hash = payPointService.checkNeaPaymentAmount(hashRequest);
		return hash;
		
	}

	
	*/

	@Override
	public HashMap<String, String> merchantPayment(String serviceI, String transactionI, double amount, long aid)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void epay() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bussewa() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void payPoint() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unitedsolution() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public WorldLinkPackage worldLinkCheck(String serviceId, String transactionId, double parseDouble, Long id)
			throws JAXBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, String> merchantPayment(String serviceId, String transactionId, double parseDouble, Long id,
			HashMap<String, String> hash) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, String> getNeaPaymentAmount(String serviceId, String transactionId, String scno,
			String officeCode) throws JAXBException {
		// TODO Auto-generated method stub
		return null;
	}

}
