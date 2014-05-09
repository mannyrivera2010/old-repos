package org;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashSet;

import org.interfaces.MessageBusI;
import org.interfaces.ClientI;

import com.google.gson.Gson;

/**
 * The Class MessageBus.
 */
public class MessageBus implements MessageBusI {

	/** The logger. */
	private Logger logger = Logger.getLogger("earasoft.MessageBus");
	
	
	private TopicSubscriberMgr topicSubscriberMgr;
	/**
	 * Instantiates a new message bus.
	 */
	public MessageBus(){
		this.logger.setUseParentHandlers(true);
		topicSubscriberMgr = new TopicSubscriberMgr();
	}

	/* (non-Javadoc)
	 * @see org.interfaces.MessageBusI#dispatch(java.lang.String, java.lang.String)
	 */
	@Override
	public void dispatch(String topic, String message, String ClientID) {
		Set<ClientI> clientsWithTopic = topicSubscriberMgr.filterSubscribersByTopic(topic);
		
		for(ClientI client : clientsWithTopic){
			try{
				client.callMessageListener(message);
			}catch(Exception e){
				logger.log(Level.WARNING,
						String.format("Could not excute callback for (%s)",client),e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.interfaces.MessageBusI#register(org.client)
	 */
	@Override
	public boolean register(ClientI client) {
		return topicSubscriberMgr.registerSubsciber(client);
	}

	/* (non-Javadoc)
	 * @see org.interfaces.MessageBusI#unregister(org.client)
	 */
	@Override
	public boolean unregister(ClientI client) {
		return topicSubscriberMgr.unregisterSubscriber(client);
	}

	/* (non-Javadoc)
	 * @see org.interfaces.MessageBusI#addTopic(org.client, java.lang.String)
	 */
	@Override
	public boolean addTopic(ClientI client, String topic) {
		return topicSubscriberMgr.addTopicToSubscriber(topic, client);
	}

	/* (non-Javadoc)
	 * @see org.interfaces.MessageBusI#removeTopic(org.client, java.lang.String)
	 */
	@Override
	public boolean removeTopic(ClientI client, String topic) {
		return topicSubscriberMgr.removeTopicFromSubscriber(topic, client);
	}

}
