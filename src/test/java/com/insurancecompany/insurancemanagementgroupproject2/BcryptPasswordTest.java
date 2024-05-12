package com.insurancecompany.insurancemanagementgroupproject2;

import com.insurancecompany.insurancemanagementgroupproject2.controller.BcryptPassword;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BcryptPasswordTest {

    @Test
    public void testHashBcryptPassword() {
        BcryptPassword bcryptPassword = new BcryptPassword();
        String password = "testpassword";
        String hashedPassword = bcryptPassword.hashBcryptPassword(password);
        assertNotNull(hashedPassword);
        assertNotEquals(password, hashedPassword);
    }

    @Test
    public void testVerifyHashedPasswordShouldReturnTrue() {
        BcryptPassword bcryptPassword = new BcryptPassword();
        String password = "testpassword";
        String hashedPassword = bcryptPassword.hashBcryptPassword(password);
        boolean isValid = bcryptPassword.verifyHashedPassword(hashedPassword, password);
        assertTrue(isValid);
    }

    @Test
    public void testVerifyHashedPasswordShouldReturnFalse() {
        BcryptPassword bcryptPassword = new BcryptPassword();
        String password = "testpassword";
        String hashedPassword = bcryptPassword.hashBcryptPassword(password);
        String incorrectPassword = "wrongpassword";
        boolean isValid = bcryptPassword.verifyHashedPassword(hashedPassword, incorrectPassword);
        assertFalse(isValid);
    }
}
