package com.insurancecompany.insurancemanagementgroupproject2.controller;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.HelloApplication;

import com.insurancecompany.insurancemanagementgroupproject2.model.User;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    @FXML
    void confirmAddClaim(ActionEvent event)  {
        String claimId = generateRandomClaimID();
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
            System.out.println(e.getMessage());
        }
    }
    @FXML
    void backToHomePage(ActionEvent event) {
       Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       currentStage.close();
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

//    public static List<Claim> fetchClaim() {
//            DatabaseConnection databaseConnection = new DatabaseConnection();
//            Connection connection = databaseConnection.getConnection();
//            List<Claim> claimList = new ArrayList<Claim>();
//            try {
//                String getClaimsQuery = "SELECT * FROM claims";
//                Statement statement = connection.createStatement();
//                ResultSet queryResult = statement.executeQuery(getClaimsQuery);
//                while (queryResult.next()) {
//                    Claim claim = new Claim();
//                    claim.setId(queryResult.getString("claim_id"));
//                    claim.setInsuredPerson(queryResult.getString("insured_person"));
//                    claim.setCardNumber(queryResult.getString("card_number"));
//                    claim.setExamDate(queryResult.getDate("exam_date"));
//                    claim.setClaimDate(queryResult.getDate("claim_date"));
//                    claim.setClaimAmount(queryResult.getFloat("claim_amount"));
//                    claim.setStatus(queryResult.getString("status"));
//                    claim.setBankName(queryResult.getString("bank_name"));
//                    claim.setBankUserName(queryResult.getString("bank_user_name"));
//                    claim.setBankNumber(queryResult.getString("bank_number"));
//                    claimList.add(claim);
//                }
//                System.out.println("Fetch data from database.claim successfully!");
//                connection.close();
//            } catch (SQLException e) {
//                System.out.println("SQL error: " + e);
//
//            }
//            return claimList;
//        }
        public static boolean proposeClaim (String claimID){
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            try {
                String proposeClaim = "UPDATE claims SET status = 'PROCESSING', exam_date = CURRENT_DATE WHERE claim_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(proposeClaim);
                preparedStatement.setString(1, claimID);
                preparedStatement.execute();
                System.out.println("Successfully propose claim " + claimID);
                connection.close();
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
            connection.close();
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
            connection.close();
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
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error in SQL function approveClaim: " + e);
            return false;
        }
    }

    public ArrayList<Claim> fetchClaimsFromDatabase() {
        ArrayList<Claim> claimList = new ArrayList<>();
        try {
            String queryClaims = "SELECT * FROM claims";
            PreparedStatement statement = connection.prepareStatement(queryClaims);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Claim claim = new Claim();
                claim.setId(resultSet.getString("claim_id"));
                claim.setInsuredPerson(resultSet.getString("insured_person"));
                claim.setCardNumber(resultSet.getString("card_number"));
                claim.setClaimDate(resultSet.getDate("claim_date"));
                claim.setExamDate(resultSet.getDate("exam_date"));
                claim.setClaimAmount(resultSet.getDouble("claim_amount"));
                claim.setStatus(resultSet.getString("status"));
                claim.setBankName(resultSet.getString("bank_name"));
                claim.setBankUserName(resultSet.getString("bank_user_name"));
                claim.setBankNumber(resultSet.getString("bank_number"));
                claimList.add(claim);
            }
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return claimList;
    }

    public boolean updateClaimInformation(String claimId, String claimDate, String examDate, String amount, String status, String bankName, String bankUser, String bankNumber) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsedClaimDate = null;
        java.util.Date parsedExamDate = null;
        java.sql.Date sqlClaimDate = null;
        java.sql.Date sqlExamDate = null;

        try {
            if (claimDate != null && !claimDate.isEmpty()) {
                parsedClaimDate = sdf.parse(claimDate);
                sqlClaimDate = new java.sql.Date(parsedClaimDate.getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        try {
            if (examDate != null && !examDate.isEmpty()) {
                parsedExamDate = sdf.parse(examDate);
                sqlExamDate = new java.sql.Date(parsedExamDate.getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        double claimAmount;
        try {
            claimAmount = Double.parseDouble(amount);
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }

        try {
            String updateQuery = "UPDATE claims SET claim_date=?, exam_date=?, claim_amount=?, status=?, bank_name=?, bank_user_name=?, bank_number=? WHERE claim_id=?";
            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                if (sqlClaimDate != null) statement.setDate(1, sqlClaimDate);
                else statement.setNull(1, Types.DATE);
                if (sqlExamDate != null) statement.setDate(2, sqlExamDate);
                else statement.setNull(2, Types.DATE);
                statement.setDouble(3, claimAmount);
                statement.setString(4, status);
                statement.setString(5, bankName);
                statement.setString(6, bankUser);
                statement.setString(7, bankNumber);
                statement.setString(8, claimId);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Claim information updated successfully!");
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int calculateTotalDocumentsOfClaim(String claimId){
        int totalCount = 0;
        String query = "SELECT COUNT(*) AS total FROM documents WHERE claim_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, claimId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    totalCount = resultSet.getInt("total");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return totalCount;
    }

    public boolean deleteClaimInformation(Claim selectedClaim) {
        try {
            String claimId = selectedClaim.getId();
            String deleteQuery = "DELETE FROM claims WHERE claim_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                statement.setString(1, claimId);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Claim deleted successfully!");
                    //remember to delete documents belongs to the claim
                    return true;
//                        claimObservableList.remove(selectedClaim);
//                        claimTableView.refresh();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
