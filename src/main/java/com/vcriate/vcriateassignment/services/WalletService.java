package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.exceptions.UserAlreadyExist;
import com.vcriate.vcriateassignment.models.Wallet;
import com.vcriate.vcriateassignment.models.WalletStatusForTransaction;
import com.vcriate.vcriateassignment.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletService {
    private WalletRepository walletRepository;


    @Autowired
    public WalletService(WalletRepository walletRepository)  {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet (String name,
                              String email,
                              Long phoneNumber) throws Exception {



        Optional<Wallet> _wallet = walletRepository.findWalletByPhoneNumber(phoneNumber);

        if(!_wallet.isEmpty())    {
            throw new UserAlreadyExist("User Already Exist");
        };

        Wallet wallet = new Wallet();
        wallet.setName(name);
        wallet.setEmail(email);
        wallet.setPhoneNumber(phoneNumber);
        wallet.setBalance(0.0);
        wallet.setWalletStatusForTransaction(WalletStatusForTransaction.UNLOCKED);

        walletRepository.save(wallet);

        return wallet;
    }
}
