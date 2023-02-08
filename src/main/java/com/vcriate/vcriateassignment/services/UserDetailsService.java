package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.models.Wallet;
import com.vcriate.vcriateassignment.models.WalletStatusForTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private WalletService walletService;
    @Autowired
    public UserDetailsService (WalletService walletService) {
        this.walletService = walletService;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Wallet wallet = walletService.findByUserName(username);
        if(wallet == null) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        if(wallet.getRoleSet() == null || wallet.getRoleSet().isEmpty()) {
            throw new RuntimeException("User has no roles");
        }
        Collection<GrantedAuthority> authorities = wallet.getRoleSet().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(toList());
        return new User(wallet.getUsername(), wallet.getPassword(), true,true,true,true, authorities);
    }



}
