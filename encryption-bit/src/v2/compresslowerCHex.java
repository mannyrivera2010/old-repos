package v2;

import java.util.ArrayList;

class StrObjecta{
	String string=null;
	int occurrence=0;
	
	
	public StrObjecta(String string) {
		this.string = string;
		occurrence++;
	}
	
	public void Add(){
		occurrence++;
	}
}

class ArrayListOccurrentce{
	static ArrayList<StrObjecta> MainAL=new ArrayList<StrObjecta>();
	
	public static void add(String Input){
		
		if(MainAL.size()<=0){
			MainAL.add(new StrObjecta(Input));
		}else{
			if(MainAL.get(MainAL.size()-1).string.equalsIgnoreCase(Input)){
				MainAL.get(MainAL.size()-1).Add();
			}else{
				MainAL.add(new StrObjecta(Input));
			}
		}
		
		
	}
	
	public static void occurence(String input){
		//String out="";
		//
		
		MainAL.clear();
			 for (int i = 0; i < input.length(); i += 1) {
				 //out+=input.substring(i, i+1);
				 add(input.substring(i, i+1));
				
			 }
	
		//System.out.println(out);
		//System.out.println(ArrayListOccurrentce.PrintArrayList_toString());
		
	}
	
	public String Compress(){
	      String Output1="";
	      
			for (int i = 0; i <= MainAL.size() - 1; i++) {
				
				if(IsNum(MainAL.get(i).string)==true){//is num
					
					//int ascii=(Integer.parseInt(MainAL.get(i).string)+48+49);
					
					char c = MainAL.get(i).string.charAt(0);
					int j = (int) c;
					
					String Cur="";
					
					if(MainAL.get(i).occurrence>=3){
						Cur=((char)(j+192))+""+MainAL.get(i).occurrence;
					}else if(MainAL.get(i).occurrence==2){
						Cur=((char)(j+182))+"";
					}
					else if(MainAL.get(i).occurrence==1){
						Cur=((char)(j+192))+"";
					}
					
					
					//Output1+=MainAL.get(i).string+","+MainAL.get(i).occurrence+"(NUM)"+Cur+"\n";
					Output1+=Cur+"";
					
				}else{//is Strings
					
					char c = MainAL.get(i).string.toLowerCase().charAt(0);
					int j = (int) c;
					
					String Cur="";
					
					if(MainAL.get(i).occurrence>=3){
						Cur=((char)(j+78))+""+MainAL.get(i).occurrence;
					}else if(MainAL.get(i).occurrence==2){
						Cur=((char)(j+105))+"";
					}
					else if(MainAL.get(i).occurrence==1){
						Cur=((char)(j+78))+"";
					}
					
					//Output1+=MainAL.get(i).string+","+MainAL.get(i).occurrence+"(LET)"+Cur+"\n";
					Output1+=Cur+"";
				}
					
				
			}
			return Output1;
	}

	

	
	public static boolean IsNum(String Input) {//Check if Input is Number
		boolean isnum = true;

		try {
			Integer.parseInt(Input);
		} catch (Exception e) {
			isnum = false;
		}

		return isnum;
	}
	
	public String PrintArrayList_toString(){
        String Output1="";
		for (int i = 0; i <= MainAL.size() - 1; i++) {
			if(i < MainAL.size()-1){
				Output1+=MainAL.get(i).string+","+MainAL.get(i).occurrence+"\n";
			}else Output1+=MainAL.get(i).string+","+MainAL.get(i).occurrence;
		}
		return Output1;
	}
	
}

public class compresslowerCHex{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		ArrayListOccurrentce ArrayListOccurrentce1=new ArrayListOccurrentce();
						
		String inta="";
		inta="8bbcabacd0968dbbbbbaa1d6";
		
		
		
		ArrayListOccurrentce.occurence(inta);
		//System.out.print(ArrayListOccurrentce1.PrintArrayList_toString());
		System.out.println(inta);
		System.out.println(ArrayListOccurrentce1.Compress());
	}
	
	

}
