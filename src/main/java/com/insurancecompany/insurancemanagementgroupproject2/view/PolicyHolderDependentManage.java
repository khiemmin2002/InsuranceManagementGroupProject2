package com.insurancecompany.insurancemanagementgroupproject2.view;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.controller.policyholder.PolicyHolderDependentController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
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

    private DatabaseConnection databaseConnection;

    private Connection connection;

    private PolicyHolderDependentController controller = new PolicyHolderDependentController(databaseConnection, connection);

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
            return;
        }
        try {
            controller.updateDependent(dependentId, password, email, phoneNumber, address);
            showAlert("Success", "Dependent updated successfully.", Alert.AlertType.INFORMATION);
            clearInputFields();
        } catch (SQLException e) {
            validationMessage.setText("Error: Unable to update dependent. Please try again");
            System.out.println(e.getMessage());
        }

    }
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void clearInputFields() {
        updateDependentID.clear();
        updatePassWordField.clear();
        updateAddressField.clear();
        updateEmailField.clear();
        updatePhoneNumField.clear();
    }

}
