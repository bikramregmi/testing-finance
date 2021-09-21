package com.wallet.serviceprovider.unitedsolutions.serviceImpl;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.wallet.serviceprovider.paypointResponse.PPResponse;
import com.wallet.serviceprovider.unitedsolution.UnitedSolutions;
import com.wallet.serviceprovider.unitedsolutions.response.FlightAvaibilityResponse;
import com.wallet.serviceprovider.unitedsolutions.service.United_Solutions_Service;

@Service
public class United_SolutionServiceImpl implements United_Solutions_Service {

	@Autowired
	private UnitedSolutions unitedSolutions;

	@Override
	public void checkBalance() {
		String strUserId = "EPAY";
		String strAirlineId = "U4";

		try {
			String response = unitedSolutions.checkBalance(strUserId, strAirlineId);
			unitedSolutions.printcheckBalanceResponseResult(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void flightAvailability() {

		String strUserId = "EPAY";
		String strPassword = "PASSWORD";
		String strAgencyId = "PLZ109";
		String strFlightDate = "25-APR-2017";
		String strSectorFrom = "KTM";
		String strSectorTo = "PKR";
		String strNationality = "NP";
		String strTripType = "R";
		String strReturnDate = "26-APR-2017";
		String intAdult = "2";
		String intChild = "1";
		try {
			String flightAvailability = unitedSolutions.flightAvailability(strUserId, strPassword, strAgencyId, strSectorFrom,
					strSectorTo, strFlightDate, strReturnDate, strTripType, strNationality, intAdult, intChild);
			
			InputSource is = new InputSource(new StringReader(flightAvailability));
	        JAXBContext jaxbContext;
	        jaxbContext = JAXBContext.newInstance(FlightAvaibilityResponse.class);
	        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	        FlightAvaibilityResponse response = (FlightAvaibilityResponse) jaxbUnmarshaller.unmarshal(is);
			
	        System.out.println(response.getInBound().getAvailability().size());
	        
			unitedSolutions.printcheckBalanceResponseResult(flightAvailability);
			//flight id from this respose should be use for reservation
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// only to be called if reservation request returns PNR id
	@Override
	public void issueTicket() {
		 String strFlightId = "";
	     String strReturnFlightId  = "";
	     String strContactName = "";
	     String strContactEmail = "";
	     String strContactMobile = "";
	     String strPassengerDetail = "";
		
	     String response = unitedSolutions.issueTicket(strFlightId, strReturnFlightId, strContactName, strContactEmail, strContactMobile, strPassengerDetail);
			unitedSolutions.printIssueTicketResponseResult(response);
	}
	
	@Override
	public void sectorCode() {

		String strUserId = "EPAY";

		String response = unitedSolutions.sectorCode(strUserId);
		unitedSolutions.printsectorCodeResponseResult(response);
	}

	@Override
	public void getItinerary() {

		String strPnoNo = "";
		String strTicketNo = "";
		String strAgencyId = "PLZ109";
		String response = unitedSolutions.getItinerary(strPnoNo, strTicketNo, strAgencyId);
		unitedSolutions.printGetItineraryResponseResult(response);
	}

	@Override
	public void reservation() {
		
		String strFlightId = ""; // string form the flight avaibility response
		String strReturnFlightId = "";
		String response = unitedSolutions.reservation(strFlightId, strReturnFlightId);
		unitedSolutions.printReservationResponseResult(response);

	}

	@Override
	public void cancelReservation() {

		String strUserId = "EPAY";
		String strPassword = "PASSWORD";
		String strAgencyId = "PLZ109";
		String strPnrNo = "";
		String strTicketNo = "";
		String strAirlineId = "";

		String response = unitedSolutions.cancelReservation(strUserId, strPassword, strAgencyId, strPnrNo, strTicketNo,
				strAirlineId);
		unitedSolutions.printCancelReservationResponseResult(response);

	}

	@Override
	public void getFlightDetail() {
		String strUserId = "EPAY";
		 String strFlightId= "";
		 
		 String response = unitedSolutions.getFlightDetail(strUserId, strFlightId);
			unitedSolutions.printgGetFlightDetailResponseResult(response);
	}

	@Override
	public void getPnrDetail() {
		
		String strUserId = "EPAY";
		String strPassword = "PASSWORD";
		String strAgencyId = "PLZ109";
		String strPnrNo = "";
		  String strLastName = "";
		  
		  String response = unitedSolutions.getPnrDetail(strUserId, strPassword, strAgencyId, strPnrNo, strLastName);
			unitedSolutions.printGetPnrDetailResponseResult(response);

	}

	@Override
	public void salesReport() {

		String strUserId = "EPAY";
		String strPassword = "PASSWORD";
		String strAgencyId = "PLZ109";
		 String strFromDate = "";
		 String strToDate = "";
		 
		  String response = unitedSolutions.salesReport(strUserId, strPassword, strAgencyId, strFromDate, strToDate);
			unitedSolutions.printSalesReportResponseResult(response);
	}

	@Override
	public void issueTicketB2B() {
		
		 String strFlightId = "";
	     String strReturnFlightId  = "";
	     String strContactName = "";
	     String strContactEmail = "";
	     String strContactMobile = "";
	     String strPassengerDetail = "";

		  String response = unitedSolutions.issueTicketB2B(strFlightId, strReturnFlightId, strContactName, strContactEmail, strContactMobile, strPassengerDetail);
			unitedSolutions.printIssueTicketResponseResult(response);
	}

	@Override
	public void refund() {
		
		String strTicketNo = "";
		String strAirlineId = "";
		
		  String response = unitedSolutions.refund(strTicketNo, strAirlineId);
			unitedSolutions.printRefundResponseResult(response);
	}

	@Override
	public void reschedule() {
		  String strPnrNo = "";
		  String strAirlineId = "";
		  String strCurrentDate = "";
		  String strNewDate = "";
		  

		  String response = unitedSolutions.reschedule(strPnrNo, strAirlineId, strCurrentDate, strNewDate);
			unitedSolutions.printRescheduleResponseResult(response);
	}

	@Override
	public void rescheduleSave() {
		
		 String strRescheduleId = "";
		 String strOperation = "";
		 
		  String response = unitedSolutions.rescheduleSave(strRescheduleId, strOperation);
					unitedSolutions.printRescheduleSaveResponseResult(response);
		
	}


}
