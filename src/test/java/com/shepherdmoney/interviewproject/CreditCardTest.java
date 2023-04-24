package com.shepherdmoney.interviewproject;

import com.shepherdmoney.interviewproject.model.BalanceHistory;
import java.time.Instant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;


import com.shepherdmoney.interviewproject.model.CreditCard;
import com.shepherdmoney.interviewproject.model.User;
import com.shepherdmoney.interviewproject.controller.UserController;
import com.shepherdmoney.interviewproject.repository.CreditCardRepository;
import com.shepherdmoney.interviewproject.repository.UserRepository;
import com.shepherdmoney.interviewproject.vo.request.AddCreditCardToUserPayload;
import com.shepherdmoney.interviewproject.vo.request.UpdateBalancePayload;
import com.shepherdmoney.interviewproject.vo.response.CreditCardView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.junit.jupiter.api.Assertions;

public class CreditCardTest {

    @Test
    public void testConstructor() {
        String cardNumber = "1234567890123456";
        String issueBank = "Chase";
        User user = new User("John", "Doe", "johndoe@example.com");
        CreditCard creditCard = new CreditCard(issueBank , cardNumber, user);
        Assertions.assertEquals(cardNumber, creditCard.getNumber());
        Assertions.assertEquals(issueBank, creditCard.getIssuanceBank());
    }
    

    @Test
    public void testSettersAndGetters() {
        String cardNumber = "1234567890123456";
        String issueBank = "Chase";
        User user = new User("John", "Doe", "johndoe@example.com");
        CreditCard creditCard = new CreditCard(issueBank , cardNumber, user);
        creditCard.setNumber(cardNumber);
        creditCard.setIssuanceBank(issueBank);
        Assertions.assertEquals(cardNumber, creditCard.getNumber());
        Assertions.assertEquals(issueBank, creditCard.getIssuanceBank());
    }
    
}
