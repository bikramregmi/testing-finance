package com.mobilebanking.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.springframework.stereotype.Service;

import com.mobilebanking.api.ICardlessOtpApi;

/**
 */
@Service
public class NodeServerRequestHandler extends Thread {

	private ICardlessOtpApi cardlessOtpApi = null;
	
    private Socket clientSocket = null;
    private PrintWriter pw;
    private   BufferedReader br;
   private  InputStream is;

  /*  public NodeServerRequestHandler(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText = serverText;
        
    }*/
    
    public NodeServerRequestHandler(){
    	
    }
    public NodeServerRequestHandler(Socket clientSocket,ICardlessOtpApi cardlessOtpApi) throws IOException {
    	 System.out.println("Waiting for a connection on port");
    	  this.clientSocket = clientSocket;
    	  this.cardlessOtpApi =cardlessOtpApi; 
    	  
    	  pw = new PrintWriter(clientSocket.getOutputStream(), true);

  	     br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  	    is  = clientSocket.getInputStream();
//  	    thread = new Thread(this);
//  	    thread.start();
	}

	public NodeServerRequestHandler(Socket clientSocket2) {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void run() {
        System.out.println("in run");
//        process();
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
	   byte[] buffer = new byte[4096];
	   int readMessage;
	    while (clientSocket.isConnected()) {
	    	readMessage = is.read(buffer);
	    	char cha = (char)readMessage;
	    	System.out.println(cha);
	    	isoRequest = new String(buffer,0,readMessage);
	      System.out.println("The message: " + isoRequest);
	      
	      if (isoRequest.equals("exit")) {
	        pw.println("Connection will close now.");
	        break;
	      } else {
	    	 String isoResponse = cardlessOtpApi.echo(isoRequest);
	        pw.println(isoResponse);
	        break;
	      }
	    }
	   
	   StringBuilder messageBuffer = new StringBuilder();
	// reads to the end of the stream or till end of message
	  
	   
	 /*  while(clientSocket.isConnected()){
		   readMessage = is.read();
		   char c = (char)readMessage;
			System.out.println(c);
		    // end?  jump out
//		    if (c == endMarker) {
//		        break;
//		    }s
		    // else, add to buffer
			if(readMessage<0){
				break;
			}
			messageBuffer.append(c);
			
			
			
			
			
			if((messageBuffer.toString()).length()>=5){
				System.out.println("length count greater");
				break;
			}
	   }*/
	   
	   
	  /* byte[] outString = new byte[4096];
	   String clientRequest="";
	   int count = clientSocket.getInputStream().read();
		System.out.println("connection test");
		for (int outputCount = 0; outputCount<count;outputCount++) {
			char response = (char) outString[outputCount];
			clientRequest = clientRequest + response;
			 messageBuffer.append(response);
		}*/
	   
	  
	// message is complete!
	String message = messageBuffer.toString();
	 System.out.println("message "+message);
	    pw.close();
	    br.close();
	    is.close();
	   }catch (Exception e) {
		   e.printStackTrace();
	}

//	    clientSocket.close();
	  }
	   


}
