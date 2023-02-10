package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.exceptions.UnauthorizeRequest;
import com.vcriate.vcriateassignment.models.Account;
import com.vcriate.vcriateassignment.models.Wallet;
import com.vcriate.vcriateassignment.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RequestAuthenticationService {
    private AccountRepository accountRepository;

    @Autowired
    public RequestAuthenticationService(AccountRepository accountRepository)    {
        this.accountRepository = accountRepository;
    }
    public boolean isRequestAuthentic(String id)    {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepository.findByUsername(username).get();
        Wallet wallet = account.getWallet();
        Long walletId = wallet.getId();
        return (walletId == Long.parseLong(id));
    }
}
