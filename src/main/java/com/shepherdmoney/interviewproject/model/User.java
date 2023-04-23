package com.shepherdmoney.interviewproject.model;

import jakarta.persistence.*;
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

    // TODO: User's credit card
    // HINT: A user can have one or more, or none at all. We want to be able to query credit cards by user
    //       and user by a credit card.
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
