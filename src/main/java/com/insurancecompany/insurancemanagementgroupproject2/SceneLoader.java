package com.insurancecompany.insurancemanagementgroupproject2;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneLoader {
    public static void loadScene(String fxmlPath, Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneLoader.class.getResource(fxmlPath));
            Parent root = fxmlLoader.load();
            System.out.println(root);
            stage.setScene(new Scene(root, 800, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
