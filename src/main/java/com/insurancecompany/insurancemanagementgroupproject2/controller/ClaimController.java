package com.insurancecompany.insurancemanagementgroupproject2.controller;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

import com.insurancecompany.insurancemanagementgroupproject2.model.Claim;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.List;
import java.util.Random;

public class ClaimController {

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
                connection.close();
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
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
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
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
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
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
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
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
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
