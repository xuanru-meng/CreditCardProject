package com.shepherdmoney.interviewproject.controller;

import com.shepherdmoney.interviewproject.vo.request.AddCreditCardToUserPayload;
import com.shepherdmoney.interviewproject.vo.request.UpdateBalancePayload;
import com.shepherdmoney.interviewproject.vo.response.CreditCardView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;



@RestController
public class CreditCardController {

    // TODO: wire in CreditCard repository here (~1 line)
    @Autowired
    private CreditCardRepository creditCardRepository;

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
        CreditCard creditCard = new CreditCard(payload.getCardNumber());
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
        List<CreditCard> creditCards = creditCardRepository.findByUserId(userId);
        if (creditCards.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<CreditCardView> creditCardViews = creditCards.stream()
                .map(creditCard -> new CreditCardView(creditCard.getId(), creditCard.getNumber()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(creditCardViews);
    }

    @GetMapping("/credit-card:user-id")
    public ResponseEntity<Integer> getUserIdForCreditCard(@RequestParam String creditCardNumber) {
        // TODO: Given a credit card number, efficiently find whether there is a user associated with the credit card
        //       If so, return the user id in a 200 OK response. If no such user exists, return 400 Bad Request
        CreditCard creditCard = creditCardRepository.findByNumber(creditCardNumber);
        if (creditCard != null) {
            return ResponseEntity.ok(creditCard.getUserId());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/credit-card:update-balance")
    public ResponseEntity<String> postMethodName(@RequestBody UpdateBalancePayload[] payload) {
        //TODO: Given a list of transactions, update credit cards' balance history.
        //      For example: if today is 4/12, a credit card's balanceHistory is [{date: 4/12, balance: 110}, {date: 4/10, balance: 100}],
        //      Given a transaction of {date: 4/10, amount: 10}, the new balanceHistory is
        //      [{date: 4/12, balance: 120}, {date: 4/11, balance: 110}, {date: 4/10, balance: 110}]
        //      Return 200 OK if update is done and successful, 400 Bad Request if the given card number
        //        is not associated with a card.
        String creditCardNumber = payload[0].getCreditCardNumber();
        CreditCard creditCard = creditCardRepository.findByCardNumber(creditCardNumber);
        if (creditCard == null) {
            return ResponseEntity.badRequest().body("Credit card not found");
        }
        List<Balance> balanceHistory = creditCard.getBalanceHistory();
        LocalDate today = LocalDate.now();
        for (UpdateBalancePayload update : payload) {
            Balance balance = new Balance();
            balance.setDate(update.getDate());
            balance.setBalance(update.getAmount() + balanceHistory.get(0).getBalance());
            balanceHistory.add(0, balance);
        }
        creditCard.setBalanceHistory(balanceHistory);
        creditCardRepository.save(creditCard);
        return ResponseEntity.ok("Balance history updated successfully");
        
    }
    
}
