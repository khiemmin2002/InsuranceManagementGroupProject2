package com.insurancecompany.insurancemanagementgroupproject2;

import com.insurancecompany.insurancemanagementgroupproject2.controller.SurveyorController;
import com.insurancecompany.insurancemanagementgroupproject2.model.Surveyor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SurveyorControllerTest {
    @Mock
    private DatabaseConnection databaseConnection;

    @Mock
    private Connection connection;

    @InjectMocks
    private SurveyorController surveyorController;

    @BeforeEach
    public void setUp() {
        databaseConnection = mock(DatabaseConnection.class);
        connection = mock(Connection.class);
        surveyorController = new SurveyorController(databaseConnection,connection);
    }

    @Test
    public void fetchSurveyorTest() throws SQLException {
        //Create mock result
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getString("id")).thenReturn("1", "2");
        when(resultSet.getString("full_name")).thenReturn("Test User 1", "Test User 2");
        when(resultSet.getString("user_name")).thenReturn("testuser", "testuser");
        when(resultSet.getString("password")).thenReturn("password1", "password2");
        when(resultSet.getString("email")).thenReturn("test1@example.com", "test2@example.com");
        when(resultSet.getString("phone_number")).thenReturn("123456789", "987654321");
        when(resultSet.getString("address")).thenReturn("123 HCM", "123 HCM");
        when(connection.createStatement()).thenReturn(mock(Statement.class));
        when(connection.createStatement().executeQuery(any())).thenReturn(resultSet);

        // Call the method under test
        List<Surveyor> surveyors = surveyorController.fetchSurveyor();

        // Assertions
        assertEquals(2, surveyors.size());
        assertEquals("1", surveyors.get(0).getId());
        assertEquals("Test User 1", surveyors.get(0).getFullName());
        assertEquals("testuser", surveyors.get(0).getUserName());
        assertEquals("password1", surveyors.get(0).getPassword());
        assertEquals("test1@example.com", surveyors.get(0).getEmail());
        assertEquals("123456789", surveyors.get(0).getPhoneNumber());
        assertEquals("123 HCM", surveyors.get(0).getAddress());

        assertEquals("2", surveyors.get(1).getId());
        assertEquals("Test User 2", surveyors.get(1).getFullName());
        assertEquals("testuser", surveyors.get(1).getUserName());
        assertEquals("password2", surveyors.get(1).getPassword());
        assertEquals("test2@example.com", surveyors.get(1).getEmail());
        assertEquals("987654321", surveyors.get(1).getPhoneNumber());
        assertEquals("123 HCM", surveyors.get(1).getAddress());
    }

    @Test
    void createNewSurveyor() throws SQLException{
        //Create mock prepared statement
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        //Create mock data
        String id = "1";
        String full_name = "Test User";
        String user_name = "testuser";
        String password = "password";
        String email = "test@example.com";
        String phone_number = "123456789";
        String address = "123 HCM";
        //Check
        boolean result = surveyorController.createNewSurveyor(id, full_name, user_name, password, email, phone_number, address);

        // Verify that the correct SQL query was executed
        verify(connection).prepareStatement("INSERT INTO users VALUES (?,?,?,?, 2,?,?,?)");

        // Verify that the prepared statement was executed
        verify(preparedStatement).setString(1, id);
        verify(preparedStatement).setString(2, full_name);
        verify(preparedStatement).setString(3, user_name);
        verify(preparedStatement).setString(4, password);
        verify(preparedStatement).setString(5, email);
        verify(preparedStatement).setString(6, phone_number);
        verify(preparedStatement).setString(7, address);
        verify(preparedStatement).execute();

        // Assert the result
        assertTrue(result,"Result must be true");
    }

    @Test
    void updateSurveyorInformation() throws SQLException {
        //Create mock prepared statement
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        //Create mock data
        String id = "1";
        String full_name = "Test User";
        String user_name = "testuser";
        String password = "password";
        String email = "test@example.com";
        String phone_number = "123456789";
        String address = "123 HCM";
        // Call the method under test
        boolean result = surveyorController.updateSurveyorInformation(id, full_name, email, phone_number, address);
        // Verify that the correct SQL query was executed
        verify(connection).prepareStatement("UPDATE users SET full_name = ?, email = ?, phone_number = ?, address = ? WHERE id = ?");
        // Verify that the prepared statement was executed
        verify(preparedStatement).setString(1, full_name);
        verify(preparedStatement).setString(2, email);
        verify(preparedStatement).setString(3, phone_number);
        verify(preparedStatement).setString(4, address);
        verify(preparedStatement).setString(5, id);
        verify(preparedStatement).execute();
        // Assert the result
        assertTrue(result, "Result must be true");
    }

    @Test
    void removeSurveyor() throws SQLException {
        //Create mock prepared statement
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        //Create mock data
        String userId = "1";
        // Call the method under test
        boolean result = surveyorController.removeSurveyor(userId);
        // Verify that the correct SQL query was executed
        verify(connection).prepareStatement("DELETE FROM users WHERE id = ?");
        // Verify that the prepared statement was executed
        verify(preparedStatement).setString(1, userId);
        verify(preparedStatement).execute();
        // Assert the result
        assertTrue(result, "Result must be true");
    }
}