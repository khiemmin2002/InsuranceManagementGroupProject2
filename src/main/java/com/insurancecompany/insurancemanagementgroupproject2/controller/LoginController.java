package com.insurancecompany.insurancemanagementgroupproject2.controller;
/**
 * @author team 5
 */
import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    // Create an instance of BcryptPassword class object
    BcryptPassword bcryptPassword = new BcryptPassword();
    // Method to validate field and allow user to login/showing login error
    public int validateLogin(String usernameTextField, String passwordField) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        // Perform logic to very file input, Catching exception
        try {
            String verifyLoginQuery = "SELECT password, role_id FROM users WHERE user_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(verifyLoginQuery);
            preparedStatement.setString(1, usernameTextField);
            ResultSet resultSet = preparedStatement.executeQuery();
            // Check value of password
            if (resultSet.next()) {
                String hashedPasswordFromDB = resultSet.getString("password");
                int roleId = resultSet.getInt("role_id");
                // Boolean for logic check
                boolean passwordMatch = bcryptPassword.verifyHashedPassword(hashedPasswordFromDB, passwordField);
                // Verify password
                if (passwordMatch) {
                    return roleId;
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
