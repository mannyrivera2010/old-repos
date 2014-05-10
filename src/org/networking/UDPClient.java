package org.networking;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.digest.DigestUtils;
import org.interfaces.MessageListenerI;
import org.utils.MessageObject;
import org.utils.Serialization;

public class UDPClient implements Runnable {
	
	/** The logger. */
	private Logger logger = Logger.getLogger("earasoft.UDPServer");
	/** The message listener. */
	private MessageListenerI messageListener;
	
	private DatagramSocket sock = null;
	private String ClientID;
	private InetAddress host;
	private int port;
	
	public UDPClient(){
		this.ClientID = UUID.randomUUID().toString();
		try {
			this.host = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.port = 7777;
	}
	
	public UDPClient(String ipAddress , int port) throws UnknownHostException{
		this();
		this.host = InetAddress.getByName("localhost");
		this.port = port;
	}

	@Override
	public void run(){
		startListening();
	}
	
	public void startListening(){
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

	void sendMsg(MessageObject clientMsgObj) throws IOException {
		clientMsgObj.setClientID(this.ClientID);
		byte[] MsgObjDataSending = Serialization.serializeAndCompress(clientMsgObj);
		DatagramPacket dp = new DatagramPacket(MsgObjDataSending,MsgObjDataSending.length, host, port);
		sock.send(dp);
	}

	private void readMsg(){
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

				logger.log(Level.FINE, reply.getAddress().getHostAddress() + " : "
						+ reply.getPort() + " - " + msgObj);
				
				if(this.messageListener != null){
					this.callMessageListener(msgObj);
				}
			}catch (Exception e) {
				retry=true;
				maxtries++;

				try {
					sock.setSoTimeout(sock.getSoTimeout()*2);
					logger.log(Level.WARNING, "Retry #" + maxtries + " : " + e +"  Timeout = " + sock.getSoTimeout());
					
					if(maxtries>=3){
						retry=false;
						sock.setSoTimeout(5000);
					}
				} catch (SocketException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
	}

	public void addMessageListener(MessageListenerI messageListener){
		this.messageListener = messageListener;
	}
	
	void callMessageListener(MessageObject message){
		this.messageListener.handleReceivedMessage(message);	
	}
	
}
