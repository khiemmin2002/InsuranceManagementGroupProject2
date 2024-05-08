package com.insurancecompany.insurancemanagementgroupproject2.model;

public class Surveyor extends User{
    public Surveyor() {
    }

    public Surveyor(String id, String fullName, String userName, String password, String email, String phoneNumber, String address, int roleId) {
        super(id, fullName, userName, password, email, phoneNumber, address, roleId);
    }
}
