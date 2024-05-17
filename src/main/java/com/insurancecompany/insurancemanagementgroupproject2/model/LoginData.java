package com.insurancecompany.insurancemanagementgroupproject2.model;
/**
 * @author team 5
 */
import com.insurancecompany.insurancemanagementgroupproject2.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginData {
    public static String usernameLogin;
    public static int roleId;
    // Static method to bind logout on logout button
    public static void logOut(Button logoutButton){
        usernameLogin = "";
        roleId = -1;
        System.out.println("Delete static variable from LoginData class!");
        logOutFXML(logoutButton);
    }
    // Static method to perform logout, containing JAVAFX control
    @FXML
    public static void logOutFXML(Button logoutButton){
        Stage currentStage = (Stage) logoutButton.getScene().getWindow();
        currentStage.setTitle("Insurance Claim Management System");
        SceneLoader.loadSceneWithInput("fxml/login.fxml", currentStage,400,400);
    }
}