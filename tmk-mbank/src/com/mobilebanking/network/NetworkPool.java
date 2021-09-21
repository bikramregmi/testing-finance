package com.mobilebanking.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkPool implements Runnable{
	 private int          serverPort   = 4444;
	    private ServerSocket serverSocket = null;
	    private boolean      isStopped    = false;
	    private Thread       runningThread= null;
	    private ExecutorService threadPool =
	            Executors.newFixedThreadPool(10);

	    public NetworkPool(int port, int poolSize){
	        this.serverPort = port;
	        this.threadPool = Executors.newFixedThreadPool(poolSize);
	    }

	    public void run(){
	        synchronized(this){
	            this.runningThread = Thread.currentThread();
	        }
	        openServerSocket();
	        while(! isStopped()){
	            Socket clientSocket = null;
	            try {
	                System.out.println(this.runningThread.getName()+" is accepting connection...") ;
	                clientSocket = this.serverSocket.accept();
	            } catch (IOException e) {
	                if(isStopped()) {
	                    System.out.println("Server Stopped.") ;
	                    break;
	                }
	                throw new RuntimeException(
	                        "Error accepting client connection", e);
	            }
	            this.threadPool.execute(
	                    new NodeServerRequestHandler(clientSocket
	                         ));
	        }
	        this.threadPool.shutdown();
	        System.out.println("Server Stopped.") ;
	    }


	    private synchronized boolean isStopped() {
	        return this.isStopped;
	    }

	    public synchronized void stop(){
	        this.isStopped = true;
	        try {
	            this.serverSocket.close();
	        } catch (IOException e) {
	            throw new RuntimeException("Error closing server", e);
	        }
	    }

	    private void openServerSocket() {
	        try {
	            this.serverSocket = new ServerSocket(this.serverPort);
	        } catch (IOException e) {
	            throw new RuntimeException("Cannot open port", e);
	        }
	    }
}
