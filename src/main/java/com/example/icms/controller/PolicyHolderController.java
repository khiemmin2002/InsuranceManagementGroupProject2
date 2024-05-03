package com.example.icms.controller;

import com.example.icms.DatabaseConnection;
import com.example.icms.model.Claim;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PolicyHolderController {

    @FXML
    private MenuItem addClaim;

    @FXML
    private TableColumn<?, ?> cardNumber;

    @FXML
    private TableColumn<?, ?> claimAmount;

    @FXML
    private TableColumn<?, ?> claimDate;

    @FXML
    private TableColumn<?, ?> claimID;

    @FXML
    private TableView<?> claimTable;

    @FXML
    private MenuItem deleteClaim;

    @FXML
    private TableColumn<?, ?> documents;

    @FXML
    private TableColumn<?, ?> examDate;

    @FXML
    private TableColumn<?, ?> insuredPerson;

    @FXML
    private TableColumn<?, ?> status;

    @FXML
    private MenuItem updateClaim;

    @FXML
    private TextField findClaimIdField;

    @FXML
    private void initialize() {
        claimTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        claimTable.widthProperty().addListener((observable, oldValue, newValue) -> {
            double tableWidth = claimTable.getWidth();
            // Set percentage widths for each column
            claimID.setPrefWidth(tableWidth * 0.1);
            insuredPerson.setPrefWidth(tableWidth * 0.2);
            cardNumber.setPrefWidth(tableWidth * 0.15);
            examDate.setPrefWidth(tableWidth * 0.1);
            claimDate.setPrefWidth(tableWidth * 0.1);
            claimAmount.setPrefWidth(tableWidth * 0.1);
            status.setPrefWidth(tableWidth * 0.1);
            documents.setPrefWidth(tableWidth * 0.15);
        });
    }

    @FXML
    private void openDependents() {

    }

    @FXML
    private void exitProgram(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void updateClaim() {

    }

    @FXML
    private void deleteClaim() {

    }

    @FXML
    private void findClaimId() {

        String claimId = findClaimIdField.getText();

    }

    public void fetchClaimData() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        ObservableList<Claim> claimData = FXCollections.observableArrayList();

        try {
            String getClaimsQuery = "SELECT * FROM claims";
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(getClaimsQuery);

            while (queryResult.next()) {
                Claim claim = new Claim();
                claim.setId(queryResult.getString("id"));
                claim.setInsuredPerson(queryResult.getString("insured_person"));
                claim.setCardNumber(queryResult.getString("card_number"));
                claim.setExamDate(queryResult.getDate("exam_date"));
                claim.setClaimDate(queryResult.getDate("claim_date"));
                claim.setClaimAmount(queryResult.getDouble("claim_amount"));
                claim.setStatus(queryResult.getString("status"));
                claim.setDocuments(queryResult.getString("documents"));
                claimData.add(claim);
            }

            claimID.setCellValueFactory(new PropertyValueFactory<>("id"));
            insuredPerson.setCellValueFactory(new PropertyValueFactory<>("insuredPerson"));
            cardNumber.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
            examDate.setCellValueFactory(new PropertyValueFactory<>("examDate"));
            claimDate.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
            claimAmount.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
            documents.setCellValueFactory(new PropertyValueFactory<>("documents"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void openAddClaimModal() {
        // Implement the logic to open add claim modal
    }
}
