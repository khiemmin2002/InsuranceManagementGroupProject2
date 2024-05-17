package com.insurancecompany.insurancemanagementgroupproject2;

import com.insurancecompany.insurancemanagementgroupproject2.controller.PolicyHolderDependentController;
import com.insurancecompany.insurancemanagementgroupproject2.model.Dependent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
         when(databaseConnection.getConnection()).thenReturn(connection);
         when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
         when(preparedStatement.executeQuery()).thenReturn(resultSet);
     }

    @Test
    void testFetchDependents() throws SQLException {
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString("id")).thenReturn("1");
        when(resultSet.getString("full_name")).thenReturn("John Doe");
        when(resultSet.getString("user_name")).thenReturn("johndoe");
        when(resultSet.getString("password")).thenReturn("hashedpassword");
        when(resultSet.getString("email")).thenReturn("johndoe@example.com");
        when(resultSet.getString("phone_number")).thenReturn("1234567890");
        when(resultSet.getString("address")).thenReturn("1234 Main St");

        List<Dependent> result = controller.fetchDependents("username");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("John Doe", result.get(0).getFullName());

        verify(preparedStatement, times(1)).setString(1, "username");
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(preparedStatement, times(1)).close();
    }
    private String generatedRandomUserId() {
        StringBuilder dependentID = new StringBuilder("C");
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            dependentID.append(random.nextInt(10));
        }
        return dependentID.toString();
    }

    @Test
    void testAddDependent() throws SQLException {
         PreparedStatement preparedStatement1 = mock(PreparedStatement.class);
         when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Arrange
        String randomUserId = generatedRandomUserId();
        String full_name = "Jane Smith";
        String user_name = "janesmith";
        String password = "testpassword";
        String email = "testemail@gmail.com";
        String phone_number = "23421234";
        String address ="123 SG";
        int roleId = 6;
        Dependent dependent = new Dependent(randomUserId, )

        boolean result = controller.addDependent();

    }


    @Test
    void testDeleteDependent() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> controller.deleteDependent("1"));

        verify(preparedStatement, times(1)).setString(1, "1");
        verify(preparedStatement, times(2)).executeUpdate(); // One for deleting from dependent, another for deleting from users
    }

    @Test
    void testUpdateDependent() throws SQLException {
        String id = "1";
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
