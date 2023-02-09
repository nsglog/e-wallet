package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.models.Account;
import com.vcriate.vcriateassignment.models.Role;
import com.vcriate.vcriateassignment.models.Wallet;
import com.vcriate.vcriateassignment.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;
import java.util.Set;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private WalletService walletService;

    @Autowired
    public AccountService (AccountRepository accountRepository,
                           PasswordEncoder passwordEncoder,
                           WalletService walletService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.walletService = walletService;
    }

    public Account createAccount (String username, String password, Set<Role> roleSet,
                                  String name, String email, Long phoneNumber) throws Exception {

        Wallet wallet = walletService.createWallet(name, email, phoneNumber);

        Account account = new Account();
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        account.setEnabled(true);
        account.setExpired(false);
        account.setLocked(false);
        account.setCredentialsExpired(false);
        account.setRoleSet(roleSet);
        account.setWallet(wallet);

        accountRepository.save(account);

        return account;
    }
}
