package com.insurancecompany.insurancemanagementgroupproject2.view;
/**
 * @author team 5
 */
import com.insurancecompany.insurancemanagementgroupproject2.controller.AdminController;
import com.insurancecompany.insurancemanagementgroupproject2.controller.ClaimController;
import com.insurancecompany.insurancemanagementgroupproject2.model.Claim;
import com.insurancecompany.insurancemanagementgroupproject2.model.LoginData;
import com.insurancecompany.insurancemanagementgroupproject2.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class DependentHomepage implements Initializable {
    // Dependent My Claim
    @FXML
    private TableView<Claim> dependentClaimTable;

    @FXML
    private TableColumn<Claim, Double> claimAmount;

    @FXML
    private TableColumn<Claim, String> claimBankName;

    @FXML
    private TableColumn<Claim, String> claimBankNum;

    @FXML
    private TableColumn<Claim, String> claimBankUserName;

    @FXML
    private TableColumn<Claim, String> claimCardNum;

    @FXML
    private TableColumn<Claim, Date> claimDate;

    @FXML
    private TableColumn<Claim, Date> claimExamDate;

    @FXML
    private TableColumn<Claim, String> claimID;

    @FXML
    private TableColumn<Claim, String> claimInsuredPerson;

    @FXML
    private TableColumn<Claim, String> claimStatus;

    @FXML
    private AnchorPane claimInformationOfDependent;

    @FXML
    private Label dependentAddress;

    @FXML
    private Label dependentEmail;

    @FXML
    private Label dependentFullName;

    @FXML
    private Label dependentIDField;

    @FXML
    private Label dependentPhoneNum;

    @FXML
    private Label dependentUsername;

    @FXML
    private Text homeDependent;

    @FXML
    private Button logoutButton;

    @FXML
    private Button navClaimBtnDependent;

    @FXML
    private Button navHomeBtnDependent;

    @FXML
    private Button navMyProfileBtnDependent;

    @FXML
    private AnchorPane profileInformationDependent;


    public void switchDashboard(ActionEvent event){
        if (event.getSource() == navHomeBtnDependent) {
            showHomeDependent();
        } else if (event.getSource() == navClaimBtnDependent) {
            dependentClaimTable.refresh();
            showClaimInformationOfDependent();
        }else if(event.getSource() == navMyProfileBtnDependent){
            showProfileInformationDependent();
        }
    }
    private void showHomeDependent(){
        homeDependent.setVisible(true);
        profileInformationDependent.setVisible(false);
        claimInformationOfDependent.setVisible(false);
    }

    private void showProfileInformationDependent(){
        homeDependent.setVisible(false);
        profileInformationDependent.setVisible(true);
        claimInformationOfDependent.setVisible(false);
    }

    private void showClaimInformationOfDependent(){
        homeDependent.setVisible(false);
        profileInformationDependent.setVisible(false);
        claimInformationOfDependent.setVisible(true);
    }

    @FXML
    private void logoutButtonOnAction(MouseEvent event) {
        LoginData.logOut(logoutButton);
    }

//    private void loadPage(String page) {
//        try {
//            URL url = getClass().getResource("/com/insurancecompany/insurancemanagementgroupproject2/fxml/" + page + ".fxml");
//            if (url == null) {
//                throw new IOException("Cannot load resource: /fxml/" + page + ".fxml");
//            }
//            Parent root = FXMLLoader.load(url);
//            borderPane.setCenter(root);
//        } catch (Exception e) {
//            Logger.getLogger(DependentHomepage.class.getName()).log(Level.SEVERE, "Failed to load FXML", e);
//        }
//    }

    ObservableList<Claim> claimData = FXCollections.observableArrayList();
    AdminController adminController = new AdminController();
    ClaimController claimController = new ClaimController();

    public void displayProfileDependent(){
        User userData = adminController.getProfileDashboardInformation(LoginData.usernameLogin, LoginData.roleId);
        if (userData != null) {
            dependentIDField.setText(userData.getId());
            dependentFullName.setText(userData.getFullName());
            dependentUsername.setText(userData.getUserName());
            dependentEmail.setText(userData.getEmail());
            dependentPhoneNum.setText(userData.getPhoneNumber());
            dependentAddress.setText(userData.getAddress());
        } else {
            System.out.println("Failed to display profile dashboard information.");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        displayProfileDependent();
        claimData.addAll(claimController.getClaimsOfInsuredPerson());
        claimID.setCellValueFactory(new PropertyValueFactory<>("id"));
        claimCardNum.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
        claimDate.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
        claimExamDate.setCellValueFactory(new PropertyValueFactory<>("examDate"));
        claimAmount.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        claimStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        claimBankName.setCellValueFactory(new PropertyValueFactory<>("bankName"));
        claimBankUserName.setCellValueFactory(new PropertyValueFactory<>("bankUserName"));
        claimBankNum.setCellValueFactory(new PropertyValueFactory<>("bankNumber"));
        claimInsuredPerson.setCellValueFactory(cellData -> {
            String insuredPerson = cellData.getValue().getInsuredPerson();
            String name = adminController.getNameForUser(insuredPerson);
            return new javafx.beans.property.SimpleStringProperty(name);
        });
        dependentClaimTable.setItems(claimData);
    }
}
