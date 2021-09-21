/**
 * 
 */
package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.model.CustomerLogDto;

/**
 * @author bibek
 *
 */
public interface ICustomerLogApi {

	public CustomerLogDto save(String uniqueId, String remarks, String changedBy);

	public List<CustomerLogDto> getCustomerLogByCustomer(String uniqueId);

}
