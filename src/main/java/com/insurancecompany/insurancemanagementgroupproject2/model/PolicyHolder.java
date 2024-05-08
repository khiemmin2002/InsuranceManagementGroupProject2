package com.insurancecompany.insurancemanagementgroupproject2.model;

public class PolicyHolder extends User{
    public PolicyHolder() {
    }

    public PolicyHolder(String id, String fullName, String userName, String password, String email, String phoneNumber, String address, int roleId) {
        super(id, fullName, userName, password, email, phoneNumber, address, roleId);
    }
}
