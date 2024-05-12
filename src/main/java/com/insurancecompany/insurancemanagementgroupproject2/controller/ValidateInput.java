package com.insurancecompany.insurancemanagementgroupproject2.controller;

import com.insurancecompany.insurancemanagementgroupproject2.model.User;
import javafx.collections.ObservableList;

public class ValidateInput {

    public boolean isValidEmail(String email) {
        // Regular expression for basic email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        // Regular expression for basic phone number validation
        String phoneRegex = "^\\d{10}$";
        return phoneNumber.matches(phoneRegex);
    }

    public boolean isUserIdUnique(ObservableList<User> observableList, String text) {
        return observableList.stream().noneMatch(user -> user.getId().equals(text));
    }

    public boolean isUserNameUnique(ObservableList<User> observableList, String username){
        return observableList.stream().noneMatch(user -> user.getUserName().equals(username));
    }
}
