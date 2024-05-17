package com.insurancecompany.insurancemanagementgroupproject2.controller;
/**
 * @author team 5
 */
import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.model.LoginData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DependentController{
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();
    // Method to get ID of a user
    public String getIDFromUserName(String username) {
        String getIDQuery = "SELECT id FROM users WHERE user_name = ?";
        String userId= "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getIDQuery);
            preparedStatement.setString(1, LoginData.usernameLogin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getString("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userId;
    }
}
