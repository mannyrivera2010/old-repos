package org.graph.undirected.unweighted;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Iterator;

import java.util.Map.Entry;

public class GenericGraphNodeLess<E extends Comparable<E>> {
	private TreeMap<E, TreeSet<E>> NodeEdge = new TreeMap<E, TreeSet<E>>();
	// TreeMap = Node
	// TreeSet = Connected Nodes

	
	public ArrayList<E> getBFS(E RootNode){
		return getBFS(this.NodeEdge,RootNode);
	}
	
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
	
	private ArrayList<E> getBFSColor(TreeMap<E, TreeSet<E>> inputTreeMap, E RootNode){
		TreeMap<E, TreeSet<E>> CloneMap= this.MapClone(inputTreeMap);
		ArrayList<E> Output= new ArrayList<E>();
		
		TreeSet<E> VisitedNodes= new TreeSet<E>();
		
		TreeMap<E,Integer> ColorValue=new TreeMap<E,Integer>();
	
		ArrayDeque<E> Queue= new ArrayDeque<E>();
		Queue.addFirst(RootNode);
	
		
		while(!Queue.isEmpty()){
			E CurrentNode= Queue.removeFirst();
			TreeSet<E> CurrentNodes=CloneMap.get(CurrentNode);
			
			if(Output.size()==0){
				VisitedNodes.add(RootNode);
				Output.add(RootNode);
				ColorValue.put(RootNode, 1);
			}
			
			//System.out.println("==========================");
			//System.out.println("Before - Queue="+Queue);
			//System.out.println("Before - Visited Nodes="+VisitedNodes);
			//System.out.println("-----------------");
			//Getting Node Info
			
			//TODO Get color value working
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
			    	
			    	if(!ColorValue.containsKey(valueTreeSet)){
			    		ColorValue.put(valueTreeSet, 0);
			    	}
			    	
			    	
			    	TreeSet<E> ColorNodes=CloneMap.get(valueTreeSet);
			    	Iterator<E> itColorNodes = ColorNodes.iterator();
			    	
			    	
			    	ArrayList<Integer> Max= new ArrayList<Integer>();
			    	
			    	while(itColorNodes.hasNext()){
			    		E CurrentColorNode=itColorNodes.next();
			    		
			    		if(!ColorValue.containsKey(CurrentColorNode)){
				    		ColorValue.put(CurrentColorNode, 0);
				    	}
			    		
			    		Max.add((ColorValue.get(CurrentColorNode)+1));
			    		
			    	
			    	}
			    	Collections.sort(Max);
			    	
			    	System.out.println(""+Max);
			    	int intMax=1;
			    	int intPrev=1;
			    	
			    	for(int SI=0;SI<Max.size();SI++){
			    		
			    		if(intMax==Max.get(SI)){
			    			//Match
			    			
			    			if(intPrev<=Max.get(SI)){
			    				//same Do nothing
			    				System.out.println("Same>>"+intPrev+"=="+Max.get(SI));
			    			}else{
			    				System.out.println("Not Same >>"+intPrev+"=="+Max.get(SI));
			    				intMax++;
			    			}
			    		
			    			
			    		}else{
			    			intMax=Max.get(SI)+1;
			    		}
			    		
			    		intPrev=Max.get(SI);
			    	}
			    
			    	ColorValue.put(valueTreeSet, intMax);
			    	
			    	Queue.addLast(valueTreeSet);
			    	VisitedNodes.add(valueTreeSet);
			    	Output.add(valueTreeSet);
			    	
			    	System.out.println(CurrentNode+"("+ColorValue.get(CurrentNode)+
			    			")->"+valueTreeSet+"("+ColorValue.get(valueTreeSet)+")");
			    	
			    }
			}//end while
			//System.out.println("-----------------");
			//System.out.println("After - Queue="+Queue);
			//System.out.println("After - Visited Nodes="+VisitedNodes);
			//System.out.println("==========================");
			
		}
		
		
		
		
		return Output;		
	}
	
	/**
	 * Geting Dot Language
	 * @return
	 */
	public String getDotLanguage(){
		return getDotLanguage(this.NodeEdge);
	}
	
	private String getDotLanguage(TreeMap<E, TreeSet<E>> inputTreeMap){
		TreeMap<E, TreeSet<E>> CloneMap= this.MapClone(inputTreeMap);
		
		StringBuilder Output=new StringBuilder();
		
		int countUnique=0;
		TreeMap<E,String> HistoryMap= new TreeMap<E,String>();
	
		
		Output.append("graph graph1 {\n");
		Output.append("\tgraph [overlap=false];\n");
	
		Iterator<Entry<E, TreeSet<E>>> it = CloneMap.entrySet().iterator();
		//Gets Empty
		while (it.hasNext()) {
			Entry<E, TreeSet<E>> pairs = (Entry<E, TreeSet<E>>) it
					.next();
		    
			E keyNode=pairs.getKey();
			TreeSet<E> valueTreeSet=pairs.getValue();
			
			if(valueTreeSet.size()==0){
				//System.out.println(""+keyNode + " -- " + currentValueTreeSetNode);	
				if(HistoryMap.containsKey(keyNode)){
					//Exist
					//System.out.println(HistoryMap.get(keyNode));
					
				}else{
					//Not Exist
					countUnique++;
					HistoryMap.put(keyNode, "Node"+countUnique);
				}
				Output.append("\t"+HistoryMap.get(keyNode)+"\n");
			}
			//it.remove(); // avoids a ConcurrentModificationException
		}
		
		
		it = CloneMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<E, TreeSet<E>> pairs = (Entry<E, TreeSet<E>>) it
					.next();
		    
			E keyNode=pairs.getKey();
			TreeSet<E> valueTreeSet=pairs.getValue();
			
			Iterator<E> itValueTreeSet = valueTreeSet.iterator();
			
			while(itValueTreeSet.hasNext()){
				E currentValueTreeSetNode=itValueTreeSet.next();
				
				//System.out.println(""+keyNode + " -- " + currentValueTreeSetNode);
				if(HistoryMap.containsKey(keyNode)){
					//Exist
					//System.out.println(HistoryMap.get(keyNode));
				
					
					if(HistoryMap.containsKey(currentValueTreeSetNode)){
						//Exist
					}else{
						countUnique++;
						HistoryMap.put(currentValueTreeSetNode, "Node"+countUnique);
					}
				}else{
					//Not Exist
					countUnique++;
					HistoryMap.put(keyNode, "Node"+countUnique);
					
					if(HistoryMap.containsKey(currentValueTreeSetNode)){
						//Exist
					}else{
						countUnique++;
						HistoryMap.put(currentValueTreeSetNode, "Node"+countUnique);
					}
				}
				
				Output.append("\t"+HistoryMap.get(keyNode) + " -- " + HistoryMap.get(currentValueTreeSetNode)+"\n");
				this.deleteEdgeOnOneNode(currentValueTreeSetNode, keyNode, CloneMap);
			}
			
			//it.remove(); // avoids a ConcurrentModificationException
		}
		
		//Getting Node Info
		Iterator<Entry<E, String>> itHistory = HistoryMap.entrySet().iterator();
		//Gets Empty
		while (itHistory.hasNext()) {
			Entry<E, String> pairs = (Entry<E, String>)  itHistory
					.next();
		    
			E keyNode=pairs.getKey();
			String valueTreeSet=pairs.getValue();
			

		    Output.append("\t"+valueTreeSet+" [label=\""+keyNode+"\"];\n");
		
			//it.remove(); // avoids a ConcurrentModificationException
		}
		
		
		Output.append("}\n");
		return Output.toString();		
	}
	
	
	/**
	 * Cloning the Graph
	 */
	public GenericGraphNodeLess<E> clone(){
		GenericGraphNodeLess<E> Clone= new GenericGraphNodeLess<E>();
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
	
	
	/*
	 
	 
	 public boolean deleteEdgeOnOneNode(E MainNode, E EdgeNode){
		return deleteEdgeOnOneNode(MainNode,EdgeNode,this.NodeEdge);
	}//end sub
	 
	 */
	
	
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
		return addEdge(Node1, Node2, this.NodeEdge);
	}// end sub
	
	
	public boolean addEdge(E Node1, E Node2,TreeMap<E, TreeSet<E>> inputTreeMap) {
		if(!checkIfNodeExist(Node1,inputTreeMap)||!checkIfNodeExist(Node2,inputTreeMap)){
			return false;
		}
		
		if(checkIfEdgeExist(Node1,Node2,inputTreeMap)){
			//EdgeExist
			System.err.println("Edge Exists");
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
		GenericGraphNodeLess<E> other = (GenericGraphNodeLess<E>) obj;
		if (NodeEdge == null) {
			if (other.NodeEdge != null)
				return false;
		} else if (!NodeEdge.equals(other.NodeEdge))
			return false;
		return true;
	}

}
