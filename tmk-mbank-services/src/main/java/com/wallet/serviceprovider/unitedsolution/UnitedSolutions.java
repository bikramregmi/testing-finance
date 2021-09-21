package com.wallet.serviceprovider.unitedsolution;

import javax.xml.bind.JAXBElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class UnitedSolutions extends WebServiceGatewaySupport  implements UnitedSolutionsInterface {
	
	private static final Logger log = LoggerFactory.getLogger(UnitedSolutions.class);
	
	ObjectFactory unitedSolutionObjectFactory = new ObjectFactory();

//	http://dev.usbooking.org/us/UnitedSolutions?wsdl
	
	/*Login ID   EPAY
	Password   PASSWORD
	AgentID    PLZ109*/
	
	@Override
	public String reservation(String strFlightId, String strReturnFlightId) {
		Reservation  reservation = new Reservation();
		reservation.setStrFlightId(strFlightId);
		reservation.setStrReturnFlightId(strReturnFlightId);
		
		JAXBElement<Reservation> objectFactoryReservation = unitedSolutionObjectFactory
				.createReservation(reservation);
		
		JAXBElement<SectorCodeResponse> reservationResponse =(JAXBElement<SectorCodeResponse>) getWebServiceTemplate().marshalSendAndReceive("http://dev.usbooking.org/us/UnitedSolutions?wsdl",
				objectFactoryReservation, new SoapActionCallback("http://booking.us.org/"));
		
		return reservationResponse.getValue().getReturn();
	}

	public void printReservationResponseResult(String response){
		log.info("Response--> " + response);
	}
	
	@Override
	public String issueTicket(String strFlightId, String strReturnFlightId, String strContactName,
			String strContactEmail, String strContactMobile, String strPassengerDetail) {
		
		IssueTicket issueTicket = new IssueTicket();
		
		issueTicket.setStrContactEmail(strContactEmail);
		issueTicket.setStrContactMobile(strContactMobile);
		issueTicket.setStrContactName(strContactName);
		issueTicket.setStrPassengerDetail(strPassengerDetail);
		issueTicket.setStrReturnFlightId(strReturnFlightId);
		issueTicket.setStrFlightId(strFlightId);
		
		JAXBElement<IssueTicket> objectFactoryIssueTicket = unitedSolutionObjectFactory
				.createIssueTicket(issueTicket);
		
		JAXBElement<IssueTicketResponse> issueTicketResponse =(JAXBElement<IssueTicketResponse>) getWebServiceTemplate().marshalSendAndReceive("http://dev.usbooking.org/us/UnitedSolutions?wsdl",
				objectFactoryIssueTicket, new SoapActionCallback("http://booking.us.org/"));
		
		return issueTicketResponse.getValue().getReturn();
	}

	public void printIssueTicketResponseResult(String response){
		log.info("Response--> " + response);
	}
	
	@Override
	public String sectorCode(String strUserId) {
		SectorCode sectorCode  = new SectorCode();
		sectorCode.setStrUserId(strUserId);
		
		JAXBElement<SectorCode> objectFactorySectorCode = unitedSolutionObjectFactory
				.createSectorCode(sectorCode);
		
		JAXBElement<SectorCodeResponse> sectorCodeResponse =(JAXBElement<SectorCodeResponse>) getWebServiceTemplate().marshalSendAndReceive("http://dev.usbooking.org/us/UnitedSolutions?wsdl",
				objectFactorySectorCode, new SoapActionCallback("http://booking.us.org/"));
		
		return sectorCodeResponse.getValue().getReturn();
	}
	
	public void printsectorCodeResponseResult(String response){
		log.info("Response--> " + response);
	}

	@Override
	public String checkBalance(String strUserId,String strAirlineId){
		CheckBalance checkBalance = new CheckBalance();
		checkBalance.setStrAirlineId(strAirlineId);
		checkBalance.setStrUserId(strUserId);
//		checkBalance.setStrervice MyService = new UnitedSolutionsService();
//		UnitedSolutions bookingservice=(UnitedSolutions) MyService.getUnitedSolutionsPort();
//		bookingservice.checkBalance(strUserId, strAirlineId);
		
		JAXBElement<CheckBalance> objectFactoryCheckBalance = unitedSolutionObjectFactory
				.createCheckBalance(checkBalance);
		
		JAXBElement<CheckBalanceResponse> checkBalanceResponse =(JAXBElement<CheckBalanceResponse>) getWebServiceTemplate().marshalSendAndReceive("http://dev.usbooking.org/us/UnitedSolutions?wsdl",
				objectFactoryCheckBalance, new SoapActionCallback("http://booking.us.org/"));
		
		return checkBalanceResponse.getValue().getReturn();
	}
	
	public void printcheckBalanceResponseResult(String response){
		log.info("Response--> " + response);
	}

	@Override
	public String flightAvailability(String strUserId, String strPassword, String strAgencyId, String strSectorFrom,
			String strSectorTo, String strFlightDate, String strReturnDate, String strTripType, String strNationality,
			String intAdult, String intChild) {
		
		FlightAvailability  flightAvailability = new FlightAvailability(); 
		
		flightAvailability.setStrUserId(strUserId);
		flightAvailability.setStrPassword(strPassword);
		flightAvailability.setStrAgencyId(strAgencyId);
		flightAvailability.setStrFlightDate(strFlightDate);
		flightAvailability.setStrSectorFrom(strSectorFrom);
		flightAvailability.setStrSectorTo(strSectorTo);
		flightAvailability.setStrNationality(strNationality);
		flightAvailability.setStrTripType(strTripType);
		flightAvailability.setStrReturnDate(strReturnDate);
		flightAvailability.setIntAdult(intAdult);
		flightAvailability.setIntChild(intChild);
		
		JAXBElement<FlightAvailability> objectFactoryflightAvailability = unitedSolutionObjectFactory
				.createFlightAvailability(flightAvailability);
		
		JAXBElement<FlightAvailabilityResponse> flightAvaibilityResponse =(JAXBElement<FlightAvailabilityResponse>) getWebServiceTemplate().marshalSendAndReceive("http://dev.usbooking.org/us/UnitedSolutions?wsdl",
				objectFactoryflightAvailability, new SoapActionCallback("http://booking.us.org/"));
		
		return flightAvaibilityResponse.getValue().getReturn();
	}
	public void printFlightAvailabilityResponseResult(String response){
		log.info("Response--> " + response);
	}

	@Override
	public String getItinerary(String strPnoNo, String strTicketNo, String strAgencyId) {
		
		GetItinerary getItinerary = new GetItinerary();
		getItinerary.setStrAgencyId(strAgencyId);
		getItinerary.setStrPnoNo(strPnoNo);
		getItinerary.setStrTicketNo(strTicketNo);
		
		JAXBElement<GetItinerary> objectFactoryGetItinerary = unitedSolutionObjectFactory
				.createGetItinerary(getItinerary);
		
		JAXBElement<GetItineraryResponse> flightAvaibilityResponse =(JAXBElement<GetItineraryResponse>) getWebServiceTemplate().marshalSendAndReceive("http://dev.usbooking.org/us/UnitedSolutions?wsdl",
				objectFactoryGetItinerary, new SoapActionCallback("http://booking.us.org/"));
		
		return flightAvaibilityResponse.getValue().getReturn();
	}
	public void printGetItineraryResponseResult(String response){
		log.info("Response--> " + response);
	}

	@Override
	public String cancelReservation(String strUserId, String strPassword, String strAgencyId, String strPnrNo,
			String strTicketNo, String strAirlineId) {
		
		CancelReservation cancelReservation = new CancelReservation();
		
		cancelReservation.setStrUserId(strUserId);
		cancelReservation.setStrPassword(strPassword);
		cancelReservation.setStrAgencyId(strAgencyId);
		cancelReservation.setStrAirlineId(strAirlineId);
		cancelReservation.setStrTicketNo(strTicketNo);
		cancelReservation.setStrPnrNo(strPnrNo);
		
		JAXBElement<CancelReservation> objectFactoryCancelReservation = unitedSolutionObjectFactory
				.createCancelReservation(cancelReservation);
		
		JAXBElement<CancelReservationResponse> cancelReservationResponse =(JAXBElement<CancelReservationResponse>) getWebServiceTemplate().marshalSendAndReceive("http://dev.usbooking.org/us/UnitedSolutions?wsdl",
				objectFactoryCancelReservation, new SoapActionCallback("http://booking.us.org/"));
		
		return cancelReservationResponse.getValue().getReturn();
	}
	
	public void printCancelReservationResponseResult(String response){
		log.info("Response--> " + response);
	}


	@Override
	public String getFlightDetail(String strUserId, String strFlightId) {

		GetFlightDetail getFlightDetail = new GetFlightDetail();
		
		getFlightDetail.setStrFlightId(strFlightId);
		getFlightDetail.setStrUserId(strUserId);
		
		JAXBElement<GetFlightDetail> objectFactoryGetFlightDetail= unitedSolutionObjectFactory
				.createGetFlightDetail(getFlightDetail);
		
		JAXBElement<GetFlightDetailResponse> cancelReservationResponse =(JAXBElement<GetFlightDetailResponse>) getWebServiceTemplate().marshalSendAndReceive("http://dev.usbooking.org/us/UnitedSolutions?wsdl",
				objectFactoryGetFlightDetail, new SoapActionCallback("http://booking.us.org/"));
		
		return cancelReservationResponse.getValue().getReturn();
	}
	
	public void printgGetFlightDetailResponseResult(String response){
		log.info("Response--> " + response);
	}

	@Override
	public String salesReport(String strUserId, String strPassword, String strAgencyId, String strFromDate,
			String strToDate) {
		SalesReport salesReport = new SalesReport();
		
		salesReport.setStrUserId(strUserId);
		salesReport.setStrPassword(strPassword);
		salesReport.setStrAgencyId(strAgencyId);
		salesReport.setStrFromDate(strFromDate);
		salesReport.setStrToDate(strToDate);
		
		JAXBElement<SalesReport> objectFactorySalesReport= unitedSolutionObjectFactory
				.createSalesReport(salesReport);
		
		JAXBElement<SalesReportResponse> salesReportResponse =(JAXBElement<SalesReportResponse>) getWebServiceTemplate().marshalSendAndReceive("http://dev.usbooking.org/us/UnitedSolutions?wsdl",
				objectFactorySalesReport, new SoapActionCallback("http://booking.us.org/"));
		
		
		return salesReportResponse.getValue().getReturn();
	}
	
	public void printSalesReportResponseResult(String response){
		log.info("Response--> " + response);
	}

	@Override
	public String issueTicketB2B(String strFlightId, String strReturnFlightId, String strContactName,
			String strContactEmail, String strContactMobile, String strPassengerDetail) {

		IssueTicketB2B issueTicketB2B= new IssueTicketB2B();
		issueTicketB2B.setStrContactEmail(strContactEmail);
		issueTicketB2B.setStrContactName(strContactName);
		issueTicketB2B.setStrContactMobile(strContactMobile);
		issueTicketB2B.setStrFlightId(strFlightId);
		issueTicketB2B.setStrReturnFlightId(strReturnFlightId);
		issueTicketB2B.setStrPassengerDetail(strPassengerDetail);
		
		JAXBElement<IssueTicketB2B> objectFactoryIssueTicketB2B= unitedSolutionObjectFactory
				.createIssueTicketB2B(issueTicketB2B);
		
		JAXBElement<IssueTicketB2BResponse> issueTicketB2BResponse =(JAXBElement<IssueTicketB2BResponse>) getWebServiceTemplate().marshalSendAndReceive("http://dev.usbooking.org/us/UnitedSolutions?wsdl",
				objectFactoryIssueTicketB2B, new SoapActionCallback("http://booking.us.org/"));
		
		return issueTicketB2BResponse.getValue().getReturn();
	}
	
	public void printSalesIssueTicketResponseResult(String response){
		log.info("Response--> " + response);
	}


	@Override
	public String refund(String strTicketNo, String strAirlineId) {
		Refund refund = new Refund();
		refund.setStrAirlineId(strAirlineId);
		refund.setStrTicketNo(strTicketNo);
		
		JAXBElement<Refund> objectFactoryRefund= unitedSolutionObjectFactory
				.createRefund(refund);
		
		JAXBElement<RefundResponse> refundResponse =(JAXBElement<RefundResponse>) getWebServiceTemplate().marshalSendAndReceive("http://dev.usbooking.org/us/UnitedSolutions?wsdl",
				objectFactoryRefund, new SoapActionCallback("http://booking.us.org/"));
		
		return refundResponse.getValue().getReturn();
	}

	public void printRefundResponseResult(String response){
		log.info("Response--> " + response);
	}
	
	@Override
	public String reschedule(String strPnrNo, String strAirlineId, String strCurrentDate, String strNewDate) {
		
		Reschedule reschedule = new Reschedule();
		
		reschedule.setStrAirlineId(strAirlineId);
		reschedule.setStrCurrentDate(strCurrentDate);
		reschedule.setStrNewDate(strNewDate);
		reschedule.setStrPnrNo(strPnrNo);
		
		JAXBElement<Reschedule> objectFactoryReschedule= unitedSolutionObjectFactory
				.createReschedule(reschedule);
		
		JAXBElement<RefundResponse> rescheduleResponse =(JAXBElement<RefundResponse>) getWebServiceTemplate().marshalSendAndReceive("http://dev.usbooking.org/us/UnitedSolutions?wsdl",
				objectFactoryReschedule, new SoapActionCallback("http://booking.us.org/"));
		
		return rescheduleResponse.getValue().getReturn();
	}
	
	public void printRescheduleResponseResult(String response){
		log.info("Response--> " + response);
	}

	@Override
	public String rescheduleSave(String strRescheduleId, String strOperation) {
		
		RescheduleSave rescheduleSave = new RescheduleSave();
		rescheduleSave.setStrOperation(strOperation);
		rescheduleSave.setStrRescheduleId(strRescheduleId);
		
		JAXBElement<RescheduleSave> objectFactoryRescheduleSave= unitedSolutionObjectFactory
				.createRescheduleSave(rescheduleSave);
		
		JAXBElement<RescheduleSaveResponse> rescheduleSaveResponse =(JAXBElement<RescheduleSaveResponse>) getWebServiceTemplate().marshalSendAndReceive("http://dev.usbooking.org/us/UnitedSolutions?wsdl",
				objectFactoryRescheduleSave, new SoapActionCallback("http://booking.us.org/"));
		
		return rescheduleSaveResponse.getValue().getReturn();
		
	}
	
	public void printRescheduleSaveResponseResult(String response){
		log.info("Response--> " + response);
	}

	@Override
	public String getPnrDetail(String strUserId, String strPassword, String strAgencyId, String strPnrNo,
			String strLastName) {
		
		GetPnrDetail getPnrDetail = new GetPnrDetail();
		
		getPnrDetail.setStrUserId(strUserId);
		getPnrDetail.setStrPassword(strPassword);
		getPnrDetail.setStrAgencyId(strAgencyId);
		getPnrDetail.setStrPnrNo(strPnrNo);
		getPnrDetail.setStrLastName(strLastName);
		
		JAXBElement<GetPnrDetail> objectFactoryGetPnrDetail= unitedSolutionObjectFactory
				.createGetPnrDetail(getPnrDetail);
		
		JAXBElement<GetPnrDetailResponse> getPnrDetailResponse =(JAXBElement<GetPnrDetailResponse>) getWebServiceTemplate().marshalSendAndReceive("http://dev.usbooking.org/us/UnitedSolutions?wsdl",
				objectFactoryGetPnrDetail, new SoapActionCallback("http://booking.us.org/"));
		
		
		return getPnrDetailResponse.getValue().getReturn();
	}

	public void printGetPnrDetailResponseResult(String response){
		log.info("Response--> " + response);
	}


}
