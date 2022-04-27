package library;


public class Hashes {
	
	private static int StrHash(String Input){
		int hash=7;
		for (int i=0; i < Input.length(); i++) {
		    hash = hash*31+Input.charAt(i);
		}
		return Math.abs(hash);
	}
	
	private static String StrHashDEC(String input,int size){
		String output="";
		
		for(int i=1;i<=size;i+=1){
			output+=Integer.toHexString(StrHash(input+size+(i*31)+"+*/-")%9)+"";
		}
		return output;
	}
	
	public static String StrBinHash(String Input, int BitSize){
		int BitValueSize=(int) Math.pow(2,BitSize);
		int TempHash=Integer.parseInt(StrHashDEC(Input,6));
		//System.out.println(TempHash);
		int HashtoSize=TempHash%BitValueSize;

		return Converter.DecimaltoBinary(HashtoSize, BitSize)+"";
	}
	
	public static String StrBinHash64(String Input){
		String Output="";
		for(int i=1;i<=8;i++){
			Output+=StrBinHash(Input+"Round"+i,8);
		}
		
		return Output;		
	}
	
	public static String StrBinHash128(String Input){
		String Output="";
		for(int i=1;i<=16;i++){
			Output+=StrBinHash(Input+"Round"+i,8);
		}
		
		return Output;		
	}
	
	public static String StrBinHash32(String Input){
		String Output="";
		for(int i=1;i<=4;i++){
			Output+=StrBinHash(Input+"Round"+i,8);
		}
		
		return Output;		
	}
	
	
	public static void main(String[] args) {
		//String Ch1=StrHashDEC(""+"hell1",6);
		//System.out.println();
		//System.out.println("DecHas="+Ch1);
		//System.out.println();
		//System.out.println("BinHash="+StrBinHash("passa"+"Round1",8));
		//System.out.println("BinHash="+StrBinHash("passa"+"Round2",8));
		//System.out.println("BinHash="+StrBinHash("passa"+"Round3",8));
		//System.out.println("BinHash="+StrBinHash("passa"+"Round4",8));
		//System.out.println("BinHash="+StrBinHash("passa"+"Round5",8));
		//System.out.println("Random2 ="+CheckSum(Random2,10));
		
		System.out.println(StrBinHash32("password_Round11"));
	
	
	}
	

}
