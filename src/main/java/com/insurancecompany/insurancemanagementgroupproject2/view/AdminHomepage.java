package com.insurancecompany.insurancemanagementgroupproject2.view;
/**
 * @author team 5
 */
import com.insurancecompany.insurancemanagementgroupproject2.controller.AdminController;
import com.insurancecompany.insurancemanagementgroupproject2.controller.BcryptPassword;
import com.insurancecompany.insurancemanagementgroupproject2.controller.ClaimController;
import com.insurancecompany.insurancemanagementgroupproject2.controller.ValidateInput;
import com.insurancecompany.insurancemanagementgroupproject2.model.*;
import com.insurancecompany.insurancemanagementgroupproject2.view.ManagerHomePage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.net.URL;
import java.text.ParseException;
import java.util.*;

public class AdminHomepage implements Initializable {
    //Button Define
    @FXML
    private Button navMainDashboardBtn;
    @FXML
    private Button navUsersBtn;
    @FXML
    private Button navInsuranceCardsBtn;
    @FXML
    private Button navClaimsBtn;
    @FXML
    private Button navProfileBtn;
    @FXML
    private Button btnLogOut;
    //AnchorPane define
    @FXML
    private AnchorPane mainDashboard;
    @FXML
    private AnchorPane userInformationDashboard;
    @FXML
    private AnchorPane insuranceCardDashboard;
    @FXML
    private AnchorPane claimDashboard;
    @FXML
    private AnchorPane profileDashboard;
    //navbar define
    @FXML
    private Label navAdminid;
    @FXML
    private Label navUsername;
    @FXML
    private Label navFullname;
    @FXML
    private Label topUsername;
    //main dashboard define
    @FXML
    private Label totalProviders;
    @FXML
    private Label totalCustomers;
    @FXML
    private Label totalInsuranceCards;
    @FXML
    private Label totalClaims;
    @FXML
    private TableView<Claim> mainDashboardTableView;

    @FXML
    private TableColumn<Claim, String> mainDashboardColClaimId;

    @FXML
    private TableColumn<Claim, String> mainDashboardColInsuredPerson;

    @FXML
    private TableColumn<Claim, Date> mainDashboardColClaimDate;

    @FXML
    private TableColumn<Claim, Float> mainDashboardColAmount;

    @FXML
    private TableColumn<Claim, String> mainDashboardColStatus;
    private ObservableList<Claim> claimObservableList = FXCollections.observableArrayList();

    //User dashboard
    @FXML
    private TableView<User> userTableView;
    @FXML
    private TableColumn<User, String> userColId;
    @FXML
    private TableColumn<User, String> userColFullname;
    @FXML
    private TableColumn<User, String> userColUsername;
    @FXML
    private TableColumn<User, String> userColPassword;
    @FXML
    private TableColumn<User, String> userColEmail;
    @FXML
    private TableColumn<User, String> userColPhonenumber;
    @FXML
    private TableColumn<User, String> userColAddress;
    @FXML
    private TableColumn<User, Integer> userColRole;
    // Observable list
    private ObservableList<User> userObservableList = FXCollections.observableArrayList();

    @FXML
    private AnchorPane editFormUserInformation;
    @FXML
    private TextField editFormUserId;
    @FXML
    private TextField editFormUserFullName;
    @FXML
    private TextField editFormUserName;
    @FXML
    private TextField editFormUserPassword;
    @FXML
    private TextField editFormUserEmail;
    @FXML
    private TextField editFormUserPhoneNumber;
    @FXML
    private TextArea editFormUserAddress;
    @FXML
    private TextField editFormUserRole;

    @FXML
    private Button editFormUserConfirmBtn;
    @FXML
    private Button editFormUserDeleteBtn;
    //Claim dashboard
    @FXML
    private TableView<Claim> claimTableView;

    @FXML
    private TableColumn<Claim, String> claimColClaimId;

    @FXML
    private TableColumn<Claim, String> claimColInsuredPerson;

    @FXML
    private TableColumn<Claim, String> claimColCardNumber;

    @FXML
    private TableColumn<Claim, String> claimColClaimDate;

    @FXML
    private TableColumn<Claim, Float> claimColAmount;

    @FXML
    private TableColumn<Claim, String> claimColStatus;

    @FXML
    private TableColumn<Claim, Void> claimColAction;
    @FXML
    private AnchorPane editFormClaimInformation;
    @FXML
    private TextField editFormClaimId;
    @FXML
    private TextField editFormClaimInsuredPerson;
    @FXML
    private TextField editFormClaimCardNumber;
    @FXML
    private TextField editFormClaimDate;
    @FXML
    private TextField editFormClaimExam;
    @FXML
    private TextField editFormClaimAmount;
    @FXML
    private TextField editFormClaimStatus;
    @FXML
    private TextField editFormClaimBankName;
    @FXML
    private TextField editFormClaimBankUser;
    @FXML
    private TextField editFormClaimBankNumber;
    @FXML
    private TextField editFormClaimTotalDocument;
    @FXML
    private Button editFormClaimConfirmBtn;
    @FXML
    private Button editFormClaimCancelBtn;

    //Insurance Card Dashboard
    @FXML
    private TableView<InsuranceCard> insuranceCardTableView;
    @FXML
    private TableColumn<InsuranceCard, String> insuranceCardColCardNumber;
    @FXML
    private TableColumn<InsuranceCard, Date> insuranceCardColExpDate;
    @FXML
    private TableColumn<InsuranceCard, String> insuranceCardColCardHolder;
    @FXML
    private TableColumn<InsuranceCard, String> insuranceCardColPolicyOwner;
    @FXML
    private TableColumn<InsuranceCard, Void> insuranceCardColAction;
    private ObservableList<InsuranceCard> insuranceCardObservableList = FXCollections.observableArrayList();
    @FXML
    private AnchorPane editFormInsuranceCardInformation;
    @FXML
    private TextField editFormInsuranceCardNumber;
    @FXML
    private TextField editFormInsuranceExpDate;
    @FXML
    private TextField editFormInsuranceCardHolderId;
    @FXML
    private TextField editFormInsuranceCardHolderName;
    @FXML
    private TextField editFormInsurancePolicyOwnerId;
    @FXML
    private TextField editFormInsurancePolicyOwnerName;
    @FXML
    private Button editFormInsuranceConfirmBtn;
    @FXML
    private Button editFormInsuranceCancelBtn;

    //Profile Dashboard
    @FXML
    private Label profileDashboardId;
    @FXML
    private Label profileDashboardUsername;
    @FXML
    private Label profileDashboardEmail;
    @FXML
    private Label profileDashboardFullname;
    @FXML
    private TextField editProfileId;
    @FXML
    private TextField editProfileFullName;
    @FXML
    private TextField editProfileUserName;
    @FXML
    private TextField editProfilePassword;
    @FXML
    private TextField editProfileEmail;
    @FXML
    private TextField editProfilePhoneNumber;
    @FXML
    private TextArea editProfileAddress;
    @FXML
    private TextField editProfileRole;
    @FXML
    private Button editProfileConfirmBtn;
    @FXML
    private AnchorPane addNewUserFormAdmin;
    @FXML
    private TextField editFormCreateUserId;
    @FXML
    private TextField editFormCreateUserFullName;
    @FXML
    private TextField editFormCreateUserName;
    @FXML
    private TextField editFormCreateUserPassword;
    @FXML
    private TextField editFormCreateUserEmail;
    @FXML
    private TextField editFormCreateUserPhone;
    @FXML
    private TextArea editFormCreateUserAddress;
    @FXML
    private ComboBox<String> editFormCreateUserRoleId;
    @FXML
    private Button editFormCreateUserConfirm;
    @FXML
    private Button editFormCreateUserCancel;
    private ObservableList<Role> roleObservableList = FXCollections.observableArrayList();
    AdminController adminController = new AdminController();
    ClaimController claimController = new ClaimController();

    //User Dashboard functions
    @FXML
    private void editFormCreateUserCancelOnAction(){
        addNewUserFormAdmin.setVisible(false);
        clearNewUserFormFields();
    }
    @FXML
    private void btnAddNewUserAdminOnAction(){
        addNewUserFormAdmin.setVisible(true);
    }
    private void editFormCreateUserRoleIdOnAction() {
        if (roleObservableList != null && !roleObservableList.isEmpty()) {
            for (Role role : roleObservableList) {
                if (!Objects.equals(role.getId(),1)) {
                    editFormCreateUserRoleId.getItems().add(role.getRoleName());
                }
            }
        } else {
            System.out.println("Role Observable List is empty or null.");
        }
    }
    // Create new instance of validate input
    ValidateInput validateInput = new ValidateInput();
    BcryptPassword bcryptPassword = new BcryptPassword();
    // Button action method handler
    @FXML
    private void addNewUserFormAdminBtnOnAction() {
        boolean isUsernameUnique = validateInput.isUserNameUnique(userObservableList, editFormCreateUserName.getText());
        boolean isValidEmail = validateInput.isValidEmail(editFormCreateUserEmail.getText());
        boolean isValidPhoneNumber = validateInput.isValidPhoneNumber(editFormCreateUserPhone.getText());
        // Validate input
        if (isUsernameUnique) {
            if (!isValidEmail || !isValidPhoneNumber) {
                System.out.println("Please check your Email or Phone Number again");
            } else {
                Role selectedRole = null;
                String selectedRoleName = editFormCreateUserRoleId.getValue();
                for (Role role : roleObservableList) {
                    if (Objects.equals(role.getRoleName().toLowerCase(), selectedRoleName.toLowerCase())) {
                        selectedRole = role;
                        break;
                    }
                }
                // validate roles
                if (selectedRole != null) {
                    ManagerHomePage managerHomePage = new ManagerHomePage();
                    User newUser = new User();
                    if (selectedRole.getId() == 2 || selectedRole.getId() == 3) {
                        newUser.setId(managerHomePage.createSurveyorID());
                    } else if (selectedRole.getId() != 1) {
                        newUser.setId(managerHomePage.createCustomerID());
                    }
                    newUser.setUserName(editFormCreateUserName.getText());
                    newUser.setFullName(editFormCreateUserFullName.getText());
                    newUser.setPassword(bcryptPassword.hashBcryptPassword(editFormCreateUserPassword.getText()));
                    newUser.setEmail(editFormCreateUserEmail.getText());
                    newUser.setPhoneNumber(editFormCreateUserPhone.getText());
                    newUser.setAddress(editFormCreateUserAddress.getText());
                    newUser.setRoleId(selectedRole.getId());
                    boolean isSuccess = adminController.addUser(newUser);
                    if (isSuccess) {
                        userObservableList.add(newUser);
                        clearNewUserFormFields();
                    } else {
                        System.out.println("Failed to add new user.");
                    }
                } else {
                    System.out.println("Error: Please select a role.");
                }
            }
        } else {
            System.out.println("Error: Username already exists.");
        }
    }
    // Method to clear form fields
    private void clearNewUserFormFields() {
        editFormCreateUserId.clear();
        editFormCreateUserName.clear();
        editFormCreateUserFullName.clear();
        editFormCreateUserPassword.clear();
        editFormCreateUserEmail.clear();
        editFormCreateUserPhone.clear();
        editFormCreateUserAddress.clear();
    }
    // Method to control form button
    @FXML
    private void editFormUserConfirmBtnOnAction() {
        String userId = editFormUserId.getText();
        String fullName = editFormUserFullName.getText();
        String email = editFormUserEmail.getText();
        String phoneNumber = editFormUserPhoneNumber.getText();
        String address = editFormUserAddress.getText();

        User user = adminController.getUserById(userId);
        boolean isSuccess;
        if (editFormUserPassword.getText().equalsIgnoreCase(user.getPassword())) {
            isSuccess = adminController.updateUserWithoutPassword(userId, fullName, email, phoneNumber, address);
        } else {
            String bcryptUpdatePassword = bcryptPassword.hashBcryptPassword(editFormUserPassword.getText());
            isSuccess = adminController.updateUser(userId, fullName, bcryptUpdatePassword, email, phoneNumber, address);
        }

        if (isSuccess) {
            editFormUserFullName.setText(fullName);
            editFormUserEmail.setText(email);
            editFormUserPhoneNumber.setText(phoneNumber);
            editFormUserAddress.setText(address);

            for (User selectedUser : userObservableList) {
                if (Objects.equals(userId, selectedUser.getId())) {
                    selectedUser.setFullName(fullName);
                    selectedUser.setEmail(email);
                    selectedUser.setPhoneNumber(phoneNumber);
                    selectedUser.setAddress(address);
                    if (!editFormUserPassword.getText().isEmpty()) {
                        selectedUser.setPassword(editFormUserPassword.getText());
                    }
                    break;
                }
            }
        } else {
            System.out.println("isSuccess: " + false);
        }
    }


    // Method to control form button
    @FXML
    private void editFormUserDeleteBtnOnAction() {
        String userId = editFormUserId.getText();
        boolean isDeleted = adminController.deleteUser(userId);
        if(isDeleted){
            userObservableList.removeIf(user -> Objects.equals(userId, user.getId()));
            editFormUserInformation.setVisible(false);
            userTableView.refresh();
            System.out.println("User deleted successfully!");
        } else {
            System.out.println("Failed to delete user");
        }
    }
    // Method to select row of users
    @FXML
    private void selectUserRow() {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            editFormUserId.setText(selectedUser.getId());
            editFormUserFullName.setText(selectedUser.getFullName());
            editFormUserName.setText(selectedUser.getUserName());
            editFormUserPassword.setText(selectedUser.getPassword());
            editFormUserEmail.setText(selectedUser.getEmail());
            editFormUserPhoneNumber.setText(selectedUser.getPhoneNumber());
            editFormUserAddress.setText(selectedUser.getAddress());
            editFormUserRole.setText(adminController.getRoleName(selectedUser.getRoleId()));
            editFormUserInformation.setVisible(true);
        }
    }
    // Method to set table view value
    private void displayUsersDashboardTableView() {
        userColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        userColFullname.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        userColUsername.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userColPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        userColEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        userColPhonenumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        userColAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        userColRole.setCellValueFactory(new PropertyValueFactory<>("roleId"));
        userTableView.setItems(userObservableList);
    }
    //Insurance Card function
    @FXML
    void editFormInsuranceCancelBtnOnAction() {
        editFormInsuranceCardInformation.setVisible(false);
    }
    // Method to set form value
    @FXML
    private void selectInsuranceCardRow(InsuranceCard selectedInsuranceCard) {
        try {
            if (selectedInsuranceCard != null) {
                editFormInsuranceCardNumber.setText(selectedInsuranceCard.getCardNumber());
                editFormInsuranceExpDate.setText(String.valueOf(selectedInsuranceCard.getExpirationDate()));
                editFormInsuranceCardHolderId.setText(selectedInsuranceCard.getCardHolderId());
                editFormInsuranceCardHolderName.setText(adminController.getNameForUser(selectedInsuranceCard.getCardHolderId()));
                editFormInsurancePolicyOwnerId.setText(selectedInsuranceCard.getPolicyOwnerId());
                editFormInsurancePolicyOwnerName.setText(adminController.getNameForUser(selectedInsuranceCard.getPolicyOwnerId()));
                editFormInsuranceCardInformation.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Method to set cell value for table view
    private void displayInsuranceCardDashboardTableView() {
        insuranceCardColCardNumber.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
        insuranceCardColExpDate.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
        insuranceCardColCardHolder.setCellValueFactory(cellData -> {
            String cardHolderId = cellData.getValue().getCardHolderId();
            String name = adminController.getNameForUser(cardHolderId);
            return new SimpleStringProperty(name);
        });
        insuranceCardColPolicyOwner.setCellValueFactory(cellData -> {
            String policyOwnerId = cellData.getValue().getPolicyOwnerId();
            String name = adminController.getNameForUser(policyOwnerId);
            return new SimpleStringProperty(name);
        });
        insuranceCardColAction.setCellFactory(createInsuranceCardActionCellFactory());
        insuranceCardTableView.setItems(insuranceCardObservableList);
    }
    // Method to return call back
    private Callback<TableColumn<InsuranceCard, Void>, TableCell<InsuranceCard, Void>> createInsuranceCardActionCellFactory() {
        return new Callback<TableColumn<InsuranceCard, Void>, TableCell<InsuranceCard, Void>>() {
            @Override
            public TableCell<InsuranceCard, Void> call(TableColumn<InsuranceCard, Void> param) {
                return new TableCell<InsuranceCard, Void>() {
                    final Button editButton = new Button("Edit");
                    final Button deleteButton = new Button("Delete");

                    {
                        editButton.setOnAction(event -> {
                            InsuranceCard selectedInsuranceCard = getTableView().getItems().get(getIndex());
                            selectInsuranceCardRow(selectedInsuranceCard);
                            editFormInsuranceConfirmBtn.setOnAction(event1 -> {
                                try {
                                    boolean isSuccess = adminController.updateInsuranceCardInformation(editFormInsuranceCardNumber.getText(), editFormInsuranceExpDate.getText());
                                    if (isSuccess) {
                                        selectedInsuranceCard.setExpirationDate(java.sql.Date.valueOf(editFormInsuranceExpDate.getText()));
                                        insuranceCardTableView.refresh();
                                        editFormInsuranceCardInformation.setVisible(false);
                                    } else {
                                        System.out.println("isSuccess: " + false);
                                    }
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                            System.out.println(selectedInsuranceCard.getCardNumber());
                        });

                        deleteButton.setOnAction(event -> {
                            InsuranceCard insuranceCard = getTableView().getItems().get(getIndex());
                            boolean isSuccess = adminController.deleteInsuranceCardInformation(insuranceCard.getCardNumber());
                            if (isSuccess) {
                                    insuranceCardObservableList.remove(insuranceCard);
                                    insuranceCardTableView.refresh();
                            } else {
                                System.out.println("isSuccess: " + false);
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(new HBox(10, editButton, deleteButton));
                        }
                    }
                };
            }
        };
    }

    // Claim Functions
    @FXML
    void editFormClaimCancelBtnOnAction() {
        editFormClaimInformation.setVisible(false);
    }
    // Method to select row and set value
    private void selectClaimRow(Claim selectedClaim) {
        try {
            if (selectedClaim != null) {
                editFormClaimId.setText(selectedClaim.getId());
                editFormClaimDate.setText(selectedClaim.getClaimDate() != null ? selectedClaim.getClaimDate().toString() : null);
                editFormClaimExam.setText(selectedClaim.getExamDate() != null ? selectedClaim.getExamDate().toString() : null);
                editFormClaimInsuredPerson.setText(adminController.getNameForUser(selectedClaim.getInsuredPerson()));
                editFormClaimCardNumber.setText(selectedClaim.getCardNumber());
                editFormClaimAmount.setText(String.valueOf(selectedClaim.getClaimAmount()));
                editFormClaimStatus.setText(selectedClaim.getStatus());
                editFormClaimBankName.setText(selectedClaim.getBankName());
                editFormClaimBankUser.setText(selectedClaim.getBankUserName());
                editFormClaimBankNumber.setText(selectedClaim.getBankNumber());
                editFormClaimTotalDocument.setText(String.valueOf(claimController.calculateTotalDocumentsOfClaim(selectedClaim.getId())));
                editFormClaimInformation.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // method to set table view cell value
    private void displayClaimDashboardTableView() {
        claimColClaimId.setCellValueFactory(new PropertyValueFactory<>("id"));
        claimColInsuredPerson.setCellValueFactory(cellData -> {
            String insuredPersonId = cellData.getValue().getInsuredPerson();
            String name = adminController.getNameForUser(insuredPersonId);
            return new SimpleStringProperty(name);
        });
        claimColCardNumber.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
        claimColClaimDate.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
        claimColAmount.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        claimColStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        claimColAction.setCellFactory(createClaimActionCellFactory());

        claimTableView.setItems(claimObservableList);
    }
    // Method to return call back
    private Callback<TableColumn<Claim, Void>, TableCell<Claim, Void>> createClaimActionCellFactory() {
        return new Callback<TableColumn<Claim, Void>, TableCell<Claim, Void>>() {
            @Override
            public TableCell<Claim, Void> call(TableColumn<Claim, Void> param) {
                return new TableCell<Claim, Void>() {
                    final Button editButton = new Button("Edit");
                    final Button deleteButton = new Button("Delete");
                    {
                        editButton.setOnAction(event -> {
                            Claim selectedClaim = getTableView().getItems().get(getIndex());
                            selectClaimRow(selectedClaim);
                            editFormClaimConfirmBtn.setOnAction(event1 -> {
                                boolean isSuccess = claimController.updateClaimInformation(editFormClaimId.getText(), editFormClaimAmount.getText(), editFormClaimStatus.getText(), editFormClaimBankName.getText(), editFormClaimBankUser.getText(), editFormClaimBankNumber.getText());
                                if (isSuccess) {
                                   selectedClaim.setClaimAmount(Double.parseDouble(editFormClaimAmount.getText()));
                                selectedClaim.setStatus(editFormClaimStatus.getText());
                                selectedClaim.setBankName(editFormClaimBankName.getText());
                                selectedClaim.setBankUserName(editFormClaimBankUser.getText());
                                selectedClaim.setBankNumber(editFormClaimBankNumber.getText());
                                claimTableView.refresh();
                                editFormClaimInformation.setVisible(false);
                                } else {
                                    System.out.println("isSuccess: " + false);
                                }
                            });
                        });

                        deleteButton.setOnAction(event -> {
                            Claim claim = getTableView().getItems().get(getIndex());
                            System.out.println("Deleting claim: " + claim.getId());
                            boolean isSuccess = claimController.deleteClaimInformation(claim);
                            if (isSuccess) {
                                claimObservableList.remove(claim);
                                claimTableView.refresh();
                                //delete claim success -> must delete document alongs with it
                                //code here
                            } else {
                                System.out.println("isSuccess: " + false);
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(new HBox(10, editButton, deleteButton));
                        }
                    }
                };
            }
        };
    }
    //Profile function
    public User getUserById(String id, ObservableList<User> users){
        for(User user: users){
            if(user.getId().equalsIgnoreCase(id)){
                System.out.println(user.getId() + user.getUserName());
            }
        }
        return null;
    }
    @FXML
    private void editProfileConfirmBtnOnAction() {
        String userId = editProfileId.getText();
        String fullName = editProfileFullName.getText();
        String email = editProfileEmail.getText();
        String phoneNumber = editProfilePhoneNumber.getText();
        String address = editProfileAddress.getText();

        User user = adminController.getUserById(userId);
        boolean isSuccess;
        if (editProfilePassword.getText().equalsIgnoreCase(user.getPassword())){
            isSuccess = adminController.updateUserWithoutPassword(userId, fullName, email, phoneNumber, address);
        } else {
            String bcryptUpdatePassword = bcryptPassword.hashBcryptPassword(editProfilePassword.getText());
            isSuccess = adminController.updateUser(userId, fullName, bcryptUpdatePassword, email, phoneNumber, address);
        }

        if (isSuccess) {
            editProfileFullName.setText(fullName);
            editProfileEmail.setText(email);
            editProfilePhoneNumber.setText(phoneNumber);
            editProfileAddress.setText(address);
            navFullname.setText(fullName);
            topUsername.setText(fullName);
            profileDashboardFullname.setText(fullName);
            profileDashboardEmail.setText(email);

            for (User selectedUser : userObservableList) {
                if (Objects.equals(userId, selectedUser.getId())) {
                    selectedUser.setFullName(fullName);
                    selectedUser.setEmail(email);
                    selectedUser.setPhoneNumber(phoneNumber);
                    selectedUser.setAddress(address);
                    if (!editProfilePassword.getText().isEmpty()) {
                        selectedUser.setPassword(editProfilePassword.getText());
                    }
                    break;
                }
            }
        } else {
            System.out.println("isSuccess: " + false);
        }
    }


    // Method to set FXML dashboard information
    public void displayProfileDashboardInformation() {
        User userData = adminController.getProfileDashboardInformation(LoginData.usernameLogin, LoginData.roleId);
        if (userData != null) {
            profileDashboardId.setText(userData.getId());
            profileDashboardUsername.setText(userData.getUserName());
            profileDashboardEmail.setText(userData.getEmail());
            profileDashboardFullname.setText(userData.getFullName());
            editProfileId.setText(userData.getId());
            editProfileFullName.setText(userData.getFullName());
            editProfileUserName.setText(userData.getUserName());
            editProfilePassword.setText(userData.getPassword());
            editProfileEmail.setText(userData.getEmail());
            editProfilePhoneNumber.setText(userData.getPhoneNumber());
            editProfileAddress.setText(userData.getAddress());
            int roleId = userData.getRoleId();
            String roleName = adminController.getRoleName(roleId);
            editProfileRole.setText(roleName);
            navAdminid.setText(userData.getId());
            navUsername.setText(LoginData.usernameLogin);
            navFullname.setText(userData.getFullName());
            topUsername.setText(userData.getFullName());
        } else {
            System.out.println("Failed to display profile dashboard information.");
        }
    }
    ///Main dashboard functions
    private void displayMainDashboardTableView() {
        mainDashboardColClaimId.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainDashboardColClaimDate.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
        mainDashboardColAmount.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        mainDashboardColStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        mainDashboardColInsuredPerson.setCellValueFactory(cellData -> {
            String insuredPersonId = cellData.getValue().getInsuredPerson();
            String name = adminController.getNameForUser(insuredPersonId);
            return new SimpleStringProperty(name);
        });
        mainDashboardTableView.setItems(claimObservableList);
    }
    // Method to switch dashboard upon action event trigger
    public void switchDashboard(ActionEvent event) {
        if (event.getSource() == navMainDashboardBtn) {
            claimTableView.refresh();
            showMainDashboard();
        } else if (event.getSource() == navUsersBtn) {
            userTableView.refresh();
            showUserDashboard();
        } else if (event.getSource() == navInsuranceCardsBtn) {
            insuranceCardTableView.refresh();
            showInsuranceCardDashboard();
        } else if (event.getSource() == navClaimsBtn) {
            claimTableView.refresh();
            showClaimDashboard();
        } else if (event.getSource() == navProfileBtn) {
            showProfileDashboard();
        }
    }
    // Method to show main dashboard
    private void showMainDashboard() {
        mainDashboard.setVisible(true);
        userInformationDashboard.setVisible(false);
        insuranceCardDashboard.setVisible(false);
        claimDashboard.setVisible(false);
        profileDashboard.setVisible(false);
    }
    // Method to show user dashboard
    private void showUserDashboard() {
        mainDashboard.setVisible(false);
        userInformationDashboard.setVisible(true);
        insuranceCardDashboard.setVisible(false);
        claimDashboard.setVisible(false);
        profileDashboard.setVisible(false);
    }
    // Method to show insurance card dashboard
    private void showInsuranceCardDashboard() {
        mainDashboard.setVisible(false);
        userInformationDashboard.setVisible(false);
        insuranceCardDashboard.setVisible(true);
        claimDashboard.setVisible(false);
        profileDashboard.setVisible(false);
    }
    // Method to show claim dashboard
    private void showClaimDashboard() {
        mainDashboard.setVisible(false);
        userInformationDashboard.setVisible(false);
        insuranceCardDashboard.setVisible(false);
        claimDashboard.setVisible(true);
        profileDashboard.setVisible(false);
    }
    // Method to show profile dashboard
    private void showProfileDashboard() {
        mainDashboard.setVisible(false);
        userInformationDashboard.setVisible(false);
        insuranceCardDashboard.setVisible(false);
        claimDashboard.setVisible(false);
        profileDashboard.setVisible(true);
    }
    // Method to initialize the page upon setup
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userObservableList.addAll(adminController.fetchUsersFromDatabase());
        claimObservableList.addAll(claimController.fetchClaimsFromDatabase());
        insuranceCardObservableList.addAll(adminController.fetchInsuranceCardsFromDatabase());
        roleObservableList.addAll(adminController.fetchRolesFromDatabase());
        editFormCreateUserRoleIdOnAction();
        totalProviders.setText(String.valueOf(adminController.displayTotalProviders()));
        totalCustomers.setText(String.valueOf(adminController.displayTotalCustomers()));
        totalInsuranceCards.setText(String.valueOf(adminController.displayTotalInsuranceCards()));
        totalClaims.setText(String.valueOf(adminController.displayTotalClaims()));
        displayMainDashboardTableView();
        displayUsersDashboardTableView();
        displayInsuranceCardDashboardTableView();
        displayClaimDashboardTableView();
        displayProfileDashboardInformation();
        btnLogOut.setOnAction(ActionEvent -> LoginData.logOut(btnLogOut));
    }
}
