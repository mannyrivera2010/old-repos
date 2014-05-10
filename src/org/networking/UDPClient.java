package org.networking;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

public class UDPClient implements Runnable {

	public static void main(String args[]) throws IOException {
		UDPClient UDPClientObj= new UDPClient();
		
		Thread t1 = new Thread(UDPClientObj);
		t1.start();
		
		UDPClientObj.sendMsg();
		UDPClientObj.sendMsg();
		System.out.println("d");
		
	}
	
	private DatagramSocket sock = null;
	private String ClientID;
	private int port = 7777;
	
	public UDPClient(){
		this.ClientID = UUID.randomUUID().toString();
	}

	@Override
	public void run(){
		StartClient();
	}
	
	public void StartClient(){
		try {
			sock = new DatagramSocket();
			sock.setSoTimeout(5000);   // set the timeout in millisecounds.

			
			while (true) {
				//sendMsg();
				readMsg();
			}
		}catch (IOException e) {
			System.err.println("IOException " + e);
		}
	}//end sum

	private void sendMsg() throws IOException {
		InetAddress host = InetAddress.getByName("localhost");
		BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
		String s;
		MessageObject ClientMsgObj= new MessageObject();
		//Pre-Requirement 
		ClientMsgObj.setClientID(ClientID);
		
		// take input and send the packet
		echo("Enter message to send : ");
		s = (String) cin.readLine();
		
		ClientMsgObj.setOneLineMessage(s);
		
		byte[] MsgObjDataSending = Serialization.serializeAndCompress(ClientMsgObj);
		DatagramPacket dp = new DatagramPacket(MsgObjDataSending,MsgObjDataSending.length, host, port);
		sock.send(dp);
	}

	private void readMsg() throws SocketException {
		/////////////////////////////////////////////////////////////////////////
		// now receive reply
		// buffer to receive incoming data
		int maxtries=0;
		boolean retry = true;
		while(retry==true){
			retry=false;
			try {
				byte[] buffer = new byte[65536];
				DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
				sock.receive(reply);

				byte[] data = reply.getData();
				MessageObject msgObj = (MessageObject) Serialization.deserializeAndDecompress(data);

				// echo the details of incoming data - client ip : client port -
				// client message
				echo(reply.getAddress().getHostAddress() + " : "
						+ reply.getPort() + " - " + msgObj);
				
			}catch (Exception e) {
				retry=true;
				maxtries++;

				sock.setSoTimeout(sock.getSoTimeout()*2);
				System.err.println("Retry #"+ maxtries + "   " + e+"  Timeout="+sock.getSoTimeout());
				
				if(maxtries>=3){
					retry=false;
					sock.setSoTimeout(5000);
				}
			}
		}
	}
	
	// simple function to echo data to terminal
	public void echo(String msg) {
		System.out.println(msg);
	}
}
