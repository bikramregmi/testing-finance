/**
 * 
 */
package com.mobilebanking.api;

import javax.mail.MessagingException;

import com.mobilebanking.model.UserType;

/**
 * @author bibek
 *
 */
public interface IEmailApi {
	
	void sendEmail(UserType userType, String userId, String email, String message,String bankName);

	void sendEmail(UserType customer, String message, String email);

	void sendLowBlanceAlert(UserType userType, String email, Double balance, String bankName,Long bankId);

	void sendAirlinesTicketEmail(String transactionIdentifier,String email);
	
	void sendEmail(String message,String email) throws MessagingException;

}
