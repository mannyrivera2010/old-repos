package org.main;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.graph.undirected.unweighted.GenericGraphSC;

public class States1 {

	static GenericGraphSC<String> GraphObj= new GenericGraphSC<String>();
	
	static TreeMap<String,String> StateMapping=new TreeMap<String,String>();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		LoadAndFill();

		String graphObjStringDot=GraphObj.getDotLanguageV2();
		//System.out.println(graphObjStringDot);
		System.out.println(GraphObj.toStringSaveFormatV1());
		Utils.writeStringToFile(graphObjStringDot, "States1\\NumSmaller.gv");
		
		//System.out.println(GraphObj.getBFSDotLanguage("California"));	
	}
	
	public static void LoadAndFill(){
		//ArrayList<String> List= Utils.filetoArrayList("States1\\States.txt");
		ArrayList<String> List= Utils.filetoArrayList("States1\\NumSmaller.txt");
		
		for(int i=0;i<List.size();i++){
			String CurrentLine=List.get(i);
			 StringTokenizer StringInput=new StringTokenizer(CurrentLine,";;;");
				
			 String Node1="";
			 String Node2="";
				while(StringInput.hasMoreTokens()){
					 Node1=StringInput.nextToken();
					 Node2=StringInput.nextToken();
					 break;
				}
				
				GraphObj.addEdge(Node1, Node2,true);		
		}	
	}
}
