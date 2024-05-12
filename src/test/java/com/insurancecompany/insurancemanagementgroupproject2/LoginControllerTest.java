package com.insurancecompany.insurancemanagementgroupproject2;

import com.insurancecompany.insurancemanagementgroupproject2.controller.BcryptPassword;
import com.insurancecompany.insurancemanagementgroupproject2.controller.LoginController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

    private BcryptPassword bcryptPassword;

    @BeforeEach
    public void setUp() {
        bcryptPassword = mock(BcryptPassword.class);
    }

    @Test
    public void testValidateLoginShouldReturnRoleId() {
        BcryptPassword bcryptPassword = new BcryptPassword();
        BcryptPassword spyBcryptPassword = Mockito.spy(bcryptPassword);
        LoginController loginController = new LoginController();
        String validUsername = "admin1234";
        String validPassword = "admin123";
        String bcryptPasswordHash = bcryptPassword.hashBcryptPassword(validPassword);
        int expectedRoleId = 1;
        Mockito.when(spyBcryptPassword.hashBcryptPassword(validPassword)).thenReturn(bcryptPasswordHash);
        Mockito.when(spyBcryptPassword.verifyHashedPassword(bcryptPasswordHash, validPassword)).thenReturn(true);
        int actualRoleId = loginController.validateLogin(validUsername, validPassword);
        assertEquals(expectedRoleId, actualRoleId);
    }

    @Test
    public void testValidateLoginShouldReturnMinusOne() {
        LoginController loginController = new LoginController();
        String invalidUsername = "admin1234";
        String invalidPassword = "admin";
        when(bcryptPassword.verifyHashedPassword(anyString(), anyString())).thenReturn(false);
        int actualRoleId = loginController.validateLogin(invalidUsername, invalidPassword);
        assertEquals(-1, actualRoleId);
    }

}
