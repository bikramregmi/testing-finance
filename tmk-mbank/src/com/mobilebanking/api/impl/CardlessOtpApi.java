package com.mobilebanking.api.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.beanutils.PropertyUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.ICardlessOtpApi;
import com.mobilebanking.api.ISparrowApi;
import com.mobilebanking.api.ITransactionApi;
import com.mobilebanking.entity.Account;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankAccountSettings;
import com.mobilebanking.entity.CardLessOTP;
import com.mobilebanking.entity.CardlessBank;
import com.mobilebanking.entity.CardlessBankAccount;
import com.mobilebanking.entity.CardlessBankFee;
import com.mobilebanking.entity.CustomerBankAccount;
import com.mobilebanking.entity.Iso8583Log;
import com.mobilebanking.entity.OfsRequest;
import com.mobilebanking.entity.OfsResponse;
import com.mobilebanking.entity.OtpInfo;
import com.mobilebanking.entity.SettlementLog;
import com.mobilebanking.entity.SmsLog;
import com.mobilebanking.entity.Transaction;
import com.mobilebanking.model.CustomerBankAccountDTO;
import com.mobilebanking.model.IsoRequestType;
import com.mobilebanking.model.IsoStatus;
import com.mobilebanking.model.OfsResponseStatus;
import com.mobilebanking.model.SettlementStatus;
import com.mobilebanking.model.SettlementType;
import com.mobilebanking.model.SmsStatus;
import com.mobilebanking.model.SmsType;
import com.mobilebanking.model.Status;
import com.mobilebanking.model.TransactionType;
import com.mobilebanking.model.UserType;
import com.mobilebanking.otp.TimeBasedOneTimePasswordGenerator;
import com.mobilebanking.repositories.AccountRepository;
import com.mobilebanking.repositories.BankAccountSettingsRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.CardlessBankAccountsRepository;
import com.mobilebanking.repositories.CardlessBankFeeRepository;
import com.mobilebanking.repositories.CardlessBankRepository;
import com.mobilebanking.repositories.CardlessOtpRepository;
import com.mobilebanking.repositories.CustomerBankAccountRepository;
import com.mobilebanking.repositories.Iso8583LogRepository;
import com.mobilebanking.repositories.OfsMessageRepository;
import com.mobilebanking.repositories.OfsRequestRepository;
import com.mobilebanking.repositories.OtpInfoRepository;
import com.mobilebanking.repositories.SettlementLogRepository;
import com.mobilebanking.repositories.SmsLogRepository;
import com.mobilebanking.repositories.TransactionRepository;
import com.mobilebanking.util.DateUtil;
import com.mobilebanking.util.OtpEncryption;
import com.mobilebanking.util.STANGenerator;
import com.mobilebanking.util.StringConstants;
import com.wallet.iso8583.core.NavaJeevanIsoCore;
import com.wallet.iso8583.network.NetworkTransportNavaJeevan;
import com.wallet.ofs.OfsMessage;
import com.wallet.ofs.service.OfsMessageService;

@Service
public class CardlessOtpApi implements ICardlessOtpApi {

	@Autowired
	private CardlessOtpRepository carlessOtpRepository;

	@Autowired
	private CardlessBankRepository cardlessBankRepository;

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private OtpInfoRepository otpInfoRepository;

	@Autowired
	private OfsMessageRepository ofsMessageRepository;

	@Autowired
	private OfsMessageService ofsMessageService;

	@Autowired
	private OfsRequestRepository ofsRequestRepository;

	@Autowired
	private ITransactionApi transactionApi;

	@Autowired
	private CardlessBankFeeRepository cardlessBankFeeRepository;

	@Autowired
	private CardlessBankAccountsRepository cardlessbankAccountRepository;

	@Autowired
	private AccountRepository accountRepisitory;

	@Autowired
	private SmsLogRepository smsLogRepository;

	@Autowired
	private ISparrowApi sparrowApi;
	
	@Autowired
	private NavaJeevanIsoCore navaJeevanIsoCore;
	
	@Autowired
	private NetworkTransportNavaJeevan networkTransporstNavaJeevan;
	
	@Autowired
	private BankAccountSettingsRepository bankAccountSettingsRepository;
	
	@Autowired
	private Iso8583LogRepository isoLogRepository;

	@Autowired
	private SettlementLogRepository settlementLogRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private CustomerBankAccountRepository customerBankAccountRepository;
	
	@Autowired
	private CustomerApi customerApi;
	
	public static void main(String[] args) throws IOException {
		/* InetAddress host = InetAddress.getLocalHost();
         Socket socket = new Socket("10.0.4.103", 1085);
		BufferedReader in = new BufferedReader (new InputStreamReader (socket.getInputStream ()));
		while (true)
		{
		    String cominginText = "";
		    try
		    {
		        cominginText = in.readLine ();
		        System.out.println (cominginText);
		    }
		    catch (IOException e)
		    {
		        //error ("System: " + "Connection to server lost!");
		        System.exit (1);
		        break;
		    }
		}*/
	
		
	}
	
	@Override
	public Boolean saveAndShareOTP(CustomerBankAccountDTO customerBankAcocuntDTO, String mobileNumber, double amount,
			Long cardlessBankId) throws Exception {

		CardLessOTP cardlessotp = new CardLessOTP();
		cardlessotp.setAccountNumber(customerBankAcocuntDTO.getAccountNumber());
		cardlessotp.setMobileNumber(mobileNumber);
		cardlessotp.setAmount(amount);
		Bank bank = bankRepository.findOne(customerBankAcocuntDTO.getBankId());
		cardlessotp.setCardlessBank(cardlessBankRepository.findOne(cardlessBankId));
		cardlessotp.setBank(bank);
		// need to set fee charge
		Double fee = getFee(cardlessBankId, amount);
		if (fee != null)
			cardlessotp.setFeeCharge(fee);
		else
			return false;
		cardlessotp = carlessOtpRepository.save(cardlessotp);

		CustomerBankAccount customerBankAccount = customerBankAccountRepository.findOne(customerBankAcocuntDTO.getId());
		CardlessBank cardLessBank = cardlessBankRepository.findOne(cardlessBankId);

		OfsMessage ofsMessage = addDetailForOfs(cardLessBank,bank.getId());
		
		ofsMessage.getMessageData().setCARD_NO(mobileNumber);
		ofsMessage.getMessageData().setDEBIT_AMOUNT((amount + fee) + "");
		
		BankAccountSettings bankAccountSettings = bankAccountSettingsRepository
				.getBankAccountSettingsByBankCodeAndStatus(bank.getSwiftCode(), Status.Active);
		// CardlessBankAccount cardlessBankAccount =
		// cardlessbankAccountRepository.findByCardlessBank(cardlessBankId);
		HashMap<String, String> fundTransferResponse = new HashMap<>();
		if (StringConstants.IS_PRODUCTION == true)
			fundTransferResponse = transactionApi.cardlessFundTransferTransacion(
					customerBankAcocuntDTO.getId(), bankAccountSettings.getBankParkingAccountNumber(),
					amount + fee, TransactionType.Cardless);
		else {
			fundTransferResponse.put("status", "success");
		}
		if (fundTransferResponse.get("status").equals("success")) {
			
			cardlessotp =carlessOtpRepository.findOne(cardlessotp.getId());
			cardlessotp.setTransactionIdentifier(fundTransferResponse.get("transactionId"));
			cardlessotp = carlessOtpRepository.save(cardlessotp);
			
			com.wallet.ofs.OfsResponse response = ofsMessageService.composeOfsMessage(ofsMessage,
					cardLessBank.getHost(), cardLessBank.getPort());

			OfsRequest ofsRequest = new OfsRequest();
			ofsRequest.setCardlessBankId(cardlessBankId);
			ofsRequest.setMessage(response.getMessageRequest());
			ofsRequestRepository.save(ofsRequest);

			OfsResponse ofsEntity = new OfsResponse();
			try {
				PropertyUtils.copyProperties(ofsEntity, response);
				ofsEntity.setStatus(OfsResponseStatus.FUNDTRANSFER);
				ofsEntity = ofsMessageRepository.save(ofsEntity);
				
				cardlessotp.setOfsResponse(ofsEntity);
				cardlessotp = carlessOtpRepository.save(cardlessotp);

			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e1) {
				e1.printStackTrace();
			}

			if (response.getResponseCode() == 1) {

				// TODO now send OTP to customer.
				Account account = accountRepisitory.findAccountByBankAndCardlessBank(customerBankAcocuntDTO.getBankId(),
						cardlessBankId);
				account.setBalance(account.getBalance() - amount);
				accountRepisitory.save(account);

				OtpInfo otpinfo = generateOTP(bank, mobileNumber, customerBankAccount.getCustomer().getUniqueId());
				if (otpinfo != null) {
					cardlessotp.setOtpinfo(otpinfo);
					cardlessotp = carlessOtpRepository.save(cardlessotp);

				}

				return true;
			} else {
				String remarksOne = "Reversal " + fundTransferResponse.get("11");
				String remarksTwo = "Cardless Reversal";
				Transaction transaction = transactionRepository
						.findOne(Long.parseLong(fundTransferResponse.get("transactionId")));
				SettlementLog settlementLog = new SettlementLog();
				settlementLog.setBank(bank);
				if (transaction != null)
					settlementLog.setTransaction(transaction);
				settlementLog.setSettlementType(SettlementType.CARDLESS);
				settlementLog.setFromAccountNumber(customerBankAcocuntDTO.getAccountNumber());
				settlementLog.setToAccountNumber(bankAccountSettings.getBankParkingAccountNumber());
				settlementLog.setAmount(amount + fee);

				String reversalResponse = reverseTransaction(customerBankAcocuntDTO.getAccountNumber(),
						bankAccountSettings.getBankParkingAccountNumber(), amount + fee, bank, remarksOne, remarksTwo,
						fundTransferResponse.get("bit90"));

				if (reversalResponse.equals("success")) {
					settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
					if (transaction != null) {
						transaction.setSettlementStatus(SettlementStatus.SUCCESS);
						transaction = transactionRepository.save(transaction);
					}
				} else {
					settlementLog.setSettlementStatus(SettlementStatus.FAILURE);
					if (transaction != null) {
						transaction.setSettlementStatus(SettlementStatus.FAILURE);
						transaction = transactionRepository.save(transaction);
					}
				}
				settlementLogRepository.save(settlementLog);
				return false;
			}

		} else {
			return false;
		}
		// this is reversal code
		/*
		 * try { else{
		 * 
		 * //reversal ofs com.wallet.ofs.OfsResponse reversalResponse =
		 * ofsMessageService.reverseTransaction(ofsMessage,ofsEntity.
		 * getTransactionId(),cardLessBank.getHost(),cardLessBank.getPort());
		 * 
		 * OfsResponse reversalEntity = new OfsResponse(); try {
		 * PropertyUtils.copyProperties(reversalEntity, reversalResponse);
		 * reversalEntity.setStatus(OfsResponseStatus.REVERSAL); ofsEntity =
		 * ofsMessageRepository.save(reversalEntity); } catch
		 * (IllegalAccessException | InvocationTargetException |
		 * NoSuchMethodException e1) { e1.printStackTrace(); } } }
		 */

	}

	private Double getFee(Long cardlessBankId, Double amount) {
		List<CardlessBankFee> feeList = cardlessBankFeeRepository.findFeeByCardlessBankIdAndStatus(cardlessBankId,
				Status.Active);
		for (CardlessBankFee fee : feeList) {
			if (fee.getSameForAll()) {
				return fee.getFee();
			} else {
				break;
			}

		}

		Double feeChrge = cardlessBankFeeRepository.findByAmountRange(amount, cardlessBankId);

		return feeChrge;
	}

	private OfsMessage addDetailForOfs(CardlessBank cardLessBank,long bankId) {
		CardlessBankAccount cardlessBankAccount = cardlessbankAccountRepository.findByCardlessBankAndBank(cardLessBank.getId(), bankId);
		OfsMessage ofsMessage = new OfsMessage();
		ofsMessage.getMessageData().setAT_UNIQUE_ID(System.currentTimeMillis() + "");
		ofsMessage.getMessageData().setATM_TIMESTAMP(System.currentTimeMillis() + "");

		ofsMessage.getUserInfo().setCompanyCode(cardLessBank.getCompanyCode());
		ofsMessage.getUserInfo().setUserPassword(cardLessBank.getUserPassword());
		ofsMessage.getUserInfo().setUserSign(cardLessBank.getUserSign());

		ofsMessage.getMessageData().setATM_TERM_NO(cardlessBankAccount.getAtmTermNo());
		ofsMessage.getMessageData().setATM_BIN_NO(cardlessBankAccount.getAtmBinNo());
		ofsMessage.getMessageData().setDEBIT_THEIR_REF(cardlessBankAccount.getDebitTheirRef());
		ofsMessage.getMessageData().setCREDIT_ACCT_NO(cardlessBankAccount.getCardlessBankAccountNo());
		ofsMessage.getMessageData().setDEBIT_ACCT_NO(cardlessBankAccount.getBankAccountNo());

		return ofsMessage;

	}

	private OtpInfo generateOTP(Bank bank, String mobileNumber, String uniqueId)
			throws NoSuchAlgorithmException, InvalidKeyException, UnknownHostException, InvocationTargetException {

		final TimeBasedOneTimePasswordGenerator totp = new TimeBasedOneTimePasswordGenerator();
		OtpInfo otpinfo = new OtpInfo();
		final Key secretKey;
		{
			final KeyGenerator keyGenerator = KeyGenerator.getInstance(totp.getAlgorithm());

			// SHA-1 and SHA-256 prefer 64-byte (512-bit) keys; SHA512 prefers
			// 128-byte keys
			keyGenerator.init(512);

			secretKey = keyGenerator.generateKey();
		}

		final Date now = new Date();
		// final Date later = new Date(now.getTime() +
		// TimeUnit.SECONDS.toMillis(7200));
		int otp = totp.generateOneTimePassword(secretKey, now);
		System.out.format("Current password: %06d\n", otp);
		String otpString;
		if(String.valueOf(otp).length()<6){
			otpString ="0"+otp;
		}else{
			otpString = String.valueOf(otp);
		}

		otpinfo.setTimeStep(7200 + "");
		otpinfo.setDate(now);

		OtpEncryption otpEncryption = new OtpEncryption();
		try {
			SecretKey key = otpEncryption.getKey();
			try {
				String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
				otpinfo.setSecretKey(otpEncryption.encrypt(encodedKey, key));
			} catch (Exception e) {
				e.printStackTrace();
			}
			// otpinfo.setSecretKey();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		otpinfo = otpInfoRepository.save(otpinfo);

		try {
			// validateOtp(totp.generateOneTimePassword(secretKey,
			// now)+"",otpinfo.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// System.out.format("Future password: %06d\n",
		// totp.generateOneTimePassword(secretKey, later));

	        
	        SmsLog smsLog = createSmsLogOtp(mobileNumber, otpString,bank.getName(),uniqueId);
			Map<String, String> response = sparrowApi.sendSMS(smsLog.getMessage(), mobileNumber);
			smsLog.setStatus(SmsStatus.QUEUED);
			smsLog = smsLogRepository.save(smsLog);
			if (response.get("code").equalsIgnoreCase("200")) {
				smsLog.setStatus(SmsStatus.DELIVERED);
				smsLog.setDelivered(new Date());
				smsLog.setBank(bank);
				smsLog.setSmsType(SmsType.OTP);
				smsLog.setSmsForUser(UserType.Customer);
				smsLog.setSmsFrom(bank.getId());
				String message = StringConstants.OTP_MESSAGE;
				message = message.replace("{otp}", "######");
				message = message.replace("{bank}", bank.getName());
				smsLog.setMessage(message);
				smsLogRepository.save(smsLog);
				bank = bankRepository.findOne(bank.getId());
				bank.setSmsCount(bank.getSmsCount() - 1);
				bank =	bankRepository.save(bank);
			}
	        
	        return otpinfo;
//	        System.out.format("Future password:  %06d\n", totp.generateOneTimePassword(secretKey, later));
	      
	}

	public SmsLog createSmsLogOtp(String mobileNumber, String otp, String bankName, String uniqueId) {
		SmsLog smsLog = new SmsLog();
		String message = StringConstants.OTP_MESSAGE;
		message = message.replace("{otp}", otp);
		message = message.replace("{bank}", bankName);
		smsLog.setMessage(message);
		smsLog.setSmsFor(uniqueId);
		smsLog.setSmsForUser(UserType.Customer);
		smsLog.setStatus(SmsStatus.INITIATED);
		smsLog.setForMobileNo(mobileNumber);
		smsLog = smsLogRepository.save(smsLog);
		return smsLog;
	}

	
	@Override
	public String echo(String isoRequest) {
		// TODO Auto-generated method stub
		HashMap<String, String> unpackedData = new HashMap<>();
		// unpackedData = navaJeevanIsoCore.unpackMessage(isoRequest);
		// String converterHexRequest = navaJeevanIsoCore.convertHexToString(isoRequest);
		// String cvertToHex = navaJeevanIsoCore.convertAsciiToHex(converterHexRequest);
		try {
			unpackedData = navaJeevanIsoCore.unpackMessage(isoRequest);
			System.out.println("unpaccked");
		} catch (Exception e) {
			e.printStackTrace();
//			return navaJeevanIsoCore.convertAsciiToHex(composeDefaultMessage());
			return composeDefaultMessage();
		}
		if (unpackedData.get("mti").equals("0800")) {
			// return  navaJeevanIsoCore.convertAsciiToHex(packEchoMessage(unpackedData));
			return packEchoMessage(unpackedData);
		} else if (unpackedData.get("mti").equals("0420")){
			return handleReversal(unpackedData);
		}else{
			// return navaJeevanIsoCore.convertAsciiToHex(unpackIsoOtpData(isoRequest));
			return unpackIsoOtpData(isoRequest);
		}
	}
	
	private String handleReversal(HashMap<String, String> unpackedData) {
		
		CardLessOTP cardLessOtp = carlessOtpRepository.findByBit90(unpackedData.get("90"));
		cardLessOtp.setIsReversed(true);
		cardLessOtp.setIsWithDrawn(false);
		cardLessOtp.setIsExpired(true);
		cardLessOtp = carlessOtpRepository.save(cardLessOtp);
		
		return null;
	}
	
	@Override
	public String unpackIsoOtpData(String isoRequest) {
		// TODO Auto-generated method stub
		HashMap<String, String> unpackedData = navaJeevanIsoCore.unpackMessage(isoRequest);

		String otp = unpackedData.get("122");

		String pin = unpackedData.get("123");

		String amount = unpackedData.get("4");

		String mobileNumber = unpackedData.get("2");
		String bit90 = null;
		if(unpackedData.get("90")==null){
			try{
				bit90 =  "0200" + unpackedData.get("11") + unpackedData.get("7") + "00000" + unpackedData.get("32");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		

		Double amt = parseAmount(amount);
		List<CardLessOTP> otpList = carlessOtpRepository.findByMobileNumberAndAmount(mobileNumber, amt);
		Boolean valid = false;
		String cardlessAccount = null;
//		cardlessAccount = "2561632132";
		if(otpList!=null && !otpList.isEmpty()){
		for (CardLessOTP cardlessOtp : otpList) {
			try {
				valid = validateOtp(otp, cardlessOtp.getId());
				if(valid == true){
					System.out.println("valid " +valid);
					cardlessOtp.setIsWithDrawn(true);
					if(bit90!=null){
						cardlessOtp.setBit90(bit90);
					}
					if(unpackedData.get("11") != null){
						cardlessOtp.setBit11(unpackedData.get("11"));
					}
					cardlessOtp = carlessOtpRepository.save(cardlessOtp);
					cardlessAccount = cardlessbankAccountRepository.findByCardlessBankAndBank(cardlessOtp.getCardlessBank().getId(), cardlessOtp.getBank().getId()).getCardlessBankAccountNo();
					/*try{
						commitCardlessPayment(cardlessOtp);
					}catch(Exception e){
						e.printStackTrace();
					}*/
					break;
					
				}

			} catch (Exception e) {
				e.printStackTrace();
				valid = false;
				return packCardlessResponse(unpackedData, valid,pin,otp,cardlessAccount);
			}
		}
		}
		
	return packCardlessResponse(unpackedData, valid,pin,otp,cardlessAccount);
	}

	private String composeDefaultMessage() {
		HashMap<String, String> isoData = new HashMap<String, String>();
		String date = DateUtil.convertDateToString(new Date());
		String[] dateArray = date.split("/");
	
		String amountString = String.valueOf(0.00);
		String[] amountStringArray = amountString.split("\\.");
		if (amountStringArray[1].length() == 1) {
			amountString = amountStringArray[0] + amountStringArray[1] + "0";
		} else if (amountStringArray[1].length() == 2) {
			amountString = amountStringArray[0] + amountStringArray[1];
		} else {
			amountString = amountStringArray[0] + amountStringArray[1].substring(0, 2);
		}
		for (int i = amountString.length(); i < 12; i++) {
			amountString = "0" + amountString;
		}
		
		isoData.put("2","0000000000" );
		isoData.put("3", "010000");
		isoData.put("4", amountString);
		isoData.put("7", DateUtil.convertDateToStringForIso(new Date()));
		isoData.put("11", STANGenerator.StanForIso("41"));
		isoData.put("12",DateUtil.convertDateToStringForIso(new Date()).substring(4, 10));
		isoData.put("13", DateUtil.convertDateToStringForIso(new Date()).substring(0, 4));
		isoData.put("17",DateUtil.convertDateToStringForIso(new Date()).substring(0, 4));
		isoData.put("15",DateUtil.convertDateToStringForIso(new Date()).substring(0, 4));
		isoData.put("18", "6011");
//		isoData.put("25", "00");
//		isoData.put("22", "000");
		isoData.put("32", "9999999999"); // would
																				// be
																				// in
																				// Bank
//		isoData.put("33", unpackedData.get("33"));
			isoData.put("39","30");
		// Settings
		// isoData.put("37", DateUtil.convertDateToStringForIso(new Date())
		// + "96");
		isoData.put("41", "00500050");
		 isoData.put("42","ATM000000000000");
		 isoData.put("43", "LXBL DAMAULI------------ DAMAULI----- NP");
		 
		isoData.put("37", DateUtil.convertDateToStringForIso(new Date()) + "98");
		// isoData.put("41", "15548116");
		isoData.put("49","524");
		isoData.put("60","2017201720172017");
//		isoData.put("90", unpackedData.get("90"));
		isoData.put("102", "0000000000");
		isoData.put("122", "000000");
		isoData.put("123", "000000");
		String packedData = null;
		try {
			packedData = navaJeevanIsoCore.packMessage("0200", isoData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return packedData;
	}


	private String packEchoMessage(HashMap<String ,String> unpackedData ){
		HashMap<String, String> isoData = new HashMap<String, String>();
		String date = DateUtil.convertDateToString(new Date());
		String[] dateArray = date.split("/");
		
//		isoData.put("1",unpackedData.get("1") );
		isoData.put("7", unpackedData.get("7"));
		isoData.put("11", unpackedData.get("11"));
		isoData.put("32", unpackedData.get("32"));
		isoData.put("39", "00");
		isoData.put("70", unpackedData.get("70"));
		String packedData = null;
		try {
			packedData = navaJeevanIsoCore.packMessage("0810", isoData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return packedData;
		
		
	}
	
	@Override
	public String sendEcho(String ip,int port) {
		return sendEchoMessage(ip,port);
	}
	
		private String sendEchoMessage(String ip,int port){
		HashMap<String, String> isoData = new HashMap<String, String>();
		String date = DateUtil.convertDateToString(new Date());
		String[] dateArray = date.split("/");
		
		isoData.put("7", DateUtil.convertDateToStringForIso(new Date()));
		isoData.put("11", STANGenerator.StanForIso(""));
		isoData.put("32", "0000044308");
		//ask civil bank
		isoData.put("70", "001");
		//Network management information code from civil bank
		
		try {
			String echoMessage = navaJeevanIsoCore.convertAsciiToHex(navaJeevanIsoCore.packMessage("0800",isoData));
			System.out.println("Echo message "+ echoMessage);
			networkTransporstNavaJeevan.transportIsoMessage(ip, port, echoMessage);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			
		}
		return null;
		
	}
		
	
	
	
	
	private String packCardlessResponse(HashMap<String ,String> unpackedData ,Boolean valid,String pin,String otp,String cardlessAccount){
		HashMap<String, String> isoData = new HashMap<String, String>();
		String date = DateUtil.convertDateToString(new Date());
		String[] dateArray = date.split("/");
		unpackedData.remove("mti");
		if(valid==true){
			unpackedData.put("39","00");
		}else{
			unpackedData.put("39","55");
		}
		if(unpackedData.get("22")==null){
			unpackedData.put("22", "000");
		}
		if(unpackedData.get("25")==null){
			unpackedData.put("25", "00");
		}
		
		if(unpackedData.get("121")==null){
			unpackedData.put("121", "CIVIL");
		}
		//validate 11 should be success
		if(valid)
		unpackedData.put("102",cardlessAccount);
		
	/*	isoData.put("2",unpackedData.get("2") );
		isoData.put("3", unpackedData.get("3"));
		isoData.put("4", unpackedData.get("4"));
		isoData.put("7", unpackedData.get("7"));
		isoData.put("11", unpackedData.get("11"));
		isoData.put("12", unpackedData.get("12"));
		isoData.put("13", unpackedData.get("13"));
		isoData.put("17",unpackedData.get("17"));
		isoData.put("15", unpackedData.get("15"));
		isoData.put("18", unpackedData.get("18"));
//		isoData.put("25", "00");
//		isoData.put("22", "000");
		isoData.put("32", unpackedData.get("32")); // would
																				// be
													*/							// in
																				// Bank
//		isoData.put("33", unpackedData.get("33"));
		
		// Settings
		// isoData.put("37", DateUtil.convertDateToStringForIso(new Date())
		// + "96");
	/*	isoData.put("41", unpackedData.get("41"));
		 isoData.put("42", unpackedData.get("42"));
//		 isoData.put("43", unpackedData.get("43"));
		isoData.put("37", DateUtil.convertDateToStringForIso(new Date()) + "98");
		// isoData.put("41", "15548116");
		isoData.put("49", unpackedData.get("49"));
		isoData.put("60", unpackedData.get("60"));
//		isoData.put("90", unpackedData.get("90"));
		isoData.put("102", unpackedData.get("2"));
		isoData.put("122", otp);
		isoData.put("123", pin);*/
		String packedData = null;
		try {
			packedData = navaJeevanIsoCore.packMessage("0210", unpackedData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return packedData;
	}
	
	private void commitCardlessPayment(CardLessOTP cardlessOtp) {
		 HashMap<String, String> fundTransferResponse = new HashMap<>();
		 CardlessBankAccount cardlessBank =  cardlessbankAccountRepository.findByCardlessBankAndBank(cardlessOtp.getCardlessBank().getId(), cardlessOtp.getCardlessBank().getId());
			BankAccountSettings bankAccountSettings = bankAccountSettingsRepository.getBankAccountSettingsByBankCodeAndStatus(cardlessOtp.getBank().getSwiftCode(), Status.Active);
		 Transaction transaction = transactionRepository.getTransactionByTransactionIdentifier(cardlessOtp.getTransactionIdentifier());
			SettlementLog settlementLog = new SettlementLog();
			settlementLog.setBank(cardlessOtp.getBank());
			settlementLog.setTransaction(transaction);
			settlementLog.setSettlementType(SettlementType.CARDLESS);
			settlementLog.setFromAccountNumber(bankAccountSettings.getBankParkingAccountNumber());
			settlementLog.setToAccountNumber(cardlessBank.getAccountNumber());
			settlementLog.setAmount(cardlessOtp.getAmount()+cardlessOtp.getFeeCharge());
		if (StringConstants.IS_PRODUCTION == true){
			try {
				System.out.println(cardlessOtp.getAmount() + cardlessOtp.getFeeCharge());
				fundTransferResponse = customerApi.accountTransfer(
						bankAccountSettings.getBankParkingAccountNumber(), cardlessBank.getAccountNumber(),
						cardlessOtp.getAmount() + cardlessOtp.getFeeCharge() , cardlessOtp.getBank());
			} catch (Exception e) {
				e.printStackTrace();
				fundTransferResponse.put("status", "failure");
			}
		}
		if(fundTransferResponse.equals("success")){
			settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
		}else{
			settlementLog.setSettlementStatus(SettlementStatus.FAILURE);
			transaction =  transactionRepository.findOne(transaction.getId());
			transaction.setSettlementStatus(SettlementStatus.FAILURE);
			transaction = transactionRepository.save(transaction);
		}
		
	}

	private Double parseAmount(String amountString) {
		double amount = 0.0;
		amountString = amountString.replaceFirst("^0*", "");
		if (!amountString.trim().isEmpty()) {
			System.out.println(amountString + " AMOUNT STRING");
			String decimalPlaces = "." + amountString.substring(amountString.length() - 2, amountString.length());
			amountString = amountString.substring(0, amountString.length() - 2) + decimalPlaces;

			amount = Double.parseDouble(amountString);

		}

		return amount;
	}

	@Override
	public Boolean validateOtp(String otp, Long id) throws Exception {
		System.out.println("validationg otp.....");
		CardLessOTP cardlessOtp = carlessOtpRepository.findOne(id);
		if(cardlessOtp.getIsExpired()==true || cardlessOtp.getIsWithDrawn()==true){
			return false;
		}
		OtpInfo otpInfo = cardlessOtp.getOtpinfo();
		String sectetKey = otpInfo.getSecretKey();
		OtpEncryption otpEncryption = new OtpEncryption();

		SecretKey key = otpEncryption.getKey();

		String otpSecretKey = otpEncryption.decrypt(sectetKey, key);
		byte[] decodedKey = Base64.getDecoder().decode(otpSecretKey);
		// rebuild key using SecretKeySpec

		Date date = new Date();
		date.setHours(date.getHours());
		final TimeBasedOneTimePasswordGenerator totp = new TimeBasedOneTimePasswordGenerator();
		final Key secretKey;
		{

			secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
		}

		System.out.format("Validation password: %06d\n", totp.generateOneTimePassword(secretKey, date));

		if (totp.generateOneTimePassword(secretKey, date) == Integer.parseInt(otp)) {
			return true;
		} else {
			date.setHours(date.getHours() - 2);
			System.out.format("Validation password after 2 hr: %06d\n", totp.generateOneTimePassword(secretKey, date));
			if (totp.generateOneTimePassword(secretKey, date) == Integer.parseInt(otp)) {
				return true;
			}
		}

		return false;

	}

	public String reverseTransaction(String sourceAccountNumber, String destinationAccountNumber, double amount,
			Bank bank, String remarksOne, String remarksTwo, String bit90) {
		HashMap<String, String> isoData = new HashMap<String, String>();
		String amountString = String.valueOf(amount);
		String[] amountStringArray = amountString.split("\\.");
		if (amountStringArray[1].length() == 1) {
			amountString = amountStringArray[0] + amountStringArray[1] + "0";
		} else if (amountStringArray[1].length() == 2) {
			amountString = amountStringArray[0] + amountStringArray[1];
		} else {
			amountString = amountStringArray[0] + amountStringArray[1].substring(0, 2);
		}
		for (int i = amountString.length(); i < 12; i++) {
			amountString = "0" + amountString;
		}
		isoData.put("2", "123467890123456");
		isoData.put("3", "400000"); // processing codes would be in general
									// settings
		isoData.put("4", amountString);
		isoData.put("7", DateUtil.convertDateToStringForIso(new Date()));
		isoData.put("11", STANGenerator.StanForIso("41"));
		isoData.put("12", DateUtil.convertDateToStringForIso(new Date()).substring(4, 10));
		isoData.put("13", DateUtil.convertDateToStringForIso(new Date()).substring(0, 4));
		isoData.put("14", "0000");
		isoData.put("15", DateUtil.convertDateToStringForIso(new Date()).substring(0, 4));
		isoData.put("18", bank.getMerchantType()); // would be in Bank Settings
		isoData.put("25", "00");
		isoData.put("22", "000");
		isoData.put("32", bank.getAcquiringInstitutionIdentificationCode()); // would
																				// be
																				// in
																				// Bank
																				// Settings
		isoData.put("41", bank.getCardAcceptorTerminalIdentification()); // would
																			// be
																			// in
																			// Bank
																			// Settings
																			// isoData.put("41",
																			// "15548116");
		isoData.put("90", bit90);
		isoData.put("37", DateUtil.convertDateToStringForIso(new Date()) + "96");
		isoData.put("49", "524"); // 524 for NRS
		isoData.put("102", sourceAccountNumber);
		isoData.put("103", destinationAccountNumber);
		isoData.put("123", remarksOne);
		isoData.put("124", remarksTwo);
		// 123,124 20 char// service name and amount, / mobile number
		String packedData = "";
		try {
			packedData = navaJeevanIsoCore.packMessage("0200", isoData);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String response = "";
		try {
			response = networkTransporstNavaJeevan.transportIsoMessage(bank.getIsoUrl(),
					Integer.parseInt(bank.getPortNumber()), packedData);
			if (response.equalsIgnoreCase("failure")) {
				Iso8583Log isoLog = new Iso8583Log();
				isoLog.setIsoRequest(packedData);
				isoLog.setRequestType(IsoRequestType.FUNDTRANSFER);
				isoLog.setIsoResponse(response);
				isoLog.setStatus(IsoStatus.NORESPONSE);
				isoLogRepository.save(isoLog);
			}
			HashMap<String, String> unpackedData = navaJeevanIsoCore.unpackMessage(response);
			if (unpackedData.get("39").equals("00")) {
				// now parse the field 47 if account holder name is to be
				// displayed
				Iso8583Log isoLog = new Iso8583Log();
				isoLog.setIsoRequest(packedData);
				isoLog.setIsoResponse(response);
				isoLog.setRequestType(IsoRequestType.FUNDTRANSFER);
				isoLog.setStatus(IsoStatus.SUCCESS);
				isoLog.setResponseCode("00");
				isoLogRepository.save(isoLog);
				return "success";
			} else {
				Iso8583Log isoLog = new Iso8583Log();
				isoLog.setIsoRequest(packedData);
				isoLog.setIsoResponse(response);
				isoLog.setRequestType(IsoRequestType.FUNDTRANSFER);
				isoLog.setStatus(IsoStatus.FAILURE);
				isoLog.setResponseCode(unpackedData.get("39"));
				isoLogRepository.save(isoLog);
				return "failure";
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "failure";
		}

		// return "failure";
	}

	@Scheduled(fixedRate = 60000)
	private void checkOtpExpiration(){
		
		List<CardLessOTP> otpList = carlessOtpRepository.findByIsWithDrawnAndIsExpired(false,false);	
		if(otpList!=null && !otpList.isEmpty() ){
		for(CardLessOTP carlessOtp : otpList){
			
			Period period = new Period(new DateTime(carlessOtp.getCreated()),new DateTime(new Date()));
			 int hour = period.getHours();
			 if(hour>2){
				 carlessOtp.setIsExpired(true);
				 carlessOtp = carlessOtpRepository.save(carlessOtp);
			 }
		}
		}
	
	}


	
	
}
