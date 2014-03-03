package library;

public class Converter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//*Testing Bin to Dec
		//System.out.println(BinarytoDecimal("00100100"));
		//System.out.println(DecimaltoBinary(255, 8));
		//*Testing String to ASCII
		//System.out.println(StrToAscii("a"));
		//System.out.println(AsciiToStr(65));
		//Testing Xor
		String Org="000c29b3354c000c29957b7408004500009deb5540004006c618c0a803cdc0a803cf115c04340ed578d5ce9f96ea501855d8897c00001703000070ea5c4c1f2a5834fe5f0d4c7f8252e955e8c76eec2c7630db940b5744bb6fefbe8a4b5d2cfceae371da2cc0b0358fbfb0eaaa7eb8db80bc97ec93d9a637f73b5c7902b419cf953d2e1fa85a9dada939fe79dfc4e6dc548de99d4482f51b0a3c6f742938c941c1eac962d92f0e335f464e";
		String Kas="000c29b3354c000c29957b7408004500009deb5240004006c61bc0a803cdc0a803cf115c04340ed57860ce9f94a6501851a8897c00001703000070334bfb37c834b3fe7a3073700ed1c938cccaaf8d5c58ce3a4929e8d8d5168f4d9f23367de745326a37b35a11ebda7e63045978a9be3d80f579aac09270b4b97a31e31630fe75c93d275af6f8bbb9f0aaaad5e05ef6405aed2bbf235ed84099246d3df554271f48e00f58fc57373359c6";
		String enc=StringXOR(Org,Kas);
		System.out.println(enc);
		//String Dec=StringXOR(enc,Kas);
		//System.out.println(Org);
		//System.out.println(Dec);
		//*Tesing Dec to Hex
		//System.out.println(DecimalToHex(457,3));
		//System.out.println(HextoDecimal("1c9"));
		

		
	}

	
	public static String SwitchBlock(String StrInput){
		String[] StrArrInput=new String[8];
	
		
		
		for (int ia = 0, ii=0; ia <= 63; ia = ia + 8,ii++) {
			StrArrInput[ii] = StrInput.substring(ia, ia+8);
			//System.out.print(StrXOR.substring(ia, ia+8));
		}
		
		
		
		// STORE
		String[][] StrTwoDim= new String[8][8];
		for(int ROW=0;ROW<=7;ROW++){//ROW
			for(int COL=0;COL<=7;COL++){//COL
				StrTwoDim[ROW][COL]=StrArrInput[ROW].substring(COL, COL+1);
			}//ROW
		}//COL
		
		String Out="";
		
		//VIEW Switched
		for(int ROW=0;ROW<=7;ROW++){//ROW
			for(int COL=0;COL<=7;COL++){//COL
				//System.out.print(StrTwoDim[ROW][COL]); //REG
				Out+=(StrTwoDim[COL][ROW]);
			}//ROW
			//System.out.println();
		}//COL
		
		
		return Out;
		
		
	}
	
	////////////////////////////////////////////////
	//String Shift
	///////////////////////////////////////////////
	public static String ShiftStrRight(String strInput,int intright){
		String Output="";
		
		
		String strLeft=strInput.substring(0, strInput.length()-intright);
		
		String strRight=strInput.substring(strInput.length()-intright, strInput.length());
		
		
	    
		Output=strRight+strLeft;
		return Output;
	}
	
	public static String ShiftStrLeft(String strInput,int intLeft){
		String Output="";
		
	
		String strLeft=strInput.substring(0, intLeft);
	    String strRight=strInput.substring(intLeft, strInput.length());
			
	    Output=strRight+""+strLeft;
		return Output;
	}
	
	
	////////////////////////////////////////////////////////
	//SwitchBlockEN
	///////////////////////////////////////////////////////
	
	public static String[] SwitchBlockEN(String[] StrArrInput){
		//for(int i=0;i<=7;i++){
		//	System.out.println(StrArrInput[i]);
		//}
		
		for(int i=0;i<=7;i++){
			StrArrInput[i]=ShiftStrLeft(StrArrInput[i],i);
		}

		// MAKE TWO DIM Array
		String[][] StrTwoDim= new String[8][8];
		for(int ROW=0;ROW<=7;ROW++){//ROW
			for(int COL=0;COL<=7;COL++){//COL
				StrTwoDim[ROW][COL]=StrArrInput[ROW].substring(COL, COL+1);
			}//ROW
		}//COL
		
		
		
		String StrTwoDimToOneDim[]= new String[8];
		for(int i=0;i<=7;i++)StrTwoDimToOneDim[i]="";
		for(int ROW=0;ROW<=7;ROW++){//ROW
			for(int COL=0;COL<=7;COL++){//COL
				//System.out.print(StrTwoDim[ROW][COL]); //REG
				StrTwoDimToOneDim[ROW]+=StrTwoDim[COL][ROW];
			}//ROW
			
		}//COL
		
		
		
		//VIEW Switched
		for(int ROW=0;ROW<=7;ROW++){//ROW
			for(int COL=0;COL<=7;COL++){//COL
				//System.out.print(StrTwoDim[ROW][COL]); //REG
				//System.out.print(StrTwoDim[COL][ROW]);
			}//ROW
			//System.out.println();
		}//COL
		return StrTwoDimToOneDim;
		
		
	}
	
	////////////////////////////////////////////////////////
	//SwitchBlockDE
	///////////////////////////////////////////////////////
	
	public static String[] SwitchBlockDE(String[] StrArrInput){
	    // STORE
		String[][] StrTwoDim= new String[8][8];
		for(int ROW=0;ROW<=7;ROW++){//ROW
			for(int COL=0;COL<=7;COL++){//COL
				StrTwoDim[ROW][COL]=StrArrInput[ROW].substring(COL, COL+1);
			}//ROW
		}//COL
		
		//VIEW Switched
		String[] StrTwoDimToOneDim=new String[8];
		for(int i=0;i<=7;i++)StrTwoDimToOneDim[i]="";
		for(int ROW=0;ROW<=7;ROW++){//ROW
			for(int COL=0;COL<=7;COL++){//COL
				//System.out.print(StrTwoDim[ROW][COL]); //REG
				//System.out.print(StrTwoDim[COL][ROW]);
				StrTwoDimToOneDim[COL]+=StrTwoDim[COL][ROW];
			}//ROW
			//System.out.println();
		}//COL
		
		
		for(int i=0;i<=7;i++){
			StrTwoDimToOneDim[i]=ShiftStrRight(StrTwoDimToOneDim[i],i);
		}
		

	
		return StrTwoDimToOneDim;
		
		//for(int i=0;i<=7;i++){
		//	System.out.println(StrTwoDimToOneDim[i]);
		//}

	}
	
	// /////////////////////////////////////////
	// String XOR
	// /////////////////////////////////////////
	
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

	// /////////////////////////////////////////
	// Text Fill Formatter
	// /////////////////////////////////////////
	
	public static String formatStrDigit(String Input, int Length) {
		String Output = "";
		while (Output.length() + Input.length() + 1 <= Length) {
			Output += "0";
		}
		Output += Input;
		return Output;
	}

}
