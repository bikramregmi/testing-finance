package com.mobilebanking.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.INotificationGroupApi;
import com.mobilebanking.converter.NotificationGroupConverter;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.FcmServerSetting;
import com.mobilebanking.entity.NotificationGroup;
import com.mobilebanking.entity.User;
import com.mobilebanking.fcm.PushNotification;
import com.mobilebanking.model.CustomerDTO;
import com.mobilebanking.model.NotificationGroupDTO;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.repositories.FcmServerSettingRepository;
import com.mobilebanking.repositories.NotificationGroupRepository;
import com.mobilebanking.util.ConvertUtil;

@Service
public class NotificationGroupApi implements INotificationGroupApi {

	@Autowired
	private NotificationGroupRepository notificationGroupRepository;

	@Autowired
	private NotificationGroupConverter notificationGroupConverter;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ConvertUtil convertUtil;

	@Autowired
	private PushNotification pushNotification;

	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private FcmServerSettingRepository fcmServerSettingRepository;

	@Override
	public void addNotificationGroup(NotificationGroupDTO notificationGroupDTO) {
		NotificationGroup notificationGroup = new NotificationGroup();
		Bank bank = bankRepository.findOne(notificationGroupDTO.getBankId());
		notificationGroup.setName(notificationGroupDTO.getName());
		notificationGroup.setBankCode(bank.getSwiftCode());
		notificationGroup.setCustomerList(new ArrayList<Customer>());
		notificationGroupRepository.save(notificationGroup);
	}

	@Override
	public void addCustomerToNotificationGroup(String notificationGroupName, String[] customerUniqueIdList,String bankCode) {
		NotificationGroup notificationGroup = notificationGroupRepository.findByName(notificationGroupName,bankCode);
		for (String customerUniqueId : customerUniqueIdList) {
			Customer customer = customerRepository.findCustomerByUniqueId(customerUniqueId);
			notificationGroup.getCustomerList().add(customer);
			try {
				User user = customer.getUser();
				FcmServerSetting fcmServerSetting = fcmServerSettingRepository.findByBankAndFcmServerKey(user.getBank().getId(), user.getServerKey());
				pushNotification.suscribeToTopic(customer.getUser().getDeviceToken(), bankCode+notificationGroupName,fcmServerSetting.getFcmServerID());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		notificationGroupRepository.save(notificationGroup);
	}

	@Override
	public void removeCustomerFromNotificationGroup(String notificationGroupName, String customerUniqueId,String bankCode) {
		Customer customer = customerRepository.findCustomerByUniqueId(customerUniqueId);
		NotificationGroup notificationGroup = notificationGroupRepository.findByName(notificationGroupName,bankCode);
		notificationGroup.getCustomerList().remove(customer);
		notificationGroupRepository.save(notificationGroup);
		
		User user = customer.getUser();
		FcmServerSetting fcmServerSetting = fcmServerSettingRepository.findByBankAndFcmServerKey(user.getBank().getId(), user.getServerKey());
		pushNotification.unsuscribeFromTopic(customer.getUser().getDeviceToken(), bankCode + notificationGroupName,fcmServerSetting.getFcmServerID());
	}

	@Override
	public List<NotificationGroupDTO> getAll() {
		List<NotificationGroup> notificationGroupList = notificationGroupRepository.findAll();
		if (notificationGroupList != null)
			return notificationGroupConverter.convertToDtoList(notificationGroupList);
		return null;
	}

	@Override
	public NotificationGroupDTO getByName(String name,String bankCode) {
		NotificationGroup notificationGroup = notificationGroupRepository.findByName(name,bankCode);
		if (notificationGroup != null)
			return notificationGroupConverter.convertToDto(notificationGroup);
		return null;
	}

	@Override
	public List<CustomerDTO> getSubscribedCustomerList(String notificationGroupName,String bankCode) {
		NotificationGroup notificationGroup = notificationGroupRepository.findByName(notificationGroupName,bankCode);
		if (notificationGroup != null)
			return convertUtil.convertCustomerToDto(notificationGroup.getCustomerList());
		return null;
	}

	@Override
	public List<CustomerDTO> getNotSubscribedCustomerList(String notificationGroupName,String bankCode) {
		List<Customer> customerList = customerRepository
				.findCustomerNotSubscribedToNotificationGroup(notificationGroupName,bankCode);
		
		if (customerList != null)
			return convertUtil.convertCustomerToDto(customerList);
		return null;
	}

	@Override
	public List<NotificationGroupDTO> getByBank(long bankId) {
		Bank bank = bankRepository.findOne(bankId);
		List<NotificationGroup> notificationGroupList = notificationGroupRepository.findByBank(bank.getSwiftCode());
		if (notificationGroupList != null)
			return notificationGroupConverter.convertToDtoList(notificationGroupList);
		return null;
	}

	@Override
	public List<NotificationGroupDTO> getByBranchCode(String bankCode) {
		List<NotificationGroup> notificationGroupList = notificationGroupRepository.findByBank(bankCode);
		if (notificationGroupList != null)
			return notificationGroupConverter.convertToDtoList(notificationGroupList);
		return null;
	
	}


}
