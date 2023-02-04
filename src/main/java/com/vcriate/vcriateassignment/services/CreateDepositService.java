package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.models.Transaction;
import com.vcriate.vcriateassignment.models.TransactionType;
import com.vcriate.vcriateassignment.models.Wallet;
import com.vcriate.vcriateassignment.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateDepositService {
    private CreateTransactionService createTransactionService;
    public CreateDepositService(CreateTransactionService createTransactionService)   {
        this.createTransactionService = createTransactionService;
    }

    public Transaction createDeposit (Double amount, long id) {

        Wallet wallet = WalletRepository.getWalletByUserId(id);

        WalletRepository.deposit(amount, wallet);
        Transaction transaction = createTransactionService.createTransaction(wallet,
                null,
                amount,
                TransactionType.DEPOSIT,
                LocalDateTime.now());

        return transaction;
    }
}
