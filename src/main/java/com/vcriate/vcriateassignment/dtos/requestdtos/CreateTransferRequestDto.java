package com.vcriate.vcriateassignment.dtos.requestdtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTransferRequestDto {

    private long transferFromUserId;
    private double amount;
    private long transferToUserId;

}
