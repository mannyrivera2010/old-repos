package org.utils;

import java.util.HashMap;

import com.google.gson.Gson;

public class MessageObject {
	private HashMap<Object, Object> data = new HashMap<Object, Object>();
	
	public MessageObject(String message) {
		super();
		this.setMessage(message);
	}
	
	public MessageObject(String message, String command){
		this(message);
		this.setCommand(command);
	}	

	public MessageObject() {
		super();
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
	public String getClientID() {
		return (String) data.get("clientID");
	}

	public void setClientID(String clientID) {
		data.put("clientID", clientID);
	}

	public String getMessage() {
		return (String) data.get("message");
	}

	public void setMessage(String message) {
		data.put("message", message);
	}

	public String getCommand() {
		return (String) data.get("command");
	}

	public void setCommand(String command) {
		data.put("command", command);
	}

	public Boolean isError() {
		return (Boolean) data.get("error");
	}

	public void setError(Boolean error) {
		data.put("error", error);
	}
	
	public void setCustomString(String key, String value){
		data.put(key, value);
	}

	public String getCustomString(String key){
		return (String) data.get(key);
	}

}
