package org.listeners;

/**
 * The Interface MessageListenerI.
 */
public interface MessageListenerI {
	
	/**
	 * Handle received message
	 *
	 * @param message the message
	 */
	public void handleReceivedMessage(String message);
}
