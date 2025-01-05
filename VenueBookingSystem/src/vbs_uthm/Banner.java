package vbs_uthm;

public abstract class Banner {
    public void mainBanner(){
        System.out.println("\n+==========================================+");
        System.out.println(String.format("| %-35s |", "Welcome to the Venue Booking System UTHM"));
        System.out.println("+==========================================+");
        System.out.println(String.format("| %-40s |","1. Log In"));
        System.out.println(String.format("| %-40s |","2. Register"));
        System.out.println(String.format("| %-40s |","3. Exit"));
        System.out.println("+==========================================+");

    } 
    public abstract void adminbanner();
    
    public abstract void userBanner();
    
}
