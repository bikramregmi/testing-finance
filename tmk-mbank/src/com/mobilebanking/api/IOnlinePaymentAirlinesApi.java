package com.mobilebanking.api;

import java.util.HashMap;

import javax.xml.bind.JAXBException;

import com.mobilebanking.airlines.model.FlightRequestDTO;
import com.mobilebanking.airlines.model.FlightReservationDTO;
import com.mobilebanking.airlines.model.FlightReservationResponse;
import com.mobilebanking.airlines.model.PassengerTotalDto;
import com.mobilebanking.plasmatech.FlightAvailabilityRes;


public interface IOnlinePaymentAirlinesApi {
	Object getSectorCodeAndNationality(HashMap<String,String> hashRequest) throws JAXBException;

	FlightAvailabilityRes getAvailableFlights(FlightRequestDTO flightRequestDto,Boolean isApp);

//	FlightReservationResponse doReservation(String serviceIdentifier, String flightId, String returnFlightId,HashMap<String, String> myhash);

	HashMap<String, Object> executePayment(HashMap<String, String> myHash,PassengerTotalDto pessangerDto);

	FlightReservationResponse reserveFlight(FlightReservationDTO flightRequestDto,Long customerId);
	

}
