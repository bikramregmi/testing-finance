package com.wallet.ofsServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wallet.ofs.MessageData;
import com.wallet.ofs.OfsMessage;
import com.wallet.ofs.OfsResponse;
import com.wallet.ofs.Options;
import com.wallet.ofs.UserInfo;
import com.wallet.ofs.service.OfsMessageService;

@Service
public class OfsServiceImpl implements OfsMessageService {


	public static void main(String [] args){
		/*OfsServiceImpl o = new OfsServiceImpl();
		OfsMessage ofsMessage = new OfsMessage();
		ofsMessage.getMessageData().setAT_UNIQUE_ID(System.currentTimeMillis()+"");
		ofsMessage.getMessageData().setCARD_NO("9849161691");
		ofsMessage.getMessageData().setATM_TIMESTAMP(System.currentTimeMillis()+"");
	
	 o.reverseTransaction(ofsMessage, "e465f6a", "62626", "6566");*/
		int otp = 055555;
		
		System.out.println("otp "+Integer.valueOf(otp));
		
		String message = "02610200F23AC48128E08010000000000000006010980201368901000000000005000002211144571370371144570221022102216011021001000000443081788888888888888888805211137037CVL00601               KTM--------------------- KTM--------- NP5240162018201820182018006332265012EJR5m0uMXnA=";
		Socket socket = null;
		PrintWriter out = null;
		BufferedReader in = null;

		try {
			socket = new Socket("10.0.4.106", 12066);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
		out.println(message);
		try {
			String response = readResponse1(in);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		out.close();
		try {
			in.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
		
	private static String readResponse1(BufferedReader br) throws IOException{
		char[] ca = new char[1024];
		  br.read(ca);
		  String response = new String(ca).trim();
		  System.out.println("response: " + response);
		  return response;
	}
		
	
	
	private  void message(OfsMessage ofsMessage) throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		
		Field [] fields = MessageData.class.getFields();
//		fields.getClass().
		
        for(Field field : fields) {
            Object value = field.get(this);

//            System.out.println(name + ": " + value.toString() + "\n");
        }
	}

	@Override
	public OfsResponse composeOfsMessage(OfsMessage ofsMessage,String host, String port) {
		
		 String ofsMessageFormat = "";
		/*FUNDS.TRANSFER,CCASHATM/I/PROCESS,SAGARP04/123123/NP0010002,,*/

		/*TRANSACTION.TYPE:1:1=ACCC,
				DEBIT.ACCT.NO:1:1=NPR1011000030002,
				DEBIT.CURRENCY:1:1=524,
				DEBIT.AMOUNT:1:1=1025,
				CREDIT.ACCT.NO:1:1=00210000023013,
				AT.UNIQUE.ID:1:1=0000010602130440,
				DEBIT.THEIR.REF:1:1=NAWA-5025,
				ATM.BIN.NO:1:1=989898,
				ATM.TERM.NO:1:1=HAMRO12,
				CARD.NO:1:1=9851049352,
				ATM.TIMESTAMP:1:1=0502140316,*/
		
		ofsMessageFormat += ofsMessage.getOperation().FUNDSTRANSFER.getValue()+",";
		
		String optionsData="";
		
		Options options = ofsMessage.getOptions();
		Gson gson = new GsonBuilder().create();
		String jsonuser = gson.toJson(options);// obj is your object 
		Map<String,Object> userresult = new Gson().fromJson(jsonuser, Map.class);
		boolean firstItteration = true;
		for(String key : userresult.keySet()){
			if(firstItteration){
				optionsData +=  userresult.get(key.toString());
				firstItteration = false;
			}else{
				optionsData +=  "/"+userresult.get(key.toString());
			}
		}
		
		ofsMessageFormat += optionsData+",";
		
		String userInfoData = "";
		UserInfo userInfo = ofsMessage.getUserInfo();
		 gson = new GsonBuilder().create();
		 jsonuser = gson.toJson(userInfo);// obj is your object 
		 userresult = new Gson().fromJson(jsonuser, Map.class);
		 firstItteration = true;
		for(String key : userresult.keySet()){
			if(firstItteration){
				userInfoData +=  userresult.get(key.toString());
				firstItteration = false;
			}else{
				userInfoData +=  "/"+userresult.get(key.toString());
			}
		}
		ofsMessageFormat += userInfoData+ ",";
		
		if(ofsMessage.getTransactionId()!=null ){
			ofsMessageFormat +=ofsMessage.getTransactionId()+",";
		}else
			ofsMessageFormat += ""+",";
		
		MessageData obj = ofsMessage.getMessageData();
//		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(obj);// obj is your object 
		Map<String,Object> result = new Gson().fromJson(json, Map.class);
		Object [] keyList = result.keySet().toArray();
		String messageData = "";
		for(Object key : keyList){
			result.put(key.toString().replace("_", "."), result.remove(key.toString()));
		}
		
		for(String key : result.keySet()){
			messageData += key+":1:1=" +result.get(key.toString())+",";
		}
		
		ofsMessageFormat += messageData;
		System.out.println(ofsMessageFormat);
		try {
			String response = sendMessage(ofsMessageFormat,host,port);
			OfsResponse ofsResponse = parseResponse(response);
			return ofsResponse;
//			System.out.println(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	
	@Override
	public OfsResponse reverseTransaction(OfsMessage ofsMessage, String transactionId,String host,String port) {
		// TODO Auto-generated method stub
		
//		FUNDS.TRANSFER,REV.WD/R/PROCESS,SAGARP04/123123/NP0010002,FT171971P3ZG,
		
//		FUNDS.TRANSFER,REV.WD/R/PROCESS,SAGARP04/123123/NP0010002,e465f6a,
		
		 String ofsMessageFormat = "";
			ofsMessageFormat += ofsMessage.getOperation().FUNDSTRANSFER.getValue()+",";
			
			String optionsData="";
			
			Options options = ofsMessage.getOptions();
			ofsMessage.getOptions().setFunction("R");
			ofsMessage.getOptions().setVersionName("REV.WD");
			Gson gson = new GsonBuilder().create();
			String jsonuser = gson.toJson(options);// obj is your object 
			Map<String,Object> userresult = new Gson().fromJson(jsonuser, Map.class);
			boolean firstItteration = true;
			for(String key : userresult.keySet()){
				if(firstItteration){
					optionsData +=  userresult.get(key.toString());
					firstItteration = false;
				}else{
					optionsData +=  "/"+userresult.get(key.toString());
				}
			}
			
			
			ofsMessageFormat += optionsData+",";
			
			String userInfoData = "";
			UserInfo userInfo = ofsMessage.getUserInfo();
			 gson = new GsonBuilder().create();
			 jsonuser = gson.toJson(userInfo);// obj is your object 
			 userresult = new Gson().fromJson(jsonuser, Map.class);
			 firstItteration = true;
			for(String key : userresult.keySet()){
				if(firstItteration){
					userInfoData +=  userresult.get(key.toString());
					firstItteration = false;
				}else{
					userInfoData +=  "/"+userresult.get(key.toString());
				}
			}
			ofsMessageFormat += userInfoData+ ",";
			
			ofsMessageFormat +=transactionId+",";
			String response = null;
			try {
				response = sendMessage(ofsMessageFormat,host,port);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			OfsResponse ofsResponse = parseResponse(response);
			return ofsResponse;
	}
	
	private String sendMessage(String message,String host,String port) throws UnknownHostException, IOException{
		Socket socket = null;
		PrintWriter out = null;
		BufferedReader in = null;

		try {
			socket = new Socket(host, Integer.parseInt(port));
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			return null;
		}
		readResponse(in);
		out.println(message);
		String response = readResponse(in);
		out.close();
		in.close();
		socket.close();
		
		return response;
    }
		
	private String readResponse(BufferedReader br) throws IOException{
		char[] ca = new char[1024];
		  br.read(ca);
		  String response = new String(ca).trim();
		  System.out.println("response: " + response);
		  return response;
	}
	
	private OfsResponse parseResponse(String ofsMessage){
//		ofsMessage = "FT17197L8QJK//1,TRANSACTION.TYPE:1:1=ACCC,DEBIT.ACCT.NO:1:1=NPR1011000030002,CURRENCY.MKT.DR:1:1=1,DEBIT.CURRENCY:1:1=NPR,DEBIT.AMOUNT:1:1=1025.00,DEBIT.VALUE.DATE:1:1=20170716,DEBIT.THEIR.REF:1:1=NAWA-5025,CREDIT.ACCT.NO:1:1=00210000023013,CURRENCY.MKT.CR:1:1=1,CREDIT.CURRENCY:1:1=NPR,CREDIT.VALUE.DATE:1:1=20170716,PROCESSING.DATE:1:1=20170716,ORDERING.CUST:1:1=CUSTOMER,CHARGE.COM.DISPLAY:1:1=NO,COMMISSION.CODE:1:1=WAIVE,CHARGE.CODE:1:1=WAIVE,RETURN.TO.DEPT:1:1=NO,FED.FUNDS:1:1=NO,POSITION.TYPE:1:1=TR,BAL.AFT.TXN:1:1=1001524D0001010260251002524D000101026025,AT.UNIQUE.ID:1:1=1512452690770,AT.AUTH.CODE:1:1=415440,ATM.TERM.NO:1:1=HAMRO12,CARD.NO:1:1=9849161691,ATM.BIN.NO:1:1=989898,ATM.TIMESTAMP:1:1=1512452690770,AMOUNT.DEBITED:1:1=NPR1025.00,AMOUNT.CREDITED:1:1=NPR1025.00,CREDIT.COMP.CODE:1:1=NP0010002,DEBIT.COMP.CODE:1:1=NP0010002,LOC.AMT.DEBITED:1:1=1025.00,LOC.AMT.CREDITED:1:1=1025.00,CREDIT.CUSTOMER:1:1=10000023,DR.ADVICE.REQD.Y.N:1:1=NO,CR.ADVICE.REQD.Y.N:1:1=NO,CHARGED.CUSTOMER:1:1=10000023,TOT.REC.COMM:";
//	 ofsMessage = "FT17197065GZ//-1/NO,POSITION.TYPE:1:1=ATM.ERROR  -  Duplicate Tranction 1512377191916";
	OfsResponse ofsResponse = new OfsResponse();
	String [] splitMessage = ofsMessage.split(",");
	
	String [] splitTransactionMessage = splitMessage[0].split("/");
	
	ofsResponse.setTransactionId(splitTransactionMessage[0]);
	ofsResponse.setMessageId(splitTransactionMessage[1]);
	ofsResponse.setResponseCode(Integer.parseInt(splitTransactionMessage[2]));
	
	if(ofsResponse.getResponseCode()<0){
		
		ofsResponse.setReturnedMessageData(splitMessage[1]);
		
	}else{
		StringBuilder strBuilder = new StringBuilder();
		for (int i = 1; i < splitMessage.length; i++) {
		   strBuilder.append(splitMessage[i]+",");
		}
		ofsResponse.setReturnedMessageData(strBuilder.toString());
	}
	
	return ofsResponse;
	
	}


}
