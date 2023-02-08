package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.exceptions.UserAlreadyExist;
import com.vcriate.vcriateassignment.models.Role;
import com.vcriate.vcriateassignment.models.User;
import com.vcriate.vcriateassignment.models.Wallet;
import com.vcriate.vcriateassignment.models.WalletStatusForTransaction;
import com.vcriate.vcriateassignment.repository.UserRepository;
import com.vcriate.vcriateassignment.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

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

    public User createWallet (String name,
                              String username,
                              String email,
                              Long phoneNumber,
                              String password, Set<Role> roleSet) throws Exception {



        Optional<User> user = userRepository.findUserByPhoneNumber(phoneNumber);

        if(!user.isEmpty())    {
            throw new UserAlreadyExist("User Already Exist");
        };

        User new_user = userService.createUser(name, email, phoneNumber, password);

        Wallet wallet = new Wallet();
        wallet.setUser(new_user);
        wallet.setUsername(username);
        wallet.setPassword(password);
        wallet.setBalance(0.0);
        wallet.setRoleSet(roleSet);
        wallet.setWalletStatusForTransaction(WalletStatusForTransaction.UNLOCKED);

        walletRepository.save(wallet);

        return new_user;
    }

    public Optional<Wallet> findByUserName(String username)   {
        return Optional.ofNullable(walletRepository.findByUserName(username));
    }

}
