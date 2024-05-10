package com.insurancecompany.insurancemanagementgroupproject2.model;

public class Dependent extends User{
    public Dependent() {
    }
    public Dependent(String id, String fullName, String userName, String password, String email, String phoneNumber, String address, int roleId) {
        super(id, fullName, userName, password, email, phoneNumber, address, roleId);
    }

    public Dependent() {
        super();
    }
}
