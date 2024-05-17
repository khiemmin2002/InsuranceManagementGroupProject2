package com.insurancecompany.insurancemanagementgroupproject2.view;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.HelloApplication;
import com.insurancecompany.insurancemanagementgroupproject2.controller.BcryptPassword;
import com.insurancecompany.insurancemanagementgroupproject2.controller.policyholder.PolicyHolderDependentController;
import com.insurancecompany.insurancemanagementgroupproject2.model.Dependent;
import com.insurancecompany.insurancemanagementgroupproject2.model.LoginData;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class PolicyHolderDependentHomePage {

    @FXML
    private TextField addAddressField;

    @FXML
    private MenuItem addDependent;

    @FXML
    private TextField addEmailField;

    @FXML
    private TextField addFullNameField;

    @FXML
    private TextField addPassWordField;

    @FXML
    private TextField addPhoneNumField;

    @FXML
    private TextField addUserNameField;


    @FXML
    private TableColumn<?, ?> addressCol;

    @FXML
    private TableView<Dependent> dependentTable;

    @FXML
    private Button clearInputButton;

    @FXML
    private Button clearAddInputBtn;

    @FXML
    private Button confirmAddBtn;

    @FXML
    private AnchorPane dependentPane;

    @FXML
    private TableColumn<?, ?> dependentIDCol;





    @FXML
    private TableColumn<?, ?> emailCol;


    @FXML
    private TableColumn<?, ?> fullNameCol;


    @FXML
    private TableColumn<?, ?> userNameCol;

    @FXML
    private MenuItem updateDependentMenu;


    @FXML
    private MenuItem logoutBtn;

    @FXML
    private TableColumn<?, ?> passwordCol;


    @FXML
    private TableColumn<?, ?> phoneNumberCol;


    @FXML
    private MenuItem exitBtn;

    @FXML
    private Button refreshBtn;

    @FXML
    private TextField inputUserName;;

    @FXML
    private MenuItem openClaimBtn;

    private DatabaseConnection databaseConnection;
    private Connection connection;

    private PolicyHolderDependentController controller = new PolicyHolderDependentController(databaseConnection, connection);



    private String userName;

    BcryptPassword bcryptPassword = new BcryptPassword();

    private void setUpDeleteColumn() {
        TableColumn<Dependent, Void> deleteCol = new TableColumn<>("Delete");
        deleteCol.setMinWidth(100);
        deleteCol.setCellFactory(param -> new TableCell<Dependent, Void>() {
            private final Button deleteBtn = new Button("Delete");

            {
                deleteBtn.setOnAction(event -> {
                    Dependent dependent = getTableView().getItems().get(getIndex());
                    if (dependent != null) {
                        deleteDependent(dependent);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteBtn);
                }
            }
        });
        dependentTable.getColumns().add(deleteCol);
    }
    private void deleteDependent(Dependent dependent) {
        System.out.println("Attempting to delete dependent with ID: " + dependent.getId());
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this dependent?", ButtonType.YES, ButtonType.NO);
        confirmationAlert.showAndWait().ifPresent(respone -> {
            if (respone == ButtonType.YES) {
                try {
                    controller.deleteDependent(dependent.getId());
                    dependentTable.getItems().remove(dependent);
                    showAlert(Alert.AlertType.INFORMATION, "Dependent deleted successfully");
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Failed to delete dependent");
                }
            }
        });
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message);
        alert.showAndWait();
    }

    @FXML
    private void initialize() {

        dependentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        dependentTable.widthProperty().addListener((observable, oldValue, newValue) -> {
            double tableWidth = dependentTable.getWidth();
            userNameCol.setPrefWidth(tableWidth * 0.15);
            fullNameCol.setPrefWidth(tableWidth * 0.2);
            dependentIDCol.setPrefWidth(tableWidth * 0.1);
            passwordCol.setPrefWidth(tableWidth * 0.15);
            phoneNumberCol.setPrefWidth(tableWidth * 0.15);
            emailCol.setPrefWidth(tableWidth * 0.15);

        });

        dependentTable.prefWidthProperty().bind(dependentPane.widthProperty());
        dependentTable.prefHeightProperty().bind(dependentPane.heightProperty().subtract(100));
        setUpDeleteColumn();
        this.userName = LoginData.usernameLogin;
        fetchDependentData();
    }

    @FXML
    void clearInputData(ActionEvent event) {
        addFullNameField.clear();
        addFullNameField.clear();
        addPassWordField.clear();
        addEmailField.clear();
        addPhoneNumField.clear();
        addAddressField.clear();

        fetchDependentData();
    }

    @FXML
    void exitProgram(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void logout(ActionEvent event) {
        try {
            MenuItem menuItem = (MenuItem) event.getSource();


            Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();
            Stage currentStage = (Stage) scene.getWindow();


            currentStage.close();


            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/login.fxml"));
            Parent root = loader.load();
            Stage loginStage = new Stage();
            Scene loginScene = new Scene(root);

            loginStage.setTitle("Login - Insurance Claim Management System");
            loginStage.setScene(loginScene);
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed to load login screen.");
        }
    }

    @FXML
    void findDependentUserName(ActionEvent event) {
        String dependentUserName = inputUserName.getText();
        try {
            ObservableList<Dependent> foundDependents = controller.findDependentUserName(dependentUserName);
            dependentTable.setItems(foundDependents);
            if (foundDependents.isEmpty()) {
                System.out.println("No dependents found.");
                showAlert(Alert.AlertType.INFORMATION, "No dependents found with that username.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error retrieving data: " + e.getMessage());
        }
    }


    @FXML
    void clearAddFields(ActionEvent event) {
        addUserNameField.clear();
        addAddressField.clear();
        addEmailField.clear();
        addFullNameField.clear();
        addPhoneNumField.clear();
        addPassWordField.clear();
        addAddressField.clear();
    }




    @FXML
    void openClaimModal(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) dependentTable.getScene().getWindow();
        currentStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/policy-holder-claim.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Dependent");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }



    @FXML
    void refreshData(ActionEvent event)  {
        fetchDependentData();
    }

    @FXML
    void openUpdateDependentModal(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/policy-holder-update-dependent.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Update Dependent");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }


    @FXML
    void confirmAddDependent(ActionEvent event) {
        try {
            String dependentId = generatedRandomUserId();

            String userName = addUserNameField.getText().trim();
            String fullName = addFullNameField.getText().trim();
            String passwordPlainText = addPassWordField.getText().trim();
            String phoneNumber = addPhoneNumField.getText().trim();
            String email = addEmailField.getText().trim();

            String address = addAddressField.getText().trim();

            int roleId = 6;

            if (userName.isEmpty() || fullName.isEmpty() || passwordPlainText.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all fields");
                alert.showAndWait();
            }
            String passWordHashed = bcryptPassword.hashBcryptPassword(passwordPlainText);
            controller.addDependent(dependentId, fullName, userName, passWordHashed, email, phoneNumber, address, roleId);
            fetchDependentData();

            showAlert(Alert.AlertType.INFORMATION, "Dependent added successfully");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Failed to add dependent: " + e.getMessage());
        }

    }

    private void fetchDependentData()  {
        try {
            ObservableList<Dependent> dependentData = controller.fetchDependents();
            dependentTable.setItems(dependentData);
            dependentIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
            fullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
            phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
            addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));


            dependentTable.setItems(dependentData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    private String generatedRandomUserId() {
        StringBuilder dependentID = new StringBuilder("C");
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            dependentID.append(random.nextInt(10));
        }
        return dependentID.toString();
    }

}

