package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.models.Transaction;
import com.vcriate.vcriateassignment.models.TransactionType;
import com.vcriate.vcriateassignment.models.Wallet;
import com.vcriate.vcriateassignment.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateTransferService {
    private CreateTransactionService createTransactionService;
    public CreateTransferService(CreateTransactionService createTransactionService)   {
        this.createTransactionService = createTransactionService;
    }

    public Transaction createTransfer (long transferToUserId, double amount, long transferFromUserId) {

        Wallet transferToWallet = WalletRepository.getWalletByUserId(transferToUserId);
        Wallet transferFromWallet = WalletRepository.getWalletByUserId(transferFromUserId);

        WalletRepository.transfer(transferToWallet, amount, transferFromWallet);

        Transaction transaction = createTransactionService.createTransaction(transferFromWallet,
                transferToWallet,
                amount,
                TransactionType.TRANSFER,
                LocalDateTime.now());

        return transaction;
    }
}
