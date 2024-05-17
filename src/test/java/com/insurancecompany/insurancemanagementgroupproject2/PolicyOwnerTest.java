package com.insurancecompany.insurancemanagementgroupproject2;

import com.insurancecompany.insurancemanagementgroupproject2.controller.POClaimController;
import com.insurancecompany.insurancemanagementgroupproject2.controller.PolicyOwnerMyPolicyHolderController;
import com.insurancecompany.insurancemanagementgroupproject2.controller.PolicyOwnerMyProfileController;
import com.insurancecompany.insurancemanagementgroupproject2.controller.PolicyOwnerMyDependentController;
import com.insurancecompany.insurancemanagementgroupproject2.model.Claim;
import com.insurancecompany.insurancemanagementgroupproject2.model.Dependent;
import com.insurancecompany.insurancemanagementgroupproject2.model.PolicyHolder;
import com.insurancecompany.insurancemanagementgroupproject2.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PolicyOwnerTest {
    @Mock
    private Connection connection;
    @Mock
    private ResultSet resultSet;
    @Mock
    private DatabaseConnection databaseConnection;

    // Controllers
    @InjectMocks
    private PolicyOwnerMyProfileController policyOwnerMyProfileController;
    @InjectMocks
    private PolicyOwnerMyPolicyHolderController policyOwnerMyPolicyHolderController;
    @InjectMocks
    private PolicyOwnerMyDependentController policyOwnerMyDependentController;
    @InjectMocks
    private POClaimController policyOwnerMyClaimController;

    @Mock
    private PreparedStatement preparedStatement;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(databaseConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(preparedStatement.executeUpdate()).thenReturn(1);
    }

    // PolicyOwner My Profile Controller Test
    @Test
    void getRoleNameSuccess() throws SQLException {
        int role = 1;
        String roleName = "test";

        // Mocking PreparedStatement behavior
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true); // Simulate that a result is found
        when(resultSet.getString("role")).thenReturn(roleName);

        // Logic to check for testing if true
        String roleNameTest = policyOwnerMyProfileController.getRoleName(role);
        assertEquals(roleNameTest, roleName);

        resultSet.close();
        preparedStatement.close();
    }

    @Test
    void getRoleNameFail() throws SQLException {
        int role = 1;
        String roleName = "test";

        // Mocking PreparedStatement behavior
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true); // Simulate that a result is found
        when(resultSet.getString("role")).thenReturn("wrong_role");

        // Logic to check for testing if true
        String roleNameTest = policyOwnerMyProfileController.getRoleName(role);
        assertNotEquals(roleNameTest, roleName);

        resultSet.close();
        preparedStatement.close();
    }

    @Test
    void updatePolicyOwner() throws SQLException {
        // Mocking data
        String id = "1";
        String fullName = "Test User";
        String password = "newpassword";
        String email = "test@example.com";
        String phoneNumber = "1234567890";
        String address = "123 Test Street";

        // Mocking PreparedStatement behavior
        when(preparedStatement.executeUpdate()).thenReturn(1); // Assuming one row is updated

        // Calling the method under test
        boolean isSuccess = policyOwnerMyProfileController.updatePolicyOwner(id, fullName, password, email, phoneNumber, address);

        // Verifying the result
        assertTrue(isSuccess);

        preparedStatement.close();
    }

    @Test
    public void testUpdatePolicyOwner_Failure() throws Exception {
        // Mocking data
        String id = "1";
        String fullName = "Test User";
        String password = "newpassword";
        String email = "test@example.com";
        String phoneNumber = "1234567890";
        String address = "123 Test Street";

        // Mocking PreparedStatement behavior for failure
        when(preparedStatement.executeUpdate()).thenReturn(0); // Assuming no rows are updated

        // Calling the method under test
        boolean isSuccess = policyOwnerMyProfileController.updatePolicyOwner(id, fullName, password, email, phoneNumber, address);

        // Verifying the result
        assertFalse(isSuccess);

        preparedStatement.close();
    }

    // PolicyOwner My PolicyHolder Controller Test
    @Test
    void updatePolicyHolderSuccess() throws SQLException {
        // Mocking data
        String id = "1";
        String fullName = "Test User";
        String password = "newpassword";
        String email = "test@example.com";
        String phoneNumber = "1234567890";
        String address = "123 Test Street";

        // Mocking PreparedStatement behavior
        when(preparedStatement.executeUpdate()).thenReturn(1); // Assuming one row is updated

        // Calling the method under test
        boolean isSuccess = policyOwnerMyPolicyHolderController.updatePolicyHolder(id, fullName, password, email, phoneNumber, address);

        // Verifying the result
        assertTrue(isSuccess);

        preparedStatement.close();
    }

    @Test
    void updatePolicyHolderFail() throws SQLException {
        // Mocking data
        String id = "1";
        String fullName = "Test User";
        String password = "newpassword";
        String email = "test@example.com";
        String phoneNumber = "1234567890";
        String address = "123 Test Street";

        // Mocking PreparedStatement behavior
        when(preparedStatement.executeUpdate()).thenReturn(0); // Assuming one row is updated

        // Calling the method under test
        boolean isSuccess = policyOwnerMyPolicyHolderController.updatePolicyHolder(id, fullName, password, email, phoneNumber, address);

        // Verifying the result
        assertFalse(isSuccess);

        preparedStatement.close();
    }

    @Test
    void getRoleNamePolicyHolderSuccess() throws SQLException {
        int role = 5;
        String roleName = "test";

        // Mocking PreparedStatement behavior
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true); // Simulate that a result is found
        when(resultSet.getString("role")).thenReturn(roleName);

        // Logic to check for testing if true
        String roleNameTest = policyOwnerMyPolicyHolderController.getRoleName(role);
        assertEquals(roleNameTest, roleName);

        resultSet.close();
        preparedStatement.close();
    }

    @Test
    void getRoleNamePolicyHolderFail() throws SQLException {
        int role = 5;
        String roleName = "test";

        // Mocking PreparedStatement behavior
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true); // Simulate that a result is found
        when(resultSet.getString("role")).thenReturn("wrong_role");

        // Logic to check for testing if true
        String roleNameTest = policyOwnerMyPolicyHolderController.getRoleName(role);
        assertNotEquals(roleNameTest, roleName);

        resultSet.close();
        preparedStatement.close();
    }

    // PolicyOwner My Dependent Controller Test
    // Test fetch dependent from database
    @Test
    void fetchDependentFromDatabaseSuccess() throws SQLException {
        String policyOwnerId = "policyOwner123";
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString("id")).thenReturn("123");
        when(resultSet.getString("full_name")).thenReturn("John Doe");
        when(resultSet.getString("user_name")).thenReturn("johndoe");
        when(resultSet.getString("password")).thenReturn("password");
        when(resultSet.getString("email")).thenReturn("johndoe@example.com");
        when(resultSet.getString("phone_number")).thenReturn("1234567890");
        when(resultSet.getString("address")).thenReturn("123 Main St");
        when(resultSet.getInt("role_id")).thenReturn(6);
        when(resultSet.getString("policy_holder_name")).thenReturn("Jane Smith");

        when(statement.executeQuery()).thenReturn(resultSet);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(databaseConnection.getConnection()).thenReturn(connection);

        ArrayList<Dependent> dependents = policyOwnerMyDependentController.fetchDependentFromDatabase();

        assertEquals(1, dependents.size());
        Dependent dependent = dependents.get(0);
        assertEquals("123", dependent.getId());
        assertEquals("John Doe", dependent.getFullName());
        assertEquals("johndoe", dependent.getUserName());
        assertEquals("password", dependent.getPassword());
        assertEquals("johndoe@example.com", dependent.getEmail());
        assertEquals("1234567890", dependent.getPhoneNumber());
        assertEquals("123 Main St", dependent.getAddress());
        assertEquals(6, dependent.getRoleId());
        assertEquals("Jane Smith", dependent.getPolicyHolderName());

        resultSet.close();
        statement.close();
    }

    @Test
    void fetchDependentFromDatabaseFailure() throws SQLException {
        String policyOwnerId = "policyOwner123";
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.next()).thenThrow(new SQLException());
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(databaseConnection.getConnection()).thenReturn(connection);

        ArrayList<Dependent> dependents = policyOwnerMyDependentController.fetchDependentFromDatabase();

        assertEquals(0, dependents.size());

        resultSet.close();
        statement.close();
    }

    // Add new dependent
    @Test
    void addNewDependentToDatabaseSuccess() throws SQLException {
        Dependent newDependent = new Dependent();
        newDependent.setId("newID");
        newDependent.setFullName("New Dependent");
        newDependent.setUserName("newusername");
        newDependent.setPassword("password");
        newDependent.setEmail("newemail@example.com");
        newDependent.setPhoneNumber("1234567890");
        newDependent.setAddress("123 New Street");
        newDependent.setRoleId(6);

        PreparedStatement insertUserStmt = mock(PreparedStatement.class);
        PreparedStatement insertDependentStmt = mock(PreparedStatement.class);
        when(insertUserStmt.executeUpdate()).thenReturn(1);
        when(insertDependentStmt.executeUpdate()).thenReturn(1);
        when(connection.prepareStatement(anyString())).thenReturn(insertUserStmt).thenReturn(insertDependentStmt);
        when(databaseConnection.getConnection()).thenReturn(connection);

        boolean isSuccess = policyOwnerMyDependentController.addNewDependentToDatabase(newDependent, "policyHolderID");
        assertTrue(isSuccess);

        verify(insertUserStmt, times(1)).executeUpdate();
        verify(insertDependentStmt, times(1)).executeUpdate();

        insertUserStmt.close();
        insertDependentStmt.close();
    }

    @Test
    void addNewDependentToDatabaseFailure() throws SQLException {
        Dependent newDependent = new Dependent();
        newDependent.setId("newID");
        newDependent.setFullName("New Dependent");
        newDependent.setUserName("newusername");
        newDependent.setPassword("password");
        newDependent.setEmail("newemail@example.com");
        newDependent.setPhoneNumber("1234567890");
        newDependent.setAddress("123 New Street");
        newDependent.setRoleId(6);

        PreparedStatement insertUserStmt = mock(PreparedStatement.class);
        PreparedStatement insertDependentStmt = mock(PreparedStatement.class);
        when(insertUserStmt.executeUpdate()).thenThrow(new SQLException());
        when(insertDependentStmt.executeUpdate()).thenReturn(1);
        when(connection.prepareStatement(anyString())).thenReturn(insertUserStmt).thenReturn(insertDependentStmt);
        when(databaseConnection.getConnection()).thenReturn(connection);

        boolean isSuccess = policyOwnerMyDependentController.addNewDependentToDatabase(newDependent, "policyHolderID");
        assertFalse(isSuccess);

        verify(insertUserStmt, times(1)).executeUpdate();
        verify(insertDependentStmt, times(0)).executeUpdate();

        insertUserStmt.close();
        insertDependentStmt.close();
    }

    // Update dependent
    @Test
    void updateDependentSuccess() throws SQLException {
        String id = "dependentID";
        String fullName = "Updated Name";
        String userName = "updatedusername";
        String password = "updatedpassword";
        String email = "updatedemail@example.com";
        String phoneNumber = "1234567890";
        String address = "456 Updated Street";

        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(databaseConnection.getConnection()).thenReturn(connection);

        boolean isSuccess = policyOwnerMyDependentController.updateDependent(id, fullName, userName, password, email, phoneNumber, address);
        assertTrue(isSuccess);

        verify(preparedStatement, times(1)).executeUpdate();

        preparedStatement.close();
    }

    @Test
    void updateDependentFailure() throws SQLException {
        String id = "dependentID";
        String fullName = "Updated Name";
        String userName = "updatedusername";
        String password = "updatedpassword";
        String email = "updatedemail@example.com";
        String phoneNumber = "1234567890";
        String address = "456 Updated Street";

        when(preparedStatement.executeUpdate()).thenReturn(0);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(databaseConnection.getConnection()).thenReturn(connection);

        boolean isSuccess = policyOwnerMyDependentController.updateDependent(id, fullName, userName, password, email, phoneNumber, address);
        assertFalse(isSuccess);

        verify(preparedStatement, times(1)).executeUpdate();

        preparedStatement.close();
    }

    // Delete dependent
    @Test
    void deleteDependentSuccess() throws SQLException {
        String dependentId = "dependentID";

        PreparedStatement deleteDocumentsStmt = mock(PreparedStatement.class);
        PreparedStatement deleteClaimsStmt = mock(PreparedStatement.class);
        PreparedStatement deleteUserStmt = mock(PreparedStatement.class);
        PreparedStatement deleteDependentStmt = mock(PreparedStatement.class);

        when(deleteDocumentsStmt.executeUpdate()).thenReturn(1);
        when(deleteClaimsStmt.executeUpdate()).thenReturn(1);
        when(deleteUserStmt.executeUpdate()).thenReturn(1);
        when(deleteDependentStmt.executeUpdate()).thenReturn(1);

        when(connection.prepareStatement("DELETE FROM documents WHERE claim_id IN (SELECT claim_id FROM claims WHERE insured_person = ?)")).thenReturn(deleteDocumentsStmt);
        when(connection.prepareStatement("DELETE FROM claims WHERE insured_person = ?")).thenReturn(deleteClaimsStmt);
        when(connection.prepareStatement("DELETE FROM users WHERE id = ?")).thenReturn(deleteUserStmt);
        when(connection.prepareStatement("DELETE FROM dependent WHERE dependent_id = ?")).thenReturn(deleteDependentStmt);
        when(databaseConnection.getConnection()).thenReturn(connection);

        boolean isSuccess = policyOwnerMyDependentController.deleteDependent(dependentId);
        assertTrue(isSuccess);

        verify(deleteDocumentsStmt, times(1)).executeUpdate();
        verify(deleteClaimsStmt, times(1)).executeUpdate();
        verify(deleteUserStmt, times(1)).executeUpdate();
        verify(deleteDependentStmt, times(1)).executeUpdate();

        deleteDocumentsStmt.close();
        deleteClaimsStmt.close();
        deleteUserStmt.close();
        deleteDependentStmt.close();
    }

    @Test
    void deleteDependentFailure() throws SQLException {
        String dependentId = "dependentID";

        PreparedStatement deleteDocumentsStmt = mock(PreparedStatement.class);
        PreparedStatement deleteClaimsStmt = mock(PreparedStatement.class);
        PreparedStatement deleteUserStmt = mock(PreparedStatement.class);
        PreparedStatement deleteDependentStmt = mock(PreparedStatement.class);

        when(deleteDocumentsStmt.executeUpdate()).thenThrow(new SQLException());
        when(deleteClaimsStmt.executeUpdate()).thenReturn(1);
        when(deleteUserStmt.executeUpdate()).thenReturn(1);
        when(deleteDependentStmt.executeUpdate()).thenReturn(1);

        when(connection.prepareStatement("DELETE FROM documents WHERE claim_id IN (SELECT claim_id FROM claims WHERE insured_person = ?)")).thenReturn(deleteDocumentsStmt);
        when(connection.prepareStatement("DELETE FROM claims WHERE insured_person = ?")).thenReturn(deleteClaimsStmt);
        when(connection.prepareStatement("DELETE FROM users WHERE id = ?")).thenReturn(deleteUserStmt);
        when(connection.prepareStatement("DELETE FROM dependent WHERE dependent_id = ?")).thenReturn(deleteDependentStmt);
        when(databaseConnection.getConnection()).thenReturn(connection);

        boolean isSuccess = policyOwnerMyDependentController.deleteDependent(dependentId);
        assertFalse(isSuccess);

        verify(deleteDocumentsStmt, times(1)).executeUpdate();
        verify(deleteClaimsStmt, times(0)).executeUpdate();
        verify(deleteUserStmt, times(0)).executeUpdate();
        verify(deleteDependentStmt, times(0)).executeUpdate();

        deleteDocumentsStmt.close();
        deleteClaimsStmt.close();
        deleteUserStmt.close();
        deleteDependentStmt.close();
    }

    // PolicyOwner My Claim Controller Test
    @Test
    void testFetchPolicyHoldersAndDependentsFromDatabase() throws SQLException {
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString("id")).thenReturn("123");
        when(resultSet.getString("full_name")).thenReturn("John Doe");
        when(resultSet.getString("user_name")).thenReturn("johndoe");
        when(resultSet.getString("password")).thenReturn("password");
        when(resultSet.getString("email")).thenReturn("johndoe@example.com");
        when(resultSet.getString("phone_number")).thenReturn("1234567890");
        when(resultSet.getString("address")).thenReturn("123 Main St");
        when(resultSet.getInt("role_id")).thenReturn(5);

        ArrayList<User> users = policyOwnerMyClaimController.fetchPolicyHoldersAndDependentsFromDatabase();
        assertEquals(1, users.size());
        assertTrue(users.get(0) instanceof PolicyHolder);
        assertEquals("123", users.get(0).getId());

        resultSet.close();
        preparedStatement.close();
    }

    @Test
    void testFetchClaimsFromDatabase() throws SQLException {
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString("claim_id")).thenReturn("1");
        when(resultSet.getString("insured_person")).thenReturn("John Doe");
        when(resultSet.getString("card_number")).thenReturn("1234567890");
        when(resultSet.getDate("claim_date")).thenReturn(new java.sql.Date(System.currentTimeMillis()));
        when(resultSet.getDate("exam_date")).thenReturn(new java.sql.Date(System.currentTimeMillis()));
        when(resultSet.getDouble("claim_amount")).thenReturn(1000.00);
        when(resultSet.getString("status")).thenReturn("NEW");
        when(resultSet.getString("bank_name")).thenReturn("Bank");
        when(resultSet.getString("bank_user_name")).thenReturn("John Doe");
        when(resultSet.getString("bank_number")).thenReturn("123456");

        ArrayList<Claim> claims = policyOwnerMyClaimController.fetchClaimsFromDatabase();
        assertEquals(1, claims.size());
        assertEquals("1", claims.get(0).getId());

        resultSet.close();
        preparedStatement.close();
    }

    @Test
    void testAddNewClaimToDatabase() throws SQLException {
        Claim claim = new Claim();
        claim.setId("1");
        claim.setInsuredPerson("John Doe");
        claim.setCardNumber("1234567890");
        claim.setClaimAmount(1000.00);
        claim.setBankName("Bank");
        claim.setBankUserName("John Doe");
        claim.setBankNumber("123456");

        boolean isSuccess = policyOwnerMyClaimController.addNewClaimToDatabase(claim);
        assertTrue(isSuccess);

        preparedStatement.close();
    }

    @Test
    void testUpdateClaimBankDetails() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(1);
        boolean isSuccess = policyOwnerMyClaimController.updateClaimBankDetails("1", "Bank", "John Doe", "123456");
        assertTrue(isSuccess);

        preparedStatement.close();
    }

    @Test
    void testDeleteClaimFromDatabase() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(1);
        boolean isSuccess = policyOwnerMyClaimController.deleteClaimFromDatabase("1");
        assertTrue(isSuccess);

        preparedStatement.close();
    }
}
