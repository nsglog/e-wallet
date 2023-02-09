package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.models.*;
import com.vcriate.vcriateassignment.repository.AuditRecordRepository;
import com.vcriate.vcriateassignment.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditRecordService extends Exception   {

    private AuditRecordRepository auditRecordRepository;
    private WalletRepository walletRepository;
    @Autowired
    public AuditRecordService(AuditRecordRepository auditRecordRepository,
                              WalletRepository walletRepository) {
        this.auditRecordRepository = auditRecordRepository;
        this.walletRepository = walletRepository;
    }

    public AuditRecord createAuditRecord (Wallet wallet,
                                          double amount,
                                          TransactionType transactionType,
                                          LocalDateTime transactionTime)  {

        AuditRecord auditRecord = new AuditRecord();
        auditRecord.setWallet(wallet);
        auditRecord.setAmount(amount);
        auditRecord.setTransactionType(transactionType);
        auditRecord.setTimeOfTransaction(transactionTime);

        auditRecordRepository.save(auditRecord);

        return auditRecord;
    }

    public List<AuditRecord> getAuditRecordsOfUser (long id) {

        Wallet wallet = walletRepository.findById(id).get();

        List<AuditRecord> auditRecordList = auditRecordRepository.findAuditRecords(wallet.getId());

        return auditRecordList;
    }
}
