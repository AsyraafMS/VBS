package testVBS;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

//remarks
// 1. add validation lastly

public class Main {
    private static ArrayList<Venue> venues = new ArrayList<>();
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Booking> booking = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Load venues from a file
        loadVenuesFromFile("D:\\testVBS2\\src\\testVBS\\venue.txt");
        loadUsersFromFile("D:\\testVBS2\\src\\testVBS\\user.txt");
        loadBookingFromFile("D:\\testVBS2\\src\\testVBS\\booking.txt");
        
        UserService userService = new UserService();
      
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
                    handleLogin(scanner, userService);
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
                name = tokenizer.nextToken();
                password = tokenizer.nextToken();
                level = tokenizer.nextToken();

                users.add(new User(name,password, Integer.parseInt(level)));
            }
            System.out.println("Users loaded successfully from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading users from file: " + e.getMessage());
        }
    }
    private static void loadBookingFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line, user, venue, date, time, status;
            
            while ((line = reader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line,",");
                user = tokenizer.nextToken();
                venue = tokenizer.nextToken();
                date = tokenizer.nextToken();
                time = tokenizer.nextToken();
                status = tokenizer.nextToken();

                //get the user object from the user input
                User userObj = null;
                for (User u : users) {
                    if (u.getUsername().equalsIgnoreCase(user)) {
                        userObj = u;
                        break;
                    }
                }

                // get venue obj from the user input
                Venue venueObj = null;
                for (Venue v : venues) {
                    if (v.getName().equalsIgnoreCase(venue)) {
                        venueObj = v;
                        break;
                    }
                }

                booking.add(new Booking(userObj,venueObj, date,time,Integer.parseInt(status)));
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

    private static void showUserMenu(Scanner scanner){
        int choice;
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
            System.out.println(String.format("| %-34s |","5. Show Bookings"));
            System.out.println("+====================================+");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1: //View Venues
                    viewVenues();
                    break;
                case 2: //Book Venue
                    addBooking(scanner, booking);
                    break;
                case 3: //Cancel Booking
                    cancelBooking(scanner);
                    break;
                case 4: //Exit
                	System.out.println("\nSigning out...\n");
                    //exitProgram(scanner);
                    return;
                case 5: //Exit
                	showUserBooking();
                    //exitProgram(scanner);
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        } while (choice != 4);
    }
    
    private static void showAdminMenu(Scanner scanner){
        int choice;
		do{
            System.out.println("\n+====================================+");
            System.out.println(String.format("| %-34s |", "Venue Booking System"));
            System.out.println("+====================================+");
            System.out.println(String.format("| %-34s |","1. View Booking Requests"));
            System.out.println(String.format("| %-34s |","2. Manage Booking Request"));
            System.out.println(String.format("| %-34s |","3. Log Out"));
            System.out.println(String.format("| %-34s |","4. Clear Venue Status"));
            System.out.println("+====================================+");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1: //View Venues
                	showBooking();
                    break;
                case 2: //Manage Bookings
                    manageRequests(scanner);
                    break;
                case 3: //Exit
                	System.out.println("\nSigning out...\n");
                    break;
                case 4: //Exit
                    clearVenueStatus(scanner);
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        } while (choice != 3);
    }

    //done
    private static void registerUser(Scanner scanner, ArrayList<User> users) {
    	int isAdmin = 0;
        System.out.println("\nRegister User:");
        System.out.print("Enter your username: ");
        String username = scanner.next();
        System.out.print("Enter your password: ");
        String password = scanner.next();
        System.out.print("Are you registering as Admin? (yes/no): ");
        String isAdminInput = scanner.next();
        
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

        // Create a new User object
        User newUser = new User(username, password, isAdmin);

        // Add the user to the ArrayList
        users.add(newUser);

        // Write the user details to the user.txt file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\testVBS2\\src\\testVBS\\user.txt", true))) {
        	writer.newLine(); // Move to the next line for the next user
        	writer.write(newUser.getUsername()+":"+newUser.getPassword()+":"+newUser.getType()); // Write the user data in a CSV-like format
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    
    //add booking
    private static void addBooking(Scanner scanner, ArrayList<Booking> booking) {
    	viewVenues();
        System.out.println("\nRegister User:");
        System.out.print("Enter venue name: ");
        String nameIn = scanner.next();
        System.out.print("Enter booking date (DD/MM/YYYY): ");
        String dateIn = scanner.next();
        System.out.print("Enter booking start time (hh:mm) in 24hrs format: ");
        String timeStartIn = scanner.next();
        System.out.print("Enter booking end time (hh:mm) in 24hrs format: ");
        String timeEndIn = scanner.next();

        String bookingTime = timeStartIn + " - " + timeEndIn;
        
        Venue selectedVenue = null;
        for (Venue venue : venues) {
            if (venue.getName().equalsIgnoreCase(nameIn)) {
            selectedVenue = venue;
            break;
            }
        }

        if (selectedVenue == null) {
            System.out.println("Venue not found. Please try again.");
            return;
        }
        
        // Create a new User object
        Booking newBooking = new Booking(Session.getLoggedInUser(),selectedVenue, dateIn, bookingTime, 3);

        // Add the user to the ArrayList
        booking.add(newBooking);
        
        // Write the booking details to the booking.txt file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\testVBS2\\src\\testVBS\\booking.txt", true))) {
            writer.newLine(); // Move to the next line for the next booking
            writer.write(newBooking.getUser().getUsername() + "," + newBooking.getVenue().getName() + "," + newBooking.getBookingDate() + "," + newBooking.getBookingTime() + "," + newBooking.getBookingStatus());
            System.out.println("Booking added successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

        
    }
    
    //done
    private static void handleLogin(Scanner scanner, UserService userService) {
    	 System.out.print("Enter username: ");
         String loginUsername = scanner.next();
         System.out.print("Enter password: ");
         String loginPassword = scanner.next();
         if (userService.loginUser (loginUsername, loginPassword)) {
             System.out.println("Login successful! Welcome, " + Session.getLoggedInUser ().getUsername());
             if(Session.getLoggedInUser().getType() == 1) {
            	 showAdminMenu(scanner);
             } else {
            	 showUserMenu(scanner);
             }
         } else {
             System.out.println("Invalid username or password.");
         }
    }

    //done
    private static void viewVenues() {
        String format1 = "+-----------------------------------------------------------------------+";
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
    
    //done (approve/reject)
    private static void manageRequests(Scanner scanner){
        showBooking();
        
        System.out.print("Enter the username and venue to manage (format: username,venue): ");
        String input = scanner.next();
        String[] parts = input.split(",");
        if (parts.length != 2) {
            System.out.println("Invalid input format. Please try again.");
            return;
        }
        String username = parts[0];
        String venueName = parts[1];

        Booking bookingToManage = null;
        for (Booking b : booking) {
            if (b.getUser().getUsername().equalsIgnoreCase(username) && b.getVenue().getName().equalsIgnoreCase(venueName) && b.getBookingStatus() == 3) {
            bookingToManage = b;
            break;
            }
        }

        if (bookingToManage == null) {
            System.out.println("Booking not found. Please try again.");
            return;
        }

        System.out.print("Approve or Reject the booking (a/r): ");
        String decision = scanner.next();
        if (decision.equalsIgnoreCase("a")) {
            bookingToManage.setBookingStatus(1); // Approved
            System.out.println("Booking approved.");

            // Update the venue status
            bookingToManage.getVenue().setStatus(true);
            //update to txt file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\testVBS2\\src\\testVBS\\venue.txt"))) {
                for (Venue v : venues) {
                    writer.write(v.getBuilding() + ":" + v.getVenueCode() + ":" + v.getName() + ":" + v.getSize() + ":" + v.getType() + ":" + v.getStatus());
                    writer.newLine();
                }
                System.out.println("Venue status updated successfully.");
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }

        } else if (decision.equalsIgnoreCase("r")) {
            bookingToManage.setBookingStatus(2); // Rejected
            System.out.println("Booking rejected.");
        } else {
            System.out.println("Invalid input. Please try again.");
        }

        // Update the booking.txt file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\testVBS2\\src\\testVBS\\booking.txt"))) {
            for (Booking b : booking) {
            writer.write(b.getUser().getUsername() + "," + b.getVenue().getName() + "," + b.getBookingDate() + "," + b.getBookingTime() + "," + b.getBookingStatus());
            writer.newLine();
            }
            System.out.println("Booking status updated successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    //for user
    private static void cancelBooking(Scanner scanner) {
            
    }   

    //clear venue status
    private static void clearVenueStatus(Scanner scanner) {
        //show list of venues where status is true
        
        //choose venue and set to false
        

        // Update the venue.txt file
        
    }
    
    private static int showUserBooking() {
    	if (booking.isEmpty()) {
            System.out.println("No bookings available.");
            return 0;
        }

        System.out.println("\n+=============================================================================+");
        System.out.println(String.format("| %-75s |", "Booking List"));
        System.out.println("+=============================================================================+");
        System.out.println(String.format("| %-5s | %-10s | %-10s | %-10s | %-15s | %-10s |", "No.", "User", "Venue", "Date", "Time", "Status"));
        System.out.println("+=============================================================================+");
        int bookingNumber = 1;
        for (Booking b : booking) {
            if (Session.getLoggedInUser().getUsername().equalsIgnoreCase(b.getUser().getUsername())) {
            System.out.println(String.format("| %-5d | %-10s | %-10s | %-10s | %-15s | %-10s |", 
            bookingNumber++,
            b.getUser().getUsername(), 
            b.getVenue().getName(), 
            b.getBookingDate(), 
            b.getBookingTime(), 
            "Pending"));
            }
        }
        System.out.println("+=============================================================================+");
        return booking.size();
    }
    
    
    private static int showBooking() {
        if (booking.isEmpty()) {
            System.out.println("No bookings available.");
            return 0;
        }

        System.out.println("\n+=============================================================================+");
        System.out.println(String.format("| %-75s |", "Booking List"));
        System.out.println("+=============================================================================+");
        System.out.println(String.format("| %-5s | %-10s | %-10s | %-10s | %-15s | %-10s |", "No.", "User", "Venue", "Date", "Time", "Status"));
        System.out.println("+=============================================================================+");
        int bookingNumber = 1;
        for (Booking b : booking) {
            if (b.getBookingStatus() == 3) {
            System.out.println(String.format("| %-5d | %-10s | %-10s | %-10s | %-15s | %-10s |", 
            bookingNumber++,
            b.getUser().getUsername(), 
            b.getVenue().getName(), 
            b.getBookingDate(), 
            b.getBookingTime(), 
            "Pending"));
            }
        }
        System.out.println("+=============================================================================+");
        return booking.size();
    }

    //done
    private static void exitProgram(Scanner scanner){
        System.out.println("\nExiting");
        scanner.close();
        System.exit(0);
    }
}
    
