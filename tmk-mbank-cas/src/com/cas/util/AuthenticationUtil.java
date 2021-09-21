package com.cas.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.cas.entity.User;

public class AuthenticationUtil {

	public static final User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		//		if (principal instanceof UserDetailsWrapper) {
		//			return ((UserDetailsWrapper) principal).getUser();
		//		}
		if (principal instanceof User) {
			return ((User) principal);
		}
		return null;
	}
}
