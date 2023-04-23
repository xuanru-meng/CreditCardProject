package com.shepherdmoney.interviewproject.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;
import java.time.LocalDate;

import javax.persistence.*;

import javax.persistence.JoinColumn;
import javax.persistence.OrderColumn;



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
    @ElementCollection
    @OneToMany
    @OrderColumn(name = "balance_index")
    private List<Balance> balanceHistory;

    public CreditCard(String issuanceBank, String number) {
        this.issuanceBank = issuanceBank;
        this.number = number;
        balanceHistory = new ArrayList<>();
        balanceHistory.add(new Balance(LocalDate.now(), 0));
    }

    public void addBalance(LocalDate date, int balance) {
        Balance newBalance = new Balance(date, balance);
        if (!balanceHistory.isEmpty() && date.isAfter(balanceHistory.get(0).getDate())) {
            throw new IllegalArgumentException("Date must be before or equal to the current balance date");
        }
        balanceHistory.add(0, newBalance);
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    private static class Balance {

        private final LocalDate date;
        private final int balance;
    }

}
