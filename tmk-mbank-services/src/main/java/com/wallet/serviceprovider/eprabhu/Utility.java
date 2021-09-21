package com.wallet.serviceprovider.eprabhu;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class Utility extends WebServiceGatewaySupport implements IUtility {
	
	private static final Logger log = LoggerFactory.getLogger(Utility.class);

	@Override
	public ReturnTransaction mobileTopup(InputMobileTopup mobileTopupRequest) {
		MobileTopup mobileTopup =new MobileTopup();
		mobileTopup.setMobileTopupRequest(mobileTopupRequest);
		
		MobileTopupResponse mobileTopupResponse =(MobileTopupResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.eprabhu.com/Api/Utility.svc?wsdl",
						mobileTopup, new SoapActionCallback("http://tempuri.org/IUtility/MobileTopup"));
		
		JAXBElement<ReturnTransaction> returnTransactionJaxb = mobileTopupResponse.getMobileTopupResult();
		ReturnTransaction returnTransaction = returnTransactionJaxb.getValue();
		return returnTransaction;
	}
	
	public void printMobileTopup(ReturnTransaction response){
		log.info("Response Code--> " + response.getCode());
		log.info("Response Status--> " + response.getMessage());
		log.info("Response Data--> " + response.getData());
		log.info("Response TransactionId--> " + response.getTransactionId());
	}


	@Override
	public ReturnTransaction rechargePins(InputRechargeCards buyRechargeCardsRequest) {
		RechargePins rechargePins = new RechargePins();
		rechargePins.setBuyRechargeCardsRequest(buyRechargeCardsRequest);
		
		RechargePinsResponse rechargePinsResponse = (RechargePinsResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.eprabhu.com/Api/Utility.svc?wsdl",
						rechargePins, new SoapActionCallback("http://tempuri.org/IUtility/RechargePins"));
		
		JAXBElement<ReturnTransaction> returnTransactionJaxb = rechargePinsResponse.getRechargePinsResult();
		ReturnTransaction returnTransaction = returnTransactionJaxb.getValue();
		return returnTransaction;
	}
	
	public void printRechargePins(ReturnTransaction response){
		log.info("Response Code--> " + response.getCode());
		log.info("Response Status--> " + response.getMessage());
		log.info("Response Data--> " + response.getData());
		log.info("Response TransactionId--> " + response.getTransactionId());
	}


	@Override
	public ReturnTransaction billPayment(InputBillPayment billPaymentRequest) {
		BillPayment billPayment =new BillPayment();
		billPayment.setBillPaymentRequest(billPaymentRequest);
		
		BillPaymentResponse billPaymentResponse = (BillPaymentResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.eprabhu.com/Api/Utility.svc?wsdl",
						billPayment, new SoapActionCallback("http://tempuri.org/IUtility/BillPayment"));
		
		JAXBElement<ReturnTransaction> returnTransactionJaxb = billPaymentResponse.getBillPaymentResult();
		ReturnTransaction returnTransaction = returnTransactionJaxb.getValue();
		return returnTransaction;
		
	}
	
	public void printBillPayment(ReturnTransaction response){
		log.info("Response Code--> " + response.getCode());
		log.info("Response Status--> " + response.getMessage());
		log.info("Response Data--> " + response.getData());
		log.info("Response TransactionId--> " + response.getTransactionId());
	}

	@Override
	public ReturnCheckPolicy checkPolicy(InputCheckPolicy checkPolicyRequest) {
		CheckPolicy checkPolicy = new CheckPolicy();
		checkPolicy.setCheckPolicyRequest(checkPolicyRequest);
		
		CheckPolicyResponse checkPolicyResponse =(CheckPolicyResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.eprabhu.com/Api/Utility.svc?wsdl",
						checkPolicy, new SoapActionCallback("http://tempuri.org/IUtility/CheckPolicy"));
		
		JAXBElement<ReturnCheckPolicy> returnCheckPolicyJaxb = checkPolicyResponse.getCheckPolicyResult();
		ReturnCheckPolicy returnCheckPolicy = returnCheckPolicyJaxb.getValue();
		return returnCheckPolicy;
	}
	
	public void printCheckPolicy(ReturnCheckPolicy response){
		log.info("Response Code--> " + response.getCode());
		log.info("Response Message--> " + response.getMessage());
		log.info("Response ActivateDate--> " + response.getActivateDate());
		log.info("Response AgentCode--> " + response.getAgentCode());
		log.info("Response BillAmount--> " + response.getBillAmount());
		log.info("Response DueDate--> " + response.getDueDate());
		log.info("Response Installments--> " + response.getInstallments());
		log.info("Response InterestAccrued--> " + response.getInterestAccrued());
		log.info("Response Name--> " + response.getName());
		log.info("Response PaymentMode--> " + response.getPaymentMode());
		log.info("Response Plan--> " + response.getPlan());
		log.info("Response PolicyNo--> " + response.getPolicyNo());
		log.info("Response Premium--> " + response.getPremium());
		log.info("Response Status--> " + response.getStatus());
		log.info("Response TotalBalance--> " + response.getTotalBalance());
	}

	@Override
	public ReturnTransaction insurancePremium(InputInsurancePremium insurancePremiumRequest) {
		InsurancePremium insurancePremium =new InsurancePremium();
		insurancePremium.setInsurancePremiumRequest(insurancePremiumRequest);
		
		InsurancePremiumResponse insurancePremiumResponse =(InsurancePremiumResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.eprabhu.com/Api/Utility.svc?wsdl",
						insurancePremium, new SoapActionCallback("http://tempuri.org/IUtility/InsurancePremium"));
		
		JAXBElement<ReturnTransaction> returnTransactionJaxb = insurancePremiumResponse.getInsurancePremiumResult();
		ReturnTransaction returnTransaction = returnTransactionJaxb.getValue();
		return returnTransaction;
	}
	
	public void printInsurancePremium(ReturnTransaction response){
		log.info("Response Code--> " + response.getCode());
		log.info("Response Status--> " + response.getMessage());
		log.info("Response Data--> " + response.getData());
		log.info("Response TransactionId--> " + response.getTransactionId());
	}

	@Override
	public ReturnFlightSearchDomestic flightSearchDomestic(InputFlightSearchDomestic flightSearchDomesticRequest) {
		FlightSearchDomestic flightSearchDomestic = new FlightSearchDomestic();
		flightSearchDomestic.setFlightSearchDomesticRequest(flightSearchDomesticRequest);
		
		FlightSearchDomesticResponse flightSearchDomesticResponse =(FlightSearchDomesticResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.eprabhu.com/Api/Utility.svc?wsdl",
						flightSearchDomestic, new SoapActionCallback("http://tempuri.org/IUtility/FlightSearchDomestic"));
		
		JAXBElement<ReturnFlightSearchDomestic> returnFlightSearchDomesticJaxb = flightSearchDomesticResponse.getFlightSearchDomesticResult();
		ReturnFlightSearchDomestic returnFlightSearchDomestic = returnFlightSearchDomesticJaxb.getValue();
		return returnFlightSearchDomestic;
	}
	
	public void printFlightSearchDomestic(ReturnFlightSearchDomestic response){
		log.info("Response Code--> " + response.getCode());
		log.info("Response Status--> " + response.getMessage());
		log.info("Response SectorFrom--> " + response.getSectorFrom());
		log.info("Response SectorTo--> " + response.getSectorTo());
		ArrayOfFlightDomestic flightDomestics = response.getInbound();
		List<FlightDomestic> flightDomesticList = flightDomestics.getFlightDomestic();
		for(FlightDomestic flightDomestic :flightDomesticList){
			log.info("Response Inbound Adult--> " + flightDomestic.getAdult());
			log.info("Response Inbound Adult Commission--> " + flightDomestic.getAdultCommission());
			log.info("Response Inbound Adult Fare--> " + flightDomestic.getAdultFare());
			log.info("Response Inbound Aircraft Type--> " + flightDomestic.getAircraftType());
			log.info("Response Inbound Airline Code--> " + flightDomestic.getAirlineCode());
			log.info("Response Inbound Airline Name--> " + flightDomestic.getAirlineName());
			log.info("Response Inbound Arrival--> " + flightDomestic.getArrival());
			log.info("Response Inbound ArrivalTime--> " + flightDomestic.getArrivalTime());
			log.info("Response Inbound Child--> " + flightDomestic.getChild());
			log.info("Response Inbound Child Commssion--> " + flightDomestic.getChildCommission());
			log.info("Response Inbound Child Fare--> " + flightDomestic.getChildFare());
			log.info("Response Inbound Currency--> " + flightDomestic.getCurrency());
			log.info("Response Inbound Departure--> " + flightDomestic.getDeparture());
			log.info("Response Inbound Departure Time--> " + flightDomestic.getDepartureTime());
			log.info("Response Inbound Flight Class Code--> " + flightDomestic.getFlightClassCode());
			log.info("Response Inbound Flight Date--> " + flightDomestic.getFlightDate());
			log.info("Response Inbound Flight Id--> " + flightDomestic.getFlightId());
			log.info("Response Inbound Flight No--> " + flightDomestic.getFlightNo());
			log.info("Response Inbound FreeBaggage--> " + flightDomestic.getFreeBaggage());
			log.info("Response Inbound FuelSurcharge--> " + flightDomestic.getFuelSurcharge());
			log.info("Response Inbound Nationality--> " + flightDomestic.getNationality());
			log.info("Response Inbound Refundable--> " + flightDomestic.getRefundable());
			log.info("Response Inbound Tax--> " + flightDomestic.getTax());
			log.info("Response Inbound Total Amount--> " + flightDomestic.getTotalAmount());
			log.info("Response Inbound Total Commssion--> " + flightDomestic.getTotalCommission());
		}
		
		flightDomestics = response.getOutbound();
		flightDomesticList = flightDomestics.getFlightDomestic();
		for(FlightDomestic flightDomestic :flightDomesticList){
			log.info("Response Outbound Adult--> " + flightDomestic.getAdult());
			log.info("Response Outbound Adult Commission--> " + flightDomestic.getAdultCommission());
			log.info("Response Outbound Adult Fare--> " + flightDomestic.getAdultFare());
			log.info("Response Outbound Aircraft Type--> " + flightDomestic.getAircraftType());
			log.info("Response Outbound Airline Code--> " + flightDomestic.getAirlineCode());
			log.info("Response Outbound Airline Name--> " + flightDomestic.getAirlineName());
			log.info("Response Outbound Arrival--> " + flightDomestic.getArrival());
			log.info("Response Outbound ArrivalTime--> " + flightDomestic.getArrivalTime());
			log.info("Response Outbound Child--> " + flightDomestic.getChild());
			log.info("Response Outbound Child Commssion--> " + flightDomestic.getChildCommission());
			log.info("Response Outbound Child Fare--> " + flightDomestic.getChildFare());
			log.info("Response Outbound Currency--> " + flightDomestic.getCurrency());
			log.info("Response Outbound Departure--> " + flightDomestic.getDeparture());
			log.info("Response Outbound Departure Time--> " + flightDomestic.getDepartureTime());
			log.info("Response Outbound Flight Class Code--> " + flightDomestic.getFlightClassCode());
			log.info("Response Outbound Flight Date--> " + flightDomestic.getFlightDate());
			log.info("Response Outbound Flight Id--> " + flightDomestic.getFlightId());
			log.info("Response Outbound Flight No--> " + flightDomestic.getFlightNo());
			log.info("Response Outbound FreeBaggage--> " + flightDomestic.getFreeBaggage());
			log.info("Response Outbound FuelSurcharge--> " + flightDomestic.getFuelSurcharge());
			log.info("Response Outbound Nationality--> " + flightDomestic.getNationality());
			log.info("Response Outbound Refundable--> " + flightDomestic.getRefundable());
			log.info("Response Outbound Tax--> " + flightDomestic.getTax());
			log.info("Response Outbound Total Amount--> " + flightDomestic.getTotalAmount());
			log.info("Response Outbound Total Commssion--> " + flightDomestic.getTotalCommission());
		}
	}


	@Override
	public ReturnFlightBookDomestic flightBookDomestic(InputFlightBookDomestic flightBookDomesticRequest) {
		FlightBookDomestic flightBookDomestic = new FlightBookDomestic();
		flightBookDomestic.setFlightBookDomesticRequest(flightBookDomesticRequest);
		
		FlightBookDomesticResponse flightBookDomesticResponse = (FlightBookDomesticResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.eprabhu.com/Api/Utility.svc?wsdl",
						flightBookDomestic, new SoapActionCallback("http://tempuri.org/IUtility/FlightBookDomestic"));
		
		JAXBElement<ReturnFlightBookDomestic> returnFlightBookDomesticjaxb = flightBookDomesticResponse.getFlightBookDomesticResult();
		ReturnFlightBookDomestic returnFlightBookDomestic = returnFlightBookDomesticjaxb.getValue();
		return returnFlightBookDomestic;
	}
	
	public void printFlightBookDomestic(ReturnFlightBookDomestic response){
		log.info("Response Code--> " + response.getCode());
		log.info("Response Status--> " + response.getMessage());
	}

	@Override
	public ReturnTransaction flightConfirmDomestic(InputFlightConfirmDomestic flightConfirmDomesticRequest) {
		FlightConfirmDomestic flightConfirmDomestic = new FlightConfirmDomestic();
		flightConfirmDomestic.setFlightConfirmDomesticRequest(flightConfirmDomesticRequest);
		
		FlightConfirmDomesticResponse flightConfirmDomesticResponse = (FlightConfirmDomesticResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.eprabhu.com/Api/Utility.svc?wsdl",
						flightConfirmDomestic, new SoapActionCallback("http://tempuri.org/IUtility/FlightConfirmDomestic"));
		
		JAXBElement<ReturnTransaction> returnTransactionJaxb = flightConfirmDomesticResponse.getFlightConfirmDomesticResult();
		ReturnTransaction returnTransaction = returnTransactionJaxb.getValue();
		return returnTransaction;
		
	}
	
	public void printFlightConfirmDomestic(ReturnTransaction response){
		log.info("Response Code--> " + response.getCode());
		log.info("Response Status--> " + response.getMessage());
		log.info("Response Data--> " + response.getData());
		log.info("Response TransactionId--> " + response.getTransactionId());
	}

	@Override
	public ReturnTransaction getTransactionStatus(InputGetTransactionStatus getTransactionStatusRequest) {
		GetTransactionStatus getTransactionStatus = new GetTransactionStatus();
		getTransactionStatus.setGetTransactionStatusRequest(getTransactionStatusRequest);
		
		GetTransactionStatusResponse getTransactionStatusResponse = (GetTransactionStatusResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.eprabhu.com/Api/Utility.svc?wsdl",
						getTransactionStatus, new SoapActionCallback("http://tempuri.org/IUtility/GetTransactionStatus"));
		
		JAXBElement<ReturnTransaction> returnTransactionJaxb = getTransactionStatusResponse.getGetTransactionStatusResult();
		ReturnTransaction returnTransaction = returnTransactionJaxb.getValue();
		return returnTransaction;
	}
	
	public void printGetTransactionStatus(ReturnTransaction response){
		log.info("Response Code--> " + response.getCode());
		log.info("Response Status--> " + response.getMessage());
		log.info("Response Data--> " + response.getData());
		log.info("Response TransactionId--> " + response.getTransactionId());
	}


	@Override
	public ReturnGetBalance getBalance(InputGetBalance getBalanceRequest) {
		GetBalance getBalance = new GetBalance();
		getBalance.setGetBalanceRequest(getBalanceRequest);
		
		GetBalanceResponse getBalanceResponse =(GetBalanceResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.eprabhu.com/Api/Utility.svc?wsdl",
						getBalance, new SoapActionCallback("http://tempuri.org/IUtility/GetBalance"));
		
		
		JAXBElement<ReturnGetBalance> returnGetBalanceJaxb = getBalanceResponse.getGetBalanceResult();
		ReturnGetBalance returnGetBalance = returnGetBalanceJaxb.getValue();
		return returnGetBalance;
	}
	
	public void printGetBalance(ReturnGetBalance response){
		log.info("Response Code--> " + response.getCode());
		log.info("Response Status--> " + response.getMessage());
		log.info("Response Balance--> " + response.getBalance());
	}

	@Override
	public ReturnTransaction dishHomeBooking(InputDishHomeBooking dishHomeBookingRequest) {
		DishHomeBooking dishHomeBooking = new DishHomeBooking();
		dishHomeBooking.setDishHomeBookingRequest(dishHomeBookingRequest);
		
		DishHomeBookingResponse dishHomeBookingResponse = (DishHomeBookingResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.eprabhu.com/Api/Utility.svc?wsdl",
						dishHomeBooking, new SoapActionCallback("http://tempuri.org/IUtility/DishHomeBooking"));
		
		JAXBElement<ReturnTransaction> returnTransactionJaxb = dishHomeBookingResponse.getDishHomeBookingResult();
		ReturnTransaction returnTransaction = returnTransactionJaxb.getValue();
		return returnTransaction;
	}
	
	public void printDishHomeBooking(ReturnTransaction response){
		log.info("Response Code--> " + response.getCode());
		log.info("Response Status--> " + response.getMessage());
		log.info("Response Data--> " + response.getData());
		log.info("Response TransactionId--> " + response.getTransactionId());
	}

	@Override
	public ReturnGetNEAOfficeCode getNEAOfficeCode(InputGetNEAOfficeCode getGetNEAOfficeCode) {
		GetNEAOfficeCode getNEAOfficeCode = new GetNEAOfficeCode();
		getNEAOfficeCode.setGetGetNEAOfficeCode(getGetNEAOfficeCode);
		
		GetNEAOfficeCodeResponse getNEAOfficeCodeResponse =(GetNEAOfficeCodeResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.eprabhu.com/Api/Utility.svc?wsdl",
						getNEAOfficeCode, new SoapActionCallback("http://tempuri.org/IUtility/GetNEAOfficeCode"));
		
		JAXBElement<ReturnGetNEAOfficeCode> returnGetNEAOfficeCodejaxb = getNEAOfficeCodeResponse.getGetNEAOfficeCodeResult();
		ReturnGetNEAOfficeCode returnGetNEAOfficeCode = returnGetNEAOfficeCodejaxb.getValue();
		return returnGetNEAOfficeCode;
	}
	
	public void printGetNEAOfficeCode(ReturnGetNEAOfficeCode response){
		log.info("Response Code--> " + response.getCode());
		log.info("Response Status--> " + response.getMessage());
		ArrayOfOfficeCode officeCodes = response.getOfficeCodes();
		List<OfficeCode> officeCodeList = officeCodes.getOfficeCode();
		for (OfficeCode officeCode : officeCodeList){
			log.info("Response Office--> " + officeCode.getOffice());
			log.info("Response OfficeCode--> " + officeCode.getOfficeCodes());
		}
		
	}

	@Override
	public ReturnGetNEABill getNEABill(InputGetNEABill getNEABillRequest) {
		GetNEABill getNEABill =new GetNEABill();
		getNEABill.setGetNEABillRequest(getNEABillRequest);
		
		GetNEABillResponse getNEABillResponse =(GetNEABillResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.eprabhu.com/Api/Utility.svc?wsdl",
						getNEABill, new SoapActionCallback("http://tempuri.org/IUtility/GetNEABill"));

		JAXBElement<ReturnGetNEABill> returnGetNEABillJaxb = getNEABillResponse.getGetNEABillResult();
		ReturnGetNEABill returnGetNEABill = returnGetNEABillJaxb.getValue();
		return returnGetNEABill;
	}
	
	public void printGetNEABill(ReturnGetNEABill response){
		log.info("Response Code--> " + response.getCode());
		log.info("Response Status--> " + response.getMessage());
		log.info("Response CustomerId--> " + response.getConsumerId());
		log.info("Response CustomerName--> " + response.getCustomerName());
		log.info("Response Office--> " + response.getOffice());
		log.info("Response OfficeCode--> " + response.getOfficeCode());
		log.info("Response SCNo--> " + response.getSCNo());
		log.info("Response TotalDueAmount--> " + response.getTotalDueAmount());
		ArrayOfNEABill billDetails= response.getBillDetail();
		List<NEABill> neaBillList = billDetails.getNEABill();
		for(NEABill neaBill:neaBillList){
			log.info("Response BillDate--> " + neaBill.getBillDate());
			log.info("Response DueBillOf--> " + neaBill.getDueBillOf());
			log.info("Response NoOfDays--> " + neaBill.getNoOfDays());
			log.info("Response Status--> " + neaBill.getStatus());
			log.info("Response BillAmount--> " + neaBill.getBillAmount());
			log.info("Response PayableAmount--> " + neaBill.getPayableAmount());
		}
		
	}

	@Override
	public ReturnGetNEACharge getNEACharge(InputGetNEACharge getNEAChargeRequest) {

		GetNEACharge getNEACharge = new GetNEACharge();
		getNEACharge.setGetNEAChargeRequest(getNEAChargeRequest);
		
		GetNEAChargeResponse getNEAChargeResponse =(GetNEAChargeResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.eprabhu.com/Api/Utility.svc?wsdl",
						getNEACharge, new SoapActionCallback("http://tempuri.org/IUtility/GetNEACharge"));
		
		JAXBElement<ReturnGetNEACharge> returnGetNEAChargeJaxb = getNEAChargeResponse.getGetNEAChargeResult();
		ReturnGetNEACharge returnGetNEACharge = returnGetNEAChargeJaxb.getValue();
		return returnGetNEACharge;
	}
	
	public void printGetNEACharge(ReturnGetNEACharge response){
		log.info("Response Code--> " + response.getCode());
		log.info("Response Status--> " + response.getMessage());
		log.info("Response SCharge--> " + response.getSCharge());
	}

	@Override
	public ReturnCheckWlinkAccount checkWlinkAccount(InputCheckWlinkAccount checkWlinkAccountRequest) {
		CheckWlinkAccount checkWlinkAccount = new CheckWlinkAccount();
		checkWlinkAccount.setCheckWlinkAccountRequest(checkWlinkAccountRequest);
		
		CheckWlinkAccountResponse checkWlinkAccountResponse =(CheckWlinkAccountResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.eprabhu.com/Api/Utility.svc?wsdl",
						checkWlinkAccount, new SoapActionCallback("http://tempuri.org/IUtility/CheckWlinkAccount"));
		
		JAXBElement<ReturnCheckWlinkAccount> returnCheckWlinkAccountJaxb = checkWlinkAccountResponse.getCheckWlinkAccountResult();
		ReturnCheckWlinkAccount returnCheckWlinkAccount = returnCheckWlinkAccountJaxb.getValue();
		return returnCheckWlinkAccount;
	}
	
	public void printCheckWlinkAccount(ReturnCheckWlinkAccount response){
		log.info("Response Code--> " + response.getCode());
		log.info("Response Status--> " + response.getMessage());
		log.info("Response CustomerName--> " + response.getCustomerName());
		log.info("Response PaymentMessage--> " + response.getPaymentMessage());
		log.info("Response WLinkUserName--> " + response.getWlinkUserName());
		
		ArrayOfRenewalPlan renewalPlans= response.getRenewalPlans();
		List<RenewalPlan> renewalPlanList =renewalPlans.getRenewalPlan();
		for(RenewalPlan renewalPlan :renewalPlanList ){
			log.info("Response Plan Id--> " + renewalPlan.getPlanId());
			log.info("Response Plan Name--> " + renewalPlan.getPlanName());
			log.info("Response Plan Amount--> " + renewalPlan.getPlanAmount());
		}
		JAXBElement<BigDecimal> bigDecimalJaxb = response.getBillAmount();
		log.info("Response BillAmount--> " + bigDecimalJaxb.getValue());
		
	}

	@Override
	public ReturnBusRouteDetail busRouteDetail(InputBusRouteDetail busRouteDetailRequest) {
		BusRouteDetail busRouteDetail = new BusRouteDetail();
		busRouteDetail.setBusRouteDetailRequest(busRouteDetailRequest);
		
		BusRouteDetailResponse busRouteDetailResponse = (BusRouteDetailResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.eprabhu.com/Api/Utility.svc?wsdl",
						busRouteDetail, new SoapActionCallback("http://tempuri.org/IUtility/BusRouteDetail"));
		
		JAXBElement<ReturnBusRouteDetail> returnBusRouteDetailJaxb = busRouteDetailResponse.getBusRouteDetailResult();
		ReturnBusRouteDetail returnBusRouteDetail = returnBusRouteDetailJaxb.getValue();
		return returnBusRouteDetail;
	}
	
	public void printBusRouteDetail(ReturnBusRouteDetail response){
		log.info("Response Code--> " + response.getCode());
		log.info("Response Status--> " + response.getMessage());
		Routes routes = response.getRoutes();
		List<String> routeList = routes.getRoute();
		for (String route : routeList){
			log.info("Route List--> " + route);
		}
		
	}

	@Override
	public ReturnBusDetailSearch busDetailSearch(InputBusDetailSearch busDetailSearchRequest) {
		BusDetailSearch busDetailSearch = new BusDetailSearch();
		busDetailSearch.setBusDetailSearchRequest(busDetailSearchRequest);
		
		BusDetailSearchResponse busDetailSearchResponse = (BusDetailSearchResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.eprabhu.com/Api/Utility.svc?wsdl",
						busDetailSearch, new SoapActionCallback("http://tempuri.org/IUtility/BusDetailSearch"));
		
		JAXBElement<ReturnBusDetailSearch> returnBusDetailSearchJaxb = busDetailSearchResponse.getBusDetailSearchResult();
		ReturnBusDetailSearch returnBusDetailSearch = returnBusDetailSearchJaxb.getValue();
		return returnBusDetailSearch;
	}
	
	public void printBusDetailSearch(ReturnBusDetailSearch response){
		log.info("Response Code--> " + response.getCode());
		log.info("Response Status--> " + response.getMessage());
		ArrayOfTrip trips = response.getTrips();
		List<Trip> tripList = trips.getTrip();
		for(Trip trip : tripList){
			log.info("Response Ammenities--> " + trip.getAmmenities());
			log.info("Response Available Seats--> " + trip.getAvailableSeats());
			log.info("Response Booked Seats--> " + trip.getBookedSeats());
			log.info("Response BusID--> " + trip.getBusId());
			log.info("Response BusType--> " + trip.getBusType());
			log.info("Response CommissionPercent--> " + trip.getCommissionPercent());
			log.info("Response DepartureTime--> " + trip.getDepartureTime());
			log.info("Response InputField--> " + trip.getInputField());
			log.info("Response InvisibleSeats--> " + trip.getInvisibleSeats());
			log.info("Response MultiDetail--> " + trip.getMultiDetail());
			log.info("Response NepaliDate--> " + trip.getNepaliDate());
			log.info("Response Operator--> " + trip.getOperator());
			log.info("Response RemovableSeats--> " + trip.getRemovableSeats());
			log.info("Response TicketPrice--> " + trip.getTicketPrice());
			log.info("Response TotalSeats--> " + trip.getTotalSeats());
			log.info("Response UnbookedSeats--> " + trip.getUnbookedSeats());
			log.info("Response VehicleType--> " + trip.getVehicleType());
		}
	}

	@Override
	public ReturnBusSeatLayout busSeatLayout(InputBusSeatLayout busSeatLayoutRequest) {
		BusSeatLayout busSeatLayout = new BusSeatLayout();
		busSeatLayout.setBusSeatLayoutRequest(busSeatLayoutRequest);
		
		BusSeatLayoutResponse busSeatLayoutResponse = (BusSeatLayoutResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.eprabhu.com/Api/Utility.svc?wsdl",
						busSeatLayout, new SoapActionCallback("http://tempuri.org/IUtility/BusSeatLayout"));
		
		JAXBElement<ReturnBusSeatLayout> returnBusSeatLayoutJaxb = busSeatLayoutResponse.getBusSeatLayoutResult();
		ReturnBusSeatLayout returnBusSeatLayout = returnBusSeatLayoutJaxb.getValue();
		return returnBusSeatLayout;
	}
	
	public void printBusSeatLayout(ReturnBusSeatLayout response){
		log.info("Response Code--> " + response.getCode());
		log.info("Response Status--> " + response.getMessage());
		log.info("Response CSS--> " + response.getCSS());
		log.info("Response HTML--> " + response.getHTML());
		log.info("Response JavaScript--> " + response.getJavaScript());
	}

	@Override
	public ReturnBusTicketBook busTicketBook(InputBusTicketBook busTicketBookRequest) {
		BusTicketBook busTicketBook = new BusTicketBook();
		busTicketBook.setBusTicketBookRequest(busTicketBookRequest);
		
		BusTicketBookResponse busTicketBookResponse =(BusTicketBookResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.eprabhu.com/Api/Utility.svc?wsdl",
						busTicketBook, new SoapActionCallback("http://tempuri.org/IUtility/BusTicketBook"));
		
		JAXBElement<ReturnBusTicketBook> returnBusTicketBookJaxb = busTicketBookResponse.getBusTicketBookResult();
		ReturnBusTicketBook returnBusTicketBook = returnBusTicketBookJaxb.getValue();
		return returnBusTicketBook;
	}
	
	public void printBusTicketBook(ReturnBusTicketBook response){
		log.info("Response Code--> " + response.getCode());
		log.info("Response Status--> " + response.getMessage());
		log.info("Response TicketSrlNo--> " + response.getTicketSrlNo());
		log.info("Response TimeOut--> " + response.getTimeOut());
		BPoints bPoints= response.getBoardingPoints();
		List<String> stationList = bPoints.getStation(); 
		for(String station :stationList){
			log.info("Response Station--> " + station);
		}
	}

	@Override
	public ReturnTransaction busTicketIssue(InputBusTicketIssue busTicketIssueRequest) {
		BusTicketIssue busTicketIssue =new BusTicketIssue();
		busTicketIssue.setBusTicketIssueRequest(busTicketIssueRequest);
		
		BusTicketIssueResponse busTicketIssueResponse =(BusTicketIssueResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.eprabhu.com/Api/Utility.svc?wsdl",
						busTicketIssue, new SoapActionCallback("http://tempuri.org/IUtility/BusTicketIssue"));
		
		JAXBElement<ReturnTransaction> returnTransactionJaxb = busTicketIssueResponse.getBusTicketIssueResult();
		ReturnTransaction returnTransaction = returnTransactionJaxb.getValue();
		return returnTransaction;
	}
	
	public void printBusTicketIssue(ReturnTransaction response){
		log.info("Response Code--> " + response.getCode());
		log.info("Response Status--> " + response.getMessage());
		log.info("Response Data--> " + response.getData());
		log.info("Response TransactionId--> " + response.getTransactionId());
	}

	@Override
	public ReturnTransaction sendSMS(InputSendSMS sendSMSRequest) {
		SendSMS sendSMS = new SendSMS();
		sendSMS.setSendSMSRequest(sendSMSRequest);
		
		SendSMSResponse sendSMSResponse =(SendSMSResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.eprabhu.com/Api/Utility.svc?wsdl",
						sendSMS, new SoapActionCallback("http://tempuri.org/IUtility/SendSMS"));
		
		JAXBElement<ReturnTransaction> returnTransactionJaxb = sendSMSResponse.getSendSMSResult();
		ReturnTransaction returnTransaction = returnTransactionJaxb.getValue();
		return returnTransaction;
	}
	
	public void printSendSMS(ReturnTransaction response){
		log.info("Response Code--> " + response.getCode());
		log.info("Response Status--> " + response.getMessage());
		log.info("Response Data--> " + response.getData());
		log.info("Response TransactionId--> " + response.getTransactionId());
	}

	@Override
	public ReturnTransaction getSMSTransactionStatus(InputGetSMSTransactionStatus getSMSTransactionStatusRequest) {
		GetSMSTransactionStatus getSMSTransactionStatus = new GetSMSTransactionStatus();
		getSMSTransactionStatus.setGetSMSTransactionStatusRequest(getSMSTransactionStatusRequest);
		
		GetSMSTransactionStatusResponse getSMSTransactionStatusResponse = (GetSMSTransactionStatusResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.eprabhu.com/Api/Utility.svc?wsdl",
						getSMSTransactionStatus, new SoapActionCallback("http://tempuri.org/IUtility/GetSMSTransactionStatus"));
		
		JAXBElement<ReturnTransaction> returnTransactionJaxb = getSMSTransactionStatusResponse.getGetSMSTransactionStatusResult();
		ReturnTransaction returnTransaction = returnTransactionJaxb.getValue();
		return returnTransaction;
	}
	
	public void printGetSMSTransactionStatus(ReturnTransaction response){
		log.info("Response Code--> " + response.getCode());
		log.info("Response Status--> " + response.getMessage());
		log.info("Response Data--> " + response.getData());
		log.info("Response TransactionId--> " + response.getTransactionId());
	}
}
