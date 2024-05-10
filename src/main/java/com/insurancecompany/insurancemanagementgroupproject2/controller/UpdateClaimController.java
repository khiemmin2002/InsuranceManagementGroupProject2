package com.insurancecompany.insurancemanagementgroupproject2.controller;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateClaimController {

    @FXML
    private Button cancelUpdateBtn;

    @FXML
    private Button confirmUpdateBtn;

    @FXML
    private TextField updateBankNameField;

    @FXML
    private TextField updateBankNumberField;

    @FXML
    private TextField updateBankUserNameField;
    @FXML
    private TextField updateCardNumberField;

    @FXML
    private TextField updateClaimAmountField;

    @FXML
    private TextField updateClaimIDField;

    @FXML
    private TextField updateInsuredPersonIDField;

    @FXML
    private Label validationMessage;

    @FXML
    void confirmAddClaim(ActionEvent event) {
        String claimId = updateClaimIDField.getText();
        String insuredPersonID = updateInsuredPersonIDField.getText();
        String cardNumber = updateCardNumberField.getText();
        String claimAmountText = updateClaimAmountField.getText();
        String bankName = updateBankNameField.getText();
        String bankUserName = updateBankUserNameField.getText();
        String bankNumber = updateBankNumberField.getText();
        if (claimId.isEmpty() || insuredPersonID.isEmpty() || cardNumber.isEmpty() || claimAmountText.isEmpty() || bankName.isEmpty() || bankNumber.isEmpty() || bankUserName.isEmpty()) {
            validationMessage.setText("Please fill in all fields");
            return;
        }
        double claimAmount = Double.parseDouble(claimAmountText);
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (Connection connection = databaseConnection.getConnection()) {
            String updateQuery = "UPDATE public.claims SET insured_person = ?, card_number = ?, claim_amount = ?, bank_name = ?, bank_user_name = ?, bank_number = ? WHERE claim_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, insuredPersonID);
            preparedStatement.setString(2, cardNumber);
            preparedStatement.setDouble(3, claimAmount);
            preparedStatement.setString(4, bankName);
            preparedStatement.setString(5, bankUserName);
            preparedStatement.setString(6, bankNumber);
            preparedStatement.setString(7, claimId);
            preparedStatement.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Claim added successfully.");
            alert.showAndWait();

            clearInputFields();


        } catch (SQLException e) {
            validationMessage.setText("Error: Unable to add claim. Please try again");
            System.out.println(e.getMessage());
        }


    }

    private void clearInputFields() {
        updateClaimIDField.clear();
        updateCardNumberField.clear();
        updateInsuredPersonIDField.clear();
        updateClaimAmountField.clear();
        updateBankNameField.clear();
        updateBankUserNameField.clear();
        updateBankNumberField.clear();
    }

    @FXML
    void cancelUpdateClaim(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/insurancecompany/insurancemanagementgroupproject2/fxml/policy-holder-homepage.fxml"));

            if (fxmlLoader.getLocation() == null) {
                validationMessage.setText("Error: Cannot find FXML file.");
                return;
            }
            Parent root = fxmlLoader.load();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setTitle("Policy Holder Homepage");
            currentStage.setScene(new Scene(root));
            PolicyHolderController controller = fxmlLoader.getController();
            controller.fetchClaimData();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unexpected Error");
            alert.setContentText("An unexpected error occurred: " + e.getMessage());
            alert.showAndWait();
        }
    }

}
