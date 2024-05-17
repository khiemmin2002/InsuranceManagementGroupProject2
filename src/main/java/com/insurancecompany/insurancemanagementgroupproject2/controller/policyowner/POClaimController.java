package com.insurancecompany.insurancemanagementgroupproject2.controller.policyowner;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class POClaimController implements Initializable {

    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    // FXML

    @FXML
    private Button addNewClaimBtn;

    @FXML
    private void addNewClaimBtnOnAction(ActionEvent event) {
        addNewClaimForm.setVisible(true);
    }

    // New Claim Form
    @FXML
    private AnchorPane addNewClaimForm;

    @FXML
    private Button newClaimFormCancelBtn;

    @FXML
    private void newClaimFormCancelBtnOnAction(ActionEvent event) {
        addNewClaimForm.setVisible(false);
    }

    @FXML
    private ListView<File> newClaimFormListView;

    @FXML
    private Button newClaimFormAddDocBtn;

    @FXML
    private void newClaimFormAddDocBtnOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Documents");
        // Set extension filter for PDF files
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(filter);

        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(newClaimFormListView.getScene().getWindow());
        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                if (!newClaimFormDocumentList.contains(file)) {
                    newClaimFormDocumentList.add(file);
                }
            }
            newClaimFormListView.refresh(); // Refresh the ListView to reflect changes
        }
    }

    @FXML
    private ComboBox<User> newClaimFormInsuredPersonField;

    @FXML
    private TextField newClaimFormBankNameField;

    @FXML
    private TextField newClaimFormBankNumberField;

    @FXML
    private TextField newClaimFormBankUserNameField;

    @FXML
    private TextField newClaimFormClaimAmountField;




    private ObservableList<User> insuredPersonObservableList = FXCollections.observableArrayList();
    private ObservableList<File> newClaimFormDocumentList = FXCollections.observableArrayList();
    private ObservableList<Claim> claimObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        insuredPersonObservableList.addAll(fetchPolicyHoldersAndDependentsFromDatabase());
        claimObservableList.addAll(fetchClaimsFromDatabase());
        setupNewClaimFormDocumentListView();
        setupInsuredPersonComboBox();
        setupClaimTableView();
    }

    // Fetching data from database

    // PolicyHolders and Dependents
    private ArrayList<User> fetchPolicyHoldersAndDependentsFromDatabase() {
        ArrayList<User> users = new ArrayList<>();
        try {
            String policyOwnerId = getIDFromUserName(LoginData.usernameLogin);
            // Fetch policyholders
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM users WHERE role_id = 5 AND id IN " +
                            "(SELECT card_holder_id FROM insurance_card WHERE policy_owner_id = ?)");
            stmt.setString(1, policyOwnerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(new PolicyHolder(
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

            // Fetch dependents
            stmt = connection.prepareStatement(
                    "SELECT u.* FROM users u " +
                            "JOIN dependent d ON u.id = d.dependent_id " +
                            "JOIN insurance_card ic ON d.policy_holder_id = ic.card_holder_id " +
                            "WHERE ic.policy_owner_id = ?");
            stmt.setString(1, policyOwnerId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(new Dependent(
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
        return users;
    }


    // Dependent
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

    // PolicyHolders
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

    // Claims
    public ArrayList<Claim> fetchClaimsFromDatabase() {
        ArrayList<Claim> claimList = new ArrayList<>();
        try {
            // Fetch the ID of the policy owner from the login data
            String policyOwnerId = getIDFromUserName(LoginData.usernameLogin);

            String queryClaims = """
                SELECT c.* FROM claims c
                JOIN users u ON c.insured_person = u.id
                LEFT JOIN insurance_card ic ON u.id = ic.card_holder_id
                WHERE ic.policy_owner_id = ? OR u.id IN (
                    SELECT dependent_id FROM dependent WHERE policy_holder_id IN (
                        SELECT card_holder_id FROM insurance_card WHERE policy_owner_id = ?
                    )
                )
        """;
            PreparedStatement statement = connection.prepareStatement(queryClaims);
            statement.setString(1, policyOwnerId);
            statement.setString(2, policyOwnerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Claim claim = new Claim();
                claim.setId(resultSet.getString("claim_id"));
                claim.setInsuredPerson(resultSet.getString("insured_person"));
                claim.setCardNumber(resultSet.getString("card_number"));
                claim.setClaimDate(resultSet.getDate("claim_date"));
                claim.setExamDate(resultSet.getDate("exam_date"));
                claim.setClaimAmount(resultSet.getDouble("claim_amount"));
                claim.setStatus(resultSet.getString("status"));
                claim.setBankName(resultSet.getString("bank_name"));
                claim.setBankUserName(resultSet.getString("bank_user_name"));
                claim.setBankNumber(resultSet.getString("bank_number"));
                claimList.add(claim);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return claimList;
    }

    // Adding claim functions
    // Setting new claim form document list view
    private void setupNewClaimFormDocumentListView() {
        newClaimFormListView.setItems(newClaimFormDocumentList);
        newClaimFormListView.setCellFactory(param -> new ListCell<File>() {
            private final HBox hBox = new HBox();
            private final Label label = new Label();
            private final Button deleteButton = new Button("Delete");

            {
                hBox.setSpacing(10);
                hBox.getChildren().addAll(label, deleteButton);
                deleteButton.setOnAction(e -> {
                    File item = getItem();
                    getListView().getItems().remove(item);
                });
            }

            @Override
            protected void updateItem(File item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    label.setText(item.getName());
                    setGraphic(hBox);
                }
            }
        });
    }

    // Rename the files
    private String renameAndSaveFile(File originalFile) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(formatter);


        String originalFileName = originalFile.getName();
        String baseName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
        String extension = getFileExtension(originalFile);

        String newFileName = formattedDate + "_" + baseName + extension;;
        System.out.println("File renamed to: " + newFileName);
        return newFileName;

    }

    // Get the file extension
    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");

        if (lastIndexOf == -1) {
            return "";
        }
        return name.substring(lastIndexOf);
    }

    // Save documents
    private void saveDocuments(String claimId) {
        for (File file : newClaimFormDocumentList) {
            String documentName = renameAndSaveFile(file);
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO documents (claim_id, document_name) VALUES (?, ?)");
                preparedStatement.setString(1, claimId);
                preparedStatement.setString(2, documentName);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Setting new claim form insured person field
    private void setupInsuredPersonComboBox() {
        newClaimFormInsuredPersonField.setCellFactory(lv -> new ListCell<User>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getFullName() + " (" + item.getId() + ")");
            }
        });

        newClaimFormInsuredPersonField.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User object) {
                return object == null ? null : object.getFullName() + " (" + object.getId() + ")";
            }

            @Override
            public User fromString(String string) {
                return newClaimFormInsuredPersonField.getItems().stream()
                        .filter(item -> (item.getFullName() + " (" + item.getId() + ")").equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        // Populate ComboBox with both policyholders and dependents
        newClaimFormInsuredPersonField.getItems().setAll(fetchPolicyHoldersAndDependentsFromDatabase());
    }



    @FXML
    private void newClaimFormConfirmBtnOnAction(ActionEvent event) {
        String newClaimNumber = generateClaimNumber();
        User selectedInsuredPerson = newClaimFormInsuredPersonField.getValue();
        if (selectedInsuredPerson == null) {
            System.out.println("Please select an insured person");
            return;
        }

        boolean isClaimNumberUnique = claimObservableList.stream().noneMatch(claim -> claim.getId().equals(newClaimNumber));

        if (isClaimNumberUnique) {
            Claim newClaim = new Claim();
            newClaim.setId(newClaimNumber);
            newClaim.setInsuredPerson(selectedInsuredPerson.getId());
            newClaim.setCardNumber(fetchCardNumberForInsuredPerson(selectedInsuredPerson.getId()));
            newClaim.setClaimAmount(Double.parseDouble(newClaimFormClaimAmountField.getText()));
            newClaim.setBankName(newClaimFormBankNameField.getText());
            newClaim.setBankUserName(newClaimFormBankUserNameField.getText());
            newClaim.setBankNumber(newClaimFormBankNumberField.getText());
            newClaim.setDocuments(newClaimFormDocumentList.toString());

            // Save the claim to the database and the documents
            boolean isSuccess = addNewClaimToDatabase(newClaim);

            if (isSuccess) {
                claimObservableList.add(newClaim);
                addNewClaimForm.setVisible(false);
                saveDocuments(newClaimNumber);
            } else {
                System.out.println("Error adding claim to database");
            }
        } else {
            System.out.println("Claim number is not unique");
        }

    }

    private String fetchCardNumberForInsuredPerson(String userId) {
        try {
            String query = """
            SELECT ic.card_number FROM users u
            LEFT JOIN dependent d ON u.id = d.dependent_id
            LEFT JOIN insurance_card ic ON ic.card_holder_id = COALESCE(d.policy_holder_id, u.id)
            WHERE u.id = ?
        """;
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("card_number");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private boolean addNewClaimToDatabase(Claim claim) {
        String insertClaimQuery = "INSERT INTO claims (claim_id, insured_person, card_number, exam_date, claim_date, claim_amount, status, bank_name, bank_user_name, bank_number) VALUES (?, ?, ?, NULL, NULL, ?, 'NEW', ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertClaimQuery);
            preparedStatement.setString(1, claim.getId());
            preparedStatement.setString(2, claim.getInsuredPerson());
            preparedStatement.setString(3, claim.getCardNumber());
            preparedStatement.setDouble(4, claim.getClaimAmount());
            preparedStatement.setString(5, claim.getBankName());
            preparedStatement.setString(6, claim.getBankUserName());
            preparedStatement.setString(7, claim.getBankNumber());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Getting claim functions
    @FXML
    private TableColumn<Claim, String> claimBankNameCol;

    @FXML
    private TableColumn<Claim, String> claimBankNumCol;

    @FXML
    private TableColumn<Claim, String> claimBankUserNameCol;

    @FXML
    private TableColumn<Claim, String> claimCardNumCol;

    @FXML
    private TableColumn<Claim, Date> claimClaimDateCol;

    @FXML
    private TableColumn<Claim, Date> claimExamDateCol;

    @FXML
    private TableColumn<Claim, String> claimIDCol;

    @FXML
    private TableColumn<Claim, String> claimInsuredPersonCol;

    @FXML
    private TableColumn<Claim, String> claimStatusCol;

    @FXML
    private TableView<Claim> claimTableView;

    // Setting up table view for claims
    private void setupClaimTableView() {
        claimIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        claimInsuredPersonCol.setCellValueFactory(new PropertyValueFactory<>("insuredPerson"));
        claimCardNumCol.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
        claimExamDateCol.setCellValueFactory(new PropertyValueFactory<>("examDate"));
        claimClaimDateCol.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
        claimStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        claimBankNameCol.setCellValueFactory(new PropertyValueFactory<>("bankName"));
        claimBankUserNameCol.setCellValueFactory(new PropertyValueFactory<>("bankUserName"));
        claimBankNumCol.setCellValueFactory(new PropertyValueFactory<>("bankNumber"));

        claimTableView.setItems(claimObservableList);
    }

    // Updating claim functions
    @FXML
    private TextField editFieldClaimBankName;

    @FXML
    private TextField editFieldClaimBankNumber;

    @FXML
    private TextField editFieldClaimBankUser;

    @FXML
    private TextField editFieldClaimCardNumber;

    @FXML
    private Button editFieldClaimConfirmBtn;

    @FXML
    private TextField editFieldClaimDate;

    @FXML
    private Button editFieldClaimDeleteBtn;

    @FXML
    private TextField editFieldClaimExamDate;

    @FXML
    private TextField editFieldClaimID;

    @FXML
    private TextField editFieldClaimInsuredPerson;

    @FXML
    private TextField editFieldClaimStatus;

    @FXML
    private void selectClaimRow(MouseEvent event) {
        Claim selectedClaim = claimTableView.getSelectionModel().getSelectedItem();
        if (selectedClaim != null) {
            editFieldClaimID.setText(selectedClaim.getId());
            editFieldClaimInsuredPerson.setText(selectedClaim.getInsuredPerson());
            editFieldClaimCardNumber.setText(selectedClaim.getCardNumber());

            // Handle potential null dates safely
            editFieldClaimExamDate.setText(selectedClaim.getExamDate() != null ? selectedClaim.getExamDate().toString() : "N/A");
            editFieldClaimDate.setText(selectedClaim.getClaimDate() != null ? selectedClaim.getClaimDate().toString() : "N/A");

            editFieldClaimStatus.setText(selectedClaim.getStatus());
            editFieldClaimBankName.setText(selectedClaim.getBankName());
            editFieldClaimBankUser.setText(selectedClaim.getBankUserName());
            editFieldClaimBankNumber.setText(selectedClaim.getBankNumber());
        }
    }

    @FXML
    private void editFieldClaimConfirmBtnOnAction(ActionEvent event) {
        String bankName = editFieldClaimBankName.getText();
        String bankUser = editFieldClaimBankUser.getText();
        String bankNumber = editFieldClaimBankNumber.getText();

        String claimId = editFieldClaimID.getText(); // Ensure you have a way to get the claimId

        boolean isUpdated = updateClaimBankDetails(claimId, bankName, bankUser, bankNumber);
        if (isUpdated) {
            // Find and update the claim in the observable list
            Claim updatedClaim = claimObservableList.stream()
                    .filter(claim -> claim.getId().equals(claimId))
                    .findFirst()
                    .orElse(null);
            if (updatedClaim != null) {
                updatedClaim.setBankName(bankName);
                updatedClaim.setBankUserName(bankUser);
                updatedClaim.setBankNumber(bankNumber);
                claimTableView.refresh();
            }
        } else {
            System.out.println("Failed to update claim bank details.");
        }
    }


    private boolean updateClaimBankDetails(String claimId, String bankName, String bankUser, String bankNumber) {
        String updateQuery = "UPDATE claims SET bank_name = ?, bank_user_name = ?, bank_number = ? WHERE claim_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, bankName);
            statement.setString(2, bankUser);
            statement.setString(3, bankNumber);
            statement.setString(4, claimId);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Deleting claim functions
    @FXML
    void editFieldClaimDeleteBtnOnAction(ActionEvent event) {
        Claim selectedClaim = claimTableView.getSelectionModel().getSelectedItem();
        if (selectedClaim != null) {
            deleteClaimFromDatabase(selectedClaim.getId());
            claimObservableList.remove(selectedClaim);
        }
    }

    private void deleteClaimFromDatabase(String claimId) {
        try {
            String deleteQuery = "DELETE FROM claims WHERE claim_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, claimId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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

    // Generate a random claim number with format fxxxxxxxxxx
    private String generateClaimNumber() {
        StringBuilder claimNumber = new StringBuilder("f");
        for (int i = 0; i < 10; i++) {
            claimNumber.append((int) (Math.random() * 10));
        }
        return claimNumber.toString();
    }
}
