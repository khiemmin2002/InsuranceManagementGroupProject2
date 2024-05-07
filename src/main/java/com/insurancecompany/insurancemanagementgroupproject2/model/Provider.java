package com.insurancecompany.insurancemanagementgroupproject2.model;

public abstract class Provider {
    private String id;
    private String fullName;
    private String userName;
    private String email;
    private String phoneNumber;

    public Provider(String id, String fullName, String userName, String email, String phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
