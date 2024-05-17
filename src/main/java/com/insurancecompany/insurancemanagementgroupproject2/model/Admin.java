package com.insurancecompany.insurancemanagementgroupproject2.model;
/**
 * @author team 5
 */
public class Admin extends User{
    // Empty constructor for admin class
    public Admin() {
    }
    //Constructor for admin class
    public Admin(String id, String fullName, String userName, String password, String email, String phoneNumber, String address, int roleId) {
        super(id, fullName, userName, password, email, phoneNumber, address, roleId);
    }
}
