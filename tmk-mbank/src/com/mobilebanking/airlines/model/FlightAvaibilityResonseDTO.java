package com.mobilebanking.airlines.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.wallet.serviceprovider.unitedsolutions.response.FlightAvaibilityResponse;
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightAvaibilityResonseDTO {

		String Status;
		
		ResponseStatus responseStatus;
		
		String message;
		
		String refresh;
		@JsonProperty("flightAvailability")
		FlightAvaibilityResponse flightAvailability;

		public String getStatus() {
			return Status;
		}

		public void setStatus(String status) {
			Status = status;
		}

		public ResponseStatus getResponseStatus() {
			return responseStatus;
		}

		public void setResponseStatus(ResponseStatus responseStatus) {
			this.responseStatus = responseStatus;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getRefresh() {
			return refresh;
		}

		public void setRefresh(String refresh) {
			this.refresh = refresh;
		}

		public FlightAvaibilityResponse getFlightAvailability() {
			return flightAvailability;
		}

		public void setFlightAvailability(FlightAvaibilityResponse flightAvailability) {
			this.flightAvailability = flightAvailability;
		}
		
}
