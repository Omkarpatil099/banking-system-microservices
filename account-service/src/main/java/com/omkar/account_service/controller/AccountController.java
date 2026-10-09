package com.omkar.account_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.omkar.account_service.dto.CreateAccountDto;
import com.omkar.account_service.dto.DepositDto;
import com.omkar.account_service.dto.WithdrawDto;
import com.omkar.account_service.entity.BankAccount;
import com.omkar.account_service.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<BankAccount> createAccount(
            @Valid @RequestBody CreateAccountDto dto) {

        BankAccount account = accountService.createAccount(dto);

        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<BankAccount> getAccount(
            @PathVariable String accountNumber) {

        BankAccount account = accountService.getAccount(accountNumber);

        return new ResponseEntity<>(account, HttpStatus.OK);
    }
    
    @PostMapping("/deposit")
    public ResponseEntity<BankAccount> depositMoney(
            @Valid @RequestBody DepositDto dto) {

        BankAccount account = accountService.depositMoney(dto);

        return new ResponseEntity<>(account, HttpStatus.OK);
    }
    @PostMapping("/withdraw")
    public ResponseEntity<BankAccount> withdrawMoney(
            @Valid @RequestBody WithdrawDto dto) {

        BankAccount account = accountService.withdrawMoney(dto);

        return new ResponseEntity<>(account, HttpStatus.OK);
    }
    @PostMapping("/internal/transfer")
    public ResponseEntity<String> transferMoney(
            @RequestParam String fromAccountNumber,
            @RequestParam String toAccountNumber,
            @RequestParam Double amount) {

        accountService.transferMoney(
                fromAccountNumber,
                toAccountNumber,
                amount);

        return ResponseEntity.ok("Transfer completed successfully");
    }
}