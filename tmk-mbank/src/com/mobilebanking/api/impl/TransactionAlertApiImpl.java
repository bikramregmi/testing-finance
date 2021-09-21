package com.mobilebanking.api.impl;

import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.ISparrowApi;
import com.mobilebanking.api.ITransactionAlertApi;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankBranch;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.FcmServerSetting;
import com.mobilebanking.entity.Notification;
import com.mobilebanking.entity.SmsLog;
import com.mobilebanking.entity.TransactionAlert;
import com.mobilebanking.entity.User;
import com.mobilebanking.entity.UserNotification;
import com.mobilebanking.fcm.PushNotification;
import com.mobilebanking.model.NotificationChannel;
import com.mobilebanking.model.NotificationStatus;
import com.mobilebanking.model.PagablePage;
import com.mobilebanking.model.SmsStatus;
import com.mobilebanking.model.SmsType;
import com.mobilebanking.model.TransactionAlertDTO;
import com.mobilebanking.model.UserType;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.repositories.FcmServerSettingRepository;
import com.mobilebanking.repositories.NotificationRepository;
import com.mobilebanking.repositories.SmsLogRepository;
import com.mobilebanking.repositories.TransactionAlertRepository;
import com.mobilebanking.repositories.UserNotificationRepository;
import com.mobilebanking.util.ConvertUtil;
import com.mobilebanking.util.DateTypes;
import com.mobilebanking.util.DateUtil;
import com.mobilebanking.util.PageInfo;

@Service
public class TransactionAlertApiImpl implements ITransactionAlertApi {

	@Autowired
	private SmsLogRepository smsLogRepository;

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private ConvertUtil convertUtil;
	@Autowired
	private ISparrowApi sparrowApi;

	@Autowired
	private TransactionAlertRepository transactionAlertRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private UserNotificationRepository userNotificationRepository;

	@Autowired
	private PushNotification pushNotification;

	@Autowired
	private FcmServerSettingRepository fcmServerSettingRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Boolean sentTransactionAlert(TransactionAlertDTO trasactionAlertDto)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		TransactionAlert transationAlert = new TransactionAlert();
		PropertyUtils.copyProperties(transationAlert, trasactionAlertDto);
		transationAlert.setStatus(SmsStatus.INITIATED);
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		try {
			Date date = dateFormat.parse(trasactionAlertDto.getDateTime());
			transationAlert.setDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
			try {
				Date date = dateFormat.parse(dateFormat.format(new Date()));
				transationAlert.setDate(date);
			} catch (ParseException ex) {
				e.printStackTrace();
			}
		}
		transationAlert = transactionAlertRepository.save(transationAlert);
		if (createTrasactionAlertSmsLog(transationAlert)) {
			transationAlert.setStatus(SmsStatus.DELIVERED);
			transationAlert = transactionAlertRepository.save(transationAlert);
		} else {
			transationAlert.setStatus(SmsStatus.FAILED);
			transationAlert = transactionAlertRepository.save(transationAlert);
		}
		Customer customer = customerRepository.findByMobileNumberAndBank(trasactionAlertDto.getMobileNumber(),
				bankRepository.findBankByCode(trasactionAlertDto.getSwiftCode()));
		if (customer == null) {
			return true;
		}
		try {
			if (!customer.isAppService()) {
				transationAlert.setNotificationStatus(NotificationStatus.NO_APP_SERVICE);
				transationAlert = transactionAlertRepository.save(transationAlert);
				return true;
			} else if (customer.getUser().getDeviceToken() == null) {
				transationAlert.setNotificationStatus(NotificationStatus.NO_DEVICE_TOKEN);
				transationAlert = transactionAlertRepository.save(transationAlert);
				return true;
			}
			Notification notification = new Notification();
			notification.setBankCode(trasactionAlertDto.getSwiftCode());
			notification.setMessage(trasactionAlertDto.getMessage());
			notification.setTitle("Transaction Alert");
			notification.setSendTo(customer.getUser().getDeviceToken());
			notification.setTopic(false);
			notification = notificationRepository.save(notification);

			UserNotification userNotificaton = new UserNotification();
			userNotificaton.setUser(customer.getUser());
			userNotificaton.setSeen(false);
			userNotificaton.setNotification(notification);
			userNotificaton.setNotificationChannel(NotificationChannel.CBSALERT);
			userNotificationRepository.save(userNotificaton);
			User user = customer.getUser();

			FcmServerSetting fcmServerSetting = fcmServerSettingRepository
					.findByBankAndFcmServerKey(user.getBank().getId(), user.getServerKey());

			String response = pushNotification.sendNotification(notification.getTitle(), notification.getMessage(),
					notification.getSendTo(), NotificationChannel.CBSALERT.toString(),
					fcmServerSetting.getFcmServerID());
			if (response.contains("Failed")) {
				transationAlert.setNotificationStatus(NotificationStatus.FAILED);
				transationAlert = transactionAlertRepository.save(transationAlert);
			} else {
				transationAlert.setNotificationStatus(NotificationStatus.DELIVERED);
				transationAlert = transactionAlertRepository.save(transationAlert);
			}
			notification.setResponse(response);
			notification = notificationRepository.save(notification);
		} catch (Exception e) {
			e.printStackTrace();
			transationAlert.setNotificationStatus(NotificationStatus.ERROR);
			transationAlert = transactionAlertRepository.save(transationAlert);
			return true;
		}
		return true;
	}

	private Boolean createTrasactionAlertSmsLog(TransactionAlert transactionAlert) {
		SmsLog smsLog = new SmsLog();
		Bank bank = bankRepository.findBankByCode(transactionAlert.getSwiftCode());
		BankBranch bankBranch = null;
		smsLog.setBank(bank);
		smsLog.setBankBranch(bankBranch);
		smsLog.setSmsForUser(UserType.Customer);
		smsLog.setSmsFor(transactionAlert.getMobileNumber());
		smsLog.setStatus(SmsStatus.INITIATED);
		smsLog.setSmsFromUser(UserType.Bank);
		smsLog.setSmsFrom(bank.getId());
		smsLog.setForMobileNo(transactionAlert.getMobileNumber());
		String message = transactionAlert.getMessage();
		smsLog.setSmsType(SmsType.CBSALERT);
		smsLog.setMessage(message);
		smsLog.setSmsIn(false);
		smsLog = smsLogRepository.save(smsLog);
		Map<String, String> responseMap = new HashMap<String, String>();
		if (bank.getSmsCount() < 0) {
			smsLog.setStatus(SmsStatus.FAILED);
			smsLogRepository.save(smsLog);
			return false;
		}
		try {
			responseMap = sparrowApi.sendSMS(message, transactionAlert.getMobileNumber());
		} catch (UnknownHostException | InvocationTargetException e) {
			e.printStackTrace();
			smsLog.setStatus(SmsStatus.FAILED);
			smsLogRepository.save(smsLog);
			return false;
		}
		smsLog.setStatus(SmsStatus.QUEUED);
		smsLog = smsLogRepository.save(smsLog);
		if (responseMap.get("code") == "200" || responseMap.get("code").equalsIgnoreCase("200")) {
			smsLog.setStatus(SmsStatus.DELIVERED);
			smsLogRepository.save(smsLog);
			try {
				bank.setSmsCount(bank.getSmsCount() - 1);
				bank = bankRepository.save(bank);
			} catch (Exception e) {

			}
			return true;
		} else {
			smsLog.setStatus(SmsStatus.FAILED);
			smsLogRepository.save(smsLog);
			return false;
		}
	}

	// @Scheduled(fixedRate = 3000)
	public void sendAlertSms() throws Exception {
		List<SmsLog> smsLogs = smsLogRepository.findSmsLogBySmsTypeAndStatusAndUserType(SmsType.CBSALERT,
				SmsStatus.PENDING, UserType.Customer);
		Map<String, String> responseMap = new HashMap<String, String>();
		if (!smsLogs.isEmpty()) {
			SmsLog sLog = new SmsLog();
			for (int i = 0; i < smsLogs.size(); i++) {
				if (smsLogs.get(i).getBank().getSmsCount() <= 0) {
					sLog.setStatus(SmsStatus.BANK_OUT_OF_SMS);
					smsLogRepository.save(smsLogs.get(i));
				} else {
					String message = smsLogs.get(i).getMessage();
					try {
						responseMap = sparrowApi.sendSMS(message, smsLogs.get(i).getForMobileNo());
					} catch (Exception e) {
						e.printStackTrace();
					}
					smsLogs.get(i).setStatus(SmsStatus.QUEUED);
					sLog = smsLogRepository.save(smsLogs.get(i));
					if (responseMap.get("code") == "200" || responseMap.get("code").equalsIgnoreCase("200")) {
						sLog.setStatus(SmsStatus.DELIVERED);
						smsLogRepository.save(sLog);
						Bank bank = bankRepository.findOne(smsLogs.get(i).getBank().getId());
						bank.setSmsCount(bank.getSmsCount() - 1);
						bankRepository.save(bank);
					} else {
						sLog.setStatus(SmsStatus.PENDING);
						smsLogRepository.save(sLog);
					}
				}
			}
		}

	}

	@Override
	public PagablePage getTransactionAlertList(Integer currentPage, String fromDate, String toDate, String mobileNo,
			String swiftCode, String channelPartner) {
		PagablePage pageablePage = new PagablePage();
		if (currentPage == null || currentPage == 0) {
			currentPage = 1;
		}
		int start = (currentPage * (int) PageInfo.pageList) - (int) PageInfo.pageList;

		entityManager = entityManager.getEntityManagerFactory().createEntityManager();
		Session session = entityManager.unwrap(Session.class);
		Criteria countCriteria = session.createCriteria(TransactionAlert.class);
		Criteria criteria = session.createCriteria(TransactionAlert.class);
		try {
			if (fromDate != null && !(fromDate.trim().equals(""))) {
				criteria.add(Restrictions.ge("created", DateUtil.getDate(fromDate, DateTypes.FROM_DATE)));
				countCriteria.add(Restrictions.ge("created", DateUtil.getDate(fromDate, DateTypes.FROM_DATE)));
			}
			if (toDate != null && !(toDate.trim().equals(""))) {
				criteria.add(Restrictions.lt("created", DateUtil.getDate(toDate, DateTypes.TO_DATE)));
				countCriteria.add(Restrictions.lt("created", DateUtil.getDate(toDate, DateTypes.TO_DATE)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (mobileNo != null && !(mobileNo.trim().equals(""))) {
			criteria.add(Restrictions.like("mobileNumber", mobileNo + "%"));
			countCriteria.add(Restrictions.like("mobileNumber", mobileNo + "%"));
		}
		if (swiftCode != null && !(swiftCode.trim().equals(""))) {
			criteria.add(Restrictions.eq("swiftCode", swiftCode));
			countCriteria.add(Restrictions.eq("swiftCode", swiftCode));
		}
		if (channelPartner != null && !(channelPartner.trim().equals(""))) {
			criteria.add(Restrictions.eq("channelPartner", channelPartner.trim()));
			countCriteria.add(Restrictions.eq("channelPartner", channelPartner.trim()));
		}
		criteria.addOrder(Order.desc("id"));
		countCriteria.setProjection(Projections.rowCount());
		int totalpage = (int) ((Integer.valueOf(countCriteria.uniqueResult().toString())) / PageInfo.pageList);
		List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(currentPage, totalpage, PageInfo.numberOfPage);
		criteria.setFirstResult(start);
		criteria.setMaxResults((int) PageInfo.pageList);
		List<TransactionAlert> transactionAlertList = criteria.list();
		pageablePage.setCurrentPage(currentPage);
		pageablePage.setObject(convertUtil.convertToTransactionAlertDTO(transactionAlertList));
		pageablePage.setPageList(pagesnumbers);
		pageablePage.setLastpage(totalpage);

		return pageablePage;
	}
	// end added

}
