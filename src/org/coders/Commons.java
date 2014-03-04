package org.coders;

import javolution.text.TextBuilder;
import jonelo.sugar.util.BubbleBabble;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;;


public class Commons {
	
	// The line separator string of the operating system.
	private static final String systemLineSeparator = System
			.getProperty("line.separator");

	public static void main(String[] args) {

		String A="1703000020cac63f3d857e6f63281ae9377848adc5a61ed47e36b0e1c11c2c8181a61a45e917030000208251e00b2695b87be911e7e0ecaa868a12a74b452d5bb583dbf74e2157170192";
		String B="1703000020763820e9a8a5f49f72fea3b02684f62eaaf5b35a207fd4eab75c7f43156fabb91703000020984c78d1b59df66bcddce4247e23f922d5f07686f4e0c8ec44ff114624bb3d3e";
		String C="1703000020d852251aa1e3c287a4392d33e127867e024370ef9b259e5ad635fd79e4992fe017030000208513d8d9feb7f5982c67576c446d5a610ccd2891798a8d57b424e09fcddb51e0";
		String D="1703000020d76878a40fe88225c86d6a2a7977fed72b02307ab6ddc6268282e7e6a24560771703000020a482e5efddd124b932fc0e4e02778d292dfb912727268e9e42d52778118a7983";
		//Log(new String(HexCoder.hexToBytes(Org)));
		Log(FormatLines(A));
		Log(FormatLines(B));
		
		String AB=StringXORCommon(A,B);
		Log(FormatLines(AB));
		String ABC=StringXORCommon(AB,C);
		Log(FormatLines(ABC));
		
		String AD=StringXORCommon(ABC,D);
		Log(FormatLines(AD));
		
	}

	
	public static String Log(String strInput){
		System.out.println(strInput);
		return strInput;
	}
	
	public static String LogERR(String strInput){
		System.err.println(strInput);
		return strInput;
	}
	
	
	// /////////////////////////////////////////
	// Format Lines
	// /////////////////////////////////////////
	
	public static String FormatLines(String in){
		
		
			in = in.trim().replace("\t",
					 "").replace("\n","").replace(" ","");
		
	
		return FormatLines(in, 0, in.length(), 32, systemLineSeparator);
	}
	
	public static String FormatLines(String in, int iOff, int iLen,
			int lineLen, String lineSeparator) {
		int blockLen = (lineLen * 3) / 4;
		if (blockLen <= 0)
			throw new IllegalArgumentException();
		int lines = (iLen + blockLen - 1) / blockLen;
		int bufLen = ((iLen + 2) / 3) * 4 + lines * lineSeparator.length();
		StringBuilder buf = new StringBuilder(bufLen);
		int ip = 0;
		while (ip < iLen) {
			int l = Math.min(iLen - ip, blockLen);
			//Log(l+">>"+(iOff + ip)+">>"+(iOff + ip+l));
			
			
			
			buf.append(in.substring(iOff+ip, (iOff + ip+l)));
			
			
			//buf.append(encode(in, iOff + ip, l));
			buf.append(lineSeparator);
			ip += l;
		}
		return buf.toString();
	}
	

	// /////////////////////////////////////////
	// Hex To String
	// /////////////////////////////////////////
	
	public static String Hex2String(String Input){
		Input=Input.trim().replace("\t",
				 "").replace("\n","").replace(" ","");
		String Output="";
		try{
			for (int i=0; i < Input.length(); i=i+2) {
			    String Decoding=Input.substring(i, i + 2);//This will get the hex number for each character
			    int j = Integer.parseInt(Decoding.toString(), 16);//This will parse the hex and convert to ascii
				char c = (char) (j);// This will convert ### to ?
			    Output+=c + "";
			}
		}catch(Exception e){
			Output="ERROR";
		}

		return Output;
	}
	
	
	// /////////////////////////////////////////
	// Hex To Base64
	// /////////////////////////////////////////
	public static String HexToBase64(String strInput){
		return Base64.encodeBase64String(HexCoder.hexToBytes(strInput));
	}
	
	public static String HexToBase64Formatted(String strInput){
		return Base64Coder.encodeLines(HexCoder.hexToBytes(strInput));
	}
	
	// /////////////////////////////////////////
	// Base64 To Hex
	// /////////////////////////////////////////
	public static String Base64ToHex(String strInput){
		return HexCoder.bytesToHex(Base64.decodeBase64(strInput));
	}
	
	public static String Base64FormattedToHex(String strInput){
		return HexCoder.bytesToHex(Base64Coder.decodeLines(strInput));
	}
	
	// /////////////////////////////////////////
	// String XOR
	// /////////////////////////////////////////
	
	
	public static String StringXORCommon(String Input1,String Input2){
		TextBuilder StrOutput=new TextBuilder();
		String strCommonL1=StringXOR(Input1,Input2);
		for(int i=0; i<strCommonL1.length() && i<Input2.length();i++){
			if(strCommonL1.substring(i,i+1).equals("0")){
				StrOutput.append(Input1.substring(i,i+1));
			}else{
				StrOutput.append("*");
			} 
		}
		return StrOutput.toString();
	}
	
	
	
	public static String StringXOR(String Input1,String Input2){
		String StrOutput="";
		for(int i=0; i<Input1.length() && i<Input2.length();i++){
			if(Input1.substring(i,i+1).equals(Input2.substring(i,i+1))){
				StrOutput+="0";
			}else{
				StrOutput+="1";
			} 
		}
		return StrOutput;
	}
	
	// /////////////////////////////////////////
	// Character to Letter <> Decimal
	// /////////////////////////////////////////
	
	public static int StrToAsii(String strLetter) {
		char c = strLetter.charAt(0);
		int j = (int) c;
		return j;
	}
	
	public static String AsciiToStr(int intInput){
		char c = (char)intInput;
		return c+"";
	}

	// /////////////////////////////////////////
	// Hex <> Decimal
	// /////////////////////////////////////////

	public static int HextoDecimal(String StrInput) {
		return Integer.parseInt(StrInput, 16);
	}
	
	public static String DecimalToHex(int intInput, int Length){
		String StrHex = Integer.toHexString(intInput);
		String Output = "";

		while (Output.length() + StrHex.length() + 1 <= Length) {
			Output += "0";
		}
		Output += StrHex;

		return Output.toUpperCase();
	}

	// /////////////////////////////////////////
	// Binary <> Decimal
	// /////////////////////////////////////////

	public static int BinarytoDecimal(String StrInput) {
		return Integer.parseInt(StrInput, 2);
	}

	public static String DecimaltoBinary(int intInput, int Length) {
		String StrBin = Integer.toBinaryString(intInput);
		String Output = "";

		while (Output.length() + StrBin.length() + 1 <= Length) {
			Output += "0";
		}
		Output += StrBin;
		return Output;
	}
	
}
