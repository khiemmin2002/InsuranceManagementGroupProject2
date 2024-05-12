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
    private TableColumn<?, ?> addressCol;

    @FXML
    private TableView<Dependent> dependentTable;

    @FXML
    private Button clearInputButton;

    @FXML
    private Button clearAddInputBtn;

    @FXML
    private Button confirmAddBtn;

    @FXML
    private TableColumn<?, ?> dependentIDCol;

    @FXML
    private TableColumn<?, ?> emailCol;


    @FXML
    private TableColumn<?, ?> fullNameCol;


    @FXML
    private TableColumn<?, ?> userNameCol;

    @FXML
    private TableColumn<?, ?> passwordCol;

    @FXML
    private TextField addDependentIDField;

    @FXML
    private TextField addPolicyHolderField;

    @FXML
    private TableColumn<?, ?> phoneNumberCol;


    @FXML
    private MenuItem exitBtn;

    @FXML
    private TextField inputUserName;;

    @FXML
    private MenuItem openClaimBtn;


    @FXML
    private MenuItem updateDependent;

    private String userName;

    @FXML
    private void initialize() {
        dependentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        dependentTable.widthProperty().addListener((observable, oldValue, newValue) -> {
            double tableWidth = dependentTable.getWidth();
            userNameCol.setPrefWidth(tableWidth * 0.15);
            fullNameCol.setPrefWidth(tableWidth * 0.2);
            dependentIDCol.setPrefWidth(tableWidth * 0.1);
            passwordCol.setPrefWidth(tableWidth * 0.15);
            phoneNumberCol.setPrefWidth(tableWidth * 0.15);
            emailCol.setPrefWidth(tableWidth * 0.15);

        });
        this.userName = LoginData.usernameLogin;
        fetchDependentData();
    }

    @FXML
    void clearInputData(ActionEvent event) {
        inputUserName.clear();
        fetchDependentData();
    }

    @FXML
    void exitProgram(ActionEvent event) {
        inputUserName.clear();
        fetchDependentData();
    }

    @FXML
    void findDependentUserName(ActionEvent event) {
        String dependentUserName = inputUserName.getText();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        try {
            String findQuery = "SELECT id, full_name, user_name, password, email, phone_number, address" +
                    "FROM users" +
                    "WHERE userName = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(findQuery);
            preparedStatement.setString(1, dependentUserName);
            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<Dependent> foundDependents = FXCollections.observableArrayList();
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
            if(foundDependents.isEmpty()) {
                System.out.println("No dependents found.");  // Debug output
            }

            dependentTable.setItems(foundDependents);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @FXML
    void clearAddFields(ActionEvent event) {

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
            String getDependentQuery = "SELECT d.id, d.full_name, d.user_name,d.password, d.email, d.phone_number, d.address " +
                    "FROM users d " +
                    "JOIN dependent dep " +
                    "ON d.id = dep.dependent_id " +
                    "JOIN users p " +
                    "ON p.id = dep.policy_holder_id " +
                    "WHERE p.user_name = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(getDependentQuery);
            preparedStatement.setString(1, userName);
            ResultSet queryResult = preparedStatement.executeQuery();
            while (queryResult.next()) {
                Dependent dependent = new Dependent();
                dependent.setId(queryResult.getString("id"));
                dependent.setFullName(queryResult.getString("full_name"));
                dependent.setUserName(queryResult.getString("user_name"));
                dependent.setPassword(queryResult.getString("password"));
                dependent.setEmail(queryResult.getString("email"));
                dependent.setPhoneNumber(queryResult.getString("phone_number"));
                dependent.setAddress(queryResult.getString("address"));

                dependentData.add(dependent);
            }
            dependentIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
            fullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
            phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
            addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));


            dependentTable.setItems(dependentData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

}

