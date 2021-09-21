package com.mobilebanking.fcm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.FcmServerSetting;
import com.mobilebanking.model.NotificationDTO;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.FcmServerSettingRepository;

//import net.minidev.json.JSONObject;

@Service
public class PushNotificationImpl implements PushNotification {

	private static final String Notification_End_Point = "https://fcm.googleapis.com/fcm/send";
	private static final String Server_Key = "AAAAxug_n7s:APA91bGXV-hPRrta1bdrmuAcCmwYJttOFvs2HrVKyYzFxoS_eEbfPEsQDqEvRWPUYCtrX6Cp_anqHzGiZv02A5Q-ZF6EVn7jiiS_vfiqunaYO3pzb0ZJ6Z9zoV7fuyYFm5TnT-bPvORl";
	private static final String Suscribe_Url = "https://iid.googleapis.com/iid/v1/<REGISTRATION_TOKEN>/rel/topics/<TOPIC_NAME>";
	private static final String Batch_remove_Url = "https://iid.googleapis.com/iid/v1:batchRemove";
	private static HashMap<String, String> serverAccessToken = new HashMap<>();
	@Autowired
	private FcmServerSettingRepository fcmServerSettingRepository;

	@Autowired
	private BankRepository bankRepository;

	@Override
	public String sendNotification(NotificationDTO notificationDTO) {
		try {
			String serverId;
			Bank bank = bankRepository.findBankByCode(notificationDTO.getBankCode());
			HashMap<String, String> info = new HashMap<String, String>();
			Gson gsonHeader = new Gson();
			Gson gsonBody = new Gson();
			info.put("title", notificationDTO.getTitle());
			info.put("message", notificationDTO.getBody());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			info.put("date", dateFormat.format(new Date()));
			gsonHeader.toJson(info);
			// JSONObject info = new JSONObject();
			//
			// info.put("sound", "default");
			int notifiedCounter = 0;
			HttpEntity<String> response = null;
			String successResponse = null;
			String failureResponse = null;
			for (String serverKey : notificationDTO.getServerKeyList()) {
				HashMap<String, Object> json = new HashMap<String, Object>();
				// if(notificationDTO.isTopic())
				json.put("to", notificationDTO.getServerKeyIdPair().get(serverKey));
				json.put("data", info);
				gsonBody.toJson(json);
				String finaljson = gsonBody.toJson(json);
				HttpHeaders headers = new HttpHeaders();
				headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
				headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

				FcmServerSetting serverSetting = fcmServerSettingRepository.findByBankAndFcmServerKey(bank.getId(),
						serverKey);
				/*
				 * if (serverAccessToken.get(serverKey)==null) {
				 * getAccessToken(serverKey); }
				 */
				headers.set("Authorization", "key= " + serverSetting.getFcmServerID());

				HttpEntity<?> entity = new HttpEntity<>(finaljson, headers);

				RestTemplate restTemplate = new RestTemplate();
				response = restTemplate.exchange(Notification_End_Point, HttpMethod.POST, entity, String.class);
				gsonBody = new Gson();
				try {
					FcmResponse fcmResponse = gsonBody.fromJson(response.getBody(), FcmResponse.class);
					if (fcmResponse.getSuccess() == null) {
						FcmGroupResponse messageIdResponse = gsonBody.fromJson(response.getBody(),
								FcmGroupResponse.class);
						if (messageIdResponse.getMessageId() != null) {
							notifiedCounter++;
							successResponse = response.getBody();
						}
					} else if (fcmResponse.getSuccess() > 0) {
						// System.out.println(fcmResponse.getMulticastId());
						System.out.println(response);
						notifiedCounter++;
						successResponse = response.getBody();
					} else {
						failureResponse = "Failed: " + fcmResponse.getResults().get(0).getError();
					}
				} catch (Exception e) {
					failureResponse = "Failed: Exception while getting response";
				}
			}
			if (notifiedCounter > 0) {
				return successResponse;
			} else {
				return failureResponse;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed";
		}

	}

	@Override
	public String sendGroupNotification(NotificationDTO notificationDTO) {
		try {
			List<FcmServerSetting> serverSettingList = new ArrayList<FcmServerSetting>();
			Bank bank = bankRepository.findBankByCode(notificationDTO.getBankCode());
			serverSettingList = fcmServerSettingRepository.findByBank(bank.getId());
			/*
			 * for(String serverKey :notificationDTO.getServerKeyList()){
			 * FcmServerSetting serverSetting =
			 * fcmServerSettingRepository.findByBankAndFcmServerKey(bank,
			 * serverKey); serverSettingList.add(serverSetting); } }
			 */

			HashMap<String, String> info = new HashMap<String, String>();
			Gson gsonHeader = new Gson();
			Gson gsonBody = new Gson();
			info.put("title", notificationDTO.getTitle());
			info.put("message", notificationDTO.getBody());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			info.put("date", dateFormat.format(new Date()));
			gsonHeader.toJson(info);
			HashMap<String, Object> json = new HashMap<String, Object>();
			// if(notificationDTO.isTopic())
			json.put("to", notificationDTO.getSendTo());
			json.put("data", info);
			gsonBody.toJson(json);
			String finaljson = gsonBody.toJson(json);
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			HttpEntity<String> response = null;
			for (FcmServerSetting fcmSetting : serverSettingList) {
				/*
				 * if
				 * (serverAccessToken.get(fcmSetting.getFcmServerKey())==null) {
				 * getAccessToken(fcmSetting.getFcmServerKey()); }
				 */
				// headers.set("Authorization", "Bearer " +
				// serverAccessToken.get(fcmSetting.getFcmServerKey()));
				headers.set("Authorization", "key = " + fcmSetting.getFcmServerID());

				HttpEntity<?> entity = new HttpEntity<>(finaljson, headers);
				RestTemplate restTemplate = new RestTemplate();
				response = restTemplate.exchange(Notification_End_Point, HttpMethod.POST, entity, String.class);
			}
			try {
				FcmResponse fcmResponse = gsonBody.fromJson(response.getBody(), FcmResponse.class);
				if (fcmResponse.getSuccess() > 0) {
					// System.out.println(fcmResponse.getMulticastId());
					System.out.println(response);
					return response.getBody();
				} else {
					return "Failed: " + fcmResponse.getResults().get(0).getError();
				}
			} catch (Exception e) {
				return response.getBody();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed";
		}
	}

	@Override
	public String sendNotification(String title, String body, String sendTo, String channel, String serverKey) {

		try {
			HashMap<String, String> info = new HashMap<String, String>();
			Gson gsonHeader = new Gson();
			info.put("title", title);
			info.put("message", body);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			info.put("date", dateFormat.format(new Date()));
			info.put("channel", channel);
			gsonHeader.toJson(info);
			HashMap<String, Object> json = new HashMap<String, Object>();
			Gson gsonBody = new Gson();
			json.put("to", sendTo);
			json.put("data", info);
			String finaljson = gsonBody.toJson(json);
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			headers.set("Authorization", "key = " + serverKey);
			HttpEntity<?> entity = new HttpEntity<>(finaljson, headers);

			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<String> response = restTemplate.exchange(Notification_End_Point, HttpMethod.POST, entity,
					String.class);
			gsonBody = new Gson();
			FcmResponse fcmResponse = gsonBody.fromJson(response.getBody(), FcmResponse.class);
			if (fcmResponse.getSuccess() > 0) {
				System.out.println(fcmResponse.getMulticastId());
				System.out.println(response);
				return response.getBody();
			} else {
				return "Failed: " + fcmResponse.getResults().get(0).getError();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed";
		}

	}

	@Override
	public void suscribeToTopic(String registrationToken, String topicName, String serverKey) {
		try {

			HttpHeaders headers = new HttpHeaders();
			// headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			// headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			headers.set("Authorization", "key= " + serverKey);
			HttpEntity<?> entity = new HttpEntity<>(headers);
			RestTemplate restTemplate = new RestTemplate();
			String url = Suscribe_Url;
			url = url.replace("<REGISTRATION_TOKEN>", registrationToken).replace("<TOPIC_NAME>", topicName);
			HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			System.out.println("subscription to " + topicName + " " + response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void unsuscribeFromTopic(String registrationToken, String topicName, String serverKey) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			headers.set("Authorization", "key=" + serverKey);
			HttpEntity<?> entity = new HttpEntity<>(headers);

			RestTemplate restTemplate = new RestTemplate();
			String url = Suscribe_Url;
			url = url.replace("<REGISTRATION_TOKEN>", registrationToken).replace("<TOPIC_NAME>", topicName);
			HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
			System.out.println("unsubscribe to " + topicName + " " + response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/*
	 * private void getAccessToken(String serverKey) throws IOException { Path
	 * currentRelativePath = Paths.get(""); String currentPath =
	 * currentRelativePath.toAbsolutePath().toString();
	 * 
	 * GoogleCredential googleCredential = GoogleCredential .fromStream(new
	 * FileInputStream(currentPath+
	 * "/src/com/mobilebanking/fcm/google-service.json"))
	 * .createScoped(Arrays.asList(
	 * "https://www.googleapis.com/auth/firebase.messaging",
	 * "https://www.googleapis.com/auth/cloud-platform"));
	 * googleCredential.refreshToken(); serverAccessToken.put(serverKey,
	 * googleCredential.getAccessToken()); }
	 */

}
