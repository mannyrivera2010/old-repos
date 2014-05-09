package org;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashSet;

import org.interfaces.MessageBusI;
import org.interfaces.SubscriberI;

/**
 * The Class MessageBus.
 */
public class MessageBus implements MessageBusI {

	/** The logger. */
	private Logger logger = Logger.getLogger("earasoft.MessageBus");
	
	/** The subscribers topics. */
	private Map<SubscriberI, HashSet<String>> subscribersTopics =
			new HashMap<SubscriberI,HashSet<String>>();
	/**
	 * Instantiates a new message bus.
	 */
	public MessageBus(){
		this.logger.setUseParentHandlers(true);
	}

	/* (non-Javadoc)
	 * @see org.interfaces.MessageBusI#dispatch(java.lang.String, java.lang.String)
	 */
	@Override
	public void dispatch(String topic, String message) {
		 for(Map.Entry<SubscriberI, HashSet<String>> entry : this.subscribersTopics.entrySet()) {
			 SubscriberI subscriber = entry.getKey();
			 HashSet<String> topics = entry.getValue();

			 try{
				 if(topics.contains(topic))
					 subscriber.callMessageListener(message);
			 }catch(Exception e){
				logger.log(Level.WARNING,
						String.format("Could not excute callback for (%s)",subscriber),e);
			 }
		 }
	}

	/* (non-Javadoc)
	 * @see org.interfaces.MessageBusI#register(org.Subscriber)
	 */
	@Override
	public boolean register(Subscriber subscriber) {
		if(subscribersTopics.containsKey(subscriber)){
			return false;
		}else{
			subscribersTopics.put(subscriber, new HashSet<String>());
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see org.interfaces.MessageBusI#unregister(org.Subscriber)
	 */
	@Override
	public boolean unregister(Subscriber subscriber) {
		if(this.subscribersTopics.containsKey(subscriber)){
			this.subscribersTopics.remove(subscriber);
			return true;
		}else{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see org.interfaces.MessageBusI#addTopic(org.Subscriber, java.lang.String)
	 */
	@Override
	public boolean addTopic(Subscriber subscriber, String topic) {
		if(subscribersTopics.containsKey(subscriber)){
			HashSet<String> topics = subscribersTopics.get(subscriber);
			
			if(topics.contains(topic)){
				return false;
			}else{
				topics.add(topic);
				return true;
			}
		}else{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see org.interfaces.MessageBusI#removeTopic(org.Subscriber, java.lang.String)
	 */
	@Override
	public boolean removeTopic(Subscriber subscriber, String topic) {
		if(subscribersTopics.containsKey(subscriber)){
			HashSet<String> topics = subscribersTopics.get(subscriber);
			
			if(topics.contains(topic)){
				topics.remove(topic);
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

}
