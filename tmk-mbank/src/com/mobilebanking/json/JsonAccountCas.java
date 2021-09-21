package com.mobilebanking.json;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonAccountCas {

	public String createAccount(String query,String json) throws IOException, JSONException{
		
		/*
		 String query = "http://localhost:8080/API/Account/Create";
         String json = "{\"country\":\"NPL\"}";*/
		
		URL url = new URL(query);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");

        OutputStream os = conn.getOutputStream();
        os.write(json.getBytes("UTF-8"));
        os.close();

        // read the response
        InputStream in = new BufferedInputStream(conn.getInputStream());
        String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
        JSONObject jsonObject = new JSONObject(result);
        System.out.println("printing resoutls ----");
        	
       String accountNo="";
       String balance="";
        Iterator<?> keys = jsonObject.keys();
        while(keys.hasNext() ) {
            String key = (String)keys.next();
            if ( jsonObject.get(key) instanceof JSONObject ) {
                JSONObject xx = new JSONObject(jsonObject.get(key).toString());
                System.out.println("res1 "+xx.getString("accountNo"));
                accountNo=xx.getString("accountNo");
                balance=xx.getString("balance");
            }
        }
        
        

        in.close();
        conn.disconnect();
		
		
	return accountNo;	
	}
	
	public String checkBalance(String query,String json) throws IOException, JSONException{
		
		 /*
		 String query = "http://localhost:8080/API/Account/Balance";
	     String json = "{\"accountNo\":\"TM1468413196880\"}";
	     */
	     URL url = new URL(query);
	     HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	     conn.setConnectTimeout(5000);
	     conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	     conn.setDoOutput(true);
	     conn.setDoInput(true);
	     conn.setRequestMethod("POST");

	     OutputStream os = conn.getOutputStream();
	     os.write(json.getBytes("UTF-8"));
	     os.close();

	     // read the response
	     InputStream in = new BufferedInputStream(conn.getInputStream());
	     String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
	     //System.out.println("result---"+result);
	     JSONObject jsonObject = new JSONObject(result);
	     
	     //System.out.println("printing resoutls ----"+jsonObject);
	     	
	    String accountNo="";
	    String balance="";
	     Iterator<?> keys = jsonObject.keys();
	     //System.out.println("keys.hasNext()="+keys.hasNext());
	     while(keys.hasNext() ) {
	         String key = (String)keys.next();
	         //System.out.println("sonObject.get(key)"+jsonObject.get(key));
	         if ( jsonObject.get(key) instanceof JSONObject ) {
	             JSONObject xx = new JSONObject(jsonObject.get(key).toString());
	             System.out.println("accountNo "+xx.getString("accountNo"));
	             System.out.println("balance =--- "+xx.getString("balance"));
	             accountNo=xx.getString("accountNo");
	             balance=xx.getString("balance");
	         }else{
	        	 System.out.println("---here----");
	         }
	     }
	     
	     

	     in.close();
	     conn.disconnect();
		return balance;
		}
		
		
		public String createTransactionCAS(String query,String json)  throws IOException, JSONException{
			
			 URL url = new URL(query);
		     HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		     conn.setConnectTimeout(5000);
		     conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		     conn.setDoOutput(true);
		     conn.setDoInput(true);
		     conn.setRequestMethod("POST");
		     System.out.println("conncetion---"+conn);
		     OutputStream os = conn.getOutputStream();
		     os.write(json.getBytes("UTF-8"));
		     os.close();
			
		     InputStream in = new BufferedInputStream(conn.getInputStream());
		     String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
		     
		     System.out.println("result---"+result);
		     JSONObject jsonObject = new JSONObject(result);
		     
		     String accountNo="";
	         String balance="";
	          Iterator<?> keys = jsonObject.keys();
	          while(keys.hasNext() ) {
	              String key = (String)keys.next();
	              if ( jsonObject.get(key) instanceof JSONObject ) {
	                  JSONObject xx = new JSONObject(jsonObject.get(key).toString());
	                //  System.out.println("res1 "+xx.getString("accountNo"));
	                //  accountNo=xx.getString("accountNo");
	               //   balance=xx.getString("balance");
	              }
	          }
			
	          return null;
		}
	
	
}
