import java.sql.*;

public class Database {
    private String hostname = "localhost";
    private int port = 33066;  
    private String username = "root";
    private String password = "";

    public Connection connDatabase(){
        Connection conn = null;

        try {
            //String driver = "com.mysql.jdbc.Driver";
            // jdbc:mysql://127.0.0.1:33066

            conn = DriverManager.getConnection("jdbc:mysql://"+hostname+":"+port, username, password);

            if (conn != null){
                System.out.println("Connection has been successfully established.");
            }
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("Error while establishing the connection!");
        }
        return conn;
    }
    public Connection connDatabase(String database){
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://"+hostname+":"+port+"/"+database, username, password);

            if (conn != null){
                System.out.println("Connection has been successfully established.");
            }
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("Error while establishing the connection!");
        }
        return conn;
    }
}
