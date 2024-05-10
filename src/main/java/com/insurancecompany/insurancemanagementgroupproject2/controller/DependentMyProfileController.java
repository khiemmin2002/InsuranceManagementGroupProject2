package com.insurancecompany.insurancemanagementgroupproject2.controller;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.model.Claim;
import com.insurancecompany.insurancemanagementgroupproject2.model.Dependent;
import com.insurancecompany.insurancemanagementgroupproject2.model.LoginData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.Label;

public class DependentMyProfileController implements Initializable {
    @FXML
    private Label dependentAddress;

    @FXML
    private Label dependentEmail;

    @FXML
    private Label dependentFullName;

    @FXML
    private Label dependentIDField;

    @FXML
    private Label dependentPhoneNum;

    @FXML
    private Label dependentUsername;


    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fetchUserData(LoginData.usernameLogin);
    }

    private void fetchUserData(String username) {
        String query = "SELECT id, full_name, user_name, email, phone_number, address FROM users WHERE user_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    dependentIDField.setText(resultSet.getString("id"));
                    dependentFullName.setText(resultSet.getString("full_name"));
                    dependentUsername.setText(resultSet.getString("user_name"));
                    dependentEmail.setText(resultSet.getString("email"));
                    dependentPhoneNum.setText(resultSet.getString("phone_number"));
                    dependentAddress.setText(resultSet.getString("address"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user data: " + e.getMessage());
            // Handle error - show message to user and log
        }
    }

}
