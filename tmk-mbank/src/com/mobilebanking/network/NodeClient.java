package com.mobilebanking.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

/**
 * Created by manohar-td-003 on 5/25/17.
 */
public class NodeClient {

    public String send(String message) {
        String clientRequest = "";
        String hostName = "10.0.4.59";
        Integer port = 445;
        System.out.println("In send: hostname=" + hostName + " port=" + port);
        try (Socket client = new Socket(hostName, port);
             DataInputStream input = new DataInputStream(client.getInputStream());
             DataOutputStream output = new DataOutputStream(client.getOutputStream())) {
            if (message != null) {
                String fromServer;
                String fromClient = message;
                output.writeUTF(fromClient);
                output.flush();
                System.out.println("First Client Message: " + message);
                fromServer = input.readUTF();
                while (!"End".equals(fromServer)) {
                    System.out.println("In client while");

                    System.out.println("Server Message: " + fromServer);
                    if (fromServer.equals("END")) {
                        System.out.println("In client while breaking");
                        break;
                    } else {
                        NodeProtocol nodeProtocol = new NodeProtocol();
                        fromClient = nodeProtocol.processServerMessage(fromServer);
                        System.out.println("In client while else fromClient=" + fromClient);
                        if (fromClient != null) {
                            System.out.println("In client while else if");
                            output.writeUTF(fromClient);
                            output.flush();
                        }
                    }
                    fromClient = "";
                    fromServer = input.readUTF();
                    System.out.println("In client while last fromServer=" + fromServer);
                }
            }
            input.close();
            output.close();
            client.close();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
        }

        return clientRequest;
    }

    public String sendMessage( String message) {
        String serverResponse = "";
        String hostName = "10.0.4.59";
        Integer port =445;
        System.out.println("In Client sendMessage: hostname=" + hostName + " port=" + port);
        /*try (Socket client = new Socket(hostName, port);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(client.getOutputStream());
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bufferedOutputStream)) {
            outputStreamWriter.write("Hello" + "\n");
            outputStreamWriter.flush();

            byte[] outString = new byte[4096];

            int count = client.getInputStream().read(outString, 0, 4096);
            System.out.println("connection test");
            for (int outputCount = 0; outputCount < count; outputCount++) {
                char response = (char) outString[outputCount];
                clientRequest = clientRequest + response;
            }
            System.out.println(clientRequest + " Client Request");
            client.close();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }*/

        try (Socket client = new Socket(hostName, port);
             InputStream input = client.getInputStream();
             OutputStream output = client.getOutputStream()) {
            System.out.println("Client Message=" + message);
            output.write(message.getBytes());
            output.flush();

            /*ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            // StandardCharsets.UTF_8.name() > JDK 7
            serverResponse =  result.toString(StandardCharsets.UTF_8.name());//result.toString("UTF-8");
            result.close();*/

            System.out.println(serverResponse + " Server Response");
            client.close();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }

        return serverResponse;
    }

    public String sendMessageTo( String message) {
        System.out.println("in sendMessageTo");
        String clientRequest = "";
        //String hostName = node.getParentIpAddress();
        String hostName = "10.0.4.59";
        Integer port =445;
        System.out.println("in sendMessageTo: hostname=" + hostName + " port=" + port);
        try (Socket client = new Socket(hostName, port);
             InputStream input = client.getInputStream();
             OutputStream output = client.getOutputStream()) {
            if (message != null) {
                String fromServer;
                String fromClient;
                ByteArrayOutputStream result = new ByteArrayOutputStream();
                output.write(message.getBytes());
                output.flush();
                System.out.println("Server Message: " + message);
                byte[] buffer = new byte[16 * 1024];
                int length;
                while (true) {
                    System.out.println("In client while");

                    length = 0;
                    while ((length = input.read(buffer)) != -1) {
                        System.out.println("In client while checking");
                        result.write(buffer, 0, length);
                    }
                    // StandardCharsets.UTF_8.name() > JDK 7
                    fromServer = result.toString(StandardCharsets.UTF_8.name());//result.toString("UTF-8");

                    System.out.println("Server Message: " + fromServer);
                    if (fromServer.equals("END"))
                        break;
                    else {
                        NodeProtocol nodeProtocol = new NodeProtocol();
                        fromClient = nodeProtocol.processServerMessage(fromServer);
                        if (fromClient != null) {
                            output.write(fromClient.getBytes());
                            output.flush();
                        }
                    }
                    fromClient = "";
                    fromServer = "";
                    result.reset();
                }
                result.close();
            }
            input.close();
            output.close();
            client.close();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }

        return clientRequest;
    }

}
