package com.wallet.serviceprovider.khalti;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class CustomErrorHandler implements ResponseErrorHandler {
	private static final Logger log = LoggerFactory.getLogger(CustomErrorHandler.class);

	private List<HttpStatus> goodStatus;

	private String acceptableStatus = "OK";

	public CustomErrorHandler() {
		goodStatus = Arrays.stream(acceptableStatus.split(",")).map(HttpStatus::valueOf).collect(Collectors.toList());
	}

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		  return !goodStatus.contains(response.getStatusCode());
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		log.error("Response error: {} {}", response.getStatusCode(), response.getStatusText());

	}

}
