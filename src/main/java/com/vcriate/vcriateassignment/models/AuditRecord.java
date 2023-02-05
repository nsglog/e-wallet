package com.vcriate.vcriateassignment.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
public class AuditRecord extends BaseModel  {
    @OneToOne
    private Wallet initiatedBy;
    @OneToOne
    private Wallet InitiatedWith;
    private double amount;
    private TransactionType transactionType;
    private LocalDateTime timeOfTransaction;
    private TransactionStatus transactionStatus;
}
