package com.cas.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cas.auth.UserDetailsWrapper;
import com.cas.entity.User;
import com.cas.entity.UserSession;


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

	void setTokenKey(String tokenKey);

}
