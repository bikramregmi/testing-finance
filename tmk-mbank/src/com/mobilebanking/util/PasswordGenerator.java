/**
 * 
 */
package com.mobilebanking.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.User;
import com.mobilebanking.repositories.CustomerProfileRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.repositories.UserRepository;

/**
 * @author bibek
 *
 */
@Component
public class PasswordGenerator {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CustomerProfileRepository customerProfileRepository;

	public Map<String, String> generatePassword() {
		Map<String, String> passwordMap = new HashMap<String, String>();
		String password = "";
		final String characters = "ABCDEFGHSTUVWXYZabcdefghijkmnopqrstuvwxyz123456789";
		final int charactesLength = characters.length();
		Random random = new Random();
		for (int i = 0; i < 8; i++) {
			password = password + characters.charAt(random.nextInt(charactesLength));
		}
		passwordMap.put("password", password);
		passwordMap.put("passwordEncoded", passwordEncoder.encode(password));
		return passwordMap;
	}

	public Map<String, String> generatePasswordForCustomer() {
		Map<String, String> passwordMap = new HashMap<String, String>();
		String password = "";
		final String characters = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz123456789!*#";
		final int charactersLength = characters.length();
		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			password = password + characters.charAt(random.nextInt(charactersLength));
		}
		passwordMap.put("password", password);
		passwordMap.put("passwordEncoded", passwordEncoder.encode(password));
		return passwordMap;
	}

	public Map<String, String> generateMPinForCustomer() {
		Map<String, String> mPinMap = new HashMap<String, String>();
		String mPin = "";
		final String characters = "0123456789";
		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			mPin = mPin + characters.charAt(random.nextInt(characters.length()));
		}
		mPinMap.put("password", mPin);
		mPinMap.put("passwordEncoded", passwordEncoder.encode(mPin));
		return mPinMap;
	}

	public String generateToken() {
		String token = "";
		final String characters = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz123456789";
		final int charactersLength = characters.length();
		Random random = new Random();
		for (int i = 0; i < 8; i++) {
			token = token + characters.charAt(random.nextInt(charactersLength));
		}
		String user = userRepository.findUserNameByToken(token);
		if (user != null) {
			generateToken();
		}
		return token;
	}

	public String generateVerificationCode() {
		String verificationCode = "";
		final String characters = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz123456789";
		final int charactersLength = characters.length();
		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			verificationCode = verificationCode + characters.charAt(random.nextInt(charactersLength));
		}
		String user = userRepository.findUserNameByToken(verificationCode);
		if (user != null) {
			generateToken();
		}
		return verificationCode;
	}

	public static String generatePin() {
		String pin = "";
		final String pinNumber = "0123456789";
		Random random = new Random();
		for (int i = 0; i < 4; i++) {
			pin = pin + pinNumber.charAt(random.nextInt(pinNumber.length()));
		}
		return pin;
	}

	public String generateUUID() {
		String uniqueId = "";
		final String characters = "1234567890";
		final int charactesLength = characters.length();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			uniqueId = uniqueId + characters.charAt(random.nextInt(charactesLength));
		}
		Customer customer = customerRepository.findCustomerByUniqueId(uniqueId);
		if (customer != null) {
			generateUUID();
		}
		return uniqueId;
	}

	public String generateUsername() {
		String uniqueId = "";
		final String characters = "0123456789";
		final int charactesLength = characters.length();
		Random random = new Random();
		for (int i = 0; i < 8; i++) {
			uniqueId = uniqueId + characters.charAt(random.nextInt(charactesLength));
		}
		User user = userRepository.findUserByUniqueId(uniqueId);
		if (user != null) {
			generateUsername();
		}
		return uniqueId;
	}

	public String generateProfileUniqueId() {
		String uniqueId = "";
		final String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final int charactesLength = characters.length();
		Random random = new Random();
		for (int i = 0; i < 5; i++)
			uniqueId = uniqueId + characters.charAt(random.nextInt(charactesLength));
		if (customerProfileRepository.findByProfileUniqueId(uniqueId) != null)
			generateProfileUniqueId();
		return uniqueId;
	}
}
