package com.insurancecompany.insurancemanagementgroupproject2.controller;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.model.Claim;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PolicyHolderClaimController {
    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    private final Connection connection = databaseConnection.getConnection();

    public boolean deleteClaimDocuments(String claimId) {
        String query = "DELETE FROM documents WHERE claim_id = ?";
        try{
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, claimId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteClaim(String claimId) throws SQLException {
        String query = "DELETE FROM public.claims WHERE claim_id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, claimId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e){
            System.out.println("Error " + e);
        }
        return false;
    }

    public ObservableList<Claim> findClaimsByInsuredPerson(String insuredPerson) throws SQLException {
        String query = "SELECT c.*, d.document_name FROM public.claims c " +
                "JOIN documents d ON c.claim_id = d.claim_id " +
                "WHERE c.insured_person = ?";
        ObservableList<Claim> claims = FXCollections.observableArrayList();
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, insuredPerson);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                claims.add(extractClaimFromResultSet(rs));
            }
            return claims;
        }catch (SQLException e){
            System.out.println("Exception" + e);
        }
        return claims;
    }

    public ObservableList<Claim> fetchAllClaims(String userName) throws SQLException {
        String query = "SELECT c.*, d.document_name FROM public.claims c " +
                "JOIN documents d ON c.claim_id = d.claim_id " +
                "WHERE insured_person IN " +
                "    (SELECT id FROM public.users WHERE user_name = ?) " +
                "OR insured_person IN " +
                "    (SELECT dependent_id FROM public.dependent WHERE policy_holder_id = " +
                "     (SELECT id FROM public.users WHERE user_name = ?))";
        ObservableList<Claim> claims = FXCollections.observableArrayList();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userName);
            stmt.setString(2, userName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                claims.add(extractClaimFromResultSet(rs));
            }
        }
        return claims;
    }
    public Claim extractClaimFromResultSet(ResultSet rs) throws SQLException {
        Claim claim = new Claim();
        claim.setId(rs.getString("claim_id"));
        claim.setInsuredPerson(rs.getString("insured_person"));
        claim.setCardNumber(rs.getString("card_number"));
        claim.setExamDate(rs.getDate("exam_date"));
        claim.setClaimDate(rs.getDate("claim_date"));
        claim.setClaimAmount(rs.getDouble("claim_amount"));
        claim.setStatus(rs.getString("status"));
        claim.setBankName(rs.getString("bank_name"));
        claim.setBankUserName(rs.getString("bank_user_name"));
        claim.setBankNumber(rs.getString("bank_number"));
        claim.setDocuments(rs.getString("document_name"));
        return claim;
    }
    public boolean updateClaim(String claimId, String insuredPersonID, String cardNumber, double claimAmount, String bankName, String bankUserName, String bankNumber) throws SQLException {
        String query = "UPDATE public.claims SET insured_person = ?, card_number = ?, claim_amount = ?, bank_name = ?, bank_user_name = ?, bank_number = ? WHERE claim_id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, insuredPersonID);
            stmt.setString(2, cardNumber);
            stmt.setDouble(3, claimAmount);
            stmt.setString(4, bankName);
            stmt.setString(5, bankUserName);
            stmt.setString(6, bankNumber);
            stmt.setString(7, claimId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e ){
            System.out.println("Error " + e);
        }
        return false;
    }
    public boolean updateDocumentDetails(String claimId, List<String> documentNames) throws SQLException {
        String query = "UPDATE documents SET document_name = ? WHERE claim_id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            for (String documentName : documentNames) {
                stmt.setString(1, documentName);
                stmt.setString(2, claimId);
                stmt.executeUpdate();
            }
            return true;
        }catch (SQLException e){
            System.out.println("Error " + e);
        }
        return false;
    }
    public boolean addClaim(Claim claim, List<String> documentNames) throws SQLException {
        String insertQuery = "INSERT INTO public.claims (claim_id, insured_person, card_number, exam_date, claim_date, claim_amount, status, bank_name, bank_user_name, bank_number) " +
                "VALUES (?, ?, ?, NULL, NULL, ?, 'NEW', ?, ?, ?)";
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try  {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, claim.getId());
            preparedStatement.setString(2, claim.getInsuredPerson());
            preparedStatement.setString(3, claim.getCardNumber());
            preparedStatement.setDouble(4, claim.getClaimAmount());
            preparedStatement.setString(5, claim.getBankName());
            preparedStatement.setString(6, claim.getBankUserName());
            preparedStatement.setString(7, claim.getBankNumber());
            preparedStatement.executeUpdate();
            addDocuments(claim.getId(), documentNames);
            return true;
        }catch (SQLException e){
            System.out.println("Error " + e);
        }
        return false;
    }

    public boolean addDocuments(String claimId, List<String> documentNames) throws SQLException {
        String insertDocQuery = "INSERT INTO documents (claim_id, document_name) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertDocQuery);
            for (String docName : documentNames) {
                preparedStatement.setString(1, claimId);
                preparedStatement.setString(2, docName);
                preparedStatement.executeUpdate();
            }
            return true;
        } catch (SQLException e){
            System.out.println("Error " + e);
        }
        return false;
    }
}
