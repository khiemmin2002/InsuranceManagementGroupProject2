package com.insurancecompany.insurancemanagementgroupproject2.controller;
/**
 * @author team 5
 */
import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.model.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdminController {

    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();
    BcryptPassword bcryptPassword = new BcryptPassword();

    //Role Functions
    public ArrayList<Role> fetchRolesFromDatabase(){
        ArrayList<Role> roleArrayList = new ArrayList<>();
        try {
            String queryRoles = "SELECT * FROM roles";
            PreparedStatement statement = connection.prepareStatement(queryRoles);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String roleName = resultSet.getString("role");
                Role role = new Role(id, roleName);
                roleArrayList.add(role);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roleArrayList;
    }

    //User functions
    public boolean addUser(User user){
        try {
            String insertQuery = "INSERT INTO users (id, full_name, user_name, password, email, phone_number, address, role_id) VALUES (?,?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1,user.getId());
                statement.setString(2, user.getFullName());
                statement.setString(3, user.getUserName());
                statement.setString(4, bcryptPassword.hashBcryptPassword(user.getPassword()));
                statement.setString(5, user.getEmail());
                statement.setString(6, user.getPhoneNumber());
                statement.setString(7, user.getAddress());
                statement.setInt(8, user.getRoleId());

                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateUser(String id, String fullName, String password, String email, String phoneNumber, String address) {
        try {
                String updateProfileQuery = "UPDATE users SET full_name = ?, password = ?, email = ?, phone_number = ?, address = ? WHERE id = ?";
                try (PreparedStatement statement = connection.prepareStatement(updateProfileQuery)) {
                    statement.setString(1, fullName);
                    statement.setString(2, bcryptPassword.hashBcryptPassword(password));
                    statement.setString(3, email);
                    statement.setString(4, phoneNumber);
                    statement.setString(5, address);
                    statement.setString(6, id);
                    int rowsUpdated = statement.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("User Information updated successfully!");
                        return true;
                    }
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean deleteUser(String userId) {
        String deleteUserQuery = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteUserQuery)) {
            statement.setString(1, userId);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public ArrayList<User> fetchUsersFromDatabase() {
        ArrayList<User> userArrayList = new ArrayList<>();
        try {
            String queryAllUsers = "SELECT * FROM users WHERE role_id = 2 OR role_id = 3 OR role_id = 4 OR role_id = 5 OR role_id =6";
            PreparedStatement statement = connection.prepareStatement(queryAllUsers);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getString("id"));
                user.setFullName(resultSet.getString("full_name"));
                user.setUserName(resultSet.getString("user_name"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setPhoneNumber(resultSet.getString("phone_number"));
                user.setAddress(resultSet.getString("address"));
                user.setRoleId(resultSet.getInt("role_id"));
//                userObservableList.add(user);
                userArrayList.add(user);
            }
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userArrayList;
    }

    //Insurance Card Dashboard
    public boolean deleteInsuranceCardInformation(String cardNumber) {
        String deleteQuery = "DELETE FROM insurance_card WHERE card_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setString(1, cardNumber);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateInsuranceCardInformation(String cardNumber, String expDate) throws ParseException {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = sdf.parse(expDate);
            java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

            String updateQuery = "UPDATE insurance_card SET expiration_date = ? WHERE card_number = ?";
            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                statement.setDate(1, sqlDate);
                statement.setString(2, cardNumber);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Insurance Card Information updated successfully!");
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
    }
    public ArrayList<InsuranceCard> fetchInsuranceCardsFromDatabase() {
        ArrayList<InsuranceCard> insuranceCardArrayList = new ArrayList<>();
        try (Connection connection = new DatabaseConnection().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM insurance_card");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                InsuranceCard insuranceCard = new InsuranceCard();
                insuranceCard.setCardNumber(resultSet.getString("card_number"));
                insuranceCard.setExpirationDate(resultSet.getDate("expiration_date"));
                insuranceCard.setCardHolderId(resultSet.getString("card_holder_id"));
                insuranceCard.setPolicyOwnerId(resultSet.getString("policy_owner_id"));
//                insuranceCardObservableList.add(insuranceCard);
                insuranceCardArrayList.add(insuranceCard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insuranceCardArrayList;
    }
    //Claim Dashboard - Reference to ClaimController
    //Profile dashboard functions
    public User getProfileDashboardInformation(String username, int roleId) {
        try {
            String queryProfileInformation = "SELECT * FROM users WHERE user_name = ? AND role_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(queryProfileInformation)) {
                statement.setString(1, username);
                statement.setInt(2, roleId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        User user = new User();
                        user.setId(resultSet.getString("id"));
                        user.setUserName(resultSet.getString("user_name"));
                        user.setEmail(resultSet.getString("email"));
                        user.setFullName(resultSet.getString("full_name"));
                        user.setRoleId(resultSet.getInt("role_id"));
                        user.setPassword(resultSet.getString("password"));
                        user.setPhoneNumber(resultSet.getString("phone_number"));
                        user.setAddress(resultSet.getString("address"));
                        return user;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getNameForUser(String userId) {
        String name = "";
        try {
            String query = "SELECT full_name FROM users WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                name = resultSet.getString("full_name");
            }
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public String getRoleName(int roleId) {
        String roleName = "";
        try {
            String queryRoleName = "SELECT role FROM roles WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(queryRoleName);
            statement.setInt(1, roleId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                roleName = resultSet.getString("role");
            }
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roleName;
    }

    public int displayTotalProviders() {
        int totalProvider = 0;
        try {
            String queryManagerCount = "SELECT COUNT(*) AS managerCount FROM users WHERE role_id = ?";
            String querySurveyorCount = "SELECT COUNT(*) AS surveyorCount FROM users WHERE role_id = ?";
            try (PreparedStatement managerStatement = connection.prepareStatement(queryManagerCount);
                 PreparedStatement surveyorStatement = connection.prepareStatement(querySurveyorCount)) {
                managerStatement.setInt(1, 2);
                int managerCount = 0;
                try (ResultSet managerResultSet = managerStatement.executeQuery()) {
                    if (managerResultSet.next()) {
                        managerCount = managerResultSet.getInt("managerCount");
                    }
                }
                surveyorStatement.setInt(1, 3);
                int surveyorCount = 0;
                try (ResultSet surveyorResultSet = surveyorStatement.executeQuery()) {
                    if (surveyorResultSet.next()) {
                        surveyorCount = surveyorResultSet.getInt("surveyorCount");
                    }
                }
                totalProvider = managerCount + surveyorCount;
//                totalProviders.setText(String.valueOf(totalProvider));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalProvider;
    }

    public int displayTotalCustomers() {
        int totalCustomer = 0;
        try {
            String queryPolicyOwnerCount = "SELECT COUNT(*) as policyOwnerCount FROM users WHERE role_id = ?";
            String queryPolicyHolderCount = "SELECT COUNT(*) as policyHolderCount FROM users WHERE role_id = ?";
            String queryDependentCount = "SELECT COUNT(*) as dependentCount FROM users WHERE role_id = ?";
            try (PreparedStatement policyOwnerStatement = connection.prepareStatement(queryPolicyOwnerCount);
                 PreparedStatement policyHolderStatement = connection.prepareStatement(queryPolicyHolderCount);
                 PreparedStatement dependentStatement = connection.prepareStatement(queryDependentCount);) {
                policyOwnerStatement.setInt(1, 4);
                int policyOwnerCount = 0;
                try (ResultSet policyOwnerResultSet = policyOwnerStatement.executeQuery()) {
                    if (policyOwnerResultSet.next()) {
                        policyOwnerCount = policyOwnerResultSet.getInt("policyOwnerCount");
                    }
                }

                policyHolderStatement.setInt(1, 5);
                int policyHolderCount = 0;
                try (ResultSet policyHolderResultSet = policyHolderStatement.executeQuery()) {
                    if (policyHolderResultSet.next()) {
                        policyHolderCount = policyHolderResultSet.getInt("policyHolderCount");
                    }
                }

                dependentStatement.setInt(1, 6);
                int dependentCount = 0;
                try (ResultSet dependentResultSet = dependentStatement.executeQuery()) {
                    if (dependentResultSet.next()) {
                        dependentCount = dependentResultSet.getInt("dependentCount");
                    }
                }

                totalCustomer = policyOwnerCount + policyHolderCount + dependentCount;
//                totalCustomers.setText(String.valueOf(totalCustomer));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalCustomer;
    }

    public int displayTotalInsuranceCards() {
        int cardCount = 0;
        try {
            String queryInsuranceCardCount = "SELECT COUNT(*) AS cardCount FROM insurance_card";
            try (PreparedStatement statement = connection.prepareStatement(queryInsuranceCardCount)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        cardCount = resultSet.getInt("cardCount");
//                        totalInsuranceCards.setText(String.valueOf(cardCount));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cardCount;
    }

    public int displayTotalClaims() {

        int claimCount = 0;
        try {
            String queryClaimCount = "SELECT COUNT(*) AS claimCount FROM claims";
            try (PreparedStatement statement = connection.prepareStatement(queryClaimCount)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        claimCount = resultSet.getInt("claimCount");
//                        totalClaims.setText(String.valueOf(claimCount));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return claimCount;
    }
}