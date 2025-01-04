package vbs_uthm;

public class UserMenu extends Menu{
	public void banner() {
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
}
