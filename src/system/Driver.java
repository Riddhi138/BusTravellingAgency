package system;
import repository.Bus;
import repository.Passenger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Driver {

    private static final String url = System.getenv("URL");
    private static final String username = System.getenv("USERNAME");
    private static final String password = System.getenv("PASSWORD");

    public static String getPassword() {
        return password;
    }

    public static String getUrl() {
        return url;
    }

    public static String getUsername() {
        return username;
    }

    // Load MySQL Driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    Connection con = null;
    {
        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Connection Failed: " + e.getMessage());
        }
    }

    Scanner sc = new Scanner(System.in);
    Bus bus = new Bus(con);            // Make obj of Bus class to use its functionality
    Passenger passenger = new Passenger(con);

    public void login() {
        System.out.println("______________________________________________________________________________________________");
        System.out.println("Choose option: \n 1. Admin Panel   \n 2. Passenger Panel");
        System.out.println("______________________________________________________________________________________________");
        int ch = sc.nextInt();
        switch (ch) {
            case 1:
                startAppAdmin();
                break;

            case 2:
                startAppUser();
                break;

            default:
                System.out.println("Invalid Choice, Try again");
        }
    }

    // Admin Login
    public void startAppAdmin() {
        System.out.println("ADMIN LOGIN");
        System.out.println("Enter username :");
        String user = sc.next();
        System.out.println("Enter password: ");
        String pass = sc.next();

        if (Bus.adminLogin(user, pass)) {
            System.out.println("Login Successful.\n");

            while (true) {
                System.out.println("______________________________________________________________________________________________");
                System.out.println("Choose option: \n 1. Display Bus \n 2. Insert Bus \n 3. Update Bus \n 4. Delete Bus \n 5. Approve Ticket \n 6.Logout");
                System.out.println("______________________________________________________________________________________________");
                int ch = sc.nextInt();
                switch (ch) {
                    case 1:
                        displayAllBus();
                        break;
                    case 2:
                        insertBusFlow();
                        break;
                    case 3:
                        updateSeat();
                        break;
                    case 4:
                        deleteBus();
                        break;
                    case 5:
                        approveTicket();
                        break;

                    case 6:
                        login();
                        break;

                    default:
                        System.out.println("Invalid Choice.");
                }
            }
        } else {
            System.out.println("Login Failed, Try again.");
        }
        sc.close();
    }

    private void displayAllBus() {
        bus.displayAllBus();
    }

    private void insertBusFlow() {
        System.out.println("Bus ID:");
        int id = sc.nextInt();
        System.out.println("Source: ");
        String src = sc.next();
        System.out.println("Destination: ");
        String dest = sc.next();
        System.out.println("Arrival Time (HH:MM:SS)");
        String arr = sc.next();
        System.out.println("Total Seats: ");
        int total = sc.nextInt();
        System.out.println("Available Seats: ");
        int available = sc.nextInt();

        bus.insertBusFlow(id, src, dest, arr, total, available);
    }

    private void updateSeat() {
        System.out.println("Bus Id: ");
        int id = sc.nextInt();
        System.out.println("New Available Seats: ");
        int seats = sc.nextInt();

        bus.updateSeat(id, seats);
    }

    private void deleteBus() {
        System.out.println("Enter the bus Id to delete: ");
        int id = sc.nextInt();
        bus.deleteBus(id);
    }

    // UPDATED: Show pending tickets first, then ask for ticket ID to approve
    private void approveTicket() {
        // First display all pending tickets
        bus.displayPendingTickets();

        System.out.println("Enter the Ticket ID to approve (from the list above): ");
        int id = sc.nextInt();
        bus.approveTicket(id);
    }

    // passenger panel
    public void startAppUser() {
        while (true) {
            System.out.println("______________________________________________________________________________________________");
            System.out.println("Choose option: \n 1. View Bus \n 2. Book Bus \n 3. Generate Ticket \n 4. View Ticket \n 5. Exit Panel");
            System.out.println("______________________________________________________________________________________________");
            int ch = sc.nextInt();
            switch (ch) {
                case 1:
                    viewBus();
                    break;
                case 2:
                    bookBus();
                    break;
                case 3:
                    generateTicket();
                    break;
                case 4:
                    viewTicket();
                    break;
                case 5:
                    System.out.println("Thank You.");
                    login();
                    break;
                default:
                    System.out.println("Invalid Choice. Try Again");
            }
        }
    }

    private void bookBus() {
        System.out.println("Enter Name: ");
        String name = sc.next();

        System.out.println("Enter Phone Number: ");
        String phone = sc.next();

        System.out.println("Enter Age:");
        int age = sc.nextInt();

        System.out.println("Enter Bus Id: ");
        int busId = sc.nextInt();

        System.out.println("Enter Source: ");
        String source = sc.next();

        System.out.println("Enter Destination: ");
        String destination = sc.next();

        passenger.bookBus(name, phone, age,busId, source, destination);
    }

    private void viewBus() {
        bus.displayAllBus();
    }

    private void generateTicket() {
        System.out.println("Enter Passenger ID to Generate Ticket: ");
        int id = sc.nextInt();
        passenger.generateTicket(id);
    }

    // View ticket method that asks for passenger ID and shows ticket
    private void viewTicket() {
        System.out.println("Enter your Passenger ID to view your tickets: ");
        int passengerId = sc.nextInt();
        passenger.viewTickets(passengerId);
    }
}