package com.insurancecompany.insurancemanagementgroupproject2.model;

public abstract class Provider {
    private String id;
    private String full_name;

    public Provider(String id, String full_name) {
        this.id = id;
        this.full_name = full_name;
    }
}
