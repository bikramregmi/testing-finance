/**
* 
*/
package com.mobilebanking.webservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobilebanking.api.IAccountApi;
import com.mobilebanking.api.IBankApi;
import com.mobilebanking.api.ICustomerApi;
import com.mobilebanking.api.ICustomerBankAccountApi;
import com.mobilebanking.api.ICustomerProfileApi;
import com.mobilebanking.api.IEmailApi;
import com.mobilebanking.api.IMerchantPaymentRefactorApi;
import com.mobilebanking.api.IMerchantServiceApi;
import com.mobilebanking.api.ITransactionApi;
import com.mobilebanking.api.IUserApi;
import com.mobilebanking.api.impl.INoticeApi;
import com.mobilebanking.model.AccountMode;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.CustomerBankAccountDTO;
import com.mobilebanking.model.CustomerDTO;
import com.mobilebanking.model.CustomerStatus;
import com.mobilebanking.model.MerchantServiceDTO;
import com.mobilebanking.model.MiniStatementRespose;
import com.mobilebanking.model.NoticeDTO;
import com.mobilebanking.model.TransactionResponseDTO;
import com.mobilebanking.model.TransactionType;
import com.mobilebanking.model.UserDTO;
import com.mobilebanking.model.UserType;
import com.mobilebanking.model.mobile.ResponseDTO;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.ClientException;
import com.mobilebanking.util.StringConstants;
import com.mobilebanking.validation.MPinVaidation;
import com.wallet.serviceprovider.paypoint.paypointResponse.NeaBillAmountResponse;
import com.wallet.serviceprovider.paypoint.paypointResponse.WorldLinkPackage;

/**
 * @author bibek
 *
 */
@Controller
@RequestMapping("/api")
public class CustomerApiController {

	@Autowired
	private ICustomerApi customerApi;

	@Autowired
	private ICustomerBankAccountApi customerBankAccountApi;

	@Autowired
	private IUserApi userApi;

	@Autowired
	private IMerchantPaymentRefactorApi merchantPaymentApi;

	@Autowired
	private MPinVaidation mPinValidation;

	@Autowired
	private INoticeApi noticeApi;

	@Autowired
	private IMerchantServiceApi serviceApi;

	@Autowired
	private ITransactionApi transactionApi;

	@Autowired
	private IAccountApi accountApi;

	@Autowired
	private ICustomerProfileApi customerProfileApi;

	@Autowired
	private IEmailApi emailApi;

	@Autowired
	private IBankApi bankApi;

	@RequestMapping(value = "/checkdevicetoken", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> checkdeviceToken(HttpServletRequest request) {

		ResponseDTO response = new ResponseDTO();

		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (AuthenticationUtil.getCurrentUser() != null) {
			boolean valid = userApi.checkDeviceToken(AuthenticationUtil.getCurrentUser().getId());
			try {
				if (valid) {
					response.setStatus("SUCCCESS");
					response.setCode(ResponseStatus.SUCCESS);
					response.setMessage(ResponseStatus.SUCCESS.getValue());
					response.setDetails(new ArrayList<>());
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
				} else {
					response.setStatus("Failure");
					response.setCode(ResponseStatus.FAILURE);
					response.setMessage(ResponseStatus.FAILURE.getValue());
					response.setDetails(new ArrayList<>());
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {
				response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
				response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
				response.setDetails(new ArrayList<>());
				e.printStackTrace();
				return new ResponseEntity<ResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		response.setCode(ResponseStatus.UNAUTHORIZED_USER);
		response.setStatus("FAILURE");
		response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		response.setDetails(new ArrayList<>());
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/setdevicetoken", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> setDeviceToken(HttpServletRequest request) {
		ResponseDTO response = new ResponseDTO();

		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getParameter("device_token") == null || request.getParameter("device_token").trim() == "") {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage("Device Token required");
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getParameter("fcmserver_identifier") == null
				|| request.getParameter("fcmserver_identifier").trim() == "") {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage("Fcm Server Identifier required");
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
				try {
					Boolean valid = userApi.setDeviceToken(request.getParameter("device_token"),
							AuthenticationUtil.getCurrentUser().getId(), request.getParameter("fcmserver_identifier"));
					response.setStatus("SUCCCESS");
					response.setCode(ResponseStatus.SUCCESS);
					response.setMessage(ResponseStatus.SUCCESS.getValue());
					response.setDetails(request.getParameter("device_token"));
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);

				} catch (Exception e) {
					response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
					response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
					response.setDetails(new ArrayList<>());
					e.printStackTrace();
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}

		response.setCode(ResponseStatus.UNAUTHORIZED_USER);
		response.setStatus("FAILURE");
		response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		response.setDetails(new ArrayList<>());
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.UNAUTHORIZED);

	}

	@RequestMapping(value = "/notices", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> getNotice(HttpServletRequest request) {
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
			response.setMessage(ResponseStatus.CLIENT_REQUIRED.getValue());
			;
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
		}

		String date = request.getParameter("date");
		String clientId = request.getHeader("client");

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
				try {
					List<NoticeDTO> noticeList = new ArrayList<>();
					if (date.equals("")) {
						noticeList = noticeApi.listNotice(clientId);
					} else {
						noticeList = noticeApi.listNoticeByDate(date, clientId);
					}

					if (noticeList != null) {

						response.setStatus("SUCCCESS");
						response.setCode(ResponseStatus.SUCCESS);
						response.setMessage(ResponseStatus.SUCCESS.getValue());
						response.setDetails(noticeList);
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
					} else {
						response.setStatus("Failed");
						response.setCode(ResponseStatus.NOTICE_UNAVAILABLE);
						response.setMessage(ResponseStatus.NOTICE_UNAVAILABLE.getValue());
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
					}
				} catch (Exception e) {
					response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
					response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
					response.setDetails(new ArrayList<>());
					e.printStackTrace();
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
				}

			}

		}
		response.setCode(ResponseStatus.UNAUTHORIZED_USER);
		response.setStatus("FAILURE");
		response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		response.setDetails(new ArrayList<>());
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/updatenotices", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> updateNotice(HttpServletRequest request, @RequestBody List<Long> noticeIds) {
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
			response.setMessage(ResponseStatus.CLIENT_REQUIRED.getValue());
			;
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
		}

		String clientId = request.getHeader("client");

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
				try {

					Boolean updateNotice = noticeApi.updateNotice(noticeIds);
					if (updateNotice) {
						response.setStatus("SUCCCESS");
						response.setCode(ResponseStatus.SUCCESS);
						response.setMessage(ResponseStatus.SUCCESS.getValue());
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);

					} else {
						response.setStatus("Failed");
						response.setCode(ResponseStatus.FAILURE);
						response.setMessage(ResponseStatus.FAILURE.getValue());
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
					}
				} catch (Exception e) {
					response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
					response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
					response.setDetails(new ArrayList<>());
					e.printStackTrace();
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
				}

			}

		}
		response.setCode(ResponseStatus.UNAUTHORIZED_USER);
		response.setStatus("FAILURE");
		response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		response.setDetails(new ArrayList<>());
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/customerdetails", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> getCustomerDetails(HttpServletRequest request) {
		ResponseDTO response = new ResponseDTO();
		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
				try {
					CustomerDTO customer = customerApi
							.getCustomerById(AuthenticationUtil.getCurrentUser().getAssociatedId());
					List<CustomerBankAccountDTO> customerAccounts = customerBankAccountApi
							.findCustomerBankAccountByCustomer(customer.getUniqueId());
					List<String> bankAccounts = new ArrayList<String>();
					List<String> bankBranches = new ArrayList<String>();
					List<AccountMode> accountModes = new ArrayList<AccountMode>();
					for (int i = 0; i < customerAccounts.size(); i++) {
						bankAccounts.add(customerAccounts.get(i).getAccountNumber());
						bankBranches.add(customerAccounts.get(i).getBranch());
					}
					customer.setAccountBranch(bankBranches);
					customer.setAccountNumbers(bankAccounts);
					customer.setAccountModes(accountModes);
					response.setStatus("SUCCCESS");
					response.setCode(ResponseStatus.SUCCESS);
					response.setMessage(ResponseStatus.SUCCESS.getValue());
					response.setDetails(customer);
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);

				} catch (Exception e) {
					response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
					response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
					response.setDetails(new ArrayList<>());
					e.printStackTrace();
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
				}

			}

		}
		response.setCode(ResponseStatus.UNAUTHORIZED_USER);
		response.setStatus("FAILURE");
		response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		response.setDetails(new ArrayList<>());
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/getbalance", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> getBalance(HttpServletRequest request) {
		ResponseDTO response = new ResponseDTO();
		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getParameter("account_number") == null || request.getParameter("account_number").isEmpty()) {
			response.setCode(ResponseStatus.ACCOUNT_NUMBER_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.ACCOUNT_NUMBER_REQUIRED.getValue());
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
				UserDTO userDto = userApi.getUserWithId((AuthenticationUtil.getCurrentUser().getId()));
				userDto.setOldPassword(request.getParameter("mPin"));
				Boolean valid = mPinValidation.mpinValidation(userDto);
				if (valid) {
					try {
						CustomerDTO customer = customerApi
								.getCustomerById(AuthenticationUtil.getCurrentUser().getAssociatedId());
						CustomerBankAccountDTO customerBankAcocunt = customerBankAccountApi
								.findCustomerBankAccountByAccountNo(request.getParameter("account_number"),
										customer.getId());
						if (customer.getState() != null && customer.getStatus().equals(CustomerStatus.Blocked)) {
							response.setCode(ResponseStatus.CUSTOMER_BLOCKED);
							response.setStatus("FAILURE");
							response.setMessage(
									StringConstants.CUSTOMER_BLOCKED.replace("{bank}", customerBankAcocunt.getBank()));
							return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
						}
						// validate first if the account numeber exists for
						// customer or not
						Double balance = null;
						if (StringConstants.IS_PRODUCTION) {
							balance = customerApi.getCustomerBankBalance(customer.getUniqueId(),
									request.getParameter("account_number"));
						} else {
							balance = 10000.00;
						}
						if (balance != null) {
							String message = StringConstants.BALANCE_INQUIRY_MSG;
							message = message.replace("{accountNumber}", request.getParameter("account_number"));
							response.setStatus("SUCCCESS");
							response.setCode(ResponseStatus.SUCCESS);
							response.setMessage(ResponseStatus.SUCCESS.getValue());
							if (customerBankAcocunt.getAccountMode() == AccountMode.LOAN) {
								response.setDetails("Your Principal Amount for Loan Account "
										+ request.getParameter("account_number") + " as of " + new Date() + " is "
										+ balance + " Thank you, " + customerBankAcocunt.getBank());
							} else
								response.setDetails("Your Balance for Account " + request.getParameter("account_number")
										+ " as of " + new Date() + " is " + balance + " Thank you, "
										+ customerBankAcocunt.getBank());
							return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
						} else {
							response.setStatus("FAILURE");
							response.setCode(ResponseStatus.FAILURE);
							response.setMessage(ResponseStatus.FAILURE.getValue());
							response.setDetails("Technical Error");
							return new ResponseEntity<ResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);
						}

					} catch (Exception e) {
						response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
						response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
						response.setDetails(new ArrayList<>());
						e.printStackTrace();
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);
					}

				} else {
					response.setCode(ResponseStatus.MPIN_DOES_NOT_MATCH);
					response.setStatus("FAILURE");
					response.setMessage(ResponseStatus.MPIN_DOES_NOT_MATCH.getValue());
					response.setDetails(new ArrayList<>());
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.UNAUTHORIZED);
				}
			}

		}
		response.setCode(ResponseStatus.UNAUTHORIZED_USER);
		response.setStatus("FAILURE");
		response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		response.setDetails(new ArrayList<>());
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/fundtransfer", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> fundTransfer(HttpServletRequest request) {
		ResponseDTO response = new ResponseDTO();
		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getParameter("from_account_number") == null
				|| request.getParameter("from_account_number").isEmpty()) {
			response.setCode(ResponseStatus.ACCOUNT_NUMBER_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.ACCOUNT_NUMBER_REQUIRED.getValue());
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getParameter("to_account_number") == null || request.getParameter("to_account_number").isEmpty()) {
			response.setCode(ResponseStatus.ACCOUNT_NUMBER_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.ACCOUNT_NUMBER_REQUIRED.getValue());
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getParameter("amount") == null) {
			response.setCode(ResponseStatus.AMOUNT_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.AMOUNT_REQUIRED.getValue());
			response.setDetails(new ArrayList<>());
		}
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
				Double amount = Double.parseDouble(request.getParameter("amount"));
				UserDTO userDto = userApi.getUserWithId((AuthenticationUtil.getCurrentUser().getId()));
				userDto.setOldPassword(request.getParameter("mPin"));
				Boolean valid = mPinValidation.mpinValidation(userDto);
				if (valid) {
					// String sourceAccountNumber = "00200201300028597000001";
					try {
						// validate the account number of sender
						CustomerDTO customer = customerApi
								.getCustomerById(AuthenticationUtil.getCurrentUser().getAssociatedId());
						CustomerBankAccountDTO customerBankAcocunt = customerBankAccountApi
								.findCustomerBankAccountByAccountNo(request.getParameter("from_account_number"),
										customer.getId());
						if (customer.getState() != null && customer.getStatus().equals(CustomerStatus.Blocked)) {
							response.setCode(ResponseStatus.CUSTOMER_BLOCKED);
							response.setStatus("FAILURE");
							response.setMessage(
									StringConstants.CUSTOMER_BLOCKED.replace("{bank}", customerBankAcocunt.getBank()));
							return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
						}
						HashMap<String, String> checkTransactionLimit = customerProfileApi.checkTransactionLimit(
								customerBankAcocunt, Double.parseDouble(request.getParameter("amount")));

						if (checkTransactionLimit.get("valid").equals("false")) {
							response.setCode(ResponseStatus.TRANSACTION_LIMIT_REACHED);
							response.setStatus("FAILURE");
							response.setMessage(checkTransactionLimit.get("message"));
							return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);

						}

						// double balance =
						// customerApi.getCustomerBankBalance(customer.getUniqueId(),
						// request.getParameter("from_account_number"));
						//
						// if (balance <
						// Double.parseDouble(request.getParameter("amount"))) {
						// response.setCode(ResponseStatus.INSUFFICIENT_BALANCE);
						// response.setStatus("FAILURE");
						// response.setMessage(ResponseStatus.INSUFFICIENT_BALANCE.getValue());
						// response.setDetails(new ArrayList<>());
						// return new ResponseEntity<ResponseDTO>(response,
						// HttpStatus.FORBIDDEN);
						// }
						String responseFund = transactionApi.fundTransferTransacion(
								request.getParameter("from_account_number"), request.getParameter("to_account_number"),
								Double.parseDouble(request.getParameter("amount")), TransactionType.Transfer,
								customer.getId());
						// String responseFund =
						// customerApi.fundTransfer(request.getParameter("from_account_number"),
						// request.getParameter("to_account_number"),
						// Double.parseDouble(request.getParameter("amount")));

						if (responseFund.equals("success")) {
							response.setCode(ResponseStatus.SUCCESS);
							response.setStatus("SUCCESS");
							// response.setMessage(ResponseStatus.SUCCESS.getValue());
							response.setMessage("Your fund transfer of NPR " + amount + " to a/c No. "
									+ request.getParameter("to_account_number") + " was successful as of " + new Date()
									+ " Thank you.-" + customerBankAcocunt.getBank());
							return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
						} else {
							System.out.println(responseFund + " FUND TRF RESPONSE");
							response.setCode(ResponseStatus.FAILURE);
							response.setStatus("FAILURE");
							// response.setMessage(ResponseStatus.FAILURE.getValue());
							response.setMessage(
									"Your fund transfer to a/c No. " + request.getParameter("to_account_number")
											+ " was failed. Please, Try again later");
							return new ResponseEntity<ResponseDTO>(response, HttpStatus.CONFLICT);
						}
					} catch (Exception e) {
						response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
						response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
						response.setDetails(new ArrayList<>());
						e.printStackTrace();
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);
					}
				} else {
					response.setCode(ResponseStatus.MPIN_DOES_NOT_MATCH);
					response.setStatus("FAILURE");
					response.setMessage(ResponseStatus.MPIN_DOES_NOT_MATCH.getValue());
					response.setDetails(new ArrayList<>());
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.UNAUTHORIZED);

				}
			}
		}
		response.setCode(ResponseStatus.UNAUTHORIZED_USER);
		response.setStatus("FAILURE");
		response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		response.setDetails(new ArrayList<>());
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/ministatement", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> getMiniStatement(HttpServletRequest request) {
		ResponseDTO response = new ResponseDTO();
		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getParameter("account_number") == null || request.getParameter("account_number").isEmpty()) {
			response.setCode(ResponseStatus.ACCOUNT_NUMBER_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.ACCOUNT_NUMBER_REQUIRED.getValue());
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
				UserDTO userDto = userApi.getUserWithId((AuthenticationUtil.getCurrentUser().getId()));
				userDto.setOldPassword(request.getParameter("mPin"));
				Boolean valid = mPinValidation.mpinValidation(userDto);
				if (valid) {
					try {
						CustomerDTO customer = customerApi
								.getCustomerById(AuthenticationUtil.getCurrentUser().getAssociatedId());
						CustomerBankAccountDTO customerBankAcocunt = customerBankAccountApi
								.findCustomerBankAccountByAccountNo(request.getParameter("account_number"),
										customer.getId());
						// call api which then hits bank iso
						if (customer.getState() != null && customer.getStatus().equals(CustomerStatus.Blocked)) {
							response.setCode(ResponseStatus.CUSTOMER_BLOCKED);
							response.setStatus("FAILURE");
							response.setMessage(
									StringConstants.CUSTOMER_BLOCKED.replace("{bank}", customerBankAcocunt.getBank()));
							return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
						}

						MiniStatementRespose miniStatement = customerApi.getMiniStatementOfUser(customer.getUniqueId(),
								request.getParameter("account_number"));
						// List<MiniStatement> miniStatement =
						// customerApi.getMiniStatementOfUser(customer.getUniqueId(),
						// request.getParameter("account_number"));
						if (miniStatement != null) {
							response.setStatus("SUCCCESS");
							response.setCode(ResponseStatus.SUCCESS);
							response.setMessage(ResponseStatus.SUCCESS.getValue());
							response.setDetails(miniStatement);
							return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
						}
						response.setStatus("Failure");
						response.setCode(ResponseStatus.No_MINISTATEMENT);
						response.setMessage(ResponseStatus.No_MINISTATEMENT.getValue());
						response.setDetails(new ArrayList<>());
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);

					} catch (Exception e) {
						response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
						response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
						response.setDetails(new ArrayList<>());
						e.printStackTrace();
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);
					}
				} else {
					response.setCode(ResponseStatus.MPIN_DOES_NOT_MATCH);
					response.setStatus("FAILURE");
					response.setMessage(ResponseStatus.MPIN_DOES_NOT_MATCH.getValue());
					response.setDetails(new ArrayList<>());
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.UNAUTHORIZED);
				}
			}
		}
		response.setCode(ResponseStatus.UNAUTHORIZED_USER);
		response.setStatus("FAILURE");
		response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		response.setDetails(new ArrayList<>());
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
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
			response.setMessage(ResponseStatus.CLIENT_REQUIRED.getValue());
			;
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
		}

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
				String clientId = request.getHeader("client");
				try {
					CustomerDTO customer = customerApi
							.getCustomerById(AuthenticationUtil.getCurrentUser().getAssociatedId());
					List<CustomerBankAccountDTO> customerAccounts = customerBankAccountApi
							.findCustomerBankAccountByCustomer(customer.getUniqueId(), clientId);
					response.setStatus("SUCCCESS");
					response.setCode(ResponseStatus.SUCCESS);
					response.setMessage(ResponseStatus.SUCCESS.getValue());
					response.setDetails(customerAccounts);
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
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

	@RequestMapping(value = "/topup", method = RequestMethod.POST)
	public ResponseEntity<TransactionResponseDTO> pay(HttpServletRequest request) {
		TransactionResponseDTO response = new TransactionResponseDTO();

		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getParameter("service_identifier") == null) {
			response.setCode(ResponseStatus.SERVICE_IDENTIFIER_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.SERVICE_IDENTIFIER_REQUIRED.getValue());
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

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
				// check balance
				String serviceIdentifier = request.getParameter("service_identifier");
				String mobileNumber = request.getParameter("phone_number").trim();
				double amount = Double.parseDouble(request.getParameter("amount"));
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
						MerchantServiceDTO service = serviceApi.findServiceByUniqueIdentifier(serviceIdentifier);
						BankDTO bank = bankApi.getBankByCode(customer.getBankCode());
						CustomerBankAccountDTO customerBankAcocunt = customerBankAccountApi
								.findCustomerBankAccountByAccountNo(request.getParameter("account_number"),
										customer.getId());

						if (customer.getState() != null && customer.getStatus().equals(CustomerStatus.Blocked)) {
							response.setCode(ResponseStatus.CUSTOMER_BLOCKED);
							response.setStatus("FAILURE");
							response.setMessage(
									StringConstants.CUSTOMER_BLOCKED.replace("{bank}", customerBankAcocunt.getBank()));
							return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.BAD_REQUEST);
						}
						// double balance = 500.00;
						checkBankBalanceForNotification(customerBankAcocunt.getBankId(), customerBankAcocunt.getBank());
						double balance = accountApi.getSpendableAmount(bank.getId());
						if (balance >= amount) {
							HashMap<String, String> myHash = new HashMap<>();
							Map<String, String> hashResponse = merchantPaymentApi.merchantPayment(serviceIdentifier,
									mobileNumber, amount, AuthenticationUtil.getCurrentUser().getId(),
									request.getParameter("account_number"), myHash);
							TransactionResponseDTO transactionResponse = new TransactionResponseDTO();
							if (hashResponse.get("status").equals("success")) {
								transactionResponse.setDate(hashResponse.get("transactionDate"));
								transactionResponse.setServiceTo(hashResponse.get("ServiceTo"));
								transactionResponse.setTransactionIdentifier(hashResponse.get("transactionIdentifier"));
								transactionResponse.setMessage("Your " + service.getService() + " Top up for "+mobileNumber+" of NPR "
										+ amount + " was successful as of " + new Date() + ".TID : "
										+ transactionResponse.getTransactionIdentifier() + " Thank You.-"
										+ customerBankAcocunt.getBank());
								transactionResponse.setStatus(ResponseStatus.SUCCESS.getKey());
								return new ResponseEntity<TransactionResponseDTO>(transactionResponse, HttpStatus.OK);
							} else if (hashResponse.get("status").equals("unknown")) {
								transactionResponse.setStatus(ResponseStatus.AMBIGUOUS_TRANSACTION.getKey());
								transactionResponse.setDate(hashResponse.get("transactionDate"));
								transactionResponse.setServiceTo(hashResponse.get("ServiceTo"));
								transactionResponse.setTransactionIdentifier(hashResponse.get("transactionIdentifier"));
								transactionResponse.setMessage("Technical Error Please Contact Administrator");
								return new ResponseEntity<TransactionResponseDTO>(transactionResponse,
										HttpStatus.CONFLICT);
							} else {
								transactionResponse.setDate(hashResponse.get("transactionDate"));
								// transactionResponse.setMessage("Technical
								// Error Please Contact Administrator");
								// transactionResponse.setStatus(ResponseStatus.BAD_REQUEST.getKey());
								transactionResponse.setMessage(hashResponse.get("" + "Result Message"));
								return new ResponseEntity<TransactionResponseDTO>(transactionResponse,
										HttpStatus.EXPECTATION_FAILED);
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

	@RequestMapping(value = "/wlinkpackages", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> wlinkPackages(HttpServletRequest request) throws ClientException {
		ResponseDTO response = new ResponseDTO();

		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getParameter("wlink_username") == null) {
			response.setCode(ResponseStatus.USER_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.USER_REQUIRED.getValue());
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getParameter("service_identifier") == null) {
			response.setCode(ResponseStatus.SERVICE_IDENTIFIER_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.SERVICE_IDENTIFIER_REQUIRED.getValue());
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
				// gET wORLD LINK WorldLinkPackage
				CustomerDTO customer = customerApi
						.getCustomerById(AuthenticationUtil.getCurrentUser().getAssociatedId());
				if (customer.getState() != null && customer.getStatus().equals(CustomerStatus.Blocked)) {
					response.setCode(ResponseStatus.CUSTOMER_BLOCKED);
					response.setStatus("FAILURE");
					response.setMessage(StringConstants.CUSTOMER_BLOCKED.replace("{bank}", ""));
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
				}
				try {

					WorldLinkPackage worldLinkPackage = merchantPaymentApi.worldLinkCheck(
							request.getParameter("service_identifier"), request.getParameter("wlink_username"), 0.0,
							AuthenticationUtil.getCurrentUser().getId());
					if (worldLinkPackage.getHashResponse().get("status").equals("success")) {
						response.setStatus("Success");
						response.setDetails(worldLinkPackage);
						response.setCode(ResponseStatus.SUCCESS);
						response.setMessage(worldLinkPackage.getHashResponse().get("Result Message"));
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
					} else {
						response.setStatus("Failure");
						response.setCode(ResponseStatus.BAD_REQUEST);
						response.setMessage(worldLinkPackage.getHashResponse().get("Result Message"));
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
					}

				} catch (JAXBException e) {
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

	@RequestMapping(value = "/wlinkpay", method = RequestMethod.POST)
	public ResponseEntity<TransactionResponseDTO> wlinkPay(HttpServletRequest request,
			@RequestBody HashMap<String, String> hash) {

		TransactionResponseDTO response = new TransactionResponseDTO();

		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getParameter("service_identifier") == null) {
			response.setCode(ResponseStatus.SERVICE_IDENTIFIER_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.SERVICE_IDENTIFIER_REQUIRED.getValue());
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

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
				// check balance
				String serviceIdentifier = request.getParameter("service_identifier");
				String username = request.getParameter("wlink_username");
				double amount = Double.parseDouble(request.getParameter("amount")) / 100;

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
						CustomerBankAccountDTO customerBankAcocunt = customerBankAccountApi
								.findCustomerBankAccountByAccountNo(request.getParameter("account_number"),
										customer.getId());

						if (customer.getState() != null && customer.getStatus().equals(CustomerStatus.Blocked)) {
							response.setCode(ResponseStatus.CUSTOMER_BLOCKED);
							response.setStatus("FAILURE");
							response.setMessage(
									StringConstants.CUSTOMER_BLOCKED.replace("{bank}", customerBankAcocunt.getBank()));
							return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.BAD_REQUEST);
						}
						checkBankBalanceForNotification(customerBankAcocunt.getBankId(), customerBankAcocunt.getBank());
						BankDTO bank = bankApi.getBankByCode(customer.getBankCode());
						double balance = accountApi.getSpendableAmount(bank.getId());
						if (balance >= amount) {
							Map<String, String> hashResponse = merchantPaymentApi.merchantPayment(serviceIdentifier,
									username, amount, AuthenticationUtil.getCurrentUser().getId(),
									request.getParameter("account_number"), hash);
							TransactionResponseDTO transactionResponse = new TransactionResponseDTO();
							if (hashResponse.get("status").equals("success")) {
								transactionResponse.setDate(hashResponse.get("transactionDate"));
								transactionResponse.setServiceTo(hashResponse.get("ServiceTo"));
								transactionResponse.setTransactionIdentifier(hashResponse.get("transactionIdentifier"));
								transactionResponse.setMessage("Your " + service.getService() + " Payment of NPR "
										+ amount + " was successful as of " + new Date() + ".TID : "
										+ transactionResponse.getTransactionIdentifier() + " Thank You, "
										+ customerBankAcocunt.getBank());
								transactionResponse.setStatus(ResponseStatus.SUCCESS.getKey());
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
										HttpStatus.METHOD_FAILURE);
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

	@RequestMapping(value = "/subisupay", method = RequestMethod.POST)
	public ResponseEntity<TransactionResponseDTO> subisuPay(HttpServletRequest request) {

		TransactionResponseDTO response = new TransactionResponseDTO();

		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getParameter("service_identifier") == null) {
			response.setCode(ResponseStatus.SERVICE_IDENTIFIER_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.SERVICE_IDENTIFIER_REQUIRED.getValue());
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

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
				// check balance
				String serviceIdentifier = request.getParameter("service_identifier");
				String mobileNo = request.getParameter("phone_number");
				String customerId = request.getParameter("customerId");
				double amount = Double.parseDouble(request.getParameter("amount"));
				HashMap<String, String> hash = new HashMap<>();
				hash.put("subisuUsername", customerId);

				if (mobileNo == null || mobileNo.trim().equals("") || mobileNo.length() != 10) {
					response.setCode(ResponseStatus.INVALID_MOBILE_NUMBER);
					response.setStatus("FAILURE");
					response.setMessage(ResponseStatus.INVALID_MOBILE_NUMBER.getValue());
					return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);
				}

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
						CustomerBankAccountDTO customerBankAcocunt = customerBankAccountApi
								.findCustomerBankAccountByAccountNo(request.getParameter("account_number"),
										customer.getId());

						if (customer.getState() != null && customer.getStatus().equals(CustomerStatus.Blocked)) {
							response.setCode(ResponseStatus.CUSTOMER_BLOCKED);
							response.setStatus("FAILURE");
							response.setMessage(
									StringConstants.CUSTOMER_BLOCKED.replace("{bank}", customerBankAcocunt.getBank()));
							return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.BAD_REQUEST);
						}
						checkBankBalanceForNotification(customerBankAcocunt.getBankId(), customerBankAcocunt.getBank());
						BankDTO bank = bankApi.getBankByCode(customer.getBankCode());
						double balance = accountApi.getSpendableAmount(bank.getId());
						if (balance >= amount) {
							Map<String, String> hashResponse = merchantPaymentApi.merchantPayment(serviceIdentifier,
									mobileNo, amount, AuthenticationUtil.getCurrentUser().getId(),
									request.getParameter("account_number"), hash);
							TransactionResponseDTO transactionResponse = new TransactionResponseDTO();
							if (hashResponse.get("status").equals("success")) {
								transactionResponse.setDate(hashResponse.get("transactionDate"));
								transactionResponse.setServiceTo(hashResponse.get("ServiceTo"));
								transactionResponse.setTransactionIdentifier(hashResponse.get("transactionIdentifier"));
								transactionResponse.setMessage("Your " + service.getService() + " Top up of NPR "
										+ amount + " was successful as of " + new Date() + ".TID : "
										+ transactionResponse.getTransactionIdentifier() + " Thank You, "
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
							} else {
								transactionResponse.setDate(hashResponse.get("transactionDate"));
								// transactionResponse.setMessage("Technical
								// Error Please Contact Administrator");
								// transactionResponse.setStatus(ResponseStatus.BAD_REQUEST.getKey());
								transactionResponse.setMessage(hashResponse.get("Result Message"));
								return new ResponseEntity<TransactionResponseDTO>(transactionResponse,
										HttpStatus.EXPECTATION_FAILED);
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

	@RequestMapping(value = "/getpinnumber", method = RequestMethod.POST)
	public ResponseEntity<TransactionResponseDTO> getPinNumber(HttpServletRequest request) {

		TransactionResponseDTO response = new TransactionResponseDTO();

		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getParameter("service_identifier") == null) {
			response.setCode(ResponseStatus.SERVICE_IDENTIFIER_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.SERVICE_IDENTIFIER_REQUIRED.getValue());
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

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
				// check balance
				String serviceIdentifier = request.getParameter("service_identifier");
				double amount = Double.parseDouble(request.getParameter("amount"));
				HashMap<String, String> hash = new HashMap<>();

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
						CustomerBankAccountDTO customerBankAcocunt = customerBankAccountApi
								.findCustomerBankAccountByAccountNo(request.getParameter("account_number"),
										customer.getId());

						if (customer.getState() != null && customer.getStatus().equals(CustomerStatus.Blocked)) {
							response.setCode(ResponseStatus.CUSTOMER_BLOCKED);
							response.setStatus("FAILURE");
							response.setMessage(
									StringConstants.CUSTOMER_BLOCKED.replace("{bank}", customerBankAcocunt.getBank()));
							return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.BAD_REQUEST);
						}
						checkBankBalanceForNotification(customerBankAcocunt.getBankId(), customerBankAcocunt.getBank());
						BankDTO bank = bankApi.getBankByCode(customer.getBankCode());
						double balance = accountApi.getSpendableAmount(bank.getId());
						if (balance >= amount) {
							Map<String, String> hashResponse = merchantPaymentApi.merchantPayment(serviceIdentifier,
									null, amount, AuthenticationUtil.getCurrentUser().getId(),
									request.getParameter("account_number"), hash);
							TransactionResponseDTO transactionResponse = new TransactionResponseDTO();
							if (hashResponse.get("status").equals("success")) {
								transactionResponse.setDate(hashResponse.get("transactionDate"));
								// transactionResponse.setServiceTo(hashResponse.get("ServiceTo"));
								transactionResponse.setTransactionIdentifier(hashResponse.get("transactionIdentifier"));
								transactionResponse.setPinNo(hashResponse.get("pinNo"));
								transactionResponse.setSerialNo(hashResponse.get("serialNo"));
								transactionResponse.setMessage("Your " + service.getService() + " Payment of NPR "
										+ amount + " was successful as of " + new Date()
										+ " Please note your PIN Number for furthur use \n" + "Pin Number: "
										+ hashResponse.get("pinNo") + "\n" + "Serial Number: "
										+ hashResponse.get("serialNo") + "\n" + " Thank You, "
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
							} else {
								transactionResponse.setDate(hashResponse.get("transactionDate"));
								// transactionResponse.setMessage("Technical
								// Error Please Contact Administrator");
								// transactionResponse.setStatus(ResponseStatus.BAD_REQUEST.getKey());
								transactionResponse.setMessage(hashResponse.get("Result Message"));
								return new ResponseEntity<TransactionResponseDTO>(transactionResponse,
										HttpStatus.EXPECTATION_FAILED);
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

	@RequestMapping(value = "/getneabill", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> getNeaBill(HttpServletRequest request) throws ClientException {
		ResponseDTO response = new ResponseDTO();

		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getParameter("scno") == null) {
			response.setCode(ResponseStatus.USER_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.USER_REQUIRED.getValue());
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getParameter("customerId") == null) {
			response.setCode(ResponseStatus.USER_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.USER_REQUIRED.getValue());
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getParameter("office_code") == null) {
			response.setCode(ResponseStatus.USER_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.USER_REQUIRED.getValue());
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getParameter("service_identifier") == null) {
			response.setCode(ResponseStatus.SERVICE_IDENTIFIER_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.SERVICE_IDENTIFIER_REQUIRED.getValue());
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		String serviceId = request.getParameter("service_identifier");
		String transactionId = request.getParameter("customerId");
		String scno = request.getParameter("scno");
		String officeCode = request.getParameter("office_code");
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
				CustomerDTO customer = customerApi
						.getCustomerById(AuthenticationUtil.getCurrentUser().getAssociatedId());

				if (customer.getState() != null && customer.getStatus().equals(CustomerStatus.Blocked)) {
					response.setCode(ResponseStatus.CUSTOMER_BLOCKED);
					response.setStatus("FAILURE");
					response.setMessage(StringConstants.CUSTOMER_BLOCKED.replace("{bank}", ""));
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
				}

				try {
					NeaBillAmountResponse neaBillAmountResponse = new NeaBillAmountResponse();
					neaBillAmountResponse = merchantPaymentApi.getNeaPaymentAmount(serviceId, transactionId, scno,
							officeCode);
					if (neaBillAmountResponse.getHashResponse().get("status").equals("success")) {
						response.setMessage("Service Accomplish");
						response.setStatus(ResponseStatus.SUCCESS.getKey());
						response.setDetails(neaBillAmountResponse);
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
					}

					else {
						response.setStatus(ResponseStatus.BAD_REQUEST.getKey());
						response.setMessage(neaBillAmountResponse.getHashResponse().get("Result Message"));
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
					}

				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		response.setCode(ResponseStatus.UNAUTHORIZED_USER);
		response.setStatus("FAILURE");
		response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		response.setDetails(new ArrayList<>());
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/neapay", method = RequestMethod.POST)
	public ResponseEntity<TransactionResponseDTO> neapay(HttpServletRequest request,
			@RequestBody HashMap<String, String> hash) {

		TransactionResponseDTO response = new TransactionResponseDTO();

		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getParameter("service_identifier") == null) {
			response.setCode(ResponseStatus.SERVICE_IDENTIFIER_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.SERVICE_IDENTIFIER_REQUIRED.getValue());
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

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
				// check balance
				String serviceIdentifier = request.getParameter("service_identifier");
				String scno = request.getParameter("scno");
				String customerId = request.getParameter("customerId");
				String officeCode = request.getParameter("office_code");

				double amount = Double.parseDouble(request.getParameter("amount")) / 100;
				hash.put("scno", scno);
				hash.put("officeCode", officeCode);
				hash.put("customerId", customerId);

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
						CustomerBankAccountDTO customerBankAcocunt = customerBankAccountApi
								.findCustomerBankAccountByAccountNo(request.getParameter("account_number"),
										customer.getId());

						if (customer.getState() != null && customer.getStatus().equals(CustomerStatus.Blocked)) {
							response.setCode(ResponseStatus.CUSTOMER_BLOCKED);
							response.setStatus("FAILURE");
							response.setMessage(
									StringConstants.CUSTOMER_BLOCKED.replace("{bank}", customerBankAcocunt.getBank()));
							return new ResponseEntity<TransactionResponseDTO>(response, HttpStatus.BAD_REQUEST);
						}
						checkBankBalanceForNotification(customerBankAcocunt.getBankId(), customerBankAcocunt.getBank());
						BankDTO bank = bankApi.getBankByCode(customer.getBankCode());
						double balance = accountApi.getSpendableAmount(bank.getId());
						if (balance >= amount) {
							Map<String, String> hashResponse = merchantPaymentApi.merchantPayment(serviceIdentifier,
									scno, amount, AuthenticationUtil.getCurrentUser().getId(),
									request.getParameter("account_number"), hash);
							TransactionResponseDTO transactionResponse = new TransactionResponseDTO();
							if (hashResponse.get("status").equals("success")) {
								transactionResponse.setDate(hashResponse.get("transactionDate"));
								transactionResponse.setServiceTo(hashResponse.get("ServiceTo"));
								transactionResponse.setTransactionIdentifier(hashResponse.get("transactionIdentifier"));
								transactionResponse.setMessage("Your " + service.getService() + " Top up of NPR "
										+ amount + " was successful as of " + new Date() + ".TID : "
										+ transactionResponse.getTransactionIdentifier() + " Thank You, "
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

	@RequestMapping(value = "/transactionhistory", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> getTransactionHistory(HttpServletRequest request) {
		ResponseDTO response = new ResponseDTO();
		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
				UserDTO userDto = userApi.getUserWithId((AuthenticationUtil.getCurrentUser().getId()));
				userDto.setOldPassword(request.getParameter("mPin"));
				Boolean valid = mPinValidation.mpinValidation(userDto);
				if (valid) {
					try {
						CustomerDTO customer = customerApi
								.getCustomerById(AuthenticationUtil.getCurrentUser().getAssociatedId());
						if (customer.getState() != null && customer.getStatus().equals(CustomerStatus.Blocked)) {
							response.setCode(ResponseStatus.CUSTOMER_BLOCKED);
							response.setStatus("FAILURE");
							response.setMessage(StringConstants.CUSTOMER_BLOCKED.replace("{bank}", customer.getBank()));
							return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
						}

						HashMap<String, Object> transactionList = transactionApi.getTransactionForCustomer(
								AuthenticationUtil.getCurrentUser().getId(), request.getParameter("from_date"),
								request.getParameter("to_date"),request.getParameter("page_no"));
						if (transactionList != null) {
							response.setStatus("SUCCCESS");
							response.setCode(ResponseStatus.SUCCESS);
							response.setMessage(ResponseStatus.SUCCESS.getValue());
							response.setDetails(transactionList);
							return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
						}
						response.setStatus("Failure");
						response.setCode(ResponseStatus.No_TRANSACTION);
						response.setMessage(ResponseStatus.No_TRANSACTION.getValue());
						response.setDetails(new ArrayList<>());
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);

					} catch (Exception e) {
						response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
						response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
						response.setDetails(new ArrayList<>());
						e.printStackTrace();
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);
					}
				} else {
					response.setCode(ResponseStatus.MPIN_DOES_NOT_MATCH);
					response.setStatus("FAILURE");
					response.setMessage(ResponseStatus.MPIN_DOES_NOT_MATCH.getValue());
					response.setDetails(new ArrayList<>());
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.UNAUTHORIZED);
				}
			}
		}
		response.setCode(ResponseStatus.UNAUTHORIZED_USER);
		response.setStatus("FAILURE");
		response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		response.setDetails(new ArrayList<>());
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.UNAUTHORIZED);
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
