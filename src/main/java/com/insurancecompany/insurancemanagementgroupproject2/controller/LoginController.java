package com.insurancecompany.insurancemanagementgroupproject2.controller;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.SceneLoader;
import com.insurancecompany.insurancemanagementgroupproject2.model.LoginData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private Button cancelButton;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void loginButtonOnAction() {
        if (!usernameTextField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            validateLogin();
        } else {
            loginMessageLabel.setText("Please enter your username and password!");
        }
    }

    @FXML
    public void cancelButtonOnAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void validateLogin() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        String verifyLoginQuery = "SELECT role_id FROM users WHERE user_name = ? AND password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(verifyLoginQuery);
            preparedStatement.setString(1, usernameTextField.getText());
            preparedStatement.setString(2, passwordField.getText());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int roleId = resultSet.getInt("role_id");
                System.out.println(roleId);
                switch (roleId) {
                    case 1 -> {
                        LoginData.usernameLogin = usernameTextField.getText();
                        LoginData.roleId = roleId;
                        loadAdminHomePage();
                        System.out.println(LoginData.usernameLogin);
                    }
                    case 2 -> {
                        LoginData.usernameLogin = usernameTextField.getText();
                        LoginData.roleId = roleId;
                        loadSurveyorHomePage();
                        System.out.println(LoginData.usernameLogin);
                    }
                    case 3 -> {
                        LoginData.usernameLogin = usernameTextField.getText();
                        LoginData.roleId = roleId;
                        loadManagerHomePage();
                        System.out.println(LoginData.usernameLogin);
                    }
                    case 6 -> {
                        LoginData.usernameLogin = usernameTextField.getText();
                        LoginData.roleId = roleId;
                        loadDependentHomePage();
                        System.out.println(LoginData.usernameLogin);
                    }
                    default -> loginMessageLabel.setText("Unknown role!");
                }
            } else {
                loginMessageLabel.setText("Invalid username or password!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            loginMessageLabel.setText("Database error!");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadAdminHomePage() {
        Stage currentStage = (Stage) usernameTextField.getScene().getWindow();
        currentStage.setTitle("Admin Portal");
        SceneLoader.loadScene("fxml/admin-homepage.fxml", currentStage);
    }

    public void loadSurveyorHomePage() {
        Stage currentStage = (Stage) usernameTextField.getScene().getWindow();
        SceneLoader.loadScene("fxml/surveyor-homepage.fxml", currentStage);
    }
    public void loadManagerHomePage() {
        Stage currentStage = (Stage) usernameTextField.getScene().getWindow();
        SceneLoader.loadScene("fxml/manager-homepage.fxml", currentStage);
    }

    public void loadDependentHomePage() {
        Stage currentStage = (Stage) usernameTextField.getScene().getWindow();
        SceneLoader.loadScene("fxml/dependent-homepage.fxml", currentStage);
    }
}
