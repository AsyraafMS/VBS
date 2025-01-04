package vbs_uthm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Venue {
    public void building(Connection conn){
        System.out.println("\n+==========================================+");
        System.out.println(String.format("| %-40s ", "List Building :"));
        System.out.print("| ");
        try {
            String sql_building = """
                    SELECT building FROM venueinfo;
                    """;
            PreparedStatement statement = conn.prepareStatement(sql_building);
            

            ResultSet dataBuilding = statement.executeQuery();

            // collection of item where every item is unique , not same
            Set<String> uniqueBuildings = new HashSet<>();

            while(dataBuilding.next()){
                uniqueBuildings.add(dataBuilding.getString("building"));
            }

            // Print the unique building
            for (String building : uniqueBuildings) {
                System.out.print(building + " ");
            }
            //System.out.println();
  
        } catch (SQLException e) {
            System.out.println("Error while viewing building!");
        }
        System.out.println("\n+==========================================+");

    }
    public void sizeBuilding(Connection conn, String building, int size){
        System.out.println("\n+=============================================+");
        System.out.println("| VenueID | Building | Name Venue | Size Venue | ");
        System.out.println("+=============================================+");
        try {
            String sql_nameSize = """
                    SELECT venueID, building, nameVenue, sizeVenue, typeVenue FROM venueinfo WHERE building=? AND sizeVenue >= ?;
                    """;
            PreparedStatement statement = conn.prepareStatement(sql_nameSize);

            statement.setString(1,building);
            statement.setInt(2,size);

            ResultSet sizeBuilding = statement.executeQuery();

            if (sizeBuilding.next()) { // Check if there's at least one row
                do {
                    System.out.print(sizeBuilding.getInt("venueID") + "\t");
                    System.out.print(sizeBuilding.getString("building") + "\t");
                    System.out.print(sizeBuilding.getString("nameVenue") + "\t");
                    System.out.print(sizeBuilding.getString("sizeVenue") + "\t");
                    if (!sizeBuilding.isLast()) { // Check if it's not the last row
                        System.out.println();
                    }
                } while (sizeBuilding.next());
            }
        } catch (SQLException e) {
            System.out.println("Error while check type and size venue!");
            System.out.println(e);
        }
        System.out.println("\n+=============================================+");
   
    }

    public void viewBook(Connection conn){
        System.out.println("\n+============================================+");
        System.out.println(String.format("| %-42s |", "List Booked Venues :"));
        System.out.println("+============================================+");
        System.out.println("| Bulding | Name Venue | Datetime | Username |");
        System.out.println("+============================================+");
        try {
            PreparedStatement statement = conn.prepareStatement("""
                    SELECT venueinfo.building, venueinfo.nameVenue, bookingvenue.datetime, user.username
                    FROM bookingvenue
                    INNER JOIN venueinfo
                    ON bookingvenue.venueID = venueinfo.venueID
                    INNER JOIN user
                    ON bookingvenue.userID = user.userID;
                    """);
            ResultSet viewBook = statement.executeQuery();
            while(viewBook.next()){
                System.out.print(viewBook.getString("building")+ "\t");
                System.out.print(viewBook.getString("nameVenue")+ "\t");
                System.out.print(viewBook.getTimestamp("datetime")+ "\t");
                System.out.print(viewBook.getString("username"));
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Error while check type and size venue!");
            System.out.println(e);
        }
        System.out.println("+============================================+");

    }

    public void viewBook(Connection conn, int UserID){
        System.out.println("\n+======================================================================+");
        System.out.println(String.format("| %-68s |", "List Your Booking Venues :"));
        System.out.println("+======================================================================+");
        System.out.println("| BookingID | Username | Building | NameVenue | Size | Type | Datetime |");
        System.out.println("+======================================================================+");
        try {
            String sql_userBookingDetail = """
                    SELECT bookingvenue.bookingID, user.username, venueinfo.building, venueinfo.nameVenue, venueinfo.sizeVenue, venueinfo.typeVenue, datetime
                    FROM bookingvenue
                    INNER JOIN user
                    ON bookingvenue.userID = user.userID
                    INNER JOIN venueinfo
                    ON bookingvenue.venueID = venueinfo.venueID
                    WHERE bookingvenue.userID = ?;
                    """;
            PreparedStatement statement = conn.prepareStatement(sql_userBookingDetail);

            statement.setInt(1, UserID);

            ResultSet viewBookUser = statement.executeQuery();

            while (viewBookUser.next()) {
                System.out.print(viewBookUser.getInt("bookingID") + "\t"); 
                System.out.print(viewBookUser.getString("username") + "\t");
                System.out.print(viewBookUser.getString("building") + "\t");
                System.out.print(viewBookUser.getString("nameVenue") + "\t");
                System.out.print(viewBookUser.getInt("sizeVenue")+ "\t");
                System.out.print(viewBookUser.getString("typeVenue")+ "\t");
                System.out.print(viewBookUser.getTimestamp("datetime"));
                System.out.println();
            }

        } catch (SQLException e) {
            System.out.println("Error while query view booking by specific user!");
            System.out.println(e);
        }
        System.out.println("+======================================================================+");
    }
    public int getBookingID(Connection conn, int venueID){
        try {
            String sql_getBookingID = "SELECT * FROM bookingvenue WHERE venueID=?";
            PreparedStatement statement = conn.prepareStatement(sql_getBookingID);

            statement.setInt(1, venueID);
            ResultSet getBookingID = statement.executeQuery();

            if(getBookingID.next()){
                return getBookingID.getInt("bookingID");
            }


        } catch (Exception e) {
            System.out.println("Error while get booking id!");
            System.out.println(e);
        }
        return -1;
    }

    public void setbookingVenue(Connection conn,int userID, int venueID, LocalDateTime datetime){
        try {
            String sql_bookingVenue = """
                    INSERT INTO bookingvenue (datetime, venueID, userID) VALUES  (?, ?, ?)
                    """;
            PreparedStatement statement = conn.prepareStatement(sql_bookingVenue);

            Timestamp timestamp = Timestamp.valueOf(datetime);

            statement.setTimestamp(1, timestamp);
            statement.setInt(2, venueID);
            statement.setInt(3, userID);

            int rowsInserted = statement.executeUpdate();
            
            if (rowsInserted > 0) {
                System.out.println("Booking venue " + venueID + " inserted successfully!");
            }
        } catch (Exception e) {
            System.out.println("\nError while set booking venue!");
            System.out.println("VenueID are not in listed!");
        }
    }

    public void cancelBook(Connection conn, int bookingID, int userID){
        try {
            String sql_cancelBook = """
                    DELETE FROM bookingvenue WHERE bookingID=? AND userID=?;
                    """;
            PreparedStatement statement = conn.prepareStatement(sql_cancelBook);

            statement.setInt(1, bookingID);
            statement.setInt(2, userID);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("\nA bookingID "+bookingID+" was deleted successfully!");
            } else {
                System.out.println("\nA bookingID " +bookingID+" are not in list!");
            }
        } catch (SQLException e) {
            System.out.println("Error while deleted booking venue!");
            System.out.println(e);
        }
    }

    public void updateBooking(Connection conn, int userID, int bookID, int venueID, LocalDateTime datetime){

        try {
            String sql_updateBook = """
                    UPDATE bookingvenue
                    SET venueID=?, datetime=?
                    WHERE bookingID=? AND userID=?;
                    """;
            PreparedStatement statement = conn.prepareStatement(sql_updateBook);

            statement.setInt(1, venueID);
            Timestamp timestamp = Timestamp.valueOf(datetime);
            statement.setTimestamp(2, timestamp);
            statement.setInt(3, bookID);
            statement.setInt(4, userID);
            int rowUpdated = statement.executeUpdate();

            if(rowUpdated > 0){
                System.out.println("\nA booking "+bookID+" was updated successfully!");
            } else {
                System.out.println("\nA booking ID "+bookID+" not in listed!");
            }

        } catch (SQLException e) {
            System.out.println("Error while handling update booing venue!");
            System.out.println(e);
        }
    }

    public void addVenue(Connection conn, String building, String code, String nameVenue, int sizeVenue, String typeVenue){
        try {
            String sql_addvenue = """
                    INSERT INTO venueinfo (building, code, nameVenue, sizeVenue, typeVenue)
                    VALUES (?, ?, ?, ?, ?);
                    """;
            PreparedStatement statement = conn.prepareStatement(sql_addvenue);

            statement.setString(1, building);
            statement.setString(2, code);
            statement.setString(3, nameVenue);
            statement.setInt(4, sizeVenue);
            statement.setString(5, typeVenue);
            
            int rowsInserted = statement.executeUpdate();
            
            if (rowsInserted>0) {
                System.out.println("A new venue was inserted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error while insert new venue!");
            System.out.println(e);
        }
    }
    public void updateVenue(Connection conn, int venueID, int newsize){
        try {
            String sql_updatesize = """
                   UPDATE venueinfo 
                   SET sizeVenue= ? 
                   WHERE venueID=?; 
                    """;
            PreparedStatement statement = conn.prepareStatement(sql_updatesize);

            statement.setInt(1, newsize);
            statement.setInt(2, venueID);

            int rowsUpdated = statement.executeUpdate();
            
            if (rowsUpdated>0) {
                System.out.println("A venue was updated successfully!");
            } else {
                System.out.println("Invalid venueID to update!");
            }
        } catch (Exception e) {
            System.out.println("Error while update the venue!");
            System.out.println(e);
        }
    }

    public void deleteVenue(Connection conn, int venueID){
        try {
            String sql_removeVenue = """
                    DELETE FROM venueinfo
                    WHERE venueID = ?;
                    """;
            PreparedStatement statement = conn.prepareStatement(sql_removeVenue);

            statement.setInt(1, venueID);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A venueID " +venueID+" was deleted successfully!");
            } else {
                System.out.println("Invalid venueID to delete!");
            }
        } catch (Exception e) {
            System.out.println("Error while to delete the venue!");
            System.out.println(e);
        }
    }
}
