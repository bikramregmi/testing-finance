package com.mobilebanking.util;

import java.net.UnknownHostException;
import java.util.HashMap;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobilebanking.airlines.model.PassengerTotalDto;
import com.mobilebanking.api.IOnlinePaymentAirlinesApi;
import com.mobilebanking.entity.MerchantManager;
import com.mobilebanking.model.Status;
import com.mobilebanking.repositories.MerchantManagerRepository;
import com.wallet.serviceprovider.eprabhu.service.Eprabhu_BusRouteDetailService;
import com.wallet.serviceprovider.khalti.service.KhanepaniService;
import com.wallet.serviceprovider.ntc.WebServiceException_Exception;
import com.wallet.serviceprovider.paypoint.service.PayPoint_GetCompanyInfoService;
import com.wallet.serviceprovider.unitedsolutions.service.United_Solutions_Service;

@Component
public class ServiceDetector {

	@Autowired
	private Eprabhu_BusRouteDetailService ePrabhu_BusRouteDetailService;

	@Autowired
	private United_Solutions_Service united_Solutions_Service;

	@Autowired
	private PayPoint_GetCompanyInfoService payPointService;

	@Autowired
	private MerchantManagerRepository merchantManagerRepository;
	
	@Autowired
	private IOnlinePaymentAirlinesApi onlinePaymentAirlinesService;
	
	@Autowired
	private KhanepaniService khanepaniService;
	
	public HashMap<String, Object> detectService(String serviceId, PassengerTotalDto pessangerDto) {
		HashMap<String, Object> hashResponse = new HashMap<String, Object>();
		HashMap<String, String> myHash = new HashMap<String, String>();
		if (serviceId.equals(StringConstants.AIRLINES_BOOKING)) {
			MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.AIRLINES_BOOKING, Status.Active,
					true);

			if (merchantManager == null) {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message", "The Selected Merchant is Not Available. Please Contact the Administrator.");
			} else {
				
				myHash.put("username",merchantManager.getMerchantsAndServices().getMerchant().getApiUsername());
				myHash.put("password",merchantManager.getMerchantsAndServices().getMerchant().getApiPassWord());
				myHash.put("apiurl",merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());

				 if (merchantManager.getMerchantsAndServices().getMerchant().getName()
						.equalsIgnoreCase("ONLINE PAYMENT")) {
					 hashResponse = onlinePaymentAirlinesService.executePayment(myHash,pessangerDto);
				} 
				 else{
					 hashResponse.put("status", "failure");
						hashResponse.put("Result Message", "The Selected Merchant is Not Available. Please Contact the Administrator.");
					}
			}
		}
		return hashResponse; 

	}
	
	

	public HashMap<String, String> detectService(String serviceId, HashMap<String, String> myHash)
			throws WebServiceException_Exception, UnknownHostException, JAXBException {

		HashMap<String, String> hashResponse = new HashMap<String, String>();

		if (serviceId.equals(StringConstants.NTC_PREPAID_TOPUP)) {
			MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.NTC_PREPAID_TOPUP,
					Status.Active, true);
			if (merchantManager == null) {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message",
						"The Selected Merchant is Not Available. Please Contact the Administrator.");
			}

			else {

				myHash.put("username", merchantManager.getMerchantsAndServices().getMerchant().getApiUsername());
				myHash.put("password", merchantManager.getMerchantsAndServices().getMerchant().getApiPassWord());
				myHash.put("apiurl", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());

				if (merchantManager.getMerchantsAndServices().getMerchant().getName().equalsIgnoreCase("PAYPOINT")) {
					myHash.put("company code", "585");
					myHash.put("serviceCode", "0");
					hashResponse = payPointService.checkPayment(myHash);
				} else {
					hashResponse.put("status", "failure");
					hashResponse.put("Result Message",
							"The Selected Merchant is Not Available. Please Contact the Administrator.");
				}
			}
		}

		if (serviceId.equals(StringConstants.NTC_POSTPAID_TOPUP)) {
			MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.NTC_POSTPAID_TOPUP,
					Status.Active, true);

			if (merchantManager == null) {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message",
						"The Selected Merchant is Not Available. Please Contact the Administrator.");
			} else {

				myHash.put("username", merchantManager.getMerchantsAndServices().getMerchant().getApiUsername());
				myHash.put("password", merchantManager.getMerchantsAndServices().getMerchant().getApiPassWord());
				myHash.put("apiurl", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());

				if (merchantManager.getMerchantsAndServices().getMerchant().getName().equalsIgnoreCase("PAYPOINT")) {
					myHash.put("company code", "585");
					myHash.put("serviceCode", "1");
					hashResponse = payPointService.checkPayment(myHash);
				} else {
					hashResponse.put("status", "failure");
					hashResponse.put("Result Message",
							"The Selected Merchant is Not Available. Please Contact the Administrator.");
				}
			}
		}

		if (serviceId.equals(StringConstants.NCELL_TOPUP)) {
			MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.NCELL_TOPUP,
					Status.Active, true);

			if (merchantManager == null) {
				hashResponse.put("status", "failure");
			} else {

				myHash.put("username", merchantManager.getMerchantsAndServices().getMerchant().getApiUsername());
				myHash.put("password", merchantManager.getMerchantsAndServices().getMerchant().getApiPassWord());
				myHash.put("apiurl", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());

				if (merchantManager.getMerchantsAndServices().getMerchant().getName().equalsIgnoreCase("PAYPOINT")) {
					myHash.put("company code", "78");
					myHash.put("serviceCode", "0");
					hashResponse = payPointService.checkPayment(myHash);
				} else {
					hashResponse.put("status", "failure");
					hashResponse.put("Result Message",
							"The Selected Merchant is Not Available. Please Contact the Administrator.");
				}
			}
		}

		if (serviceId.equals(StringConstants.SMARTCELL)) {
			MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.SMARTCELL,
					Status.Active, true);

			if (merchantManager == null) {
				hashResponse.put("status", "failure");
			} else {

				myHash.put("username", merchantManager.getMerchantsAndServices().getMerchant().getApiUsername());
				myHash.put("password", merchantManager.getMerchantsAndServices().getMerchant().getApiPassWord());
				myHash.put("apiurl", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());

				if (merchantManager.getMerchantsAndServices().getMerchant().getName().equalsIgnoreCase("PAYPOINT")) {
					myHash.put("company code", "709");
					myHash.put("serviceCode", "0");
					hashResponse = payPointService.checkPayment(myHash);
				} else {
					hashResponse.put("status", "failure");
					hashResponse.put("Result Message",
							"The Selected Merchant is Not Available. Please Contact the Administrator.");
				}
			}
		}

		if (serviceId.equals(StringConstants.DISHOME_TOPUP)) {
			MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.DISHOME_TOPUP,
					Status.Active, true);

			if (merchantManager == null) {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message",
						"The Selected Merchant is Not Available. Please Contact the Administrator.");
			} else {

				myHash.put("username", merchantManager.getMerchantsAndServices().getMerchant().getApiUsername());
				myHash.put("password", merchantManager.getMerchantsAndServices().getMerchant().getApiPassWord());
				myHash.put("apiurl", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());

				if (merchantManager.getMerchantsAndServices().getMerchant().getName().equals("PAYPOINT")) {
					myHash.put("company code", "59");
					myHash.put("serviceCode", "0");
					hashResponse = payPointService.checkPayment(myHash);
				} else {
					hashResponse.put("status", "failure");
					hashResponse.put("Result Message",
							"The Selected Merchant is Not Available. Please Contact the Administrator.");
				}
			}
		}

		if (serviceId.equals(StringConstants.WLINK_TOPUP)) {
			MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.WLINK_TOPUP,
					Status.Active, true);

			if (merchantManager == null) {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message",
						"The Selected Merchant is Not Available. Please Contact the Administrator.");
			} else {

				myHash.put("username", merchantManager.getMerchantsAndServices().getMerchant().getApiUsername());
				myHash.put("password", merchantManager.getMerchantsAndServices().getMerchant().getApiPassWord());
				myHash.put("apiurl", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());

				if (merchantManager.getMerchantsAndServices().getMerchant().getName().equalsIgnoreCase("PAYPOINT")) {
					myHash.put("company code", "597");
					myHash.put("serviceCode", "0");
					String packageId = myHash.get("packageId");
					if (packageId != null) {
						myHash.put("package", "haspackage");
					} else {
						myHash.put("package", "nopackage");
					}
					hashResponse = payPointService.executePayment(myHash);
				} else {
					hashResponse.put("status", "failure");
					hashResponse.put("Result Message",
							"The Selected Merchant is Not Available. Please Contact the Administrator.");
				}
			}
		}

		if (serviceId.equals(StringConstants.VIANET)) {
			MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.VIANET,
					Status.Active, true);

			if (merchantManager == null) {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message",
						"The Selected Merchant is Not Available. Please Contact the Administrator.");
			} else {

				myHash.put("username", merchantManager.getMerchantsAndServices().getMerchant().getApiUsername());
				myHash.put("password", merchantManager.getMerchantsAndServices().getMerchant().getApiPassWord());
				myHash.put("apiurl", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());

				if (merchantManager.getMerchantsAndServices().getMerchant().getName().equalsIgnoreCase("PAYPOINT")) {
					myHash.put("company code", "716");
					myHash.put("serviceCode", "0");
					String packageId = myHash.get("packageId");
					if (packageId != null) {
						myHash.put("package", "haspackage");
					} else {
						myHash.put("package", "nopackage");
					}
					hashResponse = payPointService.executePayment(myHash);
				} else {
					hashResponse.put("status", "failure");
					hashResponse.put("Result Message",
							"The Selected Merchant is Not Available. Please Contact the Administrator.");
				}
			}
		}

		if (serviceId.equals(StringConstants.SUBISU)) {
			MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.SUBISU,
					Status.Active, true);

			if (merchantManager == null) {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message",
						"The Selected Merchant is Not Available. Please Contact the Administrator.");
			} else {

				myHash.put("username", merchantManager.getMerchantsAndServices().getMerchant().getApiUsername());
				myHash.put("password", merchantManager.getMerchantsAndServices().getMerchant().getApiPassWord());
				myHash.put("apiurl", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());

				if (merchantManager.getMerchantsAndServices().getMerchant().getName().equalsIgnoreCase("PAYPOINT")) {
					myHash.put("company code", "596");
					myHash.put("serviceCode", "0");
					hashResponse = payPointService.checkPayment(myHash);
				} else {
					hashResponse.put("status", "failure");
					hashResponse.put("Result Message",
							"The Selected Merchant is Not Available. Please Contact the Administrator.");
				}
			}
		}

		if (serviceId.equals(StringConstants.SIMTV)) {
			MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.SIMTV,
					Status.Active, true);

			if (merchantManager == null) {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message",
						"The Selected Merchant is Not Available. Please Contact the Administrator.");
			} else {

				myHash.put("username", merchantManager.getMerchantsAndServices().getMerchant().getApiUsername());
				myHash.put("password", merchantManager.getMerchantsAndServices().getMerchant().getApiPassWord());
				myHash.put("apiurl", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());

				if (merchantManager.getMerchantsAndServices().getMerchant().getName().equalsIgnoreCase("PAYPOINT")) {
					myHash.put("company code", "595");
					myHash.put("serviceCode", "0");
					hashResponse = payPointService.checkPayment(myHash);
				} else {
					hashResponse.put("status", "failure");
					hashResponse.put("Result Message",
							"The Selected Merchant is Not Available. Please Contact the Administrator.");
				}
			}
		}

		if (serviceId.equals(StringConstants.PSTN)) {
			MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.PSTN, Status.Active,
					true);

			if (merchantManager == null) {
				hashResponse.put("status", "failure");
			} else {

				myHash.put("username", merchantManager.getMerchantsAndServices().getMerchant().getApiUsername());
				myHash.put("password", merchantManager.getMerchantsAndServices().getMerchant().getApiPassWord());
				myHash.put("apiurl", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());

				if (merchantManager.getMerchantsAndServices().getMerchant().getName().equalsIgnoreCase("PAYPOINT")) {
					myHash.put("company code", "585");
					myHash.put("serviceCode", "2");
					hashResponse = payPointService.checkPayment(myHash);
				} else {
					hashResponse.put("status", "failure");
					hashResponse.put("Result Message",
							"The Selected Merchant is Not Available. Please Contact the Administrator.");
				}
			}
		}

		if (serviceId.equals(StringConstants.ADSLU)) {
			MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.ADSLU,
					Status.Active, true);

			if (merchantManager == null) {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message",
						"The Selected Merchant is Not Available. Please Contact the Administrator.");
			} else {

				myHash.put("username", merchantManager.getMerchantsAndServices().getMerchant().getApiUsername());
				myHash.put("password", merchantManager.getMerchantsAndServices().getMerchant().getApiPassWord());
				myHash.put("apiurl", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());

				if (merchantManager.getMerchantsAndServices().getMerchant().getName().equalsIgnoreCase("PAYPOINT")) {
					myHash.put("company code", "585");
					myHash.put("serviceCode", "3");
					hashResponse = payPointService.checkPayment(myHash);
				} else {
					hashResponse.put("status", "failure");
					hashResponse.put("Result Message",
							"The Selected Merchant is Not Available. Please Contact the Administrator.");
				}
			}
		}

		if (serviceId.equals(StringConstants.ADSLV)) {
			MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.ADSLV,
					Status.Active, true);

			if (merchantManager == null) {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message",
						"The Selected Merchant is Not Available. Please Contact the Administrator.");
			} else {

				myHash.put("username", merchantManager.getMerchantsAndServices().getMerchant().getApiUsername());
				myHash.put("password", merchantManager.getMerchantsAndServices().getMerchant().getApiPassWord());
				myHash.put("apiurl", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());

				if (merchantManager.getMerchantsAndServices().getMerchant().getName().equalsIgnoreCase("PAYPOINT")) {
					myHash.put("company code", "585");
					myHash.put("serviceCode", "4");
					hashResponse = payPointService.checkPayment(myHash);
				} else {
					hashResponse.put("status", "failure");
					hashResponse.put("Result Message",
							"The Selected Merchant is Not Available. Please Contact the Administrator.");
				}
			}
		}
		if (serviceId.equals(StringConstants.ADSLV)) {
			MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.ADSLV,
					Status.Active, true);

			if (merchantManager == null) {
				hashResponse.put("status", "failure");
			} else {

				myHash.put("username", merchantManager.getMerchantsAndServices().getMerchant().getApiUsername());
				myHash.put("password", merchantManager.getMerchantsAndServices().getMerchant().getApiPassWord());
				myHash.put("apiurl", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());

				if (merchantManager.getMerchantsAndServices().getMerchant().getName().equalsIgnoreCase("PAYPOINT")) {
					myHash.put("company code", "585");
					myHash.put("serviceCode", "5");
					hashResponse = payPointService.checkPayment(myHash);
				} else {
					hashResponse.put("status", "failure");
					hashResponse.put("Result Message",
							"The Selected Merchant is Not Available. Please Contact the Administrator.");
				}
			}
		}

		if (serviceId.equals(StringConstants.NEA)) {
			MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.NEA, Status.Active,
					true);

			if (merchantManager == null) {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message",
						"The Selected Merchant is Not Available. Please Contact the Administrator.");
			} else {

				myHash.put("username", merchantManager.getMerchantsAndServices().getMerchant().getApiUsername());
				myHash.put("password", merchantManager.getMerchantsAndServices().getMerchant().getApiPassWord());
				myHash.put("apiurl", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());

				if (merchantManager.getMerchantsAndServices().getMerchant().getName().equalsIgnoreCase("PAYPOINT")) {
					myHash.put("company code", "598");
					myHash.put("serviceCode", myHash.get("officeCode"));
					hashResponse = payPointService.executePayment(myHash);
				} else {
					hashResponse.put("status", "failure");
					hashResponse.put("Result Message",
							"The Selected Merchant is Not Available. Please Contact the Administrator.");
				}
			}
		}
		
		if (serviceId.equals(StringConstants.KHANE_PANI)) {
			MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.KHANE_PANI, Status.Active,
					true);

			if (merchantManager == null) {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message",
						"The Selected Merchant is Not Available. Please Contact the Administrator.");
			} else {
				myHash.put("apiurl", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());

				if (merchantManager.getMerchantsAndServices().getMerchant().getName().equalsIgnoreCase("KHALTI")) {
					myHash.put("token", merchantManager.getMerchantsAndServices().getMerchant().getExtraFieldOne());
					hashResponse = khanepaniService.payKhanepaniBill(myHash);
				} else {
					hashResponse.put("status", "failure");
					hashResponse.put("Result Message",
							"The Selected Merchant is Not Available. Please Contact the Administrator.");
				}
			}
		}

		if (serviceId.equals(StringConstants.UTL)) {
			MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.UTL, Status.Active,
					true);

			if (merchantManager == null) {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message",
						"The Selected Merchant is Not Available. Please Contact the Administrator.");
			} else {

				myHash.put("username", merchantManager.getMerchantsAndServices().getMerchant().getApiUsername());
				myHash.put("password", merchantManager.getMerchantsAndServices().getMerchant().getApiPassWord());
				myHash.put("apiurl", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());

				if (merchantManager.getMerchantsAndServices().getMerchant().getName().equalsIgnoreCase("PAYPOINT")) {
					myHash.put("company code", "582");
					myHash.put("serviceCode", "0");
					hashResponse = payPointService.checkPayment(myHash);
				} else {
					hashResponse.put("status", "failure");
					hashResponse.put("Result Message",
							"The Selected Merchant is Not Available. Please Contact the Administrator.");
				}
			}
		}

		if (serviceId.equals(StringConstants.BROADTEL)) {
			MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.BROADTEL,
					Status.Active, true);

			if (merchantManager == null) {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message",
						"The Selected Merchant is Not Available. Please Contact the Administrator.");
			} else {

				myHash.put("username", merchantManager.getMerchantsAndServices().getMerchant().getApiUsername());
				myHash.put("password", merchantManager.getMerchantsAndServices().getMerchant().getApiPassWord());
				myHash.put("apiurl", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());

				if (merchantManager.getMerchantsAndServices().getMerchant().getName().equalsIgnoreCase("PAYPOINT")) {
					myHash.put("company code", "581");
					myHash.put("serviceCode", "0");
					myHash.put("internetorvoip", "tv");
					hashResponse = payPointService.checkPayment(myHash);
				} else {
					hashResponse.put("status", "failure");
					hashResponse.put("Result Message",
							"The Selected Merchant is Not Available. Please Contact the Administrator.");
				}
			}
		}

		if (serviceId.equals(StringConstants.DISHHOME_PIN)) {
			MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.DISHHOME_PIN,
					Status.Active, true);

			if (merchantManager == null) {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message",
						"The Selected Merchant is Not Available. Please Contact the Administrator.");
			} else {

				myHash.put("username", merchantManager.getMerchantsAndServices().getMerchant().getApiUsername());
				myHash.put("password", merchantManager.getMerchantsAndServices().getMerchant().getApiPassWord());
				myHash.put("apiurl", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());

				if (merchantManager.getMerchantsAndServices().getMerchant().getName().equalsIgnoreCase("PAYPOINT")) {
					myHash.put("company code", "580");
					myHash.put("serviceCode", "0");
					hashResponse = payPointService.checkPayment(myHash);
				} else {
					hashResponse.put("status", "failure");
					hashResponse.put("Result Message",
							"The Selected Merchant is Not Available. Please Contact the Administrator.");
				}
			}
		}

		if (serviceId.equals(StringConstants.BROADLINK)) {
			MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.BROADLINK,
					Status.Active, true);

			if (merchantManager == null) {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message",
						"The Selected Merchant is Not Available. Please Contact the Administrator.");
			} else {

				myHash.put("username", merchantManager.getMerchantsAndServices().getMerchant().getApiUsername());
				myHash.put("password", merchantManager.getMerchantsAndServices().getMerchant().getApiPassWord());
				myHash.put("apiurl", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());

				if (merchantManager.getMerchantsAndServices().getMerchant().getName().equalsIgnoreCase("PAYPOINT")) {
					myHash.put("company code", "581");
					myHash.put("serviceCode", "0");
					myHash.put("internetorvoip", "internet");
					hashResponse = payPointService.checkPayment(myHash);
				} else {
					hashResponse.put("status", "failure");
					hashResponse.put("Result Message",
							"The Selected Merchant is Not Available. Please Contact the Administrator.");
				}
			}
		}

		if (serviceId.equals(StringConstants.SMART_CELL)) {
			MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.SMART_CELL,
					Status.Active, true);

			if (merchantManager == null) {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message",
						"The Selected Merchant is Not Available. Please Contact the Administrator.");
			} else {

				myHash.put("username", merchantManager.getMerchantsAndServices().getMerchant().getApiUsername());
				myHash.put("password", merchantManager.getMerchantsAndServices().getMerchant().getApiPassWord());
				myHash.put("apiurl", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());

				if (merchantManager.getMerchantsAndServices().getMerchant().getName().equalsIgnoreCase("PAYPOINT")) {
					myHash.put("company code", "587");
					if (myHash.get("amount").equals("50.0")) {
						myHash.put("serviceCode", "1");
					}
					if (myHash.get("amount").equals("100.0")) {
						myHash.put("serviceCode", "2");
					}
					if (myHash.get("amount").equals("200.0")) {
						myHash.put("serviceCode", "3");
					}
					if (myHash.get("amount").equals("500.0")) {
						myHash.put("serviceCode", "4");
					}
					if (myHash.get("amount").equals("10.0")) {
						myHash.put("serviceCode", "10");
					}
					hashResponse = payPointService.checkPayment(myHash);
				} else {
					hashResponse.put("status", "failure");
					hashResponse.put("Result Message",
							"The Selected Merchant is Not Available. Please Contact the Administrator.");
				}
			}
		}

		if (serviceId.equals(StringConstants.NET_TV)) {
			MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.NET_TV,
					Status.Active, true);

			if (merchantManager == null) {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message",
						"The Selected Merchant is Not Available. Please Contact the Administrator.");
			} else {

				myHash.put("username", merchantManager.getMerchantsAndServices().getMerchant().getApiUsername());
				myHash.put("password", merchantManager.getMerchantsAndServices().getMerchant().getApiPassWord());
				myHash.put("apiurl", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());

				if (merchantManager.getMerchantsAndServices().getMerchant().getName().equalsIgnoreCase("PAYPOINT")) {
					myHash.put("company code", "579");
					if (myHash.get("amount").equals("50.0")) {
						myHash.put("serviceCode", "1");
					}
					if (myHash.get("amount").equals("100.0")) {
						myHash.put("serviceCode", "2");
					}
					if (myHash.get("amount").equals("200.0")) {
						myHash.put("serviceCode", "3");
					}
					if (myHash.get("amount").equals("500.0")) {
						myHash.put("serviceCode", "4");
					}
					if (myHash.get("amount").equals("1000.0")) {
						myHash.put("serviceCode", "5");
					}
					if (myHash.get("amount").equals("10.0")) {
						myHash.put("serviceCode", "10");
					}
					hashResponse = payPointService.checkPayment(myHash);
				} else {
					hashResponse.put("status", "failure");
					hashResponse.put("Result Message",
							"The Selected Merchant is Not Available. Please Contact the Administrator.");
				}
			}
		}

		if (serviceId.equals(StringConstants.CDMA)) {
			MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.CDMA, Status.Active,
					true);

			if (merchantManager == null) {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message",
						"The Selected Merchant is Not Available. Please Contact the Administrator.");
			} else {

				myHash.put("username", merchantManager.getMerchantsAndServices().getMerchant().getApiUsername());
				myHash.put("password", merchantManager.getMerchantsAndServices().getMerchant().getApiPassWord());
				myHash.put("apiurl", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());

				if (merchantManager.getMerchantsAndServices().getMerchant().getName().equalsIgnoreCase("PAYPOINT")) {
					myHash.put("company code", "585");
					myHash.put("serviceCode", "5");
					hashResponse = payPointService.checkPayment(myHash);
				} else {
					hashResponse.put("status", "failure");
					hashResponse.put("Result Message",
							"The Selected Merchant is Not Available. Please Contact the Administrator.");
				}
			}
		}

		// if (serviceId.equals("ntc_prepaid_topup") ||
		// serviceId.equals("ntc_postpaid_topup")) {
		// hashResponse = NTC_ERetailRechargeService.mobileTopUp(myHash);
		// }else if(serviceId.equals("dishHome")){
		// hashResponse = dishHome_BalanceEnquiryService.balanceEnquiry();
		// }else if (serviceId.equals("dishHomeRecharge")){
		// hashResponse = dishHome_BalanceEnquiryService.recharge(myHash);
		// }else if (serviceId.equals("rechargebycasId")){
		// hashResponse =
		// dishHome_BalanceEnquiryService.rechargeBySTBIDorCASIDorCHIPID(myHash);;
		// }
		// else if (serviceId.equals("unitedSolutionBalanceCheck")){
		// united_Solutions_Service.checkBalance();
		// hashResponse.put("test", "test");
		// }
		// else if (serviceId.equals("epayService")){
		// epayService.getBalance();
		// hashResponse.put("epay", "test");
		// }
		// else if (serviceId.equals("epayOnlineService")){
		// epayService.onlinePayment(myHash);;
		// hashResponse.put("epay", "test");
		// }
		//
		// else if (serviceId.equals("paypoint_ntc_topup")){
		// myHash.put("company code", "585");
		// hashResponse = payPointService.checkPayment(myHash);
		//
		// }else if (serviceId.equals("paypoint_ncell_topup")){
		// myHash.put("company code", "78");
		// hashResponse = payPointService.checkPayment(myHash);
		//
		// }
		//

		return hashResponse;
	}

	public void checkPaypoint() {
		// payPoint_GetCompanyInfoService.companyInfo();

		// payPoint_GetCompanyInfoService.addCancelRequest();

		// payPoint_GetCompanyInfoService.addCancelRequestEx();

		try {
			// payPoint_GetCompanyInfoService.executePayment();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// payPoint_GetCompanyInfoService.dailyReconciliation();

		// payPoint_GetCompanyInfoService.executePayment();

		// payPoint_GetCompanyInfoService.executePaymentEx();

		// payPoint_GetCompanyInfoService.getTransaction();

		// payPoint_GetCompanyInfoService.getTransactionByExternal();

		// payPoint_GetCompanyInfoService.getTransactionEx();

		// payPoint_GetCompanyInfoService.getTransactionReport();

		// payPoint_GetCompanyInfoService.getTransactionReportSummary();

		// payPoint_GetCompanyInfoService.retransmit();

	}

	public void checkEprabhu() {
		ePrabhu_BusRouteDetailService.getBusRouteDetail();

		// ePrabhu_BusRouteDetailService.getFlightSearchDomestic();

		// ePrabhu_BusRouteDetailService.checkWlinkAccount();

		// ePrabhu_BusRouteDetailService.getBalance();

		// ePrabhu_BusRouteDetailService.getBillPayment();

		// ePrabhu_BusRouteDetailService.getBusDetailSearch();

		// ePrabhu_BusRouteDetailService.getBusSeatLayout();

		// ePrabhu_BusRouteDetailService.getBusTicketBook();

		// ePrabhu_BusRouteDetailService.getBusTicketIssue();

		// ePrabhu_BusRouteDetailService.getCheckPolicy();

		// ePrabhu_BusRouteDetailService.getDishHomeBooking();

		// ePrabhu_BusRouteDetailService.getFlightBookDomestic();

		// ePrabhu_BusRouteDetailService.getFlightConfirmDomestic();

		// ePrabhu_BusRouteDetailService.getInsurancePremium();

		// ePrabhu_BusRouteDetailService.getMobileTopup();

		// ePrabhu_BusRouteDetailService.getNEABill();

		// ePrabhu_BusRouteDetailService.getNEACharge();

		// ePrabhu_BusRouteDetailService.getNEAOfficeCode();

		// ePrabhu_BusRouteDetailService.getNEAOfficeCode();

		// ePrabhu_BusRouteDetailService.getRechargePins();

		// ePrabhu_BusRouteDetailService.getSMSTransactionStatus();

		// ePrabhu_BusRouteDetailService.getTransactionStatus();
	}

	public HashMap<String, String> checkDishHome(String string, HashMap<String, String> hashRequest) {
		return hashRequest;
		// HashMap<String, String> hashResponse = new HashMap<String, String>();
		// hashResponse = dishHome_BalanceEnquiryService.balanceEnquiry();
		// return hashResponse;
		// dishHome_BalanceEnquiryService.packageInfo();
		// dishHome_BalanceEnquiryService.customerIDCheck();

	}

	public void unitedSolutions() {

		united_Solutions_Service.flightAvailability();

	}

}
