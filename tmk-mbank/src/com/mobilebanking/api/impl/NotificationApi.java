package com.mobilebanking.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.INotificationApi;
import com.mobilebanking.converter.NotificationConverter;
import com.mobilebanking.converter.UserNotificationConverter;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.Notification;
import com.mobilebanking.entity.User;
import com.mobilebanking.entity.UserNotification;
import com.mobilebanking.fcm.PushNotification;
import com.mobilebanking.model.NotificationChannel;
import com.mobilebanking.model.NotificationDTO;
import com.mobilebanking.model.PagablePage;
import com.mobilebanking.model.UserNotificationDTO;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.repositories.NotificationRepository;
import com.mobilebanking.repositories.UserNotificationRepository;
import com.mobilebanking.util.PageInfo;
import com.mobilebanking.util.StringConstants;

@Service
public class NotificationApi implements INotificationApi {

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private UserNotificationRepository userNotificationRepository;

	@Autowired
	private PushNotification pushNotification;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private NotificationConverter notificationConverter;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private UserNotificationConverter userNotificationConverter;

	@Override
	public NotificationDTO saveNotification(NotificationDTO notificationDTO) {
		Notification notification = new Notification();
		// Bank bank = bankRepository.findOne(notificationDTO.getBankId());
		notification.setTitle(notificationDTO.getTitle());
		notification.setMessage(notificationDTO.getBody());

		notification.setTopic(notificationDTO.isTopic());
		notification.setBankCode(notificationDTO.getBankCode());
		if (!notificationDTO.isTopic() && !notificationDTO.isAllCustomer()) {
			notification.setSendTo(notificationDTO.getSendTo());
		} else if (notificationDTO.isAllCustomer()) {
			notificationDTO.setSendTo(notificationDTO.getBankCode() + StringConstants.CUSTOMER_TOPIC);

		} else {
			notification.setSendTo(notificationDTO.getIdList());
		}
		notification = notificationRepository.save(notification);
		boolean firstIteration = true;

		if (!notificationDTO.isTopic() && !notificationDTO.isAllCustomer()) {
			List<String> serverIdList = new ArrayList<>();
			HashMap<String, String> serverKeyIdPair = new HashMap<>();
			for (String id : notificationDTO.getIdList().split(",")) {
				Customer customer = customerRepository.findOne(Long.parseLong(id));
				User user = customer.getUser();

				if (user.getServerKey() != null && !serverIdList.contains(user.getSecretKey())) {
					serverIdList.add(user.getServerKey());
				}

				if (user.getServerKey() != null) {
					if (serverKeyIdPair.get(user.getServerKey()) != null) {
						serverKeyIdPair.put(user.getServerKey(),
								serverKeyIdPair.get(user.getServerKey()) + "," + user.getDeviceToken());
					} else {
						serverKeyIdPair.put(user.getServerKey(), user.getDeviceToken());
					}
				}

				UserNotification userNotificaton = new UserNotification();
				userNotificaton.setUser(user);
				userNotificaton.setSeen(false);
				userNotificaton.setNotification(notification);
				if (notificationDTO.isToMobile()) {
					userNotificaton.setNotificationChannel(NotificationChannel.MOBILE);
				}
				userNotificationRepository.save(userNotificaton);
				if (firstIteration) {
					if (notificationDTO.isToMobile()) {
						notificationDTO.setSendTo(user.getDeviceToken());
					}
					/*
					 * if(notificationDTO.isToWeb()){
					 * if(notificationDTO.isToMobile()){
					 * notificationDTO.setSendTo(notificationDTO.getSendTo()+","
					 * +user.getWebToken()); }else{
					 * notificationDTO.setSendTo(user.getWebToken()); } }
					 */
					firstIteration = false;
				} else {
					if (notificationDTO.isToMobile()) {
						notificationDTO.setSendTo(notificationDTO.getSendTo() + "," + user.getDeviceToken());
					}
					/*
					 * if(notificationDTO.isToWeb()){
					 * notificationDTO.setSendTo(notificationDTO.getSendTo()+","
					 * +user.getWebToken()); }
					 */
				}
				/*
				 * if(notificationDTO.isToWeb() &&
				 * notificationDTO.isToMobile()){
				 * userNotificaton.setNotificationChannel(NotificationChannel.
				 * BOTH); }else if(notificationDTO.isToWeb()){
				 * userNotificaton.setNotificationChannel(NotificationChannel.
				 * WEB); }else
				 */
			}
			notificationDTO.setServerKeyList(serverIdList);
			notificationDTO.setServerKeyIdPair(serverKeyIdPair);
		}

		String response;
		if (notificationDTO.isTopic() || notificationDTO.isAllCustomer()) {
			response = pushNotification.sendGroupNotification(notificationDTO);
		} else {
			response = pushNotification.sendNotification(notificationDTO);
		}

		notification.setResponse(response);
		notification = notificationRepository.save(notification);

		return notificationConverter.convertToDto(notification);

	}

	@Override
	public PagablePage getNotification(Integer currentPage) {
		PagablePage page = new PagablePage();
		if (currentPage == null || currentPage == 0) {
			currentPage = 1;
		}
		entityManager = entityManager.getEntityManagerFactory().createEntityManager();
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Notification.class);

		criteria.addOrder(Order.desc("id"));

		int starting = ((currentPage * (int) PageInfo.pageList) - (int) PageInfo.pageList);

		int totalpage = (int) Math.ceil(criteria.list().size() / PageInfo.pageList);
		List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(currentPage, totalpage, PageInfo.numberOfPage);
		criteria.setFirstResult(starting);
		criteria.setMaxResults((int) PageInfo.pageList);
		List<Notification> notificationList = criteria.list();

		page.setCurrentPage(currentPage);
		page.setObject(notificationConverter.convertToDtoList(notificationList));
		page.setPageList(pagesnumbers);
		page.setLastpage(totalpage);

		return page;
	}

	@Override
	public PagablePage getBankNotification(Integer currentPage, String bankCode) {
		PagablePage page = new PagablePage();
		if (currentPage == null || currentPage == 0) {
			currentPage = 1;
		}
		entityManager = entityManager.getEntityManagerFactory().createEntityManager();
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Notification.class);
		criteria.add(Restrictions.eq("bankCode", bankCode));
		criteria.addOrder(Order.desc("id"));

		int starting = ((currentPage * (int) PageInfo.pageList) - (int) PageInfo.pageList);

		int totalpage = (int) Math.ceil(criteria.list().size() / PageInfo.pageList);
		List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(currentPage, totalpage, PageInfo.numberOfPage);
		criteria.setFirstResult(starting);
		criteria.setMaxResults((int) PageInfo.pageList);
		List<Notification> notificationList = criteria.list();

		page.setCurrentPage(currentPage);
		page.setObject(notificationConverter.convertToDtoList(notificationList));
		page.setPageList(pagesnumbers);
		page.setLastpage(totalpage);

		return page;
	}

	@Override
	public List<UserNotificationDTO> getUserNotification(Long id, NotificationChannel channel) {
		if (channel == NotificationChannel.WEB) {
			channel = NotificationChannel.MOBILE;
		} else if (channel == NotificationChannel.MOBILE) {
			channel = NotificationChannel.WEB;
		}
		Page<UserNotification> notificationList = userNotificationRepository.findLastTenByUserIdAndChannel(id, channel,
				new PageRequest(0, 10));
		return userNotificationConverter.convertToDtoList(notificationList.getContent());
	}

	@Override
	public Long getUnseenNotification(Long id) {
		Long unseen = userNotificationRepository.countnNotificationByUserIdAndStatus(id, false);
		return unseen;
	}

	@Override
	public void changeNotificationStatus(Long lastNotificationid, Long id) {
		List<UserNotification> notificationList = userNotificationRepository
				.findPreviousNotificationByUserIdAndLastNotificationIdAndStatus(id, lastNotificationid, false);
		for (UserNotification notification : notificationList) {
			notification.setSeen(true);
			userNotificationRepository.save(notification);
		}
	}

}
