package com.insurancecompany.insurancemanagementgroupproject2.controller;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.HelloApplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import com.insurancecompany.insurancemanagementgroupproject2.model.Claim;
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

import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import java.util.List;
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


    private String currentClaimId;
    

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
            System.out.println("Debug: cardNumber: " + cardNumber);
            System.out.println("Debug: claimAmountText: " + claimAmountText);
            System.out.println("Debug: insuredPerson: " + insuredPerson);
            validationMessage.setText("Please fill in all fields");
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

    @FXML
    void uploadMultipleFiles(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp");
        fileChooser.getExtensionFilters().add(extFilter);

        List<File> files = fileChooser.showOpenMultipleDialog(((Node) event.getSource()).getScene().getWindow());
        for (File file: files) {
            String renamedFile = renameAndSaveFile(file);

        }
    }

    private String renameAndSaveFile(File originalFile) {
        String claimId = currentClaimId;
        String newFileName = claimId + " " + System.currentTimeMillis() + getFileExtension(originalFile);
        File newFile = new File(originalFile.getParent(), newFileName);

        try {
            Files.copy(originalFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File renamed and saved as: " + newFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile.getPath();
    }

    private void saveFileToDatabase(String claimId, String fileName, String filePath) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try(Connection connection = databaseConnection.getConnection()){
            String insertQuery = "INSERT INTO public.documents (claim_id, document_name";
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

        public static List<Claim> fetchClaim() {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            List<Claim> claimList = new ArrayList<Claim>();
            try {
                String getClaimsQuery = "SELECT * FROM claims";
                Statement statement = connection.createStatement();
                ResultSet queryResult = statement.executeQuery(getClaimsQuery);
                while (queryResult.next()) {
                    Claim claim = new Claim();
                    claim.setId(queryResult.getString("claim_id"));
                    claim.setInsuredPerson(queryResult.getString("insured_person"));
                    claim.setCardNumber(queryResult.getString("card_number"));
                    claim.setExamDate(queryResult.getDate("exam_date"));
                    claim.setClaimDate(queryResult.getDate("claim_date"));
                    claim.setClaimAmount(queryResult.getFloat("claim_amount"));
                    claim.setStatus(queryResult.getString("status"));
                    claim.setBankName(queryResult.getString("bank_name"));
                    claim.setBankUserName(queryResult.getString("bank_user_name"));
                    claim.setBankNumber(queryResult.getString("bank_number"));
                    claimList.add(claim);
                }
                System.out.println("Fetch data from database.claim successfully!");
            } catch (SQLException e) {
                System.out.println("SQL error: " + e);

            }
            return claimList;
        }

        public static boolean proposeClaim (String claimID){
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            try {
                String proposeClaim = "UPDATE claims SET status = 'PROCESSING', exam_date = CURRENT_DATE WHERE claim_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(proposeClaim);
                preparedStatement.setString(1, claimID);
                preparedStatement.execute();
                System.out.println("Successfully propose claim " + claimID);
                return true;
            } catch (SQLException e) {
                System.out.println("Error in SQL function proposeClaim: " + e);
                return false;
            }
        }


    public static boolean resubmitClaim(String claimID){
        //Database connection
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        try{
            String resubmitClaim = "UPDATE claims SET status = 'RESUBMIT', exam_date = NULL  WHERE claim_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(resubmitClaim);
            preparedStatement.setString(1,claimID);
            preparedStatement.execute();
            System.out.println("Successfully resubmit claim " + claimID);
            return true;
        } catch (SQLException e) {
            System.out.println("Error in SQL function resubmitClaim: " + e);
            return false;
        }
    }

    public static boolean rejectClaim(String claimID){
        //Database connection
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        try{
            String rejectClaim = "UPDATE claims SET status = 'REJECT', claim_date = CURRENT_DATE WHERE claim_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(rejectClaim);
            preparedStatement.setString(1,claimID);
            preparedStatement.execute();
            System.out.println("Successfully reject claim " + claimID);
            return true;
        } catch (SQLException e) {
            System.out.println("Error in SQL function rejectClaim: " + e);
            return false;
        }
    }

    public static boolean approveClaim(String claimID){
        //Database connection
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        try{
            String approveClaim = "UPDATE claims SET status = 'DONE', claim_date = CURRENT_DATE WHERE claim_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(approveClaim);
            preparedStatement.setString(1,claimID);
            preparedStatement.execute();
            System.out.println("Successfully approve claim " + claimID);
            return true;
        } catch (SQLException e) {
            System.out.println("Error in SQL function approveClaim: " + e);
            return false;
        }
    }

}
