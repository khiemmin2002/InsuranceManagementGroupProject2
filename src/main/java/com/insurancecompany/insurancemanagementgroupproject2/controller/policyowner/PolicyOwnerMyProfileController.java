package com.insurancecompany.insurancemanagementgroupproject2.controller.policyowner;

import com.insurancecompany.insurancemanagementgroupproject2.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class PolicyOwnerMyProfileController implements Initializable {

    @FXML
    private Button policyOwnerEditConfirmBtn;

    @FXML
    private TextArea policyOwnerMyProfileAddressEditField;

    @FXML
    private TextField policyOwnerMyProfileCustomerIDEditField;

    @FXML
    private Label policyOwnerMyProfileCustomerIDLabel;

    @FXML
    private TextField policyOwnerMyProfileEmailEditField;

    @FXML
    private Label policyOwnerMyProfileEmailLabel;

    @FXML
    private TextField policyOwnerMyProfileFullNameEditField;

    @FXML
    private Label policyOwnerMyProfileFullNameLabel;

    @FXML
    private TextField policyOwnerMyProfilePasswordEditField;

    @FXML
    private TextField policyOwnerMyProfilePhoneNumberEditField;

    @FXML
    private TextField policyOwnerMyProfileRoleEditField;

    @FXML
    private TextField policyOwnerMyProfileUsernameEditField;

    @FXML
    private Label policyOwnerMyProfileUsernameLabel;

    @FXML
    private AnchorPane policyOwnerMyProfileDashboard;

    private ObservableList<PolicyOwner> policyOwnerObservableList = FXCollections.observableArrayList();


    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        displayProfileDashboardInformation();
    }

    //Policy Owner My Profile dashboard functions
    public PolicyOwner getProfileDashboardInformation() {
        try {
            String queryProfileInformation = "SELECT * FROM users WHERE user_name = ? AND role_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(queryProfileInformation)) {
                statement.setString(1, LoginData.usernameLogin);
                statement.setInt(2, LoginData.roleId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        PolicyOwner policyOwner = new PolicyOwner();
                        policyOwner.setId(resultSet.getString("id"));
                        policyOwner.setUserName(resultSet.getString("user_name"));
                        policyOwner.setEmail(resultSet.getString("email"));
                        policyOwner.setFullName(resultSet.getString("full_name"));
                        policyOwner.setRoleId(resultSet.getInt("role_id"));
                        policyOwner.setPassword(resultSet.getString("password"));
                        policyOwner.setPhoneNumber(resultSet.getString("phone_number"));
                        policyOwner.setAddress(resultSet.getString("address"));
                        return policyOwner;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public boolean updatePolicyOwner(String id, String fullName, String password, String email, String phoneNumber, String address) {
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
                    System.out.println("Policy Owner Information updated successfully!");
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void displayProfileDashboardInformation() {
        PolicyOwner policyOwnerData = getProfileDashboardInformation();
        if (policyOwnerData != null) {
            policyOwnerMyProfileCustomerIDLabel.setText(policyOwnerData.getId());
            policyOwnerMyProfileUsernameLabel.setText(policyOwnerData.getUserName());
            policyOwnerMyProfileEmailLabel.setText(policyOwnerData.getEmail());
            policyOwnerMyProfileFullNameLabel.setText(policyOwnerData.getFullName());
            policyOwnerMyProfileCustomerIDEditField.setText(policyOwnerData.getId());
            policyOwnerMyProfileFullNameEditField.setText(policyOwnerData.getFullName());
            policyOwnerMyProfileUsernameEditField.setText(policyOwnerData.getUserName());
            policyOwnerMyProfilePasswordEditField.setText(policyOwnerData.getPassword());
            policyOwnerMyProfileEmailEditField.setText(policyOwnerData.getEmail());
            policyOwnerMyProfilePhoneNumberEditField.setText(policyOwnerData.getPhoneNumber());
            policyOwnerMyProfileAddressEditField.setText(policyOwnerData.getAddress());
            int roleId = policyOwnerData.getRoleId();
            String roleName = getRoleName(roleId);
            policyOwnerMyProfileRoleEditField.setText(roleName);
        } else {
            System.out.println("Failed to display profile dashboard information.");
        }
    }

    @FXML
    void policyOwnerEditConfirmBtnOnAction(ActionEvent event) {
        boolean isSuccess = updatePolicyOwner(policyOwnerMyProfileCustomerIDEditField.getText(), policyOwnerMyProfileFullNameEditField.getText(), policyOwnerMyProfilePasswordEditField.getText(), policyOwnerMyProfileEmailEditField.getText(), policyOwnerMyProfilePhoneNumberEditField.getText(), policyOwnerMyProfileAddressEditField.getText());
        if (isSuccess) {
            policyOwnerMyProfileFullNameEditField.setText(policyOwnerMyProfileFullNameEditField.getText());
            policyOwnerMyProfilePasswordEditField.setText(policyOwnerMyProfilePasswordEditField.getText());
            policyOwnerMyProfileEmailEditField.setText(policyOwnerMyProfileEmailEditField.getText());
            policyOwnerMyProfilePhoneNumberEditField.setText(policyOwnerMyProfilePhoneNumberEditField.getText());
            policyOwnerMyProfileAddressEditField.setText(policyOwnerMyProfileAddressEditField.getText());
            for (PolicyOwner selectedPolicyOwner : policyOwnerObservableList) {
                if (Objects.equals(policyOwnerMyProfileCustomerIDEditField.getText(), selectedPolicyOwner.getId())) {
                    selectedPolicyOwner.setFullName(policyOwnerMyProfileFullNameEditField.getText());
                    selectedPolicyOwner.setPassword(policyOwnerMyProfilePasswordEditField.getText());
                    selectedPolicyOwner.setEmail(policyOwnerMyProfileEmailEditField.getText());
                    selectedPolicyOwner.setPhoneNumber(policyOwnerMyProfilePhoneNumberEditField.getText());
                    selectedPolicyOwner.setAddress(policyOwnerMyProfileAddressEditField.getText());
                    break;
                }
            }
        } else {
            System.out.println("isSuccess: " + false);
        }
    }
}
