package com.omkar.account_service.service;

import com.omkar.account_service.dto.CreateAccountDto;
import com.omkar.account_service.dto.DepositDto;
import com.omkar.account_service.dto.WithdrawDto;
import com.omkar.account_service.entity.BankAccount;

public interface AccountService {

    BankAccount createAccount(CreateAccountDto dto);

    BankAccount getAccount(String accountNumber);
    
    BankAccount depositMoney(DepositDto dto);
    
    BankAccount withdrawMoney(WithdrawDto dto);
    
    void transferMoney(String fromAccountNumber,
            String toAccountNumber,
            Double amount);
}