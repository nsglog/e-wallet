package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.exceptions.InsufficientFunds;
import com.vcriate.vcriateassignment.exceptions.InvalidUser;
import com.vcriate.vcriateassignment.exceptions.UnderTransaction;
import com.vcriate.vcriateassignment.models.AuditRecord;
import com.vcriate.vcriateassignment.models.TransactionType;
import com.vcriate.vcriateassignment.models.Wallet;
import com.vcriate.vcriateassignment.models.WalletStatusForTransaction;
import com.vcriate.vcriateassignment.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;

@Service
public class TransferService {
    private AuditRecordService auditRecordService;
    private WalletRepository walletRepository;
    @Autowired
    public TransferService(AuditRecordService auditRecordService,
                           WalletRepository walletRepository)   {
        this.auditRecordService = auditRecordService;
        this.walletRepository = walletRepository;
    }

    public AuditRecord createTransfer (long transferToUserId, double amount,
                                       long transferFromUserId) throws Exception {

        Optional<Wallet> _transferToWallet = walletRepository.getWalletByUserId(transferToUserId);
        Optional<Wallet> _transferFromWallet = walletRepository.getWalletByUserId(transferToUserId);

        if(_transferToWallet == null)    {
            throw new InvalidUser("User does not exist, please enter a valid user");
        }

        if(_transferFromWallet.get().getBalance() < amount) {
            throw new InsufficientFunds("Your wallet has insufficient funds");
        }

        AuditRecord auditRecord = initiateAudit (transferFromUserId, transferToUserId, amount);

        return auditRecord;
    }

    @Transactional(isolation = SERIALIZABLE)
    AuditRecord initiateAudit(long fromUserId, long toUserId, double amount)   {

        Wallet transferFromWallet = walletRepository.getWalletByUserIdForUpdate(fromUserId).get();
        Wallet transferToWallet = walletRepository.getWalletByUserIdForUpdate(toUserId).get();

        double current_amount = transferFromWallet.getBalance();
        AuditRecord auditRecord = null;

        if(current_amount >= amount) {

            double new_amount = current_amount - amount;

            transferFromWallet.setBalance(new_amount);
            transferToWallet.setBalance(transferToWallet.getBalance() + amount);
            walletRepository.save(transferFromWallet);
            walletRepository.save(transferToWallet);

            auditRecord = auditRecordService.createAuditRecord(transferFromWallet,
                    transferToWallet,
                    amount,
                    TransactionType.TRANSFER,
                    LocalDateTime.now());

        }

        return auditRecord;
    }
}
