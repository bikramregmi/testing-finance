/**
 * 
 */
package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.entity.OAuthClient;

/**
 * @author bibek
 *
 */
public interface IOAuthClientApi {
	
	OAuthClient registerClient(String web_server_redirect_uri, Long bankId);
	
	List<OAuthClient> listAllOAuthClients();
}
