package com.earasoft.orientdb.learning1

import com.orientechnologies.orient.graph.gremlin.OGremlinHelper
import com.tinkerpop.blueprints.Edge
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx
import com.tinkerpop.blueprints.impls.orient.OrientVertexType
import com.tinkerpop.gremlin.groovy.Gremlin

class GraphMethods {

	static{
		Gremlin.load()
		OGremlinHelper.global().create()
		
	}
	static main(args) {
		// AT THE BEGINNING
		OrientGraphFactory factory = new OrientGraphFactory("plocal:C:/temp/graph/db3").setupPool(10, 20);
		
		
		//OrientGraphNoTx graph1 = factory.getNoTx()
		//OrientVertexType account = graph1.createVertexType("Account");
		
		
		//return
		// EVERY TIME YOU NEED A GRAPH INSTANCE
		OrientGraph graph = factory.getTx();
		
		
		try{
		  Vertex luca = graph.addVertex("Account"); // 1st OPERATION: IMPLICITLY BEGIN A TRANSACTION
		  luca.setProperty( "name", "Luca" );
		  Vertex marko = graph.addVertex(null);
		  marko.setProperty( "name", "Marko" );
		  Edge lucaKnowsMarko = graph.addEdge(null, luca, marko, "knows");
		  graph.commit();
		  
		  println graph.V().map().toList()
		} catch( Exception e ) {
		  graph.rollback();
		}
	}

}
