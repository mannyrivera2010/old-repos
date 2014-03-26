package org.singlelinklist;

import java.lang.reflect.Array;


public class SinglyLinkedListTest {

	public static void main(String[] args) {
		SinglyLinkedList<String> SlObj= new SinglyLinkedList<String>();
		SlObj.addToEnd("2");
		SlObj.addToEnd("3");
		SlObj.addToEnd("4");
		SlObj.addToStart("1");
		SlObj.addToEnd("5");
		SlObj.addToStart("0");
		
		System.out.println("Length: "+SlObj.size());
		System.out.println(SlObj);
		System.out.println("Get(n):"+SlObj.get(1));
		
		SlObj.set(0, "00");
		SlObj.remove(5);
		SlObj.add(4, "E");
		
		
		
		System.out.println(SlObj);
		System.out.println("GetEnd:"+SlObj.getEnd());
		
		System.out.println(SlObj.subList(3, 5).toString());
		System.out.println(SlObj.toArray());

		
	}

}
