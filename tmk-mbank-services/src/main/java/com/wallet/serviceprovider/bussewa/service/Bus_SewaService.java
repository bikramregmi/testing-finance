package com.wallet.serviceprovider.bussewa.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.wallet.serviceprovider.bussewa.BackToPreviousPage;
import com.wallet.serviceprovider.bussewa.MultiPassengerDetailRequest;
import com.wallet.serviceprovider.bussewa.PassengerDetailInput;
import com.wallet.serviceprovider.bussewa.PaymentConfirmation;
import com.wallet.serviceprovider.bussewa.TicketBooking;
import com.wallet.serviceprovider.bussewa.TicketCancellation;
import com.wallet.serviceprovider.bussewa.TicketReconfirmation;
import com.wallet.serviceprovider.bussewa.TripResponse;

public interface Bus_SewaService {

	void getRoutes() throws JsonParseException, JsonMappingException, IOException;
	void getTrips();
	void refresh();
	void bookTicket(TicketBooking ticketBooking);
	void backToPreviousPage(BackToPreviousPage backToPreviousPage);
	void passengerInfo(TripResponse tripResponse , MultiPassengerDetailRequest deatailRequest,PassengerDetailInput passengerDetailInput);
	void confirmTicket(PaymentConfirmation paymentConfirmation);
	void cancelTicket(TicketCancellation ticketCancellation);
	void reconfirmTicket(TicketReconfirmation ticketReconfirmation);
	
}
