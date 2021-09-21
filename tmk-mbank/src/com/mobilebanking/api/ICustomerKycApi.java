/**
 * 
 */
package com.mobilebanking.api;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import com.mobilebanking.model.CustomerKycDTO;

/**
 * @author bibek
 *
 */
public interface ICustomerKycApi {
	
	CustomerKycDTO save(CustomerKycDTO customerKycDto) throws JSONException, IOException;
	
	List<CustomerKycDTO> getCustomerKycByCustomer(String uniqueId);
	
	CustomerKycDTO getCustomerKycById(long id);
}
