package com.insurancecompany.insurancemanagementgroupproject2.view;


import com.insurancecompany.insurancemanagementgroupproject2.HelloApplication;
import com.insurancecompany.insurancemanagementgroupproject2.controller.policyholder.PolicyHolderClaimController;
import com.insurancecompany.insurancemanagementgroupproject2.model.Claim;
import com.insurancecompany.insurancemanagementgroupproject2.model.LoginData;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.*;
import java.util.Random;

public class PolicyHolderClaimHomepage {

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
    private TableColumn<?, ?> bankName;

    @FXML
    private TableColumn<?, ?> bankNumber;

    @FXML
    private TableColumn<?, ?> bankUserName;


    @FXML
    private TableColumn<?, ?> examDate;

    @FXML
    private TableColumn<?, ?> document;

    @FXML
    private TableColumn<?, ?> insuredPerson;

    @FXML
    private TableColumn<?, ?> status;

    @FXML
    private MenuItem updateClaim;

    @FXML
    private MenuItem logoutBtn;

    @FXML
    private TextField inputClaimId;

    @FXML
    private MenuItem openDependentBtn;

    @FXML
    private Button clearInputButton;

    private String userName;

    @FXML
    private TableColumn<Claim, Void> deleteColumn;

    private PolicyHolderClaimController policyHolderClaimController = new PolicyHolderClaimController();


    private void setUpDeleteColumn() {
        deleteColumn.setCellFactory(param -> new TableCell<Claim, Void>() {
            private final Button btn = new Button("Delete");
            {
                btn.setOnAction(event -> {
                    Claim claim = getTableView().getItems().get(getIndex());
                    if (claim != null) {
                        deleteClaim(claim);
                    }
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });
    }
    private void showAlert(boolean success, String message) {
        Alert.AlertType type = success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR;
        Alert alert = new Alert(type);
        alert.setTitle(success ? "Success" : "Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void deleteClaim(Claim claim)  {
        if (claim == null) {
            showAlert(false, "No claim selected for deletion. ");
            return;
        }
        try {
            policyHolderClaimController.deleteClaimDocuments(claim.getId());
            policyHolderClaimController.deleteClaim(claim.getId());
            claimTable.getItems().remove(claim);
        } catch (SQLException e) {
            showAlert(false, "Error deleting claim: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }



    @FXML
    void logout(ActionEvent event) {
        try {
            MenuItem menuItem = (MenuItem) event.getSource();


            Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();
            Stage currentStage = (Stage) scene.getWindow();


            currentStage.close();


            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/login.fxml"));
            Parent root = loader.load();
            Stage loginStage = new Stage();
            Scene loginScene = new Scene(root);

            loginStage.setTitle("Login - Insurance Claim Management System");
            loginStage.setScene(loginScene);
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed to load login screen.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message);
        alert.showAndWait();
    }



    private String generateRandomClaimID() {
        StringBuilder claimId = new StringBuilder("F");
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            claimId.append(random.nextInt(10));
        }
        return claimId.toString();
    }



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
            document.setPrefWidth(tableWidth * 0.2);
            deleteColumn.setPrefWidth(tableWidth * 0.15);
        });
        this.userName = LoginData.usernameLogin;
        setUpDeleteColumn();
        fetchClaimData();
    }

    @FXML
    private void openDependents() throws IOException {
        Stage currentStage = (Stage) claimTable.getScene().getWindow();
        currentStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/policy-holder-dependent.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Dependent");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    @FXML
    private void exitProgram(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void updateClaim() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/policy-holder-update-claim.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Update Claim");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }


    @FXML
    void clearInputData(ActionEvent event) {
        inputClaimId.clear();
        fetchClaimData();
    }

    @FXML
    private void findClaimId() {
        String insuredPersonId = inputClaimId.getText();
        try {
            ObservableList<Claim> foundClaims = policyHolderClaimController.findClaimsByInsuredPerson(insuredPersonId);
            claimTable.setItems(foundClaims);
        } catch (SQLException e) {
            showAlert(false, "Error finding claims: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void fetchClaimData() {
        try {
            ObservableList<Claim> claimData = policyHolderClaimController.fetchAllClaims();
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
            document.setCellValueFactory(new PropertyValueFactory<>("documents"));
            claimTable.setItems(claimData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void openAddClaimModal() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/policy-holder-add-claim.fxml"));
        Parent root = fxmlLoader.load();

        // Set up the scene
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add Claim");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }


}
