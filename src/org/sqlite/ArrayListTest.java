package org.sqlite;

import java.util.ArrayList;

public class ArrayListTest {

	public static void main(String[] args) {
		ArrayList<String> testa= new ArrayList<String>();
		
		boolean test=true;
		while(test){
			 
       	 for(int i=1;i<=100000;i++){
       		testa.add("4");
       		testa.add("4");
       		testa.add("4");
       		testa.add("4");
       		testa.add("4");
       		
       	 }
       	
        
  	   
	             System.out.println("Number of Records"+testa.size());
	         
        }
		
		
	}
}
