package com.omkar.transaction_service.service;

import java.util.List;

import com.omkar.transaction_service.dto.TransferDto;
import com.omkar.transaction_service.entity.Transaction;

public interface TransactionService {

    Transaction transferMoney(TransferDto dto);

    List<Transaction> getTransactionHistory(String accountNumber);
}