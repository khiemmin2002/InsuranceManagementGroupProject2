package com.insurancecompany.insurancemanagementgroupproject2.model;
/**
 * @author team 5
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Claim {
    private String id;
    private String insuredPerson;
    private String cardNumber;
    private Date examDate;
    private Date claimDate;
    private double claimAmount;
    private String status;
    private String bankName;
    private String bankUserName;
    private String bankNumber;
    private String documents;
    // Empty constructor for Claim class
    public Claim() {
        this.id = "";
        this.insuredPerson = "";
        this.cardNumber = "";
        this.examDate = new Date();
        this.claimDate = new Date();
        this.claimAmount = 0.0;
        this.status = "";
        this.bankName = "";
        this.bankUserName = "";
        this.bankNumber = "";
        this.documents = "";

    }
    // Constructor for Claim class
    public Claim(String id,String insuredPerson, String cardNumber, Date examDate, Date claimDate, double claimAmount, String status, String bankName, String bankUserName, String bankNumber) {
        this.id = id;
        this.insuredPerson = insuredPerson;
        this.cardNumber = cardNumber;
        this.examDate = examDate != null ? examDate: null;
        this.claimDate = claimDate != null ? claimDate: null;
        this.claimAmount = claimAmount;
        this.status = status;
        this.bankName = bankName;
        this.bankUserName = bankUserName;
        this.bankNumber = bankNumber;
    }
    // Constructor for Claim class
    public Claim(String id, String insuredPerson, String cardNumber, Date examDate, Date claimDate, double claimAmount, String status, String bankName, String bankUserName, String bankNumber, String documents) {
        this.id = id;
        this.insuredPerson = insuredPerson;
        this.cardNumber = cardNumber;
        this.examDate = examDate;
        this.claimDate = claimDate;
        this.claimAmount = claimAmount;
        this.status = status;
        this.bankName = bankName;
        this.bankUserName = bankUserName;
        this.bankNumber = bankNumber;
        this.documents = documents;
    }



    /*
       Getter and setter for Claim class
    */
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankUserName() {
        return bankUserName;
    }

    public void setBankUserName(String bankUserName) {
        this.bankUserName = bankUserName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public java.sql.Date getClaimDateFormat(String claimDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = sdf.parse(claimDate);
            return new java.sql.Date(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public java.sql.Date getExamDateFormat(String examDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = sdf.parse(examDate);
            return new java.sql.Date(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }
}
