package com.cas.api.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.cas.api.IAuthenticationApi;
import com.cas.api.IUserApi;
import com.cas.auth.SessionLoggingStrategy;
import com.cas.entity.User;
import com.cas.exceptions.ClientException;
import com.cas.model.UserDTO;

public class AuthenticationApi implements IAuthenticationApi {

	private final AuthenticationManager authenticationManager;
	private final IUserApi userApi;
	private final SessionLoggingStrategy sessionLoggingStrategy;
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public AuthenticationApi(AuthenticationManager authenticationManager,
			IUserApi userApi, SessionLoggingStrategy sessionLoggingStrategy) {
		this.authenticationManager = authenticationManager;
		this.userApi = userApi;
		this.sessionLoggingStrategy = sessionLoggingStrategy;
	}

	@Override
	public boolean Authenticate(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			Exception {
		UserDTO dto = new UserDTO();
		dto.setUserName(request.getHeader("username"));
		dto.setPassword(request.getHeader("password"));
		dto.setIpAddress(request.getRemoteAddr());
		System.out.println("REMOTE ADD:" + request.getRemoteAddr());
		System.out.println("REMOTE HOST:" + request.getRemoteHost());
		if (checkUser(dto)) {
			if (checkIp(dto)) {
				if (Login(dto, request, response)) {
					return true;
				} else {
					throw new ClientException("Invalid Credential");
				}
			} else {
				throw new ClientException("Un-Authorized Access");
			}
		} else {
			throw new ClientException("Invalid Credential");
		}
	}

	private boolean checkUser(UserDTO dto) {
		User user = userApi.searchByUserName(dto.getUserName());
		if (user == null) {
			return false;
		} else {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			if (!encoder.matches(dto.getPassword(), user.getPassword())) {
				return false;
			}
			return true;
		}
	}

	private boolean checkIp(UserDTO dto) {
		User user = userApi.searchByUserIP(dto);
		if (user == null) {
			return false;
		}
		return true;
	}

	private boolean Login(UserDTO dto, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			Exception {
		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
					dto.getUserName(), dto.getPassword());
			Authentication auth = authenticationManager.authenticate(token);
			SecurityContext securityContext = SecurityContextHolder
					.getContext();
			System.out.println("AUTH:" + auth.isAuthenticated());
			if (auth.isAuthenticated()) {
				securityContext.setAuthentication(auth);
				SecurityContextHolder.getContext().setAuthentication(auth);
				HttpSession session = request.getSession(true);
				session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
				Authentication authentication = SecurityContextHolder
						.getContext().getAuthentication();
				sessionLoggingStrategy.onAuthentication(authentication,
						request, response);
				return true;
			} else {
				SecurityContextHolder.getContext().setAuthentication(null);
				return false;
			}

		} catch (Exception e) {
			SecurityContextHolder.getContext().setAuthentication(null);
			return false;
		}

	}

}
