package com.omkar.transaction_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.omkar.transaction_service.dto.TransferDto;
import com.omkar.transaction_service.entity.Transaction;
import com.omkar.transaction_service.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transferMoney(
            @Valid @RequestBody TransferDto dto) {

        Transaction transaction = transactionService.transferMoney(dto);

        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @GetMapping("/history/{accountNumber}")
    public ResponseEntity<List<Transaction>> getTransactionHistory(
            @PathVariable String accountNumber) {

        List<Transaction> transactions =
                transactionService.getTransactionHistory(accountNumber);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}