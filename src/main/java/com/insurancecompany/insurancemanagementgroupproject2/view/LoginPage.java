package com.insurancecompany.insurancemanagementgroupproject2.view;

import com.insurancecompany.insurancemanagementgroupproject2.SceneLoader;
import com.insurancecompany.insurancemanagementgroupproject2.controller.LoginController;
import com.insurancecompany.insurancemanagementgroupproject2.model.LoginData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginPage {
    @FXML
    private Button cancelButton;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    LoginController loginController = new LoginController();
    @FXML
    private void loginButtonOnAction() {
        if (!usernameTextField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            int roleIdLogIn = loginController.validateLogin(usernameTextField.getText(), passwordField.getText());
            System.out.println(roleIdLogIn);
            switch (roleIdLogIn) {
                case 1 -> {
                    LoginData.usernameLogin = usernameTextField.getText();
                    LoginData.roleId = roleIdLogIn;
                    loadAdminHomePage();
                    System.out.println(LoginData.usernameLogin);
                }
                case 2 -> {
                    LoginData.usernameLogin = usernameTextField.getText();
                    LoginData.roleId = roleIdLogIn;
                    loadSurveyorHomePage();
                    System.out.println(LoginData.usernameLogin);
                }
                case 3 -> {
                    LoginData.usernameLogin = usernameTextField.getText();
                    LoginData.roleId = roleIdLogIn;
                    loadManagerHomePage();
                    System.out.println(LoginData.usernameLogin);
                }
                case 4 -> {
                    LoginData.usernameLogin = usernameTextField.getText();
                    LoginData.roleId = roleIdLogIn;
                    loadPolicyOwnerHomePage();
                    System.out.println(LoginData.usernameLogin);
                }
                case 5 -> {
                    LoginData.usernameLogin = usernameTextField.getText();
                    LoginData.roleId = roleIdLogIn;
                    loadPolicyHolderHomePage();
                    System.out.println(LoginData.usernameLogin);
                }
                case 6 -> {
                    LoginData.usernameLogin = usernameTextField.getText();
                    LoginData.roleId = roleIdLogIn;
                    loadDependentHomePage();
                    System.out.println(LoginData.usernameLogin);
                }
                default -> {
                    loginMessageLabel.setText("Unknown role!");
                }
            }
        } else if (usernameTextField.getText().isEmpty() || passwordField.getText().isEmpty()){
            loginMessageLabel.setText("Please enter your username and password!");
        } else {
            loginMessageLabel.setText("Invalid username or password!");
        }
    }

    @FXML
    private void cancelButtonOnAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void loadPolicyHolderHomePage() {
        Stage currentStage = (Stage) usernameTextField.getScene().getWindow();
        SceneLoader.loadScene("fxml/policy-holder-claim.fxml", currentStage);
    }

    private void loadAdminHomePage() {
        Stage currentStage = (Stage) usernameTextField.getScene().getWindow();
        currentStage.setTitle("Admin Portal");
        SceneLoader.loadSceneWithInput("fxml/admin-homepage.fxml", currentStage,900,600);
    }

    private void loadSurveyorHomePage() {
        Stage currentStage = (Stage) usernameTextField.getScene().getWindow();
        SceneLoader.loadSceneWithInput("fxml/surveyor-homepage.fxml", currentStage, 957,461);
    }
    public void loadManagerHomePage() {
        Stage currentStage = (Stage) usernameTextField.getScene().getWindow();
        SceneLoader.loadSceneWithInput("fxml/manager-homepage.fxml", currentStage,944,709);
    }

    private void loadDependentHomePage() {
        Stage currentStage = (Stage) usernameTextField.getScene().getWindow();
        SceneLoader.loadScene("fxml/dependent-homepage.fxml", currentStage);
    }

    private void loadPolicyOwnerHomePage() {
        Stage currentStage = (Stage) usernameTextField.getScene().getWindow();
        SceneLoader.loadScene("fxml/policy-owner-homepage.fxml", currentStage);
    }
}
