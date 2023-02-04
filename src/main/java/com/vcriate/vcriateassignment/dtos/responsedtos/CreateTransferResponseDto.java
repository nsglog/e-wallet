package com.vcriate.vcriateassignment.dtos.responsedtos;

import com.vcriate.vcriateassignment.models.Transaction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTransferResponseDto {

    private Transaction transaction;
    private String message;
}
