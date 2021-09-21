package com.mobilebanking.controller;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;

import com.mobilebanking.api.IAccountApi;
import com.mobilebanking.api.IBankApi;
import com.mobilebanking.api.IChequeBlockRequestApi;
import com.mobilebanking.api.IChequeRequestApi;
import com.mobilebanking.api.ICustomerApi;
import com.mobilebanking.api.ICustomerBankAccountApi;
import com.mobilebanking.api.IMerchantPaymentRefactorApi;
import com.mobilebanking.api.IMerchantServiceApi;
import com.mobilebanking.api.IMobileBankingCancelRequestApi;
import com.mobilebanking.api.IPullSmsApi;
import com.mobilebanking.api.ISmsLogApi;
import com.mobilebanking.api.ITransactionApi;
import com.mobilebanking.api.IUserApi;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.CustomerBankAccountDTO;
import com.mobilebanking.model.CustomerDTO;
import com.mobilebanking.model.CustomerStatus;
import com.mobilebanking.model.MerchantServiceDTO;
import com.mobilebanking.model.MiniStatementRespose;
import com.mobilebanking.model.PullSmsDTO;
import com.mobilebanking.model.ShortCodeDTO;
import com.mobilebanking.model.ShortCodeRespone;
import com.mobilebanking.model.SmsType;
import com.mobilebanking.model.TransactionType;
import com.mobilebanking.model.UserDTO;
import com.mobilebanking.model.UserType;
import com.mobilebanking.util.StringConstants;
import com.mobilebanking.validation.MPinVaidation;
import com.mobilebanking.validation.ShortCodeValidation;
import com.wallet.iso8583.model.MiniStatement;

@Controller
public class ShortCodeController implements ServletContextAware {

	@Autowired
	private ShortCodeValidation shortCodeValidation;

	@Autowired
	private ICustomerApi customerApi;

	@Autowired
	private ITransactionApi transactionApi;

	@Autowired
	private IMerchantPaymentRefactorApi merchantPayment;

	@Autowired
	private IUserApi userApi;

	@Autowired
	private IPullSmsApi pullsmsApi;

	@Autowired
	private MPinVaidation mpinValidation;

	@Autowired
	private ICustomerBankAccountApi customerBankApi;

	@Autowired
	private ISmsLogApi smsLogApi;

	@Autowired
	private IChequeRequestApi chequeRequestApi;

	@Autowired
	private IChequeBlockRequestApi chequeBlockRequestApi;

	@Autowired
	private IAccountApi accountApi;

	@Autowired
	private IMerchantServiceApi serviceApi;

	@Autowired
	private IBankApi bankApi;

	@Autowired
	private IMobileBankingCancelRequestApi mobileBankingCancelRequestApi;

	@RequestMapping(method = RequestMethod.GET, value = "/receivesms")
	public ResponseEntity<String> getShortCode(HttpServletRequest request, HttpServletResponse response)
			throws NumberFormatException, Exception {
		try {
			String from = request.getParameter("from");
			from = from.replace("+977", "").trim();
			String to = request.getParameter("to");
			String keyword = request.getParameter("keyword");
			String text = request.getParameter("text");
			switch (request.getParameter("keyword")) {
			case "MBK":
				return saveCancelRequest(from, keyword, text, to);
			case "HTP":
				return checkSmsMode(from, keyword, text, to);
			default:
				return checkSmsMode(from, keyword, text, to);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String message = "Something went wrong. Please try again later or contact your bank.";
			return new ResponseEntity<String>(message, HttpStatus.OK);
		}
	}
	
	public ResponseEntity<String> saveCancelRequest(String from,String keyword,String text,String to)
			throws NumberFormatException, Exception {
		String responseMessage = "";
		try {
			savePullSms(from, keyword, text, to);
			String[] splittedText = text.split(" ");
			if (splittedText[1].equalsIgnoreCase("c")) {
				String bankCode = splittedText[2];
				BankDTO bank = bankApi.getBankByCode(bankCode);
				if (bank != null) {
					CustomerDTO customer = customerApi.getCustomerByBankAndMobileNumber(bank.getId(), from);
					if (customer != null) {
						if(mobileBankingCancelRequestApi.findByCustomer(customer.getId())!= null){
							mobileBankingCancelRequestApi.save(customer.getId());
							responseMessage = "Request has been sent. We will contact you for further detail. Thank You. -"
									+ bank.getName().split(" ")[0];
							return new ResponseEntity<String>(responseMessage, HttpStatus.OK);
						}
						responseMessage = "Request with mobile number " + from + " has already been registered. Thank You. -"
								+ bank.getName().split(" ")[0];
						return new ResponseEntity<String>(responseMessage, HttpStatus.OK);
					}
					responseMessage = "Customer with mobile number " + from + " does not exists. Thank You. -"
							+ bank.getName().split(" ")[0];
					return new ResponseEntity<String>(responseMessage, HttpStatus.OK);
				}
				responseMessage = "Invalid Bank Code. Please check your bank code and try again.";
				return new ResponseEntity<String>(responseMessage, HttpStatus.OK);
			}
			responseMessage = "Invalid keyword. " + splittedText[1] + " is not recognised as a keyword.";
			return new ResponseEntity<String>(responseMessage, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseMessage = "Something went wrong. Please try again later or contact your bank.";
			return new ResponseEntity<String>(responseMessage, HttpStatus.OK);
		}
	}

	private ResponseEntity<String> checkSmsMode(String from,String keyword,String text,String to) {
		ShortCodeRespone shorCodeRespose = new ShortCodeRespone();
		String message = "";
		try {
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
			String[] initText = text.split(" ");
			text = initText[1];
			byte[] clientIdText = Base64.decodeBase64(text);
			String clientIdString = StringUtils.newStringUtf8(clientIdText);
			String[] textArray = clientIdString.split(" ");
			String clientId = textArray[0];
			text = textArray[1];
			String token = userApi.getUserToken(clientId + from, clientId);
			
			savePullSms(from, keyword, text, token);

			message = "Service Currently Unavailable";

			if (token == null) {
				message = "We have Technical Problem, Please contact Administrator";
				return new ResponseEntity<String>(message, HttpStatus.OK);
			}

			byte[] key = token.getBytes("ASCII");

			Cipher rc4 = Cipher.getInstance("RC4");
			SecretKeySpec rc4Key = new SecretKeySpec(key, "RC4");
			rc4.init(Cipher.ENCRYPT_MODE, rc4Key);

			byte[] cipherText = Base64.decodeBase64(text);
			Cipher rc4Decrypt = Cipher.getInstance("RC4");
			rc4Decrypt.init(Cipher.DECRYPT_MODE, rc4Key);
			byte[] decyperText = rc4Decrypt.update(cipherText);
			String decryptedmessage = new String(decyperText, "ASCII");

			HashMap<String, String> myhash = new HashMap<>();
			String[] splittedString = decryptedmessage.split(" ");
			String KeyWord = splittedString[0];
			ShortCodeDTO shortCode = new ShortCodeDTO();
			shortCode.setFrom(from);
			shortCode.setKeyword(keyword);
			shortCode.setText(text);
			shortCode.setTo(to);
			CustomerDTO customer = customerApi.getCustomerBalanceByMobileNumber(shortCode.getFrom(), clientId);
			BankDTO bank = bankApi.getBankByCode(customer.getBankCode());
			CustomerBankAccountDTO account = customerBankApi.findCustomerBankAccountByAccountNo(splittedString[1],
					customer.getId());

			if (customer.getState() != null && customer.getStatus().equals(CustomerStatus.Blocked)) {

				message = StringConstants.CUSTOMER_BLOCKED.replace("{bank}", account.getBank().split(" ")[0]);
				return new ResponseEntity<String>(message, HttpStatus.OK);
			}

			String mpin = splittedString[splittedString.length - 1];
			Boolean valid = mpinValidation.mpinValidation(customer, mpin);
			if (valid) {
				if (shortCodeValidation.validateMobile(shortCode, clientId)) {
					HashMap<String, String> hashmap = new HashMap<>();
					Double balance;
					Double amount;
					UserDTO user;
					switch (KeyWord) {

					case "BALN":
						balance = customerApi.getCustomerBankBalance(customer.getUniqueId(), splittedString[1]);
						if (balance != null) {
							message = StringConstants.BALANCE_ENQUIRY.replace("{balance}", "" + balance);
							if (splittedString[1].length() >= 20) {
								message = message.replace("{accountNumber}",
										splittedString[1].substring(0, splittedString[1].length() - 14) + "########"
												+ splittedString[1].substring(splittedString[1].length() - 6));
							} else {
								message = message.replace("{accountNumber}", splittedString[1]);
							}
							message = message.replace("{bank}", account.getBank().split(" ")[0]);
							message = message.replace("{date}", dateFormat.format(new Date()));
						} else
							message = StringConstants.DEFAULT_SHORTCODE_MESSAGE + account.getBank().split(" ")[0];
						break;
					// send sms
					case "FTRF":
						String fundTransferResponse = transactionApi.fundTransferTransacion(splittedString[1],
								splittedString[2], Double.parseDouble(splittedString[3]), TransactionType.Transfer,
								customer.getId());

						if (fundTransferResponse.equals("success")) {
							message = StringConstants.FUND_TRANSFER.replace("{amount}", splittedString[3]);
							if (splittedString[2].length() >= 20) {
								message = message.replace("{accountNumber}",
										splittedString[2].substring(0, splittedString[2].length() - 14) + "########"
												+ splittedString[2].substring(splittedString[2].length() - 6));
							} else {
								message = message.replace("{accountNumber}", splittedString[2]);
							}
							message = message.replace("{bank}", account.getBank().split(" ")[0]);
							message = message.replace("{date}", dateFormat.format(new Date()));
						} else {
							message = StringConstants.FUND_TRANSFER_FAIL.replace("{amount}", splittedString[3]);
							if (splittedString[2].length() >= 20) {
								message = message.replace("{accountNumber}",
										splittedString[2].substring(0, splittedString[2].length() - 14) + "########"
												+ splittedString[2].substring(splittedString[2].length() - 6));
							} else {
								message = message.replace("{accountNumber}", splittedString[2]);
							}
							message = message.replace("{bank}", account.getBank().split(" ")[0]);
						}
						break;

					case "NTPR":
						user = userApi.findUserByAssociatedIdAndUserType(customer.getId(), UserType.Customer);
						balance = accountApi.getSpendableAmount(bank.getId());
						amount = Double.parseDouble(splittedString[2]);
						if (balance >= amount) {
							MerchantServiceDTO service = serviceApi
									.findServiceByUniqueIdentifier(StringConstants.NTC_PREPAID_TOPUP);
							hashmap = merchantPayment.merchantPayment(StringConstants.NTC_PREPAID_TOPUP,
									splittedString[3], Long.parseLong(splittedString[2]), user.getId(),
									splittedString[1], myhash);
							if (hashmap.get("status").equals("success")) {
								message = StringConstants.PAYMENT_SUCCESFUL.replace("{balance}", splittedString[2]);
								message = message.replace("{transactionId}", hashmap.get("transactionIdentifier"));
								message = message.replace("{date}", dateFormat.format(new Date()));
								message = message.replace("{bank}", account.getBank().split(" ")[0]);
								message = message.replace("{service}", service.getService());
								message = message.replace("{TargetNo}", splittedString[3]);
							} else
								message = StringConstants.DEFAULT_SHORTCODE_MESSAGE + account.getBank().split(" ")[0];
						} else {
							message = "This Service Is Currently Unavailable. Please Contact Bank.";
						}
						break;

					case "NTPP":
						customer = customerApi.getCustomerBalanceByMobileNumber(shortCode.getFrom(), clientId);
						balance = accountApi.getSpendableAmount(bank.getId());
						amount = Double.parseDouble(splittedString[2]);
						if (balance >= amount) {
							user = userApi.findUserByAssociatedIdAndUserType(customer.getId(), UserType.Customer);
							MerchantServiceDTO service = serviceApi
									.findServiceByUniqueIdentifier(StringConstants.NTC_POSTPAID_TOPUP);
							hashmap = merchantPayment.merchantPayment(StringConstants.NTC_POSTPAID_TOPUP,
									splittedString[3], Long.parseLong(splittedString[2]), user.getId(),
									splittedString[1], myhash);
							if (hashmap.get("status").equals("success")) {
								message = StringConstants.PAYMENT_SUCCESFUL.replace("{balance}", splittedString[2]);
								message = message.replace("{transactionId}", hashmap.get("transactionIdentifier"));
								message = message.replace("{date}", dateFormat.format(new Date()));
								message = message.replace("{bank}", account.getBank().split(" ")[0]);
								message = message.replace("{service}", service.getService());
								message = message.replace("{TargetNo}", splittedString[3]);
							} else
								message = StringConstants.DEFAULT_SHORTCODE_MESSAGE + account.getBank().split(" ")[0];
						} else {
							message = "This Service Is Currently Unavailable. Please Contact Bank.";
						}
						break;

					case "NCEL":
						customer = customerApi.getCustomerBalanceByMobileNumber(shortCode.getFrom(), clientId);
						user = userApi.findUserByAssociatedIdAndUserType(customer.getId(), UserType.Customer);
						balance = accountApi.getSpendableAmount(bank.getId());
						amount = Double.parseDouble(splittedString[2]);
						if (balance >= amount) {
							MerchantServiceDTO service = serviceApi
									.findServiceByUniqueIdentifier(StringConstants.NCELL_TOPUP);
							hashmap = merchantPayment.merchantPayment(StringConstants.NCELL_TOPUP, splittedString[3],
									Long.parseLong(splittedString[2]), user.getId(), splittedString[1], myhash);
							if (hashmap.get("status").equals("success")) {
								message = StringConstants.PAYMENT_SUCCESFUL.replace("{balance}", splittedString[2]);
								message = message.replace("{transactionId}", hashmap.get("transactionIdentifier"));
								message = message.replace("{date}", dateFormat.format(new Date()));
								message = message.replace("{bank}", account.getBank().split(" ")[0]);
								message = message.replace("{service}", service.getService());
								message = message.replace("{TargetNo}", splittedString[3]);
							} else
								message = StringConstants.DEFAULT_SHORTCODE_MESSAGE + account.getBank().split(" ")[0];
						} else {
							message = "This Service Is Currently Unavailable. Please Contact Bank.";
						}
						break;
					case "UTL":
						customer = customerApi.getCustomerBalanceByMobileNumber(shortCode.getFrom(), clientId);
						user = userApi.findUserByAssociatedIdAndUserType(customer.getId(), UserType.Customer);
						balance = accountApi.getSpendableAmount(bank.getId());
						amount = Double.parseDouble(splittedString[2]);
						if (balance >= amount) {
							MerchantServiceDTO service = serviceApi.findServiceByUniqueIdentifier(StringConstants.UTL);
							hashmap = merchantPayment.merchantPayment(StringConstants.UTL, splittedString[3],
									Long.parseLong(splittedString[2]), user.getId(), splittedString[1], myhash);
							if (hashmap.get("status").equals("success")) {
								message = StringConstants.PAYMENT_SUCCESFUL.replace("{balance}", splittedString[2]);
								message = message.replace("{transactionId}", hashmap.get("transactionIdentifier"));
								message = message.replace("{date}", dateFormat.format(new Date()));
								message = message.replace("{bank}", account.getBank().split(" ")[0]);
								message = message.replace("{service}", service.getService());
								message = message.replace("{TargetNo}", splittedString[3]);
							} else
								message = StringConstants.DEFAULT_SHORTCODE_MESSAGE + account.getBank().split(" ")[0];
						} else {
							message = "This Service Is Currently Unavailable. Please Contact Bank.";
						}
						break;

					case "CDMA":
						customer = customerApi.getCustomerBalanceByMobileNumber(shortCode.getFrom(), clientId);
						user = userApi.findUserByAssociatedIdAndUserType(customer.getId(), UserType.Customer);
						balance = accountApi.getSpendableAmount(bank.getId());
						amount = Double.parseDouble(splittedString[2]);
						if (balance >= amount) {
							MerchantServiceDTO service = serviceApi.findServiceByUniqueIdentifier(StringConstants.CDMA);
							hashmap = merchantPayment.merchantPayment(StringConstants.CDMA, splittedString[3],
									Long.parseLong(splittedString[2]), user.getId(), splittedString[1], myhash);
							if (hashmap.get("status").equals("success")) {
								message = StringConstants.PAYMENT_SUCCESFUL.replace("{balance}", splittedString[2]);
								message = message.replace("{transactionId}", hashmap.get("transactionIdentifier"));
								message = message.replace("{date}", dateFormat.format(new Date()));
								message = message.replace("{bank}", account.getBank().split(" ")[0]);
								message = message.replace("{service}", service.getService());
								message = message.replace("{TargetNo}", splittedString[3]);
							} else
								message = StringConstants.DEFAULT_SHORTCODE_MESSAGE + account.getBank().split(" ")[0];
						} else {
							message = "This Service Is Currently Unavailable. Please Contact Bank.";
						}
						break;

					case "ADSLV":
						customer = customerApi.getCustomerBalanceByMobileNumber(shortCode.getFrom(), clientId);
						user = userApi.findUserByAssociatedIdAndUserType(customer.getId(), UserType.Customer);
						balance = accountApi.getSpendableAmount(bank.getId());
						amount = Double.parseDouble(splittedString[2]);
						if (balance >= amount) {
							MerchantServiceDTO service = serviceApi
									.findServiceByUniqueIdentifier(StringConstants.ADSLV);
							hashmap = merchantPayment.merchantPayment(StringConstants.ADSLV, splittedString[3],
									Long.parseLong(splittedString[2]), user.getId(), splittedString[1], myhash);
							if (hashmap.get("status").equals("success")) {
								message = StringConstants.PAYMENT_SUCCESFUL.replace("{balance}", splittedString[2]);
								message = message.replace("{transactionId}", hashmap.get("transactionIdentifier"));
								message = message.replace("{date}", dateFormat.format(new Date()));
								message = message.replace("{bank}", account.getBank().split(" ")[0]);
								message = message.replace("{service}", service.getService());
								message = message.replace("{TargetNo}", splittedString[3]);
							} else
								message = StringConstants.DEFAULT_SHORTCODE_MESSAGE + account.getBank().split(" ")[0];
						} else {
							message = "This Service Is Currently Unavailable. Please Contact Bank.";
						}
						break;
					case "ADSLU":
						customer = customerApi.getCustomerBalanceByMobileNumber(shortCode.getFrom(), clientId);
						user = userApi.findUserByAssociatedIdAndUserType(customer.getId(), UserType.Customer);
						balance = accountApi.getSpendableAmount(bank.getId());
						amount = Double.parseDouble(splittedString[2]);
						if (balance >= amount) {
							MerchantServiceDTO service = serviceApi
									.findServiceByUniqueIdentifier(StringConstants.ADSLU);
							hashmap = merchantPayment.merchantPayment(StringConstants.ADSLU, splittedString[3],
									Long.parseLong(splittedString[2]), user.getId(), splittedString[1], myhash);
							if (hashmap.get("status").equals("success")) {
								message = StringConstants.PAYMENT_SUCCESFUL.replace("{balance}", splittedString[2]);
								message = message.replace("{transactionId}", hashmap.get("transactionIdentifier"));
								message = message.replace("{date}", dateFormat.format(new Date()));
								message = message.replace("{bank}", account.getBank().split(" ")[0]);
								message = message.replace("{service}", service.getService());
								message = message.replace("{TargetNo}", splittedString[3]);
							} else
								message = StringConstants.DEFAULT_SHORTCODE_MESSAGE + account.getBank().split(" ")[0];
						} else {
							message = "This Service Is Currently Unavailable. Please Contact Bank.";
						}
						break;

					case "SIM":
						customer = customerApi.getCustomerBalanceByMobileNumber(shortCode.getFrom(), clientId);
						user = userApi.findUserByAssociatedIdAndUserType(customer.getId(), UserType.Customer);
						balance = accountApi.getSpendableAmount(bank.getId());
						amount = Double.parseDouble(splittedString[2]);
						if (balance >= amount) {
							MerchantServiceDTO service = serviceApi
									.findServiceByUniqueIdentifier(StringConstants.SIMTV);
							hashmap = merchantPayment.merchantPayment(StringConstants.SIMTV, splittedString[3],
									Long.parseLong(splittedString[2]), user.getId(), splittedString[1], myhash);
							if (hashmap.get("status").equals("success")) {
								message = StringConstants.PAYMENT_SUCCESFUL.replace("{balance}", splittedString[2]);
								message = message.replace("{transactionId}", hashmap.get("transactionIdentifier"));
								message = message.replace("{date}", dateFormat.format(new Date()));
								message = message.replace("{bank}", account.getBank().split(" ")[0]);
								message = message.replace("{service}", service.getService());
								message = message.replace("{TargetNo}", splittedString[3]);
							} else
								message = StringConstants.DEFAULT_SHORTCODE_MESSAGE + account.getBank().split(" ")[0];
						} else {
							message = "This Service Is Currently Unavailable. Please Contact Bank.";
						}
						break;

					case "PSTN":
						customer = customerApi.getCustomerBalanceByMobileNumber(shortCode.getFrom(), clientId);
						user = userApi.findUserByAssociatedIdAndUserType(customer.getId(), UserType.Customer);
						balance = accountApi.getSpendableAmount(bank.getId());
						amount = Double.parseDouble(splittedString[2]);
						if (balance >= amount) {
							MerchantServiceDTO service = serviceApi.findServiceByUniqueIdentifier(StringConstants.PSTN);
							hashmap = merchantPayment.merchantPayment(StringConstants.PSTN, splittedString[3],
									Long.parseLong(splittedString[2]), user.getId(), splittedString[1], myhash);
							if (hashmap.get("status").equals("success")) {
								message = StringConstants.PAYMENT_SUCCESFUL.replace("{balance}", splittedString[2]);
								message = message.replace("{transactionId}", hashmap.get("transactionIdentifier"));
								message = message.replace("{date}", dateFormat.format(new Date()));
								message = message.replace("{bank}", account.getBank().split(" ")[0]);
								message = message.replace("{service}", service.getService());
								message = message.replace("{TargetNo}", splittedString[3]);
							} else
								message = StringConstants.DEFAULT_SHORTCODE_MESSAGE + account.getBank().split(" ")[0];
						} else {
							message = "This Service Is Currently Unavailable. Please Contact Bank.";
						}
						break;
					case "DISH":
						customer = customerApi.getCustomerBalanceByMobileNumber(shortCode.getFrom(), clientId);
						user = userApi.findUserByAssociatedIdAndUserType(customer.getId(), UserType.Customer);
						balance = accountApi.getSpendableAmount(bank.getId());
						amount = Double.parseDouble(splittedString[2]);
						if (balance >= amount) {
							MerchantServiceDTO service = serviceApi
									.findServiceByUniqueIdentifier(StringConstants.DISHOME_TOPUP);
							hashmap = merchantPayment.merchantPayment(StringConstants.DISHOME_TOPUP, splittedString[3],
									Long.parseLong(splittedString[2]), user.getId(), splittedString[1], myhash);
							if (hashmap.get("status").equals("success")) {
								message = StringConstants.PAYMENT_SUCCESFUL.replace("{balance}", splittedString[2]);
								message = message.replace("{transactionId}", hashmap.get("transactionIdentifier"));
								message = message.replace("{date}", dateFormat.format(new Date()));
								message = message.replace("{bank}", account.getBank().split(" ")[0]);
								message = message.replace("{service}", service.getService());
								message = message.replace("{TargetNo}", splittedString[3]);

							} else
								message = StringConstants.DEFAULT_SHORTCODE_MESSAGE + account.getBank().split(" ")[0];
						} else {
							message = "This Service Is Currently Unavailable. Please Contact Bank.";
						}
						break;

					case "CHRQ":

						boolean chequeRequest = chequeRequestApi.save(splittedString[1],
								Integer.parseInt(splittedString[2]), customer.getId());

						if (chequeRequest) {
							message = StringConstants.CHEQUE_REQUEST.replace("{leaves}", splittedString[2]);
							message = message.replace("{date}", dateFormat.format(new Date()));
							message = message.replace("{bank}", account.getBank().split(" ")[0]);

						} else
							message = StringConstants.DEFAULT_SHORTCODE_MESSAGE + account.getBank().split(" ")[0];
						break;
					case "CHBL":

						if (chequeBlockRequestApi.save(splittedString[1], splittedString[2],
								customer.getId()) != null) {
							message = StringConstants.CHEQUE_BLOCK.replace("{cheaqueNO}", splittedString[2]);
							message = message.replace("{date}", dateFormat.format(new Date()));
							message = message.replace("{bank}", account.getBank().split(" ")[0]);
						} else
							message = StringConstants.DEFAULT_SHORTCODE_MESSAGE + account.getBank().split(" ")[0];
						break;
					case "STMT":
						customer = customerApi.getCustomerBalanceByMobileNumber(shortCode.getFrom(), clientId);
						MiniStatementRespose statementResponse = customerApi
								.getMiniStatementOfUser(customer.getUniqueId(), splittedString[1]);

						List<MiniStatement> miniStatement = statementResponse.getMinistatementList();

						Collections.sort(miniStatement, new Comparator<MiniStatement>() {
							@Override
							public int compare(MiniStatement o1, MiniStatement o2) {
								return o2.getTransactionDate().compareTo(o1.getTransactionDate());
							}
						});

						if (miniStatement != null) {
							message = "Your mini statement \n";

							for (int i = 0; i < 5; i++) {
								String remark;

								if (miniStatement.get(i).isCredit == true) {
									remark = "Cr.";
								} else {
									remark = "Dr.";
								}
								message = message + " " + miniStatement.get(i).transactionDate + " " + remark + " "
										+ miniStatement.get(i).getAmount() + "\n";
							}
							message = message + "Thank you " + account.getBank().split(" ")[0];
						} else
							message = StringConstants.DEFAULT_SHORTCODE_MESSAGE + account.getBank().split(" ")[0];

						break;

					default:
						message = StringConstants.DEFAULT_SHORTCODE_MESSAGE + account.getBank().split(" ")[0];
						break;
					}
				} else {
					message = "Sorry Invalid Parameter, Thank you " + account.getBank().split(" ")[0];
				}
			} else {
				message = "Sorry Invalid Mpin, Thank you " + account.getBank().split(" ")[0];
			}
			smsLogApi.creatShortCodeSmsLog(customer, SmsType.NOTIFICATION, splittedString[1], message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		shorCodeRespose.setMessage(message);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
	
	private void savePullSms(String from,String keyword,String text,String to){
		PullSmsDTO pullMessage = new PullSmsDTO();
		pullMessage.setSmsfrom(from);
		pullMessage.setSmskeyword(keyword);
		pullMessage.setSmstext(text);
		pullMessage.setSmsto(to);
		pullsmsApi.savePullSms(pullMessage);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/test1")
	public ResponseEntity<Object> test(HttpServletRequest request, HttpServletResponse response)
			throws NumberFormatException, Exception {

		// String a = "489D120B4B1342F30D5B46";
		// byte[] b = a.getBytes();
		// byte [] cipherText44 =
		// DatatypeConverter.parseHexBinary(DatatypeConverter.printHexBinary(b));
		byte[] key = "YuHQ2oBs".getBytes("ASCII");
		//
		// String clearText = "Hello World";
		//
		Cipher rc4 = Cipher.getInstance("RC4");
		SecretKeySpec rc4Key = new SecretKeySpec(key, "RC4");
		rc4.init(Cipher.ENCRYPT_MODE, rc4Key);
		//
		// byte [] cipherText = rc4.update(clearText.getBytes("ASCII"));
		//
		// System.out.println("clear (ascii) " + clearText);
		// System.out.println("clear (hex) " +
		// DatatypeConverter.printHexBinary(clearText.getBytes("ASCII")));
		// System.out.println("cipher (hex) is " +
		// DatatypeConverter.printHexBinary(cipherText));
		//
		// byte [] cipherText1 =
		// DatatypeConverter.parseHexBinary(DatatypeConverter.printHexBinary(cipherText));

		// String s = DatatypeConverter.printHexBinary(cipherText)
		// int len = DatatypeConverter.printHexBinary(cipherText).length();
		// byte[] data = new byte[len / 2];
		// for (int i = 0; i < len; i += 2) {
		// data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
		// + Character.digit(s.charAt(i+1), 16));
		// }
		String str = "GyZnwXQooycdyuVIhNwOnrp23ZRyM7Sk4LbH4SC5x7gZPA==";
		// String cipherText =
		// DatatypeConverter.printBase64Binary(str.getBytes());
		byte[] cipherText = Base64.decodeBase64(str);
		// System.out.println("back to (hex) is " +cipherText1);
		Cipher rc4Decrypt = Cipher.getInstance("RC4");
		rc4Decrypt.init(Cipher.DECRYPT_MODE, rc4Key);
		byte[] clearText2 = rc4Decrypt.update(cipherText);

		System.out.println("decrypted (clear) is " + new String(clearText2, "ASCII"));

		return null;

	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub

	}

}
