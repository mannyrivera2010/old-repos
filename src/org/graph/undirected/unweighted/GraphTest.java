package org.graph.undirected.unweighted;


import java.util.ArrayList;

public class GraphTest {

/**
* @param args
*/
public static void main(String[] args) {
Dia1();
}//end sub


public static void Dia1(){
GenericGraphSC<String> GraphObj= new GenericGraphSC<String>();






GraphObj.addEdge("201", "303",true);
GraphObj.addEdge("203", "201",true);
GraphObj.addEdge("204", "201",true);
GraphObj.addEdge("204", "203",true);
GraphObj.addEdge("301", "204",true);
GraphObj.addEdge("302", "203",true);
GraphObj.addEdge("302", "310",true);


GraphObj.addEdge("303", "080",true);
GraphObj.addEdge("310", "095",true);

GraphObj.addEdge("310", "095",true);
GraphObj.addEdge("310", "303",true);

GraphObj.addEdge("095", "080",true);
GraphObj.addEdge("302", "071",true);
GraphObj.addEdge("071", "050",true);
GraphObj.addEdge("310", "050",true);



//System.out.println("Graph \n"+GraphObj.toString());
//GraphObj.getDotLanguage();
System.out.println(""+GraphObj.getDotLanguageV2());
System.out.println("-------------");
//System.out.println(GraphObj.deleteEdge("A","D"));
//System.out.println("Graph \n"+GraphObj.toString());
//System.out.println("Dot \n"+GraphObj.getDotLanguage());
//System.out.println("-------------");
System.out.println(GraphObj.getDFS("303"));
System.out.println(GraphObj.getBFS("303"));
//System.out.println(""+GraphObj.toStringSaveFormatV1());
}


}