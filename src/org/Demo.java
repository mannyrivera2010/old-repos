package org;

import java.util.ArrayList;

import org.exceptions.SubscriptionException;
import org.listeners.MessageListenerI;

/**
 * The Class Demo.
 */
public class Demo {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws SubscriptionException the subscription exception
	 */
	public static void main(String[] args) throws SubscriptionException {
		//Making Main Message Bus
		MessageBus mainMessageBus = new MessageBus();
		
		//Creating subscriberA
		Subscriber subscriberA = new Subscriber();
		subscriberA.connect(mainMessageBus);
		subscriberA.subscribe("A");
		
		subscriberA.addMessageListener(new MessageListenerI(){
			@Override
			public void handleReceivedMessage(String message) {
				System.out.printf("SubscriberA - %s\n",message);
			}
		});
		

		//Creating subscriberB
		Subscriber subscriberB = new Subscriber();
		subscriberB.connect(mainMessageBus);
		subscriberB.subscribe("B");
		
		subscriberB.addMessageListener(new MessageListenerI(){
			@Override
			public void handleReceivedMessage(String message) {
				System.out.printf("SubscriberB - %s\n",message);
			}
		});
		
		//Creating publisher1
		Publisher publisher1 = new Publisher(mainMessageBus);
		publisher1.publish("A", "hello1");
		publisher1.publish("A", "hello2");
		publisher1.publish("B", "hello3");
		publisher1.publish("B", "hello4");
		subscriberB.unsubscribe("B");
		publisher1.publish("B", "hello5");
		
		
	}

}
