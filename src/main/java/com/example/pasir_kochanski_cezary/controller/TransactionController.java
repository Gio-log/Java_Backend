package com.example.pasir_kochanski_cezary.controller;

import com.example.pasir_kochanski_cezary.model.Transaction;
import com.example.pasir_kochanski_cezary.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return ResponseEntity.ok(transactions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody Transaction transactionDetails) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found with id " + id));
        transaction.setAmount(transaction.getAmount());
        transaction.setType(transaction.getType());
        transaction.setTags(transaction.getTags());
        transaction.setNotes(transaction.getNotes());

        Transaction updatedTransaction = transactionRepository.save(transaction);
        return ResponseEntity.ok(updatedTransaction);
    }
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transactionDetails) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDetails.getAmount());
        transaction.setType(transactionDetails.getType());
        transaction.setTags(transactionDetails.getTags());
        transaction.setNotes(transactionDetails.getNotes());
        Transaction createtransaction = transactionRepository.save(transactionDetails);
        return ResponseEntity.ok(createtransaction);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Transaction> deleteTransaction(@PathVariable Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found with id " + id));
        transactionRepository.delete(transaction);
        return ResponseEntity.noContent().build();
    }
}
