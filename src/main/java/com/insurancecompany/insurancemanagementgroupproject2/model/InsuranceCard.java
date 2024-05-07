package com.insurancecompany.insurancemanagementgroupproject2.model;

import java.sql.Date;

public class InsuranceCard {
    private String card_number;
    private String card_holder_id;
    private Date expiration_date;
    private String policy_owner_id;

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getCard_holder_id() {
        return card_holder_id;
    }

    public void setCard_holder_id(String card_holder_id) {
        this.card_holder_id = card_holder_id;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getPolicy_owner_id() {
        return policy_owner_id;
    }

    public void setPolicy_owner_id(String policy_owner_id) {
        this.policy_owner_id = policy_owner_id;
    }
}
