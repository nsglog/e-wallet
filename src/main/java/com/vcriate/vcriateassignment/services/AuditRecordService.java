package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.models.*;
import com.vcriate.vcriateassignment.repository.AuditRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
@Service
public class AuditRecordService extends Exception   {

    private AuditRecordRepository auditRecordRepository;
    @Autowired
    public AuditRecordService(AuditRecordRepository auditRecordRepository) {
        this.auditRecordRepository = auditRecordRepository;
    }

    public AuditRecord createAuditRecord (Wallet initiatedBy,
                                          Wallet initiatedWith,
                                          double amount,
                                          TransactionType transactionType,
                                          LocalDateTime transactionTime)  {

        AuditRecord auditRecord = new AuditRecord();
        auditRecord.setInitiatedBy(initiatedBy);
        auditRecord.setInitiatedWith(initiatedWith);
        auditRecord.setAmount(amount);
        auditRecord.setTransactionType(transactionType);
        auditRecord.setTimeOfTransaction(transactionTime);

        return auditRecord;
    }
}
