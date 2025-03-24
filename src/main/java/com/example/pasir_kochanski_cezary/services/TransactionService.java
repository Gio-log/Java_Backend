package com.example.pasir_kochanski_cezary.services;

import com.example.pasir_kochanski_cezary.model.Transaction;
import com.example.pasir_kochanski_cezary.dto.TransactionDTO;
import com.example.pasir_kochanski_cezary.model.TransactionType;
import com.example.pasir_kochanski_cezary.model.User;
import com.example.pasir_kochanski_cezary.repository.TransactionRepository;
import com.example.pasir_kochanski_cezary.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService
{
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Nie znaleziono zalogowanego użytkownika"));
    }

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public List<Transaction> getAllTransactions()
    {
        User user = getCurrentUser();
        return transactionRepository.findAllByUser(user);
    }

    public Transaction getTransactionById(long id)
    {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nie znalezniono transakcji o ID " + id));
    }

    public Transaction updateTransaction(Long id, TransactionDTO transactionDTO)
    {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono transakcji o ID " + id));

        transaction.setAmount(transactionDTO.getAmount());
        transaction.setType(TransactionType.valueOf(transactionDTO.getType()));
        transaction.setTags(transactionDTO.getTags());
        transaction.setNotes(transactionDTO.getNotes());

        return transactionRepository.save(transaction);
    }

    public Transaction createTransaction(TransactionDTO transactionDetails) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDetails.getAmount());
        transaction.setType(TransactionType.valueOf(transactionDetails.getType()));
        transaction.setTags(transactionDetails.getTags());
        transaction.setNotes(transactionDetails.getNotes());
        transaction.setUser(getCurrentUser());


        Transaction savedTransaction = transactionRepository.save(transaction);

        return savedTransaction;
    }

    public void deleteTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id " + id));

        transactionRepository.delete(transaction);
    }

}
