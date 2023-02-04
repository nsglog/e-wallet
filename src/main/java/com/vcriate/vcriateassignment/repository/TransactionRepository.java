package com.vcriate.vcriateassignment.repository;

import com.vcriate.vcriateassignment.models.Transaction;
import com.vcriate.vcriateassignment.models.TransactionType;
import com.vcriate.vcriateassignment.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    boolean initiateTransfer (Wallet initiatedBy, Wallet initiatedWith, double amount, TransactionType transactionType);
    boolean initiateDeposit (Wallet initiatedBy, double amount);
    boolean initiateWithdraw (Wallet initiatedBy, double amount);
}
