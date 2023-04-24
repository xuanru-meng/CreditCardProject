package com.shepherdmoney.interviewproject.controller;

import com.shepherdmoney.interviewproject.model.CreditCard;
import com.shepherdmoney.interviewproject.model.User;
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

import java.util.List;
import java.time.Instant;
import java.time.ZoneId;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import com.shepherdmoney.interviewproject.model.CreditCard;
import com.shepherdmoney.interviewproject.model.BalanceHistory;



@RestController
public class CreditCardController {

    // TODO: wire in CreditCard repository here (~1 line)
    @Autowired
    private CreditCardRepository creditCardRepository;
    private UserRepository userRepository;

    public CreditCardController(CreditCardRepository creditCardRepository, UserRepository userRepository) {
        this.creditCardRepository = creditCardRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/credit-card")
    public ResponseEntity<Integer> addCreditCardToUser(@RequestBody AddCreditCardToUserPayload payload) {
        // TODO: Create a credit card entity, and then associate that credit card with user with given userId
        //       Return 200 OK with the credit card id if the user exists and credit card is successfully associated with the user
        //       Return other appropriate response code for other exception cases
        //       Do not worry about validating the card number, assume card number could be any arbitrary format and length
            // Find the user with the given user id

    Optional<User> user = userRepository.findById(payload.getUserId());
    if (user.isPresent()) {

        // Create a new credit card entity
        //CreditCard creditCard = new CreditCard(payload.getCardIssuanceBank(), payload.getCardNumber(), user);
        Optional<User> optionalUser = userRepository.findById(payload.getUserId());
        User useren = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));
        CreditCard creditCard = new CreditCard(payload.getCardIssuanceBank(), payload.getCardNumber(), useren);
        // Associate the credit card with the user
        user.get().addCreditCard(creditCard);
        // Save the changes to the database
        userRepository.save(user.get());
        return ResponseEntity.ok(creditCard.getId());
    } else {
        return ResponseEntity.notFound().build();
    }

    }

    @GetMapping("/credit-card:all")
    public ResponseEntity<List<CreditCardView>> getAllCardOfUser(@RequestParam int userId) {
        // TODO: return a list of all credit card associated with the given userId, using CreditCardView class
        //       if the user has no credit card, return empty list, never return null
        List<CreditCard> creditCards = creditCardRepository.findByUserId((long)userId);
        if (creditCards.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<CreditCardView> creditCardViews = creditCards.stream()
                .map(creditCard -> new CreditCardView(Integer.valueOf(creditCard.getId()).toString(), creditCard.getNumber()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(creditCardViews);
    }

    @GetMapping("/credit-card:user-id")
    public ResponseEntity<Integer> getUserIdForCreditCard(@RequestParam String creditCardNumber) {
        // TODO: Given a credit card number, efficiently find whether there is a user associated with the credit card
        //       If so, return the user id in a 200 OK response. If no such user exists, return 400 Bad Request
        //CreditCard creditCard = creditCardRepository.findByNumber(creditCardNumber);
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findByCardNumber(creditCardNumber);
        CreditCard creditCard = optionalCreditCard.orElse(null);
        if (creditCard != null) {
            return ResponseEntity.ok(creditCard.getUserId());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/credit-card:update-balance")
    public ResponseEntity<String> postMethodName(@RequestBody UpdateBalancePayload[] payload) {
        // Given a list of transactions, update credit cards' balance history.
        String creditCardNumber = payload[0].getCreditCardNumber();
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findByCardNumber(creditCardNumber);
        CreditCard creditCard = optionalCreditCard.orElseThrow(() -> new RuntimeException("Credit card not found"));

        //CreditCard creditCard = creditCardRepository.findByCardNumber(creditCardNumber);
        if (creditCard == null) {
            return ResponseEntity.badRequest().body("Credit card not found");
        }
        List<BalanceHistory> balanceHistory = creditCard.getBalanceHistory();
        LocalDate today = LocalDate.now();
        for (UpdateBalancePayload update : payload) {
            Instant transactionTime = update.getTransactionTime();
            LocalDate transactionDate = transactionTime.atZone(ZoneId.systemDefault()).toLocalDate();
            if (transactionDate.isAfter(today)) {
                return ResponseEntity.badRequest().body("Transaction date cannot be in the future");
            }

            if (!balanceHistory.isEmpty() && transactionDate.isAfter(balanceHistory.get(0).getDate().atZone(ZoneId.systemDefault()).toLocalDate())) {
                throw new IllegalArgumentException("Transaction date must be before or equal to the current balance date");
            }
            

            LocalDateTime transactionDateTime = transactionDate.atStartOfDay();
            ZonedDateTime transactionZonedDateTime = transactionDateTime.atZone(ZoneId.systemDefault());
            Instant transactionTime1 = transactionZonedDateTime.toInstant();

            BalanceHistory balance = new BalanceHistory(transactionTime1, update.getTransactionAmount());
            balanceHistory.add(0, balance);
        }
        creditCard.setBalanceHistory(balanceHistory);
        creditCardRepository.save(creditCard);
        return ResponseEntity.ok("Balance history updated successfully");
    }
    
    
}
