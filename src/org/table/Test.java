package org.table;

import javolution.util.FastMap;
import javolution.util.FastSet;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		FastMap<Integer,String[]> Table= new FastMap<Integer,String[]>();
		Table.put(1,new String[]{"1-1","1-2"});
		Table.put(2,new String[]{"2-1","2-2"});
		Table.put(3,new String[]{"3-1","3-2"});
		
		System.out.println(Table.getEntry(1).getValue());
		System.out.println(Table.containsKey(2));
		
		////////////////////
		
		

		
	}

}
