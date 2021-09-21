package com.mobilebanking.webservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobilebanking.api.IAccountApi;
import com.mobilebanking.api.IBankApi;
import com.mobilebanking.api.ICustomerApi;
import com.mobilebanking.api.ICustomerBankAccountApi;
import com.mobilebanking.api.IEmailApi;
import com.mobilebanking.api.IKhanepaniApi;
import com.mobilebanking.api.IMerchantPaymentRefactorApi;
import com.mobilebanking.api.IMerchantServiceApi;
import com.mobilebanking.api.IUserApi;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.CustomerBankAccountDTO;
import com.mobilebanking.model.CustomerDTO;
import com.mobilebanking.model.CustomerStatus;
import com.mobilebanking.model.MerchantServiceDTO;
import com.mobilebanking.model.TransactionResponseDTO;
import com.mobilebanking.model.UserDTO;
import com.mobilebanking.model.UserType;
import com.mobilebanking.model.mobile.ResponseDTO;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.ClientException;
import com.mobilebanking.util.StringConstants;
import com.mobilebanking.validation.MPinVaidation;
import com.wallet.serviceprovider.khalti.KhanepaniCounter;
import com.wallet.serviceprovider.khalti.KhanepaniCustomerDetailResponse;
import com.wallet.serviceprovider.khalti.KhanepaniServiceChargeResponse;

@Controller
public class KhanepaniApiController {
	
	@Autowired
	private IUserApi userApi;
	
	@Autowired
	private ICustomerApi customerApi;
	
	@Autowired
	private IKhanepaniApi khanePaniApi;
	
	@Autowired
	private IAccountApi accountApi;
	
	@Autowired
	private IMerchantPaymentRefactorApi merchantPaymentApi;
	
	@Autowired
	private IMerchantServiceApi serviceApi;
	
	@Autowired
	private ICustomerBankAccountApi customerBankAccountApi;
	
	@Autowired
	private MPinVaidation mPinValidation;
	
	@Autowired
	private IEmailApi emailApi;

	@Autowired
	private IBankApi bankApi;
	
	@RequestMapping(method = RequestMethod.GET, value = "/get/khanepanicounters")
	ResponseEntity<ResponseDTO> getKhanePaniCounters(HttpServletRequest request, HttpServletResponse response) {
		ResponseDTO responseDto = new ResponseDTO();
		try {
			List<KhanepaniCounter> khanepaniCounterList = khanePaniApi.getKhanePaniCounters();
			if (khanepaniCounterList != null) {
				responseDto.setCode(ResponseStatus.SUCCESS);
				responseDto.setStatus(ResponseStatus.SUCCESS.getValue());
				responseDto.setMessage("Counter Name(s) and Value(s) Retrieved Successfully.");
				responseDto.setDetails(khanepaniCounterList);
				return new ResponseEntity<ResponseDTO>(responseDto, HttpStatus.OK);
			} else {
				responseDto.setCode(ResponseStatus.BAD_REQUEST);
				responseDto.setStatus(ResponseStatus.BAD_REQUEST.getValue());
				responseDto.setMessage("Counters currently Not Available");
				return new ResponseEntity<ResponseDTO>(responseDto, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseDto.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
			responseDto.setStatus(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
			responseDto.setMessage("Some error occured while processing. Please try again after few moments.");
			return new ResponseEntity<ResponseDTO>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/api/getkhanepanibill", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> getNeaBill(HttpServletRequest request) throws ClientException {
		ResponseDTO response = new ResponseDTO();

		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getParameter("customer_code") == null) {
			response.setCode(ResponseStatus.CUSTOMER_CODE_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.CUSTOMER_CODE_REQUIRED.getValue());
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getParameter("counter") == null) {
			response.setCode(ResponseStatus.COUNTER_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.COUNTER_REQUIRED.getValue());
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (request.getParameter("month_id") == null) {
			response.setCode(ResponseStatus.MONTH_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.MONTH_REQUIRED.getValue());
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		String customer_code = request.getParameter("customer_code");
		String counter = request.getParameter("counter");
		String month_id = request.getParameter("month_id");
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
				CustomerDTO customer = customerApi.getCustomerById(AuthenticationUtil.getCurrentUser().getAssociatedId());

				if (customer.getState() != null && customer.getStatus().equals(CustomerStatus.Blocked)) {
					response.setCode(ResponseStatus.CUSTOMER_BLOCKED);
					response.setStatus("FAILURE");
					response.setMessage(StringConstants.CUSTOMER_BLOCKED.replace("{bank}", ""));
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
				}

				try {
					KhanepaniCustomerDetailResponse khanepaniCustomerDetail = new KhanepaniCustomerDetailResponse();
					khanepaniCustomerDetail = khanePaniApi.getKhanepaniCustomerDetail(customer_code, counter, month_id);
					if (khanepaniCustomerDetail != null) {
						if(khanepaniCustomerDetail.getTotal_dues().equals("0")){
							response.setCode(ResponseStatus.BAD_REQUEST);
							response.setStatus("Failure");
							response.setMessage("No Due amount at this moment");
							response.setDetails(new ArrayList<>());
							return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
						}
						response.setCode(ResponseStatus.SUCCESS);
						response.setMessage(ResponseStatus.SUCCESS.getValue());
						response.setStatus(ResponseStatus.SUCCESS.getKey());
						response.setDetails(khanepaniCustomerDetail);
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
					}else {
						response.setCode(ResponseStatus.BAD_REQUEST);
						response.setStatus("Failure");
						response.setMessage("Service currently not available. Please try again later.");
						response.setDetails(new ArrayList<>());
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
					}

				} catch (Exception e) {
					e.printStackTrace();
					response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
					response.setStatus("FAILURE");
					response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
					response.setDetails(new ArrayList<>());
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
		response.setCode(ResponseStatus.UNAUTHORIZED_USER);
		response.setStatus("FAILURE");
		response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		response.setDetails(new ArrayList<>());
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "/api/getkhanepaniservicecharge", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> getKhanepaniServiceCharge(HttpServletRequest request) throws ClientException {
		ResponseDTO response = new ResponseDTO();

		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}


		if (request.getParameter("counter") == null) {
			response.setCode(ResponseStatus.COUNTER_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.COUNTER_REQUIRED.getValue());
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (request.getParameter("amount") == null) {
			response.setCode(ResponseStatus.AMOUNT_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.MONTH_REQUIRED.getValue());
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		String counter = request.getParameter("counter");
		String amount = request.getParameter("amount");
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
				CustomerDTO customer = customerApi.getCustomerById(AuthenticationUtil.getCurrentUser().getAssociatedId());

				if (customer.getState() != null && customer.getStatus().equals(CustomerStatus.Blocked)) {
					response.setCode(ResponseStatus.CUSTOMER_BLOCKED);
					response.setStatus("FAILURE");
					response.setMessage(StringConstants.CUSTOMER_BLOCKED.replace("{bank}", ""));
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
				}

				try {
					KhanepaniServiceChargeResponse khanepaniServiceChargeResponse = new KhanepaniServiceChargeResponse();
					khanepaniServiceChargeResponse = khanePaniApi.getKhanePaniServiceCharge(amount, counter);
					if (khanepaniServiceChargeResponse != null) {
						response.setCode(ResponseStatus.SUCCESS);
						response.setMessage(ResponseStatus.SUCCESS.getValue());
						response.setStatus(ResponseStatus.SUCCESS.getKey());
						response.setDetails(khanepaniServiceChargeResponse);
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
					}

					else {
						response.setCode(ResponseStatus.BAD_REQUEST);
						response.setStatus("Failure");
						response.setMessage("Service currently not available. Please try again later.");
						response.setDetails(new ArrayList<>());
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
					}

				} catch (Exception e) {
					e.printStackTrace();
					response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
					response.setStatus("FAILURE");
					response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
					response.setDetails(new ArrayList<>());
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
		response.setCode(ResponseStatus.UNAUTHORIZED_USER);
		response.setStatus("FAILURE");
		response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		response.setDetails(new ArrayList<>());
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "/api/khanepanipay", method = RequestMethod.POST)
	public ResponseEntity<TransactionResponseDTO> khanepaniPay(HttpServletRequest request) {

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
		
		if (request.getParameter("customer_code") == null) {
			response.setCode(ResponseStatus.CUSTOMER_CODE_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.CUSTOMER_CODE_REQUIRED.getValue());
			return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getParameter("counter") == null) {
			response.setCode(ResponseStatus.COUNTER_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.COUNTER_REQUIRED.getValue());
			return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (request.getParameter("account_number") == null) {
			response.setCode(ResponseStatus.ACCOUNT_NUMBER_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.ACCOUNT_NUMBER_REQUIRED.getValue());
			return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
				// check balance
				String serviceIdentifier = StringConstants.KHANE_PANI;
				String customer_code = request.getParameter("customer_code");
				String counter = request.getParameter("counter");

				double amount = Double.parseDouble(request.getParameter("amount"));
				HashMap<String,String> hash = new HashMap<String,String>();
				hash.put("counter", counter);
				hash.put("customer_code", customer_code);

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
						MerchantServiceDTO service = serviceApi.findServiceByUniqueIdentifier(serviceIdentifier);
						CustomerBankAccountDTO customerBankAcocunt = customerBankAccountApi.findCustomerBankAccountByAccountNo(request.getParameter("account_number"), customer.getId());

						if (customer.getState() != null && customer.getStatus().equals(CustomerStatus.Blocked)) {
							response.setCode(ResponseStatus.CUSTOMER_BLOCKED);
							response.setStatus("FAILURE");
							response.setMessage(StringConstants.CUSTOMER_BLOCKED.replace("{bank}", customerBankAcocunt.getBank()));
							return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.BAD_REQUEST);
						}
						checkBankBalanceForNotification(customerBankAcocunt.getBankId(), customerBankAcocunt.getBank());
						BankDTO bank = bankApi.getBankByCode(customer.getBankCode());
						double balance = accountApi.getSpendableAmount(bank.getId());
						if (balance >= amount) {
							Map<String, String> hashResponse = merchantPaymentApi.merchantPayment(serviceIdentifier, customer_code, amount, AuthenticationUtil.getCurrentUser().getId(),
									request.getParameter("account_number"), hash);
							TransactionResponseDTO transactionResponse = new TransactionResponseDTO();
							if (hashResponse.get("status").equals("success")) {
								transactionResponse.setDate(hashResponse.get("transactionDate"));
								transactionResponse.setServiceTo(hashResponse.get("ServiceTo"));
								transactionResponse.setTransactionIdentifier(hashResponse.get("transactionIdentifier"));
								transactionResponse.setMessage("Your " + service.getService() + " payment of NPR "
										+ amount + " was successful as of " + new Date() + ".TID : "
										+ transactionResponse.getTransactionIdentifier() + ". Your remaining due amount is "+hashResponse.get("dueAmount")+". Thank You, "
										+ customerBankAcocunt.getBank());
								return new ResponseEntity<TransactionResponseDTO>(transactionResponse, HttpStatus.OK);
							} else if (hashResponse.get("status").equals("unknown")) {
								transactionResponse.setStatus(ResponseStatus.AMBIGUOUS_TRANSACTION.getKey());
								transactionResponse.setDate(hashResponse.get("transactionDate"));
								transactionResponse.setServiceTo(hashResponse.get("ServiceTo"));
								transactionResponse.setTransactionIdentifier(hashResponse.get("transactionIdentifier"));
								transactionResponse.setMessage("Technical Error Please Contact Administrator");
								return new ResponseEntity<TransactionResponseDTO>(transactionResponse,
										HttpStatus.CONFLICT);
							}

							else {
								transactionResponse.setStatus(ResponseStatus.BAD_REQUEST.getKey());
								transactionResponse.setMessage(hashResponse.get("Result Message"));
								return new ResponseEntity<TransactionResponseDTO>(transactionResponse,
										HttpStatus.BAD_REQUEST);
							}
						} else {
							response.setCode(ResponseStatus.INSUFFICIENT_BALANCE);
							response.setStatus("FAILURE");
							response.setMessage("This Service Is Currently Unavailable. Please Contact Bank.");
							return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.BAD_REQUEST);
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
	
	private void checkBankBalanceForNotification(Long bankid, String bankName) {
		HashMap<String, Double> hashMap = accountApi.getCreditBalanceAndBalanceByBank(bankid);
		if (hashMap.get("balance") <= 0.0) {
			Boolean response = bankApi.getIsMessageSent(bankid);
			if (response == null || response == false) {
				String email = bankApi.getBankEmail(bankid);
				List<String> emailList = new ArrayList<>();
				emailList.add(email);
				// emailList.add("sameer.khanal@threemonk.com");
				emailList.add("admin@mbank.com.np");
				for (String mailAddress : emailList) {
					emailApi.sendLowBlanceAlert(UserType.Admin, mailAddress,
							hashMap.get("creditLimit") + hashMap.get("balance"), bankName, bankid);
				}
			}
		}

	}

}
