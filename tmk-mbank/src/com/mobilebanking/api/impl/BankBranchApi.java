/**
 * 
 */
package com.mobilebanking.api.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IBankBranchApi;
import com.mobilebanking.api.IEmailApi;
import com.mobilebanking.api.ISparrowApi;
import com.mobilebanking.converter.BankBranchConverter;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankBranch;
import com.mobilebanking.entity.BankOAuthClient;
import com.mobilebanking.entity.City;
import com.mobilebanking.entity.SmsLog;
import com.mobilebanking.entity.User;
import com.mobilebanking.entity.UserLog;
import com.mobilebanking.model.BankBranchDTO;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.SmsStatus;
import com.mobilebanking.model.SmsType;
import com.mobilebanking.model.Status;
import com.mobilebanking.model.UserType;
import com.mobilebanking.repositories.BankBranchRepository;
import com.mobilebanking.repositories.BankOAuthClientRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.CityRepository;
import com.mobilebanking.repositories.SmsLogRepository;
import com.mobilebanking.repositories.UserLogRepository;
import com.mobilebanking.repositories.UserRepository;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.ConvertUtil;
import com.mobilebanking.util.PasswordGenerator;
import com.mobilebanking.util.StringConstants;

/**
 * @author bibek
 *
 */
@Service
public class BankBranchApi implements IBankBranchApi {
	
	@Autowired
	private BankRepository bankRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private BankBranchRepository bankBranchRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordGenerator passwordGenerator;
	@Autowired
	private SmsLogRepository smsLogRepository;
	@Autowired
	private ISparrowApi sparrowApi;
	@Autowired
	private UserLogRepository userLogRepository;
	
	@Autowired
	private IEmailApi emailApi;
	@Autowired
	private BankBranchConverter bankBranchConverter;	
	@Autowired
	private BankOAuthClientRepository bankAuthRepository;
	
	/* (non-Javadoc)
	 * @see com.mobilebanking.api.BankBranchApi#saveBranch(com.mobilebanking.model.BankBranchDTO)
	 */
	@Override
	public void saveBranch(BankBranchDTO branchDto) {
		BankBranch bankBranch = new BankBranch();
		System.out.println(branchDto.getBank() + " THIS IS BANK ID");
		Bank bank = bankRepository.findOne(Long.parseLong(branchDto.getBank()));
		City city = cityRepository.findCityByIdAndState(branchDto.getState(), Long.parseLong(branchDto.getCity()));
		bankBranch.setName(branchDto.getName());
		bankBranch.setAddress(branchDto.getAddress());
		bankBranch.setCity(city);
		bankBranch.setBranchCode(branchDto.getBranchCode());
		bankBranch.setBank(bank);
		bankBranch.setMaker(branchDto.isMaker());
		bankBranch.setChecker(branchDto.isChecker());
		bankBranch.setMobileNumber(branchDto.getMobileNumber());
		bankBranch.setEmail(branchDto.getEmail());
		bankBranch.setStatus(Status.Active);
		bankBranch.setBranchId(branchDto.getBranchId());
		bankBranch.setLatitude(branchDto.getLatitude());
		bankBranch.setLongitude(branchDto.getLongitude());
		try {
			bankBranch = bankBranchRepository.save(bankBranch);
			
//			User user  = addDefaultUser(bankBranch);
			
//			if (bankBranch.getMobileNumber() != null) {
//				createSmsLog(bankBranch, SmsType.REGISTRATION, user);
//			}
//			emailApi.sendEmail(UserType.BankBranch, user.getUserName(), bankBranch.getEmail(), user.getSecretKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private User addDefaultUser(BankBranch bankBranch) {
		
		User user = new User();
		user.setUserName(bankBranch.getBranchId());
//		user.setPassword(passwordGenerator.generatePassword().get("passwordEncoded"));
		Map<String, String> passwordMap = passwordGenerator.generatePassword();
		user.setSecretKey(passwordMap.get("password"));
		user.setPassword(passwordMap.get("passwordEncoded"));
		user.setAddress(bankBranch.getAddress());
		user.setAssociatedId(bankBranch.getId());
		user.setUserType(UserType.BankBranch);
		user.setCity(bankBranch.getCity());
		user.setStatus(Status.Active);
		user.setAuthority(Authorities.BANK_BRANCH_ADMIN + "," + Authorities.AUTHENTICATED);
		user.setFirstLogin(true);
		user = userRepository.save(user);
		
		UserLog userLog = new UserLog();
		userLog.setUser(user);
		userLog.setRemarks("User Created ");
		userLogRepository.save(userLog);
		return user;
		//send username and pass word via email
	}

	/* (non-Javadoc)
	 * @see com.mobilebanking.api.BankBranchApi#listBankBranchByBank(long)
	 */
	@Override
	public List<BankBranchDTO> listBankBranchByBank(long bankId) {
		List<BankBranch> bankBranchList = ConvertUtil.convertIterableToList(bankBranchRepository.findBankBranchByBank(bankId));
		if (! bankBranchList.isEmpty()) {
			return ConvertUtil.convertBankBranchToDto(bankBranchList);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.mobilebanking.api.BankBranchApi#findBranchById(long)
	 */
	@Override
	public BankBranchDTO findBranchById(long id) {
		BankBranch bankBranch = bankBranchRepository.findOne(id);
		return ConvertUtil.convertBankBranch(bankBranch);
	}

	/* (non-Javadoc)
	 * @see com.mobilebanking.api.BankBranchApi#listAllBranch()
	 */
	@Override
	public List<BankBranchDTO> listAllBranch() {
		List<BankBranch> bankBranches = bankBranchRepository.findAllActiveBranches(Status.Active);
		if (! bankBranches.isEmpty()) {
			return bankBranchConverter.convertToDtoList(bankBranches);
		}
		return null;
	}

	@Override
	public List<BankBranchDTO> listBankBranchByNameLikeAndBank(String name, long bankId) {
		List<BankBranch> bankBranchList = ConvertUtil.convertIterableToList(
				bankBranchRepository.findBankBranchByNameLikeAndBank(name, bankId));
		System.out.println(bankBranchList +  " THIS IS BANK BRANCH LIST");
		if (! bankBranchList.isEmpty()) {
			return ConvertUtil.convertBankBranchToDto(bankBranchList);
		}
		return null;
	}

	@Override
	public void saveBulkBankBranch(BankBranchDTO branchDto) {
		BankBranch bankBranch = new BankBranch();
		
		Bank bank = bankRepository.findByBank(branchDto.getBank());
		City city = cityRepository.findCitByCityNamesAndState(branchDto.getState(), branchDto.getCity());
		bankBranch.setName(branchDto.getName());
		bankBranch.setAddress(branchDto.getAddress());
		bankBranch.setCity(city);
		bankBranch.setBranchCode(branchDto.getBranchCode());
		bankBranch.setBank(bank);
		bankBranch.setMobileNumber(branchDto.getMobileNumber());
		bankBranch.setEmail(branchDto.getEmail());
		try {
			bankBranch = bankBranchRepository.save(bankBranch);
			User user = addDefaultUser(bankBranch);
			/*if (bankBranch.getMobileNumber() != null) {
				createSmsLog(bankBranch, SmsType.REGISTRATION, user);
			}
			*/
			emailApi.sendEmail(UserType.BankBranch, user.getUserName(), bankBranch.getEmail(), user.getSecretKey(),bank.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createSmsLog(BankBranch bankBranch, SmsType smsType, User user) {
		
		SmsLog smsLog = new SmsLog();
		smsLog.setBankBranch(bankBranch);
		smsLog.setSmsForUser(UserType.BankBranch);
		smsLog.setSmsFor(String.valueOf(bankBranch.getId()));
		smsLog.setSmsFromUser(UserType.Admin);
		smsLog.setSmsFrom(AuthenticationUtil.getCurrentUser().getId());
		smsLog.setStatus(SmsStatus.INITIATED);
		smsLog.setSmsType(smsType);
		smsLog.setForMobileNo(bankBranch.getMobileNumber());
		if (smsType.equals(SmsType.REGISTRATION)) {
			String message = StringConstants.REGISTRATION_SMS;
			message = message.replace("{username}", user.getUserName());
			message = message.replace("{password}", user.getSecretKey());
			smsLog.setMessage(message);	
		} else if (smsType.equals(SmsType.NOTIFICATION)) {
			String message = StringConstants.REGISTRATION_USERNAME.replace("{username}", user.getUserName());
			message = message.replace("{password}", user.getSecretKey());
			smsLog.setMessage(message);
		}
		
		smsLogRepository.save(smsLog);
	}

	@Override
//	@Scheduled(fixedRate=10000)
	public void sendSmsForBranchUser() throws Exception {
		List<SmsLog> smsLogs = smsLogRepository.findSmsLogBySmsTypeAndStatusAndUserType(
				SmsType.REGISTRATION, SmsStatus.INITIATED, UserType.BankBranch);
		System.out.println(smsLogs + " THIS IS SMS LOGS BANK BRANCH");
		Map<String, String> responseMap = new HashMap<String, String> ();
		if (! smsLogs.isEmpty()) {
			for (int i=0;i<smsLogs.size();i++) {
				SmsLog sLog = smsLogs.get(i);
				if(smsLogs.get(i).getBankBranch().getBank().getSmsCount()<=0){
					sLog.setStatus(SmsStatus.BANK_OUT_OF_SMS);
					smsLogRepository.save(sLog);
				}else{
//					System.out.println("BANK BRANCH SMS SEND");
					responseMap = sparrowApi.sendSMS(
							smsLogs.get(i).getMessage(), smsLogs.get(i).getBankBranch().getMobileNumber());
					sLog.setStatus(SmsStatus.QUEUED);
					sLog = smsLogRepository.save(sLog);
					if (responseMap.get("code") == "200" || responseMap.get("code").equalsIgnoreCase("200")) {
						sLog.setStatus(SmsStatus.DELIVERED);
						sLog.setDelivered(new Date());
						smsLogRepository.save(sLog);
						Bank bank = bankRepository.findOne(smsLogs.get(i).getBankBranch().getBank().getId());
						bank.setSmsCount(bank.getSmsCount()-1);
						bankRepository.save(bank);
					} else {	
						sLog.setResponseCode(responseMap.get("code"));
						sLog.setResponseMessage(responseMap.get("response"));
						smsLogRepository.save(sLog);
						
					}
				}
			}
		}
		
	}

	@Override
//	@Scheduled(fixedRate=11000)
	public void sendUserNameForBranchUser() throws Exception {
		List<SmsLog> smsLogs = smsLogRepository.findSmsLogBySmsTypeAndStatusAndUserType(
				SmsType.NOTIFICATION, SmsStatus.INITIATED, UserType.BankBranch);
		Map<String, String> responseMap = new HashMap<String, String> ();
		if (! smsLogs.isEmpty()) {
			SmsLog sLog = new SmsLog();
			for (int i=0;i<smsLogs.size();i++) {
				if(smsLogs.get(i).getBankBranch().getBank().getSmsCount()<=0){
					sLog.setStatus(SmsStatus.BANK_OUT_OF_SMS);
					smsLogRepository.save(smsLogs.get(i));
				}else{
//				System.out.println("BANK BRANCH SMS SEND");
				responseMap = sparrowApi.sendSMS(
						smsLogs.get(i).getMessage(), smsLogs.get(i).getBankBranch().getMobileNumber());
				smsLogs.get(i).setStatus(SmsStatus.QUEUED);
				sLog = smsLogRepository.save(smsLogs.get(i));
				if (responseMap.get("code") == "200" || responseMap.get("code").equalsIgnoreCase("200")) {
					sLog.setStatus(SmsStatus.DELIVERED);
					sLog.setDelivered(new Date());
					smsLogRepository.save(sLog);
					Bank bank = bankRepository.findOne(smsLogs.get(i).getBankBranch().getBank().getId());
					bank.setSmsCount(bank.getSmsCount()-1);
					bankRepository.save(bank);
				} else {	
					sLog.setStatus(SmsStatus.FAILED);
					sLog.setResponseCode(responseMap.get("code"));
					sLog.setResponseMessage(responseMap.get("response"));
					smsLogRepository.save(sLog);
					
				}
				}
			}
		}
	}

	@Override
	public void editBranch(BankBranchDTO branchDto) {
		City city = cityRepository.findCityByIdAndState(branchDto.getState(), Long.parseLong(branchDto.getCity()));
		BankBranch bankBranch = bankBranchRepository.findOne(branchDto.getId());
		bankBranch.setAddress(branchDto.getAddress());
		bankBranch.setChecker(branchDto.isChecker());
		bankBranch.setMaker(branchDto.isMaker());
		bankBranch.setCity(city);
		bankBranch.setName(branchDto.getName());
		bankBranch.setBranchCode(branchDto.getBranchCode());
		bankBranch.setEmail(branchDto.getEmail());
		bankBranch.setLatitude(branchDto.getLatitude());
		bankBranch.setLongitude(branchDto.getLongitude());
		bankBranchRepository.save(bankBranch);
		
	}

	@Override
	public BankBranchDTO findBranchByBranchId(String branchId) {
		BankBranch branch = bankBranchRepository.findByBranchId(branchId);
		if(branch!=null){
			return ConvertUtil.convertBankBranch(branch);
		}
		return null;
	}

	@Override
	public BankBranchDTO findBranchByBankAndBranchCode(long bankId, String branchCode) {
		Bank bank = bankRepository.findOne(bankId);
		BankBranch branch = bankBranchRepository.findByBankAndBranchCode(bank.getId(), branchCode);
		if(branch!=null){
			return ConvertUtil.convertBankBranch(branch);
		}
		return null;
	}

	@Override
	public List<BankBranchDTO> listBankByClientId(String clientId) {
	
		BankOAuthClient bankAouthClient = bankAuthRepository.findByOAuthClientId(clientId);
		Bank bank = bankAouthClient.getBank();
		List<BankBranch> bankBranchList = bankBranchRepository.findByBank(bank);
		if (! bankBranchList.isEmpty()) {
			return ConvertUtil.convertBankBranchToDto(bankBranchList);
		}
		return null;
		
	}

	@Override
	public BankDTO findBankOfBranch(long associatedId) {
		BankBranch branch = bankBranchRepository.findOne(associatedId);
		return ConvertUtil.convertBank(branch.getBank());
	}

}
