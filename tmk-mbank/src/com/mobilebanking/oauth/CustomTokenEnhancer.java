package com.mobilebanking.oauth;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Service;

import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankBranch;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.SmsLog;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.SmsStatus;
import com.mobilebanking.model.SmsType;
import com.mobilebanking.model.UserType;
import com.mobilebanking.repositories.BankBranchRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.repositories.SmsLogRepository;
import com.mobilebanking.repositories.UserRepository;
import com.wallet.sms.SparrowSmsResponseCode;
import com.wallet.sms.service.SendSparrowSmsApi;

@Service
public class CustomTokenEnhancer implements TokenEnhancer {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private SendSparrowSmsApi sparrowSmsApi;
	@Autowired
	private SmsLogRepository smsLogRepository;
	@Autowired
	private BankBranchRepository bankBranchRepository;
	@Autowired
	private BankRepository bankRepository;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		return accessToken;
		/*Map<String,Object> additionalInformation = new HashMap<String, Object>();
		  Map<String, String> smsResponse = new HashMap<String, String>();
		  try {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetailsWrapper) {
				User user =  ((UserDetailsWrapper) principal).getUser();
				if (user.getWebServiceLoginCount() == 0) {
					additionalInformation.put("firstLogin", true);
					
					Customer customer = customerRepository.findById(user.getAssociatedId());
					String message = StringConstants.VERIFICATION_MESSAGE;
					message = message.replace("{verificationCode}", user.getVerificationCode());
					smsResponse = sparrowSmsApi.sendSms(message, customer.getMobileNumber());
					if (smsResponse.get("code").equals("200")) {
						createSmsLog(
								customer, SmsType.VERIFICATION, message, SmsStatus.DELIVERED, smsResponse.get("code"));
						user.setWebServiceLoginCount(user.getWebServiceLoginCount()+1);
						userRepository.save(user);
					} else {
						createSmsLog(customer, SmsType.VERIFICATION, message, SmsStatus.FAILED, smsResponse.get("code"));
					}
					
				} else {
					additionalInformation.put("isFirstLogin", false);
					user.setWebServiceLoginCount(user.getWebServiceLoginCount()+1);
					userRepository.save(user);
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
		
		customAccessToken.setAdditionalInformation(additionalInformation);
		return customAccessToken;*/
	}
	
	public void createSmsLog(Customer customer, SmsType smsType, String message, SmsStatus smsStatus, String responseCode) {
		SmsLog smsLog = new SmsLog();
		User createdBy = customer.getCreatedBy();
		if (createdBy.getUserType().equals(UserType.BankBranch)) {
			BankBranch bankBranch = bankBranchRepository.findOne(createdBy.getAssociatedId());
			Bank bank = bankBranch.getBank();
			smsLog.setSmsFrom(bankBranch.getId());
			smsLog.setSmsFromUser(UserType.BankBranch);
			bank.setSmsCount(bank.getSmsCount() - 1);
			bankRepository.save(bank);
		} else {
			Bank bank = bankRepository.findOne(createdBy.getAssociatedId());
			smsLog.setBank(bank);
			smsLog.setSmsFromUser(UserType.Bank);
			smsLog.setSmsFrom(bank.getId());
			bank.setSmsCount(bank.getSmsCount() - 1);
			bankRepository.save(bank);
		}
		smsLog.setMessage(message);
		smsLog.setSmsForUser(UserType.Customer);
		smsLog.setSmsFor(customer.getUniqueId());
		smsLog.setStatus(smsStatus);
		smsLog.setResponseMessage(SparrowSmsResponseCode.getValue(responseCode));;
		smsLog.setResponseCode(responseCode);
		if (responseCode.equals("200")) {
			smsLog.setDelivered(new Date());
		}
		try {
			smsLogRepository.save(smsLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
 
}
