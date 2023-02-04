package com.vcriate.vcriateassignment.dtos.requestdtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTransferRequestDto {

    private long transferToUserId;
    private double amount;
}
