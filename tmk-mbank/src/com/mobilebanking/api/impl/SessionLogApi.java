package com.mobilebanking.api.impl;


import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mobilebanking.api.ISessionApi;
import com.mobilebanking.api.ISessionLogApi;
import com.mobilebanking.entity.SessionLog;
import com.mobilebanking.entity.User;
import com.mobilebanking.entity.UserSession;
import com.mobilebanking.repositories.SessionLogRepository;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.TimeDifferenceCalUtil;

@Service
public class SessionLogApi implements ISessionLogApi {

	@Autowired
	private SessionLogRepository sessionLogRepository;
	@Autowired
	private ISessionApi sessionApi;
	private final Logger logger = LoggerFactory.getLogger(SessionLogApi.class);

	/*public SessionLogApi(SessionLogRepository sessionLogRepository, ISessionApi sessionApi) {
		this.sessionLogRepository = sessionLogRepository;
		this.sessionApi = sessionApi;
	}
*/
	@Override
	@Transactional
	public void logUserLoggedIn(long userId, String sessionId, String remoteAddress) {
		logger.debug("Logging user Logged in:" + userId);
		SessionLog s = new SessionLog();
		s.setUserId(userId);
		s.setLoggedIn(new Date());
		s.setSessionId(sessionId);
		s.setRemoteAddress(remoteAddress);
		sessionLogRepository.save(s);
	}

	@Override
	@Transactional
	public void logUserLoggedOut(long userId, String sessionId) {
		SessionLog s = sessionLogRepository.findBySessionId(sessionId);
		if (s != null && s.getUserId() == userId) {
			s.setUserId(userId);
			s.setLoggedOut(new Date());
			s.setSessionId(sessionId);

			sessionLogRepository.save(s);
		}
	}

	@Override
	public long getTotalOnlineUsers() {
		return sessionApi.countActiveSessions();
	}

	@Override
	@Transactional
	public void endUserSession(long userId) {
		List<UserSession> sessions = sessionApi.getAllUserSession(userId, false);
		for (UserSession s : sessions) {
			sessionApi.expireSession(s.getSessionId());
		}
	}

	
	private SessionLog findUserSessionById(String sessionId) {
		return sessionLogRepository.findBySessionId(sessionId);
	}

	@Override
	public List<SessionLog> getUserHistory(final long userId) {
		Page<SessionLog> sessionLogs = sessionLogRepository.findAll(new Specification<SessionLog>() {

			@Override
			public Predicate toPredicate(Root<SessionLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("userId"), userId);
			}
		}, new PageRequest(0, 10, new Sort(new Order(Direction.DESC, "id"))));

		return sessionLogs.getContent();
	}

	@Override
	public String getUserAccountActivity() {
		SessionLog login = getUserSessionLogin(AuthenticationUtil.getCurrentUser().getId());

		if (login == null) {
			return "None";
		}
		String logout = getUserSessionLogOut(AuthenticationUtil.getCurrentUser().getId(), login.getSessionId());

		if (logout == null || logout.isEmpty()) {
			return "None";
		}

		TimeDifferenceCalUtil timeDiff = new TimeDifferenceCalUtil(logout, login.getLoggedIn().toString());
		return timeDiff.getTimeDifference();
	} 

	@Override
	public String getUserAccountActivity(User u) {
		SessionLog login = getUserSessionLogin(u.getId());

		if (login == null) {
			return "None";
		}
		String logout = getUserSessionLogOut(u.getId(), login.getSessionId());

		if (logout == null || logout.isEmpty()) {
			return "None";
		}

		TimeDifferenceCalUtil timeDiff = new TimeDifferenceCalUtil(logout, login.getLoggedIn().toString());
		return timeDiff.getTimeDifference();
	}

	public SessionLog getUserSessionLogin(long userId) {
		List<UserSession> sessions = sessionApi.getAllUserSession(userId, false);
		if (sessions.size() > 0) {
			return findUserSessionById(sessions.get(0).getSessionId());
		}

		return null;
	}

	public String getUserSessionLogOut(long userId, String sessionId) {
		List<SessionLog> sl = getSessionLogs(userId);

		for (SessionLog log : sl) {
			if (!log.getSessionId().equals(sessionId)) {
				return (log.getLoggedOut() == null ? log.getLoggedIn().toString() : log.getLoggedOut().toString());
			}
		}
		return null;
	}

	public List<SessionLog> getSessionLogs(final long userId) {
		Page<SessionLog> sl = sessionLogRepository.findAll(new Specification<SessionLog>() {

			@Override
			public Predicate toPredicate(Root<SessionLog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				return cb.equal(root.get("userId"), userId);
			}
		}, new PageRequest(0, 5, new Sort(new Order(Direction.DESC, "loggedIn"))));
		return sl.getContent();
	}


}
