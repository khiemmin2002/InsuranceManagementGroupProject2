package com.insurancecompany.insurancemanagementgroupproject2.controller;
/**
 * @author team 5
 */
import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.model.LoginData;
import com.insurancecompany.insurancemanagementgroupproject2.model.Claim;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class ClaimController {
    // Create instance of controller
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();
    DependentController dependentController = new DependentController();
        // Method to return a List of Claim from database
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
        // Method to allow a claim status to be proposed
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
        // Method to allow a claim status to be resubmitted
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
        // Method to allow a claim status to be rejected
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
        // Method to allow a claim to be approved
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
        // Method to return an arraylist of claim from database
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
        // Method to update claim information
        public boolean updateClaimInformation(String claimId, String amount, String status, String bankName, String bankUser, String bankNumber) {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();

            double claimAmount;
            // Catching exception
            try {
                claimAmount = Double.parseDouble(amount);
            } catch (NumberFormatException | NullPointerException e) {
                e.printStackTrace();
                return false;
            }
            // Catching exception, execute update
            try {
                String updateQuery = "UPDATE claims SET claim_amount=?, status=?, bank_name=?, bank_user_name=?, bank_number=? WHERE claim_id=?";
                try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                    statement.setDouble(1, claimAmount);
                    statement.setString(2, status);
                    statement.setString(3, bankName);
                    statement.setString(4, bankUser);
                    statement.setString(5, bankNumber);
                    statement.setString(6, claimId);
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
        // Method return int number of document
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
        // Method to delete a claim
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
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
        // Method to return arraylist of claim
        public ArrayList<Claim> getClaimsOfInsuredPerson() {
            ArrayList<Claim> claims = new ArrayList<>();
            try {
                String getClaimsQuery = "SELECT * FROM claims WHERE insured_person = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(getClaimsQuery);
                preparedStatement.setString(1, dependentController.getIDFromUserName(LoginData.usernameLogin));
                ResultSet queryResult = preparedStatement.executeQuery();
                // Loop through resource
                while (queryResult.next()) {
                    Claim claim = new Claim();
                    claim.setId(queryResult.getString("claim_id"));
                    claim.setInsuredPerson(queryResult.getString("insured_person"));
                    claim.setCardNumber(queryResult.getString("card_number"));
                    claim.setExamDate(queryResult.getDate("exam_date"));
                    claim.setClaimDate(queryResult.getDate("claim_date"));
                    claim.setClaimAmount(queryResult.getDouble("claim_amount"));
                    claim.setStatus(queryResult.getString("status"));
                    claim.setBankName(queryResult.getString("bank_name"));
                    claim.setBankUserName(queryResult.getString("bank_user_name"));
                    claim.setBankNumber(queryResult.getString("bank_number"));
                    claims.add(claim);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return claims;
        }
}
