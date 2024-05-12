package com.insurancecompany.insurancemanagementgroupproject2.controller;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    BcryptPassword bcryptPassword = new BcryptPassword();

    public int validateLogin(String usernameTextField, String passwordField) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        try {
            String verifyLoginQuery = "SELECT password, role_id FROM users WHERE user_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(verifyLoginQuery);
            preparedStatement.setString(1, usernameTextField);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String hashedPasswordFromDB = resultSet.getString("password");
                int roleId = resultSet.getInt("role_id");

                boolean passwordMatch = bcryptPassword.verifyHashedPassword(hashedPasswordFromDB, passwordField);

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
