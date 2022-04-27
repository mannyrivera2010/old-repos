package v2;


public class EncryptionWay2 {

	/**
	 * @param args
	 */

	public static String formatStrDigit(String Input, int Length) {
		String Output = "";

		while (Output.length() + Input.length() + 1 <= Length) {
			Output += "0";
		}

		Output += Input;
		return Output;
	}

	public static void main(String[] args) {
		
		String In="This gritty urban drama follows the lives of Biggs\n";
			In+="(Ky-Mani Marley) and Wayne (Spragga Benz), two poor kids\n";
			In+= "from Kingston, Jamaica, who turn to crime as a means of!!\n";
			//In+= "survival. Drugs, robbery and murder become a way of life\n";
			//In+= "for the pair, who soon turn their sights to U.S. shores.\n";
			//In+= "Co-starring hip-hop superstar Wyclef Jean and model Tyson\n";
			//In+= "Beckford, this gangster tale pulls no punches in its\n";
			//In+= "unflinching portrayal of the criminal underworld.";
		
		In="The St. Mary’s County Vice Narcotics Division continuously monitors activity on the Internet and social networking sites for criminal activity in the St. Mary’s County area. There was an increase in the presence of individuals requesting sexual encounters for money.";
		
		//In="s x";
		
		
		String Encode = Encrypt_Single(In,"pas");
	

		System.out.println("-"+(Encode));

		//System.out.println(Encode);

		//Encode="6e027d3f81cace35";
		String Decode=Decrypt_Single(Encode, "pas");
		
		System.out.println("-"+Decode);
		
		int Decoding=Integer.parseInt("31");
		//char c = (char)(Decoding);
		//System.out.println("*"+c+"*");

	}

	public static String Encrypt_Single(String Input,String Key){
		return encode(Input,Key+"*");
	   }
	    
	public static String Decrypt_Single(String Input,String Key){
		  return decode(Input,Key+"*");
	    }
	    
    
    private static int PassHash(String input){
		return (StrHash(checksums.StrHashHex(input+"S*", 100))%100)+1;	
    }
    
	private static int StrHash(String Input) {
		int hash = 7;
		for (int i = 0; i < Input.length(); i++) {
			hash = hash * 31 + Input.charAt(i);
		}
		return (Math.abs(hash) % 100) + 1;
	}
    
	private static String encode(String Input, String Password) {
		String Output = "";

		Input=mixings.reverseIt((Input)); 
		
		int checksumlen=10;
		
		int passSu = PassHash(Password);
		//System.out.println(passSu);
		
		for (int i = 0; i < Input.length(); ++i) {
			char c = Input.charAt(i);
			int j = (int) c;

			// /Encode Step
			String Encoding = formatStrDigit(((j + passSu))+ "", 3);

			int Current=Integer.parseInt(Encoding);
			//System.out.println(Current);
			
			
			Encoding = Integer.toHexString(Current);

			Output += Encoding;
		}

		Output=checksums.CheckSum(Password+"*", checksumlen)+Output;
		
		return mixings.EncodeMixFin(mixings.EncodeMixFin(Output));
		//return (Output);
	}
	
	private static String decode(String Input, String Password) {
		String Output = "";
		
		//Input= "";
		Input= mixings.DecodeMixFin(mixings.DecodeMixFin(Input));
		
		
		int passSu = PassHash(Password);
		
		String Check="";
		int checksumlen=10;
		String ComCheck=checksums.CheckSum(Password+"*", checksumlen);
		
		for (int i = 0; i < checksumlen; i += 2) {
			Check+=Input.substring(i, i + 2);
		}
		
		boolean WrongCheckSum=true;
		
		//System.out.println(Check);
		if(Check.equalsIgnoreCase(ComCheck)==true){
			WrongCheckSum=false;
		}else{
			WrongCheckSum=true;
		}
			
		
		
		for (int i = checksumlen; i < Input.length(); i += 2) {

			// /Decode Step

			
			int Decoding=0; 
			
			try {
				Decoding=Integer.parseInt(Input.substring(i, i + 2), 16);
				//System.out.println(Decoding);
				
			} catch (Exception e) {
				
				Output="RORRE";
				break;//Break
				
				
			}

			// Decoding=Integer.parseInt((Decoding-passSu)-1+"",16);

			 //System.out.println(Decoding);

			char c = 0;
			
			if(WrongCheckSum==true){
				c = (char) (Decoding - checksums.RandomNumbera(1,30));
			}else{
				c = (char) (Decoding - passSu);
			}
				//RandomNumbera
			
			
			//System.out.println(passSu);
			String Oa = c + "";

			Output += Oa + "";
		}

		
		//Output= mixings.DecodeMixFin(mixings.DecodeMixFin(Output));
		
		Output=mixings.reverseIt((Output));
		return Output;
	}
	



}
