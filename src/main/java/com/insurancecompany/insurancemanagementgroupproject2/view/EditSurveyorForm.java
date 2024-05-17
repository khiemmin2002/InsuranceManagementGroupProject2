package com.insurancecompany.insurancemanagementgroupproject2.view;
/**
 * @author team 5
 */
import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.SceneLoader;
import com.insurancecompany.insurancemanagementgroupproject2.controller.SurveyorController;
import com.insurancecompany.insurancemanagementgroupproject2.controller.ValidateInput;
import com.insurancecompany.insurancemanagementgroupproject2.model.Surveyor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class EditSurveyorForm {
    @FXML
    public TextField full_name;
    @FXML
    public Button submitSurveyor;
    @FXML
    public PasswordField password;
    @FXML
    public TextField username;
    @FXML
    public TextField email;
    @FXML
    public TextField phone_number;
    @FXML
    public TextField address;
    @FXML
    public Label errorLabel;
    @FXML
    public ChoiceBox<String> idBox;
    private SurveyorController surveyorController;
    private List<Surveyor> surveyorList;
    /*
     *  Initialize the pages upon opening pages, create controller and local variable
     */
    @FXML
    private void initialize(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        surveyorController = new SurveyorController(databaseConnection, databaseConnection.getConnection());
        ObservableList<String> surveyorsID= FXCollections.observableArrayList();
        surveyorList = surveyorController.fetchSurveyor();
        surveyorsID.setAll(getID(surveyorList));
        submitSurveyor.setOnAction(submitForm);
        idBox.getItems().addAll(surveyorsID);
        idBox.setOnAction(fillForm);
    }
    //Create new validate input class object
    ValidateInput validateInput = new ValidateInput();
    // Set event handler for button on click action
    @FXML
    EventHandler<ActionEvent> submitForm = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            editSurveyor(idBox.getValue());
        }
    };
    // Set event handler for choice box on click action
    @FXML
    EventHandler<ActionEvent> fillForm = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            for(Surveyor surveyor : surveyorList){
                if(surveyor.getId().equals(idBox.getValue())){
                    full_name.setText(surveyor.getFullName());
                    phone_number.setText(surveyor.getPhoneNumber());
                    System.out.println(surveyor.getPhoneNumber());
                    email.setText(surveyor.getEmail());
                    System.out.println(surveyor.getEmail());
                    address.setText(surveyor.getAddress());
                    break;
                }
            }
        }
    };
    // Function to fetch ID
    private List<String> getID(List<Surveyor> surveyors){
        List<String> id = new ArrayList<>();
        for(Surveyor surveyor : surveyorList){
            id.add(surveyor.getId());
        }
        return id;
    }
    /*
     *  Method to check field inside text field then call controller to edit a surveyor
     */
    private void editSurveyor(String id){
        //Validate field is not empty
        if (full_name.getText().isEmpty() ||  email.getText().isEmpty()
                || phone_number.getText().isEmpty() || address.getText().isEmpty()) {
            errorLabel.setText("Please fill in all fields");
            return;
        }
        //Validate email
        if (!validateInput.isValidEmail(email.getText())) {
            errorLabel.setText("Invalid email format");
            return;
        }

        // Validate phone number
        if (!validateInput.isValidPhoneNumber(phone_number.getText())) {
            errorLabel.setText("Invalid phone number format");
            return;
        }
        surveyorController.updateSurveyorInformation(
                id,full_name.getText(),email.getText(),
                phone_number.getText(),address.getText());
        SceneLoader.loadSceneWithInput("fxml/manager-homepage.fxml",thisStage(),944,709);
    }
    //method to get this stage context
    private Stage thisStage(){
        return (Stage) submitSurveyor.getScene().getWindow();
    }
}
