package com.insurancecompany.insurancemanagementgroupproject2.controller;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.model.Dependent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PolicyHolderDependentController {

    public ObservableList<Dependent> fetchDependents(String userName)  throws SQLException {
        ObservableList<Dependent> dependentData = FXCollections.observableArrayList();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (Connection connection = databaseConnection.getConnection()) {
            String query = "SELECT d.id, d.full_name, d.user_name, d.password, d.email, d.phone_number, d.address " +
                    "FROM users d " +
                    "JOIN dependent dep ON d.id = dep.dependent_id " +
                    "JOIN users p ON p.id = dep.policy_holder_id " +
                    "WHERE p.user_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                Dependent dependent = new Dependent();
                dependent.setId(results.getString("id"));
                dependent.setFullName(results.getString("full_name"));
                dependent.setUserName(results.getString("user_name"));
                dependent.setPassword(results.getString("password"));
                dependent.setEmail(results.getString("email"));
                dependent.setPhoneNumber(results.getString("phone_number"));
                dependent.setAddress(results.getString("address"));
                dependentData.add(dependent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dependentData;
    }
    public void addDependent(Dependent dependent, String policyHolderUserName) throws SQLException  {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String policyHolderId = null;

        try (Connection connection = databaseConnection.getConnection()) {

            String findPolicyHolder = "SELECT id FROM users WHERE user_name = ?";
            try (PreparedStatement findStmt = connection.prepareStatement(findPolicyHolder)) {
                findStmt.setString(1, policyHolderUserName);
                ResultSet rs = findStmt.executeQuery();
                if (rs.next()) {
                    policyHolderId = rs.getString("id");
                }
            }

            if (policyHolderId == null) {
                throw new SQLException("Policy holder not found.");
            }

            String insertUser = "INSERT INTO users (id, full_name, user_name, password, email, phone_number, address) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement userStmt = connection.prepareStatement(insertUser)) {
                userStmt.setString(1, dependent.getId());
                userStmt.setString(2, dependent.getFullName());
                userStmt.setString(3, dependent.getUserName());
                userStmt.setString(4, dependent.getPassword());  // Assuming password is already hashed
                userStmt.setString(5, dependent.getEmail());
                userStmt.setString(6, dependent.getPhoneNumber());
                userStmt.setString(7, dependent.getAddress());
                userStmt.executeUpdate();
            }

            String insertDependent = "INSERT INTO dependent (dependent_id, policy_holder_id) VALUES (?, ?)";
            try (PreparedStatement depStmt = connection.prepareStatement(insertDependent)) {
                depStmt.setString(1, dependent.getId());
                depStmt.setString(2, policyHolderId);
                depStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteDependent(String dependentId) throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        try (Connection connection = databaseConnection.getConnection()) {

            String deleteRelation = "DELETE FROM dependent WHERE dependent_id = ?";
            try (PreparedStatement relationStmt = connection.prepareStatement(deleteRelation)) {
                relationStmt.setString(1, dependentId);
                relationStmt.executeUpdate();
            }


            String deleteUser = "DELETE FROM users WHERE id = ?";
            try (PreparedStatement userStmt = connection.prepareStatement(deleteUser)) {
                userStmt.setString(1, dependentId);
                int result = userStmt.executeUpdate();
                if (result == 0) {
                    throw new SQLException("No dependent found with ID: " + dependentId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<Dependent> findDependentUserName(String userName) throws SQLException {
        ObservableList<Dependent> foundDependents = FXCollections.observableArrayList();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        try {
            String findQuery = "SELECT id, full_name, user_name, password, email, phone_number, address " +
                    "FROM users " +
                    "WHERE user_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(findQuery);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Dependent dependent = new Dependent();
                dependent.setId(resultSet.getString("id"));
                dependent.setFullName(resultSet.getString("full_name"));
                dependent.setUserName(resultSet.getString("user_name"));
                dependent.setPassword(resultSet.getString("password"));
                dependent.setEmail(resultSet.getString("email"));
                dependent.setPhoneNumber(resultSet.getString("phone_number"));
                dependent.setAddress(resultSet.getString("address"));
                foundDependents.add(dependent);

            }
            if (foundDependents.isEmpty()) {
                System.out.println("No dependents found.");  // Debug output
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundDependents;
    }
    public void updateDependent(String dependentId, String password, String email, String phoneNumber, String address)  throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (Connection connection = databaseConnection.getConnection()) {
            String updateQuery = "UPDATE public.users SET password = ?, email = ?, phone_number = ?, address = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, password);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, phoneNumber);
                preparedStatement.setString(4, address);
                preparedStatement.setString(5, dependentId);
                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Updating dependent failed, no rows affected.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
