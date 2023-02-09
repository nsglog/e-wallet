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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    public AuditRecord createTransfer (long transferFromUserId, double amount,
                                       long transferToUserId) throws Exception {

        Optional<Wallet> _transferToWallet = walletRepository.findById(transferToUserId);
        Optional<Wallet> _transferFromWallet = walletRepository.findById(transferFromUserId);

        if(_transferToWallet.isEmpty())    {
            throw new InvalidUser("User does not exist, please enter a valid user");
        }

        if(_transferFromWallet.get().getBalance() < amount) {
            throw new InsufficientFunds("Your wallet has insufficient funds");
        }

        if(_transferFromWallet.get().getWalletStatusForTransaction().equals(WalletStatusForTransaction.LOCKED) ||
            _transferToWallet.get().getWalletStatusForTransaction().equals(WalletStatusForTransaction.LOCKED))  {

            throw new UnderTransaction("One of the user already involve in another transaction");
        }

        Optional<AuditRecord> auditRecord = initiateAudit (transferFromUserId, transferToUserId, amount);

        if (auditRecord.isEmpty())    {
            throw new UnderTransaction("One of the user already involve in another transaction");
        }

        return auditRecord.get();
    }

    @Transactional(isolation = SERIALIZABLE)
    Optional<AuditRecord> initiateAudit(long fromUserId, long toUserId, double amount)   {

        Wallet transferFromWallet = walletRepository.getWalletByUserIdForUpdate(fromUserId).get();
        Wallet transferToWallet = walletRepository.getWalletByUserIdForUpdate(toUserId).get();

        if(transferFromWallet.getWalletStatusForTransaction().equals(WalletStatusForTransaction.LOCKED) ||
                transferToWallet.getWalletStatusForTransaction().equals(WalletStatusForTransaction.LOCKED))  {

            return null;
        }

        transferToWallet.setWalletStatusForTransaction(WalletStatusForTransaction.LOCKED);
        transferFromWallet.setWalletStatusForTransaction(WalletStatusForTransaction.LOCKED);

        walletRepository.save(transferFromWallet);
        walletRepository.save(transferFromWallet);

        double current_amount = transferFromWallet.getBalance();
        AuditRecord auditRecordForDeposit = null;
        AuditRecord auditRecordForWithDraw = null;

        double new_amount = current_amount - amount;

        transferFromWallet.setBalance(new_amount);
        transferToWallet.setBalance(transferToWallet.getBalance() + amount);

        walletRepository.save(transferFromWallet);
        walletRepository.save(transferToWallet);

        auditRecordForWithDraw = auditRecordService.createAuditRecord(transferFromWallet,
                amount,
                TransactionType.DEBIT,
                LocalDateTime.now());
        auditRecordForDeposit = auditRecordService.createAuditRecord(transferToWallet,
                amount,
                TransactionType.CREDIT,
                LocalDateTime.now());


        transferFromWallet.setWalletStatusForTransaction(WalletStatusForTransaction.UNLOCKED);
        transferToWallet.setWalletStatusForTransaction(WalletStatusForTransaction.UNLOCKED);

        walletRepository.save(transferFromWallet);
        walletRepository.save(transferToWallet);

        return Optional.ofNullable(auditRecordForWithDraw);
    }
}
