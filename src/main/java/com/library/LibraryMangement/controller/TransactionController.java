package com.library.LibraryMangement.controller;

import com.library.LibraryMangement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/issueBook")
    public ResponseEntity<String> issueBook(@RequestParam("cardId") int cardId,
                                            @RequestParam("bookId") int bookId) {
        try {
            String transactionId = transactionService.issueBooks(cardId, bookId);
            return ResponseEntity.ok("Your transaction was successful. Transaction ID: " + transactionId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/returnBook")
    public ResponseEntity<String> returnBook(@RequestParam("cardId") int cardId,
                                             @RequestParam("bookId") int bookId) {
        try {
            String transactionId = transactionService.returnBooks(cardId, bookId);
            return ResponseEntity.ok("Your transaction was successful. Transaction ID: " + transactionId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}
