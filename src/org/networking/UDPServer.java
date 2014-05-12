package org.networking;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.interfaces.MessageListenerI;
import org.interfaces.UDPMessageListenerI;
import org.utils.MessageObject;
import org.utils.Serialization;

public class UDPServer extends Thread{

	/** The message listener. */
	private UDPMessageListenerI messageListener;
	
	/** The logger. */
	private Logger logger = Logger.getLogger("earasoft.UDPServer");
	
	private DatagramSocket sock = null;
	private int port = 7777;
	
	public UDPServer(){
		try {
			sock = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public HashMap<String,Object> recieveObject() throws IOException{
		// Buffer to receive incoming data
		byte[] buffer = new byte[65536];
		DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);

		sock.receive(incoming);
		
		byte[] data = incoming.getData();
		logger.log(Level.INFO, "Client Connected:" + incoming.getSocketAddress());
		HashMap<String,Object> Objs= new HashMap<String,Object>();
		Objs.put("MessageObject", (MessageObject) Serialization.deserializeAndDecompress(data));
		Objs.put("Connection", (DatagramPacket)incoming);
		return Objs;
	}

	public void sendObject(MessageObject msgObj, DatagramPacket outgoing) throws IOException{
		//Sending Data back to Client
		byte[] MsgObjData = Serialization.serializeAndCompress(msgObj);
		DatagramPacket dp = new DatagramPacket(MsgObjData,
				MsgObjData.length, outgoing.getAddress(),
				outgoing.getPort());
		sock.send(dp);
	}

	/**
	 * Multi-Threaded
	 * be able to receive incoming messages while it is processing a message
	 */
	@Override
	public void run(){
		try {
			startServer();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void startServer() throws InterruptedException{
			logger.log(Level.INFO, "Server socket created. Waiting for incoming data...");
			// Communication loop
			while (true) {
				try {
					//Getting Data from Client
					HashMap<String,Object> Objs = recieveObject();
					DatagramPacket incoming=(DatagramPacket) Objs.get("Connection");
					MessageObject msgObj = (MessageObject) Objs.get("MessageObject");
	
					logger.log(Level.INFO, (incoming.getAddress().getHostAddress() + " : "
							+ incoming.getPort() + " - \t ID:"+msgObj.getClientID()));
					
					if(this.messageListener != null){
						this.callMessageListener(incoming, msgObj);
					}else{
						this.handleMessage(incoming, msgObj);	
					}
				}catch (IOException e) {
					logger.log(Level.SEVERE, "IOException ", e);
				}catch(Exception e){
					logger.log(Level.WARNING, "Error Handling Message", e);
				}
			}// End While
	}

	public void handleMessage(DatagramPacket incoming, MessageObject msgObj){
		logger.log(Level.INFO, (incoming.getAddress().getHostAddress() + " : "
				+ incoming.getPort() + " - " + msgObj + "\t ID:"+msgObj.getClientID()));
		//sending Object
		try {
			sendObject(msgObj, incoming);
		} catch (IOException e) {
			logger.log(Level.WARNING,"Error Sending", e);
		}
	}

	public void addMessageListener(UDPMessageListenerI messageListener){
		this.messageListener = messageListener;
	}

	void callMessageListener(DatagramPacket datagramPacket, MessageObject message){
		this.messageListener.handleReceivedMessage(datagramPacket, message);	
	}

}
