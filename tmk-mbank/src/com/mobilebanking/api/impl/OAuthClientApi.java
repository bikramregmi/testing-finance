/**
 * 
 */
package com.mobilebanking.api.impl;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IOAuthClientApi;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankOAuthClient;
import com.mobilebanking.entity.OAuthClient;
import com.mobilebanking.repositories.BankOAuthClientRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.OAuthClientRepository;

/**
 * @author bibek
 *
 */
@Service
public class OAuthClientApi implements IOAuthClientApi {
	
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	@Autowired
	private OAuthClientRepository oAuthClientRepository;
	@Autowired
	private BankOAuthClientRepository bankOAuthClientRepository;
	@Autowired
	private BankRepository bankRepository;
	
	@Override
	public OAuthClient registerClient(String web_server_redirect_uri, Long bankId) {
		OAuthClient oAuthClient = this.getClientDetails();
		oAuthClient.setWebServerRedirectUri(web_server_redirect_uri);
        oAuthClient = oAuthClientRepository.save(oAuthClient);
        if (bankId != null) {
            Bank bank = bankRepository.findOne(bankId);
            if (bank !=  null) {
            	BankOAuthClient bankOAuthClient = new BankOAuthClient();
                bankOAuthClient.setBank(bank);
                bankOAuthClient.setoAuthClientId(oAuthClient.getClientId());
                bankOAuthClientRepository.save(bankOAuthClient);
            }
        }
        		return oAuthClient ;
	}
	
	public OAuthClient getClientDetails() {
		OAuthClient oAuthClient = new OAuthClient();
		oAuthClient.setResourceIds("mBankTest");
		oAuthClient.setAccessTokenValidity("900");
		oAuthClient.setRefreshTokenValidity("0");
		oAuthClient.setAdditionalInformation("No Additional Information");
		oAuthClient.setAuthorities("ROLE_CLIENT,ROLE_TRUSTED_CLIENT");
		oAuthClient.setAuthorizedGrantTypes("client_credentials,password,authorization_code,refresh_token");
		oAuthClient.setClientId(getClientId(10));
		oAuthClient.setClientSecret(getClientSecret());
		oAuthClient.setAutoapprove(true);
		oAuthClient.setScope("read,write,trust");
		
		return oAuthClient;
	}
	
	public String getClientSecret(){
		Random random = new Random();
		int id = random.nextInt(555555-444444+1)+555555-444444;
		return Integer.toString(id);
	}
	
	public static String getClientId(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
		}

	@Override
	public List<OAuthClient> listAllOAuthClients() {
		List<OAuthClient> oAuthClients = oAuthClientRepository.getAllOAuthClient();
		return oAuthClients;
	}
}
