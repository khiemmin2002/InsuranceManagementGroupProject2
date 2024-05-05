package com.insurancecompany.insurancemanagementgroupproject2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PolicyHolderApplicationTest extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/policy-holder-homepage.fxml"));

        // Set up the scene
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Policy Holder Homepage");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
