package com.insurancecompany.insurancemanagementgroupproject2.controller;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.SceneLoader;
import com.insurancecompany.insurancemanagementgroupproject2.model.Claim;
import com.insurancecompany.insurancemanagementgroupproject2.model.LoginData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DependentController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button logoutButton;



    // Dependent My Policy Holder



    @FXML
    private void home(MouseEvent event) {
        System.out.println("Home");
        borderPane.setCenter(anchorPane);
    }

    @FXML
    private void myClaim(MouseEvent event) {
        System.out.println("My Claim");
        loadPage("dependent-my-claim");
    }

    @FXML
    private void myPolicyHolder(MouseEvent event) {
        System.out.println("My Policy Holder");
        loadPage("dependent-my-policy-holder");
    }

    @FXML
    private void myPolicyOwner(MouseEvent event) {
        System.out.println("My Policy Owner");
        loadPage("dependent-my-policy-owner");
    }

    @FXML
    private void myInsuranceCard(MouseEvent event) {
        System.out.println("My Insurance Card");
        loadPage("dependent-my-insurance-card");
    }

    @FXML
    private void myProfile(MouseEvent event) {
        System.out.println("My Profile");
        loadPage("dependent-my-profile");
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
