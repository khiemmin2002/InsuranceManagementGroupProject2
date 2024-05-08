package com.insurancecompany.insurancemanagementgroupproject2.model;

import java.util.Date;

public class Manager extends User {
    public Manager() {
    }

    public Manager(String id, String fullName, String userName, String password, String email, String phoneNumber, String address, int roleId) {
        super(id, fullName, userName, password, email, phoneNumber, address, roleId);
    }
}
