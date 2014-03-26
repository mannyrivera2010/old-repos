package org.stack;

public class QueueTest {


	public static void main(String[] args) {
		Queue<String> QueueObj= new Queue<String>();
		
		QueueObj.push("1");
		QueueObj.push("2");
		QueueObj.push("3");
				
		System.out.println(QueueObj);
		System.out.println(QueueObj.pop());
		System.out.println(QueueObj);
		System.out.println(QueueObj.pop());
		

	}

}
