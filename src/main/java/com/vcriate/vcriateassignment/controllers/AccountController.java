package com.vcriate.vcriateassignment.controllers;

import com.vcriate.vcriateassignment.dtos.requestdtos.CreateAccountRequestDto;
import com.vcriate.vcriateassignment.dtos.responsedtos.CreateAccountResponseDto;
import com.vcriate.vcriateassignment.models.Account;
import com.vcriate.vcriateassignment.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private AccountService accountService;
    @Autowired
    public AccountController (AccountService accountService)    {
        this.accountService = accountService;
    }

    @PostMapping("/account")
    public CreateAccountResponseDto createAccount (@RequestBody CreateAccountRequestDto createAccountRequestDto)  {
        CreateAccountResponseDto createAccountResponseDto = new CreateAccountResponseDto();
        Account account = null;

        try {
            account = accountService.createAccount(createAccountRequestDto.getUsername(),
                    createAccountRequestDto.getPassword(),
                    createAccountRequestDto.getRoleSet(),
                    createAccountRequestDto.getName(),
                    createAccountRequestDto.getEmail(),
                    createAccountRequestDto.getPhoneNumber());

            createAccountResponseDto.setAccount(account);
            createAccountResponseDto.setMessage("Account successfully created");

        }
        catch (Exception exception) {
            createAccountResponseDto.setMessage(exception.getMessage());
        }

        return createAccountResponseDto;
    }
}
