package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.exceptions.InsufficientFunds;
import com.vcriate.vcriateassignment.models.AuditRecord;
import com.vcriate.vcriateassignment.models.TransactionType;
import com.vcriate.vcriateassignment.models.Wallet;
import com.vcriate.vcriateassignment.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        Optional<Wallet> _wallet = walletRepository.getWalletByUserId(id);
        Wallet wallet = _wallet.get();

        double currentAmount = wallet.getBalance();

        if(currentAmount > amount)  {

            double new_amount = currentAmount - amount;
            wallet.setBalance(new_amount);
            walletRepository.save(wallet);

            AuditRecord auditRecord = auditRecordService.createAuditRecord(wallet,
                    null,
                    amount,
                    TransactionType.WITHDRAW,
                    LocalDateTime.now());

            return auditRecord;
        }

        throw new InsufficientFunds("Insufficient Funds");
    }
}
