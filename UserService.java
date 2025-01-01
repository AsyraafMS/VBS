package testVBS;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UserService {
    private static final String USER_FILE = "D:\\\\\\\\vbs3\\\\\\\\src\\\\\\\\vbs3\\\\\\\\user.txt";

    public void registerUser  (User  user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            writer.write(user.getUsername() + ":" + user.getPassword() + ":" + user.getType());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean loginUser (String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(":");
                if (userDetails[0].equals(username) && userDetails[1].equals(password)) {
                    // Create a User object with the isAdmin status
                    int isAdmin = Integer.parseInt(userDetails[2]);
                    Session.setLoggedInUser (new User(username, password, isAdmin));
                    return true; // Login successful
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Login failed
    }
}
