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

public class ClaimDAO {
    public void deleteClaimDocuments(String claimId) {
        String query = "DELETE FROM documents WHERE claim_id = ?";
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, claimId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteClaim(String claimId) throws SQLException {
        String query = "DELETE FROM public.claims WHERE claim_id = ?";
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, claimId);
            stmt.executeUpdate();
        }
    }

    public ObservableList<Claim> findClaimsByInsuredPerson(String insuredPerson) throws SQLException {
        String query = "SELECT c.*, d.document_name FROM public.claims c " +
                "JOIN documents d ON c.claim_id = d.claim_id " +
                "WHERE c.insured_person = ?";
        ObservableList<Claim> claims = FXCollections.observableArrayList();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, insuredPerson);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                claims.add(extractClaimFromResultSet(rs));
            }
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
    private Claim extractClaimFromResultSet(ResultSet rs) throws SQLException {
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
    public void updateClaim(String claimId, String insuredPersonID, String cardNumber, double claimAmount, String bankName, String bankUserName, String bankNumber) throws SQLException {
        String query = "UPDATE public.claims SET insured_person = ?, card_number = ?, claim_amount = ?, bank_name = ?, bank_user_name = ?, bank_number = ? WHERE claim_id = ?";
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, insuredPersonID);
            stmt.setString(2, cardNumber);
            stmt.setDouble(3, claimAmount);
            stmt.setString(4, bankName);
            stmt.setString(5, bankUserName);
            stmt.setString(6, bankNumber);
            stmt.setString(7, claimId);
            stmt.executeUpdate();
        }
    }
    public void updateDocumentDetails(String claimId, List<String> documentNames) throws SQLException {
        String query = "UPDATE documents SET document_name = ? WHERE claim_id = ?";
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            for (String documentName : documentNames) {
                stmt.setString(1, documentName);
                stmt.setString(2, claimId);
                stmt.executeUpdate();
            }
        }
    }
}
