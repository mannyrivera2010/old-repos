package org.interfaces;

import org.exceptions.SubscriptionException;
import org.listeners.MessageListenerI;

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
	public void subscribe(String topic) throws SubscriptionException;
	
	/**
	 * Unsubscribe.
	 *
	 * @param topic the topic
	 * @throws SubscriptionException the subscription exception
	 */
	public void unsubscribe(String topic) throws SubscriptionException;
	
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
	void callMessageListener(String message);
	
	/**
	 * Publish.
	 *
	 * @param topic the topic
	 * @param message the message
	 */
	public void publish(String topic, String message);

}
