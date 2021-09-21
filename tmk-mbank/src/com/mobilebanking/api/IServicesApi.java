package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.model.ServicesDTO;
import com.mobilebanking.util.ClientException;



public interface IServicesApi {

	ServicesDTO getServicesByUniqueId(long identifier);
	ServicesDTO getServicesByIdentifier(String identifier);

	List<ServicesDTO> getAllServiceExceptIncluded(long projectId);


	void saveService(ServicesDTO serviceDto) throws ClientException;
	List<ServicesDTO> getAllServiceByMerchant(String merchantName);
	void editService(ServicesDTO serviceDto,String merchantName);
	void deleteService(Long serviceId);
	List<ServicesDTO> getAllService();
	


}
