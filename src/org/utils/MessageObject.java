package org.utils;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MessageObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private String clientID;
	private String message;
	private String command = "";
	private boolean error = false;
	
	private InetAddress inetAddress;
	private int port;
	
	public MessageObject() {
		super();		
		
	}
	
	public MessageObject(String message) {
		super();
		this.message = message;
	}
	
	public MessageObject(String message, String command){
		this(message);
		this.setCommand(command);
	}	



	@Override
	public String toString() {
		return "MessageObject [clientID=" + clientID + ", message=" + message
				+ ", command=" + command + ", error=" + error
				+ ", inetAddress=" + inetAddress + ", port=" + port + "]";
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

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}
	
	

}
