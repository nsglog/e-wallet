package com.vcriate.vcriateassignment.dtos.requestdtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWalletRequestDto {

    private String name;
    private String email;
    private Long phoneNumber;

}
