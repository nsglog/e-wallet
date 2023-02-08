package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.exceptions.UnderTransaction;
import com.vcriate.vcriateassignment.models.AuditRecord;
import com.vcriate.vcriateassignment.models.TransactionType;
import com.vcriate.vcriateassignment.models.Wallet;
import com.vcriate.vcriateassignment.models.WalletStatusForTransaction;
import com.vcriate.vcriateassignment.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DepositService {
    private AuditRecordService auditRecordService;
    private WalletRepository walletRepository;
    @Autowired
    public DepositService(AuditRecordService auditRecordService,
                          WalletRepository walletRepository)   {
        this.auditRecordService = auditRecordService;
        this.walletRepository = walletRepository;
    }

    public AuditRecord createDeposit (Double amount, long id) throws Exception{

        Wallet wallet = walletRepository.getWalletByUserId(id).get();

        if(wallet.getWalletStatusForTransaction().equals(WalletStatusForTransaction.LOCKED))    {
            throw new UnderTransaction("Your wallet is currently involved in another transaction");
        }

        Optional<AuditRecord> auditRecord = initiateAudit (id, amount);

        if(auditRecord.isEmpty()) {
            throw new UnderTransaction("Your wallet is currently involved in another transaction");
        }

        return auditRecord.get();
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    Optional<AuditRecord> initiateAudit (long id, double amount)  throws Exception    {

        Wallet wallet = walletRepository.getWalletByUserIdForUpdate(id).get();

        if(wallet.getWalletStatusForTransaction().equals(WalletStatusForTransaction.LOCKED))    {
            return null;
        }

        wallet.setWalletStatusForTransaction(WalletStatusForTransaction.LOCKED);

        wallet.setBalance(wallet.getBalance() + amount);
        wallet.setWalletStatusForTransaction(WalletStatusForTransaction.UNLOCKED);
        walletRepository.save(wallet);

        AuditRecord auditRecord = auditRecordService.createAuditRecord(wallet,
                amount,
                TransactionType.CREDIT,
                LocalDateTime.now());

        return Optional.ofNullable(auditRecord);
    }
}
