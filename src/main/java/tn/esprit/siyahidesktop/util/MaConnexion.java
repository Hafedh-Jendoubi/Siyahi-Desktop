package tn.esprit.siyahidesktop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaConnexion {
    // Database connection details
    final String URL = "jdbc:mysql://localhost:3306/siyahi?";
    final String USR = "root";
    final String PWD = "";

    // Singleton instance of Connection
    private Connection cnx;
    private static MaConnexion instance;

    // Private constructor for singleton pattern
    private MaConnexion() {
        try {
            // Load the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Create the connection to the database
            cnx = DriverManager.getConnection(URL, USR, PWD);
            System.out.println("Connection to database successful.");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        } catch (SQLException e) {
            System.err.println("Error establishing database connection: " + e.getMessage());
            throw new RuntimeException("Error establishing database connection", e);
        }
    }

    // Public method to get the instance of the connection
    public static MaConnexion getInstance() {
        if (instance == null) {
            instance = new MaConnexion();
        }
        return instance;
    }

    // Getter for the connection
    public Connection getCnx() {
        return cnx;
    }

    // Method to test if the connection is still open
    public boolean testConnection() {
        try {
            if (cnx != null && !cnx.isClosed()) {
                System.out.println("Connection is active.");
                return true;
            } else {
                System.out.println("Connection is closed.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Failed to check connection status: " + e.getMessage());
            return false;
        }
    }
}
