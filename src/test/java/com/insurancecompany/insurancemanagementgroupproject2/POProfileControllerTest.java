package com.insurancecompany.insurancemanagementgroupproject2;

import com.insurancecompany.insurancemanagementgroupproject2.controller.policyowner.POProfileController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class POProfileControllerTest {
    @Mock
    private Connection connection;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private POProfileController POProfileController;
    @Mock
    private PreparedStatement preparedStatement;
    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getRoleNameSuccess() throws SQLException {
        int role = 1;
        String role_name = "test";

        // Mocking PreparedStatement behavior
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        // Set behaviour for return
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true); // Simulate that a result is found
        when(resultSet.getString("role")).thenReturn(role_name);

        //Logic to check for testing if true
        String role_name_test = POProfileController.getRoleName(role);
        assertEquals(role_name_test,role_name);
    }

    @Test
    void getRoleNameFail() throws SQLException {
        int role = 1;
        String role_name = "test";

        // Mocking PreparedStatement behavior
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        // Set behaviour for return
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true); // Simulate that a result is found
        when(resultSet.getString("role")).thenReturn("wrong_role");

        //Logic to check for testing if true
        String role_name_test = POProfileController.getRoleName(role);
        assertNotEquals(role_name_test, role_name);
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
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Assuming one row is updated

        // Calling the method under test
        boolean isSuccess = POProfileController.updatePolicyOwner(id, fullName, password, email, phoneNumber, address);

        // Verifying the result
        assertTrue(isSuccess);
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
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0); // Assuming no rows are updated

        // Calling the method under test
        boolean isSuccess = POProfileController.updatePolicyOwner(id, fullName, password, email, phoneNumber, address);

        // Verifying the result
        assertFalse(isSuccess);
    }
}