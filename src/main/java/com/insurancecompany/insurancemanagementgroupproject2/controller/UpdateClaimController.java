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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UpdateClaimController {

    @FXML
    private Button cancelUpdateBtn;

    @FXML
    private Button confirmUpdateBtn;

    @FXML
    private Button btnUploadDocuments;

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
    private  List<String> uploadedDocumentNames = new ArrayList<>();

    @FXML
    void confirmUpdateClaim(ActionEvent event) {
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

            for (String documentName: uploadedDocumentNames) {
                updateDocumentDetails(claimId, documentName);
            }
            showAlert(Alert.AlertType.INFORMATION, "Claim and associated documents updated successfully.");
            clearInputFields();
            uploadedDocumentNames.clear();

        } catch (SQLException e) {
            validationMessage.setText("Error: Unable to add claim. Please try again");
            System.out.println(e.getMessage());
        }

    }
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message);
        alert.showAndWait();
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
    void uploadMultipleFiles(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extensionFilter);


        List<File> files = fileChooser.showOpenMultipleDialog(((Node) event.getSource()).getScene().getWindow());
        if (files != null && !files.isEmpty()) {
            for (File file : files) {
                String renamedFile = renameAndSaveFile(file);
                if (renamedFile != null) {
                    uploadedDocumentNames.add(renamedFile);
                }
            }
        } else {
            System.out.println("No files were selected.");
        }
    }

    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");

        if (lastIndexOf == -1) {
            return "";
        }
        return name.substring(lastIndexOf);
    }

    private String renameAndSaveFile(File originalFile) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(formatter);


        String originalFileName = originalFile.getName();
        String baseName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
        String extension = getFileExtension(originalFile);

        String newFileName = formattedDate + "_" + baseName + extension;;
        System.out.println("File renamed to: " + newFileName);
        return newFileName;
    }
    private void updateDocumentDetails(String claimId, String documentName) {
        String updateQuery = "UPDATE documents SET document_name = ? WHERE claim_id = ?";
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, documentName);
            preparedStatement.setString(2, claimId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
}

    @FXML
    void cancelUpdateClaim(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

}
