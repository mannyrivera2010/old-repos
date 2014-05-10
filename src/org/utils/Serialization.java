package org.utils;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * The Class Serialization.
 */
public class Serialization {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		//Intial Message
		MessageObject ClientMsgObj= new MessageObject("One line Message");
		byte[] dataCompress = serializeAndCompress(ClientMsgObj);
		
		//deserialize:
		MessageObject ServerMsgObj = (MessageObject) deserializeAndDecompress(dataCompress);
		System.out.println(ServerMsgObj);
	}
	
	
	/**
	 * Deserialize and decompress.
	 *
	 * @param HexCode the hex code
	 * @return the object
	 */
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
	
	/**
	 * Deserialize and decompress.
	 *
	 * @param CompressDataArray the compress data array
	 * @return the object
	 */
	public static Object deserializeAndDecompress(byte[] CompressDataArray){
		byte[] dataDecompress=Compress.DecompressDeflater(CompressDataArray);
		return SerializationUtils.deserialize(dataDecompress);
		
	}
	
	/**
	 * Serialize and compress.
	 *
	 * @param ObjectInput the object input
	 * @return the byte[]
	 */
	public static byte[] serializeAndCompress(Object ObjectInput){
		byte[] data = SerializationUtils.serialize((Serializable) ObjectInput);
		return Compress.CompressDeflater(data);
	}
	
	/**
	 * Serialize and compress encode hex.
	 *
	 * @param ObjectInput the object input
	 * @return the string
	 */
	public static String serializeAndCompressEncodeHex(Object ObjectInput){
		byte[] data = SerializationUtils.serialize((Serializable) ObjectInput);
		byte[] dataCompress=Compress.CompressDeflater(data);
		return Hex.encodeHexString(dataCompress);
	}

}
