package vbs_uthm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class User{
    private String username; 
    private String password;

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    
    public String getUsername() { return username; } 
    public String getPassword() { return password; }

    public void setUsernamePassword(String username, String password, Connection conn){
        setUsername(username);
        setPassword(password);
        
        // sql statement to insert value
        try {
            String sql_insertUserPassword = """
                    INSERT INTO user (username, password)
                    VALUES (?, ?);
                    """;
            PreparedStatement statement = conn.prepareStatement(sql_insertUserPassword);
            statement.setString(1, this.username);
            statement.setString(2, this.password);

            int rowsInserted = statement.executeUpdate();
            
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Error while insert value user and password !");
            System.out.println(e.getMessage());
        }
    }

    public int[] getRole(Connection conn, String username, String password){
        
        setUsername(username);
        setPassword(password);

        int[] info = new int[2];
        try {
            String sql_loginIdRole = """
                    SELECT userID,role FROM user where username = ? AND password = ?; 
                    """;
            PreparedStatement statement = conn.prepareStatement(sql_loginIdRole);
            
            statement.setString(1, username);
            statement.setString(2, password);
            
            
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                info[0] = resultSet.getInt("userID");
                info[1] = resultSet.getInt("role");
                
                //return info;
            } else {
                System.out.println("Invalid username or password!");
                info[0] = 0;
                //return info;
            }
        } catch (SQLException e) {
            System.out.println("Error while getRole user!");
            System.out.println(e);
        }
        return info;
    }


}

