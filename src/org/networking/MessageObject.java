package org.networking;

import java.io.Serializable;

public class MessageObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private String ClientID;
	private String OneLineMessage;
	
	public MessageObject(Integer intgerSequence,
			String oneLineMessage) {
		super();
		OneLineMessage = oneLineMessage;
	}

	public MessageObject() {
		super();		
	}

	public String getOneLineMessage() {
		return OneLineMessage;
	}

	public void setOneLineMessage(String oneLineMessage) {
		OneLineMessage = oneLineMessage;
	}

	@Override
	public String toString() {
		return "MessageObject [OneLineMessage=" + OneLineMessage + "]";
	}

	public String getClientID() {
		return ClientID;
	}

	public void setClientID(String clientID) {
		ClientID = clientID;
	}
	

}
