
package org.shared;

public class EncodeWeak {

	public static void main(String[] args){//This is for function testing
				
		String Input= "1234567891012345678901234567890\n" +
		"abcdefabcdefabcdefabcdef\n" +
		"hello\n" +
		"line1\n" +
		"line2\n";
		
		///*
		Input= "MATLAB is  a high performance tool that is used extensively in the field of engineering and signal\n" +
				"processing.  To use matlab you usually need a version of it installed on your computer. To work\n" +
				"around this , you could actually make a standalone executable from the matlab files so that your\n" +
				"program...";
		//*/	
		
		
		String CharToAscii=String2Hex(Input);
		
		
		
		
		
		System.out.println("-"+CharToAscii+"-");
		//String HalfEncoded=HalfEncode(CharToAscii);
		//System.out.println("-"+HalfEncoded+"-");
		//System.out.println("-"+HalfDecode(HalfEncoded)+"-");
		System.out.println();
		
		System.out.println("-"+Hex2String(CharToAscii)+"-");
		
		
		String aInput="NO|Time";
		
		int intDiv=aInput.indexOf("|");
		String aCommand=aInput.substring(0, intDiv);
		String aString=aInput.substring(intDiv+1, aInput.length());
	
		System.out.println(aCommand);
		System.out.println(aString);
		
		
		
	}
	
	//This Function Converts 
	public static String String2Hex(String Input){
		String Current="";
		String Output="";
		
		try{
			for (int i=0; i < Input.length(); i++) {//Loop that goes process string   
			    Current=Input.substring(i,i+1);//get one character for conversion 
				int j = (int) Current.charAt(0);//convert the letter to Ascii 
				String s = Integer.toString(j, 16);//Convert 3 digit Ascii to 2 digit hex number
			    Output+=formatStrDigit(s+"",2);//append to output and make sure it is length of 3 
			}
		}catch(Exception e){
			Output="ERROR";
		}
	
		return Output;
	}
	
	public static String String2Ascii(String Input){
		String Current="";
		String Output="";
		
		try{
			for (int i=0; i < Input.length(); i++) {//Loop that goes process string   
			    Current=Input.substring(i,i+1);//get one character for conversion 
				int j = (int) Current.charAt(0);//convert the letter to Ascii 
				String s = Integer.toString(j, 10);//Convert 3 digit Ascii to 2 digit hex number
			    Output+=formatStrDigit(s+"",3);//append to output and make sure it is length of 3 
			}
		}catch(Exception e){
			Output="ERROR";
		}
	
		return Output;
	}
	
	
	//This function Converts the 3 digit HEX to Ascii code back to Characters
	public static String Hex2String(String Input){
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
	
	//This function Converts the 3 digit HEX to Ascii code back to Characters
	public static String Ascii2String(String Input){
		String Output="";
		try{
			for (int i=0; i < Input.length(); i=i+3) {
			    String Decoding=Input.substring(i, i + 3);//This will get the hex number for each character
			    int j = Integer.parseInt(Decoding.toString());//This will parse the hex and convert to ascii
				char c = (char) (j);// This will convert ### to ?
			    Output+=c + "";
			}
		}catch(Exception e){
			Output="ERROR";
		}

		return Output;
	}
	
	//This function make sure the string is 3 character long. useful with converting Char to Ascii to HEX. 
	//(01)Checks if Input length of Input is < 3 if not then add 0 until reach length of 3
	public static String formatStrDigit(String Input, int Length) {
		String Output = "";
		while (Output.length() + Input.length() + 1 <= Length) {//(01)
			Output += "0";
		}
		Output += Input;
		return Output;
	}
	
	

	
	
	
}
