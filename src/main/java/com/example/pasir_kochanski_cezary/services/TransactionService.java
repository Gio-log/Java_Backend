package com.example.pasir_kochanski_cezary.services;

import com.example.pasir_kochanski_cezary.dto.BalanceDTO;
import com.example.pasir_kochanski_cezary.model.Transaction;
import com.example.pasir_kochanski_cezary.dto.TransactionDTO;
import com.example.pasir_kochanski_cezary.model.TransactionType;
import com.example.pasir_kochanski_cezary.model.User;
import com.example.pasir_kochanski_cezary.repository.TransactionRepository;
import com.example.pasir_kochanski_cezary.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService
{
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Nie znaleziono zalogowanego użytkownika"));
    }

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }
    public BalanceDTO getUserBalance(User user) {
        List<Transaction> userTransactions = transactionRepository.findByUser((user));

        double income = userTransactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double expense = userTransactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();


        return new BalanceDTO(income, expense, income - expense);
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

        if (!transaction.getUser().getEmail().equals(getCurrentUser().getEmail())) {
            throw new SecurityException("Brak dostępu do edycji tej transakcji");
        }

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
        transaction.setTimestamp(LocalDateTime.now());


        Transaction savedTransaction = transactionRepository.save(transaction);

        return savedTransaction;
    }

    public void deleteTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id " + id));

        if (!transaction.getUser().getEmail().equals(getCurrentUser().getEmail())) {
            throw new SecurityException("Brak dostępu do edycji tej transakcji");
        }

        transactionRepository.delete(transaction);
    }

}
