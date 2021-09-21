package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.model.WebServiceDTO;
import com.mobilebanking.util.ClientException;

public interface IWebServiceApi {

	void createUser(WebServiceDTO dto) throws ClientException;

	List<WebServiceDTO> getAlluser();

	WebServiceDTO findByUserAndkey(String clientId, String clientKey);

	WebServiceDTO findByAccessKey(String accessKey);

}
