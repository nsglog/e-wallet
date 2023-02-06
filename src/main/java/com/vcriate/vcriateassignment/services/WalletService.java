package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.exceptions.UserAlreadyExist;
import com.vcriate.vcriateassignment.models.User;
import com.vcriate.vcriateassignment.models.Wallet;
import com.vcriate.vcriateassignment.models.WalletStatusForTransaction;
import com.vcriate.vcriateassignment.repository.UserRepository;
import com.vcriate.vcriateassignment.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletService {
    private UserService userService;
    private WalletRepository walletRepository;
    private UserRepository userRepository;
    @Autowired
    public WalletService(UserService userService,
                         WalletRepository walletRepository,
                         UserRepository userRepository)  {
        this.userService = userService;
        this.walletRepository = walletRepository;
        this.userRepository= userRepository;
    }

    public Wallet createWallet (String name, String email, Long phoneNumber) throws Exception {

        Optional<User> user = userRepository.getUserByPhoneNumber(phoneNumber);

        if(user != null)    {
            throw new UserAlreadyExist("User Already Exist");
        };

        User new_user = userService.createUser(name, email, phoneNumber);
        Wallet wallet = new Wallet();
        wallet.setUser(new_user);
        wallet.setBalance(0.0);
        wallet.setWalletStatusForTransaction(WalletStatusForTransaction.OPEN);
        walletRepository.save(wallet);
        return wallet;
    }
}
