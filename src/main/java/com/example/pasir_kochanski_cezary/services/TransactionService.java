package com.example.pasir_kochanski_cezary.services;

import com.example.pasir_kochanski_cezary.model.Transaction;
import com.example.pasir_kochanski_cezary.dto.TransactionDTO;
import com.example.pasir_kochanski_cezary.model.TransactionType;
import com.example.pasir_kochanski_cezary.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService
{
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions()
    {
        return transactionRepository.findAll();
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
        Transaction transaction = new Transaction(
                transactionDetails.getAmount(),
                TransactionType.valueOf(transactionDetails.getType()),
                transactionDetails.getTags(),
                transactionDetails.getNotes()
        );



        Transaction savedTransaction = transactionRepository.save(transaction);

        return savedTransaction;
    }

    public void deleteTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id " + id));

        transactionRepository.delete(transaction);
    }

}
