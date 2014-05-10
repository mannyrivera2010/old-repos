package org.utils;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.apache.commons.codec.binary.Base64;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CompressTest {

	@Test
	public void testDecompressDeflater() {
		byte[] compressedByteArray = Base64.decodeBase64("eNoLSS0uAQAD3QGh");
		
		Assert.assertEquals("Test", 
				new String(Compress.DecompressDeflater(compressedByteArray)));
	}

	@Test
	public void testCompressDeflater() {
		String plainText = "Test";
		byte[] input = plainText.getBytes();
		String compressedString = Base64.encodeBase64String(Compress.CompressDeflater(input));
		
		Assert.assertEquals("eNoLSS0uAQAD3QGh",
				compressedString);
	
	}

}
