package org.utils;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

public class MessageObjectTest {

	@Test
	public void testMessageObjectString() {
		MessageObject msgObj1 = new MessageObject("message");
		String expected = "{\"data\":{\"message\":\"message\"}}";
		Assert.assertEquals(expected, msgObj1.toString());
	}

	@Test
	public void testMessageObjectMessageCommand() {
		MessageObject msgObj1 = new MessageObject("message", "command");
		String expected = "{\"data\":{\"message\":\"message\",\"command\":\"command\"}}";
		Assert.assertEquals(expected, msgObj1.toString());
	}

	@Test
	public void testDemo() {
		MessageObject msgObj1 = new MessageObject();
		msgObj1.setMessage("message");
		msgObj1.setError(true);
		
		String strMsgObj1 = msgObj1.toString();
		
		Assert.assertEquals("{\"data\":{\"message\":\"message\",\"error\":true}}",
				strMsgObj1);
		
		
		Gson gson = new Gson();
		MessageObject msgObjF = gson.fromJson(msgObj1.toString(), MessageObject.class);
		
		Assert.assertEquals(strMsgObj1.toString(), msgObjF.toString());
		
	}
}
