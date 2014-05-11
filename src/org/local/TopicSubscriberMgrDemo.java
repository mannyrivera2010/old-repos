package org.local;

import org.interfaces.ClientI;
import org.interfaces.TopicSubscriberMgrI;

public class TopicSubscriberMgrDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientI client1 = new Client("client1");
		ClientI client2 = new Client("client2");
		
		TopicSubscriberMgrI mgr = new TopicSubscriberMgr();
		
		mgr.createTopic("topic1");
		mgr.createTopic("topic2");
		System.out.printf("Topics: %s\n",mgr.listTopics());
		
		mgr.registerSubscriber(client1);
		mgr.registerSubscriber(client2);
		System.out.println(mgr.listSubscribers());
		
		System.out.printf("mgr: %s\n", mgr);
		
		try {
			mgr.addTopicToSubscriber("topic1", client1);
			mgr.addTopicToSubscriber("topic3", client1);
			mgr.addTopicToSubscriber("topic3", client2);
			System.out.printf("Get Topics for Subscriber: %s\n",mgr.filterSubscribersByTopic("topic1"));
			System.out.printf("Get Subscriber for Topics: %s\n",mgr.filterTopicsBySubscriber(client1));
			System.out.printf("mgr: %s\n", mgr);
			mgr.deleteTopic("topic1");
			System.out.printf("Get Topics for Subscriber: %s\n",mgr.filterSubscribersByTopic("topic1"));
			System.out.printf("Get Subscriber for Topics: %s\n",mgr.filterTopicsBySubscriber(client1));
			System.out.printf("mgr: %s\n", mgr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.printf("Topics: %s\n",mgr.listTopics());
	}

}
