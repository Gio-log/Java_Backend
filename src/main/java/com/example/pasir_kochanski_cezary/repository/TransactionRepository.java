package com.example.pasir_kochanski_cezary.repository;

import com.example.pasir_kochanski_cezary.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
