package com.insurancecompany.insurancemanagementgroupproject2.view;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.SceneLoader;
import com.insurancecompany.insurancemanagementgroupproject2.controller.SurveyorController;
import com.insurancecompany.insurancemanagementgroupproject2.model.Surveyor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class DeleteSurveyorForm {
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private Label nameLabel;
    @FXML
    private Button submitDelete;
    private SurveyorController surveyorController;
    private List<Surveyor> surveyorList;
    @FXML
    public void initialize(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        surveyorController = new SurveyorController(databaseConnection, databaseConnection.getConnection());
        ObservableList<String> surveyorsID= FXCollections.observableArrayList();
        surveyorList = surveyorController.fetchSurveyor();
        surveyorsID.setAll(getID(surveyorList));
        choiceBox.getItems().addAll(surveyorsID);
        submitDelete.setOnAction((ActionEvent) -> deleteSurveyor());
        choiceBox.setOnAction(fillLabel);
    }
    @FXML
    EventHandler<ActionEvent> fillLabel = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            for(Surveyor surveyor : surveyorList){
                if(surveyor.getId().equals(choiceBox.getValue())){
                    nameLabel.setText(surveyor.getFullName());
                    break;
                }
            }
        }
    };
    private void deleteSurveyor(){
        if(choiceBox.getValue() == null){
            nameLabel.setText("Please choose a surveyors!");
            return;
        } else {
            surveyorController.removeSurveyor(choiceBox.getValue());
            SceneLoader.loadSceneWithInput("fxml/manager-homepage.fxml",thisStage(),944,709);
        }
    }
    private List<String> getID(List<Surveyor> surveyors){
        List<String> id = new ArrayList<>();
        for(Surveyor surveyor : surveyorList){
            id.add(surveyor.getId());
        }
        return id;
    }
    private Stage thisStage(){
        return (Stage) submitDelete.getScene().getWindow();
    }
}
