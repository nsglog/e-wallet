package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.models.Transaction;
import com.vcriate.vcriateassignment.models.TransactionType;
import com.vcriate.vcriateassignment.models.Wallet;
import com.vcriate.vcriateassignment.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateWithdrawService {
    private CreateTransactionService createTransactionService;
    public CreateWithdrawService(CreateTransactionService createTransactionService)   {
        this.createTransactionService = createTransactionService;
    }

    public Transaction createWithdraw (Double amount, long id) {

        Wallet wallet = WalletRepository.getWalletByUserId(id);

        WalletRepository.withdraw(amount, wallet);
        Transaction transaction = createTransactionService.createTransaction(wallet,
                null,
                amount,
                TransactionType.WITHDRAW,
                LocalDateTime.now());

        return transaction;
    }
}
