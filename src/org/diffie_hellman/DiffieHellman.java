package org.diffie_hellman;


//Copyright 2007-2008 David Yu dyuproject@gmail.com
//------------------------------------------------------------------------
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at 
//http://www.apache.org/licenses/LICENSE-2.0
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.


//package com.dyuproject.util;

import java.math.BigInteger;
import java.util.Random;

/**
* 
* Diffie-Hellman key exchange is a cryptographic protocol that allows two parties 
* that have no prior knowledge of each other to jointly establish a shared secret 
* key over an insecure communications channel.
* 
* @author David Yu
* @created Sep 7, 2008
*/

public final class DiffieHellman
{
  
  public static final DiffieHellman BASE_2 = new DiffieHellman(BigInteger.valueOf(2));
  public static final DiffieHellman BASE_5 = new DiffieHellman(BigInteger.valueOf(5));
  
  private static final long __loadTime = System.currentTimeMillis();
  
  private final BigInteger _base;
  
  public static BigInteger gen256bitRandomBigInt(){        
	  return new BigInteger(256, new Random());	        
  }
	
  public DiffieHellman(BigInteger base)
  {
      _base = base;
  }
  
  /**
   * Generates a random private Key (element 0) and a random public key (element 1) 
   * from the given {@code modulus}.
   * 
   * @param modulus
   * @return BigInteger array.  Element 0 is privateKey.  Element 1 is publicKey.
   */
  public BigInteger[] generateRandomKeys(BigInteger modulus)
  {
      BigInteger privateKey = BigInteger.valueOf(System.currentTimeMillis() + __loadTime);
      return new BigInteger[]{privateKey, generatePublicKey(privateKey, modulus)};
  }
  
  /**
   * Generates a public key from the given {@code privateKey} and {@code modulus}.
   */
  public BigInteger generatePublicKey(BigInteger privateKey, BigInteger modulus)
  {
      return _base.modPow(privateKey, modulus);
  }
  
  /**
   * Gets/computes the shared secret key from the given {@code privateKey}, 
   * {@code modulus} and {@code responseKey} - which is a public key.
   */
  public static BigInteger getSharedSecretKey(BigInteger privateKey, BigInteger modulus, 
          BigInteger responseKey)
  {
      return responseKey.modPow(privateKey, modulus);
  }

}
/*

//Copyright 2007-2008 David Yu dyuproject@gmail.com
//------------------------------------------------------------------------
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at 
//http://www.apache.org/licenses/LICENSE-2.0
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.


package com.dyuproject.util;

import java.math.BigInteger;

import junit.framework.TestCase;

//* @author David Yu
//* @created Sep 8, 2008

public class DiffieHellmanTest extends TestCase
{
  
  public void testSharedSecret()
  {
      //from http://en.wikipedia.org/wiki/Diffie-Hellman
      BigInteger modulus = BigInteger.valueOf(23);
      
      BigInteger a_private = BigInteger.valueOf(6);
      BigInteger a_public = DiffieHellman.BASE_5.generatePublicKey(a_private, modulus);        
      
      BigInteger b_private = BigInteger.valueOf(15);
      BigInteger b_public = DiffieHellman.BASE_5.generatePublicKey(b_private, modulus);
      
      BigInteger a_shared_secret = DiffieHellman.getSharedSecretKey(a_private, modulus, b_public);
      
      BigInteger b_shared_secret = DiffieHellman.getSharedSecretKey(b_private, modulus, a_public);
      
      assertEquals(a_shared_secret, b_shared_secret);
      assertEquals(2, a_shared_secret.intValue());
  }

}

*/