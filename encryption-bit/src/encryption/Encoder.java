package encryption;

import library.Converter;
import library.Hashes;

public class Encoder {

	public static void main(String[] args) {
		String Org="Class Started at 2:00";
		String Encoded= Encode(Org,"1234");
		System.out.println(Encoded);
		
		
		String Cyper=Encoded;
		System.out.println(Decode(Cyper,"1234"));		

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
		int FormatPerLine = 1; // Number of Block per Line
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

				StrBlank += "031";
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
		String[] StrBinary = new String[8]; // Used to Store 8 Binary Number
		int intloc = 0;
		for (int i = 0; i < StrInputAscii.length(); i = i + 3) {// loop
			int intCurrentNum = Integer.parseInt(StrInputAscii.substring(i, i + 3));
			StrBinary[intloc] = Converter.DecimaltoBinary(intCurrentNum, 8);
			intloc++;
		}// end loop

		// 2.Make the BinaryHashes
		String[] StrBinaryHash = new String[8]; // Used to Store 8 Binary Hashes
		for (int ia = 0; ia <= 7; ia = ia + 1) {
			StrBinaryHash[ia] = (Hashes.StrBinHash(StrPass + "Round1" + ia, 8));
		}

		// 3.Make the XOR Binaries
		String[] StrXOR = new String[8]; // Used to Store 8 Binary Hashes
		for (int ia = 0; ia <= 7; ia = ia + 1) {
			StrXOR[ia] = Converter.StringXOR(StrBinary[ia], StrBinaryHash[ia]);
		}
		

		// 4.Convert Binary to DEC
		int intBinDec[] = new int[8];
		for (int ia = 0; ia <= 7; ia = ia + 1) {
			intBinDec[ia] = Converter.BinarytoDecimal(StrXOR[ia]);
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
		String StrDecBin[] = new String[8];
		for (int ia = 0; ia <= 7; ia = ia + 1) {
			StrDecBin[ia] = Converter.DecimaltoBinary(StrHexDec[ia], 8);
		}

		
		// 3.Make the BinaryHashes
		String[] StrBinaryHash = new String[8]; // Used to Store 8 Binary Hashes
		for (int ia = 0; ia <= 7; ia = ia + 1) {
			StrBinaryHash[ia] = (Hashes.StrBinHash(StrPass + "Round1" + ia, 8));
		}

		
		// 4.Make the XOR Binaries
		String[] StrXOR = new String[8]; // Used to Store 8 Binary Hashes
		for (int ia = 0; ia <= 7; ia = ia + 1) {
			StrXOR[ia] = Converter.StringXOR(StrDecBin[ia], StrBinaryHash[ia]);
		}
		
	
		// 5.Convert BIN to ASCII Code
		int[] StrBinAscii = new int[8]; //
		for (int ia = 0; ia <= 7; ia = ia + 1) {
			StrBinAscii[ia] = Converter.BinarytoDecimal(StrXOR[ia]);
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
