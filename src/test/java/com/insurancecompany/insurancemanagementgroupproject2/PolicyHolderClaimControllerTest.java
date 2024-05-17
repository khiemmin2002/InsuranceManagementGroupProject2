package com.insurancecompany.insurancemanagementgroupproject2;

import com.insurancecompany.insurancemanagementgroupproject2.controller.policyholder.PolicyHolderClaimController;
import com.insurancecompany.insurancemanagementgroupproject2.model.Claim;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PolicyHolderClaimControllerTest {
    @InjectMocks
    private PolicyHolderClaimController controller;
    @Mock
    DatabaseConnection databaseConnection;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    //Mock data to test
    private final String CLAIM_ID = "testClaimId";
    private final String USER_NAME = "testUserName";
    private final String INSURED_PERSON = "testInsuredPerson";
    private final String INSURED_PERSON_ID = "C0000000";
    private final String CARD_NUMBER = "testCardNumber";
    private final String STATUS = "NEW";
    private final Date EXAM_DATE = null;
    private final Date CLAIM_DATE = null;
    private final double CLAIM_AMOUNT = 1000.0;
    private final String BANK_NAME = "testBank";
    private final String BANK_USER_NAME = "testBankUser";
    private final String BANK_NUMBER = "testBankNumber";
    private final List<String> DOCUMENT_LIST = Arrays.asList("testdocument1.pdf", "testdocument2.pdf");
    private Claim claim = new Claim(CLAIM_ID,INSURED_PERSON,CARD_NUMBER,EXAM_DATE,CLAIM_DATE,CLAIM_AMOUNT,STATUS,BANK_NAME,BANK_NUMBER,BANK_USER_NAME);

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(databaseConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @Test
    void deleteClaimDocuments() throws SQLException {
        // Prepare the mock return (successful operation mock)
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        // Check value to see if operation success
        boolean doAble = controller.deleteClaimDocuments(CLAIM_ID);
        assertTrue(doAble);
    }

    @Test
    void deleteClaim() throws SQLException {
        // Prepare the mock return (successful operation mock)
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        // Check value to see if operation success
        boolean doAble = controller.deleteClaim(CLAIM_ID);
        assertTrue(doAble);
    }

    @Test
    void updateClaim() throws SQLException {
        // Prepare the mock return (successful operation mock)
        when(preparedStatement.executeUpdate()).thenReturn(1);
        // Check value to see if operation success
        boolean doAble = controller.updateClaim(CLAIM_ID, INSURED_PERSON_ID, CARD_NUMBER, CLAIM_AMOUNT, BANK_NAME, BANK_USER_NAME, BANK_NUMBER);
        assertTrue(doAble);
    }

    @Test
    void updateDocumentDetails() throws SQLException {
        // Prepare the mock return (successful operation mock)
        when(preparedStatement.executeUpdate()).thenReturn(1);
        // Check value to see if operation success
        boolean doAble = controller.updateDocumentDetails(CLAIM_ID, DOCUMENT_LIST);
        assertTrue(doAble);
    }
}