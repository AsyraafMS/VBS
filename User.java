package vbs3;

public class User {
    private int userId;
    private String username;
    private String password;
    private int type; // 1 = admin, 2 = user
    
    //parametrize contructor;
    public User(int userId, String username, String password, int type){
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    //getter
    public int getUserId() {return userId;}
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public int getType() {return type;}

    //setter
    public void setUserId(int userId) {this.userId = userId;}
    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public void setType(int type) {this.type = type;}

    //method
    public boolean auth(String username, String password){
        if(this.username.equals(username) && this.password.equals(password)){
            return true;
        }
        return false;
    }
}