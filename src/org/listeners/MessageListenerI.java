package org.listeners;

/**
 * The Interface MessageListenerI.
 */
public interface MessageListenerI {
	
	/**
	 * Handle received JSON message
	 *
	 * @param message the message
	 */
	public void handleReceivedMessage(String message);
}
