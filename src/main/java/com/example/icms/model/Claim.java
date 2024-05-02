package com.example.icms.model;

import java.util.Date;
import java.util.Map;

public class Claim {
    private String id;
    private String insuredPerson;
    private String cardNumber;
    private Date examDate;
    private Date claimDate;
    private double claimAmount;
    private String status;
    private String documents;

    public Claim() {
        this.id = "";
        this.insuredPerson = "";
        this.cardNumber = "";
        this.examDate = new Date();
        this.claimDate = new Date();
        this.claimAmount = 0.0;
        this.status = "";
        this.documents = "";
    }

    public Claim(String insuredPerson, String cardNumber, Date examDate, Date claimDate, double claimAmount, String status, String documents) {
        this.insuredPerson = insuredPerson;
        this.cardNumber = cardNumber;
        this.examDate = examDate;
        this.claimDate = claimDate;
        this.claimAmount = claimAmount;
        this.status = status;
        this.documents = documents;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInsuredPerson() {
        return insuredPerson;
    }

    public void setInsuredPerson(String insuredPerson) {
        this.insuredPerson = insuredPerson;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public Date getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }

    public double getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }
}
