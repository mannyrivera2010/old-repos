package com.company.test

import groovy.json.JsonSlurper

import com.datastax.driver.core.Cluster
import com.datastax.driver.core.Session
import com.datastax.driver.core.Cluster.Builder


class ReadFiles {
    
    /**
     * Helper function used to iterate lines of a file
     * @param inputFile
     * @param processLineClosure
     */
    //    static void lineIteratorHelper(File inputFile, Closure processLineClosure, Closure errorClosure=null){
    //        try{
    //            LineIterator it = FileUtils.lineIterator(inputFile, "UTF-8");
    //            try {
    //                while (it.hasNext()) {
    //                    String line = it.nextLine();
    //                    processLineClosure(line)
    //                }
    //            } finally {
    //                it.close();
    //            }
    //        }catch(Exception e){
    //            if(errorClosure!=null){
    //                errorClosure(e)
    //            }else{
    //                logger.error("Error", e)
    //            }
    //        }
    //    }
    
    static main(args) {
        
        Builder clusterBuilder = Cluster.builder()
        
        clusterBuilder.addContactPoint("127.0.0.1")
        
        
        Cluster cluster = clusterBuilder.build();
        
        Session session = cluster.newSession();
        
        session.execute("CREATE KEYSPACE IF NOT EXISTS simplex WITH replication " +
                "= {'class':'SimpleStrategy', 'replication_factor':3};");
        
      
            session.execute(
                "CREATE TABLE IF NOT EXISTS simplex.songs (" +
                      "id uuid PRIMARY KEY," +
                      "title text," +
                      "album text," +
                      "artist text," +
                      "tags set<text>," +
                      "data blob" +
                      ");");
                  
                  
        
        File file = new File("C:/data/graphOutput20151213.json")
        println "Below is the content of the file ${file.absolutePath}"
        
        TreeMap sch = new TreeMap()
        
        JsonSlurper js = new JsonSlurper()
        
        double total = 0
        
        def lineNo = 1
        def line
        file.withReader { reader ->
            while ((line = reader.readLine())!=null) {
                
                Map currentObj = js.parseText(line)
                
                total += (line.getBytes().size() / 1024 / 1024)
                //println total
                
                currentObj.remove("_inE")
                currentObj.remove("_outE")
                
                
               
                if(sch.containsKey(currentObj['vertexType'])){
                    TreeSet vert = sch.get(currentObj['vertexType'])
                    
                    
                }else{
                    sch.put(currentObj['vertexType'], currentObj.keySet() as TreeSet)
                }
                
                if(currentObj['vertexType'] == "PERSON"){
                    
                    //println currentObj
                    println currentObj.keySet() as TreeSet
                }
                //println currentObj
                
                if(line.getBytes().size() >= 1024*100){
                    
                }else{
                    
                }
                
                
                //println "${lineNo}. ${line}"
                lineNo++
            }
        }
        
    }
    
}
