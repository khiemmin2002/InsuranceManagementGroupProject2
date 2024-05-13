package com.insurancecompany.insurancemanagementgroupproject2;

import com.insurancecompany.insurancemanagementgroupproject2.controller.ValidateInput;
import com.insurancecompany.insurancemanagementgroupproject2.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidateInputTest {
    private ValidateInput validateInput;
    private ObservableList<User> userList;
    @BeforeEach
    void setUp() {
        validateInput = new ValidateInput();
        userList = FXCollections.observableArrayList(
                new User("1", "Test User 1", "testuser1", "password1", "testuser1@example.com", "1234567890", "123 HCM", 2),
                new User("2", "Test User 2", "testuser2", "password2", "testuser2@example.com", "9876543210", "456 HCM", 5)
        );
    }

    @Test
    void isValidEmail() {
        assertTrue(validateInput.isValidEmail("test@gmail.com"));
    }

    @Test
    void isInvalidEmail() {
        assertFalse(validateInput.isValidEmail("testmail.com"));
    }

    @Test
    void isValidPhoneNumber() {
        assertTrue(validateInput.isValidPhoneNumber("0912345678"));
    }

    @Test
    void isInvalidPhoneNumber() {
        assertFalse(validateInput.isValidPhoneNumber("091234567"));
    }

    @Test
    void isUserNameUnique() {
        assertTrue(validateInput.isUserNameUnique(userList,"testuser3"));
    }

    @Test
    void isUserNameUniqueInvalid() {
        assertFalse(validateInput.isUserNameUnique(userList,"testuser2"));
    }
}