package com.insurancecompany.insurancemanagementgroupproject2.controller;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.model.LoginData;
import com.insurancecompany.insurancemanagementgroupproject2.model.PolicyHolder;
import com.insurancecompany.insurancemanagementgroupproject2.model.Role;
import com.insurancecompany.insurancemanagementgroupproject2.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class PolicyOwnerMyPolicyHolderController implements Initializable {

    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    @FXML
    private Button addNewPolicyHolderBtn;

    @FXML
    private AnchorPane addNewPolicyHolderForm;

    @FXML
    private Button policyHolderFormCancelBtn;

    @FXML
    private TextArea editFieldPolicyHolderAddress;

    @FXML
    private TextField editFieldPolicyHolderEmail;

    @FXML
    private TextField editFieldPolicyHolderFullName;

    @FXML
    private TextField editFieldPolicyHolderID;

    @FXML
    private TextField editFieldPolicyHolderPassword;

    @FXML
    private TextField editFieldPolicyHolderPhoneNumber;

    @FXML
    private TextField editFieldPolicyHolderRole;

    @FXML
    private TextField editFieldPolicyHolderUsername;

    @FXML
    private TableView<PolicyHolder> policyHolderTableView;

    @FXML
    private TableColumn<PolicyHolder, String> policyHolderAddressCol;

    @FXML
    private TableColumn<PolicyHolder, String> policyHolderEmailCol;

    @FXML
    private TableColumn<PolicyHolder, String> policyHolderFullNameCol;

    @FXML
    private TableColumn<PolicyHolder, String> policyHolderIDCol;

    @FXML
    private TableColumn<PolicyHolder, String> policyHolderPasswordCol;

    @FXML
    private TableColumn<PolicyHolder, String> policyHolderPhoneNumCol;

    @FXML
    private TableColumn<PolicyHolder, Integer> policyHolderRoleCol;

    @FXML
    private TableColumn<PolicyHolder, String> policyHolderUsernameCol;

    private ObservableList<PolicyHolder> policyHolderObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addNewPolicyHolderBtnOnAction();
        policyHolderFormCancelBtnOnAction();
        policyHolderObservableList.addAll(fetchPolicyHoldersFromDatabase());
        displayPolicyHolders();

    }

    @FXML
    private void addNewPolicyHolderBtnOnAction() {
        addNewPolicyHolderForm.setVisible(true);
    }


    @FXML
    private void policyHolderFormCancelBtnOnAction() {
        addNewPolicyHolderForm.setVisible(false);
    }

    public ArrayList<PolicyHolder> fetchPolicyHoldersFromDatabase() {
        ArrayList<PolicyHolder> policyHolderArrayList = new ArrayList<>();
        try {
            String policyOwnerId = getIDFromUserName(LoginData.usernameLogin);

            // SQL query to select policyholders related to the logged-in policy owner
            String query = "SELECT u.* FROM users u " +
                    "JOIN insurance_card ic ON u.id = ic.card_holder_id " +
                    "WHERE ic.policy_owner_id = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, policyOwnerId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                PolicyHolder policyHolder = new PolicyHolder();
                policyHolder.setId(resultSet.getString("id"));
                policyHolder.setFullName(resultSet.getString("full_name"));
                policyHolder.setUserName(resultSet.getString("user_name"));
                policyHolder.setPassword(resultSet.getString("password"));
                policyHolder.setEmail(resultSet.getString("email"));
                policyHolder.setPhoneNumber(resultSet.getString("phone_number"));
                policyHolder.setAddress(resultSet.getString("address"));
                policyHolder.setRoleId(resultSet.getInt("role_id"));
                policyHolderArrayList.add(policyHolder);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return policyHolderArrayList;
    }

    // Fetch roles from the database
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

    // Display all policyholders in the TableView
    public void displayPolicyHolders() {
        policyHolderIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        policyHolderFullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        policyHolderUsernameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        policyHolderPasswordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        policyHolderEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        policyHolderPhoneNumCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        policyHolderAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        policyHolderRoleCol.setCellValueFactory(new PropertyValueFactory<>("roleId"));
        policyHolderTableView.setItems(policyHolderObservableList);
    }

    // Setting text when clicking on a row in the TableView
    @FXML
    private void selectPolicyHolderRow() {
        PolicyHolder selectedPolicyHolder = policyHolderTableView.getSelectionModel().getSelectedItem();
        if (selectedPolicyHolder != null) {
            editFieldPolicyHolderID.setText(selectedPolicyHolder.getId());
            editFieldPolicyHolderFullName.setText(selectedPolicyHolder.getFullName());
            editFieldPolicyHolderUsername.setText(selectedPolicyHolder.getUserName());
            editFieldPolicyHolderPassword.setText(selectedPolicyHolder.getPassword());
            editFieldPolicyHolderEmail.setText(selectedPolicyHolder.getEmail());
            editFieldPolicyHolderPhoneNumber.setText(selectedPolicyHolder.getPhoneNumber());
            editFieldPolicyHolderAddress.setText(selectedPolicyHolder.getAddress());
            editFieldPolicyHolderRole.setText(getRoleName(selectedPolicyHolder.getRoleId()));
        }
    }

    @FXML
    private void editFieldPolicyHolderConfirmBtnOnAction(ActionEvent event) {
        boolean isSuccess = updatePolicyHolder(editFieldPolicyHolderID.getText(),
                editFieldPolicyHolderFullName.getText(),
                editFieldPolicyHolderPassword.getText(),
                editFieldPolicyHolderEmail.getText(),
                editFieldPolicyHolderPhoneNumber.getText(),
                editFieldPolicyHolderAddress.getText());
        if (isSuccess) {
            editFieldPolicyHolderFullName.setText(editFieldPolicyHolderFullName.getText());
            editFieldPolicyHolderPassword.setText(editFieldPolicyHolderPassword.getText());
            editFieldPolicyHolderEmail.setText(editFieldPolicyHolderEmail.getText());
            editFieldPolicyHolderPhoneNumber.setText(editFieldPolicyHolderPhoneNumber.getText());
            editFieldPolicyHolderAddress.setText(editFieldPolicyHolderAddress.getText());
            for (PolicyHolder selectedPolicyHolder : policyHolderObservableList) {
                if (Objects.equals(editFieldPolicyHolderID.getText(), selectedPolicyHolder.getId())) {
                    selectedPolicyHolder.setFullName(editFieldPolicyHolderFullName.getText());
                    selectedPolicyHolder.setPassword(editFieldPolicyHolderPassword.getText());
                    selectedPolicyHolder.setEmail(editFieldPolicyHolderEmail.getText());
                    selectedPolicyHolder.setPhoneNumber(editFieldPolicyHolderPhoneNumber.getText());
                    selectedPolicyHolder.setAddress(editFieldPolicyHolderAddress.getText());
                    break;
                }
            }
            // Refresh the TableView
            policyHolderTableView.refresh();
        } else {
            System.out.println("isSuccess: " + false);
        }
    }

    @FXML
    private void editFieldPolicyHolderDeleteBtnOnAction(ActionEvent event) {
        boolean isSuccess = deletePolicyHolder(editFieldPolicyHolderID.getText());
        if (isSuccess) {
            for (PolicyHolder selectedPolicyHolder : policyHolderObservableList) {
                if (Objects.equals(editFieldPolicyHolderID.getText(), selectedPolicyHolder.getId())) {
                    policyHolderObservableList.remove(selectedPolicyHolder);
                    break;
                }
            }
            // Refresh the TableView
            policyHolderTableView.refresh();
        } else {
            System.out.println("isSuccess: " + false);
        }
    }

    // Update the policyholder in the database
    public boolean updatePolicyHolder(String id, String fullName, String password, String email, String phoneNumber, String address) {
        try {
            String updateProfileQuery = "UPDATE users SET full_name = ?, password = ?, email = ?, phone_number = ?, address = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(updateProfileQuery)) {
                statement.setString(1, fullName);
                statement.setString(2, password);
                statement.setString(3, email);
                statement.setString(4, phoneNumber);
                statement.setString(5, address);
                statement.setString(6, id);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Policy Holder Information updated successfully!");
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete the policyholder from the database
    public boolean deletePolicyHolder(String id) {
        Connection conn = null;
        PreparedStatement deleteInsuranceCardsStmt = null;
        PreparedStatement deletePolicyHolderStmt = null;

        try {
            conn = databaseConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // First, delete dependent records in the insurance_card table
            String deleteInsuranceCardsQuery = "DELETE FROM insurance_card WHERE card_holder_id = ?";
            deleteInsuranceCardsStmt = conn.prepareStatement(deleteInsuranceCardsQuery);
            deleteInsuranceCardsStmt.setString(1, id);
            deleteInsuranceCardsStmt.executeUpdate();

            // Now, delete the policyholder from the users table
            String deletePolicyHolderQuery = "DELETE FROM users WHERE id = ?";
            deletePolicyHolderStmt = conn.prepareStatement(deletePolicyHolderQuery);
            deletePolicyHolderStmt.setString(1, id);
            int rowsDeleted = deletePolicyHolderStmt.executeUpdate();

            conn.commit(); // Commit the transaction

            if (rowsDeleted > 0) {
                System.out.println("Policy Holder and related insurance cards deleted successfully!");
                return true;
            } else {
                System.out.println("No Policy Holder found with the specified ID.");
                return false;
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // rollback transaction on error
                } catch (SQLException ex) {
                    System.out.println("Error rolling back transaction");
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (deleteInsuranceCardsStmt != null) deleteInsuranceCardsStmt.close();
                if (deletePolicyHolderStmt != null) deletePolicyHolderStmt.close();
                if (conn != null) {
                    conn.setAutoCommit(true); // Reset auto-commit to true
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Add a new policyholder to the database
    public boolean addNewPolicyHolder(String fullName, String userName, String password, String email, String phoneNumber, String address, int roleId) {
        try {
            String insertPolicyHolderQuery = "INSERT INTO users (full_name, user_name, password, email, phone_number, address, role_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertPolicyHolderQuery)) {
                statement.setString(1, fullName);
                statement.setString(2, userName);
                statement.setString(3, password);
                statement.setString(4, email);
                statement.setString(5, phoneNumber);
                statement.setString(6, address);
                statement.setInt(7, roleId);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("A new Policy Holder was inserted successfully!");
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getIDFromUserName(String username) {
        String getIDQuery = "SELECT id FROM users WHERE user_name = ?";
        String userId= "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getIDQuery);
            preparedStatement.setString(1, LoginData.usernameLogin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getString("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userId;
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
}
