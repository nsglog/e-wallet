package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.models.Account;
import com.vcriate.vcriateassignment.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Component
public class UserDetailsServiceImplementation implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Account> _account = accountRepository.findByUsername(username);

        if(_account.isEmpty()) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }

        Account account = _account.get();

        System.out.println("kya user retrieve ho rha hai ?");

        if(account.getRoleSet() == null || account.getRoleSet().isEmpty()) {
            throw new RuntimeException("User has no roles");
        }

        Collection<GrantedAuthority> authorities = account.getRoleSet().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(toList());

        for(GrantedAuthority authority : authorities)   {
            System.out.println(authority.getAuthority().toString());
        }

        return new User(account.getUsername(),
                account.getPassword(),
                account.isEnabled(),
                !account.isExpired(),
                !account.isCredentialsExpired(),
                !account.isLocked(),
                authorities);
    }



}
