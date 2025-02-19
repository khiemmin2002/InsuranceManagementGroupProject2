package com.insurancecompany.insurancemanagementgroupproject2.view;
/**
 * @author team 5
 */
import com.insurancecompany.insurancemanagementgroupproject2.controller.ClaimController;
import com.insurancecompany.insurancemanagementgroupproject2.model.Claim;
import com.insurancecompany.insurancemanagementgroupproject2.model.LoginData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SurveyorHomepage {
    @FXML
    public ChoiceBox<String> claimChoiceBox;
    @FXML
    public Label surveryorName;
    @FXML
    public Button logout;
    @FXML
    private TableView<Claim> claimTable;
    @FXML
    private Button fetchAllClaimButton;
    @FXML
    private Button fetchProposableClaimButton;
    @FXML
    private Button fetchSingleClaimButton;
    @FXML
    private Label errorLabel;
    @FXML
    private Button sortPerson;
    @FXML
    private Button sortCard;
    @FXML
    private Button refreshData;
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
    /*
    *  Initialize the pages upon opening pages
    */
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
            // Setting cell value for Table View
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
        });
        // Set event handlers for buttons
        fetchAllClaimButton.setOnAction(fetchAllClick);
        fetchProposableClaimButton.setOnAction(fetchProposalClick);
        fetchSingleClaimButton.setOnAction(fetchSingleClaimClick);
        sortPerson.setOnAction(sortByPerson);
        sortCard.setOnAction(sortByCard);
        refreshData.setOnAction(refreshClaimData);
        logout.setOnAction(logoutClick);
        surveryorName.setText("Welcome Insurance Surveyor " + LoginData.usernameLogin);
        //Call API to fetch claim data from database
        fetchClaimData();
    }
    /*
    Event Handler to handle button action of onclick
     */
    EventHandler<ActionEvent> fetchAllClick = (ActionEvent ) -> fetchAllClaimData(claimList);
    EventHandler<ActionEvent> fetchProposalClick = (ActionEvent ) -> fetchStatusNewClaimData();
    EventHandler<ActionEvent> sortByPerson = (ActionEvent ) -> sortByClaimPerson();
    EventHandler<ActionEvent> sortByCard = (ActionEvent ) -> sortByClaimCard();
    EventHandler<ActionEvent> refreshClaimData = (ActionEvent ) -> fetchClaimData();
    EventHandler<ActionEvent> logoutClick = (ActionEvent ) -> LoginData.logOut(logout);
    EventHandler<ActionEvent> fetchSingleClaimClick = new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (claimChoiceBox.getValue() == null) {
                errorLabel.setText("Empty claim value!");
            } else {
                fetchClaimForAction(claimChoiceBox.getValue());
            }
        }
    };
    //Create a new Claim Controller object
    ClaimController claimController = new ClaimController();
    /*
        Function to fetch all claim data by calling from claim controller methods and append to local variable
     */
    public List<Claim> fetchClaimData() {
        //Create ObservableList for TableView
        ObservableList<Claim> claimData = FXCollections.observableArrayList();
        ObservableList<String> newClaimID = FXCollections.observableArrayList();
        claimList = claimController.fetchClaimsFromDatabase();
        claimData.addAll(claimList);
        //Set view table
        claimTable.setItems(claimData);
        for (Claim claim : claimList){
            if(claim.getStatus().equals("NEW")){
                newClaimID.add(claim.getId());
            }
        }
        claimChoiceBox.setItems(newClaimID);
        return claimList;
    }
    // Return a single claim matching the ID and properties to perform action
    public void fetchClaimForAction(String claimID){
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
    // Return all claim data from local variable back
    public void fetchAllClaimData(List<Claim> claim) {
        ObservableList<Claim> claimData = FXCollections.observableArrayList();
        claimData.addAll(claim);
        claimTable.setItems(claimData);
    }
    // Fetch claim with value status = 'NEW'
    public void fetchStatusNewClaimData() {
        ObservableList<Claim> claimData = FXCollections.observableArrayList();
        for(Claim claim : claimList){
            if(claim.getStatus().equals("NEW")){
                claimData.add(claim);
            }
        }
        claimTable.setItems(claimData);
    }
    //Sort the claim list by user ID
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
    //Sort the claim list by card number
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
    /*
    Create an alert to allow surveyor role to perform action on claims
     */
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
        if (result.isPresent() && result.get() == proposeButton) {
            //Handle proposing a claim to manager
            boolean success = ClaimController.proposeClaim(claimID);
            System.out.println("Claim " + claimID + " proposed successfully: " + success);
            claimChoiceBox.setValue(null);
            fetchClaimData();
        } else if (result.get() == requestButton) {
            // Handle requesting more information from a claim
            boolean success = ClaimController.resubmitClaim(claimID);
            System.out.println("Claim " + claimID + " request for more information: " + success);
            claimChoiceBox.setValue(null);
            fetchClaimData();
        }else {
            //Handle cancellation of operation
            claimChoiceBox.setValue(null);
            System.out.println("Claim proposing cancelled.");
            fetchAllClaimData(claimList);
        }
    }
}
