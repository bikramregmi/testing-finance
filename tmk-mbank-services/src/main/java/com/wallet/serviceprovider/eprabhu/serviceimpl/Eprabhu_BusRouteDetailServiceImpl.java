package com.wallet.serviceprovider.eprabhu.serviceimpl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.serviceprovider.eprabhu.ArrayOfBusPassenger;
import com.wallet.serviceprovider.eprabhu.ArrayOfPassenger;
import com.wallet.serviceprovider.eprabhu.InputBillPayment;
import com.wallet.serviceprovider.eprabhu.InputBusDetailSearch;
import com.wallet.serviceprovider.eprabhu.InputBusRouteDetail;
import com.wallet.serviceprovider.eprabhu.InputBusSeatLayout;
import com.wallet.serviceprovider.eprabhu.InputBusTicketBook;
import com.wallet.serviceprovider.eprabhu.InputBusTicketIssue;
import com.wallet.serviceprovider.eprabhu.InputCheckPolicy;
import com.wallet.serviceprovider.eprabhu.InputCheckWlinkAccount;
import com.wallet.serviceprovider.eprabhu.InputDishHomeBooking;
import com.wallet.serviceprovider.eprabhu.InputFlightBookDomestic;
import com.wallet.serviceprovider.eprabhu.InputFlightConfirmDomestic;
import com.wallet.serviceprovider.eprabhu.InputFlightSearchDomestic;
import com.wallet.serviceprovider.eprabhu.InputGetBalance;
import com.wallet.serviceprovider.eprabhu.InputGetNEABill;
import com.wallet.serviceprovider.eprabhu.InputGetNEACharge;
import com.wallet.serviceprovider.eprabhu.InputGetNEAOfficeCode;
import com.wallet.serviceprovider.eprabhu.InputGetSMSTransactionStatus;
import com.wallet.serviceprovider.eprabhu.InputGetTransactionStatus;
import com.wallet.serviceprovider.eprabhu.InputInsurancePremium;
import com.wallet.serviceprovider.eprabhu.InputMobileTopup;
import com.wallet.serviceprovider.eprabhu.InputRechargeCards;
import com.wallet.serviceprovider.eprabhu.InputSendSMS;
import com.wallet.serviceprovider.eprabhu.ReturnBusDetailSearch;
import com.wallet.serviceprovider.eprabhu.ReturnBusRouteDetail;
import com.wallet.serviceprovider.eprabhu.ReturnBusSeatLayout;
import com.wallet.serviceprovider.eprabhu.ReturnBusTicketBook;
import com.wallet.serviceprovider.eprabhu.ReturnCheckPolicy;
import com.wallet.serviceprovider.eprabhu.ReturnCheckWlinkAccount;
import com.wallet.serviceprovider.eprabhu.ReturnFlightBookDomestic;
import com.wallet.serviceprovider.eprabhu.ReturnFlightSearchDomestic;
import com.wallet.serviceprovider.eprabhu.ReturnGetBalance;
import com.wallet.serviceprovider.eprabhu.ReturnGetNEABill;
import com.wallet.serviceprovider.eprabhu.ReturnGetNEACharge;
import com.wallet.serviceprovider.eprabhu.ReturnGetNEAOfficeCode;
import com.wallet.serviceprovider.eprabhu.ReturnTransaction;
import com.wallet.serviceprovider.eprabhu.Utility;
import com.wallet.serviceprovider.eprabhu.service.Eprabhu_BusRouteDetailService;

@Service
public class Eprabhu_BusRouteDetailServiceImpl implements Eprabhu_BusRouteDetailService {
	
	@Autowired
	Utility utility;
	
	@Override
	public void getBusRouteDetail() {
		InputBusRouteDetail inputBusRouteDetail = new InputBusRouteDetail();
		inputBusRouteDetail.setUserName("ENET");
		inputBusRouteDetail.setPassword("Enet&pmpl8848");
		
		ReturnBusRouteDetail busRouteDetailresponse = utility.busRouteDetail(inputBusRouteDetail);
		utility.printBusRouteDetail(busRouteDetailresponse);
		
	}

	@Override
	public void getFlightSearchDomestic() {
		
		InputFlightSearchDomestic inputFlightSearchDomestic = new InputFlightSearchDomestic();
		inputFlightSearchDomestic.setAdult(1);
		inputFlightSearchDomestic.setChild(0);
		inputFlightSearchDomestic.setDepartureDate("2017/02/23");
		inputFlightSearchDomestic.setFlightMode(1);
		inputFlightSearchDomestic.setNationality("Nepali");
		inputFlightSearchDomestic.setPassword("Enet&pmpl8848");
		inputFlightSearchDomestic.setReturnDate("2017/02/24");
		inputFlightSearchDomestic.setSectorFrom("KTM");
		inputFlightSearchDomestic.setSectorTo("PKR");
		inputFlightSearchDomestic.setUserName("ENET");
		
		ReturnFlightSearchDomestic flightSearchDomesticResponse =utility.flightSearchDomestic(inputFlightSearchDomestic);
		
		utility.printFlightSearchDomestic(flightSearchDomesticResponse);
	}

	@Override
	public void getMobileTopup() {
		InputMobileTopup inputMobileTopup = new InputMobileTopup();
		inputMobileTopup.setAmount(0);
		inputMobileTopup.setMobileNumber("0987654321");
		inputMobileTopup.setOperatorCode(0);
		inputMobileTopup.setPartnerTxnId("0");
		inputMobileTopup.setPassword("Enet&pmpl8848");
		inputMobileTopup.setUserName("ENET");
		
		ReturnTransaction transactionResponse = utility.mobileTopup(inputMobileTopup);
		utility.printMobileTopup(transactionResponse);
		
	}

	@Override
	public void getRechargePins() {
		InputRechargeCards rechargeCards = new InputRechargeCards();
		rechargeCards.setAmount(0);
		rechargeCards.setOperatorCode(0);
		rechargeCards.setPartnerTxnId("0");
		rechargeCards.setPassword("Enet&pmpl8848");
		rechargeCards.setQuantity(0);
		rechargeCards.setUserName("ENET");
		
		ReturnTransaction transactionResponse = utility.rechargePins(rechargeCards);
		utility.printRechargePins(transactionResponse);
	}

	@Override
	public void getBillPayment() {
		InputBillPayment billPayment = new InputBillPayment();
		billPayment.setAmount(0);
		/*billPayment.setExtraField1(0);
		billPayment.setExtraField2(0);
		billPayment.setExtraField3(0);
		billPayment.setExtraField4(0);*/
		billPayment.setOperatorCode(0);
		billPayment.setPartnerTxnId("0");
		billPayment.setPassword("Enet&pmpl8848");
		billPayment.setSubscriber("0");
		billPayment.setUserName("ENET");
		
		ReturnTransaction transactionResponse = utility.billPayment(billPayment);
		utility.printBillPayment(transactionResponse);
	}

	@Override
	public void getCheckPolicy() {
		InputCheckPolicy checkPolicyRequest = new InputCheckPolicy();
		checkPolicyRequest.setOperatorCode(0);
		checkPolicyRequest.setPassword("Enet&pmpl8848");
		checkPolicyRequest.setPolicyNumber("0");
		checkPolicyRequest.setUserName("ENET");
		
		ReturnCheckPolicy checkPolicyResponse = utility.checkPolicy(checkPolicyRequest);
		
		utility.printCheckPolicy(checkPolicyResponse);
	}

	@Override
	public void getInsurancePremium() {
		InputInsurancePremium insurancePremiumRequest =new InputInsurancePremium();
		insurancePremiumRequest.setAmount(0);
		insurancePremiumRequest.setOperatorCode(0);
		insurancePremiumRequest.setPartnerTxnId("0");
		insurancePremiumRequest.setPassword("Enet&pmpl8848");
		insurancePremiumRequest.setPolicyName("Policyname");
		insurancePremiumRequest.setPolicyNumber("0");
		insurancePremiumRequest.setUserName("ENET");
		
		ReturnTransaction transactionResponse = utility.insurancePremium(insurancePremiumRequest);
		utility.printInsurancePremium(transactionResponse);
		
	}

	@Override
	public void getFlightBookDomestic() {
		InputFlightBookDomestic flightBookDomesticRequest = new InputFlightBookDomestic();
		flightBookDomesticRequest.setPassword("Enet&pmpl8848");
		flightBookDomesticRequest.setUserName("ENET");
		flightBookDomesticRequest.setInboundFlightId("Inbound");
		flightBookDomesticRequest.setOutboundFlightId("Outbound");
		
		ReturnFlightBookDomestic flightBookDomesticResponse = utility.flightBookDomestic(flightBookDomesticRequest);
		utility.printFlightBookDomestic(flightBookDomesticResponse);
	}

	@Override
	public void getFlightConfirmDomestic() {
		InputFlightConfirmDomestic flightConfirmDomesticRequest = new InputFlightConfirmDomestic();
		flightConfirmDomesticRequest.setAdult(0);
		ArrayOfPassenger passenger = new ArrayOfPassenger();
		flightConfirmDomesticRequest.setAdultPassengers(passenger);
		flightConfirmDomesticRequest.setAmount(0);
		flightConfirmDomesticRequest.setChild(0);
		flightConfirmDomesticRequest.setChildPassengers(passenger);
		flightConfirmDomesticRequest.setContactEmail("customer@email.com");
		flightConfirmDomesticRequest.setContactNo("0987654321");
		flightConfirmDomesticRequest.setContactPerson("customerB");
		flightConfirmDomesticRequest.setCurrency("NRs");
		flightConfirmDomesticRequest.setInboundFlightId("NI8");
		flightConfirmDomesticRequest.setNationality("Nepali");
		flightConfirmDomesticRequest.setOutboundFlightId("NT9");
		flightConfirmDomesticRequest.setPartnerTxnId("0");
		flightConfirmDomesticRequest.setPassword("Enet&pmpl8848");
		flightConfirmDomesticRequest.setUserName("ENET");
		
		ReturnTransaction transactionResponse = utility.flightConfirmDomestic(flightConfirmDomesticRequest);
		utility.printFlightConfirmDomestic(transactionResponse);
		
	}

	@Override
	public void getTransactionStatus() {
		InputGetTransactionStatus getTransactionStatusRequest =new InputGetTransactionStatus();
		getTransactionStatusRequest.setPartnerTxnId("0");
		getTransactionStatusRequest.setPassword("Enet&pmpl8848");
		getTransactionStatusRequest.setUserName("ENET");
		
		ReturnTransaction transactionResponse = utility.getTransactionStatus(getTransactionStatusRequest);
		utility.printGetTransactionStatus(transactionResponse);
	}

	@Override
	public void getBalance() {
		InputGetBalance getBalanceRequest = new InputGetBalance();
		getBalanceRequest.setPassword("Enet&pmpl8848");
		getBalanceRequest.setUserName("ENET");
		
		ReturnGetBalance balanceResponse = utility.getBalance(getBalanceRequest);
		utility.printGetBalance(balanceResponse);
	}

	@Override
	public void getDishHomeBooking() {
		InputDishHomeBooking dishHomeBookingRequest = new InputDishHomeBooking();
		dishHomeBookingRequest.setAmount(0);
		dishHomeBookingRequest.setCustomerName("Customer1");
		dishHomeBookingRequest.setDistrict("Disctrict");
		/*dishHomeBookingRequest.setHouseNo(value);
		dishHomeBookingRequest.setLocation(value);
		dishHomeBookingRequest.setPhone();*/
		dishHomeBookingRequest.setMobile("09876545321");
		dishHomeBookingRequest.setPartnerTxnId("0");
		dishHomeBookingRequest.setPassword("Enet&pmpl8848");
		
		dishHomeBookingRequest.setSTBType("type");
		dishHomeBookingRequest.setUserName("ENET");
		dishHomeBookingRequest.setVDC("VDC");
		dishHomeBookingRequest.setWard("001");
		
		ReturnTransaction transactionResponse = utility.dishHomeBooking(dishHomeBookingRequest);
		utility.printDishHomeBooking(transactionResponse);
	}

	@Override
	public void getNEAOfficeCode() {
		InputGetNEAOfficeCode getNEAOfficeCode = new InputGetNEAOfficeCode();
		getNEAOfficeCode.setPassword("Enet&pmpl8848");
		getNEAOfficeCode.setUserName("ENET");
		
		ReturnGetNEAOfficeCode NEAOfficeCodeResponse =utility.getNEAOfficeCode(getNEAOfficeCode);
		utility.printGetNEAOfficeCode(NEAOfficeCodeResponse);
	}

	@Override
	public void getNEABill() {
		InputGetNEABill getNEABillRequest = new InputGetNEABill();
		getNEABillRequest.setConsumerId("0");
		getNEABillRequest.setOfficeCode(null);
		getNEABillRequest.setPassword("Enet&pmpl8848");
		getNEABillRequest.setSCNo(null);
		getNEABillRequest.setUserName("ENET");
		
		ReturnGetNEABill NEABillResponse= utility.getNEABill(getNEABillRequest);
		utility.printGetNEABill(NEABillResponse);
		
	}

	@Override
	public void getNEACharge() {
		InputGetNEACharge getNEAChargeRequest=new InputGetNEACharge();
		getNEAChargeRequest.setAmount(new BigDecimal(0));
		getNEAChargeRequest.setConsumerId("0");
		getNEAChargeRequest.setOfficeCode("0");
		getNEAChargeRequest.setPassword("Enet&pmpl8848");
		getNEAChargeRequest.setSCNo(null);
		getNEAChargeRequest.setUserName("ENET");
		
		ReturnGetNEACharge NEAChargeResponse = utility.getNEACharge(getNEAChargeRequest);
		utility.printGetNEACharge(NEAChargeResponse);
	}

	@Override
	public void checkWlinkAccount() {
		InputCheckWlinkAccount checkWlinkAccountRequest = new InputCheckWlinkAccount();
		checkWlinkAccountRequest.setPassword("Enet&pmpl8848");
		checkWlinkAccountRequest.setUserName("ENET");
		checkWlinkAccountRequest.setWlinkUserName("user");
		
		ReturnCheckWlinkAccount checkWlinkAccountResponse = utility.checkWlinkAccount(checkWlinkAccountRequest);
		utility.printCheckWlinkAccount(checkWlinkAccountResponse);
	}

	@Override
	public void getBusDetailSearch() {
		InputBusDetailSearch busDetailSearchRequest = new InputBusDetailSearch();
		busDetailSearchRequest.setDepartureDate("");
		busDetailSearchRequest.setFrom("");
		busDetailSearchRequest.setPassword("Enet&pmpl8848");
		busDetailSearchRequest.setTo("");
		busDetailSearchRequest.setUserName("ENET");
		
		ReturnBusDetailSearch busDetailSearchResponse = utility.busDetailSearch(busDetailSearchRequest);
		utility.printBusDetailSearch(busDetailSearchResponse);
	}

	@Override
	public void getBusSeatLayout() {
		InputBusSeatLayout busSeatLayoutRequest = new InputBusSeatLayout();
		busSeatLayoutRequest.setBusId(0);
		busSeatLayoutRequest.setPassword("Enet&pmpl8848");
		busSeatLayoutRequest.setUserName("ENET");
		
		ReturnBusSeatLayout busSeatLayoutResponse = utility.busSeatLayout(busSeatLayoutRequest);
		utility.printBusSeatLayout(busSeatLayoutResponse);
	}

	@Override
	public void getBusTicketBook() {
		InputBusTicketBook busTicketBookRequest = new InputBusTicketBook();
		busTicketBookRequest.setBusId(0);
		busTicketBookRequest.setPassword("Enet&pmpl8848");
		busTicketBookRequest.setSelectedSeat(null);
		busTicketBookRequest.setUserName("ENET");
		
		ReturnBusTicketBook busTicketBookResponse = utility.busTicketBook(busTicketBookRequest);
		utility.printBusTicketBook(busTicketBookResponse);
	}

	@Override
	public void getBusTicketIssue() {
		InputBusTicketIssue busTicketIssueRequest = new InputBusTicketIssue();
		busTicketIssueRequest.setAmount(0);
		busTicketIssueRequest.setBoardingPoint(null);
		busTicketIssueRequest.setBusId(0);
		busTicketIssueRequest.setMobileNumber("");
		busTicketIssueRequest.setPartnerTxnId("");
		busTicketIssueRequest.setPassengers(new ArrayOfBusPassenger());
		busTicketIssueRequest.setPassword("Enet&pmpl8848");
		busTicketIssueRequest.setSelectedSeats("");
		busTicketIssueRequest.setTicketSrlNo("");
		busTicketIssueRequest.setUserName("ENET");
		
		ReturnTransaction transactionResponse = utility.busTicketIssue(busTicketIssueRequest);
		utility.printBusTicketIssue(transactionResponse);
		
	}

	@Override
	public void sendSMS() {
		InputSendSMS sendSMSRequest = new InputSendSMS();
		sendSMSRequest.setMessage("");
		sendSMSRequest.setMobileNumber("");
		sendSMSRequest.setPartnerTxnId("");
		sendSMSRequest.setPassword("Enet&pmpl8848");
		sendSMSRequest.setSenderId("");
		sendSMSRequest.setUserName("ENET");
		
		ReturnTransaction transactionResponse = utility.sendSMS(sendSMSRequest);
		utility.printSendSMS(transactionResponse);
	}

	@Override
	public void getSMSTransactionStatus() {
		InputGetSMSTransactionStatus getSMSTransactionStatusRequest = new InputGetSMSTransactionStatus();
//		getSMSTransactionStatusRequest.setPartnerTxnId("");
//		getSMSTransactionStatusRequest.setPassword("");
//		getSMSTransactionStatusRequest.setUserName("");
		
		ReturnTransaction transactionResponse = utility.getSMSTransactionStatus(getSMSTransactionStatusRequest);
		utility.printGetSMSTransactionStatus(transactionResponse);
	}
	
	

}
