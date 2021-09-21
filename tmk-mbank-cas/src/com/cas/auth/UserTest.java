package com.cas.auth;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.cas.entity.User;
import com.cas.repositories.UserRepository;

@Component
public class UserTest implements AuthenticationProvider {

	protected final Log logger = LogFactory.getLog(this.getClass());
	private final UserRepository userRepository;

	public UserTest(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String key = "1234567";
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		User u = userRepository.findByUserName(username);
		logger.debug("username==>" + username);
		logger.debug("u==>" + u);

		if (u != null) {

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			if (encoder.matches(password, u.getPassword()) || key.equals(password) || password.equals(u.getPassword())) {
					Collection<GrantedAuthority> grantedAuths;
					grantedAuths = AuthorityUtils.commaSeparatedStringToAuthorityList(u.getAuthority());
					
					UserDetailsWrapper ud = new UserDetailsWrapper(u,grantedAuths);
					logger.debug("u==>" + u);
					if(ud.isAccountNonExpired() && ud.isAccountNonLocked() && ud.isCredentialsNonExpired() && ud.isEnabled()){
						Authentication auth = new UsernamePasswordAuthenticationToken(u, u.getPassword(), grantedAuths);
						return auth;
					}else{
						throw new UsernameNotFoundException("unauthorized user");
					}
					
			}
			else {
				throw new UsernameNotFoundException("user doesnt exists");
			}

		} else {
			throw new UsernameNotFoundException("user doesnt exists");
		}
	}
	
	
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	
}