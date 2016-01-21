package com.company.test;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Cluster.Builder

public class Test1 {
    
    public static void main(String[] args) {
        Builder clusterBuilder = Cluster.builder()
        
        clusterBuilder.addContactPoint("127.0.0.1")
        
        
        Cluster cluster = clusterBuilder.build();
        
                Session session = cluster.newSession();
                
                session.execute("CREATE KEYSPACE IF NOT EXISTS simplex WITH replication " + 
                        "= {'class':'SimpleStrategy', 'replication_factor':3};");
//                
//                
                session.execute(
                        "CREATE TABLE IF NOT EXISTS simplex.songs (" +
                              "id uuid PRIMARY KEY," + 
                              "title text," + 
                              "album text," + 
                              "artist text," + 
                              "tags set<text>," + 
                              "data blob" + 
                              ");");
                
                  session.execute(
                        "CREATE TABLE IF NOT EXISTS simplex.playlists (" +
                              "id uuid," +
                              "title text," +
                              "album text, " + 
                              "artist text," +
                              "song_id uuid," +
                              "PRIMARY KEY (id, title, album, artist)" +
                              ");");
//                
//                session.execute(
//                        "INSERT INTO simplex.songs (id, title, album, artist, tags) " +
//                        "VALUES (" +
//                            "756716f7-2e54-4715-9f00-91dcbea6cf50," +
//                            "'La Petite Tonkinoise'," +
//                            "'Bye Bye Blackbird'," +
//                            "'Joséphine Baker'," +
//                            "{'jazz', '2013'})" +
//                            ";");
//                  session.execute(
//                        "INSERT INTO simplex.playlists (id, song_id, title, album, artist) " +
//                        "VALUES (" +
//                            "2cc9ccb7-6221-4ccb-8387-f22b6a1b354d," +
//                            "756716f7-2e54-4715-9f00-91dcbea6cf50," +
//                            "'La Petite Tonkinoise'," +
//                            "'Bye Bye Blackbird'," +
//                            "'Joséphine Baker'" +
//                            ");");
                  
                  
                ResultSet results = session.execute("SELECT * FROM simplex.playlists " +
                        "WHERE id = 2cc9ccb7-6221-4ccb-8387-f22b6a1b354d;");
                
                System.out.println(String.format("%-30s\t%-20s\t%-20s\n%s", "title", "album", "artist",
                        "-------------------------------+-----------------------+--------------------"));
                 for (Row row : results) {
                     System.out.println(String.format("%-30s\t%-20s\t%-20s", row.getString("title"),
                     row.getString("album"),  row.getString("artist")));
                 }
                 System.out.println("-------------------------------");
                 
                 ResultSet resultsa = session.execute("SELECT * FROM simplex.playlists " +
                         "");
                 
                 System.out.println(String.format("%-30s\t%-20s\t%-20s\n%s", "title", "album", "artist",
                         "-------------------------------+-----------------------+--------------------"));
                  for (Row row : resultsa) {
                      System.out.println(String.format("%-30s\t%-20s\t%-20s", row.getString("title"),
                      row.getString("album"),  row.getString("artist")));
                  }
                  
//                //Save off the prepared statement you're going to use
//                PreparedStatement statement = session.prepare("INSERT INTO tester.user (userID, firstName, lastName) VALUES (?,?,?)");
//
//                List<ResultSetFuture> futures = new ArrayList<ResultSetFuture>();
//                for (int i = 0; i < 1000; i++) {
//                 //please bind with whatever actually useful data you're importing
//                 BoundStatement bind = statement.bind(i, "John", "Tester");
//                 ResultSetFuture resultSetFuture = session.executeAsync(bind);
//                 futures.add(resultSetFuture);
//                }
//                //not returning anything useful but makes sure everything has completed before you exit the thread.
//                for(ResultSetFuture future: futures){
//                 future.getUninterruptibly();
//                }
                cluster.close();
                
    }
    
}
