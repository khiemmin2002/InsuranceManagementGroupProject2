package com.insurancecompany.insurancemanagementgroupproject2.controller;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class ClaimController {
    @FXML
    private Pane addClaimPane;

    @FXML
    private TextField bankNameField;

    @FXML
    private TextField bankNumberField;

    @FXML
    private TextField bankUserNameField;


    @FXML
    private Button cancelButton;

    @FXML
    private TextField cardNumberInput;

    @FXML
    private TextField claimAmountInput;

    @FXML
    private Button confirmAddButton;

    @FXML
    private TextField insuredPersonInput;

    @FXML
    private Label validationMessage;

    @FXML
    void confirmAddClaim(ActionEvent event)  {
        String claimId = generateRandomClaimID();
        String cardNumber = cardNumberInput.getText();
        String claimAmountText = claimAmountInput.getText();
        String insuredPerson = insuredPersonInput.getText();
        String bankName = bankNameField.getText();
        String bankUserName = bankUserNameField.getText();
        String bankNumber = bankNumberField.getText();

        if (cardNumber.isEmpty() || claimAmountText.isEmpty() || insuredPerson.isEmpty()) {
            if (cardNumber.isEmpty() || claimAmountText.isEmpty() || insuredPerson.isEmpty()) {
                System.out.println("Debug: cardNumber: " + cardNumber);
                System.out.println("Debug: claimAmountText: " + claimAmountText);
                System.out.println("Debug: insuredPerson: " + insuredPerson);
                validationMessage.setText("Please fill in all fields");
                return;
            }

            validationMessage.setText("Please fill in all field");
            return;
        }
        double claimAmount = Double.parseDouble(claimAmountText);
        DatabaseConnection databaseConnection = new DatabaseConnection();

        try (Connection connection = databaseConnection.getConnection()) {
            String insertQuery = "INSERT INTO public.claims (claim_id, insured_person, card_number, exam_date, claim_date, claim_amount, status, bank_name, bank_user_name, bank_number) " +
                    "VALUES (?, ?, ?, NULL, NULL, ?, 'NEW', ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, claimId);
            preparedStatement.setString(2, insuredPerson);
            preparedStatement.setString(3, cardNumber);
            preparedStatement.setDouble(4, claimAmount);
            preparedStatement.setString(5, bankName);
            preparedStatement.setString(6, bankUserName);
            preparedStatement.setString(7, bankNumber);

            preparedStatement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Claim added successfully.");
            alert.showAndWait();

            clearInputFields();
        } catch (SQLException e) {
            validationMessage.setText("Error: Unable to add claim. Please try again.");
            e.printStackTrace();
        }
    }
    @FXML
    void backToHomePage(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/policy-holder-homepage.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Policy Holder Homepage");
            stage.setScene(new Scene(root));
            PolicyHolderController controller = fxmlLoader.getController();
            controller.fetchClaimData();
            stage.show();

            cancelButton.getScene().getWindow().hide();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean proposeClaim(String claimID){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        try{
            String proposeClaim = "UPDATE claims SET status = 'PROCESSING', exam_date = CURRENT_DATE WHERE claim_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(proposeClaim);
            preparedStatement.setString(1,claimID);
            preparedStatement.execute();
            System.out.println("Successfully propose claim " + claimID);
            return true;
        } catch (SQLException e) {
            System.out.println("Error in SQL function proposeClaim: " + e);
            return false;
        }
    }

    

    private void clearInputFields() {
        cardNumberInput.clear();
        claimAmountInput.clear();
        insuredPersonInput.clear();
        validationMessage.setText("");
    }
    private String generateRandomClaimID() {
        StringBuilder claimId = new StringBuilder("F");
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            claimId.append(random.nextInt(10));
        }
        return claimId.toString();
    }

}
