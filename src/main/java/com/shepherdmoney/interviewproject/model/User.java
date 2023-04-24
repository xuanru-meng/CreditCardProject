package com.shepherdmoney.interviewproject.model;

import jakarta.persistence.*;
import java.util.HashSet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "MyUser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String email;

    //write the constructor
    public User(String firstName, String lastName, String email) {
        this.name = firstName + " " + lastName;
        this.email = email;
    }


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<CreditCard> creditCards;
    
    public void addCreditCard(CreditCard creditCard) {
        if (creditCards == null) {
            creditCards = new HashSet<>();
        }
        creditCards.add(creditCard);
        creditCard.setUser(this);
    }

    public void removeCreditCard(CreditCard creditCard) {
        if (creditCards != null) {
            creditCards.remove(creditCard);
            creditCard.setUser(null);
        }
    }

}
