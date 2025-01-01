package testVBS;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class Venue {
    private String building;
    private String venueCode;
    private String name;
    private int roomSize;
    private String roomType;
    private boolean venueStatus;

    //Constructor 
    public Venue(String building,String venueCode, String name, int roomSize, String roomType, boolean venueStatus) {
        this.building = building;
        this.venueCode = venueCode;
        this.name = name;
        this.roomSize = roomSize;
        this.roomType = roomType;
        this.venueStatus = venueStatus;
    }

    //Accessor
    public String getName() {return name;}
    public String getBuilding(){return building;}
    public String getVenueCode(){return venueCode;}
    public int getSize() {return roomSize;}
    public String getType() {return roomType;}
    public boolean getStatus() {return venueStatus;}

    //Mutator
    public void setStatus(boolean status){ this.venueStatus = status;}


    //Methods
    public void reserveVenue(String filename){
        if (this.venueStatus) {
            System.out.println(name + " is already booked.");
        } else {
            this.venueStatus = true;
            System.out.println(name + " is successfully booked.");
        }
        
        // for venue.txt
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(":");
                if (parts[2].equals(this.name)) {
                    parts[5] = String.valueOf(this.venueStatus);
                    lines.set(i, String.join(":", parts));
                    break;
                }
            }
            Files.write(Paths.get(filename), lines);
        } catch (IOException e) {
            System.out.println("Error updating file: " + e.getMessage());
        }
    }

    public void cancelBooking(String filename) {
        if (venueStatus) {
            venueStatus = false;
            System.out.println(name + " booking has been cancelled.");
        } else {
            System.out.println(name + " is not booked.");
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(":");
                if (parts[2].equals(name)) {
                    parts[5] = String.valueOf(venueStatus);
                    lines.set(i, String.join(":", parts));
                    break;
                }
            }
            Files.write(Paths.get(filename), lines);
        } catch (IOException e) {
            System.out.println("Error updating file: " + e.getMessage());
        }
    }

    public String toString(){
        return String.format("| %-10s | %-4s | %-10s | %-10s | %-10s | %-10s |", this.building, this.venueCode, this.name, this.roomSize, this.roomType, this.venueStatus ? "Booked" : "Available");
    }

    
}
