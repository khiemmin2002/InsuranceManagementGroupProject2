package com.example.icms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection() {
        String url = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:5432/postgres?user=postgres.kdvbunduidmibrvamrpj&password=Group5PasswordHardToGuess";

        try {
            Class.forName("org.postgresql.Driver");
            databaseLink = DriverManager.getConnection(url);
            System.out.println("Connected to the PostgreSQL database successfully!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return databaseLink;
    }
}
