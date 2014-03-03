package main;

import library.Converter;
import library.Hashes;

public class engine {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String[] Sample = new String[8];
		Sample[0] = "12345678";
		Sample[1] = "12345678";
		Sample[2] = "12345678";
		Sample[3] = "12345678";
		Sample[4] = "12345678";
		Sample[5] = "12345678";
		Sample[6] = "12345678";
		Sample[7] = "12345678";
		
		String [] SampleEN= new String[8];
		SampleEN[0] = "12345678";
		SampleEN[1] = "23456781";
		SampleEN[2] = "34567812";
		SampleEN[3] = "45678123";
		SampleEN[4] = "56781234";
		SampleEN[5] = "67812345";
		SampleEN[6] = "78123456";
		SampleEN[7] = "81234567";
		
		

		String StrPT="12345678" +
				"12345678" +
				"12345678" +
				"12345678" +
				"12345678" +
				"12345678" +
				"12345678" +
				"12345678";
			
		String StrCT=Converter.SwitchBlock(StrPT);
		System.out.println(StrCT);
		
		System.out.println(Converter.SwitchBlock(StrCT));
		//System.out.println(ShiftStrRight(Sample[0],3));
		
		System.out.println("-----------------");
		
	

		//3a.BlockEncodeEn
		
		String[] StrBlockEN=Converter.SwitchBlockEN(Sample);
		
		
		for (int ia = 0; ia <= 7; ia = ia + 1) {
			System.out.println(StrBlockEN[ia]);
		}
			
		
		
		System.out.println("-----------------");
	
	}
	


			
	
		
       private static String Mix8BitEN(String in) {
		
		//REV
		String FinA1 = in.substring(0, 4);
		String FinA1r="";
		for(int i=4;i>0;i=i-1){
			FinA1r+=FinA1.substring(i-1, i);
		}
		
		
		String FinB1 = in.substring(4, 8);
		String FinB1r="";
		for(int i=4;i>0;i=i-1){
			FinB1r+=FinB1.substring(i-1, i);
		}
		
		//Swap
		String FinA2 = "";
		String FinB2 = "";

		for(int i=0;i<4;i=i+2)
		FinA2+=FinA1r.substring(i, i+1)+FinB1r.substring(i, i+1);

		for(int i=1;i<4;i=i+2)
		FinB2+=FinA1r.substring(i, i+1)+FinB1r.substring(i, i+1);

		return FinA2 + FinB2;
	}
	


}
