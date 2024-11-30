import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;


//Remarks
// 1. Should add PK to data store
// 2. Search and Sort function
// 3. Add and Delete Venue
// 4. Add and Delete User


public class VBS {
    private static ArrayList<Venue> venues = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Load venues from a file
        loadVenuesFromFile("venue.txt");
      
        while (true) {
            showMenu();
            
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: //View Venues
                    viewVenues();
                    break;
                case 2: //Book Venue
                    reserveVenue(scanner);
                    break;
                case 3: //Cancel Booking
                    cancelBooking(scanner);
                    break;
                case 4: //Exit
                    exitProgram(scanner);
                    return;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        }
    }

    private static void loadVenuesFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line, name, building, venueCode, roomSize, roomType, status;
            
            while ((line = reader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line,":");
                building = tokenizer.nextToken();
                venueCode = tokenizer.nextToken();
                name = tokenizer.nextToken();
                roomSize = tokenizer.nextToken();
                roomType = tokenizer.nextToken();
                status = tokenizer.nextToken();


                venues.add(new Venue(building,venueCode,name, Integer.parseInt(roomSize), roomType,Boolean.parseBoolean(status)));
            }
            System.out.println("\nVenues loaded successfully from " + filename);
        } catch (IOException e) {
            System.out.println("\nError loading venues from file: " + e.getMessage());
        }
    }
   
    private static void showMenu(){
        System.out.println("\n+====================================+");
            System.out.println(String.format("| %-34s |", "Venue Booking System"));
            System.out.println("+====================================+");
            System.out.println(String.format("| %-34s |","1. View Venues"));
            //System.out.println(String.format("| %-34s |","n. Search Venues"));
            //System.out.println(String.format("| %-34s |","n. Sort Venue"));
            System.out.println(String.format("| %-34s |","2. Book Venue"));
            System.out.println(String.format("| %-34s |","3. Cancel Booking"));
            System.out.println(String.format("| %-34s |","4. Exit"));
            System.out.println("+====================================+");

            // (admin functions?)
            //System.out.println(String.format("| %-34s |","n. Add Venues"));
            //System.out.println(String.format("| %-34s |","n. Delete Venues"));
    }

    private static void viewVenues() {
        String format1 = "\n+-----------------------------------------------------------------------+";
        String format2 = String.format("| %-10s | %-4s | %-10s | %-10s | %-10s | %-10s |", "Building", "Code", "Name", "Size", "Type", "Status");
        String format3 = "+------------+------+------------+------------+------------+------------+";
        int pageSize = 5;
        int totalVenues = venues.size();
        int totalPages = (int) Math.ceil((double) totalVenues / pageSize);
        Scanner pageScanner = new Scanner(System.in);

        int currentPage = 1;
        while (true) {
            int start = (currentPage - 1) * pageSize;
            int end = Math.min(start + pageSize, totalVenues);
            System.out.println(format1);
            System.out.println(String.format("| %-29s%-40s |", "", "Venue List"));
            System.out.println(format3);
            System.out.println(format2);
            System.out.println(format3);
            for (int i = start; i < end; i++) {
                Venue venue = venues.get(i);
                System.out.println(venue.toString()); 
            }
            System.out.println(format3);

            System.out.println("Available Venues (Page " + currentPage + " of " + totalPages + "):");
            if (currentPage < totalPages) {
                System.out.print("\nEnter 'n' for next page or 'q' to back to menu: ");
                String input = pageScanner.nextLine();
                if (input.equalsIgnoreCase("n")) {
                    currentPage++;
                } else if (input.equalsIgnoreCase("q")) {
                    break;
                } else {
                    System.out.println("\nInvalid input. Please try again.");
                }
            } else {
                System.out.println("\nEnd of results.");
                break;
            }

        }
    }
    
    private static void reserveVenue(Scanner scanner){
        System.out.print("\nEnter venue name to book: ");
        String name = scanner.nextLine();
        for (Venue venue : venues) {
           
            if (venue.getName().equalsIgnoreCase(name)) {
                venue.reserveVenue("venue.txt");
            } return;
        }
        System.out.println("\nVenue not found.");
    };

    private static void cancelBooking(Scanner scanner) {
        System.out.print("\nEnter venue name to cancel booking: ");
        String name = scanner.nextLine();
        for (Venue venue : venues) {
            if (venue.getName().equalsIgnoreCase(name)) {
                venue.cancelBooking("venue.txt");
                return;
            }
        }
        System.out.println("\nVenue not found.");
    }   

    private static void exitProgram(Scanner scanner){
        System.out.println("\nExiting");
        scanner.close();
        System.exit(0);
    }
}
    

