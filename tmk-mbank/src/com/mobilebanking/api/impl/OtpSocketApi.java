package com.mobilebanking.api.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.ICardlessOtpApi;
import com.mobilebanking.repositories.CardlessOtpRepository;

@Service
public class OtpSocketApi implements Runnable{

	private Socket clientSocket = null;
	
	@Autowired
	private static ICardlessOtpApi cardkessOtpApi;
	
	@Autowired
	private CardlessOtpRepository carlessOtpRepository;
	
	
	public OtpSocketApi(Socket clientSocket) {
   	 System.out.println("Waiting for a connection on port");
   	  this.clientSocket = clientSocket;
	}
	
	public OtpSocketApi(){
		
	}

	public void run() {
       System.out.println("in run");
//       process();
       try {
			newprocess();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       System.out.println("Request processed");
   }
	
	
	   private void newprocess() throws IOException{
		   try{
		   String isoRequest;
		/*    int cTosPortNumber = n;
		  

		    ServerSocket servSocket = new ServerSocket(cTosPortNumber);
		    System.out.println("Waiting for a connection on " + cTosPortNumber);
		 
		    Socket fromClientSocket = servSocket.accept();*/
		    PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);

		    BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		    while ((isoRequest = br.readLine()) != null) {
		      System.out.println("The message: " + isoRequest);
//		      CardlessOtpApi cardkessOtpApi = new CardlessOtpApi();
		    String isoResponse = cardkessOtpApi.unpackIsoOtpData(isoRequest);

		      if (isoRequest.equals("bye")) {
		        pw.println("bye");
		        break;
		      } else {
//		        str =  str;
		        pw.println(isoResponse);
		      }
		    }
		   }catch (Exception e) {
			   e.printStackTrace();
		}
		   /* pw.close();
		    br.close();

		    fromClientSocket.close();*/
		  }
	
}
