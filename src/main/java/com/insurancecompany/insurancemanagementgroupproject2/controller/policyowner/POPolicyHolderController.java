package com.insurancecompany.insurancemanagementgroupproject2.controller.policyowner;
/**
 * @author team 5
 */
import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.controller.BcryptPassword;
import com.insurancecompany.insurancemanagementgroupproject2.model.LoginData;
import com.insurancecompany.insurancemanagementgroupproject2.model.PolicyHolder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class POPolicyHolderController implements Initializable {

    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();
    BcryptPassword bcryptPassword = new BcryptPassword();

    @FXML
    private Button addNewPolicyHolderBtn;

    @FXML
    private AnchorPane addNewPolicyHolderForm;

    @FXML
    private Button policyHolderFormCancelBtn;

    @FXML
    private TextArea editFieldPolicyHolderAddress;

    @FXML
    private TextField editFieldPolicyHolderEmail;

    @FXML
    private TextField editFieldPolicyHolderFullName;

    @FXML
    private TextField editFieldPolicyHolderID;

    @FXML
    private TextField editFieldPolicyHolderPassword;

    @FXML
    private TextField editFieldPolicyHolderPhoneNumber;

    @FXML
    private TextField editFieldPolicyHolderRole;

    @FXML
    private TextField editFieldPolicyHolderUsername;

    @FXML
    private TableView<PolicyHolder> policyHolderTableView;

    @FXML
    private TableColumn<PolicyHolder, String> policyHolderAddressCol;

    @FXML
    private TableColumn<PolicyHolder, String> policyHolderEmailCol;

    @FXML
    private TableColumn<PolicyHolder, String> policyHolderFullNameCol;

    @FXML
    private TableColumn<PolicyHolder, String> policyHolderIDCol;

    @FXML
    private TableColumn<PolicyHolder, String> policyHolderPasswordCol;

    @FXML
    private TableColumn<PolicyHolder, String> policyHolderPhoneNumCol;

    @FXML
    private TableColumn<PolicyHolder, Integer> policyHolderRoleCol;

    @FXML
    private TableColumn<PolicyHolder, String> policyHolderUsernameCol;

    // Create new PolicyHolder form

    @FXML
    private TextArea addNewPolicyHolderAddressField;

    @FXML
    private TextField addNewPolicyHolderCardExpriedDateField;

    @FXML
    private TextField addNewPolicyHolderEmailField;

    @FXML
    private TextField addNewPolicyHolderFullNameField;

    @FXML
    private TextField addNewPolicyHolderPassword;

    @FXML
    private TextField addNewPolicyHolderPhoneNumField;

    @FXML
    private TextField addNewPolicyHolderUsernameField;


    private ObservableList<PolicyHolder> policyHolderObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addNewPolicyHolderBtnOnAction();
        policyHolderFormCancelBtnOnAction();
        policyHolderObservableList.addAll(fetchPolicyHoldersFromDatabase());
        displayPolicyHolders();
    }

    // Button for opening the form to add a new policyholder
    @FXML
    private void addNewPolicyHolderBtnOnAction() {
        addNewPolicyHolderForm.setVisible(true);
    }

    // Button for canceling the form to add a new policyholder
    @FXML
    private void policyHolderFormCancelBtnOnAction() {
        addNewPolicyHolderForm.setVisible(false);
    }

    private ArrayList<PolicyHolder> fetchPolicyHoldersFromDatabase() {
        ArrayList<PolicyHolder> policyHolderArrayList = new ArrayList<>();
        try {
            String policyOwnerId = getIDFromUserName(LoginData.usernameLogin);

            // SQL query to select policyholders related to the logged-in policy owner
            String query = "SELECT u.* FROM users u " +
                    "JOIN insurance_card ic ON u.id = ic.card_holder_id " +
                    "WHERE ic.policy_owner_id = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, policyOwnerId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                PolicyHolder policyHolder = new PolicyHolder();
                policyHolder.setId(resultSet.getString("id"));
                policyHolder.setFullName(resultSet.getString("full_name"));
                policyHolder.setUserName(resultSet.getString("user_name"));
                policyHolder.setPassword(resultSet.getString("password"));
                policyHolder.setEmail(resultSet.getString("email"));
                policyHolder.setPhoneNumber(resultSet.getString("phone_number"));
                policyHolder.setAddress(resultSet.getString("address"));
                policyHolder.setRoleId(resultSet.getInt("role_id"));
                policyHolderArrayList.add(policyHolder);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return policyHolderArrayList;
    }

    // Display all policyholders in the TableView
    public void displayPolicyHolders() {
        policyHolderIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        policyHolderFullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        policyHolderUsernameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        policyHolderPasswordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        policyHolderEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        policyHolderPhoneNumCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        policyHolderAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        policyHolderRoleCol.setCellValueFactory(new PropertyValueFactory<>("roleId"));
        policyHolderTableView.setItems(policyHolderObservableList);
    }
    // Setting text when clicking on a row in the TableView
    @FXML
    private void selectPolicyHolderRow() {
        PolicyHolder selectedPolicyHolder = policyHolderTableView.getSelectionModel().getSelectedItem();
        if (selectedPolicyHolder != null) {
            editFieldPolicyHolderID.setText(selectedPolicyHolder.getId());
            editFieldPolicyHolderFullName.setText(selectedPolicyHolder.getFullName());
            editFieldPolicyHolderUsername.setText(selectedPolicyHolder.getUserName());
            editFieldPolicyHolderPassword.setText(selectedPolicyHolder.getPassword());
            editFieldPolicyHolderEmail.setText(selectedPolicyHolder.getEmail());
            editFieldPolicyHolderPhoneNumber.setText(selectedPolicyHolder.getPhoneNumber());
            editFieldPolicyHolderAddress.setText(selectedPolicyHolder.getAddress());
            editFieldPolicyHolderRole.setText(getRoleName(selectedPolicyHolder.getRoleId()));
        }
    }

    @FXML
    private void editFieldPolicyHolderConfirmBtnOnAction(ActionEvent event) {
        boolean isSuccess = updatePolicyHolder(editFieldPolicyHolderID.getText(),
                editFieldPolicyHolderFullName.getText(),
                bcryptPassword.hashBcryptPassword(editFieldPolicyHolderPassword.getText()),
                editFieldPolicyHolderEmail.getText(),
                editFieldPolicyHolderPhoneNumber.getText(),
                editFieldPolicyHolderAddress.getText());
        if (isSuccess) {
            editFieldPolicyHolderFullName.setText(editFieldPolicyHolderFullName.getText());
            editFieldPolicyHolderPassword.setText(editFieldPolicyHolderPassword.getText());
            editFieldPolicyHolderEmail.setText(editFieldPolicyHolderEmail.getText());
            editFieldPolicyHolderPhoneNumber.setText(editFieldPolicyHolderPhoneNumber.getText());
            editFieldPolicyHolderAddress.setText(editFieldPolicyHolderAddress.getText());
            for (PolicyHolder selectedPolicyHolder : policyHolderObservableList) {
                if (Objects.equals(editFieldPolicyHolderID.getText(), selectedPolicyHolder.getId())) {
                    selectedPolicyHolder.setFullName(editFieldPolicyHolderFullName.getText());
                    selectedPolicyHolder.setPassword(editFieldPolicyHolderPassword.getText());
                    selectedPolicyHolder.setEmail(editFieldPolicyHolderEmail.getText());
                    selectedPolicyHolder.setPhoneNumber(editFieldPolicyHolderPhoneNumber.getText());
                    selectedPolicyHolder.setAddress(editFieldPolicyHolderAddress.getText());
                    break;
                }
            }
            // Refresh the TableView
            policyHolderTableView.refresh();
        } else {
            System.out.println("isSuccess: " + false);
        }
    }

    // Adding delete function to the delete button
    @FXML
    private void editFieldPolicyHolderDeleteBtnOnAction(ActionEvent event) {
        boolean isSuccess = deletePolicyHolder(editFieldPolicyHolderID.getText());
        if (isSuccess) {
            for (PolicyHolder selectedPolicyHolder : policyHolderObservableList) {
                if (Objects.equals(editFieldPolicyHolderID.getText(), selectedPolicyHolder.getId())) {
                    policyHolderObservableList.remove(selectedPolicyHolder);
                    break;
                }
            }
            // Refresh the TableView
            policyHolderTableView.refresh();
        } else {
            System.out.println("isSuccess: " + false);
        }
    }

    @FXML
    private void policyHolderFormConfirmBtnOnAction(ActionEvent event) {
        String newPolicyHolderID = generatePolicyHolderID();
        String newInsuranceCardNum = generateInsuranceCardNum();
        String expirationDateString = addNewPolicyHolderCardExpriedDateField.getText(); // Assume you have a TextField for date input

        boolean isIdUnique = policyHolderObservableList.stream().noneMatch(policyHolder -> policyHolder.getId().equals(newPolicyHolderID));
        boolean isUsernameUnique = policyHolderObservableList.stream().noneMatch(policyHolder -> policyHolder.getUserName().equals(addNewPolicyHolderUsernameField.getText()));

        if (isIdUnique && isUsernameUnique) {
            PolicyHolder newPolicyHolder = new PolicyHolder();
            newPolicyHolder.setId(newPolicyHolderID);
            newPolicyHolder.setUserName(addNewPolicyHolderUsernameField.getText());
            newPolicyHolder.setFullName(addNewPolicyHolderFullNameField.getText());
            newPolicyHolder.setPassword(bcryptPassword.hashBcryptPassword(addNewPolicyHolderPassword.getText()));
            newPolicyHolder.setEmail(addNewPolicyHolderEmailField.getText());
            newPolicyHolder.setPhoneNumber(addNewPolicyHolderPhoneNumField.getText());
            newPolicyHolder.setAddress(addNewPolicyHolderAddressField.getText());
            newPolicyHolder.setRoleId(5);  // Setting role id for policy holder

            // Parse the expiration date
            java.sql.Date expirationDate = java.sql.Date.valueOf(expirationDateString);

            boolean isSuccess = addNewPolicyHolderAndCard(newPolicyHolder, newInsuranceCardNum, expirationDate);
            if (isSuccess) {
                policyHolderObservableList.add(newPolicyHolder);
                clearNewPolicyFolderFormFields();
            } else {
                System.out.println("Failed to add new user and insurance card.");
            }
        } else {
            if (!isIdUnique) System.out.println("Error: ID already exists.");
            if (!isUsernameUnique) System.out.println("Error: Username already exists.");
        }
    }
    // Clear the fields in the form to add a new policyholder
    private void clearNewPolicyFolderFormFields() {
        addNewPolicyHolderUsernameField.clear();
        addNewPolicyHolderFullNameField.clear();
        addNewPolicyHolderPassword.clear();
        addNewPolicyHolderEmailField.clear();
        addNewPolicyHolderPhoneNumField.clear();
        addNewPolicyHolderAddressField.clear();
        addNewPolicyHolderCardExpriedDateField.clear();
    }
    // Update the policyholder in the database
    public boolean updatePolicyHolder(String id, String fullName, String password, String email, String phoneNumber, String address) {
        try {
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
                    System.out.println("Policy Holder Information updated successfully!");
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Delete the policyholder from the database
    public boolean deletePolicyHolder(String id) {
        Connection conn = null;
        PreparedStatement deleteInsuranceCardsStmt = null;
        PreparedStatement deletePolicyHolderStmt = null;
        try {
            conn = databaseConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // First, delete dependent records in the insurance_card table
            String deleteInsuranceCardsQuery = "DELETE FROM insurance_card WHERE card_holder_id = ?";
            deleteInsuranceCardsStmt = conn.prepareStatement(deleteInsuranceCardsQuery);
            deleteInsuranceCardsStmt.setString(1, id);
            deleteInsuranceCardsStmt.executeUpdate();

            // Now, delete the policyholder from the users table
            String deletePolicyHolderQuery = "DELETE FROM users WHERE id = ?";
            deletePolicyHolderStmt = conn.prepareStatement(deletePolicyHolderQuery);
            deletePolicyHolderStmt.setString(1, id);
            int rowsDeleted = deletePolicyHolderStmt.executeUpdate();

            conn.commit(); // Commit the transaction

            if (rowsDeleted > 0) {
                System.out.println("Policy Holder and related insurance cards deleted successfully!");
                return true;
            } else {
                System.out.println("No Policy Holder found with the specified ID.");
                return false;
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // rollback transaction on error
                } catch (SQLException ex) {
                    System.out.println("Error rolling back transaction");
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (deleteInsuranceCardsStmt != null) deleteInsuranceCardsStmt.close();
                if (deletePolicyHolderStmt != null) deletePolicyHolderStmt.close();
                if (conn != null) {
                    conn.setAutoCommit(true); // Reset auto-commit to true
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private boolean addNewPolicyHolderAndCard(PolicyHolder policyHolder, String cardNumber, java.sql.Date expirationDate) {
        Connection conn = null;
        PreparedStatement insertUserStmt = null;
        PreparedStatement insertCardStmt = null;

        try {
            conn = databaseConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // Insert the new policyHolder
            String insertUserQuery = "INSERT INTO users (id, full_name, user_name, password, email, phone_number, address, role_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            insertUserStmt = conn.prepareStatement(insertUserQuery);
            insertUserStmt.setString(1, policyHolder.getId());
            insertUserStmt.setString(2, policyHolder.getFullName());
            insertUserStmt.setString(3, policyHolder.getUserName());
            insertUserStmt.setString(4, policyHolder.getPassword());
            insertUserStmt.setString(5, policyHolder.getEmail());
            insertUserStmt.setString(6, policyHolder.getPhoneNumber());
            insertUserStmt.setString(7, policyHolder.getAddress());
            insertUserStmt.setInt(8, policyHolder.getRoleId());
            insertUserStmt.executeUpdate();

            // Insert the corresponding insurance card
            String insertCardQuery = "INSERT INTO insurance_card (card_number, card_holder_id, policy_owner_id, expiration_date) VALUES (?, ?, ?, ?)";
            insertCardStmt = conn.prepareStatement(insertCardQuery);
            insertCardStmt.setString(1, cardNumber);
            insertCardStmt.setString(2, policyHolder.getId());
            insertCardStmt.setString(3, getIDFromUserName(LoginData.usernameLogin)); // Owner ID from login data
            insertCardStmt.setDate(4, expirationDate);
            insertCardStmt.executeUpdate();

            conn.commit(); // Commit the transaction
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback transaction on error
                } catch (SQLException ex) {
                    System.out.println("Error rolling back transaction");
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (insertUserStmt != null) insertUserStmt.close();
                if (insertCardStmt != null) insertCardStmt.close();
                if (conn != null) {
                    conn.setAutoCommit(true); // Reset auto-commit to true
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private String getIDFromUserName(String username) {
        String getIDQuery = "SELECT id FROM users WHERE user_name = ?";
        String userId= "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getIDQuery);
            preparedStatement.setString(1, LoginData.usernameLogin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getString("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userId;
    }

    public String getRoleName(int roleId) {
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

    // Function to create a random ID for the new policyholder
    private static String generatePolicyHolderID() {
        StringBuilder policyNumber = new StringBuilder("C");
        for (int i = 0; i < 7; i++) {
            policyNumber.append((int) (Math.random() * 10));
        }
        return policyNumber.toString();
    }

    // Function to create a random insurance card number for the new policyholder
    private static String generateInsuranceCardNum() {
        // Print a random insurance card number with format like of 10-digits using StringBuilder
        StringBuilder insuranceCardNumber = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            insuranceCardNumber.append((int) (Math.random() * 10));
        }
        return insuranceCardNumber.toString();
    }
}
