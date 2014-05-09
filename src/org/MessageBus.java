package org;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashSet;

import org.interfaces.MessageBusI;
import org.interfaces.ClientI;

/**
 * The Class MessageBus.
 */
public class MessageBus implements MessageBusI {

	/** The logger. */
	private Logger logger = Logger.getLogger("earasoft.MessageBus");
	
	/** The clients topics. */
	private Map<ClientI, HashSet<String>> clientsTopics =
			new HashMap<ClientI,HashSet<String>>();
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
	public void dispatch(String topic, String message, String ClientID) {

		 for(Map.Entry<ClientI, HashSet<String>> entry : this.clientsTopics.entrySet()) {
			 ClientI client = entry.getKey();
			 HashSet<String> topics = entry.getValue();

			 try{
				 //boolean ClientIsSender = ClientID.equals(client.toString());
				 if(topics.contains(topic)) //&& !ClientIsSender
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
	public boolean register(Client client) {
		if(clientsTopics.containsKey(client)){
			return false;
		}else{
			clientsTopics.put(client, new HashSet<String>());
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see org.interfaces.MessageBusI#unregister(org.client)
	 */
	@Override
	public boolean unregister(Client client) {
		if(this.clientsTopics.containsKey(client)){
			this.clientsTopics.remove(client);
			return true;
		}else{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see org.interfaces.MessageBusI#addTopic(org.client, java.lang.String)
	 */
	@Override
	public boolean addTopic(Client client, String topic) {
		if(clientsTopics.containsKey(client)){
			HashSet<String> topics = clientsTopics.get(client);
			
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
	 * @see org.interfaces.MessageBusI#removeTopic(org.client, java.lang.String)
	 */
	@Override
	public boolean removeTopic(Client client, String topic) {
		if(clientsTopics.containsKey(client)){
			HashSet<String> topics = clientsTopics.get(client);
			
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
