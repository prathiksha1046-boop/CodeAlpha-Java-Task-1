package Task4_HotelReservationSystem;
import java.util.*;
import java.io.*;

class Room {

    private int roomNumber;
    private String category;
    private double price;
    private boolean available;

    public Room(int roomNumber, String category, double price) {

        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.available = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

class Booking {

    private int bookingId;
    private String customerName;
    private int roomNumber;
    private String category;
    private int nights;
    private double amount;

    public Booking(
            int bookingId,
            String customerName,
            int roomNumber,
            String category,
            int nights,
            double amount) {

        this.bookingId = bookingId;
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.category = category;
        this.nights = nights;
        this.amount = amount;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getNights() {
        return nights;
    }

    public double getAmount() {
        return amount;
    }

    public String toString() {

        return "Booking ID: " + bookingId +
                " | Customer: " + customerName +
                " | Room: " + roomNumber +
                " | Category: " + category +
                " | Nights: " + nights +
                " | Amount: ₹" + amount;
    }
}

public class HotelReservationSystem {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();

    static int bookingCounter = 1001;

    public static void main(String[] args) {

        loadRooms();

        int choice;

        do {

            System.out.println("\n====================================");
            System.out.println("       HOTEL RESERVATION SYSTEM");
            System.out.println("====================================");

            System.out.println("1. View Available Rooms");
            System.out.println("2. Search Rooms");
            System.out.println("3. Book Room");
            System.out.println("4. Cancel Reservation");
            System.out.println("5. View Booking Details");
            System.out.println("6. Exit");

            System.out.print("\nEnter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    displayRooms();
                    break;

                case 2:
                    searchRooms();
                    break;

                case 3:
                    bookRoom();
                    break;

                case 4:
                    cancelBooking();
                    break;

                case 5:
                    viewBookings();
                    break;

                case 6:
                    System.out.println(
                            "Thank you for using Hotel Reservation System!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 6);
    }

    // Create rooms
    static void loadRooms() {

        rooms.add(new Room(101, "Standard", 1500));
        rooms.add(new Room(102, "Standard", 1500));

        rooms.add(new Room(201, "Deluxe", 2500));
        rooms.add(new Room(202, "Deluxe", 2500));

        rooms.add(new Room(301, "Suite", 4000));
        rooms.add(new Room(302, "Suite", 4000));
    }

    // Display available rooms
    static void displayRooms() {

        System.out.println("\n------------- AVAILABLE ROOMS -------------");

        System.out.printf(
                "%-10s %-15s %-15s%n",
                "Room",
                "Category",
                "Price/Night"
        );

        for (Room room : rooms) {

            if (room.isAvailable()) {

                System.out.printf(
                        "%-10d %-15s ₹%-15.2f%n",
                        room.getRoomNumber(),
                        room.getCategory(),
                        room.getPrice()
                );
            }
        }
    }

    // Search rooms by category
    static void searchRooms() {

        System.out.println("\nRoom Categories:");
        System.out.println("1. Standard");
        System.out.println("2. Deluxe");
        System.out.println("3. Suite");

        System.out.print("Enter category: ");

        int choice = sc.nextInt();

        String category;

        if (choice == 1) {
            category = "Standard";
        } else if (choice == 2) {
            category = "Deluxe";
        } else if (choice == 3) {
            category = "Suite";
        } else {
            System.out.println("Invalid category!");
            return;
        }

        System.out.println("\nAvailable " + category + " Rooms:");

        boolean found = false;

        for (Room room : rooms) {

            if (room.getCategory().equalsIgnoreCase(category)
                    && room.isAvailable()) {

                System.out.println(
                        "Room " + room.getRoomNumber() +
                        " - ₹" + room.getPrice() + " per night"
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms available.");
        }
    }

    // Book room
    static void bookRoom() {

        displayRooms();

        System.out.print("\nEnter room number: ");
        int roomNumber = sc.nextInt();

        Room selectedRoom = findRoom(roomNumber);

        if (selectedRoom == null) {

            System.out.println("Room not found!");
            return;
        }

        if (!selectedRoom.isAvailable()) {

            System.out.println("Room is already booked!");
            return;
        }

        sc.nextLine();

        System.out.print("Enter customer name: ");
        String customerName = sc.nextLine();

        System.out.print("Enter number of nights: ");
        int nights = sc.nextInt();

        if (nights <= 0) {

            System.out.println("Invalid number of nights!");
            return;
        }

        double totalAmount =
                selectedRoom.getPrice() * nights;

        System.out.println("\n------------- PAYMENT -------------");
        System.out.println("Customer: " + customerName);
        System.out.println("Room: " + selectedRoom.getRoomNumber());
        System.out.println("Nights: " + nights);
        System.out.println("Total Amount: ₹" + totalAmount);

        System.out.println("\nPayment Methods:");
        System.out.println("1. UPI");
        System.out.println("2. Credit Card");
        System.out.println("3. Debit Card");

        System.out.print("Choose payment method: ");
        int payment = sc.nextInt();

        if (payment < 1 || payment > 3) {

            System.out.println("Invalid payment method!");
            return;
        }

        System.out.println("\nProcessing payment...");

        System.out.println("Payment successful!");

        int bookingId = bookingCounter++;

        Booking booking = new Booking(
                bookingId,
                customerName,
                selectedRoom.getRoomNumber(),
                selectedRoom.getCategory(),
                nights,
                totalAmount
        );

        bookings.add(booking);

        selectedRoom.setAvailable(false);

        saveBooking(booking);

        System.out.println("\n================================");
        System.out.println("       BOOKING CONFIRMED");
        System.out.println("================================");

        System.out.println("Booking ID: " + bookingId);
        System.out.println("Customer: " + customerName);
        System.out.println("Room Number: "
                + selectedRoom.getRoomNumber());
        System.out.println("Category: "
                + selectedRoom.getCategory());
        System.out.println("Nights: " + nights);
        System.out.println("Amount Paid: ₹" + totalAmount);

        System.out.println("================================");
    }

    // Find room
    static Room findRoom(int roomNumber) {

        for (Room room : rooms) {

            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }

        return null;
    }

    // Cancel booking
    static void cancelBooking() {

        if (bookings.isEmpty()) {

            System.out.println("No bookings available.");
            return;
        }

        viewBookings();

        System.out.print("\nEnter Booking ID to cancel: ");
        int bookingId = sc.nextInt();

        Booking booking = null;

        for (Booking b : bookings) {

            if (b.getBookingId() == bookingId) {

                booking = b;
                break;
            }
        }

        if (booking == null) {

            System.out.println("Booking not found!");
            return;
        }

        Room room = findRoom(booking.getRoomNumber());

        if (room != null) {
            room.setAvailable(true);
        }

        bookings.remove(booking);

        System.out.println("\nBooking cancelled successfully.");

        System.out.println("Refund Amount: ₹"
                + booking.getAmount());

        saveCancellation(booking);
    }

    // View bookings
    static void viewBookings() {

        System.out.println("\n------------- BOOKING DETAILS -------------");

        if (bookings.isEmpty()) {

            System.out.println("No active bookings.");
            return;
        }

        for (Booking booking : bookings) {

            System.out.println(booking);
        }
    }

    // Save booking into file
    static void saveBooking(Booking booking) {

        try {

            FileWriter writer =
                    new FileWriter("hotel_bookings.txt", true);

            writer.write("BOOKING CONFIRMED\n");
            writer.write(booking.toString() + "\n");
            writer.write("--------------------------------\n");

            writer.close();

        } catch (IOException e) {

            System.out.println("Error saving booking.");
        }
    }

    // Save cancellation
    static void saveCancellation(Booking booking) {

        try {

            FileWriter writer =
                    new FileWriter("hotel_bookings.txt", true);

            writer.write("BOOKING CANCELLED\n");
            writer.write(booking.toString() + "\n");
            writer.write("--------------------------------\n");

            writer.close();

        } catch (IOException e) {

            System.out.println("Error saving cancellation.");
        }
    }
}
