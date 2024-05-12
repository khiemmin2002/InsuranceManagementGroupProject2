package com.insurancecompany.insurancemanagementgroupproject2.controller;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.model.Dependent;
import com.insurancecompany.insurancemanagementgroupproject2.model.LoginData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PolicyHolderDependentController {

    @FXML
    private MenuItem addDependent;

    @FXML
    private TableView<Dependent> dependentTable;

    @FXML
    private Button clearInputButton;

    @FXML
    private TableColumn<?,?> dependentIDCol;

    @FXML
    private TableColumn<?,?> dependentUserNameCol;

    @FXML
    private MenuItem exitBtn;

    @FXML
    private TextField inputUserId;

    @FXML
    private MenuItem openClaimBtn;

    @FXML
    private TableColumn<?, ?> policyHolderIDCol;

    @FXML
    private TableColumn<?,?> policyHolderUserNameCol;

    @FXML
    private MenuItem updateDependent;

    private String userName;

    @FXML
    private void initialize() {
        this.userName = LoginData.usernameLogin;
        fetchDependentData();
    }

    @FXML
    void clearInputData(ActionEvent event) {

    }

    @FXML
    void exitProgram(ActionEvent event) {

    }

    @FXML
    void findUserID(ActionEvent event) {

    }

    @FXML
    void openAddDependentModal(ActionEvent event) {

    }

    @FXML
    void openClaimModal(ActionEvent event) {

    }

    @FXML
    void updateDependent(ActionEvent event) {

    }

    private void fetchDependentData() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        ObservableList<Dependent> dependentData = FXCollections.observableArrayList();

        try {
            String getDependetQuery = "SELECT d.*, u.user_name AS dependent_name, p.user_name AS policy_holder_name  " +
                    "FROM dependent d " +
                    "JOIN users u ON d.dependent_id = u.id " +
                    "JOIN users p ON d.policy_holder_id = p.id " +
                    "WHERE p.user_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(getDependetQuery);
            preparedStatement.setString(1, userName);
            ResultSet queryResult = preparedStatement.executeQuery();
            while (queryResult.next()) {
                Dependent dependent = new Dependent();
                dependent.setDependentId(queryResult.getString("dependent_id"));
                dependent.setDependentUserName(queryResult.getString("dependent_name"));
                dependent.setPolicyHolderId(queryResult.getString("policy_holder_id"));
                dependent.setPolicyHolderUserName(queryResult.getString("policy_holder_name"));
                dependentData.add(dependent);
            }
            dependentIDCol.setCellValueFactory(new PropertyValueFactory<>("dependentId"));
            dependentUserNameCol.setCellValueFactory(new PropertyValueFactory<>("dependentUserName"));
            policyHolderIDCol.setCellValueFactory(new PropertyValueFactory<>("policyHolderId"));
            policyHolderUserNameCol.setCellValueFactory(new PropertyValueFactory<>("policyHolderUserName"));

            dependentTable.setItems(dependentData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

}
