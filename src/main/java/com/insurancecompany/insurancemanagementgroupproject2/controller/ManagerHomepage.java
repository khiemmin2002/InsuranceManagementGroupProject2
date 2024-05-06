package com.insurancecompany.insurancemanagementgroupproject2.controller;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.Models.Claim;
import com.insurancecompany.insurancemanagementgroupproject2.Models.Provider;
import com.insurancecompany.insurancemanagementgroupproject2.Models.Surveyor;
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
import java.util.List;
import java.util.Optional;

public class ManagerHomepage extends SurveyorHomepage{
    @FXML
    public TableView<Surveyor> surveyorTable;
    @FXML
    private Button fetchAllClaimButton;
    @FXML
    private Button fetchProposableClaimButton;
    @FXML
    private Button fetchSingleClaimButton;
    @FXML
    private Button refreshData;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField insertID;
    @FXML
    private Button sortPerson;
    @FXML
    private Button sortCard;
    @FXML
    private TableView<Claim> claimTable;
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
    @FXML
    private TableColumn<?, ?> surveyorID;
    @FXML
    private TableColumn<?, ?> surveyorFullName;
    @FXML
    private TableColumn<?, ?> surveyorUserName;
    @FXML
    private TableColumn<?, ?> surveyorEmail;
    @FXML
    private TableColumn<?, ?> surveyorPhoneNumber;
    private List<Claim> claimList = new ArrayList<Claim>();
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
        });
        surveyorTable.widthProperty().addListener((observable, oldValue, newValue) -> {
                    surveyorID.setCellValueFactory(new PropertyValueFactory<>("id"));
                    surveyorFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
                    surveyorUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
                    surveyorEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
                    surveyorPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        });
        // Set event handlers for buttons
        fetchAllClaimButton.setOnAction(fetchAllClick);
        fetchProposableClaimButton.setOnAction(fetchProposalClick);
        fetchSingleClaimButton.setOnAction(fetchSingleClaimClick);
        sortPerson.setOnAction(sortByPerson);
        sortCard.setOnAction(sortByCard);
        refreshData.setOnAction(refreshClaimData);
        //Call API to fetch claim data from database
        claimList = fetchClaimData();
        fetchSurveyorData();
    }
    EventHandler<ActionEvent> refreshClaimData = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            claimList = fetchClaimData();
            fetchSurveyorData();
        }
    };
    public void fetchSurveyorData(){
        //Create new instance of DatabaseConnection class
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        //Create ObservableList for TableView
        ObservableList<Surveyor> surveyorData = FXCollections.observableArrayList();
        //Handling SQL exception by surrounding try catch
        try {
            String getSurveyorQuery = "SELECT id,full_name,user_name,email,phone_number FROM users WHERE role_id = 2";
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(getSurveyorQuery);
            //Extract result and put it into local arraylist
            while (queryResult.next()){
                Surveyor provider = new Surveyor(
                        queryResult.getString("id"),
                        queryResult.getString("full_name"),
                        queryResult.getString("user_name"),
                        queryResult.getString("email"),
                        queryResult.getString("phone_number")
                );
                surveyorData.add(provider);
            }
            surveyorTable.setItems(surveyorData);
            System.out.println("Fetch data from database.user successfully!");
        }catch (SQLException e){
            System.out.println("SQL exception: " + e);
        }
    }

    @Override
    public void fetchSingleClaim(String claimID) {
        ObservableList<Claim> claimData = FXCollections.observableArrayList();
        boolean statusCheck = false;
        for(Claim claim : claimList){
            if(claim.getId().equals(claimID)){
                claimData.add(claim);
                if(claim.getStatus().equals("PROCESSING")){
                    statusCheck = true;
                }
                break;
            }
        }
        claimTable.setItems(claimData);
        if(statusCheck){createProposeAlert(claimID);
        }
    }

    @Override
    public void createProposeAlert(String claimID) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Approval confirmation");
        alert.setContentText("Approving " + claimID + " status?");
        // Define custom buttons, set it to the alert
        ButtonType approveButton = new ButtonType("Approve claim");
        ButtonType denyButton = new ButtonType("Deny claim");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonType.CANCEL.getButtonData());
        alert.getButtonTypes().setAll(approveButton,denyButton,cancelButton);
        //Logic to handle operation of button
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            //Handle proposing a claim to manager
            System.out.println("Claim " + claimID + " approved successfully!");
            insertID.setText("");
            fetchClaimData();
        } else if (result.get() == denyButton) {
            // Handle requesting more information from a claim
            System.out.println("Claim " + claimID + " denied!");
            insertID.setText("");
            fetchClaimData();
        }else {
            //Handle cancellation of operation
            insertID.setText("");
            System.out.println("Claim proposing cancelled.");
        }
    }
}
