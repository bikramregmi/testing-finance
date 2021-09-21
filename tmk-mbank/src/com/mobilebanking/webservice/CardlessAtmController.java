package com.mobilebanking.webservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobilebanking.api.IAccountApi;
import com.mobilebanking.api.ICardlessBankApi;
import com.mobilebanking.api.ICardlessOtpApi;
import com.mobilebanking.api.ICustomerApi;
import com.mobilebanking.api.ICustomerBankAccountApi;
import com.mobilebanking.api.IUserApi;
import com.mobilebanking.model.AccountDTO;
import com.mobilebanking.model.CardlessBankDTO;
import com.mobilebanking.model.CardlessBankResponse;
import com.mobilebanking.model.CustomerBankAccountDTO;
import com.mobilebanking.model.CustomerDTO;
import com.mobilebanking.model.CustomerStatus;
import com.mobilebanking.model.TransactionResponseDTO;
import com.mobilebanking.model.UserDTO;
import com.mobilebanking.model.mobile.ResponseDTO;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.ClientException;
import com.mobilebanking.util.StringConstants;
import com.mobilebanking.validation.MPinVaidation;

@Controller
@RequestMapping("/api")
public class CardlessAtmController {

	@Autowired
	private IUserApi userApi;

	@Autowired
	private MPinVaidation mPinValidation;

	@Autowired
	private ICustomerApi customerApi;

	@Autowired
	private ICustomerBankAccountApi customerBankAccountApi;

	@Autowired
	private ICardlessOtpApi cardlessOtpApi;

	@Autowired
	private IAccountApi accountApi;

	@Autowired
	private ICardlessBankApi cardlessBankApi;

	@RequestMapping(value = "/cardlesscash", method = RequestMethod.POST)
	public ResponseEntity<TransactionResponseDTO> cardlessCash(HttpServletRequest request) {
		TransactionResponseDTO response = new TransactionResponseDTO();

		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getParameter("amount") == null) {
			response.setCode(ResponseStatus.AMOUNT_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.AMOUNT_REQUIRED.getValue());
			return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getParameter("account_number") == null) {
			response.setCode(ResponseStatus.ACCOUNT_NUMBER_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.ACCOUNT_NUMBER_REQUIRED.getValue());
			return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getParameter("phone_number") == null) {
			response.setCode(ResponseStatus.PHONE_NUMBER_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.PHONE_NUMBER_REQUIRED.getValue());
			return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		if (request.getParameter("cardlessbank_id") == null) {
			response.setCode(ResponseStatus.CARDLESS_BANK_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.CARDLESS_BANK_REQUIRED.getValue());
			return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
				// check balance
				String mobileNumber = request.getParameter("phone_number");
				double amount = Double.parseDouble(request.getParameter("amount"));
				Long cardLessBank = Long.parseLong(request.getParameter("cardlessbank_id"));
				// MerchantServiceDTO service =
				// serviceApi.findServiceByUniqueIdentifier(serviceIdentifier);
				try {
					UserDTO userDto = userApi.getUserWithId((AuthenticationUtil.getCurrentUser().getId()));
					userDto.setOldPassword(request.getParameter("mPin"));
					boolean valid = mPinValidation.mpinValidation(userDto);
					if (valid) {
						CustomerDTO customer = customerApi
								.getCustomerById(AuthenticationUtil.getCurrentUser().getAssociatedId());

						if (customer == null) {
							response.setCode(ResponseStatus.CUSTOMER_NOT_FOUND);
							response.setStatus("FAILURE");
							response.setMessage(ResponseStatus.CUSTOMER_NOT_FOUND.getValue());
							return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);
						}
						CustomerBankAccountDTO customerBankAcocunt = customerBankAccountApi
								.findCustomerBankAccountByAccountNo(request.getParameter("account_number"),customer.getId());

						if (customer.getState() != null && customer.getStatus().equals(CustomerStatus.Blocked)) {
							response.setCode(ResponseStatus.CUSTOMER_BLOCKED);
							response.setStatus("FAILURE");
							response.setMessage(
									StringConstants.CUSTOMER_BLOCKED.replace("{bank}", customerBankAcocunt.getBank()));
							return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.BAD_REQUEST);
						}
						// checkBankBalanceForNotification(customerBankAcocunt.getBankId(),customerBankAcocunt.getBank());
						AccountDTO account = accountApi.getAccountByBankAndCardlessBank(customerBankAcocunt.getBankId(),
								cardLessBank);
						if (account == null || account.getBalance() < amount) {
							response.setCode(ResponseStatus.INSUFFICIENT_BALANCE);
							response.setStatus("FAILURE");
							response.setMessage("This Service Is Currently Unavailable. Please Contact Bank.");
							return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.BAD_REQUEST);
						} else {

							Boolean otpStatus = cardlessOtpApi.saveAndShareOTP(customerBankAcocunt, mobileNumber, amount, cardLessBank);

							// Map<String, String> hashResponse = merchantPaymentApi.merchantPayment(
							// serviceIdentifier, mobileNumber, amount, AuthenticationUtil.getCurrentUser().getId(),
							// request.getParameter("account_number"),myHash);
							TransactionResponseDTO transactionResponse = new TransactionResponseDTO();
							if (otpStatus) {
								transactionResponse.setDate(new Date().toString());
								transactionResponse.setMessage(
										"Your transaction was created successfully, You will recieve an OTP soon. Thank You.-"
												+ customerBankAcocunt.getBank());
								transactionResponse.setStatus(ResponseStatus.SUCCESS.getKey());
								return new ResponseEntity<TransactionResponseDTO>(transactionResponse, HttpStatus.OK);
							} else {
								transactionResponse.setDate(new Date().toString());
								transactionResponse.setMessage("Unable to generate OTP please try again later.");
								// transactionResponse.setStatus(ResponseStatus.BAD_REQUEST.getKey());
								transactionResponse.setStatus(ResponseStatus.FAILURE.getKey());
								return new ResponseEntity<TransactionResponseDTO>(transactionResponse,
										HttpStatus.EXPECTATION_FAILED);
							}
						}
					} else {
						response.setCode(ResponseStatus.MPIN_DOES_NOT_MATCH);
						response.setStatus("FAILURE");
						response.setMessage(ResponseStatus.MPIN_DOES_NOT_MATCH.getValue());
						return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.UNAUTHORIZED);
					}
				} catch (ClientException e) {
					e.printStackTrace();
					response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
					response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
					e.printStackTrace();
					return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);

				} catch (Exception e) {
					e.printStackTrace();
					response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
					response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
					e.printStackTrace();
					return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);
				}

			}

		}

		response.setCode(ResponseStatus.UNAUTHORIZED_USER);
		response.setStatus("FAILURE");
		response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.FORBIDDEN);
	}

	/*
	 * private void checkBankBalanceForNotification( Long bankid , String
	 * bankName){ HashMap<String,Double> hashMap =
	 * accountApi.getCreditBalanceAndBalanceByBank(bankid);
	 * 
	 * if(hashMap.get("balance") <= 0.0){ Boolean response =
	 * bankApi.getIsMessageSent(bankid); if(response==null || response==false){
	 * String email = bankApi.getBankEmail(bankid); List<String> emailList = new
	 * ArrayList<>(); emailList.add(email);
	 * 
	 * // emailList.add("sameer.khanal@threemonk.com");
	 * emailList.add("admin@mbank.com.np"); for(String mailAddress:emailList){
	 * emailApi.sendLowBlanceAlert(UserType.Admin,mailAddress,
	 * hashMap.get("creditLimit")+hashMap.get("balance") , bankName,bankid); } }
	 * }
	 * 
	 * }
	 */
	
	@RequestMapping(value="/cardlessbank", method=RequestMethod.GET)
	public ResponseEntity<ResponseDTO> getAccounts(HttpServletRequest request) {
		ResponseDTO response = new ResponseDTO();
		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (request.getHeader("client") == null) {
			response.setCode(ResponseStatus.CLIENT_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.CLIENT_REQUIRED.getValue());;
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
		}
		
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER+","+Authorities.AUTHENTICATED)) {
				String clientId = request.getHeader("client");
				try {
					List<CardlessBankDTO> cardlessBankList = cardlessBankApi.findByClientId(clientId); 

				List<CardlessBankResponse> carlessBank = new ArrayList<>();
				
				for(CardlessBankDTO cardless : cardlessBankList){
					CardlessBankResponse cardlessBankResponse = new CardlessBankResponse();
					cardlessBankResponse.setCardlessBankId(cardless.getId());
					cardlessBankResponse.setCardlessBankName(cardless.getBank());
					carlessBank.add(cardlessBankResponse);
				}
					
					if(cardlessBankList!=null && !cardlessBankList.isEmpty() ){
					response.setStatus("SUCCCESS");
					response.setCode(ResponseStatus.SUCCESS);
					response.setMessage(ResponseStatus.SUCCESS.getValue());
					response.setDetails(carlessBank);
					return new ResponseEntity<ResponseDTO> (response, HttpStatus.OK);
					}else{
						response.setStatus("FAILURE");
						response.setCode(ResponseStatus.FAILURE);
						response.setMessage("No cardless bank available");
						return new ResponseEntity<ResponseDTO> (response, HttpStatus.NO_CONTENT);
					}
				} catch (Exception e) {
					response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
					response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
					response.setDetails(new ArrayList<>());
					e.printStackTrace();
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);
				}
				
			}
		}
		response.setCode(ResponseStatus.UNAUTHORIZED_USER);
		response.setStatus("FAILURE");
		response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		response.setDetails(new ArrayList<>());
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.UNAUTHORIZED);
	}
	
}
