package com.example.icms.controller;

import com.example.icms.DatabaseConnection;
import com.example.icms.model.Claim;
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
    private MenuItem exitMenuItem;

    @FXML
    private MenuItem updateClaimMenuItem;

    @FXML
    private MenuItem deleteClaimMenuItem;
    @FXML
    private TableView<?> tableView;

    @FXML
    private TableColumn<?, ?> idColumn;
    @FXML
    private TableColumn<?, ?> insuredPersonColumn;
    @FXML
    private TableColumn<?, ?> cardNumberColumn;
    @FXML
    private TableColumn<?, ?> examDateColumn;
    @FXML
    private TableColumn<?, ?> claimDateColumn;
    @FXML
    private TableColumn<?, ?> claimAmountColumn;
    @FXML
    private TableColumn<?, ?> statusColumn;
    @FXML
    private TableColumn<?, ?> documentsColumn;
    @FXML
    private TextField findClaimIdField;

    @FXML
    private void initialize() {
        // Set column resize policy to constrain the column width
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Add a listener to resize columns when table view size changes
        tableView.widthProperty().addListener((observable, oldValue, newValue) -> {
            double tableWidth = tableView.getWidth();
            // Set percentage widths for each column
            idColumn.setPrefWidth(tableWidth * 0.1);
            insuredPersonColumn.setPrefWidth(tableWidth * 0.2);
            cardNumberColumn.setPrefWidth(tableWidth * 0.15);
            examDateColumn.setPrefWidth(tableWidth * 0.1);
            claimDateColumn.setPrefWidth(tableWidth * 0.1);
            claimAmountColumn.setPrefWidth(tableWidth * 0.1);
            statusColumn.setPrefWidth(tableWidth * 0.1);
            documentsColumn.setPrefWidth(tableWidth * 0.15);
        });
    }

    @FXML
    private void openDependents() {
        // Implement the logic to open dependents
    }

    @FXML
    private void exitProgram(ActionEvent event) {
        Stage stage = (Stage) exitMenuItem.getParentPopup().getOwnerWindow();
        stage.close();
    }

    @FXML
    private void updateClaim() {
        // Implement the logic to update a claim
    }

    @FXML
    private void deleteClaim() {
        // Implement the logic to delete a claim
    }

    @FXML
    private void findClaimId() {
        // Implement the logic to find a claim by ID
        String claimId = findClaimIdField.getText();
        // Use claimId to search for the claim in your data
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

            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            insuredPersonColumn.setCellValueFactory(new PropertyValueFactory<>("insuredPerson"));
            cardNumberColumn.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
            examDateColumn.setCellValueFactory(new PropertyValueFactory<>("examDate"));
            claimDateColumn.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
            claimAmountColumn.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
            documentsColumn.setCellValueFactory(new PropertyValueFactory<>("documents"));
         } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
