package com.vcriate.vcriateassignment.dtos.responsedtos;

import com.vcriate.vcriateassignment.models.Wallet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWalletResponseDto {

    private Wallet wallet;
    private String message;
}
