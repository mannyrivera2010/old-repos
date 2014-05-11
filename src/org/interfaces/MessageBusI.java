package org.interfaces;

import java.util.HashSet;
import java.util.List;
import java.util.Map;


// TODO: Auto-generated Javadoc
/**
 * The Interface TopicSubscriberMgrI.
 */
public interface MessageBusI {

	/**
	 * Adds the topic to subscriber.
	 *
	 * @param topic the topic
	 * @param client the client
	 * @return true, if successful
	 */
	public abstract boolean addTopicToSubscriber(String topic, ClientI client);

	/**
	 * Removes the topic from subscriber.
	 *
	 * @param topic the topic
	 * @param client the client
	 * @return true, if successful
	 */
	public abstract boolean removeTopicFromSubscriber(String topic,
			ClientI client);

	/**
	 * Creates the topic.
	 *
	 * @param topic the topic
	 * @return true, if successful
	 */
	public abstract boolean createTopic(String topic);

	/**
	 * Delete topic.
	 *
	 * @param topic the topic
	 * @return true, if successful
	 */
	public abstract boolean deleteTopic(String topic);

	/**
	 * Gets the map topic to subscribers.
	 *
	 * @return the map topic to subscribers
	 */
	public abstract Map<String, HashSet<ClientI>> getMapTopicToSubscribers();

	/**
	 * Gets the map subscriber to topics.
	 *
	 * @return the map subscriber to topics
	 */
	public abstract Map<ClientI, HashSet<String>> getMapSubscriberToTopics();

	/**
	 * List topics.
	 *
	 * @return the list
	 */
	public abstract List<String> listTopics();

	/**
	 * List subscribers.
	 *
	 * @return the list
	 */
	public abstract List<ClientI> listSubscribers();

	/**
	 * Register subscriber.
	 *
	 * @param client the client
	 * @return true, if successful
	 */
	public abstract boolean registerSubscriber(ClientI client);

	/**
	 * Unregister subscriber.
	 *
	 * @param client the client
	 * @return true, if successful
	 */
	public abstract boolean unregisterSubscriber(ClientI client);

	/**
	 * Filter subscribers by topic.
	 *
	 * @param topic the topic
	 * @return the hash set
	 */
	public abstract HashSet<ClientI> filterSubscribersByTopic(String topic);

	/**
	 * Filter topics by subscriber.
	 *
	 * @param client the client
	 * @return the hash set
	 */
	public abstract HashSet<String> filterTopicsBySubscriber(ClientI client);

	/**
	 * Dispatch.
	 *
	 * @param topic the topic
	 * @param message the message
	 */
	void dispatch(String topic, String message);

}