/*package com.wallet.api.impl;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.api.IDishHomeApi;
import com.wallet.core.TransactionCore;
import com.wallet.entity.Agent;
import com.wallet.entity.Customer;
import com.wallet.entity.DishHome;
import com.wallet.entity.Merchant;
import com.wallet.entity.Services;
import com.wallet.entity.User;
import com.wallet.model.DishHomeDto;
import com.wallet.model.UserType;
import com.wallet.repositories.AgentRepository;
import com.wallet.repositories.CustomerRepository;
import com.wallet.repositories.DishHomeRepository;
import com.wallet.repositories.MerchantRepository;
import com.wallet.repositories.ServicesRepository;
import com.wallet.repositories.UserRepository;
import com.wallet.serviceprovider.ntc.WebServiceException_Exception;
import com.wallet.util.ClientException;
import com.wallet.util.ConvertUtil;
import com.wallet.util.ServiceDetector;

@Service
public class DishHomeApi implements IDishHomeApi {

	@Autowired
	private MerchantRepository merchantRepository;

	@Autowired
	private ServicesRepository servicesRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AgentRepository agentRepository;

	@Autowired
	private ServiceDetector serviceDetector;

	@Autowired
	private TransactionCore trasactioncore;
	
	@Autowired
	private DishHomeRepository dishHomeRepository;

	@Override
	public boolean dishHomeRecharge(String serviceI, double amount, long aid) throws Exception {
		
		boolean valid = true;
		Services service = servicesRepository.getServiceByIdentifier(serviceI);
		
		Merchant merchant = null;
		if (service != null) {
			merchant = merchantRepository.findOne(service.getMerchant().getId());
		}

		Customer sourceCustomer = null;
		Agent sourceAgent = null;

		User user = userRepository.findOne(aid);
		
		if (user.getUserType() == UserType.Customer) {
			sourceCustomer = customerRepository.findOne(user.getAssociatedId());
			valid = dishHomePaymentCustomer(merchant, service, aid, amount, sourceCustomer);
		} else if (user.getUserType() == UserType.Agent) {
			sourceAgent = agentRepository.findOne(user.getAssociatedId());
			valid = dishHomePaymentAgent(merchant, service, aid, amount, sourceAgent);
		} else {
			valid = false;
		}
			return valid;
			
			
		}

	@Override
	public boolean rechargeBySTBIDorCASIDorCHIPID(String serviceI, double amount, String casId, long aid)
			throws Exception {

		boolean valid = true;
		Services service = servicesRepository.getServiceByIdentifier(serviceI);
		
		Merchant merchant = null;
		if (service != null) {
			merchant = merchantRepository.findOne(service.getMerchant().getId());
		}

		Customer sourceCustomer = null;
		Agent sourceAgent = null;

		User user = userRepository.findOne(aid);
		
		if (user.getUserType() == UserType.Customer) {
			sourceCustomer = customerRepository.findOne(user.getAssociatedId());
			valid = dishHomePaymentCustomer(merchant, service, aid, amount,casId, sourceCustomer);
		} else if (user.getUserType() == UserType.Agent) {
			sourceAgent = agentRepository.findOne(user.getAssociatedId());
			valid = dishHomePaymentAgent(merchant, service, aid, amount,casId, sourceAgent);
		} else {
			valid = false;
		}
			return valid;
		
	}
	

	private boolean dishHomePaymentCustomer(Merchant merchant, Services service, long aid, double amount, String casId,
			Customer sourceCustomer) throws ClientException, WebServiceException_Exception, UnknownHostException {
		HashMap<String, String> hashRequest = new HashMap<String, String>();
		hashRequest.put("amount", "" + amount);
		hashRequest.put("serviceId", service.getUniqueIdentifier());
		hashRequest.put("STBIDorCASIDorCHIPID", casId);
		long id = trasactioncore.transactionCustomerToParking(merchant, aid, amount, sourceCustomer, hashRequest);
		trasactioncore.ledgerCustomerToParking(amount, sourceCustomer);
		trasactioncore.accountCustomerToParking(sourceCustomer, amount);
		
		HashMap<String, String> hashResponse = serviceDetector.detectService(service.getUniqueIdentifier(),
				hashRequest);
		if (hashResponse == null) {
			return false;
		} else {
			if (hashResponse.get("status").equals("success")) {
				trasactioncore.transactionParkingToService(id, service, hashResponse);
				trasactioncore.ledgerParkingToService(amount, service);
				trasactioncore.accountParkingToService(amount, service);
			} else {
				
				trasactioncore.reverseTransactionParkingToCustomer(id, sourceCustomer, hashResponse);
				trasactioncore.reverseLedgerParkingToCustomer(amount, sourceCustomer);
				trasactioncore.reverseAccountParkingToCustomer(amount, sourceCustomer);
				return false;
			}
		return true;
	}
	}

	private boolean dishHomePaymentAgent(Merchant merchant, Services service, long aid, double amount, String casId,
			Agent sourceAgent) throws ClientException, WebServiceException_Exception, UnknownHostException {
		HashMap<String, String> hashRequest = new HashMap<String, String>();
		hashRequest.put("amount", "" + amount);
		hashRequest.put("serviceId", service.getUniqueIdentifier());
		hashRequest.put("STBIDorCASIDorCHIPID", casId);
		long id = trasactioncore.transactionAgentToParking(merchant, aid, amount, sourceAgent, hashRequest);
		trasactioncore.ledgerAgentToParking(amount, sourceAgent);
		trasactioncore.accountAgentToParking(sourceAgent, amount);
		
		HashMap<String, String> hashResponse = serviceDetector.detectService(service.getUniqueIdentifier(),
				hashRequest);
		if (hashResponse == null) {
			return false;
		} else {
			if (hashResponse.get("status").equals("success")) {
				trasactioncore.transactionParkingToService(id, service, hashResponse);
				trasactioncore.ledgerParkingToService(amount, service);
				trasactioncore.accountParkingToService(amount, service);
			} else {
				
				trasactioncore.reverseTransactionParkingToAgent(id, sourceAgent, hashResponse);
				trasactioncore.reverseLedgerParkingToAgent(amount, sourceAgent);
				trasactioncore.reverseAccountParkingToAgent(amount, sourceAgent);
				return false;
			}
		return true;
	}
	}


	private boolean dishHomePaymentAgent(Merchant merchant, Services service, long aid, double amount,
			Agent sourceAgent) throws ClientException, WebServiceException_Exception, UnknownHostException {
		HashMap<String, String> hashRequest = new HashMap<String, String>();
		hashRequest.put("amount", "" + amount);
		hashRequest.put("serviceId", service.getUniqueIdentifier());
		long id = trasactioncore.transactionAgentToParking(merchant, aid, amount, sourceAgent, hashRequest);
		trasactioncore.ledgerAgentToParking(amount, sourceAgent);
		trasactioncore.accountAgentToParking(sourceAgent, amount);
		
		HashMap<String, String> hashResponse = serviceDetector.detectService(service.getUniqueIdentifier(),
				hashRequest);
		if (hashResponse == null) {
			return false;
		} else {
			if (hashResponse.get("status").equals("success")) {
				trasactioncore.transactionParkingToService(id, service, hashResponse);
				trasactioncore.ledgerParkingToService(amount, service);
				trasactioncore.accountParkingToService(amount, service);
			} else {
				
				trasactioncore.reverseTransactionParkingToAgent(id, sourceAgent, hashResponse);
				trasactioncore.reverseLedgerParkingToAgent(amount, sourceAgent);
				trasactioncore.reverseAccountParkingToAgent(amount, sourceAgent);
				return false;
			}
		return true;
	}
	}
	private boolean dishHomePaymentCustomer(Merchant merchant, Services service, long aid, double amount,
			Customer sourceCustomer) throws ClientException, WebServiceException_Exception, UnknownHostException {
		boolean valid = true;
		HashMap<String, String> hashRequest = new HashMap<String, String>();
		hashRequest.put("amount", "" + amount);
		hashRequest.put("serviceId", service.getUniqueIdentifier());
		long id = trasactioncore.transactionCustomerToParking(merchant, aid, amount, sourceCustomer, hashRequest);
		trasactioncore.ledgerCustomerToParking(amount, sourceCustomer);
		trasactioncore.accountCustomerToParking(sourceCustomer, amount);

		HashMap<String, String> hashResponse = serviceDetector.detectService(service.getUniqueIdentifier(),
				hashRequest);
		if (hashResponse == null) {
			valid = false;
		} else {
			if (hashResponse.get("status").equals("success")) {
				trasactioncore.transactionParkingToService(id, service, hashResponse);
				trasactioncore.ledgerParkingToService(amount, service);
				trasactioncore.accountParkingToService(amount, service);
			} else {
				valid = false;
				trasactioncore.reverseTransactionParkingToCustomer(id, sourceCustomer, hashResponse);
				trasactioncore.reverseLedgerParkingToCustomer(amount, sourceCustomer);
				trasactioncore.reverseAccountParkingToCustomer(amount, sourceCustomer);
			}
		}

		return valid;
	}

	@Override
	public DishHomeDto balanceEnquiry(String serviceI) throws UnknownHostException {
		HashMap<String, String> hashRequest = new HashMap<String, String>();
		Services service = servicesRepository.getServiceByIdentifier(serviceI);
		hashRequest.put("serviceId", service.getUniqueIdentifier());
		DishHome dishHome = new DishHome();
		dishHome.setRequestDetail(hashRequest);
		dishHome = dishHomeRepository.save(dishHome);
		Map<String, String> hashResponse = new HashMap<String, String>();
		try {
			hashResponse = serviceDetector.detectService(service.getUniqueIdentifier(), hashRequest);
		} catch (WebServiceException_Exception e) {
			e.printStackTrace();
		}
		
		if(hashResponse==null){
			return null;
		}else
			if(hashResponse.get("status").equals("success")){
				dishHome.setResponseDetail(hashResponse);
				dishHome = dishHomeRepository.save(dishHome);
				return ConvertUtil.convertDishHome(dishHome);
			}
		
			else{
				return null;
			}
	}


}
*/