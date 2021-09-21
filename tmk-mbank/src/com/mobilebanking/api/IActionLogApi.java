/**
 * 
 */
package com.mobilebanking.api;

import com.mobilebanking.model.ActionLogDTO;
import com.mobilebanking.model.UserType;

/**
 * @author bibek
 *
 */
public interface IActionLogApi {
	
	ActionLogDTO saveActionLog(String remarks, long customerId, UserType userType, long userId) throws Exception;
}
