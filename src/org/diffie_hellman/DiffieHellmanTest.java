package org.diffie_hellman;

import java.math.BigInteger;
import java.util.Random;

public class DiffieHellmanTest{
 	
	public static void main(String arg[]){
		
	      //from http://en.wikipedia.org/wiki/Diffie-Hellman
	      BigInteger modulus = DiffieHellman.gen256bitRandomBigInt();
	      
	      //Client A
	      BigInteger a_private = BigInteger.valueOf(954844);
	      BigInteger a_public = DiffieHellman.BASE_5.generatePublicKey(a_private, modulus);        
	      
	      //Client B
	      BigInteger b_private = BigInteger.valueOf(115195);
	      BigInteger b_public = DiffieHellman.BASE_5.generatePublicKey(b_private, modulus);
	      
	      
	      
	      String a_shared_secret = DiffieHellman.getSharedSecretKey(a_private, modulus, b_public).toString();
	      
	      String b_shared_secret = DiffieHellman.getSharedSecretKey(b_private, modulus, a_public).toString();
	      
	      
	      System.out.println(a_shared_secret);
	      System.out.println(b_shared_secret);
	      
	      //assertEquals(a_shared_secret, b_shared_secret);
	     // assertEquals(2, a_shared_secret.intValue());
	}
	


}
