package com.insurancecompany.insurancemanagementgroupproject2.controller;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.model.Claim;
import com.insurancecompany.insurancemanagementgroupproject2.model.InsuranceCard;
import com.insurancecompany.insurancemanagementgroupproject2.model.LoginData;
import com.insurancecompany.insurancemanagementgroupproject2.model.User;
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
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

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
    private Button logOutButtonOnAction;

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


    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    //User Dashboard functions
    @FXML
    private void selectUserRow() {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            editFormUserId.setText(selectedUser.getId());
            editFormUserFullName.setText(selectedUser.getFull_name());
            editFormUserName.setText(selectedUser.getUser_name());
            editFormUserPassword.setText(selectedUser.getPassword());
            editFormUserEmail.setText(selectedUser.getEmail());
            editFormUserPhoneNumber.setText(selectedUser.getPhone_number());
            editFormUserAddress.setText(selectedUser.getAddress());
            editFormUserRole.setText(getRoleName(selectedUser.getRole_id()));
            editFormUserInformation.setVisible(true);
        }
    }

    @FXML
    private void updateUserInformation() {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        try {
            if (selectedUser != null) {
                String id = selectedUser.getId();
                String fullName = editFormUserFullName.getText();
                String password = editFormUserPassword.getText();
                String email = editFormUserEmail.getText();
                String phoneNumber = editFormUserPhoneNumber.getText();
                String address = editFormUserAddress.getText();
                String updateProfileQuery = "UPDATE users SET full_name = ?, password = ?, email = ?, phone_number = ?, address = ? WHERE id = ?";
                try (PreparedStatement statement = connection.prepareStatement(updateProfileQuery)) {
                    statement.setString(1, fullName);
                    statement.setString(2, password);
                    statement.setString(3, email);
                    statement.setString(4, phoneNumber);
                    statement.setString(5, address);
                    statement.setString(6, id);
                    int rowsUpdated = statement.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("User Information updated successfully!");
                        editFormUserFullName.setText(fullName);
                        editFormUserPassword.setText(password);
                        editFormUserEmail.setText(email);
                        editFormUserPhoneNumber.setText(phoneNumber);
                        editFormUserAddress.setText(address);
                        selectedUser.setFull_name(fullName);
                        selectedUser.setPassword(password);
                        selectedUser.setEmail(email);
                        selectedUser.setPhone_number(phoneNumber);
                        selectedUser.setAddress(address);
                        userTableView.refresh();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteUserInformation() {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                String id = selectedUser.getId();
                String deleteQuery = "DELETE FROM users WHERE id = ?";
                try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                    statement.setString(1, id);
                    int rowDeleted = statement.executeUpdate();
                    if (rowDeleted > 0) {
                        System.out.println("User deleted successfully!");
                        userObservableList.remove(selectedUser);
                        editFormUserInformation.setVisible(false);
                        userTableView.refresh();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayUsersDashboardTableView() {
        userColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        userColFullname.setCellValueFactory(new PropertyValueFactory<>("full_name"));
        userColUsername.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        userColPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        userColEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        userColPhonenumber.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        userColAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        userColRole.setCellValueFactory(new PropertyValueFactory<>("role_id"));
        fetchUsersFromDatabase();

        userTableView.setItems(userObservableList);
    }

    private void fetchUsersFromDatabase() {
        try {
            String queryAllUsers = "SELECT * FROM users WHERE role_id = 2 OR role_id = 3 OR role_id = 4 OR role_id = 5 OR role_id =6";
            PreparedStatement statement = connection.prepareStatement(queryAllUsers);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getString("id"));
                user.setFull_name(resultSet.getString("full_name"));
                user.setUser_name(resultSet.getString("user_name"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone_number(resultSet.getString("phone_number"));
                user.setAddress(resultSet.getString("address"));
                user.setRole_id(resultSet.getInt("role_id"));
                userObservableList.add(user);
            }
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Insurance Card Dashboard

    @FXML
    private void deleteInsuranceCardInformation(InsuranceCard selectedInsuranceCard){
        if (selectedInsuranceCard != null) {
            try {
                String card_number = selectedInsuranceCard.getCard_number();
                String deleteQuery = "DELETE FROM insurance_card WHERE card_number = ?";
                try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                    statement.setString(1, card_number);
                    int rowDeleted = statement.executeUpdate();
                    if (rowDeleted > 0) {
                        System.out.println("Insurance Card deleted successfully!");
                        insuranceCardObservableList.remove(selectedInsuranceCard);
                        insuranceCardTableView.refresh();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private void updateInsuranceCardInformation(InsuranceCard selectedInsuranceCard) throws ParseException {
        if (selectedInsuranceCard != null) {
            String cardNumber = selectedInsuranceCard.getCard_number();
            String expDate = editFormInsuranceExpDate.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = sdf.parse(expDate);
            java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

            String updateQuery = "UPDATE insurance_card SET expiration_date = ? WHERE card_number = ?";
            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                statement.setDate(1, sqlDate);
                statement.setString(2, cardNumber);
                int rowsUpdated = statement.executeUpdate();
                System.out.println("Rows Updated: " + rowsUpdated);

                if (rowsUpdated > 0) {
                    System.out.println("Insurance Card Information updated successfully!");
                    selectedInsuranceCard.setExpiration_date(sqlDate);
                    insuranceCardTableView.refresh();
                    editFormInsuranceExpDate.setText(String.valueOf(sqlDate));
                    editFormInsuranceCardInformation.setVisible(false);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    void editFormInsuranceCancelBtnOnAction() {
        editFormInsuranceCardInformation.setVisible(false);
    }
    @FXML
    private void selectInsuranceCardRow(InsuranceCard selectedInsuranceCard) {
        try {
            if (selectedInsuranceCard != null) {
                editFormInsuranceCardNumber.setText(selectedInsuranceCard.getCard_number());
                editFormInsuranceExpDate.setText(String.valueOf(selectedInsuranceCard.getExpiration_date()));
                editFormInsuranceCardHolderId.setText(selectedInsuranceCard.getCard_holder_id());
                editFormInsuranceCardHolderName.setText(getNameForUser(selectedInsuranceCard.getCard_holder_id()));
                editFormInsurancePolicyOwnerId.setText(selectedInsuranceCard.getPolicy_owner_id());
                editFormInsurancePolicyOwnerName.setText(getNameForUser(selectedInsuranceCard.getPolicy_owner_id()));
                editFormInsuranceCardInformation.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void displayInsuranceCardDashboardTableView() {
        insuranceCardColCardNumber.setCellValueFactory(new PropertyValueFactory<>("card_number"));
        insuranceCardColExpDate.setCellValueFactory(new PropertyValueFactory<>("expiration_date"));
        insuranceCardColCardHolder.setCellValueFactory(cellData -> {
            String cardHolderId = cellData.getValue().getCard_holder_id();
            String name = getNameForUser(cardHolderId);
            return new SimpleStringProperty(name);
        });
        insuranceCardColPolicyOwner.setCellValueFactory(cellData -> {
            String policyOwnerId = cellData.getValue().getPolicy_owner_id();
            String name = getNameForUser(policyOwnerId);
            return new SimpleStringProperty(name);
        });
        insuranceCardColAction.setCellFactory(createInsuranceCardActionCellFactory());
        fetchInsuranceCardsFromDatabase();
        insuranceCardTableView.setItems(insuranceCardObservableList);
    }
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
                                    updateInsuranceCardInformation(selectedInsuranceCard);
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                            System.out.println(selectedInsuranceCard.getCard_number());
                        });

                        deleteButton.setOnAction(event -> {
                            InsuranceCard insuranceCard = getTableView().getItems().get(getIndex());
                            deleteInsuranceCardInformation(insuranceCard);
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
    private void fetchInsuranceCardsFromDatabase() {
        try (Connection connection = new DatabaseConnection().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM insurance_card");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                InsuranceCard insuranceCard = new InsuranceCard();
                insuranceCard.setCard_number(resultSet.getString("card_number"));
                insuranceCard.setExpiration_date(resultSet.getDate("expiration_date"));
                insuranceCard.setCard_holder_id(resultSet.getString("card_holder_id"));
                insuranceCard.setPolicy_owner_id(resultSet.getString("policy_owner_id"));
                insuranceCardObservableList.add(insuranceCard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Claim Dashboard
    private void updateClaimInformation(Claim selectedClaim) {
        String claimId = selectedClaim.getClaimId();
        String claimDate = editFormClaimDate.getText();
        String examDate = editFormClaimExam.getText();
        String amount = editFormClaimAmount.getText();
        String status = editFormClaimStatus.getText();
        String bankName = editFormClaimBankName.getText();
        String bankUser = editFormClaimBankUser.getText();
        String bankNumber = editFormClaimBankNumber.getText();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsedClaimDate = null;
        java.util.Date parsedExamDate = null;
        java.sql.Date sqlClaimDate = null;
        java.sql.Date sqlExamDate = null;

        try {
            if (claimDate != null && !claimDate.isEmpty()) {
                parsedClaimDate = sdf.parse(claimDate);
                sqlClaimDate = new java.sql.Date(parsedClaimDate.getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        try {
            if (examDate != null && !examDate.isEmpty()) {
                parsedExamDate = sdf.parse(examDate);
                sqlExamDate = new java.sql.Date(parsedExamDate.getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        float claimAmount;
        try {
            claimAmount = Float.parseFloat(amount);
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
            return;
        }

        try {
            String updateQuery = "UPDATE claims SET claim_date=?, exam_date=?, claim_amount=?, status=?, bank_name=?, bank_user_name=?, bank_number=? WHERE claim_id=?";
            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                if (sqlClaimDate != null) statement.setDate(1, sqlClaimDate);
                else statement.setNull(1, Types.DATE);
                if (sqlExamDate != null) statement.setDate(2, sqlExamDate);
                else statement.setNull(2, Types.DATE);
                statement.setFloat(3, claimAmount);
                statement.setString(4, status);
                statement.setString(5, bankName);
                statement.setString(6, bankUser);
                statement.setString(7, bankNumber);
                statement.setString(8, claimId);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Claim information updated successfully!");
                    selectedClaim.setClaimDate(sqlClaimDate);
                    selectedClaim.setExamDate(sqlExamDate);
                    selectedClaim.setClaimAmount(claimAmount);
                    selectedClaim.setStatus(status);
                    selectedClaim.setBankName(bankName);
                    selectedClaim.setBankUser(bankUser);
                    selectedClaim.setBankNumber(bankNumber);
                    claimTableView.refresh();
                    if (sqlClaimDate != null) editFormClaimDate.setText(String.valueOf(sqlClaimDate));
                    if (sqlExamDate != null) editFormClaimExam.setText(String.valueOf(sqlExamDate));
                    editFormClaimAmount.setText(String.valueOf(claimAmount));
                    editFormClaimStatus.setText(status);
                    editFormClaimBankName.setText(bankName);
                    editFormClaimBankUser.setText(bankUser);
                    editFormClaimBankNumber.setText(bankNumber);
                    editFormClaimInformation.setVisible(false);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void editFormClaimCancelBtnOnAction() {
        editFormClaimInformation.setVisible(false);
    }

    private void selectClaimRow(Claim selectedClaim){
        try {
            if (selectedClaim != null) {
                editFormClaimId.setText(selectedClaim.getClaimId());
                editFormClaimDate.setText(selectedClaim.getClaimDate() != null ? selectedClaim.getClaimDate().toString() : null);
                editFormClaimExam.setText(selectedClaim.getExamDate() != null ? selectedClaim.getExamDate().toString() : null);
                editFormClaimInsuredPerson.setText(getNameForUser(selectedClaim.getInsuredPerson()));
                editFormClaimCardNumber.setText(selectedClaim.getCardNumber());
                editFormClaimAmount.setText(String.valueOf(selectedClaim.getClaimAmount()));
                editFormClaimStatus.setText(selectedClaim.getStatus());
                editFormClaimBankName.setText(selectedClaim.getBankName());
                editFormClaimBankUser.setText(selectedClaim.getBankUser());
                editFormClaimBankNumber.setText(selectedClaim.getBankNumber());
                editFormClaimInformation.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteClaimInformation(Claim selectedClaim) {
        if (selectedClaim != null) {
            try {
                String claimId = selectedClaim.getClaimId();
                String deleteQuery = "DELETE FROM claims WHERE claim_id = ?";
                try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                    statement.setString(1, claimId);
                    int rowsDeleted = statement.executeUpdate();
                    if (rowsDeleted > 0) {
                        System.out.println("Claim deleted successfully!");
                        claimObservableList.remove(selectedClaim);
                        claimTableView.refresh();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayClaimDashboardTableView() {
        claimColClaimId.setCellValueFactory(new PropertyValueFactory<>("claimId"));
        claimColInsuredPerson.setCellValueFactory(cellData -> {
            String insuredPersonId = cellData.getValue().getInsuredPerson();
            String name = getNameForUser(insuredPersonId);
            return new SimpleStringProperty(name);
        });
        claimColCardNumber.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
        claimColClaimDate.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
        claimColAmount.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        claimColStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        claimColAction.setCellFactory(createClaimActionCellFactory());

        claimTableView.setItems(claimObservableList);
    }

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
                                updateClaimInformation(selectedClaim);
                            });
                        });

                        deleteButton.setOnAction(event -> {
                            Claim claim = getTableView().getItems().get(getIndex());
                            System.out.println("Deleting claim: " + claim.getClaimId());
                            deleteClaimInformation(claim);
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

    //Profile dashboard functions
    @FXML
    private void editProfileConfirmBtnOnAction() {
        updateProfileDashboardInformation();
    }

    private void updateProfileDashboardInformation() {
        try {
            String id = editProfileId.getText();
            String fullName = editProfileFullName.getText();
            String password = editProfilePassword.getText();
            String email = editProfileEmail.getText();
            String phoneNumber = editProfilePhoneNumber.getText();
            String address = editProfileAddress.getText();

            String updateProfileQuery = "UPDATE users SET full_name = ?, password = ?, email = ?, phone_number = ?, address = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(updateProfileQuery)) {
                statement.setString(1, fullName);
                statement.setString(2, password);
                statement.setString(3, email);
                statement.setString(4, phoneNumber);
                statement.setString(5, address);
                statement.setString(6, id);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Profile updated successfully!");
                    editProfileFullName.setText(fullName);
                    editProfilePassword.setText(password);
                    editProfileEmail.setText(email);
                    editProfilePhoneNumber.setText(phoneNumber);
                    editProfileAddress.setText(address);
                    navFullname.setText(fullName);
                    profileDashboardFullname.setText(fullName);
                    profileDashboardEmail.setText(email);
                    topUsername.setText(fullName);
                } else {
                    System.out.println("Failed to update profile!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayProfileDashboardInformation() {
        try {
            String queryProfileInformation = "SELECT * FROM users WHERE user_name = ? AND role_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(queryProfileInformation)) {
                statement.setString(1, LoginData.usernameLogin);
                statement.setInt(2, LoginData.roleId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        profileDashboardId.setText(resultSet.getString("id"));
                        profileDashboardUsername.setText(resultSet.getString("user_name"));
                        profileDashboardEmail.setText(resultSet.getString("email"));
                        profileDashboardFullname.setText(resultSet.getString("full_name"));
                        editProfileId.setText(resultSet.getString("id"));
                        editProfileFullName.setText(resultSet.getString("full_name"));
                        editProfileUserName.setText(resultSet.getString("user_name"));
                        editProfilePassword.setText(resultSet.getString("password"));
                        editProfileEmail.setText(resultSet.getString("email"));
                        editProfilePhoneNumber.setText(resultSet.getString("phone_number"));
                        editProfileAddress.setText(resultSet.getString("address"));
                        int roleId = resultSet.getInt("role_id");
                        String roleName = getRoleName(roleId);
                        editProfileRole.setText(roleName);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Main Dash board
    private void displayMainDashboardTableView() {
        mainDashboardColClaimId.setCellValueFactory(new PropertyValueFactory<>("claimId"));
        mainDashboardColClaimDate.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
        mainDashboardColAmount.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        mainDashboardColStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        mainDashboardColInsuredPerson.setCellValueFactory(cellData -> {
            String insuredPersonId = cellData.getValue().getInsuredPerson();
            String name = getNameForUser(insuredPersonId);
            return new SimpleStringProperty(name);
        });
        mainDashboardTableView.setItems(claimObservableList);
    }

    private void fetchClaimsFromDatabase() {

        try {
            String queryClaims = "SELECT * FROM claims";
            PreparedStatement statement = connection.prepareStatement(queryClaims);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Claim claim = new Claim();
                claim.setClaimId(resultSet.getString("claim_id"));
                claim.setInsuredPerson(resultSet.getString("insured_person"));
                claim.setCardNumber(resultSet.getString("card_number"));
                claim.setClaimDate(resultSet.getDate("claim_date"));
                claim.setExamDate(resultSet.getDate("exam_date"));
                claim.setClaimAmount(resultSet.getFloat("claim_amount"));
                claim.setStatus(resultSet.getString("status"));
                claim.setBankName(resultSet.getString("bank_name"));
                claim.setBankUser(resultSet.getString("bank_user_name"));
                claim.setBankNumber(resultSet.getString("bank_number"));
                claimObservableList.add(claim);
            }
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getNameForUser(String userId) {
        String name = "";
        try {
            String query = "SELECT full_name FROM users WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                name = resultSet.getString("full_name");
            }
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    private String getRoleName(int roleId) {
        String roleName = "";
        try {
            String queryRoleName = "SELECT role FROM roles WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(queryRoleName);
            statement.setInt(1, roleId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                roleName = resultSet.getString("role");
            }
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roleName;
    }

    public void displayAdminInformation() {
        try {
            String queryAdminInformation = "SELECT * FROM users WHERE user_name = ? AND role_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(queryAdminInformation)) {
                statement.setString(1, LoginData.usernameLogin);
                statement.setInt(2, LoginData.roleId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String adminId = resultSet.getString("id");
                        String fullName = resultSet.getString("full_name");

                        navAdminid.setText(adminId);
                        navUsername.setText(LoginData.usernameLogin);
                        navFullname.setText(fullName);
                        topUsername.setText(fullName);
                    } else {
                        System.out.println("Admin not found!");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayTotalProviders() {

        try {
            String queryManagerCount = "SELECT COUNT(*) AS managerCount FROM users WHERE role_id = ?";
            String querySurveyorCount = "SELECT COUNT(*) AS surveyorCount FROM users WHERE role_id = ?";
            try (PreparedStatement managerStatement = connection.prepareStatement(queryManagerCount);
                 PreparedStatement surveyorStatement = connection.prepareStatement(querySurveyorCount)) {
                managerStatement.setInt(1, 2);
                int managerCount = 0;
                try (ResultSet managerResultSet = managerStatement.executeQuery()) {
                    if (managerResultSet.next()) {
                        managerCount = managerResultSet.getInt("managerCount");
                    }
                }
                surveyorStatement.setInt(1, 3);
                int surveyorCount = 0;
                try (ResultSet surveyorResultSet = surveyorStatement.executeQuery()) {
                    if (surveyorResultSet.next()) {
                        surveyorCount = surveyorResultSet.getInt("surveyorCount");
                    }
                }
                int totalProvider = managerCount + surveyorCount;
                totalProviders.setText(String.valueOf(totalProvider));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayTotalCustomers() {

        try {
            String queryPolicyOwnerCount = "SELECT COUNT(*) as policyOwnerCount FROM users WHERE role_id = ?";
            String queryPolicyHolderCount = "SELECT COUNT(*) as policyHolderCount FROM users WHERE role_id = ?";
            String queryDependentCount = "SELECT COUNT(*) as dependentCount FROM users WHERE role_id = ?";
            try (PreparedStatement policyOwnerStatement = connection.prepareStatement(queryPolicyOwnerCount);
                 PreparedStatement policyHolderStatement = connection.prepareStatement(queryPolicyHolderCount);
                 PreparedStatement dependentStatement = connection.prepareStatement(queryDependentCount);) {
                policyOwnerStatement.setInt(1, 4);
                int policyOwnerCount = 0;
                try (ResultSet policyOwnerResultSet = policyOwnerStatement.executeQuery()) {
                    if (policyOwnerResultSet.next()) {
                        policyOwnerCount = policyOwnerResultSet.getInt("policyOwnerCount");
                    }
                }

                policyHolderStatement.setInt(1, 5);
                int policyHolderCount = 0;
                try (ResultSet policyHolderResultSet = policyHolderStatement.executeQuery()) {
                    if (policyHolderResultSet.next()) {
                        policyHolderCount = policyHolderResultSet.getInt("policyHolderCount");
                    }
                }

                dependentStatement.setInt(1, 6);
                int dependentCount = 0;
                try (ResultSet dependentResultSet = dependentStatement.executeQuery()) {
                    if (dependentResultSet.next()) {
                        dependentCount = dependentResultSet.getInt("dependentCount");
                    }
                }

                int totalCustomer = policyOwnerCount + policyHolderCount + dependentCount;
                totalCustomers.setText(String.valueOf(totalCustomer));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayTotalInsuranceCards() {

        try {
            String queryInsuranceCardCount = "SELECT COUNT(*) AS cardCount FROM insurance_card";
            try (PreparedStatement statement = connection.prepareStatement(queryInsuranceCardCount)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int cardCount = resultSet.getInt("cardCount");
                        totalInsuranceCards.setText(String.valueOf(cardCount));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayTotalClaims() {

        try {
            String queryClaimCount = "SELECT COUNT(*) AS claimCount FROM claims";
            try (PreparedStatement statement = connection.prepareStatement(queryClaimCount)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int claimCount = resultSet.getInt("claimCount");
                        totalClaims.setText(String.valueOf(claimCount));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void switchDashboard(ActionEvent event) {
        if (event.getSource() == navMainDashboardBtn) {
            showMainDashboard();
        } else if (event.getSource() == navUsersBtn) {
            showUserDashboard();
        } else if (event.getSource() == navInsuranceCardsBtn) {
            showInsuranceCardDashboard();
        } else if (event.getSource() == navClaimsBtn) {
            showClaimDashboard();
        } else if (event.getSource() == navProfileBtn) {
            showProfileDashboard();
        }
    }

    private void showMainDashboard() {
        mainDashboard.setVisible(true);
        userInformationDashboard.setVisible(false);
        insuranceCardDashboard.setVisible(false);
        claimDashboard.setVisible(false);
        profileDashboard.setVisible(false);
    }

    private void showUserDashboard() {
        mainDashboard.setVisible(false);
        userInformationDashboard.setVisible(true);
        insuranceCardDashboard.setVisible(false);
        claimDashboard.setVisible(false);
        profileDashboard.setVisible(false);
    }

    private void showInsuranceCardDashboard() {
        mainDashboard.setVisible(false);
        userInformationDashboard.setVisible(false);
        insuranceCardDashboard.setVisible(true);
        claimDashboard.setVisible(false);
        profileDashboard.setVisible(false);
    }

    private void showClaimDashboard() {
        mainDashboard.setVisible(false);
        userInformationDashboard.setVisible(false);
        insuranceCardDashboard.setVisible(false);
        claimDashboard.setVisible(true);
        profileDashboard.setVisible(false);
    }

    private void showProfileDashboard() {
        mainDashboard.setVisible(false);
        userInformationDashboard.setVisible(false);
        insuranceCardDashboard.setVisible(false);
        claimDashboard.setVisible(false);
        profileDashboard.setVisible(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fetchClaimsFromDatabase();
        displayAdminInformation();
        displayTotalProviders();
        displayTotalCustomers();
        displayTotalInsuranceCards();
        displayTotalClaims();
        displayMainDashboardTableView();
        displayUsersDashboardTableView();
        displayInsuranceCardDashboardTableView();
        displayClaimDashboardTableView();
        displayProfileDashboardInformation();
    }
}
