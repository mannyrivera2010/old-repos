package v2;

public class mixings {

	 private static String Mix (String s1, String s2) {
	        if (s1.length() == 0) return s2;
	        if (s2.length() == 0) return s1;
	        return s1.substring(0,1) + Mix (s2, s1.substring(1));
	 }
	 
	 private static String EncodeMix1(String in){
		 int len1=in.length();
		 String fir=in.substring(0, len1/2);
		 String sec=in.substring(len1/2,len1);
		 //System.out.println(fir);
		 //System.out.println(sec);
		 fir=reverseIt(fir);
		 sec=reverseIt(sec);
		 
		 return reverseIt(Mix(fir,sec));
	 }
	 
	 private static String DecodeMix1(String in){
		 String Part1="";
		 String Part2="";
		 
		 for (int i = 0; i < in.length(); i += 1) {
			 if(i%2==0){
				 Part1+=in.substring(i, i+1);
			 }else{
				 Part2+=in.substring(i, i+1);
			 }
		 }
		 
		 Part1=reverseIt(Part1);
		 Part2=reverseIt(Part2);
		 return reverseIt(Part1+Part2);//Mix(fir,sec);
	 }
	 
	 public static String reverseIt(String source) {
		    int i, len = source.length();
		    StringBuffer dest = new StringBuffer(len);

		    for (i = (len - 1); i >= 0; i--)
		      dest.append(source.charAt(i));
		    return dest.toString();
    }
	 
	 public static String EncodeSwap(String in){
		 String out="";
		 for (int i = 0; i < in.length(); i += 2) {
			 try {
				 out+=in.substring(i+1, i+2)+in.substring(i, i+1);
			} catch (Exception e) {
				out+="*";
			}
			 
		 }
		 
		return out;
	 }
	 
	
	 public static String EncodeMixFin(String in){
		return EncodeSwap(EncodeMix1(EncodeMix1(in)));
	 }
	 
	 public static String DecodeMixFin(String in){
			return DecodeMix1(DecodeMix1(EncodeSwap(in)));
	}
	 
	 
	 
	    public static void main(String[] args) {
	    	
	    	//System.out.println (EncodeMixFin("12345678"));
	    	
	    	System.out.println(Encodeway2("12341111"));
	    	//System.out.println(Decodeway2("056054054054054054054054"));
	    	
	    	
	        //System.out.println (Mix ("9c 8d 9b 9c", "9c 8d 9b 9c"));
	        //System.out.println (Mix ("Hi", "There"));
	        //System.out.println (Mix ("Hello again", "Pax"));
	  
	   }
	    
	    
	    public static String Encodeway2(String in){
	    	String Output="";
	    	
	    	String Previous="!";
	    	
	    	for (int i = 0; i < in.length(); i += 1) {
	    		String StrCurrent=in.substring(i, i+1);
	    		
	    		int intAsii=StrToAsii(StrCurrent+"");
	    		int intPrevious=StrToAsii(Previous);
	    		System.out.println(intPrevious);
	    		int current=0;
	    		
	    		if(intAsii+((intPrevious%2)+1)<=255){
	    			
	    			if(((intPrevious%2)==0)){
	    				current++;	
	    			}
	    			
	    			current=intAsii;
	    			//current=intAsii;
	    			
	    			System.out.print(formatStrDigit(intAsii+"",3)+"-");
	    			Output+=formatStrDigit(current+"",3)+"-";
	    			
	    		}
	    		else if(intAsii-((intPrevious%2)+1)>=1){
	    			current=intAsii-((intPrevious%2)+1);
	    			
	    			//current=intAsii;
	    			
	    			System.out.print(formatStrDigit(intAsii+"",3)+"-");
	    			
	    			Output+=formatStrDigit(current+"",3)+"-";
	    		}
	    		
	    		
				//System.out.println(current);
				
				
				Previous=StrCurrent;
			 }
	    	System.out.println();
			return Output;
	    }
	    
	    public static String Decodeway2(String in){
	    	String Previous="!";
	    	String Output="";
	    	
	    	for (int i = 0; i < in.length(); i += 3) {
	    		String StrCurrent=in.substring(i, i+3);
	    		
	    		int intAsii=Integer.parseInt(StrCurrent);
	    		
	    		int current=0;
	    		
	    		
				System.out.println(StrCurrent);
				
				
				Previous=StrCurrent;
			 }
	    	
			return Output;
	    }
	    
	    
	    public static int StrToAsii(String Letter){
	    	
	    	char c = Letter.charAt(0);
			int j = (int) c;
			
			return j;
	    	
	    }
		public static String formatStrDigit(String Input, int Length) {
			String Output = "";

			while (Output.length() + Input.length() + 1 <= Length) {
				Output += "0";
			}

			Output += Input;
			return Output;
		}
	   

	

}
