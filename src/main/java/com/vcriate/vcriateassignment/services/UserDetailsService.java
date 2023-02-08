package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.models.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private WalletService walletService;
    @Autowired
    public UserDetailsService (WalletService walletService) {
        this.walletService = walletService;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Wallet> wallet = walletService.findByUserName(username);

        if(wallet.isEmpty()) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }

        if(wallet.get().getRoleSet() == null || wallet.get().getRoleSet().isEmpty()) {
            throw new RuntimeException("User has no roles");
        }

        Collection<GrantedAuthority> authorities = wallet.get().getRoleSet().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(toList());

        for(GrantedAuthority authority : authorities)   {
            System.out.println(authority.getAuthority().toString());
        }

        return new User(wallet.get().getUsername(),
                wallet.get().getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }



}
