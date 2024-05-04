package com.example.icms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePageTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("policy-holder-homepage.fxml"));

        primaryStage.setTitle("ICMS Application");
        primaryStage.setScene(new Scene(root, 800, 600)); // Adjust width and height as needed
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
