package com.omkar.transaction_service.service;

import java.time.LocalDateTime;
import com.omkar.transaction_service.kafka.TransactionEventProducer;
import com.omkar.transaction_service.kafka.TransferEvent;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.omkar.transaction_service.dto.TransferDto;
import com.omkar.transaction_service.entity.Transaction;
import com.omkar.transaction_service.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private TransactionEventProducer transactionEventProducer;
    
    @Autowired
    private RestClient.Builder restClientBuilder;

    @Override
    public Transaction transferMoney(TransferDto dto) {

        try {
            restClientBuilder.build()
                    .post()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("http")
                            .host("ACCOUNT-SERVICE")
                            .path("/api/accounts/internal/transfer")
                            .queryParam("fromAccountNumber",
                                    dto.getFromAccountNumber())
                            .queryParam("toAccountNumber",
                                    dto.getToAccountNumber())
                            .queryParam("amount", dto.getAmount())
                            .build())
                    .retrieve()
                    .toBodilessEntity();

        } catch (RestClientException e) {
            throw new RuntimeException(
                    "Account transfer failed: " + e.getMessage());
        }

        TransferEvent event = new TransferEvent(
                dto.getFromAccountNumber(),
                dto.getToAccountNumber(),
                dto.getAmount()
        );

        transactionEventProducer.sendTransferEvent(event);

        Transaction transaction = new Transaction();

        transaction.setTransactionId(
                "TXN" + UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 10)
                        .toUpperCase()
        );

        transaction.setFromAccountNumber(dto.getFromAccountNumber());
        transaction.setToAccountNumber(dto.getToAccountNumber());
        transaction.setTransactionType("TRANSFER");
        transaction.setAmount(dto.getAmount());
        transaction.setStatus("SUCCESS");
        transaction.setTransactionDate(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionHistory(String accountNumber) {

        return transactionRepository
                .findByFromAccountNumberOrToAccountNumberOrderByTransactionDateDesc(
                        accountNumber,
                        accountNumber
                );
    }
}