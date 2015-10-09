package com.earasoft.orientdb.learning1

import java.text.NumberFormat

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx
import com.orientechnologies.orient.core.metadata.schema.OClass
import com.orientechnologies.orient.core.metadata.schema.OSchema
import com.orientechnologies.orient.core.metadata.schema.OType
import com.orientechnologies.orient.graph.gremlin.OGremlinHelper
import com.tinkerpop.blueprints.Direction
import com.tinkerpop.blueprints.Edge
import com.tinkerpop.blueprints.Graph
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.impls.orient.OrientEdgeType
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx
import com.tinkerpop.blueprints.impls.orient.OrientVertexType
import com.tinkerpop.gremlin.groovy.Gremlin

class GraphMethods {
    private static final Logger logger = LoggerFactory.getLogger(GraphMethods.class)
    
	static{
		Gremlin.load()
		OGremlinHelper.global().create()
        MetaClassesBase.load()
	}
    
    static String formatNumber(Object input){
        return NumberFormat.getNumberInstance(Locale.US).format(input)
    }
    
	static main(args) {
        OrientGraph graph = new OrientGraph("remote:localhost/personifyTest");
        
        try{
            //println "count start.."
            //println graph.V().count()
      
        
        long startTime = System.currentTimeMillis()
        String graphsonFile = 'C:\\graphOutput-20150608.json'
        long count = 0
        GeneralUtils.lineIteratorHelper(new File(graphsonFile), {String line ->
            Map vertexRawMap = line.jsonToObject()
            long vertexId = vertexRawMap['_id']
            String vertexType = vertexRawMap['vertexType']
            
            logger.trace('vertexId: ' + vertexId)
       
            
            /*
             * Vertex Creation - START
             */
            Vertex titanGraphVertex = graph.addVertex("class:"+vertexType)
          
            
            Set<String> keys =  vertexRawMap.keySet()
            
            
                for(key in keys){
                    String faunusProperty = key
                    Object faunusValue = vertexRawMap["${key}"]
                    
                    if (faunusProperty.equals('vertexType')) {
                        titanGraphVertex.setProperty('vertexType', faunusValue)
                    } else {
                        boolean valid = faunusProperty.contains('temp_') || faunusProperty.startsWith("_")
                        if (!valid) {
                            titanGraphVertex.setProperty(faunusProperty, faunusValue)
                        }
                    }
                }
                
                logger.trace("Vertex Created ($vertexId ) \t $vertexType\t")
                logger.trace("===========================")
                
                //println "Vertex Created ($vertexId ) \t $vertexType\t" + (vertexRawMap as TreeMap)
            
            count ++
            
            if(count % 10000 == 0){ //Every so often commit to the graph
               graph.commit()
//                db.commit()
                double timeA= (System.currentTimeMillis() - startTime) / 1000
                double ratePerSec = count / timeA
                double esTime = (1 / ratePerSec) / 60
                logger.debug("Committed: " + formatNumber(count) + " - " + formatNumber(1) + "\t Time (mins): " + formatNumber( timeA / 60 ) + "\tE/S Time(mins): " + esTime)
                println "Committed: " + formatNumber(count)
            }
            
        }, {throw it ;
            })
      
        
        }finally{
        graph.shutdown()
    }
        
        return 
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
		te()
	}
    
    private static te() {
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
