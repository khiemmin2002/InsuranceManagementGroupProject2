package com.insurancecompany.insurancemanagementgroupproject2.model;
/**
 * @author team 5
 */
public class Role {
    private int id;
    private String roleName;
    // Getter and setter for role class
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    // Constructor for role class
    public Role(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }
    // Empty constructor for role class
    public Role() {
    }
}
