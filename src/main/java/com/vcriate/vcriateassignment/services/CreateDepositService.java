package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.models.AuditRecord;
import com.vcriate.vcriateassignment.models.TransactionType;
import com.vcriate.vcriateassignment.models.Wallet;
import com.vcriate.vcriateassignment.repository.AuditRecordRepository;
import com.vcriate.vcriateassignment.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CreateDepositService {
    private CreateAuditRecordService createAuditRecordService;
    private WalletRepository walletRepository;
    @Autowired
    public CreateDepositService(CreateAuditRecordService createAuditRecordService,
                                WalletRepository walletRepository,
                                AuditRecordRepository auditRecordRepository)   {
        this.createAuditRecordService = createAuditRecordService;
        this.walletRepository = walletRepository;
    }

    public AuditRecord createDeposit (Double amount, long id) {

        Optional<Wallet> _wallet = walletRepository.getWalletByUserId(id);
        Wallet wallet = _wallet.get();
        wallet.setBalance(wallet.getBalance() + amount);
        walletRepository.save(wallet);

        AuditRecord auditRecord = createAuditRecordService.createTransaction(wallet,
                null,
                amount,
                TransactionType.DEPOSIT,
                LocalDateTime.now());

        return auditRecord;
    }
}
