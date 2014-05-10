package org;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.interfaces.ClientI;
import org.junit.Before;
import org.junit.Test;

public class TopicSubscriberMgrTest {

	private TopicSubscriberMgr topicSubscriberMgr;
	private Map<String, HashSet<ClientI>> getMapTopicToSubscribers;
	private Map<ClientI, HashSet<String>> getMapSubscriberToTopics;
	
	private Client client1;
	private Client client2;
	private Client client3;
	
	private HashSet<String> topics;
	private HashSet<ClientI> clients;
	
    @Before
    public void setUp() throws Exception{
    	this.topicSubscriberMgr = new TopicSubscriberMgr();
    	this.getMapSubscriberToTopics = this.topicSubscriberMgr.getMapSubscriberToTopics();
    	this.getMapTopicToSubscribers  = this.topicSubscriberMgr.getMapTopicToSubscribers();

    	this.client1 = new Client("Client1");
    	this.client2 = new Client("Client2");
    	this.client3 = new Client("Client3");
    	
    	this.topics = new HashSet<String>();
    	this.clients = new HashSet<ClientI>();
    }

	public void _addSubsciberAndTopic(ClientI client, String topic){
		this.getMapSubscriberToTopics.put(client, new HashSet<String>());
		this.getMapSubscriberToTopics.get(client).add(topic);
		
		this.getMapTopicToSubscribers.put("hello", this.clients);
		this.getMapTopicToSubscribers.get("hello").add(client);
	}
	
	@Test
	public void testAddTopicToSubscriberFalseCaseNoSubscir() {
		boolean actual = this.topicSubscriberMgr.addTopicToSubscriber("hello", this.client1);
		Assert.assertFalse(actual);
	}
	
	@Test
	public void testAddTopicToSubscriberTrueCaseNewTopic() {
		this.getMapSubscriberToTopics.put(this.client1, this.topics);
		boolean actual = this.topicSubscriberMgr.addTopicToSubscriber("hello", this.client1);
		Assert.assertTrue(actual);
		Assert.assertTrue(this.getMapTopicToSubscribers.containsKey("hello"));
		
		this.clients.add(client1);
		Assert.assertTrue(this.getMapTopicToSubscribers.get("hello").equals(this.clients));
		this.topics.add("hello");
		Assert.assertTrue(this.getMapSubscriberToTopics.get(this.client1).equals(this.topics));
	}
	
	@Test
	public void testAddTopicToSubscriberTrueCase() {
		this.getMapSubscriberToTopics.put(this.client1, this.topics);
		this.getMapTopicToSubscribers.put("hello", this.clients);
		
		boolean actual = this.topicSubscriberMgr.addTopicToSubscriber("hello", this.client1);
		
		Assert.assertTrue(actual);
		this.clients.add(client1);
		Assert.assertTrue(this.getMapTopicToSubscribers.get("hello").equals(this.clients));
		this.topics.add("hello");
		Assert.assertTrue(this.getMapSubscriberToTopics.get(this.client1).equals(this.topics));
	}
	
	@Test
	public void testRemoveTopicFromSubscriberTrue() {
		this.getMapSubscriberToTopics.put(this.client1, this.topics);
		this.getMapTopicToSubscribers.put("hello", this.clients);
		
		boolean actual = this.topicSubscriberMgr.removeTopicFromSubscriber("hello", this.client1);
		Assert.assertTrue(actual);
		
		Assert.assertTrue(this.getMapSubscriberToTopics.containsKey(this.client1));
		Assert.assertTrue(this.getMapTopicToSubscribers.containsKey("hello"));
		Assert.assertFalse(this.getMapSubscriberToTopics.get(this.client1).contains("hello"));
		Assert.assertFalse(this.getMapTopicToSubscribers.get("hello").contains(this.client1));
	}
	
	@Test
	public void testRemoveTopicFromSubscriberFalseCase() {
		boolean actual = this.topicSubscriberMgr.removeTopicFromSubscriber("hello", this.client1);
		Assert.assertFalse(actual);
	}

	@Test
	public void testCreateTopicTrueCase() {
		boolean actual = this.topicSubscriberMgr.createTopic("hello");
		Assert.assertTrue(actual);
		Assert.assertTrue(this.getMapTopicToSubscribers.containsKey("hello"));
	}

	@Test
	public void testCreateTopicFalseCase() {
		this.topicSubscriberMgr.createTopic("hello");
		boolean actual = this.topicSubscriberMgr.createTopic("hello");
		Assert.assertFalse(actual);
	}
	
	@Test
	public void testDeleteTopicTrueCaseNoSubscribers() {
		this.getMapTopicToSubscribers.put("hello", this.clients);
		boolean actual = this.topicSubscriberMgr.deleteTopic("hello");
		Assert.assertTrue(actual);
	}
	
	@Test
	public void testDeleteTopicTrueCaseSubscribers() {	
		ClientI client = this.client1;
		String topic = "hello";
		this._addSubsciberAndTopic(client, topic);
		
		Assert.assertTrue(this.getMapSubscriberToTopics.get(client).contains("hello"));
		boolean actual = this.topicSubscriberMgr.deleteTopic("hello");
		
		Assert.assertTrue(actual);
		Assert.assertTrue(this.getMapSubscriberToTopics.containsKey(client));
		Assert.assertFalse(this.getMapSubscriberToTopics.get(client).contains("hello"));		
	}
	
	@Test
	public void testDeleteTopicFalseCase() {
		boolean actual = this.topicSubscriberMgr.deleteTopic("hello");
		Assert.assertFalse(actual);
	}
	

	@Test
	public void testListTopicsEmpty() {
		List<String> actual = this.topicSubscriberMgr.listTopics();
		List<String> expected = new ArrayList<String>();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testListTopics() {
		this.getMapTopicToSubscribers.put("topic1", this.clients);
		List<String> actual = this.topicSubscriberMgr.listTopics();
		List<String> expected = new ArrayList<String>();
		expected.add("topic1");
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testListSubscibersEmtpy() {
		List<ClientI> actual = this.topicSubscriberMgr.listSubscribers();
		List<ClientI> expected = new ArrayList<ClientI>();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testListSubscibers() {
		this.topicSubscriberMgr.getMapSubscriberToTopics().put(this.client1, new HashSet<String>());
		List<ClientI> actual = this.topicSubscriberMgr.listSubscribers();
		List<ClientI> expected = new ArrayList<ClientI>();
		expected.add(this.client1);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testRegisterSubscriberTrueCase() {
		boolean actual = this.topicSubscriberMgr.registerSubscriber(this.client1);
		Assert.assertTrue(actual);
		Assert.assertTrue(this.getMapSubscriberToTopics.containsKey(this.client1));
	}

	@Test
	public void testRegisterSubscriberFalseCase(){
		this.topicSubscriberMgr.getMapSubscriberToTopics().put(this.client1, new HashSet<String>());
		boolean actual = this.topicSubscriberMgr.registerSubscriber(this.client1);
		Assert.assertFalse(actual);
		Assert.assertTrue(this.getMapSubscriberToTopics.containsKey(this.client1));
	}
	
	@Test
	public void testUnregisterSubscriberFalseCase() {
		boolean actual = this.topicSubscriberMgr.unregisterSubscriber(this.client1);
		Assert.assertFalse(actual);
	}
	
	@Test
	public void testUnregisterSubscriberTrueCaseNoTopics() {
		ClientI client = this.client1;
		this.getMapSubscriberToTopics.put(client, new HashSet<String>());
		
		boolean actual = this.topicSubscriberMgr.unregisterSubscriber(client);
		Assert.assertTrue(actual);
		System.out.println(this.topicSubscriberMgr);
	}
	
	@Test
	public void testUnregisterSubscriberTrueCaseTopics() {
		ClientI client = this.client1;
		String topic = "hello";
		this._addSubsciberAndTopic(client, topic);

		boolean actual = this.topicSubscriberMgr.unregisterSubscriber(client);

		Assert.assertTrue(actual);
		Assert.assertTrue(this.getMapTopicToSubscribers.containsKey("hello"));
		Assert.assertFalse(this.getMapTopicToSubscribers.get("hello").contains(client));
	}

	@Test
	public void testFilterSubscribersByTopic() {
		ClientI client = this.client1;
		String topic = "hello";
		this._addSubsciberAndTopic(client, topic);
		
		this.clients = this.topicSubscriberMgr.filterSubscribersByTopic("hello");
		
		HashSet<ClientI> expected = new HashSet<ClientI>();
		expected.add(this.client1);
		Assert.assertEquals(expected, this.clients);
	}

	@Test
	public void testFilterTopicsBySubscriber() {
		ClientI client = this.client1;
		String topic = "hello";
		this._addSubsciberAndTopic(client, topic);
		
		this.topics = this.topicSubscriberMgr.filterTopicsBySubscriber(this.client1);
		HashSet<String> expected = new HashSet<String>();
		expected.add("hello");

		Assert.assertEquals(expected, this.topics);
	}

}
