package com.insurancecompany.insurancemanagementgroupproject2.model;

public abstract class Customer {
    private String id;
    private String full_name;
    private String user_name;
    private String email;
    private String phone_number;
    private String address;
    private InsuranceCard insurance_card;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public InsuranceCard getInsurance_card() {
        return insurance_card;
    }

    public void setInsurance_card(InsuranceCard insurance_card) {
        this.insurance_card = insurance_card;
    }

    public Customer(String id, String full_name, String user_name, String email, String phone_number, String address) {
        this.id = id;
        this.full_name = full_name;
        this.user_name = user_name;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
        this.insurance_card = null;
    }

    public Customer() {
        this.id = "";
        this.full_name = "";
        this.user_name = "";
        this.email = "";
        this.phone_number = "";
        this.address = "";
        this.insurance_card = null;
    }
}
