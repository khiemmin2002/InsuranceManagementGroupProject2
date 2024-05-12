package com.insurancecompany.insurancemanagementgroupproject2.controller;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.model.LoginData;
import com.insurancecompany.insurancemanagementgroupproject2.model.PolicyHolder;
import com.insurancecompany.insurancemanagementgroupproject2.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
            // Assuming the login data class has a method to get the logged in user's username
            String policyOwnerId = getIDFromUserName(LoginData.usernameLogin);

            // SQL query to select policy holders related to the logged in policy owner
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
}
