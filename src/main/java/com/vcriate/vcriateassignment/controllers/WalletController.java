package com.vcriate.vcriateassignment.controllers;

import com.vcriate.vcriateassignment.dtos.requestdtos.CreateWalletRequestDto;
import com.vcriate.vcriateassignment.dtos.responsedtos.CreateWalletResponseDto;
import com.vcriate.vcriateassignment.models.Wallet;
import com.vcriate.vcriateassignment.services.CreateWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {
    private CreateWalletService createWalletService;
    @Autowired
    public WalletController (CreateWalletService createWalletService)   {
        this.createWalletService = createWalletService;
    }

    @PostMapping(value = "/wallet")
    public CreateWalletResponseDto createWallet (@RequestBody CreateWalletRequestDto requestDto)   {

        Wallet wallet;
        CreateWalletResponseDto createWalletResponseDto = new CreateWalletResponseDto();

        try {
            wallet = createWalletService.createWallet(requestDto.getName(),
                        requestDto.getEmail(),
                        requestDto.getPhoneNumber());
            createWalletResponseDto.setWallet(wallet);
            createWalletResponseDto.setMessage("Wallet created successfully");
        }

        catch (Exception exception) {
            createWalletResponseDto.setMessage(exception.getMessage());
        }

        return createWalletResponseDto;
    }
}
