package hsql_db;

import model.*;

import java.sql.*;



/*
* This class is to connect he application with database and perform below operations :
* 1) Update the POSTS and REPLY tables with latest values when user push Quit Application button.
* 2) Update the Application with the posts in the database whenever program starts.
* */
public class ConnectionTest {

    // This method is to create Connection object of the connection to the hsqldb database.
    public static Connection getConnection(String dbName) throws SQLException, ClassNotFoundException {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        Connection con = DriverManager.getConnection("jdbc:hsqldb:file:database/" + dbName, "SA", "");
        return con;
    }

    //------------------------------------------------------------------------------------------------------------------
    /*
    * These methods will fetch the data from the database when program gets started and populate the All_posts ArrayList.
    * 1. LoadDatabase to create database connection and fetch the Posts and Reply data into Result set.
    * 2. EventPostInsert to create an Eventpost object on the data fetched from database and Insert it into All_posts.
    * 3. SalePostInsert to create a Salepost object on the data fetched from database and insert it into All_posts.
    * 4. JobPostInsert to create a Jobpost object on the data fetched from database and insert it into All_posts.
    * */

    public void loadDatabase(UniLink object) {
        final String DBNAME = "testDB";
        final String tableName = "POSTS";
        final String replyTable = "REPLY";
        try (Connection con = getConnection(DBNAME);
             Statement stmt = con.createStatement();
        ) {
            String query = "SELECT * FROM "+tableName;
            ResultSet resultSet = stmt.executeQuery(query);
            while(resultSet.next()){
                String id = resultSet.getString("id");
                if (id.startsWith("EVE")){
                    eventPostInsert(resultSet, object, id);
                }
                if(id.startsWith("SAL")){
                    salePostInsert(resultSet, object, id);
                }
                if(id.startsWith("JOB")){
                    jobPostInsert(resultSet, object, id);
                }
            }
            String query2 = "SELECT * FROM "+replyTable;
            ResultSet resultSet1 = stmt.executeQuery(query2);
            while (resultSet1.next()){
                String postId = resultSet1.getString("post_id");
                String respondentId = resultSet1.getString("respondent_id");
                double value = resultSet1.getDouble("value");
                Reply reply = new Reply(postId, value, respondentId);
                for(Post post : object.allPosts){
                    if(post.getId().equals(postId)){
                        post.getReplies().add(reply);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void jobPostInsert(ResultSet resultSet, UniLink object, String id) throws SQLException {
        String username =  resultSet.getString("creator_id");
        String title =  resultSet.getString("title");
        String desc =  resultSet.getString("desc");
        String status = resultSet.getString("status");
        String image =  resultSet.getString("image");
        double proposedPrice =  resultSet.getDouble("proposed_price");
        double lowestOffer =  resultSet.getDouble("lowest_offer");
        JobPost jobpost = new JobPost(id, title, desc, username, proposedPrice, image);
        jobpost.setLowestOffer(lowestOffer);
        jobpost.setStatus(status);
        object.allPosts.add(jobpost);
        System.out.println("job added");
    }

    private void salePostInsert(ResultSet resultSet, UniLink object, String id) throws SQLException {
        String username =  resultSet.getString("creator_id");
        String title =  resultSet.getString("title");
        String desc =  resultSet.getString("desc");
        String status = resultSet.getString("status");
        String image =  resultSet.getString("image");
        double askingPrice =  resultSet.getDouble("asking_price");
        double minRaise =  resultSet.getDouble("min_raise");
        double highestOffer =  resultSet.getDouble("highest_offer");
        SalePost salepost = new SalePost(id, title, desc, username, askingPrice, minRaise, image);
        salepost.setHighestPrice(highestOffer);
        salepost.setStatus(status);
        object.allPosts.add(salepost);
        System.out.println("sale added");
    }

    private void eventPostInsert(ResultSet resultSet, UniLink object, String id) throws SQLException{
        String username =  resultSet.getString("creator_id");
        String title =  resultSet.getString("title");
        String desc =  resultSet.getString("desc");
        String status = resultSet.getString("status");
        String image =  resultSet.getString("image");
        String venue =  resultSet.getString("venue");
        String date = resultSet.getString("date");
        int capacity = resultSet.getInt("capacity");
        int Attendee_count = resultSet.getInt("attendee_count");
        EventPost eventpost = new EventPost(id, title, desc, username, venue, date, capacity, image);
        eventpost.setStatus(status);
        eventpost.setAttendeeCount(Attendee_count);
        object.allPosts.add(eventpost);
        System.out.println("event added");
    }

    //-----------------------------------------------------------------------------------------------------------------
    /*
     * This methods will update the database when Quit Application button pushed.
     * 1) UpdateDatabase to check if the POSTS and REPLY tables Exists
     * 2) DeleteTable to delete the old table each time before saving new data.
     * 3) CreateTables to create new POSTS table each time before inserting data.
     * 4) InsertData to insert new Posts into the POSTS
     * 5) CreateReplyTables to create REPLY table each time before inserting replies
     * 6) InsertReplyData to insert replies of each posts in the Uni_Link arrayList.
     * */
    public void updateDatabase(UniLink obj) throws SQLException, ClassNotFoundException {

        final String DBNAME = "testDB";
        final String tableName = "POSTS";
        final String replyTable = "REPLY";
        try (Connection con = ConnectionTest.getConnection(DBNAME);
             Statement stmt = con.createStatement();
        ){
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet PTable = dbm.getTables(null, null, tableName.toUpperCase(), null);
            ResultSet RTable = dbm.getTables(null, null, replyTable.toUpperCase(), null);
            if(RTable!= null) {
                if (RTable.next()) {
                    deleteTable(stmt, RTable, replyTable);
                    deleteTable(stmt, PTable, tableName);
                    createTables(stmt, con);
                    insertData(con, obj);
                    createReplyTables(stmt, con);
                    insertReplyData(con, obj);
                }
                else{
                    //deleteTable(stmt, PTable, tableName);
                    createTables(stmt, con);
                    insertData(con, obj);
                    createReplyTables(stmt, con);
                    insertReplyData(con, obj);
                }
            }
        }
    }

    private void deleteTable(Statement stmt, ResultSet table, String tableName) throws SQLException {

        System.out.println("Table " + tableName + " exists.");
        int result =  stmt.executeUpdate("DROP TABLE "+ tableName);
        if(result == 0){
            System.out.println("Table " + tableName +" deleted successfully" );
            table.close();
        }

    }

    private void createReplyTables(Statement stmt, Connection con) throws SQLException {
        String CreateReplyQuery = "CREATE TABLE REPLY ("
                + "post_id VARCHAR(8) NOT NULL,"
                + "respondent_id VARCHAR(8) NOT NULL,"
                + "value DOUBLE NOT NULL,"
                + "FOREIGN KEY (post_id) REFERENCES "
                + "POSTS (id))";
        int result = stmt.executeUpdate(CreateReplyQuery);
        if (result == 0){
            System.out.println("Table REPLY created successfully");
        }
    }

    private void insertReplyData(Connection con, UniLink obj) throws SQLException {
        String Query = "INSERT INTO REPLY VALUES (?, ?, ?)";
        for(Post post : obj.allPosts){
            for (Reply reply : post.getReplies()){
                PreparedStatement ps = con.prepareStatement(Query);
                ps.setString(1, reply.getPostID());
                ps.setString(2, reply.getResponderID());
                ps.setDouble(3, reply.getValue());
                int result = ps.executeUpdate();
                //System.out.println(result + " row(s) affected");
                con.commit();
            }
        }
    }

    private void createTables(Statement stmt, Connection con) throws SQLException {
        String CreateTableQuery = "CREATE TABLE POSTS ("
                + "id VARCHAR(8) NOT NULL,"
                + "creator_id VARCHAR(8) NOT NULL,"
                + "title VARCHAR(100) NOT NULL,"
                + "desc VARCHAR(100) NOT NULL,"
                + "status VARCHAR(6) NOT NULL,"
                + "image VARCHAR(100) NOT NULL,"
                + "venue VARCHAR (50)," + "date VARCHAR(10),"+ "capacity INT,"+ "attendee_count INT,"
                + "asking_price DOUBLE," + "min_raise DOUBLE," + "highest_offer DOUBLE,"
                + "proposed_price DOUBLE," + "lowest_offer DOUBLE,"
                + "PRIMARY KEY (id))";
        int result = stmt.executeUpdate(CreateTableQuery);
        if (result == 0){
            System.out.println("Table POSTS created successfully");
        }
    }

    private void insertData(Connection con, UniLink obj) throws SQLException {

        String queryEvent = "INSERT INTO POSTS (id, creator_id, title, desc, status, image, venue, date, capacity, attendee_count) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String querySale = "INSERT INTO POSTS (id, creator_id, title, desc, status, image, asking_price, min_raise, highest_offer) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String queryJob = "INSERT INTO POSTS (id, creator_id, title, desc, status, image, proposed_price, lowest_offer) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String query = "";
        for(Post post : obj.allPosts){
            if(post.getId().startsWith("EVE")){
                query = queryEvent;
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, post.getId());
                ps.setString(2, post.getCreatorID());
                ps.setString(3, post.getTitle());
                ps.setString(4, post.getDescription());
                ps.setString(5, post.getStatus());
                ps.setString(6, post.getPhoto());
                ps.setString(7,((EventPost)post).getVenue());
                ps.setString(8, ((EventPost)post).getDate());
                ps.setInt   (9, ((EventPost)post).getCapacity());
                ps.setInt   (10, ((EventPost)post).getAttendeeCount());
                int result = ps.executeUpdate();
                //System.out.println(result + " row(s) affected");
                con.commit();
            }
            if(post.getId().startsWith("SAL")){
                query = querySale;
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, post.getId());
                ps.setString(2, post.getCreatorID());
                ps.setString(3, post.getTitle());
                ps.setString(4, post.getDescription());
                ps.setString(5, post.getStatus());
                ps.setString(6, post.getPhoto());
                ps.setDouble(7, ((SalePost)post).getAskingPrice());
                ps.setDouble(8, ((SalePost)post).getMinRaise());
                ps.setDouble(9, ((SalePost)post).getHighestPrice());
                int result = ps.executeUpdate();
                //System.out.println(result + " row(s) affected");
                con.commit();
            }
            if(post.getId().startsWith("JOB")){
                query = queryJob;
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, post.getId());
                ps.setString(2, post.getCreatorID());
                ps.setString(3, post.getTitle());
                ps.setString(4, post.getDescription());
                ps.setString(5, post.getStatus());
                ps.setString(6, post.getPhoto());
                ps.setDouble(7, ((JobPost)post).getPropPrice());
                ps.setDouble(8, ((JobPost)post).getLowestOffer());
                int result = ps.executeUpdate();
                //System.out.println(result + " row(s) affected");
                con.commit();
            }
        }

    }

    //-----------------------------------------------------------------------------------------------------------------

}
