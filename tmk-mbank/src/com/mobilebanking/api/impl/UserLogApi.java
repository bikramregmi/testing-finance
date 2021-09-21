/**
 * 
 */
package com.mobilebanking.api.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mobilebanking.api.IUserLogApi;
import com.mobilebanking.entity.User;
import com.mobilebanking.entity.UserLog;
import com.mobilebanking.model.UserLogDto;
import com.mobilebanking.repositories.UserLogRepository;
import com.mobilebanking.repositories.UserRepository;

/**
 * @author bibek
 *
 */
@Repository
public class UserLogApi implements IUserLogApi {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserLogRepository userLogRepository;
	
	@Override
	public void save(String id, String remarks, boolean isLoggedIn) throws Exception {
		UserLog userLog = new UserLog();
		User user = userRepository.findByUsername(id);
		if (user == null) {
			user = userRepository.findOne(Long.parseLong(id));
		}
		
		userLog.setUser(user);
		userLog.setRemarks(remarks);
		if (isLoggedIn) {
			userLog.setLastLoggedIn(new Date());
		}
		
		userLogRepository.save(userLog);
	}

	/* (non-Javadoc)
	 * @see com.mobilebanking.api.IUserLogApi#getLogsByUser(long)
	 */
	@Override
	public List<UserLogDto> getLogsByUser(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
