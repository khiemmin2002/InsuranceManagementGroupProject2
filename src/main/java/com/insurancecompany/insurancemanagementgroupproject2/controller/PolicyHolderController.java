package com.insurancecompany.insurancemanagementgroupproject2.controller;


import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.HelloApplication;
import com.insurancecompany.insurancemanagementgroupproject2.model.Claim;
import com.insurancecompany.insurancemanagementgroupproject2.model.Manager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.List;

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
    private TableView<Claim> claimTable;

    @FXML
    private MenuItem deleteClaim;

    @FXML
    private TableColumn<?, ?> bankName;

    @FXML
    private TableColumn<?, ?> bankNumber;

    @FXML
    private TableColumn<?, ?> bankUserName;


    @FXML
    private TableColumn<?, ?> examDate;

    @FXML
    private TableColumn<?, ?> insuredPerson;

    @FXML
    private TableColumn<?, ?> status;

    @FXML
    private MenuItem updateClaim;

    @FXML
    private TextField inputClaimId;

    @FXML
    private Button clearInputButton;

    @FXML
    private Button btnUploadDocuments;



    @FXML
    private void initialize() {
        claimTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        claimTable.widthProperty().addListener((observable, oldValue, newValue) -> {
            double tableWidth = claimTable.getWidth();
            claimID.setPrefWidth(tableWidth * 0.1);
            insuredPerson.setPrefWidth(tableWidth * 0.2);
            cardNumber.setPrefWidth(tableWidth * 0.15);
            examDate.setPrefWidth(tableWidth * 0.1);
            claimDate.setPrefWidth(tableWidth * 0.1);
            claimAmount.setPrefWidth(tableWidth * 0.1);
            status.setPrefWidth(tableWidth * 0.1);
            bankName.setPrefWidth(tableWidth * 0.15);
            bankUserName.setPrefWidth(tableWidth * 0.15);
            bankNumber.setPrefWidth(tableWidth * 0.15);
        });
        fetchClaimData();
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
    void clearInputData(ActionEvent event) {
        inputClaimId.clear();
        fetchClaimData();
    }

    @FXML
    private void findClaimId() {

        String insuredPersonId = inputClaimId.getText();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        try {
            String claimQuery = "SELECT * FROM public.claims WHERE insured_person = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(claimQuery);
            preparedStatement.setString(1, insuredPersonId);
            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<Claim> foundClaims = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Claim claim = new Claim();
                claim.setId(resultSet.getString("claim_id"));
                claim.setInsuredPerson(resultSet.getString("insured_person"));
                claim.setCardNumber(resultSet.getString("card_number"));
                claim.setExamDate(resultSet.getDate("exam_date"));
                claim.setClaimDate(resultSet.getDate("claim_date"));
                claim.setClaimAmount(resultSet.getDouble("claim_amount"));
                claim.setStatus(resultSet.getString("status"));
                claim.setBankName(resultSet.getString("bank_name"));
                claim.setBankUserName(resultSet.getString("bank_user_name"));
                claim.setBankNumber(resultSet.getString("bank_number"));

                foundClaims.add(claim);

            }
            claimTable.setItems(foundClaims);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void fetchClaimData() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        ObservableList<Claim> claimData = FXCollections.observableArrayList();

        try {
            String getClaimsQuery = "SELECT * FROM public.claims";
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(getClaimsQuery);

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

            claimID.setCellValueFactory(new PropertyValueFactory<>("id"));
            insuredPerson.setCellValueFactory(new PropertyValueFactory<>("insuredPerson"));
            cardNumber.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
            examDate.setCellValueFactory(new PropertyValueFactory<>("examDate"));
            claimDate.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
            claimAmount.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
            bankName.setCellValueFactory(new PropertyValueFactory<>("bankName"));
            bankUserName.setCellValueFactory(new PropertyValueFactory<>("bankUserName"));
            bankNumber.setCellValueFactory(new PropertyValueFactory<>("bankNumber"));

            claimTable.setItems(claimData); // Set the items to the TableView
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void openAddClaimModal() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/add-claim.fxml"));
        Parent root = fxmlLoader.load();

        // Set up the scene
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add Claim");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }




}
