package org.interfaces;

/**
 * The Interface PublisherI.
 */
public interface PublisherI {
	
	/**
	 * Publish.
	 *
	 * @param topic the topic
	 * @param message the message
	 */
	public void publish(String topic, String message);
}
