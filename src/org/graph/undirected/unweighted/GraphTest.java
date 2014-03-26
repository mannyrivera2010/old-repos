package org.graph.undirected.unweighted;

public class GraphTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GenericGraphNodeLess<String> GraphObj= new GenericGraphNodeLess<String>();
	
		GraphObj.addNode("A");
		GraphObj.addNode("B");
		GraphObj.addNode("C");
		GraphObj.addNode("D");
		GraphObj.addNode("E");
		GraphObj.addNode("F");
		GraphObj.addNode("G");
		GraphObj.addNode("H");
		GraphObj.addNode("X");
		
		GraphObj.addEdge("A", "B");
		GraphObj.addEdge("B", "E");
		GraphObj.addEdge("E", "G");
		GraphObj.addEdge("G", "A");
		GraphObj.addEdge("A", "D");
		GraphObj.addEdge("D", "F");
		GraphObj.addEdge("F", "B");
		GraphObj.addEdge("F", "C");
		GraphObj.addEdge("C", "H");
		//GraphObj.addEdge("G", "X");
			
		System.out.println("Graph \n"+GraphObj.toString());
		
		GraphObj.getDotLanguage();
		System.out.println("Dot \n"+GraphObj.getDotLanguage());
		System.out.println("-------------");
		//System.out.println(GraphObj.deleteEdge("A","D"));
		//System.out.println("Graph \n"+GraphObj.toString());
		//System.out.println("Dot \n"+GraphObj.getDotLanguage());
		//System.out.println("-------------");
		System.out.println(GraphObj.getBFS("A"));
		
		
		
		
	}//end sub

}
