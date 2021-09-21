/**
 * 
 */
package com.mobilebanking.util;

import java.io.File;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


/**
 * @author bibek
 *
 */
@Component
public class SendEmail {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private JavaMailSender secondMailSender;
	
	private Logger logger=LoggerFactory.getLogger(SendEmail.class );

	public void sendMail(String to, String password, String username, String bankName) throws MessagingException {

		MimeMessage mimeMessage = secondMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		String html = "<p>Dear " + username + ",</p>" + "<p>Your Password is: " + password
				+ "</p> <p>Please Change the password at First Attempt</p>" + "<p>Sincerely,</p>" + bankName
				+ "<br>"+"<br>"
				
				+"This is system generated email. Please do not reply or send any message to this mail address."
				;
		helper.setFrom("noreply@mbank.com.np");
		helper.setTo(to);
		helper.setSubject("Registration Email");

		helper.setText("text/html", html);

		secondMailSender.send(mimeMessage);

	}

	public void sendMail(String email, String message) throws MessagingException {
		MimeMessage mimeMessage = secondMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		// String html = "<p>Dear "+username+",</p>"
		// + "<p>Your Password is: " + password + "</p> <p>Please Change the
		// password at First Attempt</p>";
		helper.setFrom("noreply@mbank.com.np");

		helper.setTo(email);
		helper.setSubject("Registration Email");

		helper.setText("text/html", message);

		secondMailSender.send(mimeMessage);

	}

	public void sendMail(String to, Double balance, String bankName) throws MessagingException {

		MimeMessage mimeMessage = secondMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		String html = "<p>Dear Bank/Admin,</p>" + "<p>The spendable balance of  " + bankName + " is getting low ("
				+ balance + ").</p> <p>Please contact administrator to load balance for smooth transaction.</p>"
				+ "<br>" + "<p>Sincerely,</p>" + "HT Support";

		helper.setFrom("support@mbank.com.np");

		helper.setTo(to);
		helper.setSubject("Low Balance Alert");

		helper.setText("text/html", html);

		secondMailSender.send(mimeMessage);

	}
	
	public void sendEmailWithAttachment(String to,  String msg, String subject, String transactionId) throws MessagingException{
		try{
			
			MimeMessage message =  mailSender.createMimeMessage();  
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);  
	        helper.setFrom("support@mbank.com.np");  
	       
	        helper.setTo(to);  
	        helper.setSubject(subject);  
	       
	        helper.setText("text/html; charset=utf-8", msg);  
	        
	        String filename = getArsTicketPath(transactionId + ".pdf");
	        DataSource source = new FileDataSource(filename);
	        
	        helper.addAttachment(transactionId+".pdf", source);
	   
	        mailSender.send(message);
			}catch(Exception e){
				logger.info("error in sending mail with ticket attachment");
				e.printStackTrace();
			}
			
			
		}

	private String getArsTicketPath(String fileName) {
		String filePath = "";
		filePath = StringConstants.OS_LINUX_DIRECTORY+"/"+"arsTickets";
		File file = new File(filePath);
		if(!file.exists()){
			file.mkdirs();
		}
		filePath+="/"+fileName;
		
		return filePath;
	}

}
