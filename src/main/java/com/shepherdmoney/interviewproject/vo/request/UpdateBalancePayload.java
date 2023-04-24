package com.shepherdmoney.interviewproject.vo.request;

import java.time.Instant;

import lombok.Data;

@Data
public class UpdateBalancePayload {

    private String creditCardNumber;
    
    private Instant transactionTime;

    private double transactionAmount;

    //write setters and getters for all the fields


    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String cardNumber) {
        this.creditCardNumber = cardNumber;
    }

    public Instant getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Instant transactionTime) {
        this.transactionTime = transactionTime;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double amount) {
        this.transactionAmount = amount;
    }
    
}
