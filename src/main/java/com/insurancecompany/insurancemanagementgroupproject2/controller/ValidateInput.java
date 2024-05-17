package com.insurancecompany.insurancemanagementgroupproject2.controller;
/**
 * @author team 5
 */
import com.insurancecompany.insurancemanagementgroupproject2.model.User;
import javafx.collections.ObservableList;

public class ValidateInput {
    // Method to validate if email is correctly input
    public boolean isValidEmail(String email) {
        // Regular expression for basic email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    // Method to validate if phone number (10 number total) is correctly input
    public boolean isValidPhoneNumber(String phoneNumber) {
        // Regular expression for basic phone number validation
        String phoneRegex = "^\\d{10}$";
        return phoneNumber.matches(phoneRegex);
    }
    // Method to check for username to see if it unique (avoid username duplication in database)
    public boolean isUserNameUnique(ObservableList<User> observableList, String username){
        return observableList.stream().noneMatch(user -> user.getUserName().equals(username));
    }
}
