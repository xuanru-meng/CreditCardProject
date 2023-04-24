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

class BalanceHistoryTest {

    @Autowired
    private UserController userController;


    @Test
    public void testConstructor() {
        Instant now = Instant.now();
        double balance = 100.0;
        BalanceHistory balanceHistory = new BalanceHistory(now, balance);
        Assertions.assertEquals(now, balanceHistory.getDate());
        Assertions.assertEquals(balance, balanceHistory.getBalance());
    }

    @Test
    public void testSettersAndGetters() {
        Instant now = Instant.now();
        double balance = 100.0;
        BalanceHistory balanceHistory = new BalanceHistory();
        balanceHistory.setDate(now);
        balanceHistory.setBalance(balance);
        Assertions.assertEquals(now, balanceHistory.getDate());
        Assertions.assertEquals(balance, balanceHistory.getBalance());
    }


}
