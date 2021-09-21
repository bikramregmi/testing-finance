package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.model.CustomerDTO;
import com.mobilebanking.model.NotificationGroupDTO;

public interface INotificationGroupApi {

	public void addNotificationGroup(NotificationGroupDTO notificationGroupDTO);

	public void removeCustomerFromNotificationGroup(String notificationGroupName, String customerUniqueId,String bankCode);

	public List<NotificationGroupDTO> getAll();

	public NotificationGroupDTO getByName(String name,String bankCode);

	public List<CustomerDTO> getSubscribedCustomerList(String notificationGroupName,String bankCode);

	public List<CustomerDTO> getNotSubscribedCustomerList(String notificationGroupName,String bankCode);

	void addCustomerToNotificationGroup(String notificationGroupName, String[] customerUniqueIdList, String bankCode);

	 List<NotificationGroupDTO> getByBank(long associatedId);

	public List<NotificationGroupDTO> getByBranchCode(String bankCode);


}
