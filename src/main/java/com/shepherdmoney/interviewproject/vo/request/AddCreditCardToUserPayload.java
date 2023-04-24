package com.shepherdmoney.interviewproject.vo.request;

import lombok.Data;

@Data
public class AddCreditCardToUserPayload {

    private int userId;

    private String cardIssuanceBank;

    private String cardNumber;

    //write setters and getters for all the fields

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCardIssuanceBank() {
        return cardIssuanceBank;
    }

    public void setCardIssuanceBank(String cardIssuanceBank) {
        this.cardIssuanceBank = cardIssuanceBank;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
}
