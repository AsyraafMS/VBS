package vbs_uthm;

public class AdminMenu extends Menu{
	public void banner() {
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
