package com.insurancecompany.insurancemanagementgroupproject2.controller;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.model.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
            String insertQuery = "INSERT INTO users (id,full_name, user_name, password, email, phone_number, address, role_id) VALUES (?,?, ?, ?, ?, ?, ?, ?)";
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
    public boolean deleteUser(String id) {
            try {
                String deleteQuery = "DELETE FROM users WHERE id = ?";
                try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                    statement.setString(1, id);
                    int rowDeleted = statement.executeUpdate();
                    if (rowDeleted > 0) {
                        System.out.println("User deleted successfully!");
                        return true;
//                        userObservableList.remove(selectedUser);
//                        editFormUserInformation.setVisible(false);
//                        userTableView.refresh();s
                    }
                }
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
    public boolean deleteInsuranceCardInformation(String cardNumber){
            try {
                String deleteQuery = "DELETE FROM insurance_card WHERE card_number = ?";
                try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                    statement.setString(1, cardNumber);
                    int rowDeleted = statement.executeUpdate();
                    if (rowDeleted > 0) {
                        System.out.println("Insurance Card deleted successfully!");
                        return true;
                    }
                }
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
//                    selectedInsuranceCard.setExpirationDate(sqlDate);
//                    insuranceCardTableView.refresh();
//                    editFormInsuranceExpDate.setText(String.valueOf(sqlDate));
//                    editFormInsuranceCardInformation.setVisible(false);
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

    //Claim Dashboard
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

    //Profile dashboard functions
    public User getProfileDashboardInformation() {
        try {
            String queryProfileInformation = "SELECT * FROM users WHERE user_name = ? AND role_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(queryProfileInformation)) {
                statement.setString(1, LoginData.usernameLogin);
                statement.setInt(2, LoginData.roleId);
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

    //Main Dash board

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