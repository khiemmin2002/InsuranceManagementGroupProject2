package com.insurancecompany.insurancemanagementgroupproject2.model;
/**
 * @author team 5
 */
public class PolicyHolder extends User{
    // Empty constructor for policyholder class
    public PolicyHolder() {
    }
    // Constructor for policyholder class
    public PolicyHolder(String id, String fullName, String userName, String password, String email, String phoneNumber, String address, int roleId) {
        super(id, fullName, userName, password, email, phoneNumber, address, roleId);
    }
}
