package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.dtos.responsedtos.GetAuditRecordsResponseDto;
import com.vcriate.vcriateassignment.exceptions.InsufficientFunds;
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
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawService {
    private AuditRecordService auditRecordService;
    private WalletRepository walletRepository;
    @Autowired
    public WithdrawService(AuditRecordService auditRecordService,
                           WalletRepository walletRepository)   {
        this.auditRecordService = auditRecordService;
        this.walletRepository = walletRepository;
    }

    public AuditRecord createWithdraw (Double amount, long id) throws Exception {

        Wallet wallet = walletRepository.findById(id).get();

        double currentAmount = wallet.getBalance();

        if(currentAmount < amount)  {
            throw  new InsufficientFunds("Your account has insufficient funds");
        }

        if(wallet.getWalletStatusForTransaction().equals(WalletStatusForTransaction.LOCKED))    {
            throw new UnderTransaction("Your account is currently involved in another transaction");
        }

        Optional<AuditRecord> auditRecord = initiateAudit (id, amount);

        if(auditRecord.isEmpty()) {
            throw new UnderTransaction("Your account is currently involved in a transaction");
        }

        return auditRecord.get();
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    Optional<AuditRecord> initiateAudit (long id, double amount)  throws Exception {

        Wallet wallet = walletRepository.getWalletByUserIdForUpdate(id).get();

        if(wallet.getWalletStatusForTransaction().equals(WalletStatusForTransaction.LOCKED))    {
            return null;
        }

        wallet.setWalletStatusForTransaction(WalletStatusForTransaction.LOCKED);
        walletRepository.save(wallet);

        double currentAmount = wallet.getBalance();

        double new_amount = currentAmount - amount;

        wallet.setBalance(new_amount);
        wallet.setWalletStatusForTransaction(WalletStatusForTransaction.UNLOCKED);
        walletRepository.save(wallet);

        AuditRecord auditRecord = auditRecordService.createAuditRecord(wallet,
                amount,
                TransactionType.DEBIT,
                LocalDateTime.now());

        return Optional.ofNullable(auditRecord);
    }
}