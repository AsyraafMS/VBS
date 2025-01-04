public class User {
    String username;
    String password;
    int type; // 1 = admin, 2 = user
    
    //parametrize contructor;
    public User(String username, String password, int type){
        this.username = username;
        this.password = password;
        this.type = type;
    }

    //getter
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public int getType() {return type;}

    //setter
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
