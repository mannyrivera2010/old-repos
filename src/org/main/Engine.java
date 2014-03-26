package org.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.graph.undirected.unweighted.GenericGraphSC;

public class Engine {

	static GenericGraphSC<String> GraphObj= new GenericGraphSC<String>();
	
	enum Part{EDGES,STATES};
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GetMap();
		String graphObjStringDot=GraphObj.getDotLanguageV2();
		
		System.out.println("------------------------------");
		System.out.println("BFS visit for each state");
		System.out.println(GraphObj.getBFS("California"));
		System.out.println("------------------------------");
		System.out.println("BFS visit for each state");
		System.out.println(GraphObj.getDFS("California"));
		System.out.println("------------------------------");
		System.out.println("Get List of States");
		System.out.println(GraphObj.writeStateName());
		System.out.println("------------------------------");
		System.out.println("Get List of States With (BFS based)");
		System.out.println(GraphObj.writeStatesNameWithColors());
		System.out.println("------------------------------");
		System.out.println(graphObjStringDot);
		Utils.writeStringToFile(graphObjStringDot, "output.gv");
	}
	
	public static void GetMap(){
		ArrayList<String> AlFile= Utils.filetoArrayList("input.txt");
		
		ArrayList<ArrayList<Integer>> AlFile_Edges= new ArrayList<ArrayList<Integer>>();
		TreeMap<Integer,String> AlFile_States= new TreeMap<Integer,String>();
		
		Part CurrentPart= Part.EDGES;
		for(int i = 0 ; i<AlFile.size() ; i++){
			if(AlFile.get(i).contains("where")){
				CurrentPart= Part.STATES;
				i++;
			}
			
			String CurrentLine=AlFile.get(i);
			if(CurrentPart==Part.EDGES){
				//edge
				
				ArrayList<Integer> Temp= new ArrayList<Integer>();
				
                StringTokenizer StringInput=new StringTokenizer(CurrentLine," |\t|,");
				
				while(StringInput.hasMoreTokens()){
					Temp.add(Integer.parseInt(StringInput.nextToken()));
				}
				
				AlFile_Edges.add(Temp);
			}else if(CurrentPart==Part.STATES){
				//states
				//System.out.println(CurrentLine);
				StringTokenizer StringInput=new StringTokenizer(CurrentLine,"\t|,");
				
				while(StringInput.hasMoreTokens()){
					AlFile_States.put(Integer.parseInt(StringInput.nextToken().trim()), StringInput.nextToken().trim());
					break;
				}
			}
			
		}
		
		
		//System.out.println(AlFile_Edges);
		//System.out.println(AlFile_States);
		
		for(int i = 0;i<AlFile_Edges.size();i++){
			for(int j = 0; j<AlFile_Edges.get(i).size();j++){
				String strNode1=AlFile_States.get(i+1);
				String strNode2=AlFile_States.get(AlFile_Edges.get(i).get(j));
				//System.out.println(strNode1+"\t-->\t"+strNode2);
				GraphObj.addEdge(strNode1, strNode2, true);
			}//Inner
		}//Outer loop
		
	}

}
