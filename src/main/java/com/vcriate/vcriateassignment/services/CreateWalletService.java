package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.models.User;
import com.vcriate.vcriateassignment.models.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateWalletService {
    private CreateUserService createUserService;
    @Autowired
    public CreateWalletService(CreateUserService createUserService)  {
        this.createUserService = createUserService;
    }

    public Wallet createWallet (String name, String email, Long phoneNumber)  {

        User user = createUserService.createUser(name, email, phoneNumber);
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(0.0);
        return wallet;
    }
}
