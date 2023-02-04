package com.vcriate.vcriateassignment.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class Transaction extends BaseModel  {
    private Wallet initiatedBy;
    private Wallet InitiatedWith;
    private double amount;
    private TransactionType transactionType;
    private LocalDateTime timeOfTransaction;
    private TransactionStatus transactionStatus;
}
