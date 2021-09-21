package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.entity.SessionLog;
import com.mobilebanking.entity.User;




public interface ISessionLogApi {

	void logUserLoggedIn(long userId, String sessionId, String remoteAddress);

	void logUserLoggedOut(long userId, String sessionId);

	long getTotalOnlineUsers();

	List<SessionLog> getUserHistory(long userId);

	void endUserSession(long userId);

	String getUserAccountActivity();

	String getUserAccountActivity(User u);
}
