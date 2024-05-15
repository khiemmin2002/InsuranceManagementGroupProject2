package com.insurancecompany.insurancemanagementgroupproject2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
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
    private PolicyHolderClaimController policyHolderClaimController = new PolicyHolderClaimController();

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

        try {

            double claimAmount = Double.parseDouble(claimAmountText);
            policyHolderClaimController.updateClaim(claimId, insuredPersonID, cardNumber, claimAmount, bankName, bankUserName, bankNumber);


            if (!uploadedDocumentNames.isEmpty()) {
                policyHolderClaimController.updateDocumentDetails(claimId, uploadedDocumentNames);
                uploadedDocumentNames.clear();
            }


            showAlert(Alert.AlertType.INFORMATION, "Claim and associated documents updated successfully.");
            clearInputFields();
        } catch (SQLException e) {

            validationMessage.setText("Error updating claim: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            validationMessage.setText("Error in number input: " + e.getMessage());
            e.printStackTrace();
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

    private void updateDocumentDetails(String claimId) {
        try {
            if (!uploadedDocumentNames.isEmpty()) {
                policyHolderClaimController.updateDocumentDetails(claimId, uploadedDocumentNames);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            validationMessage.setText("Error updating documents: " + e.getMessage());
        }
    }


    @FXML
    void cancelUpdateClaim(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

}