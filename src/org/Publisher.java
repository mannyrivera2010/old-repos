package org;

import java.util.UUID;

import org.interfaces.MessageBusI;
import org.interfaces.PublisherI;


/**
 * The Class Publisher.
 */
public class Publisher implements PublisherI {

	/** The message bus. */
	private MessageBusI messageBus;
	
	/** The id. */
	private String id;
	
	/**
	 * Instantiates a new publisher.
	 */
	public Publisher(){
		this.id = UUID.randomUUID().toString();
	}

	/**
	 * Instantiates a new publisher.
	 *
	 * @param messageBus the message bus
	 */
	public Publisher(MessageBusI messageBus) {
		this();
		this.messageBus = messageBus;
	}

	/* (non-Javadoc)
	 * @see org.interfaces.PublisherI#publish(java.lang.String, java.lang.String)
	 */
	@Override
	public void publish(String topic, String message) {
		this.messageBus.dispatch(topic, message);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Publisher [id=" + id + "]";
	}
	
}
