package repository;

import java.sql.*;
import static system.Driver.getUrl;
import static system.Driver.getUsername;
import static system.Driver.getPassword;

public class Bus {

    public Bus(Connection con) {
        // Constructor accepting a connection, though not used
    }

    // Admin login method
    public static boolean adminLogin(String user, String pass) {
        try (Connection connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword())) {
            String sql = "SELECT * FROM Admin WHERE username = ? AND password = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user);
            stm.setString(2, pass);
            ResultSet resultSet = stm.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Display all bus records
    public void displayAllBus() {
        try (Connection connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword())) {
            String sql = "SELECT * FROM bus";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet resultSet = stm.executeQuery();
            System.out.println("Available Buses:");

            while (resultSet.next()) {
                System.out.println("Bus ID: " + resultSet.getInt("bus_id"));
                System.out.println("Source: " + resultSet.getString("source"));
                System.out.println("Destination: " + resultSet.getString("destination"));
                System.out.println("Arrival Time: " + resultSet.getString("arrival_time"));
                System.out.println("Total Seats: " + resultSet.getInt("total_seats"));
                System.out.println("Available Seats: " + resultSet.getInt("available_seats"));
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Insert a new bus
    public void insertBusFlow(int id, String source, String destination, String arrival, int totalSeats, int availSeats) {
        try (Connection connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword())) {
            String sql = "INSERT INTO Bus VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.setString(2, source);
            stm.setString(3, destination);
            stm.setTime(4, Time.valueOf(arrival));
            stm.setInt(5, totalSeats);
            stm.setInt(6, availSeats);
            stm.executeUpdate();
            System.out.println("Bus details entered successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Update available seats for a bus
    public void updateSeat(int busId, int newAvailSeats) {
        try (Connection connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword())) {
            String sql = "UPDATE Bus SET available_seats = ? WHERE bus_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, newAvailSeats);
            stm.setInt(2, busId);
            stm.executeUpdate();
            System.out.println("Available seats updated successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Delete a bus
    public void deleteBus(int busId) {
        try (Connection connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword())) {
            String sql = "DELETE FROM Bus WHERE bus_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, busId);
            stm.executeUpdate();
            System.out.println("Bus deleted successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // NEW: Display all pending tickets with their IDs
    public void displayPendingTickets() {

        try (Connection connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword()))
        {
            //nickname t,b,p for db table and perform join operation
            String sql = "SELECT t.ticket_id, t.passenger_id, t.bus_id, t.source, t.destination, t.status, p.name " +
                    "FROM Ticket t JOIN Passenger p ON t.passenger_id = p.passenger_id " +
                    "WHERE t.status = 'Pending'";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet resultSet = stm.executeQuery();

            System.out.println("\n=== PENDING TICKETS ===");
          //  System.out.println("Format: [Ticket ID] - Passenger: Name (ID: passenger_id) | Bus ID: bus_id | Route: source -> destination");
            System.out.println("----------------------------------------------------------------------------------------");

            boolean hasPendingTickets = false;
            while (resultSet.next()) {
                hasPendingTickets = true;

                //retrieve info using ResultSet obj

                System.out.println("[" + resultSet.getInt("ticket_id") + "] - Passenger: " +
                        resultSet.getString("name") +
                        " (ID: " + resultSet.getInt("passenger_id") +
                        ") | Bus ID: " + resultSet.getInt("bus_id") +
                        " | Route: " + resultSet.getString("source") + " -> " +
                        resultSet.getString("destination"));
            }

            if (!hasPendingTickets) {
                System.out.println("No pending tickets found.");
            }
            System.out.println("========================\n");

        } catch (Exception e) {
            System.out.println("Error displaying pending tickets: " + e.getMessage());
        }
    }

    //Approve a ticket and decrease available seats
    public void approveTicket(int ticketId) {
        try (Connection connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword())) {
            // get the bus_id from the ticket
            String getBusIdSql = "SELECT bus_id FROM Ticket WHERE ticket_id = ? AND status = 'Pending'";
            PreparedStatement getBusStmt = connection.prepareStatement(getBusIdSql);
            getBusStmt.setInt(1, ticketId);
            ResultSet rs = getBusStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Ticket not found or already processed.");
                return;
            }

            int busId = rs.getInt("bus_id");

            // Update ticket status to approved
            String updateTicketSql = "UPDATE Ticket SET status = 'Approved' WHERE ticket_id = ?";
            PreparedStatement updateTicketStmt = connection.prepareStatement(updateTicketSql);
            updateTicketStmt.setInt(1, ticketId);
            updateTicketStmt.executeUpdate();

            // Decrease available seats for the bus
            String updateSeatsSql = "UPDATE Bus SET available_seats = available_seats - 1 WHERE bus_id = ? AND available_seats > 0";
            PreparedStatement updateSeatsStmt = connection.prepareStatement(updateSeatsSql);
            updateSeatsStmt.setInt(1, busId);
            int rowsAffected = updateSeatsStmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Ticket approved successfully. Available seats decreased for Bus ID: " + busId);
            } else {
                System.out.println("Ticket approved, but no seats were available to decrease for Bus ID: " + busId);
            }

        } catch (Exception e) {
            System.out.println("Error approving ticket: " + e.getMessage());
        }
    }
}