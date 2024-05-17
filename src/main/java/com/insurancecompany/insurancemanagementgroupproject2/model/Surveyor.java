package com.insurancecompany.insurancemanagementgroupproject2.model;
/**
 * @author team 5
 */
public class Surveyor extends User{
    //Empty Constructor for surveyor class
    public Surveyor() {
    }
    //Constructor for surveyor class
    public Surveyor(String id, String fullName, String userName, String password, String email, String phoneNumber, String address, int roleId) {
        super(id, fullName, userName, password, email, phoneNumber, address, roleId);
    }
}
