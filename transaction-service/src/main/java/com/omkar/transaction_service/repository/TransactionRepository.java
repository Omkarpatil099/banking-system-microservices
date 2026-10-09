package com.omkar.transaction_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omkar.transaction_service.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByFromAccountNumberOrToAccountNumberOrderByTransactionDateDesc(
            String fromAccountNumber,
            String toAccountNumber
    );

    Transaction findByTransactionId(String transactionId);
}