package com.insurancecompany.insurancemanagementgroupproject2;
/**
 * @author team 5
 */
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneLoader {
    // Method to load a screen with predefined value
    public static void loadScene(String fxmlPath, Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneLoader.class.getResource(fxmlPath));
            Parent root = fxmlLoader.load();
            stage.setScene(new Scene(root, 800, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Method to load a screen with self-defined value
    public static void loadSceneWithInput(String fxmlPath, Stage stage, double width, double height) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneLoader.class.getResource(fxmlPath));
            Parent root = fxmlLoader.load();
            stage.setScene(new Scene(root, width, height));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
