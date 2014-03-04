package table.compression;

public interface Compression {
	public byte[] Decompress(byte[] compressedData);
	public byte[] Compress(byte[] decompressedData);
	
}
