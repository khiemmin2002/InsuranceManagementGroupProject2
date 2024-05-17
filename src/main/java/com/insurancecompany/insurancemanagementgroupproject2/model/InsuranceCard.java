package com.insurancecompany.insurancemanagementgroupproject2.model;
/**
 * @author team 5
 */
import java.sql.Date;

public class InsuranceCard {
    private String cardNumber;
    private String cardHolderId;
    private Date expirationDate;
    private String policyOwnerId;
    /*
        Getter and setter for InsuranceCard class
     */
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderId() {
        return cardHolderId;
    }

    public void setCardHolderId(String cardHolderId) {
        this.cardHolderId = cardHolderId;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getPolicyOwnerId() {
        return policyOwnerId;
    }

    public void setPolicyOwnerId(String policyOwnerId) {
        this.policyOwnerId = policyOwnerId;
    }
    //Empty constructor for insurance card class
    public InsuranceCard() {
    }
    // Constructor for insurance card class
    public InsuranceCard(String cardNumber, String cardHolderId, Date expirationDate, String policyOwnerId) {
        this.cardNumber = cardNumber;
        this.cardHolderId = cardHolderId;
        this.expirationDate = expirationDate;
        this.policyOwnerId = policyOwnerId;
    }
}
