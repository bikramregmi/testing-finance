package com.mobilebanking.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mobilebanking.entity.User;
import com.mobilebanking.entity.UserSession;
import com.mobilebanking.model.UserSessionDTO;
import com.mobilebanking.session.UserDetailsWrapper;



public interface ISessionApi {
	
	void registerNewSession(String sessionId, UserDetailsWrapper principal);

	void removeSession(String tokenKey);

	UserSession getUserSession(String sessionId);

	void refreshSession(String sessionId);

	List<UserSession> getAllUserSession(long userId, boolean includeExpiredSessions);

	void expireSession(String sessionId);

	long countActiveSessions();

	Page<User> findOnlineUsers(Pageable page);

	Page<UserSession> findActiveSessions(Pageable page);

	void registerNewSession(String sessionId, User principal);

	List<UserSessionDTO> getAllActiveUser();

}
