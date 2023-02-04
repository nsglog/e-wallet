package com.vcriate.vcriateassignment.controllers;

import com.vcriate.vcriateassignment.dtos.requestdtos.CreateDepositRequestDto;
import com.vcriate.vcriateassignment.dtos.responsedtos.CreateDepositResponseDto;
import com.vcriate.vcriateassignment.dtos.responsedtos.CreateTransferResponseDto;
import com.vcriate.vcriateassignment.models.Transaction;
import com.vcriate.vcriateassignment.services.CreateDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepositController {
    private CreateDepositService createDepositService;
    @Autowired
    public DepositController (CreateDepositService createDepositService)    {
        this.createDepositService = createDepositService;
    }

    @PostMapping(value = "/deposit/{id}")
    public CreateDepositResponseDto createDeposit (CreateDepositRequestDto requestDto, @PathVariable String id)  {

        Transaction transaction = createDepositService.createDeposit(requestDto.getAmount(), Long.parseLong(id));
        CreateDepositResponseDto createDepositResponseDto = new CreateDepositResponseDto();
        createDepositResponseDto.setMessage("SUCCESS");
        createDepositResponseDto.setTransaction(transaction);
        return createDepositResponseDto;
    }
}
