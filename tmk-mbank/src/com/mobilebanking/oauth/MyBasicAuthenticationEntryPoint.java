/**
 * 
 */
package com.mobilebanking.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

/**
 * @author bibek
 *
 */
public class MyBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
	

	@Override
	public void commence(HttpServletRequest request,HttpServletResponse response,AuthenticationException authException){
		System.out.println(request.getParameterNames());
		response.setHeader("WWW-Authenticate","Unauthorized client");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		System.out.println("Authentication Error : "+authException.getMessage());
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
	     setRealmName("mbank");
	     super.afterPropertiesSet();
	}
}
