package com.insurancecompany.insurancemanagementgroupproject2.model;
/**
 * @author team 5
 */
public class PolicyOwner extends User{
    // Empty constructor for
    public PolicyOwner() {
    }
    // Constructor for policy owner
    public PolicyOwner(String id, String fullName, String userName, String password, String email, String phoneNumber, String address, int roleId) {
        super(id, fullName, userName, password, email, phoneNumber, address, roleId);
    }
}
