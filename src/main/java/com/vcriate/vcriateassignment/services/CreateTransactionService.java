package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.models.*;
import com.vcriate.vcriateassignment.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
@Service
public class CreateTransactionService extends Exception   {

    private TransactionRepository transactionRepository;

    public CreateTransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction (Wallet initiatedBy,
                                          Wallet initiatedWith,
                                          double amount,
                                          TransactionType transactionType,
                                          LocalDateTime transactionTime)  {

        Transaction transaction = new Transaction();
        transaction.setInitiatedBy(initiatedBy);
        transaction.setInitiatedWith(initiatedWith);
        transaction.setAmount(amount);
        transaction.setTransactionType(transactionType);
        transaction.setTimeOfTransaction(transactionTime);

        return transaction;
    }
}
