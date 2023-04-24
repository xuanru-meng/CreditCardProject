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

public class UserTest {
    @Test
    public void testAddCreditCard() {
        User user = new User("John", "Doe", "johndoe@example.com");
        CreditCard creditCard = new CreditCard("Chase" ,"4111111111111111", user);

        user.addCreditCard(creditCard);

        Assertions.assertTrue(user.getCreditCards().contains(creditCard));
        Assertions.assertEquals(user, creditCard.getOwner());
    }

    @Test
    public void testRemoveCreditCard() {
        User user = new User("John", "Doe", "johndoe@example.com");
        CreditCard creditCard = new CreditCard("Chase" ,"4111111111111111", user);

        user.addCreditCard(creditCard);
        user.removeCreditCard(creditCard);

        Assertions.assertFalse(user.getCreditCards().contains(creditCard));
        //Assertions.assertNull(creditCard.getUser());
    }
}
