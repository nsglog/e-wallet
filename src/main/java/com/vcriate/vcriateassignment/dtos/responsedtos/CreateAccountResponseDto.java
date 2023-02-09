package com.vcriate.vcriateassignment.dtos.responsedtos;

import com.vcriate.vcriateassignment.models.Account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountResponseDto {

    private Account account;
    private String message;

}
