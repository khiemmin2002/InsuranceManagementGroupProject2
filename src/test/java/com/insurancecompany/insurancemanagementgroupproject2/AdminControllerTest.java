package com.insurancecompany.insurancemanagementgroupproject2;

import com.insurancecompany.insurancemanagementgroupproject2.controller.AdminController;
import com.insurancecompany.insurancemanagementgroupproject2.controller.BcryptPassword;
import com.insurancecompany.insurancemanagementgroupproject2.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminControllerTest {

    @Mock
    private DatabaseConnection databaseConnection;

    @Mock
    private Connection connection;

    @Mock
    private BcryptPassword bcryptPassword;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(databaseConnection.getConnection()).thenReturn(connection);
    }

    @Test
    public void testFetchRolesFromDatabase() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("role")).thenReturn("Manager");
        when(statement.executeQuery()).thenReturn(resultSet);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        ArrayList<Role> roles = adminController.fetchRolesFromDatabase();

        assertEquals(1, roles.size());
        Role role = roles.get(0);
        assertEquals(1, role.getId());
        assertEquals("Manager", role.getRoleName());
    }

    @Test
    public void testAddUser() throws SQLException {
        User user = new User();
        user.setId("123");
        user.setFullName("John Doe");
        user.setUserName("johndoe");
        user.setPassword("password");
        user.setEmail("johndoe@example.com");
        user.setPhoneNumber("1234567890");
        user.setAddress("123 Main St");
        user.setRoleId(2);

        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);
        when(bcryptPassword.hashBcryptPassword(anyString())).thenReturn("hashedPassword");

        assertTrue(adminController.addUser(user));
    }

    @Test
    public void testUpdateUser() throws SQLException {
        String id = "123";
        String fullName = "John Doe";
        String password = "newPassword";
        String email = "newemail@example.com";
        String phoneNumber = "9876543210";
        String address = "456 Oak St";

        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);
        when(bcryptPassword.hashBcryptPassword(anyString())).thenReturn("hashedPassword");

        assertTrue(adminController.updateUser(id, fullName, password, email, phoneNumber, address));
    }

    @Test
    public void testDeleteUser() throws SQLException {
        String id = "123";

        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);

        assertTrue(adminController.deleteUser(id));
    }

    @Test
    public void testFetchUsersFromDatabase() throws SQLException {
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
        when(resultSet.getInt("role_id")).thenReturn(2);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        ArrayList<User> users = adminController.fetchUsersFromDatabase();

        assertEquals(1, users.size());
        User user = users.get(0);
        assertEquals("123", user.getId());
        assertEquals("John Doe", user.getFullName());
        assertEquals("johndoe", user.getUserName());
        assertEquals("password", user.getPassword());
        assertEquals("johndoe@example.com", user.getEmail());
        assertEquals("1234567890", user.getPhoneNumber());
        assertEquals("123 Main St", user.getAddress());
        assertEquals(2, user.getRoleId());
    }

    @Test
    public void testDeleteInsuranceCardInformation() throws SQLException {
        String cardNumber = "1234567890";

        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);

        assertTrue(adminController.deleteInsuranceCardInformation(cardNumber));
    }

    @Test
    public void testUpdateInsuranceCardInformation() throws SQLException, ParseException {
        String cardNumber = "1234567890";
        String expDate = "2024-05-01";

        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);

        assertTrue(adminController.updateInsuranceCardInformation(cardNumber, expDate));
    }

    @Test
    public void testGetProfileDashboardInformation() throws SQLException {
        String username = "johndoe";
        int roleId = 2;

        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("id")).thenReturn("123");
        when(resultSet.getString("user_name")).thenReturn("johndoe");
        when(resultSet.getString("email")).thenReturn("johndoe@example.com");
        when(resultSet.getString("full_name")).thenReturn("John Doe");
        when(resultSet.getInt("role_id")).thenReturn(2);
        when(resultSet.getString("password")).thenReturn("password");
        when(resultSet.getString("phone_number")).thenReturn("1234567890");
        when(resultSet.getString("address")).thenReturn("123 Main St");
        when(statement.executeQuery()).thenReturn(resultSet);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        User user = adminController.getProfileDashboardInformation(username, roleId);

        assertNotNull(user);
        assertEquals("123", user.getId());
        assertEquals("johndoe", user.getUserName());
        assertEquals("johndoe@example.com", user.getEmail());
        assertEquals("John Doe", user.getFullName());
        assertEquals(2, user.getRoleId());
        assertEquals("password", user.getPassword());
        assertEquals("1234567890", user.getPhoneNumber());
        assertEquals("123 Main St", user.getAddress());
    }

    @Test
    public void testGetNameForUser() throws SQLException {
        String userId = "123";

        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("full_name")).thenReturn("John Doe");
        when(statement.executeQuery()).thenReturn(resultSet);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        String name = adminController.getNameForUser(userId);

        assertEquals("John Doe", name);
    }

    @Test
    public void testGetRoleName() throws SQLException {
        int roleId = 2;

        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("role")).thenReturn("Manager");
        when(statement.executeQuery()).thenReturn(resultSet);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        String roleName = adminController.getRoleName(roleId);

        assertEquals("Manager", roleName);
    }

    @Test
    public void testDisplayTotalProviders() throws SQLException {
        PreparedStatement managerStatement = mock(PreparedStatement.class);
        ResultSet managerResultSet = mock(ResultSet.class);
        when(managerResultSet.next()).thenReturn(true);
        when(managerResultSet.getInt("managerCount")).thenReturn(5);
        when(managerStatement.executeQuery()).thenReturn(managerResultSet);

        PreparedStatement surveyorStatement = mock(PreparedStatement.class);
        ResultSet surveyorResultSet = mock(ResultSet.class);
        when(surveyorResultSet.next()).thenReturn(true);
        when(surveyorResultSet.getInt("surveyorCount")).thenReturn(3);
        when(surveyorStatement.executeQuery()).thenReturn(surveyorResultSet);

        when(connection.prepareStatement(anyString())).thenReturn(managerStatement).thenReturn(surveyorStatement);

        int totalProvider = adminController.displayTotalProviders();

        assertEquals(8, totalProvider);
    }
    @Test
    public void testDisplayTotalCustomers() throws SQLException {
        PreparedStatement policyOwnerStatement = mock(PreparedStatement.class);
        PreparedStatement policyHolderStatement = mock(PreparedStatement.class);
        PreparedStatement dependentStatement = mock(PreparedStatement.class);
        ResultSet policyOwnerResultSet = mock(ResultSet.class);
        ResultSet policyHolderResultSet = mock(ResultSet.class);
        ResultSet dependentResultSet = mock(ResultSet.class);

        when(connection.prepareStatement("SELECT COUNT(*) as policyOwnerCount FROM users WHERE role_id = ?"))
                .thenReturn(policyOwnerStatement);
        when(connection.prepareStatement("SELECT COUNT(*) as policyHolderCount FROM users WHERE role_id = ?"))
                .thenReturn(policyHolderStatement);
        when(connection.prepareStatement("SELECT COUNT(*) as dependentCount FROM users WHERE role_id = ?"))
                .thenReturn(dependentStatement);

        when(policyOwnerStatement.executeQuery()).thenReturn(policyOwnerResultSet);
        when(policyHolderStatement.executeQuery()).thenReturn(policyHolderResultSet);
        when(dependentStatement.executeQuery()).thenReturn(dependentResultSet);

        when(policyOwnerResultSet.next()).thenReturn(true);
        when(policyOwnerResultSet.getInt("policyOwnerCount")).thenReturn(10);
        when(policyHolderResultSet.next()).thenReturn(true);
        when(policyHolderResultSet.getInt("policyHolderCount")).thenReturn(20);
        when(dependentResultSet.next()).thenReturn(true);
        when(dependentResultSet.getInt("dependentCount")).thenReturn(30);

        int totalCustomers = adminController.displayTotalCustomers();
        assertEquals(60, totalCustomers);
    }

    @Test
    public void testDisplayTotalInsuranceCards() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(connection.prepareStatement("SELECT COUNT(*) AS cardCount FROM insurance_card")).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("cardCount")).thenReturn(100);

        int totalInsuranceCards = adminController.displayTotalInsuranceCards();
        assertEquals(100, totalInsuranceCards);
    }

    @Test
    public void testDisplayTotalClaims() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(connection.prepareStatement("SELECT COUNT(*) AS claimCount FROM claims")).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("claimCount")).thenReturn(50);

        int totalClaims = adminController.displayTotalClaims();
        assertEquals(50, totalClaims);
    }
}
