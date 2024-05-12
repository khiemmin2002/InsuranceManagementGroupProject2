package com.insurancecompany.insurancemanagementgroupproject2.controller;

public class ValidateInput {

    public static boolean isValidEmail(String email) {
        // Regular expression for basic email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Regular expression for basic phone number validation
        String phoneRegex = "^\\d{10}$";
        return phoneNumber.matches(phoneRegex);
    }
}
