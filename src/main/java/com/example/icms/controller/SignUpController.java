package com.example.icms.controller;


import com.example.icms.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpController {

    @FXML
    private TextField fullNameTextField;

    @FXML
    private TextField userNameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private ChoiceBox<String> roleChoiceBox;

    @FXML
    private Label signUpMessageLabel;

    @FXML
    private Label confirmPasswordMessageLabel;

    @FXML
    private Label roleMessageLabel;

    public void registerButtonOnAction(ActionEvent event) {
        if (roleChoiceBox.getValue() == null) {
            roleMessageLabel.setText("Please select a role!");
            return;
        }

        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            confirmPasswordMessageLabel.setText("Password does not match!");
            return;
        }

        confirmPasswordMessageLabel.setText("");
        roleMessageLabel.setText("");
        signUpUser();
    }


    public void signUpUser(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String fullName = fullNameTextField.getText();
        String userName = userNameTextField.getText();
        String password = passwordField.getText();
        int selectedRole = changeRoleNameToRoleId(roleChoiceBox.getValue());
        System.out.println("selectedRole:  " + selectedRole);

        String insertStatement = "INSERT INTO users (full_name, user_name, password, role_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertStatement)) {
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, password);
            preparedStatement.setInt(4, selectedRole);
            
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                signUpMessageLabel.setText("You have been registered successfully!");
            } else {
                signUpMessageLabel.setText("Fail to register!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public int changeRoleNameToRoleId(String roleName){
        String selectedRole = roleChoiceBox.getValue();
        if(selectedRole.equalsIgnoreCase("Insurance Manager")){
            return 2;
        } else if (selectedRole.equalsIgnoreCase("Insurance Surveyor")) {
            return 3;
        } else if (selectedRole.equalsIgnoreCase("Policy Owner")){
            return 4;
        }else if (selectedRole.equalsIgnoreCase("Policy Holder")){
            return 5;
        }else {
            return 6;
        }
    }
    public void signInButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/icms/login.fxml"));
            Parent root = fxmlLoader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root, 400, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
