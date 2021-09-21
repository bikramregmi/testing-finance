package com.mobilebanking.session;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mobilebanking.entity.User;
import com.mobilebanking.model.Status;


public class UserDetailsWrapper implements UserDetails, Serializable, Comparable<UserDetailsWrapper> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Collection<GrantedAuthority> authorities;
	private final User user;
	private String remoteAddress;

	public UserDetailsWrapper(User user, Collection<GrantedAuthority> authorities) {
		this.user = user;
		this.authorities = authorities;
	}

	public UserDetailsWrapper(User user, Collection<GrantedAuthority> authorities, String remoteAddress) {
		this.user = user;
		this.authorities = authorities;
		this.remoteAddress = remoteAddress;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword(); 
	}

	@Override
	public String getUsername() {
		return user.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		if (user.getStatus().equals(Status.Deleted)) {
			return false;
		}
		return true;
	}
 
	@Override
	public boolean isAccountNonLocked() {
		if (user.getStatus().equals(Status.Active)) {
			return true;
		}
		return false;
		// return !user.isDisabled();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		if (user.getStatus().equals(Status.Active)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isEnabled() {
		if (user.getStatus().equals(Status.Active)) {
			return true;
		}
		return false;
	}

	public User getUser() {
		return user;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserDetailsWrapper) {
			return ((UserDetailsWrapper) obj).getUser().getId().equals(user.getId());
		}
		return false;
	}

	@Override
	public int compareTo(UserDetailsWrapper o) {
		return 0;
	}

	@Override
	public String toString() {
		return "user" + user.getId();
	}

	@Override
	public int hashCode() {
		return Integer.valueOf(user.getId() + "");
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

}
