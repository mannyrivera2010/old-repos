package org.utils;

import java.io.Serializable;
import java.net.InetAddress;

public class MessageObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private String clientID;
	private String message;
	
	private InetAddress inetAddress;
	private int port;
	
	public MessageObject(String message) {
		super();
		this.message = message;
	}

	public MessageObject() {
		super();		
	}

	@Override
	public String toString() {
		return "MessageObject [Message=" + message + "]";
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public InetAddress getInetAddress() {
		return inetAddress;
	}

	public void setInetAddress(InetAddress inetAddress) {
		this.inetAddress = inetAddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	

}
