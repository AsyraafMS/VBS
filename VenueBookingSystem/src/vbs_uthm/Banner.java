package vbs_uthm;

public class Banner {
    public static void mainBanner(){
        System.out.println("\n+==========================================+");
        System.out.println(String.format("| %-35s |", "Welcome to the Venue Booking System UTHM"));
        System.out.println("+==========================================+");
        System.out.println(String.format("| %-40s |","1. Log In"));
        System.out.println(String.format("| %-40s |","2. Register"));
        System.out.println(String.format("| %-40s |","3. Exit"));
        System.out.println("+==========================================+");

    } 
    public static void userBanner(){
        System.out.println("\n+=======================================+");
        System.out.println(String.format("| %-35s |", "Venue Booking System UTHM For Student"));
        System.out.println("+=======================================+");
        System.out.println(String.format("| %-37s |","1. Search Venue"));
        System.out.println(String.format("| %-37s |","2. Book Venue"));
        System.out.println(String.format("| %-37s |","3. List Booking Venue"));
        System.out.println(String.format("| %-37s |","4. Cancel Booking Venue"));
        System.out.println(String.format("| %-37s |","5. Update Booking Venue"));
        System.out.println(String.format("| %-37s |","6. Logout"));
        System.out.println("+=======================================+");
    }

    public static void adminbanner(){
        System.out.println("\n+=======================================+");
        System.out.println(String.format("| %-37s |", "Venue Booking System UTHM For Admin"));
        System.out.println("+=======================================+");
        System.out.println(String.format("| %-37s |","1. Search Venue"));
        System.out.println(String.format("| %-37s |","2. Add Venue"));
        System.out.println(String.format("| %-37s |","3. Update Venue"));
        System.out.println(String.format("| %-37s |","4. Delete Venue"));
        System.out.println(String.format("| %-37s |","5. logout"));
        System.out.println("+=======================================+");
    }
}