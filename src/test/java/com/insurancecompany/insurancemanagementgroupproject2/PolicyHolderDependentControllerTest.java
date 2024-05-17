package com.insurancecompany.insurancemanagementgroupproject2;


import com.insurancecompany.insurancemanagementgroupproject2.controller.policyholder.PolicyHolderDependentController;
import com.insurancecompany.insurancemanagementgroupproject2.model.Dependent;
import com.insurancecompany.insurancemanagementgroupproject2.model.LoginData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PolicyHolderDependentControllerTest {
    @InjectMocks
    private PolicyHolderDependentController controller;
    @Mock
    DatabaseConnection databaseConnection;
    @Mock
    Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    @BeforeEach
    void setUp() throws SQLException {
        databaseConnection = mock(DatabaseConnection.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        controller = new PolicyHolderDependentController(databaseConnection, connection);

        // Setup the connection and preparedStatement with leniency
        lenient().when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        lenient().when(preparedStatement.executeQuery()).thenReturn(resultSet);
        lenient().when(resultSet.next()).thenReturn(true, false);
        lenient().when(resultSet.getString("id")).thenReturn("C1924845");
        lenient().when(resultSet.getString("full_name")).thenReturn("John Doe");
        lenient().when(resultSet.getString("user_name")).thenReturn("johndoe");
        lenient().when(resultSet.getString("password")).thenReturn("hashedpassword");
        lenient().when(resultSet.getString("email")).thenReturn("johndoe@example.com");
        lenient().when(resultSet.getString("phone_number")).thenReturn("1234567890");
        lenient().when(resultSet.getString("address")).thenReturn("1234 Main St");
    }

    @Test
    void testFetchDependents() throws SQLException {

        String testUserName = "johndoe";
        setLoginDataUsername(testUserName);

        // Execute the method under test
        List<Dependent> dependents = controller.fetchDependents();

        // Assertions to check if the fetched dependents match the expected data
        assertEquals(1, dependents.size());
        assertEquals("C1924845", dependents.get(0).getId());
        assertEquals("John Doe", dependents.get(0).getFullName());
        assertEquals("johndoe", dependents.get(0).getUserName());
        assertEquals("hashedpassword", dependents.get(0).getPassword());
        assertEquals("johndoe@example.com", dependents.get(0).getEmail());
        assertEquals("1234567890", dependents.get(0).getPhoneNumber());
        assertEquals("1234 Main St", dependents.get(0).getAddress());
    }

    private void setLoginDataUsername(String username) {
        // You need to ensure LoginData.usernameLogin is set for the test environment
        LoginData.usernameLogin = username;
    }

//    @Test
//    void testAddDependent() throws SQLException {
//        // Setup
//        PreparedStatement findStmt = mock(PreparedStatement.class);
//        PreparedStatement userStmt = mock(PreparedStatement.class);
//        PreparedStatement depStmt = mock(PreparedStatement.class);
//        ResultSet rs = mock(ResultSet.class);
//
//        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
//        // Method call
//        String id = "C2346231";
//        String fullName = "Jane Smith";
//        String userName = "janesmith";
//        String password = "testpassword";
//        String email = "testemail@gmail.com";
//        String phoneNumber = "23421234";
//        String address = "123 SG";
//        int roleId = 6;
//
//
//        boolean result = controller.addDependent(id, fullName, userName, password, email, phoneNumber, address, roleId);
//
//        // Verification
//        verify(connection).prepareStatement("SELECT id FROM users WHERE user_name = ?");
//        verify(findStmt).setString(1, LoginData.usernameLogin);
//        verify(findStmt).executeQuery();
//
//
//        verify(connection).prepareStatement("INSERT INTO users (id, full_name, user_name, password, role_id ,email, phone_number, address) " +
//                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
//        verify(userStmt).setString(1, id);
//        verify(userStmt).setString(2, fullName);
//        verify(userStmt).setString(3, userName);
//        verify(userStmt).setString(4, password);
//        verify(userStmt).setInt(5, roleId);
//        verify(userStmt).setString(6, email);
//        verify(userStmt).setString(7, phoneNumber);
//        verify(userStmt).setString(8, address);
//        verify(userStmt).executeUpdate();;
//
//        verify(connection).prepareStatement("INSERT INTO dependent (dependent_id, policy_holder_id) VALUES (?, ?)");
//        verify(depStmt).setString(1, id);
//        verify(depStmt).setString(2, "C1000114");
//        verify(depStmt).executeUpdate();
//        assertTrue(result,"Result must be true");
//    }

    @Test
    void testAddDependent() throws SQLException {
        PreparedStatement findStmt = mock(PreparedStatement.class);
        PreparedStatement userStmt = mock(PreparedStatement.class);
        PreparedStatement depStmt = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(connection.prepareStatement("SELECT id FROM users WHERE user_name = ?")).thenReturn(findStmt);
        when(connection.prepareStatement("INSERT INTO users (id, full_name, user_name, password, role_id, email, phone_number, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"))
                .thenReturn(userStmt);
        when(connection.prepareStatement("INSERT INTO dependent (dependent_id, policy_holder_id) VALUES (?, ?)")).thenReturn(depStmt);

        // Setup for findStmt
        when(findStmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);  // Assuming a user is found
        when(rs.getString("id")).thenReturn("C1000114");  // Returning a fake user ID

        // Act
        String id = "C2346231";
        String fullName = "Jane Smith";
        String userName = "janesmith";
        String password = "testpassword";
        String email = "testemail@gmail.com";
        String phoneNumber = "23421234";
        String address = "123 SG";
        int roleId = 6;
        controller.addDependent(id, fullName, userName, password, email, phoneNumber, address, roleId);

        // Verification
        verify(findStmt).setString(1, LoginData.usernameLogin);
        verify(findStmt).executeQuery();

        verify(userStmt).setString(1, id);
        verify(userStmt).setString(2, fullName);
        verify(userStmt).setString(3, userName);
        verify(userStmt).setString(4, password);
        verify(userStmt).setInt(5, roleId);
        verify(userStmt).setString(6, email);
        verify(userStmt).setString(7, phoneNumber);
        verify(userStmt).setString(8, address);
        verify(userStmt).executeUpdate();

        verify(depStmt).setString(1, id);
        verify(depStmt).setString(2, "C1000114");
        verify(depStmt).executeUpdate();
    }





    @Test
    void testDeleteDependent() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> controller.deleteDependent("   "));

        verify(preparedStatement, times(1)).setString(1, "1");
        verify(preparedStatement, times(2)).executeUpdate(); // One for deleting from dependent, another for deleting from users
    }

    @Test
    void testUpdateDependent() throws SQLException {
        String id = "C2346231";
        String password = "newPassword";
        String email = "newemail@example.com";
        String phoneNumber = "1234567890";
        String address = "123 New St";
        when(preparedStatement.executeUpdate()).thenReturn(1); // Assume the update affects one row.

        controller.updateDependent(id, password, email, phoneNumber, address);

        verify(preparedStatement, times(1)).setString(1, password);
        verify(preparedStatement, times(1)).setString(2, email);
        verify(preparedStatement, times(1)).setString(3, phoneNumber);
        verify(preparedStatement, times(1)).setString(4, address);
        verify(preparedStatement, times(1)).setString(5, id);
        verify(preparedStatement, times(1)).executeUpdate();
    }




}