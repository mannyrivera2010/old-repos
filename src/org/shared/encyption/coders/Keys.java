package org.shared.encyption.coders;

import org.shared.encryption.JackSum;

public class Keys {
	

	public static byte[] Create16byteKey_MD5(String StrInput){
		JackSum jackSumObj = new JackSum();
		String MD5_1=jackSumObj.md5(StrInput+"Salt1");
		String MD5_2=jackSumObj.md5(StrInput+MD5_1+"Salt1");
		
		byte[]IntArray=new byte[16];
		//String Oiu="";
		for(int i=0,j=0;i<=127;i=i+8,j++){
			//Oiu+=MD5_2.substring(i, i+8);
			IntArray[j]=(byte)(Integer.parseInt(MD5_2.substring(i, i+8), 2));
		}
        return IntArray;
	}
	
	
}
