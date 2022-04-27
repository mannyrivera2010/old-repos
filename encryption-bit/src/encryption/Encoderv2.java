package encryption;

import library.Converter;
import library.Hashes;

public class Encoderv2 {
	
	final static int intRounds=25;
	
	public static void main(String[] args) {
		String Org="class Started at 1:31";
		String Encoded= Encode(Org,"Pass");
		System.out.println(Encoded);
		
		
		String Cyper=Encoded;
		System.out.println(Decode(Cyper,"Pass"));	
		
		//System.out.println(Converter.StringXOR("6D6F7A2E636C61737A6F482E617274652F6F097A20323A33", Encoded));

	}

	public static String Encode(String Input, String StrPass) {
		// Format8char>24Ascii
		// 24/3=8
		// ### > 0000 0000
		// Round 1(0000 0000)> 16Hex 
		String Output = ""; // Final Output
		String FinOut = "";
		String CurrentLetter = "";

		int finDivPos = 8;
		int curdivpos = finDivPos;
		// boolean blnLast = false;
		int FormatPerLine = 2; // Number of Block per Line
		int FormatCurPerLine = 1; // Cur Block Count

		
		for (int i = 0; i <= Input.length() - 1; i++) {// START LOOP

			curdivpos--;
			CurrentLetter = Input.substring(i, i + 1);

			char c = CurrentLetter.charAt(0);
			int j = (int) c;

			// Output += CurrentLetter+"("+curdivpos+"-"+blnLast+"),";
			Output += Converter.formatStrDigit(j + "", 3);
			// Output+=CurrentLetter;

			if (curdivpos <= 0) {
				curdivpos = finDivPos;

			}

			// System.out.println(curdivpos+","+CurrentLetter);
			if (i == Input.length() - 1 || curdivpos == 8) {
				// blnLast = true;

				String StrBlank = "";

				for (int ni = 0; ni <= curdivpos - 1; ni++) {
					if (curdivpos != finDivPos) {
						StrBlank += "032";
						// StrBlank+="*";
					}
				}

				if (FormatCurPerLine >= FormatPerLine) {
					FinOut += EncodeRound1(Output + StrBlank, StrPass) + "";
					FormatCurPerLine = 0;
				} else {
					FinOut += EncodeRound1(Output + StrBlank, StrPass) + "";
				}
				Output = "";
				FormatCurPerLine++;
			} else {
				// blnLast = false;
			}

		}// END LOOP

		//*Special Case for "" 
		if (Input.length() == 0) {// start IF
			String StrBlank = "";

			for (int ni = 1; ni <= 8; ni++) {// ForLoop

				StrBlank += "032";
				//StrBlank += ni;
				
				// StrBlank+="*";
			}// End Forloop
			
			FinOut += EncodeRound1(StrBlank, StrPass);
		
		}

		// System.out.println(curdivpos);
		
		return FinOut;
	}// END SUB

	
	private static String EncodeRound1(String StrInputAscii, String StrPass) {
		//System.out.println(StrInputAscii);
		
		// 1.Convert ASCII to Binary
		String StrBinary = ""; // Used to Store 8 Binary Number
		int intloc = 0;
		for (int i = 0; i < StrInputAscii.length(); i = i + 3) {// loop
			int intCurrentNum = Integer.parseInt(StrInputAscii.substring(i, i + 3));
			StrBinary+=Converter.DecimaltoBinary(intCurrentNum, 8);
			intloc++;
		}// end loop

		String LeftRight[]= new String[2];
		LeftRight[0]=StrBinary.substring(0,32);
		LeftRight[1]=StrBinary.substring(32,64);
		
		//System.out.println(StrBinary);
		//System.out.println(LeftRight[0]+LeftRight[1]);
		
		String strIV = Hashes.StrBinHash64("IV");
		// 2.Make the BinaryHashe
		String StrBinaryHash = Hashes.StrBinHash32(StrPass + "Round1");
		// 3.Make the XOR Binarie
		String StrXOR = LeftRight[0]+Converter.StringXOR(LeftRight[1], StrBinaryHash); // Used to Store 8 Binary Hashes
		
		
		//**********************************************************************
		StrXOR=Converter.StringXOR(StrXOR, strIV);
		
		LeftRight[0]=StrXOR.substring(0,32);
		LeftRight[1]=StrXOR.substring(32,64);
		
		StrXOR=LeftRight[0]+LeftRight[1];
		
		//Round 2 to 15
		for(int intRound=2;intRound<=intRounds;intRound++){
			
			LeftRight[0]=StrXOR.substring(0,32);
			LeftRight[1]=StrXOR.substring(32,64);
			
			//StrBinaryHash = Hashes.C(StrPass + "Round"+intRound);
			String StrBinL=StrBinaryHash;
			// 3.Make the XOR Binary
			StrXOR =  LeftRight[0]+Converter.StringXOR(LeftRight[1], StrBinL); // Used to Store 8 Binary Hashes
		
			LeftRight[0]=StrXOR.substring(0,32);
			LeftRight[1]=StrXOR.substring(32,64);
			
			StrXOR=LeftRight[1]+LeftRight[0];
			StrXOR=Converter.StringXOR(StrXOR, strIV);
		}
		//System.out.println(StrXOR);
		//======================================================================================
		
		


		// 4.Convert Binary to DEC
		int intBinDec[] = new int[8];
		for (int ia = 0, ii=0; ia <= 63; ia = ia + 8,ii++) {
			intBinDec[ii] = Converter.BinarytoDecimal(StrXOR.substring(ia, ia+8));
			//System.out.print(StrXOR.substring(ia, ia+8));
		}

		// 5.Convert DEC to 2-char HEX
		String StrDecHex[] = new String[8];
		for (int ia = 0; ia <= 7; ia = ia + 1) {
			StrDecHex[ia] = Converter.DecimalToHex(intBinDec[ia], 2);
		}

		// 6.PRINT OUT
		String Output1 = "";
		for (int ia = 0; ia <= 7; ia = ia + 1) {
			Output1 += StrDecHex[ia];
		}
		// System.out.println(Output1);

		return Output1;
	}// end sub
	
	

	
	
	public static String Decode(String Input,String StrPass) {
		
		String FinOut = "";
		String CurrentLetter = "";

	
	
		// boolean blnLast = false;


		for (int i = 0; i <= Input.length() - 1; i=i+16) {// START LOOP

			CurrentLetter = Input.substring(i, i + 16);

			
			FinOut+=DecodeRound1(CurrentLetter,StrPass);
			
		}// END LOOP

	
		return FinOut;
	}// END SUB
	

	private static String DecodeRound1(String StrInputHex, String StrPass) {
		//System.out.println(StrInputAscii);
		// 1.Convert 2-Char Hex to DEC
		int StrHexDec[] = new int[8];
		
		for (int i=0,cur=0; i < 16; i=i+2, cur++){
			StrHexDec[cur]=Converter.HextoDecimal(StrInputHex.substring(i, i+2));
			//System.out.println(Converter.HextoDecimal(Hex.substring(i, i+2)));
		}


		// 2. Convert DEC to 8bit Bin
		String StrDecBin = "";
		for (int ia = 0; ia <= 7; ia = ia + 1) {
			StrDecBin += Converter.DecimaltoBinary(StrHexDec[ia], 8);
		}

		
		String LeftRight[]= new String[2];
		LeftRight[0]=StrDecBin.substring(0,32);
		LeftRight[1]=StrDecBin.substring(32,64);
		
		//System.out.println(StrBinary);
		//System.out.println(LeftRight[0]+LeftRight[1]);
		
		String strIV = Hashes.StrBinHash64("IV");
		// 2.Make the BinaryHashe
		String StrBinaryHash = Hashes.StrBinHash32(StrPass + "Round1");
		// 3.Make the XOR Binarie
		String StrXOR = LeftRight[0]+Converter.StringXOR(LeftRight[1], StrBinaryHash); // Used to Store 8 Binary Hashes
	
		//**********************************************************************
		StrXOR=Converter.StringXOR(StrXOR, strIV);
		
		LeftRight[0]=StrXOR.substring(0,32);
		LeftRight[1]=StrXOR.substring(32,64);
		
		StrXOR=LeftRight[0]+LeftRight[1];
		
		//Round 2 to 15
		for(int intRound=2;intRound<=intRounds;intRound++){
			
			LeftRight[0]=StrXOR.substring(0,32);
			LeftRight[1]=StrXOR.substring(32,64);
			
			//StrBinaryHash = Hashes.C(StrPass + "Round"+intRound);
			String StrBinL=StrBinaryHash;
			// 3.Make the XOR Binary
			StrXOR =  LeftRight[0]+Converter.StringXOR(LeftRight[1], StrBinL); // Used to Store 8 Binary Hashes
		
			LeftRight[0]=StrXOR.substring(0,32);
			LeftRight[1]=StrXOR.substring(32,64);
			
			StrXOR=LeftRight[1]+LeftRight[0];
		}
		//System.out.println(StrXOR);
		//======================================================================================
		
		
		
		// 5.Convert BIN to ASCII Code
		int[] StrBinAscii = new int[8]; //
		for (int ia = 0, ii=0; ia <= 63; ia = ia + 8,ii++) {
			StrBinAscii[ii] = Converter.BinarytoDecimal(StrXOR.substring(ia, ia+8));
		}


		
		// 6.PRINT OUT
		String Output1 = "";
		for (int ia = 0; ia <= 7; ia = ia + 1) {
			Output1 += Converter.AsciiToStr(StrBinAscii[ia]);
		}
		// System.out.println(Output1);

		return Output1;

	}// end sub
}
