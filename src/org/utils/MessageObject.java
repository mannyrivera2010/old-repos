package org.utils;

import java.util.HashMap;

import com.google.gson.Gson;

// TODO: Auto-generated Javadoc
/**
 * The Class MessageObject.
 */
public class MessageObject {
	
	/** The data. */
	private HashMap<Object, Object> data = new HashMap<Object, Object>();
	
	/**
	 * Instantiates a new message object.
	 *
	 * @param message the message
	 */
	public MessageObject(String message) {
		super();
		this.setMessage(message);
	}
	
	/**
	 * Instantiates a new message object.
	 *
	 * @param message the message
	 * @param command the command
	 */
	public MessageObject(String message, String command){
		this(message);
		this.setCommand(command);
	}	

	/**
	 * Instantiates a new message object.
	 */
	public MessageObject() {
		super();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
	/**
	 * Gets the client id.
	 *
	 * @return the client id
	 */
	public String getClientID() {
		return (String) data.get("clientID");
	}

	/**
	 * Sets the client id.
	 *
	 * @param clientID the new client id
	 */
	public void setClientID(String clientID) {
		data.put("clientID", clientID);
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return (String) data.get("message");
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		data.put("message", message);
	}

	/**
	 * Gets the command.
	 *
	 * @return the command
	 */
	public String getCommand() {
		return (String) data.get("command");
	}

	/**
	 * Sets the command.
	 *
	 * @param command the new command
	 */
	public void setCommand(String command) {
		data.put("command", command);
	}

	/**
	 * Checks if is error.
	 *
	 * @return the boolean
	 */
	public Boolean isError() {
		return (Boolean) data.get("error");
	}

	/**
	 * Sets the error.
	 *
	 * @param error the new error
	 */
	public void setError(Boolean error) {
		data.put("error", error);
	}
	
	/**
	 * Sets the custom string.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public void setCustomString(String key, String value){
		data.put(key, value);
	}

	/**
	 * Gets the custom string.
	 *
	 * @param key the key
	 * @return the custom string
	 */
	public String getCustomString(String key){
		return (String) data.get(key);
	}

}
