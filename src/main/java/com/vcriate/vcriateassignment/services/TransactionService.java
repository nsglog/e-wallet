package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.models.*;
import com.vcriate.vcriateassignment.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
@Service
public class TransactionService extends Exception   {

    private TransactionRepository transactionRepository;

    public TransactionService (TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction (Wallet initiatedBy, Wallet initiatedWith, double amount, TransactionType transactionType, LocalDateTime transactionTime)  {

        boolean transactionStatus = initiateTransaction (initiatedBy, initiatedWith, amount, transactionType);

        if(transactionStatus) {

            Transaction transaction = new Transaction();
            transaction.setInitiatedBy(initiatedBy);
            transaction.setInitiatedWith(initiatedWith);
            transaction.setAmount(amount);
            transaction.setTransactionType(transactionType);
            transaction.setTimeOfTransaction(transactionTime);
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            return transaction;
        }

        else {
            return null;
        }
    }

    private boolean initiateTransaction (Wallet initiatedBy, Wallet initiatedWith, double amount, TransactionType transactionType)  {
        if(transactionType.equals("TRANSFER")) {
            return transactionRepository.initiateTransfer(initiatedBy, initiatedWith, amount, transactionType);
        }
        else if(transactionType.equals("DEPOSIT"))  {
            return transactionRepository.initiateDeposit(initiatedBy, amount);
        }
        else {
            return transactionRepository.initiateWithdraw(initiatedBy, amount);
        }
    }


}
