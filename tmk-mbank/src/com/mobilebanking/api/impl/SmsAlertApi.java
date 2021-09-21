package com.mobilebanking.api.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.ISmsAlertApi;
import com.mobilebanking.converter.SmsAlertConverter;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BulkSmsAlertBatch;
import com.mobilebanking.entity.SmsAlert;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.BulkSmsAlertDTO;
import com.mobilebanking.model.SmsAlertDTO;
import com.mobilebanking.model.SmsStatus;
import com.mobilebanking.model.UserType;
import com.mobilebanking.repositories.BankBranchRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.BulkSmsAlertBatchRepository;
import com.mobilebanking.repositories.SmsAlertRepository;
import com.mobilebanking.repositories.UserRepository;

@Service
public class SmsAlertApi implements ISmsAlertApi {

	@Autowired
	private SmsAlertRepository smsAlertRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SparrowApi sparrowApi;

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private BankBranchRepository branchRepository;
	
	@Autowired
	private BulkSmsAlertBatchRepository bulkSmsAlertBatchRepository;
	
	@Autowired
	private SmsAlertConverter smsAlertConverter;

	@Override
	public Long saveBulkBatch(BulkSmsAlertDTO bulkSmsAlertDTO) {
		Long batchId = createBatch(bulkSmsAlertDTO.getCreatedBy());
		for (SmsAlertDTO smsAlertdto : bulkSmsAlertDTO.getSmsAlertList()) {
			smsAlertdto.setBulkBatchId(batchId);
			smsAlertdto.setCreatedBy(bulkSmsAlertDTO.getCreatedBy());
			saveSmsAlert(smsAlertdto);
		}
		return batchId;
	}

	private Long createBatch(Long userId) {
		BulkSmsAlertBatch batch = new BulkSmsAlertBatch();
		batch.setBatchId(getBulkBatch());
		batch.setCreatedBy(userRepository.findOne(userId));
		bulkSmsAlertBatchRepository.save(batch);
		return batch.getId();
	}

	private String getBulkBatch() {
		String batch = "";
		final String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final int charactesLength = characters.length();
		Random random = new Random();
		for (int i = 0; i < 6; i++)
			batch = batch + characters.charAt(random.nextInt(charactesLength));
		BulkSmsAlertBatch alertList = bulkSmsAlertBatchRepository.findByBatchId(batch);
		if (alertList != null)
			getBulkBatch();
		return batch;
	}

	@Override
	public void saveSmsAlert(SmsAlertDTO smsAlertdto) {
		SmsAlert smsAlert = new SmsAlert();
		smsAlert.setForMobileNumber(smsAlertdto.getMobileNumber());
		smsAlert.setMessage(smsAlertdto.getMessage());
		smsAlert.setCreatedBy(userRepository.findOne(smsAlertdto.getCreatedBy()));
		smsAlert.setBulkSmsAlertBatch(bulkSmsAlertBatchRepository.findOne(smsAlertdto.getBulkBatchId()));
		smsAlert.setSmsStatus(SmsStatus.INITIATED);
		smsAlertRepository.save(smsAlert);
	}

	@Scheduled(fixedDelay = 10 * 1000)
	public void checkBulkSms() {
		List<SmsAlert> smsAlertList = smsAlertRepository.findBySmsStatus(SmsStatus.INITIATED);
		if (smsAlertList != null && !smsAlertList.isEmpty()) {
			for (SmsAlert smsAlert : smsAlertList) {
				smsAlert = smsAlertRepository.findOne(smsAlert.getId());
				smsAlert.setSmsStatus(SmsStatus.QUEUED);
				smsAlertRepository.save(smsAlert);
			}
			for (SmsAlert smsAlert : smsAlertList) {
				Bank bank = getBank(smsAlert.getCreatedBy());
				if (bank != null && bank.getSmsCount() <= 0) {
					smsAlert = smsAlertRepository.findOne(smsAlert.getId());
					smsAlert.setSmsStatus(SmsStatus.BANK_OUT_OF_SMS);
					smsAlertRepository.save(smsAlert);
				} else {
					sendSmsAlert(smsAlert);
				}
			}
		}
	}

	private Bank getBank(User createdBy) {
		if (createdBy.getUserType() == UserType.Bank) {
			return bankRepository.findOne(createdBy.getId());
		} else if (createdBy.getUserType() == UserType.BankBranch) {
			return branchRepository.findOne(createdBy.getId()).getBank();
		}
		return null;
	}

	private void sendSmsAlert(SmsAlert smsAlert) {
		smsAlert = smsAlertRepository.findOne(smsAlert.getId());
		try {
			Map<String, String> responseMap = sparrowApi.sendSMS(smsAlert.getMessage(), smsAlert.getForMobileNumber());
			if (responseMap.get("code") == "200" || responseMap.get("code").equalsIgnoreCase("200")) {
				smsAlert.setSmsStatus(SmsStatus.DELIVERED);
				smsAlert.setDeliveredDate(new Date());
				smsAlertRepository.save(smsAlert);
			} else {
				smsAlert.setSmsStatus(SmsStatus.FAILED);
				smsAlert.setResponseCode(responseMap.get("code"));
				smsAlertRepository.save(smsAlert);
			}
		} catch (Exception e) {
			e.printStackTrace();
			smsAlert.setSmsStatus(SmsStatus.FAILED);
			smsAlertRepository.save(smsAlert);
		}
	}

	@Override
	public List<BulkSmsAlertDTO> getBatchList(Long userId) {
		User user = userRepository.findOne(userId);
		List<BulkSmsAlertBatch> bulkBatchList = null;
		if(user.getUserType() == UserType.Admin){
			bulkBatchList = bulkSmsAlertBatchRepository.findAllBulkBatch();
		}else if(user.getUserType() == UserType.Bank){
			bulkBatchList = bulkSmsAlertBatchRepository.findBulkBatchByBank(user.getAssociatedId());
		}else if(user.getUserType() == UserType.BankBranch){
			bulkBatchList = bulkSmsAlertBatchRepository.findBulkBatchByBankBranch(user.getAssociatedId());
		}
		if(bulkBatchList != null)
			return smsAlertConverter.convertBulkBatch(bulkBatchList);
		return null;
	}

	@Override
	public List<SmsAlertDTO> getSmsAlertByBatch(long batchId) {
		List<SmsAlert> alertList = smsAlertRepository.findByBatchId(batchId);
		if(alertList != null)
			return smsAlertConverter.convertToDtoList(alertList);
		return null;
	}

}
