/**
 * 
 */
package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.model.UserLogDto;

/**
 * @author bibek
 *
 */
public interface IUserLogApi {
	
	void save(String id, String remarks, boolean isLoggedIn) throws Exception;
	
	List<UserLogDto> getLogsByUser(long userId);

}
