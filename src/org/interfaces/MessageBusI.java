package org.interfaces;

import org.Client;

/**
 * The Interface MessageBusI.
 */
public interface MessageBusI {
		
		/**
		 * Register.
		 *
		 * @param subscriber the subscriber
		 * @return true, if successful
		 */
		public boolean register(ClientI subscriber);
		
		/**
		 * Unregister.
		 *
		 * @param subscriber the subscriber
		 * @return true, if successful
		 */
		public boolean unregister(ClientI subscriber);
		
		/**
		 * Dispatch.
		 *
		 * @param topic the topic
		 * @param message the message
		 */
		public void dispatch(String topic, String message, String ClientID);
		
		/**
		 * Adds the topic.
		 *
		 * @param subscriber the subscriber
		 * @param topic the topic
		 * @return true, if successful
		 */
		boolean addTopic(ClientI subscriber, String topic);
		
		/**
		 * Removes the topic.
		 *
		 * @param subscriber the subscriber
		 * @param topic the topic
		 * @return true, if successful
		 */
		boolean removeTopic(ClientI subscriber, String topic);
}
