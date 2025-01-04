package vbs_uthm;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sqlautomation extends Database {
    public static void createDatabase(Connection conn,String databaseName){
            try {
                String sql = "CREATE DATABASE " + databaseName;
    
                Statement statement = conn.createStatement();
                statement.executeUpdate(sql);
                statement.close();
    
                System.out.println("Database " + databaseName + " has been created successfully");
            } catch (SQLException e) {
                if(e.getMessage().contains("database exists")){
                    System.out.println("Database '" + databaseName + "' is already existing, Skipping creation");
                } else {
                    System.err.println("An error occurred while creating the database: " + e.getMessage());
                }
            }
    }
    
    public static List<String> fileToList(String filename){
        List<String> dataList = new ArrayList<>();
        try{
            dataList = Files.readAllLines(Paths.get(filename)); 
        } catch (Exception e) {
            System.out.print(e);
        }
        return dataList; 
    }

    public static void createTable(Connection conn, String tableName){
        
        String[] column = {"venueID", "building", "code", "nameVenue", "sizeVenue", "typeVenue"};
        String[] column_datatype = {"INT NOT NULL AUTO_INCREMENT", "VARCHAR(250)", "VARCHAR(250)", "VARCHAR(250)", "INT", "VARCHAR(250)"};
        String primaryKey = "PRIMARY KEY(venueID)";

        try {
            Statement statement = conn.createStatement();
            // StringBuilder is string object that can modified 
            StringBuilder sqlcTable = new StringBuilder("CREATE TABLE "+ tableName + " ("); 

            for(int idx=0; idx<column.length; idx++){
                sqlcTable.append(column[idx]).append(" ").append(column_datatype[idx]);
                if (idx < column.length - 1){
                    sqlcTable.append(", ");
                }
            }

            sqlcTable.append(" ,").append(primaryKey).append(");");
            System.out.println(sqlcTable);
            //System.exit(0);
            statement.executeUpdate(sqlcTable.toString());
            statement.close();
            System.out.println("Table "+ tableName +" has been created successfuly");
        } catch (SQLException e) {
            System.out.println("Error while to create table for venueInfo!");
            System.err.println(e.getMessage());
        }

        /////////////////////////////////
        // Create table user 
        // additional

        try {
            Statement statement = conn.createStatement();
            String createTableUser = """
                    CREATE TABLE user (
                    userID INT NOT NULL AUTO_INCREMENT,
                    username VARCHAR(255) NOT NULL,
                    password VARCHAR(255) NOT NULL,
                    role INT DEFAULT 0,
                    PRIMARY KEY (userID)
                    ) 
                    """;
            statement.executeUpdate(createTableUser);
            statement.close();
            System.out.println("Table User has been created successfuly");
        }catch (SQLException e) {
            System.out.println("Error while to create table for user!");
            System.err.println(e.getMessage());
        }

        ////////////////////////////////
        /// Create table bookVenue

        try {
            Statement statement = conn.createStatement();
            String createTableBookingVenue = """
                    CREATE TABLE bookingvenue (
                        bookingID INT NOT NULL AUTO_INCREMENT,
                        datetime DATETIME NOT NULL,
                        venueID INT,
                        userID INT, 
                        PRIMARY KEY (bookingID), 
                        FOREIGN KEY (venueID) REFERENCES venueinfo(venueID), 
                        FOREIGN KEY (userID) REFERENCES user(userID)
                    )
                    """;
            statement.executeUpdate(createTableBookingVenue);
            statement.close();
            System.out.println("Table BookingVenue has been created successfuly");
        } catch (SQLException e) {
            System.out.println("Error while to create table for Book Venue!");
            System.err.println(e.getMessage());
        }
    }


    public static List<String> columnName(Statement statement, String tableName){
        List<String> columnName = new ArrayList<>();
        try {
            String listColumnName = "select COLUMN_NAME from information_schema.columns where table_name = '"+tableName+"'";
            ResultSet result = statement.executeQuery(listColumnName);
            while(result.next()){
                columnName.add(result.getString("COLUMN_NAME"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return columnName;
    }

    
    public static void insertTable(Connection conn, String nameFile, String tableName){
        // load data from file into list data
        // make sure in current directory namefile while run this file

        List<String> data = fileToList("src\\vbs_uthm\\"+nameFile);

        Iterator<String> lodIterator = data.iterator();

        List<String> building = new ArrayList<>();
        List<String> code = new ArrayList<>();
        List<String> nameVenue = new ArrayList<>();
        List<Integer> sizeVenue = new ArrayList<>();
        List<String> typeVenue = new ArrayList<>();
        //List<String> available = new ArrayList<>(); 

        String regex = "([^:]+):([^:]+):([^:]+):([^:]+):([^:]+)";
        while(lodIterator.hasNext()){
            Pattern pattern = Pattern.compile(regex);
            Matcher feature = pattern.matcher(lodIterator.next());

            while (feature.find()) { 
                building.add(feature.group(1));
                code.add(feature.group(2));
                nameVenue.add(feature.group(3));
                sizeVenue.add(Integer.parseInt(feature.group(4)));
                typeVenue.add(feature.group(5));
                //available.add(feature.group(6));    
            }
        }

        try {
            Statement statement = conn.createStatement();
            List<String> columnName = columnName(statement, tableName);
            columnName.remove("venueID");
            StringBuilder sqliTable = new StringBuilder("INSERT INTO "+ tableName + " (");
            
            for(int idx=0; idx < columnName.size(); idx++){
                sqliTable.append(columnName.get(idx));
                if (idx < columnName.size() - 1){
                    sqliTable.append(", ");
                }
            } 
            sqliTable.append(") ").append("VALUES ");
            for (int idx = 0; idx < building.size(); idx++) {
                sqliTable.append("(")
                         .append("'").append(building.get(idx)).append("'").append(", ")
                         .append("'").append(code.get(idx)).append("'").append(", ")
                         .append("'").append(nameVenue.get(idx)).append("'").append(", ")
                         .append(sizeVenue.get(idx)).append(", ")
                         .append("'").append(typeVenue.get(idx)).append("'")
                         //.append(available.get(idx)) // will store the datatype 0 and 1
                         .append(idx < building.size() - 1 ? "), " : ");");
            }
        
            statement.executeUpdate(sqliTable.toString());
            statement.close();
            System.out.println("Successfully insert data into table "+tableName);
            
        } catch (SQLException e) {
            System.out.println("Error while insert data into table!");
            System.out.println(e);
        }
    }
    public static void insertAdmin(Connection conn, String username, String password){
        try {
            String sql_insertadmin = """
                    INSERT INTO user (username, password, role)
                    VALUES (?,?,?);
                    """; 
            PreparedStatement statement = conn.prepareStatement(sql_insertadmin);

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setInt(3, 1);

            int adminInserted = statement.executeUpdate();

            if (adminInserted>0) {
                System.out.println("admin user is " +username+" are inserted sucessfully!");
            }
        } catch (Exception e) {
            System.out.println("Error while insert user admin automatic!");
        }
    }

    public static void main(String[] args) {

        String databaseName = "vbs";
        String tableName = "venueinfo";
        String nameFile = "venue.txt";

        Sqlautomation instance = new Sqlautomation();
         
        // Connection without database
        Connection conn = instance.connDatabase();
        
        createDatabase(conn,databaseName);
        
        // Connection with specific database
        Connection connWDd = instance.connDatabase("vbs");
        createTable(connWDd, tableName);
        
        // insert/convert data from file into Database
        insertTable(connWDd, nameFile, tableName);

        // autoInsert admin user
        insertAdmin(connWDd, "admin", "admin123");   

    }

}

