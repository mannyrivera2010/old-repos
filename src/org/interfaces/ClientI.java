package org.interfaces;

import org.utils.MessageObject;

/**
 * The Interface SubscriberI.
 */
public interface ClientI {
	
	/**
	 * Connect.
	 *
	 * @param messageBus the message bus
	 */
	public void connect(MessageBusI messageBus);
	
	/**
	 * Disconnect.
	 *
	 * @param messageBus the message bus
	 */
	public void disconnect(MessageBusI messageBus);
	
	/**
	 * Subscribe.
	 *
	 * @param topic the topic
	 * @throws SubscriptionException the subscription exception
	 */
	public void subscribe(String topic);
	
	/**
	 * Unsubscribe.
	 *
	 * @param topic the topic
	 * @throws SubscriptionException the subscription exception
	 */
	public void unsubscribe(String topic);
	
	/**
	 * Adds the message listener.
	 *
	 * @param messageListener the message listener
	 */
	void addMessageListener(MessageListenerI messageListener);
	
	/**
	 * Call message listener.
	 *
	 * @param message2 
	 */
	void callMessageListener(MessageObject message);
	
	/**
	 * Publish.
	 *
	 * @param topic the topic
	 * @param message the message
	 */
	public void publish(String topic, String message);

	
}
