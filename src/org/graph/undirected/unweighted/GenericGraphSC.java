package org.graph.undirected.unweighted;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Iterator;

import java.util.Map.Entry;

public class GenericGraphSC<E extends Comparable<E>> {
	/**
	 * TreeMap Variable keeps track of all nodes and Edges
	 * TreeMap = Node
	 * TreeSet<E> = Connected Nodes
	 */
	private TreeMap<E, TreeSet<E>> NodeEdge = new TreeMap<E, TreeSet<E>>();
	
	public TreeMap<E, TreeSet<E>> getTreeMap(){
		return this.NodeEdge;
	}
	
	//Get States
	public ArrayList<E> writeStateName(){
		return this.getListOfNodesData();
	}
	
	public String writeStatesNameWithColors(){
		return getStatesWithColors(NodeEdge,this.writeStateName().get(0));
	}
	/**
	 * getStatesWithColors
	 * @param inputTreeMap
	 * @param RootNode
	 * @return
	 */
	private String getStatesWithColors(TreeMap<E, TreeSet<E>> inputTreeMap, E RootNode){
		StringBuilder OutputString=new StringBuilder();
		//^^Used to create the String Output
		Colors PickColor= new Colors();
		//^^Color Object
		TreeMap<E,String> uniqueNodeAssignMapping= new TreeMap<E,String>();
		//^^Used to store Mapping
		int countUnique=0;
		
	
		TreeSet<String> tsUniqueConnections= new TreeSet<String>();
		TreeSet<String> tsUniqueConnectionsOneWay= new TreeSet<String>();
		
		
		TreeMap<E, TreeSet<E>> CloneMap= this.MapClone(inputTreeMap);
		ArrayList<E> Output= new ArrayList<E>();
		TreeMap<E,Integer> uniqueNodeColorMapping= new TreeMap<E,Integer>();
		
		TreeSet<E> VisitedNodes= new TreeSet<E>();
		
		ArrayDeque<E> Queue= new ArrayDeque<E>();
		Queue.addFirst(RootNode);	
		
	
		while(!Queue.isEmpty()){
			E CurrentNode= Queue.removeFirst();
			TreeSet<E> CurrentNodes=CloneMap.get(CurrentNode);			
			 
			 
			
			if(Output.size()==0){
				VisitedNodes.add(RootNode);
				Output.add(RootNode);
				countUnique++;
				uniqueNodeAssignMapping.put(RootNode, "Node"+countUnique);
				uniqueNodeColorMapping.put(RootNode, 0);
				
			}

			Iterator<E> itHistory = CurrentNodes.iterator();
			//Gets Empty
			while (itHistory.hasNext()) {
				E valueTreeSet=itHistory.next();
				if(!uniqueNodeColorMapping.containsKey(valueTreeSet))uniqueNodeColorMapping.put(valueTreeSet, -1);
				
				if(uniqueNodeAssignMapping.containsKey(valueTreeSet)){
					//Exist
					//System.out.println(HistoryMap.get(keyNode));
					
				}else{
					//Not Exist
					countUnique++;
					uniqueNodeAssignMapping.put(valueTreeSet, "Node"+countUnique);
				}
				
				
				//-------
				ArrayList<E> valuesEdgesCurrentNode=this.getArrayListOfEdges(CurrentNode, inputTreeMap);
				
				for(int i = 0;i<valuesEdgesCurrentNode.size();i++){
					if(uniqueNodeAssignMapping.get(valuesEdgesCurrentNode.get(i))!=null){
						if(!tsUniqueConnectionsOneWay.contains("\t"+uniqueNodeAssignMapping.get(CurrentNode)+"--"+uniqueNodeAssignMapping.get(valuesEdgesCurrentNode.get(i))+";\n")){
							tsUniqueConnections.add("\t"+uniqueNodeAssignMapping.get(CurrentNode)+"--"+uniqueNodeAssignMapping.get(valuesEdgesCurrentNode.get(i))+";\n");
							tsUniqueConnectionsOneWay.add("\t"+uniqueNodeAssignMapping.get(valuesEdgesCurrentNode.get(i))+"--"+uniqueNodeAssignMapping.get(CurrentNode)+";\n");	
						}
						
					}
						
				}
				
				//----------
				
			    if(VisitedNodes.contains(valueTreeSet)){
			    	//Visited
			    	//System.out.println("Visited="+valueTreeSet);
			    	VisitedNodes.add(valueTreeSet);
			    }else{
			    	//System.out.println("Added="+valueTreeSet);
			    	Queue.addLast(valueTreeSet);
			    	VisitedNodes.add(valueTreeSet);
			    	
			    	
			    	//TODO
			    	ArrayList<E> valuesEdges=this.getArrayListOfEdges(valueTreeSet, inputTreeMap);
			    	ArrayList<Integer> AlColorValues=new ArrayList<Integer>();
					//TODO
					for(int intValuesEdges=0;intValuesEdges<valuesEdges.size();intValuesEdges++){
						E currentEdgeNode=valuesEdges.get(intValuesEdges);
						if(!uniqueNodeColorMapping.containsKey(currentEdgeNode))uniqueNodeColorMapping.put(currentEdgeNode, -1);
						//System.out.print(currentEdgeNode+"("+uniqueNodeColorMapping.get(currentEdgeNode)+"), ");
						 AlColorValues.add(uniqueNodeColorMapping.get(currentEdgeNode));
						 
						
						 
					}
			    	
			    	uniqueNodeColorMapping.put(valueTreeSet, this.GetMinNum(uniqueNodeColorMapping.get(valueTreeSet), AlColorValues));
			    	//System.out.println(uniqueNodeAssignMapping.get(CurrentNode)+"--"+uniqueNodeAssignMapping.get(valueTreeSet));
	
			    	
			    	 
			    	 
			    	 
			    	//OutputString.append("\t"+uniqueNodeAssignMapping.get(CurrentNode)+"--"+uniqueNodeAssignMapping.get(valueTreeSet)+";\n");
			    	
			    	Output.add(valueTreeSet);
			    }
			}//end while
		}
		
		//System.out.println(uniqueNodeColorMapping);
		
		ArrayList<String> NodeConnections= new ArrayList<String>(tsUniqueConnections);
		
		for(int i=0;i<NodeConnections.size();i++){
			//OutputString.append(NodeConnections.get(i));
		}
		
		//Adding Node Information to End of file
				Iterator<Entry<E, String>> itHistory = uniqueNodeAssignMapping.entrySet().iterator();
				
				while (itHistory.hasNext()) {
					Entry<E, String> pairs = (Entry<E, String>)  itHistory.next();
				    
					E keyNode=pairs.getKey();
					String valueTreeSet=pairs.getValue();
					
					
					Integer intColorV=uniqueNodeColorMapping.get(keyNode)==null?-1:uniqueNodeColorMapping.get(keyNode);
					String ColorV=PickColor.getStringIndex(intColorV);
					
					OutputString.append(keyNode+"-"+ColorV+"\n");
				}

				
				
		return OutputString.toString();		
	}//end sub
	/**
	 * Get DFS 
	 * @param RootNode
	 * @return
	 */
	public ArrayList<E> getDFS(E RootNode){
		return getDFS(this.NodeEdge,RootNode);
	}
	
	/**
	 * GetDFS helper
	 * @param inputTreeMap
	 * @param RootNode
	 * @return
	 */
	private ArrayList<E> getDFS(TreeMap<E, TreeSet<E>> inputTreeMap, E RootNode){
		ArrayList<E> Output= new ArrayList<E>();
		//^^This ArrayList is Used to Store the Output Elements
		TreeSet<E> VisitedNodes= new TreeSet<E>();
		//^^This TreeSet is used to store the Visited Nodes
		ArrayDeque<E> Stack= new ArrayDeque<E>();
		//^^Stack is used for DFS
		Stack.addFirst(RootNode);
		//^^Adding First Node which was provided by the user

		while(!Stack.isEmpty()){
			E CurrentNode= Stack.getLast();
			//^^Gets the last item of the stack

			if(!VisitedNodes.contains(CurrentNode)){
				Output.add(CurrentNode);
			}

			VisitedNodes.add(CurrentNode);
			//System.out.println("Current Stack\t"+Stack); //Debugging
			//System.out.println("On Node\t"+CurrentNode); //Debugging
			//System.out.println("Visited Nodes: "+VisitedNodes); //Debugging
			//-----------------
			//ALG

			//Need to get Un-Visited Nodes

			ArrayList<E> ALEdges=this.getArrayListOfEdges(CurrentNode, inputTreeMap);

			int ALEdgesSize=ALEdges.size();
			for(int intEdge=0;intEdge<ALEdgesSize;intEdge++){
				E CurrentEdgeNode=ALEdges.get(intEdge);
				//System.out.println("\t"+CurrentNode+">>"+CurrentEdgeNode+" ("+intEdge+")\t Visited="+VisitedNodes.contains(CurrentEdgeNode));

				if(VisitedNodes.contains(CurrentEdgeNode)){
					//System.out.println("\t>>Delete="+CurrentEdgeNode);
					ALEdges.remove(intEdge);
					ALEdgesSize=ALEdges.size();
					intEdge=-1;
				}
			}

			if(ALEdges.size()==0){
				Stack.removeLast();
			}else{
				Stack.add(ALEdges.get(0));
			}
			//END ALG
			//-----------------
			//System.out.println("After Stack\t"+Stack);
			//System.out.println("After Output: "+ Output);
			//System.out.println("------------------------");
		}// while !Stack.isEmpty()
		return Output;
	}//end sub


	/**
	 * Get BFS
	 * @param RootNode
	 * @return
	 */
	public ArrayList<E> getBFS(E RootNode){
		return getBFS(this.NodeEdge,RootNode);
	}

	/**
	 * Get BFS helper
	 * @param inputTreeMap
	 * @param RootNode
	 * @return
	 */
	private ArrayList<E> getBFS(TreeMap<E, TreeSet<E>> inputTreeMap, E RootNode){
		TreeMap<E, TreeSet<E>> CloneMap= this.MapClone(inputTreeMap);
		ArrayList<E> Output= new ArrayList<E>();

		TreeSet<E> VisitedNodes= new TreeSet<E>();

		ArrayDeque<E> Queue= new ArrayDeque<E>();
		Queue.addFirst(RootNode);

		while(!Queue.isEmpty()){
			E CurrentNode= Queue.removeFirst();
			TreeSet<E> CurrentNodes=CloneMap.get(CurrentNode);

			if(Output.size()==0){
				VisitedNodes.add(RootNode);
				Output.add(RootNode);
			}

			Iterator<E> itHistory = CurrentNodes.iterator();
			//Gets Empty
			while (itHistory.hasNext()) {
				E valueTreeSet=itHistory.next();

				if(VisitedNodes.contains(valueTreeSet)){
					//Visited
					//System.out.println("Visited="+valueTreeSet);
					VisitedNodes.add(valueTreeSet);
				}else{
					//System.out.println("Added="+valueTreeSet);
					Queue.addLast(valueTreeSet);
					VisitedNodes.add(valueTreeSet);
					Output.add(valueTreeSet);
				}
			}//end while
		}
		return Output;	
	}//end sub

	/**
	 * Get BFS DotLanguage
	 * @param RootNode
	 * @return
	 */
	public String getBFSDotLanguage(E RootNode){
		return getBFSDotLanguage(this.NodeEdge,RootNode);
	}
	
	/**
	 * Get BFS helper
	 * @param inputTreeMap
	 * @param RootNode
	 * @return
	 */
	private String getBFSDotLanguage(TreeMap<E, TreeSet<E>> inputTreeMap, E RootNode){
		StringBuilder OutputString=new StringBuilder();
		//^^Used to create the String Output
		Colors PickColor= new Colors();
		//^^Color Object
		TreeMap<E,String> uniqueNodeAssignMapping= new TreeMap<E,String>();
		//^^Used to store Mapping
		int countUnique=0;
		
		OutputString.append("graph graph1 {\n");
		OutputString.append("\tgraph [overlap=false];\n");
	
		TreeSet<String> tsUniqueConnections= new TreeSet<String>();
		TreeSet<String> tsUniqueConnectionsOneWay= new TreeSet<String>();
		
		
		TreeMap<E, TreeSet<E>> CloneMap= this.MapClone(inputTreeMap);
		ArrayList<E> Output= new ArrayList<E>();
		TreeMap<E,Integer> uniqueNodeColorMapping= new TreeMap<E,Integer>();
		
		TreeSet<E> VisitedNodes= new TreeSet<E>();
		
		ArrayDeque<E> Queue= new ArrayDeque<E>();
		Queue.addFirst(RootNode);	
		
	
		while(!Queue.isEmpty()){
			E CurrentNode= Queue.removeFirst();
			TreeSet<E> CurrentNodes=CloneMap.get(CurrentNode);			
			 
			 
			
			if(Output.size()==0){
				VisitedNodes.add(RootNode);
				Output.add(RootNode);
				countUnique++;
				uniqueNodeAssignMapping.put(RootNode, "Node"+countUnique);
				uniqueNodeColorMapping.put(RootNode, 0);
				
			}

			Iterator<E> itHistory = CurrentNodes.iterator();
			//Gets Empty
			while (itHistory.hasNext()) {
				E valueTreeSet=itHistory.next();
				if(!uniqueNodeColorMapping.containsKey(valueTreeSet))uniqueNodeColorMapping.put(valueTreeSet, -1);
				
				if(uniqueNodeAssignMapping.containsKey(valueTreeSet)){
					//Exist
					//System.out.println(HistoryMap.get(keyNode));
					
				}else{
					//Not Exist
					countUnique++;
					uniqueNodeAssignMapping.put(valueTreeSet, "Node"+countUnique);
				}
				
				
				//-------
				ArrayList<E> valuesEdgesCurrentNode=this.getArrayListOfEdges(CurrentNode, inputTreeMap);
				
				for(int i = 0;i<valuesEdgesCurrentNode.size();i++){
					if(uniqueNodeAssignMapping.get(valuesEdgesCurrentNode.get(i))!=null){
						if(!tsUniqueConnectionsOneWay.contains("\t"+uniqueNodeAssignMapping.get(CurrentNode)+"--"+uniqueNodeAssignMapping.get(valuesEdgesCurrentNode.get(i))+";\n")){
							tsUniqueConnections.add("\t"+uniqueNodeAssignMapping.get(CurrentNode)+"--"+uniqueNodeAssignMapping.get(valuesEdgesCurrentNode.get(i))+";\n");
							tsUniqueConnectionsOneWay.add("\t"+uniqueNodeAssignMapping.get(valuesEdgesCurrentNode.get(i))+"--"+uniqueNodeAssignMapping.get(CurrentNode)+";\n");	
						}
						
					}
						
				}
				
				//----------
				
			    if(VisitedNodes.contains(valueTreeSet)){
			    	//Visited
			    	//System.out.println("Visited="+valueTreeSet);
			    	VisitedNodes.add(valueTreeSet);
			    }else{
			    	//System.out.println("Added="+valueTreeSet);
			    	Queue.addLast(valueTreeSet);
			    	VisitedNodes.add(valueTreeSet);
			    	
			    	
			    	//TODO
			    	ArrayList<E> valuesEdges=this.getArrayListOfEdges(valueTreeSet, inputTreeMap);
			    	ArrayList<Integer> AlColorValues=new ArrayList<Integer>();
					//TODO
					for(int intValuesEdges=0;intValuesEdges<valuesEdges.size();intValuesEdges++){
						E currentEdgeNode=valuesEdges.get(intValuesEdges);
						if(!uniqueNodeColorMapping.containsKey(currentEdgeNode))uniqueNodeColorMapping.put(currentEdgeNode, -1);
						//System.out.print(currentEdgeNode+"("+uniqueNodeColorMapping.get(currentEdgeNode)+"), ");
						 AlColorValues.add(uniqueNodeColorMapping.get(currentEdgeNode));
						 
						
						 
					}
			    	
			    	uniqueNodeColorMapping.put(valueTreeSet, this.GetMinNum(uniqueNodeColorMapping.get(valueTreeSet), AlColorValues));
			    	//System.out.println(uniqueNodeAssignMapping.get(CurrentNode)+"--"+uniqueNodeAssignMapping.get(valueTreeSet));
	
			    	
			    	 
			    	 
			    	 
			    	//OutputString.append("\t"+uniqueNodeAssignMapping.get(CurrentNode)+"--"+uniqueNodeAssignMapping.get(valueTreeSet)+";\n");
			    	
			    	Output.add(valueTreeSet);
			    }
			}//end while
		}
		
		//System.out.println(uniqueNodeColorMapping);
		
		ArrayList<String> NodeConnections= new ArrayList<String>(tsUniqueConnections);
		
		for(int i=0;i<NodeConnections.size();i++){
			OutputString.append(NodeConnections.get(i));
		}
		
		//Adding Node Information to End of file
				Iterator<Entry<E, String>> itHistory = uniqueNodeAssignMapping.entrySet().iterator();
				
				while (itHistory.hasNext()) {
					Entry<E, String> pairs = (Entry<E, String>)  itHistory.next();
				    
					E keyNode=pairs.getKey();
					String valueTreeSet=pairs.getValue();
					
					
					Integer intColorV=uniqueNodeColorMapping.get(keyNode)==null?-1:uniqueNodeColorMapping.get(keyNode);
					String ColorV=PickColor.getStringIndex(intColorV);
					
					OutputString.append("\t"+valueTreeSet+" [label=\""+keyNode+"\", style=filled, fillcolor=\""+ColorV+"\"];\n");
				}

				OutputString.append("}\n");
				
		return OutputString.toString();		
	}//end sub
		
	/**
	 * Get arrayList of Data in Nodes
	 * 
	 * @return
	 */
	private ArrayList<E> getArrayListOfEdges(E RootNode,TreeMap<E, TreeSet<E>> inputTreeMap) {
		if(inputTreeMap.containsKey(RootNode)){
			return new ArrayList<E>(inputTreeMap.get(RootNode));
		}else{
			return new ArrayList<E>();
		}
	}// end sub
	
	
	/**
	 * Geting Dot Language
	 * @return
	 */
	public String getDotLanguageV1(){
		return getDotLanguageV1(this.NodeEdge);
	}
	
	private String getDotLanguageV1(TreeMap<E, TreeSet<E>> inputTreeMap){
		StringBuilder Output=new StringBuilder();
		//^^Used to create the String Output
		TreeMap<E, TreeSet<E>> CloneMap= this.MapClone(inputTreeMap);
		//^^Clone Class Map to new Map for Modification Protection
		Colors PickColor= new Colors();
		//^^Color Object
		TreeMap<E,String> uniqueNodeAssignMapping= new TreeMap<E,String>();
		//^^Used to store Mapping
		int countUnique=0;
		//^^Used for String to Node* mapping	
		Output.append("graph graph1 {\n");
		Output.append("\tgraph [overlap=false];\n");
	
		//-------------------
		//Getting Nodes with no edges
		//-------------------
		Iterator<Entry<E, TreeSet<E>>> it = CloneMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<E, TreeSet<E>> pairs = (Entry<E, TreeSet<E>>) it
					.next();
		    
			E keyNode=pairs.getKey();
			TreeSet<E> valueTreeSet=pairs.getValue();
			
			if(valueTreeSet.size()==0){
				//System.out.println(""+keyNode + " -- " + currentValueTreeSetNode);	
				if(uniqueNodeAssignMapping.containsKey(keyNode)){
					//Exist
					//System.out.println(HistoryMap.get(keyNode));
					
				}else{
					//Not Exist
					countUnique++;
					uniqueNodeAssignMapping.put(keyNode, "Node"+countUnique);
				}
				Output.append("\t"+uniqueNodeAssignMapping.get(keyNode)+";\n");
			}
			//it.remove(); // avoids a ConcurrentModificationException
		}
		
		//-------------------
		//Going Through Tree
		//-------------------
		TreeMap<E,Integer> ColorValue= new TreeMap<E,Integer>(); 
		TreeSet<E> ColorValueVisited= new TreeSet<E>(); 
		
		
		it = CloneMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<E, TreeSet<E>> pairs = (Entry<E, TreeSet<E>>) it
					.next();
		    
			E keyNode=pairs.getKey();
			TreeSet<E> valueTreeSet=pairs.getValue();
			
			Iterator<E> itValueTreeSet = valueTreeSet.iterator();
			
			while(itValueTreeSet.hasNext()){
				E currentValueTreeSetNode=itValueTreeSet.next();				
	
				if(uniqueNodeAssignMapping.containsKey(keyNode)){
					//Exist
					//System.out.println(HistoryMap.get(keyNode));					
					if(uniqueNodeAssignMapping.containsKey(currentValueTreeSetNode)){
						//Exist
					}else{
						countUnique++;
						uniqueNodeAssignMapping.put(currentValueTreeSetNode, "Node"+countUnique);
					}
				}else{
					//Not Exist
					countUnique++;
					uniqueNodeAssignMapping.put(keyNode, "Node"+countUnique);
					
					if(uniqueNodeAssignMapping.containsKey(currentValueTreeSetNode)){
						//Exist
					}else{
						countUnique++;
						uniqueNodeAssignMapping.put(currentValueTreeSetNode, "Node"+countUnique);
					}
				}
				//////
				if(!ColorValue.containsKey(keyNode))ColorValue.put(keyNode, 0);
				if(!ColorValue.containsKey(currentValueTreeSetNode))ColorValue.put(currentValueTreeSetNode, 0);

				
				//System.out.println("Before="+keyNode+"("+HistoryMap.get(keyNode)+">"+ColorValue.get(keyNode)+
				//		 ") -- " + 
				//		currentValueTreeSetNode+"("+HistoryMap.get(currentValueTreeSetNode)+">"+ColorValue.get(currentValueTreeSetNode)+")");
				
			
				
				ColorValueVisited.add(keyNode);//VISITED Don't add no more
				
				ArrayList<E> ALEdgesOfCurrentNode=this.getArrayListOfEdges(keyNode, inputTreeMap);
				//System.out.println(ALEdgesOfCurrentNode);
				//System.out.println(ColorValueVisited);
				

				//Sets all Edges to 0 if null
				for(int i2=0;i2<ALEdgesOfCurrentNode.size();i2++){
					if(ColorValue.get(ALEdgesOfCurrentNode.get(i2))==null){
						ColorValue.put(ALEdgesOfCurrentNode.get(i2), 0);
					}	
				}//end for
				
				//See all Edge;
				TreeSet<Integer> TempTreeSet=new TreeSet<Integer>();
				for(int i2=0;i2<ALEdgesOfCurrentNode.size();i2++){
					TempTreeSet.add(ColorValue.get(ALEdgesOfCurrentNode.get(i2)));
					//System.out.print(ColorValue.get(ALEdgesOfCurrentNode.get(i2))+", ");
				}
				//System.out.println();
				//System.out.println(TempTreeSet);
				
				
				ArrayList<Integer> TempAL1= new ArrayList<Integer>(TempTreeSet);
			
				int min=0;
				int current=0;
				while(current>=0&&current<TempAL1.size()){
					if(TempAL1.get(current)==min){
						current++;
						min++;
					}else{
						min=current;
						break;
					}
					
				}
				//System.out.println(min);
				
				for(int i2=0;i2<ALEdgesOfCurrentNode.size();i2++){
					//System.out.println("\t"+ALEdgesOfCurrentNode.get(i2)+">"+ColorValue.get(ALEdgesOfCurrentNode.get(i2)).toString());
					
					if(!ColorValueVisited.contains(ALEdgesOfCurrentNode.get(i2))){
						
						int num1=ColorValue.get(keyNode)+1;
						int num2=min;
						
						int numRes=-1;
						if(num1<=num2){
							numRes=num1;
						}else{
							numRes=num2;
						}
						ColorValue.put(ALEdgesOfCurrentNode.get(i2), numRes);
					}
				}
				
				if(ColorValue.get(keyNode)!=ColorValue.get(currentValueTreeSetNode)){
					
					ColorValueVisited.add(currentValueTreeSetNode);//VISITED Don't add no more
				}else{
					ColorValueVisited.remove(currentValueTreeSetNode);
				}
				
				//System.out.println("After="+keyNode+"("+HistoryMap.get(keyNode)+">"+ColorValue.get(keyNode)+
					//	 ") -- " + 
					//	currentValueTreeSetNode+"("+HistoryMap.get(currentValueTreeSetNode)+">"+ColorValue.get(currentValueTreeSetNode)+")");
				
				//System.out.println("----------------");
				
				Output.append("\t"+uniqueNodeAssignMapping.get(keyNode) + " -- " + uniqueNodeAssignMapping.get(currentValueTreeSetNode)+";\n");
				this.deleteEdgeOnOneNode(currentValueTreeSetNode, keyNode, CloneMap);
			}
			//it.remove(); // avoids a ConcurrentModificationException
		}
				
		//----------------------------
		//Adding Node Information to End of file
		Iterator<Entry<E, String>> itHistory = uniqueNodeAssignMapping.entrySet().iterator();
		
		while (itHistory.hasNext()) {
			Entry<E, String> pairs = (Entry<E, String>)  itHistory
					.next();
		    
			E keyNode=pairs.getKey();
			String valueTreeSet=pairs.getValue();
			
			String ColorV=ColorValue.get(keyNode)==null?"White":PickColor.getStringIndex(ColorValue.get(keyNode));
		    Output.append("\t"+valueTreeSet+" [label=\""+keyNode+"\" style=filled, fillcolor=\""+ColorV+"\"];\n");
		
			//it.remove(); // avoids a ConcurrentModificationException
		}
		
		Output.append("}\n");
		return Output.toString();		
	}//end sub
	
	/**
	 * Get DotLanguage V2
	 * @param inputTreeMap
	 * @return
	 */
	
	public String getDotLanguageV2(){
		return getDotLanguageV2(this.NodeEdge);
	}
	
	//TODO
	//TODO
	private boolean getDotAssignHelper(Integer countUniqueInput,TreeMap<E,String> uniqueNodeAssignMapping,E Node){
		if(uniqueNodeAssignMapping.containsKey(Node)){
			//Exist
			
			return true;
		}else{
			//Not Exist
			
			//System.out.println(">>>>>>"+Node);
			countUniqueInput++;
			uniqueNodeAssignMapping.put(Node, "Node"+countUniqueInput);	
			return false;
		}
	}//End getDotAssignHelper()
	
	private void getDotMakeOneWayHelper(TreeMap<E, TreeSet<E>> CloneMap, E keyNode, E currentEdgeNode){
		if(CloneMap.containsKey(currentEdgeNode)){
			if(CloneMap.get(currentEdgeNode).contains(keyNode)){
				CloneMap.get(currentEdgeNode).remove(keyNode);
				if(CloneMap.get(currentEdgeNode).size()==0){
					CloneMap.put(currentEdgeNode, null);
				}		
			}
		}
	}//end getDotMakeOneWayHelper()
	
	public Integer GetMinNum(int curNode,ArrayList<Integer> Input){
		Collections.sort(Input);
		//System.out.println("ArrayList:"+Input);
		//System.out.println("ArrayList Size:"+Input.size());
		
		int min=0;
		int Seq=0;
		
		int current=0;
		while(current>=0&&current<Input.size()){
			int currentNumber=Input.get(current);
			//System.out.println("Input.get("+current+")"+currentNumber);
			
			if(currentNumber==-1){
				//skip
			}else if(currentNumber>=0){
				
				//System.out.println("Cur:"+currentNumber+"\tSeq:"+Seq);
				
				if(currentNumber>Seq){
					//System.out.println("-------");
					min=Seq;
					break;
				}else{
					min=currentNumber+1;
				}
				
				if(currentNumber==Seq)Seq++;
			}
			
			current++;
		}
		
		
		//System.out.println("MinValue="+min);
		if(curNode==-1){
			return min;
		}else{
			return curNode;
		}
		
	}//end sub
	
	private void getDotAssignColor(TreeMap<E, TreeSet<E>> CloneMap, TreeMap<E, Integer> uniqueNodeColorMapping){
		// TreeMap<E,String> uniqueNodeAssignMapping  is used to get nodeEdges
		//uniqueNodeColorMapping
		Iterator<Entry<E, TreeSet<E>>> it = CloneMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<E, TreeSet<E>> pairs = (Entry<E, TreeSet<E>>)it.next();
		    //^^Get Pairs
			E keyNode=pairs.getKey();
			if(!uniqueNodeColorMapping.containsKey(keyNode))uniqueNodeColorMapping.put(keyNode, -1);
			ArrayList<E> valuesEdges= pairs.getValue()==null?new ArrayList<E>():new ArrayList<E>(pairs.getValue());


			if(valuesEdges.size()==0){//Add_Nodes_with_no_Edges
				uniqueNodeColorMapping.put(keyNode, 0);
			}else{
				
				ArrayList<Integer> AlColorValues=new ArrayList<Integer>();
				//TODO
				for(int intValuesEdges=0;intValuesEdges<valuesEdges.size();intValuesEdges++){
					E currentEdgeNode=valuesEdges.get(intValuesEdges);
					if(!uniqueNodeColorMapping.containsKey(currentEdgeNode))uniqueNodeColorMapping.put(currentEdgeNode, -1);
					//System.out.print(currentEdgeNode+"("+uniqueNodeColorMapping.get(currentEdgeNode)+"), ");
					 AlColorValues.add(uniqueNodeColorMapping.get(currentEdgeNode));
					
				}
				
				uniqueNodeColorMapping.put(keyNode, this.GetMinNum(uniqueNodeColorMapping.get(keyNode), AlColorValues));
				
				//System.out.println("");	
			}
			//System.out.println("On Node >>\t"+keyNode+"("+uniqueNodeColorMapping.get(keyNode)+") ");
		}
			
		
		
	}//endDotAssignC
	
	private String getDotLanguageV2(TreeMap<E, TreeSet<E>> inputTreeMap){
		StringBuilder Output=new StringBuilder();
		StringBuilder OutputNodeData=new StringBuilder();
		//^^Used to create the String Output
		TreeMap<E, TreeSet<E>> CloneMap= this.MapClone(inputTreeMap);
		//^^Clone Class Map to new Map for Modification Protection
		Colors PickColor= new Colors();
		//^^Color Object
		TreeMap<E,Integer> uniqueNodeColorMapping= new TreeMap<E,Integer>();
		//^^Used to store Color
		TreeMap<E,String> uniqueNodeAssignMapping= new TreeMap<E,String>();
		//^^Used to store Mapping
		Integer countUnique=0;
		//^^Used for String to Node* mapping	
		this.getDotAssignColor(CloneMap, uniqueNodeColorMapping);
		//^^Assigns all the colors to the Nodes
		
		
		Output.append("graph graph1 {\n");
		Output.append("\tgraph [overlap=false];\n");
	
		//-------------------
		//-------------------
		Iterator<Entry<E, TreeSet<E>>> it = CloneMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<E, TreeSet<E>> pairs = (Entry<E, TreeSet<E>>)it.next();
		    //^^Get Pairs
			E keyNode=pairs.getKey();
			if(!this.getDotAssignHelper(countUnique, uniqueNodeAssignMapping, keyNode))countUnique++;
			
			ArrayList<E> valuesEdges= pairs.getValue()==null?null:new ArrayList<E>(pairs.getValue());
			//^^Get All Edges for Current Node
			if(valuesEdges!=null){//if_ArrayList_null_skip
				for(int intValuesEdges=0;intValuesEdges<valuesEdges.size();intValuesEdges++){
					E currentEdgeNode=valuesEdges.get(intValuesEdges);
					
					if(!this.getDotAssignHelper(countUnique, uniqueNodeAssignMapping, currentEdgeNode))countUnique++;
					//^^Assign Node to Unique ID And Init Color Mapping
					this.getDotMakeOneWayHelper(CloneMap, keyNode, currentEdgeNode);
					//^^Make Edge go only 1 way if it has 2 edges

					
					//|v| 
					//uniqueNodeColorMapping.put(keyNode, 1);
					
	

					//System.out.println(keyNode + "("
					//		+ uniqueNodeAssignMapping.get(keyNode) + ")"
					//		+ " > " + PickColor.getStringIndex(uniqueNodeColorMapping.get(keyNode))
					//		+"\t"
					//		+ currentEdgeNode + "("
					//		+ uniqueNodeAssignMapping.get(currentEdgeNode)
					//		+ ")"
					//		+ " > " + PickColor.getStringIndex(uniqueNodeColorMapping.get(currentEdgeNode)));
					
					Output.append("\t"+uniqueNodeAssignMapping.get(keyNode)+" -- "+ uniqueNodeAssignMapping.get(currentEdgeNode)+";\n");
					
				}//end loop


				if(valuesEdges.size()==0){//Add_Nodes_with_no_Edges
					//System.out.println(keyNode+"("+uniqueNodeAssignMapping.get(keyNode)+")\t"+valuesEdges);
					Output.append("\t"+uniqueNodeAssignMapping.get(keyNode)+";\n");
				}
				
				
				//String CurrentKeyNodeColor=PickColor.getStringIndex(uniqueNodeColorMapping.get(keyNode));
				//System.out.println(uniqueNodeAssignMapping.get(keyNode)
				//		+ ">>"
				//		+ keyNode
				//		+ "\t"
				//		+ CurrentKeyNodeColor);
			}//valuesEdges!=null
		}//end while
		//-------------------
		//-------------------
		
		
		Output.append(OutputNodeData.toString());
		//----------------------------
		//Adding Node Information to End of file
		Iterator<Entry<E, String>> itHistory = uniqueNodeAssignMapping.entrySet().iterator();
		
		while (itHistory.hasNext()) {
			Entry<E, String> pairs = (Entry<E, String>)  itHistory.next();
		    
			E keyNode=pairs.getKey();
			String valueTreeSet=pairs.getValue();
			
			
			Integer intColorV=uniqueNodeColorMapping.get(keyNode)==null?-1:uniqueNodeColorMapping.get(keyNode);
			String ColorV=PickColor.getStringIndex(intColorV);
			
			Output.append("\t"+valueTreeSet+" [label=\""+keyNode+"\", style=filled, fillcolor=\""+ColorV+"\"];\n");
		}

		Output.append("}\n");
		return Output.toString();		
	}//end sub
	/**
	 * Cloning the Graph
	 */
	public GenericGraphSC<E> clone(){
		GenericGraphSC<E> Clone= new GenericGraphSC<E>();
		Clone.NodeEdge=this.MapClone(this.NodeEdge);
		return Clone;
	}
	
	/**
	 * Cloning the Map
	 * @return
	 */
	private TreeMap<E, TreeSet<E>> MapClone(TreeMap<E, TreeSet<E>> inputTreeMap){
		TreeMap<E, TreeSet<E>> TempNodeEdge = new TreeMap<E, TreeSet<E>>();
		if(inputTreeMap.size()==0){
			//Nothing to Copy
		}else{
			Iterator<Entry<E, TreeSet<E>>> it = inputTreeMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry<E, TreeSet<E>> pairs = (Entry<E, TreeSet<E>>) it
						.next();
				// System.out.println(pairs.getKey() + " = " + pairs.getValue());
				
				TreeSet<E> ClonedTreeSet= new TreeSet<E>();
			
				Iterator<E> itClonedTreeSet = pairs.getValue().iterator();
				
				while(itClonedTreeSet.hasNext()){
					ClonedTreeSet.add(itClonedTreeSet.next());
				}
				
				TempNodeEdge.put(pairs.getKey(), ClonedTreeSet);
				//it.remove(); // avoids a ConcurrentModificationException
			}
		}
		return TempNodeEdge;
	}
	
	/**
	 * AddNode
	 * @param Item
	 * @return
	 */
	public boolean addNode(E Item) {
		return addNode(Item,this.NodeEdge);
	}

	/**
	 * Supporting AddNode
	 * @param Item
	 * @param inputTreeMap
	 * @return
	 */
	private boolean addNode(E Item,TreeMap<E, TreeSet<E>> inputTreeMap) {
		if (checkIfNodeExist(Item,inputTreeMap)) {
			return false;
		} else {
			inputTreeMap.put(Item, new TreeSet<E>());
			return true;
		}
	}
	/**
	 * Delete Node if no Edges
	 * @param MainNode
	 * @return
	 */
	public boolean deleteNodeIfNoEdges(E MainNode){
		return this.deleteNodeIfNoEdges(MainNode,this.NodeEdge);
	}//
	
	private boolean deleteNodeIfNoEdges(E MainNode,TreeMap<E, TreeSet<E>> inputTreeMap){
		if(this.checkIfNodeExist(MainNode,inputTreeMap)){
			TreeSet<E> TreeSetNodes=inputTreeMap.get(MainNode);
			
			if(TreeSetNodes.size()==0){
				inputTreeMap.remove(MainNode);
				return true;
			}else{
				return false;
			}
		}else{
			//not exist
			return false;
		}		
	}//
	
	/**
	 * Delete Edge Node
	 * @param MainNode
	 * @param EdgeNode
	 * @return
	 */
	public boolean deleteEdge(E Node1, E Node2){
		return deleteEdge(Node1,Node2,this.NodeEdge);
	}
	
	
	private boolean deleteEdge(E Node1, E Node2,TreeMap<E, TreeSet<E>> inputTreeMap){
		return deleteEdgeOnOneNode(Node1,Node2,inputTreeMap)&&deleteEdgeOnOneNode(Node2,Node1,inputTreeMap);
	}
	
	private boolean deleteEdgeOnOneNode(E MainNode, E EdgeNode,TreeMap<E, TreeSet<E>> inputTreeMap){
		//System.out.println("(this.checkIfNodeExist(MainNode)="+this.checkIfNodeExist(MainNode,inputTreeMap));
		if(this.checkIfNodeExist(MainNode,inputTreeMap)){
			TreeSet<E> TreeSetNodes=inputTreeMap.get(MainNode);
			
			//System.out.println("TreeSetNodes.contains(EdgeNode)="+TreeSetNodes.contains(new E(EdgeNode)));
			
			if(TreeSetNodes.contains(EdgeNode)){
				//Exist
				TreeSetNodes.remove(EdgeNode);
				return true;
			}else{
				//not exist
				return false;
			}
		}else{
			//not exist
			return false;
		}		
	}//end sub 
	
	/**
	 * Check if Node Exist
	 * 
	 * @param Item
	 * @return
	 */
	public boolean checkIfNodeExist(E Item,TreeMap<E, TreeSet<E>> inputTreeMap) {
		if (NodeEdge.containsKey(Item)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check if Node Exist Helper
	 * @param Node1
	 * @param Node2
	 * @param inputTreeMap
	 * @return
	 */
	private boolean checkIfEdgeExist(E Node1, E Node2,TreeMap<E, TreeSet<E>> inputTreeMap) {
		// Checks if Node1 and Node2 exist
		if (checkIfNodeExist(Node1,inputTreeMap) && checkIfNodeExist(Node2,inputTreeMap)) {
			// Checks if Edges exists

			TreeSet<E> Node1Connections = inputTreeMap
					.get(Node1);
			TreeSet<E> Node2Connections = inputTreeMap
					.get(Node2);

			if (Node1Connections.contains(Node2)
					&& Node2Connections.contains(Node1)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}// end sub
	
	/**
	 * add Edge
	 * @param Node1
	 * @param Node2
	 * @return
	 */
	public boolean addEdge(E Node1, E Node2) {
		return addEdge(Node1, Node2, this.NodeEdge,false);
	}// end sub
	
	public boolean addEdge(E Node1, E Node2, boolean CreateNodeIfNotExist) {
		return addEdge(Node1, Node2, this.NodeEdge,CreateNodeIfNotExist);
	}// end sub
	
	
	public boolean addEdge(E Node1, E Node2,TreeMap<E, TreeSet<E>> inputTreeMap,boolean CreateNodeIfNotExist) {
		
		if(CreateNodeIfNotExist){
			if(!checkIfNodeExist(Node1,inputTreeMap)){
				this.addNode(Node1, inputTreeMap);
			}
			
			if(!checkIfNodeExist(Node2,inputTreeMap)){
				this.addNode(Node2, inputTreeMap);
			}
			
		}else{
			if(!checkIfNodeExist(Node1,inputTreeMap)||!checkIfNodeExist(Node2,inputTreeMap)){
				return false;
			}	
		}
		
		
		
		if(checkIfEdgeExist(Node1,Node2,inputTreeMap)){
			//EdgeExist
			//System.err.println("Edge Exists");
			return false;
		}else{
			TreeSet<E> Node1Connections = inputTreeMap
					.get(Node1);
			TreeSet<E> Node2Connections = inputTreeMap
					.get(Node2);
			
			
			Node1Connections.add(Node2);
			Node2Connections.add(Node1);
			return true;
		}
	}// end sub

	/**
	 * Get arrayList of Data in Nodes
	 * 
	 * @return
	 */
	public ArrayList<E> getListOfNodesData() {
		ArrayList<E> ALData = new ArrayList<E>();

		Iterator<Entry<E, TreeSet<E>>> it = NodeEdge.entrySet().iterator();
		while (it.hasNext()) {
			Entry<E, TreeSet<E>> pairs = (Entry<E, TreeSet<E>>) it
					.next();
			// System.out.println(pairs.getKey() + " = " + pairs.getValue());
			ALData.add(pairs.getKey());
			//it.remove(); // avoids a ConcurrentModificationException
		}
		return ALData;
	}// end sub

	/**
	 * ToString()
	 */
	public String toString(){
		StringBuilder Output=new StringBuilder();
		
		//System.out.println(">>>>Start<<<<");
		
		ArrayList<E> ListOfNode= this.getListOfNodesData();
		//System.out.println(ListOfNode);
		for(int i = 0 ; i<ListOfNode.size();i++){
			//System.out.println("I"+i);
			E CurrentNode=ListOfNode.get(i);
			TreeSet<E> Node1Connections = NodeEdge.get(CurrentNode);
			
			Output.append(CurrentNode+"\t>>>\t"+new ArrayList<E>(Node1Connections)+"\n");
		}
		return Output.toString();
	}
	
	/**
	 * SaveFormatV1
	 */
	public String toStringSaveFormatV1(){
		StringBuilder Output=new StringBuilder();
		
		//System.out.println(">>>>Start<<<<");
		

		ArrayList<E> ListOfNode= this.getListOfNodesData();
		TreeMap<E,String> MappingNumber= new TreeMap<E,String>();
		
		for(int i = 0 ; i<ListOfNode.size();i++){
			E CurrentNode=ListOfNode.get(i);
			MappingNumber.put(CurrentNode, i+1+"");
		}


		//System.out.println(ListOfNode);
		for(int i = 0 ; i<ListOfNode.size();i++){
			//System.out.println("I"+i);
			E CurrentNode=ListOfNode.get(i);
			TreeSet<E> Node1Connections = NodeEdge.get(CurrentNode);
			
			ArrayList<E> ALEdgeNodes=new ArrayList<E>(Node1Connections);
			
			StringBuilder ALPOutput=new StringBuilder();
			
			for(int ja=0;ja<ALEdgeNodes.size();ja++){
				ALPOutput.append(MappingNumber.get(ALEdgeNodes.get(ja))+" ");
			}
				
				
			//Output.append(CurrentNode+"\t>>>\t"+ALPOutput.toString().trim()+"\n");
			Output.append(""+ALPOutput.toString().trim()+"\n");
		}
		
		Output.append("where"+"\n");
		
		for(int i = 0 ; i<ListOfNode.size();i++){
			E CurrentNode=ListOfNode.get(i);
			Output.append((i+1)+"\t"+CurrentNode+"\n");
			
		}
		
		return Output.toString();
	}
	
	/**
	 * Get hashCode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((NodeEdge == null) ? 0 : NodeEdge.hashCode());
		return result;
	}

	/**
	 * Equals
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericGraphSC<E> other = (GenericGraphSC<E>) obj;
		if (NodeEdge == null) {
			if (other.NodeEdge != null)
				return false;
		} else if (!NodeEdge.equals(other.NodeEdge))
			return false;
		return true;
	}

}
