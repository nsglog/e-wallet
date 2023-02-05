package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.models.User;
import com.vcriate.vcriateassignment.models.Wallet;
import com.vcriate.vcriateassignment.repository.UserRepository;
import com.vcriate.vcriateassignment.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateWalletService {
    private CreateUserService createUserService;
    private WalletRepository walletRepository;
    private UserRepository userRepository;
    @Autowired
    public CreateWalletService(CreateUserService createUserService,
                               WalletRepository walletRepository,
                               UserRepository userRepository)  {
        this.createUserService = createUserService;
        this.walletRepository = walletRepository;
        this.userRepository= userRepository;
    }

    public Wallet createWallet (String name, String email, Long phoneNumber)  {

        User user = userRepository.getUserByPhoneNumber(phoneNumber);

        if(user != null)    return null;

        User new_user = createUserService.createUser(name, email, phoneNumber);
        Wallet wallet = new Wallet();
        wallet.setUser(new_user);
        wallet.setBalance(0.0);
        walletRepository.save(wallet);
        return wallet;
    }
}
