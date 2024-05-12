package com.insurancecompany.insurancemanagementgroupproject2.model;

public class Dependent extends User{
    private String policyHolderID;
    private String policyHolderUserName;
    private String dependentID;
    private String dependentUserName;


    public Dependent(String policyHolderID, String policyHolderUserName, String dependentID, String dependentUserName) {
        this.policyHolderID = policyHolderID;
        this.policyHolderUserName = policyHolderUserName;
        super.setId(dependentID);
        super.setUserName(dependentUserName);
    }
    public Dependent() {
        super();
    }

    public String getPolicyHolderId() {
        return policyHolderID;
    }

    public void setPolicyHolderId(String policyHolderID) {
        this.policyHolderID = policyHolderID;
    }

    public String getPolicyHolderUserName() {
        return policyHolderUserName;
    }

    public void setPolicyHolderUserName(String policyHolderUserName) {
        this.policyHolderUserName = policyHolderUserName;
    }

    public String getDependentId() {
        return dependentID;
    }

    public void setDependentId(String dependentID) {
        this.dependentID = dependentID;
    }

    public String getDependentUserName() {
        return dependentUserName;
    }

    public void setDependentUserName(String dependentUserName) {
        this.dependentUserName = dependentUserName;
    }
}
