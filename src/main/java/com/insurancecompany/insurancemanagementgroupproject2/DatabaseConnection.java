package com.insurancecompany.insurancemanagementgroupproject2;
/**
 * @author team 5
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public Connection databaseLink;
    // Method to connect to database through URL string
    public Connection getConnection() {
        String url = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:5432/postgres?user=postgres.kdvbunduidmibrvamrpj&password=Group5PasswordHardToGuess";
        // Catching error
        try {
            Class.forName("org.postgresql.Driver");
            databaseLink = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error from database connection: " + e );
        }
        return databaseLink;
    }
}