package com.insurancecompany.insurancemanagementgroupproject2.controller;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.Models.Claim;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SurveyorHomepage {
    @FXML
    public TableView<Claim> claimTable;
    @FXML
    public Button fetchAllClaimButton;
    @FXML
    public Button fetchProposableClaimButton;
    @FXML
    public Button fetchSingleClaimButton;
    @FXML
    public Label errorLabel;
    @FXML
    public TextField insertID;
    @FXML
    public Button sortPerson;
    @FXML
    public Button sortCard;
    @FXML
    public Button refreshData;
    @FXML
    private TableColumn<?, ?> claimID;
    @FXML
    private TableColumn<?, ?> cardNumber;
    @FXML
    private TableColumn<?, ?> claimAmount;
    @FXML
    private TableColumn<?, ?> claimDate;
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
    private List<Claim> claimList;
    @FXML
    private void initialize() {
        // Set up column widths and cell value factories
        claimTable.widthProperty().addListener((observable, oldValue, newValue) -> {
            double tableWidth = claimTable.getWidth();
            // Set preferred widths for columns based on table width
            // This ensures that columns resize dynamically
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

            // Set event handlers for buttons
            fetchAllClaimButton.setOnAction(fetchAllClick);
            fetchProposableClaimButton.setOnAction(fetchProposalClick);
            fetchSingleClaimButton.setOnAction(fetchSingleClaimClick);
            sortPerson.setOnAction(sortByPerson);
            sortCard.setOnAction(sortByCard);
            refreshData.setOnAction(refreshClaimData);
            //Call API to fetch claim data from database
            fetchClaimData();
        });
    }

    EventHandler<ActionEvent> fetchAllClick = _ -> fetchAllClaimData();
    EventHandler<ActionEvent> fetchProposalClick = _ -> fetchStatusNewClaimData();
    EventHandler<ActionEvent> sortByPerson = _ -> sortByClaimPerson();
    EventHandler<ActionEvent> sortByCard = _ -> sortByClaimCard();
    EventHandler<ActionEvent> refreshClaimData = _ -> fetchClaimData();

    EventHandler<ActionEvent> fetchSingleClaimClick = new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (insertID.getText().isEmpty()) {
                errorLabel.setText("Cannot search empty field!");
            } else {
                fetchSingleClaim(insertID.getText());
            }
        }
    };
    public void fetchClaimData() {
        //Create new instance of DatabaseConnection class
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        //Create ObservableList for TableView
        ObservableList<Claim> claimData = FXCollections.observableArrayList();
        claimList = new ArrayList<Claim>();
        //Handling SQL exception by surrounding try catch
        try {
            String getClaimsQuery = "SELECT * FROM claims";
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(getClaimsQuery);
            //Extract result and put it into local arraylist
            while (queryResult.next()) {
                Claim claim = new Claim();
                claim.setId(queryResult.getString("claim_id"));
                claim.setInsuredPerson(queryResult.getString("insured_person"));
                claim.setCardNumber(queryResult.getString("card_number"));
                claim.setExamDate(queryResult.getDate("exam_date"));
                claim.setClaimDate(queryResult.getDate("claim_date"));
                claim.setClaimAmount(queryResult.getFloat("claim_amount"));
                claim.setStatus(queryResult.getString("status"));
                claim.setBankName(queryResult.getString("bank_name"));
                claim.setBankUserName(queryResult.getString("bank_user_name"));
                claim.setBankNumber(queryResult.getString("bank_number"));
                claimData.add(claim);
                claimList.add(claim);
            }
            //Set view table
            System.out.println("Fetch data from database successfully!");
            claimTable.setItems(claimData);
        } catch (SQLException e) {
            System.out.println("SQL error: " + e);
        }
    }
    public void fetchSingleClaim(String claimID){
        ObservableList<Claim> claimData = FXCollections.observableArrayList();
        boolean statusCheck = false;
        for(Claim claim : claimList){
            if(claim.getId().equals(claimID)){
                claimData.add(claim);
                if(claim.getStatus().equals("NEW")){
                    statusCheck = true;
                }
                break;
            }
        }
        claimTable.setItems(claimData);
        if(statusCheck){createProposeAlert(claimID);
        }
    }
    public void fetchAllClaimData() {
        ObservableList<Claim> claimData = FXCollections.observableArrayList();
        claimData.addAll(claimList);
        claimTable.setItems(claimData);
    }
    public void fetchStatusNewClaimData() {
        ObservableList<Claim> claimData = FXCollections.observableArrayList();
        for(Claim claim : claimList){
            if(claim.getStatus().equals("NEW")){
                claimData.add(claim);
            }
        }
        claimTable.setItems(claimData);
    }
    public void sortByClaimPerson(){
        ObservableList<Claim> claimData = FXCollections.observableArrayList();
        List<Claim> sortedList = claimList;
        sortedList.sort(new Comparator<Claim>() {
            @Override
            public int compare(Claim o1, Claim o2) {
                //Extract numerical number inside claim person ("C" + 7 number), then parse
                //as Int to compare
                long num1 = Long.parseLong(o1.getInsuredPerson().substring(1));
                long num2 = Long.parseLong(o2.getInsuredPerson().substring(1));
                //Compare the number after casting to integer
                return Long.compare(num1,num2);
            }
        });
        claimData.addAll(sortedList);
        claimTable.setItems(claimData);
    }
    public void sortByClaimCard(){
        ObservableList<Claim> claimData = FXCollections.observableArrayList();
        List<Claim> sortedList = claimList;
        sortedList.sort(new Comparator<Claim>() {
            @Override
            public int compare(Claim o1, Claim o2) {
                //Extract numerical number inside claim id ("X" + number), then parse
                //as Int to compare
                long num1 = Long.parseLong(o1.getCardNumber().substring(1));
                long num2 = Long.parseLong(o2.getCardNumber().substring(1));
                //Compare the number after casting to integer
                return Long.compare(num1,num2);
            }
        });
        claimData.addAll(sortedList);
        claimTable.setItems(claimData);
    }
    public void createProposeAlert(String claimID){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Propose dialog");
        alert.setContentText("Proposing claim " + claimID + " to manager?");
        // Define custom buttons, set it to the alert
        ButtonType proposeButton = new ButtonType("Propose");
        ButtonType requestButton = new ButtonType("Request");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonType.CANCEL.getButtonData());
        alert.getButtonTypes().setAll(proposeButton,requestButton,cancelButton);
        //Logic to handle operation of button
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            //Handle proposing a claim to manager
            System.out.println("Claim " + claimID + " proposed successfully!");
            insertID.setText("");
            fetchClaimData();
        } else if (result.get() == requestButton) {
            // Handle requesting more information from a claim
            System.out.println("Claim " + claimID + " postponed.");
            insertID.setText("");
            fetchClaimData();
        }else {
            //Handle cancellation of operation
            insertID.setText("");
            System.out.println("Claim proposing cancelled.");
        }
    }
}
