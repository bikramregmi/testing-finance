/**
 * 
 */
package com.mobilebanking.api.impl;

import java.util.Date;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IEmailApi;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.EmailLog;
import com.mobilebanking.model.EmailStatus;
import com.mobilebanking.model.EmailType;
import com.mobilebanking.model.UserType;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.EmailLogRepository;
import com.mobilebanking.util.SendEmail;
import com.mobilebanking.util.StringConstants;

/**
 * @author bibek
 *
 */
@Service
public class EmailApi implements IEmailApi {

	@Autowired
	private SendEmail sendEmail;
	@Autowired
	private EmailLogRepository emailLogRepository;

	@Autowired
	private BankRepository bankRepository;

	@Override
	public void sendEmail(UserType userType, String userId, String email, String password, String bankName) {

		EmailLog emailLog = new EmailLog();
		emailLog.setForUserType(userType);
		emailLog.setForUser(userId);
		emailLog.setEmail(email);
		emailLog.setStatus(EmailStatus.INITIATED);
		emailLog.setEmailType(EmailType.REGISTRATION);
		emailLog = emailLogRepository.save(emailLog);

		try {
			sendEmail.sendMail(email, password, userId, bankName);
			emailLog.setStatus(EmailStatus.DELIVERED);
			emailLog.setDelivered(new Date());
			emailLogRepository.save(emailLog);

		} catch (Exception e) {
			e.printStackTrace();
			emailLog.setStatus(EmailStatus.FAILED);
			emailLogRepository.save(emailLog);
		}

	}

	@Override
	public void sendEmail(UserType userType, String message, String email) {

		EmailLog emailLog = new EmailLog();
		emailLog.setForUserType(userType);
		// emailLog.setForUser(userId);
		emailLog.setEmail(email);
		emailLog.setStatus(EmailStatus.INITIATED);
		emailLog = emailLogRepository.save(emailLog);
		try {
			sendEmail.sendMail(email, message);
			emailLog.setStatus(EmailStatus.DELIVERED);
			emailLog.setDelivered(new Date());
			emailLogRepository.save(emailLog);

		} catch (Exception e) {
			emailLog.setStatus(EmailStatus.FAILED);
			emailLogRepository.save(emailLog);
		}
	}

	@Override
	public void sendLowBlanceAlert(UserType userType, String email, Double balance, String bankName, Long bankId) {

		EmailLog emailLog = new EmailLog();
		emailLog.setForUserType(userType);
		// emailLog.setForUser(userId);
		emailLog.setEmail(email);
		emailLog.setStatus(EmailStatus.INITIATED);
		emailLog.setEmailType(EmailType.LOW_BALANCE_ALERT);
		emailLog.setBankId(bankId);
		emailLog = emailLogRepository.save(emailLog);
		try {
			sendEmail.sendMail(email, balance, bankName);
			emailLog.setStatus(EmailStatus.DELIVERED);
			emailLog.setDelivered(new Date());
			emailLogRepository.save(emailLog);
			Bank bank = bankRepository.findOne(bankId);
			bank.setIsAlertSent(true);
			bankRepository.save(bank);
		} catch (Exception e) {
			emailLog.setStatus(EmailStatus.FAILED);
			emailLogRepository.save(emailLog);
		}
	}

	@Override
	public void sendAirlinesTicketEmail(String transactionIdentifier,String contactEmail) {

		String subject = "Mobile Banking Flight Ticket";
		String msg0;
		if (StringConstants.IS_PRODUCTION) {
			msg0 = "<img src='https://mbank.com.np/images/mbank_logo.png' /><br/><br/>Dear User,<br/><br/>";
		} else
			msg0 = "<img src='http://202.79.34.126:9091/images/mbank_logo.png' /><br/><br/>Dear User,<br/><br/>";

		String msg = "Thank you for purchasing flight tickets from our Mobile Banking.<br/> Please find the ticket in the attachment.";

		String msg6 = "<br/><br/>Should you have any questions, <br/> Kindly inform your Bank <br/>";

		String msg7 = "<b>Thank you,<br/>Mbank Payment</b>";

		String msg8 = "<br/><br/>If you think that you shouldn't have received this email, you can safely ignore it.";

		String msgFinal = msg0 + msg  + msg6 + msg7 + msg8;

		try {
			sendEmail.sendEmailWithAttachment(contactEmail, msgFinal, subject, transactionIdentifier);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void sendEmail(String message, String email) throws MessagingException {
		sendEmail.sendMail(email, message);
		
	}

	/*
	 * @Override public Boolean getEmailLogDetailByBank(Long bankid) {
	 * List<EmailLog> emailLogList =
	 * emailLogRepository.findByEmailTypeAndBankId(EmailType.LOW_BALANCE_ALERT,
	 * bankid);
	 * 
	 * 
	 * return false; }
	 */

}
