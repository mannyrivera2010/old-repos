package com.earasoft.orientdb.learning1

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx
import com.orientechnologies.orient.core.metadata.schema.OClass
import com.orientechnologies.orient.core.metadata.schema.OSchema
import com.orientechnologies.orient.core.metadata.schema.OType
import com.orientechnologies.orient.graph.gremlin.OGremlinHelper
import com.sun.org.apache.bcel.internal.generic.ObjectType;
import com.tinkerpop.blueprints.Direction
import com.tinkerpop.blueprints.Edge
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.impls.orient.OrientEdgeType
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
		OrientGraphNoTx graph1 = new OrientGraphNoTx("memory:");
		graph1.setUseClassForEdgeLabel(true);
		graph1.setUseClassForVertexLabel(true)
		graph1.setUseLightweightEdges(false)

		OrientVertexType SubVertex1 = graph1.getVertexType("SubVertex1") 
		if (SubVertex1 == null){
			SubVertex1 = graph1.createVertexType("SubVertex1");
			SubVertex1.createProperty("Name", OType.STRING).setMandatory(true).setNotNull(true)
			SubVertex1.createEdgeProperty(Direction.IN, "SubEdge")
			SubVertex1.createEdgeProperty(Direction.OUT, "SubEdge")
			SubVertex1.createEdgeProperty(Direction.IN, "contains")
			SubVertex1.createEdgeProperty(Direction.OUT, "contains")
			SubVertex1.createIndex("NameIdx", OClass.INDEX_TYPE.UNIQUE, "Name")
			SubVertex1.setStrictMode(true)
		}

		if (graph1.getEdgeType("SubEdge") == null){
			OrientEdgeType SubEdge = graph1.createEdgeType("SubEdge");
			SubEdge.createProperty("label1", OType.STRING)
			SubEdge.createProperty("out", OType.LINK, SubVertex1)
			SubEdge.createProperty("in", OType.LINK,SubVertex1)
			SubEdge.setStrictMode(true)
		}
			
		if (graph1.getEdgeType("contains") == null){
			OrientEdgeType containsEdge = graph1.createEdgeType("contains");
			containsEdge.createProperty("out", OType.LINK)
			containsEdge.createProperty("in", OType.LINK)
			containsEdge.setStrictMode(true)
		}


		Vertex v1 = graph1.addVertex("class:SubVertex1", "Name","test1");
		Vertex v2 = graph1.addVertex("class:SubVertex1","Name","test7");
		//graph1.addVertex("class:SubVertex1","Name","test7");
		
		Vertex v3 = graph1.addVertex(null);
		Edge e1 = graph1.addEdge("class:SubEdge", v1, v2, null);
		e1.setProperty("label1", "")
		Edge e2 = graph1.addEdge("class:contains", v1, v3, null);
		graph1.commit();

		println "test"

		println graph1.V().map().toList()
		println graph1.V().toList()
		println graph1.V().bothE.toList()
		println graph1.V().bothE().map().toList()
		println graph1.V().bothE().toList()

		return
		// AT THE BEGINNING
		OrientGraphFactory factory1 = new OrientGraphFactory("plocal:C:/temp/graph/db4");


		//OrientVertexType accounta = factory1.getNoTx().createVertexType("Account5");

		ODatabaseDocumentTx database = factory1.getDatabase()
		//OrientVertexType account = graph1.createVertexType("Account");

		//OClass account = database.getMetadata().getSchema().createClass("Account4",OrientVertex.class);
		//account.createProperty("id", OType.INTEGER);
		//account.createProperty("birthDate", OType.DATE);


		OSchema schema = database.getMetadata().getSchema();
		println schema.getClasses()
		println schema.getClass("V").getProperties()

		return
		// EVERY TIME YOU NEED A GRAPH INSTANCE
		OrientGraphFactory factory = new OrientGraphFactory("plocal:C:/temp/graph/db3").setupPool(1, 10);
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
