package com.insurancecompany.insurancemanagementgroupproject2.controller;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.model.Claim;
import com.insurancecompany.insurancemanagementgroupproject2.model.LoginData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;

public class DependentMyClaimController implements Initializable {
    // Dependent My Claim
    @FXML
    private TableView<Claim> dependentClaimTable;

    @FXML
    private TableColumn<Claim, Double> claimAmount;

    @FXML
    private TableColumn<Claim, String> claimBankName;

    @FXML
    private TableColumn<Claim, String> claimBankNum;

    @FXML
    private TableColumn<Claim, String> claimBankUserName;

    @FXML
    private TableColumn<Claim, String> claimCardNum;

    @FXML
    private TableColumn<Claim, Date> claimDate;

    @FXML
    private TableColumn<Claim, Date> claimExamDate;

    @FXML
    private TableColumn<Claim, String> claimID;

    @FXML
    private TableColumn<Claim, String> claimInsuredPerson;

    @FXML
    private TableColumn<Claim, String> claimStatus;

    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        claimID.setCellValueFactory(new PropertyValueFactory<>("id"));
        claimCardNum.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
        claimDate.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
        claimExamDate.setCellValueFactory(new PropertyValueFactory<>("examDate"));
        claimAmount.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        claimStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        claimBankName.setCellValueFactory(new PropertyValueFactory<>("bankName"));
        claimBankUserName.setCellValueFactory(new PropertyValueFactory<>("bankUserName"));
        claimBankNum.setCellValueFactory(new PropertyValueFactory<>("bankNumber"));
        claimInsuredPerson.setCellValueFactory(cellData -> {
            String insuredPerson = cellData.getValue().getInsuredPerson();
            String name = getNameByCustomerID(insuredPerson);
            return new javafx.beans.property.SimpleStringProperty(name);
        });
        fetchClaimData();
    }

    // Dependent My Claim
    // Fetch claim data
    private void fetchClaimData() {
        // Fetch claim data from the database

        ObservableList<Claim> claimData = FXCollections.observableArrayList();
        try {
            String getClaimsQuery = "SELECT * FROM claims WHERE insured_person = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(getClaimsQuery);
            preparedStatement.setString(1, getIDFromUserName(LoginData.usernameLogin));
            ResultSet queryResult = preparedStatement.executeQuery();

            while (queryResult.next()) {
                Claim claim = new Claim();
                claim.setId(queryResult.getString("claim_id"));
                claim.setInsuredPerson(queryResult.getString("insured_person"));
                claim.setCardNumber(queryResult.getString("card_number"));
                claim.setExamDate(queryResult.getDate("exam_date"));
                claim.setClaimDate(queryResult.getDate("claim_date"));
                claim.setClaimAmount(queryResult.getDouble("claim_amount"));
                claim.setStatus(queryResult.getString("status"));
                claim.setBankName(queryResult.getString("bank_name"));
                claim.setBankUserName(queryResult.getString("bank_user_name"));
                claim.setBankNumber(queryResult.getString("bank_number"));
                claimData.add(claim);
            }


            dependentClaimTable.setItems(claimData); // Set the items to the TableView
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    private String getNameByCustomerID(String customerID) {
        String getNameQuery = "SELECT full_name FROM users WHERE id = ?";
        String name = "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getNameQuery);
            preparedStatement.setString(1, customerID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                name = resultSet.getString("full_name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return name;
    }
}
