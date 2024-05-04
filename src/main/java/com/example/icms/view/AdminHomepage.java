package com.example.icms.view;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminHomepage extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Admin Home Page");

        Button manageUsersButton = new Button("Manage Users");
        Button manageClaimsButton = new Button("View Claims");
        Button manageProvidersButton = new Button("Manage Providers");
        Button statisticsButton = new Button("View Statistics");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.getChildren().addAll(manageUsersButton, manageClaimsButton, manageProvidersButton, statisticsButton);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
