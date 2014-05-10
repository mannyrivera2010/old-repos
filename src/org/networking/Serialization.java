package org.networking;

import java.io.Serializable;



import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class Serialization {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Intial Message
		MessageObject ClientMsgObj= new MessageObject(1,"One line Message");
		byte[] dataCompress=serializeAndCompress(ClientMsgObj);
		
		//deserialize:
		MessageObject ServerMsgObj = (MessageObject) deserializeAndDecompress(dataCompress);
		
		System.out.println(ServerMsgObj);
	}
	
	
	public static Object deserializeAndDecompress(String HexCode){
		byte[] dataDecompress = null;
		try {
			dataDecompress = Compress.DecompressDeflater(Hex.decodeHex(HexCode.toCharArray()));
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SerializationUtils.deserialize(dataDecompress);
	}
	
	public static Object deserializeAndDecompress(byte[] CompressDataArray){
		byte[] dataDecompress=Compress.DecompressDeflater(CompressDataArray);
		return SerializationUtils.deserialize(dataDecompress);
		
	}
	
	public static byte[] serializeAndCompress(Object ObjectInput){
		byte[] data = SerializationUtils.serialize((Serializable) ObjectInput);
		return Compress.CompressDeflater(data);
	}
	
	public static String serializeAndCompressEncodeHex(Object ObjectInput){
		byte[] data = SerializationUtils.serialize((Serializable) ObjectInput);
		byte[] dataCompress=Compress.CompressDeflater(data);
		return Hex.encodeHexString(dataCompress);
	}

}
