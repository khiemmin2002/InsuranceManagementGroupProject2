package com.insurancecompany.insurancemanagementgroupproject2.controller;

import com.insurancecompany.insurancemanagementgroupproject2.model.LoginData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PolicyOwnerHomepageController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button logoutButton;



    @FXML
    private void home(MouseEvent event) {
        System.out.println("Home");
        borderPane.setCenter(anchorPane);
    }

    @FXML
    private void myClaim(MouseEvent event) {
        System.out.println("My Claim");
        loadPage("policy-owner-my-claim");
    }

    @FXML
    private void myPolicyHolder(MouseEvent event) {
        System.out.println("My Policy Holder");
        loadPage("policy-owner-my-policy-holder");
    }

    @FXML
    private void myDependent(MouseEvent event) {
        System.out.println("My Dependent");
        loadPage("policy-owner-my-dependent");
    }


    @FXML
    private void myInsuranceCard(MouseEvent event) {
        System.out.println("My Insurance Card");
        loadPage("policy-owner-my-insurance-card");
    }

    @FXML
    private void myProfile(MouseEvent event) {
        System.out.println("My Profile");
        loadPage("policy-owner-my-profile");
    }

    @FXML
    private void logoutButtonOnAction(MouseEvent event) {
        LoginData.logOut(logoutButton);
    }

    private void loadPage(String page) {
        try {
            URL url = getClass().getResource("/com/insurancecompany/insurancemanagementgroupproject2/fxml/" + page + ".fxml");
            if (url == null) {
                throw new IOException("Cannot load resource: /fxml/" + page + ".fxml");
            }
            Parent root = FXMLLoader.load(url);
            borderPane.setCenter(root);
        } catch (Exception e) {
            Logger.getLogger(DependentController.class.getName()).log(Level.SEVERE, "Failed to load FXML", e);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
