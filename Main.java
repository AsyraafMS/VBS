package vbs3;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static ArrayList<Venue> venues = new ArrayList<>();
    private static ArrayList<User> users = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Load venues from a file
        loadVenuesFromFile("D:\\vbs3\\src\\vbs3\\venue.txt");
        loadUsersFromFile("D:\\vbs3\\src\\vbs3\\user.txt");
      
        int choice;
        while (true) {
            showMainMenu();
            
            System.out.print("Choose an option: ");
            while (!scanner.hasNextInt()) {
                System.out.println("\nInvalid input. Please enter a number.\n");
                scanner.next(); // Clear the invalid input
                System.out.print("Enter your choice: ");
                }
                choice = scanner.nextInt();

            switch (choice) {
                case 1: //Login
                    handleLogin(scanner);
                    break;
                case 2: //Register user
                    registerUser(scanner, users);
                    break;
                case 3: //Exit
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
    private static void loadUsersFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line, id, name, password, level;
            
            while ((line = reader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line,":");
                id = tokenizer.nextToken();
                name = tokenizer.nextToken();
                password = tokenizer.nextToken();
                level = tokenizer.nextToken();

                users.add(new User(Integer.parseInt(id),name,password, Integer.parseInt(level)));
            }
            System.out.println("Users loaded successfully from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading users from file: " + e.getMessage());
        }
    }
   
    private static void showMainMenu(){
        System.out.println("\n+====================================+");
            System.out.println(String.format("| %-34s |", "Venue Booking System"));
            System.out.println("+====================================+");
            System.out.println(String.format("| %-34s |","1. Log In"));
            System.out.println(String.format("| %-34s |","2. Register"));
            System.out.println(String.format("| %-34s |","3. Exit"));
            System.out.println("+====================================+");
    }

    private static void showUserMenu(Scanner scanner, String username){
        int choice;
        System.out.println("\nWelcome " + username+"!");
		do{
            System.out.println("\n+====================================+");
            System.out.println(String.format("| %-34s |", "Venue Booking System"));
            System.out.println("+====================================+");
            System.out.println(String.format("| %-34s |","1. View Venues"));
            //System.out.println(String.format("| %-34s |","n. Search Venues"));
            //System.out.println(String.format("| %-34s |","n. Sort Venue"));
            System.out.println(String.format("| %-34s |","2. Book Venue"));
            System.out.println(String.format("| %-34s |","3. Cancel Booking"));
            System.out.println(String.format("| %-34s |","4. Log Out"));
            System.out.println("+====================================+");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

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
                	System.out.println("\nSigning out...\n");
                    //exitProgram(scanner);
                    return;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        } while (choice != 4);
    }
    
    private static void showAdminMenu(Scanner scanner, String username){
        int choice;
        System.out.println("\nWelcome " + username+"!");
		do{
            System.out.println("\n+====================================+");
            System.out.println(String.format("| %-34s |", "Venue Booking System"));
            System.out.println("+====================================+");
            System.out.println(String.format("| %-34s |","1. View Booking Requests"));
            System.out.println(String.format("| %-34s |","2. Manage Booking Request"));
            System.out.println(String.format("| %-34s |","3. Log Out"));
            System.out.println("+====================================+");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1: //View Venues
                	showBooking();
                    break;
                case 2: //Manage Bookings
                    //reserveVenue(scanner);
                    break;
                case 3: //Exit
                	System.out.println("\nSigning out...\n");
                    return;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    private static void registerUser(Scanner scanner, ArrayList<User> users) {
        System.out.println("\nRegister User:");
        System.out.print("Enter your username: ");
        String username = scanner.next();
        System.out.print("Enter your password: ");
        String password = scanner.next();
        System.out.print("Are you registering as Admin? (yes/no): ");
        String isAdminInput = scanner.next();
        int isAdmin = 0;
        if (isAdminInput.equalsIgnoreCase("yes")) {
        	isAdmin = 1;
        } else {
        	isAdmin = 2;
        }

        if (isAdmin == 1) {
            System.out.print("Enter admin referral code: ");
            String referralCode = scanner.next();
            if (referralCode.equals("UTHM_ADMIN")) {
                System.out.println("Registration as Admin successful.");
            } else {
                System.out.println("Invalid referral code. Registration failed.");
                return; // Exit function if referral code is invalid
            }
        } else {
            System.out.println("Registration as User successful.");
        }
        
     // Determine the next user ID based on the last user's ID in the list
        int newUserId = 1; // Default ID if the list is empty
        if (!users.isEmpty()) {
            User lastUser = users.get(users.size() - 1);
            newUserId = lastUser.getUserId() + 1;
        }

        // Create a new User object
        User newUser = new User(newUserId, username, password, isAdmin);

        // Add the user to the ArrayList
        users.add(newUser);

        // Write the user details to the user.txt file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\\\vbs3\\\\src\\\\vbs3\\\\user.txt", true))) {
        	writer.newLine(); // Move to the next line for the next user
        	writer.write(newUser.getUserId()+":"+newUser.getUsername()+":"+newUser.getPassword()+":"+newUser.getType()); // Write the user data in a CSV-like format
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    
    private static String login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return username; // Return username if a matching user is found
            }
        }
        return null;
    }

    private static void handleLogin(Scanner scanner){
        String username = login(scanner);
        if (username != null) {
            boolean isAdmin = checkIfAdmin(username);
        if (isAdmin) {
            showAdminMenu(scanner, username);
        } else {
            showUserMenu(scanner, username);
        }
        } else {
        System.out.println("Login failed. Please try again.");
        }
    }

    private static boolean checkIfAdmin(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getType() == 1) {
                return true; // User is an admin if level is 1
            }
        }
        return false;
    }

    private static void viewVenues() {
        String format1 = "\n+-----------------------------------------------------------------------+";
        String format2 = String.format("| %-10s | %-4s | %-10s | %-10s | %-10s | %-10s |", "Building", "Code", "Name", "Size", "Type", "Status");
        int pageSize = 5;
        int totalVenues = venues.size();
        int totalPages = (int) Math.ceil((double) totalVenues / pageSize);
        Scanner pageScanner = new Scanner(System.in);

        int currentPage = 1;
        while (true) {
            int start = (currentPage - 1) * pageSize;
            int end = Math.min(start + pageSize, totalVenues);
            System.out.println("\n"+format1);
            System.out.println(String.format("| %-29s%-40s |", "", "Venue List"));
            System.out.println(format1);
            System.out.println(format2);
            System.out.println(format1);

            //search result start
            for (int i = start; i < end; i++) {
                Venue venue = venues.get(i);
                System.out.println(venue.toString()); 
            }
            //search result end
            System.out.println(format1);
            

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
        scanner.nextLine();
        String name = scanner.nextLine();
        for (Venue venue : venues) {
            if (venue.getName().equalsIgnoreCase(name)) {
                venue.reserveVenue("D:\\\\vbs3\\\\src\\\\vbs3\\\\venue.txt");
            } return;
        }
        System.out.println("\nVenue not found.");
    };

    private static void cancelBooking(Scanner scanner) {
        
    	int totalVenues = showBooking();
        //cancel
        if (totalVenues>0) {
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
        
    }   
    
    private static int showBooking() {
    	//start display booking list 
        String format1 = "+-----------------------------------------------------------------------+";
        String format2 = String.format("| %-10s | %-4s | %-10s | %-10s | %-10s | %-10s |", "Building", "Code", "Name", "Size", "Type", "Status");
        int pageSize = 5;

        // Filter venues where getStatus() returns true
        List<Venue> filteredVenues = venues.stream()
                .filter(Venue::getStatus)
                .collect(Collectors.toList());

        int totalVenues = filteredVenues.size();
        int totalPages = (int) Math.ceil((double) totalVenues / pageSize);
        Scanner pageScanner = new Scanner(System.in);

        if (totalVenues == 0) {
            System.out.println("\nNo results found.");
            return 0;
        }

        int currentPage = 1;
        while (true) {
            int start = (currentPage - 1) * pageSize;
            int end = Math.min(start + pageSize, totalVenues);
            System.out.println("\n" + format1);
            System.out.println(String.format("| %-29s%-40s |", "", "Venue List"));
            System.out.println(format1);
            System.out.println(format2);
            System.out.println(format1);

            // Display filtered venues for the current page
            for (int i = start; i < end; i++) {
                Venue venue = filteredVenues.get(i);
                System.out.println(venue.toString());
            }

            System.out.println(format1);
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
        return 0;
        // end display booking list
    }

    private static void exitProgram(Scanner scanner){
        System.out.println("\nExiting");
        scanner.close();
        System.exit(0);
    }
}
    
