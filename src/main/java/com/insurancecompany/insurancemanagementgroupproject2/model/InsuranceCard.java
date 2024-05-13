package com.insurancecompany.insurancemanagementgroupproject2.model;

import java.sql.Date;

public class InsuranceCard {
    private String cardNumber;
    private String cardHolderId;
    private Date expirationDate;
    private String policyOwnerId;

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

    public InsuranceCard() {
    }

    public InsuranceCard(String cardNumber, String cardHolderId, Date expirationDate, String policyOwnerId) {
        this.cardNumber = cardNumber;
        this.cardHolderId = cardHolderId;
        this.expirationDate = expirationDate;
        this.policyOwnerId = policyOwnerId;
    }
}
