package com.insurancecompany.insurancemanagementgroupproject2.model;

import com.insurancecompany.insurancemanagementgroupproject2.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginData {
    public static String usernameLogin;
    public static int roleId;

    public static void logOut(Button logoutButton){
        usernameLogin = "";
        roleId = 0;
        System.out.println("Delete static variable from LoginData class!");
        logOutFXML(logoutButton);
    }
    @FXML
    public static void logOutFXML(Button logoutButton){
        Stage currentStage = (Stage) logoutButton.getScene().getWindow();
        currentStage.setTitle("Surveyors Portal");
        SceneLoader.loadSceneWithInput("fxml/login.fxml", currentStage,400,400);
    }
}