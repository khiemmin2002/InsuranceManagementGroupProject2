package com.insurancecompany.insurancemanagementgroupproject2.model;

public class PolicyOwner extends User{
    public PolicyOwner() {
    }

    public PolicyOwner(String id, String fullName, String userName, String password, String email, String phoneNumber, String address, int roleId) {
        super(id, fullName, userName, password, email, phoneNumber, address, roleId);
    }
}
