package main;

import java.math.BigInteger;
import java.net.InetAddress;
import java.util.ArrayList;

public class BigInte {

	/**
	 * @param args
	 */
	int num1=0;
	static int num=0;
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
	
		
		//size=3
		//A=0
		//B=1
		//C=2
		//AA=3
		//AB=4
		//...
		//Input 3 --> 00
		
		
		
		ArrayList<String> Chars= new ArrayList<String>();
		Chars.add("A");
		Chars.add("B");
		Chars.add("C");
		
		System.out.println(3-3);
		
		
		
		for(int i=0;i<10;i++){
			int input=i;
			
			while(input>=0){
				System.out.print(Chars.get((input)%Chars.size())+"("+input+")"+",");
				
				if(input>Chars.size()){
					input=input-(Chars.size()+1);
				}else{
					input=input-(Chars.size());
				}
				
			}
			System.out.println();
		}
	
		
		
	}

	
	

	
	
}
