package com.insurancecompany.insurancemanagementgroupproject2.controller.policyowner;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.model.Dependent;
import com.insurancecompany.insurancemanagementgroupproject2.model.LoginData;
import com.insurancecompany.insurancemanagementgroupproject2.model.PolicyHolder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PODependentController implements Initializable {

    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    @FXML
    private TextArea addNewDependentAddressField;

    @FXML
    private Button addNewDependentBtn;

    @FXML
    private TextField addNewDependentEmailField;

    @FXML
    private AnchorPane addNewDependentForm;

    @FXML
    private TextField addNewDependentFullNameField;

    @FXML
    private TextField addNewDependentPasswordField;

    @FXML
    private TextField addNewDependentPhoneNumberField;

    @FXML
    private ComboBox<PolicyHolder> addNewDependentPolicyHolderField;

    @FXML
    private TextField addNewDependentUsernameField;

    @FXML
    private TableColumn<Dependent, String> dependentAddressCol;

    @FXML
    private TableColumn<Dependent, String> dependentEmailCol;

    @FXML
    private Button dependentFormCancelBtn;

    @FXML
    private Button dependentFormConfirmBtn;

    @FXML
    private TableColumn<Dependent, String> dependentFullNameCol;

    @FXML
    private TableColumn<Dependent, String> dependentIDCol;

    @FXML
    private TableColumn<Dependent, String> dependentPasswordCol;

    @FXML
    private TableColumn<Dependent, String> dependentPhoneNumberCol;

    @FXML
    private TableColumn<?, ?> dependentPolicyHolderCol;

    @FXML
    private TableColumn<Dependent, Integer> dependentRoleCol;

    @FXML
    private TableView<Dependent> dependentTableView;

    @FXML
    private TableColumn<Dependent, String> dependentUserNameCol;

    @FXML
    private TextArea editFieldDependentAddress;

    @FXML
    private Button editFieldDependentConfirmBtn;

    @FXML
    private Button editFieldDependentDeleteBtn;

    @FXML
    private TextField editFieldDependentEmail;

    @FXML
    private TextField editFieldDependentFullName;

    @FXML
    private TextField editFieldDependentID;

    @FXML
    private TextField editFieldDependentPassword;

    @FXML
    private TextField editFieldDependentPhoneNumber;

    @FXML
    private TextField editFieldDependentRole;

    @FXML
    private TextField editFieldDependentUsername;

    private ObservableList<PolicyHolder> policyHolderObservableList = FXCollections.observableArrayList();
    private ObservableList<Dependent> dependentObservableList = FXCollections.observableArrayList();

    @FXML
    private void addNewDependentBtnOnAction(ActionEvent event) {
        addNewDependentForm.setVisible(true);
    }

    @FXML
    private void dependentFormCancelBtnOnAction(ActionEvent event) {
        addNewDependentForm.setVisible(false);
    }

    // Confirm button for adding a new dependent
    @FXML
    private void dependentFormConfirmBtnOnAction(ActionEvent event) {
        PolicyHolder selectedPolicyHolder = addNewDependentPolicyHolderField.getValue();
        if (selectedPolicyHolder == null) {
            System.out.println("No Policy Holder selected.");
            return;
        }

        String newDependentID = generateDependentID();
        boolean isIDUnique = dependentObservableList.stream().noneMatch(dependent -> dependent.getId().equals(newDependentID));
        boolean isUsernameUnique = dependentObservableList.stream().noneMatch(dependent -> dependent.getUserName().equals(addNewDependentUsernameField.getText()));

        if (isIDUnique && isUsernameUnique) {
            Dependent newDependent = new Dependent();
            newDependent.setId(newDependentID);
            newDependent.setUserName(addNewDependentUsernameField.getText());
            newDependent.setFullName(addNewDependentFullNameField.getText());
            newDependent.setPassword(addNewDependentPasswordField.getText());
            newDependent.setEmail(addNewDependentEmailField.getText());
            newDependent.setPhoneNumber(addNewDependentPhoneNumberField.getText());
            newDependent.setAddress(addNewDependentAddressField.getText());
            newDependent.setRoleId(6);  // Assuming 6 is the role ID for dependents

            boolean isSuccess = addNewDependentToDatabase(newDependent, selectedPolicyHolder.getId());
            if (isSuccess) {
                dependentObservableList.add(newDependent);
                clearDependentFormFields();
                dependentTableView.refresh();
            } else {
                System.out.println("Failed to add new dependent.");
            }
        } else {
            if (!isIDUnique) System.out.println("Error: Dependent ID already exists.");
            if (!isUsernameUnique) System.out.println("Error: Dependent username already exists.");
        }
    }

    @FXML
    private void editFieldDependentConfirmBtnOnAction(ActionEvent event) {
        String id = editFieldDependentID.getText();
        String fullName = editFieldDependentFullName.getText();
        String userName = editFieldDependentUsername.getText();
        String password = editFieldDependentPassword.getText();
        String email = editFieldDependentEmail.getText();
        String phoneNumber = editFieldDependentPhoneNumber.getText();
        String address = editFieldDependentAddress.getText();

        boolean isSuccess = updateDependent(id, fullName, userName, password, email, phoneNumber, address);
        if (isSuccess) {
            Dependent selectedDependent = dependentTableView.getSelectionModel().getSelectedItem();
            if (selectedDependent != null) {
                selectedDependent.setFullName(fullName);
                selectedDependent.setUserName(userName);
                selectedDependent.setPassword(password);
                selectedDependent.setEmail(email);
                selectedDependent.setPhoneNumber(phoneNumber);
                selectedDependent.setAddress(address);
                dependentTableView.refresh();
            }
        } else {
            System.out.println("Failed to update dependent.");
        }
    }

    @FXML
    private void editFieldDependentDeleteBtnOnAction(ActionEvent event) {
        Dependent selectedDependent = dependentTableView.getSelectionModel().getSelectedItem();
        if (selectedDependent != null) {
            boolean isSuccess = deleteDependent(selectedDependent.getId());
            if (isSuccess) {
                dependentObservableList.remove(selectedDependent);
                dependentTableView.refresh();
                System.out.println("Dependent successfully deleted.");
            } else {
                System.out.println("Failed to delete the dependent.");
            }
        } else {
            System.out.println("No dependent selected for deletion.");
        }
    }

    // Handle selecting a row in the TableView -> Populate the fields in the edit form
    @FXML
    private void selectDependentRow(MouseEvent event) {
        Dependent selectedDependent = dependentTableView.getSelectionModel().getSelectedItem();
        if (selectedDependent != null) {
            editFieldDependentID.setText(selectedDependent.getId());
            editFieldDependentFullName.setText(selectedDependent.getFullName());
            editFieldDependentUsername.setText(selectedDependent.getUserName());
            editFieldDependentPassword.setText(selectedDependent.getPassword());
            editFieldDependentEmail.setText(selectedDependent.getEmail());
            editFieldDependentPhoneNumber.setText(selectedDependent.getPhoneNumber());
            editFieldDependentAddress.setText(selectedDependent.getAddress());
            editFieldDependentRole.setText(String.valueOf(selectedDependent.getRoleId()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dependentObservableList.addAll(fetchDependentFromDatabase());
        policyHolderObservableList.addAll(fetchPolicyHoldersFromDatabase());
        setupTableViewColumns();
        setupPolicyHolderComboBox();
    }

    // Set up table view columns
    // Setup for displaying the policyholder's name in the TableView
    private void setupTableViewColumns() {
        dependentIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        dependentFullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        dependentUserNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        dependentPasswordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        dependentEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        dependentPhoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        dependentAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        dependentRoleCol.setCellValueFactory(new PropertyValueFactory<>("roleId"));
        // Setting up new column for displaying the policyholder's name
        dependentPolicyHolderCol.setCellValueFactory(new PropertyValueFactory<>("policyHolderName"));

        dependentTableView.setItems(dependentObservableList);
    }

    private void setupPolicyHolderComboBox() {
        addNewDependentPolicyHolderField.setCellFactory(lv -> new ListCell<PolicyHolder>() {
            @Override
            protected void updateItem(PolicyHolder item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getFullName() + " (" + item.getId() + ")");
            }
        });

        addNewDependentPolicyHolderField.setConverter(new StringConverter<PolicyHolder>() {
            @Override
            public String toString(PolicyHolder object) {
                return object == null ? null : object.getFullName() + " (" + object.getId() + ")";
            }

            @Override
            public PolicyHolder fromString(String string) {
                return addNewDependentPolicyHolderField.getItems().stream()
                        .filter(item -> (item.getFullName() + " (" + item.getId() + ")").equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        // Populate ComboBox
        addNewDependentPolicyHolderField.getItems().setAll(fetchPolicyHoldersFromDatabase());
    }


    // Fetch dependent data from the database
    private ArrayList<Dependent> fetchDependentFromDatabase() {
        ArrayList<Dependent> dependentArrayList = new ArrayList<>();
        try {
            String policyOwnerId = getIDFromUserName(LoginData.usernameLogin);

            // Updated query to include the policyholder's full name
            String query = "SELECT d.*, ph.full_name AS policy_holder_name FROM users d " +
                    "INNER JOIN dependent dp ON d.id = dp.dependent_id " +
                    "INNER JOIN users ph ON dp.policy_holder_id = ph.id " +
                    "INNER JOIN insurance_card ic ON ph.id = ic.card_holder_id " +
                    "WHERE ic.policy_owner_id = ? AND d.role_id = 6";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, policyOwnerId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Dependent dependent = new Dependent();
                dependent.setId(resultSet.getString("id"));
                dependent.setFullName(resultSet.getString("full_name"));
                dependent.setUserName(resultSet.getString("user_name"));
                dependent.setPassword(resultSet.getString("password"));
                dependent.setEmail(resultSet.getString("email"));
                dependent.setPhoneNumber(resultSet.getString("phone_number"));
                dependent.setAddress(resultSet.getString("address"));
                dependent.setRoleId(resultSet.getInt("role_id"));
                dependent.setPolicyHolderName(resultSet.getString("policy_holder_name")); // Assuming you have a setter for this
                dependentArrayList.add(dependent);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dependentArrayList;
    }

    private ArrayList<PolicyHolder> fetchPolicyHoldersFromDatabase() {
        ArrayList<PolicyHolder> policyHolders = new ArrayList<>();
        try {
            String policyOwnerId = getIDFromUserName(LoginData.usernameLogin);
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM users WHERE role_id = 5 AND id IN " +
                            "(SELECT card_holder_id FROM insurance_card WHERE policy_owner_id = ?)");
            stmt.setString(1, policyOwnerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                policyHolders.add(new PolicyHolder(
                        rs.getString("id"),
                        rs.getString("full_name"),
                        rs.getString("user_name"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        rs.getInt("role_id")
                ));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return policyHolders;
    }

    // Add a new dependent to the database
    private boolean addNewDependentToDatabase(Dependent dependent, String policyHolderId) {
        String insertUserQuery = "INSERT INTO users (id, full_name, user_name, password, email, phone_number, address, role_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String insertDependentQuery = "INSERT INTO dependent (dependent_id, policy_holder_id) VALUES (?, ?)";

        try (Connection conn = databaseConnection.getConnection()) {
            conn.setAutoCommit(false);  // Start transaction

            try (PreparedStatement userStmt = conn.prepareStatement(insertUserQuery);
                 PreparedStatement dependentStmt = conn.prepareStatement(insertDependentQuery)) {

                // Insert into users
                userStmt.setString(1, dependent.getId());
                userStmt.setString(2, dependent.getFullName());
                userStmt.setString(3, dependent.getUserName());
                userStmt.setString(4, dependent.getPassword());
                userStmt.setString(5, dependent.getEmail());
                userStmt.setString(6, dependent.getPhoneNumber());
                userStmt.setString(7, dependent.getAddress());
                userStmt.setInt(8, dependent.getRoleId());
                userStmt.executeUpdate();

                // Insert into dependent
                dependentStmt.setString(1, dependent.getId());
                dependentStmt.setString(2, policyHolderId);
                dependentStmt.executeUpdate();

                conn.commit();  // Commit transaction
                return true;
            } catch (SQLException ex) {
                conn.rollback();  // Rollback transaction on error
                ex.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update the dependent in the database
    private boolean updateDependent(String id, String fullName, String userName, String password, String email, String phoneNumber, String address) {
        String updateQuery = "UPDATE users SET full_name = ?, user_name = ?, password = ?, email = ?, phone_number = ?, address = ? WHERE id = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setString(1, fullName);
            stmt.setString(2, userName);
            stmt.setString(3, password);
            stmt.setString(4, email);
            stmt.setString(5, phoneNumber);
            stmt.setString(6, address);
            stmt.setString(7, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete the dependent in the database
    private boolean deleteDependent(String dependentId) {
        String deleteUserQuery = "DELETE FROM users WHERE id = ?";
        String deleteDependentQuery = "DELETE FROM dependent WHERE dependent_id = ?";

        try (Connection conn = databaseConnection.getConnection()) {
            conn.setAutoCommit(false);  // Start transaction

            try (PreparedStatement stmtUser = conn.prepareStatement(deleteUserQuery);
                 PreparedStatement stmtDependent = conn.prepareStatement(deleteDependentQuery)) {

                // Delete from dependent table
                stmtDependent.setString(1, dependentId);
                stmtDependent.executeUpdate();

                // Delete from users table
                stmtUser.setString(1, dependentId);
                stmtUser.executeUpdate();

                conn.commit();  // Commit transaction
                return true;
            } catch (SQLException ex) {
                conn.rollback();  // Rollback on error
                ex.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get the ID from the username
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

    // Function to create a random ID for the new policyholder
    private static String generateDependentID() {
        StringBuilder policyNumber = new StringBuilder("C");
        for (int i = 0; i < 7; i++) {
            policyNumber.append((int) (Math.random() * 10));
        }
        return policyNumber.toString();
    }

    // Clear the fields in the dependent form
    private void clearDependentFormFields() {
        addNewDependentUsernameField.clear();
        addNewDependentFullNameField.clear();
        addNewDependentPasswordField.clear();
        addNewDependentEmailField.clear();
        addNewDependentPhoneNumberField.clear();
        addNewDependentAddressField.clear();
        addNewDependentPolicyHolderField.getSelectionModel().clearSelection();
    }

}
