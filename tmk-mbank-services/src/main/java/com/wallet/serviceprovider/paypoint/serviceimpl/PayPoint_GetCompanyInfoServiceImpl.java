package com.wallet.serviceprovider.paypoint.serviceimpl;

import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.wallet.serviceprovider.paypoint.OperationsSoap;
import com.wallet.serviceprovider.paypoint.paypointResponse.NeaBillAmountResponse;
import com.wallet.serviceprovider.paypoint.paypointResponse.PPResponse;
import com.wallet.serviceprovider.paypoint.paypointResponse.Package;
import com.wallet.serviceprovider.paypoint.paypointResponse.Payment;
import com.wallet.serviceprovider.paypoint.paypointResponse.TransactionResponse;
import com.wallet.serviceprovider.paypoint.paypointResponse.WorldLinkPackage;
import com.wallet.serviceprovider.paypoint.service.PayPoint_GetCompanyInfoService;

@Service
public class PayPoint_GetCompanyInfoServiceImpl implements PayPoint_GetCompanyInfoService {
	private static final Logger log = LoggerFactory.getLogger(PayPoint_GetCompanyInfoServiceImpl.class);
	@Autowired
	OperationsSoap operationsSoap;

	@Override
	public void companyInfo() {

		int companyCode = 78;
		int serviceCode = 0;
		// String userId = myHash.get("");
		// String userPassword = myHash.get("");
		String userId = "HaMroTec";
		String userPassword = "HM@T2|Ar";
		int salePointType = 6;

		String companyInfo = operationsSoap.getCompanyInfo(companyCode, serviceCode, userId, userPassword,
				salePointType);
		operationsSoap.printCompanyInfoResponse(companyInfo);

	}

	@Override
	public NeaBillAmountResponse checkNeaPaymentAmount(HashMap<String, String> myHash) throws JAXBException {
		HashMap<String, String> hashResponse = new HashMap<>();
		UUID uniqueId = UUID.randomUUID();
		// (new Date()).getTime()
		// customer.setUniqueID(String.valueOf(uniqueId));

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String formattedDate = dateFormat.format(new Date());
		int companyCode = Integer.parseInt(myHash.get("company code"));

		String account = myHash.get("serviceTo");
		String special1 = myHash.get("officeCode");

		long amt = 0;

		int serviceCode = Integer.parseInt(myHash.get("officeCode"));

		myHash.put("serviceCode", "" + serviceCode);
		myHash.put("account", account);
		String special2 = myHash.get("customerId");
		String transactionDate = formattedDate;
		long transactionId = new Date().getTime();
		String userId = myHash.get("username");
		String userPassword = myHash.get("password");
		String url = myHash.get("apiurl");
		int salePointType = 6;
		String checkPayment = null;
		try {
			checkPayment = operationsSoap.checkPayment(companyCode, serviceCode, account, special1, special2,
					transactionDate, transactionId, userId, userPassword, salePointType, url);
		} catch (Exception e) {
			e.printStackTrace();
			hashResponse.put("status", "failure");
		}

		InputSource is = new InputSource(new StringReader(checkPayment));
		JAXBContext jaxbContext;
		jaxbContext = JAXBContext.newInstance(PPResponse.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		PPResponse response = (PPResponse) jaxbUnmarshaller.unmarshal(is);
		NeaBillAmountResponse neaBillResponse = new NeaBillAmountResponse();
		if (response.getResult().equals("000")) {
			hashResponse.put("status", "success");

			hashResponse.put("Key", "" + response.getKey());
			hashResponse.put("Result Message", "" + response.getResultMessage());
			hashResponse.put("Utility Code", "" + response.getUtilityInfo().getUtilityCode());
			hashResponse.put("Bill Number", "" + response.getBillInfo().getBill().getBillNumber());
			hashResponse.put("Customer Name", "" + response.getBillInfo().getBill().getReserveInfo());
			hashResponse.put("RefStan", "" + response.getBillInfo().getBill().getRefStan());
			hashResponse.put("Commission Type",
					"" + response.getBillInfo().getBill().getBillParam().getCommission().getType());
			hashResponse.put("Service Charge",
					"" + response.getBillInfo().getBill().getBillParam().getCommission().getVal());
			hashResponse.put("Amount", "" + response.getBillInfo().getBill().getAmount());
			try {
				hashResponse.put("Billable Amount", response.getBillInfo().getBill().getBillParam().getPayments()
						.getPayment().get(0).get_billAmount());
				hashResponse.put("Penalty",
						response.getBillInfo().getBill().getBillParam().getPayments().getPayment().get(0).get_status());
				hashResponse.put("Bill Amount",
						response.getBillInfo().getBill().getBillParam().getPayments().getPayment().get(0).get_amount());
				hashResponse.put("Dayres", response.getBillInfo().getBill().getBillParam().getPayments().getPayment()
						.get(0).get_description());
			} catch (NullPointerException e) {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message", "No pending bills available.Please try again later.");
				neaBillResponse.setHashResponse(hashResponse);
				return neaBillResponse;
			}
			if (response.getBillInfo().getBill().getBillParam().getPayments().getPayment() != null
					|| !response.getBillInfo().getBill().getBillParam().getPayments().getPayment().isEmpty()) {
				neaBillResponse.setPayment(response.getBillInfo().getBill().getBillParam().getPayments().getPayment());
			} else {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message", "No pending bills available.Please try again later.");
				neaBillResponse.setHashResponse(hashResponse);
				return neaBillResponse;
			}
			JAXBContext jaxbContext1 = JAXBContext.newInstance(Payment.class);
			Marshaller jaxbMarshaller = jaxbContext1.createMarshaller();

			jaxbMarshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);

			// jaxbMarshaller.setProperty(Marshaller., false);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(response.getBillInfo().getBill().getBillParam().getPayments().getPayment().get(0),
					sw);

			String result = sw.toString();

			// log.info("result-->" + result);

			hashResponse.put("payment", result);

			// if(serviceCode==597){
			// hashResponse.put("RefStan" , ""+
			// response.getBillInfo().getBill().getRefStan());
			// }

		} else {
			hashResponse.put("status", "failure");
			hashResponse.put("Key", "" + response.getKey());
			hashResponse.put("Result Message", "" + response.getResultMessage());
		}
		operationsSoap.printCheckPayment(checkPayment);
		neaBillResponse.setHashResponse(hashResponse);
		return neaBillResponse;
	}

	@Override
	public WorldLinkPackage checkPakageForWorldLink(HashMap<String, String> myHash) throws JAXBException {
		// SimpleDateFormat dateFormat = new
		// SimpleDateFormat("yyy-mm-ddTHH:mm:ss");
		// String formattedDate = dateFormat.format(new Date());
		HashMap<String, String> hashResponse = new HashMap<>();
		UUID uniqueId = UUID.randomUUID();
		// (new Date()).getTime()
		// customer.setUniqueID(String.valueOf(uniqueId));

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String formattedDate = dateFormat.format(new Date());
		int companyCode = Integer.parseInt(myHash.get("company code"));

		// String account ="9779849650000";
		String account = myHash.get("serviceTo");
		String special1 = null;
		long amt = 0;
		if (companyCode == 59 || companyCode == 78 || companyCode == 597 || companyCode == 716) {
			amt = (long) Double.parseDouble(myHash.get("amount"));
			account = myHash.get("serviceTo");
		}
		int serviceCode = 0;

		serviceCode = Integer.parseInt(myHash.get("serviceCode"));

		myHash.put("serviceCode", "" + serviceCode);
		if (companyCode == 585) {
			special1 = "" + amt;
		} else if (companyCode == 78 || companyCode == 59 || companyCode == 597) {
			special1 = null;
		}
		myHash.put("account", account);
		String special2 = null;
		String transactionDate = formattedDate;
		long transactionId = new Date().getTime();
		String userId = myHash.get("username");
		String userPassword = myHash.get("password");
		String url = myHash.get("apiurl");
		int salePointType = 6;
		String checkPayment = null;
		try {
			checkPayment = operationsSoap.checkPayment(companyCode, serviceCode, account, special1, special2,
					transactionDate, transactionId, userId, userPassword, salePointType, url);
			//checkPayment = checkPayment.replaceAll("&", "&amp;");
			System.out.println("CHECK PAYMENT RESPONSE==> " + checkPayment);
		} catch (Exception e) {
			e.printStackTrace();
			hashResponse.put("status", "failure");
		}

		/*
		 * checkPayment =
		 * "<PPResponse Result=\"000\" Key=\"5833033a-8f18-4623-a1e2-8ee54148e968\">"
		 * +
		 * "<ResultMessage>Operation is succesfully completed</ResultMessage>"+
		 * "<UtilityInfo>"+ " <UtilityCode>597</UtilityCode>" +
		 * "</UtilityInfo>"+ "<BillInfo>"+ "<Bill>"+
		 * " <BillNumber>5833033a-8f18-4623-a1e2-8ee54148e968</BillNumber>"
		 * +"<DueDate>2015-01-27T14:25:59</DueDate>"+"   <Amount>0</Amount>"
		 * +" <ReserveInfo>Min.Amount:250.00 Rs</ReserveInfo>"
		 * +"<BillParam>"+" <mask>6</mask>"
		 * +"<commission type=\"0\" val=\"0.00\" op=\"-\" paysource=\"1\" />"
		 * +"<packages count=\"2\">"
		 * +"<package id=\"306\" amount=\"158200\" currency=\"Rs\">21st Anv.Offer FTTH -100GB/mth 10mbps new 2073 (Rs.1582)</package>"
		 * +
		 * "<package id=\"318\" amount=\"1803400\" currency=\"Rs\">21st Anv.Offer FTTH 1500gb/12mths 10 mbps New 2073 (Rs.18034)</package>"
		 * +
		 * "</packages></BillParam><RefStan>6035915919029</RefStan></Bill> </BillInfo></PPResponse>"
		 * ;
		 */
		InputSource is = new InputSource(new StringReader(checkPayment));
		JAXBContext jaxbContext;
		jaxbContext = JAXBContext.newInstance(PPResponse.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		PPResponse response = (PPResponse) jaxbUnmarshaller.unmarshal(is);

		WorldLinkPackage worldLinkPackage = new WorldLinkPackage();
		List<Package> packages = new ArrayList<Package>();

		if (response.getResult().equals("000")) {
			hashResponse.put("status", "success");

			hashResponse.put("Key", "" + response.getKey());
			hashResponse.put("Result Message", "" + response.getResultMessage());
			hashResponse.put("Utility Code", "" + response.getUtilityInfo().getUtilityCode());
			hashResponse.put("Bill Number", "" + response.getBillInfo().getBill().getBillNumber());
			hashResponse.put("Reserve Info", "" + response.getBillInfo().getBill().getReserveInfo());
			hashResponse.put("RefStan", "" + response.getBillInfo().getBill().getRefStan());
			hashResponse.put("Commission Type",
					"" + response.getBillInfo().getBill().getBillParam().getCommission().getType());
			hashResponse.put("Commission Value",
					"" + response.getBillInfo().getBill().getBillParam().getCommission().getVal());
			hashResponse.put("Amount", "" + response.getBillInfo().getBill().getAmount());
			hashResponse.put("serviceCode", "" + myHash.get("serviceCode"));
			if (packages != null) {
				packages = response.getBillInfo().getBill().getBillParam().getPackages().getPackageList();
			}
			// if(serviceCode==597){
			// hashResponse.put("RefStan" , ""+
			// response.getBillInfo().getBill().getRefStan());
			// }

		} else if (response.getResult().equals("064")) {
			hashResponse.put("status", "failure");
			hashResponse.put("Key", "" + response.getKey());
			hashResponse.put("Result Message", "No pending bills available. Please try again later.");
		} else {
			hashResponse.put("status", "failure");
			hashResponse.put("Key", "" + response.getKey());
			hashResponse.put("Result Message", "" + response.getResultMessage());
		}
		worldLinkPackage.setHashResponse(hashResponse);
		worldLinkPackage.setPackages(packages);
		operationsSoap.printCheckPayment(checkPayment);
		return worldLinkPackage;

	}

	@Override
	public HashMap<String, String> checkPayment(HashMap<String, String> myHash) throws JAXBException {

		// SimpleDateFormat dateFormat = new
		// SimpleDateFormat("yyy-mm-ddTHH:mm:ss");
		// String formattedDate = dateFormat.format(new Date());
		HashMap<String, String> hashResponse = new HashMap<>();
		UUID uniqueId = UUID.randomUUID();
		// (new Date()).getTime()
		// customer.setUniqueID(String.valueOf(uniqueId));

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String formattedDate = dateFormat.format(new Date());
		int companyCode = Integer.parseInt(myHash.get("company code"));

		// String account ="9779849650000";
		String account;
		String special1 = null;
		long amt;
		int serviceCode = 0;
		amt = (long) Double.parseDouble(myHash.get("amount")) * 100;
		long amtCase = (long) Double.parseDouble(myHash.get("amount"));
		/*
		 * if (companyCode == 590) { switch ((int) (amtCase)) { case 300:
		 * serviceCode = 1; break; case 400: serviceCode = 2; break; case 500:
		 * serviceCode = 3; break; case 1000: serviceCode = 4; break; case 2000:
		 * serviceCode = 5; break; case 3000: serviceCode = 6; break; case 5000:
		 * serviceCode = 8; break; case 7000: serviceCode = 10; break;
		 * 
		 * }
		 */

		if (companyCode == 709) {
			switch ((int) (amtCase)) {
			case 50:
				serviceCode = 1;
				break;
			case 100:
				serviceCode = 2;
				break;
			case 200:
				serviceCode = 3;
				break;
			case 500:
				serviceCode = 4;
				break;
			case 10:
				serviceCode = 10;
				break;
			}
		} else
			serviceCode = Integer.parseInt(myHash.get("serviceCode"));

		if (companyCode == 59 || companyCode == 78 || companyCode == 597 || companyCode == 595) {
			account = myHash.get("serviceTo");

		} else if (companyCode == 596) {
			account = myHash.get("subisuUsername");
		} else if (companyCode == 585 && (serviceCode == 0 || serviceCode == 1 || serviceCode == 5)) {
			account = "977" + myHash.get("serviceTo");
		} else if (companyCode == 587) {

			account = "VoucherSCellTest";
		} else if (companyCode == 709) {
			account = "977" + myHash.get("serviceTo");
		} else {
			account = myHash.get("serviceTo");
		}

		myHash.put("serviceCode", "" + serviceCode);
		if (companyCode == 585) {
			special1 = "" + amt;
		} else if (companyCode == 596) {
			special1 = myHash.get("serviceTo");
		} else if (companyCode == 582) {
			special1 = "" + amt;
		} else if (companyCode == 78 || companyCode == 59 || companyCode == 597) {
			special1 = null;
		} else if (companyCode == 597 && !myHash.get("package").equals("nopackage")) {
			special1 = myHash.get("packageId");
		}

		if (companyCode == 581) {

			String comingAmt = myHash.get("amount");
			String actualAmt = comingAmt.substring(0, comingAmt.lastIndexOf("."));

			if (actualAmt.equals("500")) {
				if (myHash.get("internetorvoip").equals("internet")) {
					serviceCode = 1;
				} else {
					serviceCode = 6;
				}
			} else if (actualAmt.equals("1500")) {
				serviceCode = 2;
			} else if (actualAmt.equals("2000")) {
				serviceCode = 3;
			} else if (actualAmt.equals("2260")) {
				serviceCode = 4;
			} else if (actualAmt.equals("250")) {
				serviceCode = 5;
			} else {
				serviceCode = 10;
			}

			account = "VoucherBLTest";
		}

		if (companyCode == 580) {
			String comingAmt = myHash.get("amount");
			String actualAmt = comingAmt.substring(0, comingAmt.lastIndexOf("."));

			if (actualAmt.equals("300")) {
				serviceCode = 12;
			} else if (actualAmt.equals("400")) {
				serviceCode = 13;
			} else if (actualAmt.equals("500")) {
				serviceCode = 2;
			} else if (actualAmt.equals("1000")) {
				serviceCode = 3;
			} else if (actualAmt.equals("2000")) {
				serviceCode = 14;
			} else if (actualAmt.equals("3000")) {
				serviceCode = 15;
			} else if (actualAmt.equals("4000")) {
				serviceCode = 16;
			} else if (actualAmt.equals("5000")) {
				serviceCode = 17;
			} else if (actualAmt.equals("6000")) {
				serviceCode = 18;
			} else if (actualAmt.equals("7000")) {
				serviceCode = 19;
			} else {
				serviceCode = 9;
			}

			account = "VoucherDTHTest";
		}

		myHash.put("account", account);
		String special2 = null;
		String transactionDate = formattedDate;
		long transactionId = new Date().getTime();
		String userId = myHash.get("username");
		String userPassword = myHash.get("password");
		String url = myHash.get("apiurl");
		int salePointType = 6;
		String checkPayment = null;

		try {
			checkPayment = operationsSoap.checkPayment(companyCode, serviceCode, account, special1, special2,
					transactionDate, transactionId, userId, userPassword, salePointType, url);
		} catch (Exception e) {
			e.printStackTrace();
			hashResponse.put("status", "failure");
			hashResponse.put("Result Message", "Merchant currently not available.");
			return hashResponse;
		}
		InputSource is = new InputSource(new StringReader(checkPayment));
		JAXBContext jaxbContext;
		jaxbContext = JAXBContext.newInstance(PPResponse.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		PPResponse response = (PPResponse) jaxbUnmarshaller.unmarshal(is);

		if (response.getResult().equals("000")) {
			hashResponse.put("status", "success");
			hashResponse.put("Key", "" + response.getKey());
			hashResponse.put("Result Message", "" + response.getResultMessage());
			hashResponse.put("Utility Code", "" + response.getUtilityInfo().getUtilityCode());
			hashResponse.put("Bill Number", "" + response.getBillInfo().getBill().getBillNumber());
			hashResponse.put("Reserve Info", "" + response.getBillInfo().getBill().getReserveInfo());
			hashResponse.put("RefStan", "" + response.getBillInfo().getBill().getRefStan());
			hashResponse.put("Commission Type",
					"" + response.getBillInfo().getBill().getBillParam().getCommission().getType());
			hashResponse.put("Commission Value",
					"" + response.getBillInfo().getBill().getBillParam().getCommission().getVal());

			// if(serviceCode==597){
			// hashResponse.put("RefStan" , ""+
			// response.getBillInfo().getBill().getRefStan());
			// }

		} else {
			hashResponse.put("status", "failure");
			hashResponse.put("Key", "" + response.getKey());
			hashResponse.put("Result Message", "" + response.getResultMessage());
			return hashResponse;
		}

		operationsSoap.printCheckPayment(checkPayment);

		HashMap<String, String> finalResponse = new HashMap<String, String>();
		if (response.getResult().equals("000")) {
			finalResponse = executePayment(myHash, hashResponse);
			if (finalResponse.get("status").equals("success")) {
				hashResponse.put("Result Message", finalResponse.get("Result Message"));
				if (companyCode == 587 || companyCode == 581 || companyCode == 580) {
					hashResponse.put("pinNo", finalResponse.get("pinNo"));
					hashResponse.put("serialNo", finalResponse.get("serialNo"));
				}
			}

			else if (finalResponse.get("status").equals("unknown")) {
				hashResponse.put("status", "unknown");
			} else {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message", "" + finalResponse.get("Result Message"));
			}
		}
		return hashResponse;
		// InputSource is = new InputSource(new StringReader(checkPayment));
		// JAXBContext jaxbContext;
		// jaxbContext = JAXBContext.newInstance(RequestQueryResponse.class);
		// Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		// RequestQueryResponse requestQueryResponse = (RequestQueryResponse)
		// jaxbUnmarshaller.unmarshal(is);
		//
		// return hashResponse;

	}

	@Override
	public HashMap<String, String> executePayment(HashMap<String, String> myHash,
			HashMap<String, String> checkPaymentResponse) throws JAXBException {
		// companyCode need to be changes for different Merchant
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String formattedDate = dateFormat.format(new Date());

		HashMap<String, String> hashResponse = new HashMap<>();

		int companyCode = Integer.parseInt(checkPaymentResponse.get("Utility Code"));
		int serviceCode = Integer.parseInt(myHash.get("serviceCode"));
		String account;
		if (companyCode == 59) {
			account = myHash.get("serviceTo");
		} else if (companyCode == 596) {
			account = myHash.get("account");
		} else if (companyCode == 598) {
			account = myHash.get("serviceTo");
		} else if (companyCode == 587) {

			account = "VoucherSCellTest";
		} else if (companyCode == 709) {
			account = "977" + myHash.get("serviceTo");
		} else
			account = myHash.get("account");

		String special1 = null;
		String special2 = null;
		long amt;
		if (companyCode == 598) {
			amt = (long) Double.parseDouble(myHash.get("amount"));
			special2 = myHash.get("customerId");
			special1 = myHash.get("payment");
		} else {
			amt = (long) Double.parseDouble(myHash.get("amount")) * 100;
		}
		if (companyCode == 585) {
			special1 = "" + amt;
		} else if (companyCode == 582) {
			special1 = "" + amt;
		} else if (companyCode == 78 || companyCode == 59) {
			special1 = null;
		} else if (companyCode == 596) {
			special1 = myHash.get("serviceTo");
		} else if (companyCode == 597 && !myHash.get("package").equals("nopackage")) {
			special1 = myHash.get("packageId");
		}

		if (companyCode == 581) {

			String comingAmt = myHash.get("amount");
			String actualAmt = comingAmt.substring(0, comingAmt.lastIndexOf("."));

			if (actualAmt.equals("500")) {
				if (myHash.get("internetorvoip").equals("internet")) {
					serviceCode = 1;
				} else {
					serviceCode = 6;
				}
			} else if (actualAmt.equals("1500")) {
				serviceCode = 2;
			} else if (actualAmt.equals("2000")) {
				serviceCode = 3;
			} else if (actualAmt.equals("2260")) {
				serviceCode = 4;
			} else if (actualAmt.equals("250")) {
				serviceCode = 5;
			} else {
				serviceCode = 10;
			}

			account = "VoucherBLTest";
		}

		if (companyCode == 580) {
			String comingAmt = myHash.get("amount");
			String actualAmt = comingAmt.substring(0, comingAmt.lastIndexOf("."));

			if (actualAmt.equals("300")) {
				serviceCode = 12;
			} else if (actualAmt.equals("400")) {
				serviceCode = 13;
			} else if (actualAmt.equals("500")) {
				serviceCode = 2;
			} else if (actualAmt.equals("1000")) {
				serviceCode = 3;
			} else if (actualAmt.equals("2000")) {
				serviceCode = 14;
			} else if (actualAmt.equals("3000")) {
				serviceCode = 15;
			} else if (actualAmt.equals("4000")) {
				serviceCode = 16;
			} else if (actualAmt.equals("5000")) {
				serviceCode = 17;
			} else if (actualAmt.equals("6000")) {
				serviceCode = 18;
			} else if (actualAmt.equals("7000")) {
				serviceCode = 19;
			} else {
				serviceCode = 9;
			}

			account = "VoucherDTHTest";
		}
		String tranactionDate = formattedDate;
		// need to provide transactionId from the transaction table
		long transactionId = Long.parseLong(myHash.get("transactionId"));
		long refStan = Long.parseLong(checkPaymentResponse.get("RefStan"));
		long amount = amt;
		String billNumber = checkPaymentResponse.get("Bill Number");
		String userId = myHash.get("username");
		String userPassword = myHash.get("password");
		String url = myHash.get("apiurl");
		int salePointType = 6;
		String executePayment = null;
		try {
			executePayment = operationsSoap.executePayment(companyCode, serviceCode, account, special1, special2,
					tranactionDate, transactionId, refStan, amount, billNumber, userId, userPassword, salePointType,
					url);

			operationsSoap.printExecutePayment(executePayment);
		} catch (Exception e) {
			e.printStackTrace();
			hashResponse.put("status", "unknown");
			return hashResponse;
		}
		InputSource is = new InputSource(new StringReader(executePayment));
		JAXBContext jaxbContext;
		jaxbContext = JAXBContext.newInstance(PPResponse.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		PPResponse response = (PPResponse) jaxbUnmarshaller.unmarshal(is);

		if (response.getResult().equals("000")) {
			hashResponse.put("status", "success");
			hashResponse.put("Key", "" + response.getKey());
			hashResponse.put("Result Message", "" + response.getResultMessage());
			if (companyCode == 587 || companyCode == 581 || companyCode == 580) {
				hashResponse.put("pinNo", response.getResultData().getVoucher().getPinNumber());
				hashResponse.put("serialNo", response.getResultData().getVoucher().getSerialNumber());
			}
		} else if (response.getResult().equals("001") || response.getResult().equals("002")
				|| response.getResult().equals("004") || response.getResult().equals("010")
				|| response.getResult().equals("011") || response.getResult().equals("012")
				|| response.getResult().equals("099") || response.getResult().equals("111")
				|| response.getResult().equals("112")) {
			hashResponse.put("status", "unknown");
		}

		else if (response.getResult().equals("003") || response.getResult().equals("006")
				|| response.getResult().equals("007") || response.getResult().equals("008")
				|| response.getResult().equals("009") || response.getResult().equals("014")
				|| response.getResult().equals("015") || response.getResult().equals("016")
				|| response.getResult().equals("017") || response.getResult().equals("020")
				|| response.getResult().equals("021") || response.getResult().equals("022")
				|| response.getResult().equals("023") || response.getResult().equals("025")
				|| response.getResult().equals("026") || response.getResult().equals("027")
				|| response.getResult().equals("028") || response.getResult().equals("029")
				|| response.getResult().equals("030") || response.getResult().equals("031")
				|| response.getResult().equals("032") || response.getResult().equals("033")
				|| response.getResult().equals("048") || response.getResult().equals("062")
				|| response.getResult().equals("063") || response.getResult().equals("064")
				|| response.getResult().equals("065") || response.getResult().equals("068")
				|| response.getResult().equals("100") || response.getResult().equals("101")
				|| response.getResult().equals("602")) {
			hashResponse.put("status", "failure");
			hashResponse.put("Key", "" + response.getKey());
			hashResponse.put("Result Message", "" + response.getResultMessage());
		} else {
			hashResponse.put("status", "unknown");
		}

		return hashResponse;

	}

	@Override
	public void executePaymentEx() {
		int companyCode = 78;
		int serviceCode = 0;
		String account = null;
		String special1 = null;
		String special2 = null;
		String transactionDate = null;
		long transactionId = 0;
		long refStan = 0;
		long amount = 0;
		String billNumber = null;
		String userId = "HaMroTec";
		String userPassword = "HM@T2|Ar";
		int salePointType = 6;
		int paySourceType = 0;
		int currencyCode = 0;

		String executePaymentEx = operationsSoap.executePaymentEx(companyCode, serviceCode, account, special1, special2,
				transactionDate, transactionId, refStan, amount, billNumber, userId, userPassword, salePointType,
				paySourceType, currencyCode);
		operationsSoap.printExecutePaymentEx(executePaymentEx);

	}

	@Override
	public void addCancelRequest() {
		int companyCode = 78;
		long refStan = 0;
		String transactionDate = null;
		long transactionId = 0;
		String userId = "HaMroTec";
		String userPassword = "HM@T2|Ar";
		int salePointType = 6;

		String addCancelRequest = operationsSoap.addCancelRequest(companyCode, refStan, transactionDate, transactionId,
				userId, userPassword, salePointType);
		operationsSoap.printAddCancelRequest(addCancelRequest);

	}

	@Override
	public void addCancelRequestEx() {
		int companyCode = 0;
		long refStan = 0;
		String transactionDate = null;
		long transactionId = 0;
		String userId = "HaMroTec";
		String userPassword = "HM@T2|Ar";
		int salePointType = 6;
		String note = null;

		String addCancelRequestEx = operationsSoap.addCancelRequestEx(companyCode, refStan, transactionDate,
				transactionId, userId, userPassword, salePointType, note);
		operationsSoap.printAddCancelRequestEx(addCancelRequestEx);

	}

	@Override
	public void getTransactionReport() {
		String userLogin = null;
		String userPassword = "HM@T2|Ar";
		String dealerName = null;
		String userName = null;
		String startDate = null;
		String endDate = null;
		long refStan = 0;
		int salePointType = 6;
		int status = 0;

		String getTransactionReport = operationsSoap.getTransactionReport(userLogin, userPassword, dealerName, userName,
				startDate, endDate, refStan, salePointType, status);
		operationsSoap.printTransactionReport(getTransactionReport);
	}

	@Override
	public void getTransactionReportSummary() {
		String userLogin = null;
		String userPassword = "HM@T2|Ar";
		String dealerName = null;
		String userName = null;
		String startDate = null;
		String endDate = null;
		long refStan = 0;
		int salePointType = 6;
		int status = 0;

		String getTransactionReportSummary = operationsSoap.getTransactionReportSummary(userLogin, userPassword,
				dealerName, userName, startDate, endDate, refStan, salePointType, status);
		operationsSoap.printTransactionReportSummary(getTransactionReportSummary);
	}

	@Override
	public HashMap<String, String> getTransaction(HashMap<String, String> myhash) throws JAXBException {
		String userLogin = myhash.get("username");
		String userPassword = myhash.get("password");
		String url = myhash.get("apiurl");
		long stan = -1;
		long refStan = Long.parseLong(myhash.get("refStan"));
		String key = "";
		String billNumber = myhash.get("billNumber");

		HashMap<String, String> hashResponse = new HashMap<>();

		String getTransaction = operationsSoap.getTransaction(userLogin, userPassword, stan, refStan, key, billNumber,
				url);
		operationsSoap.printTransaction(getTransaction);

		operationsSoap.printTransaction(getTransaction);

		InputSource is = new InputSource(new StringReader(getTransaction));
		JAXBContext jaxbContext;
		jaxbContext = JAXBContext.newInstance(TransactionResponse.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		TransactionResponse transactionResponse = (TransactionResponse) jaxbUnmarshaller.unmarshal(is);
		if (transactionResponse.getResult().equals("000")) {
			if (transactionResponse.getResultMessage().getTransaction() == null) {
				jaxbContext = JAXBContext.newInstance(PPResponse.class);
				Unmarshaller jaxbUnmarshaller1 = jaxbContext.createUnmarshaller();
				PPResponse response = (PPResponse) jaxbUnmarshaller1.unmarshal(is);

				if (response.getResultMessage().equalsIgnoreCase("No Data")) {
					hashResponse.put("status", "failure");
					// now reverse the transaction
				} else {
					hashResponse.put("status", "unknown");
					// transaction status is still unknown try again later
				}
			} else {
				String status = transactionResponse.getResultMessage().getTransaction().getStatus();

				if (status.equals("0") || status.equals("2") || status.equals("3") || status.equals("10")
						|| status.equals("12") || status.equals("20") || status.equals("21") || status.equals("15")) {
					hashResponse.put("status", "unknown");
					// transaction status is still unknown try again later
				} else if (status.equals("1") || status.equals("5") || status.equals("11") || status.equals("13")) {
					hashResponse.put("status", "success");
					// now forward the transaction to the merchant
				} else if (status.equals("4") || status.equals("6") || status.equals("14") || status.equals("16")) {
					hashResponse.put("status", "failure");
					// now reverse the transaction
				} else {
					hashResponse.put("status", "unknown");
				}

			}
		} else if (transactionResponse.getResult().equals("001") || transactionResponse.getResult().equals("002")
				|| transactionResponse.getResult().equals("003") || transactionResponse.getResult().equals("006")
				|| transactionResponse.getResult().equals("011") || transactionResponse.getResult().equals("012")
				|| transactionResponse.getResult().equals("014") || transactionResponse.getResult().equals("062")
				|| transactionResponse.getResult().equals("063") || transactionResponse.getResult().equals("064")
				|| transactionResponse.getResult().equals("065") || transactionResponse.getResult().equals("068")
				|| transactionResponse.getResult().equals("099") || transactionResponse.getResult().equals("100")) {
			hashResponse.put("status", "unknown");
			hashResponse.put("Key", "" + transactionResponse.getKey());
			hashResponse.put("Result Message", "" + transactionResponse.getResultMessage());
		} else {
			hashResponse.put("status", "unknown");
		}
		return hashResponse;

	}

	@Override
	public void getTransactionEx() {
		String userLogin = null;
		String userPassword = "HM@T2|Ar";
		long stan = -1;
		long refStan = 0;
		String key = null;
		String billNumber = null;

		String getTransactionEx = operationsSoap.getTransactionEx(userLogin, userPassword, stan, refStan, key,
				billNumber);
		operationsSoap.printTransactionEx(getTransactionEx);
	}

	@Override
	public void getTransactionByExternal() {
		String userLogin = null;
		String userPassword = "HM@T2|Ar";
		long stanEx = 0;
		String date = null;

		String getTransactionByExternal = operationsSoap.getTransactionByExternal(userLogin, userPassword, stanEx,
				date);
		operationsSoap.printTransactionByExternal(getTransactionByExternal);
	}

	@Override
	public void dailyReconciliation() {
		String userLogin = null;
		String userPassword = "HM@T2|Ar";
		int dealerId = 0;
		int companyId = 0;
		String day = null;
		long totalSum = 0;
		int totalCount = 0;

		String dailyReconciliation = operationsSoap.dailyReconciliation(userLogin, userPassword, dealerId, companyId,
				day, totalSum, totalCount);
		operationsSoap.printDailyReconciliation(dailyReconciliation);

	}

	@Override
	public void retransmit() {
		int companyCode = 78;
		String account = null;
		String transactionDate = null;
		long transactionId = 0;
		long refStan = 0;
		String userId = "HaMroTec";
		String userPassword = "HM@T2|Ar";
		int salePointType = 6;
		String note = null;

		String retransmit = operationsSoap.retransmit(companyCode, account, transactionDate, transactionId, refStan,
				userId, userPassword, salePointType, note);
		operationsSoap.printRetransmit(retransmit);

	}

	@Override
	public HashMap<String, String> executePayment(HashMap<String, String> myHash) throws JAXBException {
		// companyCode need to be changes for different Merchant
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String formattedDate = dateFormat.format(new Date());

		HashMap<String, String> hashResponse = new HashMap<>();

		int companyCode = Integer.parseInt(myHash.get("Utility Code"));
		int serviceCode = Integer.parseInt(myHash.get("serviceCode"));
		String account;
		if (companyCode == 59 || companyCode == 597 || companyCode == 598 || companyCode == 716) {
			account = myHash.get("serviceTo");
		} else if (companyCode == 709) {
			account = "977" + myHash.get("serviceTo");
		} else {
			account = myHash.get("account");
		}

		String special1 = null;
		long amt;

		amt = (long) Double.parseDouble(myHash.get("amount")) * 100;
		if (companyCode == 716) {
			Double amountDouble = Double.parseDouble(myHash.get("amount")) * 100;
			amt = amountDouble.longValue();
		}
		if (companyCode == 585) {
			special1 = "" + amt;
		} else if (companyCode == 78 || companyCode == 59) {
			special1 = null;
		} else if ((companyCode == 597 || companyCode == 716) && !myHash.get("package").equals("nopackage")) {
			special1 = myHash.get("packageId");
		}
		String special2 = null;
		if (companyCode == 598) {
			special2 = myHash.get("customerId");
			special1 = myHash.get("payment");
		}
		String tranactionDate = formattedDate;
		// need to provide transactionId from the transaction table
		long transactionId = Long.parseLong(myHash.get("transactionId"));
		long refStan = Long.parseLong(myHash.get("RefStan"));
		long amount = amt;
		String billNumber = myHash.get("Bill Number");
		String userId = myHash.get("username");
		String userPassword = myHash.get("password");
		String url = myHash.get("apiurl");
		int salePointType = 6;

		String executePayment = operationsSoap.executePayment(companyCode, serviceCode, account, special1, special2,
				tranactionDate, transactionId, refStan, amount, billNumber, userId, userPassword, salePointType, url);

		operationsSoap.printExecutePayment(executePayment);

		InputSource is = new InputSource(new StringReader(executePayment));
		JAXBContext jaxbContext;
		jaxbContext = JAXBContext.newInstance(PPResponse.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		PPResponse response = (PPResponse) jaxbUnmarshaller.unmarshal(is);

		if (response.getResult().equals("000")) {
			hashResponse.put("status", "success");
			hashResponse.put("Key", "" + response.getKey());
			hashResponse.put("Result Message", "" + response.getResultMessage());
			if(myHash.get("RefStan") != null || !(myHash.get("RefStan").trim().equals(""))){
				hashResponse.put("RefStan", myHash.get("RefStan"));
			}
			if (response.getResultData() != null) {
				if (response.getResultData().getDebtInfo() != null) {
					hashResponse.put("debt", response.getResultData().getDebtInfo().getDebt());
					hashResponse.put("unpaidBillCount", response.getResultData().getDebtInfo().getRest_count());
				}
				if (response.getResultData().getInvoiceInfo() != null) {
					if (response.getResultData().getInvoiceInfo().getHead() != null) {
						hashResponse.put("scno",
								response.getResultData().getInvoiceInfo().getHead().getAccount().getContent());
						hashResponse.put("serviceCharge",
								response.getResultData().getInvoiceInfo().getHead().getCommission().getContent());
					}
					if (response.getResultData().getInvoiceInfo().getBody() != null) {
						hashResponse.put("officeName", response.getResultData().getInvoiceInfo().getBody()
								.getNeaResponseI().getNeaResponseF()[0].getContent());
						hashResponse.put("customerId", response.getResultData().getInvoiceInfo().getBody()
								.getNeaResponseI().getNeaResponseF()[1].getContent());
						hashResponse.put("customerName", response.getResultData().getInvoiceInfo().getBody()
								.getNeaResponseI().getNeaResponseF()[2].getContent());
						hashResponse.put("dueBillDate", response.getResultData().getInvoiceInfo().getBody()
								.getNeaResponseI().getNeaResponseF()[3].getContent());
						hashResponse.put("billDate", response.getResultData().getInvoiceInfo().getBody()
								.getNeaResponseI().getNeaResponseF()[4].getContent());
						hashResponse.put("invoiceAmount", response.getResultData().getInvoiceInfo().getBody()
								.getNeaResponseI().getNeaResponseF()[5].getContent());
						hashResponse.put("invoiceStatus", response.getResultData().getInvoiceInfo().getBody()
								.getNeaResponseI().getNeaResponseF()[6].getContent());
						hashResponse.put("penaltyAmount", response.getResultData().getInvoiceInfo().getBody()
								.getNeaResponseI().getNeaResponseF()[7].getContent());
					}
				}
			}

		} else if (response.getResult().equals("001") || response.getResult().equals("002")
				|| response.getResult().equals("004") || response.getResult().equals("010")
				|| response.getResult().equals("011") || response.getResult().equals("012")
				|| response.getResult().equals("099") || response.getResult().equals("111")
				|| response.getResult().equals("112")) {
			hashResponse.put("status", "unknown");
		}

		else if (response.getResult().equals("003") || response.getResult().equals("006")
				|| response.getResult().equals("007") || response.getResult().equals("008")
				|| response.getResult().equals("009") || response.getResult().equals("014")
				|| response.getResult().equals("015") || response.getResult().equals("016")
				|| response.getResult().equals("017") || response.getResult().equals("020")
				|| response.getResult().equals("021") || response.getResult().equals("022")
				|| response.getResult().equals("023") || response.getResult().equals("025")
				|| response.getResult().equals("026") || response.getResult().equals("027")
				|| response.getResult().equals("028") || response.getResult().equals("029")
				|| response.getResult().equals("030") || response.getResult().equals("031")
				|| response.getResult().equals("032") || response.getResult().equals("033")
				|| response.getResult().equals("048") || response.getResult().equals("062")
				|| response.getResult().equals("063") || response.getResult().equals("064")
				|| response.getResult().equals("065") || response.getResult().equals("068")
				|| response.getResult().equals("100") || response.getResult().equals("101")
				|| response.getResult().equals("602")) {
			hashResponse.put("status", "failure");
			hashResponse.put("Key", "" + response.getKey());
			hashResponse.put("Result Message", "" + response.getResultMessage());
		} else {
			hashResponse.put("status", "unknown");
		}

		return hashResponse;

	}

}
