package org.server;

import gnu.crypto.pad.WrongPaddingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.security.InvalidKeyException;

import org.diffie_hellman.DiffieHellman;
import org.shared.encryption.ciphers.AES128CBCPad_Compress;

public class ClientThread extends Thread {
	protected Socket clientSocket;
	private String StrClientID="-1";
	
	boolean blnGlobalEncryption=false;
	
    static BigInteger globalEncryption_modulus = null;
    static BigInteger globalEncryption_privateKey = null;
    static BigInteger globalEncryption_publicKey=null; 
    static BigInteger globalEncryption_ClientpublicKey=null; 
    static String globalEncryption_sharedSecret = null;
    
    
	ClientThread(Socket clientSoc, String ClientID) {
		StrClientID=ClientID;
		
		clientSocket = clientSoc;
		System.out.println("New Client ID:("+StrClientID+")Connected - ip:"	+ clientSocket.getInetAddress());
		start();
	}

	public void run() {
		
		//System.out.println ("New Client Connected");
		try {
			AES128CBCPad_Compress AesEn = new AES128CBCPad_Compress();
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));

			String inputLine;
			
			while ((inputLine = in.readLine()) != null) {
				
				
				String DecodedInputLine = org.shared.EncodeWeak.Hex2String(inputLine);
				
				
				if (DecodedInputLine.toLowerCase().equals("*quit*")){
					break;
				}//end if
					

				boolean Exit = false;

				if (DecodedInputLine.toLowerCase().equals("*terminate*")) {
					DecodedInputLine = "server|Terminated server";
					Exit = true;
				}//end if

				String EndOutput = "";
				String aInput = DecodedInputLine;

				int intDiv = aInput.indexOf("|");
				String aCommand = aInput.substring(0, intDiv);
				String aString = aInput.substring(intDiv + 1, aInput.length());

				System.out.println ("Client ID:("+StrClientID+")"+" Received Server Command (Encrypted="+blnGlobalEncryption+"): " + aCommand);
				
				
				if(blnGlobalEncryption==true&&!aCommand.equals("SET")){
					aString=AesEn.AES_decrypt(aString, globalEncryption_sharedSecret);
				}

				if (aCommand.equalsIgnoreCase("UC")) {// "ALL UPPER CASE"
					EndOutput = aString.toUpperCase();
				} else if (aCommand.equalsIgnoreCase("LC")) {// "ALL LOWER CASE"
					EndOutput = aString.toLowerCase();
				} else if (aCommand.equalsIgnoreCase("SH")) {// "STRING TO HEX"
					EndOutput = org.shared.EncodeWeak.String2Hex(aString);
				} else if (aCommand.equalsIgnoreCase("HS")) {// "HEX TO STRING"
					EndOutput = org.shared.EncodeWeak.Hex2String(aString);
				} else if (aCommand.equalsIgnoreCase("SA")) {// "STRING TO ASCII"
					EndOutput = org.shared.EncodeWeak.String2Ascii(aString);
				} else if (aCommand.equalsIgnoreCase("AS")) {// "ASCII TO STRING"
					EndOutput = org.shared.EncodeWeak.Ascii2String(aString);
				} else if (aCommand.equalsIgnoreCase("NO CHANGE")) {// "NO CHANGE"
					EndOutput = aString;
					
				} else if (aCommand.equalsIgnoreCase("SET")) {// SETUP Encryption
					blnGlobalEncryption=true;
					
					//System.out.println(aString);
					
					String tempModulus=aString.substring(0,aString.indexOf(","));
					String tempClientPublicKey=aString.substring(aString.indexOf(",")+1);
					
					
					globalEncryption_modulus = new BigInteger(tempModulus);
					globalEncryption_privateKey = DiffieHellman.gen256bitRandomBigInt();
					globalEncryption_publicKey = DiffieHellman.BASE_5.generatePublicKey(globalEncryption_privateKey, globalEncryption_modulus);
					
					globalEncryption_ClientpublicKey=new BigInteger(tempClientPublicKey);
					//Send server modulus and Client public key to get Server Public Key
					globalEncryption_sharedSecret=DiffieHellman.getSharedSecretKey(globalEncryption_privateKey, globalEncryption_modulus, globalEncryption_ClientpublicKey).toString();
					
					//System.err.print(globalEncryption_sharedSecret);
					
					
					EndOutput = globalEncryption_publicKey.toString();//Send Client Server Public Key
					
				} else {
					EndOutput = aString;
				}//end if

				if(blnGlobalEncryption==true&&!aCommand.equals("SET")){
					EndOutput=AesEn.AES_encrypt(EndOutput, globalEncryption_sharedSecret);
				}
				
				
				out.println(org.shared.EncodeWeak.String2Hex(EndOutput));

				if (Exit == true) {
					System.exit(0);
				}//end if
			}

			out.close();
			in.close();
			clientSocket.close();
		} catch (IOException | InvalidKeyException | WrongPaddingException e) {
			System.err
					.println("Problem with Communication Server - Client Left");
		}
	}//end sub

}
