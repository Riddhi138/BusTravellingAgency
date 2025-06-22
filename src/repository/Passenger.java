package repository;

import java.sql.*;
import system.Driver;

public class Passenger {

    private Connection con;

    public Passenger(Connection con) {
        this.con = con;
    }

    // Book a bus and print passenger ID after registration
    public void bookBus(String name, String phone, int age, int busId, String source, String destination) {
        try (Connection connection = DriverManager.getConnection(Driver.getUrl(), Driver.getUsername(), Driver.getPassword())) {

            // Check if a bus exists with the given busId, source, and destination (case-insensitive)
            String checkSql = "SELECT * FROM Bus WHERE bus_id = ? AND LOWER(source) = LOWER(?) AND LOWER(destination) = LOWER(?)";
            PreparedStatement checkStmt = connection.prepareStatement(checkSql);
            checkStmt.setInt(1, busId);
            checkStmt.setString(2, source.trim());
            checkStmt.setString(3, destination.trim());

            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Bus not found for ID: " + busId + " with source: " + source + " and destination: " + destination);
                return;
            }

            // Insert passenger with valid bus_id and get the generated passenger_id
            String insertPassenger = "INSERT INTO Passenger (name, phone_no, age, bus_id, source, destination) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(insertPassenger, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setInt(3, age);
            stmt.setInt(4, busId);
            stmt.setString(5, source.trim());
            stmt.setString(6, destination.trim());

            stmt.executeUpdate();

            // Get the generated passenger_id and display it
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int passengerId = generatedKeys.getInt(1);
                System.out.println("Booking Successful. Passenger Registered.");
                System.out.println("*** IMPORTANT: Your Passenger ID is: " + passengerId + " ***");
                System.out.println("*** Please remember this ID for generating tickets and viewing your bookings! ***");
            }

        } catch (SQLException e) {
            System.out.println("Error booking bus: " + e.getMessage());
        }
    }

    // Generate ticket
    public void generateTicket(int passengerId) {
        try (Connection connection = DriverManager.getConnection(Driver.getUrl(), Driver.getUsername(), Driver.getPassword())) {

            // Get passenger details including bus_id
            String fetchSql = "SELECT * FROM Passenger WHERE passenger_id = ?";
            PreparedStatement fetchStmt = connection.prepareStatement(fetchSql);
            fetchStmt.setInt(1, passengerId);
            ResultSet rs = fetchStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Passenger not found with ID: " + passengerId);
                return;
            }

            int busId = rs.getInt("bus_id");
            String source = rs.getString("source");
            String destination = rs.getString("destination");

            // Insert ticket including bus_id
            String insertTicket = "INSERT INTO Ticket (passenger_id, bus_id, source, destination, status) VALUES (?, ?, ?, ?, 'Pending')";
            PreparedStatement ticketStmt = connection.prepareStatement(insertTicket);
            ticketStmt.setInt(1, passengerId);
            ticketStmt.setInt(2, busId);
            ticketStmt.setString(3, source);
            ticketStmt.setString(4, destination);

            ticketStmt.executeUpdate();
            System.out.println("Ticket generated successfully and is pending approval.");

        } catch (SQLException e) {
            System.out.println("Error generating ticket: " + e.getMessage());
        }
    }

    // View tickets for a specific passenger
    public void viewTickets(int passengerId) {
        try (Connection connection = DriverManager.getConnection(Driver.getUrl(), Driver.getUsername(), Driver.getPassword()))
        {

            // make nickname (t,p,b) here for each db table
            // we performed join operations to gather info from diff. table

            String sql = "SELECT t.ticket_id, t.passenger_id, t.bus_id, t.source, t.destination, t.status, " +
                    "p.name, p.phone_no, p.age, b.arrival_time " +
                    "FROM Ticket t " +
                    "JOIN Passenger p ON t.passenger_id = p.passenger_id " +
                    "JOIN Bus b ON t.bus_id = b.bus_id " +
                    "WHERE t.passenger_id = ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, passengerId);
            ResultSet resultSet = stm.executeQuery();

            System.out.println("\n" + "=".repeat(80));
            System.out.println("                          YOUR TICKET DETAILS");
            System.out.println("=".repeat(80));

            boolean hasTickets = false;
            while (resultSet.next()) {
                hasTickets = true;
                System.out.println("Ticket ID: " + resultSet.getInt("ticket_id"));
                System.out.println("Passenger Name: " + resultSet.getString("name"));
                System.out.println("Passenger ID: " + resultSet.getInt("passenger_id"));
                System.out.println("Phone Number: " + resultSet.getString("phone_no"));
                System.out.println("Age: " + resultSet.getInt("age"));
                System.out.println("Bus ID: " + resultSet.getInt("bus_id"));
                System.out.println("Source: " + resultSet.getString("source"));
                System.out.println("Destination: " + resultSet.getString("destination"));
                System.out.println("Arrival Time: " + resultSet.getString("arrival_time"));
                System.out.println("Status: " + resultSet.getString("status"));
                System.out.println("-".repeat(80));
            }
            System.out.println("Seat will be Confirmed only after Status: Approved.");

            if (!hasTickets) {
                System.out.println("No tickets found for Passenger ID: " + passengerId);
                System.out.println("Please check your Passenger ID or book a bus first.");
            }

            System.out.println("=".repeat(80) + "\n");

        } catch (Exception e) {
            System.out.println("Error viewing tickets: " + e.getMessage());
        }
    }
}