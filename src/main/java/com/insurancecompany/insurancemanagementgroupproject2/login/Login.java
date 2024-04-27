package com.insurancecompany.insurancemanagementgroupproject2.login;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Login {
    public void start (Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Get the css file
        URL cssLogin = Login.class.getResource("login.css");
        if (cssLogin != null) {
            String css = cssLogin.toExternalForm();
            scene.getStylesheets().add(css);
        } else {
            System.err.println("Could not find the CSS file.");
        }

        // Disable resizing
        stage.setResizable(false);

        stage.setTitle("Health Harbor Insurance Login");
        stage.setScene(scene);
        stage.show();
    }
}
