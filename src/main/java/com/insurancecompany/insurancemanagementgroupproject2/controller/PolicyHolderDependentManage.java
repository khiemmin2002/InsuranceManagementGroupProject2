package com.insurancecompany.insurancemanagementgroupproject2.controller;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PolicyHolderDependentManage {

    @FXML
    private Button cancelUpdateButton;

    @FXML
    private Button confirmUpdateBtn;

    @FXML
    private TextField updateAddressField;

    @FXML
    private TextField updateDependentID;

    @FXML
    private TextField updateEmailField;

    @FXML
    private TextField updatePassWordField;

    @FXML
    private TextField updatePhoneNumField;

    @FXML
    private Label validationMessage;

    @FXML
    void cancelUpdate(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void confirmUpdate(ActionEvent event) {
        String dependentId = updateDependentID.getText();
        String phoneNumber = updatePhoneNumField.getText();
        String password = updatePassWordField.getText();
        String email = updateEmailField.getText();
        String address = updateAddressField.getText();
        if (dependentId.isEmpty() || phoneNumber.isEmpty() || password.isEmpty() || email.isEmpty() || address.isEmpty()) {
            validationMessage.setText("Please fill in all fields");
        }
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (Connection connection = databaseConnection.getConnection()) {
            String updateQuery = "UPDATE public.users SET password = ?, email = ?,phone_number = ?, address = ? WHERE id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phoneNumber);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, dependentId);

            preparedStatement.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Dependent updated successfully.");
            alert.showAndWait();

            clearInputFields();

        } catch (SQLException e) {
            validationMessage.setText("Error: Unable to update dependent. Please try again");
            System.out.println(e.getMessage());
        }
    }
    private void clearInputFields() {
        updateDependentID.clear();
        updatePassWordField.clear();
        updateAddressField.clear();
        updateEmailField.clear();
        updatePhoneNumField.clear();
    }

}
