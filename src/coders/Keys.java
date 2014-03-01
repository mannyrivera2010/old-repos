package coders;

// TODO: Auto-generated Javadoc
/**
 * The Class Keys.
 */
public class Keys {

	/**
	 * Create16byte key_ m d5.
	 *
	 * @param StrInput the str input
	 * @return the byte[]
	 */
	public static byte[] Create16byteKey_MD5(String StrInput){
		hash.engine test=new hash.engine();
		String MD5_1=test.md5(StrInput+"Salt1");
		String MD5_2=test.md5(StrInput+MD5_1+"Salt1");
		byte[]IntArray=new byte[16];
		//String Oiu="";
		for(int i=0,j=0;i<=127;i=i+8,j++){
			//Oiu+=MD5_2.substring(i, i+8);
			IntArray[j]=(byte)(Integer.parseInt(MD5_2.substring(i, i+8), 2));
		}
        return IntArray;
	}
	
	

	/**
	 * Create32byte key_ sh a256.
	 *
	 * @param StrInput the str input
	 * @return the byte[]
	 */
	public static byte[] Create32byteKey_SHA256(String StrInput){
		hash.engine test=new hash.engine();
		String MD5_1=test.SHA256(StrInput+"Salt1");


		byte[]IntArray=new byte[32];
		//String Oiu="";
		for(int i=0,j=0;i<=255;i=i+8,j++){
			//Oiu+=MD5_2.substring(i, i+8);
			IntArray[j]=(byte)(Integer.parseInt(MD5_1.substring(i, i+8), 2));
		}
		
        return IntArray;
	}
	
}
