package org.interfaces;

import org.Subscriber;

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
		public boolean register(Subscriber subscriber);
		
		/**
		 * Unregister.
		 *
		 * @param subscriber the subscriber
		 * @return true, if successful
		 */
		public boolean unregister(Subscriber subscriber);
		
		/**
		 * Dispatch.
		 *
		 * @param topic the topic
		 * @param message the message
		 */
		public void dispatch(String topic, String message);
		
		/**
		 * Adds the topic.
		 *
		 * @param subscriber the subscriber
		 * @param topic the topic
		 * @return true, if successful
		 */
		boolean addTopic(Subscriber subscriber, String topic);
		
		/**
		 * Removes the topic.
		 *
		 * @param subscriber the subscriber
		 * @param topic the topic
		 * @return true, if successful
		 */
		boolean removeTopic(Subscriber subscriber, String topic);
}
