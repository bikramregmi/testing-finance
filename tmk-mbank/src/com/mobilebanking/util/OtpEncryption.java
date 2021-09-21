package com.mobilebanking.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class OtpEncryption {
	 static Cipher cipher;  
	 public  String encrypt(String plainText, SecretKey secretKey)
	            throws Exception {
		 cipher = Cipher.getInstance("AES");
	        byte[] plainTextByte = plainText.getBytes();
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	        byte[] encryptedByte = cipher.doFinal(plainTextByte);
	        Base64.Encoder encoder = Base64.getEncoder();
	        String encryptedText = encoder.encodeToString(encryptedByte);
	        return encryptedText;
	    }

	    public String decrypt(String encryptedText, SecretKey secretKey)
	            throws Exception {
	    	cipher = Cipher.getInstance("AES");

	        Base64.Decoder decoder = Base64.getDecoder();
	        byte[] encryptedTextByte = decoder.decode(encryptedText);
	        cipher.init(Cipher.DECRYPT_MODE, secretKey);
	        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
	        String decryptedText = new String(decryptedByte);
	        return decryptedText;
	    }
	    
	    public SecretKey getKey() throws UnsupportedEncodingException, NoSuchAlgorithmException{
	    	byte[] key;
	    	 String myKey ="h62Df2Tk";
		        MessageDigest sha = null;
		        key = myKey.getBytes("UTF-8");
		        sha = MessageDigest.getInstance("SHA-1");
		        key = sha.digest(key);
		        key = Arrays.copyOf(key, 16);
		        SecretKey secretKey = new SecretKeySpec(key, "AES");
		        return secretKey;
	    }
}
