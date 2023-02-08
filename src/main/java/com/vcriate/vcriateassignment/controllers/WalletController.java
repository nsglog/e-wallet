package com.vcriate.vcriateassignment.controllers;

import com.vcriate.vcriateassignment.dtos.requestdtos.CreateWalletRequestDto;
import com.vcriate.vcriateassignment.dtos.responsedtos.CreateWalletResponseDto;
import com.vcriate.vcriateassignment.models.User;
import com.vcriate.vcriateassignment.services.WalletService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {
    private WalletService walletService;
    @Autowired
    public WalletController (WalletService walletService)   {
        this.walletService = walletService;
    }


    @PermitAll
    @PostMapping(value = "/wallet")
    public @ResponseBody CreateWalletResponseDto createWallet (@RequestBody CreateWalletRequestDto requestDto)   {

        User user;
        CreateWalletResponseDto createWalletResponseDto = new CreateWalletResponseDto();

        try {
            user = walletService.createWallet(requestDto.getName(),
                        requestDto.getUsername(),
                        requestDto.getEmail(),
                        requestDto.getPhoneNumber(),
                        requestDto.getPassword(),
                        requestDto.getRoleSet());
            createWalletResponseDto.setUser(user);
            createWalletResponseDto.setMessage("wallet for user created successfully");
        }

        catch (Exception exception) {
            createWalletResponseDto.setMessage(exception.getMessage());
        }

        return createWalletResponseDto;
    }
}
