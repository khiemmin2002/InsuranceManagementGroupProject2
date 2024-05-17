package com.insurancecompany.insurancemanagementgroupproject2.model;
/**
 * @author team 5
 */
public class Manager extends User {
    // Empty Constructor for manager class
    public Manager() {
    }
    //Constructor for manager class
    public Manager(String id, String fullName, String userName, String password, String email, String phoneNumber, String address, int roleId) {
        super(id, fullName, userName, password, email, phoneNumber, address, roleId);
    }
}
