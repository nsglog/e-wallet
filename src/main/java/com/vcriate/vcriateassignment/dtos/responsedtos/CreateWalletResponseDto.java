package com.vcriate.vcriateassignment.dtos.responsedtos;

import com.vcriate.vcriateassignment.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWalletResponseDto {

    private User user;
    private String message;
}
