package com.insurancecompany.insurancemanagementgroupproject2.Models;

public abstract class Customer {
    private String id;
    private String full_name;
    private String email;
    private String phone_number;
    private String address;
    private InsuranceCard insurance_card;

    public Customer(String id, String full_name, String email, String phone_number, String address) {
        this.id = id;
        this.full_name = full_name;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
    }
}
