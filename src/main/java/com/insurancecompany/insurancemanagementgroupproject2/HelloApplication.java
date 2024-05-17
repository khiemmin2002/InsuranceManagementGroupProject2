package com.insurancecompany.insurancemanagementgroupproject2;
/**
 * @author team 5
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        stage.setTitle("Insurance Claims Management System");
        stage.setScene(scene);
        stage.show();
    }
    // Starting point of the application
    public static void main(String[] args) {
        launch();
    }
}