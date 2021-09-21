package com.wallet.serviceprovider.ntc;

import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class EndPoint extends WebServiceGatewaySupport implements EndPointInterface {

	private static final Logger log = LoggerFactory.getLogger(EndPoint.class);

	ObjectFactory objectFactory = new ObjectFactory();

	@Override
	public ERetailCreditResult eRetailCredit(AuthorizationData requesterAuthentication, AgentIdentifier creditedAgent,
			boolean creditedAgentNotification, TransferredStockList transferredStockList, String requestTag)
			throws WebServiceException_Exception {

		ERetailCredit eRetailCredit = new ERetailCredit();
		eRetailCredit.setRequesterAuthentication(requesterAuthentication);
		eRetailCredit.setCreditedAgent(creditedAgent);
		eRetailCredit.setCreditedAgentNotification(creditedAgentNotification);
		eRetailCredit.setTransferredStockList(transferredStockList);
		eRetailCredit.setRequestTag(requestTag);

		JAXBElement<ERetailCredit> objectFactoryERetailCredit = objectFactory.createERetailCredit(eRetailCredit);

		JAXBElement<ERetailCreditResponse> eRetailCreditResponse = (JAXBElement<ERetailCreditResponse>) getWebServiceTemplate()
				.marshalSendAndReceive("http://10.0.1.10:8080/soapposapi?wsdl", objectFactoryERetailCredit,
						new SoapActionCallback("http://soapposapi.eservglobal.com/"));

		ERetailCreditResult eRetailCreditResult = eRetailCreditResponse.getValue().geteRetailCreditResult();

		return eRetailCreditResult;
	}

	public void printERetailCreditResponse(ERetailCreditResult response) {

		GlobalTransaction globalTransaction = response.getGlobalTransaction();

		if (globalTransaction != null) {
			log.info("Credited Actor Name --> " + globalTransaction.getCreditedActorName());
			log.info("Debited Actor Name --> " + globalTransaction.getDebitedActorName());

		} else {
			log.info("No global transaction info received");
		}

		log.info("Debited Agent --> " + response.getCreditedAgent());
	}

	@Override
	public ERetailDebitResult eRetailDebit(AuthorizationData requesterAuthentication, AgentIdentifier debitedAgent,
			boolean debitedAgentNotification, TransferredStockList transferredStockList, String requestTag)
			throws WebServiceException_Exception {

		ERetailDebit eRetailDebit = new ERetailDebit();
		eRetailDebit.setRequesterAuthentication(requesterAuthentication);
		eRetailDebit.setDebitedAgent(debitedAgent);
		eRetailDebit.setDebitedAgentNotification(debitedAgentNotification);
		eRetailDebit.setTransferredStockList(transferredStockList);
		eRetailDebit.setRequestTag(requestTag);

		JAXBElement<ERetailDebit> objectFactoryERetailDebit = objectFactory.createERetailDebit(eRetailDebit);

		JAXBElement<ERetailDebitResponse> eRetailDebitResponse = (JAXBElement<ERetailDebitResponse>) getWebServiceTemplate()
				.marshalSendAndReceive("http://10.0.1.10:8080/soapposapi?wsdl", objectFactoryERetailDebit,
						new SoapActionCallback("http://soapposapi.eservglobal.com/"));

		ERetailDebitResult eRetailDebitResult = eRetailDebitResponse.getValue().geteRetailDebitResult();

		return eRetailDebitResult;
	}

	public void printERetailDebitResponse(ERetailDebitResult response) {

		GlobalTransaction globalTransaction = response.getGlobalTransaction();

		if (globalTransaction != null) {
			log.info("Credited Actor Name --> " + globalTransaction.getCreditedActorName());
			log.info("Debited Actor Name --> " + globalTransaction.getDebitedActorName());

		} else {
			log.info("No global transaction info received");
		}

		log.info("Debited Agent --> " + response.getDebitedAgent());
	}

	@Override
	public ERetailRechargeResult eRetailRecharge(AuthorizationData requesterAuthentication,
			AgentIdentifier debitedAgent, String rechargedMsisdn, boolean debitedAgentNotification, long rechargeAmount,
			long productId, String requestTag) throws WebServiceException_Exception {

		ERetailRecharge eRetailRecharge = new ERetailRecharge();
		eRetailRecharge.setRequesterAuthentication(requesterAuthentication);
		eRetailRecharge.setDebitedAgent(debitedAgent);
		eRetailRecharge.setRechargedMsisdn(rechargedMsisdn);
		eRetailRecharge.setDebitedAgentNotification(debitedAgentNotification);
		eRetailRecharge.setRechargeAmount(rechargeAmount);
		eRetailRecharge.setProductId(productId);
		eRetailRecharge.setRequestTag(requestTag);

		JAXBElement<ERetailRecharge> objectFactoryERetailRecharge = objectFactory
				.createERetailRecharge(eRetailRecharge);

		JAXBElement<ERetailRechargeResponse> eRetailRechargeResponse = (JAXBElement<ERetailRechargeResponse>) getWebServiceTemplate()
				.marshalSendAndReceive("http://10.0.1.10:8080/soapposapi?wsdl", objectFactoryERetailRecharge,
						new SoapActionCallback("http://soapposapi.eservglobal.com/"));

		ERetailRechargeResult eRetailRechargeResult = eRetailRechargeResponse.getValue().geteRetailRechargeResult();

		return eRetailRechargeResult;
	}

	public void printERetailRechargeResponse(ERetailRechargeResult response) {

		GlobalTransaction globalTransaction = response.getGlobalTransaction();

		if (globalTransaction != null) {

			List<DetailTransaction> detailTransactions = globalTransaction.getDetailTransactionList()
					.getDetailTransaction();
			for (DetailTransaction detailTransaction : detailTransactions) {
				log.info("Agent Id --> " + detailTransaction.getAgentId());
				log.info("Amount --> " + detailTransaction.getAmount());
				log.info("Balance Credit Left --> " + detailTransaction.getBalanceCreditLeft());
				log.info("Balance Name --> " + detailTransaction.getBalanceName());
				log.info("Balance Type --> " + detailTransaction.getBalanceType());
				log.info("Currency Description --> " + detailTransaction.getCurrencyDescription());
				log.info("Operation --> " + detailTransaction.getOperation());
				log.info("Quantity --> " + detailTransaction.getQuantity());
				log.info("Unitary Price --> " + detailTransaction.getUnitaryPrice());
				log.info("Vat --> " + detailTransaction.getVat());
			}

		} else {
			log.info("No global transaction info received");
		}

		log.info("Transaction Date Time --> " + globalTransaction.getTransactionDateTime());
		log.info("Transaction ID --> " + globalTransaction.getTransactionId());
		log.info("Transaction Oper State --> " + globalTransaction.getTransactionOperState());

		log.info("Request Tag --> " + response.getRequestTag());
	}

	@Override
	public ERetailTransferResult eRetailTransfer(AuthorizationData requesterAuthentication,
			AgentIdentifier debitedAgent, AgentIdentifier creditedAgent, boolean debitedAgentNotification,
			boolean creditedAgentNotification, TransferredStockList transferredStockList, String requestTag)
			throws WebServiceException_Exception {

		ERetailTransfer eRetailTransfer = new ERetailTransfer();
		eRetailTransfer.setRequesterAuthentication(requesterAuthentication);
		eRetailTransfer.setDebitedAgent(debitedAgent);
		eRetailTransfer.setCreditedAgent(creditedAgent);
		eRetailTransfer.setDebitedAgentNotification(debitedAgentNotification);
		eRetailTransfer.setCreditedAgentNotification(creditedAgentNotification);
		eRetailTransfer.setTransferredStockList(transferredStockList);
		eRetailTransfer.setRequestTag(requestTag);

		JAXBElement<ERetailTransfer> objectFactoryERetailTransfer = objectFactory
				.createERetailTransfer(eRetailTransfer);

		JAXBElement<ERetailTransferResponse> eRetailTransferResponse = (JAXBElement<ERetailTransferResponse>) getWebServiceTemplate()
				.marshalSendAndReceive("http://10.0.1.10:8080/soapposapi?wsdl", objectFactoryERetailTransfer,
						new SoapActionCallback("http://soapposapi.eservglobal.com/"));

		ERetailTransferResult eRetailTransferResult = eRetailTransferResponse.getValue().geteRetailTransferResult();

		return eRetailTransferResult;
	}

	public void printERetailTransferResponse(ERetailTransferResult response) {

		GlobalTransaction globalTransaction = response.getGlobalTransaction();

		if (globalTransaction != null) {
			log.info("Credited Actor Name --> " + globalTransaction.getCreditedActorName());
			log.info("Debited Actor Name --> " + globalTransaction.getDebitedActorName());

		} else {
			log.info("No global transaction info received");
		}

		log.info("Credited Agent --> " + response.getCreditedAgent());
		log.info("Debited Agent --> " + response.getDebitedAgent());
	}

	@Override
	public AgentBalanceResult getAgentBalance(AuthorizationData requesterAuthentication, AgentIdentifier targetAgent,
			String productId, String requestTag) throws WebServiceException_Exception {

		AgentBalance agentBalance = new AgentBalance();
		agentBalance.setRequesterAuthentication(requesterAuthentication);
		agentBalance.setTargetAgent(targetAgent);
		agentBalance.setProductId(productId);
		agentBalance.setRequestTag(requestTag);

		JAXBElement<AgentBalance> objectFactoryAgentBalance = objectFactory.createAgentBalance(agentBalance);

		JAXBElement<AgentBalanceResponse> agentBalanceResponse = (JAXBElement<AgentBalanceResponse>) getWebServiceTemplate()
				.marshalSendAndReceive("http://10.0.1.10:8080/soapposapi?wsdl", objectFactoryAgentBalance,
						new SoapActionCallback("http://soapposapi.eservglobal.com/"));

		AgentBalanceResult agentBalanceResult = agentBalanceResponse.getValue().getAgentBalanceResult();

		return agentBalanceResult;
	}

	public void printAgentBalanceResponse(AgentBalanceResult response) {

		BalanceList balanceList = response.getBalanceList();
		if (balanceList != null) {

			List<Balance> balances = balanceList.getBalance();
			for (Balance balance : balances) {
				log.info("Balance Credit --> " + balance.getBalanceCredit());
				log.info("Balance ID --> " + balance.getBalanceId());
				log.info("Balance Max --> " + balance.getBalanceMax());
				log.info("Balance Min --> " + balance.getBalanceMin());
				log.info("Balance Name --> " + balance.getBalanceName());
				log.info("Balance Type --> " + balance.getBalanceType());
				log.info("Transaction Max --> " + balance.getTransacMax());
				log.info("Transaction Min --> " + balance.getTransacMin());
			}

		} else {
			log.info("No balance list info received");
		}

		log.info("Request Tag --> " + response.getRequestTag());

		Long transactionId = response.getTransactionId();
		log.info("Transaction ID --> " + transactionId.toString());

	}

	@Override
	public AgentInfoResult getAgentInfo(AuthorizationData requesterAuthentication, AgentIdentifier targetAgent,
			String requestTag) throws WebServiceException_Exception {

		AgentInfo agentInfo = new AgentInfo();

		agentInfo.setRequesterAuthentication(requesterAuthentication);
		agentInfo.setTargetAgent(targetAgent);
		agentInfo.setRequestTag(requestTag);

		JAXBElement<AgentInfo> objectFactoryAgentInfo = objectFactory.createAgentInfo(agentInfo);

		log.info("Request sent by --> " + objectFactoryAgentInfo.getValue().getRequestTag());
		log.info("Starting Marshalling");

		JAXBElement<AgentInfoResponse> agentInfoResponse = (JAXBElement<AgentInfoResponse>) getWebServiceTemplate()
				.marshalSendAndReceive("http://10.0.1.10:8080/soapposapi?wsdl", objectFactoryAgentInfo,
						new SoapActionCallback("http://soapposapi.eservglobal.com/"));

		AgentInfoResult agentInfoResult = agentInfoResponse.getValue().getAgentInfoResult();

		log.info("Finished UnMarshalling");
		return agentInfoResult;
	}

	public void printAgentInfoResponse(AgentInfoResult response) {

		TargetAgentInfo targetAgentInfo = response.getTargetAgentInfo();

		if (targetAgentInfo != null) {
			log.info("Actor Id --> " + targetAgentInfo.getActorId());
			log.info("Company --> " + targetAgentInfo.getCompany());
			log.info("Country --> " + targetAgentInfo.getCountry());
			log.info("Creation Date Time --> " + targetAgentInfo.getCreationDateTime());
			log.info("First name --> " + targetAgentInfo.getFirstName());
			log.info("Gender --> " + targetAgentInfo.getGender());
			log.info("Language --> " + targetAgentInfo.getLanguage());
			log.info("Last Name --> " + targetAgentInfo.getLastName());
			log.info("MSISDN --> " + targetAgentInfo.getMsisdn());
			log.info("Profile Id --> " + targetAgentInfo.getProfileId());
			// log.info("SMS Capability --> " + targetAgentInfo.getSms);
			// log.info("Soap Capability --> " + targetAgentInfo.getSoap);
			log.info("State --> " + targetAgentInfo.getState());
			// log.info("Stk Capability --> " + targetAgentInfo.getStk);
			log.info("Street Name --> " + targetAgentInfo.getStreetName());
			// log.info("USSD Capability --> " + targetAgentInfo.getUssd);
			// log.info("Web Capability --> " + targetAgentInfo.getWeb);

		} else {
			log.info("No target agent info received");
		}

		log.info("Request Tag --> " + response.getRequestTag());
	}

	@Override
	public void getTransactionInfo(AuthorizationData requesterAuthentication, String transactionId,
			Holder<String> requestTag, Holder<GlobalTransaction> transaction) throws WebServiceException_Exception {

		TransactionInfo transactionInfo = new TransactionInfo();
		transactionInfo.setRequesterAuthentication(requesterAuthentication);
		transactionInfo.setTransactionId(transactionId);
		transactionInfo.setRequestTag(requestTag);

		JAXBElement<TransactionInfo> objectFactoryTransactionInfo = objectFactory
				.createTransactionInfo(transactionInfo);

		JAXBElement<TransactionInfoResponse> transactionInfoResponse = (JAXBElement<TransactionInfoResponse>) getWebServiceTemplate()
				.marshalSendAndReceive("http://10.0.1.10:8080/soapposapi?wsdl", objectFactoryTransactionInfo,
						new SoapActionCallback("http://soapposapi.eservglobal.com/"));

		log.info("Request Tag --> " + transactionInfoResponse.getValue().getRequestTag());

		GlobalTransaction globalTransaction = transactionInfoResponse.getValue().getTransaction();
		if (globalTransaction != null) {

			DetailTransactionList detailTransactionList = globalTransaction.getDetailTransactionList();
			if (detailTransactionList != null) {

				List<DetailTransaction> detailTransactions = detailTransactionList.getDetailTransaction();

				for (DetailTransaction detailTransaction : detailTransactions) {
					log.info("Agent Id --> " + detailTransaction.getAgentId());
					log.info("Amount --> " + detailTransaction.getAmount());
					log.info("Balance Credit Left --> " + detailTransaction.getBalanceCreditLeft());
					log.info("Balance Name --> " + detailTransaction.getBalanceName());
					log.info("Balance Type --> " + detailTransaction.getBalanceType());
					log.info("Currency Description --> " + detailTransaction.getCurrencyDescription());
					log.info("Operation --> " + detailTransaction.getOperation());
					log.info("Quantity --> " + detailTransaction.getQuantity());
					log.info("Unitary Price --> " + detailTransaction.getUnitaryPrice());
					log.info("Vat --> " + detailTransaction.getVat());

				}
			} else {
				log.info("No detail transaction info received");
			}

			log.info("External Transaction Id --> " + globalTransaction.getExternalTransactionId());
			log.info("Medium --> " + globalTransaction.getMedium());
			log.info("OerationType --> " + globalTransaction.getOperationType());
			log.info("Transaction Date Time --> " + globalTransaction.getTransactionDateTime());
			log.info("Transaction ID --> " + globalTransaction.getTransactionId());
			log.info("Transaction Oper State --> " + globalTransaction.getTransactionOperState());
		} else {
			log.info("No global transaction info received");
		}

	}

}
