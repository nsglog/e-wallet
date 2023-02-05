package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.models.AuditRecord;
import com.vcriate.vcriateassignment.models.TransactionType;
import com.vcriate.vcriateassignment.models.Wallet;
import com.vcriate.vcriateassignment.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateWithdrawService {
    private CreateAuditRecordService createAuditRecordService;
    private WalletRepository walletRepository;
    @Autowired
    public CreateWithdrawService(CreateAuditRecordService createAuditRecordService,
                                 WalletRepository walletRepository)   {
        this.createAuditRecordService = createAuditRecordService;
        this.walletRepository = walletRepository;
    }

    public AuditRecord createWithdraw (Double amount, long id) {

        Wallet wallet = walletRepository.getWalletByUserId(id);
        double currentAmount = wallet.getBalance();

        if(currentAmount > amount)  {
            double new_amount = currentAmount - amount;
            wallet.setBalance(new_amount);
            AuditRecord auditRecord = createAuditRecordService.createTransaction(new Wallet(),
                    null,
                    amount,
                    TransactionType.WITHDRAW,
                    LocalDateTime.now());
            return auditRecord;
        }

        return null;
    }
}
