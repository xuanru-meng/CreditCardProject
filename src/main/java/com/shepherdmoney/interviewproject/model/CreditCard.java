package com.shepherdmoney.interviewproject.model;

import java.util.ArrayList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.ZoneId;


import java.util.List;
import java.time.LocalDate;

import javax.persistence.*;

import javax.persistence.JoinColumn;



@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String issuanceBank;

    private String number;

    // TODO: Credit card's owner. For detailed hint, please see User class
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;



    // TODO: Credit card's balance history. It is a requirement that the dates in the balanceHistory 
    //       list must be in chronological order, with the most recent date appearing first in the list. 
    //       Additionally, the first object in the list must have a date value that matches today's date, 
    //       since it represents the current balance of the credit card. For example:
    //       [
    //         {date: '2023-04-13', balance: 1500},
    //         {date: '2023-04-12', balance: 1200},
    //         {date: '2023-04-11', balance: 1000},
    //         {date: '2023-04-10', balance: 800}
    //       ]

    // Credit card's balance history
    @OneToMany(mappedBy = "creditCard", cascade = CascadeType.ALL)
    private List<BalanceHistory> balanceHistory = new ArrayList<>();
    
    public CreditCard(String issuanceBank, String number, User owner) {
        this.issuanceBank = issuanceBank;
        this.number = number;
        this.owner = owner;
        addBalance(LocalDate.now(), 0);
    }
    
    public void addBalance(LocalDate date, int balance) {
        BalanceHistory newBalance = new BalanceHistory(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant(), balance);
        if (!balanceHistory.isEmpty() && date.isAfter(balanceHistory.get(0).getDate().atZone(ZoneId.systemDefault()).toLocalDate())) {
            throw new IllegalArgumentException("Date must be before or equal to the current balance date");
        }
        balanceHistory.add(0, newBalance);
        newBalance.setCreditCard(this);
    }

    public void setUser(User user) {
        this.owner = user;
    }

    // write setters and getters for all the fields
    public String getIssuanceBank() {
        return issuanceBank;
    }

    public void setIssuanceBank(String issuanceBank) {
        this.issuanceBank = issuanceBank;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String cardNumber) {
        this.number = cardNumber;
    }

    public List<BalanceHistory> getBalanceHistory() {
        return balanceHistory;
    }

    public void setBalanceHistory(List<BalanceHistory> balanceHistory) {
        this.balanceHistory = balanceHistory;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User user) {
        this.owner = user;
    }


    public int getUserId() {
        return getOwner().getId();
    }

    public void setUserId(Long userId) {
        getOwner().setId(userId.intValue());
    }

 





}
