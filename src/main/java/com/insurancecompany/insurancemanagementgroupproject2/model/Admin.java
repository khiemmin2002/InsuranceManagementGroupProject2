package com.insurancecompany.insurancemanagementgroupproject2.model;

public class Admin extends User{
    public Admin() {
    }

    public Admin(String id, String fullName, String userName, String password, String email, String phoneNumber, String address, int roleId) {
        super(id, fullName, userName, password, email, phoneNumber, address, roleId);
    }
}
