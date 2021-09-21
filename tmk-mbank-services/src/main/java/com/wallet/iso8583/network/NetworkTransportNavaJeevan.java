/**
 * 
 */
package com.wallet.iso8583.network;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.springframework.stereotype.Component;

/**
 * @author bibek
 *
 */
@Component
public class NetworkTransportNavaJeevan {

	public String transportIsoMessage(String serverUrl, int port, String isoMessage)
			throws UnknownHostException, IOException {
		String clientRequest = "";
		try {
			Socket connection = new Socket(serverUrl, port);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(connection.getOutputStream());
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bufferedOutputStream);
			outputStreamWriter.write(isoMessage + "\n");
			outputStreamWriter.flush();

			byte[] outString = new byte[4096];

			int count = connection.getInputStream().read(outString, 0, 4096);
			for (int outputCount = 0; outputCount < count; outputCount++) {
				char response = (char) outString[outputCount];
				clientRequest = clientRequest + response;
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			return "failure";
		}

		return clientRequest;
	}
	
	public String transportEchoIsoMessage(String serverUrl, int port, String isoMessage)
			throws UnknownHostException, IOException {
		String clientRequest = "";
		try {
			Socket connection = new Socket();
			connection.connect(new InetSocketAddress(serverUrl, port), 30 * 1000);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(connection.getOutputStream());
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bufferedOutputStream);
			outputStreamWriter.write(isoMessage + "\n");
			outputStreamWriter.flush();

			byte[] outString = new byte[4096];

			int count = connection.getInputStream().read(outString, 0, 4096);
			for (int outputCount = 0; outputCount < count; outputCount++) {
				char response = (char) outString[outputCount];
				clientRequest = clientRequest + response;
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			return "failure";
		}

		return clientRequest;
	}

}
