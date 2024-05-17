package com.insurancecompany.insurancemanagementgroupproject2.view;

import com.insurancecompany.insurancemanagementgroupproject2.controller.policyholder.PHClaimController;
import com.insurancecompany.insurancemanagementgroupproject2.model.Claim;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PolicyHolderClaimManage {
    @FXML
    private Pane addClaimPane;

    @FXML
    private TextField bankNameField;

    @FXML
    private TextField bankNumberField;

    @FXML
    private TextField bankUserNameField;

    @FXML
    private Button btnUploadDocuments;



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


    private String currentClaimId;

    private List<String> uploadedDocumentNames = new ArrayList<>();

    private PHClaimController PHClaimController = new PHClaimController();


    @FXML
    void confirmAddClaim(ActionEvent event)  {
        if (currentClaimId == null || currentClaimId.isEmpty()) {
            currentClaimId = generateRandomClaimID();
        }
        btnUploadDocuments.setDisable(false);
        System.out.println("Current claim ID: " + currentClaimId);
        String cardNumber = cardNumberInput.getText();
        String claimAmountText = claimAmountInput.getText();
        String insuredPerson = insuredPersonInput.getText();
        String bankName = bankNameField.getText();
        String bankUserName = bankUserNameField.getText();
        String bankNumber = bankNumberField.getText();

        if (cardNumber.isEmpty() || claimAmountText.isEmpty() || insuredPerson.isEmpty() || bankName.isEmpty() || bankUserName.isEmpty() || bankNumber.isEmpty()) {
            System.out.println("Debug: cardNumber: " + cardNumber);
            System.out.println("Debug: claimAmountText: " + claimAmountText);
            System.out.println("Debug: insuredPerson: " + insuredPerson);
            validationMessage.setText("Please fill in all fields");
            return;

        }
        try {
            double claimAmount = Double.parseDouble(claimAmountText);
            Claim claim = new Claim(currentClaimId, insuredPerson, cardNumber, null, null, claimAmount, "NEW", bankName, bankUserName, bankNumber);
            PHClaimController.addClaim(claim, uploadedDocumentNames);
            showAlert(Alert.AlertType.INFORMATION, "Claim and associated documents added successfully.");
            clearInputFields();
            uploadedDocumentNames.clear();
        } catch (SQLException e) {
            validationMessage.setText("Error: " + e.getMessage());
        }


    }

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
            PHClaimController.updateClaim(claimId, insuredPersonID, cardNumber, claimAmount, bankName, bankUserName, bankNumber);


            if (!uploadedDocumentNames.isEmpty()) {
                PHClaimController.updateDocumentDetails(claimId, uploadedDocumentNames);
                uploadedDocumentNames.clear();
            }


            showAlert(Alert.AlertType.INFORMATION, "Claim and associated documents updated successfully.");
            clearUpdateInputFields();
        } catch (SQLException e) {

            validationMessage.setText("Error updating claim: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            validationMessage.setText("Error in number input: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void backToHomePage(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void cancelUpdateClaim(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message);
        alert.showAndWait();
    }


    private void clearInputFields() {
        cardNumberInput.clear();
        claimAmountInput.clear();
        insuredPersonInput.clear();
        bankNameField.clear();
        bankNumberField.clear();
        bankUserNameField.clear();
        validationMessage.setText("");
    }

    private void clearUpdateInputFields() {
        updateClaimIDField.clear();
        updateCardNumberField.clear();
        updateInsuredPersonIDField.clear();
        updateClaimAmountField.clear();
        updateBankNameField.clear();
        updateBankUserNameField.clear();
        updateBankNumberField.clear();
    }
    private String generateRandomClaimID() {
        StringBuilder claimId = new StringBuilder("F");
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            claimId.append(random.nextInt(10));
        }
        return claimId.toString();
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




    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");

        if (lastIndexOf == -1) {
            return "";
        }
        return name.substring(lastIndexOf);
    }
}
