package com.insurancecompany.insurancemanagementgroupproject2.model;

public class Dependent extends User{
    private String policyHolderName;

    public Dependent() {
    }

    public Dependent(String id, String fullName, String userName, String password, String email, String phoneNumber, String address, int roleId) {
        super(id, fullName, userName, password, email, phoneNumber, address, roleId);
    }

    public Dependent(String id, String fullName, String userName, String password, String email, String phoneNumber, String address, int roleId, String policyHolderName) {
        super(id, fullName, userName, password, email, phoneNumber, address, roleId);
        this.policyHolderName = policyHolderName;  // Initialize the new property
    }

    // Getter for policy holder's name
    public String getPolicyHolderName() {
        return policyHolderName;
    }

    // Setter for policy holder's name
    public void setPolicyHolderName(String policyHolderName) {
        this.policyHolderName = policyHolderName;
    }

}
