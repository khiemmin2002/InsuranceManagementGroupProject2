package com.insurancecompany.insurancemanagementgroupproject2.view;
/**
 * @author team 5
 */
import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.SceneLoader;
import com.insurancecompany.insurancemanagementgroupproject2.controller.BcryptPassword;
import com.insurancecompany.insurancemanagementgroupproject2.controller.SurveyorController;
import com.insurancecompany.insurancemanagementgroupproject2.controller.ValidateInput;
import com.insurancecompany.insurancemanagementgroupproject2.view.ManagerHomePage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateSurveyorForm {
    @FXML
    public TextField full_name;
    @FXML
    public PasswordField password;
    @FXML
    public TextField username;
    @FXML
    public TextField email;
    @FXML
    public TextField phone_number;
    @FXML
    public TextField address;
    @FXML
    public Button submitSurveyor;
    @FXML
    private Label errorLabel;
    private SurveyorController surveyorController;
    private String id;
    private BcryptPassword bcryptPassword;
    /*
     *  Initialize the pages upon opening pages, create controller and local variable
     */
    @FXML
    private void initialize(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        surveyorController = new SurveyorController(databaseConnection,databaseConnection.getConnection());
        // Set event handler for button on click action
        submitSurveyor.setOnAction(ActionEvent -> createSurveyor());
        ManagerHomePage managerHomePage = new ManagerHomePage();
        id = managerHomePage.createSurveyorID();
        bcryptPassword = new BcryptPassword();
    }
    //Create new validate input class object
    ValidateInput validateInput = new ValidateInput();

    /*
     *  Method to check field inside text field then call controller to create new surveyor
     */
    private void createSurveyor(){
        // Validate input not empty
        if (full_name.getText().isEmpty() || username.getText().isEmpty() || password.getText().isEmpty() || email.getText().isEmpty()
                || phone_number.getText().isEmpty() || address.getText().isEmpty()) {
            errorLabel.setText("Please fill in all fields");
            return;
        }
        // Validate email
        if (!validateInput.isValidEmail(email.getText())) {
            errorLabel.setText("Invalid email format");
            return;
        }
        // Validate phone number
        if (!validateInput.isValidPhoneNumber(phone_number.getText())) {
            errorLabel.setText("Invalid phone number format");
            return;
        }
        // Call controller to create
        surveyorController.createNewSurveyor(
                id,full_name.getText(),username.getText(),bcryptPassword.hashBcryptPassword(password.getText()),email.getText(),
                phone_number.getText(),address.getText());
        // Route back
        System.out.println("Created surveyor " + full_name.getText() + " successfully!");
        SceneLoader.loadSceneWithInput("fxml/manager-homepage.fxml",thisStage(),944,709);
    }
    //Method to get this stage context
    private Stage thisStage(){
        return (Stage) submitSurveyor.getScene().getWindow();
    }
}
