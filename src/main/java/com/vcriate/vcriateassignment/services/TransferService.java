package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.exceptions.InsufficientFunds;
import com.vcriate.vcriateassignment.exceptions.InvalidUser;
import com.vcriate.vcriateassignment.models.AuditRecord;
import com.vcriate.vcriateassignment.models.TransactionType;
import com.vcriate.vcriateassignment.models.Wallet;
import com.vcriate.vcriateassignment.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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
        Optional<Wallet> _transferFromWallet = walletRepository.getWalletByUserId(transferFromUserId);

        if(_transferToWallet == null)    {
            throw new InvalidUser("User does not exist");
        }

        Wallet transferToWallet = _transferToWallet.get();
        Wallet transferFromWallet = _transferFromWallet.get();

        double current_amount = transferFromWallet.getBalance();

        if(current_amount >= amount) {

            double new_amount = current_amount - amount;

            transferFromWallet.setBalance(new_amount);
            transferToWallet.setBalance(transferToWallet.getBalance() + amount);

            AuditRecord auditRecord = auditRecordService.createAuditRecord(transferFromWallet,
                    transferToWallet,
                    amount,
                    TransactionType.TRANSFER,
                    LocalDateTime.now());

            return auditRecord;
        }

        throw new InsufficientFunds("Insufficient Funds");
    }
}
