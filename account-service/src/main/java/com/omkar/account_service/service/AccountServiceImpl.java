package com.omkar.account_service.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omkar.account_service.client.UserServiceClient;
import com.omkar.account_service.dto.CreateAccountDto;
import com.omkar.account_service.dto.DepositDto;
import com.omkar.account_service.dto.WithdrawDto;
import com.omkar.account_service.entity.BankAccount;
import com.omkar.account_service.repository.BankAccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserServiceClient userServiceClient;

    @Override
    public BankAccount createAccount(CreateAccountDto dto) {

        boolean userExists =
                userServiceClient.userExists(dto.getUserId());

        if (!userExists) {
            throw new RuntimeException("User does not exist");
        }

        BankAccount account = new BankAccount();

        account.setAccountNumber(generateAccountNumber());
        account.setUserId(dto.getUserId());
        account.setAccountType(dto.getAccountType());
        account.setBalance(BigDecimal.ZERO);

        return bankAccountRepository.save(account);
    }

    @Override
    public BankAccount getAccount(String accountNumber) {

        return bankAccountRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public BankAccount depositMoney(DepositDto dto) {

        BankAccount account =
                bankAccountRepository.findByAccountNumber(dto.getAccountNumber());

        if (account == null) {
            throw new RuntimeException("Account not found");
        }

        BigDecimal amount =
                BigDecimal.valueOf(dto.getAmount());

        account.setBalance(
                account.getBalance().add(amount)
        );

        return bankAccountRepository.save(account);
    }

    @Override
    public BankAccount withdrawMoney(WithdrawDto dto) {

        BankAccount account =
                bankAccountRepository.findByAccountNumber(dto.getAccountNumber());

        if (account == null) {
            throw new RuntimeException("Account not found");
        }

        BigDecimal amount =
                BigDecimal.valueOf(dto.getAmount());

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(
                account.getBalance().subtract(amount)
        );

        return bankAccountRepository.save(account);
    }

    @Override
    @Transactional
    public void transferMoney(
            String fromAccountNumber,
            String toAccountNumber,
            Double amount) {

        BankAccount sender =
                bankAccountRepository.findByAccountNumber(fromAccountNumber);

        if (sender == null) {
            throw new RuntimeException("Sender account not found");
        }

        BankAccount receiver =
                bankAccountRepository.findByAccountNumber(toAccountNumber);

        if (receiver == null) {
            throw new RuntimeException("Receiver account not found");
        }

        if (sender.getAccountNumber()
                .equals(receiver.getAccountNumber())) {

            throw new RuntimeException(
                    "Sender and receiver accounts cannot be same");
        }

        BigDecimal transferAmount =
                BigDecimal.valueOf(amount);

        if (sender.getBalance()
                .compareTo(transferAmount) < 0) {

            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(
                sender.getBalance().subtract(transferAmount)
        );

        receiver.setBalance(
                receiver.getBalance().add(transferAmount)
        );

        bankAccountRepository.save(sender);
        bankAccountRepository.save(receiver);
    }

    private String generateAccountNumber() {

        return "ACC" + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 10)
                .toUpperCase();
    }
}