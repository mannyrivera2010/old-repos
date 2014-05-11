package org.local;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.interfaces.ClientI;
import org.interfaces.MessageBusI;
import org.utils.MessageObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class MessageBus implements MessageBusI {
	private Map<String, HashSet<ClientI>> mapTopicToSubscribers = new TreeMap<String, HashSet<ClientI>>();
	private Map<ClientI, HashSet<String>> mapSubscriberToTopics = new HashMap<ClientI, HashSet<String>>();
	/** The logger. */
	private Logger logger = Logger.getLogger("earasoft.MessageBus");
	
	/* (non-Javadoc)
	 * @see org.TopicSubscriberMgrI#addTopicToSubscriber(java.lang.String, org.interfaces.ClientI)
	 */
	@Override
	public boolean addTopicToSubscriber(String topic, ClientI client){
		if(this.mapTopicToSubscribers.containsKey(topic) && this.mapSubscriberToTopics.containsKey(client)){
			this.mapSubscriberToTopics.get(client).add(topic);
			this.mapTopicToSubscribers.get(topic).add(client);
			return true;
		}else{
			if(!this.mapSubscriberToTopics.containsKey(client))
				return false;
			// Topic does not exist
			this.createTopic(topic);
			this.mapSubscriberToTopics.get(client).add(topic);
			this.mapTopicToSubscribers.get(topic).add(client);
			return true;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.TopicSubscriberMgrI#removeTopicFromSubscriber(java.lang.String, org.interfaces.ClientI)
	 */
	@Override
	public boolean removeTopicFromSubscriber(String topic, ClientI client){
		if(this.mapTopicToSubscribers.containsKey(topic) && this.mapSubscriberToTopics.containsKey(client)){
			this.mapTopicToSubscribers.get(topic).remove(client);
			this.mapSubscriberToTopics.get(client).remove(topic);
			return true;
		}else{
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.TopicSubscriberMgrI#createTopic(java.lang.String)
	 */
	@Override
	public boolean createTopic(String topic){
		if(this.mapTopicToSubscribers.containsKey(topic)){
			return false;
		}else{
			this.mapTopicToSubscribers.put(topic, new HashSet<ClientI>());
			return true;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.TopicSubscriberMgrI#deleteTopic(java.lang.String)
	 */
	@Override
	public boolean deleteTopic(String topic){
		if(this.mapTopicToSubscribers.containsKey(topic)){
			//Remove Topic from Subscribers that has it
			for(ClientI client : this.mapTopicToSubscribers.get(topic)){
				this.mapSubscriberToTopics.get(client).remove(topic);
			}
			
			this.mapTopicToSubscribers.remove(topic);
			return true;
		}else{
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.TopicSubscriberMgrI#getMapTopicToSubscribers()
	 */
	@Override
	public Map<String, HashSet<ClientI>> getMapTopicToSubscribers() {
		return mapTopicToSubscribers;
	}

	/* (non-Javadoc)
	 * @see org.TopicSubscriberMgrI#getMapSubscriberToTopics()
	 */
	@Override
	public Map<ClientI, HashSet<String>> getMapSubscriberToTopics() {
		return mapSubscriberToTopics;
	}

	/* (non-Javadoc)
	 * @see org.TopicSubscriberMgrI#listTopics()
	 */
	@Override
	public List<String> listTopics(){
		return new ArrayList<String>(this.mapTopicToSubscribers.keySet());
	}
	
	/* (non-Javadoc)
	 * @see org.TopicSubscriberMgrI#listSubscribers()
	 */
	@Override
	public List<ClientI> listSubscribers(){
		return new ArrayList<ClientI>(this.mapSubscriberToTopics.keySet());
	}
	
	/* (non-Javadoc)
	 * @see org.TopicSubscriberMgrI#registerSubscriber(org.interfaces.ClientI)
	 */
	@Override
	public boolean registerSubscriber(ClientI client){
		if(this.mapSubscriberToTopics.containsKey(client)){
			return false;
		}else{
			this.mapSubscriberToTopics.put(client, new HashSet<String>());
			return true;
		}	
	}
	
	/* (non-Javadoc)
	 * @see org.TopicSubscriberMgrI#unregisterSubscriber(org.interfaces.ClientI)
	 */
	@Override
	public boolean unregisterSubscriber(ClientI client){
		if(this.mapSubscriberToTopics.containsKey(client)){
			// Remove Subscribers that contain 
			for(String topic : this.mapSubscriberToTopics.get(client)){
				this.mapTopicToSubscribers.get(topic).remove(client);
			}
			
			this.mapSubscriberToTopics.remove(client);
			return true;
		}else{
			return false;
		}	
	}
	
	/* (non-Javadoc)
	 * @see org.TopicSubscriberMgrI#filterSubscribersByTopic(java.lang.String)
	 */
	@Override
	public HashSet<ClientI> filterSubscribersByTopic(String topic){
		if(this.mapTopicToSubscribers.containsKey(topic)){
			return this.mapTopicToSubscribers.get(topic);
		}else{
			return new HashSet<ClientI>();
			//throw new Exception("Topic does not exist");
		}
	}
	
	/* (non-Javadoc)
	 * @see org.TopicSubscriberMgrI#filterTopicsBySubscriber(org.interfaces.ClientI)
	 */
	@Override
	public HashSet<String> filterTopicsBySubscriber(ClientI client){
		if(this.mapSubscriberToTopics.containsKey(client)){
			return this.mapSubscriberToTopics.get(client);
		}else{
			return new HashSet<String>();
			//throw new Exception("Subscriber is not registered");
		}	
	}

	
	@Override
	public void dispatch(String topic, String message) {
		Set<ClientI> clientsWithTopic = this.filterSubscribersByTopic(topic);
		
		for(ClientI client : clientsWithTopic){
			try{
				client.callMessageListener(new MessageObject(message));
			}catch(Exception e){
				logger.log(Level.WARNING,
						String.format("Could not excute callback for (%s)",client),e);
			}
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((mapSubscriberToTopics == null) ? 0 : mapSubscriberToTopics
						.hashCode());
		result = prime
				* result
				+ ((mapTopicToSubscribers == null) ? 0 : mapTopicToSubscribers
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageBus other = (MessageBus) obj;
		if (mapSubscriberToTopics == null) {
			if (other.mapSubscriberToTopics != null)
				return false;
		} else if (!mapSubscriberToTopics.equals(other.mapSubscriberToTopics))
			return false;
		if (mapTopicToSubscribers == null) {
			if (other.mapTopicToSubscribers != null)
				return false;
		} else if (!mapTopicToSubscribers.equals(other.mapTopicToSubscribers))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TopicSubscriberMgr [mapTopicToSubscribers="
				+ mapTopicToSubscribers + ", mapSubscriberToTopics="
				+ mapSubscriberToTopics + "]";
	}
}
