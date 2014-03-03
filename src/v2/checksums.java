package v2;

public class checksums {

	public static String formatStrDigit(String Input, int Length) {
		String Output = "";
		//Length++;

		while (Output.length() + Input.length() + 1 <= Length) {
			Output += "0";
		}

		Output += Input;
		return Output;
	}
	
	public static String StrHashDEC_Total(String input,int size){
		String output1pass="";
		String output2pass="";
		String output3pass="";
		
		int suma=0;
		
		for(int i=0;i<=input.length()-1;i+=1){
			
			if(input.length()!=0){
				suma+=(StrHash(input.substring(i, i+1)))%1000000;
				output1pass+=""+(suma)+",";
			}
			
			
			
		}
	
		for(int i=1;i<=size;i+=1){
			output2pass+=formatStrDigit(((StrHash(output1pass+"-"+(i*25)+size)%998)+1)+"",3)+"";
		}
		
		int AltPos=-1;
		
		for (int i=1; i < output2pass.length(); i=i+3) {
	
			
			output3pass+=output2pass.substring(i+AltPos, i+AltPos+1);
			
			AltPos=(AltPos+1);
			
			if(AltPos==2){
				AltPos=-2;
				AltPos=(AltPos+1);
			}
		}
		
		return output3pass;
	}
	
	
	static int StrHash(String Input){
		int hash=7;
		for (int i=0; i < Input.length(); i++) {
		    hash = hash*31+Input.charAt(i);
		}
		return Math.abs(hash);
	}
	
	
	public static String StrHashDEC(String input,int size){
		String output="";
		
		for(int i=1;i<=size;i+=1){
			output+=Integer.toHexString(StrHash(input+(i*31)+"+*/-")%9)+"";
		}
		return output;
	}
	
	public static String StrHashHex(String input,int size){
		String output="";
		
		for(int i=1;i<=size;i+=1){
			output+=Integer.toHexString(StrHash(input+(i*31)+"+")%16)+"";
		}
		return output;
	}
	
	public static String CheckSum(String input,int FinSize){
		input=StrHashHex(input+FinSize,100);
		String output="";
		//Expand
		for(int i=0;i<=input.length()-10;i+=10){
			output+=StrHashHex(input.substring(i, i+10)+"*"+FinSize,100)+"";
		}
		
		return StrHashHex(output+StrHashDEC(output+FinSize,10)+FinSize,FinSize);//Reduction to Size
	}
	
	public static String HexSerial(String input){
		input=input.toLowerCase().trim();
		String Output=CheckSum(input+"*1*",5).toUpperCase();
		Output+="-"+CheckSum(input+"*2*",5).toUpperCase();
		Output+="-"+CheckSum(input+"*3*",5).toUpperCase();
		Output+="-"+CheckSum(input+"*4*",5).toUpperCase();
		Output+="-"+CheckSum(input+"*5*",5).toUpperCase();
		return Output;
	}
	
	public static boolean CheckHexSerial(String UserName,String Serial){
	    if(HexSerial(UserName.toLowerCase().trim()).equalsIgnoreCase(Serial.trim())){
	    	return true;
	    }else{
	    	return false;	
	    }
		
	}
	
	public static String DecSerial(String input){
		input=input.toLowerCase().trim();
		String Output=StrHashDEC(CheckSum(input+"*1*bdface579bdf68ac3579bd468f1357*",5).toUpperCase(),3);
		Output+="-"+StrHashDEC(CheckSum(input+"*2*f13a86fdb975eca81fdb970ec531fd*",5).toUpperCase(),3);
		Output+="-"+StrHashDEC(CheckSum(input+"*3*e0279b2468ac357902468a135ce024",5).toUpperCase(),3);
		Output+="-"+StrHashDEC(CheckSum(input+"*4*79b20e7531fd642097531f864db975",5).toUpperCase(),3);
		Output+="-"+StrHashDEC(CheckSum(input+"*5*f13a86fdb975eca81fdb970ec531fd",5).toUpperCase(),3);
		Output+="-"+StrHashDEC(CheckSum(input+"*6*ca8135ce0246df13ace024bdf68ace",5).toUpperCase(),3);
		return Output;
	}
	
	public static boolean CheckDecSerial(String UserName,String Serial){
	    if(DecSerial(UserName.toLowerCase().trim()).equalsIgnoreCase(Serial.trim())){
	    	return true;
	    }else{
	    	return false;	
	    }
		
	}
	

	
	public static String RepeatRand(int Repeat){
	String Output="";
	
	for(int i = 1;i<=Repeat;i++){
		Output+=RandomNumbera(0,9);
	}

		return Output;
	}
	
	public static void main(String[] args) {
		
		String Random1=""+RepeatRand(100);
		String Random2=""+RepeatRand(100);
		String Random3=""+RepeatRand(100);
		String Random4=""+RepeatRand(100);
		String Random5=""+RepeatRand(100);
		
		String Ch1=StrHashHex(""+Random1,30);
		String Ch2=StrHashHex(""+Random2,10);
		
		
		System.out.println("Number1:"+Random1);
		System.out.println("Number2:"+Random2);
		System.out.println("Number3:"+Random3);
		System.out.println("Number4:"+Random4);
		System.out.println("Number5:"+Random5);
		System.out.println();
		//System.out.println("HashHex1="+Ch1);
		//System.out.println("HashHex1="+Ch2);
		//System.out.println();
		System.out.println("Dec:"+DecSerial(Random1+""));
		System.out.println("Dec:"+DecSerial(Random2+""));
		System.out.println("Dec:"+DecSerial(Random3+""));
		System.out.println("Dec:"+DecSerial(Random4+""));
		System.out.println("Dec:"+DecSerial(Random5+""));
		
		System.out.println();
			
		System.out.println(HexSerial("Test12"));
		System.out.println(CheckHexSerial("Test12","02457-E0297-E0297-B9702-0EC57"));
		
	

	
	}
	
	
	public static int RandomNumbera(int min, int max) { //Generate a Random Number between MIN and MAX
		int n=(int)(Math.random() * (max - min + 1) ) + min;
		while(n<min||n>max){//Make Sure Number is Between Min and Max
			n=(int)(Math.random() * (max - min + 1) ) + min;
		}
		return n;
	}

}
