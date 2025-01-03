package vbs_uthm;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


public class mainVBS extends Database{

    public static void registration(Scanner input, User user, Connection conn){
        System.out.println();
        System.out.print("Enter Your Name: ");
        String name = input.next();
        
        System.out.print("Enter Your Password: ");
        String password = input.next();
        
        user.setUsernamePassword(name, password, conn);
    }

    public static int[] login(Connection conn, User user, Scanner input){
        System.out.println();
        System.out.print("Enter your name: ");
        String name = input.next();
        System.out.print("Enter your password: ");
        String password = input.next();
        
        int[] info = user.getRole(conn, name, password);
        
        // System.out.println(info[0]); userID
        // System.out.println(info[1]); role
        return info;
    }

    public static LocalDateTime inputDatetime(Scanner input){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dateTime = null;

        while (true) {
            System.out.print("Enter date and time (dd-MM-yyyy HH:mm): ");
            String dateTimeInput = input.nextLine();

            try {
                dateTime = LocalDateTime.parse(dateTimeInput, formatter);
                break; // Exit the loop if input is valid
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Please try again.");
            }
        }
        return dateTime;
    }

    public static void bookvenue(Connection conn, Venue venue, Scanner input, int userID){
        System.out.print("Enter venueID to Booking: ");
        int venueID = input.nextInt();
        input.nextLine(); // Consume the newline character left by nextInt() 

        // request user datetime
        LocalDateTime dateTime = inputDatetime(input);

        venue.setbookingVenue(conn, userID, venueID, dateTime);

    }

    public static void cancelBook(Connection conn, int userID, Scanner input, Venue venue){
        System.out.print("Enter bookingID To Cancel Book: ");
        int bookID = input.nextInt();

        venue.cancelBook(conn, bookID, userID);

    }

    public static void updateBook(Connection conn, Scanner input, Venue venue, int userID){
        
        System.out.print("Enter the bookingID: ");
        int bookID = input.nextInt();
        
        System.out.print("Enter the new venueID: ");
        int venueID = input.nextInt();

        input.nextLine(); // Consume the newline character left by nextInt() 

        LocalDateTime dateTime = inputDatetime(input);

        venue.updateBooking(conn, userID, bookID, venueID, dateTime);

    }

    public static void addVenue(Connection conn, Scanner input , Venue venue){

        while (true) { 
            try {
                System.out.print("Enter the Name Building: ");
                String building = input.next().toUpperCase();
                System.out.print("Enter the Character Code: ");
                String code = input.next();
                System.out.print("Enter the Name Venue: ");
                String nameVenue = input.next().toUpperCase();
                System.out.print("Enter the Number Size Venue: ");
                int sizeVenue = input.nextInt();
                System.out.print("Enter the Type Venue: ");
                String typeVenue = input.next();

                venue.addVenue(conn, building, code, nameVenue, sizeVenue, typeVenue);
                break;

            } catch (Exception e) {
                System.out.println("Invalid data type!");
                input.nextLine();  // Consume the newline character left by nextInt() 
            }           
        }
    }
    public static void updateVenue(Connection conn, Scanner input, Venue venue){
        System.out.print("\nEnter the venueID: ");
        int venueID = input.nextInt();

        System.out.print("New size of venue: ");
        int new_size = input.nextInt();

        venue.updateVenue(conn, venueID, new_size);
    }
    public static void deleteVenue(Connection conn, Scanner input, Venue venue){
        System.out.print("Enter the venueID to remove: ");
        int venueID = input.nextInt();

        venue.deleteVenue(conn, venueID);
    }
    public static void main(String[] args) {
        User user = new User();
        Venue venue = new Venue();
        Banner banner = new Banner();
        Scanner input = new Scanner(System.in);
        int[] session = null;


        // Connection with database
        Sqlautomation instance = new Sqlautomation();
        Connection conn = instance.connDatabase("vbs");
        
        
        while (true) { 
            banner.mainBanner();
            
            System.out.print("Enter your option: ");
            int option = input.nextInt();
            switch(option) {
                case 1:
                    while(true){
                        session = login(conn, user, input);
                        if(session[0] != 0){
                            break;
                        }
                    }
                    // option for user
                    if (session[1] == 0){
                        while (true) { 
                            banner.userBanner();
                            System.out.print("Enter your option: ");
                            int userOption = input.nextInt();
                            switch (userOption) {
                                case 1:
                                    venue.building(conn);
                                    System.out.print("Enter the name building: ");
                                    String building = input.next().toUpperCase();
                                    
                                    System.out.print("Specify the size of people: ");
                                    int size = input.nextInt();

                                    venue.sizeBuilding(conn, building, size);
                                    break;
                                case 2: 
                                    venue.viewBook(conn);
                                    bookvenue(conn,venue,input,session[0]);
                                    break;
                                case 3:
                                    venue.viewBook(conn, session[0]);
                                    break;
                                case 4:
                                    venue.viewBook(conn, session[0]);
                                    cancelBook(conn, session[0], input, venue); 
                                    break;
                                case 5:
                                    venue.viewBook(conn, session[0]);
                                    updateBook(conn, input, venue, session[0]);
                                    break;
                                case 6:
                                    // logout the user feature 
                                    break;
                                default:
                                    System.out.println("\nInvalid choice. Please try again.");
                            }
                            if (userOption == 6) {
                                break; 
                            }
                        }        
                    }
                    // option for admin
                    if (session[1] == 1){
                        System.out.println("HI Admin");
                        while (true) { 
                            banner.adminbanner();
                            System.out.print("Enter your option: ");
                            int adminOption = input.nextInt();
                            switch (adminOption) {
                                case 1:
                                    venue.building(conn);
                                    System.out.print("Enter the name building: ");
                                    String building = input.next().toUpperCase();
                                    
                                    System.out.print("Specify the size of people: ");
                                    int size = input.nextInt();

                                    venue.sizeBuilding(conn, building, size);
                                    break;
                                case 2:
                                    addVenue(conn, input, venue);
                                    break;
                                case 3:
                                    updateVenue(conn, input, venue);
                                    break;
                                case 4: 
                                    deleteVenue(conn, input, venue);
                                    break;
                                case 5:
                                    
                                default:
                                    System.out.println("\nInvalid choice. Please try again.");
                            }
                            if (adminOption == 5) {
                                break;
                            }
                        }
                    }
                    break;
                case 2:
                    registration(input, user, conn);
                    break;
                case 3: 
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
            if (option == 3) {
                break; 
            }
        }
        
    }
    
}
