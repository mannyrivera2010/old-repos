package main;

import java.math.BigInteger;



// TODO: Auto-generated Javadoc
/**
 * The Class engine.
 */
public class engine {


	/**
	 * Create16byte key_ m d5.
	 *
	 * @param StrInput the str input
	 * @return the byte[]
	 */
	public static byte[] Create16byteKey_MD5(String StrInput){
		hash.engine test=new hash.engine();
		String MD5_1=test.md5(StrInput)+"Salt1";
		String MD5_2=test.md5(StrInput+MD5_1)+"Salt2";
		byte[]IntArray=new byte[16];
		//String Oiu="";
		for(int i=0,j=0;i<=127;i=i+8,j++){
			//Oiu+=MD5_2.substring(i, i+8);
			IntArray[j]=(byte)(Integer.parseInt(MD5_2.substring(i, i+8), 2));
		}
        return IntArray;
	}
	
	
	

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
	
		//System.out.print(intToByteArray(257).length);

		
		System.out.println(new String(Create16byteKey_MD5("dad")));
   
		
	

	}
	
	
	/**
	 * Part2.
	 */
	void part2(){
		
		Integer bi = Integer.parseInt("00000000", 2);

		System.out.println(bi);
		
		

        byte[] byteArray = new byte[] {87, 79, 87, 46, 46, 46,34,24,22,39,38,38,23,39,32,34};
        String value = new String(byteArray);
        
        System.out.println(byteArray.length);
	}
	

	
	


}
