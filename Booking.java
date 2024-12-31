package vbs3;

public class Booking {
    private int bookingId;
	private User user;
    private Venue venue;
    private String bookingDate;
    private String bookingTime;
    private int bookingStatus; // 1 = pending, 2 = approved, 3 = rejected

    //parametrize contructor;
    public Booking(int bookingId, User user, Venue venue, String bookingDate, String bookingTime, int bookingStatus){
        this.bookingId = bookingId;
        this.user = user;
        this.venue = venue;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.bookingStatus = bookingStatus;
    }

    //getter
    public int getBookingId() {return bookingId;}
    public User getUser() {return user;}
    public Venue getVenue() {return venue;}
    public String getBookingDate() {return bookingDate;}
    public String getBookingTime() {return bookingTime;}
    public int getBookingStatus() {return bookingStatus;}

    //setter
    public void setBookingId(int bookingId) {this.bookingId = bookingId;}
    public void setUser(User user) {this.user = user;}
    public void setVenue(Venue venue) {this.venue = venue;}
    public void setBookingDate(String bookingDate) {this.bookingDate = bookingDate;}
    public void setBookingTime(String bookingTime) {this.bookingTime = bookingTime;}
    public void setBookingStatus(int bookingStatus) {this.bookingStatus = bookingStatus;}

    

}
