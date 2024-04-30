package com.example.icms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection(){
        String databaseName = "insurance_card_managenent_system";
        String databaseUser = "postgres";
        String databasePassword = "nguyenaluy";
        String url = "jdbc:postgresql://localhost:5432/"+databaseName;

        try{
            Class.forName("org.postgresql.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
            System.out.println("Connected to the PostgreSQL database successfully!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return databaseLink;
    }
}
