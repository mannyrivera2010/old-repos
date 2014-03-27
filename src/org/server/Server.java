package org.server;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import org.shared.encryption.JackSum;

public class Server{
	protected static boolean serverContinue = true;

	final static int SocketNumber = 12011;
	static int intClientID = 0;

	public static void main(String[] args) throws IOException {
		
		List<Thread> threads = new ArrayList<Thread>();
		
		
		JackSum JackSumObj= new JackSum();
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(SocketNumber);
			System.out.println("Connection Socket Created");
			try {
				while (serverContinue) {
					serverSocket.setSoTimeout(10000);
					// System.out.println ("Waiting for Connection");
					try {
						intClientID++;
						
						if(intClientID>=10000000){
							intClientID=1;
						}						
						
						threads.add(new ClientThread(serverSocket.accept(), intClientID+ "-"+JackSumObj.adler32Date_HEX()));
						
						
					} catch (SocketTimeoutException ste) {
						// System.out.println ("Timeout Occurred");
					}
				}
			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + SocketNumber
					+ ".");
			System.exit(1);
		} finally {
			try {
				System.out.println("Closing Server Connection Socket");
				serverSocket.close();
			} catch (IOException e) {
				System.err.println("Could not close port: " + SocketNumber
						+ ".");
				System.exit(1);
			}
		}
	}

}
