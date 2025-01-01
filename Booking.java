package testVBS;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Booking {
	private User user;
    private Venue venue;
    private String bookingDate;
    private String bookingTime;
    private int bookingStatus; // 1 = pending, 2 = approved, 3 = rejected

    //parametrize contructor;
    public Booking(User user, Venue venue, String bookingDate, String bookingTime, int bookingStatus){
        this.user = user;
        this.venue = venue;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.bookingStatus = bookingStatus;
    }

    //getter
    public User getUser() {return user;}
    public Venue getVenue() {return venue;}
    public String getBookingDate() {return bookingDate;}
    public String getBookingTime() {return bookingTime;}
    public int getBookingStatus() {return bookingStatus;}

    //setter
    public void setUser(User user) {this.user = user;}
    public void setVenue(Venue venue) {this.venue = venue;}
    public void setBookingDate(String bookingDate) {this.bookingDate = bookingDate;}
    public void setBookingTime(String bookingTime) {this.bookingTime = bookingTime;}
    public void setBookingStatus(int bookingStatus) {this.bookingStatus = bookingStatus;}

    //
    public void addBooking(String filename){
        if (this.bookingStatus == 2) {
            System.out.println(venue.getName() + " is already booked.");
        } else {
        	//add booking
            this.bookingStatus = 1;
            System.out.println(venue.getName() + " booking request successfully sent and pending.");
        }

        //writing to file
        try {
            String bookingDetails = user.getUsername() + ":" + venue.getName() + ":" + bookingDate + ":" + bookingTime + ":" + bookingStatus;
            Files.write(Paths.get(filename), (bookingDetails + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    };

}
